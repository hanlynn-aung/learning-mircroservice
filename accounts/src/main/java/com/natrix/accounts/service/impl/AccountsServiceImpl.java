package com.natrix.accounts.service.impl;

import com.natrix.accounts.constants.AccountsConstants;
import com.natrix.accounts.dto.CustomerDto;
import com.natrix.accounts.entity.Accounts;
import com.natrix.accounts.entity.Customer;
import com.natrix.accounts.exception.CustomerAlreadyExistsException;
import com.natrix.accounts.mapper.CustomerMapper;
import com.natrix.accounts.repository.AccountsRepository;
import com.natrix.accounts.repository.CustomerRepository;
import com.natrix.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;

    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToEntity(customerDto, new Customer());

        if (this.customerRepository.existsByMobileNumber(customer.getMobileNumber())) {
            throw new CustomerAlreadyExistsException("Customer with mobile number " + customer.getMobileNumber() + " already exists");
        }

        Customer savedCustomer = this.customerRepository.save(customer);

        Accounts accounts = this.createNewAccount(savedCustomer);

        this.accountsRepository.save(accounts);

    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {

        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

}
