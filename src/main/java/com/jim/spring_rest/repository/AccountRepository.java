package com.jim.spring_rest.repository;

import com.jim.spring_rest.dao.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "select a.* from account a inner join account_holder_account aha on aha.account_Id = a.Id where aha.account_holder_Id = :accountHolderId", nativeQuery = true)
    Collection<Account> getByAccountHolderId(long accountHolderId);
}
