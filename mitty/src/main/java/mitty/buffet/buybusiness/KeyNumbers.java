package mitty.buffetvalue.business;

/**
 * Created by i839511 on 5/21/17.
 */
public class KeyNumbers {

    //INCOME STATEMENTS ITEMS

    /**
     * return on investment ratio... PErcentage gives
     * the interest percent per year.
     *
     */
    static double roe(double earningPerShare , double bookValuePerShare){
        return earningPerShare/bookValuePerShare;
    }



    /**
     * return on investment ratio... PErcentage gives
     * the interest percent per year.
     *
     */
    static double roi(double earningPerShare , double marketPrice){
        return earningPerShare/marketPrice;
    }


    static double interestPercentage(double earningPerShare , double marketPrice){
        return roi(earningPerShare,marketPrice)*100;
    }

    /**
     * Amount of dollars you pay for 1 dollar return.
     *
     * Every X(PE) Dollar i invest, i get 1 dollar a year back.
     *
     * @param earningPerShare
     * @param marketPrice
     * @return
     */
    static double priceByEarning(double earningPerShare, double marketPrice){
        return  marketPrice/earningPerShare;
    }



    static double interestPercentage(double priceByEarnings)
    {
        return (1/priceByEarnings) * 100;
    }

    /// BALACE SHEET ITEMS



    static double InvestmentSafetyPercentage(double bookValuePerShare , double marketPrice){
        return marginOfSafety(bookValuePerShare,marketPrice)*100;
    }

    /**
     * Amount of dollars i pay for 1 dollar of equity.
     * Every (P/BV) dollars i invest, i get it backed by 1 dollar of equity.
     * @param bookValue
     * @param marketPrice
     * @return
     */
    static double priceByBookValue(double bookValue,double marketPrice){
        return marketPrice/bookValue;
    }

    /**
     * Is the percentage of safetry for buying the business
     * f0r the money invested.
     * How mucgh percent of the money will be returned if
     * the business closes today. liquidating all assets.
     *
     * @param bookValuePerShare
     * @param marketPRice
     * @return
     */
    static double marginOfSafety(double bookValuePerShare , double marketPRice){

        //BookValue = Equity
        //EbookValue = Equity/ Share outstanding.
        //Equity = Total Liablity - total asset.
        //
        return bookValuePerShare/marketPRice;


    }



}
