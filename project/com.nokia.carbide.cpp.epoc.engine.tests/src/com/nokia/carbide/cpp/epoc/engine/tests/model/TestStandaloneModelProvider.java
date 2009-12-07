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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import com.nokia.carbide.cpp.epoc.engine.model.BldInfModelFactory;
import com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.IModelProvider;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.MMPModelFactory;
import com.nokia.carbide.cpp.epoc.engine.model.ModelProviderFactory;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfModel;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPModel;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.cpp.epoc.engine.tests.TestsPlugin;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

/**
 * Test the standalone model provider -- i.e. reading/writing with java.io.File.  
 * Real tests in TestModelProvider.
 *
 */
public class TestStandaloneModelProvider extends BaseTest {

	private File baseDir;
	
	private IModelProvider<IBldInfOwnedModel, IBldInfModel> bldInfModelProvider;
	private IModelProvider<IMMPOwnedModel, IMMPModel> mmpModelProvider;
	
	private static final String PROJECT_NAME = "project1";

	private static final String BASE_DIR = "data/provider-test/" + PROJECT_NAME;
	private IViewConfiguration configuration;
	protected Collection<IDefine> macros;
	private IMMPViewConfiguration mmpConfiguration;

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.BaseTest#setUp()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		if (Platform.isRunning()) {
			baseDir = FileUtils.pluginRelativeFile(TestsPlugin.getDefault(), BASE_DIR);
		} else {
			baseDir = new File(BASE_DIR).getAbsoluteFile();
		}
		
		bldInfModelProvider = ModelProviderFactory.createStandaloneModelProvider(new BldInfModelFactory());
		mmpModelProvider = ModelProviderFactory.createStandaloneModelProvider(new MMPModelFactory());
		((ModelProviderBase) bldInfModelProvider).cacheModels(false);
		((ModelProviderBase) mmpModelProvider).cacheModels(false);
		
		parserConfig.projectPath = new Path(baseDir.getAbsolutePath());
		
		macros = new ArrayList<IDefine>();
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
			
			public String getDefaultDefFileBase(boolean isASSP) {
				return "BWINS";
			}
			
			public boolean isEmulatorBuild() {
				return true;
			}
			
		};
	}
	
	/**
	 * Test a load that happens implicitly due to querying a shared model
	 *
	 */
	public void testSharedModelLoading() throws Exception {
		IPath thePath = new Path(baseDir + "/group/bld.inf");

		// verify not shared yet
		IBldInfModel model = bldInfModelProvider.findSharedModel(thePath);
		assertNull(model);
		
		// implicitly load
		model = bldInfModelProvider.getSharedModel(thePath);
		assertNotNull(model);
		
		// not it's shared
		assertEquals(model, bldInfModelProvider.findSharedModel(thePath));
		// release reference
		bldInfModelProvider.releaseSharedModel(model);
		
		// be sure it was loaded
		assertNotNull(((IOwnedModel)model).getDocument());
		assertTrue(((IOwnedModel)model).getDocument().get().length() > 0);
		
		IBldInfView view = model.createView(configuration);
		assertEquals(1, view.getAllMMPReferences().length);

		view.dispose();
		
		// release our view
		bldInfModelProvider.releaseSharedModel(model);

		// model should be gone now
		assertNull(bldInfModelProvider.findSharedModel(thePath));
	}

	/**
	 * Test that implicit loads don't happen with nonexistent files
	 *
	 */
	public void testSharedModelFileNotFound() throws Exception {
		IPath thePath = new Path(baseDir + "/group/bldsnap.inf");

		// verify not shared yet
		IBldInfModel model = bldInfModelProvider.findSharedModel(thePath);
		assertNull(model);
		
		// try to implicitly load
		model = bldInfModelProvider.getSharedModel(thePath);
		
		// shouldn't be here
		assertNull(model);
	}


	/** just sanity to ensure two model providers don't fight */
	public void testModelProviderIntegration() throws Exception {
		IPath thePath = new Path(baseDir + "/group/bld.inf");
		
		IBldInfModel bldInf = bldInfModelProvider.getSharedModel(thePath);
		IBldInfView bldInfView = bldInf.createView(configuration);
		
		IMMPModel mmpModel = mmpModelProvider.getSharedModel(
				new Path(baseDir.getAbsolutePath()).append(
						bldInfView.getAllMMPReferences()[0].getPath()));
		assertNotNull(mmpModel);
		
		IMMPView mmpView = mmpModel.createView(mmpConfiguration);
		assertEquals(1, mmpView.getSources().size());
		assertEquals(new Path("src/test.cpp"), mmpView.getSources().get(0));
		mmpView.dispose();
		
		mmpModelProvider.releaseSharedModel(mmpModel);
		
		bldInfView.dispose();
		bldInfModelProvider.releaseSharedModel(bldInf);
	}
}
