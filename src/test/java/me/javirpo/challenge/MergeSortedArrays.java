package me.javirpo.challenge;

import java.util.Arrays;

/**
 * You are given two sorted arrays of numbers, where the first array is twice as long as the second array.
 * Also, the second half of the first array is empty.
 * Write a function, which merges these two arrays and preserves the sorted order of the elements.
 */
public class MergeSortedArrays {
    public static void main(String[] args) {
        int[] arr1 = {2, 3, 6, 7, 0, 0, 0, 0};
        int[] arr2 = {1, 6, 9, 10};
        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
        merge(arr1, arr2);
        System.out.println(Arrays.toString(arr1));
    }
    public static void merge(int[] arr1, int[] arr2) {
        int end = arr1.length - 1;
        int len = arr2.length;

        int i=len-1, j=len-1;
        while (i>=0 && j>=0) {
            if (arr1[i] > arr2[j]) {
                arr1[end] = arr1[i];
                i--;
            } else {
                arr1[end] = arr2[j];
                j--;
            }
            end--;
        }
        while(i>=0) {
            arr1[end] = arr1[i];
            i--;
            end--;
        }
        while(j>=0) {
            arr1[end] = arr2[j];
            j--;
            end--;
        }
    }

}
