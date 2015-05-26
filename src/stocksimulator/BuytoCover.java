

package stocksimulator;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * AUTHOR: Bradley Winter
 * 
 * Allows the user to buy back the stock they sold short as to get off margin
 * 
 */
public class BuytoCover extends Trade{
    
    DecimalFormat df = new DecimalFormat("#.00"); 
 
    public BuytoCover(Account ac)
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
                    if(hasEnoughCash(totalPrice))
                    {
                        System.out.println("The total comes to: \u00A3" + df.format(totalPrice));
                        // set how many user has of the current stock
                        stockTrade.setQuantity(quantity);     
                        // set stock type to cover
                        stockTrade.setType("cover"); 
                        
                        if(quantity == stockTrade.getQuantity()) {
                            ac.deleteStock(stockIndex);
                        } else {
                            ac.reduceStock(stockIndex, quantity);
                        }
                        stockTrade.updateSharesLeft(quantity); // update the amount of shares left in a stock
                        ac.updateCash(totalPrice, "buy"); // update users cash
                    } else
                    {
                        System.out.println("Sorry, not enough cash.");
                    }
                } else
                {
                    System.out.println("Sorry not enough shares available.");
                }
            }
                        
        } catch(Exception ex)
        {
            System.out.println("Error trade not gone through");
        }
      
    } // end buy constuctor
    
  /* overrides the shares Available in the trade class
   * allows program to see if there are enough shares already shorted to cover.
   */
    @Override
  public boolean sharesAvailable(Stock s, int quantity) {
      
      if(s.getType().equals("short")) {
          return (quantity <= s.getSharesLeft()) && (quantity > 0);
      } else {
          return false;
      }
  }    

}
