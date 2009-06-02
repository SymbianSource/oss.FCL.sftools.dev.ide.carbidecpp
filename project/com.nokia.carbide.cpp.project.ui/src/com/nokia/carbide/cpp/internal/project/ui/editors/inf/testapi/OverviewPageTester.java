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
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;

import com.nokia.carbide.cpp.internal.project.ui.editors.inf.MakMakeFilesSectionPart;
import com.nokia.carbide.cpp.internal.project.ui.editors.inf.OverviewPage;

/**
 * An interface for testing the Overview page of the Bld.inf Editor
 */
public class OverviewPageTester {

	/**
	 * Retrieves any error message from the Overview page.
	 * @param page - Overview page provided by caller
	 * @return error message string if success, null otherwise
	 */
	public static String getErrorMesaage(FormPage page) {
		if (page != null && page instanceof OverviewPage) {
			OverviewPage overviewPage = (OverviewPage) page;
			return overviewPage.getErrorMesaage();
		}
		return null;
	}

	/**
	 * Retrieves the "Active Build Configuration" label from the Overview page.
	 * @param page - Overview page provided by caller
	 * @return - label widget if success, null otherwise
	 */
	public static Label getActiveBuildConfigLabel(FormPage page) {
		if (page != null && page instanceof OverviewPage) {
			OverviewPage overviewPage = (OverviewPage) page;
			return overviewPage.getActiveBuildConfigLabel();
		}
		return null;
	}

	/**
	 * Retrieves the "Project Build Platforms" label from the Overview page.
	 * @param page - Overview page provided by caller
	 * @return - label widget if success, null otherwise
	 */
	public static Label getProjectPlatformsLabel(FormPage page) {
		if (page != null && page instanceof OverviewPage) {
			OverviewPage overviewPage = (OverviewPage) page;
			return overviewPage.getProjectPlatformsLabel();
		}
		return null;
	}

	/**
	 * Retrieves the Components section from the Overview page.
	 * @param page - Overview page provided by caller
	 * @return - section part if success, null otherwise
	 */
	public static SectionPart getComponentsSection(FormPage page) {
		if (page != null && page instanceof OverviewPage) {
			OverviewPage overviewPage = (OverviewPage) page;
			return overviewPage.getComponentsSectionPart();
		}
		return null;
	}

	/**
	 * Retrieves the Test Components section from the Overview page.
	 * @param page - Overview page provided by caller
	 * @return - section part if success, null otherwise
	 */
	public static SectionPart getTestComponentsSection(FormPage page) {
		if (page != null && page instanceof OverviewPage) {
			OverviewPage overviewPage = (OverviewPage) page;
			return overviewPage.getTestComponentsSectionPart();
		}
		return null;
	}

	/**
	 * Retrieves the table viewer of a Components section.
	 * @param part - Components section part provided by caller
	 * @return - table viewer if success, null otherwise
	 */
	public static TableViewer getComponentsTableViewer(SectionPart part) {
		if (part != null && part instanceof MakMakeFilesSectionPart) {
			MakMakeFilesSectionPart mPart = (MakMakeFilesSectionPart) part;
			return mPart.getTableViewer();
		}
		return null;
	}

	/**
	 * Retrieves the "Create New MMP File" button widget of a Components section.
	 * @param part - Components section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getCreateMMPButton(SectionPart part) {
		if (part != null && part instanceof MakMakeFilesSectionPart) {
			MakMakeFilesSectionPart mPart = (MakMakeFilesSectionPart) part;
			return mPart.getCreateMMPButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Add Existing MMP File" button widget of a Components section.
	 * @param part - Components section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getAddMMPButton(SectionPart part) {
		if (part != null && part instanceof MakMakeFilesSectionPart) {
			MakMakeFilesSectionPart mPart = (MakMakeFilesSectionPart) part;
			return mPart.getAddMMPButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Add Existing Make File" button widget of a Components section.
	 * @param part - Components section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getAddMakeFileButton(SectionPart part) {
		if (part != null && part instanceof MakMakeFilesSectionPart) {
			MakMakeFilesSectionPart mPart = (MakMakeFilesSectionPart) part;
			return mPart.getAddMakeFileButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Edit" button widget of a Components section.
	 * @param part - Components section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getEditButton(SectionPart part) {
		if (part != null && part instanceof MakMakeFilesSectionPart) {
			MakMakeFilesSectionPart mPart = (MakMakeFilesSectionPart) part;
			return mPart.getEditButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Open" button widget of a Components section.
	 * @param part - Components section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getOpenButton(SectionPart part) {
		if (part != null && part instanceof MakMakeFilesSectionPart) {
			MakMakeFilesSectionPart mPart = (MakMakeFilesSectionPart) part;
			return mPart.getOpenButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Up" button widget of a Components section.
	 * @param part - Components section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getUpButton(SectionPart part) {
		if (part != null && part instanceof MakMakeFilesSectionPart) {
			MakMakeFilesSectionPart mPart = (MakMakeFilesSectionPart) part;
			return mPart.getUpButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Down" button widget of a Components section.
	 * @param part - Components section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getDownButton(SectionPart part) {
		if (part != null && part instanceof MakMakeFilesSectionPart) {
			MakMakeFilesSectionPart mPart = (MakMakeFilesSectionPart) part;
			return mPart.getDownButton();
		}
		return null;
	}

	/**
	 * Retrieves the "Remove" button widget of a Components section.
	 * @param part - Components section part provided by caller
	 * @return - button widget if success, null otherwise
	 */
	public static Button getRemoveButton(SectionPart part) {
		if (part != null && part instanceof MakMakeFilesSectionPart) {
			MakMakeFilesSectionPart mPart = (MakMakeFilesSectionPart) part;
			return mPart.getRemoveButton();
		}
		return null;
	}

	/**
	 * Retrieves the new MMP file wizard launched from a Components section.
	 * @param part - Components section part provided by caller
	 * @return - new MMP file wizard if success, null otherwise
	 */
	public static Wizard getMMPWizard(SectionPart part) {
		if (part != null && part instanceof MakMakeFilesSectionPart) {
			MakMakeFilesSectionPart mPart = (MakMakeFilesSectionPart) part;
			return mPart.getMMPWizard();
		}
		return null;
	}

	/**
	 * Retrieves the MMP file dialog launched from a Components section.
	 * @param part - Components section part provided by caller
	 * @return - MMP file dialog if success, null otherwise
	 */
	public static Dialog getMMPFileDialog(SectionPart part) {
		if (part != null && part instanceof MakMakeFilesSectionPart) {
			MakMakeFilesSectionPart mPart = (MakMakeFilesSectionPart) part;
			return mPart.getMMPFileDialog();
		}
		return null;
	}

	/**
	 * Retrieves the make file dialog launched from a Components section.
	 * @param part - Components section part provided by caller
	 * @return - make file dialog if success, null otherwise
	 */
	public static Dialog getMakeFileDialog(SectionPart part) {
		if (part != null && part instanceof MakMakeFilesSectionPart) {
			MakMakeFilesSectionPart mPart = (MakMakeFilesSectionPart) part;
			return mPart.getMakeFileDialog();
		}
		return null;
	}

	/**
	 * Retrieves the editor part launched from a Components section.
	 * @param part - Components section part provided by caller
	 * @return - editor part if success, null otherwise
	 */
	public static IEditorPart getComponentEditor(SectionPart part) {
		if (part != null && part instanceof MakMakeFilesSectionPart) {
			MakMakeFilesSectionPart mPart = (MakMakeFilesSectionPart) part;
			return mPart.getComponentEditor();
		}
		return null;
	}

}
