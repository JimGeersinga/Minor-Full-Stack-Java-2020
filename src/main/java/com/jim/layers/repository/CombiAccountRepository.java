package com.jim.layers.repository;

import com.jim.layers.dao.CombiAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombiAccountRepository extends JpaRepository<CombiAccount, Long> {
}
