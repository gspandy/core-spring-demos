package com.gordondickens.mvcflashscope.repository;

import com.gordondickens.mvcflashscope.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository
        extends JpaSpecificationExecutor<Account>, JpaRepository<Account, Long> {
}
