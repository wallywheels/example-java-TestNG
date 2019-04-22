package com.epam.rp.tests.extension;

import com.epam.reportportal.testng.TestNGService;
import com.epam.ta.reportportal.ws.model.FinishTestItemRQ;
import com.epam.ta.reportportal.ws.model.ItemAttributeResource;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
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
		if (testResult.getParameters() != null && testResult.getParameters().length != 0) {
			final Set<ItemAttributeResource> attributes = Optional.fromNullable(rq.getAttributes()).or(new HashSet<>());
			for (Object param : testResult.getParameters()) {
				attributes.add(new ItemAttributeResource(null, param.toString()));
				attributes.add(new ItemAttributeResource("SLDC", "EU"));
				attributes.add(new ItemAttributeResource("SLID", "afab7a393be2432db004f0edf3971b4f"));
			}
			rq.setAttributes(attributes);

		}
		return rq;
	}

	@Override
	protected FinishTestItemRQ buildFinishTestMethodRq(String status, ITestResult testResult) {
		FinishTestItemRQ finishTestItemRQ = super.buildFinishTestMethodRq(status, testResult);
		if (testResult.getThrowable() != null) {
			String description = "```error\n" + Throwables.getStackTraceAsString(testResult.getThrowable()) + "\n```";
			description = description + Throwables.getStackTraceAsString(testResult.getThrowable());
			finishTestItemRQ.setDescription(description);
		}
		return finishTestItemRQ;
	}
}