package com.company.factory;

import com.company.console_help.ConsoleHelper;
import org.reflections.Reflections;

import java.util.Set;

public class FactoryConsoleHelper {

    private static FactoryConsoleHelper instance;
    private Reflections reflections;
    private Set<Class<? extends ConsoleHelper>> console;

    private FactoryConsoleHelper() {
        reflections = new Reflections("com.company");
        console = reflections.getSubTypesOf(ConsoleHelper.class);
    }

    public static FactoryConsoleHelper getInstance() {
        if (instance == null) {
            instance = new FactoryConsoleHelper();
        }
        return instance;
    }

    public ConsoleHelper getConsole() {
        for (Class<? extends ConsoleHelper> console : console) {
            if (!console.isAnnotationPresent(Deprecated.class)) {
                try {
                    return console.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        throw new RuntimeException("Error");
    }
}