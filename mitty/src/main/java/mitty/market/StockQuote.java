package mitty.market;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.Vector;

import mitty.util.In;

/**
 * An instance is a stock quote for a given ticker symbol at the time of
 * creation. The static variable record maintains a list of all requested
 * quotes. Requires In.java to compile; download and place it in the same
 * directory. Download site: http://www.cs.princeton.edu/introcs/stdlib/In.java
 * Demo idea due to Sedgewick and Wayne
 */
public class StockQuote {

	
																			// of
																			// all
																			// requested
																			// quotes


	
	public static double getQuote(String s) {
		
			//return getQuoteYahoo(s);
			
		return getQuoteNasdaq(s);
		}
	
	/**
	 * Print current price of stock with ticker symbol s, and add the new quote
	 * to the record. Print format: <symbol> @ <date>: $<price> Precondition: s
	 * is a valid ticker symbol (case doesn't matter): examples: "goog", "GOOG"
	 * "MsFT"
	 * 
	 * @throws MalformedURLException
	 */
	public static double getQuoteYahoo(String s) {

		double price = 0.0;

		// find URL for data
		String url = "http://finance.yahoo.com/q?s=" + s;

		// treat the webpage as a String so we can process it
		String targetText = new In(url).readAll();

		// System.out.println(targetText);
		// pull out the substring corresponding to the price
		targetText = targetText.substring(targetText.indexOf("time_rtq_ticker"));
		targetText = targetText.substring(0, targetText.indexOf("</span>"));
		targetText = targetText.substring(targetText.lastIndexOf(">") + 1);

		// convert String in targetText to a double price
		price = Double.parseDouble(targetText);

		// record the new quote (by creating it) and print the info out
		// System.out.println(new StockQuote(s, new Date(), price));
		return price;
	}
	
	
	/**
	 * Print current price of stock with ticker symbol s, and add the new quote
	 * to the record. Print format: <symbol> @ <date>: $<price> Precondition: s
	 * is a valid ticker symbol (case doesn't matter): examples: "goog", "GOOG"
	 * "MsFT"
	 * 
	 * @throws MalformedURLException
	 */
	public static double getQuoteNasdaq(String s) {

		double price = 0.0;

		// find URL for data
		String url = "http://www.nasdaq.com/symbol/" + s;

		// treat the webpage as a String so we can process it
		String targetText = new In(url).readAll();

		// System.out.println(targetText);
		// pull out the substring corresponding to the price
		targetText = targetText.substring(targetText.indexOf("<span class=\"last-sale\">"));
		targetText = targetText.substring(0, targetText.indexOf("</span>"));
		targetText = targetText.substring(targetText.lastIndexOf("$") + 1);

		
		targetText = targetText.trim();
		//System.out.println(targetText);
		//targetText = targetText.substring(1);
		//System.out.println(targetText);
		
		//targetText = targetText.substring(targetText.lastIndexOf(">") + 1);

		// convert String in targetText to a double price
		price = Double.parseDouble(targetText);

		// record the new quote (by creating it) and print the info out
		// System.out.println(new StockQuote(s, new Date(), price));
		return price;
	}


	public static void main(String argv[]) {
		test();
	}

	/** just for private debugging purposes */
	public static void test() {
		// tests of getQuote
		System.out.println("getting a google quote:"+getQuote("goog"));
		System.out.println("getting a MSFT quote: "+getQuote("msft"));
		System.out.println("getting an apple quote: "+getQuote("aapl"));
		

	}

}
