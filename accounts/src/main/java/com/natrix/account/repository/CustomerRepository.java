package com.natrix.account.repository;

import com.natrix.account.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, Long> {

    boolean existsByMobileNumber(String mobileNumber);

    Optional<Customer> findByMobileNumber(String mobileNumber);

}
