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
package com.nokia.cdt.internal.debug.launch.wizard;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Point;


class WizardNode implements IWizardNode {
	private IWizard wizard;
	private Wizard selected;

	public WizardNode(LaunchWizardSelectionPage parentPage, Wizard selected) {
		this.selected = selected;
	}
	
	public void dispose() {
		if (wizard != null) {
			wizard.dispose();
			wizard = null;
		}
	}
	
	public Point getExtent() {
		return new Point(-1, -1);
	}
	
	/**
	 * Returns the Wizard.
	 */
	public IWizard getWizard() {
		return selected;
	}

	public boolean isContentCreated() {
		return selected != null;
	}
}
