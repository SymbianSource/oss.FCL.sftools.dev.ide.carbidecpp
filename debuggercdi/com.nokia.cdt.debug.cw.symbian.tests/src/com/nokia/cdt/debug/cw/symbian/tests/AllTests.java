package com.nokia.cdt.debug.cw.symbian.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nokia.cdt.debug.cw.symbian.tests");
		//$JUnit-BEGIN$
		suite.addTestSuite(SymbianE32ParserTest.class);
		suite.addTestSuite(DwarfReaderTest.class);
		suite.addTestSuite(SymbolReaderTest.class);
		//$JUnit-END$
		return suite;
	}

}
