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

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.OverviewPage;

/**
 * An interface for testing the Overview page of the MMP Editor
 */
public class OverviewPageTester {

	/**
	 * Retrieves the "Active Build Configuration" label of the Overview page.
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
	 * Retrieves the "Target Name" text widget of the Overview page.
	 * @param page - Overview page provided by caller
	 * @return - text widget if success, null otherwise
	 */
	public static Text getTargetNameText(FormPage page) {
		if (page != null && page instanceof OverviewPage) {
			OverviewPage overviewPage = (OverviewPage) page;
			return overviewPage.getTargetNameText();
		}
		return null;
	}

	/**
	 * Retrieves the "Target Path" text widget of the Overview page.
	 * @param page - Overview page provided by caller
	 * @return - text widget if success, null otherwise
	 */
	public static Text getTargetPathText(FormPage page) {
		if (page != null && page instanceof OverviewPage) {
			OverviewPage overviewPage = (OverviewPage) page;
			return overviewPage.getTargetPathText();
		}
		return null;
	}

	/**
	 * Retrieves the "Target Type" combo widget of the Overview page.
	 * @param page - Overview page provided by caller
	 * @return - combo widget if success, null otherwise
	 */
	public static Combo getTargetTypeCombo(FormPage page) {
		if (page != null && page instanceof OverviewPage) {
			OverviewPage overviewPage = (OverviewPage) page;
			return overviewPage.getTargetTypeCombo();
		}
		return null;
	}

	/**
	 * Retrieves the "UID 2" text widget of the Overview page.
	 * @param page - Overview page provided by caller
	 * @return - text widget if success, null otherwise
	 */
	public static Text getUID2Text(FormPage page) {
		if (page != null && page instanceof OverviewPage) {
			OverviewPage overviewPage = (OverviewPage) page;
			return overviewPage.getUID2Text();
		}
		return null;
	}

	/**
	 * Retrieves the "UID 3" text widget of the Overview page.
	 * @param page - Overview page provided by caller
	 * @return - text widget if success, null otherwise
	 */
	public static Text getUID3Text(FormPage page) {
		if (page != null && page instanceof OverviewPage) {
			OverviewPage overviewPage = (OverviewPage) page;
			return overviewPage.getUID3Text();
		}
		return null;
	}

	/**
	 * Retrieves the "Add a user include path" hyperlink widget of the Overview page.
	 * @param page - Overview page provided by caller
	 * @return - hyperlink widget if success, null otherwise
	 */
	public static Hyperlink getAddAUserIncludeHyperlink(FormPage page) {
		if (page != null && page instanceof OverviewPage) {
			OverviewPage overviewPage = (OverviewPage) page;
			return overviewPage.getAddAUserIncludeHyperlink();
		}
		return null;
	}

	/**
	 * Retrieves the "Add a system include path" hyperlink widget of the Overview page.
	 * @param page - Overview page provided by caller
	 * @return - hyperlink widget if success, null otherwise
	 */
	public static Hyperlink getAddASystemIncludeHyperlink(FormPage page) {
		if (page != null && page instanceof OverviewPage) {
			OverviewPage overviewPage = (OverviewPage) page;
			return overviewPage.getAddASystemIncludeHyperlink();
		}
		return null;
	}

	/**
	 * Retrieves the "Sources" hyperlink widget of the Overview page.
	 * @param page - Overview page provided by caller
	 * @return - hyperlink widget if success, null otherwise
	 */
	public static ImageHyperlink getSourcesHyperlink(FormPage page) {
		if (page != null && page instanceof OverviewPage) {
			OverviewPage overviewPage = (OverviewPage) page;
			return overviewPage.getSourcesHyperlink();
		}
		return null;
	}

	/**
	 * Retrieves the "Libraries" hyperlink widget of the Overview page.
	 * @param page - Overview page provided by caller
	 * @return - hyperlink widget if success, null otherwise
	 */
	public static ImageHyperlink getLibrariesHyperlink(FormPage page) {
		if (page != null && page instanceof OverviewPage) {
			OverviewPage overviewPage = (OverviewPage) page;
			return overviewPage.getLibrariesHyperlink();
		}
		return null;
	}

	/**
	 * Retrieves the "Options" hyperlink widget of the Overview page.
	 * @param page - Overview page provided by caller
	 * @return - hyperlink widget if success, null otherwise
	 */
	public static ImageHyperlink getOptionsHyperlink(FormPage page) {
		if (page != null && page instanceof OverviewPage) {
			OverviewPage overviewPage = (OverviewPage) page;
			return overviewPage.getOptionsHyperlink();
		}
		return null;
	}

}
