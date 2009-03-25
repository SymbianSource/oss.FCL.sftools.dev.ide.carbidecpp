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

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.sourcegen.IComponentSourceMapping;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstEnumDeclaration;

import java.io.File;

/**
 * 
 *
 */
public class SrcMappingTestUniqueEnums extends SrcMappingBase {

    static final String CONTAINER = "MyContainer";
    
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

    public void testEnumUniqueBasic() throws Exception {
        // make sure we can invoke the uniquification algorithm
        
        sf.setFile(new File("TestEnumUniqueBasic1.rss"));
        generator.setRssFileForTesting(rss);
        
        exportInstance("com.nokia.examples.srcmapEnumUnique", "testEnumUniqueBasic0");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestEnumUniqueBasic0.rss", sf);
                        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("COMMAND");
        assertNotNull(def);

        // not unique
        checkMemberInit(def, "command", IAstIdExpression.class, 
                "CommandCancel");
        
        /////////

        IComponentInstance instance = getInstance("testEnumUniqueBasic1");
        assertNotNull(instance);
        
        // do the export
        exportInstance(instance);
        checkNoMessages();
        
        rewriteTu(tu);
                
        
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(2, defs.length);
        def = defs[1];

        // unique, using com.nokia.sdt.component.symbian.NAME_ALG_COMMANDS
        // instance name is testEnumUniqueBasic1
        // property name is theCmd
        checkMemberInit(def, "command", IAstIdExpression.class, 
                "E"+CONTAINER+"TestEnumUniqueBasic1TheCmd");
        
    }

    public void testEnumUniqueness1() throws Exception {
        // test that unique ids are properly unique and located
        // in the right place

        sf.setFile(new File("TestEnumUniqueness1.rss"));
        generator.setRssFileForTesting(rss);

        exportInstance("com.nokia.examples.srcmapEnumUnique", "testEnumUniqueness1");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestEnumUniqueness1.rss", sf);
                
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("COMMAND");
        assertNotNull(def);

        // unique, using com.nokia.sdt.component.symbian.NAME_ALG_COMMANDS
        // instance name is testEnumUniqueness1
        // property name is theCmd
        checkMemberInit(def, "command", IAstIdExpression.class, 
                "E" + CONTAINER+"TestEnumUniqueness1TheCmd");
        
        IAstSourceFile hrhFile = rss.findIncludeFile("TestEnumUniqueness1.hrh");
        assertNotNull(hrhFile);
        
        checkEnumDeclaredIn("E"+CONTAINER+"TestEnumUniqueness1TheCmd",
                "T"+CONTAINER+"Commands",
                hrhFile);
        
        checkRefFile("ref/TestEnumUniqueness1.hrh", hrhFile.getSourceFile());
    }

    protected void checkEnumDeclaredIn(String enumName, String enumDeclName, IAstSourceFile file) {
        // make sure enumerator was registered
        IAstEnumerator enm = tu.findEnumerator(enumName);
        assertNotNull(enm);

        // make sure it is in the right enumeration
        IAstEnumDeclaration decl = enm.getEnumeration();
        assertNotNull(decl);
        if (enumDeclName != null) {
            assertNotNull(decl.getName());
            assertEquals(enumDeclName, decl.getName().getName());
        } else {
            assertNull(decl.getName());
        }
        
        // make sure it is in an *.hrh file
        IAstSourceFile srcFile = decl.getAstSourceFile();
        assertNotNull(srcFile);
        assertEquals(file, srcFile);
    }
    
    public void testEnumUniqueness2() throws Exception {
        // try again with two enums

        sf.setFile(new File("TestEnumUniqueness2.rss"));
        generator.setRssFileForTesting(rss);

        exportInstance("com.nokia.examples.srcmapEnumUnique", "testEnumUniqueness1");
        checkNoMessages();
        
        // and another
        IComponentInstance instance = getInstance("testEnumUniqueness2");
        assertNotNull(instance);
        
        exportInstance(instance);
        checkNoMessages();
        
        /////
        
        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestEnumUniqueness2.rss", sf);
                
        
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(2, defs.length);

        // unique, using com.nokia.sdt.component.symbian.NAME_ALG_COMMANDS
        // instance name is testEnumUniqueness1
        // property name is theCmd
        checkMemberInit(defs[0], "command", IAstIdExpression.class, 
                "E"+CONTAINER+"TestEnumUniqueness1TheCmd");
        checkMemberInit(defs[1], "command", IAstIdExpression.class, 
                "E"+CONTAINER+"TestEnumUniqueness2TheCmd");

        IAstSourceFile hrhFile = rss.findIncludeFile("TestEnumUniqueness2.hrh");
        assertNotNull(hrhFile);

        checkEnumDeclaredIn("E"+CONTAINER+"TestEnumUniqueness1TheCmd", 
                "T"+CONTAINER+"Commands", hrhFile);
        checkEnumDeclaredIn("E"+CONTAINER+"TestEnumUniqueness2TheCmd", 
                "T"+CONTAINER+"Commands", hrhFile);
        
        checkRefFile("ref/TestEnumUniqueness2.hrh", hrhFile.getSourceFile());
    }

    public void testEnumTooManyConflicts() throws Exception {
        // try case of fixing conflicts with existing enums

        sf.setFile(new File("TestEnumTooManyConflicts.rss"));
        generator.setRssFileForTesting(rss);

        IComponentInstance instance = getInstance("testEnumUniqueness1");
        assertNotNull(instance);

        IAstSourceFile hrhFile = null;
        
        for (int i = 0; i < IComponentSourceMapping.MAX_NAME_CONFLICTS + 1; i++) {
            // do the export
        	exportInstance(instance);
            if (i < IComponentSourceMapping.MAX_NAME_CONFLICTS)
                checkNoMessages();
            else {
                assertTrue(hadErrors);
                checkMessage("CannotUniquifyEnum");
                break;
            }

            manipulator.getResourceTracker().reset();
            manipulator.getTypeHandler().reset();

            rewriteTu(tu);

            IAstResourceDefinition[] defs = tu.getResourceDefinitions();
            assertEquals(1, defs.length);

            // unique, using com.nokia.sdt.component.symbian.NAME_ALG_COMMANDS
            // instance name is testEnumUniqueness1
            // property name is theCmd
            String expectedName = "E"+CONTAINER+"TestEnumUniqueness1TheCmd";
            if (i > 0)
                expectedName = expectedName + (i+1);
            
            checkMemberInit(defs[0], "command", IAstIdExpression.class, expectedName);

            hrhFile = rss.findIncludeFile("TestEnumTooManyConflicts.hrh");
            assertNotNull(hrhFile);

            if (false) {
            	System.out.println(hrhFile.getSourceRange());
            	IAstEnumDeclaration decl = tu.findEnumDeclaration("T"+CONTAINER+"Commands");
            	IAstEnumerator[] enms = decl.getEnumerators();
            	for (int j = 0; j < enms.length; j++) {
					System.out.println("ext="+enms[j].getExtendedRange());
				}
            }
            
            checkEnumDeclaredIn(expectedName, "T"+CONTAINER+"Commands", hrhFile);
            
            // delete the resource, but keep the enum
            rss.removeFileNode(defs[0]);
            tu.refresh();
        }
        
        checkRefFile("ref/TestEnumTooManyConflicts.rss", sf);
        checkRefFile("ref/TestEnumTooManyConflicts.hrh", hrhFile.getSourceFile());
        
    }

    public void testEnumUniqueness3() throws Exception {
        // try case of enums in unnamed vs. named locations

        sf.setFile(new File("TestEnumUniqueness3.rss"));
        generator.setRssFileForTesting(rss);

        IComponentInstance instance = getInstance("testEnumUniqueness1");
        assertNotNull(instance);
        
        // do the export
        exportInstance(instance);
        checkNoMessages();

        rewriteTu(tu);
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("COMMAND");
        assertNotNull(def);
        String defName = def.getName().getName();
        
        // get the known enum
        IAstEnumerator enm = tu.findEnumerator(
                "E"+CONTAINER+"TestEnumUniqueness1TheCmd");
        assertNotNull(enm);
        
        // move it
        IAstEnumDeclaration decl = new AstEnumDeclaration(null);
        rss.appendFileNode(decl);
        enm.getEnumeration().removeEnumerator(enm);
        decl.addEnumerator(enm);
        
        // delete the original export
        rss.removeFileNode(def);
        tu.refresh();
        manipulator.getTypeHandler().reset();
        manipulator.getResourceTracker().reset();
        
        // export again
        instance = getInstance("testEnumUniqueness1");
        assertNotNull(instance);

        exportInstance(instance);
        checkNoMessages();
        
        /////

        rewriteTu(tu);
        //System.out.println(new String(sf.getText()));
        checkRefFile("ref/TestEnumUniqueness3.rss", sf);

        IAstSourceFile hrhFile = rss.findIncludeFile("TestEnumUniqueness3.hrh");
        assertNotNull(hrhFile);
        checkRefFile("ref/TestEnumUniqueness3.hrh", hrhFile.getSourceFile());
                
        // now check that the struct definition was rewritten
        // and its enum is uniquified
        IAstResourceDefinition[] defs = tu.getResourceDefinitions();
        assertEquals(1, defs.length);
        assertEquals(defName, defs[0].getName().getName());
        
        checkMemberInit(defs[0], "command", IAstIdExpression.class, 
                "E"+CONTAINER+"TestEnumUniqueness1TheCmd2");


        checkEnumDeclaredIn("E"+CONTAINER+"TestEnumUniqueness1TheCmd", null, rss);
        checkEnumDeclaredIn("E"+CONTAINER+"TestEnumUniqueness1TheCmd2", "T"+CONTAINER+"Commands", hrhFile);
        
    }

}
