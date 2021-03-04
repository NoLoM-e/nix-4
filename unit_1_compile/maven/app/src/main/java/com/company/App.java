package com.company;

import com.company.Class2;
import com.company.class1.Class1;
import org.apache.commons.math3.complex.*;

public class App
{
    public static void main(String[] args)
    {
        Class1 class1 = new Class1();
        Class2 class2 = new Class2();

        class1.test();
        class2.test();

        Complex z1 = new Complex(1.5, 21.7);
        Complex z2 = new Complex(3.21, 1.11);
        Complex z3 = z1.add(z2);

        System.out.println("(1.5 + 21i) + (3.21 + 1.11i) = " + z3.getReal() + " + " + z3.getImaginary() + "i");
    }
}