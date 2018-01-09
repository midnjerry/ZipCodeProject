package jerry.balderas.zipcode.parser;

public class Parser {

	/**
	 * Converts serialized input into an integer array that represents a list of
	 * boundary points.
	 * 
	 * @param input
	 *            - Serialized Input in format "[min1,max1] [min2,max2]
	 *            [min3,max3] ..."
	 * @return - int array that represent boundary end points. For above example
	 *         returns {min1,max1,min2,max2,min3,max3,...}
	 */
	public int[] deSerializeRanges(String input) {
		if (isNullOrEmpty(input)) {
			return new int[0];
		}
		String[] ranges = removeSpaces(input);
		return convertToIntegerBoundaryPoints(ranges);
	}

	/**
	 * Parses the integers for each array and returns all of them as an integer
	 * array
	 * 
	 * @param ranges
	 *            - array of ranges in String format "[min1,max1]"
	 * @return integer array that represents all of the boundary points
	 *         associated with ranges
	 */
	private int[] convertToIntegerBoundaryPoints(String[] ranges) {
		int[] rangeBoundaryPoints = new int[ranges.length * 2];
		for (int i = 0; i < ranges.length; i++) {
			int[] minMax = parseRange(ranges[i]);
			rangeBoundaryPoints[i * 2] = minMax[0];
			rangeBoundaryPoints[i * 2 + 1] = minMax[1];
		}
		return rangeBoundaryPoints;
	}

	/**
	 * Returns integer pair for a range in String Format
	 * 
	 * @param range
	 *            - String in format: "[num1,num2]"
	 * @return int array: {num1, num2}
	 */
	private int[] parseRange(String range) {
		validateRangeNotNull(range);
		String trimmedInput = removeBrackets(range);
		String[] minMaxPair = removeCommaDelimiter(trimmedInput);
		return parseIntegers(minMaxPair);
	}

	/**
	 * Checks if String is null or empty
	 * 
	 * @param input
	 *            - String to Test
	 * @return true if String is null or empty, otherwise false
	 */
	private boolean isNullOrEmpty(String input) {
		return (input == null || input.length() == 0);
	}

	/**
	 * Validates if range in String format is null or empty and returns
	 * ZipCodeException if true.
	 * 
	 * @param range
	 *            - String to Test
	 * @throws ZipCodeException
	 *             - if range is null or empty.
	 */
	private void validateRangeNotNull(String range) {
		if (range == null || range.length() == 0) {
			throw new ZipCodeException(String.format("Parsing error: You must delimit ranges with only one space"));
		}
	}

	/**
	 * Removes space delimiter from String and returns all substrings as an
	 * array.
	 * 
	 * @param input
	 *            - String to be delimited
	 * @return String Array of all substrings of input delimited by " ".
	 */
	private String[] removeSpaces(String input) {
		return input.split(" ");
	}

	/**
	 * Parses a String Array of numerical text into an int array.
	 * 
	 * @param zipcodes
	 *            - String Array of purely numerical text to parse
	 * @return int array of all parsed numbers
	 */
	private int[] parseIntegers(String[] zipcodes) {
		int[] endPoints = new int[zipcodes.length];
		for (int i = 0; i < zipcodes.length; i++)
			endPoints[i] = parseInteger(zipcodes[i]);
		return endPoints;
	}

	/**
	 * Validates that input is a five-digit numerical integer and then returns
	 * the numerical representation of that value as an int.
	 * 
	 * @param fiveDigitInput
	 *            - a String containing the int representation to be parsed.
	 * @return the integer value represented by the argument.
	 * @throws ZipCodeException
	 *             - if the string does not contain a parsable integer or is not
	 *             of length 5.
	 */
	private int parseInteger(String fiveDigitInput) {
		if (fiveDigitInput.length() != 5) {
			throw new ZipCodeException("Parsing Error: Must be 5-digit integer, text with error: " + fiveDigitInput);
		}

		try {
			return Integer.parseInt(fiveDigitInput);
		} catch (NumberFormatException e) {
			throw new ZipCodeException("Parsing Error: Must be 5-digit integer, text with error: " + fiveDigitInput);
		}

	}

	/**
	 * Removes comma delimiter from input and returns substrings as a String
	 * array.
	 * 
	 * @param range
	 *            - String to be delimited
	 * @return String Array of all substrings of input delimited by ",".
	 */
	private String[] removeCommaDelimiter(String range) {
		String[] zipcodes = range.split(",");
		if (zipcodes.length != 2) {
			throw new ZipCodeException(
					"Parsing Error: You must delimit ranges with spaces and numbers with commas.\nFormat: \"[<number>,<number>] [<number>,<number>]\", text with error: "
							+ range);
		}
		return zipcodes;
	}

	/**
	 * Trims the outer brackets of a string
	 * 
	 * @param range
	 *            - string to modify, should be in format: "[num1,num2]"
	 * @return substring of input string within brackets.
	 * @throws ZipCodeException
	 *             - when open bracket and closing bracket are not detected.
	 * 
	 */
	private String removeBrackets(String range) {
		if (range.charAt(0) != '[') {
			throw new ZipCodeException("Parsing Error: Range must begin with '[', text with error: " + range);
		}
		if (range.charAt(range.length() - 1) != ']') {
			throw new ZipCodeException("Parsing Error: Range must end with ']', text with error: " + range);
		}
		return range.substring(1, range.length() - 1);
	}
}
