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
* This test essentially parses a lot of times, for use with
* a profiler.
*
*
*/
package com.nokia.carbide.cpp.epoc.engine.tests.parser;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ITranslationUnitParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.mmp.IMMPParserConfiguration;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ASTPreprocessor;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
public class TestParsingSpeed extends BaseTest {
	static final String TEST_DIR = "C:\\Symbian\\9.2\\200644\\src\\cedar\\generic\\base\\e32test";

	private IMMPParserConfiguration mmpConfig;
	private AcceptedNodesViewFilter viewFilter;
	private ArrayList<IDefine> macros;

	private String masterMMPText;

	private int fileCount;

	protected void setUp() throws Exception {
		super.setUp();

		mmpConfig = new IMMPParserConfiguration() {

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
		
		viewFilter = new AcceptedNodesViewFilter();
		macros = new ArrayList<IDefine>();
		
		File[] files = new File[0];
		
		File sdkDir = new File(TEST_DIR);
		if (sdkDir.exists()) {
			files = FileUtils.listFilesInTree(sdkDir,
					new FileFilter() {
	
				public boolean accept(File pathname) {
					return pathname.isDirectory()
					|| pathname.getName().toLowerCase().endsWith(".mmp"); 
				}
				
			}, false);
		}
		
		StringBuilder masterMMP = new StringBuilder();
		fileCount = 0;
		for (File file : files) {
			parserConfig.getFilesystem().put(file.getName(), load(file));
			masterMMP.append("#include \"");
			masterMMP.append(file.getName());
			masterMMP.append("\"\n");
			fileCount++;
		}
		
		masterMMPText = masterMMP.toString();
		parserConfig.getFilesystem().put("master.mmp", masterMMPText);
	}

	// load file
	protected String load(File file) throws Exception {
		char[] text = FileUtils.readFileContents(file, null);
		
		return new String(text);
	}

	static final int ITERS = 10;
	
	public void testSpeed() throws Exception {
		// cache stuff
		runSpeed();
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < ITERS; i++) {
			runSpeed();
		}
		long elapsed = System.currentTimeMillis() - start;
		if (fileCount > 0) {
			System.out.println("Elapsed time: " + elapsed + " = " 
					+ (elapsed / ITERS) + " ms per parse or ~" 
					+ (elapsed / ITERS / fileCount) + " ms per file");
		}
	}
	
	protected IASTTranslationUnit parse(String filename) throws Exception {
		IASTTranslationUnit ppTu = (IASTTranslationUnit) parserConfig.getTranslationUnitProvider().getTranslationUnit(
				new File(filename), parserConfig.getModelDocumentProvider());
		
		// preprocess
		ASTPreprocessor preprocessor = createPreprocessor(parserConfig);
		IPreprocessorResults results = preprocessor.preprocess(ppTu, viewFilter, macros);

		ITranslationUnitParser parser = ParserFactory.createMMPParser(mmpConfig);
		IASTTranslationUnit mmpTu = parser.parse(results);
		return mmpTu;
	}
	

	private void runSpeed() throws Exception {
		parse("master.mmp");
	}
}
