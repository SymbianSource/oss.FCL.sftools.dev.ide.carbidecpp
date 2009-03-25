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

package com.nokia.sdt.component.symbian.test.sourcegen;

import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.sourcegen.IComponentSourceGenInfo;
import com.nokia.sdt.sourcegen.ISourceGenLocation;
import com.nokia.sdt.testsupport.ComponentHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import java.util.Map;

/**
 * This is a test of compiling the defineLocations stuff
 * from XML.
 * 
 *
 */
public class SourceGenLocationsTest extends SourceGenTestBase {

    static ComponentProvider provider;
    static IComponentSet set;
  
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        if (set == null) {
        	provider = TestDataModelHelpers.findOrCreateProviderForUserComponents("data/srcgen");
            set = ComponentHelpers.queryAllComponents(provider);
        }
    }
    
    public void testNone() throws Exception {
        IComponentSourceGenInfo info = getSourceGenInfo(set, "com.nokia.examples.srcgen0");
        assertEquals(0, info.getLocationIdToLocationMap().size());
    }
    
    public void testBasic() throws Exception {
        IComponentSourceGenInfo info = getSourceGenInfo(set, "com.nokia.examples.srcgen3");
        Map map = info.getLocationIdToLocationMap();
        assertEquals(4, map.size());
        
        if (EMIT_MESSAGES)
            System.out.println(getSourceGenInfoScript(set, "com.nokia.examples.srcgen3"));

        
        ISourceGenLocation loc;
        
        loc = (ISourceGenLocation) map.get("srcgen3$FILE");
        assertNotNull(loc);
        assertNull(loc.getBaseLocation() );
        assertEquals("src", loc.getDirectory() );
        assertEquals("main.cpp", loc.getFile() );
        assertEquals("", loc.getLocation());
        assertEquals(//"com"+SEPARATOR+"nokia"+SEPARATOR+"examples"+SEPARATOR+"srcgen3"+SEPARATOR
                "defineLocation"+SEPARATOR+"srcgen3"+SEPARATOR+"FILE",
                loc.getFunction());
        
        loc = (ISourceGenLocation) map.get("srcgen3$INCLUDES");
        assertNotNull(loc);
        assertEquals("srcgen3$FILE", loc.getBaseLocation() );
        assertNull(loc.getDirectory() );
        assertNull(loc.getFile() );
        assertEquals("region(Designer Includes)", loc.getLocation());
        
        loc = (ISourceGenLocation) map.get("srcgen3$CLASS");
        assertNotNull(loc);
        assertEquals("srcgen3$FILE", loc.getBaseLocation() );
        assertNull(loc.getDirectory() );
        assertNull(loc.getFile() );
        assertEquals("class(MyClass)", loc.getLocation());
        
        loc = (ISourceGenLocation) map.get("srcgen3$INIT_COMPONENTS");
        assertNotNull(loc);
        assertEquals("srcgen3$CLASS", loc.getBaseLocation() );
        assertNull(loc.getDirectory() );
        assertNull(loc.getFile() );
        assertEquals("function(InitComponents())", loc.getLocation());
        
    }

    public void testDerived() throws Exception {
        IComponentSourceGenInfo info = getSourceGenInfo(set, "com.nokia.examples.srcgen3");
        ISourceGenLocation origClassLoc;
        
        origClassLoc = (ISourceGenLocation) info.getLocationIdToLocationMap().get("srcgen3$CLASS");
 
        /////////
        
        info = getSourceGenInfo(set, "com.nokia.examples.srcgen3_derived");
        Map map = info.getLocationIdToLocationMap();
        assertEquals(4, map.size());
        
       if (EMIT_MESSAGES)
            System.out.println(getSourceGenInfoScript(set, "com.nokia.examples.srcgen3"));

        ISourceGenLocation loc;
        
        loc = (ISourceGenLocation) map.get("srcgen3$FILE");
        assertNotNull(loc);
        
        assertNull(loc.getBaseLocation() );
        assertEquals("src", loc.getDirectory() );
        assertEquals("main.cpp", loc.getFile() );
        assertEquals("", loc.getLocation());
        assertEquals(//"com"+SEPARATOR+"nokia"+SEPARATOR+"examples"+SEPARATOR+"srcgen3"+SEPARATOR
                "defineLocation"+SEPARATOR+"srcgen3"+SEPARATOR+"FILE",
                loc.getFunction());
        
        loc = (ISourceGenLocation) map.get("srcgen3$INCLUDES");
        assertNotNull(loc);
        assertEquals("srcgen3$FILE", loc.getBaseLocation() );
        assertNull(loc.getDirectory() );
        assertNull(loc.getFile() );
        assertEquals("region(Designer Includes)", loc.getLocation());
        
        loc = (ISourceGenLocation) map.get("srcgen3$CLASS");
        assertNotNull(loc);

        // this component overrides this location
        assertNotSame(origClassLoc, loc);

        assertEquals("srcgen3$FILE", loc.getBaseLocation() );
        assertNull(loc.getDirectory() );
        assertNull(loc.getFile() );
        assertEquals("class(MyClass)", loc.getLocation());
        
        loc = (ISourceGenLocation) map.get("srcgen3$INIT_COMPONENTS");
        assertNotNull(loc);
        assertEquals("srcgen3$CLASS", loc.getBaseLocation() );
        assertNull(loc.getDirectory() );
        assertNull(loc.getFile() );
        assertEquals("function(InitComponents())", loc.getLocation());
                
    }
    
    public void testErrors() throws Exception {
        testMessages(set, "com.nokia.examples.srcgen3_bad0",
                new String[] { "CannotSpecifyWithBase" } );
        testMessages(set, "com.nokia.examples.srcgen3_bad1",
                new String[] { "CannotSpecifyWithBase" } );
        testMessages(set, "com.nokia.examples.srcgen3_bad2",
                new String[] { "CannotSpecifyWithBase" } );

        testMessages(set, "com.nokia.examples.srcgen3_bad3",
                new String[] { "MustSpecifyWithoutBase" } );
        testMessages(set, "com.nokia.examples.srcgen3_bad4",
                new String[] { "MustSpecifyWithoutBase" } );
        testMessages(set, "com.nokia.examples.srcgen3_bad5",
                new String[] { "MustSpecifyWithoutBase" } );
        testMessages(set, "com.nokia.examples.srcgen3_bad6",
                new String[] { "ReusedLocationId" } );

        testMessages(set, "com.nokia.examples.srcgen3_derived_bad0",
                new String[] { "ReusedLocationId" } );

        testMessages(set, "com.nokia.examples.srcgen3_bad7",
                new String[] { "NoPhaseInDefineLocation" } );

    }
    
    
}
