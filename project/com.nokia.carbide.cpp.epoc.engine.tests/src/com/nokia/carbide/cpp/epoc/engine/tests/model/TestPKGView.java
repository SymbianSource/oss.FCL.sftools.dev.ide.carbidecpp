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

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.model.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.*;
import com.nokia.carbide.internal.cpp.epoc.engine.model.pkg.PKGView;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.PathUtils;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.text.IDocument;

import java.util.*;

public class TestPKGView extends BaseViewTests {
	private static final String LANGUAGES = 
		";Languages\n&EN,FR\n\n";
	private static final String PKGHEADER = 
		";Package Header\n#{\"Foo-EN\", \"Foo-FR\"}, (0x1000001F), 1, 2, 3\n\n";
	
	private static final String LANG_DEST_FILE = "C:\\private\\10000005\\import\\InstTest\\lang.txt";
	private static final String FRENCH_SRC_FILE = "foo1\\frenchfile.txt";
	private static final String ENG_SRC_FILE = "foo2\\englishfile.txt";
	private static final String LANG_INSTALL_FILE_STATEMENT = 
		"{\"" + ENG_SRC_FILE + "\", \"" + FRENCH_SRC_FILE + "\"}-\"" + LANG_DEST_FILE + "\"";
	private static final String FIXED_DRIVE_INSTALL_FILE = 
		";Ordinary file to fixed drive\n" + LANG_INSTALL_FILE_STATEMENT + "\n\n";
	private static final String NO_LANG_SRC_FILE = "foo3\\1.png";
	private static final String NO_LANG_DEST_FILE = "!:\\private\\10000005\\import\\InstTest\\1.png";
	private static final String NO_LANG_OPTION = "FF";
	private static final String NO_LANG_INSTALL_FILE_STATEMENT = 
		"\"" + NO_LANG_SRC_FILE + "\"-\"" + NO_LANG_DEST_FILE + "\", " + NO_LANG_OPTION;
	private static final String SELECTED_DRIVE_INSTALL_FILE = 
		";Ordinary file to selected drive\n" + NO_LANG_INSTALL_FILE_STATEMENT + "\n\n";
	private static final String[][] LANG_TO_SRC_FILES = {
		new String[] { "EN", ENG_SRC_FILE }, 
		new String[] { "FR", FRENCH_SRC_FILE }
	};
	private static final String CONDITIONAL_SRC_FILE = "foo4\\pass.txt";
	private static final String CONDITIONAL_OPTION1 = "FT";
	private static final String CONDITIONAL_OPTION2 = "TC";
	private static final String CONDITIONAL_BLOCK =
		";Conditional block\n" +
		"IF MemoryRAM>0\n\"" + CONDITIONAL_SRC_FILE + "\"-\"\", " + 
		CONDITIONAL_OPTION1 + ", " + CONDITIONAL_OPTION2 + "\nENDIF\n\n";
	
	private static final String ESCAPABLE_FILES = 
		"&EN,FR\n" +
		"{\"c:\\users\\E0192828\\back.txt\", \"c:\\users\\E0FG\\front.txt\"}-\"sp ac es.txt\"\r\n" +
		"\"c:\\users\\E0192828\\back.txt\" - \"sp ac es.txt\"";
	
	private static final Map<String, String> lang2SrcFileMap = new HashMap<String, String>();
	static {
		for (String[] lang2SrcFileSpec : LANG_TO_SRC_FILES) {
			lang2SrcFileMap.put(lang2SrcFileSpec[0], lang2SrcFileSpec[1]);
		}
	};
	
	protected IPath path;
	protected IPKGOwnedModel model;
	protected IViewConfiguration config;

	public TestPKGView() {
		super();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.path = new Path(getTokenAbsolutePath()).append("/symbian/9.4/epoc32/tools/Test.PKG");
	
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
		model = new PKGModelFactory().createModel(path, document);
		model.parse();
	}
	
	protected void commitTest(IPKGView view, String modelText) {
		ILogListener logListener = new ILogListener() {
			public void logging(IStatus status, String plugin) {
				fail(status.getMessage());
			}
		};
		Logging.addListener(logListener);
		try {
			commitTest(model, view, modelText);
		} finally {
			Logging.removeListener(logListener);
		}
	}

	protected IPKGView getView(IViewConfiguration config) {
		IPKGView view = (IPKGView) model.createView(config);
		assertNotNull(view);
		return view;
	}

	public void testReadTopLevelInstallFiles() throws Exception {
		String modelText = LANGUAGES + PKGHEADER + FIXED_DRIVE_INSTALL_FILE + SELECTED_DRIVE_INSTALL_FILE;
		makeModel(modelText);
		IPKGView view = getView(config);
		List<IPKGInstallFile> installFileList = ((PKGView) view).getInstallFileList();
		assertNotNull(installFileList);
		assertEquals(2, installFileList.size());
		
		// first install file is language dependent
		IPKGInstallFile installFile = installFileList.get(0);
		assertEquals(PathUtils.createPath(LANG_DEST_FILE), installFile.getDestintationFile());
		List<EPKGLanguage> langs = view.getLanguages();
		assertEquals(2, langs.size());
		Map<EPKGLanguage, IPath> sourceFiles = installFile.getSourceFiles();
		for (EPKGLanguage lang : langs) {
			IPath srcFilePath = sourceFiles.get(lang);
			String srcFile = lang2SrcFileMap.get(lang.getCode());
			assertEquals(PathUtils.createPath(srcFile), srcFilePath);
		}
		
		// second install file is language independent
		installFile = installFileList.get(1);
		assertEquals(PathUtils.createPath(NO_LANG_DEST_FILE), installFile.getDestintationFile());
		sourceFiles = installFile.getSourceFiles();
		assertEquals(1, sourceFiles.size());
		assertTrue(sourceFiles.containsKey(EPKGLanguage.INDEPENDENT));
		IPath srcFilePath = sourceFiles.get(EPKGLanguage.INDEPENDENT);
		assertEquals(PathUtils.createPath(NO_LANG_SRC_FILE), srcFilePath);
		assertEquals(1, installFile.getOptions().size());
		assertEquals(NO_LANG_OPTION, installFile.getOptions().get(0));
		
		commitTest(view, modelText);
	}
	
	public void testReadAllInstallFiles() throws Exception {
		String modelText = LANGUAGES + PKGHEADER + FIXED_DRIVE_INSTALL_FILE + CONDITIONAL_BLOCK + SELECTED_DRIVE_INSTALL_FILE;
		makeModel(modelText);
		IPKGView view = getView(config);
		List<IPKGInstallFile> installFileList = ((PKGView) view).getInstallFileList();
		assertNotNull(installFileList);
		assertEquals(2, installFileList.size());
		IPKGInstallFile[] allInstallFiles = view.getAllInstallFiles();
		assertNotNull(allInstallFiles);
		assertEquals(3, allInstallFiles.length);
		
		// find conditional install file
		IPKGInstallFile conditionalInstallFile = null;
		for (IPKGInstallFile installFile : allInstallFiles) {
			if (installFile.getContainer() != null) {
				conditionalInstallFile = installFile;
				break;
			}
		}
		assertNotNull(conditionalInstallFile);
		IPath destintationFile = conditionalInstallFile.getDestintationFile();
		assertTrue(destintationFile.isEmpty());
		assertEquals(1, conditionalInstallFile.getSourceFiles().size());
		assertEquals(PathUtils.createPath(CONDITIONAL_SRC_FILE), conditionalInstallFile.getSourceFiles().get(EPKGLanguage.INDEPENDENT));
		assertEquals(2, conditionalInstallFile.getOptions().size());
		
		commitTest(view, modelText);
	}
	
	public void testAddInstallFiles() throws Exception {
		String modelText = LANGUAGES + PKGHEADER + CONDITIONAL_BLOCK;
		makeModel(modelText);
		IPKGView view = getView(config);
		
		// fully create and add a language independent install file statement
		IPKGInstallFile installFile1 = view.createInstallFile(null);
		installFile1.getSourceFiles().put(EPKGLanguage.INDEPENDENT, PathUtils.createPath(NO_LANG_SRC_FILE));
		installFile1.getOptions().add(NO_LANG_OPTION);
		installFile1.setDestinationFile(PathUtils.createPath(NO_LANG_DEST_FILE));
		view.addInstallFile(installFile1);
		
		// create a language dependent install file and modify after add
		IPKGInstallFile installFile2 = view.createInstallFile(null);
		view.addInstallFile(installFile2);
		for (String[] lang2SrcFileArray : LANG_TO_SRC_FILES) {
			installFile2.getSourceFiles().put(
					EPKGLanguage.forLangCode(lang2SrcFileArray[0]), PathUtils.createPath(lang2SrcFileArray[1]));
		}
		installFile2.setDestinationFile(PathUtils.createPath(LANG_DEST_FILE));
		
		// NOTE: the newlines are lost and shouldn't be
		//String modifiedModelText = modelText + NO_LANG_INSTALL_FILE_STATEMENT + "\n" + LANG_INSTALL_FILE_STATEMENT;
		String modifiedModelText =
			";Languages\n" + 
			"&EN,FR\n" + 
			";Package Header\n" + 
			"#{\"Foo-EN\", \"Foo-FR\"}, (0x1000001F), 1, 2, 3\n" + 
			";Conditional block\n" + 
			"IF MemoryRAM>0\n" + 
			"\"foo4\\pass.txt\"-\"\", FT, TC\n" + 
			"ENDIF\n" + 
			"\"foo3\\1.png\"-\"!:\\private\\10000005\\import\\InstTest\\1.png\", FF\n" + 
			"{\"foo2\\englishfile.txt\", \"foo1\\frenchfile.txt\"}-\"C:\\private\\10000005\\import\\InstTest\\lang.txt\"";
		commitTest(view, modifiedModelText);
		
		view = getView(config);
		List<IPKGInstallFile> installFileList = ((PKGView) view).getInstallFileList();
		assertEquals(installFile1, installFileList.get(0));
		assertEquals(installFile2, installFileList.get(1));
		assertEquals(2, installFileList.size());
	}
	
	public void testQuotedStrings() throws Exception {
		makeModel(ESCAPABLE_FILES);
		IPKGView view = getView(config);

		IPKGInstallFile[] installFiles = view.getAllInstallFiles();
		assertEquals(2, installFiles.length);
		
		IPKGInstallFile file = installFiles[0];
		assertEquals(2, file.getSourceFiles().size());
		assertEquals(PathUtils.createPath("c:/users/E0192828/back.txt"), file.getSourceFiles().get(EPKGLanguage.EN));
		assertEquals(PathUtils.createPath("c:/users/E0FG/front.txt"), file.getSourceFiles().get(EPKGLanguage.FR));
		assertEquals(PathUtils.createPath("sp ac es.txt"), file.getDestintationFile());
		
		file = installFiles[1];
		assertEquals(1, file.getSourceFiles().size());
		assertEquals(PathUtils.createPath("c:/users/E0192828/back.txt"), file.getSourceFiles().get(EPKGLanguage.INDEPENDENT));
		assertEquals(PathUtils.createPath("sp ac es.txt"), file.getDestintationFile());
		
		commitTest(view, ESCAPABLE_FILES);
	}
	
}
