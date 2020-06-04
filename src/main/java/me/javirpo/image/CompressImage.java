package me.javirpo.image;

import java.io.File;
import java.io.IOException;

import com.tinify.Source;
import com.tinify.Tinify;

public class CompressImage {
    public static void main(String[] args) throws IOException {
        Tinify.setKey("fMj2htvJjWc0SbcHx4qdT9ZKXLwBW593");

        File fromDir = new File("C:\\Users\\javier.restrepo\\Downloads\\AKA\\Tiny");
        File toDir = new File("C:\\Users\\javier.restrepo\\Downloads\\AKA\\Tiny\\Tiny-2");

        int i = 0;
        for (File fromFile : fromDir.listFiles()) {
            if (fromFile.isDirectory()) {
                System.out.println("Dir-Omitted - " + fromFile.getAbsolutePath());
                continue;
            }
            i++;
            System.out.print(i + ": ");
            File toFile = new File(toDir, fromFile.getName());
            if (!toFile.exists()) {
                tiny(fromFile, toFile);
            } else {
                System.out.println("Omitted - " + fromFile.getAbsolutePath());
            }
        }
    }

    private static void tiny(File file, File toFile) throws IOException {
        System.out.println("Starting - " + file.getAbsolutePath());
        Source source = Tinify.fromFile(file.getAbsolutePath());
        source.toFile(toFile.getAbsolutePath());
        System.out.println("Finished - " + toFile.getAbsolutePath());
    }
}
