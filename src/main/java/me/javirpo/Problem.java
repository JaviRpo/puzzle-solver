package me.javirpo;

public class Problem {
    public static void main(String[] args) {
        String s = "adasd";

        char[] arr = s.toCharArray();

        boolean palindrome = version1(arr);
        System.out.println("Is palindrome: " + palindrome);

        palindrome = version2(s);
        System.out.println("Is palindrome: " + palindrome);
    }

    private static boolean version2(String s) {
        StringBuilder sb = new StringBuilder(s);
        sb.reverse();
        String s2 = sb.toString();
        
        boolean palindrome = s.equals(s2);
        return palindrome;
    }

    private static boolean version1(char[] arr) {
        boolean palindrome = true;
        for (int i = 0, j = arr.length - 1; i < arr.length / 2; i++, j--) {
            if (arr[i] != arr[j]) {
                palindrome = false;
                break;
            }
        }
        return palindrome;
    }
}
