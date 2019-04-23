package com.epam.rp.tests.extension;

import com.epam.reportportal.testng.BaseTestNGListener;

public class ExtendedListener extends BaseTestNGListener {
	public ExtendedListener() {
		super(new ParamTaggingTestNgService());
	}
}
