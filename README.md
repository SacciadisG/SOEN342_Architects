# SOEN342_Architects

## Project Overview

A CLI-based task management system built in Java. It allows users to create and organize tasks within projects, break tasks down into subtasks, assign collaborators with role-based task limits, set up recurring task schedules, and export data to CSV or iCalendar formats. All data is persisted locally in a SQLite database.

### Key Features

- **Task Management** — Create, update, delete, and track tasks with priorities (`LOW`, `MEDIUM`, `HIGH`) and statuses (`OPEN`, `COMPLETED`, `CANCELLED`).
- **Project Organization** — Group tasks into projects. Add or remove tasks from projects and view tasks by project.
- **Collaborators** — Add team members as Senior (max 2 open tasks), Intermediate (max 5), or Junior (max 10). The system enforces these limits when assigning work.
- **Subtasks** — Break tasks into smaller subtasks, each with their own status and optional collaborator assignment.
- **Tags** — Label tasks with custom tags and filter by them.
- **Recurring Tasks** — Set up tasks that repeat on a daily, weekly, or monthly schedule using a configurable recurrence pattern. Recurring tasks are only generated on the day they are set to, not beforehand. For example, if a task is set to recur daily, the next occurence of this task will be automatically generated the day after this recurrence is set (and so on).
- **Import/Export** — Export all tasks to CSV or import tasks (with projects, subtasks, and collaborators) from a CSV file.
- **Calendar Integration** — Export individual tasks, project tasks, or filtered task lists to `.ics` (iCalendar) format.
- **Activity History** — Every task modification is logged with a timestamp. View the full change history for any task.
- **Search & Filtering** — Find tasks by keyword, title, status, priority, tag, date range, due date, or day of the week.

## CLI Commands

Once the application is running, type any command below. Type `help` to see this list in-app, or `exit` to quit.

| Command | Description |
|---|---|
| `create-task` | Create a new task |
| `update-task` | Update an existing task |
| `update-task-status` | Update a task's status (OPEN, COMPLETED, CANCELLED) |
| `view-task` | View detailed task information |
| `view-all-tasks` | View all tasks with sorting options |
| `view-tasks-by-priority` | View all tasks filtered by priority level |
| `view-tasks-by-status` | View all tasks filtered by status |
| `view-tasks-by-date-range` | View all tasks within a date range |
| `view-tasks-by-due-date` | View tasks with a specific due date |
| `search-tasks` | Search tasks by criteria (title, date range, status, or day) |
| `search-tasks-by-keyword` | Search tasks by keyword in title or description |
| `view-task-history` | View the activity history for a specific task |
| `add-tag-to-task` | Add a tag to a task |
| `view-tasks-by-tag` | View all tasks filtered by a specific tag |
| `add-subtask` | Add a subtask to a task |
| `update-subtask-status` | Update a subtask's status |
| `create-project` | Create a new project |
| `add-task-to-project` | Add a task to a project |
| `remove-task-from-project` | Remove a task from a project |
| `view-tasks-by-project` | View all tasks in a specific project |
| `create-collaborator` | Create a new collaborator (senior, intermediate, or junior) |
| `add-collaborator-to-task` | Add a collaborator to a task |
| `add-collaborator-to-project` | Add a collaborator to a project |
| `create-recurring-task` | Create a recurring task with daily, weekly, or monthly pattern |
| `export-tasks` | Export tasks to CSV |
| `import-tasks` | Import tasks from CSV |
| `export-calendar` | Export tasks to iCalendar format (.ics file) |
| `help` | Display the help message |
| `exit` | Save data and quit the application |

## How to Use

1. Launch the application (see **Running the App** below).
2. The system loads any previously saved data from the local SQLite database.
3. At the prompt, type a command name (case-insensitive) and follow the on-screen prompts. For example:
   - `create-task` → enter a title, description, and optional due date (comma-separated, date as `YYYY-MM-DD`).
   - `create-project` → enter a project name and description, then use `add-task-to-project` to assign tasks.
   - `create-collaborator` → choose a role type, then use `add-collaborator-to-task` to assign them.
4. When you type `exit`, all in-memory data is saved to the database and the connection is closed.

## Running the App

### Dependencies

- **Java 19+** (JDK)
- **Apache Maven 3.6+**

Both must be installed and available on your `PATH`.

### Quick Start

From the project root, run the PowerShell script:

```powershell
.\runApp.ps1
```

This builds the project and launches the application in one step.

### Manual Steps (if preferred)

Build the fat JAR:
```bash
mvn clean package
```

Run it:
```bash
java -jar target/task-management-system-1.0-SNAPSHOT.jar
```

## Team Members

| Student Name | Student ID |
|---|---|
| Gregory Sacciadis | 40207512 |
| Mateo Garzon Velasco | 40277001 |
| Yifu Li | 40286100 |