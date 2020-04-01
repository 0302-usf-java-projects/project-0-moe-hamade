package com.revature.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import com.revature.model.Account;
import static com.revature.controller.Menus.*;

public class BankingDAO implements DAOInterface {
  
  Account accnt = new Account();

  @Override
  public boolean addAccount(Account account) {
    if(!authenticateUser(account.getUsername())) {
    try (Connection conn = ConnectionUtil.connect()){
      String sql = "insert into Account (username, pswrd, fullName, balance) values(?,?,?,?)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, account.getUsername());
      ps.setString(2, account.getPassword());
      ps.setString(3, account.getFullName());
      ps.setDouble(4, account.getBalance());
      ps.execute();
     return true;
    }catch(SQLException e) {
      e.printStackTrace();    
      }
    } else {
          return false;
  }
            return false;
}
  

  @Override
  public boolean login(Account account) {
    try (Connection conn = ConnectionUtil.connect()){
      String sql = "select * from Account where username = ? and pswrd = ?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, account.getUsername());
      ps.setString(2, account.getPassword());
      ResultSet rs = ps.executeQuery();
      rs.next();
      account.setId(rs.getInt(1));
      account.setUsername(rs.getString(2));
      account.setBalance(rs.getDouble(5));
      System.out.println("\nWe have identified your credentials. " + account.getUsername() + " you are now logged in.");
      return true;
      
    }catch(SQLException e) {
      System.out.println("We could not log you in, please try again.");
      mainMenu();  
    }
    return false;
  }

  @Override
  public double depositMoney(int id, double depo) {
    if(depo > 0) {
    try(Connection conn = ConnectionUtil.connect()){
      String sql = "{? = call depositMoney(?,?) }";
      CallableStatement cs = conn.prepareCall(sql);
      cs.registerOutParameter(1,Types.DOUBLE);
      cs.setInt(2, id);
      cs.setDouble(3, depo);
      cs.execute();
      double d = cs.getDouble(1);
      cs.close();
      return d;
      
    }catch(SQLException e) {
      e.printStackTrace();
      }
    }else {
      return -1;
  }
        return -1;
}
  
  @Override
  public double withdrawMoney(int id, double wd) {
    try(Connection conn = ConnectionUtil.connect()){
      String sql = "{? = call withdrawMoney(?,?) }";
      CallableStatement cs = conn.prepareCall(sql);
      cs.registerOutParameter(1,Types.DOUBLE);
      cs.setInt(2, id);
      cs.setDouble(3, wd);
      cs.execute();
      double d = cs.getDouble(1);
      cs.close();
      return d;
      
    }catch(SQLException e) {
      e.printStackTrace();
    }
    return -1;
    
  }

  @Override
  public boolean authenticateUser(String username) {
   try (Connection conn = ConnectionUtil.connect()){
     String sql = "select exists(select 1 from account where username = '" + username + "')";
     PreparedStatement ps = conn.prepareStatement(sql);
     ResultSet rs = ps.executeQuery();
     rs.next();
     boolean match = rs.getBoolean(1);
     return match;
   
   }catch(SQLException e) {
     e.printStackTrace();
     }
      return false;
 }


  @Override
  public boolean transferBetweenAccounts(int id, String user_name, double num) {
   if(authenticateUser(user_name)) {
      try(Connection conn = ConnectionUtil.connect()){
         String sql = "{? = call transferBetweenAccounts(?,?,?) }";
         CallableStatement cs = conn.prepareCall(sql);
         cs.registerOutParameter(1,Types.DOUBLE);
         cs.setInt(2, id);
         cs.setString(3,  user_name);
         cs.setDouble(4, num);
         cs.execute();
         double d = cs.getDouble(1);
         accnt.setBalance(d);
         cs.close();
         return true;
         
       }catch(SQLException e) {
         e.printStackTrace();
       }
     }
     return false;
   }
  }

 
 

  
  



  
