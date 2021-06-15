package com.company.service;

import com.company.dto.AccountDto;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    Optional<AccountDto> findById(long id) throws SQLException;
    List<String[]> findByIdAndPeriod(long id, String from, String to) throws SQLException;
    List<AccountDto> findByOwnerId(long id) throws SQLException;
}
