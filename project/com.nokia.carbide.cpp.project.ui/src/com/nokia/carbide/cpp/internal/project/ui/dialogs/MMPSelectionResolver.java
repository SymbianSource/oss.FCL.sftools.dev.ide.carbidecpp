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
package com.nokia.carbide.cpp.internal.project.ui.dialogs;

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMMPReference;
import com.nokia.carbide.cpp.internal.api.project.core.IMMPSelectionResolver;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;

import java.util.ArrayList;
import java.util.List;

public class MMPSelectionResolver implements IMMPSelectionResolver {

	private final String message;

	public MMPSelectionResolver(String mmpSelectionDialogMessage) {
		this.message = mmpSelectionDialogMessage;
		
	}
	
	public List<IMMPReference> addSourceToWhichMMPs(final List<IMMPReference> allMMPs, final List<IMMPReference> mmpsContainingSourcePath) {
		final List<IMMPReference> mmps = new ArrayList<IMMPReference>(0);
		
		// run in the UI thread
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		if (window == null) {
			IWorkbenchWindow windows[] = workbench.getWorkbenchWindows();
			if (windows.length < 1) {
				// no windows!?  bail.
				return mmps;
			}
			window = windows[0];
		}

		final Shell shell = window.getShell();

		shell.getDisplay().syncExec(new Runnable() {
			public void run() {
				SelectMMPsForNewSourceDialog dlg = new SelectMMPsForNewSourceDialog(shell, message);
				dlg.setLists(allMMPs, mmpsContainingSourcePath);
				if (Dialog.OK == dlg.open()) {
					for (Object mmp: dlg.getResult()) {
						mmps.add((IMMPReference)mmp);
					}
				}
			}
		});

		return mmps;
	}

}
