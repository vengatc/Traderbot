package mitty.market;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import mitty.analysis.StockHistoryEntry;
import mitty.util.In;

public class StockHistory {

	// CSV file header
	private static final String[] FILE_HEADER_MAPPING = { "Date", "Open", "High", "Low", "Close", "Volume",
			"Adj Close" };

	// Student attributes
	private static final String Date = "Date";
	private static final String CLOSE = "Close";
	private static final String VOLUME = "Volume";

	private static StockHistoryEntry readCsvFile( String symbol,String csvdata) {

		StringReader csvStringReader = null;

		CSVParser csvParser = null;

		// Create the CSVFormat object with the header mapping
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

		StockHistoryEntry stockEntry = new StockHistoryEntry();
		stockEntry.setSymbol(symbol);

		try {


			// initialize FileReader object
			csvStringReader = new StringReader(csvdata);

			// initialize CSVParser object
			csvParser = new CSVParser(csvStringReader, csvFileFormat);

			// Get a list of CSV file records
			List<CSVRecord> csvRecords = csvParser.getRecords();
            long timestampLastRead=0;
			// Read the CSV file records starting from the second record to skip
			// the header
			for (int i = 1; i < csvRecords.size(); i++) {
				CSVRecord record = csvRecords.get(i);
				// Create a new student object and fill his data
				timestampLastRead = dateStringToLong(record.get(Date));
				stockEntry.getCloses().put(dateStringToLong(record.get(Date)), Double.valueOf(record.get(CLOSE)));
				stockEntry.getVolumes().put(dateStringToLong(record.get(Date)), Double.valueOf(record.get(VOLUME)));
			}
			
			stockEntry.setSnapsnotTime(timestampLastRead);

		} catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
		} finally {
			try {
				csvStringReader.close();
				csvParser.close();
			} catch (IOException e) {
				System.out.println("Error while closing csvFileParser !!!");
				e.printStackTrace();
			}
		}
		return stockEntry;

	}
	
	private static String getHistoryCSV(String symbol,Calendar today) {


		//http://real-chart.finance.yahoo.com/table.csv?s=YHOO&a=03&b=12&c=1996&d=03&e=22&f=2016&g=d&ignore=.csv
		
			
	    String url = "http://real-chart.finance.yahoo.com/table.csv?s="+symbol;
	    url+="&a=03&b=12&c=1996";
	   
	    url+="&d="+ today.get(Calendar.MONTH);
	    url+="&e="+today.get(Calendar.DATE);
	    url+="&f="+today.get(Calendar.YEAR);

	    url+="&d=03&e=22&f=2016";
	    url+="&g=d&ignore=.csv";

		// treat the webpage as a String so we can process it
		String csv = new In(url).readAll();

		
		return csv;
	}
	
	static private Long dateStringToLong(String datestr) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		long mills = 0;
		Date date;
		try {
			date = (Date) formatter.parse(datestr);
			mills = date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mills;
	}
	
	
	public static StockHistoryEntry getStockHistoryEntry(String symbol)
	{
		Calendar  calendar = Calendar.getInstance();
		String reportcsv= getHistoryCSV(symbol,calendar);
		return readCsvFile(symbol,reportcsv);
	}

}