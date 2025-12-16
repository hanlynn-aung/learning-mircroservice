package com.natrix.accounts.repository;

import com.natrix.accounts.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, Long> {

    boolean existsByMobileNumber(String mobileNumber);
}
