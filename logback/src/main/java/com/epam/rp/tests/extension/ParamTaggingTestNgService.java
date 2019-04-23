package com.epam.rp.tests.extension;

import com.epam.reportportal.testng.TestNGService;
import com.epam.ta.reportportal.ws.model.FinishTestItemRQ;
import com.epam.ta.reportportal.ws.model.ItemAttributeResource;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import org.testng.ITestResult;
import rp.com.google.common.base.Optional;
import rp.com.google.common.base.Throwables;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:pavel_bortnik@epam.com">Pavel Bortnik</a>
 */
public class ParamTaggingTestNgService extends TestNGService {

	@Override
	protected StartTestItemRQ buildStartStepRq(ITestResult testResult) {
		final StartTestItemRQ rq = super.buildStartStepRq(testResult);
		SauceOnDemandSessionIdProvider sessionIdProvider = (SauceOnDemandSessionIdProvider) testResult.getInstance();
		if (sessionIdProvider != null && sessionIdProvider.getSessionId() != null) {
			String sessionId = sessionIdProvider.getSessionId();
			final Set<ItemAttributeResource> attributes = Optional.fromNullable(rq.getAttributes()).or(new HashSet<>());
			attributes.add(new ItemAttributeResource("SLDC", "EU"));
			attributes.add(new ItemAttributeResource("SLID", sessionId));
			rq.setAttributes(attributes);
		}
		return rq;
	}

//	@Override
//	protected FinishTestItemRQ buildFinishTestMethodRq(String status, ITestResult testResult) {
//		FinishTestItemRQ rq = super.buildFinishTestMethodRq(status, testResult);
//		SauceOnDemandSessionIdProvider sessionIdProvider = (SauceOnDemandSessionIdProvider) testResult.getInstance();
//		if (sessionIdProvider != null && sessionIdProvider.getSessionId() != null) {
//			String sessionId = sessionIdProvider.getSessionId();
//			final Set<ItemAttributeResource> attributes = Optional.fromNullable(rq.getAttributes()).or(new HashSet<>());
//			attributes.add(new ItemAttributeResource("SLDC", "EU"));
//			attributes.add(new ItemAttributeResource("SLID", sessionId));
//			rq.setAttributes(attributes);
//		}
//		return rq;
//	}
}