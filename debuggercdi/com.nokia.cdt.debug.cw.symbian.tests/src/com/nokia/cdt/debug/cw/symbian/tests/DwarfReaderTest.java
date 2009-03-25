package com.nokia.cdt.debug.cw.symbian.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.TestCase;

import org.eclipse.cdt.utils.debug.dwarf.DwarfReader;

public class DwarfReaderTest extends TestCase {

	private String m_outputFileName = "c:\\temp\\DwarfReaderOutput.txt";

	// The Elf file to parse. 
	//  
	private String[]  m_dataFileNames; 
		
	private PrintStream	m_outputFileStream = null;
	
	protected void setUp() throws Exception {
		super.setUp();

		m_dataFileNames = getDataFileNames();
		
		if (m_outputFileStream == null) {
			File outputFile = new File(m_outputFileName);
			if (!outputFile.exists())
				outputFile.createNewFile();

			m_outputFileStream = new PrintStream(new FileOutputStream(
					outputFile, false /* append? */));

			m_outputFileStream.println("Output of test on CDT DwarfReader");
			m_outputFileStream.println("==========================================");
		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		m_outputFileStream.close();
	}

	public final void testGetSourceFiles() {
		for (String fname: m_dataFileNames) {
			File f = new File(fname);
			if (f == null || !f.exists())
				fail("Data file not found: " + f.getAbsolutePath());

			// Currently this just check if the parsing process succeeds,
			// not check if the output is correct.
			// Look at the output file designated by "m_outputFileName"
			// and see if it's correct.
			getSourceFilesForBinary(fname);
		}
	}

	// Get data files to test
	//
	private String[] getDataFileNames() throws Exception {
		String[] fnames = null;
		
		ArrayList<String> list = new ArrayList<String>(10);

		// To add individual data files...
		//
		/*
		list.add("M:\\epoc32\\RELEASE\\armv5\\udeb\\agenda.sym");
		list.add(TestsPlugin.getPluginPath()+"\\data\\one2.sym");
		list.add(TestsPlugin.getPluginPath()+"\\data\\_h2_dma.sym");
		*/
		
		// To add files under one folder...
		//
		// All all .sym files under data folder in the project.
		list.addAll(Arrays.asList(TestsPlugin.getFileFullNamesInFolder(
				TestsPlugin.getPluginPath()+"\\data\\", ".sym")));
		/*
		list.addAll(Arrays.asList(TestsPlugin.getFileFullNamesInFolder(
				"M:\\epoc32\\RELEASE\\armv5\\udeb\\", ".sym")));
		list.addAll(Arrays.asList(TestsPlugin.getFileFullNamesInFolder(
				"M:\\epoc32\\RELEASE\\gcce\\udeb\\", ".sym")));
		*/
		
		fnames = new String[list.size()];
		list.toArray(fnames);
		
		return fnames;
	}
	
	private void getSourceFilesForBinary(String binaryFileName) {
		DwarfReader reader = null;
		
		try {
			reader = new DwarfReader(binaryFileName);
		} catch (IOException e) {
			e.printStackTrace();
			fail("File \"" + binaryFileName + "\" is not valid Elf file.");
			return;
		}

		m_outputFileStream.println("\n----------------------------------------------------");
		m_outputFileStream.println("Data file to check: ");
		m_outputFileStream.println("\t" + binaryFileName);
		m_outputFileStream.println("----------------------------------------------------");
		
		String[] files = reader.getSourceFiles();
		
		m_outputFileStream.print("Number of source files: ");
		m_outputFileStream.println(files.length);
		m_outputFileStream.println();

		// Sort the file lists.
		Arrays.sort(files);
		
		for (String f : files) {
			m_outputFileStream.println(f);
		}
	}

	// --- NOTE ------
	// This is for my own manual junit test only, not for public 
	// auto-test.
	public void my_testLineInfoContainsMoreSourceFiles() {
		
		// Iterate through those files and check...
		for (String f : m_dataFileNames) {
			m_outputFileStream.format("Check [%s] :\n", f);
			
			if (lineInfoContainsMoreSourceFiles(f))			
				m_outputFileStream.println("yes !");
			else
				m_outputFileStream.println("No.");
		}
	}
	
	/*
	 * This function checks if .debug_line section contains 
	 * source files that are not referenced by any TAG_compile_units.
	 * 
	 * @param dataFileName
	 */
	private boolean lineInfoContainsMoreSourceFiles(String dataFileName) {
		DwarfReader reader = null;
		
		try {
			reader = new DwarfReader(dataFileName);
		} catch (IOException e) {
			e.printStackTrace();
			fail("File \"" + dataFileName + "\" is not valid Elf file.");
			return false;
		}

		// Hold on !
		// Look at code of getSourceFiles() and add two public 
		// APIs like 
		//		"getSourcesFromCompileUnit()" and 
		//		"getSourcesFromDebugLine()"
		// and then test here.
		String[] filesFromCU = reader.getSourceFiles(); // change to getFromCompileUnit()
		String[] filesFromLineInfo = reader.getSourceFiles(); // change to getFromDebugLine()
		
/*		
 		// Sort the file lists and compare the list if needed.
 		//
		Arrays.sort(filesFromCU);
		Arrays.sort(filesFromLineInfo);

		assertEquals(filesFromCU.length, filesFromLineInfo.length);
		for (int i = 0; i < filesFromCU.length; i++) {
			assertEquals(filesFromCU[i], filesFromLineInfo[i]);
		}
*/
		return (filesFromCU.length > filesFromLineInfo.length);
	}

}
