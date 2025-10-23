import java.io.*;

public class RandomAccessEmployee {

    private static final int RECORD_SIZE = 16;

    public static void main(String[] args) throws IOException {
        File fileObject = new File("EmployeeData.dat");
        RandomAccessFile file = new RandomAccessFile(fileObject, "rw");

        int[] departments = {10, 20, 10, 10, 30, 30, 20};
        double[] salaries = {1000.45, 2400.60, 3000.0, 1500.56, 2200.0, 1435.87, 2000.0};

        int n = salaries.length;

        for (int i = 0; i < n; i++) {
            file.writeInt(i + 1);
            file.writeInt(departments[i]);
            file.writeDouble(salaries[i]);
        }
        file.close();

        file = new RandomAccessFile(fileObject, "rw");

        file.seek(2 * RECORD_SIZE);
        System.out.println("Employee No: " + file.readInt() + " | Dept: " + file.readInt() + " | Salary: " + file.readDouble());

        file.seek(0);
        while (file.getFilePointer() < file.length()) {
            int id = file.readInt();
            int dept = file.readInt();
            double salary = file.readDouble();
            System.out.println("ID: " + id + " | Dept: " + dept + " | Salary: " + salary);
        }
    }

    // 1. Query
    public static void query(RandomAccessFile file, int id) throws IOException {
        file.seek(0);
        while (file.getFilePointer() < file.length()) {
            int currentId = file.readInt();
            int dept = file.readInt();
            double salary = file.readDouble();

            if (currentId == id) {
                System.out.println("Employee: " + currentId + " | Dept: " + dept + " | Salary: " + salary);
                return;
            }
        }
        System.out.println("Employee " + id + " not found.");
    }

    // 2. Insert
    public static void insert(RandomAccessFile file, int id, int dept, double salary) throws IOException {
        file.seek(0);
        while (file.getFilePointer() < file.length()) {
            int currentId = file.readInt();
            file.skipBytes(12);
            if (currentId == id) {
                System.out.println("Employee " + id + " already exists.");
                return;
            }
        }

        file.seek(file.length());
        file.writeInt(id);
        file.writeInt(dept);
        file.writeDouble(salary);
        System.out.println("Employee " + id + " inserted successfully.");
    }

    // 3. Modify salary
    public static void modify(RandomAccessFile file, int id, double change) throws IOException {
        file.seek(0);
        while (file.getFilePointer() < file.length()) {
            long position = file.getFilePointer();
            int currentId = file.readInt();
            int dept = file.readInt();
            double salary = file.readDouble();

            if (currentId == id) {
                double newSalary = salary + change;
                file.seek(position + 8);
                file.writeDouble(newSalary);
                System.out.println("Employee " + id +
                        " | Old Salary: " + salary +
                        " | New Salary: " + newSalary);
                return;
            }
        }
        System.out.println("Employee " + id + " not found.");
    }

    // 4. Logical delete
    public static void delete(RandomAccessFile file, int id) throws IOException {
        file.seek(0);
        while (file.getFilePointer() < file.length()) {
            long position = file.getFilePointer();
            int currentId = file.readInt();
            file.skipBytes(12);

            if (currentId == id) {
                file.seek(position);
                file.writeInt(-1);
                file.writeInt(0);
                file.writeDouble(0.0);
                System.out.println("Employee " + id + " deleted successfully.");
                return;
            }
        }
        System.out.println("Employee " + id + " not found.");
    }
}
