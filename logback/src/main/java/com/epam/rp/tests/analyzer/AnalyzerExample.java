package com.epam.rp.tests.analyzer;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public class AnalyzerExample {

	@Test
	public void someSeriousTest1() {
		Assert.assertEquals(
				"You asked me once,\" said O'Brien, \"what was in Room 101. I told you that you knew the answer already. Everyone knows it. The thing that is in Room 101 is the worst thing in the world.",
				"You asked me once,\" said O'Brien, \"what was in Room 101. I told you that you knew the answer already. Everyone knows it is. The thing that is in Room 101 is the worst thing in the world"
		);
	}

	@Test
	public void someSeriousTest2() throws IOException {
		throw new IOException("Some very serious problem.");
	}

	@Test
	public void someSeriousTest3() {
		Assert.assertEquals(3, 3);
	}

}
