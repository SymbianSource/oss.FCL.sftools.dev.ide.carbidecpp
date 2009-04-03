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

package com.nokia.sdt.symbian.ui.appeditor;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.emf.dm.ILocalizedStringBundle;
import com.nokia.sdt.emf.dm.Language;
import com.nokia.sdt.symbian.dm.*;
import com.nokia.sdt.symbian.workspace.ISymbianProjectContext;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.workspace.IProjectContext;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.editor.FormEditor;


	/**
	 * Helper class to encapsulate the shared state which may
	 * be needed by various parts of the application editor, e.g.
	 * pages and blocks.
	 *
	 */
class AppEditorContext {
	
	public static final String OVERVIEW_PAGE_ID = "overview"; //$NON-NLS-1$
	public static final String VIEWS_PAGE_ID = "views"; //$NON-NLS-1$
	public static final String LANGUAGES_PAGE_ID = "languages"; //$NON-NLS-1$

	private ApplicationEditor	applicationEditor;
	private IEditorSite			editorSite;
	private S60ModelUtils.S60RootModelType s60RootModelType;
	private UIQModelUtils.UIQRootModelType uiqRootModelType;
	private int appUiPageIndex;
		
	public AppEditorContext(ApplicationEditor applicationEditor) {
		this.applicationEditor = applicationEditor;
		this.editorSite = applicationEditor.getEditorSite();
		this.s60RootModelType = S60ModelUtils.getS60RootModelType(applicationEditor.getDataModel());
		this.uiqRootModelType = UIQModelUtils.getUIQRootModelType(applicationEditor.getDataModel());
		appUiPageIndex = -1;
	}
	
	IEditorSite getEditorSite() {
		return editorSite;
	}
	
	FormEditor getFormEditor() {
		return applicationEditor;
	}
	
	CommandStack getCommandStack() {
		return applicationEditor.getCommandStack();
	}

	IDesignerDataModel getRootDataModel() {
		return applicationEditor.getDataModel();
	}
	
	S60ModelUtils.S60RootModelType getS60RootModelType() {
		return s60RootModelType;
	}
	
	UIQModelUtils.UIQRootModelType getUIQRootModelType() {
		return uiqRootModelType;
	}
	
	Language getDisplayLanguage() {
		return getStringBundle().getDefaultLanguage();
	}
	
	ILocalizedStringBundle getStringBundle() {
		DesignerDataModel dm = (DesignerDataModel) getRootDataModel();
		return dm.getDesignerData().getStringBundle();
	}
	
	void setDisplayLanguage(Language language) {
		Check.checkArg((language));
		getStringBundle().setDefaultLanguage(language);
	}
	
	ISymbianProjectContext getSymbianProjectContext() {
		ISymbianProjectContext result = null;
		IProjectContext projectContext = getRootDataModel().getProjectContext();
		if (projectContext != null) {
			result = (ISymbianProjectContext) projectContext.getAdapter(ISymbianProjectContext.class);
		}
		return result;
	}
	
	/**
	 * Adds the command to the command stack and executes it
	 */
	void addAndExecuteCommand(Command command) {
		getCommandStack().execute(command);
	}
	
	void setAppUiPageIndex(int index) {
		appUiPageIndex = index;
	}
	
	int getAppUiPageIndex() {
		return appUiPageIndex;
	}

}
