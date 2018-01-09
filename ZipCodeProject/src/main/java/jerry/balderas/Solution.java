package jerry.balderas;

import java.util.Arrays;

import jerry.balderas.zipcode.ZipCodeTracker;
import jerry.balderas.zipcode.parser.ZipCodeException;

public class Solution {

	/**
	 * ZipCodeProject - an application that accepts 5-digit zip code ranges in
	 * format [<number1>,<number2>] and consolidates all zip code ranges so that
	 * a simplified string is returned.
	 */
	public static void main(String[] args) {
		try {
			String zipCodeInput = concatenateToOneString(args);
			String result = consolidateRanges(zipCodeInput);
			System.out.println(result);
		} catch (ZipCodeException validationError) {
			System.out.println(validationError.getMessage());
		}
	}

	/**
	 * 
	 * Concatenates String Array into one string delimited by a " ".
	 *
	 * @param args
	 *            - parameters
	 * @return String that concatenates all args components.
	 */
	private static String concatenateToOneString(String[] args) {
		if (args == null || args.length == 0) {
			throw new ZipCodeException(
					"Usage: java -jar ZipCodeProject.jar [<5-digit zipcode>,<5-digit zipcode>] [<5-digit zipcode>,<5-digit zipcode>] ...");
		}
		return String.join(" ", Arrays.asList(args));
	}

	/**
	 * 
	 * Takes serialized zipcode range input, registers the zip code ranges, and
	 * returns simplified serialized string
	 *
	 * @param input
	 *            - serialized Input
	 * @return Simplified string of all zip code ranges contained in input
	 *         String.
	 */
	public static String consolidateRanges(String input) {
		ZipCodeTracker tracker = new ZipCodeTracker();
		tracker.markZipCodes(input);
		return tracker.serializeZipCodeRanges();
	}

}
