

package stocksimulator;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * AUTHOR: Bradley Winter
 * 
 * Allows the user to trade stocks worth more than their total account value
 * this is called trading on margin and is an advance feature for this simulator
 * 
 */
public class SellShort extends Trade{
  
    DecimalFormat df = new DecimalFormat("#.00"); 

    public SellShort(Account ac)
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
                    // set how many user has of the current stock
                    stockTrade.setQuantity(quantity);
                    // set stock type to short
                    stockTrade.setType("short");                    
                    stockTrade.updateSharesLeft(quantity, "sell"); // update the amount of shares left in a stock 
                    ac.addStock(stockTrade, "short"); // add stock to users account
                    ac.updateCash(totalPrice, "sell"); // update users cash
                } else
                {
                    System.out.println("Sorry not enough shares available.");
                }
            }
                        
        } catch(Exception ex)
        {
            System.out.println("Error trade not gone through");
        }
      
    } // end sell short constuctor
  
  /* overrides the shares Available in the trade class
   * allows program to see if there are enough shares to sell short.
   */
    @Override
  public boolean sharesAvailable(Stock s, int quantity) {
      
      return (quantity + s.getSharesLeft()) <= s.getTotalShares();
  }

}
