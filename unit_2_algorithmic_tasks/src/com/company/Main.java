package com.company;


import java.util.Scanner;



class Main
{

    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);


        System.out.println("Enter str for task 1:");
        countString(s.next());
    }

    public static void countString(String str)
    {
        int sum = 0;

        str = str.replaceAll("[^0-9]", "");

        for(int i = 0; i < str.length(); i++)
        {
            sum += str.charAt(i) - 48;
        }

        System.out.println("sum = " + sum);
    }
}