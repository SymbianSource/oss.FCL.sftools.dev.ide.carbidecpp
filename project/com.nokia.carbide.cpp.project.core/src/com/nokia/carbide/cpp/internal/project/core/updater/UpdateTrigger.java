/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.cpp.internal.project.core.updater;

import com.nokia.carbide.updater.extension.IUpdateTrigger;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.*;

/**
 * Class implementing the com.nokia.carbide.updater.updateTrigger extension point
 * 
 * This implementation will check that the project update dialog was shown at startup once
 */
public class UpdateTrigger extends AbstractUpdateTrigger implements IUpdateTrigger {

	private static final String LOCAL_KEY = ".updateTrigger";
	// use the version of Carbide updating to as the trigger property
	private static final String TRIGGER_PROPERTY = "1.2.0"; //$NON-NLS-1$
	private static final String SYMBIAN_PERSPECTIVE_ID = "com.symbian.cdt.SymbianPerspective"; //$NON-NLS-1$

	@Override
	protected String getLocalKey() {
		return LOCAL_KEY;
	}

	@Override
	protected String getTriggerProperty() {
		return TRIGGER_PROPERTY;
	}

	@Override
	protected void postTriggerAction() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IWorkbench workbench = PlatformUI.getWorkbench();
				IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window == null)
					return;
				IWorkbenchPage page = window.getActivePage();
				if (page == null)
					return;
				String id;
				if (isIntroViewActive(page))
					return;
				IPerspectiveDescriptor descriptor = 
					workbench.getPerspectiveRegistry().findPerspectiveWithId(CARBIDE_PERSPECTIVE_ID);
				page.setPerspective(descriptor);
				IPerspectiveDescriptor[] perspectives = page.getSortedPerspectives();
				for (int i = 0; i < perspectives.length; i++) {
					descriptor = perspectives[i];
					id = descriptor.getId();
					if (id.equals(SYMBIAN_PERSPECTIVE_ID)) {
						page.closePerspective(descriptor, true, true);
						break;
					}
				}
			}
		});
	}

}
