package com.company.controller;

import com.company.dto.AccountDto;
import com.company.dto.CategoriesDto;
import com.company.dto.SaveOperationDto;
import com.company.entity.Category;
import com.company.exceptions.OperationException;
import com.company.service.AccountService;
import com.company.service.OperationService;
import com.company.service.impl.AccountServiceImpl;
import com.company.service.impl.OperationServiceImpl;
import com.company.util.Converter;
import com.company.util.Parser;
import jakarta.validation.ValidatorFactory;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {

    Scanner s = new Scanner(System.in);

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    AccountService accountService;
    OperationService operationService;
    Parser parser;

    SessionFactory sessionFactory;
    EntityManager entityManager;
    ValidatorFactory validator;
    String name;
    String pass;
    int id;

    public Controller(SessionFactory sessionFactory, ValidatorFactory validator, String name, String pass, int id){
        this.sessionFactory = sessionFactory;
        this.validator = validator;
        this.name = name;
        this.pass = pass;
        this.id = id;
    }

    public void run(){

        try{
            this.entityManager = sessionFactory.createEntityManager();
            accountService = new AccountServiceImpl("jdbc:postgresql://localhost:5432/postgres",name, pass);
            operationService = new OperationServiceImpl(() -> entityManager, validator);
            parser = new Parser();

            showAllAccounts();

            while (true){
                System.out.println("Enter operation");
                System.out.println("1 - create new operation");
                System.out.println("2 - save info on account to file");
                System.out.println("3 - see all user accounts");
                System.out.println("0 - to exit");
                switch (s.nextLine()){
                    case "1":
                        try {
                            createNewOperation();
                        } catch (Exception e){
                            logger.error(e.getMessage());
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "2":
                        try {
                            saveToFile();
                        } catch (Exception e){
                            logger.error(e.getMessage());
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "3":
                        try {
                            showAllAccounts();
                        } catch (Exception e){
                            logger.error(e.getMessage());
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "0":
                        System.exit(0);
                    default:
                        System.out.println("Wrong option");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    private void createNewOperation(){
        System.out.println("Enter value of operation");

        double value = Double.parseDouble(s.nextLine());


        List<CategoriesDto> categories = new ArrayList<>();
        while (true){
            System.out.println("Enter category id or 0 to continue to next step");
            String id = s.nextLine();
            if(id.equals("0")){
                break;
            }
            else{
                categories.add(Converter.entityToDto(entityManager.find(Category.class, Long.parseLong(id))));
            }
        }

        System.out.println("Enter account id");
        AccountDto account = null;
        try {
            String id = s.nextLine();
            if(accountService.findByOwnerId(this.id).stream().noneMatch(accountDto -> accountDto.getId() == Long.parseLong(id))) {
                throw new RuntimeException("Wrong account id");
            }
            account = accountService.findById(Long.parseLong(id)).get();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        System.out.println("Enter time when transaction was commited, -1 - now");

        String time = s.nextLine();

        if(time.equals("-1")){
            time = Instant.now().toString();
        }

        SaveOperationDto operationDto = new SaveOperationDto(value, categories, Instant.parse(time), account);

        try {
            operationService.save(operationDto);
        } catch (OperationException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void saveToFile(){
        System.out.println("Enter account id");
        long id = Long.parseLong(s.nextLine());
        try {
            if(accountService.findByOwnerId(this.id).stream().noneMatch(accountDto -> accountDto.getId() == id)) {
                throw new RuntimeException("Wrong account id");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        System.out.println("Enter from time");
        String from = s.nextLine();

        System.out.println("Enter to time");
        String to = s.nextLine();

        try {
            List<String[]> byIdAndPeriod = accountService.findByIdAndPeriod(id, from, to);
            parser.parse(byIdAndPeriod);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void showAllAccounts(){
        try {
            List<AccountDto> accounts = accountService.findByOwnerId(id);

            for (AccountDto a : accounts) {
                System.out.println(String.format("id : %d \tvalue : %f", a.getId(), a.getValue()));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
