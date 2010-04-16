/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.remoteconnections.interfaces;

import java.util.Map;

import org.eclipse.swt.widgets.Composite;

/**
 * An extended interface for a connection factory.
 */
public interface IConnectionFactory2 extends IConnectionFactory {

	public static final String PREFERRED_CONNECTION_NAME = "preferred-connection-name"; //$NON-NLS-1$

	public interface ISettingsChangedListener {
		void settingsChanged();
	}

	/**
	 * Creates a composite with UI for editing settings, optionally initialized with initialSettings.<br>
	 * Can provide optional errorReporter and optional listener. Assumes parent has GridLayout
	 * @param parent Composite
	 * @param errorReporter IValidationErrorReporter
	 * @param initialSettings Map may be <code>null</code>
	 * @param settingsListener ISettingsChangedListener
	 */
	void createEditingUI(Composite parent, IValidationErrorReporter errorReporter, 
			Map<String, String> initialSettings, ISettingsChangedListener settingsListener);
}
