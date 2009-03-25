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
* Test the BldInfViewPathHelper class.
*
*/
package com.nokia.carbide.cdt.builder.test;

import com.nokia.carbide.cdt.builder.*;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfModel;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.io.ByteArrayInputStream;
import java.io.File;

import junit.framework.TestCase;

public class TestBldInfViewPathHelper extends TestCase {
	
	private static final String PROJECT1_NAME = "TestBldInf";
	private static final IPath BLDINF_PATH = new Path("group/bld.inf");
	private static final String BLDINF1 = 
		"PRJ_MMPFILES\n"+
		"foo\n"+
		"\n"+
		"PRJ_EXTENSIONS\r\n" + 
		"START EXTENSION tools/stlport\r\n" + 
		"  OPTION STLPORT_VERSION  5.1.0\r\n" + 
		"  OPTION STL_REL_LIB_NAME  libstlport.5.1.a\r\n" + 
		"  OPTION STL_DEB_LIB_NAME  libstlportg.5.1.a\r\n" + 
		"  OPTION SOURCE_ARCHIVE ../source/STLport-5.1.0.zip\r\n" + 
		"END\r\n" + 
		"";
	
	private static final IPath STLPORT_EPOC_PATH =
		new Path("epoc32/tools/makefile_templates/tools/stlport");
	
	private IPath epocRoot;
	private IProject project1;
	private IPath project1Root;
	private File tmpDir;
	private File epocDir;
	private IBldInfModel bldinfModel1;
	private DefaultViewConfiguration defaultConfiguration1;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tmpDir = FileUtils.getTemporaryDirectory();
		epocDir = new File(tmpDir, "epocroot");
		epocDir.mkdir();
		epocRoot = new Path(epocDir.getAbsolutePath());
		
		// setup skeleton SDK dir
		File iconDir = new File(epocDir, "tools/makefile_templates/tools");
		iconDir.mkdirs();
		new File(iconDir, "stlport".toString()).createNewFile();
		
		// imported project
		project1 = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT1_NAME);
		if (project1.exists())
			project1.delete(true, null);
		IProjectDescription desc = ResourcesPlugin.getWorkspace().newProjectDescription(PROJECT1_NAME);
		desc.setLocation(new Path(tmpDir.getAbsolutePath()).append(PROJECT1_NAME));
		project1.create(desc, null);
		project1Root = project1.getRawLocation();
		if (project1Root == null)
			project1Root = project1.getLocation();
		project1.open(null);

		// create makefiles and images
		project1.getFolder("group").create(true, true, new NullProgressMonitor());
		createFile(project1, BLDINF_PATH, BLDINF1);
		
		bldinfModel1 = EpocEnginePlugin.getBldInfModelProvider().getSharedModel(
				new Path(PROJECT1_NAME).append(BLDINF_PATH));
		assertNotNull(bldinfModel1);
		
		defaultConfiguration1 = new DefaultViewConfiguration(
				project1, null, new AcceptedNodesViewFilter());
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
		EpocEnginePlugin.getBldInfModelProvider().releaseSharedModel(bldinfModel1);
		project1.delete(true, null);
	}
	
	public void testBasic() {
		// assert no crash, etc.
		IBldInfView view = bldinfModel1.createView(defaultConfiguration1);
		new BldInfViewPathHelper(view, epocRoot);
		view.dispose();
	}

	public void testBasic(IBldInfModel model, IViewConfiguration configuration) {
		IBldInfView view = model.createView(configuration);
		BldInfViewPathHelper helper = new BldInfViewPathHelper(view, epocRoot);
		IPath result;
		
		IExtension extension = view.getExtensions().get(0);
		
		result = helper.convertExtensionTemplateToFilesystem(extension.getTemplatePath());
		
		assertNotNull(result);
		assertEquals(epocRoot.append(STLPORT_EPOC_PATH), result);

		IPath relPath = helper.convertExtensionTemplateFromFilesystem(result);
		assertEquals(extension.getTemplatePath(), relPath);
		
		view.dispose();

	}
	
	public void testExceptions1(IBldInfModel model, IViewConfiguration configuration) {
		IBldInfView view = model.createView(configuration);
		BldInfViewPathHelper helper = new BldInfViewPathHelper(view, epocRoot);
		
		IPath fullPath = new Path("c:/unrelated/directory/something");

		IPath relPath = helper.convertExtensionTemplateFromFilesystem(fullPath);
		assertNull(relPath);
		
		view.dispose();

	}
}
