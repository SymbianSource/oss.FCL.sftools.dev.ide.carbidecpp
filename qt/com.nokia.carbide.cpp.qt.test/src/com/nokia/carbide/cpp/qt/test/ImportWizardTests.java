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
package com.nokia.carbide.cpp.qt.test;

import java.net.URL;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IPDOMManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;

import abbot.swt.eclipse.utils.InvokeImportWizard;

import com.nokia.carbide.automation.utils.GenericHelper;
import com.nokia.carbide.automation.utils.UITestCase;
import com.nokia.carbide.automation.utils.eclipse.EclipseUtils;
import com.nokia.carbide.automation.utils.eclipse.ProjectUtils;
import com.nokia.carbide.automation.utils.importer.ImporterEclipseConstants;

public class ImportWizardTests extends UITestCase {

	private static final String GOOD_PRO_FILE = "Data/TestProject1/good.pro";
	private static final String BAD_PRO_FILE = "Data/TestProject2/bad.pro";
	
	public ImportWizardTests() {
		super(ImportWizardTests.class.getName());
	}

	protected void setUp() throws Exception {
		super.setUp();

		// turn off the indexer
		CCorePlugin.getIndexManager().setDefaultIndexerId(IPDOMManager.ID_NO_INDEXER);
		
		// set the default Qt version
		Activator.getDefault().setDefaultQtVersion();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		ProjectUtils.deleteProject("good", false);
		ProjectUtils.deleteProject("bad", false);
	}

	public void testImportGoodProFile() throws Exception {
    	URL url = FileLocator.find(Activator.getDefault().getBundle(), new Path(""), null); //$NON-NLS-1$
		assertNotNull(url);
        url = FileLocator.resolve(url);
		assertNotNull(url);
		
		Shell shell = InvokeImportWizard.invoke("Qt/Qt Project", "/", EclipseUtils.getActiveShell());
		assertNotNull(shell);
		Widget page = EclipseUtils.findWidgetByID(shell, "QtProFileSelectionPage");
		assertNotNull(page);
		Combo combo = (Combo) EclipseUtils.findWidgetByID(page, "proFileCombo");
		assertNotNull(combo);
		EclipseUtils.enterText(combo, new Path(url.getPath()).append(GOOD_PRO_FILE).toOSString());
		EclipseUtils.clickButton(ImporterEclipseConstants.MENU_SEQUENCE_NEXT);

		page = EclipseUtils.findWidgetByID(shell, "BuildTargetsPage");
		assertNotNull(page);

		final Tree tree = (Tree) EclipseUtils.findWidgetByID(page, "buildConfigsTree");
		assertNotNull(tree);
		
		// need to run this in the ui thread
		getDisplay().syncExec(new Runnable() {
			public void run() {
				assertTrue(tree.getItems().length > 0);
			}
		});

		EclipseUtils.clickButton(ImporterEclipseConstants.MENU_SEQUENCE_FINISH);
		
		// now make sure the project exists
		GenericHelper.pause(EclipseUtils.MEDIUM_SLEEP_TIME);
		
		IProject project = ProjectUtils.getProject("good");
		assertNotNull(project);
		assertTrue(project.exists());
		
		// make sure the bld.inf file was generated
		IFile file = project.getFile("bld.inf");
		assertNotNull(file);
		assertTrue(file.exists());
	}
	
	public void testImportBadProFile() throws Exception {
    	URL url = FileLocator.find(Activator.getDefault().getBundle(), new Path(""), null); //$NON-NLS-1$
		assertNotNull(url);
        url = FileLocator.resolve(url);
		assertNotNull(url);
		
		Shell shell = InvokeImportWizard.invoke("Qt/Qt Project", "/", EclipseUtils.getActiveShell());
		assertNotNull(shell);
		Widget page = EclipseUtils.findWidgetByID(shell, "QtProFileSelectionPage");
		assertNotNull(page);
		Combo combo = (Combo) EclipseUtils.findWidgetByID(page, "proFileCombo");
		assertNotNull(combo);
		EclipseUtils.enterText(combo, new Path(url.getPath()).append(BAD_PRO_FILE).toOSString());
		EclipseUtils.clickButton(ImporterEclipseConstants.MENU_SEQUENCE_NEXT);

		page = EclipseUtils.findWidgetByID(shell, "BuildTargetsPage");
		assertNotNull(page);

		final Tree tree = (Tree) EclipseUtils.findWidgetByID(page, "buildConfigsTree");
		assertNotNull(tree);
		
		// need to run this in the ui thread
		getDisplay().syncExec(new Runnable() {
			public void run() {
				assertTrue(tree.getItems().length > 0);
			}
		});

		EclipseUtils.clickButton(ImporterEclipseConstants.MENU_SEQUENCE_FINISH);

		// now make sure the project exists
		GenericHelper.pause(EclipseUtils.MEDIUM_SLEEP_TIME);

		IProject project = ProjectUtils.getProject("bad");
		assertNotNull(project);
		assertTrue(project.exists());
		
		// make sure the bld.inf file was not generated
		IFile file = project.getFile("bld.inf");
		assertNotNull(file);
		assertFalse(file.exists());
	}
}
