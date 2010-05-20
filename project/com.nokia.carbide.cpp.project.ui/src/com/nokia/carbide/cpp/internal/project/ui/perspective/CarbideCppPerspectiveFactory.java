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
package com.nokia.carbide.cpp.internal.project.ui.perspective;

import org.eclipse.cdt.internal.ui.wizards.CWizardRegistry;
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.ui.*;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.navigator.resources.ProjectExplorer;

public class CarbideCppPerspectiveFactory implements IPerspectiveFactory {
		
	private static final String SYMBIAN_PROJECT_NAVIGATOR_VIEW_ID = 
		"com.nokia.carbide.cpp.project.ui.views.SymbianProjectNavigatorView"; //$NON-NLS-1$
	private static final String EXECUTABLES_VIEW_ID = 
		"org.eclipse.cdt.debug.ui.executablesView"; //$NON-NLS-1$
	
	private static final String INSTALL_VIEW_ID = "com.nokia.carbide.discovery.view"; //$NON-NLS-1$
	
	private static final String TOP_LEFT = "topLeft"; //$NON-NLS-1$
	private static final String BOTTOM_LEFT = "bottomLeft"; //$NON-NLS-1$
	private static final String BOTTOM_RIGHT = "bottom"; //$NON-NLS-1$
	private static final String TOP_RIGHT = "topRight"; //$NON-NLS-1$

	private static final String perspectiveId = 
		"com.nokia.carbide.cpp.CarbideCppPerspective"; //$NON-NLS-1$
	
	
	public static String getPerspectiveId() {
		return perspectiveId;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
	 */
	public void createInitialLayout(IPageLayout layout) {
		// Get the editor area.
		String editorArea = layout.getEditorArea();

		// Top left: Resource Navigator view and Bookmarks view placeholder
		IFolderLayout topLeft = layout.createFolder(TOP_LEFT, IPageLayout.LEFT, 0.25f, editorArea);
		topLeft.addView(ProjectExplorer.VIEW_ID);

		// Bottom left: SPN View
		IFolderLayout bottomLeft = layout.createFolder(BOTTOM_LEFT, IPageLayout.BOTTOM, 0.5f, TOP_LEFT);
		bottomLeft.addView(SYMBIAN_PROJECT_NAVIGATOR_VIEW_ID);

		// Bottom right: Task List view
		IFolderLayout bottomRight = layout.createFolder(BOTTOM_RIGHT, IPageLayout.BOTTOM, 0.75f, editorArea);
		bottomRight.addView(IPageLayout.ID_PROBLEM_VIEW);
		bottomRight.addView(IConsoleConstants.ID_CONSOLE_VIEW);
		bottomRight.addView(EXECUTABLES_VIEW_ID);
		
		// Top right: Outline view
		IFolderLayout topRight = layout.createFolder(TOP_RIGHT, IPageLayout.RIGHT, 0.75f, editorArea);
		topRight.addView(IPageLayout.ID_OUTLINE);

		// Add action sets for search, c element and navigate
		layout.addActionSet(CUIPlugin.SEARCH_ACTION_SET_ID);
		layout.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);
		
		// show view shortcuts
		layout.addShowViewShortcut(ProjectExplorer.VIEW_ID);
		layout.addShowViewShortcut(SYMBIAN_PROJECT_NAVIGATOR_VIEW_ID);
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
		layout.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW); // build console
		layout.addShowViewShortcut(EXECUTABLES_VIEW_ID);
		layout.addShowViewShortcut(INSTALL_VIEW_ID);

		// add show-in contributions
		layout.addShowInPart(ProjectExplorer.VIEW_ID);
		
		addCDTWizardShortcutIdsToLayout(layout);
	}
	
	@SuppressWarnings("restriction")
	private void addCDTWizardShortcutIdsToLayout(IPageLayout layout) {
		// cdt folder wizard ids
		String[] ids = CWizardRegistry.getFolderWizardIDs();
		for (String id : ids) {
			layout.addNewWizardShortcut(id);
		}
		
		// cdt file wizard ids
		ids = CWizardRegistry.getFileWizardIDs();
		for (String id : ids) {
			layout.addNewWizardShortcut(id);
		}
		
		// cdt type wizard ids
		ids = CWizardRegistry.getTypeWizardIDs();
		for (String id : ids) {
			layout.addNewWizardShortcut(id);
		}
	}
}
