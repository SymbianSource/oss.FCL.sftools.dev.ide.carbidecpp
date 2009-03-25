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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.Assert;

import org.eclipse.cdt.core.ProblemMarkerInfo;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.nokia.carbide.cdt.builder.builder.CarbideCommandLauncher;

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
		FileReader consoleReader;
		StringBuffer sb = new StringBuffer();
		try {
			consoleReader = new FileReader(consoleOutput);
			char[] buf = new char[1024];
	        int len;
	        while ((len = consoleReader.read(buf)) > 0) {
	            sb.append(buf, 0, len);
	        }
	        consoleReader.close();

		} catch (FileNotFoundException e) {
			Assert.fail();
		} catch (IOException e) {
			Assert.fail();
		}
        return sb.toString();
	}
	
	public void writeFileContentsToStdout(java.io.File consoleOutput) {
		writeStringToStdout(readFile(consoleOutput));
	}

	// Drive the whole parsing mechanism, stdout is output normally came from console outout
	public void writeStringToStdout(String consoleOutput) {
		// pick out the stdout stream directly, where CDT error parser sniff
		// for error messages from build console
		try {
			stdoutStream.write(consoleOutput.getBytes());
		} catch (IOException e) {
			Assert.fail();
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
			Assert.fail();
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
				Assert.fail("Message from IDE marker is null, expected value is " + expected.description);				
			}
		} else {
			if (result.description.equals(expected.description) == false) {
				Assert.fail("Message from IDE marker is " + result.description +
						", expected value is " + expected.description);
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
				Assert.fail("External path string from IDE marker is " + result.externalPath +
								", expected value is " + expected.externalPath);
				return false;
			}			
		}
		return true;
	}
	
	// read control case from a XML file and setup internal list
	public void readControlXML(InputStream xmlInputStream) {
		try {
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder;
			docBuilder = dbfac.newDocumentBuilder();
	        Document doc = docBuilder.parse(xmlInputStream);
	        doc.getDocumentElement().normalize();

	        NodeList markerNodeList = doc.getElementsByTagName(MARKER_INFO);
	        int markerNodeListSize = markerNodeList.getLength();
	        
	        xmlFilePromblemMarkerInfoList.clear();
	        
	        for (int i = 0; i < markerNodeListSize; i++) {
	        	IResource file = null;
	        	int lineNumber;
	        	String description;
	        	int severity;
	        	String variableName;
	        	IPath externalPath;
	        	
	        	Node markerNode = markerNodeList.item(i);
		        NamedNodeMap attributes = markerNode.getAttributes();
		        
		        Node locationNode = attributes.getNamedItem(LINE_NUMBER);
		        lineNumber = Integer.parseInt(locationNode.getNodeValue());

		        Node messageNode = attributes.getNamedItem(MESSAGE);
		        description = messageNode.getNodeValue();
		        
		        Node severityNode = attributes.getNamedItem(SEVERITY);
		        severity = Integer.parseInt(severityNode.getNodeValue());

		        Node variableNameNode = attributes.getNamedItem(VARIABLE_NAME);
		        variableName = variableNameNode.getNodeValue();
		        
		        Node externalPathStringNode = attributes.getNamedItem(EXTERNAL_PATH_STRING);
		        externalPath = new Path(externalPathStringNode.getNodeValue());		        

	        	ProblemMarkerInfo details = new ProblemMarkerInfo(file, lineNumber, description, severity, variableName, externalPath);
	        	xmlFilePromblemMarkerInfoList.add(details);

	        }
		} catch (ParserConfigurationException e) {
			Assert.fail();
		} catch (SAXException e) {
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
			
			// prepare XML tree
	        DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder;

			docBuilder = dbfac.newDocumentBuilder();
	        Document doc = docBuilder.newDocument();

	        Element root = doc.createElement("root");
	        doc.appendChild(root);
	        
	        int index = 0;

			for (ProblemMarkerInfo marker : ideProblemMarkerInfoList)
			{
		        Element child = doc.createElement(MARKER_INFO);
		        Comment comment = doc.createComment("Error Marker at index " + index);
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
					externalPathString = marker.externalPath.toString();
				}
				child.setAttribute(EXTERNAL_PATH_STRING, externalPathString);
				
				root.appendChild(comment);
		        root.appendChild(child);
			}

	        // print
	        TransformerFactory transfac = TransformerFactory.newInstance();
	        Transformer trans = transfac.newTransformer();
	        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        trans.setOutputProperty(OutputKeys.INDENT, "yes");

	        // create string from xml tree
	        StringWriter sw = new StringWriter();
	        StreamResult result = new StreamResult(sw);
	        DOMSource source = new DOMSource(doc);
	        trans.transform(source, result);
	        String xmlString = sw.toString();

	        // print xml
	        printStream.println(xmlString);
	        printStream.flush();
	        printStream.close();
        
		} catch (ParserConfigurationException e) {
			Assert.fail();
		} catch (TransformerConfigurationException e) {
			Assert.fail();
		} catch (TransformerException e) {
			Assert.fail();
		}

	}

}
