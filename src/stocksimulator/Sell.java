

package stocksimulator;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * AUTHOR: Bradley Winter
 * 
 * Allows the user to sell stocks a basic feature needed for trading
 */
public class Sell extends Trade{
    
    DecimalFormat df = new DecimalFormat("#.00");     
    
    public Sell(Account ac)
    {
        super(ac);
        
        try
        {
            //validation on stock symbol
            Scanner user_input = new Scanner(System.in);
            
            System.out.println("Enter stock symbol you would like to trade: ");
            symbol = user_input.nextLine();
            
            int stockIndex = ac.find(symbol, StockSimulator.stocks, "");
                    
            if(stockIndex == -1)
            {
                System.out.println("Sorry that stock is not trading.");
                
            } else
            {
                Stock stockTrade = StockSimulator.stocks[stockIndex];
                
                System.out.println("How many shares would you like to trade: ");
                quantity = user_input.nextInt();
                
                // validation on quantity
                if(sharesAvailable(stockTrade ,quantity))
                {
                    double totalPrice = stockTrade.getPrice() * quantity;
                    System.out.println("The total comes to: \u00A3" + df.format(totalPrice));
                    // set stock type to sell
                    stockTrade.setType("sell");
                    stockTrade.updateSharesLeft(quantity, "sell"); // update the amount of shares left in a stock 
                    // check to see if user is selling all their shares
                    if(quantity == stockTrade.getQuantity()) {
                      ac.deleteStock(stockIndex); // remove stock from users account
                    } else {
                        ac.reduceStock(stockIndex, quantity);
                    }
                    ac.updateCash(totalPrice, "sell"); // update users cash                        
                    
                } else
                {
                    System.out.println("Sorry you don't have that many shares.");
                }
            }
                        
        } catch(Exception ex)
        {
            System.out.println("Error trade not gone through");
        }
      
    } // end sell constuctor
  
  /* overrides the shares Available in the trade class
   * allows program to see if there are enough shares to sell.
   */
    @Override
  public boolean sharesAvailable(Stock s, int quantity) {
      
      if(s.getType().equals("buy")) {
          return (quantity + s.getSharesLeft()) <= s.getTotalShares();
      } else {
          return false;
      }
  }

}
