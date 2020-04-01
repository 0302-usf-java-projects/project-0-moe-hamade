package com.revature.applicationTests;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.revature.model.Account;
import com.revature.repository.BankingDAO;
import com.revature.service.AccService;



public class Tests {

  private static AccService accS;
  
  @Before
  public void setUp() {
  accS = new AccService();
  }
  
  @After
  public void tearDown() {
    accS = null;
  }

  @Test
  public void checkNegativeDepositTest(){
    
    double result = -1;
    assertTrue(accS.depositMoney(1, result) == -1);
    
  }
  
  @Test
  public void checkLoginWithValidCredetials() {
    BankingDAO bd = new BankingDAO();
    Account accnt = new Account("moe", "hamade123");
    assertTrue(bd.login(accnt) == true);
  }
  
}
