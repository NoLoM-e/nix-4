package com.company;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class App
{
    public static void main( String[] args ) {

        int[] arr = {1, 2, 3, 124, 12, 1, 2, 3, 1};
        System.out.println("unique(arr) = " + unique(arr));
        arr = new int[]{1, 4, 5, 1, 1, 3};
        System.out.println("unique(arr) = " + unique(arr));
        arr = new int[]{12, 135, 64, 5, 1, 3, 512, 3, 1, 5, 2, 5, 5, 5, 1};
        System.out.println("unique(arr) = " + unique(arr));

        int x = 1, y = 1, x1 = 1, y1 = 0;
        System.out.println("horseMove() = " + horseMove(x, y, x1, y1));
        x = 1;
        y = 1;
        x1 = 3;
        y1 = 2;
        System.out.println("horseMove() = " + horseMove(x, y, x1, y1));
        x = 1;
        y = 1;
        x1 = 2;
        y1 = 3;
        System.out.println("horseMove() = " + horseMove(x, y, x1, y1));
        x = 5;
        y = 5;
        x1 = 5;
        y1 = 5;
        System.out.println("horseMove() = " + horseMove(x, y, x1, y1));
        x = 2;
        y = 3;
        x1 = 4;
        y1 = 4;
        System.out.println("horseMove() = " + horseMove(x, y, x1, y1));
        x = -5;
        y = -3;
        x1 = -6;
        y1 = -1;
        System.out.println("horseMove() = " + horseMove(x, y, x1, y1));
        x = -5;
        y = -3;
        x1 = -1;
        y1 = -6;
        System.out.println("horseMove() = " + horseMove(x, y, x1, y1));
        int xA, xB, xC, yA, yB,yC;
        xA = 1;
        xB = 1;
        xC = 3;
        yA = 3;
        yB = 1;
        yC = 1;
        System.out.println("triangleSquare(xA, yA, xB, yB, xC, yC) = " + triangleSquare(xA, yA, xB, yB, xC, yC));
        xA = 5;
        xB = 10;
        xC = 22;
        yA = 15;
        yB = 3;
        yC = 7;
        System.out.println("triangleSquare(xA, yA, xB, yB, xC, yC) = " + triangleSquare(xA, yA, xB, yB, xC, yC));
        xA = 22;
        xB = 30;
        xC = -4;
        yA = 0;
        yB = -25;
        yC = -1;
        System.out.println("triangleSquare(xA, yA, xB, yB, xC, yC) = " + triangleSquare(xA, yA, xB, yB, xC, yC));

        String str;
        str = "";
        System.out.println("isAllowed(str) = " + isAllowed(str));
        str = "(ekjfhew[weiofwmio]7238)";
        System.out.println("isAllowed(str) = " + isAllowed(str));
        str = "{weirjmweoi3298[wriuxhiu3}wqri,]";
        System.out.println("isAllowed(str) = " + isAllowed(str));
        str = "3892nrx]samdi)wur}";
        System.out.println("isAllowed(str) = " + isAllowed(str));
    }
    //1.1
    public static long unique(int[] arr)
    {
        long count = 0;
        pointer:
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if(arr[j] == arr[i])
                {
                    continue pointer;
                }
            }
            count++;
        }
        return count;
    }
    //1.2
    public static boolean horseMove(int x, int y, int x1, int y1)
    {
        if(Math.abs(x1 - x) == 2 && Math.abs(y1 - y) == 1)
        {
            return true;
        }
        if(Math.abs(y1 - y) == 2 && Math.abs(x1 - x) == 1)
        {
            return true;
        }
        return false;
    }
    //1.3
    public static double triangleSquare(double xA, double yA, double xB, double yB,double xC, double yC)
    {
        double AB = Math.sqrt(Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2));
        double AC = Math.sqrt(Math.pow(xC - xA, 2) + Math.pow(yC - yA, 2));
        double BC = Math.sqrt(Math.pow(xC - xB, 2) + Math.pow(yC - yB, 2));

        double halfSum = (AB + AC + BC) / 2;
        double square = Math.sqrt(halfSum * (halfSum - AB) * (halfSum - AC) * (halfSum - BC));

        return square;
    }
    //2.1
    public static boolean isAllowed(String str) {
        String[] s = str.split("");
        List<String> l = new LinkedList<>(Arrays.asList(s));
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).equals("(")) {
                for (int j = l.size() - 1; j > 0; j--) {
                    if (l.get(j).equals("]") || l.get(j).equals("}")) {
                        return false;
                    }
                    if (l.get(j).equals(")")) {
                        l.remove(j);
                        l.remove(i);
                        break;
                    }
                }
            }
            if (l.get(i).equals("[")) {
                for (int j = l.size() - 1; j > 0; j--) {
                    if (l.get(j).equals(")") || l.get(j).equals("}")) {
                        return false;
                    }
                    if (l.get(j).equals("]")) {
                        l.remove(j);
                        l.remove(i);
                        break;
                    }
                }
            }
            if (l.get(i).equals("{")) {
                for (int j = l.size() - 1; j > 0; j--) {
                    if (l.get(j).equals("]") || l.get(j).equals(")")) {
                        return false;
                    }
                    if (l.get(j).equals("}")) {
                        l.remove(j);
                        l.remove(i);
                        break;
                    }
                }
            }
            if (l.get(i).equals("}") || l.get(i).equals(")") || l.get(i).equals("]")) {
                return false;
            }
        }
        return true;
    }
}
