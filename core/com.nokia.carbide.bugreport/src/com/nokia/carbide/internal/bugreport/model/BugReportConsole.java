/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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
 
package com.nokia.carbide.internal.bugreport.model;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.IWorkbenchPage;

import com.nokia.carbide.internal.bugreport.plugin.BugReporterPlugin;

/**
 * Handles the printing to the Bug_Reports console
 *
 */
public class BugReportConsole implements IDocumentListener {

	final static String CONSOLE_NAME = "Bug_Reports"; //$NON-NLS-1$
	MessageConsole console = null;
	ConsoleLine currentLine = null;
	
	public BugReportConsole() {
		console = findConsole(CONSOLE_NAME);
	}
	
	public void documentAboutToBeChanged(DocumentEvent arg0) {
	}

	/**
	 * Called when console is written. When console is written 
	 * we will add the hyperlink to the added line here.
	 */
	public void documentChanged(DocumentEvent arg0) {
		if (currentLine == null)
			return;
		IDocument doc = console.getDocument();

		try {
			IRegion reg = doc.getLineInformation(doc.getNumberOfLines()-1);
			int offset = reg.getOffset() + currentLine.getLinkOffset();
			int length = currentLine.getLinkLength();
			console.addHyperlink(currentLine, offset, length);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		currentLine = null;
		doc.removeDocumentListener(this); // there is a good reason why we add and remove this all the time
	}
	
	/**
	 * Writes the given line to the console.
	 * @param line line to be written to the console
	 */
	public void addLineToConsole(ConsoleLine line) {
		currentLine = line;
		IDocument doc = console.getDocument();
		try {
			
			// There is a good reason why we add and remove this all the time.
			// Because we always search existing console with findConsole, we would 
			// add multimple instances of this class as a listener if we would say 
			// addDocumentListener(this) in the findConsole method. addListener would 
			// get called each time wizard is launched with a new instance of "this".
			doc.addDocumentListener(this);
			
			MessageConsoleStream out = console.newMessageStream();
			if (doc.getLength() == 0) { // don't all line break before first line
				out.print(line.getLineText());
			} else { // were not writing the first line, add line break
				out.println();
				out.print(line.getLineText());
			}
			out.flush();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			currentLine = null;
			doc.removeDocumentListener(this); // there is a good reason why we add and remove this all the time
		}
	}
	
	/**
	 * Shows Bug_Report console view.
	 *
	 */
	public static void showYourself() {
		MessageConsole bugReportsConsole = findConsole(CONSOLE_NAME);
		if (bugReportsConsole == null)
			return;
		
		try {		
			IWorkbenchPage page = BugReporterPlugin.getDefault().
									getWorkbench().
										getActiveWorkbenchWindow().
											getActivePage();
			String id = IConsoleConstants.ID_CONSOLE_VIEW;
			IConsoleView view = (IConsoleView) page.showView(id);
			view.display(bugReportsConsole);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Finds and returns Bug_Reports console. If Bug_Reports console 
	 * has not yet been created, this method creates it and returns it.
	 * @param name console name
	 * @return MessageConsole
	 */
	private static MessageConsole findConsole(String name) {
		try {
			ConsolePlugin plugin = ConsolePlugin.getDefault();
			IConsoleManager conMan = plugin.getConsoleManager();
			IConsole[] existing = conMan.getConsoles();
			for (int i = 0; i < existing.length; i++)
				if (name.equals(existing[i].getName())) 
					return (MessageConsole) existing[i];
			// no console found, so create a new one
			MessageConsole myConsole = new MessageConsole(name, null);
			conMan.addConsoles(new IConsole[]{myConsole});
			return myConsole;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	

}
