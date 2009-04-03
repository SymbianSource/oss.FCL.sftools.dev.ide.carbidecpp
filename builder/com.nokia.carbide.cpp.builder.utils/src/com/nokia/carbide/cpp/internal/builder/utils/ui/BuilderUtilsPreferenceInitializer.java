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
* Class used to initialize default preference values.
*
*/
package com.nokia.carbide.cpp.internal.builder.utils.ui;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.nokia.carbide.cpp.internal.builder.utils.Activator;

public class BuilderUtilsPreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(BuilderUtilsPreferenceConstants.PREF_OUTPUT_TO_CONSOLE, true);
		store.setDefault(BuilderUtilsPreferenceConstants.PREF_CPP_ARGUMENTS, "-undef -nostdinc -C"); //$NON-NLS-1$
	}

}
