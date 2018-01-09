package jerry.balderas.zipcode;

import jerry.balderas.zipcode.parser.Parser;
import jerry.balderas.zipcode.parser.ZipCodeException;

public class ZipCodeTracker {
	public final static int MIN_ZIPCODE = 1;
	public final static int MAX_ZIPCODE = 99999;
	boolean[] isMarked = new boolean[MAX_ZIPCODE + 1];

	/**
	 * Registers all zip codes that fall between start and end.
	 *
	 * @param start
	 *            - The minimum value of the zipcode range
	 * @param end
	 *            - The maximum value of the zipcode range
	 */
	public void markZipCodes(int start, int end) {
		validateZipCode(start);
		validateZipCode(end);
		validateInCorrectOrder(start, end);
		for (int zip = start; zip <= end; zip++) {
			isMarked[zip] = true;
		}
	}

	/**
	 * Registers all zip codes represented by ranges in input.
	 *
	 * @param serializedInput
	 *            - Accepts multiple integer ranges delimited by a " ". Example:
	 *            "[min1,max1] [min2,max2] ... [minN,maxN]"
	 */
	public void markZipCodes(String serializedInput) {
		Parser parser = new Parser();
		int[] minMaxValuesForRanges = parser.deSerializeRanges(serializedInput);
		for (int i = 0; i < minMaxValuesForRanges.length; i += 2) {
			markZipCodes(minMaxValuesForRanges[i], minMaxValuesForRanges[i + 1]);
		}
	}

	/**
	 * Reads all ZipCode ranges in memory and creates a represenation of those
	 * ranges in String format.
	 */
	public String serializeZipCodeRanges() {
		StringBuilder builder = new StringBuilder();
		int start = MIN_ZIPCODE;
		int end = MAX_ZIPCODE;
		boolean isStreak = false;
		for (int zipcode = start; zipcode <= end; zipcode++) {
			if (isMarked[zipcode] && !isStreak) {
				isStreak = true;
				start = zipcode;
			} else if (!isMarked[zipcode] && isStreak) {
				isStreak = false;
				builder.append(serializeRange(start, zipcode - 1) + " ");
			}
		}
		if (isStreak) {
			builder.append(serializeRange(start, end));
		}
		return builder.toString().trim();
	}

	/**
	 * Represents a zipcode range in String format
	 * 
	 * @param start
	 *            - The minimum value of the zipcode range
	 * @param end
	 *            - The maximum value of the zipcode range
	 */
	private String serializeRange(int start, int end) {
		return String.format("[%05d,%05d]", start, end);
	}

	/**
	 * Validates that zipcode range is in correct order.
	 * 
	 * @param start
	 *            - The minimum value of the zipcode range
	 * @param end
	 *            - The maximum value of the zipcode range
	 */
	private void validateInCorrectOrder(int start, int end) {
		if (start > end) {
			throw new ZipCodeException(String
					.format("ZipCode error: [%d,%d] - first value cannot be larger than second value", start, end));
		}
	}

	/**
	 * Validates that zipcode is within acceptable boundaries
	 * 
	 * @param zipcode
	 *            - ZipCode to test
	 */
	private void validateZipCode(int zipcode) {
		if (zipcode < MIN_ZIPCODE || zipcode > MAX_ZIPCODE) {
			throw new ZipCodeException(
					String.format("ZipCode error: %d must be >= %d and <= %d", zipcode, MIN_ZIPCODE, MAX_ZIPCODE));
		}
	}

}
