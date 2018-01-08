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
		assertArrayEquals(new int[] { 1, 1 }, parser.deSerializeRanges("[00001,00001]"));
		assertArrayEquals(new int[] { 1, 5 }, parser.deSerializeRanges("[00001,00005]"));
		assertArrayEquals(new int[] { 1, 6, 8, 10 }, parser.deSerializeRanges("[00001,00006] [00008,00010]"));
		assertArrayEquals(new int[] { 25, 25, 25, 25 }, parser.deSerializeRanges("[00025,00025] [00025,00025]"));
		assertArrayEquals(new int[] {}, parser.deSerializeRanges(""));
	}

	@Test(expected = ZipCodeException.class)
	public void testMissingLeftBracket_ThrowsException() {
		parser.deSerializeRanges("[10000,12000");
	}

	@Test(expected = ZipCodeException.class)
	public void testMissingRightBracket_ThrowsException() {
		parser.deSerializeRanges("10000,12000]");
	}

	@Test(expected = ZipCodeException.class)
	public void testMissingBrackets_ThrowsException() {
		parser.deSerializeRanges("10000,12000");
	}

	@Test(expected = ZipCodeException.class)
	public void spaceBetweenCommaAndNumbers_ThrowsException() {
		parser.deSerializeRanges("10000, 12000]");
	}

	@Test(expected = ZipCodeException.class)
	public void noSpaceBetweenBrackets_ThrowsException() {
		parser.deSerializeRanges("[10000,12000][25000,23402]");
	}

	@Test(expected = ZipCodeException.class)
	public void twoSpacesBetweenBrackets_ThrowsException() {
		parser.deSerializeRanges("[10000,12000]  [25000,23402]");
	}

	@Test(expected = ZipCodeException.class)
	public void numbersGreaterThan99999_ThrowsException() {
		parser.deSerializeRanges("[12000,100000]");
	}

	@Test(expected = ZipCodeException.class)
	public void inputIsOneDigit_ThrowsException() {
		parser.deSerializeRanges("[1,10000]");
	}

	@Test(expected = ZipCodeException.class)
	public void inputIsSixDigits_ThrowsException() {
		parser.deSerializeRanges("[12345,123456]");
	}

	@Test(expected = ZipCodeException.class)
	public void alphanumericValue_ThrowsException() {
		parser.deSerializeRanges("[12345,1234e]");
	}
}
