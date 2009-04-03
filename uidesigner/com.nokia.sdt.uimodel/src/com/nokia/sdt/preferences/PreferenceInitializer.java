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
package com.nokia.sdt.preferences;

import com.nokia.sdt.uimodel.UIModelPlugin;

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.osgi.framework.Bundle;

import java.io.IOException;
import java.net.URL;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = UIModelPlugin.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_PROMPT_BEFORE_EVENT_SAVE, true);
		store.setDefault(PreferenceConstants.P_DOUBLE_CLICK_BEHAVIOR, PreferenceConstants.EDIT_LABEL);
		store.setDefault(PreferenceConstants.P_PROMPT_BEFORE_UNDOING_SOURCE_SYNC, true);
		try {
			store.setDefault(PreferenceConstants.P_FILE_HEADER_TEMPLATE_LOCATION, getPathToDefaultTemplateLocation());
		} catch (IOException e) {
			// leave pref empty
		}
		
	/*
		store.setDefault(PreferenceConstants.P_BOOLEAN, true);
		store.setDefault(PreferenceConstants.P_CHOICE, "choice2");
		store.setDefault(PreferenceConstants.P_STRING,
				"Default value");
	*/
	}

	private String getPathToDefaultTemplateLocation() throws IOException {
		String pathStr = ""; //$NON-NLS-1$
		Bundle bundle = Platform.getBundle("com.nokia.sdt.component.symbian"); //$NON-NLS-1$
        URL url = Platform.find(bundle, new Path(".")); //$NON-NLS-1$
        if (url != null) {
			url = Platform.resolve(url);
			IPath path = new Path(url.getPath());
			path = path.append("sourceFileHeader.txt"); //$NON-NLS-1$
			pathStr = path.toOSString();
        }
		return pathStr;
	}
}
