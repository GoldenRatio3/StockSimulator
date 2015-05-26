package stocksimulator;

// for i/o
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Scanner; // for user input
import java.text.DecimalFormat; // for price formatting
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockSimulator
{
  private Scanner user_input = new Scanner(System.in);
  DecimalFormat df = new DecimalFormat("#.00"); 
  static ArrayList<Account> accounts = new ArrayList<>();

  Account ac;
  
  // declaring an array of stocks, loads from file
  static Stock[] stocks;  
  
  public StockSimulator()
  {
      try {
          System.out.print("Enter an Account Name: ");
          String accountName = user_input.next();
          System.out.print("Enter Starting Money: \u00A3");
          int startingMoney = user_input.nextInt();
          ac = new Account(accountName, startingMoney);
          accounts.add(ac);
              
          while(true)
          {
              showMenu(ac);
          }
      } catch(java.util.InputMismatchException ex) {
            System.out.println("Sorry you did not enter your starting money correctly. Please try again.");
            StockSimulator.main(null);
        }
    
  }
  
  public StockSimulator(Account ac)
  {
      this.ac = ac;
      
      while(true)
      {
          showMenu(ac);
      }
  }
  
  public static void main(String[] args)
  {
    Scanner user_input = new Scanner(System.in);

    System.out.println("Starting Stock Simulator");
    System.out.println("------------------------");
    
    // loads the current stocks into array from file
    readStocks();
    // loads the current acounts into list array from file
    readAccounts();
    
    // checks to see if the user has an account
    System.out.println("Do you have an account: <y/n> ");
    String answer = user_input.nextLine();
    
    if(answer.equals("y") || answer.equals("Y"))
    {
        System.out.println("Enter your account name: ");
        String accountName = user_input.nextLine();
        int accountIndex = searchAccounts(accountName);
        
        if(accountIndex == -1)
        {
            System.out.println("Sorry, there is no account by that name. Please set up an account");
            StockSimulator ss = new StockSimulator();   
        } else
        {
            StockSimulator ss = new StockSimulator(accounts.get(accountIndex));
        }
    } else
    {
        StockSimulator ss = new StockSimulator();
    }
  } // end main method
  
  public void showMenu(Account ac)
  {
      // starts market
      Market thread = new Market();
      thread.start();
      
      // menu printed to console
      System.out.println("\nSelect an option: \n" + " 1. Portfolio\n" + " 2. Search Stock\n" + " 3. Trade stock\n" + " 4. Exit\n");
      int selection;
      // makes sure user enters in int
      try {
          selection = user_input.nextInt();
      } catch (InputMismatchException ex) {
          selection = 5;
      }
      user_input.nextLine(); // stops input skipping
      
      switch(selection)
      {
          case 1:
              // displays the users portfolio
              ac.displayPortfolio();
              break;
          case 2:
              // search stock case
              searchStock(ac);
              break;
          case 3:
              // Trade stock case
              decideTransaction(ac);
              break;
          case 4:
              // exit case
              exit();
              break;
          default:
              System.out.println("Invalid selection. Try Again.");
              break;
      } 

  } // end showMenu method
   
  public void decideTransaction(Account ac)
  {
            // menu printed to console
      System.out.println("\nSelect an option: \n" + " 1. Buy\n" + " 2. Sell\n" + " 3. Sell Short\n" + " 4. Buy to Cover\n" + " 5. Back\n");
      int selection;
      // makes sure user enters in int
      try {
          selection = user_input.nextInt(); 
      } catch (InputMismatchException ex) {
          selection = 5;
      }
      user_input.nextLine(); // stops input skipping
      
      switch(selection) {
          case 1:
              new Buy(ac);
              break;
          case 2:
              new Sell(ac);
              break;
          case 3:
              new SellShort(ac);
              break;
          case 4:
              new BuytoCover(ac);
              break;
          default:
              System.out.println("Invalid Selection. Go back to main menu.");
      {
          try {
              Thread.sleep(500);
          } catch (InterruptedException ex) {
              Logger.getLogger(StockSimulator.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
              break;
      }
  }
  public void searchStock(Account ac)
  {
      System.out.println("Enter stock name: ");   
      String stockSymbol = user_input.nextLine();
      
      // search stock name
      int position = ac.find(stockSymbol, stocks, "");
      // display stock and its characteristics
      if(position == -1)
      {
          System.out.println("Sorry that stock is not trading");
      } else
      {
          // display stock symbol, description and current price
          System.out.println(stocks[position].getSymbol() + ", " + stocks[position].getDescription() + " trading at: \u00A3" + df.format(stocks[position].getPrice()));
      } 
     
  }
  
  public static int searchAccounts(String accountName)
  {
      int index = -1;
      for(int i  = 0; i < accounts.size(); i++) {
          Account ac = accounts.get(i);
          if(accountName.equals(ac.getName())) {
              index = i;
          } 
      }
      return index;
  }
  
  /*
   * Save stocks to file
   */  
  public void saveStocks() {
      try {
          ObjectOutputStream objectOutputStream = new ObjectOutputStream( new FileOutputStream("stocks.ser"));
          objectOutputStream.writeObject(stocks);
          objectOutputStream.close();
          
      } catch (FileNotFoundException ex) {
          System.out.println("File not found.");
      } catch (IOException ex) {
          System.out.println("Error, file not saved.");
      }  
  }
  
  /*
   * Read stocks from file
   */  
  public static void readStocks() {
      try {
          ObjectInputStream objectInputStream = new ObjectInputStream( new FileInputStream("stocks.ser"));
          Stock[] stocks1 = (Stock[]) objectInputStream.readObject();
          StockSimulator.stocks = stocks1;
      } catch (IOException ex) {
          System.out.println("Error, file not read from");
      } catch (ClassNotFoundException ex) {
          System.out.println("Error, class not found");
      }
  }
  
   /*
   * Save accounts to file
   */  
  public void saveAccounts() {
      try {
          ObjectOutputStream objectOutputStream = new ObjectOutputStream( new FileOutputStream("accounts.ser"));
          objectOutputStream.writeObject(accounts);
          objectOutputStream.close();
          
      } catch (FileNotFoundException ex) {
          System.out.println("File not found.");
      } catch (IOException ex) {
          System.out.println("Error, accounts file not saved.");
      }  
  }
  
  /*
   * Read accounts from file
   */  
  public static void readAccounts() {
      try {
          ObjectInputStream objectInputStream = new ObjectInputStream( new FileInputStream("accounts.ser"));
          ArrayList<Account> accounts1 = (ArrayList<Account>) objectInputStream.readObject();
          StockSimulator.accounts = accounts1;
      } catch (IOException ex) {
          System.out.println("Error, file not read from");
      } catch (ClassNotFoundException ex) {
          System.out.println("Error, class not found");
      }
  }
  
  public void exit()
  {
      System.out.println("Exiting...");
      // save stocks to file
      saveStocks();
      saveAccounts();
      System.exit(0);
  } // end exit method

} // end class
