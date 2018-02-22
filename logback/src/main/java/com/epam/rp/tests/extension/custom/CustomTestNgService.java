/*
 * Copyright 2017 EPAM Systems
 *
 * This file is part of EPAM Report Portal.
 * https://github.com/reportportal/service-api
 *
 * Report Portal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Report Portal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Report Portal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.epam.rp.tests.extension.custom;

import com.epam.reportportal.testng.TestNGService;
import com.epam.ta.reportportal.ws.model.FinishTestItemRQ;
import com.epam.ta.reportportal.ws.model.StartTestItemRQ;
import com.google.common.base.Throwables;
import org.testng.ITestResult;
import rp.com.google.common.base.Optional;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Pavel Bortnik
 */
public class CustomTestNgService extends TestNGService {

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
			String description = "```error\n" + Throwables.getStackTraceAsString(testResult.getThrowable()) + "\n```";
			finishTestItemRQ.setDescription(description);
		}
		return finishTestItemRQ;
	}

}
