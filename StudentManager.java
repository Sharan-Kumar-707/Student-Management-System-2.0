import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StudentManager {
    private final ArrayList<Student> students = new ArrayList<>();
    private final Path filePath = Paths.get("students.txt");

    public StudentManager() {
        loadStudents();
    }

    public boolean addStudent(int id, String name, int age) {
        if (id <= 0 || age <= 0 || name == null || name.trim().isEmpty()) {
            System.out.println("ID and age must be positive, and name cannot be empty.");
            return false;
        }

        if (findStudent(id) != null) {
            System.out.println("That student ID already exists.");
            return false;
        }

        students.add(new Student(id, name.trim(), age));
        saveStudents();
        System.out.println("Student added successfully.");
        return true;
    }

    public void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("\n--- Student Records ---");
        for (Student student : students) {
            System.out.println("ID: " + student.getId());
            System.out.println("Name: " + student.getName());
            System.out.println("Age: " + student.getAge());
            System.out.println("-----------------------");
        }
    }

    public boolean updateStudent(int id, String name, int age) {
        if (id <= 0 || age <= 0 || name == null || name.trim().isEmpty()) {
            System.out.println("ID and age must be positive, and name cannot be empty.");
            return false;
        }

        Student student = findStudent(id);
        if (student == null) {
            System.out.println("Student not found.");
            return false;
        }

        student.setName(name.trim());
        student.setAge(age);
        saveStudents();
        System.out.println("Student updated successfully.");
        return true;
    }

    public boolean deleteStudent(int id) {
        Iterator<Student> iterator = students.iterator();

        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getId() == id) {
                iterator.remove();
                saveStudents();
                System.out.println("Student deleted successfully.");
                return true;
            }
        }

        System.out.println("Student not found.");
        return false;
    }

    private Student findStudent(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    private void saveStudents() {
        try (BufferedWriter writer = Files.newBufferedWriter(
                filePath,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {

            for (Student student : students) {
                // Base64 encoding keeps commas and other punctuation in names safe.
                String encodedName = java.util.Base64.getEncoder().encodeToString(
                        student.getName().getBytes(StandardCharsets.UTF_8));
                writer.write(student.getId() + "," + encodedName + "," + student.getAge());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save student records: " + e.getMessage());
        }
    }

    private void loadStudents() {
        if (!Files.exists(filePath)) {
            return;
        }

        List<String> invalidLines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                try {
                    if (parts.length != 3) {
                        throw new IllegalArgumentException("Invalid record format");
                    }

                    int id = Integer.parseInt(parts[0]);
                    String name = new String(
                            java.util.Base64.getDecoder().decode(parts[1]),
                            StandardCharsets.UTF_8);
                    int age = Integer.parseInt(parts[2]);

                    if (id <= 0 || age <= 0 || name.trim().isEmpty() || findStudent(id) != null) {
                        throw new IllegalArgumentException("Invalid or duplicate student data");
                    }

                    students.add(new Student(id, name, age));
                } catch (IllegalArgumentException e) {
                    invalidLines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load student records: " + e.getMessage());
        }

        if (!invalidLines.isEmpty()) {
            System.out.println("Warning: " + invalidLines.size()
                    + " invalid record(s) were skipped while loading students.txt.");
        }
    }
}
