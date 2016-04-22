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
public class TestStockHistory {

	static String dbPath = "/tmp/testenv";

	
	@Test
	public void testStockEntry() {

		StockHistoryEntry gld= StockHistory.getStockHistoryEntry("GLD");

	
		assertTrue("stockentry is null", gld != null);

		System.out.println("Current values" + gld);

		assertTrue("stockentry has closequotes", gld.getCloses().keySet().size() >1);
		assertTrue("stockentry has closequotes", gld.getVolumes().values().size() >1);



	}

	
}
