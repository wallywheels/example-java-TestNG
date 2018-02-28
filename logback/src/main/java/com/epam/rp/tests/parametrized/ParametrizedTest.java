package com.epam.rp.tests.parametrized;

import com.epam.reportportal.annotations.ParameterKey;
import com.epam.reportportal.annotations.UniqueID;
import com.epam.rp.tests.extension.ParameterizedTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Parametrized test. You should get all parameters. Custom keys for parameters
 * Custom uniqueID.
 *
 * @author Pavel Bortnik
 */
public class ParametrizedTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ParameterizedTest.class);

	@Test
	@Parameters({ "message" })
	@UniqueID("My_custom_unique_id")
	public void testParamsUniqueId(String msg) throws InterruptedException {
		for (int i = 0; i < 3; i++) {
			LOGGER.info(msg + ": " + i);
		}

		for (int i = 0; i < 3; i++) {
			LOGGER.debug("Debug message");
		}
		for (int i = 0; i < 3; i++) {
			LOGGER.warn("Warn message");
		}
	}

	@Test(threadPoolSize = 2, dataProvider = "data-provider")
	public void testCustomParameterKey(@ParameterKey("my_great_parameter") String msg, String msg2) throws InterruptedException {
		for (int i = 0; i < 3; i++) {
			LOGGER.info(msg + ": " + i + "msq2:" + msg2);
		}
		for (int i = 0; i < 3; i++) {
			LOGGER.debug("Debug message");
		}
		for (int i = 0; i < 3; i++) {
			LOGGER.warn("Warn message");
		}
		Assert.fail("Something wrong with test parameters");
	}

	@DataProvider(parallel = true, name = "data-provider")
	public Iterator<Object[]> params() {
		return Arrays.asList(new Object[] { "one", "two" }, new Object[] { "two", "one" }, new Object[] { "three", null }).iterator();
	}

}
