package com.epam.rp.tests.extension;

import com.epam.reportportal.testng.BaseTestNGListener;
import org.testng.ITestResult;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public class ExtendedListener extends BaseTestNGListener {
	public ExtendedListener() {
		super(new ParamTaggingTestNgService());
	}

	@Override
	public void onTestStart(ITestResult testResult) {
		System.out.println(testResult.getMethod().getInvocationCount());

		System.out.println(testResult.getMethod().getCurrentInvocationCount());
		super.onTestStart(testResult);
	}
}
