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
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.symbian.Messages;
import com.nokia.sdt.symbian.SymbianLanguage;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.AbstractCommand;

import java.util.ArrayList;

public class AddLanguagesCommand extends AbstractCommand {

	private DesignerDataModel model;
	private SymbianLanguage requestedLanguages[];
	private ArrayList<Language> languagesToAdd = new ArrayList<Language>();
	
	public AddLanguagesCommand(IDesignerDataModel model, SymbianLanguage[] languagesToAdd) {
		Check.checkArg(model);
		Check.checkArg(languagesToAdd);
		this.model = (DesignerDataModel) model;
		this.requestedLanguages = languagesToAdd;
		setLabel(Messages.getString("AddLanguagesCommand.undoLabel")); //$NON-NLS-1$
	}

	@Override
	protected boolean prepare() {
		ILocalizedStringBundle stringBundle = model.getDesignerData().getStringBundle();
		// Figure out which of the requested languages really need to be added
		// so undo will delete only those
		for (SymbianLanguage sl : requestedLanguages) {
			Language l = new Language(sl.code);
			if (stringBundle.findLocalizedStringTable(l) == null) {
				languagesToAdd.add(l);
			}
		}
		return true;
	}

	@Override
	public void undo() {
		ILocalizedStringBundle stringBundle = model.getDesignerData().getStringBundle();
		for (Language l : languagesToAdd) {
			ILocalizedStringTable table = stringBundle.findLocalizedStringTable(l);
			if (table != null) {
				stringBundle.getLocalizedStringTables().remove(table);
			}
		}
	}

	public void execute() {
		ILocalizedStringBundle stringBundle = model.getDesignerData().getStringBundle();
		for (Language l : languagesToAdd) {
			stringBundle.addLocalizedStringTable(l);
		}
	}

	public void redo() {
		execute();
	}

}
