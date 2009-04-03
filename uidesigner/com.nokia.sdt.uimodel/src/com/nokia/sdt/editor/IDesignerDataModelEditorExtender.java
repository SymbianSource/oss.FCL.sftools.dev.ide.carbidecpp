/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.forms.editor.FormEditor;


/**
 * This extension is used to extend a designer data model editor
 * This extension can be used to add/insert pages:
 * @see IDesignerDataModelEditor#getFormEditor()
 * @see FormEditor#addPage(int, org.eclipse.ui.forms.editor.IFormPage)
 */
public interface IDesignerDataModelEditorExtender {
	
	static final String EXTENSION_ID = "com.nokia.sdt.uidesigner.editorExtender"; //$NON-NLS-1$
	static final String EXTENDER = "class"; //$NON-NLS-1$
	static final String EDITOR_ID_FILTER = "editorIdFilter"; //$NON-NLS-1$
	
	void editorInitialized(IDesignerDataModelEditor editor) throws CoreException;

}
