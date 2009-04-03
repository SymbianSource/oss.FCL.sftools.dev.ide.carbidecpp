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

import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.IMessageListener;
import com.nokia.sdt.component.*;
import com.nokia.sdt.component.symbian.sourcegen.SourceGenAdapterScript;
import com.nokia.sdt.component.symbian.sourcegen.SourceGenXMLParser;
import com.nokia.sdt.component.symbian.test.PluginTest;
import com.nokia.sdt.scripting.ScriptException;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.testsupport.AdapterHelpers;
import com.nokia.sdt.testsupport.TestHelpers;
import com.nokia.sdt.utils.*;

import org.eclipse.emf.ecore.EObject;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

/**
 * Base class for testing sourcegen stuff.
 * 
 * NOTE: for the moment, this covers only the XML parsing aspect (<srcgen>,
 * inheritance, error messages, et al). Actual generation of source will likely
 * be implemented and tested in com.nokia.sdt.sourcegen{,.tests}.
 * 
 * 
 * 
 */
public abstract class SourceGenTestBase extends TestCase {

    public static final String SEPARATOR = SourceGenXMLParser.SEPARATOR;
    
    protected IMessageListener msgHandler;
    protected List messages;
    protected boolean EMIT_MESSAGES;

    protected void setUp() throws Exception {
        super.setUp();
        
        EMIT_MESSAGES = false;
        
        messages = new ArrayList();
        msgHandler = new IMessageListener() {
			public boolean isHandlingMessage(IMessage msg) {
				return true;
			}

            public void emitMessage(IMessage msg) {
                messages.add(msg);
            }
        };
        MessageReporting.addListener(msgHandler);
        
        TestHelpers.setPlugin(PluginTest.getDefault());
    }

    protected void tearDown() throws Exception {
        MessageReporting.removeListener(msgHandler);
    }


    IComponentSourceGen getSourceGen(EObject object) {
        IComponent component = AdapterHelpers.getComponent(object);
        assertNotNull(component);
        return (IComponentSourceGen) component
                .getAdapter(IComponentSourceGen.class);
    }

    IComponentSourceGen getSourceGen(IComponent component) {
        assertNotNull(component);
        IComponentSourceGen sg = (IComponentSourceGen) component
                .getAdapter(IComponentSourceGen.class);
        return sg;
    }

    /**
     * Verify that only the given templates appear in the map. White-box
     * knowledge: the ids are in the form "FORM.ID" if the template has a given
     * form, else "ID".
     * 
     * @param ids
     *            the list and order of form+ids to match
     * @param map
     *            the template map
     */
    public void checkTemplates(String[] ids, Map map) {
        StringBuffer buf = new StringBuffer();

        // check existence
        for (int i = 0; i < ids.length; i++) {
            if (!map.containsKey(ids[i]))
                buf.append("missing id: " + ids[i] + "\n");
            else {
                matchTemplate(ids[i], (ISourceGenTemplate)map.get(ids[i]), buf);
            }
        }

        // verify order
        if (buf.length() == 0) {
            if (map.keySet().size() != ids.length)
                buf.append("map does not contain same number of templates as expected: "
                     + ids.length + " != " + map.keySet().size()+"\n");

            int idx = 0;
            for (Iterator iter = map.keySet().iterator(); idx < ids.length
                    && iter.hasNext();) {
                String id = (String) iter.next();
                if (!ids[idx].equals(id))
                    buf.append("index "+idx+" is not "+ids[idx]+" but "+id+"\n");
                idx++;
            }
        }

        if (buf.length() > 0)
            fail(buf.toString());
    }
    protected void matchTemplate(String mid, ISourceGenTemplate t, StringBuffer buf) {
        assertNotNull(t);

        String id;
        String form;
        String group;

        Pattern p = Pattern.compile("((.*)\\.)?(.*?)(#(.*))?");
        Matcher m = p.matcher(mid);
        assertTrue(m.matches());
        group = m.group(2);
        id = m.group(3);
        form = m.group(5);

        if (!id.equals(t.getId())
                || (group == null && t.getGroup() != null)
                || (group != null && t.getGroup() == null)
                || (group != null && t.getGroup() != null 
                        && !group.equals(t.getGroup()))
                || (form == null && t.getForms() != null && !t.getForms().equals(""))
                || (form != null && t.getForms() == null)
                || (form != null && t.getForms() != null 
                        && !form.equals(t.getForms()))
           )
            buf.append("expected " + mid + " but found " + t.getId()
                    + ", group " + t.getGroup()
                    + ", forms " + t.getForms() + "\n");
    }

    /**
     * @param keys
     *            message keys expected (no order implied)
     */
    public void testMessages(IComponentSet set, String componentId,
            String[] keys) {

        messages.clear();
        IComponent base = set.lookupComponent(componentId);
        assertNotNull(base);

        getSourceGen(base);
        
        checkMessages(keys);
    }
    public void checkMessages(String[] keys) {
    	StringBuffer buf = new StringBuffer();
        IMessage[] problems = (IMessage[]) messages.toArray(new IMessage[messages.size()]);
        if (keys.length != problems.length)
            buf.append("message count differs: " + keys.length + " != "
                    + problems.length + "\n");

        for (int i = 0; i < keys.length; i++) {
            assertMsg(keys[i], buf);
        }
        for (int i = 0; i < problems.length; i++) {
            boolean found = false;
            for (int j = 0; j < keys.length; j++) {
                if (keyMatches(keys[j], problems[i].getMessageKey())) {
                    if (problems[i].getMessage().indexOf('{') != -1)
                        buf.append("message seems to have bad formatting: " + problems[i].getMessage() + "\n");
                    found = true;
                    break;
                }
            }
            if (!found)
                buf.append("unexpected error reported: "
                        + problems[i].getMessageKey() + ":" + problems[i].getMessage() + "\n");
        }
        if (buf.length() != 0)
            fail(buf.toString());
    }

    /**
     * Ensure the given message key is one of the reported problems
     * 
     * @param key
     * @param buf
     *            reporting errors
     */
    public void assertMsg(String key, StringBuffer buf) {
        for (Iterator iter = messages.iterator(); iter.hasNext();) {
            IMessage msg = (IMessage) iter.next();
            if (keyMatches(key, msg.getMessageKey())) {
                if (EMIT_MESSAGES)
                    System.out.println("got message: " + msg.getMessage());
                return;
            }
        }
        buf.append("message " + key + " not reported\n");
    }

    public boolean keyMatches(String key, String msgKey) {
        return msgKey.equals(key) || msgKey.endsWith("." + key);
    }

    public void derivedTest(IComponentSet set, String componentId,
            String[] expected) throws Exception {
        
        messages.clear();
        IComponent base = set.lookupComponent(componentId);
        assertNotNull(base);

        IComponentSourceGen sourcegen = getSourceGen(base);

        if (messages.size() != 0) {
            StringBuffer buf = new StringBuffer();
            for (Iterator iter = messages.iterator(); iter.hasNext();) {
                IMessage msg = (IMessage) iter.next();
                buf.append("unexpected message: " + msg);
            }
            fail(buf.toString());
            
        }

        Map map = sourcegen.getSourceGenInfo().getTemplateIdToSourceGenTemplateMap();
        assertNotNull(map);

        checkTemplates(expected, map);
    }

    protected IComponentSourceGenInfo getSourceGenInfo(IComponent base) {
        IComponentSourceGen sourcegen = getAndCheckSourcegen(base);

        return sourcegen.getSourceGenInfo();
    }

	/**
	 * @param base
	 * @return
	 */
	private IComponentSourceGen getAndCheckSourcegen(IComponent base) {
		IComponentSourceGen sourcegen = getSourceGen(base);
		// not sure what this is testing...
		assertEquals(base, sourcegen.getComponent());
        //assertEquals(base.getId(), sourcegen.getComponent().getId());

        assertNotNull(sourcegen);
        if (messages.size() != 0) {
            StringBuffer buf = new StringBuffer();
            for (Iterator iter = messages.iterator(); iter.hasNext();) {
                IMessage msg = (IMessage) iter.next();
                buf.append("unexpected message: " + msg + "\n");
            }
            fail(buf.toString());
        }
		return sourcegen;
	}

 
    public IComponentSourceGenInfo getSourceGenInfo(IComponentSet set, String component) {
        messages.clear();
        IComponent base = set.lookupComponent(component);
        assertNotNull(base);

        return getSourceGenInfo(base);
        
    }
    
    public String getSourceGenInfoScript(IComponentSet set, String component) throws ScriptException {
        messages.clear();
        IComponent base = set.lookupComponent(component);
        assertNotNull(base);

       	IComponentSourceGen sourcegen = getAndCheckSourcegen(base);
       	return ((SourceGenAdapterScript) sourcegen).getJavascript();
    	
    }
}
