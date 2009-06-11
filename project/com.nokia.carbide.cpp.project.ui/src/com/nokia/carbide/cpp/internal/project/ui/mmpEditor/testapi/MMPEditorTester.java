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

package com.nokia.carbide.cpp.internal.project.ui.mmpEditor.testapi;

import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.MMPEditor;

/**
 * An interface for testing the MMP Editor
 */
public class MMPEditorTester {

	/**
	 * Opens the Overview page of the MMP editor and returns the page.
	 * @param editor - editor part provided by caller
	 * @return Overview page if success, null otherwise
	 */
	public static FormPage getOverviewPage(FormEditor editor) {
		if (editor != null && editor instanceof MMPEditor) {
			MMPEditor mmpEditor = (MMPEditor) editor;
			return mmpEditor.getOverviewPage();
		}
		return null;
	}

	/**
	 * Opens the Sources page of the MMP editor and returns the page.
	 * @param editor - editor part provided by caller
	 * @return Sources page if success, null otherwise
	 */
	public static FormPage getSourcesPage(FormEditor editor) {
		if (editor != null && editor instanceof MMPEditor) {
			MMPEditor mmpEditor = (MMPEditor) editor;
			return mmpEditor.getSourcesPage();
		}
		return null;
	}

	/**
	 * Opens the Libraries page of the MMP editor and returns the page.
	 * @param editor - editor part provided by caller
	 * @return Libraries page if success, null otherwise
	 */
	public static FormPage getLibrariesPage(FormEditor editor) {
		if (editor != null && editor instanceof MMPEditor) {
			MMPEditor mmpEditor = (MMPEditor) editor;
			return mmpEditor.getLibrariesPage();
		}
		return null;
	}

	/**
	 * Opens the Options page of the MMP editor and returns the page.
	 * @param editor - editor part provided by caller
	 * @return Options page if success, null otherwise
	 */
	public static FormPage getOptionsPage(FormEditor editor) {
		if (editor != null && editor instanceof MMPEditor) {
			MMPEditor mmpEditor = (MMPEditor) editor;
			return mmpEditor.getOptionsPage();
		}
		return null;
	}

	/**
	 * Opens the text editor page of the MMP editor and returns the page.
	 * @param editor - editor part provided by caller
	 * @return text editor page if success, null otherwise
	 */
	public static TextEditor getTextEditorPage(FormEditor editor) {
		if (editor != null && editor instanceof MMPEditor) {
			MMPEditor mmpEditor = (MMPEditor) editor;
			return mmpEditor.getTextEditorPage();
		}
		return null;
	}

	/**
	 * Retrieves the view model of the MMP editor.
	 * @param editor - editor part provided by caller
	 * @return MMP view model if success, null otherwise
	 */
	public static IMMPView getMMPView(FormEditor editor) {
		if (editor != null && editor instanceof MMPEditor) {
			MMPEditor mmpEditor = (MMPEditor) editor;
			return mmpEditor.getMMPView();
		}
		return null;
	}

}
