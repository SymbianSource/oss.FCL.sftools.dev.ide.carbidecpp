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
 
package com.nokia.carbide.internal.bugreport.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import com.nokia.carbide.internal.bugreport.model.BugReportConsole;
import com.nokia.carbide.internal.bugreport.ui.wizards.*;

/**
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class NewBugReport implements IWorkbenchWindowActionDelegate, IStartup {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public NewBugReport() {
	}
	
	public void earlyStartup() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		runBugReportWizard();
	}

	/**
	 * Shows the Bug_Report Wizard.
	 *
	 */
	void runBugReportWizard() {
		Runnable showWizardRunnable = new Runnable(){
			public void run(){
				BugReporterWizardDialog wizDialog;
				NewBugReportWizard wiz = null;
				wiz = new NewBugReportWizard();
				wizDialog = new BugReporterWizardDialog(window.getShell(), wiz);
				wizDialog.create();		
				wizDialog.getShell().setSize(600, 600);	
				if (wizDialog.open() == 0) // report was sent
					BugReportConsole.showYourself();
			}
		};
		
		Display.getDefault().asyncExec(showWizardRunnable);   		
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}