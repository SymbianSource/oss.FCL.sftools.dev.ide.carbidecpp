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

package com.nokia.carbide.cpp.internal.project.ui.editors.inf.testapi;

import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

import com.nokia.carbide.cpp.internal.project.ui.editors.inf.BldInfEditor;

/**
 * An interface for testing the Bld.inf Editor
 */
public class BldInfEditorTester {

	/**
	 * Opens the Overview page of the Bld.inf editor and returns the page.
	 * @param editor - editor part provided by caller
	 * @return Overview page if success, null otherwise
	 */
	public static FormPage getOverviewPage(FormEditor editor) {
		if (editor != null && editor instanceof BldInfEditor) {
			BldInfEditor infEditor = (BldInfEditor) editor;
			return infEditor.getOverviewPage();
		}
		return null;
	}

	/**
	 * Opens the Exports page of the Bld.inf editor and returns the page.
	 * @param editor - editor part provided by caller
	 * @return Exports page if success, null otherwise
	 */
	public static FormPage getExportsPage(FormEditor editor) {
		if (editor != null && editor instanceof BldInfEditor) {
			BldInfEditor infEditor = (BldInfEditor) editor;
			return infEditor.getExportsPage();
		}
		return null;
	}

	/**
	 * Opens the text editor page of the Bld.inf editor and returns the page.
	 * @param editor - editor part provided by caller
	 * @return text editor page if success, null otherwise
	 */
	public static TextEditor getTextEditorPage(FormEditor editor) {
		if (editor != null && editor instanceof BldInfEditor) {
			BldInfEditor infEditor = (BldInfEditor) editor;
			return infEditor.getTextEditorPage();
		}
		return null;
	}

}
