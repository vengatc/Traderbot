package mitty.asset;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import mitty.db.StorageManager;

//testing storage...
public class TestPortfolio {

	static String dbPath = "/tmp/testenv";

	@BeforeClass
	static public void setup() {
		StorageManager.setPath(dbPath);
		StorageManager.instance();
		Assets.instance().getMoneyMarket().deposit(100000);

	}

	@Test
	public void testBuyAndSell() {
		// create
		Assets.instance().getPortfolio().buy("GLD", 100);
		// update.
		Assets.instance().getPortfolio().buy("GLD", 2);

		StorageManager.instance().close();
		System.out.println(Assets.instance().getPortfolio());
		// Now i need to close and reopen.

		// Trying various options here.
		// getting from DB without closing.
		PortfolioEntry pe = PortfolioEntry.findByPK("GLD");
		assertTrue("Pe is null", pe != null);
		assertTrue("pe.number" + pe.getNumber(), pe.getNumber() == 102);
		Assets.instance().getPortfolio().sellAll("GLD");
		
		System.out.println(Assets.instance().getPortfolio());


		pe = PortfolioEntry.findByPK("GLD");

		assertTrue("pe.number" + pe.getNumber(), pe.getNumber() == 0);
		Assets.instance().getPortfolio().buy("GLD", 1);
		pe = PortfolioEntry.findByPK("GLD");

		assertTrue("pe.avgcost " + pe.getCost(), pe.getCost() > 0);
		
		

	}

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
