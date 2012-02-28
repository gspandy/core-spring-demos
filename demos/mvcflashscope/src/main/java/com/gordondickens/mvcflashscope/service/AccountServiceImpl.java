package com.gordondickens.mvcflashscope.service;

import com.gordondickens.mvcflashscope.domain.Account;
import com.gordondickens.mvcflashscope.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository AccountRepository;

    public long countAllAccounts() {
        return AccountRepository.count();
    }

    public void deleteAccount(Account Account) {
        AccountRepository.delete(Account);
    }

    public Account findAccount(Long id) {
        return AccountRepository.findOne(id);
    }

    public List<Account> findAllAccounts() {
        return AccountRepository.findAll();
    }

    public List<Account> findAccountEntries(int firstResult, int maxResults) {
        return AccountRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

    public void saveAccount(Account Account) {
        AccountRepository.save(Account);
    }

    public Account updateAccount(Account Account) {
        return AccountRepository.save(Account);
    }
}
