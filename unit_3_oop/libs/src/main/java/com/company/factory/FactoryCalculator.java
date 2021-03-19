package com.company.factory;

import com.company.calculator.Calculator;
import org.reflections.Reflections;

import java.util.Set;

public class FactoryCalculator {

    private static FactoryCalculator instance;
    private Reflections reflections;
    private Set<Class<? extends Calculator>> calc;

    private FactoryCalculator() {
        reflections = new Reflections("com.company");
        calc = reflections.getSubTypesOf(Calculator.class);
    }

    public static FactoryCalculator getInstance() {
        if (instance == null) {
            instance = new FactoryCalculator();
        }
        return instance;
    }

    public Calculator getCalc() {
        for (Class<? extends Calculator> calc : calc) {
            if (!calc.isAnnotationPresent(Deprecated.class)) {
                try {
                    return calc.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        throw new RuntimeException("Error");
    }
}