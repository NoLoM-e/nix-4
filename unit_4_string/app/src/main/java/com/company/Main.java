package com.company;


public class Main {

    public static void main(String[] args) {
        String str = "hello world";
        System.out.println(ReverseString.reverse(str));
        System.out.println(ReverseString.reverse(str, "worl"));
        System.out.println(ReverseString.reverse(str, 3, 7));
        str = "45678 wqui 378s";
        System.out.println(ReverseString.reverse(str));
        System.out.println(ReverseString.reverse(str, "78"));
        System.out.println(ReverseString.reverse(str, 3, 10));
    }
}
