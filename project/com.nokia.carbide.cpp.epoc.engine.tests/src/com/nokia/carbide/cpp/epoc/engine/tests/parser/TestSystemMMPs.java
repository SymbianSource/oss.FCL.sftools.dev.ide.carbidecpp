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

package com.nokia.carbide.cpp.epoc.engine.tests.parser;

import com.nokia.carbide.cpp.epoc.engine.model.IModelProvider;
import com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.MMPModelFactory;
import com.nokia.carbide.cpp.epoc.engine.model.ModelProviderFactory;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPModel;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTProblemNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ITranslationUnitParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.mmp.IMMPParserConfiguration;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ASTPreprocessor;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test parsing system MMPs.
 *
 */
public abstract class TestSystemMMPs extends BaseTest {
	static final String TEST_DIR = "data/mmp/";

	private IMMPParserConfiguration happyConfig;
	private IMMPParserConfiguration mmpConfig;
	private AcceptedNodesViewFilter viewFilter;
	private ArrayList<IDefine> macros;
	private IViewConfiguration viewConfig;

	private IModelProvider mmpModelProvider;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		mmpModelProvider = ModelProviderFactory.createStandaloneModelProvider(new MMPModelFactory());
		happyConfig = new IMMPParserConfiguration() {

			public boolean isAifStatementRecognized() {
				return true;
			}
			
			public boolean isBitmapSourceStatementRecognized() {
				return true;
			}

			public boolean isOptionStatementRecognized() {
				return true;
			}

			public boolean isStartBlockStatementRecognized() {
				return true;
			}

			public boolean isUidStatementRecognized() {
				return true;
			}

			public int categorizeStatement(String keyword) {
				try {
					return EMMPStatement.valueOf(keyword).getCategory();
				} catch (IllegalArgumentException e) {
					return UNKNOWN_STATEMENT;
				}
			}
			
		};
		
		mmpConfig = happyConfig;
		viewFilter = new AcceptedNodesViewFilter();
		macros = new ArrayList<IDefine>();
		viewConfig = new IMMPViewConfiguration() {

			public IViewFilter getViewFilter() {
				return viewFilter;
			}

			public Collection<IDefine> getMacros() {
				return macros;
			}
			
			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}

			public String getDefaultDefFileBase(boolean isASSP) {
				return "BWINS";
			}

			public boolean isEmulatorBuild() {
				return true;
			}

			public boolean isStatementSupported(EMMPStatement statement) {
				return true;
			}

		};
		
		File[] files = projectRelativeFile(TEST_DIR).listFiles(new FileFilter() {

			public boolean accept(File pathname) {
				return !pathname.getName().startsWith(".") 
				&& !pathname.isDirectory();
			}
			
		});
		for (File file : files) {
			parserConfig.getFilesystem().put(file.getName(), load(file.getName()));
		}
	}
	
	protected IASTTranslationUnit parse(String filename) throws Exception {
		IDocument document = parserConfig.getModelDocumentProvider().getDocument(new File(filename));
		IDocumentParser preparser = ParserFactory.createPreParser();
		IASTTranslationUnit ppTu = preparser.parse(new Path(filename), document); 
		
		// preprocess
		ASTPreprocessor preprocessor = createPreprocessor(parserConfig);
		IPreprocessorResults results = preprocessor.preprocess(ppTu, viewFilter, macros);
		
		ITranslationUnitParser parser = ParserFactory.createMMPParser(mmpConfig);
		IASTTranslationUnit mmpTu = parser.parse(results);
		assertNoProblems(mmpTu);
		return mmpTu;
	}
	
	// load testdir-relative file
	protected String load(String name) throws Exception {
		File file = projectRelativeFile(TEST_DIR + name);
		char[] text = FileUtils.readFileContents(file, null);
		
		return new String(text);
	}
	
	class MMPParseTest extends TestCase {

		private String file;

		public MMPParseTest(String file) {
			super("Parsing " + file);
			this.file = file;
		}
		
		/* (non-Javadoc)
		 * @see junit.framework.TestCase#runTest()
		 */
		@Override
		protected void runTest() throws Throwable {
			TestSystemMMPs.this.setUp();
			try {
				macros.clear();
				IASTTranslationUnit tu = parse(file);
				assertTrue(tu.getNodes().size() > 0);
				
				macros.clear();
				macros.add(DefineFactory.createDefine("WINS","WINS"));
				tu = parse(file);
				assertTrue(tu.getNodes().size() > 0);
				IASTProblemNode[] problems = getProblems(tu);
				assertEquals(0, problems.length);
				
				macros.clear();
				macros.add(DefineFactory.createDefine("GCC32", "GCC32"));
				tu = parse(file);
				assertTrue(tu.getNodes().size() > 0);
				problems = getProblems(tu);
				assertEquals(0, problems.length);
	
				File fullPath = new File(projectRelativeFile(TEST_DIR), file);
				String text = new String(FileUtils.readFileContents(fullPath, null));
				IMMPModel model = (IMMPModel) mmpModelProvider.getSharedModel(new Path(fullPath.getAbsolutePath()));
				assertNotNull(model);
				IMMPView view = model.createView(viewConfig);
				assertNotNull(view);
				commitTest((IOwnedModel) model, view, text);
				view.dispose();
				mmpModelProvider.releaseSharedModel(model);
			} finally {
				TestSystemMMPs.this.tearDown();
			}
		}
		
	}
	
	static public TestSuite createSuite() {
		TestSuite suite = new TestSuite("Parsing system MMPs");
		TestSystemMMPs base = new TestSystemMMPs() {
			
		};
		suite.addTest(base.new MMPParseTest("app_to_exe.mmp"));
		suite.addTest(base.new MMPParseTest("edll.mmp"));
		suite.addTest(base.new MMPParseTest("eexe.mmp"));
		suite.addTest(base.new MMPParseTest("eexedll.mmp"));
		suite.addTest(base.new MMPParseTest("emulator.mmp"));
		suite.addTest(base.new MMPParseTest("epoc.mmp"));
		suite.addTest(base.new MMPParseTest("estub.mmp"));
		suite.addTest(base.new MMPParseTest("euser.mmp"));
		suite.addTest(base.new MMPParseTest("variant_euser.mmp"));
		return suite;
	}

	public static Test suite() {
		return createSuite();
	}
}
