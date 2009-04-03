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
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.testsupport.ComponentHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This is a test of compiling the templateGroup/useTemplateGroup
 * nodes from XML.
 * 
 *
 */
public class SourceGenTemplateGroupsTest extends SourceGenTestBase {

    static ComponentProvider provider;
    static IComponentSet set;
    
    
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        if (set == null) {
            try {
            	provider = TestDataModelHelpers.findOrCreateProviderForUserComponents("data/srcgen");
                set = ComponentHelpers.queryAllComponents(provider);
            } catch (Exception e) {
                fail();
            }
        }
    }

    /**
     * @param templates
     * @param ids
     */
    private void checkList(List templates, String[] ids) {
        assertNotNull(templates);
        StringBuffer buf = new StringBuffer();
        int idx = 0;
        for (Iterator iter = templates.iterator(); iter.hasNext();) {
            ISourceGenTemplate t = (ISourceGenTemplate) iter.next();
            String str = t.getId();
            if (idx >= ids.length)
                buf.append("more items in group than expected: " + str+"\n");
            else
                matchTemplate(ids[idx], t, buf);
            idx++;
        }
        while (idx < ids.length) {
            buf.append("not enough items in template group: " + ids[idx]);
            idx++;
        }
        if (buf.length() > 0)
            fail(buf.toString());
    }

    public void testBasic() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
        checkMessages(new String[0]);
       
        // prototype should look like this, based on component ID
        assertEquals("com"+SEPARATOR+"nokia"+SEPARATOR+"examples"+SEPARATOR+"srcgen2", sourcegen.getSourceGenInfo().getPrototype());
        
        assertEquals(base, sourcegen.getComponent());

        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        checkTemplates(new String[] { "grp.foo", "rap" }, map);

        ISourceGenTemplate t;

        t = (ISourceGenTemplate) map.get("grp.foo");
        assertNotNull(t);
        assertEquals("foo", t.getId());
        assertEquals("grp", t.getGroup());
        assertEquals("generate"+SEPARATOR+"foo"+SEPARATOR+"grp", t.getFunction());
        assertEquals("",t.getForms());

        t = (ISourceGenTemplate) map.get("rap");
        assertNotNull(t);
        assertEquals("rap", t.getId());
        assertNull(t.getGroup());
        assertEquals("generate"+SEPARATOR+"rap", t.getFunction());
        assertEquals("", t.getForms());

        Map gmap = sourcegen.getSourceGenInfo().getTemplateGroupIdToSourceGenTemplateGroupMap();
        ISourceGenTemplateGroup g;
         
        g = (ISourceGenTemplateGroup) gmap.get("grp");
        assertNotNull(g);
        
        assertEquals("grp", g.getId());
        checkList(g.getTemplates(), new String[] { "grp.foo" });
        
        ///
        
        
    }
    
    public void testDupes() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2b");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        checkTemplates(new String[] { "grp.foo", "foo", "rap" }, map);

        ISourceGenTemplate t;

        t = (ISourceGenTemplate) map.get("grp.foo");
        assertNotNull(t);
        assertEquals("foo", t.getId());
        assertEquals("grp", t.getGroup());
        assertEquals("generate"+SEPARATOR+"foo"+SEPARATOR+"grp", t.getFunction());
        assertEquals("", t.getForms());

        t = (ISourceGenTemplate) map.get("foo");
        assertNotNull(t);
        assertEquals("foo", t.getId());
        assertNull(t.getGroup());
        assertEquals("generate"+SEPARATOR+"foo", t.getFunction());
        assertEquals("", t.getForms());

        t = (ISourceGenTemplate) map.get("rap");
        assertNotNull(t);
        assertEquals("rap", t.getId());
        assertNull(t.getGroup());
        assertEquals("generate"+SEPARATOR+"rap", t.getFunction());
        assertEquals("", t.getForms());

        Map gmap = sourcegen.getSourceGenInfo().getTemplateGroupIdToSourceGenTemplateGroupMap();
        ISourceGenTemplateGroup g;
         
        g = (ISourceGenTemplateGroup) gmap.get("grp");
        assertNotNull(g);
        
        assertEquals("grp", g.getId());
        checkList(g.getTemplates(), new String[] { "grp.foo" });
    }
    
    public void testDupes2() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2c");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        checkTemplates(new String[] { "grp.foo", "grp.far", "foo", "rap", "grp2.foo" }, map);
    }
    
    public void testDerived0() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2c_derived0");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        // no inheritance
        checkTemplates(new String[] { "tag" }, map);
    }

    public void testDerived1() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2c_derived1");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        // template, use-template *
        checkTemplates(new String[] { "tag", "foo", "rap" }, map);
    }
    
    public void testDerived2() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2c_derived2");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        // template, use-template-group *
        checkTemplates(new String[] { "foo", "grp.foo", "grp.far", "grp2.foo" }, map);
    }    
    
    public void testDerived2b() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2c_derived2b");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        // template, use-template-group grp2
        checkTemplates(new String[] { "foo", "grp2.foo" }, map);
    }    
    
    public void testDerived2c() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2c_derived2c");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        // use-template-group grp, template, use-template-group grp2
        checkTemplates(new String[] { "grp.foo", "grp.far", "foo", "grp2.foo" }, map);
    }    
        
    public void testDerived_with_id() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2e_derived");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        // use-template-group grp grp2, template
        checkTemplates(new String[] { "NaviPaneBase.0", "NaviPaneBase.1", "NaviPaneBase.2" , "foo" }, map);
    }    
        
    public void testDerived_again() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2e_derived_again");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        // use-template-group grp grp2, template
        checkTemplates(new String[] { "NaviPaneBase.0", "NaviPaneBase.1", "NaviPaneBase.2" , "foo", "bar" }, map);
    }    
    
    public void testDerived3() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2c_derived3");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        // template, use-template-group grp + use-template *
        checkTemplates(new String[] { "foo", "grp.foo", "grp.far" }, map);
    }

    public void testDerived4() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2c_derived4");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
    
        // use-template-group grp + use-template far, template
        checkTemplates(new String[] { "grp.far", "foo" }, map);
    }      
    
    public void testDerived5() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2c_derived5");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
    
        // template, use-template-group grp + use-template foo, template-group
        checkTemplates(new String[] { "foo", "grp.foo", "pop.foo" }, map);
    }      

    public void testForms0() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2d");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
        Map gmap = sourcegen.getSourceGenInfo().getTemplateGroupIdToSourceGenTemplateGroupMap();
        assertNotNull(gmap);
    
        // two groups, with different numbers of templates,
        // then a named template (same name as a group),
        // then a named template in an unnamed group
        checkTemplates(new String[] { "main.first#CEikDialog", "main.other#CEikDialog", 
                "main.first#default",
                "main", "embedded" }, map);
    }     

    public void testForms1() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2d_forms");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
        Map gmap = sourcegen.getSourceGenInfo().getTemplateGroupIdToSourceGenTemplateGroupMap();
        assertNotNull(gmap);

        // one template supports two forms, but is still only one template
        checkTemplates(new String[] { "main.first#CEikDialog", 
        		"main.other#CEikDialog", 
                "schnozz#First,Second",
                "self.first#Center,Left,Right",
                "self.other#Center,Left,Right" 
        }, map);
    }     
    
    public void testForms2() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2g");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
        Map gmap = sourcegen.getSourceGenInfo().getTemplateGroupIdToSourceGenTemplateGroupMap();
        assertNotNull(gmap);
        
        // there are no ids, so scan source
        String js = getSourceGenInfoScript(set, "com.nokia.examples.srcgen2g");
        Pattern p = Pattern.compile("if \\(formrx.test\\((.*?)\\)");
        Matcher m = p.matcher(js);
        int pos = 0;
        while (m.find(pos)) {
        	String arg = m.group(1);
        	assertFalse(arg.equals("\"\""));
        	pos = m.end();
        }
    }     

    public void testFormsDerived0() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2d_derived0");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
        Map gmap = sourcegen.getSourceGenInfo().getTemplateGroupIdToSourceGenTemplateGroupMap();
        assertNotNull(gmap);

        // no inheritance, one yip
        checkTemplates(new String[] { "yip" }, map);
    }     
    
    public void testFormsDerived1() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2d_derived1");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
        Map gmap = sourcegen.getSourceGenInfo().getTemplateGroupIdToSourceGenTemplateGroupMap();
        assertNotNull(gmap);

        // inherit use-template-group *
        checkTemplates(new String[] { "main.first#CEikDialog", "main.other#CEikDialog", 
                "main.first#default" }, map);
    }          
    
    public void testFormsDerived2() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2d_derived2");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
        Map gmap = sourcegen.getSourceGenInfo().getTemplateGroupIdToSourceGenTemplateGroupMap();
        assertNotNull(gmap);

        // inherit use-template-group main (only group)
        checkTemplates(new String[] { "main.first#CEikDialog", "main.other#CEikDialog", 
                "main.first#default" }, map);
    }          
      
    public void testFormsDerived3() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2d_derived3");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
        Map gmap = sourcegen.getSourceGenInfo().getTemplateGroupIdToSourceGenTemplateGroupMap();
        assertNotNull(gmap);

        // inherit use-template-group main, add new group MyDialog
        checkTemplates(new String[] { "main.first#CEikDialog", "main.other#CEikDialog", 
                "main.first#default",
                "club.action#MyDialog" }, map);
    }          
    
    public void testFormsDerived4() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2d_derived4");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
        Map gmap = sourcegen.getSourceGenInfo().getTemplateGroupIdToSourceGenTemplateGroupMap();
        assertNotNull(gmap);

        // inherit use-template-group main, add new group MyDialog,
        // add a new group main using form MyDialog
        checkTemplates(new String[] { "main.first#CEikDialog", "main.other#CEikDialog", 
                "main.first#default",
                "main.action#MyDialog" }, map);
    }          
 
    public void testFormsDerived5() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2d_derived5");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
        Map gmap = sourcegen.getSourceGenInfo().getTemplateGroupIdToSourceGenTemplateGroupMap();
        assertNotNull(gmap);

        // inherit use-template-group *, use-template *
        checkTemplates(new String[] { "main.first#CEikDialog", "main.other#CEikDialog", 
                "main.first#default", "main", "embedded" }, map);
    }          
    
    public void testDeriveTwice0() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2d_derived5_derived0");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
        Map gmap = sourcegen.getSourceGenInfo().getTemplateGroupIdToSourceGenTemplateGroupMap();
        assertNotNull(gmap);
    
        // inherit nothing
        checkTemplates(new String[] {  }, map);
       
    }
 
    public void testDeriveTwice1() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2d_derived5_derived1");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
        Map gmap = sourcegen.getSourceGenInfo().getTemplateGroupIdToSourceGenTemplateGroupMap();
        assertNotNull(gmap);
    
        // inherit use-template-group *, use-template *
        checkTemplates(new String[] { "main.first#CEikDialog", "main.other#CEikDialog", 
                "main.first#default", "main", "embedded" }, map);
       
    }

    public void testBadComponents() throws Exception {
        /* we fix up bad ids now
        testMessages(set, "com.nokia.examples.bad_srcgen0",
                new String[] { "IllegalId" });
        testMessages(set, "com.nokia.examples.bad_srcgen1",
                new String[] { "IllegalId", "UseTemplateGroupBadId" });
        testMessages(set, "com.nokia.examples.bad_srcgen2",
                new String[] { "IllegalId" });
        testMessages(set, "com.nokia.examples.bad_srcgen3",
                new String[] { "IllegalId" });
*/

        testMessages(set, "com.nokia.examples.srcgen2c_derived2b_bad0",
                new String[] { "UseTemplateGroupReuse" });
        testMessages(set, "com.nokia.examples.srcgen2d_derived1_bad0",
                new String[] { "UseTemplateGroupReuse", "UseTemplateGroupReuse" });
        testMessages(set, "com.nokia.examples.srcgen2d_derived1_bad1",
                new String[] { "UseTemplateGroupBadId" });
        testMessages(set, "com.nokia.examples.srcgen2d_derived3_bad0",
                new String[] { "TemplateGroupIdFormInUse" });
        testMessages(set, "com.nokia.examples.srcgen2d_derived4_bad0",
                new String[] { "TemplateIdInUse" });
        
    }
    
    public void testComplex() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.complex2");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);
        
        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);
        Map gmap = sourcegen.getSourceGenInfo().getTemplateGroupIdToSourceGenTemplateGroupMap();
        assertNotNull(gmap);
    
        // two groups, with different numbers of templates,
        // then two named and one unnamed template
        checkTemplates(new String[] { "grp.foo#flab", "grp.far#flab", 
                "grp.foo#default", "grp.far#default", "grp.fob#default",
                "foo", "rap" }, map);
    }      
    
    public void testEvents() throws Exception {
        IComponent base = set.lookupComponent("com.nokia.examples.srcgen2f");
        assertNotNull(base);
        IComponentSourceGen sourcegen = getSourceGen(base);
        assertNotNull(sourcegen);
                checkMessages(new String[0]);

        Map gmap = sourcegen.getSourceGenInfo().getTemplateGroupIdToSourceGenTemplateGroupMap();
        assertNotNull(gmap);
        ISourceGenTemplateGroup group = (ISourceGenTemplateGroup) gmap.get("NaviPaneBase");
        assertNotNull(group);
        List templates = group.getTemplates();
        for (Iterator iter = templates.iterator(); iter.hasNext();) {
			ISourceGenTemplate template = (ISourceGenTemplate) iter.next();
			List events = template.getIfEvents();
			assertNotNull(events);
			assertEquals("eventChanged", events.get(0));
        }
    	
    }

}
