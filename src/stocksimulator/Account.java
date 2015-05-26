package stocksimulator;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Account implements Serializable
{
  private final String accountName;
  private double accountValue, cash;
  private ArrayList<Stock> heldStocks = new ArrayList<>();
  DecimalFormat df = new DecimalFormat("#.00"); 


  /*
   * constructor to set up users account, one time only.
   */
  public Account(String accountName, double cash)
  {
    this.accountName = accountName;
    this.cash = cash;
  }

  /*
   * returns the users name
   */
  public String getName()
  {
    return accountName;
  }

  /*
   * returns the users cash
   */
  public double getCash()
  {
    return cash;
  }
  
  /*
   * updates the users cash
   */
  public void updateCash(double cash, String trade)
  {
      switch (trade) {
          case "buy":
              this.cash -= cash;
              break;
          case "sell":
              this.cash += cash;
              break;
          default:
              System.out.println("Error, trade required.");
              break;
      }
  }
  
  /*
   * adds stock to the users account
   */
  public void addStock(Stock s, String type)
  {
      // check to see if stock has already been bought
      int index = -1;
      for(int i = 0; i < heldStocks.size(); i++) {
          Stock stock = heldStocks.get(i);
          if(stock.getSymbol().equals(s.getSymbol()) && (stock.getType().equals(s.getType()))) {
              index = i;
          }
      }
      if(index == -1) {
          heldStocks.add(s);      
      } else {
          Stock stock = heldStocks.get(index);
          // calculate quantity
          int quantity = stock.getQuantity() + s.getQuantity();
          stock.setQuantity(quantity);
      }
  }
  
  /*
   * removes stock from the users account
   */
  public void deleteStock(int index)
  {
      heldStocks.remove(index);
  }  
  
  /*
   * displays portfolio to user
   */
  
  public void displayPortfolio()
  {
      System.out.println(accountName + "'s Portfolio");
      System.out.println("---------------------------");
      accountValue = getAccountValue();
      System.out.println("\n Account Value: \u00A3" + df.format(accountValue) + "      Cash: \u00A3" + df.format(cash) + "\n");
      
      int numOfShorts = 0;
      
      // print the stocks the user holds
      for (Stock stock : heldStocks) {
          if(!stock.getType().equals("short")) {
              System.out.println("Stock Symbol: " + stock.getSymbol() + "\n" + "Description: " + stock.getDescription() + "\n" + "Price: \u00A3" + df.format(stock.getPrice()) + "\n" + "Quantity: " + (int)(stock.getQuantity()) + "\n" + "Total: \u00A3" + df.format(stock.getPrice()*stock.getQuantity()) + "\n");
          } else {
              numOfShorts++;
          }
      }
      
      // print the shorting stocks the user holds
      if(!(numOfShorts == 0)) {
          System.out.println("Shorting Stocks");
          System.out.println("-----------------");
          for (Stock stock : heldStocks) {
              if(stock.getType().equals("short")) {
                  System.out.println("Stock Symbol: " + stock.getSymbol() + "\n" + "Description: " + stock.getDescription() + "\n" + "Price: \u00A3" + df.format(stock.getPrice()) + "\n" + "Quantity: " + (int)(stock.getQuantity()) + "\n" + "Total: \u00A3" + df.format(stock.getPrice()*stock.getQuantity()) + "\n");
              } 
          } 
      }
      
  }
  
  /*
   * reduce the users stock
   */
  public void reduceStock(int index, int quantity)
  {
      Stock stock = heldStocks.get(index);
      // set new quantity of users stock
      stock.setQuantity((stock.getQuantity()-quantity));
  }  
  
  /* 
   * retrieve account value
   */
  public double getAccountValue() {
      
      double totalValue = 0;
      
      for(Stock s : heldStocks) {
          
          totalValue += (s.getPrice() * s.getQuantity());
      }
      
      return totalValue + cash;
  }
  
  /*
   * finds if the symbol is in the stocks array returns -1 if not
   */
  public int find(String symbol, Stock[] stocks, String type)
  {
    for(int i = 0; i<stocks.length; i++)
    {
      if((stocks[i].getSymbol().equals(symbol)) && !(stocks[i].getType().equals(type)))
      {
	return i;
      }  // end if
    } // end for loop

    return -1;

  } // end find method
}
