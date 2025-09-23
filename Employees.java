import java.io.*;

public class Employees {
    public static void main(String[] args) {
        // Array of 5 employees: ID and Name
        String[][] employees = {
                {"1", "Ly"},
                {"2", "Ana"},
                {"3", "Maria"},
                {"4", "David"},
                {"5", "Sergio"}
        };

        String filePath = "Employees.txt";

        // Writing to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] emp : employees) {
                writer.write(emp[0] + " " + emp[1]);
                writer.newLine(); // Adds a newline after each employee
            }
            System.out.println("Employees file created successfully.");
        } catch (IOException e) {
            System.out.println("Error writing the file: " + e.getMessage());
        }

        // Reading from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("\nContents of Employees.txt:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}
