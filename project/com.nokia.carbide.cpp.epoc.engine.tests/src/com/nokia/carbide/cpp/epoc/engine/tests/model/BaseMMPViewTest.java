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
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.MMPModelFactory;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.text.IDocument;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseMMPViewTest extends BaseViewTests {

	protected IPath path;
	protected IMMPOwnedModel model;
	protected IMMPViewConfiguration mmpConfig;
	protected ArrayList<IDefine> macros;
	protected IMMPViewConfiguration allConfig;
	protected String defFileBase;
	protected boolean isEmulator;

	static boolean[] errors = { false };
	static {
		Logging.addListener(new ILogListener() {
			public void logging(IStatus status, String plugin) {
				errors[0] = true;
			}
			
		});
	}


	public BaseMMPViewTest() {
		super();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		this.path = projectPath.append("group/test.mmp");
	
		this.macros = new ArrayList<IDefine>();
	
		errors[0] = false;
		
		defFileBase = "BWINS";
		isEmulator = true;
		
		mmpConfig = new IMMPViewConfiguration() {
			public boolean isStatementSupported(EMMPStatement statement) {
				return true;
			}
	
			public IViewFilter getViewFilter() {
				return new AcceptedNodesViewFilter();
			}
	
			public Collection<IDefine> getMacros() {
				return macros;
			}
			
			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}
			
			public String getDefaultDefFileBase(boolean isASSP) {
				return defFileBase;
			}
			public boolean isEmulatorBuild() {
				return isEmulator;
			}
		};
		
		allConfig = new IMMPViewConfiguration() {
			public boolean isStatementSupported(EMMPStatement statement) {
				return true;
			}
	
			public IViewFilter getViewFilter() {
				return new AllNodesViewFilter();
			}
	
			public Collection<IDefine> getMacros() {
				return macros;
			}
			
			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}
			public String getDefaultDefFileBase(boolean isASSP) {
				return defFileBase;
			}
			public boolean isEmulatorBuild() {
				return isEmulator;
			}
		};
	}

	protected void makeModel() {
		String text = parserConfig.getFilesystem().get(modelPath.lastSegment());
		IDocument document = DocumentFactory.createDocument(text);
		model = new MMPModelFactory().createModel(modelPath, document);
	
		model.parse();
	}

	protected void makeModel(String text) {
		IDocument document = DocumentFactory.createDocument(text);
		model = new MMPModelFactory().createModel(path, document);
	
		model.parse();
	}

	protected IMMPView getView(IMMPViewConfiguration config) {
		IMMPView view = (IMMPView) model.createView(config);
		assertNotNull(view);
		return view;
	}

	protected void commitTest(IMMPView view, String refText) {
		commitTest(model, view, refText);
	}

}
