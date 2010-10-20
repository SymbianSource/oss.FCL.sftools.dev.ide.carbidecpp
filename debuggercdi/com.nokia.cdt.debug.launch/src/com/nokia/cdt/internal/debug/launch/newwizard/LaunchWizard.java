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

import com.nokia.cdt.internal.debug.launch.wizard.LaunchOptions;

/**
 * New launch wizard for Carbide 3.0.
 * 
 * See https://xdabug001.ext.nokia.com/bugzilla/show_bug.cgi?id=10419
 */
public class LaunchWizard extends AbstractLaunchWizard {
	 
	public LaunchWizard(LaunchOptions launchOptions) {
		super(launchOptions, Messages.getString("LaunchWizard.Title")); //$NON-NLS-1$
	}

	@Override
	protected AbstractUnifiedLaunchOptionsPage createMainPage(IWizardData data) {
		return new UnifiedLaunchOptionsPage(data);
	}

	@Override
	protected IWizardData createWizardData(LaunchOptions launchOptions) {
		LaunchWizardData wizardData = new LaunchWizardData();
		wizardData.initialize(launchOptions);
		return wizardData;
	}

}
