package me.javirpo.challenge;

import java.util.Arrays;

public class VendingMachine {
    public static void main(String[] args) {
        double M = 5;
        double P = 0.01;

        int[] arr = getChange(M, P);
        System.out.println("M: " + M + " - P: " + P + " - " + Arrays.toString(arr));

        M = 5;
        P = 0.99;
        arr = getChange(M, P);
        System.out.println("M: " + M + " - P: " + P + " - " + Arrays.toString(arr));

        M = 3.14;
        P = 1.99;
        arr = getChange(M, P);
        System.out.println("M: " + M + " - P: " + P + " - " + Arrays.toString(arr));

        M = 3;
        P = 0.01;
        arr = getChange(M, P);
        System.out.println("M: " + M + " - P: " + P + " - " + Arrays.toString(arr));

        M = 4;
        P = 3.14;
        arr = getChange(M, P);
        System.out.println("M: " + M + " - P: " + P + " - " + Arrays.toString(arr));

        M = 0.45;
        P = 0.34;
        arr = getChange(M, P);
        System.out.println("M: " + M + " - P: " + P + " - " + Arrays.toString(arr));
    }

    private static int[] getChange(double M, double P) {
        int iM = (int)(M * 100.0); //500
        int iP = (int)(P * 100.0); //1
        int returnMoney = iM - iP; // 500 - 1 = 499 --> 4 dollars

        int dollars = returnMoney / 100; //4
        returnMoney %= 100; //99

        int c50 = returnMoney / 50; //1
        returnMoney %= 50; //49

        int c25 = returnMoney / 25; //1
        returnMoney %= 25; //24

        int c10 = returnMoney / 10; //1
        returnMoney %= 10; //4

        int c5 = returnMoney / 5; //0
        returnMoney %= 5; //4

        return new int[] {returnMoney, c5, c10, c25, c50, dollars};
    }
}
