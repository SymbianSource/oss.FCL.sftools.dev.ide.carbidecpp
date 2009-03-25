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

import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.testsupport.ComponentHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;


/**
 * This is a test of changes made after the initial release
 * (some are already tested in sourcegen.tests)
 * 
 *
 */
public class SourceGenTemplatesRel2Test extends SourceGenTestBase {

    static ComponentProvider provider;
    static IComponentSet set;
    
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        EMIT_MESSAGES = true;
        Logging.alwaysLogToConsole = true;
        if (provider == null) {
        	provider = TestDataModelHelpers.findOrCreateProviderForUserComponents(
        			"data/srcgen_rel2");
        }
        set = ComponentHelpers.queryAllComponents(provider);
    }

    public void testMacroErrorDefining() throws Exception {
        testMessages(set, "com.nokia.examples.macros_bad1",
                new String[] { "RedefiningMacroError" });
        testMessages(set, "com.nokia.examples.macros_bad2",
                new String[] { "DupMacroArgument" });
        testMessages(set, "com.nokia.examples.macros_bad3",
                new String[] { "BadImportArgumentsMacro" });
        // it's okay to redefine arguments this way
        //testMessages(set, "com.nokia.examples.macros_bad4",
        //        new String[] { "DupMacroArgument" });
        testMessages(set, "com.nokia.examples.macros_bad5",
                new String[] { "BadImportArgumentsMacro" });
        testMessages(set, "com.nokia.examples.macros_bad6",
                new String[] { "BadPassArgumentsArgument" });
        // it's okay to redefine arguments this way
        //testMessages(set, "com.nokia.examples.macros_bad7",
        //        new String[] { "DupMacroArgumentOnImport" });
        
        
   	
    }
    public void testMacroErrorUsing() throws Exception {
        testMessages(set, "com.nokia.examples.macros_bad8",
                new String[] { "NoSuchMacro" });
        testMessages(set, "com.nokia.examples.macros_bad9",
                new String[] { "UnknownExpandArgument" });
        testMessages(set, "com.nokia.examples.macros_bad11",
                new String[] { "UnknownMacroVariableModifier" });
    	
    }
    public void testMacroErrorTemplates() throws Exception {
        testMessages(set, "com.nokia.examples.macros_bad10",
                new String[] { "InvalidTemplateIdInMacro" });
    	
    }

}
