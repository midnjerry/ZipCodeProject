package jerry.balderas.zipcode;

import jerry.balderas.zipcode.parser.Parser;

public class ZipCodeTracker {
	public final static int MIN_ZIPCODE = 1;
	public final static int MAX_ZIPCODE = 99999;
	boolean[] isMarked = new boolean[MAX_ZIPCODE + 1];

	private void markZipCodes(int start, int end) {
		for (int zip = start; zip <= end; zip++) {
			isMarked[zip] = true;
		}
	}

	public void markZipCodes(String serializedInput) {
		Parser parser = new Parser();
		Integer[] ranges = parser.parseZipCodeRanges(serializedInput);
		for (int i = 0; i < ranges.length; i += 2) {
			markZipCodes(ranges[i], ranges[i + 1]);
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
				builder.append(String.format("[%05d,%05d] ", start, zipcode - 1));
			}
		}
		if (isStreak) {
			builder.append(String.format("[%05d,%05d] ", start, end));
		}
		return builder.toString().trim();
	}

}
