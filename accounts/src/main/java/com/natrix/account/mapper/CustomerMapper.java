package com.natrix.account.mapper;

import com.natrix.account.dto.CustomerDto;
import com.natrix.account.entity.Customer;

public class CustomerMapper {

    public static CustomerDto mapToDto(Customer customer, CustomerDto customerDto) {

        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer mapToEntity(CustomerDto customerDto, Customer customer) {

        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

}
