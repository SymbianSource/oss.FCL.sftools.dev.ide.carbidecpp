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
* Test the PKGViewPathHelper class.
* 
*/
package com.nokia.carbide.cdt.builder.test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IPDOMManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.DefaultViewConfiguration;
import com.nokia.carbide.cdt.builder.PKGViewPathHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.carbide.cdt.internal.api.builder.SISBuilderInfo2;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.EPKGLanguage;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.IPKGInstallFile;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.IPKGModel;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.IPKGView;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.PKGModelHelper;

public class TestPKGViewPathHelper extends BaseTest {
	
	private static final String PROJECT_NAME = "TestPKG";
	private static final IPath PKG_PATH = new Path("sis/test.pkg");
	private static final String PKG1 = 
		"&EN\n"+
		"#{\"test\"},(0xA0000180),1,0,0, TYPE=SA\n"+
		"%{\"Vendor-EN\"}\n"+
		":\"Vendor\"\r\n" + 
		"[0x101F7961], 0, 0, 0, {\"S60ProductID\"}\r\n" + 
		"\"$(EPOCROOT)\\epoc32\\release\\$(PLATFORM)\\$(TARGET)\\test.exe\"			-\"\"\r\n" + 
		"\"test.txt\" - \"\"\r\n"+
		"\"\\Symbian\\9.1\\S60_3rd\\file.txt\" - \"\"\r\n"+
		"";
	
	//TODO add relative path as well
	
	private static final int NUMBER_OF_FILES = 3;
	private IProject project;
	private IPKGModel pkgModel;
	private DefaultViewConfiguration defaultConfiguration;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// turn off the indexer
		CCorePlugin.getIndexManager().setDefaultIndexerId(IPDOMManager.ID_NO_INDEXER);

		project = ProjectCorePlugin.createProject(PROJECT_NAME, null);

		ProjectCorePlugin.postProjectCreatedActions(project, "group/bld.inf", 
				TestPlugin.getUsableBuildConfigs(), new ArrayList<String>(), 
				"Debug MMP", null, new NullProgressMonitor());
		
		// create makefiles and images
		project.getFolder("sis").create(true, true, new NullProgressMonitor());
		createFile(project, PKG_PATH, PKG1);

		pkgModel = PKGModelHelper.getPKGModelProvider().getSharedModel(
				new Path(PROJECT_NAME).append(PKG_PATH));
		assertNotNull(pkgModel);
		
		defaultConfiguration = new DefaultViewConfiguration(
				project, null, new AcceptedNodesViewFilter());
	}
	
	/**
	 */
	private void createFile(IProject project, IPath path, String contents) throws CoreException {
		IFile file = project.getFile(path);
		file.create(new ByteArrayInputStream(
				contents != null ? contents.getBytes() : new byte[0]), 
				true, new NullProgressMonitor());
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		PKGModelHelper.getPKGModelProvider().releaseSharedModel(pkgModel);
		project.delete(true, null);
	}
	
	public void testBasic() {
		// assert no crash, etc.
		IPKGView view = pkgModel.createView(defaultConfiguration);

        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		new PKGViewPathHelper(view, cpi.getDefaultConfiguration());
		view.dispose();
	}

	public void testPaths() {
		IPKGView view = pkgModel.createView(defaultConfiguration);
        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
        ICarbideBuildConfiguration config = cpi.getDefaultConfiguration();
		PKGViewPathHelper helper = new PKGViewPathHelper(view, config);

		IPKGInstallFile[] files = view.getAllInstallFiles();
		assertNotNull(files);
		assertEquals(files.length, NUMBER_OF_FILES);

		IPath path1 = new Path(config.getSDK().getEPOCROOT()).append("epoc32/release").append(config.getPlatformString().toLowerCase()).append(config.getTargetString().toLowerCase()).append("test.exe");
		IPath path2 = project.getLocation().append("sis/test.txt");
		IPath path3 = new Path("/Symbian/9.1/S60_3rd/file.txt");
		
		List<IPath> paths = new ArrayList<IPath>();
		
		for (IPKGInstallFile file : files) {
			Map<EPKGLanguage, IPath> sourceFiles = file.getSourceFiles();
			
			for (EPKGLanguage language : sourceFiles.keySet()) {
				IPath path = helper.getAbsolutePathFromViewPath(sourceFiles.get(language));
				assertNotNull(path);
				paths.add(path);
			}
		}
		
		assertEquals(paths.size(), NUMBER_OF_FILES);
		
		assertEquals(path1, paths.get(0));
		assertEquals(path2, paths.get(1));
		assertEquals(path3, paths.get(2));
		
		ISISBuilderInfo sisInfo = new SISBuilderInfo2(project);
		sisInfo.setContentSearchLocation(project.getLocation().toOSString());
		helper.setSISBuilderInfo(sisInfo);
		assertEquals(new Path("sis/test.txt"), helper.getViewPathFromAbsolutePath(paths.get(1)));
		
		view.dispose();
	}
}
