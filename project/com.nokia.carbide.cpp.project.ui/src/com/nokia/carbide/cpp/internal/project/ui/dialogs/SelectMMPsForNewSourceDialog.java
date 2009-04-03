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
import com.nokia.carbide.cpp.internal.project.ui.*;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.*;

import java.util.List;

public class SelectMMPsForNewSourceDialog extends ElementListSelectionDialog {
	
	public SelectMMPsForNewSourceDialog(Shell shell, String message) {
		super(shell, new MMPReferenceLabelProvider());
		setShellStyle(getShellStyle() | SWT.RESIZE);
		setTitle(Messages.getString("SelectMMPsForNewSourceDialog.DialogTitle")); //$NON-NLS-1$
		setMessage(message);
		setMultipleSelection(true);
		
		setImage(CarbideUIPlugin.getSharedImages().getImage(ICarbideSharedImages.IMG_CARBIDE_C_ICON_16_16)); //$NON-NLS-1$
		setHelpAvailable(true);
	}

	/*
	 * @see Windows#configureShell
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(newShell, ProjectUIHelpIds.MMP_SELECTION_DIALOG);
	}

	public void setLists(List<IMMPReference> allMMPs, List<IMMPReference> mmpsContainingSourcePath) {
		setElements(allMMPs.toArray());
		setInitialElementSelections(mmpsContainingSourcePath);
	}
}
