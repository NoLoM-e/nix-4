package com.company;


import java.util.Arrays;
import java.util.Scanner;



class Main
{

    public static void main(String[] args)
    {
        Scanner s = new Scanner(System.in);


        System.out.println("Enter str for task 1:");
        countString(s.next());

        System.out.println("Enter str for task 2:");
        countChars(s.next());

        System.out.println("Enter lesson number for task 3:");
        lessonTime(s.nextInt());
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

    public static void countChars(String str)
    {
        str = str.replaceAll("[^a-zA-Zа-яА-Я]", "");

        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        str = new String(chars);

        while (!str.equals(""))
        {
            char c = str.charAt(0);
            int counter = 0;

            for(int i = 0; i < str.length(); i++)
            {
                if(str.charAt(i) == c)
                {
                    counter++;
                }
            }

            System.out.println(c + " - " + counter);

            str = str.replaceAll(String.valueOf(c), "");
        }
    }

    public static void lessonTime(double i)
    {
        double hours = 9;
        double minutes = 0;
        double duration;

        duration = 45 * i + 5 * Math.ceil(i / 2) + 10 * Math.floor(i / 2);

        hours += Math.floor(duration / 60) % 24;
        minutes += duration % 60;

        System.out.println((int) hours + " " + (int)  minutes);
    }
}