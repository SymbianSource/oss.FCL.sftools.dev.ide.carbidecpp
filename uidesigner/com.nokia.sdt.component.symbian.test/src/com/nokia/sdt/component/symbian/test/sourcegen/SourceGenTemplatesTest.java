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

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.sourcegen.IComponentSourceGen;
import com.nokia.sdt.sourcegen.ISourceGenTemplate;
import com.nokia.sdt.testsupport.ComponentHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * This is a test of compiling the template/useTemplate
 * nodes from XML.
 * 
 *
 */
public class SourceGenTemplatesTest extends SourceGenTestBase {

    static ComponentProvider provider;
    static IComponentSet set;
    
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        if (provider == null) {
        	provider = TestDataModelHelpers.findOrCreateProviderForUserComponents("data/srcgen");
        }
        set = ComponentHelpers.queryAllComponents(provider);
    }

    public void testDerivedSkip() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen0_skip_derived");
        assertNotNull(base);
        
        IComponentSourceGen sourcegen = getSourceGen(base);

        assertNotNull(sourcegen);
        assertEquals(0, messages.size());

        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        // use-template "*" used AFTER
        checkTemplates(new String[] { "bar", "foo", "rap" }, map);
    }


    public void testBasic() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen0");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
        assertEquals(0, messages.size());
        
    }

    public void testNoSrcGen() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.nosrcgen");
        assertNotNull(base);

        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNull(sourcegen);
    }

    public void testBase() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen0");
        assertNotNull(base);
        
        IComponentSourceGen sourcegen = getSourceGen(base);

        assertNotNull(sourcegen);
        assertEquals(0, messages.size());

        // prototype should look like this, based on component ID
        assertEquals("com"+SEPARATOR+"nokia"+SEPARATOR+"examples"+SEPARATOR+"srcgen0", sourcegen.getSourceGenInfo().getPrototype());
        
        // don't rely on the exact component
        //assertEquals(base, sourcegen.getComponent());
        assertEquals(base.getId(), sourcegen.getComponent().getId());

        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        checkTemplates(new String[] { "foo", "rap" }, map);
        
        ISourceGenTemplate t = (ISourceGenTemplate) map.get("foo");
        assertNotNull(t);
        assertEquals("foo", t.getId());
        assertNull(t.getGroup());
        assertEquals("generate"+SEPARATOR+"foo", t.getFunction());
        assertEquals("", t.getForms());
        assertNull(t.getIfEvents());
        
        ISourceGenTemplate t2 = (ISourceGenTemplate) map.get("rap");
        assertNotNull(t2);
        assertEquals("rap", t2.getId());
        assertNull(t.getGroup());
        assertEquals("generate"+SEPARATOR+"rap", t2.getFunction());
        assertEquals("", t2.getForms());
        List events = t2.getIfEvents();
        assertNotNull(events);
        assertEquals("eventChanged", events.get(0));

    }

    public void testDerivedNoInherit() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen0_derived0");
        assertNotNull(base);
        
        IComponentSourceGen sourcegen = getSourceGen(base);

        assertNotNull(sourcegen);
        assertEquals(0, messages.size());

        // prototype should look like this, based on component ID
        assertEquals("com"+SEPARATOR+"nokia"+SEPARATOR+"examples"+SEPARATOR+"srcgen0_derived0", sourcegen.getSourceGenInfo().getPrototype());
        
        assertEquals(base, sourcegen.getComponent());

        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
        
        // no use-templates, so nothing inherited
        checkTemplates(new String[] { "bar" }, map);
    }

    public void testDerivedInherit1() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen0_derived1");
        assertNotNull(base);
        
        IComponentSourceGen sourcegen = getSourceGen(base);

        assertNotNull(sourcegen);
        assertEquals(0, messages.size());

        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        // use-template "foo" used BEFORE
        checkTemplates(new String[] { "foo", "bar" }, map);
        
        ISourceGenTemplate t = (ISourceGenTemplate) map.get("foo");
        assertNotNull(t);
        assertEquals("foo", t.getId());
        assertNull(t.getGroup());
        assertEquals("generate"+SEPARATOR+"foo", t.getFunction());
        assertEquals("", t.getForms());

        ISourceGenTemplate t2 = (ISourceGenTemplate) map.get("bar");
        assertNotNull(t2);
        assertEquals("bar", t2.getId());
        assertNull(t2.getGroup());
        assertEquals("generate"+SEPARATOR+"bar", t2.getFunction());
        assertEquals("", t2.getForms());

    }

    public void testDerivedInheritAll() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen0_derived2");
        assertNotNull(base);
        
        IComponentSourceGen sourcegen = getSourceGen(base);

        assertNotNull(sourcegen);
        assertEquals(0, messages.size());

        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        // use-template "*" used AFTER
        checkTemplates(new String[] { "bar", "foo", "rap" }, map);
    }

    public void testDerivedInheritList() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen0_derived3");
        assertNotNull(base);
        
        IComponentSourceGen sourcegen = getSourceGen(base);

        assertNotNull(sourcegen);
        assertEquals(0, messages.size());
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        // use-template "rap,foo" used AFTER
        checkTemplates(new String[] { "bar", "rap", "foo" }, map);
    }

    public void testDerivedInheritTwice() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen0_derived4");
        assertNotNull(base);
        
        IComponentSourceGen sourcegen = getSourceGen(base);

        assertNotNull(sourcegen);
        assertEquals(0, messages.size());

        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        // use-template "rap" used BEFORE and "foo" AFTER
        checkTemplates(new String[] { "rap", "bar", "foo" }, map);
    }

    public void testDerivedOverride() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen0_derived5");
        assertNotNull(base);
        
        IComponentSourceGen sourcegen = getSourceGen(base);

        assertNotNull(sourcegen);
        assertEquals(0, messages.size());

        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        // redefines rap
        checkTemplates(new String[] { "foo", "rap" }, map);
        
        ISourceGenTemplate t2 = (ISourceGenTemplate) map.get("rap");
        assertNotNull(t2);
        assertEquals("rap", t2.getId());
        assertNull(t2.getGroup());
        assertEquals("generate"+SEPARATOR+"rap", t2.getFunction());
        assertEquals("", t2.getForms());
        
    }

    // make sure you can inherit templates from base of base
    public void testDerivedAgain() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen0_derived_again");
        assertNotNull(base);
        
        IComponentSourceGen sourcegen = getSourceGen(base);

        assertNotNull(sourcegen);
        assertEquals(0, messages.size());

        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        checkTemplates(new String[] { "lambast","bar", "foo", "rap" }, map);
    }

    public void testFormsBasic() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen1");
        assertNotNull(base);
        
        IComponentSourceGen sourcegen = getSourceGen(base);

        assertNotNull(sourcegen);
        assertEquals(0, messages.size());

        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
  
        checkTemplates(new String[] { "foo#alt", "foo#default", "bar", "bar#alt,default" }, map);
   
        ISourceGenTemplate t;
        
        t = (ISourceGenTemplate) map.get("foo#alt");
        assertNotNull(t);
        assertEquals("foo", t.getId());
        assertNull(t.getGroup());
        assertEquals("generate"+SEPARATOR+"foo"+SEPARATOR+SEPARATOR+"alt", t.getFunction());
        assertEquals("alt", t.getForms());

        t = (ISourceGenTemplate) map.get("foo#default");
        assertNotNull(t);
        assertEquals("foo", t.getId());
        assertNull(t.getGroup());
       assertEquals("generate"+SEPARATOR+"foo"+SEPARATOR+SEPARATOR+"default", t.getFunction());
        assertEquals("default", t.getForms());

        t = (ISourceGenTemplate) map.get("foo");
        assertNull(t);
        
        t = (ISourceGenTemplate) map.get("bar");
        assertNotNull(t);
        assertEquals("bar", t.getId());
        assertNull(t.getGroup());
        assertEquals("generate"+SEPARATOR+"bar", t.getFunction());
        assertEquals("",  t.getForms());

        t = (ISourceGenTemplate) map.get("bar#alt,default");
        assertNotNull(t);
        assertEquals("bar", t.getId());
        assertNull(t.getGroup());
        assertEquals("generate"+SEPARATOR+"bar"+SEPARATOR+SEPARATOR+"alt"+SEPARATOR+"default", t.getFunction());
        assertEquals("alt,default",  t.getForms());

    }

    public void testFormsDerived0() throws Exception {
        // inherits nothing
        derivedTest(set, "com.nokia.examples.srcgen1_derived0", 
                new String[] { "bar" });

        // inherits all from base
        derivedTest(set, "com.nokia.examples.srcgen1_derived1",
                new String[] { "bar", "foo#alt", "foo#default" });

        // defines its own form that does not intersect the base ids
        derivedTest(set, "com.nokia.examples.srcgen1_derived2",
                new String[] { "foo#alt", "foo#default", "bar", "bar#alt,default", "zap#local" });

        // defines its own form which also makes a "foo"
        derivedTest(set, "com.nokia.examples.srcgen1_derived3",
                new String[] { "foo#local", "foo#alt", "foo#default", "bar", "bar#alt,default" });
    }


    public void testBadComponents() throws Exception {
        testMessages(set, "com.nokia.examples.srcgen_bad1",
                new String[] { "TemplateIdInUse" });
        testMessages(set, "com.nokia.examples.srcgen_bad2",
                new String[] { "TemplateGroupIdInUse" });
        testMessages(set, "com.nokia.examples.srcgen_bad3",
                new String[] { "UseTemplateWithoutBase" });
        // not an error
        //testMessages(set, "com.nokia.examples.srcgen_bad5",
        //        new String[] { "IfEventNotMatched" });
        
        testMessages(set, "com.nokia.examples.srcgen1_derived0_bad3",
                new String[] { "UseTemplateBadId" });
        testMessages(set, "com.nokia.examples.srcgen1_derived0_bad4",
                new String[] { "TemplateIdInUse" });
        testMessages(set, "com.nokia.examples.srcgen1_derived0_bad5",
                new String[] { "UseTemplateReuse" });
        
    }
    
    public void testSample() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.complex");
        assertNotNull(base);
        
        IComponentSourceGen sourcegen = getSourceGen(base);
        
        // prototype should look like this, based on component ID
        assertEquals("com"+SEPARATOR+"nokia"+SEPARATOR+"examples"+SEPARATOR+"complex", sourcegen.getSourceGenInfo().getPrototype());

        checkTemplates(new String[] { "foo#CEikDialog", "foo#default", "pol" } , sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap());
    }
    

    /** Code inside inline blocks would have inconsistent escaping applied
     * based on whether it had an id='...' block 
     * @throws Exception
     */
    public void testInlineEscapes() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen5");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
        checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
        Map gmap = sourcegen.getSourceGenInfo().getTemplateGroupIdToSourceGenTemplateGroupMap();
        assertNotNull(gmap);
        
        // scan source and ensure all the forms are emitted the same
        String js = getSourceGenInfoScript(set, "com.nokia.examples.srcgen5");
        String[] lines = js.split("\r?\n");
        Pattern p = Pattern.compile("[ab]=\"(1|\\\\\"2\\\\\")\\\\n\";");
        int count = 0;
        for (String line : lines) {
        	if (p.matcher(line.trim()).matches())
        		count++;
        }
        assertEquals(12, count);
    }     

}
