package com.epam.rp.tests.extension;

import com.epam.reportportal.testng.BaseTestNGListener;
import com.epam.reportportal.testng.TestNGService;
import com.epam.ta.reportportal.ws.model.FinishTestItemRQ;
import com.epam.ta.reportportal.ws.model.issue.Issue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import rp.com.google.common.base.Throwables;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Listeners(RetryTest.ExtendedListener.class)
public class RetryTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(RetryTest.class);

	private static final AtomicInteger COUNTER = new AtomicInteger(0);
	private static final AtomicInteger COUNTER2 = new AtomicInteger(0);

	@Test(retryAnalyzer = RetryTest.RetryImpl.class)
	public void failOne() throws IOException, InterruptedException {
		String errorMsg = "Ooops";
		if (9 != COUNTER.get()) {
			for (int i = 0; i < 10; i++) {
				LOGGER.error(errorMsg + COUNTER.get());
			}
			Assert.fail(errorMsg + COUNTER2.get());
		}
		Assert.fail("HE HE FAIL");
	}

	@Test(retryAnalyzer = RetryTest.RetryImpl2.class)
	public void failOne2() throws IOException, InterruptedException {
		String errorMsg = "OoopsFailTwo";
		if (9 != COUNTER2.get()) {
			for (int i = 0; i < 10; i++) {
				LOGGER.error(errorMsg + COUNTER2.get());
			}
			Assert.fail(errorMsg + COUNTER2.get());
		}

	}

	public static class RetryImpl implements IRetryAnalyzer {

		@Override
		public boolean retry(ITestResult result) {
			try {
				Thread.currentThread().sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return !result.isSuccess() && COUNTER.incrementAndGet() < 10;
		}
	}

	public static class RetryImpl2 implements IRetryAnalyzer {

		@Override
		public boolean retry(ITestResult result) {
			try {
				Thread.currentThread().sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return !result.isSuccess() && COUNTER2.incrementAndGet() < 10;
		}
	}

	public static class ExtendedListener extends BaseTestNGListener {
		public ExtendedListener() {
			super(new RetryTest.ProvidedIssueListener());
		}

		@Override
		public void onTestStart(ITestResult testResult) {
			System.out.println(testResult.getMethod().getInvocationCount());

			System.out.println(testResult.getMethod().getCurrentInvocationCount());
			super.onTestStart(testResult);
		}
	}

	public static class ProvidedIssueListener extends TestNGService {

		@Override
		protected FinishTestItemRQ buildFinishTestMethodRq(String status, ITestResult testResult) {
			FinishTestItemRQ finishTestItemRQ = super.buildFinishTestMethodRq(status, testResult);
			Issue issue = new Issue();
			issue.setComment("bla bla");
			issue.setIssueType("PB001");
			finishTestItemRQ.setIssue(issue);
			if (testResult.getThrowable() != null) {
				String description = "```error\n" + Throwables.getStackTraceAsString(testResult.getThrowable()) + "\n```";
				finishTestItemRQ.setDescription(description);
			}
			return finishTestItemRQ;
		}
	}
}
