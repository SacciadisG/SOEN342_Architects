package taskmanagement.csv;

import taskmanagement.catalog.CollaboratorCatalog;
import taskmanagement.catalog.ProjectCatalog;
import taskmanagement.catalog.TaskCatalog;
import taskmanagement.domain.*;
import taskmanagement.enums.PriorityEnum;
import taskmanagement.enums.StatusEnum;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles CSV export and import for the task management system.
 *
 * TSK1.03.01 — CSV Handling & Export Service
 * TSK1.03.02 — CSV Import/Parsing Logic
 * TSK1.03.03 — Import Auto-Create Logic for Projects
 * TSK1.03.04 — Import Auto-Create Logic for Collaborators
 * TSK1.03.05 — Bulk Import Integration
 */
public class CSVHandler {

    // Column order as specified in the project requirements (Iteration II)
    private static final String HEADER =
        "TaskName,Description,Subtask,Status,Priority,DueDate,ProjectName,ProjectDescription,Collaborator,CollaboratorCategory";

    private static final List<String> REQUIRED_FIELDS =
        Arrays.asList("TaskName", "Status", "Priority");

    private final TaskCatalog taskCatalog;
    private final ProjectCatalog projectCatalog;
    private final CollaboratorCatalog collaboratorCatalog;

    public CSVHandler(TaskCatalog taskCatalog,
                      ProjectCatalog projectCatalog,
                      CollaboratorCatalog collaboratorCatalog) {
        this.taskCatalog = taskCatalog;
        this.projectCatalog = projectCatalog;
        this.collaboratorCatalog = collaboratorCatalog;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TSK1.03.01 — Export Service
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Exports all tasks to a CSV file at the given path.
     * Tasks with multiple subtasks produce multiple rows (one per subtask).
     * Tasks with no subtasks produce a single row with empty subtask/collaborator columns.
     */
    public void exportToCSV(String filePath) throws IOException {
        List<Task> tasks = taskCatalog.getAllTasks();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(HEADER);
            writer.newLine();

            for (Task task : tasks) {
                for (String row : buildRows(task)) {
                    writer.write(row);
                    writer.newLine();
                }
            }
        }
    }

    /** Maps one Task to one or more CSV row strings. */
    private List<String> buildRows(Task task) {
        String taskName    = escape(task.getTitle());
        String description = escape(task.getDescription());
        String status      = task.getStatus().name();
        String priority    = task.getPriority().name();
        String dueDate     = task.getDueDate() != null ? task.getDueDate().toString() : "";
        String projName    = "";
        String projDesc    = "";

        if (task.getProject() != null) {
            projName = escape(task.getProject().getName());
            projDesc = escape(task.getProject().getDescription());
        }

        List<Subtask> subtasks = task.getSubtasks();
        if (subtasks == null || subtasks.isEmpty()) {
            return Collections.singletonList(
                joinRow(taskName, description, "", status, priority, dueDate, projName, projDesc, "", "")
            );
        }

        return subtasks.stream().map(subtask -> {
            String subtaskTitle = escape(subtask.getTitle());
            String collabName   = "";
            String collabCat    = "";
            if (subtask.getAssignedCollaborator() != null) {
                Collaborator c = subtask.getAssignedCollaborator();
                collabName = escape(c.getName());
                collabCat  = categoryOf(c);
            }
            return joinRow(taskName, description, subtaskTitle, status, priority, dueDate, projName, projDesc, collabName, collabCat);
        }).collect(Collectors.toList());
    }

    private String categoryOf(Collaborator c) {
        if (c instanceof SeniorCollaborator)       return "Senior";
        if (c instanceof IntermediateCollaborator) return "Intermediate";
        if (c instanceof JuniorCollaborator)       return "Junior";
        return "";
    }

    private String joinRow(String... fields) {
        return String.join(",", fields);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TSK1.03.02 — CSV Import / Parsing Logic
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Reads a CSV file and returns a list of validated row maps.
     * Validates required fields and enum values for each row.
     *
     * @throws IOException              if the file cannot be read
     * @throws IllegalArgumentException if any validation errors are found (all listed)
     */
    public List<Map<String, String>> parseAndValidateCSV(String filePath) throws IOException {
        List<Map<String, String>> records = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String headerLine = reader.readLine();
            if (headerLine == null) throw new IOException("CSV file is empty.");

            String[] headers = parseLine(headerLine);
            validateHeaders(headers, errors);
            if (!errors.isEmpty()) {
                throw new IllegalArgumentException("CSV header errors:\n" + String.join("\n", errors));
            }

            String line;
            int lineNum = 2;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) { lineNum++; continue; }

                String[] values = parseLine(line);
                Map<String, String> record = new LinkedHashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    record.put(headers[i].trim(), i < values.length ? values[i].trim() : "");
                }

                errors.addAll(validateRecord(record, lineNum));
                records.add(record);
                lineNum++;
            }
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("CSV validation errors:\n" + String.join("\n", errors));
        }

        return records;
    }

    private void validateHeaders(String[] headers, List<String> errors) {
        Set<String> headerSet = new HashSet<>(Arrays.asList(headers));
        for (String req : REQUIRED_FIELDS) {
            if (!headerSet.contains(req)) errors.add("Missing required column: " + req);
        }
    }

    private List<String> validateRecord(Map<String, String> record, int lineNum) {
        List<String> errors = new ArrayList<>();

        String taskName = record.getOrDefault("TaskName", "");
        String status   = record.getOrDefault("Status",   "");
        String priority = record.getOrDefault("Priority", "");
        String dueDate  = record.getOrDefault("DueDate",  "");

        if (taskName.isBlank())
            errors.add("Line " + lineNum + ": 'TaskName' is required.");

        if (status.isBlank()) {
            errors.add("Line " + lineNum + ": 'Status' is required.");
        } else {
            try { StatusEnum.valueOf(status.toUpperCase()); }
            catch (IllegalArgumentException e) {
                errors.add("Line " + lineNum + ": Invalid Status '" + status + "'. Expected: OPEN, COMPLETED, CANCELLED.");
            }
        }

        if (priority.isBlank()) {
            errors.add("Line " + lineNum + ": 'Priority' is required.");
        } else {
            try { PriorityEnum.valueOf(priority.toUpperCase()); }
            catch (IllegalArgumentException e) {
                errors.add("Line " + lineNum + ": Invalid Priority '" + priority + "'. Expected: LOW, MEDIUM, HIGH.");
            }
        }

        if (!dueDate.isBlank()) {
            try { LocalDate.parse(dueDate); }
            catch (DateTimeParseException e) {
                errors.add("Line " + lineNum + ": Invalid DueDate '" + dueDate + "'. Expected format: YYYY-MM-DD.");
            }
        }

        return errors;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TSK1.03.03 — Auto-Create Logic for Projects
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns the existing project with the given name, or auto-creates a new one.
     * Project names are unique per spec — the same name always resolves to the same project.
     */
    private Project resolveProject(String name, String description) {
        if (name == null || name.isBlank()) return null;

        Project existing = projectCatalog.findByName(name);
        if (existing != null) return existing;

        Project newProject = new Project(null, name, description);
        projectCatalog.addProject(newProject);
        System.out.println("[Import] Auto-created project: \"" + name + "\"");
        return newProject;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TSK1.03.04 — Auto-Create Logic for Collaborators
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns the existing collaborator with the given name, or auto-creates one
     * based on CollaboratorCategory (defaults to Junior if unrecognized).
     */
    private Collaborator resolveCollaborator(String name, String category) {
        if (name == null || name.isBlank()) return null;

        Collaborator existing = collaboratorCatalog.findByName(name);
        if (existing != null) return existing;

        Collaborator newCollab;
        switch (category.trim().toLowerCase()) {
            case "senior":
                newCollab = new SeniorCollaborator(null, name);
                break;
            case "intermediate":
                newCollab = new IntermediateCollaborator(null, name);
                break;
            case "junior":
            default:
                newCollab = new JuniorCollaborator(null, name);
                break;
        }

        collaboratorCatalog.addCollaborator(newCollab);
        System.out.println("[Import] Auto-created collaborator: \"" + name + "\" (" + category + ")");
        return newCollab;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TSK1.03.05 — Bulk Import Integration
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Full import pipeline:
     *   parse → validate → resolve projects/collaborators → bulk save to TaskCatalog
     *
     * Multiple rows with the same TaskName are merged into one Task with multiple subtasks.
     *
     * @return the number of distinct tasks imported
     */
    public int importFromCSV(String filePath) throws IOException {
        List<Map<String, String>> records = parseAndValidateCSV(filePath);

        // LinkedHashMap to preserve insertion order; keyed by TaskName for row merging
        Map<String, Task> taskMap = new LinkedHashMap<>();

        for (Map<String, String> record : records) {
            String taskName     = record.get("TaskName");
            String description  = record.getOrDefault("Description",         "");
            String statusStr    = record.getOrDefault("Status",              "OPEN");
            String priorityStr  = record.getOrDefault("Priority",            "MEDIUM");
            String dueDateStr   = record.getOrDefault("DueDate",             "");
            String projName     = record.getOrDefault("ProjectName",         "");
            String projDesc     = record.getOrDefault("ProjectDescription",  "");
            String collabName   = record.getOrDefault("Collaborator",        "");
            String collabCat    = record.getOrDefault("CollaboratorCategory","Junior");
            String subtaskTitle = record.getOrDefault("Subtask",             "");

            // Get or create the Task object for this TaskName
            Task task = taskMap.computeIfAbsent(taskName, name -> {
                StatusEnum   s = StatusEnum.valueOf(statusStr.toUpperCase());
                PriorityEnum p = PriorityEnum.valueOf(priorityStr.toUpperCase());

                LocalDate dueDate = null;
                if (!dueDateStr.isBlank()) {
                    try { dueDate = LocalDate.parse(dueDateStr); }
                    catch (DateTimeParseException ignored) { /* validated upstream */ }
                }

                Task t = new Task(null, name, description, LocalDate.now(), dueDate, p, s);

                // TSK1.03.03: resolve/auto-create project
                Project project = resolveProject(projName, projDesc);
                if (project != null) t.setProject(project);

                return t;
            });

            // Attach subtask + TSK1.03.04: resolve/auto-create collaborator
            if (!subtaskTitle.isBlank()) {
                Subtask subtask = new Subtask(null, subtaskTitle, StatusEnum.OPEN);
                if (!collabName.isBlank()) {
                    subtask.setAssignedCollaborator(resolveCollaborator(collabName, collabCat));
                }
                task.addSubtask(subtask);
            }
        }

        // Bulk-save all resolved tasks to the TaskCatalog
        for (Task task : taskMap.values()) {
            taskCatalog.addTask(task);
        }

        System.out.println("[Import] Imported " + taskMap.size() + " task(s) from \"" + filePath + "\".");
        return taskMap.size();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Utility — CSV parsing & formatting
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Parses a single CSV line respecting RFC 4180 quoting.
     * Package-private for unit testing.
     */
    String[] parseLine(String line) {
        List<String> fields = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                // Escaped quote inside quoted field: "" → "
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                fields.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString());
        return fields.toArray(new String[0]);
    }

    /**
     * Wraps a value in double-quotes if it contains commas, quotes, or newlines.
     * Internal double-quotes are escaped as "" per RFC 4180.
     */
    private String escape(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
