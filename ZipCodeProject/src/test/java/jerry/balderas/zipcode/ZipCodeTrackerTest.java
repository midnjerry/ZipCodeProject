package jerry.balderas.zipcode;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import jerry.balderas.zipcode.parser.ZipCodeException;

public class ZipCodeTrackerTest {
	private ZipCodeTracker mTracker;

	@Before
	public void setup() {
		mTracker = new ZipCodeTracker();
	}

	public void assertRanges(String expected, int... minMaxValues) {
		ZipCodeTracker tracker = new ZipCodeTracker();
		for (int i = 0; i < minMaxValues.length; i += 2) {
			tracker.markZipCodes(minMaxValues[i], minMaxValues[i + 1]);
		}
		assertEquals(expected, tracker.serializeZipCodeRanges());
	}

	private int[] getAtomizedZipCodes(int start, int end) {
		int[] result = new int[(end - start + 1) * 2];
		for (int i = 0; i < result.length / 2; i++) {
			result[i * 2] = i + start;
			result[i * 2 + 1] = i + start;
		}
		return result;
	}

	@Test
	public void testMinimization() {
		assertRanges("");
		assertRanges("", new int[0]);
		assertRanges("[10000,12000]", 10000, 12000);
		assertRanges("[00001,00001]", 1, 1);
		assertRanges("[00001,00001]", 1, 1, 1, 1);
		assertRanges("[10000,12000]", 10000, 10999, 11000, 12000);
		assertRanges("[10000,10000] [10002,10003]", 10000, 10000, 10002, 10003);
		assertRanges("[10000,12000]", 10000, 11500, 10500, 12000);
		assertRanges("[10000,12000]", 10500, 12000, 10000, 11500);
		assertRanges("[10000,12000]", getAtomizedZipCodes(10000, 12000));
		assertRanges("[00001,99999]", getAtomizedZipCodes(1, 99999));
		String expected = "[10000,12000] [13000,14000]";
		assertRanges(expected, 10000, 12000, 13000, 14000);
		assertRanges(expected, 10000, 12000, 13000, 14000, 13090, 14000);
		assertRanges(expected, 13090, 14000, 10000, 12000, 13000, 14000);
	}

	@Test(expected = ZipCodeException.class)
	public void numbersNotInOrder_ThrowsException() {
		mTracker.markZipCodes(12000, 10000);
	}

	@Test(expected = ZipCodeException.class)
	public void negativeNumber_ThrowsException() {
		mTracker.markZipCodes(-1000, 10000);
	}

	@Test(expected = ZipCodeException.class)
	public void numberGreaterThan99999_ThrowsException() {
		mTracker.markZipCodes(1000, 100000);
	}

	@Test
	public void expectEmptyStringFromNewTracker() {
		assertEquals("", mTracker.serializeZipCodeRanges());
	}

}
