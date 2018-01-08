package jerry.balderas;

import jerry.balderas.zipcode.ZipCodeTracker;
import jerry.balderas.zipcode.parser.ParserException;

/*
 * Hello!
 * 
 * Here is my submitted code.  I've studied many of "Uncle Bob"'s lectures, and I'm a fan
 * of code acting as your documentation.  I think the code should speak for itself by using
 * descriptive language for variables and function names.    
 */

public class Solution {

	// Expected Input Format: "[min_int,max_int] [min_int2,max_int2] ...
	// [min_int_n,max_int_n]"
	// Ex: [94133,94133] [94200,94299] [94600,94699]
	public static void main(String[] args) {
		try {
			String input = createZipCodeRangeInputString(args);
			System.out.println(getUnionOfZipCodeRanges(input));
		} catch (ParserException parserError) {
			System.out.println(parserError.getMessage());
		}
	}

	private static String createZipCodeRangeInputString(String[] args) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			builder.append(args[i] + " ");
		}
		return builder.toString().trim();
	}

	public static String getUnionOfZipCodeRanges(String input) {
		ZipCodeTracker tracker = new ZipCodeTracker();
		tracker.markZipCodes(input);
		return tracker.serializeZipCodeRanges();
	}

}
