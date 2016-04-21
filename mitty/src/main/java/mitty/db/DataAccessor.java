package mitty.db;


import com.sleepycat.je.DatabaseException;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;

import mitty.asset.MoneyMarket;
import mitty.asset.PortfolioEntry;
import mitty.asset.StatergyEntry;
                            
public class DataAccessor {
    // Open the indices
    public DataAccessor(EntityStore store)
        throws DatabaseException {

    	
    	//Portfolio.
        // Primary key for PortfolioEntry class
        portfolioEntryBySymbol = store.getPrimaryIndex(
            String.class, PortfolioEntry.class);
   

        // Secondary key for Inventory classes
        // Last field in the getSecondaryIndex() method must be
        // the name of a class member; in this case, an Inventory.class
        // data member.
       /* portfolioEntryByAccountID = store.getSecondaryIndex(
        		portfolioEntryBySymbol, String.class, "accountID");*/


    	//Portfolio.
        // Primary key for PortfolioEntry class
        moneyMarketByaccountID = store.getPrimaryIndex(
            String.class, MoneyMarket.class);
        
        statergyEntryBySymbol = store.getPrimaryIndex(
                String.class, StatergyEntry.class);
        
       // statergyEntryByAccountID = store.getSecondaryIndex(
        //		statergyEntryBySymbol, String.class, "accountID");


    }



    // PortfolioEntry Accessors
    public PrimaryIndex<String,PortfolioEntry> portfolioEntryBySymbol;
    public SecondaryIndex<String,String,PortfolioEntry> portfolioEntryByAccountID;
    public PrimaryIndex<String,MoneyMarket> moneyMarketByaccountID;

    public PrimaryIndex<String,StatergyEntry> statergyEntryBySymbol;
   // public SecondaryIndex<String,String,StatergyEntry> statergyEntryByAccountID;

} 
