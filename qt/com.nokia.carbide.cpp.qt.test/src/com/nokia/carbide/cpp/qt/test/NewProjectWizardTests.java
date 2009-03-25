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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IPDOMManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

import abbot.swt.eclipse.utils.InvokeNewWizard;
import abbot.swt.finder.matchers.WidgetTextMatcher;
import abbot.swt.tester.ShellTester;
import abbot.swt.tester.WidgetTester;

import com.nokia.carbide.automation.utils.GenericHelper;
import com.nokia.carbide.automation.utils.UITestCase;
import com.nokia.carbide.automation.utils.builder.BuilderUtils;
import com.nokia.carbide.automation.utils.eclipse.EclipseUtils;
import com.nokia.carbide.automation.utils.eclipse.ProjectUtils;
import com.nokia.carbide.automation.utils.importer.ImporterEclipseConstants;
import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;

public class NewProjectWizardTests extends UITestCase {

	private static final String CONSOLE = "console";
	private static final String GUI_DIALOG = "dialog";
	private static final String GUI_WINDOW = "window";
	private static final String GUI_WIDGET = "widget";
	
	boolean cleanupProjects = false;
	
	public NewProjectWizardTests() {
		super(NewProjectWizardTests.class.getName());
	}

	protected void setUp() throws Exception {
		super.setUp();

		// turn off the indexer
		CCorePlugin.getIndexManager().setDefaultIndexerId(IPDOMManager.ID_NO_INDEXER);

		// set the default Qt version
		Activator.getDefault().setDefaultQtVersion();
	}

	protected void tearDown() throws Exception {
		if (cleanupProjects) {
			ProjectUtils.deleteProject(CONSOLE, true);
			ProjectUtils.deleteProject(GUI_DIALOG, true);
			ProjectUtils.deleteProject(GUI_WINDOW, true);
			ProjectUtils.deleteProject(GUI_WIDGET, true);
		}
		
		super.tearDown();
	}

	public void testNewConsoleProject() throws Exception {
		createTemplateProject("Qt Console", CONSOLE);
	}

	public void testNewDialogProject() throws Exception {
		createTemplateProject("Qt GUI Dialog", GUI_DIALOG);
	}

	public void testNewWindowProject() throws Exception {
		createTemplateProject("Qt GUI Main Window", GUI_WINDOW);
	}

	public void testNewWidgetProject() throws Exception {
		createTemplateProject("Qt GUI Widget", GUI_WIDGET);
	}

	public void testBuildConsoleProject() throws Exception {
		buildProject(CONSOLE);
	}

	public void testBuildDialogProject() throws Exception {
		buildProject(GUI_DIALOG);
	}

	public void testBuildWindowProject() throws Exception {
		buildProject(GUI_WINDOW);
	}

	public void testBuildWidgetProject() throws Exception {
		buildProject(GUI_WIDGET);
	}

	protected void createTemplateProject(String templateName, String projectName) throws Exception {
		Shell shell = EclipseUtils.getActiveShell();
		InvokeNewWizard.invoke("Qt/Qt Project", "/", shell);
		// pick up the wizard shell
		shell = ShellTester.waitVisible("New Qt Symbian OS C++ Project");

		Widget page = EclipseUtils.findWidgetByID(shell, "ChooseTemplatePage");
		assertNotNull(page);
		final Tree tree = (Tree) EclipseUtils.findWidgetByID(page, "templateTree");
		assertNotNull(tree);
		
		final TreeItem item = (TreeItem) getFinder().find(tree, 
				new WidgetTextMatcher(templateName, TreeItem.class));

		WidgetTester.getWidgetTester().actionClick(item);

		EclipseUtils.clickButton(ImporterEclipseConstants.MENU_SEQUENCE_NEXT);

		page = EclipseUtils.findWidgetByID(shell, "NewProjectPage");
		assertNotNull(page);

		EclipseUtils.enterLabeledText("&Project name:", projectName);

		EclipseUtils.clickButton(ImporterEclipseConstants.MENU_SEQUENCE_NEXT);
		
		page = EclipseUtils.findWidgetByID(shell, "BuildTargetsPage");
		assertNotNull(page);

		final Tree configsTree = (Tree) EclipseUtils.findWidgetByID(page, "buildConfigsTree");
		assertNotNull(configsTree);
		
		// need to run this in the ui thread
		getDisplay().syncExec(new Runnable() {
			public void run() {
				assertTrue(tree.getItems().length > 0);
			}
		});

		EclipseUtils.clickButton(ImporterEclipseConstants.MENU_SEQUENCE_NEXT);

		page = EclipseUtils.findWidgetByID(shell, "QtModulesPage");
		assertNotNull(page);
		
		EclipseUtils.clickButton(ImporterEclipseConstants.MENU_SEQUENCE_NEXT);

		EclipseUtils.clickButton(ImporterEclipseConstants.MENU_SEQUENCE_FINISH);
		
		// now make sure the project exists
		GenericHelper.pause(EclipseUtils.MEDIUM_SLEEP_TIME);
		
		IProject project = ProjectUtils.getProject(projectName);
		assertNotNull(project);
		assertTrue(project.exists());
		
		// make sure there's a pkg file
		IFile pkgFile = project.getFile(projectName + "_armv5_udeb.pkg");
		assertNotNull(pkgFile);
		assertTrue(pkgFile.exists());
	}

	protected void buildProject(String projectName) throws Exception {
		IProject project = ProjectUtils.getProject(projectName);
		assertNotNull(project);
		assertTrue(project.exists());

		// build each build configuration
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		assertNotNull(cpi);
		
		for (ICarbideBuildConfiguration config : cpi.getBuildConfigurations()) {
			// there should be no errors at this point
			assertFalse(CarbideCPPBuilder.projectHasBuildErrors(project));

			BuilderUtils.buildProject(project, config);

			// there should be no errors at this point, but there may be warnings
			assertFalse(CarbideCPPBuilder.projectHasBuildErrors(project));
		}

		project.refreshLocal(IResource.DEPTH_INFINITE, null);
		
		// make sure the sisx file was built
		IFile sisxFile = project.getFile(projectName + "_armv5_udeb.sisx");
		assertNotNull(sisxFile);
		assertTrue(sisxFile.exists());
	}

	public void testDerivedFiles() throws Exception {
		IProject project = ProjectUtils.getProject(CONSOLE);
		assertNotNull(project);
		assertTrue(project.exists());
		
		// make sure the bld.inf and mmp file are derived
		for (IResource res : project.members()) {
			if (res instanceof IFile) {
				if (res.getName().compareToIgnoreCase("bld.inf") == 0) {
					assertTrue(res.isDerived());
				}

				if (res.getName().endsWith(".mmp")) {
					assertTrue(res.isDerived());
				}
			}
		}
	}
	
	public void testProFileListener() throws Exception {
		IProject project = ProjectUtils.getProject(CONSOLE);
		assertNotNull(project);
		assertTrue(project.exists());

		// get the current time
		long currentTime = System.currentTimeMillis();
		
		// modify the .pro file
		IFile proFile = project.getFile("console.pro");
		assertNotNull(proFile);
		assertTrue(proFile.exists());
		proFile.setContents(proFile.getContents(), true, false, null);
		
		// wait a bit
		GenericHelper.pause(EclipseUtils.MEDIUM_SLEEP_TIME);

		// make sure the bld.inf and mmp file were re-generated
		for (IResource res : project.members()) {
			if (res instanceof IFile) {
				if (res.getName().compareToIgnoreCase("bld.inf") == 0) {
					assertTrue(currentTime < res.getLocalTimeStamp());
				}

				if (res.getName().endsWith(".mmp")) {
					assertTrue(currentTime < res.getLocalTimeStamp());
				}
			}
		}
	}
	
	public void testProFileListenerError() throws Exception {
		IProject project = ProjectUtils.getProject(CONSOLE);
		assertNotNull(project);
		assertTrue(project.exists());

		// there should be no errors at this point
		assertFalse(CarbideCPPBuilder.projectHasBuildErrors(project));
		
		// modify the .pro file to be invalid
		IFile proFile = project.getFile("console.pro");
		assertNotNull(proFile);
		assertTrue(proFile.exists());
		
		InputStream stream = new InputStream() {
			private Reader fReader = new StringReader("this is a bad .pro file");
			public int read() throws IOException {
				return fReader.read();
			}
		};

		proFile.setContents(stream, true, false, null);
		
		// wait a bit
		GenericHelper.pause(EclipseUtils.MEDIUM_SLEEP_TIME);

		// there should now be error(s)
		assertTrue(project.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE).length > 0);
	}
	
	// dummy test to set cleanup flag - must be last test!
	public void testEnd() throws Exception {
		cleanupProjects = true;
	}
}
