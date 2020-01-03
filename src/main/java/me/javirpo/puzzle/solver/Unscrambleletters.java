package me.javirpo.puzzle.solver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Unscrambleletters {
    private static final String URL = "https://unscrambleletters.us/solver";
    private static Scanner SC;
    private static final Map<String, Map<Integer, Collection<String>>> fullMap = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {
        try (Scanner sc = new Scanner(System.in)) {
            SC = sc;
            while (true) {
                System.out.println();
                if (!fullMap.isEmpty()) {
                    System.out.println("-1. Load Letters (" + fullMap.size() + ")");
                    System.out.println("-2. Clear Letters (" + fullMap.size() + ")");
                }
                System.out.print("Write letters: ");
                String letters = sc.nextLine();

                Entry<String, Map<Integer, Collection<String>>> entry = getWordsOption(letters);
                letters = entry.getKey();
                Map<Integer, Collection<String>> map = entry.getValue();

                System.out.println("DONE");

                String opt = letters;
                label_while: while (!"-1".equals(opt)) {
                    System.out.println();
                    System.out.println("0. To list");
                    System.out.println("-1. To Exit");
                    System.out.println("-2. Save Letters");
                    System.out.println("Number and Regex to process");
                    System.out.print("Write: ");

                    opt = sc.nextLine();
                    switch (opt) {
                        case "0":
                            print(map);
                            break;
                        case "-1":
                            fullMap.remove(letters);
                            break label_while;
                        case "-2":
                            store(letters, map);
                            break label_while;
                        default:
                            try {
                                String[] opts = opt.split(" ");
                                if (opts.length == 1) {
                                    print(map, Integer.parseInt(opts[0]));
                                    // } else if (opts.length == 2) {
                                    // search(map, Integer.parseInt(opts[0]), opts[1]);
                                } else {
                                    search(map, opts, letters);
                                }
                            } catch (Exception e) {
                                System.out.println("NO VALID - " + e.getMessage());
                            }
                            break;
                    }
                }
            }
        }
    }

    private static final void store(String letters, Map<Integer, Collection<String>> map) {
        fullMap.put(letters, map);
    }

    private static void print(Map<Integer, Collection<String>> map) {
        map.entrySet().stream().forEach(entry -> {
            System.out.println("Words with " + entry.getKey() + " - " + entry.getValue());
        });
    }

    private static void print(Map<Integer, Collection<String>> map, int value) {
        System.out.println("Words with " + value + " - " + map.getOrDefault(value, Collections.emptyList()));
    }

    public static Collection<String> getAndFilter(Map<Integer, Collection<String>> map, int value, String regex) {
        regex = checkSubPatterns(regex);
        Pattern pattern = Pattern.compile(regex);
        return map.getOrDefault(value, Collections.emptyList())
            .stream()
            .filter(word -> pattern.matcher(word).matches())
            .collect(Collectors.toList());
    }

    private static String checkSubPatterns(String regex) {
        int indexStart = 0;
        do {
            indexStart = regex.indexOf("[", indexStart);
            if (indexStart == -1) {
                break;
            }

            int indexEnd = regex.indexOf("]", indexStart);
            String subLetters = regex.substring(indexStart + 1, indexEnd);
            try {
                int number = Integer.parseInt(subLetters);
                Entry<String, Map<Integer, Collection<String>>> entry = getWordsByNumber(number);
                regex = regex.replace(subLetters, entry.getKey());
            } catch (NumberFormatException nfe) {
                // Is letter
                fullMap.computeIfAbsent(subLetters, k -> {
                    Map<Integer, Collection<String>> words;
                    try {
                        words = getWords(subLetters);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return words;
                });
            }

            indexStart++;
        } while (true);

        return regex;
    }

    private static void search(Map<Integer, Collection<String>> map, String[] opts, String letters) {
        ArrayList<Collection<String>> wordsOfWords = new ArrayList<>(opts.length);
        for (int i = 0; i < opts.length; i += 2) {
            Collection<String> words = getAndFilter(map, Integer.parseInt(opts[i]), opts[i + 1]);
            wordsOfWords.add(words);
            if (words == null || words.isEmpty()) {
                System.out.println("-- Nothing for " + i / 2 + " --");
            } else {
                System.out.println("Letters " + i / 2 + " " + words);
            }
        }

        System.out.println("Result");
        boolean[] marks = new boolean[letters.length()];
        ArrayList<String> successWords = new ArrayList<>(wordsOfWords.size());
        process(wordsOfWords, successWords, 0, marks, letters);
        System.out.println();
    }

    private static void process(ArrayList<Collection<String>> wordsOfWords, ArrayList<String> successWords, int n, boolean[] marks,
        String letters) {
        if (n == wordsOfWords.size()) {
            System.out.println(successWords);
            return;
        }
        Collection<String> wordsN = wordsOfWords.get(n);
        for (String word : wordsN) {
            boolean[] marksN = Arrays.copyOf(marks, marks.length);
            if (mark(word, marksN, letters)) {
                successWords.add(word);
                process(wordsOfWords, successWords, n + 1, marksN, letters);
                successWords.remove(successWords.size() - 1);
            }
        }
    }

    private static boolean mark(String word, boolean[] marks, String letters) {
        for (char c : word.toCharArray()) {
            boolean marked = false;
            for (int i = 0; i < marks.length; i++) {
                if (!marks[i] && letters.charAt(i) == c) {
                    marks[i] = true;
                    marked = true;
                    break;
                }
            }
            if (!marked) {
                return false;
            }
        }
        return true;
    }

    private static Entry<String, Map<Integer, Collection<String>>> getWordsOption(String option) throws IOException {
        switch (option) {
            case "-1":
                int i = 0;
                for (String key : fullMap.keySet()) {
                    System.out.println(i + " " + key);
                    i++;
                }
                System.out.print("Write letters: ");
                String letters = SC.nextLine();

                Map<Integer, Collection<String>> map = fullMap.get(letters);
                if (map != null) {
                    return new MyEntry<>(letters, map);
                }
                try {
                    int number = Integer.parseInt(letters);
                    return getWordsByNumber(number);
                } catch (NumberFormatException nfe) {
                    System.out.println("NO VALID - " + nfe.getMessage());
                    return new MyEntry<>("-1", Collections.emptyMap());
                }
            case "-2":
                fullMap.clear();
                return new MyEntry<>("-1", Collections.emptyMap());
            default:
                return new MyEntry<>(option, getWords(option));
        }
    }

    private static Entry<String, Map<Integer, Collection<String>>> getWordsByNumber(int number) {
        int i;
        i = 0;
        for (Entry<String, Map<Integer, Collection<String>>> entry : fullMap.entrySet()) {
            if (i == number) {
                return new MyEntry<>(entry.getKey(), entry.getValue());
            }
            i++;
        }
        return new MyEntry<>("" + number, null);
    }

    public static Map<Integer, Collection<String>> getWordsWithCache(String letters) throws IOException {
        Map<Integer, Collection<String>> map = fullMap.get(letters);
        if (map != null && !map.isEmpty()) {
            return map;
        }
        map = getWords(letters);
        fullMap.put(letters, map);
        
        return map;
    }

    private static Map<Integer, Collection<String>> getWords(String letters) throws IOException {
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("hand", letters));
        HttpPost post = new HttpPost(URL);
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        try (CloseableHttpClient client = HttpClientBuilder.create().build();
            CloseableHttpResponse response = client.execute(post)) {
            System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");

            return processHtml(responseString);
        }
    }

    private static Map<Integer, Collection<String>> processHtml(String responseString) {
        Map<Integer, Collection<String>> map = new LinkedHashMap<>();
        String groupTag = "<h3 class='word_listing_1'>";
        // System.out.println(responseString);
        int indexGroup = 0;
        while (indexGroup != -1) {
            indexGroup = responseString.indexOf(groupTag, indexGroup + 1);
            if (indexGroup != -1) {
                int indexGroupEnd = responseString.indexOf("</h3>", indexGroup);
                String[] groupName = responseString.substring(indexGroup + groupTag.length(), indexGroupEnd).trim().split(" ");
                int groupNumber = Integer.parseInt(groupName[groupName.length - 1]);

                int nextIndexGroup = responseString.indexOf(groupTag, indexGroup + 1);

                Collection<String> words = getWords(responseString, indexGroup, nextIndexGroup);
                map.put(groupNumber, words);
            }
        }

        return map;
    }

    private static Collection<String> getWords(String responseString, int indexGroup, int until) {
        Set<String> list = new TreeSet<>();
        String wordTag = "<h3 class='word_listing_2'>";

        int indexWord = indexGroup;
        while (indexWord != -1) {
            indexWord = responseString.indexOf(wordTag, indexWord + 1);
            if (indexWord != -1 && (until == -1 || indexWord < until)) {
                int indexWordEnd = responseString.indexOf("</h3>", indexWord);
                String word = responseString.substring(indexWord + wordTag.length(), indexWordEnd).trim();

                list.add(word);
            }
        }

        return list;
    }

    @Getter
    @AllArgsConstructor
    public static class MyEntry<K, V> implements Entry<K, V> {
        private K key;
        private V value;

        @Override
        public V setValue(V value) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
