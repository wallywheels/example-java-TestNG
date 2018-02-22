package com.epam.rp.tests.extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Extension example
 *
 * @author Andrei Varabyeu
 */
//@Listeners({ ParameterizedTest.ExtendedListener.class })
public class ParameterizedTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ParameterizedTest.class);

	@Test(threadPoolSize = 2, dataProvider = "data-provider")
	public void testParams(String msg) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			LOGGER.info(msg + ": " + i);
			if (i == 1) {
				Thread.sleep(TimeUnit.SECONDS.toMillis(5L));
			}
		}
	}

	@DataProvider(parallel = true, name = "data-provider")
	public Iterator<Object[]> params() {
		return Arrays.asList(new Object[] { "first-parameter" }, new Object[] { "second-parameter" }).iterator();
	}

}
