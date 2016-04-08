package mitty.util;

import java.text.DecimalFormat;

// for output formatting

public class Out {
	static DecimalFormat df = new DecimalFormat();
	static {
		df.setMaximumFractionDigits(2);
	}

	public static String decimal(double d) {
		return df.format(d);
	}

}
