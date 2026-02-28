### **Use Case: Update Task Status**

**Primary Actor:** User  
**Scope:** Personal Task Management System  
**Level:** User Goal  
**Stakeholders and Interests:** 
* **User:** Wants to accurately reflect the progress of their work and maintain a history of changes.

**Preconditions:** 
* The system is running and the `TaskCatalog` is loaded.  
* At least one task exists in the system.

**Main Success Scenario (Success Case):** 1. User requests to view their tasks to identify the specific ID.  
2. System displays a list of tasks with their current statuses and IDs.  
3. User enters the `updateTaskStatus` command with a specific **TaskID** and a **New Status** (e.g., "completed").  
4. System validates that the TaskID exists.  
5. System validates that the New Status is one of the allowed values (*open*, *completed*, *cancelled*).  
6. System updates the status of the task.  
7. System creates an `ActivityEntry` with a timestamp and a description of the change.  
8. System confirms the update was successful to the user.

**Extensions (Failure Cases):** 
* **4a. TaskID not found:** 
    1. System informs the user that the ID does not exist.  
    2. System prompts for a valid ID or allows the user to cancel the operation.  
* **5a. Invalid Status provided:** 
    1. User enters a status not recognized by the system (e.g., "in-progress" or "deleted").  
    2. System informs the user that the status is invalid and lists the allowed options (*open*, *completed*, *cancelled*).  
    3. Use case continues at step 3.