package mitty.analysis;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import mitty.asset.Assets;
import mitty.db.StorageManager;

//testing storage...
public class TestStockHistoryEntry {

	static String dbPath = "/tmp/testenv";

	@BeforeClass
	static public void setup() throws IOException {
		 Assets.reset();
		FileUtils.deleteDirectory(new File(dbPath));
		StorageManager.instance().close();
		StorageManager.setPath(dbPath);
		StorageManager.instance();
		// Assets.instance().getMoneyMarket().deposit(100000);

	}
/*
	@Test
	public void testStockEntry() {

		StockHistoryEntry gld = new StockHistoryEntry();
		gld.setSymbol("GLD");
		gld.getCloses().put(1l, 1.0);
		gld.getCloses().put(2l, 2.0);

		gld.store();

		StorageManager.instance().close();
		// Now i need to close and reopen.

		// Trying various options here.
		// getting from DB without closing.
		StockHistoryEntry pe = StockHistoryEntry.findByPK("GLD");

		assertTrue("stockentry is null", pe != null);

		System.out.println("Current values" + pe);

		assertTrue("stockentry has closequotes", pe.getCloses().get(2l) == 2.0);

		gld.getCloses().put(3l, 3.0);
		gld.store();

		StorageManager.instance().close();

		pe = StockHistoryEntry.findByPK("GLD");

		assertTrue("stockentry is null", pe != null);

		System.out.println("Current values" + pe);

		assertTrue("stockentry has closequotes", pe.getCloses().get(3l) == 3.0);

	}
	
	*/

	@AfterClass
	static public void teardown() {
		try {
			FileUtils.deleteDirectory(new File(dbPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
