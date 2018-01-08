package jerry.balderas;

import java.util.Arrays;

import jerry.balderas.zipcode.ZipCodeTracker;
import jerry.balderas.zipcode.parser.ZipCodeException;

/*
 * Hello!
 * 
 * Here is my submitted code.  I've studied many of "Uncle Bob"'s lectures, and I'm a fan
 * of code acting as your documentation.  I think the code should speak for itself by using
 * descriptive language for variables and function names.  That being said, if you find this
 * documentation inadequate I'm open to changing my style to better suit the company's needs.       
 */

public class Solution {

	public static void main(String[] args) {
		try {
			String zipCodeInput = concatenateToOneString(args);
			String result = consolidateRanges(zipCodeInput);
			System.out.println(result);
		} catch (ZipCodeException validationError) {
			System.out.println(validationError.getMessage());
		}
	}

	private static String concatenateToOneString(String[] args) {
		if (args == null || args.length == 0) {
			throw new ZipCodeException(
					"Usage: java -jar ZipCodeProject.jar [<5-digit zipcode>,<5-digit zipcode>] [<5-digit zipcode>,<5-digit zipcode>] ...");
		}

		return String.join(" ",Arrays.asList(args));
	}
		

	public static String consolidateRanges(String input) {
		ZipCodeTracker tracker = new ZipCodeTracker();
		tracker.markZipCodes(input);
		return tracker.serializeZipCodeRanges();
	}

}
