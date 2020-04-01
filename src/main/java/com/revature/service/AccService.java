package com.revature.service;

import com.revature.model.Account;
import com.revature.repository.BankingDAO;

public class AccService {
    
  private static BankingDAO bd = new BankingDAO();
  
  public boolean createAccount (Account a) {
           return  bd.addAccount(a);
  }
  
  public double depositMoney (int id, double depo) {
    return bd.depositMoney(id,depo);
  }

  public double withdrawMoney(int id, double wd) {
    return bd.withdrawMoney(id, wd) ;
    
  }
  
  public boolean transfer(int id, String user_name, double num) {
    return bd.transferBetweenAccounts(id, user_name, num);
  }  
}
