package jerry.balderas.zipcode;

import jerry.balderas.zipcode.parser.Parser;
import jerry.balderas.zipcode.parser.ZipCodeException;

public class ZipCodeTracker {
	public final static int MIN_ZIPCODE = 1;
	public final static int MAX_ZIPCODE = 99999;
	boolean[] isMarked = new boolean[MAX_ZIPCODE + 1];

	public void markZipCodes(int start, int end) {
		validateZipCode(start);
		validateZipCode(end);
		validateInCorrectOrder(start, end);
		for (int zip = start; zip <= end; zip++) {
			isMarked[zip] = true;
		}
	}

	public void markZipCodes(String serializedInput) {
		Parser parser = new Parser();
		int[] minMaxValuesForRanges = parser.deSerializeRanges(serializedInput);
		for (int i = 0; i < minMaxValuesForRanges.length; i += 2) {
			markZipCodes(minMaxValuesForRanges[i], minMaxValuesForRanges[i + 1]);
		}
	}

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

	private String serializeRange(int start, int end) {
		return String.format("[%05d,%05d]", start, end);
	}

	private void validateInCorrectOrder(int start, int end) {
		if (start > end) {
			throw new ZipCodeException(String
					.format("ZipCode error: [%d,%d] - first value cannot be larger than second value", start, end));
		}
	}

	private void validateZipCode(int zipcode) {
		if (zipcode < MIN_ZIPCODE || zipcode > MAX_ZIPCODE) {
			throw new ZipCodeException(
					String.format("ZipCode error: %d must be >= %d and <= %d", zipcode, MIN_ZIPCODE, MAX_ZIPCODE));
		}
	}

}
