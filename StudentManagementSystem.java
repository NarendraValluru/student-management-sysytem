import java.io.*;
import java.util.*;

class Student implements Serializable {
    int rollNo;
    String name;
    double marks;

    Student(int rollNo, String name, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Roll No: " + rollNo + ", Name: " + name + ", Marks: " + marks;
    }
}

public class StudentManagementSystem {
    static ArrayList<Student> studentList = new ArrayList<>();
    static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        loadFromFile();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by Roll No");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Save & Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addStudent(sc);
                case 2 -> viewStudents();
                case 3 -> searchStudent(sc);
                case 4 -> updateStudent(sc);
                case 5 -> deleteStudent(sc);
                case 6 -> {
                    saveToFile();
                    System.out.println("Data saved. Exiting...");
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 6);
        sc.close();
    }

    static void addStudent(Scanner sc) {
        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();
        studentList.add(new Student(roll, name, marks));
        System.out.println("Student added successfully!");
    }

    static void viewStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("\n-- Student List --");
        for (Student s : studentList) {
            System.out.println(s);
        }
    }
    
    static void searchStudent(Scanner sc) {
        System.out.print("Enter Roll No to search: ");
        int roll = sc.nextInt();
        for (Student s : studentList) {
            if (s.rollNo == roll) {
                System.out.println("Student Found: " + s);
                return;
            }
        }
        System.out.println("Student not found.");
    }

    static void updateStudent(Scanner sc) {
        System.out.print("Enter Roll No to update: ");
        int roll = sc.nextInt();
        sc.nextLine();
        for (Student s : studentList) {
            if (s.rollNo == roll) {
                System.out.print("Enter new Name: ");
                s.name = sc.nextLine();
                System.out.print("Enter new Marks: ");
                s.marks = sc.nextDouble();
                System.out.println("Student updated.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    static void deleteStudent(Scanner sc) {
        System.out.print("Enter Roll No to delete: ");
        int roll = sc.nextInt();
        Iterator<Student> it = studentList.iterator();
        while (it.hasNext()) {
            if (it.next().rollNo == roll) {
                it.remove();
                System.out.println("Student deleted.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

   static void saveToFile() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
        for (Student s : studentList) {
            bw.write(s.rollNo + "," + s.name + "," + s.marks);
            bw.newLine();
        }
        System.out.println("Data saved to file.");
    } catch (IOException e) {
        System.out.println("Error saving data: " + e.getMessage());
    }
}


    static void loadFromFile() {
    File file = new File(FILE_NAME);
    if (!file.exists()) return;

    try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 3) {
                int roll = Integer.parseInt(parts[0]);
                String name = parts[1];
                double marks = Double.parseDouble(parts[2]);
                studentList.add(new Student(roll, name, marks));
            }
        }
        System.out.println("Data loaded from file.");
    } catch (IOException | NumberFormatException e) {
        System.out.println("Error loading data: " + e.getMessage());
    }
}

}
