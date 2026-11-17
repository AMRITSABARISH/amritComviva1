import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * A simple console application for managing employee records.
 * Compile with: javac EmployeeManagement.java
 * Run with:     java EmployeeManagement
 */
public class EmployeeManagement {
    private static final Map<Integer, Employee> employees = new HashMap<>();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.println("\n=== Employee Management ===");
                System.out.println("1. Add employee");
                System.out.println("2. Display all employees");
                System.out.println("3. Search employee by ID");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");

                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1":
                        addEmployee(scanner);
                        break;
                    case "2":
                        displayEmployees();
                        break;
                    case "3":
                        searchEmployee(scanner);
                        break;
                    case "4":
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Enter 1, 2, 3, or 4.");
                }
            }
        }
    }

    private static void addEmployee(Scanner scanner) {
        int id = readInt(scanner, "Employee ID: ");
        if (employees.containsKey(id)) {
            System.out.println("An employee with that ID already exists.");
            return;
        }

        String name = readRequiredText(scanner, "Name: ");
        String department = readRequiredText(scanner, "Department: ");
        double salary = readNonNegativeDouble(scanner, "Salary: ");

        employees.put(id, new Employee(id, name, department, salary));
        System.out.println("Employee added successfully.");
    }

    private static void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees have been added yet.");
            return;
        }

        List<Employee> sortedEmployees = new ArrayList<>(employees.values());
        sortedEmployees.sort((first, second) -> Integer.compare(first.getId(), second.getId()));
        System.out.printf("%-12s %-24s %-20s %12s%n", "ID", "Name", "Department", "Salary");
        System.out.println("--------------------------------------------------------------------------");
        for (Employee employee : sortedEmployees) {
            System.out.printf("%-12d %-24s %-20s %12.2f%n",
                    employee.getId(), employee.getName(), employee.getDepartment(), employee.getSalary());
        }
    }

    private static void searchEmployee(Scanner scanner) {
        int id = readInt(scanner, "Enter employee ID to search: ");
        Employee employee = employees.get(id);
        if (employee == null) {
            System.out.println("No employee found with ID " + id + ".");
        } else {
            System.out.println("Employee found:");
            System.out.println(employee);
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readNonNegativeDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (Double.isFinite(value) && value >= 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // Show the same helpful message for malformed input below.
            }
            System.out.println("Please enter a valid, non-negative salary.");
        }
    }

    private static String readRequiredText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    private static final class Employee {
        private final int id;
        private final String name;
        private final String department;
        private final double salary;

        private Employee(int id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        private int getId() {
            return id;
        }

        private String getName() {
            return name;
        }

        private String getDepartment() {
            return department;
        }

        private double getSalary() {
            return salary;
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Department: " + department
                    + ", Salary: " + String.format("%.2f", salary);
        }
    }
}
