import java.io.*;

public class CreateDir {
    public static void main(String[] args) {
        File dir = new File("NEWDIR");
        File file1 = new File(dir, "File1.txt");
        File file2 = new File(dir, "File2.txt");

        dir.mkdir();
        try {
            if (file1.createNewFile())
                System.out.printf("FILE1 created successfully%n");
            else
                System.out.printf("Could not create FILE1%n");

            if (file2.createNewFile())
                System.out.printf("FILE2 created successfully%n");
            else
                System.out.printf("Could not create FILE2%n");
        } catch (IOException ioe) {
            System.out.println("An error occurred: " + ioe.getMessage());
        }
    }
}

