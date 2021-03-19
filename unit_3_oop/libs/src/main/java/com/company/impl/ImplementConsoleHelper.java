package com.company.impl;

import com.company.console_help.ConsoleHelper;

import java.util.Scanner;

public class ImplementConsoleHelper implements ConsoleHelper
{
    private Scanner s = new Scanner(System.in);
    public int getInt()
    {
        return s.nextInt();
    }

    public long getLong()
    {
        return s.nextLong();
    }

    public String getStr()
    {
        return s.next();
    }

    public void out(Object o)
    {
        System.out.println(o);
    }
}
