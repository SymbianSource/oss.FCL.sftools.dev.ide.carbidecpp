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

package com.nokia.carbide.cpp.epoc.engine.tests.model;

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.model.BldInfModelFactory;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseBldInfViewTest extends BaseViewTests {

	protected IPath path;
	protected IBldInfOwnedModel model;
	protected IViewConfiguration config;
	protected Collection<IDefine> macros;
	protected IViewConfiguration allConfig;

	public BaseBldInfViewTest() {
		super();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.path =projectPath.append("group/bld.inf");
	
		macros = new ArrayList<IDefine>();
		config = new IViewConfiguration() {
	
			public IViewFilter getViewFilter() {
				return new AcceptedNodesViewFilter();
			}
	
			public Collection<IDefine> getMacros() {
				return macros ;
			}
			
			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}
		};
		
		allConfig = new IViewConfiguration() {
	
			public IViewFilter getViewFilter() {
				return new AllNodesViewFilter();
			}
	
			public Collection<IDefine> getMacros() {
				return macros ;
			}
			
			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}
		};
	}

	protected void makeModel(String text) {
		IDocument document = DocumentFactory.createDocument(text);
		model = new BldInfModelFactory().createModel(path, document);
	
		model.parse();
	}

	protected IBldInfView getView(IViewConfiguration config) {
		IBldInfView view = (IBldInfView) model.createView(config);
		assertNotNull(view);
		return view;
	}

	protected void commitTest(IBldInfView view, String refText) {
		commitTest(model, view, refText);
	}

}
