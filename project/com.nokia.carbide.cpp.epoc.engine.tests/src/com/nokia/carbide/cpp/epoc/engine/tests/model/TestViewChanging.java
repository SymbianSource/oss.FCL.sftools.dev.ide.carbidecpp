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

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
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
import org.eclipse.jface.text.IDocument;

import java.util.Collection;
import java.util.Collections;

/**
 * Test that changes made to a view propagate correctly to the model's TU.
 *
 */
public class TestViewChanging extends BaseViewTests {

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
				return Collections.EMPTY_LIST;
			}
			
			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}
		};
		
	}
	

	protected void makeModel(String text) {
		IDocument document = DocumentFactory.createDocument(text);
		model = new DummyModel(path, document);

		model.parse();
	}
	
	protected IDummyView getView(IViewConfiguration config) {
		IDummyView view = (IDummyView) model.createView(config);
		assertNotNull(view);
		return view;
	}
	
	private IASTTranslationUnit getTU(IView view) {
		return ((ViewASTBase)view).getFilteredTranslationUnit();
	}
	/**
	 * Test adding new text, which goes outside #ifs. 
	 * @throws Exception
	 */
	public void testAddNew() throws Exception {
		makeModel("#if 1\nmy model\n#endif\n");

		IDummyView view = getView(viewConfig);
		assertNotNull(view);
		
		// make sure view has filtered stuff changes
		assertEquals(1, getTU(view).getNodes().size());
		assertEquals("my model\n", getTU(view).getNewText());

		view.add("another line");
		
		// commit changes
		view.commit();
		
		// make sure view gets changes
		assertEquals("my model\nanother line\n", getTU(view).getNewText());
		assertEquals(2, getTU(view).getNodes().size());
		
		// make sure model reflects changes and original tests
		assertEquals("#if 1\nmy model\n#endif\nanother line\n", model.getTranslationUnit().getNewText());
		assertEquals("#if 1\nmy model\n#endif\nanother line\n", model.getDocument().get());
		view.dispose();

		model.dispose();
	}
	
	/**
	 * Test changing existing text in a normal case.
	 * @throws Exception
	 */
	public void testChangeExisting() throws Exception {
		makeModel("line 1\nmy model\n");

		IDummyView view = getView(viewConfig);
		assertNotNull(view);
		
		// make sure view has filtered stuff changes
		assertEquals(2, getTU(view).getNodes().size());
		assertEquals("line 1\nmy model\n", getTU(view).getNewText());

		view.change("my model", "your model");
		
		// commit changes
		view.commit();
		
		// make sure view gets changes
		assertEquals("line 1\nyour model\n", getTU(view).getNewText());
		assertEquals(2, getTU(view).getNodes().size());
		
		// make sure model reflects changes and original tests
		assertEquals("line 1\nyour model\n", model.getTranslationUnit().getNewText());
		assertEquals("line 1\nyour model\n", model.getDocument().get());
		view.dispose();

		model.dispose();
	}
	
	/**
	 * Test changing existing text, which remains inside the #if.
	 * @throws Exception
	 */
	public void testChangeExistingCond() throws Exception {
		makeModel("#if 1\nmy model\n#endif\n");

		IDummyView view = getView(viewConfig);
		assertNotNull(view);
		
		// make sure view has filtered stuff changes
		assertEquals(1, getTU(view).getNodes().size());
		assertEquals("my model\n", getTU(view).getNewText());

		view.change("my model", "your model");
		
		// commit changes
		view.commit();
		
		// make sure view gets changes
		assertEquals("your model\n", getTU(view).getNewText());
		assertEquals(1, getTU(view).getNodes().size());
		
		// make sure model reflects changes and original tests
		assertEquals("#if 1\nyour model\n#endif\n", model.getTranslationUnit().getNewText());
		assertEquals("#if 1\nyour model\n#endif\n", model.getDocument().get());
		view.dispose();

		model.dispose();
	}

	/**
	 * Test changing existing text, which remains inside the #if.
	 * @throws Exception
	 */
	public void testChangeExistingCond2() throws Exception {
		// be sure to change the right one!
		makeModel("#if 0\nmy model\n#else\nmy model\n#endif\n");

		IDummyView view = getView(viewConfig);
		assertNotNull(view);
		
		// make sure view has filtered stuff changes
		assertEquals(1, getTU(view).getNodes().size());
		assertEquals("my model\n", getTU(view).getNewText());

		view.change("my model", "your model");
		
		// commit changes
		view.commit();
		
		// make sure view gets changes
		assertEquals("your model\n", getTU(view).getNewText());
		assertEquals(1, getTU(view).getNodes().size());
		
		// make sure model reflects changes and original tests
		assertEquals("#if 0\nmy model\n#else\nyour model\n#endif\n", model.getTranslationUnit().getNewText());
		assertEquals("#if 0\nmy model\n#else\nyour model\n#endif\n", model.getDocument().get());
		view.dispose();

		model.dispose();
	}
	
	/**
	 * Test changing existing text, which has conditional parts inside it.
	 * This comments out the node and adds a new one.
	 * @throws Exception
	 */
	public void testChangeExistingMacros() throws Exception {
		makeModel("#define FROTZ model\nmy FROTZ\n");

		IDummyView view = getView(viewConfig);
		assertNotNull(view);
		
		// make sure view has filtered stuff changes
		assertEquals(1, getTU(view).getNodes().size());
		assertEquals("my model\n", getTU(view).getNewText());

		view.change("my model", "your model");
		
		// commit changes
		view.commit();
		
		// make sure view gets changes
		assertEquals("your model\n", getTU(view).getNewText());
		assertEquals(1, getTU(view).getNodes().size());
		
		// make sure model reflects changes and original tests
		assertEquals("#define FROTZ model\n#if 0\nmy FROTZ\n#endif\nyour model\n", model.getTranslationUnit().getNewText());
		assertEquals("#define FROTZ model\n#if 0\nmy FROTZ\n#endif\nyour model\n", model.getDocument().get());
		view.dispose();

		model.dispose();
	}
}
