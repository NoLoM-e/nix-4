package com.company;


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
    }
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
}
