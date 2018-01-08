package jerry.balderas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SolutionTest {

	private String getAtomizedZipCodeInput(int start, int end) {
		StringBuilder builder = new StringBuilder();
		for (int zip = start; zip <= end; zip++) {
			builder.append(String.format("[%05d,%05d] ", zip, zip));
		}
		return builder.toString().trim();
	}

	@Test
	public void testSimplification() {
		assertEquals("[10000,12000]", Solution.getUnionOfZipCodeRanges("[10000,12000]"));
		assertEquals("[00001,00001]", Solution.getUnionOfZipCodeRanges("[00001,00001]"));
		assertEquals("[00001,00001]", Solution.getUnionOfZipCodeRanges("[00001,00001] [00001,00001]"));
		assertEquals("[10000,12000]", Solution.getUnionOfZipCodeRanges("[10000,10999] [11000,12000]"));
		assertEquals("[10000,10000] [10002,10003]", Solution.getUnionOfZipCodeRanges("[10000,10000] [10002,10003]"));
		assertEquals("[10000,12000]", Solution.getUnionOfZipCodeRanges("[10000,11500] [10500,12000]"));
		assertEquals("[10000,12000]", Solution.getUnionOfZipCodeRanges("[10500,12000] [10000,11500]"));
		assertEquals("[10000,12000]", Solution.getUnionOfZipCodeRanges(getAtomizedZipCodeInput(10000, 12000)));
		String expected = "[10000,12000] [13000,14000]";
		assertEquals(expected, Solution.getUnionOfZipCodeRanges("[10000,12000] [13000,14000]"));
		assertEquals(expected, Solution.getUnionOfZipCodeRanges("[10000,12000] [13000,14000] [13090,14000]"));
		assertEquals(expected, Solution.getUnionOfZipCodeRanges("[13090,14000] [10000,12000] [13000,14000]"));
	}

	@Test
	public void worstCaseScenario() {
		assertEquals("[00001,99999]", Solution.getUnionOfZipCodeRanges(getAtomizedZipCodeInput(1, 99999)));
	}
}
