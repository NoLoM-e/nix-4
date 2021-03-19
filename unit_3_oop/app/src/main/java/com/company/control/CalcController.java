package com.company.control;

import com.company.calculator.Calculator;
import com.company.console_help.ConsoleHelper;
import com.company.factory.FactoryCalculator;
import com.company.factory.FactoryConsoleHelper;

import java.math.BigDecimal;


public class CalcController
{
    private final Calculator calculator = FactoryCalculator.getInstance().getCalc();
    private final ConsoleHelper console = FactoryConsoleHelper.getInstance().getConsole();

    public void run()
    {
        pointer:
        while (true)
        {
            System.out.println("1 to start, 0 to finish");
            switch (console.getStr())
            {
                case "1" : console.out(calculate()); break;
                case "0" : break pointer;
            }

        }
    }

    private String calculate()
    {
        System.out.println("Enter first number:");
        BigDecimal a = new BigDecimal(console.getStr());
        System.out.println("Enter second number:");
        BigDecimal b = new BigDecimal(console.getStr());
        System.out.println("Enter operation +, -, *, /, **:");
        switch (console.getStr()) {
            case "+":
                return calculator.sum(a, b).toString();
            case "-":
                return calculator.minus(a, b).toString();
            case "*":
                return calculator.mult(a, b).toString();
            case "/":
                return calculator.divide(a, b).toString();
            case "**":
                return calculator.pow(a, b.intValue()).toString();
            default:
                return "Unknown input";
        }
    }
}
