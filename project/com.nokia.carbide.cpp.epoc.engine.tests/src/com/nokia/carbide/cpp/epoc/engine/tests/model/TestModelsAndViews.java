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

import com.nokia.carbide.cpp.epoc.engine.model.IModelListener;
import com.nokia.carbide.cpp.epoc.engine.model.IModelLoadResults;
import com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.IViewListener;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.cpp.epoc.engine.tests.model.dummy.DummyModel;
import com.nokia.carbide.cpp.epoc.engine.tests.model.dummy.IDummyView;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ViewASTBase;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;

import java.util.Collection;
import java.util.Collections;

/**
 * Test IOwnedModel and IView APIs
 *
 */
public class TestModelsAndViews extends BaseViewTests {

	private IPath path;
	private IViewConfiguration viewConfig;
	private DummyModel model;


	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.model.BaseTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.path = new Path("test.mdl");
		
		viewConfig = new IViewConfiguration() {

			public IViewFilter getViewFilter() {
				return new AcceptedNodesViewFilter();
			}

			public Collection<IDefine> getMacros() {
				return Collections.emptyList();
			}
			
			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}

		};
		
	}
	
	
	class ModelListener implements IModelListener {
		IOwnedModel changedModel;
		IView updatedView;
		public void modelUpdated(IOwnedModel model, IView view) {
			updatedView = view;
		}
		public void modelChanged(IOwnedModel model) {
			changedModel = model;
		}
	}

	protected void makeModel(String text) {
		IDocument document = new Document(text);
		model = new DummyModel(path, document);
		
	}

	public void testCreateModel() throws Exception {
		makeModel("my model\n");

		assertEquals(path, model.getPath());
		assertNotNull(model.getDocument());

		assertEquals(0, model.getViews().length);
		
		assertNull(model.getTranslationUnit());
		IModelLoadResults results = model.parse();
		assertNotNull(results);
		assertEquals(0, results.getMessages().length);

		assertNotNull(model.getTranslationUnit());
		assertEquals(0, model.getViews().length);

		assertEquals("my model\n", model.getTranslationUnit().getNewText());
		
		// verify that illegal access detected
		try {
			model.getTranslationUnit().getNodes().clear();
			fail();
		} catch (IllegalStateException e) {
			
		}
		
		model.dispose();
	}
	
	public void testCreateView() throws Exception {
		makeModel("my model\n");

		// add model
		ModelListener listener = new ModelListener();
		model.addListener(listener);
		
		// parse content
		model.parse();
		
		// assert we detected the change
		assertEquals(model, listener.changedModel);
		listener.changedModel = null;

		// make a view
		IDummyView view = (IDummyView) model.createView(viewConfig);
		assertNotNull(view);
		
		assertNotNull(getTU(view));
		assertNotSame(model.getTranslationUnit(), getTU(view));
		assertEquals(model, view.getModel());
		
		// ensure model knows
		IView[] views = model.getViews();
		assertEquals(1, views.length);
		assertEquals(view, views[0]);
		
		// test contents
		assertEquals(1, getTU(view).getNodes().size());
		assertEquals("my model\n", getTU(view).getNewText());
		
		// verify that illegal access detected
		try {
			getTU(view).getNodes().clear();
			fail();
		} catch (IllegalStateException e) {
		}
		
		// verify cannot dispose with view
		try {
			model.dispose();
			fail();
		} catch (IllegalStateException e) {
			
		}
		
		view.dispose();
		
		// make sure model noticed
		views = model.getViews();
		assertEquals(0, views.length);

		// assert no other listeners
		assertNull(listener.changedModel);

		model.dispose();
	}

	private IASTTranslationUnit getTU(IDummyView view) {
		return ((ViewASTBase)view).getFilteredTranslationUnit();
	}
	
	class ViewListener implements IViewListener {
		IView changedView;
		public void viewChanged(IView view) {
			changedView = view;
		}
	}

	/**
	 * Test modifying single view (note: real tests in {@link TestViewChanging})
	 * and the transaction APIs.
	 * @throws Exception
	 */
	public void testModifySingleView() throws Exception {
		makeModel("my model\n");

		model.parse();

		// make a view
		IDummyView view = (IDummyView) model.createView(viewConfig);
		assertNotNull(view);

		// add listeners
		ViewListener view1Listener = new ViewListener();
		view.addListener(view1Listener);

		// add local contents
		view.add("another line");
		
		// assert change reported
		assertEquals(view, view1Listener.changedView);
		view1Listener.changedView = null;
		
		// assert not changed yet
		assertEquals(1, getTU(view).getNodes().size());
		assertEquals("my model\n", getTU(view).getNewText());
		
		// commit changes
		view.commit();
		
		//assertFalse(view.hasChanges());
		assertFalse(view.needsSynchonize());
		
		// make sure view gets changes
		assertEquals("my model\nanother line\n", getTU(view).getNewText());
		assertEquals(2, getTU(view).getNodes().size());
		
		// make sure model reflects changes
		assertEquals("my model\nanother line\n", model.getTranslationUnit().getNewText());
		assertEquals("my model\nanother line\n", model.getDocument().get());
		view.dispose();

		model.dispose();
	}
	
	
	/**
	 * Test modifying single view while another view is open to test
	 * the transaction APIs.
	 * @throws Exception
	 */
	public void testModifyOneOfTwoViews() throws Exception {
		makeModel("my model\n");

		model.parse();

		// make a view
		IDummyView view1 = (IDummyView) model.createView(viewConfig);
		assertNotNull(view1);

		// make another view
		IDummyView view2 = (IDummyView) model.createView(viewConfig);
		assertNotNull(view2);

		// add listeners

		ModelListener view1ModelListener = new ModelListener();
		model.addListener(view1ModelListener);
		ModelListener view2ModelListener = new ModelListener();
		model.addListener(view2ModelListener);

		// add local contents
		view1.add("another line");
		
		// assert not changed yet
		assertEquals("my model\n", getTU(view1).getNewText());
		assertEquals(1, getTU(view1).getNodes().size());
		
		// assert other view is okay
		assertEquals("my model\n", getTU(view2).getNewText());
		assertEquals(1, getTU(view2).getNodes().size());

		// commit changes
		view1.commit();

		// assert view1 didn't get pinged about itself
		assertNull(view1ModelListener.changedModel);
		assertEquals(view1, view1ModelListener.updatedView);
		
		//assertFalse(view1.hasChanges());
		assertFalse(view1.needsSynchonize());
		
		// assert view2 did get pinged
		assertNull(view2ModelListener.changedModel);
		assertEquals(view1, view2ModelListener.updatedView);
		
		// assert view2 is out-of-sync
		//assertFalse(view2.hasChanges());
		assertTrue(view2.needsSynchonize());
		
		// make sure view1 gets changes
		assertEquals("my model\nanother line\n", getTU(view1).getNewText());
		assertEquals(2, getTU(view1).getNodes().size());
		
		// make sure model reflects changes
		assertEquals("my model\nanother line\n", model.getTranslationUnit().getNewText());
		assertEquals("my model\nanother line\n", model.getDocument().get());
		
		// assert view2 is out of sync
		assertTrue(view2.needsSynchonize());
		
		// make sure view2 can still be changed
		view2.add("foofy\n");
		
		// make sure it still reflects its old content
		assertEquals("my model\n", getTU(view2).getNewText());
		assertEquals(1, getTU(view2).getNodes().size());
		
		// make sure view2 cannot be committed
		try {
			view2.commit();
			fail();
		} catch (IllegalStateException e) {
			
			// try to revert
			view2.revert();
			//assertFalse(view2.hasChanges());
			assertFalse(view2.needsSynchonize());
			
			// assert it has the new contents
			assertEquals("my model\nanother line\n", getTU(view2).getNewText());
			assertEquals(2, getTU(view2).getNodes().size());
		}

		// assert you can dispose out of order
		view1.dispose();
		
		view2.dispose();


		model.dispose();
	}
	
	/**
	 * Test modifying two views at the same time, committing them both,
	 * and merging to allow committing the second, to validate the transaction APIs.
	 * @throws Exception
	 */
	public void testModifyTwoViewsWithMerge() throws Exception {
		makeModel("my model\n");

		model.parse();

		// make a view
		IDummyView view1 = (IDummyView) model.createView(viewConfig);
		assertNotNull(view1);

		// make another view
		IDummyView view2 = (IDummyView) model.createView(viewConfig);
		assertNotNull(view2);

		// add listeners
		ModelListener view1ModelListener = new ModelListener();
		model.addListener(view1ModelListener);
		ModelListener view2ModelListener = new ModelListener();
		model.addListener(view2ModelListener);

		ViewListener view1Listener = new ViewListener();
		ViewListener view2Listener = new ViewListener();
		view1.addListener(view1Listener);
		view2.addListener(view2Listener);
		
		// add local contents
		view1.add("another line #1");
		view2.add("another line #1");	// pseudo-conflict
		view2.add("another line #2");	// unique change
		
		// commit changes
		view1.commit();
		//assertFalse(view1.hasChanges());
		assertFalse(view1.needsSynchonize());

		// assert view2 is out of sync
		assertTrue(view2.needsSynchonize());
		
		// make sure view2 cannot be committed
		try {
			view1ModelListener.updatedView = null;
			view1ModelListener.changedModel = null;
			view2ModelListener.updatedView = null;
			view2ModelListener.changedModel = null;
		
			view1Listener.changedView = null;
			view2Listener.changedView = null;
			
			view2.commit();
			fail();
		} catch (IllegalStateException e) {
			
			// assert view1 is not notified as changing
			assertNull(view2Listener.changedView);
			assertNull(view2ModelListener.updatedView);
			assertNull(view2ModelListener.changedModel);
			
			// try to merge
			assertTrue(view2.merge());

			// assert the merge was reported
			assertEquals(view2, view2Listener.changedView);

			// assert the change was not reported
			assertNull(view1ModelListener.updatedView);
			assertNull(view1ModelListener.changedModel);
			assertNull(view2ModelListener.updatedView);
			assertNull(view2ModelListener.changedModel);

			// assert it has the new contents
			assertEquals("my model\nanother line #1\n", getTU(view2).getNewText());
			assertEquals(2, getTU(view2).getNodes().size());

			// still modified
			//assertTrue(view2.hasChanges());
			assertFalse(view2.needsSynchonize());
			
			// should be commitable now (in real life, need a loop
			// in case another committer sneaked in)

			view2Listener.changedView = null;
			try {
				view2.commit();
			} catch (IllegalStateException e2) {
				fail();
			}

			// the view did change, since it was reparsed
			assertEquals(view2, view2Listener.changedView);
			
			// and the view changed the model
			assertEquals(view2, view1ModelListener.updatedView);
			assertEquals(view2, view2ModelListener.updatedView);

			// assert it has all the contents
			assertEquals("my model\nanother line #1\nanother line #2\n", getTU(view2).getNewText());
			assertEquals(3, getTU(view2).getNodes().size());
		}

		// assert you can dispose out of order
		view1.dispose();
		
		view2.dispose();


		model.dispose();
	}
	
	/**
	 * Test modifying two views at the same time, committing them both,
	 * and detecting conflicts, to validate the transaction APIs.
	 * @throws Exception
	 */
	public void testModifyTwoViewsWithConflict() throws Exception {
		makeModel("my model\n");

		model.parse();

		// make a view
		IDummyView view1 = (IDummyView) model.createView(viewConfig);
		assertNotNull(view1);

		// make another view
		IDummyView view2 = (IDummyView) model.createView(viewConfig);
		assertNotNull(view2);

		// add listeners
		ModelListener view1ModelListener = new ModelListener();
		model.addListener(view1ModelListener);
		ModelListener view2ModelListener = new ModelListener();
		model.addListener(view2ModelListener);

		ViewListener view1Listener = new ViewListener();
		ViewListener view2Listener = new ViewListener();
		view1.addListener(view1Listener);
		view2.addListener(view2Listener);
		
		// add local contents
		view1.add("another line #1");
		view1.remove("my model");
		view2.change("my model", "foo model");	// conflicting change
		
		// commit changes
		view1.commit();
		//assertFalse(view1.hasChanges());
		assertFalse(view1.needsSynchonize());

		// assert the model is updated
		assertEquals("another line #1\n", model.getTranslationUnit().getNewText());
		assertEquals(1, model.getTranslationUnit().getNodes().size());

		// assert view2 is out of sync
		assertTrue(view2.needsSynchonize());
		
		// make sure view2 cannot be committed
		try {
			view1ModelListener.updatedView = null;
			view1ModelListener.changedModel = null;
			view2ModelListener.updatedView = null;
			view2ModelListener.changedModel = null;
		
			view1Listener.changedView = null;
			view2Listener.changedView = null;
			
			view2.commit();
			fail();
		} catch (IllegalStateException e) {
			
			// assert view1 is not notified as changing
			assertNull(view2Listener.changedView);
			assertNull(view2ModelListener.updatedView);
			assertNull(view2ModelListener.changedModel);
			
			// try to merge but fail
			assertFalse(view2.merge());
			
			// assert the failed merge was reported
			assertEquals(view2, view2Listener.changedView);

			// assert the change was not reported
			assertNull(view1ModelListener.updatedView);
			assertNull(view1ModelListener.changedModel);
			assertNull(view2ModelListener.updatedView);
			assertNull(view2ModelListener.changedModel);

			// assert it has the new base contents
			assertEquals("another line #1\n", getTU(view2).getNewText());
			assertEquals(1, getTU(view2).getNodes().size());

			// ensure we can change after that
			view2.add("another line #2");
			
			// still modified
			//assertTrue(view2.hasChanges());
			assertTrue(view2.needsSynchonize());
			
			// assert can't commit
			try {
				view2.commit();
				fail();
			} catch (IllegalStateException e3) {
				
			}
			
			// force synchronize 
			view2.forceSynchronized();
			//assertTrue(view2.hasChanges());
			assertFalse(view2.needsSynchonize());

			// should be commitable now (in real life, need a loop
			// in case another committer sneaked in)

			view2Listener.changedView = null;
			try {
				view2.commit();
			} catch (IllegalStateException e2) {
				fail();
			}

			// the view did change, since it was reparsed
			assertEquals(view2, view2Listener.changedView);
			
			// and the view changed the model
			assertEquals(view2, view1ModelListener.updatedView);
			assertEquals(view2, view2ModelListener.updatedView);

			// assert it has all the contents
			assertEquals("another line #1\nanother line #2\n", getTU(view2).getNewText());
			assertEquals(2, getTU(view2).getNodes().size());
		}

		// assert you can dispose out of order
		view1.dispose();
		
		view2.dispose();


		model.dispose();
	}
	
	public void testModelDocumentChanging() {
		makeModel("my model\n");

		model.parse();

		// make a view
		IDummyView view1 = (IDummyView) model.createView(viewConfig);
		assertNotNull(view1);

		// try to change document
		model.getDocument().set("foo");
		
		// now, the views should be out of sync
		assertTrue(view1.needsSynchonize());
		view1.revert();
		
		// can't do this, though
		try {
			model.setDocument(new Document("bar"));
			fail();
		} catch (AssertionError e) {
			
		}
		
		view1.dispose();
		
		// can still change now
		model.getDocument().set("foo");
		
		// and can change out document directly once views are gone
		model.setDocument(new Document("bar"));
		
	}
	
	public void testPathConversion() {
		path = projectPath.append("path/to/mymodel.mdl");
		makeModel("my model\n");

		model.parse();

		// make a view
		IDummyView view = (IDummyView) model.createView(viewConfig);
		assertNotNull(view);
		
		assertEquals(new Path("mymodel.mdl"), 
				view.convertProjectToModelPath(new Path("path/to/mymodel.mdl")));
		assertEquals(new Path("../parentfile.txt"), 
				view.convertProjectToModelPath(new Path("path/parentfile.txt")));
		assertEquals(new Path("../../../crazyfile.txt"), 
				view.convertProjectToModelPath(new Path("../crazyfile.txt")));
		assertEquals(new Path("subdir/kidfile.txt"), 
				view.convertProjectToModelPath(new Path("path/to/subdir/kidfile.txt")));
		assertEquals(null, 
				view.convertProjectToModelPath(null));
		assertEquals(new Path("/foo/bar/baz.txt"), 
				view.convertProjectToModelPath(new Path("/foo/bar/baz.txt")));
		assertEquals(new Path("x:/foo/bar/baz.txt"), 
				view.convertProjectToModelPath(new Path("x:/foo/bar/baz.txt")));
		
		assertEquals(new Path("path/to/mymodel.mdl"),
				view.convertModelToProjectPath(new Path("mymodel.mdl")));
		assertEquals(new Path("path/parentfile.txt"),
				view.convertModelToProjectPath(new Path("../parentfile.txt")));
		assertEquals(new Path("../crazyfile.txt"), 
				view.convertModelToProjectPath(new Path("../../../crazyfile.txt")));
		assertEquals(new Path("path/to/subdir/kidfile.txt"),
				view.convertModelToProjectPath(new Path("subdir/kidfile.txt")));
		assertEquals(null, 
				view.convertProjectToModelPath(null));
		assertEquals(new Path("/foo/bar/baz.txt"), 
				view.convertProjectToModelPath(new Path("/foo/bar/baz.txt")));
		assertEquals(new Path("x:/foo/bar/baz.txt"), 
				view.convertProjectToModelPath(new Path("x:/foo/bar/baz.txt")));
		
	}
}
