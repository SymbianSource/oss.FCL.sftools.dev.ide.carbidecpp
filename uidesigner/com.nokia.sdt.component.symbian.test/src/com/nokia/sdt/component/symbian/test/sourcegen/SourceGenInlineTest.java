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
import com.nokia.sdt.testsupport.ComponentHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 *
 */
public class SourceGenInlineTest extends SourceGenTestBase {

    static ComponentProvider provider;
    static IComponentSet set;
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.component.symbian.test.sourcegen.SourceGenTestBase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        if (set == null) {
        	provider = TestDataModelHelpers.findOrCreateProviderForUserComponents("data/srcgen");
            set = ComponentHelpers.queryAllComponents(provider);
        }
    }
    
    public void testInlineFuncScope() throws Exception {
        String js = getSourceGenInfoScript(set, "com.nokia.examples.srcgen4");
        Pattern func = Pattern.compile("generate = function[^{]*\\{(.*?)return contribs.*\\}", Pattern.MULTILINE+Pattern.DOTALL);
        Matcher matcher = func.matcher(js);
        assertTrue(matcher.find());
        
        String f = matcher.group(1);
        int idx = -1;
        int cnt = 0;
        while ((idx = f.indexOf("inline text", idx+1)) > 0)
            cnt++;
    
        assertEquals(3, cnt);
    }

    public void testInlineFileScope() throws Exception {
    	String js = getSourceGenInfoScript(set, "com.nokia.examples.srcgen4b");
        
        Pattern func = Pattern.compile("(.*)generate = function.*\\{.*?\\}(.*)", Pattern.MULTILINE+Pattern.DOTALL);
        Matcher matcher = func.matcher(js);
        assertTrue(matcher.find());
        
        String f = matcher.group(1) + matcher.group(2);
        int idx = -1;
        int cnt = 0;
        while ((idx = f.indexOf("inline text", idx+1)) > 0)
            cnt++;
    
        assertEquals(3, cnt);
    }

}
