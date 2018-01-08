package jerry.balderas.zipcode.parser;

import java.util.ArrayList;

public class Parser {

	public Integer[] parseZipCodeRanges(String input) {
		validateInputNotNull(input);
		ArrayList<Integer> resultList = new ArrayList<Integer>();
		String[] ranges = removeSpaces(input);
		for (String range : ranges) {
			validateRangeNotNull(range, input);
			resultList.addAll(parseRange(range));
		}
		return resultList.toArray(new Integer[resultList.size()]);
	}

	public void validateInputNotNull(String input) {
		if (input == null || input.length() == 0) {
			throw new ZipCodeException(
					"Usage: java -jar ZipCode.jar [<5-digit zipcode>,<5-digit zipcode>] [<5-digit zipcode>,<5-digit zipcode>] ...");
		}
	}

	private void validateRangeNotNull(String range, String input) {
		if (range == null || range.length() == 0) {
			throw new ZipCodeException(String
					.format("Parsing error: You must delimit ranges with only one space, text with error: %s", input));
		}
	}

	private String[] removeSpaces(String input) {
		return input.split(" ");
	}

	private ArrayList<Integer> parseRange(String range) {
		String trimmedInput = removeBrackets(range);
		String[] minMaxPair = removeCommaDelimiter(trimmedInput);
		return parseIntegers(minMaxPair);
	}

	private ArrayList<Integer> parseIntegers(String[] zipcodes) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		result.add(parseInteger(zipcodes[0]));
		result.add(parseInteger(zipcodes[1]));
		return result;
	}

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

	private String[] removeCommaDelimiter(String range) {
		String[] zipcodes = range.split(",");
		if (zipcodes.length != 2) {
			throw new ZipCodeException(
					"Parsing Error: You must delimit ranges with spaces and numbers with commas.\nFormat: \"[<number>,<number>] [<number>,<number>]\", text with error: "
							+ range);
		}
		return zipcodes;
	}

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
