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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.Document;

import com.nokia.carbide.cpp.epoc.engine.model.BldInfModelFactory;
import com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.IModelListener;
import com.nokia.carbide.cpp.epoc.engine.model.IModelProvider;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.MMPModelFactory;
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
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase;

/**
 * Test basics of the model provider
 *
 */
public class TestModelProvider extends BaseTest {

	private static final String PROJECT_NAME = "test-models";
	private static final String TEST_BLDINF_0 = "PRJ_PLATFORMS default\n"+
			"PRJ_MMPFILES\n"+
			"test.mmp\n";
	private static final String TEST_MMP_0 = 
		"SOURCEPATH ..\\src\n"+
		"SOURCE test.cpp\n";
	
	private IModelProvider<IBldInfOwnedModel, IBldInfModel> bldInfModelProvider;
	private IModelProvider<IMMPOwnedModel, IMMPModel> mmpModelProvider;
	private IViewConfiguration configuration;
	protected Collection<IDefine> macros;
	private IMMPViewConfiguration mmpConfiguration;
	private Map<IPath, String> fs;

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.BaseTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		fs = new HashMap<IPath, String>();
		bldInfModelProvider = new MemoryFileSystemModelProvider(fs, new BldInfModelFactory());
		((ModelProviderBase) bldInfModelProvider).cacheModels(false);
		mmpModelProvider = new MemoryFileSystemModelProvider(fs, new MMPModelFactory());
		((ModelProviderBase) mmpModelProvider).cacheModels(false);
		
		parserConfig.projectPath = new Path(PROJECT_NAME);
		
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
	
	public void testCreateModel() throws CoreException {

		fs.clear();
		
		Path thePath = new Path(PROJECT_NAME + "/group/bld.inf");
		IBldInfOwnedModel model = bldInfModelProvider.createModel(thePath);
		assertNotNull(model);
		
		// not registered so can do this twice
		IBldInfOwnedModel model2 = bldInfModelProvider.createModel(thePath);
		assertNotNull(model2);
		assertNotSame(model, model2);

		// not registered yet!
		assertNull(bldInfModelProvider.findSharedModel(thePath));

		// no document for new model
		assertNull(model.getDocument());
		
		// this should be allowed from a nonregistered model
		model.setDocument(new Document(TEST_BLDINF_0));

		bldInfModelProvider.registerModel(model);
		
		// assert the file now exists
		assertTrue(fs.containsKey(thePath));
	}
	
	public void testLoadModel() throws Exception {
		fs.clear();
		
		IPath thePath = new Path(PROJECT_NAME + "/group/bld.inf");
		
		fs.put(thePath, TEST_BLDINF_0);
		
		IBldInfOwnedModel model = bldInfModelProvider.createModel(thePath);
		assertNotNull(model);
		
		// not loaded here
		assertNull(model.getDocument());

		// register
		bldInfModelProvider.registerModel(model);

		// make sure not saved!
		assertFalse(((MemoryFileSystemModelProvider)bldInfModelProvider).saved);
		
		// ensure loaded
		assertNotNull(model.getDocument());
		
		// ensure it's got the right contents
		assertEquals(TEST_BLDINF_0, model.getDocument().get());
		
	}
	
	/**
	 * Test explicit registration of models
	 *
	 */
	public void testRegisterModels() throws CoreException {

		fs.clear();
		
		IPath thePath = new Path(PROJECT_NAME + "/group/bld.inf");
		IBldInfOwnedModel model = bldInfModelProvider.createModel(thePath);
		assertNotNull(model);
		model.setDocument(new Document(TEST_BLDINF_0));
		bldInfModelProvider.registerModel(model);

		// make sure it was saved
		assertTrue(((MemoryFileSystemModelProvider)bldInfModelProvider).saved);
		((MemoryFileSystemModelProvider)bldInfModelProvider).saved = false;

		// owner holds one reference
		assertEquals(1, ((ModelProviderBase) bldInfModelProvider).testGetUseCount(model));
		
		// and ensure we can find it
		IBldInfModel sharedModel = bldInfModelProvider.findSharedModel(thePath);
		assertNotNull(sharedModel);
		
		// make sure it was not saved
		assertFalse(((MemoryFileSystemModelProvider)bldInfModelProvider).saved);

		// twice...
		IBldInfModel sharedModel2 = bldInfModelProvider.findSharedModel(thePath);
		assertSame(sharedModel, sharedModel2);
		
		// make sure not saved!
		assertFalse(((MemoryFileSystemModelProvider)bldInfModelProvider).saved);
		
		// ensure it can be released...
		bldInfModelProvider.releaseSharedModel(sharedModel2);
		bldInfModelProvider.releaseSharedModel(sharedModel);

		// and that it's not removed because of that -- we still hold one reference
		assertNotNull(bldInfModelProvider.findSharedModel(thePath));
		
		//////
		
		/// check canonical paths
		IPath funnyPath = new Path(PROJECT_NAME + "/GROUP/BLD.INF");
		sharedModel = bldInfModelProvider.findSharedModel(funnyPath);
		assertNotNull(sharedModel);
		assertSame(sharedModel,sharedModel2);
		
		assertEquals(sharedModel.getPath().toOSString(), sharedModel2.getPath().toOSString());		

		/////
		
		// now unregister
		bldInfModelProvider.unregisterModel(model);

		// should be gone now 
		assertNull(bldInfModelProvider.findSharedModel(thePath));

	}
	
	/**
	 * Test a load that happens implicitly due to querying a shared model
	 *
	 */
	public void testSharedModelLoading() throws Exception {
		IPath thePath = new Path(PROJECT_NAME + "/group/bld.inf");
		fs.put(thePath, TEST_BLDINF_0);

		// verify not shared yet
		IBldInfModel model = bldInfModelProvider.findSharedModel(thePath);
		assertNull(model);
		
		// implicitly load
		model = bldInfModelProvider.getSharedModel(thePath);
		assertNotNull(model);
		
		// make sure not saved!
		assertFalse(((MemoryFileSystemModelProvider)bldInfModelProvider).saved);

		// not it's shared
		assertEquals(model, bldInfModelProvider.findSharedModel(thePath));
		// release reference
		bldInfModelProvider.releaseSharedModel(model);
		
		// be sure it was loaded
		assertNotNull(((IOwnedModel)model).getDocument());
		assertEquals(TEST_BLDINF_0, ((IOwnedModel)model).getDocument().get());
		
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
		IPath thePath = new Path(PROJECT_NAME + "/group/bld.inf");

		// verify not shared yet
		IBldInfModel model = bldInfModelProvider.findSharedModel(thePath);
		assertNull(model);
		
		// try to implicitly load
		model = bldInfModelProvider.getSharedModel(thePath);
		
		// shouldn't be here
		assertNull(model);
		
		// make sure not saved!
		assertFalse(((MemoryFileSystemModelProvider)bldInfModelProvider).saved);
	}

	/**
	 * Test that model lives when (dangling) views exist.
	 * @throws Exception
	 */
	public void testModelLifetimeWithViews() throws Exception {
		IPath thePath = new Path(PROJECT_NAME + "/group/bld.inf");
		fs.put(thePath, TEST_BLDINF_0);
		
		////
		// owned stuff: keep reference
		IBldInfOwnedModel ownedModel = bldInfModelProvider.createModel(thePath);
		bldInfModelProvider.registerModel(ownedModel);
		assertEquals(1, ((ModelProviderBase) bldInfModelProvider).testGetUseCount(ownedModel));

		//// 
		// client stuff
		IBldInfModel model = bldInfModelProvider.findSharedModel(thePath);
		assertSame(model, ownedModel);
		
		IBldInfView view = model.createView(configuration);
		assertEquals(1, view.getAllMMPReferences().length);
		assertEquals(2, ((ModelProviderBase) bldInfModelProvider).testGetUseCount((IOwnedModel)model));

		// release our model [OOPS! view not disposed!]
		bldInfModelProvider.releaseSharedModel(model);
		
		//// 
		// owned stuff
		// assert that owner detects dangling view: this maintains the reference (to avoid cascading failures)
		try {
			bldInfModelProvider.releaseSharedModel(ownedModel);
			fail();
		} catch (IllegalStateException e) {
			
		}
		
		view.dispose();

		// should work now
		bldInfModelProvider.releaseSharedModel(ownedModel);

	}

	/** just sanity to ensure two model providers don't fight */
	public void testModelProviderIntegration() throws Exception {
		IPath thePath = new Path(PROJECT_NAME + "/group/bld.inf");
		fs.put(thePath, TEST_BLDINF_0);
		IPath theMMPPath = new Path(PROJECT_NAME + "/group/test.mmp");
		fs.put(theMMPPath, TEST_MMP_0);
		
		IBldInfModel bldInf = bldInfModelProvider.getSharedModel(thePath);
		IBldInfView bldInfView = bldInf.createView(configuration);
		
		IMMPModel mmpModel = mmpModelProvider.getSharedModel(
				new Path(PROJECT_NAME).append(
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

	private static final String TEST_BLDINF_1 = "PRJ_PLATFORMS default\n"+
	"PRJ_MMPFILES\n";

	public void testModelSaving() throws Exception {
		
		IPath thePath = new Path(PROJECT_NAME + "/group/bld.inf");
		fs.put(thePath, TEST_BLDINF_0);

		IBldInfModel model = bldInfModelProvider.getSharedModel(thePath);
		assertNotNull(model);
		
		// change
		IBldInfView view = model.createView(configuration);
		view.getMakMakeReferences().remove(0);
		view.commit();
		
		// ensure change reported and propagated
		assertEquals(TEST_BLDINF_1, fs.get(thePath));
		
		
	}
	
	public void testModelNotSaving() throws Exception {
		
		IPath thePath = new Path(PROJECT_NAME + "/group/bld.inf");
		fs.put(thePath, TEST_BLDINF_0);

		IBldInfModel model = bldInfModelProvider.getSharedModel(thePath);
		assertNotNull(model);
		
		final boolean[] modelPinged = { false };
		model.addListener(new IModelListener() {

			public void modelChanged(IOwnedModel model) {
				modelPinged[0]  =true;
			}

			public void modelUpdated(IOwnedModel model, IView view) {
				modelPinged[0]  =true;
			}
			
		});
		
		// no change!
		IBldInfView view = model.createView(configuration);
		view.commit();
		
		// ensure change not reported and not propagated
		assertFalse(modelPinged[0]);
		assertEquals(TEST_BLDINF_0, fs.get(thePath));
		
		
	}
	/**
	 * Ensure the models work sanely with threading.
	 * @throws Exception
	 */
	public void testThreading() throws CoreException {
		IPath mmpPath = projectPath.append("test.mmp");
		
		ModelProviderThreadTests test = new ModelProviderThreadTests();
		test.testThreading(mmpModelProvider, mmpConfiguration, mmpPath);
	}
	
}
