package jerry.balderas.zipcode.parser;

import java.util.ArrayList;

import jerry.balderas.zipcode.ZipCodeTracker;

public class Parser {

	public Integer[] parseZipCodeRanges(String input) {
		validateInputNotNull(input);
		ArrayList<Integer> resultList = new ArrayList<Integer>();
		String[] ranges = input.split(" ");
		for (String range : ranges) {
			resultList.addAll(parseRange(range));
		}
		return resultList.toArray(new Integer[resultList.size()]);
	}

	public void validateInputNotNull(String input) {
		if (input == null || input.length() == 0) {
			throw new ParserException(
					"Usage: java -jar ZipCode.jar [<5-digit zipcode>,<5-digit zipcode>] [<5-digit zipcode>,<5-digit zipcode>] ...");
		}
	}

	private ArrayList<Integer> parseRange(String range) {
		String trimmedInput = removeBrackets(range);
		String[] minMaxPair = removeCommaDelimiter(trimmedInput);
		return parseIntegers(minMaxPair);
	}

	private ArrayList<Integer> parseIntegers(String[] zipcodes) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int start = parseInteger(zipcodes[0]);
		int end = parseInteger(zipcodes[1]);
		if (start > end) {
			throw new ParserException(String
					.format("Parsing error: [%d,%d] - first value cannot be larger than second value", start, end));
		}
		result.add(start);
		result.add(end);
		return result;
	}

	private int parseInteger(String fiveDigitInput) {
		int result = -1;
		String errorMessage = String.format(
				"Parsing Error: Must be 5-digit integer from %05d to %05d, text with error: %s",
				ZipCodeTracker.MIN_ZIPCODE, ZipCodeTracker.MAX_ZIPCODE, fiveDigitInput);
		try {
			result = Integer.parseInt(fiveDigitInput);
			if (result < ZipCodeTracker.MIN_ZIPCODE || result > ZipCodeTracker.MAX_ZIPCODE
					|| fiveDigitInput.length() != 5) {
				throw new ParserException(errorMessage);
			}
		} catch (NumberFormatException e) {
			throw new ParserException(errorMessage);
		}
		return result;
	}

	private String[] removeCommaDelimiter(String range) {
		String[] zipcodes = range.split(",");
		if (zipcodes.length != 2) {
			throw new ParserException(
					"Parsing Error: You must delimit ranges with spaces and numbers with commas.\nFormat: \"[<number>,<number>] [<number>,<number>]\", text with error: "
							+ range);
		}
		return zipcodes;
	}

	private String removeBrackets(String range) {
		if (range.charAt(0) != '[') {
			throw new ParserException("Parsing Error: Range must begin with '[', text with error: " + range);
		}
		if (range.charAt(range.length() - 1) != ']') {
			throw new ParserException("Parsing Error: Range must end with ']', text with error: " + range);
		}
		return range.substring(1, range.length() - 1);
	}
}
