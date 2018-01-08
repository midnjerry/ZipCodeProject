package jerry.balderas;

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
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			builder.append(args[i] + " ");
		}
		return builder.toString().trim();
	}

	public static String consolidateRanges(String input) {
		ZipCodeTracker tracker = new ZipCodeTracker();
		tracker.markZipCodes(input);
		return tracker.serializeZipCodeRanges();
	}

}
