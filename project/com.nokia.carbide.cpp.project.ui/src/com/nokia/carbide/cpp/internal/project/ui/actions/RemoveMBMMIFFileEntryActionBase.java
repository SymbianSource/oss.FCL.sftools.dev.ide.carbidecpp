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
package com.nokia.carbide.cpp.internal.project.ui.actions;

import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.IView;

import org.eclipse.jface.dialogs.MessageDialog;

import java.text.MessageFormat;

public abstract class RemoveMBMMIFFileEntryActionBase extends EditMBMMIFFileEntryActionBase {

	public RemoveMBMMIFFileEntryActionBase() {
	}

	/**
	 * @param view
	 */
	protected void removeMbmMifEntryInView(IView view) {
		IMultiImageSource source = findMultiImageSource(view, mbmMifFileEntry.getTargetFilePath());
		
		boolean doit = MessageDialog.openConfirm(getShell(), 
				Messages.getString("RemoveMBMMIFFileEntryActionBase.DeleteEntryTitle"), //$NON-NLS-1$
				MessageFormat.format(
						Messages.getString("RemoveMBMMIFFileEntryActionBase.DeleteEntryDescription"), //$NON-NLS-1$
						new Object[] { mbmMifFileEntry.getTargetFilePath() }));
		if (doit) {
			getMultiImageSources(view).remove(source);
			commitStanza(view);
		}
	}
}
