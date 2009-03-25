/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.test.srcmapping;

import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.sourcegen.ISourceGenProvider;
import com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorDefineDirective;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIncludeDirective;
import com.nokia.sdt.sourcegen.tests.SourceGenTestHelper;
import com.nokia.sdt.symbian.ISymbianNameGenerator;
import com.nokia.sdt.symbian.dm.DesignerDataModel;

/**
 * Test updating existing RSS with source mapping
 * 
 *
 */
public class SrcMappingTestUpdatingLocRls extends SrcMappingBase {
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.test.srcmapping.SrcMappingBase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        sgProvider.setSourceGenFlags(ISourceGenProvider.FLAG_ONE_WAY_UPDATE);
    }

	/**
	 * Test updating LOC files
	 */
	public void testUpdatingLocFiles() throws Exception {
		// use the default files/names
		rss = generator.getRssFile(null, true);
		tu = rss.getTranslationUnit();
		if (tu == null)
			tu = new TranslationUnit(rss);
		asf = rss;
		sf = asf.getSourceFile();
		
		registerUserDefinedString("STR_2");

		//[[[ create the files
	    
		IRssProjectFileManager fileMan = fileManager;
		IAstLocSourceFile locFile = (IAstLocSourceFile) fileMan.findOrCreateDerivedSourceFile(
				dataModel, rss, ISymbianNameGenerator.RESOURCE_DIRECTORY_ID, null, 
				".loc", AstLocSourceFile.class, null, false);
		IAstLxxSourceFile lxxFileEn = (IAstLxxSourceFile) fileMan.findOrCreateDerivedSourceFile(
				dataModel, locFile, ISymbianNameGenerator.RESOURCE_DIRECTORY_ID, null, 
				".l10", AstLxxSourceFile.class, new Object[] { new Integer(Language.LANG_American) }, false);
		locFile.addLxxSourceFile(lxxFileEn);
		IAstLxxSourceFile lxxFileRu = (IAstLxxSourceFile) fileMan.findOrCreateDerivedSourceFile(
				dataModel, locFile, ISymbianNameGenerator.RESOURCE_DIRECTORY_ID, null, 
				".l16", AstLxxSourceFile.class, new Object[] { new Integer(Language.LANG_Russian) }, false);
		locFile.addLxxSourceFile(lxxFileRu);
		
		StringBuffer buffer = new StringBuffer();
		add(buffer, "\n");
		define(buffer, lxxFileEn, "STR_1", "\"Hello\"", "// greeting\n", null);
		define(buffer, lxxFileEn, "STR_2", "\"Goodbye\"", null, " /* later */\n");
		lxxFileEn.getSourceFile().setText(buffer.toString().toCharArray());
		
		buffer = new StringBuffer();
		add(buffer, lxxFileRu, new AstCharacterSetStatement("UTF8"), "CHARACTER_SET UTF8", null, " // really\n");
		define(buffer, lxxFileRu, "STR_1", "\""+UTF8Strings.RUSSIAN_HELLO+"\"", "// greeting\n", null);
		define(buffer, lxxFileRu, "STR_2", "\""+UTF8Strings.RUSSIAN_GOODBYE+"\"", null, " /* later */\n");
		lxxFileRu.getSourceFile().setText(buffer.toString().toCharArray());
	
		buffer = new StringBuffer();
		add(buffer, rss, new AstPreprocessorLocIncludeNode(locFile.getSourceFile().getFileName(), true, locFile), "#include \"" + locFile.getSourceFile().getFileName() + "\"", null, null);
		add(buffer, "\n\n");
		define(buffer, rss, "MACRO_1", "\"A macro value\"", "/* where is this used? */\n", null);
		define(buffer, rss, "MACRO_2", "\"Other macro\"", null, null);
		rss.getSourceFile().setText(buffer.toString().toCharArray());
	
		/*
		System.out.println("---\n"+rss.getCurrentText(sgProvider.getSourceFormatter())+"---\n");
		System.out.println(locFile.getCurrentText(sgProvider.getSourceFormatter()));
		System.out.println(lxxFileEn.getCurrentText(sgProvider.getSourceFormatter()));
		System.out.println(lxxFileRu.getCurrentText(sgProvider.getSourceFormatter()));
		*/
		// ]]]
	
		tu.rewrite(sgProvider.getSourceFormatter());
	    SourceGenTestHelper.parentingTest(tu.getSourceFile());
	    SourceGenTestHelper.testSourceConsistency(sgProvider.getSourceFormatter(), tu.getSourceFile());
		
	    // regenerate the file
	    generator.setStringHandler(locHandler);
	    generator.generateResources(getInstance("testArrayOfStrings"));
	
	    checkRefFile("ref2/TestUpdatingLocFiles.rss", sf);
	    checkRefFile("ref2/TestUpdatingLocFiles.loc", locFile.getSourceFile());
	    checkRefFile("ref2/TestUpdatingLocFiles.l10", lxxFileEn.getSourceFile());
	    checkRefFile("ref2/TestUpdatingLocFiles.l16", lxxFileRu.getSourceFile());
	    
	    // add a new language
	    IDesignerData dd = ((DesignerDataModel)dataModel).getDesignerData(); 
	    Language l = new Language(Language.LANG_French);
	    ILocalizedStringBundle stringBundle = dd.getStringBundle();
		stringBundle.addLocalizedStringTable(l);
	
	    // change a string
	    stringBundle.setDefaultLanguage(new Language(Language.LANG_American));
	    stringBundle.updateLocalizedStringDefault("STR_1", "Heya");
	    stringBundle.setDefaultLanguage(new Language(Language.LANG_French));
	    stringBundle.updateLocalizedStringDefault("STR_10", "quatorze");
	
	    // regenerate the file
	    generator.setStringHandler(locHandler);
	    generator.generateResources(getInstance("testArrayOfStrings"));
	
	    IAstLxxSourceFile lxxFileFr = locFile.findLxxFile(Language.LANG_French);
	    assertNotNull(lxxFileFr);
	    // only three strings exported from DM
	    assertEquals(3, lxxFileFr.getFileNodes(IAstPreprocessorDefineDirective.class).length);
	    
	    checkRefFile("ref2/TestUpdatingLocFiles_b.rss", sf);
	    checkRefFile("ref2/TestUpdatingLocFiles_b.loc", locFile.getSourceFile());
	    checkRefFile("ref2/TestUpdatingLocFiles_b.l10", lxxFileEn.getSourceFile());
	    checkRefFile("ref2/TestUpdatingLocFiles_b.l16", lxxFileRu.getSourceFile());
	    checkRefFile("ref2/TestUpdatingLocFiles_b.l02", lxxFileFr.getSourceFile());
	
	    ////
	    
	    // add strings
	    stringBundle.setDefaultLanguage(new Language(Language.LANG_Russian));
	    stringBundle.updateLocalizedStringDefault("STR_999", "");
	    
	    // regenerate the file
	    generator.generateResources(getInstance("testArrayOfStrings"));
	
	    checkRefFile("ref2/TestUpdatingLocFiles_c.rss", sf);
	    checkRefFile("ref2/TestUpdatingLocFiles_c.loc", locFile.getSourceFile());
	    checkRefFile("ref2/TestUpdatingLocFiles_c.l10", lxxFileEn.getSourceFile());
	    checkRefFile("ref2/TestUpdatingLocFiles_c.l16", lxxFileRu.getSourceFile());
	    checkRefFile("ref2/TestUpdatingLocFiles_c.l02", lxxFileFr.getSourceFile());
	
	    assertEquals(3, locFile.getLxxSourceFiles().length);
	    assertEquals(5, lxxFileEn.getDefines().length);
	    assertEquals(4, lxxFileFr.getDefines().length);
	    assertEquals(5, lxxFileRu.getDefines().length);
	
	}


	/**
	 * Test updating LOC files which were once parsed
	 */
	public void testUpdatingLocFiles2() throws Exception {
	
		parseRssFrom("user/TestUpdatingLocFiles2.rss", 
				new String[] { "user/TestUpdatingLocFiles2.loc",
				"user/TestUpdatingLocFiles2.l10",
				"user/TestUpdatingLocFiles2.l16"});

		// appears in both langs
		registerUserDefinedString("STR_2");

		generator.setRssFileForTesting(rss);
		
	    SourceGenTestHelper.parentingTest(tu.getSourceFile());
	    SourceGenTestHelper.testSourceConsistency(sgProvider.getSourceFormatter(), tu.getSourceFile());
	
	    IAstPreprocessorIncludeDirective incl = rss.findInclude("TestUpdatingLocFiles2.loc");
	    assertNotNull(incl);
	    assertTrue(incl.getFile() instanceof IAstLocSourceFile);
	    IAstLocSourceFile locFile = (IAstLocSourceFile) incl.getFile();
	    
	    IAstLxxSourceFile[] lxxFiles = locFile.getLxxSourceFiles();
	    assertEquals(2, lxxFiles.length);
	    IAstLxxSourceFile lxxFileEn = locFile.findLxxFile(Language.LANG_American);
	    assertNotNull(lxxFileEn);
	    IAstLxxSourceFile lxxFileRu = locFile.findLxxFile(Language.LANG_Russian);
	    assertNotNull(lxxFileRu);
	    
	    IAstCharacterSetStatement[] csets = (IAstCharacterSetStatement[]) lxxFileRu.getFileNodes(IAstCharacterSetStatement.class);
	    assertEquals(1, csets.length);
	    
	    // regenerate the file
	    generator.setStringHandler(locHandler);
	    generator.generateResources(getInstance("testArrayOfStrings"));
	
	    // ensure the same files are used
	    {
		    IAstPreprocessorIncludeDirective incl2 = rss.findInclude("TestUpdatingLocFiles2.loc");
		    assertNotNull(incl2);
		    assertEquals(incl, incl2);
		    assertTrue(incl2.getFile() instanceof IAstLocSourceFile);
		    IAstLocSourceFile locFile2 = (IAstLocSourceFile) incl2.getFile();
		    assertEquals(locFile, locFile2);
		    
		    IAstLxxSourceFile[] lxxFiles2 = locFile2.getLxxSourceFiles();
		    assertEquals(2, lxxFiles2.length);
		    IAstLxxSourceFile lxxFileEn2 = locFile2.findLxxFile(Language.LANG_American);
		    assertNotNull(lxxFileEn2);
		    IAstLxxSourceFile lxxFileRu2 = locFile2.findLxxFile(Language.LANG_Russian);
		    assertNotNull(lxxFileRu2);
		    assertEquals(lxxFileEn, lxxFileEn2);
		    assertEquals(lxxFileRu, lxxFileRu2);
	    	
	    }
	    checkRefFile("ref2/TestUpdatingLocFiles2.rss", sf);
	    checkRefFile("ref2/TestUpdatingLocFiles2.loc", locFile.getSourceFile());
	    checkRefFile("ref2/TestUpdatingLocFiles2.l10", lxxFileEn.getSourceFile());
	    checkRefFile("ref2/TestUpdatingLocFiles2.l16", lxxFileRu.getSourceFile());
	    
	    // add a new language
	    IDesignerData dd = ((DesignerDataModel)dataModel).getDesignerData(); 
	    Language l = new Language(Language.LANG_French);
	    ILocalizedStringBundle stringBundle = dd.getStringBundle();
		stringBundle.addLocalizedStringTable(l);
	
	    // change a string
	    stringBundle.setDefaultLanguage(new Language(Language.LANG_American));
	    stringBundle.updateLocalizedStringDefault("STR_1", "Heya");
	    stringBundle.setDefaultLanguage(new Language(Language.LANG_French));
	    stringBundle.updateLocalizedStringDefault("STR_10", "quatorze");
	
	    // regenerate the file
	    generator.setStringHandler(locHandler);
	    generator.generateResources(getInstance("testArrayOfStrings"));
	
	    IAstLxxSourceFile lxxFileFr = locFile.findLxxFile(Language.LANG_French);
	    assertNotNull(lxxFileFr);
	    // only three strings exported from DM
	    assertEquals(3, lxxFileFr.getFileNodes(IAstPreprocessorDefineDirective.class).length);
	    
	    checkRefFile("ref2/TestUpdatingLocFiles2_b.rss", sf);
	    checkRefFile("ref2/TestUpdatingLocFiles2_b.loc", locFile.getSourceFile());
	    checkRefFile("ref2/TestUpdatingLocFiles2_b.l10", lxxFileEn.getSourceFile());
	    checkRefFile("ref2/TestUpdatingLocFiles2_b.l16", lxxFileRu.getSourceFile());
	    checkRefFile("ref2/TestUpdatingLocFiles2_b.l02", lxxFileFr.getSourceFile());
	
	    ////
	    
	    // add strings
	    stringBundle.setDefaultLanguage(new Language(Language.LANG_Russian));
	    stringBundle.updateLocalizedStringDefault("STR_999", "");
	    
	    // regenerate the file
	    generator.generateResources(getInstance("testArrayOfStrings"));
	
	    checkRefFile("ref2/TestUpdatingLocFiles2_c.rss", sf);
	    checkRefFile("ref2/TestUpdatingLocFiles2_c.loc", locFile.getSourceFile());
	    checkRefFile("ref2/TestUpdatingLocFiles2_c.l10", lxxFileEn.getSourceFile());
	    checkRefFile("ref2/TestUpdatingLocFiles2_c.l16", lxxFileRu.getSourceFile());
	    checkRefFile("ref2/TestUpdatingLocFiles2_c.l02", lxxFileFr.getSourceFile());
	
	    assertEquals(3, locFile.getLxxSourceFiles().length);
	    assertEquals(5, lxxFileEn.getDefines().length);
	    assertEquals(4, lxxFileFr.getDefines().length);
	    assertEquals(5, lxxFileRu.getDefines().length);
	
	    // delete languages
	    ILocalizedStringTable table = stringBundle.findLocalizedStringTable(
	    		new Language(Language.LANG_French));
	    stringBundle.getLocalizedStringTables().remove(table);
	    
	    // regenerate the file
	    generator.generateResources(getInstance("testArrayOfStrings"));
	
	    checkRefFile("ref2/TestUpdatingLocFiles2_d.loc", locFile.getSourceFile());
	    assertEquals(2, locFile.getLxxSourceFiles().length);
	    
	}

	/**
	 * Test updating RLS files
	 */
	public void testUpdatingRlsFiles() throws Exception {
	
		// use the default files/names
		rss = generator.getRssFile(null, true);
		tu = rss.getTranslationUnit();
		if (tu == null)
			tu = new TranslationUnit(rss);
		asf = rss;
		sf = asf.getSourceFile();

		registerUserDefinedString("STR_2");

		//[[[ create the files
	
	    
		IRssProjectFileManager fileMan = fileManager;
		IAstRlsSourceFile rlsFileEn = (IAstRlsSourceFile) fileMan.findOrCreateDerivedSourceFile(
				dataModel, rss, ISymbianNameGenerator.RESOURCE_DIRECTORY_ID, "_10", 
				".rls", AstRlsSourceFile.class, new Object[] { new Integer(Language.LANG_American) }, false);
		IAstRlsSourceFile rlsFileRu = (IAstRlsSourceFile) fileMan.findOrCreateDerivedSourceFile(
				dataModel, rss, ISymbianNameGenerator.RESOURCE_DIRECTORY_ID, "_16", 
				".rls", AstRlsSourceFile.class, new Object[] { new Integer(Language.LANG_Russian) }, false);
		
		StringBuffer buffer = new StringBuffer();
		add(buffer, "\n");
		addRlsString(buffer, rlsFileEn, "STR_1", "Hello", "// greeting\n", null);
		addRlsString(buffer, rlsFileEn, "STR_2", "Goodbye", null, " /* later */\n");
		rlsFileEn.getSourceFile().setText(buffer.toString().toCharArray());
		
		
		buffer = new StringBuffer();
		add(buffer, rlsFileRu, new AstCharacterSetStatement("UTF8"), "CHARACTER_SET UTF8", null, " // really\n");
		addRlsString(buffer, rlsFileRu, "STR_1", UTF8Strings.RUSSIAN_HELLO, "// greeting\n", null);
		addRlsString(buffer, rlsFileRu, "STR_10", "russian fourteen", null, " /* later */\n");
		rlsFileRu.getSourceFile().setText(buffer.toString().toCharArray());
	
		buffer = new StringBuffer();
		addRlsInclude(buffer, rss, rlsFileEn);
		addRlsInclude(buffer, rss, rlsFileRu);
		add(buffer, "\n\n");
		define(buffer, rss, "MACRO_1", "\"A macro value\"", "/* where is this used? */\n", null);
		define(buffer, rss, "MACRO_2", "\"Other macro\"", null, null);
		rss.getSourceFile().setText(buffer.toString().toCharArray());
	
		// ]]]
	
		tu.rewrite(sgProvider.getSourceFormatter());
	    SourceGenTestHelper.parentingTest(tu.getSourceFile());
	    SourceGenTestHelper.testSourceConsistency(sgProvider.getSourceFormatter(), tu.getSourceFile());
		
	    // regenerate the file
	    generator.setStringHandler(rlsHandler);
	    generator.generateResources(getInstance("testArrayOfStrings"));
	
	    checkRefFile("ref2/TestUpdatingRlsFiles.rss", sf);
	    checkRefFile("ref2/TestUpdatingRlsFiles_10.rls", rlsFileEn.getSourceFile());
	    checkRefFile("ref2/TestUpdatingRlsFiles_16.rls", rlsFileRu.getSourceFile());
	    
	    // add a new language
	    IDesignerData dd = ((DesignerDataModel)dataModel).getDesignerData(); 
	    Language l = new Language(Language.LANG_French);
	    ILocalizedStringBundle stringBundle = dd.getStringBundle();
		stringBundle.addLocalizedStringTable(l);
	    
	    // change a string
	    stringBundle.setDefaultLanguage(new Language(Language.LANG_American));
	    stringBundle.updateLocalizedStringDefault("STR_1", "Heya");
	    stringBundle.setDefaultLanguage(new Language(Language.LANG_French));
	    stringBundle.updateLocalizedStringDefault("STR_10", "quatorze");
	    
	    // regenerate the file
	    generator.setStringHandler(rlsHandler);
	    generator.generateResources(getInstance("testArrayOfStrings"));
	
	    IAstRlsSourceFile rlsFileFr = rss.findRlsFile(Language.LANG_French);
	    assertNotNull(rlsFileFr);
	    // only three strings exported from DM
	    assertEquals(3, rlsFileFr.getRlsStrings().length);
	    
	    checkRefFile("ref2/TestUpdatingRlsFiles_b.rss", sf);
	    checkRefFile("ref2/TestUpdatingRlsFiles_b_10.rls", rlsFileEn.getSourceFile());
	    checkRefFile("ref2/TestUpdatingRlsFiles_b_16.rls", rlsFileRu.getSourceFile());
	    checkRefFile("ref2/TestUpdatingRlsFiles_b_02.rls", rlsFileFr.getSourceFile());
	
	    // add strings
	    stringBundle.setDefaultLanguage(new Language(Language.LANG_Russian));
	    stringBundle.updateLocalizedStringDefault("STR_999", "");
	    
	    // regenerate the file
	    generator.setStringHandler(rlsHandler);
	    generator.generateResources(getInstance("testArrayOfStrings"));
	
	    checkRefFile("ref2/TestUpdatingRlsFiles_c.rss", sf);
	    checkRefFile("ref2/TestUpdatingRlsFiles_c_10.rls", rlsFileEn.getSourceFile());
	    checkRefFile("ref2/TestUpdatingRlsFiles_c_16.rls", rlsFileRu.getSourceFile());
	    checkRefFile("ref2/TestUpdatingRlsFiles_c_02.rls", rlsFileFr.getSourceFile());
	
	    assertEquals(5, rlsFileEn.getRlsStrings().length);
	    assertEquals(4, rlsFileFr.getRlsStrings().length);
	    assertEquals(4, rlsFileRu.getRlsStrings().length);
	
	}

	/**
	 * Test updating RLS files which were once parsed
	 */
	public void testUpdatingRlsFiles2() throws Exception {
	
		parseRssFrom("user/TestUpdatingRlsFiles2.rss", 
				new String[] { "user/TestUpdatingRlsFiles2_10.rls",
				"user/TestUpdatingRlsFiles2_16.rls"});

		// appears for only one lang
		registerUserDefinedString("STR_2");

		generator.setRssFileForTesting(rss);
		
	    SourceGenTestHelper.parentingTest(tu.getSourceFile());
	    SourceGenTestHelper.testSourceConsistency(sgProvider.getSourceFormatter(), tu.getSourceFile());

	    IAstRlsSourceFile[] rlsFiles = rss.getRlsSourceFiles();
	    assertEquals(2, rlsFiles.length);
	    IAstRlsSourceFile rlsFileEn = rss.findRlsFile(Language.LANG_American);
	    assertNotNull(rlsFileEn);
	    IAstRlsSourceFile rlsFileRu = rss.findRlsFile(Language.LANG_Russian);
	    assertNotNull(rlsFileRu);
	    assertNotSame(rlsFileEn, rlsFileRu);
	    
	    IAstCharacterSetStatement[] csets = (IAstCharacterSetStatement[]) rlsFileRu.getFileNodes(IAstCharacterSetStatement.class);
	    assertEquals(1, csets.length);
	    
	    // regenerate the file
	    generator.setStringHandler(rlsHandler);
	    generator.generateResources(getInstance("testArrayOfStrings"));
	
	    // ensure the same files are used
	    {
		    IAstRlsSourceFile[] rlsFiles2 = rss.getRlsSourceFiles();
		    assertEquals(2, rlsFiles2.length);
		    IAstRlsSourceFile rlsFileEn2 = rss.findRlsFile(Language.LANG_American);
		    assertNotNull(rlsFileEn2);
		    IAstRlsSourceFile rlsFileRu2 = rss.findRlsFile(Language.LANG_Russian);
		    assertNotNull(rlsFileRu2);
		    assertEquals(rlsFileEn, rlsFileEn2);
		    assertEquals(rlsFileRu, rlsFileRu2);
	    	
	    }
	    checkRefFile("ref2/TestUpdatingRlsFiles2.rss", sf);
	    checkRefFile("ref2/TestUpdatingRlsFiles2_10.rls", rlsFileEn.getSourceFile());
	    checkRefFile("ref2/TestUpdatingRlsFiles2_16.rls", rlsFileRu.getSourceFile());
	    
	    // add a new language
	    IDesignerData dd = ((DesignerDataModel)dataModel).getDesignerData(); 
	    Language l = new Language(Language.LANG_French);
	    ILocalizedStringBundle stringBundle = dd.getStringBundle();
		stringBundle.addLocalizedStringTable(l);
	
	    // change a string
	    stringBundle.setDefaultLanguage(new Language(Language.LANG_American));
	    stringBundle.updateLocalizedStringDefault("STR_1", "Heya");
	    stringBundle.setDefaultLanguage(new Language(Language.LANG_French));
	    stringBundle.updateLocalizedStringDefault("STR_10", "quatorze");
	
	    // regenerate the file
	    generator.setStringHandler(rlsHandler);
	    generator.generateResources(getInstance("testArrayOfStrings"));
	
	    IAstRlsSourceFile rlsFileFr = rss.findRlsFile(Language.LANG_French);
	    assertNotNull(rlsFileFr);
	    // only three strings exported from DM
	    assertEquals(3, rlsFileFr.getRlsStrings().length);
	    
	    checkRefFile("ref2/TestUpdatingRlsFiles2_b.rss", sf);
	    checkRefFile("ref2/TestUpdatingRlsFiles2_b_10.rls", rlsFileEn.getSourceFile());
	    checkRefFile("ref2/TestUpdatingRlsFiles2_b_16.rls", rlsFileRu.getSourceFile());
	    checkRefFile("ref2/TestUpdatingRlsFiles2_b_02.rls", rlsFileFr.getSourceFile());
	
	    ////
	    
	    // add strings
	    stringBundle.setDefaultLanguage(new Language(Language.LANG_Russian));
	    stringBundle.updateLocalizedStringDefault("STR_999", "");
	    
	    // regenerate the file
	    generator.generateResources(getInstance("testArrayOfStrings"));
	
	    checkRefFile("ref2/TestUpdatingRlsFiles2_c.rss", sf);
	    checkRefFile("ref2/TestUpdatingRlsFiles2_c_10.rls", rlsFileEn.getSourceFile());
	    checkRefFile("ref2/TestUpdatingRlsFiles2_c_16.rls", rlsFileRu.getSourceFile());
	    checkRefFile("ref2/TestUpdatingRlsFiles2_c_02.rls", rlsFileFr.getSourceFile());
	
	    assertEquals(3, rss.getRlsSourceFiles().length);
	    assertEquals(5, rlsFileEn.getRlsStrings().length);
	    assertEquals(4, rlsFileFr.getRlsStrings().length);
	    assertEquals(4, rlsFileRu.getRlsStrings().length);

	    // delete languages
	    ILocalizedStringTable table = stringBundle.findLocalizedStringTable(
	    		new Language(Language.LANG_French));
	    stringBundle.getLocalizedStringTables().remove(table);
	    
	    // regenerate the file
	    generator.generateResources(getInstance("testArrayOfStrings"));
	
	    checkRefFile("ref2/TestUpdatingLocFiles2_d.rss", sf);
	    assertEquals(2, rss.getRlsSourceFiles().length);

	}
}
