import java.util.Scanner;

public class StudentManagement {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        boolean running = true;

        while (running) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. View Students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            Integer choice = readInteger();
            if (choice == null) {
                System.out.println("Please enter a number from 1 to 5.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter student ID (positive number): ");
                    Integer addId = readInteger();
                    if (addId == null) {
                        System.out.println("Invalid ID. Please enter a whole number.");
                        break;
                    }

                    System.out.print("Enter student name: ");
                    String addName = scanner.nextLine().trim();

                    System.out.print("Enter student age (positive number): ");
                    Integer addAge = readInteger();
                    if (addAge == null) {
                        System.out.println("Invalid age. Please enter a whole number.");
                        break;
                    }

                    manager.addStudent(addId, addName, addAge);
                    break;

                case 2:
                    System.out.print("Enter the ID of the student to update: ");
                    Integer updateId = readInteger();
                    if (updateId == null) {
                        System.out.println("Invalid ID. Please enter a whole number.");
                        break;
                    }

                    System.out.print("Enter the new name: ");
                    String newName = scanner.nextLine().trim();

                    System.out.print("Enter the new age (positive number): ");
                    Integer newAge = readInteger();
                    if (newAge == null) {
                        System.out.println("Invalid age. Please enter a whole number.");
                        break;
                    }

                    manager.updateStudent(updateId, newName, newAge);
                    break;

                case 3:
                    System.out.print("Enter the ID of the student to delete: ");
                    Integer deleteId = readInteger();
                    if (deleteId == null) {
                        System.out.println("Invalid ID. Please enter a whole number.");
                        break;
                    }
                    manager.deleteStudent(deleteId);
                    break;

                case 4:
                    manager.viewStudents();
                    break;

                case 5:
                    running = false;
                    System.out.println("Goodbye! Your student records are saved in students.txt.");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        }

        scanner.close();
    }

    private static Integer readInteger() {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
