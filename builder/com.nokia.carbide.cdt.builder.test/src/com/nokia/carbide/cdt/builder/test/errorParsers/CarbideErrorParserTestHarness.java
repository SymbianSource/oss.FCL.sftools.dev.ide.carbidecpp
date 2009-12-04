/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description: 
* This is a test harness implementing as a warper to Carbide.c++ error parsing,
* any test written using this harness will cover all production code involved
* in error parsing.
* 1. Provides redirection of error parser input/output to junit(programmatic) friendly format
* 2. Provides facility for preparing, diffing control result in XML format
* 
*/
package com.nokia.carbide.cdt.builder.test.errorParsers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.cdt.core.ProblemMarkerInfo;
import org.eclipse.cdt.core.model.ICModelMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.jdom.Attribute;
import org.jdom.Comment;
import org.jdom.DefaultJDOMFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.filter.ElementFilter;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.nokia.carbide.cdt.builder.builder.CarbideCommandLauncher;
import com.nokia.carbide.cdt.builder.test.TestPlugin;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.HostOS;

public class CarbideErrorParserTestHarness extends CarbideCommandLauncher {
	static final String EMPTY = "^EMPTY^";
	static final String MARKER_INFO = "marker_info";
	static final String FILE = "file";
	static final String LINE_NUMBER = "line_number";
	static final String MESSAGE = "message";
	static final String SEVERITY = "severity";
	static final String VARIABLE_NAME = "variable_name";
	static final String EXTERNAL_PATH_STRING = "external_path_string";
	
	IProject project;
	ArrayList<ProblemMarkerInfo> ideProblemMarkerInfoList;
	ArrayList<ProblemMarkerInfo> xmlFilePromblemMarkerInfoList;
	boolean debug = false;
	
	public CarbideErrorParserTestHarness(IProject project, 
			  IProgressMonitor monitor, 
			  String[] errorParserIds, 
			  IPath workingDir) {
		super(project, monitor, errorParserIds, workingDir);
		this.project = project;
		ideProblemMarkerInfoList = new ArrayList<ProblemMarkerInfo>();
		xmlFilePromblemMarkerInfoList = new ArrayList<ProblemMarkerInfo>();
	}
	
	public void resetInternalLists() {
		ideProblemMarkerInfoList.clear();
		xmlFilePromblemMarkerInfoList.clear();
	}
	
	private String readFile(java.io.File consoleOutput) {
		try {
			return new String(FileUtils.readFileContents(consoleOutput, null));
		} catch (CoreException e) {
			Assert.fail(e.toString());
			return null;
		}
	}
	
	public void writeFileContentsToStdout(java.io.File consoleOutput) {
		writeStringToStdout(readFile(consoleOutput));
	}

	// Drive the whole parsing mechanism, stdout is output normally came from console outout
	public void writeStringToStdout(String consoleOutput) {
		try {
			// pick out the stdout stream directly, where CDT error parser sniff
			// for error messages from build console
			stdoutStream.getProject().deleteMarkers(ICModelMarker.C_MODEL_PROBLEM_MARKER, true, IResource.DEPTH_INFINITE); // clear UI list before we start
			stdoutStream.clearScratchBuffer();	// some error parser do multiple lines
			consoleOutput += "\n";	// force the input file to flush all lines
			stdoutStream.write(consoleOutput.getBytes());
			stdoutStream.flush();
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}
	
	// this is ridiculous, project.findMarkers returns array of reverse order on every alternate runs...
	// need to intercept and keep track ourself
	// reportProblems() calls this from base class
	public void addMarker(ProblemMarkerInfo problemMarkerInfo) {
		ideProblemMarkerInfoList.add(problemMarkerInfo);
		super.addMarker(problemMarkerInfo);
	}
	
	// Read in error markers from IDE to this test harness and set up internal marker list
	public void getMarkersFromIde() {
		// flush out markers into project, that's the only way to tap into 
		// markers reported by error parsers in current API
		ideProblemMarkerInfoList.clear();
		stdoutStream.reportProblems();
	}
	
	/**
	 * Do the comparison of the parsed results from the console and the expected results (control file)
	 * @param consoleOutput - The output from the real build
	 * @param controlXml - The XML file with the expected results
	 * @return true on success, otherwise false.
	 */
	public boolean parseStringAndTestAgainstXML(java.io.File consoleOutput, java.io.File controlXml) {
		try {
			return parseStringAndTestAgainstXML(readFile(consoleOutput), new FileInputStream(controlXml));
		} catch (FileNotFoundException e) {
			Assert.fail(e.toString());
		}
		return false;
	}
	
	/**
	 * Wrapper for parseStringAndTestAgainstXML().
	 * @param consoleOutput - The output from the real build
	 * @param xmlInputStream - The XML file with the expected results
	 * @return
	 */
	public boolean parseStringAndTestAgainstXML (String consoleOutput, InputStream xmlInputStream) {
        // make slashes match what the tool would really generate
		if (HostOS.IS_UNIX)
			consoleOutput = consoleOutput.replaceAll("\\\\(?!\r|\n)", "/");
		writeStringToStdout(consoleOutput);
		return testIdeMarkerAgainstXML (xmlInputStream);
	}
	
	/**
	 * Diff the IDE marker list against XML, assuming former is set up properly
	 */ 
	public boolean testIdeMarkerAgainstXML(InputStream xmlInputStream) {
		// load data from IDE and control file
		getMarkersFromIde();
		readControlXML(xmlInputStream);
		
		if (ideProblemMarkerInfoList.size() != xmlFilePromblemMarkerInfoList.size()) {
			if (debug) {
				java.io.File file;
				try {
					file = FileUtils.pluginRelativeFile(TestPlugin.getDefault(), "data/currentOutput.xml");
					writeRegressionXMLFile(new java.io.PrintStream(file));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Assert.fail("IDE contains " + ideProblemMarkerInfoList.size() + " markers and Control file contains " + xmlFilePromblemMarkerInfoList.size());
			return false;
		}
		
		for (int i = 0; i < ideProblemMarkerInfoList.size(); i++) {
			if (markerCompare(ideProblemMarkerInfoList.get(i), xmlFilePromblemMarkerInfoList.get(i)) == false) {
				Assert.fail("IDE differs from control file in item index " + i + "\n");
				return false;
			}
		}
		
		return true;
	}
	
	/** Compares two problem markers for each field. If any field of either input doesn't match, the test fails 
	 * 
	 * @param result - The actual parsed result
	 * @param expected - The expected marker info.
	 * @return - true on success, false on any failure.
	 */
	public boolean markerCompare(ProblemMarkerInfo result, ProblemMarkerInfo expected) {
		/* CarbideCommandLauncher.addMarker() does not set file
		if (result.file.toString().equals(expected.file.toString() == false) {
			Assert.fail("Location from IDE marker is " + result.file.toString() +
					", expected value is " + expected.file.toString());
			return false;
		}
		*/
		if (result.lineNumber != expected.lineNumber) {
			Assert.fail("Location from IDE marker is " + result.lineNumber +
						", expected value is " + expected.lineNumber);
			return false;

		}
		if (result.description.equals(expected.description) == false) {
			if (expected.description.equals(EMPTY) == false) {
				Assert.assertEquals("Unexpected message",
						expected.description, result.description);				
			}
		} else {
			if (result.description.equals(expected.description) == false) {
				Assert.assertEquals("Message from IDE marker does not match",
						expected.description, result.description);
				return false;
			}
		}
		if (result.severity != expected.severity) {
			Assert.fail("Severity from IDE marker is " + result.severity +
						", expected value is " + expected.severity);
			return false;
		}
		if (result.variableName == null) {
			if (expected.variableName.equals(EMPTY) == false) {
				Assert.fail("Variable name from IDE marker is null, expected value is " + expected.variableName);				
			}
		} else {
			if (result.variableName.equals(expected.variableName) == false) {
				Assert.fail("Variable name from IDE marker is " + result.variableName +
						",expected value is " + expected.variableName);
				return false;
			}
		}
		if (result.externalPath == null) {
			if (expected.externalPath.toString().equals(EMPTY) == false) {
				Assert.fail("External path string IDE marker is null, expected value is " + expected.externalPath);				
			}			
		} else {
			if (result.externalPath.equals(expected.externalPath) == false) {
				// note: for some reason, on Unix, relative paths become full paths
				if (result.externalPath.makeRelative().equals(expected.externalPath.makeRelative())) {
					// fine
				} else {
					Assert.assertEquals("External path string from IDE marker", expected.externalPath, result.externalPath);
					return false;
				}
			}			
		}
		return true;
	}
	
	// read control case from a XML file and setup internal list
	public void readControlXML(InputStream xmlInputStream) {
		try {
	        SAXBuilder docBuilder = new SAXBuilder();
	        Document doc = docBuilder.build(xmlInputStream);

	        Element root = doc.getRootElement();
	        ElementFilter elementFilter = new ElementFilter(MARKER_INFO);

	        List<?> allMarkerInfo = root.getContent(elementFilter);
	        
	        xmlFilePromblemMarkerInfoList.clear();
	        
	        int markerInfoListSize = allMarkerInfo.size();
	        
	        for (int i = 0; i < markerInfoListSize; i++) {
	        	Object info = allMarkerInfo.get(i);
	        	IResource file = null;
	        	int lineNumber;
	        	String description;
	        	int severity;
	        	String variableName;
	        	IPath externalPath;
	        	
	        	Assert.assertTrue(info instanceof Element);
	        	Element markerInfo = (Element)info;
		        
		        Attribute locationNode = markerInfo.getAttribute(LINE_NUMBER);
		        lineNumber = Integer.parseInt(locationNode.getValue());

		        Attribute messageNode = markerInfo.getAttribute(MESSAGE);
		        description = messageNode.getValue();
		        // make slashes match what the tool would really generate
		        if (HostOS.IS_UNIX) {
		        	description = description.replaceAll("\\\\(?!\r|\n)", "/");	
		        }
		        
		        Attribute severityNode = markerInfo.getAttribute(SEVERITY);
		        severity = Integer.parseInt(severityNode.getValue());

		        Attribute variableNameNode = markerInfo.getAttribute(VARIABLE_NAME);
		        variableName = variableNameNode.getValue();
		        
		        Attribute externalPathStringNode = markerInfo.getAttribute(EXTERNAL_PATH_STRING);
		        externalPath = HostOS.createPathFromString(externalPathStringNode.getValue());		        

	        	ProblemMarkerInfo details = new ProblemMarkerInfo(file, lineNumber, description, severity, variableName, externalPath);
	        	xmlFilePromblemMarkerInfoList.add(details);

	        }
		} catch (JDOMException e) {
			Assert.fail();
		} catch (IOException e) {
			Assert.fail();
		}

	}
	
	// One could prepare control case with this function
	// printSteam could be System.out or new PrintStream(java.io.File file)
	/**
	 * This is used to write the regression file for the console output. 
	 * i.e. these are the expected results. This isn't part of the test, just a utility
	 * to help generate the results for for comparision during the acutal test.
	 */
	public void writeRegressionXMLFile(PrintStream printStream) {		
		try {
	        DefaultJDOMFactory jdomFactory = new DefaultJDOMFactory();
	        Document jdomdoc = jdomFactory.document(jdomFactory.element("root"));
	        Element root = jdomdoc.getRootElement();
	        
	        int index = 0;
	        for (ProblemMarkerInfo marker : ideProblemMarkerInfoList)
	        {
	        	Element child = jdomFactory.element(MARKER_INFO);
	        	Comment comment = jdomFactory.comment("Error Marker at index " + index);
	        	++index;
	        	
		        /* CarbideCommandLauncher.addMarker() does not set file
		        file = marker.getAttribute(IMarker.LOCATION, 0);
				child.setAttribute(LOCATION, new Integer(file).toString()); 
		         */
	        	
		        child.setAttribute(FILE, EMPTY); // file
				child.setAttribute(LINE_NUMBER, new Integer(marker.lineNumber).toString());
				String description;
				if (marker.description == null || marker.description.equals("")) {
					description = EMPTY;
				} else {
					description = marker.description;
				}			
				child.setAttribute(MESSAGE, description);
				child.setAttribute(SEVERITY, new Integer(marker.severity).toString());
				String variableName;
				if (marker.variableName == null || marker.variableName.equals("")) {
					variableName = EMPTY;
				} else {
					variableName = marker.variableName;
				}
				child.setAttribute(VARIABLE_NAME, variableName);
				String externalPathString;
				if (marker.externalPath == null || marker.externalPath.equals("")) {
					externalPathString = EMPTY;
				} else {
					externalPathString = marker.externalPath.toPortableString();
				}
				child.setAttribute(EXTERNAL_PATH_STRING, externalPathString);
				
				root.addContent(comment);
				root.addContent(child);
	        }

			// drop JAXP for JDOM
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			outputter.output(jdomdoc, printStream);

	        // print xml
	        printStream.flush();
	        printStream.close();
        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
