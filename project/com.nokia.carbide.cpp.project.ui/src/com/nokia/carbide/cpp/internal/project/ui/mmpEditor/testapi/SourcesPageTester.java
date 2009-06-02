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

import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;

import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.MissingSourcesSectionPart;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.ResourcesSectionPart;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.SourcesPage;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.SourcesSectionPart;

/**
 * An interface for testing the Sources page of the MMP Editor
 */
public class SourcesPageTester {

	/**
	 * Retrieves any error message from the Sources page.
	 * @param page - Sources page provided by caller
	 * @return error message string if success, null otherwise
	 */
	public static String getErrorMesaage(FormPage page) {
		if (page != null && page instanceof SourcesPage) {
			SourcesPage sourcesPage = (SourcesPage) page;
			return sourcesPage.getErrorMesaage();
		}
		return null;
	}

	/**
	 * Retrieves the "C/C++ Sources" section of the Sources page.
	 * @param page - Overview page provided by caller
	 * @return - section part if success, null otherwise
	 */
	public static SectionPart getSourcesSection(FormPage page) {
		if (page != null && page instanceof SourcesPage) {
			SourcesPage sourcesPage = (SourcesPage) page;
			return sourcesPage.getSourcesSection();
		}
		return null;
	}

	/**
	 * Retrieves the "Resources" section of the Sources page.
	 * @param page - Overview page provided by caller
	 * @return - section part if success, null otherwise
	 */
	public static SectionPart getReourcesSection(FormPage page) {
		if (page != null && page instanceof SourcesPage) {
			SourcesPage sourcesPage = (SourcesPage) page;
			return sourcesPage.getReourcesSection();
		}
		return null;
	}

	/**
	 * Retrieves the "Missing C/C++ Sources" section of the Sources page.
	 * @param page - Overview page provided by caller
	 * @return - section part if success, null otherwise
	 */
	public static SectionPart getMissingSourcesSection(FormPage page) {
		if (page != null && page instanceof SourcesPage) {
			SourcesPage sourcesPage = (SourcesPage) page;
			return sourcesPage.getMissingSourcesSection();
		}
		return null;
	}

	/**
	 * Retrieves the tree viewer of the "C/C++ Sources" section.
	 * @param part - "C/C++ Sources" section part provided by caller
	 * @return - tree viewer if success, null otherwise
	 */
	public static CheckboxTreeViewer getSourcesTreeViewer(SectionPart part) {
		if (part != null && part instanceof SourcesSectionPart) {
			SourcesSectionPart srcPart = (SourcesSectionPart) part;
			return srcPart.getTreeViewer();
		}
		return null;
	}

	/**
	 * Retrieves the tree viewer of the "Resources" section.
	 * @param part - "Resources" section part provided by caller
	 * @return - tree viewer if success, null otherwise
	 */
	public static TreeViewer getResourcesTreeViewer(SectionPart part) {
		if (part != null && part instanceof ResourcesSectionPart) {
			ResourcesSectionPart rsrcPart = (ResourcesSectionPart) part;
			return rsrcPart.getTreeViewer();
		}
		return null;
	}

	/**
	 * Retrieves the "Add" button widget of the "Resources" section.
	 * @param part - "Resources" section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getResourceAddButton(SectionPart part) {
		if (part != null && part instanceof ResourcesSectionPart) {
			ResourcesSectionPart rsrcPart = (ResourcesSectionPart) part;
			return rsrcPart.getAddButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Edit" button widget of the "Resources" section.
	 * @param part - "Resources" section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getResourceEditButton(SectionPart part) {
		if (part != null && part instanceof ResourcesSectionPart) {
			ResourcesSectionPart rsrcPart = (ResourcesSectionPart) part;
			return rsrcPart.getEditButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Remove" button widget of the "Resources" section.
	 * @param part - "Resources" section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getResourceRemoveButton(SectionPart part) {
		if (part != null && part instanceof ResourcesSectionPart) {
			ResourcesSectionPart rsrcPart = (ResourcesSectionPart) part;
			return rsrcPart.getRemoveButton();
		}
		return null;
	}

	/**
	 * Retrieves the table viewer of the "Missing C/C++ Sources" section.
	 * @param part - "Missing C/C++ Sources" section part provided by caller
	 * @return - table viewer if success, null otherwise
	 */
	public static TableViewer getMissingSourcesTableViewer(SectionPart part) {
		if (part != null && part instanceof MissingSourcesSectionPart) {
			MissingSourcesSectionPart msrcPart = (MissingSourcesSectionPart) part;
			return msrcPart.getTableViewer();
		}
		return null;
	}

	/**
	 * Retrieves the "Remove" button widget of the "Missing C/C++ Sources" section.
	 * @param part - "Missing C/C++ Sources" section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getMissingSourcesRemoveButton(SectionPart part) {
		if (part != null && part instanceof MissingSourcesSectionPart) {
			MissingSourcesSectionPart msrcPart = (MissingSourcesSectionPart) part;
			return msrcPart.getRemoveButton();
		}
		return null;
	}

}
