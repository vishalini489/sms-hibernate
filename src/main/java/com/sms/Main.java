package com.sms;

import com.sms.dao.StudentDao;
import com.sms.entity.Student;
import com.sms.util.HibernateUtil;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        StudentDao dao = new StudentDao();
        Scanner sc    = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   Student Management System          ║");
        System.out.println("║   Powered by Hibernate + MySQL       ║");
        System.out.println("╚══════════════════════════════════════╝");

        while (true) {
            printMenu();
            System.out.print("Enter your choice: ");
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 8.");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1: {
                    System.out.println("\n📋  All Students:");
                    List<Student> students = dao.getStudents();

                    if (students.isEmpty()) {
                        System.out.println("  No students found in the database.");
                    } else {
                        for (Student s : students) {
                            s.displayStudent();
                        }
                        System.out.println("  Total: " + students.size() + " student(s).");
                    }
                    break;
                }

                case 2: {
                    System.out.print("\nEnter Roll Number to search: ");
                    int roll = readInt(sc);

                    Student found = dao.findByRollNumber(roll);
                    if (found != null) {
                        System.out.println("Student found:");
                        found.displayStudent();
                    } else {
                        System.out.println("No student found with Roll Number: " + roll);
                    }
                    break;
                }

                case 3: {
                    System.out.println("\n  Add New Student:");
                    System.out.print("  Enter Roll Number : ");
                    int id = readInt(sc);

                    if (dao.findByRollNumber(id) != null) {
                        System.out.println("⚠️  A student with Roll Number " + id + " already exists.");
                        break;
                    }

                    System.out.print("  Enter Name        : ");
                    String name = sc.nextLine().trim();

                    System.out.print("  Enter Age         : ");
                    int age = readInt(sc);

                    if (age < 15 || age > 60) {
                        System.out.println("⚠️  Age must be between 15 and 60.");
                        break;
                    }
                    System.out.print("  Enter Course Name : ");
                    String course = sc.nextLine().trim();

                    Student newStudent = new Student(id, name, age, course);
                    boolean added = dao.addStudent(newStudent);

                    if (added) {
                        System.out.println("Student added successfully!");
                    } else {
                        System.out.println("Failed to add student.");
                    }
                    break;
                }

                case 4: {
                    System.out.println("\n Update Student:");

                    System.out.print("  Enter Roll Number of student to update: ");
                    int id = readInt(sc);

                    Student existing = dao.findByRollNumber(id);
                    if (existing == null) {
                        System.out.println("No student found with Roll Number: " + id);
                        break;
                    }

                    System.out.println("  Current details:");
                    existing.displayStudent();

                    System.out.print("  New Name (press Enter to keep '" + existing.getName() + "'): ");
                    String name = sc.nextLine().trim();
                    if (!name.isEmpty()) {
                        existing.setName(name);
                    }

                    System.out.print("  New Age  (press Enter to keep " + existing.getAge() + "): ");
                    String ageStr = sc.nextLine().trim();
                    if (!ageStr.isEmpty()) {
                        try {
                            int newAge = Integer.parseInt(ageStr);
                            if (newAge < 15 || newAge > 60) {
                                System.out.println("⚠️  Age must be 15-60. Keeping original.");
                            } else {
                                existing.setAge(newAge);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("⚠️  Invalid age entered. Keeping original.");
                        }
                    }

                    System.out.print("  New Course (press Enter to keep '" + existing.getCourseName() + "'): ");
                    String course = sc.nextLine().trim();
                    if (!course.isEmpty()) {
                        existing.setCourseName(course);
                    }

                    boolean updated = dao.updateStudent(existing);
                    if (updated) {
                        System.out.println("Student updated successfully!");
                    } else {
                        System.out.println("Failed to update student.");
                    }
                    break;
                }

                case 5: {
                    System.out.println("\n Delete Student:");

                    System.out.print("Enter Roll Number to delete: ");
                    int roll = readInt(sc);

                    Student toDelete = dao.findByRollNumber(roll);
                    if (toDelete == null) {
                        System.out.println("No student found with Roll Number: " + roll);
                        break;
                    }

                    System.out.println("  You are about to delete:");
                    toDelete.displayStudent();
                    System.out.print("  Confirm delete? (yes/no): ");
                    String confirm = sc.nextLine().trim();

                    if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
                        boolean deleted = dao.deleteStudent(roll);
                        if (deleted) {
                            System.out.println("Student deleted successfully!");
                        } else {
                            System.out.println("Failed to delete student.");
                        }
                    } else {
                        System.out.println("  Delete cancelled.");
                    }
                    break;
                }

                case 6: {
                    System.out.println("\n🔍  Search Students by Name:");
                    System.out.print("  Enter name keyword: ");
                    String keyword = sc.nextLine().trim();

                    List<Student> results = dao.searchByName(keyword);

                    if (results.isEmpty()) {
                        System.out.println("  No students found matching '" + keyword + "'.");
                    } else {
                        System.out.println("  Found " + results.size() + " result(s):");
                        for (Student s : results) {
                            s.displayStudent();
                        }
                    }
                    break;
                }

                case 7: {
                    System.out.println("\n📊  Sort Students:");
                    System.out.println("  1. Sort by Name");
                    System.out.println("  2. Sort by Age");
                    System.out.print("  Choose sort option: ");

                    int sortChoice = readInt(sc);
                    String field   = sortChoice == 2 ? "age" : "name";

                    List<Student> sorted = dao.getStudentsSorted(field);

                    if (sorted.isEmpty()) {
                        System.out.println("  No students to display.");
                    } else {
                        System.out.println("  Students sorted by " + field + ":");
                        for (Student s : sorted) {
                            s.displayStudent();
                        }
                    }
                    break;
                }

                case 8: {
                    System.out.println("\n  Goodbye! Shutting down...");
                    HibernateUtil.shutdown();   // close the SessionFactory cleanly
                    sc.close();
                    System.exit(0);
                    break;
                }

                default: {
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                }

            }
        }
    }

    private static void printMenu() {
        System.out.println("\n════════════════════════════════════════");
        System.out.println("            MAIN MENU");
        System.out.println("════════════════════════════════════════");
        System.out.println("  1. View all students");
        System.out.println("  2. Find student by Roll Number");
        System.out.println("  3. Add new student");
        System.out.println("  4. Update student");
        System.out.println("  5. Delete student");
        System.out.println("  6. Search by Name");
        System.out.println("  7. View students sorted");
        System.out.println("  8. Exit");
        System.out.println("════════════════════════════════════════");
    }

    private static int readInt(Scanner sc) {
        while (true) {
            if (sc.hasNextInt()) {
                int val = sc.nextInt();
                sc.nextLine();
                return val;
            } else {
                System.out.print("Please enter a valid number: ");
                sc.nextLine();
            }
        }
    }
}
