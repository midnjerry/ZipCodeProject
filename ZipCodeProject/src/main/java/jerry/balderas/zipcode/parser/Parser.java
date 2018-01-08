package jerry.balderas.zipcode.parser;

public class Parser {

	public int[] deSerializeRanges(String input) {
		validateNotNull(input);
		String[] ranges = removeSpaces(input);
		return convertToIntegerBoundaryPoints(ranges);
	}

	private int[] convertToIntegerBoundaryPoints(String[] ranges) {
		int[] rangeBoundaryPoints = new int[ranges.length * 2];
		for (int i = 0; i < ranges.length; i++) {
			int[] minMax = parseRange(ranges[i]);
			rangeBoundaryPoints[i * 2] = minMax[0];
			rangeBoundaryPoints[i * 2 + 1] = minMax[1];
		}
		return rangeBoundaryPoints;
	}

	private int[] parseRange(String range) {
		validateRangeNotNull(range);
		String trimmedInput = removeBrackets(range);
		String[] minMaxPair = removeCommaDelimiter(trimmedInput);
		return parseIntegers(minMaxPair);
	}

	private void validateNotNull(String input) {
		if (input == null || input.length() == 0) {
			throw new ZipCodeException(
					"Usage: java -jar ZipCode.jar [<5-digit zipcode>,<5-digit zipcode>] [<5-digit zipcode>,<5-digit zipcode>] ...");
		}
	}

	private void validateRangeNotNull(String range) {
		if (range == null || range.length() == 0) {
			throw new ZipCodeException(String
					.format("Parsing error: You must delimit ranges with only one space"));
		}
	}

	private String[] removeSpaces(String input) {
		return input.split(" ");
	}



	private int[] parseIntegers(String[] zipcodes) {
		int[] minMaxPair = new int[2];
		minMaxPair[0] = parseInteger(zipcodes[0]);
		minMaxPair[1] = parseInteger(zipcodes[1]);
		return minMaxPair;
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
