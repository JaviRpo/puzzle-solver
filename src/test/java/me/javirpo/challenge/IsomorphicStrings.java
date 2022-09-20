package me.javirpo.challenge;

import java.util.Map;
import java.util.TreeMap;

public class IsomorphicStrings {

    public static void main(String[] args) {
        String s, t;
        boolean res;

        // prepare the input data
        s = "egg";
        t = "add";
        res = solveIt(s,t);
        System.out.println("output: " + res + ", s = " + s + ", t = " + t);

        s = "foo";
        t = "bar";
        res = solveIt(s,t);
        System.out.println("output: " + res + ", s = " + s + ", t = " + t);

        s = "paper";
        t = "title";
        res = solveIt(s,t);
        System.out.println("output: " + res + ", s = " + s + ", t = " + t);

        s = "isomorphic";
        t = "occurrence";
        res = solveIt(s,t);
        System.out.println("output: " + res + ", s = " + s + ", t = " + t);
    }
    private static boolean solveIt(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        Map<Character, Character> map = new TreeMap<>();

        for(int i=0; i<s.length();i++) {
            char charS = s.charAt(i);
            char charT = t.charAt(i);

            Character mapS = map.get(charS);
            if (mapS == null) {
                map.put(charS, charT);
            } else if (mapS != charT) {
                return false;
            }
        }
        return true;
    }
}
