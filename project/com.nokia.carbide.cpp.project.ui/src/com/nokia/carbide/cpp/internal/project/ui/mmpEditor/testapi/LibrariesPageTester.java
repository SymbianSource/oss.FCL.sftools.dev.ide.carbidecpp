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

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;

import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.LibrariesPage;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.LibrarySectionPart;

/**
 * An interface for testing the Libraries page of the MMP Editor
 */
public class LibrariesPageTester {

	/**
	 * Retrieves any error message from the Libraries page.
	 * @param page - Libraries page provided by caller
	 * @return error message string if success, null otherwise
	 */
	public static String getErrorMessage(FormPage page) {
		if (page != null && page instanceof LibrariesPage) {
			LibrariesPage librariesPage = (LibrariesPage) page;
			return librariesPage.getErrorMessage();
		}
		return null;
	}

	/**
	 * Retrieves the "Libraries" section of the Libraries page.
	 * @param page - Libraries page provided by caller
	 * @return - section part if success, null otherwise
	 */
	public static SectionPart getMainLibrariesSection(FormPage page) {
		if (page != null && page instanceof LibrariesPage) {
			LibrariesPage librariesPage = (LibrariesPage) page;
			return librariesPage.getMainLibrariesSection();
		}
		return null;
	}

	/**
	 * Retrieves the "Static Libraries" section of the Libraries page.
	 * @param page - Libraries page provided by caller
	 * @return - section part if success, null otherwise
	 */
	public static SectionPart getStaticLibrariesSection(FormPage page) {
		if (page != null && page instanceof LibrariesPage) {
			LibrariesPage librariesPage = (LibrariesPage) page;
			return librariesPage.getStaticLibrariesSection();
		}
		return null;
	}

	/**
	 * Retrieves the "Debug Libraries" section of the Libraries page.
	 * @param page - Libraries page provided by caller
	 * @return - section part if success, null otherwise
	 */
	public static SectionPart getDebugLibrariesSection(FormPage page) {
		if (page != null && page instanceof LibrariesPage) {
			LibrariesPage librariesPage = (LibrariesPage) page;
			return librariesPage.getDebugLibrariesSection();
		}
		return null;
	}

	/**
	 * Retrieves the "Win32 Libraries" section of the Libraries page.
	 * @param page - Libraries page provided by caller
	 * @return - section part if success, null otherwise
	 */
	public static SectionPart getWin32LibrariesSection(FormPage page) {
		if (page != null && page instanceof LibrariesPage) {
			LibrariesPage librariesPage = (LibrariesPage) page;
			return librariesPage.getWin32LibrariesSection();
		}
		return null;
	}

	/**
	 * Retrieves the "ASSP Libraries" section of the Libraries page.
	 * @param page - Libraries page provided by caller
	 * @return - section part if success, null otherwise
	 */
	public static SectionPart getASSPLibrariesSection(FormPage page) {
		if (page != null && page instanceof LibrariesPage) {
			LibrariesPage librariesPage = (LibrariesPage) page;
			return librariesPage.getASSPLibrariesSection();
		}
		return null;
	}

	/**
	 * Retrieves the table viewer of a Libraries section.
	 * @param part - Libraries section part provided by caller
	 * @return - table viewer if success, null otherwise
	 */
	public static TableViewer getTableViewer(SectionPart part) {
		if (part != null && part instanceof LibrarySectionPart) {
			LibrarySectionPart lPart = (LibrarySectionPart) part;
			return lPart.getTableViewer();
		}
		return null;
	}

	/**
	 * Retrieves the "Add" button widget of a Libraries section.
	 * @param part - Libraries section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getAddButton(SectionPart part) {
		if (part != null && part instanceof LibrarySectionPart) {
			LibrarySectionPart lPart = (LibrarySectionPart) part;
			return lPart.getAddButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Edit" button widget of a Libraries section.
	 * @param part - Libraries section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getEditButton(SectionPart part) {
		if (part != null && part instanceof LibrarySectionPart) {
			LibrarySectionPart lPart = (LibrarySectionPart) part;
			return lPart.getEditButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Up" button widget of a Libraries section.
	 * @param part - Libraries section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getUpButton(SectionPart part) {
		if (part != null && part instanceof LibrarySectionPart) {
			LibrarySectionPart lPart = (LibrarySectionPart) part;
			return lPart.getUpButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Down" button widget of a Libraries section.
	 * @param part - Libraries section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getDownButton(SectionPart part) {
		if (part != null && part instanceof LibrarySectionPart) {
			LibrarySectionPart lPart = (LibrarySectionPart) part;
			return lPart.getDownButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Remove" button widget of a Libraries section.
	 * @param part - Libraries section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getRemovepButton(SectionPart part) {
		if (part != null && part instanceof LibrarySectionPart) {
			LibrarySectionPart lPart = (LibrarySectionPart) part;
			return lPart.getRemovepButton();
		}
		return null;
	}

	/**
	 * Retrieves the add library dialog launched of a Libraries section.
	 * @param part - Libraries section part provided by caller
	 * @return - add library dialog if success, null otherwise
	 */
	public static Dialog getAddLibraryDialog(SectionPart part) {
		if (part != null && part instanceof LibrarySectionPart) {
			LibrarySectionPart lPart = (LibrarySectionPart) part;
			return lPart.getAddLibraryDialog();
		}
		return null;
	}

	/**
	 * Retrieves the edit library dialog launched of a Libraries section.
	 * @param part - Libraries section part provided by caller
	 * @return - edit library dialog if success, null otherwise
	 */
	public static Dialog getEditLibraryDialog(SectionPart part) {
		if (part != null && part instanceof LibrarySectionPart) {
			LibrarySectionPart lPart = (LibrarySectionPart) part;
			return lPart.getEditLibraryDialog();
		}
		return null;
	}

}
