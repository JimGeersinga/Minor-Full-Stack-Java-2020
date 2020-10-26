package com.jim.layers.repository;

import com.jim.layers.PerformanceLogging;
import com.jim.layers.dao.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@PerformanceLogging
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
