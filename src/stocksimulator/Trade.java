package stocksimulator;

public class Trade
{
  String symbol;
  int quantity;
  final Account ac;
  
 /*
  * contructor trade to set account doing the trade
  */
  public Trade(Account ac)
  {
      this.ac = ac;
  }
  
 /*
  * returns true if there are shares available to be bought otherwise
  * returns false
  */
  public boolean sharesAvailable(Stock s, int quantity)
  {
      return (quantity <= s.getSharesLeft()) && (quantity > 0);
  }
  
 /*
  * returns true if the user has enough cash otherwise, 
  * returns false.
  */  
  public boolean hasEnoughCash(double total)
  {
      return total <= ac.getCash();
  }
}
