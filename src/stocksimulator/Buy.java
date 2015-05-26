

package stocksimulator;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * AUTHOR: Bradley Winter
 * 
 * Allows the user to buy stocks a basic feature needed for trading
 */
public class Buy extends Trade{
    
    // formats the input to 2 d.p.
    DecimalFormat df = new DecimalFormat("#.00"); 

    public Buy(Account ac)
    {
        // calls the trade constructor to set start of trade
        super(ac);
        
        try
        {
            //validation on stock symbol
            Scanner user_input = new Scanner(System.in);
            
            //get user input for stock
            System.out.println("Enter stock symbol you would like to trade: ");
            symbol = user_input.nextLine();
            
            //check to make sure stock exists
            int stockIndex = ac.find(symbol, StockSimulator.stocks, "");
              
            // display to user
            if(stockIndex == -1)
            {
                System.out.println("Sorry that stock is not trading.");
                
            } else
            {
                // get how many shares the user would like to trade
                Stock stockTrade = StockSimulator.stocks[stockIndex];
                
                System.out.println("How many shares would you like to trade: ");
                quantity = user_input.nextInt();
                
                // validation on quantity
                if(sharesAvailable(stockTrade ,quantity))
                {
                    double totalPrice = stockTrade.getPrice() * quantity;
                    // make sure user has enough cash
                    if(hasEnoughCash(totalPrice))
                    {
                        System.out.println("The total comes to: \u00A3" + df.format(totalPrice));
                        // set how many user has of the current stock
                        stockTrade.setQuantity(quantity);
                        stockTrade.updateSharesLeft(quantity); // update the amount of shares left in the market for that stock
                        // set type of stock to buy
                        stockTrade.setType("buy");
                        ac.addStock(stockTrade, "buy"); // add stock to users account
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

}
