import java.io.File;
import java.io.IOException;

public class FileInfo {
    public static void main(String[] args) throws IOException {
        System.out.printf("INFORMATION ABOUT THE FILE: ");
        String path = "C:\\Users\\Ly\\Documents\\VerInf.java";
        File f = new File(path);

        if (f.exists()) {
            System.out.println("File name:            " + f.getName());
            System.out.println("Path:                 " + f.getPath());
            System.out.println("Absolute path:        " + f.getAbsolutePath());
            System.out.println("Can read:             " + f.canRead());
            System.out.println("Can write:            " + f.canWrite());
            System.out.println("Length:               " + f.length());
            System.out.println("Is a directory:       " + f.isDirectory());
            System.out.println("Is a file:            " + f.isFile());
            System.out.println("Parent directory:     " + f.getParent());
        } else {
            System.out.println("File not found");
        }
    }
}
