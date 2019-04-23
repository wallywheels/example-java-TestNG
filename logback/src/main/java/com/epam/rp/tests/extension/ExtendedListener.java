package com.epam.rp.tests.extension;

import com.epam.reportportal.listeners.Statuses;
import com.epam.reportportal.testng.BaseTestNGListener;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import org.testng.ITestResult;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public class ExtendedListener extends BaseTestNGListener {
	public ExtendedListener() {
		super(new ParamTaggingTestNgService());
	}
}
