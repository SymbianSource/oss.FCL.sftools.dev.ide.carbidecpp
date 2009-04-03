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

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.emf.dm.Language;
import com.nokia.sdt.sourcegen.IComponentSourceMapping;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstRssSourceFile;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.*;
import com.nokia.sdt.testsupport.AdapterHelpers;
import com.nokia.sdt.testsupport.FileHelpers;

import org.eclipse.ui.views.properties.IPropertySource;

import java.util.List;

/**
 * 
 *
 */
public class SrcMappingTestBasic extends SrcMappingBase {

    
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testBasic0() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcmapBasic");
        assertNotNull(base);
        IComponentSourceMapping sourcemapping = getSourceMapping(base);
        assertNotNull(sourcemapping);
    }

    public void testBasic1() throws Exception {
    	exportInstance("com.nokia.examples.srcmapBasic", "test0"); 
        checkNoMessages();
        
    }

    public void testBasic2() throws Exception {
    	exportInstance("com.nokia.examples.srcmapBasic", "test0"); 
        checkNoMessages();

        // make sure the required header was included
        IAstSourceFile incl = tu.findIncludedFile(FileHelpers.projectRelativeFile("data/srcmapping/SDK/basicheader.rh").getAbsolutePath());
        assertNotNull(incl);
        assertEquals("basicheader.rh", incl.getSourceFile().getFileName());
        
        String name = getGeneratedResourceName(getInstance("test0"));
        assertNotNull(name);
        
        // and make sure it really had an impact on the TU
        IAstStructDeclaration decl = tu.findStructDeclaration("ONE_STRING");
        assertNotNull(decl);
        assertNotNull(tu.findResourceDefinition(name));
    }

    public void testBasic3() throws Exception {
    	exportInstance("com.nokia.examples.srcmapBasic", "test0");
        checkNoMessages();
        
        // now check that only the main RSS file was changed
        List files = rewriteTu(tu);
        
        assertEquals(1, files.size());
        assertEquals(asf, files.get(0));
    }

    public void testBasic4() throws Exception {
    	exportInstance("com.nokia.examples.srcmapBasic", "test0");
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestBasic4.rss", sf);
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        
        // the value should reference the string
        checkMemberInit(def, "text", IAstLiteralExpression.K_STRING, "\"check this textual data\"");
    }

    /**
     * Check names: these are a conversion of camel-case into underscores.
     * We want to avoid superfluous underscores.
     * 
     * @throws Exception
     */
    public void testBasic5() throws Exception {
        IAstResourceDefinition def;
        
        exportInstance("com.nokia.examples.srcmapBasic", "test0");
        def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        assertEquals("r_test0", def.getName().getName());

        // ensure we use the same one twice
        exportInstance("com.nokia.examples.srcmapBasic", "test0");
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(1, defs.length);
        def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        assertEquals("r_test0", def.getName().getName());
        asf.removeAllContents();
        tu.refresh();

        exportInstance("com.nokia.examples.srcmapBasic", "Test0");
        def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        // conflict, so renamed
        assertEquals("r_test0_2", def.getName().getName());
        asf.removeAllContents();
        tu.refresh();
        
        exportInstance("com.nokia.examples.srcmapBasic", "MyComponentInstance");
        def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        assertEquals("r_my_component_instance", def.getName().getName());
        asf.removeAllContents();
        tu.refresh();
        
        exportInstance("com.nokia.examples.srcmapBasic", "What.the.Hell?");
        def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        assertEquals("r_what_the_hell_", def.getName().getName());
        asf.removeAllContents();
        tu.refresh();
        
    }
    
    /** 
     * Verify inheritance
     * @throws Exception
     */
    public void testBasic6() throws Exception {
    	exportInstance("com.nokia.examples.srcmapDerived", "test0_d");
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestBasic6.rss", sf);
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        
        // the value should reference the string
        checkMemberInit(def, "text", IAstLiteralExpression.K_STRING, "\"check this textual data\"");
    }

    // two instances using same header
    public void testTwoOfHeader() throws Exception {
        // export one
    	exportInstance("com.nokia.examples.srcmapBasic", "test0");
        checkNoMessages();

        // export another
    	exportInstance("com.nokia.examples.srcmapSimple", "test1");
        checkNoMessages();

        //
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestTwoOfHeader.rss", sf);
    
        // now check that both struct definitions were written
        IAstResourceDefinition def;
        def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        def = tu.findResourceDefinitionOfType("SIMPLE_TYPES");
        assertNotNull(def);

        assertNotNull(getGeneratedResourceName(getInstance("test0")));
        assertNotNull(getGeneratedResourceName(getInstance("test1")));
        
        // check that only one basicheader.rh is included
        IAstSourceFile[] includedFiles = tu.getIncludedFiles();
        assertEquals(1, includedFiles.length);
    }
    
    // check exporting same instance twice
    public void testEmitTwice() throws Exception {
        // export one
        IComponentInstance instance = getInstance("test0");
        exportInstance(instance);
        checkNoMessages();

        rewriteTu(tu);

        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(1, defs.length);
        checkMemberInit(defs[0], "text", IAstLiteralExpression.K_STRING, "\"check this textual data\"");

        //////////////////
        
        // change a value
        IPropertySource ps = AdapterHelpers.getPropertySource(instance);
        assertNotNull(ps);
        ps.setPropertyValue("textdata", "new text");
        
        // export again
        //context.resetInstanceToResourceMap();
        manipulator.getResourceTracker().resetComplete();
        exportInstance(instance);
        checkNoMessages();


        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestEmitTwice.rss", sf);

        // now check that same def overwritten
        IAstResourceDefinition[] defs2 = tu.getResourceDefinitions();
        assertEquals(1, defs.length);
        assertTrue(defs[0] == defs2[0]);
        
        // and that the value is updated
        checkMemberInit(defs2[0], "text", IAstLiteralExpression.K_STRING, "\"new text\"");
        
    }

    public void testUnwritable() throws Exception {
        // check that rewriting a resource in a read-only
        // file makes a unique name
        
        exportInstance("com.nokia.examples.srcmapBasic", "test0");
        checkNoMessages();
        rewriteTu(tu);
        
        // get the generated resource
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        
        // now, move the resource to the locals 
        // (i.e. an RSS file without a source file, hence unwritable)
        tu.setLocalSourceFile(new AstRssSourceFile());
        rss.removeFileNode(def);
        ((IAstRssSourceFile) tu.getLocalSourceFile()).appendFileNode(def);

        rewriteTu(tu);
        //System.out.println("1)\n"+new String(sf.getText()));

        // re-export the instance

        manipulator.getResourceTracker().reset();
        exportInstance("com.nokia.examples.srcmapBasic", "test0");
        checkNoMessages();
        rewriteTu(tu);
        //System.out.println("2)\n"+new String(sf.getText()));
        checkRefFile("ref/TestUnwritable.rss", sf);

        // ensure we have a new resource in the main source file
        // with the expected "modified" name
        IAstTopLevelNode tops[] = ((IAstRssSourceFile)tu.getSourceFile()).getTopLevelNodes();
        assertEquals(1, tops.length);
        
        IAstResourceDefinition def2 = tu.findResourceDefinition(
                def.getName().getName() + "_2");
        assertNotNull(def2);
    }

    public void testUnwritable2() throws Exception {
        // check that rewriting a resource in a read-only file
        // makes a unique name and doesn't overwrite target resource
        
        exportInstance("com.nokia.examples.srcmapBasic", "test0");
        checkNoMessages();
        rewriteTu(tu);
        
        // get the generated resource
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);
        
        // now, move the resource to the locals 
        // (i.e. an RSS file without a source file, hence unwritable)
        tu.setLocalSourceFile(new AstRssSourceFile());
        //tu.getLocalSourceFile().moveContents(tu.getSourceFile());
        rss.removeFileNode(def);
        ((IAstRssSourceFile) tu.getLocalSourceFile()).appendFileNode(def);


        rewriteTu(tu);
        //System.out.println("1)\n"+new String(sf.getText()));

        // re-export the instance
        manipulator.getResourceTracker().reset();
        exportInstance("com.nokia.examples.srcmapBasic", "test0");
        checkNoMessages();
        rewriteTu(tu);
        //System.out.println("2)\n"+new String(sf.getText()));
        
        // ensure we have a new resource in the main source file
        // with the expected "modified" name
        IAstTopLevelNode tops[] = ((IAstRssSourceFile)tu.getSourceFile()).getTopLevelNodes();
        assertEquals(1, tops.length);
        
        IAstResourceDefinition def2 = tu.findResourceDefinition(
                def.getName().getName() + "_2");
        assertNotNull(def2);

        // change the type of the definition
        def2.setStructType(tu.findStructDeclaration("RECT"));
        
        // re-export the instance AGAIN
        manipulator.getResourceTracker().reset();
        exportInstance("com.nokia.examples.srcmapBasic", "test0");
        checkNoMessages();
        

        rewriteTu(tu);
        //System.out.println("3)\n"+new String(sf.getText()));
        checkRefFile("ref/TestUnwritable2.rss", sf);
        
        // ensure we have a new resource in the main source file
        // and that the original one isn't changed
        tops = ((IAstRssSourceFile)tu.getSourceFile()).getTopLevelNodes();
        assertEquals(2, tops.length);
        
        IAstResourceDefinition def3 = tu.findResourceDefinition(
                def.getName().getName() + "_3");
        assertNotNull(def3);
        assertNotSame(def2, def3);
        assertEquals("RECT", def2.getStructType().getStructName().getName());
        assertEquals("ONE_STRING", def3.getStructType().getStructName().getName());
    }

    public void testTooManyConflicts() throws Exception {
        // check that rewriting a resource in a read-only file
        // makes a unique name and doesn't overwrite target resource
        
        IComponent base = set.lookupComponent("com.nokia.examples.srcmapBasic");
        assertNotNull(base);
        IComponentSourceMapping sourcemapping = getSourceMapping(base);
        assertNotNull(sourcemapping);

        IComponentInstance instance = getInstance("test0");
        assertNotNull(instance);

        tu.setLocalSourceFile(new AstRssSourceFile());

        IAstResourceDefinition def = null;
        for (int i = 0; i < IComponentSourceMapping.MAX_NAME_CONFLICTS+1; i++) {
            // do an export
            manipulator.getResourceTracker().reset();
            sourcemapping.exportInstance(generator, instance);
            if (i < IComponentSourceMapping.MAX_NAME_CONFLICTS)
                checkNoMessages();
            else {
                assertTrue(hadErrors);
                checkMessage("CannotCreateUniqueResource");
                break;
            }

            rewriteTu(tu);
            //System.out.println("0)\n"+new String(sf.getText()));
            
            // get the generated resource
            if (i == 0) {
                def = tu.findResourceDefinitionOfType("ONE_STRING");
                assertNotNull(def);
            } else {
                IAstResourceDefinition def2 = tu.findResourceDefinition(
                        def.getName().getName() + "_" + (i+1));
                assertNotNull(def2);
            }
            
            // now, move the resource to the locals 
            // (i.e. an RSS file without a source file, hence unwritable)
            tu.getLocalSourceFile().moveContents(tu.getSourceFile());
    
            rewriteTu(tu);
            //System.out.println("1)\n"+new String(sf.getText()));
        }
    }

    public void testTooManyConflicts2() throws Exception {
        // check that reexporting doesn't overwrite
        // resource with proper name but different type
        
        IComponent base = set.lookupComponent("com.nokia.examples.srcmapBasic");
        assertNotNull(base);
        IComponentSourceMapping sourcemapping = getSourceMapping(base);
        assertNotNull(sourcemapping);

        IComponentInstance instance = getInstance("test0");
        assertNotNull(instance);

        tu.setLocalSourceFile(new AstRssSourceFile());

        IAstResourceDefinition orig = null;
        for (int i = 0; i < IComponentSourceMapping.MAX_NAME_CONFLICTS+1; i++) {
            // do an export
        	manipulator.getResourceTracker().reset();
            sourcemapping.exportInstance(generator, instance);
            if (i < IComponentSourceMapping.MAX_NAME_CONFLICTS)
                checkNoMessages();
            else {
                assertTrue(hadErrors);
                checkMessage("CannotCreateUniqueResource");
                break;
            }

            rewriteTu(tu);
            //System.out.println("0)\n"+new String(sf.getText()));
            
            // get the generated resource
            IAstResourceDefinition def;
            if (i == 0) {
                def = tu.findResourceDefinitionOfType("ONE_STRING");
                assertNotNull(def);
                orig = def;
            } else {
                def = tu.findResourceDefinition(
                        orig.getName().getName() + "_" + (i+1));
                assertNotNull(def);
            }

            // change the type
            def.setStructType(tu.findStructDeclaration("RECT"));
    
            rewriteTu(tu);
            //System.out.println("1)\n"+new String(sf.getText()));
        }
        checkRefFile("ref/TestTooManyConflicts2.rss", sf);
        
        
    }


    public void testLocalized0() throws Exception {
        // this data model has no localized string entries
    	initWithDataModel(BASE_DIR + "fixture0.nxd");
        
        IComponentInstance instance = getInstance("test0");
        assertNotNull(instance);
        
        generator.setStringHandler(locHandler);
        generator.generateResources(instance);
        checkNoMessages();
        IAstLocSourceFile locFile = getLocFile();
        assertNull(locFile);
        
        checkNoMessages();
        
        rewriteTu(tu);
        checkRefFile("ref/TestLocalized0.rss", sf);
        
        // make sure there are no *.loc preprocessor nodes
        IAstPreprocessorIncludeDirective[] incs = rss.getIncludeFiles();
        assertEquals(1, incs.length);
    }
    
    /**
	 * @return
	 */
	private IAstLocSourceFile getLocFile() {
		IAstPreprocessorIncludeDirective[] incls = generator.getRssFile(null, false).getIncludeFiles();
		for (int i = 0; i < incls.length; i++) {
			if (incls[i].getFile() instanceof IAstLocSourceFile)
				return (IAstLocSourceFile) incls[i].getFile();
		}
		return null;
	}

	public void testLocalized1() throws Exception {
        IComponentInstance instance = getInstance("test0_macro");
        assertNotNull(instance);
        
        generator.setStringHandler(locHandler);
        generator.generateResources(instance);
        checkNoMessages();
        IAstLocSourceFile locFile = getLocFile();
        assertNotNull(locFile);
        IAstLxxSourceFile[] lxxFiles = locFile.getLxxSourceFiles();
        assertEquals(2, lxxFiles.length);

        rewriteTu(tu);
        checkRefFile("ref/TestLocalized1.rss", sf);
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);

        // the value should reference the macro
        checkMemberInit(def, "text", IAstPreprocessorMacroExpression.class, "MACRO_1");

        // and the macro should be registered
        IAstPreprocessorDefineDirective define = tu.findDefine("MACRO_1");
        assertNotNull(define);
        
        // in this file
        assertEquals(asf, define.getAstSourceFile());
        
        IMacro macro = define.getMacro();
        assertNotNull(macro);
        assertEquals("MACRO_1", macro.getName());
        assertEquals("\"A macro value\"", macro.getExpansion());
        assertEquals("\"A macro value\"", define.getMacroValue().getCurrentText(sgProvider.getSourceFormatter()));
        
    }

    public void testLocalized2() throws Exception {

        IComponentInstance instance = getInstance("test0_i18n");
        assertNotNull(instance);
        
        // import localized string table
        generator.setStringHandler(locHandler);
        generator.generateResources(instance);
        checkNoMessages();
        IAstLocSourceFile locFile = getLocFile();
        assertNotNull(locFile);

        IAstLxxSourceFile[] lxxFiles = locFile.getLxxSourceFiles();
        assertEquals(2, lxxFiles.length);
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestLocalized2.rss", sf);
        checkRefFile("ref/TestLocalized2.l10", locFile.findLxxFile(10).getSourceFile());
        checkRefFile("ref/TestLocalized2.l16", locFile.findLxxFile(16).getSourceFile());
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);

        // the value should reference the macro
        checkMemberInit(def, "text", IAstPreprocessorMacroExpression.class, "STR_1");

        //System.out.println(locFile.getSourceFile().getFilePath()+"\n"+ locFile.getCurrentText()+"\n");
        checkRefFile("ref/TestLocalized2_loc.rss", locFile.getSourceFile());

        // the macro should show up *.lxx files
        lxxFiles = locFile.getLxxSourceFiles();
        assertEquals(2, lxxFiles.length);

        checkRefFile("ref/TestLocalized2_lxx_1.rss", lxxFiles[0].getSourceFile());
        checkRefFile("ref/TestLocalized2_lxx_2.rss", lxxFiles[1].getSourceFile());

        assertTrue(lxxFiles[0].getLanguageCode() == Language.LANG_American ||
                lxxFiles[0].getLanguageCode() == Language.LANG_Russian);
        assertTrue(lxxFiles[1].getLanguageCode() == Language.LANG_American ||
                lxxFiles[1].getLanguageCode() == Language.LANG_Russian);
        
        for (int i = 0; i < 2; i++) {
            //System.out.println("file " + lxxFiles[i].getSourceFile().getFilePath());
            //System.out.println(lxxFiles[i].getCurrentText()+"\n");
            IAstPreprocessorDefineDirective define = lxxFiles[i].findDefine("STR_1");
            assertNotNull(define);
            assertEquals(lxxFiles[i].getAstSourceFile(), define.getAstSourceFile());
            
            IMacro macro = define.getMacro();
            assertNotNull(macro);
            assertEquals("STR_1", macro.getName());
            if (lxxFiles[i].getLanguageCode() == Language.LANG_American) {
                assertEquals("\"Hello\"", macro.getExpansion());
                assertEquals("\"Hello\"", define.getMacroValue().getCurrentText(sgProvider.getSourceFormatter()));
            } else {
                assertEquals("\""+UTF8Strings.RUSSIAN_HELLO+", dude\"", macro.getExpansion());
                assertEquals("\""+UTF8Strings.RUSSIAN_HELLO+", dude\"", define.getMacroValue().getCurrentText(sgProvider.getSourceFormatter()));
            }
        }
    }

    public void testLocalized0Rls() throws Exception {
        // this data model has no localized string entries
    	initWithDataModel(BASE_DIR + "fixture0.nxd"); 
        
        IComponentInstance instance = getInstance("test0");
        assertNotNull(instance);

        generator.setStringHandler(rlsHandler);
        generator.generateResources(instance);
        checkNoMessages();
        IAstLocSourceFile locFile = getLocFile();
        assertNull(locFile);
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestLocalized0Rls.rss", sf);
    }
    
    public void testLocalized1Rls() throws Exception {
        IComponentInstance instance = getInstance("test0_macro");
        assertNotNull(instance);
        
        // import localized string table
        //OldRssEngine.importLocalizedStringsIntoRls(context, tu, dataModel, (IAstRssSourceFile) tu.getSourceFile());

        // do the export
        //sourcemapping.exportInstance(manipulator, tu, instance);
        generator.setStringHandler(rlsHandler);
        generator.generateResources(instance);
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestLocalized1Rls.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);

        // the value should reference the macro
        checkMemberInit(def, "text", IAstPreprocessorMacroExpression.class, "MACRO_1");

        // and the macro should be registered
        IAstPreprocessorDefineDirective define = tu.findDefine("MACRO_1");
        assertNotNull(define);
        
        // in this file
        assertEquals(asf, define.getAstSourceFile());
        
        IMacro macro = define.getMacro();
        assertNotNull(macro);
        assertEquals("MACRO_1", macro.getName());
        assertEquals("\"A macro value\"", macro.getExpansion());
        assertEquals("\"A macro value\"", define.getMacroValue().getCurrentText(sgProvider.getSourceFormatter()));
    }

    public void testLocalized2Rls() throws Exception {
        IComponentInstance instance = getInstance("test0_i18n");
        assertNotNull(instance);

        generator.setStringHandler(rlsHandler);
        generator.generateResources(instance);
        checkNoMessages();

        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestLocalized2Rls.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);

        // the value should reference the macro
        checkMemberInit(def, "text", IAstPreprocessorMacroExpression.class, "STR_1");

        //System.out.println(locFile.getSourceFile().getFilePath()+"\n"+ locFile.getCurrentText()+"\n");
        IAstRlsSourceFile[] rlsFiles = rss.getRlsSourceFiles();

        // the macro should show up *.rls files
        assertEquals(2, rlsFiles.length);

        checkRefFile("ref/TestLocalized2Rls_1.rss", rlsFiles[0].getSourceFile());
        checkRefFile("ref/TestLocalized2Rls_2.rss", rlsFiles[1].getSourceFile());

        IAstRlsSourceFile rlsFile;
        rlsFile = (IAstRlsSourceFile) rlsFiles[0];
        assertTrue(rlsFile.getLanguageCode() == Language.LANG_American ||
                rlsFile.getLanguageCode() == Language.LANG_Russian);
        rlsFile = (IAstRlsSourceFile) rlsFiles[1];
        assertTrue(rlsFile.getLanguageCode() == Language.LANG_American ||
                rlsFile.getLanguageCode() == Language.LANG_Russian);
        
        for (int i = 0; i < 2; i++) {
            //System.out.println("file " + lxxFiles[i].getSourceFile().getFilePath());
            //System.out.println(lxxFiles[i].getCurrentText()+"\n");
            rlsFile = (IAstRlsSourceFile) rlsFiles[i];
            IAstRlsString string = rlsFile.findRlsString("STR_1");
            assertNotNull(string);
            assertEquals(rlsFile.getAstSourceFile(), string.getAstSourceFile());
            
            assertEquals("STR_1", string.getIdentifier().getName());
            if (rlsFile.getLanguageCode() == Language.LANG_American)
                assertEquals("\"Hello\"", string.getString().getCurrentText(sgProvider.getSourceFormatter()));
            else
                assertEquals("\"" + UTF8Strings.RUSSIAN_HELLO + ", dude\"", 
                        string.getString().getCurrentText(sgProvider.getSourceFormatter()));
        }
    }

    public void testLocalizedComplexProperty() throws Exception {

    	exportInstance("test0_i18n_holder");
        checkNoMessages();

        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestLocalizedComplexProperty.rss", sf);
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(def);

        // the value should reference the macro
        checkMemberInit(def, "text", IAstPreprocessorMacroExpression.class, "STR_1");

    }

    public void testIdentifier0() throws Exception {
        IComponentInstance instance = getInstance("test_id_0");
        exportInstance(instance);

        checkNoMessages();

        rewriteTu(tu);
        checkRefFile("ref/TestBasicIdentifiers0.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("SIMPLE_TYPES_DEFAULTS");
        assertNotNull(def);

        // the value should reference a name
        checkMemberInit(def, "word", IAstIdExpression.class, "EMbmImagePicture");

    }
    

    public void testIdentifier1() throws Exception {
        IComponentInstance instance = getInstance("test_id_1");
        exportInstance(instance);

        checkNoMessages();

        rewriteTu(tu);
        checkRefFile("ref/TestBasicIdentifiers1.rss", sf);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("SIMPLE_TYPES_DEFAULTS");
        assertNotNull(def);

        // the value should reference nothing -- 
        // the ID is blank, which is enough to override the default
        // of "1"
        checkNoMemberInit(def, "word");

    }
    // TODO: test errors
    //  -- illegal data model values
    //  -- all the errors emitted from InstanceSourceMapper
    
}
