package com.nokia.carbide.search.system.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(BasicTest.class);
		//$JUnit-END$
		return suite;
	}

}
