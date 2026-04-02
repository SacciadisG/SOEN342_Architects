# SOEN342_Architects

## Project Overview
A comprehensive task management system built in Java that helps teams and individuals organize, track, and collaborate on projects and tasks. The system supports project hierarchies, task dependencies through subtasks, team collaboration, recurring tasks, calendar integration, and detailed activity tracking.

### Key Features
- **Task Management**: Create, update, search, and track tasks with priorities and statuses
- **Project Organization**: Group tasks into projects and manage them collectively
- **Collaborators**: Add team members with different roles (Senior, Junior, or Collaborator)
- **Subtasks & Tags**: Break down tasks and organize them with custom tags
- **Recurring Tasks**: Automate task creation on daily, weekly, or monthly schedules
- **Calendar Integration**: Export tasks to and import from calendar formats
- **Search & Filtering**: Find tasks by keywords, date ranges, due dates, or tags
- **Activity History**: Track all changes and modifications to tasks

## Building the Project
```bash
mvn clean package
```

## Running the Application
```bash
java -cp ".:target/classes:$(mvn dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout)" taskmanagement.Main
```

Or using Maven directly:
```bash
mvn exec:java -Dexec.mainClass="taskmanagement.Main"
```

## Team Members
- Gregory Sacciadis (40207512)
- Mateo Garzon Velasco (40277001)
- Yifu Li (40286100) 
