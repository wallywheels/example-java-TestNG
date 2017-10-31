package com.epam.rp.tests.extension;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Listeners(ReportPortalTestNGListener.class)
public class RetryTest {

	private static final AtomicInteger COUNTER = new AtomicInteger(0);

	@Test(retryAnalyzer = RetryTest.RetryImpl.class)
	public void failOne() throws IOException, InterruptedException {

		if (2 != COUNTER.get()) {
			Assert.fail("Ooops");
		}

	}

	public static class RetryImpl implements IRetryAnalyzer {

		@Override
		public boolean retry(ITestResult result) {
			return !result.isSuccess() && COUNTER.incrementAndGet() < 5;
		}
	}
}
