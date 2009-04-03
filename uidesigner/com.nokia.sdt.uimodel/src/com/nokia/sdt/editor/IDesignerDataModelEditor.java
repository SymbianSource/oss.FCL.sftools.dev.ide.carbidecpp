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
package com.nokia.sdt.editor;

import com.nokia.sdt.datamodel.IDesignerDataModel;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.forms.editor.FormEditor;

public interface IDesignerDataModelEditor extends IEditorPart {

	public interface SaveListener {
		/**
		 * @return <code>true</code> if can save, <code>false</code> to veto save
		 */
		boolean queryAboutToSave(IDesignerDataModelEditor editor);
		void preSaveNotify(IDesignerDataModelEditor editor, IProgressMonitor monitor);
		void postSaveNotify(IDesignerDataModelEditor editor, IProgressMonitor monitor);
	}
	
	void setListenToResourceChanges(boolean listen);

	void reload();

	IDesignerDataModel getDataModel();

	String getPartName();

	void addSaveListener(SaveListener listener);

	void removeSaveListener(SaveListener listener);
	
	void refreshFromModel();

	void close(boolean askToSave);
	
	void setInput(IEditorInput input);
	
	void disposeOutlinePage();

	CommandStack getCommandStack();
	
	FormEditor getFormEditor();
	
	void setTabTitle(int pageIndex, String title);
	
	void activatePage(int pageIndex);

	boolean hasOpenError();
}