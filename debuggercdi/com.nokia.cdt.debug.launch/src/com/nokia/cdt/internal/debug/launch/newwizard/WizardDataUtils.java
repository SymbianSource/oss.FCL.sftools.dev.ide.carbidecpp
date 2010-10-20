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

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.osgi.service.prefs.Preferences;


/**
 *
 */
public class WizardDataUtils {

	/** Get current workspace setting */
	public static boolean isWorkspaceBuildBeforeLaunch() {
		// here's how to get the prefs from a plugin's #getPreferenceStore() without violating access
		String prefId = IDebugUIConstants.PREF_BUILD_BEFORE_LAUNCH;
		int idx = prefId.lastIndexOf('.');
		String plugin = prefId.substring(0, idx);
		Preferences node = Platform.getPreferencesService().getRootNode().node(InstanceScope.SCOPE).node(plugin);
		return node.getBoolean(prefId, true);
	}


}
