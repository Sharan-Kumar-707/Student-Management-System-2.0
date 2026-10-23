# Student Record Management System

A beginner-friendly, console-based Java application for managing student records. It supports adding, viewing, updating, and deleting students.

## Features

- Add, view, update, and delete student records
- Uses `ArrayList<Student>` to manage records in memory
- Saves records to `students.txt` and loads them when the program starts
- Encapsulates student fields using `private` fields and getters/setters
- Rejects non-positive IDs and ages, empty names, and duplicate IDs
- Handles non-numeric menu and data input without crashing
- Uses an `Iterator` for safe deletion
- Separates student data, record management, and menu interaction into different classes

## Requirements

- Java JDK 8 or newer

## Run the application

Open a terminal in this folder and run:

```bash
javac Student.java StudentManager.java StudentManagement.java
java StudentManagement
```

The application creates `students.txt` in the current working directory when it first saves records.

## Project files

- `Student.java` — student data model with encapsulation
- `StudentManager.java` — CRUD operations and file handling
- `StudentManagement.java` — console menu and input validation
- `students.txt` — created automatically to persist records

## Notes

The project stores each student's name using Base64 encoding inside the text file so commas in names do not break the record format. Base64 is only an encoding method, not encryption.
