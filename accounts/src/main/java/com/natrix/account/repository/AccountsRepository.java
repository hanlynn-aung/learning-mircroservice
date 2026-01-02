package com.natrix.account.repository;

import com.natrix.account.entity.Accounts;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends BaseRepository<Accounts, Long> {

    Optional<Accounts> findByCustomerId(Long customerId);

    @Modifying
    void deleteByCustomerId(Long customerId);

}
