package stocksimulator;

import java.io.Serializable;

public class Stock implements Serializable
{
  private final String symbol, description;
  private final int totalShares;
  private int sharesLeft, quantity;
  private String type;
  private double currentPrice, dayChange, yearHigh, yearLow;

  /*
   * constructor sets new stock
   */    
  public Stock(String symbol, String description, double currentPrice, int totalShares)
  {
    this.symbol = symbol;
    this.description = description;
    this.currentPrice = currentPrice;
    this.totalShares = totalShares;
    sharesLeft = totalShares;
    type = "stock";
  }
  
  /*
   * getters and setters
   */  
  public void setQuantity(int quantity)
  {
      this.quantity = quantity;
  }
  
  public int getQuantity()
  {
      return quantity;
  }

  public double getPrice()
  {
    return currentPrice;
  }
  
  public void setPrice(double currentPrice)
  {
      this.currentPrice = currentPrice;
  }

  public String getSymbol()
  {
    return symbol;
  }

  public String getDescription()
  {
    return description;
  }
  
  public void setType(String type) {
      this.type = type;
  }
  
  public String getType() {
      return type;
  }
  
  /*
   * get how many shares are left in the open market to trade
   */  
  public int getSharesLeft()
  {
      return sharesLeft;
  }
  
  /*
   * update shares left when buying or buying to cover
   */
  public void updateSharesLeft(int quantity)
  {
          sharesLeft -= quantity;
  }
  
  /*
   * update shares left when selling or selling short
   */
  public void updateSharesLeft(int quantity, String transaction) {
      
      sharesLeft += quantity;

  }

  /*
   * get the total shares available
   */    
  public int getTotalShares()
  {
      return totalShares;
  }

}
