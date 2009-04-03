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
*
*/
package com.nokia.cdt.debug.cw.symbian.breakpointactions;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.cdt.debug.core.breakpointactions.AbstractBreakpointAction;
import org.eclipse.cdt.debug.core.breakpointactions.IResumeActionEnabler;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.model.IBreakpoint;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import com.freescale.cdt.debug.cw.core.cdi.model.Thread;

public class SkipAction extends AbstractBreakpointAction {
	
	private int statements = 1;

	public IStatus execute(IBreakpoint breakpoint, IAdaptable context, IProgressMonitor monitor) {
		IStatus result = Status.OK_STATUS;
		
		ICDIThread cdiThread = (ICDIThread) context.getAdapter(ICDIThread.class);
		
		if (cdiThread != null && cdiThread instanceof Thread)
		{
			Thread cwThread = (Thread) cdiThread;
			int statementCount = statements;
			while (statementCount-- > 0) {
				cwThread.getCWThread().SkipCurrentLine(true); // only skip source statements for now				
			}			
			IResumeActionEnabler resumeEnabler = (IResumeActionEnabler) context.getAdapter(IResumeActionEnabler.class);
			try {
				resumeEnabler.resume();
			} catch (Exception e) { e.printStackTrace(); }
		}
			
		return result;
	}

	public String getDefaultName() {
		return "Skip Action";
	}

	public String getIdentifier() {
		return "com.nokia.cdt.debug.cw.symbian.breakpointactions.SkipAction"; //$NON-NLS-1$
	}

	public String getMemento() {
		String logData = new String(""); //$NON-NLS-1$

		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = dfactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element rootElement = doc.createElement("skipData"); //$NON-NLS-1$
			rootElement.setAttribute("statements", Integer.toString(statements)); //$NON-NLS-1$

			doc.appendChild(rootElement);

			ByteArrayOutputStream s = new ByteArrayOutputStream();

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.METHOD, "xml"); //$NON-NLS-1$
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$

			DOMSource source = new DOMSource(doc);
			StreamResult outputTarget = new StreamResult(s);
			transformer.transform(source, outputTarget);

			logData = s.toString("UTF8"); //$NON-NLS-1$

		} catch (Exception e) {
			e.printStackTrace();
		}
		return logData;
	}

	public String getSummary() {
		return "Skip " + statements + " statements";
	}

	public String getTypeName() {
		return "Skip Action";
	}

	public void initializeFromMemento(String data) {
		Element root = null;
		DocumentBuilder parser;
		try {
			parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			parser.setErrorHandler(new DefaultHandler());
			root = parser.parse(new InputSource(new StringReader(data))).getDocumentElement();
			String value = root.getAttribute("statements"); //$NON-NLS-1$
			if (value == null)
				throw new Exception();
			statements = Integer.valueOf(value).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getStatements() {
		return statements;
	}

}
