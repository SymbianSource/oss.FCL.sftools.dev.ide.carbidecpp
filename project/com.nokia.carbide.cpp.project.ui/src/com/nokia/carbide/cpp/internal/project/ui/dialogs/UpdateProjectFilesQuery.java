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

import com.nokia.carbide.cpp.internal.api.project.core.IUpdateProjectFilesQuery;
import com.nokia.carbide.cpp.internal.api.project.core.ResourceChangeListener;
import com.nokia.carbide.cpp.project.ui.utils.ProjectUIUtils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;

import java.util.List;

public class UpdateProjectFilesQuery implements IUpdateProjectFilesQuery {

	private boolean shouldUpdate;
	
	public boolean shouldUpdate(final IProject project, final IFile file, final List<String> changeDetails, final boolean isAdd) {
		shouldUpdate = false;
		
		// run in the UI thread
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		if (window == null) {
			IWorkbenchWindow windows[] = workbench.getWorkbenchWindows();
			if (windows.length > 0) {
				window = windows[0];
			} else {
				// no windows?  IDE might be shutting down
				return false;
			}
		}

		final Shell shell = window.getShell();

		final UpdateProjectFilesQueryDialog dlg = new UpdateProjectFilesQueryDialog(shell, project, file, changeDetails);

		shell.getDisplay().syncExec(new Runnable() {
			public void run() {
				shouldUpdate = (Dialog.OK == dlg.open());
				if (dlg.rememberDecision()) {
					if (isAdd) {
						ProjectUIUtils.setAddFilesToProjectOption(shouldUpdate ? ResourceChangeListener.UPDATE_FILES_OPTION_ALWAYS : ResourceChangeListener.UPDATE_FILES_OPTION_NEVER);
					} else {
						ProjectUIUtils.setChangedFilesInProjectOption(shouldUpdate ? ResourceChangeListener.UPDATE_FILES_OPTION_ALWAYS : ResourceChangeListener.UPDATE_FILES_OPTION_NEVER);
					}
				}
			}
		});

		return shouldUpdate;
	}

}
