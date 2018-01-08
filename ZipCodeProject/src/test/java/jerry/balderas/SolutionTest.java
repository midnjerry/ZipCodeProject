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
		assertEquals("[10000,12000]", Solution.consolidateRanges("[10000,12000]"));
		assertEquals("[00001,00001]", Solution.consolidateRanges("[00001,00001]"));
		assertEquals("[00001,00001]", Solution.consolidateRanges("[00001,00001] [00001,00001]"));
		assertEquals("[10000,12000]", Solution.consolidateRanges("[10000,10999] [11000,12000]"));
		assertEquals("[10000,10000] [10002,10003]", Solution.consolidateRanges("[10000,10000] [10002,10003]"));
		assertEquals("[10000,12000]", Solution.consolidateRanges("[10000,11500] [10500,12000]"));
		assertEquals("[10000,12000]", Solution.consolidateRanges("[10500,12000] [10000,11500]"));
		assertEquals("[10000,12000]", Solution.consolidateRanges(getAtomizedZipCodeInput(10000, 12000)));
		assertEquals("[10000,12000] [99998,99999]",
				Solution.consolidateRanges("[10000,12000] [99998,99998] [99999,99999]"));
		String expected = "[10000,12000] [13000,14000]";
		assertEquals(expected, Solution.consolidateRanges("[10000,12000] [13000,14000]"));
		assertEquals(expected, Solution.consolidateRanges("[10000,12000] [13000,14000] [13090,14000]"));
		assertEquals(expected, Solution.consolidateRanges("[13090,14000] [10000,12000] [13000,14000]"));

	}

	@Test
	public void worstCaseScenario() {
		assertEquals("[00001,99999]", Solution.consolidateRanges(getAtomizedZipCodeInput(1, 99999)));
	}

	@Test
	public void testMain() {
		Solution.main(new String[] { "[10000,12000]", "[13000,14000]", "[13090,14000]" });
	}

	@Test
	public void callingEmptyMain_OutputsUsageMessage() {
		Solution.main(new String[] {});
	}
}
