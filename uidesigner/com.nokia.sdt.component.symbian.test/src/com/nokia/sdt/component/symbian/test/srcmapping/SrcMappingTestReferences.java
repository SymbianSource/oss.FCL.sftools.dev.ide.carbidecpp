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
import com.nokia.sdt.sourcegen.doms.rss.dom.*;

/**
 * 
 *
 */
public class SrcMappingTestReferences extends SrcMappingBase {

	/**
	 * Ensure a component which references the same instance
	 * twice does not spawn multiple copies of that resource
	 * @throws Exception
	 */
    public void testReferences() throws Exception {
        exportInstance("com.nokia.examples.srcmapRefs", "test4");
        checkNoMessages();
        exportInstance("com.nokia.examples.srcmapBasic", "test0");
        checkNoMessages();
         
        rewriteTu(tu);
        //System.out.println("---\n"+new String(sf.getText()));
        checkRefFile("ref/TestReferences.rss", sf);

        // make sure we have only two resources, not several dupes
        // (aka lots of empty temporary referent resources)
        IAstResourceDefinition defs[] = tu.getResourceDefinitions();
        assertEquals(2, defs.length);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("OWNER");
        assertNotNull(def);
        IAstResourceDefinition ref = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(ref);
        
        // check all the simple types for proper formatting and DOM expression
        checkMemberInit(def, "theLink", IAstIdExpression.class, ref.getName().getName());
        checkMemberInit(def, "theLlink", IAstIdExpression.class, ref.getName().getName());
        
        // make sure referent written
        checkMemberInit(ref, "text", IAstLiteralExpression.K_STRING, "\"check this textual data\"");
        
    }

    /**
     * Check what happens when a parent component has
     * references to child components, when parent is generated first  
     */
    public void testReferencesParentChild1() throws Exception {
    	exportInstance("com.nokia.examples.srcmapRefs", "testRefs_ParentChildOrdering");
        checkNoMessages();
        
        // export referenced component as a child
    	exportInstance("com.nokia.examples.srcmapBasic", "testRefs_Child");
        checkNoMessages();
         
        rewriteTu(tu);
        //System.out.println("---\n"+new String(sf.getText()));
        checkRefFile("ref/TestReferencesParentChild.rss", sf);

        // make sure we have only two resources, not several dupes
        // (aka lots of empty temporary referent resources)
        IAstResourceDefinition defs[] = tu.getResourceDefinitions();
        assertEquals(2, defs.length);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("OWNER");
        assertNotNull(def);
        IAstResourceDefinition ref = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(ref);
        
        // make sure the link worked
        checkMemberInit(def, "theLink", IAstIdExpression.class, ref.getName().getName());
        
        // make sure the referent has contents
        checkMemberInit(ref, "text", IAstLiteralExpression.K_STRING, "\"check this textual data\"");
    }

    /**
     * Check what happens when a parent component has
     * references to child components, when child is generated first  
     */
    public void testReferencesParentChild2() throws Exception {
        
        // export referenced component
    	exportInstance("com.nokia.examples.srcmapBasic", "testRefs_Child");
        checkNoMessages();
         
        // export referencing component
    	exportInstance("com.nokia.examples.srcmapRefs", "testRefs_ParentChildOrdering");
        checkNoMessages();
        
        rewriteTu(tu);
        //System.out.println("---\n"+new String(sf.getText()));
        checkRefFile("ref/TestReferencesParentChild2.rss", sf);

        // make sure we have only two resources, not several dupes
        // (aka lots of empty temporary referent resources)
        IAstResourceDefinition defs[] = tu.getResourceDefinitions();
        assertEquals(2, defs.length);
        
        
        IAstResourceDefinition def = tu.findResourceDefinitionOfType("OWNER");
        assertNotNull(def);
        IAstResourceDefinition ref = tu.findResourceDefinitionOfType("ONE_STRING");
        assertNotNull(ref);
        
        // make sure the link worked
        checkMemberInit(def, "theLink", IAstIdExpression.class, ref.getName().getName());
        
        // make sure the referent has contents
        checkMemberInit(ref, "text", IAstLiteralExpression.K_STRING, "\"check this textual data\"");
    }

    /**
     * Test that a resource emitted as an expression and also
     * referenced, we get both, and the resource info is valid. 
     * @throws Exception
     */
    public void testMultiExport1() throws Exception {
    	
    	IComponentInstance refParent = getInstance("testRefs_MultiExport");
    	IComponentInstance refChild = getInstance("testRefs_MultiExportChild");
    	IComponentInstance reffer = getInstance("testRefs_MultiExportReferrer");

        // export referencing component
    	exportInstance(reffer);
        
    	// export referenced component
    	exportInstance(refParent);

        // export child
    	exportInstance(refChild);
        
        rewriteTu(tu);

        assertNotNull(getGeneratedResourceName(refParent));
        assertNotNull(getGeneratedResourceName(refChild));
        assertNotNull(getGeneratedResourceName(reffer));
        
        checkNoMessages();
        checkRefFile("ref/TestReferencesMultiExport1.rss", sf);
    }

    /**
     * Test that a resource emitted as an expression and also
     * referenced, we get both, and the resource info is valid. 
     * @throws Exception
     */
    public void testMultiExport2() throws Exception {
    	
    	IComponentInstance refParent = getInstance("testRefs_MultiExport");
    	IComponentInstance refChild = getInstance("testRefs_MultiExportChild");
    	IComponentInstance reffer = getInstance("testRefs_MultiExportReferrer");

    	// different order than previous
    	
    	// export referenced component
    	exportInstance(refParent);

        // export child
    	exportInstance(refChild);
        
        // export referencing component
    	exportInstance(reffer);
        
        rewriteTu(tu);

        assertNotNull(getGeneratedResourceName(refParent));
        assertNotNull(getGeneratedResourceName(refChild));
        assertNotNull(getGeneratedResourceName(reffer));
        
        checkNoMessages();
        checkRefFile("ref/TestReferencesMultiExport2.rss", sf);
    }


}
