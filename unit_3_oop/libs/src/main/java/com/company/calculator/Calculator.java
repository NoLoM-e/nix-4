package com.company.calculator;

import java.math.BigDecimal;

public interface Calculator
{
    BigDecimal sum(BigDecimal a, BigDecimal b);
    BigDecimal minus(BigDecimal a, BigDecimal b);
    BigDecimal mult(BigDecimal a, BigDecimal b);
    BigDecimal pow(BigDecimal a, int b);
    BigDecimal divide(BigDecimal a, BigDecimal b);
}