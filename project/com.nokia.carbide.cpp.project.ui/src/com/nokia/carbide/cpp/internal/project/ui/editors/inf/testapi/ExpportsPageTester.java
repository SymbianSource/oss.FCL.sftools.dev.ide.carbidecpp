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

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;

import com.nokia.carbide.cpp.internal.project.ui.editors.inf.ExportSectionPart;
import com.nokia.carbide.cpp.internal.project.ui.editors.inf.ExportsPage;

/**
 * An interface for testing the Exports page of the Bld.inf Editor
 */
public class ExpportsPageTester {

	/**
	 * Retrieves the Exports section from an Exports page.
	 * @param page - Exports page provided by caller
	 * @return - Exports section if success, null otherwise
	 */
	public static SectionPart getExportsSection(FormPage page) {
		if (page != null && page instanceof ExportsPage) {
			ExportsPage exportsPage = (ExportsPage) page;
			return exportsPage.getExportsSection();
		}
		return null;
	}

	/**
	 * Retrieves the Test Exports section from an Exports page.
	 * @param page - Exports page provided by caller
	 * @return - Test Exports section if success, null otherwise
	 */
	public static SectionPart getTestExportsSection(FormPage page) {
		if (page != null && page instanceof ExportsPage) {
			ExportsPage exportsPage = (ExportsPage) page;
			return exportsPage.getTestExportsSection();
		}
		return null;
	}

	/**
	 * Retrieves the table viewer of an Exports section.
	 * @param part - Exports section part provided by caller
	 * @return - table viewer if success, null otherwise
	 */
	public static TableViewer getComponentsTableViewer(SectionPart part) {
		if (part != null && part instanceof ExportSectionPart) {
			ExportSectionPart ePart = (ExportSectionPart) part;
			return ePart.getTableViewer();
		}
		return null;
	}

	/**
	 * Retrieves the "Add" button widget of an Exports section.
	 * @param part - Exports section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getAddButton(SectionPart part) {
		if (part != null && part instanceof ExportSectionPart) {
			ExportSectionPart ePart = (ExportSectionPart) part;
			return ePart.getAddButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Edit" button widget of an Exports section.
	 * @param part - Exports section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getEditButton(SectionPart part) {
		if (part != null && part instanceof ExportSectionPart) {
			ExportSectionPart ePart = (ExportSectionPart) part;
			return ePart.getEditButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Up" button widget of an Exports section.
	 * @param part - Exports section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getUpButton(SectionPart part) {
		if (part != null && part instanceof ExportSectionPart) {
			ExportSectionPart ePart = (ExportSectionPart) part;
			return ePart.getUpButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Down" button widget of an Exports section.
	 * @param part - Exports section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getDownButton(SectionPart part) {
		if (part != null && part instanceof ExportSectionPart) {
			ExportSectionPart ePart = (ExportSectionPart) part;
			return ePart.getDownButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Remove" button widget of an Exports section.
	 * @param part - Exports section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getRemoveButton(SectionPart part) {
		if (part != null && part instanceof ExportSectionPart) {
			ExportSectionPart ePart = (ExportSectionPart) part;
			return ePart.getRemoveButton();
		}
		return null;
	}

	/**
	 * Retrieves the export file dialog launched from an Exports section.
	 * @param part - Exports section part provided by caller
	 * @return - export file dialog if success, null otherwise
	 */
	public static Dialog getExportFileDialog(SectionPart part) {
		if (part != null && part instanceof ExportSectionPart) {
			ExportSectionPart ePart = (ExportSectionPart) part;
			return ePart.getExportFileDialog();
		}
		return null;
	}

}
