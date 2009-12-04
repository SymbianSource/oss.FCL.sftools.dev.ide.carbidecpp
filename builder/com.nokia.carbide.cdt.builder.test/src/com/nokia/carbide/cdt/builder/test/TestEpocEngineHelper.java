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
package com.nokia.carbide.cdt.builder.test;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IPDOMManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.DefaultMMPViewConfiguration;
import com.nokia.carbide.cdt.builder.EMMPPathContext;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.MMPViewPathHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.MMPViewRunnableAdapter;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

public class TestEpocEngineHelper extends BaseTest {

	private static final String BASE_DIR = "data/TestProject/";
	private static final String CARBIDE_PROJECT_NAME = "CarbideProject";
	private static final String NON_CARBIDE_PROJECT_NAME = "NonCarbideProject";
	private static final String PROJECT_RELATIVE_BLDINF_PATH = "group/bld.inf";
	private static final IPath INCLUDE_PATH = new Path("CarbideProject/TestInc");
	
	private IProject carbideProject;
	private IProject nonCarbideProject;

	protected void setUp() throws Exception {
		if (carbideProject == null){
			// turn off the indexer
			CCorePlugin.getIndexManager().setDefaultIndexerId(IPDOMManager.ID_NO_INDEXER);

			// turn off auto-building
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IWorkspaceDescription workspaceDesc = workspace.getDescription();
			workspaceDesc.setAutoBuilding(false);
			workspace.setDescription(workspaceDesc);

			// create the test project
			carbideProject = ProjectCorePlugin.createProject(CARBIDE_PROJECT_NAME, null);
			assertNotNull(carbideProject);

			// copy the project contents into the workspace
			File baseDir;
			if (Platform.isRunning()) {
				baseDir = FileUtils.pluginRelativeFile(TestPlugin.getDefault(), BASE_DIR);
			} else {
				baseDir = new File(BASE_DIR).getAbsoluteFile();
			}

			FileUtils.copyTreeNoParent(baseDir, carbideProject.getLocation().toFile(), new FileFilter() {
				public boolean accept(File arg0) {
					return true;
				}
			});
			
			// refresh the workspace
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);

			List<ISymbianBuildContext> configs = TestPlugin.getUsableBuildConfigs();
			
			ProjectCorePlugin.postProjectCreatedActions(carbideProject, PROJECT_RELATIVE_BLDINF_PATH, configs, new ArrayList<String>(), "Debug MMP", null, new NullProgressMonitor());
		}
		
		if (nonCarbideProject == null) {
			// create the test project
			nonCarbideProject = ProjectCorePlugin.createProject(NON_CARBIDE_PROJECT_NAME, null);
			assertNotNull(nonCarbideProject);
		}
		
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		carbideProject.delete(true, true, null);

		nonCarbideProject.delete(true, true, null);
	}
	
	public void testGetEpocRootForProject() throws Exception {

		// test null parameter
		assertNull(EpocEngineHelper.getEpocRootForProject(null));

		// test non-Carbide project
		assertNotNull(nonCarbideProject);
		assertNull(EpocEngineHelper.getEpocRootForProject(nonCarbideProject));
		
		// test closed project
		nonCarbideProject.close(null);
		assertNull(EpocEngineHelper.getEpocRootForProject(nonCarbideProject));
		
		// test Carbide project
		assertNotNull(carbideProject);
		IPath epocroot = EpocEngineHelper.getEpocRootForProject(carbideProject);
		assertNotNull(epocroot);

        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(carbideProject);
        assertNotNull(cpi);
        assertTrue(epocroot.equals(new Path(cpi.getDefaultConfiguration().getSDK().getEPOCROOT())));
	}

	public void testAddIncludePathToProject() throws Exception {
		
		// test null parameters
		EpocEngineHelper.addIncludePathToProject(null, null);

		// test non-Carbide project
		assertNotNull(nonCarbideProject);
		EpocEngineHelper.addIncludePathToProject(nonCarbideProject, INCLUDE_PATH);
		
		// test closed project
		nonCarbideProject.close(null);
		EpocEngineHelper.addIncludePathToProject(nonCarbideProject, INCLUDE_PATH);
		
		// test Carbide project
		assertNotNull(carbideProject);
		EpocEngineHelper.addIncludePathToProject(carbideProject, INCLUDE_PATH);

        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(carbideProject);
        assertNotNull(cpi);
        
		for (ICarbideBuildConfiguration config : cpi.getBuildConfigurations()) {
			final IPath epocroot = new Path(config.getSDK().getEPOCROOT());

			for (final IPath mmpPath : EpocEngineHelper.getMMPFilesForBuildConfiguration(config)) {
				
				EpocEnginePlugin.runWithMMPView(mmpPath, 
					new DefaultMMPViewConfiguration(config, new AcceptedNodesViewFilter()), 
					new MMPViewRunnableAdapter() {

						public Object run(IMMPView view) {
							MMPViewPathHelper helper = new MMPViewPathHelper(view, epocroot);
							boolean found = false;
							for (IPath inc : view.getUserIncludes()) {
								IPath path = helper.convertMMPToProject(EMMPPathContext.USERINCLUDE, inc);
								if (path.equals(inc)) {
									found = true;
									break;
								}
							}
							
							assertTrue(found);
							return null;
						}
				});
			}
		}
	}
}
