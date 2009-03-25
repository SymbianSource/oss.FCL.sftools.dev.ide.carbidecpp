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
 
package com.nokia.carbide.internal.bugreport.ui.wizards;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.jface.wizard.*;

import com.nokia.carbide.internal.bugreport.resources.HelpContextIDs;
import com.nokia.carbide.internal.bugreport.resources.Messages;

/**
 * This summary wizard page shows the summary of everything what will 
 * be sent to the server.
 *
 */
public class SummaryPage extends WizardPage {

	Text systemDetailsText;

	public SummaryPage(){
		super(Messages.getString("SummaryPage.BugReporting")); //$NON-NLS-1$
			
		setTitle(Messages.getString("SummaryPage.ReportSummary")); //$NON-NLS-1$
			
		setDescription(Messages.getString("SummaryPage.ReviewReport")); //$NON-NLS-1$
		
		// User cannot finish the page before some valid 
		// selection is made.
		setPageComplete(false);
	 }

	public void createControl(Composite parent) {
		Composite composite =  new Composite(parent, SWT.NULL);
		
	    // create the desired layout for this wizard page
		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		composite.setLayout(gl);
		
		systemDetailsText = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData summaryGD = new GridData(GridData.FILL_BOTH);
		systemDetailsText.setLayoutData(summaryGD);
		
		setControl(composite);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, HelpContextIDs.BUG_DESCRIPTION_PAGE);
	}
	
	/**
	 * Sets the summary text which is shown to user.
	 * @param text text which is shown to user as a summary
	 */
	public void setSummaryText(String text) {
		systemDetailsText.setText(text);
	}
}
