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


package com.nokia.carbide.remoteconnections.tests.extensions;

import com.nokia.carbide.remoteconnections.interfaces.*;

import org.eclipse.swt.widgets.Composite;

import java.util.Collections;
import java.util.Map;

/**
 *
 */
public class NoSettingsConnectionType implements IConnectionType {

	public IConnectionFactory getConnectionFactory() {
		return new IConnectionFactory() {

			public IConnection createConnection(Map<String, String> settings) {
				return new IConnection() {

					private String name;
					private String id;

					public void dispose() {
					}

					public IConnectionType getConnectionType() {
						return NoSettingsConnectionType.this;
					}

					public String getDisplayName() {
						return name;
					}

					public String getIdentifier() {
						return id;
					}

					public Map<String, String> getSettings() {
						return Collections.EMPTY_MAP;
					}

					public void setDisplayName(String name) {
						this.name = name;
					}

					public void setIdentifier(String id) {
						this.id = id;
					}

					public void updateSettings(Map<String, String> newSettings) {
					}

					public void useConnection(boolean use) {
					}
				};
			}

			public void createEditingUI(Composite parent, IValidationErrorReporter errorReporter, Map<String, String> initialSettings) {
			}

			public Map<String, String> getSettingsFromUI() {
				return Collections.EMPTY_MAP;
			}
			
		};
	}

	public String getDisplayName() {
		return "No Settings Connection";
	}
	
	public String getDescription() {
		return "This no settings connection type has no settings to set";
	}

	public String getHelpContext() {
		return null;
	}

	public String getIdentifier() {
		return getClass().getName();
	}

}
