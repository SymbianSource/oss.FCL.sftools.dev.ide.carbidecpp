package com.nokia.cdt.debug.cw.symbian.tests;

import junit.framework.TestCase;

import com.nokia.cdt.debug.cw.symbian.symbolreader.IFunction;
import com.nokia.cdt.debug.cw.symbian.symbolreader.ISourceLocation;
import com.nokia.cdt.debug.cw.symbian.symbolreader.ISymbolFile;
import com.nokia.cdt.debug.cw.symbian.symbolreader.SymbolReaderManager;

public class SymbolReaderTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSymUnload() throws Exception {

		java.io.File src = new java.io.File(TestsPlugin.getPluginPath()+"\\data\\_h2_dma.sym");
		java.io.File test = java.io.File.createTempFile("testSymUnload_", ".sym");
		
		java.io.InputStream srcStream = new java.io.FileInputStream(src);
		java.io.OutputStream testStream = new java.io.FileOutputStream(test);
    
        // copy to test file
        byte[] buf = new byte[1024];
        int len;
        while ((len = srcStream.read(buf)) > 0) {
        	testStream.write(buf, 0, len);
        }
        srcStream.close();
        testStream.close();
		
		//open symbol file
		ISymbolFile symbolFile = SymbolReaderManager.getSymbolReaderManager().openSymbolFile(test.getAbsolutePath());

		//create address from hex string
		java.math.BigInteger big = new java.math.BigInteger("841d", 16);

		//get function and source location
		IFunction function = symbolFile.findFunctionByAddress( big );
		ISourceLocation loca = symbolFile.findSourceLocation( big );
		
		// close and make sure we released the file(can delete)
		symbolFile.close();
		
		boolean succeed = test.delete();
		
		assertTrue(succeed);
	}
}
