/**
 * 
 */
package com.nokia.cdt.debug.cw.symbian.tests;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.eclipse.cdt.core.IBinaryParser.IBinaryFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.cdt.debug.cw.symbian.binaryparser.SymbianE32Parser;

public class SymbianE32ParserTest extends TestCase {

	static String s_testFileName = "data\\_h2_dma.dll";

	IPath m_testFilePath = null;

	SymbianE32Parser m_parser = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		m_parser = new SymbianE32Parser();
		if (m_parser == null)
			fail("Failed to instantiate SymbianE32Parser.");

		File f = TestsPlugin.projectRelativeFile(s_testFileName);
		if (f == null || !f.exists())
			fail("Data file " + s_testFileName + " not found.");

		m_testFilePath = new Path(f.getAbsolutePath());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		// Dispose the parser
		m_parser = null;
	}

	/**
	 * Test method for
	 * {@link com.nokia.cdt.debug.cw.symbian.binaryparser.SymbianE32Parser#getBinary(org.eclipse.core.runtime.IPath)}.
	 */
	// @Test
	public void testGetBinaryIPath() {
		IBinaryFile binary = null;

		String msg = "The file " + s_testFileName + " is not recognized.";
		try {
			binary = m_parser.getBinary(m_testFilePath);
		} catch (IOException e) {
			fail(msg);
		}

		assertTrue(msg, binary != null);
	}

	/**
	 * Test method for
	 * {@link com.nokia.cdt.debug.cw.symbian.binaryparser.SymbianE32Parser#getFormat()}.
	 */
	// @Test
	public void testGetFormat() {
		assertTrue("getFormat() fails", m_parser.getFormat() == "Symbian E32");
	}

	/**
	 * Test method for
	 * {@link com.nokia.cdt.debug.cw.symbian.binaryparser.SymbianE32Parser#isBinary(byte[], org.eclipse.core.runtime.IPath)}.
	 */
	// @Test
	public void testIsBinary() {
		assertTrue("File " + s_testFileName + " is not Symbian E32 binary",
				m_parser.isBinary(null, m_testFilePath));
	}
}
