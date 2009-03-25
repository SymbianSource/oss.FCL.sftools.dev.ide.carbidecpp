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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SkipActionComposite extends Composite {

	private Text statements;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 */
	public SkipActionComposite(Composite parent, int style, SkipActionPage page) {
		super(parent, style);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		setLayout(gridLayout);

		final Label statementsToSkipLabel = new Label(this, SWT.NONE);
		statementsToSkipLabel.setText("Statements to skip:");

		statements = new Text(this, SWT.BORDER);
		statements.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		//
		statements.setText(Integer.toString(page.getSkipAction().getStatements()));

		final Label useCautionSkippingLabel = new Label(this, SWT.NONE);
		useCautionSkippingLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED));
		final GridData gridData = new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 2, 1);
		gridData.verticalIndent = 12;
		useCautionSkippingLabel.setLayoutData(gridData);
		useCautionSkippingLabel.setText("Use Caution, skipping statements may result in unpredictable behavior of your code.");

	}

	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void dispose() {
		super.dispose();
	}

	public int getStatements() {
		int count = 1;
		count = Integer.parseInt(statements.getText());
		return count;
	}

}
