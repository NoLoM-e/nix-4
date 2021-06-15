package com.company.util;

import com.company.dto.AccountDto;
import com.company.dto.CategoriesDto;
import com.company.dto.OperationDto;
import com.company.dto.UserDto;
import com.company.entity.Account;
import com.company.entity.Category;
import com.company.entity.Operation;
import com.company.entity.User;

import java.util.stream.Collectors;

public class Converter {

    public static AccountDto entityToDto(Account account){
        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setUser(entityToDto(account.getUser()));
        dto.setValue(account.getValue());
        return dto;
    }

    public static UserDto entityToDto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setTel(user.getTel());
        return dto;
    }

    public static CategoriesDto entityToDto(Category category){
        CategoriesDto dto = new CategoriesDto();
        dto.setId(category.getId());
        dto.setIncome(category.isIncome());
        dto.setName(category.getName());
        return dto;
    }

    public static OperationDto entityToDto(Operation operation){
        OperationDto dto = new OperationDto();
        dto.setCommited(operation.getCommited());
        dto.setValue(operation.getValue());
        dto.setAccount(entityToDto(operation.getAccount()));
        dto.setCategories(operation.getCategories().stream().map(Converter::entityToDto).collect(Collectors.toList()));
        return dto;
    }

    public static void mergeEntityWithDto(Operation operation, OperationDto dto){
        operation.setValue(dto.getValue());
        mergeEntityWithDto(operation.getAccount(), dto.getAccount());
        operation.setCommited(dto.getCommited());
        for (CategoriesDto category :
                dto.getCategories()) {
            Category category1 = new Category();
            mergeEntityWithDto(category1, category);
            operation.addCategory(category1);
        }
    }
    public static void mergeEntityWithDto(Account account, AccountDto dto){
        account.setValue(dto.getValue());
        mergeEntityWithDto(account.getUser(), dto.getUser());
    }
    public static void mergeEntityWithDto(Category category, CategoriesDto dto){
        category.setIncome(dto.isIncome());
    }
    public static void mergeEntityWithDto(User user, UserDto dto){
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setTel(dto.getTel());
    }

}
