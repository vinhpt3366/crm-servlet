package crm_app07.utils;

import java.text.DecimalFormat;

public class NumberUtil {
	public static boolean isPositiveInteger(String input) {
		if (input == null) {
			return false;
		}

		try {
			int number = Integer.parseInt(input);
			return number > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static String calculatePercentage(int part, int total) {
		if (total == 0 || part == 0) {
			return "0.0";
		}

		double percentage = ((double) part / total) * 100;
		DecimalFormat df = new DecimalFormat("#.0");
		return df.format(percentage);
	}

}
