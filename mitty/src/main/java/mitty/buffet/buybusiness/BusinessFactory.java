package mitty.buffet.buybusiness;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.function.Function;

/**
 * Created by i839511 on 5/21/17.
 * Fetch and create business.
 */
public class BusinessFactory implements Function<String, Business> {

    static double  TenYearFedRate=get10YearFedRate();

    public static BusinessFactory me = new BusinessFactory();

    static double getMarketPrice(String ticker) {
        try {
            String url = "http://www.msn.com/en-us/money/stockdetails?symbol=$$";
            url = url.replace("$$", ticker);

            Document doc = Jsoup.connect(url).get();

            Elements newsHeadlines = doc.getElementsByClass("currentval");
            return Double.parseDouble(newsHeadlines.text());
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }

    }

    static double getPE(String ticker) {
        //  <li class="today-trading-tile">
        //<span class="name"><p class='truncated-string'  title='P/E Ratio (EPS)'>P/E Ratio (EPS)</p></span>
        // <span class="value baseminus"><p class='truncated-string'  title='11.48 (18.76) '>11.48 (18.76) </p></span>
        // </li>

        try {
            String url = "http://www.msn.com/en-us/money/stockdetails?symbol=$$";
            url = url.replace("$$", ticker);

            Document doc = Jsoup.connect(url).get();


            Elements p = doc.select("p.truncated-string");

            boolean found = false;
            String peString = "";
            for (Element n : p) {
                if (found) {
                    peString = n.text();
                    break;
                }
                if (n.text().contains("P/E Ratio (EPS)")) {
                    found = true;
                }
            }

            return Double.parseDouble(peString.split(" ")[0]);
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }

    }

    static double getDebtToEquityRatio(String ticker) {
        //  <li class="today-trading-tile">
        //<span class="name"><p class='truncated-string'  title='P/E Ratio (EPS)'>P/E Ratio (EPS)</p></span>
        // <span class="value baseminus"><p class='truncated-string'  title='11.48 (18.76) '>11.48 (18.76) </p></span>
        // </li>

        try {
            String url = "http://www.msn.com/en-us/money/stockdetails/analysis?symbol=$$";
            url = url.replace("$$", ticker);

            Document doc = Jsoup.connect(url).get();


            Elements p = doc.select("p.truncated-string");

            boolean found = false;
            String peString = "";
            for (Element n : p) {
                if (found) {
                    peString = n.text();
                    break;
                }
                if (n.text().contains("Debt/Equity Ratio")) {
                    found = true;
                }
            }

            return Double.parseDouble(peString);
        } catch (Exception e) {
           // e.printStackTrace();
            return 0;
        }

    }


    static double getRoe(String ticker) {
        //  <li class="today-trading-tile">
        //<span class="name"><p class='truncated-string'  title='P/E Ratio (EPS)'>P/E Ratio (EPS)</p></span>
        // <span class="value baseminus"><p class='truncated-string'  title='11.48 (18.76) '>11.48 (18.76) </p></span>
        // </li>

        try {
            String url = "http://www.msn.com/en-us/money/stockdetails/analysis?symbol=$$";
            url = url.replace("$$", ticker);

            Document doc = Jsoup.connect(url).get();


            Elements p = doc.select("p.truncated-string");

            boolean found = false;
            String peString = "";
            for (Element n : p) {
                if (found) {
                    peString = n.text();
                    break;
                }
                if (n.text().contains("Return on Equity %")) {
                    found = true;
                }
            }

            return Double.parseDouble(peString);
        } catch (Exception e) {
            // e.printStackTrace();
            return 0;
        }

    }


    static double getCurrentRatio(String ticker) {
        //  <li class="today-trading-tile">
        //<span class="name"><p class='truncated-string'  title='P/E Ratio (EPS)'>P/E Ratio (EPS)</p></span>
        // <span class="value baseminus"><p class='truncated-string'  title='11.48 (18.76) '>11.48 (18.76) </p></span>
        // </li>

        try {
            String url = "http://www.msn.com/en-us/money/stockdetails/analysis?symbol=$$";
            url = url.replace("$$", ticker);

            Document doc = Jsoup.connect(url).get();


            Elements p = doc.select("p.truncated-string");

            boolean found = false;
            String peString = "";
            for (Element n : p) {
                if (found) {
                    peString = n.text();
                    break;
                }
                if (n.text().contains("Current Ratio")) {
                    found = true;
                }
            }

            return Double.parseDouble(peString);
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }

    }
    static double getBookValue(String ticker) {
        //<li style="width: 60%; " class="left-align  heading" data-role="">
        //<p class='truncated-string'  title='Book Value/Share'>Book Value/Share</p>
        // </li>
        //<li style="width: 40%; " class="right-align  value" data-role="">
        //<p class='truncated-string'  title='192.35'>192.35</p>
        try {

           // String url = "http://www.msn.com/en-us/money/stockdetails/analysis/fi-126.1.$$.NYS";
             String url = "http://www.msn.com/en-us/money/stockdetails/analysis?symbol=$$";

            url = url.replace("$$", ticker);

            Document doc = Jsoup.connect(url).get();

            Elements p = doc.select("p.truncated-string");

            boolean found = false;
            String peString = "";
            for (Element n : p) {
                if (found) {
                    peString = n.text();
                    break;
                }
                if (n.text().contains("Book Value/Share")) {
                    found = true;
                }
            }

            return Double.parseDouble(peString);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }


    static double getCurrentDivident(String ticker) {
        //<li style="width: 60%; " class="left-align  heading" data-role="">
        //<p class='truncated-string'  title='Book Value/Share'>Book Value/Share</p>
        // </li>
        //<li style="width: 40%; " class="right-align  value" data-role="">
        //<p class='truncated-string'  title='192.35'>192.35</p>
        try {

            String url = "http://www.msn.com/en-us/money/stockdetails/fi-126.1.$$.NYS?symbol=$$";
            url = url.replace("$$", ticker);

            Document doc = Jsoup.connect(url).get();

            Elements p = doc.select("p.truncated-string");

            boolean found = false;
            String peString = "";
            for (Element n : p) {
                if (found) {
                    peString = n.text();
                    break;
                }
                if (n.text().contains("Dividend Rate (Yield)")) {
                    found = true;
                }
            }

            return Double.parseDouble(peString.split(" ")[0]);
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }

    }
    static double getAverage10YearBookValueGrowthPercentage(String ticker) {
           //<tr>
             //       <td><a href='/term/Book Value Per Share/NYSE:GS/Book%2BValue%2BPer%2BShare/Goldman%2BSachs%2BGroup%2BInc' class='nav' target='_blank'>Book Value Growth (%)</a></td>
              //      <td>8.10&nbsp;</td>
               //     <td>6.70&nbsp;</td>
                //    <td>6.20&nbsp;</td>
        try {

            String url = "https://www.gurufocus.com/financials/$$";
            url = url.replace("$$", ticker);

            Document doc = Jsoup.connect(url).get();

            Element table = doc.select("table[id=R1]").get(0);

            Elements rows = table.select("tr");

            for(Element row : rows){
                Elements cols = row.select("td");

                if(cols.size()>0 && cols.get(0).select("a").text().contains("Book Value Growth")){
                    String tenyarstr= cols.get(1).text().replace("\u00a0", "");
                   // System.out.println(tenyarstr.trim() + "HHHHHH");
                    return Double.parseDouble(tenyarstr.trim());

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
       return 0;
    }

    static double getAverage5YearBookValueGrowthPercentage(String ticker) {
        //<tr>
        //       <td><a href='/term/Book Value Per Share/NYSE:GS/Book%2BValue%2BPer%2BShare/Goldman%2BSachs%2BGroup%2BInc' class='nav' target='_blank'>Book Value Growth (%)</a></td>
        //      <td>8.10&nbsp;</td>
        //     <td>6.70&nbsp;</td>
        //    <td>6.20&nbsp;</td>
        try {

            String url = "https://www.gurufocus.com/financials/$$";
            url = url.replace("$$", ticker);

            Document doc = Jsoup.connect(url).get();

            Element table = doc.select("table[id=R1]").get(0);

            Elements rows = table.select("tr");

            for(Element row : rows){
                Elements cols = row.select("td");

                if(cols.size()>0 && cols.get(0).select("a").text().contains("Book Value Growth")){
                    String fiveyarstr= cols.get(2).text().replace("\u00a0", "");
                    // System.out.println(tenyarstr.trim() + "HHHHHH");
                    return Double.parseDouble(fiveyarstr.trim());

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    static double get10YearFedRate() {
      // <table class="t-chart" xmlns:fmt="urn:treasury-xslt-fmt" xmlns:msxml="urn:schemas-microsoft-com:xslt" xmlns:d="http://schemas.microsoft.com/ado/2007/08/dataservices" xmlns:m="http://schemas.microsoft.com/ado/2007/08/dataservices/metadata" xmlns:a="http://www.w3.org/2005/Atom">
        // <tr><th scope="col">Date</th><th scope="col">1 mo</th><th scope="col">3 mo</th><th scope="col">6 mo</th><th scope="col">1 yr</th><th scope="col">2 yr</th><th scope="col">3 yr</th><th scope="col">5 yr</th><th scope="col">7 yr</th><th scope="col">10 yr</th><th scope="col">20 yr</th><th scope="col">30 yr</th></tr>
        // <tr class="oddrow">
        // <td scope="row" class="text_view_data">05/01/17</td>
        // <td class="text_view_data">0.67</td>
        // <td class="text_view_data">0.83</td>
        // <td class="text_view_data">0.98</td>
        // <td class="text_view_data">1.09</td>
        // <td class="text_view_data">1.28</td>
        // <td class="text_view_data">1.48</td>
        // <td class="text_view_data">1.84</td>
        // <td class="text_view_data">2.13</td>
        // <td class="text_view_data">2.33</td>
        // <td class="text_view_data">2.71</td><td class="text_view_data">3.00</td></tr><tr class="evenrow"><td scope="row" class="text_view_data">05/02/17</td><td class="text_view_data">0.72</td><td class="text_view_data">0.82</td><td class="text_view_data">0.99</td><td class="text_view_data">1.08</td><td class="text_view_data">1.27</td><td class="text_view_data">1.45</td><td class="text_view_data">1.81</td><td class="text_view_data">2.09</td><td class="text_view_data">2.29</td><td class="text_view_data">2.68</td><td class="text_view_data">2.97</td></tr>
        try {

            String url = "https://www.treasury.gov/resource-center/data-chart-center/interest-rates/Pages/TextView.aspx?data=yield";

            Document doc = Jsoup.connect(url).get();

            Element table = doc.select("table[class=t-chart]").get(0);

            Elements rows = table.select("tr");

            for(Element row : rows){
                Elements cols = row.select("td");

                if(cols.size()>0 ){
                    String tenyarstr= cols.get(9).text();
                    // System.out.println(tenyarstr.trim() + "HHHHHH");
                    return Double.parseDouble(tenyarstr.trim());

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }
    public static void main(String[] args) throws Exception {
        //String url = "http://www.msn.com/en-us/money/stockdetails/fi-126.1.$$.NYS?symbol=$$";
        //url = url.replace("$$", "GS");
        //Document doc = Jsoup.connect(url).get();

        //double bookVAlueGrowth = getAverage10YearBookValueGrowthPercentage("GS");
       // double divident =  get10YearFedRate();
                // getCurrentDivident("GS");


       // System.out.println(bookVAlueGrowth);

        System.out.println(getRoe("SAP"));

    }

    public Business apply(String ticker) {
        double bookVAlueGrowth = getAverage10YearBookValueGrowthPercentage(ticker);
        double marketPrice = getMarketPrice(ticker);
        double pe = getPE(ticker);
        double bookv = getBookValue(ticker);
        double debtToEquity = getDebtToEquityRatio(ticker);
        double currentRatio = getCurrentRatio(ticker);
        double TenYearBookValue = getAverage10YearBookValueGrowthPercentage(ticker);
        double FiveYearBookValue = getAverage5YearBookValueGrowthPercentage(ticker);
        double divident = getCurrentDivident(ticker);
        double roe = getRoe(ticker);

        Business b = new Business(ticker);
        b.setMarketPrice(marketPrice);
        b.setPe(pe);
        b.setBookValue(bookv);
        b.setDebtToEquity(debtToEquity);
        b.setCurrentRatio(currentRatio);
        b.setTenBookValueGrowth(TenYearBookValue);
        b.setDivident(divident);
        b.setTenYearFedRate(TenYearFedRate);
        b.setFiveYearBookValueGrowth(FiveYearBookValue);
        b.setRoe(roe);
        return b;
    }
}
