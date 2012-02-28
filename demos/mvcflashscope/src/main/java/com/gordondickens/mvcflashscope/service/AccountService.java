package com.gordondickens.mvcflashscope.service;

import com.gordondickens.mvcflashscope.domain.Account;

import java.util.List;

/**
 * User: gordondickens
 * Date: 2/28/12
 * Time: 10:09 AM
 */
public interface AccountService {

    public abstract long countAllAccounts();


    public abstract void deleteAccount(Account Account);


    public abstract Account findAccount(Long id);


    public abstract List<Account> findAllAccounts();


    public abstract List<Account> findAccountEntries(int firstResult, int maxResults);


    public abstract void saveAccount(Account Account);


    public abstract Account updateAccount(Account Account);

}
