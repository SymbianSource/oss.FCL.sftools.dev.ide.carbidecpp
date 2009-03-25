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
package com.nokia.sdt.symbian.dm;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.emf.dm.ILocalizedStringBundle;
import com.nokia.sdt.emf.dm.Language;
import com.nokia.sdt.symbian.Messages;

import org.eclipse.emf.common.command.AbstractCommand;

/**
 * EMF command to set the default language for a single
 * data model. Does not set the ISymbianProjectContext's
 * current language or synchronize across models.
 */
public class SetDefaultLanguageCommand extends AbstractCommand {
	
	private ILocalizedStringBundle bundle;
	private Language newLanguage;
	private Language oldLanguage;

	public SetDefaultLanguageCommand(IDesignerDataModel model,
			Language language) {
		this.newLanguage = language;
		DesignerDataModel dm = (DesignerDataModel) model;
		this.bundle = dm.getDesignerData().getStringBundle();
		this.oldLanguage = this.bundle.getDefaultLanguage();
		this.setLabel(Messages.getString("SetDefaultLanguageCommand.undoText")); //$NON-NLS-1$
	}
	
	protected boolean prepare() {
		return true;
	}

	public void execute() {
		bundle.setDefaultLanguage(newLanguage);
	}

	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		bundle.setDefaultLanguage(oldLanguage);
	}
}
