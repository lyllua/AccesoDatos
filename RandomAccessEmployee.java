import java.io.*;

public class RandomAccessEmployee {

    private static final int RECORD_SIZE = 16; // Each record: 4 bytes ID + 4 bytes department + 8 bytes salary

    public static void main(String[] args) throws IOException {
        File filePath = new File("RandomEmployees.dat");
        // Create a random access file, read-write mode
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");

        // Arrays with initial employee data
        int departments[] = {10, 20, 10, 10, 30, 30, 20};
        Double salaries[] = {1000.45, 2400.60, 3000.0, 1500.56, 2200.0, 1435.87, 2000.0};

        int n = salaries.length; // Number of employees

        // Write initial data to the file
        for (int i = 0; i < n; i++) {
            file.writeInt(i + 1);       // Employee ID (starting from 1)
            file.writeInt(departments[i]); // Department
            file.writeDouble(salaries[i]); // Salary
        }

        // --- EXAMPLE: read the third employee ---
        file.seek(2 * RECORD_SIZE); // Index 2 -> third employee
        System.out.println("Employee ID " + file.readInt() +
                " Dept: " + file.readInt() +
                " Salary: " + file.readDouble());

        file.close();
    }

    // --- QUERY EMPLOYEE ---
    public static void query(RandomAccessFile file, int id) throws IOException {
        boolean found = false;
        file.seek(0); // start from the beginning
        while (file.getFilePointer() < file.length()) {
            int currentId = file.readInt();
            int dept = file.readInt();
            double salary = file.readDouble();
            if (currentId == id) {
                System.out.println("Employee ID " + currentId + " Dept: " + dept + " Salary: " + salary);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Employee " + id + " does not exist.");
        }
    }

    // --- INSERT EMPLOYEE ---
    public static void insert(RandomAccessFile file, int id, int dept, double salary) throws IOException {
        boolean exists = false;
        file.seek(0);
        while (file.getFilePointer() < file.length()) {
            int currentId = file.readInt();
            file.readInt();   // skip department
            file.readDouble(); // skip salary
            if (currentId == id) {
                exists = true;
                break;
            }
        }
        if (exists) {
            System.out.println("Employee " + id + " already exists.");
        } else {
            file.seek(file.length()); // move to the end of the file
            file.writeInt(id);
            file.writeInt(dept);
            file.writeDouble(salary);
            System.out.println("Employee " + id + " inserted successfully.");
        }
    }

    // --- MODIFY EMPLOYEE SALARY ---
    public static void modify(RandomAccessFile file, int id, double amount) throws IOException {
        boolean found = false;
        file.seek(0);
        while (file.getFilePointer() < file.length()) {
            long position = file.getFilePointer();
            int currentId = file.readInt();
            int dept = file.readInt();
            double salary = file.readDouble();
            if (currentId == id) {
                double newSalary = salary + amount;
                file.seek(position + 8); // move to the start of the salary field
                file.writeDouble(newSalary);
                System.out.println("Employee " + id + " -> Old Salary: " + salary + ", New Salary: " + newSalary);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Employee " + id + " does not exist.");
        }
    }

    // --- LOGICAL DELETE EMPLOYEE ---
    public static void delete(RandomAccessFile file, int id) throws IOException {
        boolean found = false;
        file.seek(0);
        while (file.getFilePointer() < file.length()) {
            long position = file.getFilePointer();
            int currentId = file.readInt();
            file.readInt();   // skip department
            file.readDouble(); // skip salary
            if (currentId == id) {
                file.seek(position);       // go back to the start of the record
                file.writeInt(-1);         // mark ID as deleted
                file.writeInt(0);          // set department to 0
                file.writeDouble(0.0);     // set salary to 0
                System.out.println("Employee " + id + " deleted successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Employee " + id + " does not exist.");
        }
    }
}

