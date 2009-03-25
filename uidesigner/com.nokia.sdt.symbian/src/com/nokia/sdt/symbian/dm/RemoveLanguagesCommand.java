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

import org.eclipse.emf.common.command.AbstractCommand;

import java.util.ArrayList;

public class RemoveLanguagesCommand extends AbstractCommand {

	private DesignerDataModel model;
	private SymbianLanguage requestedLanguages[];
	private ArrayList<ILocalizedStringTable> removedLanguages = new ArrayList<ILocalizedStringTable>();

	public RemoveLanguagesCommand(IDesignerDataModel model, SymbianLanguage[] requestedLanguages) {
		this.model = (DesignerDataModel) model;
		this.requestedLanguages = requestedLanguages;
		setLabel(Messages.getString("RemoveLanguagesCommand.undoLabel")); //$NON-NLS-1$
	}
	
	protected boolean prepare() {
		ILocalizedStringBundle stringBundle = model.getDesignerData().getStringBundle();
		for (SymbianLanguage sl : requestedLanguages) {
			Language language = new Language(sl.code);
			ILocalizedStringTable table = stringBundle.findLocalizedStringTable(language);
			if (table != null) {
				removedLanguages.add(table);
			}
		}
		return true;
	}

	public void execute() {
		ILocalizedStringBundle stringBundle = model.getDesignerData().getStringBundle();
		for (ILocalizedStringTable lst : removedLanguages) {
			stringBundle.getLocalizedStringTables().remove(lst);
		}
	}

	public void redo() {
		execute();
	}

	public void undo() {
		ILocalizedStringBundle stringBundle = model.getDesignerData().getStringBundle();
		for (ILocalizedStringTable lst : removedLanguages) {
			stringBundle.getLocalizedStringTables().add(lst);
		}
		
	}
}
