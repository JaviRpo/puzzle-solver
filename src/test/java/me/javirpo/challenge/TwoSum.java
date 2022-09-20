package me.javirpo.challenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TwoSum {
    public static void main(String[] args) {
        int[] sol = solveIt(new long[]{2,7,11,15}, 9);
        System.out.println(Arrays.toString(sol));

        sol = solveIt(new long[]{3,2,4}, 6);
        System.out.println(Arrays.toString(sol));

        sol = solveIt(new long[]{3,3}, 6);
        System.out.println(Arrays.toString(sol));
    }

    /*
    target 9
    num 2, diff 7
    num 7, diff 2

    map<7, 0>
     */
    private static int[] solveIt(long[] nums, long target) {
        Map<Long, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            long num = nums[i];
            Integer pos = map.get(num);
            if (pos != null) {
                return new int[] {pos, i};
            }

            long diff = target - nums[i];
            map.put(diff, i);
        }

        return null;
    }

    private static int[] solveIt_BruteForce(long[] nums, long target) {
        for (int i = 0; i < nums.length -1; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] {i, j};
                }
            }
        }

        return null;
    }
}
