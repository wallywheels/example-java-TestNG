package com.epam.rp.tests;

import java.util.Arrays;
import java.util.Collections;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import com.epam.reportportal.testng.ReportPortalTestNGListener;

/**
 * @author Andrei Varabyeu
 */
public class Runner {
	public static void main(String[] args) {
		TestNG testNG = new TestNG(false);
		testNG.setListenerClasses(Collections.<Class> singletonList(ReportPortalTestNGListener.class));

		testNG.setTestSuites(Arrays.asList("suites/logging_tests.xml", "suites/preconditions_suite.xml", "suites/randomizer_suite.xml"));

		TestListenerAdapter results = new TestListenerAdapter();
		testNG.addListener(results);
		boolean hasFailures;
		try {
			testNG.run();
			hasFailures = results.getFailedTests().size() > 0 || results.getSkippedTests().size() > 0;
		} catch (Throwable e) {
			/* something goes wrong... */
			hasFailures = true;
		}
		System.exit(hasFailures ? 1 : 0);

	}

}
