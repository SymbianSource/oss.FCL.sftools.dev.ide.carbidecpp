/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.tests.workspace;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.*;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfModel;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.*;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.cpp.epoc.engine.tests.TestsPlugin;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase;
import com.nokia.cpp.internal.api.utils.core.MultiResourceChangeListenerDispatcher;
import com.nokia.sdt.utils.WorkspaceFileTracker;
import com.nokia.cpp.internal.api.utils.core.MultiResourceChangeListenerDispatcher.IResourceChangeHandler;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IPDOMManager;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;

import java.util.*;

/**
 * Basic tests using the workspace
 *
 */
public abstract class BaseProjectTests extends BaseTest {

	private static String PROJECT_NAME_BASE = "test-models";
	
	protected IProject project;
	protected String projectName;
	protected ArrayList<IDefine> macros;
	protected IViewConfiguration configuration;
	protected IMMPViewConfiguration mmpConfiguration;
	protected IModelProvider<IBldInfOwnedModel, IBldInfModel> bldInfModelProvider;
	protected IModelProvider<IMMPOwnedModel, IMMPModel> mmpModelProvider;

	protected String defFileBase;

	protected boolean isEmulator;

	private MultiResourceChangeListenerDispatcher dispatcher;

	protected boolean waitingResourceChanged;


	private static int gIndex;
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.BaseTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		dispatcher = new MultiResourceChangeListenerDispatcher();
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription workspaceDesc = workspace.getDescription();
		workspaceDesc.setAutoBuilding(false);
		workspace.setDescription(workspaceDesc);

		bldInfModelProvider = EpocEnginePlugin.getBldInfModelProvider();
		((ModelProviderBase) bldInfModelProvider).reset();
		mmpModelProvider = EpocEnginePlugin.getMMPModelProvider();
		((ModelProviderBase) mmpModelProvider).reset();

		projectName = PROJECT_NAME_BASE + (gIndex++);
		project = ProjectCorePlugin.createProject(projectName, null);

		ProjectCorePlugin.postProjectCreatedActions(project, "group/bld.inf", TestsPlugin.getUsableBuildConfigs(), 
				new ArrayList<String>(), "Debug MMP", null, new NullProgressMonitor());

		Preferences corePrefs = CCorePlugin.getDefault().getPluginPreferences();
		corePrefs.setValue(CCorePlugin.PREF_INDEXER, IPDOMManager.ID_NO_INDEXER);
		
		parserConfig.projectPath = ResourcesPlugin.getWorkspace().getRoot().getLocation().append(projectName);
		
		macros = new ArrayList<IDefine>();
		defFileBase = "BWINS";
		isEmulator = true;
		
		configuration = new IViewConfiguration() {

			public Collection<IDefine> getMacros() {
				return macros;
			}

			public IViewFilter getViewFilter() {
				return new AcceptedNodesViewFilter();
			}

			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}
			
		};
		
		mmpConfiguration = new IMMPViewConfiguration() {

			public Collection<IDefine> getMacros() {
				return macros;
			}

			public IViewFilter getViewFilter() {
				return new AcceptedNodesViewFilter();
			}

			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}
			
			public boolean isStatementSupported(EMMPStatement statement) {
				return true;
			}
			
			public String getDefaultDefFileBase() {
				return defFileBase;
			}
			
			public boolean isEmulatorBuild() {
				return isEmulator;
			}
			
		};
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		((ModelProviderBase) mmpModelProvider).reset();
		((ModelProviderBase) bldInfModelProvider).reset();
		WorkspaceJob job = new WorkspaceJob("Deleting!") {
			IProject theProject = project;
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {
				theProject.delete(true, monitor);
				return Status.OK_STATUS;
			}
			
		};
		job.setRule(project.getWorkspace().getRoot());
		job.setPriority(Job.SHORT);
		job.schedule();
	}
	
	/** Save a workspace-relative file */
	protected void createOrSaveFile(IPath path, String text) throws CoreException {
		WorkspaceFileTracker tracker = new WorkspaceFileTracker();
		tracker.saveFileText(ResourcesPlugin.getWorkspace().getRoot().getFile(path).getLocation().toFile(), 
				null, text.toCharArray());
	}
	
	protected void addWaitingResourceListener(IPath workspacePath) {
		dispatcher.addResource(workspacePath, new IResourceChangeHandler() {

			public void resourceChanged(IPath workspacePath) {
				waitingResourceChanged = true;
			}
			
		});
	}
}
