package mitty.util;

import java.text.DecimalFormat;

// for output formatting

public  class Out {
	public static DecimalFormat df = new DecimalFormat();
	static{
	df.setMaximumFractionDigits(2);
	}

}
