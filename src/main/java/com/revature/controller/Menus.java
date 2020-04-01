package com.revature.controller;

import java.util.*;
import com.revature.exception.PasswordTooShortException;
import com.revature.exception.UsernameIsTakenException;
import com.revature.model.Account;
import com.revature.repository.BankingDAO;
import com.revature.service.AccService;



public class Menus {


  public static Scanner sc = new Scanner(System.in);
  private static AccService as = new AccService();
//  private static Set<Account> accounts = new HashSet<Account>();
  private static Account accnt = new Account();
  private static BankingDAO bd = new BankingDAO();
  private static int PASSWORD_REQUIRED_LENGTH = 8;



  public static void mainMenu() {
    System.out.println("Welcome to the main menu! Please select an option from the following");
    System.out.println("1. Create an account");
    System.out.println("2. Log in if you are already a memeber");
    System.out.println("3. Exit application");
    
    int option = sc.nextInt();
    sc.nextLine();
    switch (option) {

      case 1:
        createAccount();
        mainMenu();
        break;
        

      case 2:
        if (logIn()) {
          runMenu();
        } else {
          mainMenu();

        }
        break;

      case 3:
        System.exit(0);

      default:
        System.out.println("sorry we don't recognize this option, please try again");
        mainMenu();
        break;
    }
  }
  

  public static void createAccount() {
    System.out.println("We're glad to help you create an account\n\n");
    int attempts = 0;
    while (attempts < 3) {
      System.out.println("Please enter your first and last name");
      String fullName = sc.nextLine();
      System.out.println("Please enter a username that you desire");
      String username = sc.nextLine();
      System.out.println("Please enter a password that you desire");
      String password = sc.nextLine();
      try {
          if(bd.authenticateUser(username)) {
            throw new UsernameIsTakenException();
          }
        if(password.length() < PASSWORD_REQUIRED_LENGTH) {
          throw new PasswordTooShortException();
        }else {Account account = new Account(username, password, fullName);
        if (as.createAccount(account)) {
          System.out.println("congrats, you created an account!");
          break;
        } else {
          System.out.println("Sorry, creating your account was not successful, please try again");
          attempts++;
          break;
        }
        }
      }catch(PasswordTooShortException e) {
        System.out.println("Password is too short, please try again, please enter your credentials again");
        attempts++;
        createAccount();
      }catch (UsernameIsTakenException e){
        System.out.println("Username is already taken. Please choose a different username");
        attempts++;
        createAccount();
      }

      // try {
      
    }
  }

    // }catch (PasswordTooShortException e) {
    // System.out.println("Password is too short, please try again");
    // attempts++;
    // }catch(UsernameIsTakenException e) {
    // System.out.println("Username is already taken, please pick a diffenrent username");
    // attempts++;
    // }
    //
    // }
//    if (attempts > 3) {
//      System.out.println("Too many attempts. You will be returned to the welcome menu");
//      mainMenu();

    
  

  // }
  //
  public static boolean logIn() {
    System.out.println("To log in, please enter your username");
    String user_In = sc.next();
    System.out.println("Please enter your password");
    String pass_In = sc.next();
    return bd.login(new Account(user_In, pass_In));
  }

  public static int runMenu() {
    System.out.println("You are logged in. Please select from the following options");
    System.out.println("Choose 1 to make a deposit");
    System.out.println("Choose 2 to withdraw money");
    System.out.println("Choose 3 to view you balance");
    System.out.println("Choose 4 to make a transfer");
    System.out.println("Choose 5 to logout and return to login menu");
    System.out.println("Choose 0 to exit");

    int options1 = sc.nextInt();
    sc.nextLine();
    switch (options1) {
      case 1:
        depositMoney();
        return 1;

      case 2:
        withdrawMoney();
        return 1;

      case 3:
        viewBalance();
        return 1;

      case 4:
        transferBetweenAccounts();
        return 1;
        
      case 5:
        if(logIn()) {
          runMenu();
        }else {
          mainMenu();
        }
        return 1;
        
      case 0:
        System.exit(0);

      default:
        System.out.println("This option is not available, please try again");
        runMenu();
        return 1;



    }

  }

  public static void depositMoney() {
    System.out.println("Please enter the ammount you would like to deposit");
    double depo = sc.nextDouble();
    if(depo > 0) {
    accnt.setBalance(as.depositMoney(accnt.getId(), depo));
    System.out.println("Thank you for making a deposit.\nPress 1 if you would like to view your balance");
    System.out.println("press 2 if you would like to go back to the bank menu");
    System.out.println("press 3 to log out and return to the main menu");
    String options2 = sc.next();
    switch (options2) {
      case "1":
        System.out.println(
            "Your balance is: " + accnt.getBalance() + ". You will be returned to the bank menu.");
        runMenu();
        break;
      case "2":
        runMenu();
        break;
      case "3":
        mainMenu();

      default:
        System.out
            .println("This is not an option, you will be logged out and returned to the main menu");
        mainMenu();


    }
    
   } else {
     System.out.println("Your input is invalid. Please enter a valid amount.");
     depositMoney();
   }

  }

  public static void withdrawMoney() {
    System.out.println("please enter the amount you would like to withdraw");
    double wd = sc.nextDouble();

    if (wd > 0) {
      if (accnt.getBalance() >= wd) {
        accnt.setBalance(as.withdrawMoney(accnt.getId(), wd));
      } else {
        System.out.println("you dont have enough money, please enter a lower amount");
        withdrawMoney();
      }
    } else {
      System.out.println("your input was less than 0, please enter a valid amount");
      withdrawMoney();
    }
    System.out.println("Thank you for banking with us. \nPress 1 if you would like to view your balance");
    System.out.println("Press 2 if you would like to go back to the bank menu");
    System.out.println("Press 3 to log out and return to the main menu");
    String options3 = sc.next();
    switch (options3) {
      case "1":
        System.out.println(
            "Your balance is: " + accnt.getBalance() + ". You will be returned to the bank menu.");
        runMenu();
        break;
      case "2":
        runMenu();
        break;
      case "3":
        mainMenu();

      default:
        System.out
            .println("This is not an option, you will be logged out and returned to the main menu");
        mainMenu();

    }
  }

  public static void viewBalance() {
    System.out.println("Your balance is: " + accnt.getBalance() + ".");
    System.out.println( "press 1 to return to the bank menu if you would like to make another trasaction, otherwise press 2 to log out return to the main menu");
    int options4 = sc.nextInt();
    switch (options4) {
      case 1:
        runMenu();
        break;
      case 2:
        mainMenu();
        break;
      default:
        System.out.println("you have picked an invalid option. exiting application now");
        System.exit(0);
    }

  }
  
  
  public static void transferBetweenAccounts() {
    System.out.println("Please enter the amout you would like to transfer");
    double num = sc.nextDouble();
    if (num > 0) {
    if (num > accnt.getBalance()) {
      System.out.println("You dont have enough money. Please enter a lower amount to transfer");
      transferBetweenAccounts();
    }else {
      System.out.println("Please enter the username that you would like to transfer into");
      String user_name = sc.next();
      
      if(as.transfer(accnt.getId(),user_name, num)) {    //balance after transfer is done
        System.out.println("Transfer successful! Your balance is:" + accnt.getBalance());
        System.out.println("You will be returned to the Bank Menu");
        runMenu();
      }
      else {
        System.out.println("That username is not valid. Please enter in a valid username.");
        transferBetweenAccounts();
      }
      
    }
      
    }
    
  
  else {
    System.out.println("please enter a valid amount");
    transferBetweenAccounts();
    
  }

}
  
  
  
  
  
  
}
