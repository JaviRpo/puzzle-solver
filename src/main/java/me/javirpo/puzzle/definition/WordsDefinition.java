package me.javirpo.puzzle.definition;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.*;

public class WordsDefinition {

    private Scanner sc;

    private Map<String, List<String>> map;
    private final Path path = Paths.get("words.txt");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type mapType = new TypeToken<SortedMap<String, List<String>>>() {}.getType();

    public static void main(String[] args) throws IOException {
        new WordsDefinition()._run();
    }

    private void _run() throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            sc = scanner;

            run();
        }
    }

    private void run() throws IOException {
        load();

        while (true) {
            System.out.println();
            System.out.println("0. Exit");
            System.out.println("- Word");
            System.out.print("Write: ");
            String word = sc.nextLine().trim().toLowerCase();

            if ("0".equals(word)) {
                break;
            } else if(word.length() == 0) {
                continue;
            }

            System.out.println();
            System.out.println("- Definitions ; separated");

            String[] definitions = sc.nextLine().split(";");

            map.compute(word, (k, v) -> {
                if(v== null) {
                    v = new ArrayList<>();
                }
                Collections.addAll(v, definitions);
                return v;
            });

            save();
        }
        System.out.println("Finish ...");
        System.out.println();
    }

    private void load() throws IOException {
        String jsonString = Files.readString(path);
        map = gson.fromJson(jsonString, mapType);
    }

    private void save() throws IOException {
        String jsonString = gson.toJson(map);
        Files.writeString(path, jsonString);
    }
}
