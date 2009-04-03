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

import com.nokia.sdt.sourcegen.doms.rss.dom.IAstResourceDefinition;

/**
 * Test removing stuff from RSS while updating.
 * 
 *
 */
public class SrcMappingTestDeleting extends SrcMappingBase {
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.test.srcmapping.SrcMappingBase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // this is used in the .rss files, which are also copied away
        copyAwayFile("user/myfoo.h");
        
    }
    
    /**
	 * Test that deleting a known resource with a known field
	 * and unknown field works.
	 * @throws Exception
	 */
	public void testBasicKnownContents1() throws Exception {
	    
	    parseRssFrom("user/TestDeletingKnownContents1.rss");
	    setInstanceToResourceMapping("test0", "r_test0");
	
	    removeInstance(getInstance("test0"));
	    checkNoMessages();
	    generator.generateResources(null);
	    
	    checkRefFile("ref2/TestDeletingKnownContents1.rss", sf);
	    
	    IAstResourceDefinition def = tu.findResourceDefinitionOfType("ONE_STRING");
	    assertNull(def);
	}

    /**
     * Test rewriting a file with an unknown resource.  
     * Basic test to ensure we don't overwrite that resource.
     * @throws Exception
     */
    public void testBasicUnknownContents1() throws Exception {
        parseRssFrom("user/TestDeletingUnknownContents1.rss");
        assertFalse(rss.isDirtyTree());

        setInstanceToResourceMapping("test0", "r_test0_2");
        
        removeInstance(getInstance("test0"));
	    generator.generateResources(null);
	    checkNoMessages();

        //rewriteTu(tu);
        checkRefFile("ref2/TestDeletingBasicUnknownContents1.rss", sf);

        
        IAstResourceDefinition def = tu.findResourceDefinition("r_test0");
        assertNotNull(def);

        def = tu.findResourceDefinition("r_test0_2");
        assertNull(def);

    }



}
