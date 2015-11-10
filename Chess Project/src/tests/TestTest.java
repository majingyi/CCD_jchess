package tests;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestTest {

	@Test
	public void testTestPositive() {
		System.out.println("Done.");
	}

	@Test
	public void testTestNegative() {
		fail("Failed.");
	}
}
