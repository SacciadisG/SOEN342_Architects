# Fully-Dressed Use Case – Create Task

## Use Case Name
Create Task

## Primary Actor
User

## Scope
Personal Task Management System

## Level
User goal level

---

## Stakeholders & Interests
- **User** – wants to create and track a new task for future reference and management.

---

## Preconditions
- The system is running.
- The user has loged in.

---

## Success Guarantee (Postconditions)
- A new Task instance is created.
- The task title and description are set from user input.
- The task status is initialized to "open".
- The task creation date is set to the current system date.
- The task is stored in the system.
- An activity entry is recorded for the task creation.

---

## Main Success Scenario (Basic Flow)

1. The user selects the option to create a new task.
2. The system prompts the user to enter task details.
3. The user enters a title and an optional description.
4. The user optionally specifies a due date.
5. The user submits the task.
6. The system validates the input.
7. The system creates a new Task instance.
8. The system initializes default values (status, creation date, default priority).
9. The system stores the task.
10. The system records an activity entry.
11. The system confirms successful task creation to the user.

---

## Extensions (Alternative / Failure Flow)

1. Title is empty

2. The system displays an error message indicating that the title is required.  
3. The task is not created.  
4. The user may re-enter valid task information.

---

## Special Requirements
- The system must automatically record the task creation date.
- The system must initialize the task status to "open".

---

## Open Issues
- Default priority value to be confirmed.
