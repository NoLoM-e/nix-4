package com.company.service.impl;

import com.company.dto.AccountDto;
import com.company.dto.CategoriesDto;
import com.company.dto.OperationDto;
import com.company.dto.UserDto;
import com.company.entity.Account;
import com.company.entity.Category;
import com.company.entity.Operation;
import com.company.entity.User;
import com.company.exceptions.OperationException;
import com.company.service.AccountService;
import com.company.util.Converter;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;


public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private Connection connection;

    public AccountServiceImpl(String url, String user, String pass){
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            logger.error(String.format("Unable to connect to %s with user %s, pass %s", url, user, pass));
            throw new RuntimeException("Unable to connect to database");
        }
    }

    @Override
    public Optional<AccountDto> findById(long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("select * from accounts where id = ?")){

            logger.info(String.format("Starting searching account with id %d", id));

            connection.setAutoCommit(false);

            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            if(!rs.next()){
                logger.error(String.format("Unable to find account with id %d", id));
                connection.rollback();
                throw new RuntimeException(String.format("Unable to find account with id %d", id));
            }

            AccountDto accountDto = new AccountDto();
            accountDto.setId(rs.getBigDecimal("id").longValue());
            accountDto.setValue(rs.getDouble("value"));
            accountDto.setUser(findUser(rs.getBigDecimal("user_id").longValue()));

            connection.commit();

            logger.info(String.format("Finished searching account with id %d", id));
            return Optional.of(accountDto);
        } catch (SQLException e) {
            logger.error(String.format("Unable to find account with id %d", id));
            connection.rollback();
            throw e;
        }
    }

    @Override
    public List<String[]> findByIdAndPeriod(long id, String from, String to) throws SQLException {

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             "select * from operations o where o.account_id = ? and o.commited between cast('" + from + "' as date) and cast('" + to +"' as date)")){
            connection.setAutoCommit(false);

            logger.info(String.format("Starting searching operations with account id %d from %s to %s", id, from, to));

            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            List<String[]> res = new ArrayList<>();

            res.add(new String[]{"id", "commited", "value", "categories"});

            while (rs.next()){
                String[] operation = new String[4];
                operation[0] = rs.getBigDecimal("id").toString();
                operation[1] = rs.getTimestamp("commited").toString();
                operation[2] = String.valueOf(rs.getDouble("value"));
                List<CategoriesDto> categories = findCategoriesByOperationId(rs.getBigDecimal("id").longValue());
                List<String> collect = categories.stream().map(CategoriesDto::getName).collect(Collectors.toList());
                operation[3] = String.join(";", collect);
                res.add(operation);
            }
            connection.commit();
            logger.info(String.format("Finished searching operations with account id %d from %s to %s", id, from, to));
            return res;
        } catch (SQLException e) {
            logger.error(String.format("Unable to find account with id %d and with time limits %s %s", id, from, to));
            connection.rollback();
            throw e;
        }
    }

    @Override
    public List<AccountDto> findByOwnerId(long id) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("select * from accounts where user_id = ?")){

            connection.setAutoCommit(false);

            logger.info(String.format("Starting searching accounts with user id %d", id));

            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            List<AccountDto> dtos = new ArrayList<>();

            while (rs.next()) {
                AccountDto dto = new AccountDto(rs.getBigDecimal("id").longValue(), findUser(rs.getBigDecimal("user_id").longValue()), rs.getDouble("value"));
                dtos.add(dto);
            }
            connection.commit();

            logger.info(String.format("Finished searching accounts with user id %d", id));
            return dtos;

        } catch (SQLException e) {
            logger.error("Unnable to find by user id " + id);
            connection.rollback();
            throw e;
        }
    }


    private UserDto findUser(long id) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("select * from users where id = ?")) {

            connection.setAutoCommit(false);

            logger.info(String.format("Starting searching user with id %d", id));

            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            connection.commit();

            if(!rs.next()){
                logger.error("Unable to find user with id " + id);
                throw new RuntimeException("Unable to find user with id " + id);
            }

            logger.info(String.format("Finished searching user with id %d", id));
            return new UserDto(
                    rs.getBigDecimal("id").longValue(),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("tel"));

        } catch (SQLException e) {
            logger.error("Unable to find user with id " + id);
            connection.rollback();
            throw e;
        }
    }

    private List<CategoriesDto> findCategoriesByOperationId(long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from categories c inner join categories_operations co on co.operation_id = ? where c.id = co.category_id")){

            connection.setAutoCommit(false);

            logger.info(String.format("Starting searching categories with operation id %d", id));

            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            List<CategoriesDto> res = new ArrayList<>();

            while (rs.next()){
                CategoriesDto category = new CategoriesDto();
                category.setName(rs.getString("name"));
                category.setId(rs.getBigDecimal("id").longValue());
                category.setIncome(rs.getBoolean("isincome"));
                res.add(category);
            }

            connection.commit();

            logger.info(String.format("Finished searching categories with operation id %d", id));

            return res;
        } catch (SQLException e) {
            logger.error("Unable to find categories for operation with id" + id);
            connection.rollback();
            throw new RuntimeException("Unable to find categories for operation with id " + id);
        }
    }
}
