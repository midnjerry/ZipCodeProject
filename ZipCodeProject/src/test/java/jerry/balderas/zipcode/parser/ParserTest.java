package jerry.balderas.zipcode.parser;


import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

public class ParserTest {

	private Parser parser;

	@Before
	public void setup() {
		parser = new Parser();
	}

	@Test
	public void parseCorrectlyFormattedInput() {
		assertArrayEquals(new Integer[] { 1, 1 }, parser.parseZipCodeRanges("[00001,00001]"));
		assertArrayEquals(new Integer[] { 1, 5 }, parser.parseZipCodeRanges("[00001,00005]"));
		assertArrayEquals(new Integer[] { 1, 6, 8, 10 }, parser.parseZipCodeRanges("[00001,00006] [00008,00010]"));
		assertArrayEquals(new Integer[] { 25, 25, 25, 25 }, parser.parseZipCodeRanges("[00025,00025] [00025,00025]"));
	}

	@Test(expected = ZipCodeException.class)
	public void testMissingLeftBracket_ThrowsException() {
		parser.parseZipCodeRanges("[10000,12000");
	}

	@Test(expected = ZipCodeException.class)
	public void testMissingRightBracket_ThrowsException() {
		parser.parseZipCodeRanges("10000,12000]");
	}

	@Test(expected = ZipCodeException.class)
	public void testMissingBrackets_ThrowsException() {
		parser.parseZipCodeRanges("10000,12000");
	}

	@Test(expected = ZipCodeException.class)
	public void spaceBetweenCommaAndNumbers_ThrowsException() {
		parser.parseZipCodeRanges("10000, 12000]");
	}

	@Test(expected = ZipCodeException.class)
	public void noSpaceBetweenBrackets_ThrowsException() {
		parser.parseZipCodeRanges("[10000,12000][25000,23402]");
	}

	@Test(expected = ZipCodeException.class)
	public void twoSpacesBetweenBrackets_ThrowsException() {
		parser.parseZipCodeRanges("[10000,12000]  [25000,23402]");
	}

	@Test(expected = ZipCodeException.class)
	public void numbersGreaterThan99999_ThrowsException() {
		parser.parseZipCodeRanges("[12000,100000]");
	}

	@Test(expected = ZipCodeException.class)
	public void inputIsOneDigit_ThrowsException() {
		parser.parseZipCodeRanges("[1,10000]");
	}

	@Test(expected = ZipCodeException.class)
	public void inputIsSixDigits_ThrowsException() {
		parser.parseZipCodeRanges("[12345,123456]");
	}

	@Test(expected = ZipCodeException.class)
	public void alphanumericValue_ThrowsException() {
		parser.parseZipCodeRanges("[12345,1234e]");
	}

	@Test(expected = ZipCodeException.class)
	public void inputIsNull_ThrowsException() {
		parser.parseZipCodeRanges("");
	}

}
