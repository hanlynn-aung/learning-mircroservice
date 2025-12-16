package com.natrix.accounts.repository;

import com.natrix.accounts.entity.Accounts;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends BaseRepository<Accounts, Long> {

}
