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
package com.nokia.carbide.cdt.internal.builder.ui;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.nokia.carbide.cdt.builder.BuilderPreferenceConstants;
import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;

/**
 * Class used to initialize default preference values.
 */
public class BuilderPreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = CarbideBuilderPlugin.getDefault().getPreferenceStore();
		store.setDefault(BuilderPreferenceConstants.PREF_USE_BUILIN_X86_VARS, true);
		store.setDefault(BuilderPreferenceConstants.PREF_BUILD_TEST_COMPS, true);
		store.setDefault(BuilderPreferenceConstants.PREF_MANAGE_DEPENDENCIES, true);
		store.setDefault(BuilderPreferenceConstants.PREF_USE_CONCURRENT_BUILDING, true);
		store.setDefault(BuilderPreferenceConstants.PREF_CONCURRENT_BUILD_JOBS, 4); //TODO see how Runtime.getRuntime().availableProcessors() works, or maybe use NUMBER_OF_PROCESSORS env variable?
		store.setDefault(BuilderPreferenceConstants.PREF_USE_INCREMENTAL_BUILDER, false);
		store.setDefault(BuilderPreferenceConstants.PREF_CLEAN_LEVEL, 0);
		store.setDefault(BuilderPreferenceConstants.PREF_MMP_CHANGED_ACTION_PROMPT, true);
		store.setDefault(BuilderPreferenceConstants.PREF_DEFAULT_MMP_CHANGED_ACTION, 0);
		store.setDefault(BuilderPreferenceConstants.PREF_MAKE_ENGINE, "make"); //$NON-NLS-1$
	}

}
