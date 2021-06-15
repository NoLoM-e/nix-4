package com.company.service.impl;

import com.company.dto.CategoriesDto;
import com.company.dto.OperationDto;
import com.company.dto.SaveOperationDto;
import com.company.entity.Account;
import com.company.entity.Category;
import com.company.entity.Operation;
import com.company.exceptions.OperationException;
import com.company.service.OperationService;
import com.company.util.Converter;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Supplier;

public class OperationServiceImpl implements OperationService {

    private static final Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);

    private final Supplier<EntityManager> manager;

    private final Validator validator;

    public OperationServiceImpl(Supplier<EntityManager> manager, ValidatorFactory validator){
        this.manager = manager;
        this.validator = validator.getValidator();
    }


    @Override
    public OperationDto getById(Long id) throws OperationException {
        EntityManager entityManager = manager.get();

        logger.info(String.format("Starting searching operation with id %d", id));

        Operation operation = entityManager.find(Operation.class, id);

        logger.info(String.format("Finished searching operation with id %d", id));
        return Converter.entityToDto(operation);
    }

    @Override
    public void save(SaveOperationDto operationDto) throws OperationException {
        validate(operationDto);

        logger.info(String.format("Starting saving operation commited at %s", operationDto.getCommited().toString()));

        if(operationDto.getValue() == 0){
            logger.error("Value of operation committed at " + operationDto.getCommited() + " was 0");
            throw new OperationException("Value of operation cannot be 0");
        }

        boolean isIncome = operationDto.getCategories().get(0).isIncome();

        for (CategoriesDto category :
                operationDto.getCategories()) {
            if(category.isIncome() != isIncome){
                throw new OperationException("All categories have to be income or consuming");
            }
        }

        if(isIncome && operationDto.getValue() < 0){
            throw new OperationException("Operation cannot be income with negative value");
        }

        if(!isIncome && operationDto.getValue() > 0){
            throw new OperationException("Operation cannot be consuming with positive value");
        }

        EntityManager entityManager = manager.get();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try{
            Operation operation = new Operation();
            operation.setCategories(new ArrayList<>());
            mergeEntityWithDto(operationDto, operation);
            entityManager.persist(operation);
            updateBalance(operation);
            transaction.commit();
            logger.info(String.format("Finished saving operation commited at %s", operationDto.getCommited().toString()));
        } catch (Exception e){
            transaction.rollback();
            logger.error("Unable to save operation", e);
            throw e;
        }
    }

    private void validate(SaveOperationDto operationDto) {
        Set<ConstraintViolation<SaveOperationDto>> constraintViolations = validator.validate(operationDto);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    private void mergeEntityWithDto(SaveOperationDto dto, Operation operation){
        EntityManager entityManager = manager.get();

        operation.setAccount(entityManager.find(Account.class, dto.getAccount().getId()));
        operation.setValue(dto.getValue());
        operation.setCommited(dto.getCommited());
        for (CategoriesDto category: dto.getCategories()) {
            operation.addCategory(entityManager.find(Category.class, category.getId()));
        }
    }

    private void updateBalance(Operation operation){
        EntityManager entityManager = manager.get();

        Account account = operation.getAccount();

        account.setValue(account.getValue() + operation.getValue());

        entityManager.merge(account);
    }
}
