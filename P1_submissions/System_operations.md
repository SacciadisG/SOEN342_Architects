1.	createTask(title, description, priority, dueDate)
	Responsibility: Instantiates a new Task, assigns a unique ID, and adds it to the TaskCatalog.
2.	createProject(name, description)
	Responsibility: Instantiates a new Project and adds it to the ProjectCatalog.
3.	updateTaskStatus(taskId, newStatus)
	Responsibility: Modifies the status of an existing task and triggers an activity log entry.
4.	assignTaskToProject(taskId, projectId)
	Responsibility: Creates an aggregation link between a Task (from the TaskCatalog) and a Project (from the ProjectCatalog).
5.	removeTaskFromProject(taskId)
	Responsibility: Breaks the association between a Task and its current Project without deleting the Task from the TaskCatalog.
6.	createSubtask(parentTaskId, title)
	Responsibility: Creates a Subtask that is compositionally owned by a specific parent Task.
7.	addTagToTask(taskId, tagKeyword)
	Responsibility: Creates or finds a Tag and associates it with the specified Task.
8.	searchTasks(keyword)
	Responsibility: Queries the TaskCatalog for matches in titles or descriptions.
9.	viewTasks(criteria)
	Responsibility: A polymorphic operation or set of operations to filter tasks by date, priority, status, project, or tag.
10.	viewTaskHistory(taskId)
	Responsibility: Retrieves all ActivityEntry instances associated with a specific Task.
11.	createActivityEntry(taskId, description)
	Responsibility: An internal-facing operation used by other methods to record system changes
<img width="468" height="492" alt="image" src="https://github.com/user-attachments/assets/2398f4f3-bb23-4265-a06f-3fbbe45f276e" />
