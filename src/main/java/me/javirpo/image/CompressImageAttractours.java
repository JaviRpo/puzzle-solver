package me.javirpo.image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tinify.Source;
import com.tinify.Tinify;

public class CompressImageAttractours {
    public static void main(String[] args) throws IOException {
        Tinify.setKey("fMj2htvJjWc0SbcHx4qdT9ZKXLwBW593");

        File fromDir = new File("C:\\P\\AP\\Attractours\\Attractours-Site\\www\\wp-content\\uploads\\2020\\");

        List<String> currentCompressed = loadCurrentNames(fromDir);
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        File todayFile = new File(fromDir, "log-"+today+".txt");
        Files.write(todayFile.toPath(), currentCompressed);
        
        ArrayList<File> dirs = new ArrayList<>();
        dirs.add(fromDir);

        for (int i = 0; i < dirs.size(); i++) {
            fromDir = dirs.get(i);
            System.out.println("Folder: " + fromDir.getAbsolutePath());

            int j = 0;
            for (File fromFile : fromDir.listFiles()) {
                if (fromFile.isDirectory()) {
                    dirs.add(fromFile);
                    continue;
                } else if (fromFile.getName().endsWith(".txt") || fromFile.getName().endsWith(".mp4")) {
                    continue;
                }

                j++;
                System.out.print(j + ": ");
                if (currentCompressed.contains(fromFile.getName())) {
                    System.out.println("Omitted - " + fromFile.getAbsolutePath());
                } else {
                    tiny(fromFile);
                    currentCompressed.add(fromFile.getName());
                    Files.write(todayFile.toPath(), Arrays.asList(fromFile.getName()), StandardOpenOption.APPEND);
                }
            }
        }
    }

    private static List<String> loadCurrentNames(File fromDir) throws IOException {
        File lastFile = null;
        for (File fromFile : fromDir.listFiles()) {
            if (fromFile.getName().endsWith(".txt")) {
                if (lastFile == null) {
                    lastFile = fromFile;
                } else if (lastFile.getName().compareTo(fromFile.getName()) < 0) {
                    lastFile = fromFile;
                }
            }
        }
        System.out.println("File to process: " + lastFile.getAbsolutePath());

        if (lastFile == null) {
            System.exit(1);
        }

        List<String> currentCompressed = Files.readAllLines(lastFile.toPath());

        return currentCompressed;
    }

    private static void tiny(File file) throws IOException {
        System.out.println("Starting - " + file.getAbsolutePath());
        Source source = Tinify.fromFile(file.getAbsolutePath());
        source.toFile(file.getAbsolutePath());
        System.out.println("Finished - " + file.getAbsolutePath());
    }
}
