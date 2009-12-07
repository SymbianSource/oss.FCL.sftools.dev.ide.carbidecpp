/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.carbide.cpp.epoc.engine.*;
import com.nokia.carbide.cpp.epoc.engine.model.*;
import com.nokia.carbide.cpp.epoc.engine.model.bsf.IBSFOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.bsf.IBSFView;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.*;
import com.nokia.cpp.internal.api.utils.core.IMessage;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.text.IDocument;

import java.util.*;

public class TestBSFView extends BaseViewTests {
	static final public String HEADER = "#<bsf>#\n";
	protected IPath path;
	protected IBSFOwnedModel model;
	protected IViewConfiguration config;
	protected IViewConfiguration allConfig;

	public TestBSFView() {
		super();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.path = new Path("c:/symbian/9.5/epoc32/tools/Test.bsf");
	
		config = new IViewConfiguration() {
	
			public IViewFilter getViewFilter() {
				return new AcceptedNodesViewFilter();
			}
	
			public Collection<IDefine> getMacros() {
				return Collections.emptyList() ;
			}
			
			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}
		};
	}

	protected void makeModel(String text) {
		IDocument document = DocumentFactory.createDocument(text);
		model = new BSFModelFactory().createModel(path, document);
	
		model.parse();
	}

	protected IBSFView getView(IViewConfiguration config) {
		IBSFView view = (IBSFView) model.createView(config);
		assertNotNull(view);
		return view;
	}

	public void testInvalid() throws Exception {
		makeModel("some random file");

		IBSFView view = getView(config);
		assertNotNull(view);

		assertEquals(ETristateFlag.UNSPECIFIED, view.getCompileWithParent());
		assertFalse(view.isVariant());
		assertFalse(view.isVirtualVariant());

		IMessage[] messages = view.getMessages();
		assertEquals(2, messages.length);
		assertEquals("BSFView.InvalidBSFHeader", messages[0].getMessageKey());
		assertEquals("BSFView.NoCustomizesStatement", messages[1].getMessageKey());
	}

	public void testCustomizesParsing() throws Exception {
		makeModel(HEADER +
				"CUSTOMIZES ARMV5\n");

		IBSFView view = getView(config);
		assertNotNull(view);
		checkNoProblems(view);
		
		assertEquals("Test", view.getName());
		
		assertEquals("ARMV5", view.getCustomizes());
		assertEquals(ETristateFlag.UNSPECIFIED, view.getCompileWithParent());
		assertFalse(view.isVariant());
		assertFalse(view.isVirtualVariant());
	}
	
	public void testCustomizesParsing2() throws Exception {
		// CUSTOMIZES is specially detected so if specified more than
		// once, the second looks like a customization option
		makeModel(HEADER +
				"CUSTOMIZES ARMV5\n"+
				"CUSTOMIZES BLAH\n"
				);

		IBSFView view = getView(config);
		assertNotNull(view);
		checkNoProblems(view);
		
		assertEquals("ARMV5", view.getCustomizes());
		assertEquals(ETristateFlag.UNSPECIFIED, view.getCompileWithParent());
		assertFalse(view.isVariant());
		assertFalse(view.isVirtualVariant());
		
		// the second entry is detected as an option
		assertEquals("BLAH", view.getCustomizationOptions().get("CUSTOMIZES"));
	}
	
	public void testVariantParsing() throws Exception {
		makeModel(HEADER +
				"CUSTOMIZES ARMV5\n"+
				"VARIANT");

		IBSFView view = getView(config);
		assertNotNull(view);
		checkNoProblems(view);
		
		assertEquals("ARMV5", view.getCustomizes());
		assertTrue(view.isVariant());
		assertFalse(view.isVirtualVariant());
		
		makeModel(HEADER +
				"CUSTOMIZES ARMV5\n"+
				"VIRTUALVARIANT\n"
				);

		view = getView(config);
		assertNotNull(view);
		checkNoProblems(view);
		
		assertEquals(ETristateFlag.UNSPECIFIED, view.getCompileWithParent());
		assertFalse(view.isVariant());
		assertTrue(view.isVirtualVariant());

	}
	
	public void testCompileParsing() throws Exception {
		makeModel(HEADER +
				"CUSTOMIZES ARMV5\n"+
				"COMPILEWITHPARENT");

		IBSFView view = getView(config);
		assertNotNull(view);
		checkNoProblems(view);

		assertEquals(ETristateFlag.ENABLED, view.getCompileWithParent());

		makeModel(HEADER +
				"CUSTOMIZES ARMV5\n"+
				"COMPILEALONE\n"
				);

		view = getView(config);
		assertNotNull(view);
		checkNoProblems(view);

		assertEquals(ETristateFlag.DISABLED, view.getCompileWithParent());

		makeModel(HEADER +
				"CUSTOMIZES ARMV5\n"
				);

		view = getView(config);
		assertNotNull(view);
		checkNoProblems(view);

		assertEquals(ETristateFlag.UNSPECIFIED, view.getCompileWithParent());

		view = getView(config);
		assertNotNull(view);
		checkNoProblems(view);

	}
	
}
