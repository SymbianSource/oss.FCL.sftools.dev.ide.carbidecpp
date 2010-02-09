/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.cdt.internal.debug.launch.newwizard;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * 
 */
public interface IWizardSection {

	public interface ISectionChangeListener {
		void changed();
	}
	
	/** Initialize settings once per wizard (before UI is shown) */
	void initializeSettings();
	
	void createControl(Composite parent);
	
	Control getControl();
	
	/** Get the current status (never <code>null</code>).  This serves as the validation status as well
	 * as being displayed in the wizard validation area. */
	IStatus getStatus();

	/**
	 * @return
	 */
	String getSectionName();

	/**
	 * Set the listener notified when the Change button is clicked.
	 */
	void setChangeListener(ISectionChangeListener listener);
}
