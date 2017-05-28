package mitty.buffet.buybusiness;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by i839511 on 5/21/17.
 */
public class Business implements Comparable<Business> {


    String ticker;
    double marketPrice;
    double bookValue; //book Value per share.
    double pe; //pricebyEarnings
    double debtToEquity;
    double fiveYearBookValueGrowth;
    double tenYearFedRate;
    double divident;
    double tenBookValueGrowth;
    double currentRatio; //Money comming in / money going out per 12 months. totalCurrentAssets ( likable to convert to cash) / total current liablity (going to pay out in next 12 months)
    double eps;

    double roe; //return on equity.
    double returnOnCurrentPrice = -999;

    public Business(String ticker) {
        this.ticker = ticker;
    }

    static double roe(double earningPerShare, double bookValuePerShare) {
        return earningPerShare / bookValuePerShare;
    }

    /**
     * return on investment ratio... PErcentage gives
     * the interest percent per year.
     */
    static double roi(double earningPerShare, double marketPrice) {
        return earningPerShare / marketPrice;
    }

    static double interestPercentage(double earningPerShare, double marketPrice) {
        return roi(earningPerShare, marketPrice) * 100;
    }

    /**
     * Amount of dollars you pay for 1 dollar return.
     * <p>
     * Every X(PE) Dollar i invest, i get 1 dollar a year back.
     *
     * @param earningPerShare
     * @param marketPrice
     * @return
     */
    static double priceByEarning(double earningPerShare, double marketPrice) {
        return marketPrice / earningPerShare;
    }

    static void sleep(int sleep) {
        try {
            Thread.sleep(sleep);
        } catch (Exception e) {
        }
    }

    public static void main(String arg[]) {

        String[] portfolio = {"SAP", "BRK.B", "WMT", "TGT", "WFC", "AAPL", "BAC", "GS", "AMAT", "MSFT", "GOOG", "NVDA", "COST"};
        List<Business> bs = Arrays.stream(portfolio).map(BusinessFactory.me).filter(bus -> bus.pe != 0).collect(Collectors.toList());//.forEach(

        Collections.sort(bs, Collections.reverseOrder());
        System.out.println("Date :" + new Date().toString());
        bs.stream().forEach(b -> b.printValuation());

    }

    public double getEps() {
        return eps;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public double getRoe() {
        return roe;
    }

    public void setRoe(double roe) {
        this.roe = roe;
    }

    public void setTenYearFedRate(double tenYearFedRate) {
        this.tenYearFedRate = tenYearFedRate;
    }

    public void setFiveYearBookValueGrowth(double fiveYearFedRate) {
        fiveYearBookValueGrowth = fiveYearFedRate;
    }

    public void setDivident(double divident) {
        this.divident = divident;
    }

    public void setTenBookValueGrowth(double tenBookValueGrowth) {
        this.tenBookValueGrowth = tenBookValueGrowth;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * Risk associated with owning  Investment.
     *
     * 1. Excessive Debt owned by company(only when tide goes out you will see who is swiming naked)
     * 2. Overpaying for an investment (Price is what you pay , value is what you get)
     * 3. Not knowing what you are doing.
     *
     * //Compare value with federal note.
     *
     * S&P rating anything with A.
     * Municipal can be B.
     * FED interest curve
     *
     * @param arg
     */


    /***
     * Four Rules for INVESTING
     *
     * 1) Managed by viginalat leader (look aout for possible danger
     *    (DEBT) => DEBT/EQUIT < 0.5 by waren.
     * 2) Long term prospect
     * 3) Stock must by stable and understandable.
     * 4) Stock must be undervalued.
     *

     */

    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }

    public void setPe(double pe) {
        this.pe = pe;
    }

    /**
     * Buffet Rules.
     * 1. Stock must be stable and understandable.
     * 2. A stock must have long term prospect.
     * 3. Much be managed by vigilent leders.
     * 4. Stock must be undervalued. ( Low PE <15 and Price/Book (Safety net) <1.5
     * <p>
     * <p>
     * Buy in such that stock market can close, and not open for 5  years.
     * <p>
     * Price is what yo8u pay, value is what you get.
     */


    double quickValuation() {
        double valueation = pe * priceByBookValue();
        return valueation;
    }


    /*public double interinsicValueCalculator(){

    }*/

    boolean quickValuationTool() {

        return quickValuation() < 22.5;
    }

    double investmentSafetyPercentage() {
        return InvestmentSafetyPercentage();
    }

    /**
     * Rule 1.Vigialt leadership
     *
     * @return
     */
    boolean vigilantLeaderShip() {
        return (getDebtToEquity() < 0.5 && getCurrentRatio() > 1.5);
    }

    /**
     * 1) Managed by viginalat leader (look aout for possible danger
     * (DEBT) => DEBT/EQUIT < 0.5 by waren likes it.
     *
     * @return
     */
    public double getDebtToEquity() {
        return debtToEquity;
    }




    /// BALACE SHEET ITEMS

    public void setDebtToEquity(double debtToEquity) {
        this.debtToEquity = debtToEquity;
    }

    /**
     * 1) Managed by viginalat leader
     * Current Ratio > 1.5 by waren likes it.
     * <p>
     * How company will handle debt in next 12 months.
     * Money in in 12 months and Money to pay out in next 12 months.
     * <p>
     * Money comming in / money going out per 12 months.
     * totalCurrentAssets ( likable to convert to cash)
     * / total current liablity (going to pay out in next 12 months)
     *
     * @return
     */
    public double getCurrentRatio() {
        return debtToEquity;
    }

    public void setCurrentRatio(double currentRatio) {
        this.currentRatio = currentRatio;
    }

    double intrinicValue() {
        return intrinicValue(tenYearFedRate, tenBookValueGrowth);
    }

    //INCOME STATEMENTS ITEMS

    double intrinic5YearBookValue() {
        return intrinicValue(tenYearFedRate, fiveYearBookValueGrowth);
    }

    /**
     * Purchase price of the stock to get Fed interest rate.
     *
     * @return
     */
    double intrinicValue(double tenYearRate, double bookValueGrowth) {
        double currentdivident = divident;
        double gain = bookValueGrowth;
        double currentBookValue = bookValue;

        // coupon=Number(document.calculator.coupon.value);
        // par=Number(document.calculator.par.value);
        double year = 10; // number of years we are comparing agains.

        double TenYearFedRater = tenYearRate;

        double perc = (1 + gain / 100);
        double base = Math.pow(perc, year);
        double parr = currentBookValue * base;
        TenYearFedRater = TenYearFedRater / 100;

        double extra = Math.pow((1 + TenYearFedRater), year);

        double c = currentdivident * (1 - (1 / extra)) / TenYearFedRater + parr / extra;
        return c;
    }

    /**
     * Is the percentage of safetry for buying the business
     * f0r the money invested.
     * How mucgh percent of the money will be returned if
     * the business closes today. liquidating all assets.
     *
     * @return
     */
    double marginOfSafety() {

        //BookValue = Equity
        //EbookValue = Equity/ Share outstanding.
        //Equity = Total Liablity - total asset.
        //
        return bookValue / marketPrice;


    }

    /**
     * Amount of dollars i pay for 1 dollar of equity.
     * Every (P/BV) dollars i invest, i get it backed by 1 dollar of equity.
     *
     * @return
     */
    double priceByBookValue() {
        return marketPrice / bookValue;
    }

    double InvestmentSafetyPercentage() {
        return marginOfSafety() * 100;
    }

    double interestPercentage() {
        return (1 / pe) * 100;
    }

    /**
     * Perentage (interest) return per year , based on current market price.
     *
     * @return
     */
    double percentageReturnOnCurrentPrice() {
        if (returnOnCurrentPrice == -999) {
            double i = 0.01;
            double r = 0.5;
            double iVal = Math.floor(intrinicValue(r, tenBookValueGrowth));
            double mPrice = Math.floor(marketPrice);
            while (iVal != mPrice) {
                if (iVal > mPrice) r = r + i;
                if (iVal < mPrice) r = r - i;
                iVal = Math.floor(intrinicValue(r, tenBookValueGrowth));
                //System.out.println(iVal+":"+r);
            }
            returnOnCurrentPrice = r;
        }
        return returnOnCurrentPrice;
    }

    @Override
    public int compareTo(Business o) {
        return (int) (percentageReturnOnCurrentPrice() - o.percentageReturnOnCurrentPrice());

    }

    void compute() {

        quickValuation();
        quickValuationTool();
        investmentSafetyPercentage();
        interestPercentage();
        vigilantLeaderShip();
        intrinicValue();
        intrinic5YearBookValue();
        percentageReturnOnCurrentPrice();

    }

    void printValuation() {
        System.out.println("______________________" + getTicker() + "______________________");
        System.out.printf("Price (%.2f) P/E(%.2f) BookValue(%.2f) Debt2Equity(%.2f) CurrentRatio(%.2f) TenYearGrowthBV(%.2f) FiveYearGrowthBV(%.2f) Divident(%.2f)  FedRate(%.2f) ROE(%.2f) \n", marketPrice, pe, bookValue, debtToEquity, currentRatio, tenBookValueGrowth, fiveYearBookValueGrowth, divident, tenYearFedRate, roe);

        System.out.printf("valuation(<22.5) =  %.2f \n", quickValuation());
        System.out.println(getTicker() + "passing quick valuation = " + quickValuationTool());
        System.out.printf(getTicker() + " Investment safety percentage = %.2f\n", investmentSafetyPercentage());
        System.out.printf(getTicker() + " Interest percentage = %.2f \n", interestPercentage());
        System.out.printf(getTicker() + " Vigilant leadership check = " + vigilantLeaderShip() + "\n");
        System.out.printf(getTicker() + " Intrinic Value (10 Year book value avg)  of Stock= %.2f \n", intrinicValue());
        System.out.printf(getTicker() + " Intrinic Value (5 Year book value avg)  of Stock= %.2f \n", intrinic5YearBookValue());

        System.out.printf(getTicker() + " Interest rate if bought on current market price = %.2f \n", percentageReturnOnCurrentPrice());

    }


    public String getBusinessValue() {

        StringBuffer out = new StringBuffer();
        out.append(String.format("______________________" + getTicker() + "______________________"));
        out.append("<br>");
        out.append(String.format("Price (%.2f) P/E(%.2f) BookValue(%.2f) Debt2Equity(%.2f) CurrentRatio(%.2f) TenYearGrowthBV(%.2f) FiveYearGrowthBV(%.2f) Divident(%.2f)  FedRate(%.2f) ROE(%.2f) \n", marketPrice, pe, bookValue, debtToEquity, currentRatio, tenBookValueGrowth, fiveYearBookValueGrowth, divident, tenYearFedRate, roe));
        out.append("<br>");
        out.append(String.format("valuation(<22.5) =  %.2f \n", quickValuation()));
        out.append("<br>");
        out.append(String.format(getTicker() + "passing quick valuation = " + quickValuationTool()));
        out.append("<br>");
        out.append(String.format(getTicker() + " Investment safety percentage = %.2f\n", investmentSafetyPercentage()));
        out.append("<br>");
        out.append(String.format(getTicker() + " Interest percentage = %.2f \n", interestPercentage()));
        out.append("<br>");
        out.append(String.format(getTicker() + " Vigilant leadership check = " + vigilantLeaderShip() + "\n"));
        out.append("<br>");
        out.append(String.format(getTicker() + " Intrinic Value (10 Year book value avg)  of Stock= %.2f \n", intrinicValue()));
        out.append("<br>");
        out.append(String.format(getTicker() + " Intrinic Value (5 Year book value avg)  of Stock= %.2f \n", intrinic5YearBookValue()));
        out.append("<br>");
        out.append(String.format(getTicker() + " Interest rate if bought on current market price = %.2f \n", percentageReturnOnCurrentPrice()));

        return out.toString();
    }

    public static String  getWatchlistValue() {

        StringBuffer out = new StringBuffer();

        String[] portfolio = {"SAP", "BRK.B", "WMT", "TGT", "WFC", "AAPL", "BAC", "GS", "AMAT", "MSFT", "GOOG", "NVDA", "COST"};
        List<Business> bs = Arrays.stream(portfolio).map(BusinessFactory.me).filter(bus -> bus.pe != 0).collect(Collectors.toList());//.forEach(

        Collections.sort(bs, Collections.reverseOrder());
        System.out.println("Date :" + new Date().toString());
        bs.stream().forEach(b -> out.append(b.getBusinessValue()).append("<br>"));
        return out.toString();

    }
}
