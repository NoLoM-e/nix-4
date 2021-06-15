package com.company.service;

import com.company.dto.OperationDto;
import com.company.dto.SaveOperationDto;
import com.company.exceptions.OperationException;

public interface OperationService {

    OperationDto getById(Long id) throws OperationException;
    void save(SaveOperationDto operation) throws OperationException;
}
