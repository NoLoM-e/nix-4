package com.company.impl;

import com.company.calculator.Calculator;

import java.math.BigDecimal;

public class ImplementCalculator implements Calculator
{
    public BigDecimal sum(BigDecimal a, BigDecimal b)
    {
        return a.add(b);
    }
    public BigDecimal minus(BigDecimal a, BigDecimal b)
    {
        return a.subtract(b);
    }
    public BigDecimal mult(BigDecimal a, BigDecimal b)
    {
        return a.multiply(b);
    }
    public BigDecimal pow(BigDecimal a, int b)
    {
        return a.pow(b);
    }
    public BigDecimal divide(BigDecimal a, BigDecimal b)
    {
        return a.divide(b);
    }
}
