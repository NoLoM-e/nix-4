package com.company;


import com.company.controller.Controller;
import com.company.entity.Account;
import com.company.entity.Category;
import com.company.entity.Operation;
import com.company.entity.User;

import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



public class Main {
    public static void main(String[] args) {

        try {
            String name = args[0];
            String pass = args[1];
            int id = Integer.parseInt(args[2]);

            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.username", name);
            configuration.setProperty("hibernate.connection.password", pass);
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres");
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperty("hibernate.show_sql", "false");
            configuration.setProperty("hibernate.format_sql", "false");
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Account.class);
            configuration.addAnnotatedClass(Category.class);
            configuration.addAnnotatedClass(Operation.class);

            SessionFactory sessionFactory = configuration.buildSessionFactory();

            ValidatorFactory validator = Validation.buildDefaultValidatorFactory();

            Controller controller = new Controller(sessionFactory, validator, name, pass, id);
            controller.run();


        } catch (NumberFormatException e){
            System.out.println("Wrong number format");
            System.out.println(e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
