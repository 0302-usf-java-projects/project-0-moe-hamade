package com.revature.repository;

import com.revature.model.Account;

public interface DAOInterface {

  public boolean addAccount(Account account);
  public boolean login(Account account);
  public double depositMoney(int id, double depo);
  public double withdrawMoney(int id, double wd);
  public boolean authenticateUser(String username );
  public boolean transferBetweenAccounts(int id, String user_name, double num);
}
