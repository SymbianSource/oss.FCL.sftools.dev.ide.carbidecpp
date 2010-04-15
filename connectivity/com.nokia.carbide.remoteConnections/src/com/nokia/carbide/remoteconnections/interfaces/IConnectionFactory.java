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

import org.eclipse.swt.widgets.Composite;

import java.util.Map;

/**
 * The interface for a connection factory
 */
public interface IConnectionFactory {
	
	interface IValidationErrorReporter {
		void setErrorMessage(String newMessage);
	}
	
	/**
	 * Creates a composite with UI for editing settings, optionally initialized with initialSettings.<br>
	 * Can provide optional errorReporter and optional listener. Assumes parent has GridLayout
	 * @param parent Composite
	 * @param errorReporter IValidationErrorReporter
	 * @param initialSettings Map may be <code>null</code>
	 * @deprecated use IConnectionFactory2.createEditingUI(Composite, IValidationErrorReporter, Map, com.nokia.carbide.remoteconnections.interfaces.IConnectionFactory2.ISettingsChangedListener)
	 */
	void createEditingUI(Composite parent, IValidationErrorReporter errorReporter, Map<String, String> initialSettings);

	/**
	 * Returns the settings from the editing UI as name value pairs
	 * @return Map
	 * @deprecated use IConnectionFactory2.IEditingUI.getSettings() or IConnectionFactory2.getDefaultSettings()
	 */
	Map<String, String> getSettingsFromUI();

	/**
	 * Create a new connection from settings or default settings if <code>null</code>
	 * @param settings Map may be <code>null</code>
	 * @return IConnection
	 */
	IConnection createConnection(Map<String, String> settings);

}