package com.revature.model;

import com.revature.exception.PasswordTooShortException;
import com.revature.exception.UsernameIsTakenException;

public class Account {
  
  public static boolean loggedIn = false;
  private static int id;
  private static String username;
  private static String password;
  private static String fullName;
  private static double balance = 0;
  
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
  
  public Account (String username, String password){
    this.setUsername(username);
    this.setPassword(password);
    
  }
  
  public Account (String username, String password, String fullname){
    this.setUsername(username);
    this.setPassword(password);
    this.setFullName(fullname);
    
  }
  

  
  
  public Account() {
  }

  public boolean authenticate(String username, String password) {
    return this.username.equals(username) && this.password.equals(password);
  }
  
  
  
  public String getUsername() {
    return username;
  }
  public void setUsername(String username){
    this.username = username;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password){

    this.password = password;
  }
  public String getFullName() {
    return fullName;
  }
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
  
  public double getBalance() {
    return this.balance;
  }
  
  
  public void setBalance(double balance) {
    this.balance = balance;
  }

  public void reduceBalace(double amount) {
    this.balance -= amount;
    }
  public void increaseBalance(double amount) {
    this.balance += amount;
  }
  
  @Override
  public String toString() {
    return "Account [username=" + username + ", password=" + password + ", fullName=" + fullName
        + "]";
  }
  
  
}
