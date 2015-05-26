package stocksimulator;

import java.util.Random;
import static stocksimulator.StockSimulator.stocks;

/**
 *
 * AUTHOR: Bradley Winter
 * 
 * Simulates an actual stock market includes; stock price change and active traders
 * 
 */
public class Market extends Thread {
 
    
    @Override
    public void run() {
        while (true) {
            int stockIndex = randInt(0, stocks.length - 1);
            Stock currentStock = stocks[stockIndex]; // selects stock at random
            double currentPrice = currentStock.getPrice();
            double max = currentPrice + (currentPrice * 0.002);
            double min = currentPrice - (currentPrice * 0.002);
            // set random stock price in between 0.2% either way
            currentStock.setPrice(randDouble(min, max));
               
            try {
                Thread.sleep(10000); // sleeps for ten seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }   
    
    /*
     * returns a random integer
     */
    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    
    /*
     * returns a random double
     */
    public static double randDouble(double min, double max) {
        Random r = new Random();
        double randomValue = min + (max - min) * r.nextDouble();
        return randomValue;
    }

}  
