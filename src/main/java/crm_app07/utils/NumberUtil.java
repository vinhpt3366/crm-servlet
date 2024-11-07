package crm_app07.utils;

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

}
