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

import java.io.ByteArrayInputStream;
import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.IModelListener;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPModel;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.cpp.internal.api.utils.core.FileUtils;


/**
 * This tests the use pattern of an editor working on a model
 * and watching for changes to the model.  Since the editor
 * needs to intermittently commit its text in order for the
 * text view to show up, but doesn't want to persist such
 * changes, as would happen if it made changes on a view
 * whose model is owned by a model provider, so it needs to 
 * work from a private model.  When the editor decides to save 
 * its content, it updates the shared model's content with its own content.
 *
 */
public class TestEditorSharing extends BaseProjectTests {
	private Path mmpPath;

	private IMMPModel watchModel;

	private IModelListener watchListener;
	private boolean modelChanged ;
	private boolean modelUpdated ;
	
	private static final String REF_PROJECT = "data/provider-test/editor-sharing/";
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.workspace.BaseProjectTests#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		project.getFolder("group").create(false, true, null);
		
		mmpPath = new Path(projectName + "/group/test.mmp");
		copyRefToProject("group/test.mmp");
		
		// load shared model, to monitor its changes
		watchModel = mmpModelProvider.getSharedModel(mmpPath);
		assertNotNull(watchModel);
		
		watchListener = new IModelListener() {

			public void modelChanged(IOwnedModel model) {
				modelChanged = true;
			}

			public void modelUpdated(IOwnedModel model, IView view) {
				modelUpdated = true;
			}
			
		};
		
		watchModel.addListener(watchListener);
		modelChanged = false;
		modelUpdated = false;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.workspace.BaseProjectTests#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		watchModel.removeListener(watchListener);
		mmpModelProvider.releaseSharedModel(watchModel);
		super.tearDown();
	}

	private String getRefFile(String path) throws Exception {
		File file = projectRelativeFile(REF_PROJECT + path);
		char[] text = FileUtils.readFileContents(file, null);
		return new String(text);
	}
	
	private void copyRefToProject(String path, String trgPath) throws Exception {
		IPath projPath = new Path(projectName).append(trgPath);
		String text = getRefFile(path);
		ByteArrayInputStream bis = new ByteArrayInputStream(text.getBytes());
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(projPath);
		if (file.exists())
			file.setContents(bis, false, true, null);
		else
			file.create(bis, true, null);
	}

	private void copyRefToProject(String path) throws Exception {
		copyRefToProject(path, path);
	}
	public void testOnlyEditorModifies() throws Exception {
		// new model can use existing registered model's path as
		// long as it's not also registered
		IMMPOwnedModel model = mmpModelProvider.createModel(mmpPath);
		mmpModelProvider.load(model);
		model.parse();
		
		IMMPView view = model.createView(mmpConfiguration);
		view.getSources().add(new Path("src/another.cpp"));
		
		view.commit();

		sleep();
		
		// the watched model is not affected
		assertFalse(modelChanged);
		
		// but the owned model has changed
		assertEquals(getRefFile("group/test_1.mmp"), model.getDocument().get());

		///////
		
		view.dispose();
	}
	
	public void testExternalModification() throws Exception {
		IMMPOwnedModel model = mmpModelProvider.createModel(mmpPath);
		mmpModelProvider.load(model);
		model.parse();
		
		IMMPView view = model.createView(mmpConfiguration);

		// external change
		copyRefToProject("group/test_1.mmp", "group/test.mmp");
		
		// wait for listeners
		sleep();
		
		// the watched model is affected
		assertTrue(modelChanged);
		assertFalse(modelUpdated);
		
		// but the view on the owned model has not changed
		assertFalse(view.needsSynchonize());
		assertEquals(getRefFile("group/test.mmp"), model.getDocument().get());

		// now, forcibly reload and reparse the model
		mmpModelProvider.load(model);
		model.parse();
		assertEquals(getRefFile("group/test_1.mmp"), model.getDocument().get());
		
		// make sure view noticed
		assertTrue(view.needsSynchonize());
		view.revert();
		assertEquals(getRefFile("group/test_1.mmp"), model.getDocument().get());
		
		///////
		
		
		view.dispose();
	
	}
	
}
