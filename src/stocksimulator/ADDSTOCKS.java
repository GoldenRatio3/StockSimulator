

package stocksimulator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * AUTHOR: Bradley Winter
 */
public class ADDSTOCKS {
    
    static Stock[] stocks
  = {new Stock("APPL","Apple Inc, designs, manufactures and markets mobile communication and media devices, personal computers and portable digital music players worldwide.",122.02, 100000000), new Stock("MSFT","Microsoft Corp, develops, licenses, markets and supports software, services and devices worldwide.",42.60, 200000000),
  new Stock("FB","Facebook, online social networking service",81.67, 100000000),
  new Stock("TWTR","Twitter, online social networking service, that a user sends and reads short messages",50.47, 100000000),
  new Stock("PG","Procter and Gamble co, is an American multinational consumer goods company",82.83, 100000000),
  new Stock("JD","JD, is engaged in the sale of electronics products and general merchandise products",29.22, 100000000),
  new Stock("GOOG","Google, is an American multinational corporation specialising in Internet-related services",542.56, 100000000),
  new Stock("WMT","Walmart, is a retail corporation that operates a chain of discount stores",80.71, 100000000),
  new Stock("TSCO","Tesco, a british multinational grocery and general merchandise retailer",242.13, 100000000),
  new Stock("VA","Virgin America, a United States based airline",30.36, 100000000),
  new Stock("BT","Bt Group, is a British multinational telecommunications service",65.65, 100000000),
  new Stock("HSBA","HSBC, a British multinational banking and financial services company",581.08, 100000000)
  };
  
    
      /*
   * Save stocks to file
   */  
  public static void saveStocks() {
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
  
  public static void main(String[] args) {
      //readStocks();
      //saveStocks();
  }

}
