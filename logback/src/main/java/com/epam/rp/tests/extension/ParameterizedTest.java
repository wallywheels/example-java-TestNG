package com.epam.rp.tests.extension;

import com.epam.reportportal.listeners.ListenerParameters;
import com.epam.reportportal.service.ReportPortalClient;
import com.epam.reportportal.testng.*;
import com.epam.ta.reportportal.ws.model.FinishTestItemRQ;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import rp.com.google.common.base.Optional;
import rp.com.google.common.base.Throwables;
import rp.com.google.inject.Module;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static rp.com.google.inject.util.Modules.override;

/**
 * Extension example
 *
 * @author Andrei Varabyeu
 */
@Listeners({ ParameterizedTest.ExtendedListener.class })
public class ParameterizedTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ParameterizedTest.class);

	@Test(threadPoolSize = 2, dataProvider = "bla-bla")
	public void testParams(String msg) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			LOGGER.info(msg + ": " + i);
			if (i == 1) {
				Thread.sleep(TimeUnit.SECONDS.toMillis(5L));
			}
		}
	}

	@DataProvider(parallel = true, name = "bla-bla")
	public Iterator<Object[]> params() {
		return Arrays.asList(new Object[] { "one" }, new Object[] { "two" }).iterator();
	}

	public static class ExtendedListener extends BaseTestNGListener {
		public ExtendedListener() {
			super(override(new TestNGAgentModule()).with((Module) binder -> binder.bind(ITestNGService.class)
					.toProvider(new TestNGProvider() {
						@Override
						protected TestNGService createTestNgService(ListenerParameters listenerParameters,
								ReportPortalClient reportPortalClient) {
							return new ParamTaggingTestNgService(listenerParameters, reportPortalClient);
						}
					})));
		}
	}

	public static class ParamTaggingTestNgService extends TestNGService {

		public ParamTaggingTestNgService(ListenerParameters parameters, ReportPortalClient reportPortalClient) {
			super(parameters, reportPortalClient);
		}

		@Override
		protected StartTestItemRQ buildStartStepRq(ITestResult testResult) {
			final StartTestItemRQ rq = super.buildStartStepRq(testResult);
			if (testResult.getParameters() != null && testResult.getParameters().length != 0) {
				final Set<String> tags = Optional.fromNullable(rq.getTags()).or(new HashSet<>());
				for (Object param : testResult.getParameters()) {
					tags.add(param.toString());
				}
				rq.setTags(tags);

			}
			return rq;
		}

		@Override
		protected FinishTestItemRQ buildFinishTestMethodRq(String status, ITestResult testResult) {
			FinishTestItemRQ finishTestItemRQ = super.buildFinishTestMethodRq(status, testResult);
			if (testResult.getThrowable() != null) {
				String description = Strings.nullToEmpty(finishTestItemRQ.getDescription());
				description = description + Throwables.getStackTraceAsString(testResult.getThrowable());
				finishTestItemRQ.setDescription(description);
			}
			return finishTestItemRQ;
		}
	}

}
