package com.company;


public class App 
{
    public static void main( String[] args ) {

        int[] arr = {1,2,3,124,12,1,2,3,1};
        System.out.println("unique(arr) = " + unique(arr));
        arr = new int[]{1, 4, 5, 1, 1, 3};
        System.out.println("unique(arr) = " + unique(arr));
        arr = new int[]{12,135,64,5,1,3,512,3,1,5,2,5,5,5,1};
        System.out.println("unique(arr) = " + unique(arr));

        
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
}
