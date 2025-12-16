package com.natrix.accounts.service;

import com.natrix.accounts.dto.CustomerDto;

public interface IAccountsService {

    void createAccount(CustomerDto customerDto);
}
