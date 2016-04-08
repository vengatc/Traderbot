package mitty.util;

import java.text.DecimalFormat;

// for output formatting

public class Out {
	public static DecimalFormat df = new DecimalFormat();
	static {
		df.setMaximumFractionDigits(2);
	}

	public static String decimal(double d) {

		if (d == 0)
			return "0";
		return df.format(d);
	}

}
