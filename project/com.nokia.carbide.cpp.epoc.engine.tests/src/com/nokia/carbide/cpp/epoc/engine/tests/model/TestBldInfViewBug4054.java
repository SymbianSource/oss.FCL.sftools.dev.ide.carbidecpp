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

package com.nokia.carbide.cpp.epoc.engine.tests.model;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.IModelProvider;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefaultModelDocumentProvider;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.BasicIncludeFileLocator;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Test that writes to a file inside a lot of #includes doesn't mess up other files
 * <p>
 * This is an ugly test since it relies on filesystem-hosted models and files,
 * when this class hierarchy assumes memory-based models.
 *
 */
public class TestBldInfViewBug4054 extends BaseBldInfViewTest {

	private File refTrpProject;
	private File trpProject;

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.model.BaseBldInfViewTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// redirect fields to look on disk
		refTrpProject = projectRelativeFile("data/real-world/trp");
		trpProject = new File(FileUtils.getTemporaryDirectory(), "testBug4054/trp");
		trpProject.mkdirs();
		FileUtils.copyTreeNoParent(refTrpProject, trpProject, null);
		projectPath = new Path(trpProject.getAbsolutePath());
		modelPath = projectPath.append("group/bld.inf");
		super.setUp();
		parserConfig.modelDocumentProvider = DefaultModelDocumentProvider.getInstance();
		parserConfig.fileLocator = new BasicIncludeFileLocator(new File[] { trpProject }, new File[0]);
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		FileUtils.delTree(trpProject.getParentFile());
		super.tearDown();
	}

	public void testBug4054Shallow() throws Exception {
		_testBug4054(refTrpProject, trpProject, "group/shallow_bld.inf", null);
	}

	public void testBug4054() throws Exception {
		_testBug4054(refTrpProject, trpProject, "group/bld.inf", "trp-updated");
	}

	/**
	 * @param trpProject
	 */
	private void _testBug4054(File refTrpProject, File trpProject, String bldInfPath, String refDir) throws Exception {
		
		IPath trpBldInf = projectPath.append(bldInfPath);
		
		//System.out.println("Reading " + trpBldInf);
		
		// get text for comparison later
		String text = new String(FileUtils.readFileContents(trpBldInf.toFile(), null));

		// use EpocEnginePlugin model provider to get models so that saves are persisted to disk
		IModelProvider<IBldInfOwnedModel, IBldInfModel> modelProvider = EpocEnginePlugin.getBldInfModelProvider();
		IBldInfModel model = modelProvider.getSharedModel(trpBldInf);
		this.model = (IBldInfOwnedModel) model;
		
		try {
			IBldInfView view = model.createView(config);
			try {
				checkNoProblems(view);
				
				//assertEquals(3, view.getTestExports().size());
				
				// make sure nothing changes if... nothing changes
				commitTest(view, text);
				
				// ensure nothing changed
				compareTrees(refTrpProject, trpProject);
				
				///
				if (refDir != null) {
					boolean found = false;
					Set<IExport> exportSet = new HashSet<IExport>(view.getTestExports());
					assertEquals(view.getTestExports().size(), exportSet.size());
					for (IExport export : view.getTestExports()) {
						if (export.getSourcePath().equals(new Path("test/tools/PortRouter/rom/PortRouter.iby"))) {
							export.setSourcePath(new Path("test/tools/PortRouter/PortRouter.iby"));
							found = true;
						}
						//System.out.println(export);
					}
					assertTrue(found);
				
					// make sure nothing changes in the main file
					commitTest(view, text);
					
					// ensure files updated
					compareTrees(new File(refTrpProject.getParentFile(), refDir), trpProject);
				}
			} finally {
				view.dispose();
			}
		} finally {
			modelProvider.releaseSharedModel(model);
		}
	}
}

