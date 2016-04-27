package mitty.market;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import mitty.analysis.StockHistoryEntry;
import mitty.asset.Assets;
import mitty.db.StorageManager;

//testing storage...
public class TestStockQuote {


	
	@Test
	public void testStockQuote() {

		assertTrue("Stock quote for gld"+ StockQuote.getQuote("GLD") ,StockQuote.getQuote("GLD")>0)  ;

	}

	
}
