/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.templatewizard.symbian.tests;

import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.Path;

import java.util.*;

import junit.framework.TestCase;

/**
 * Test the com.nokia.sdt.utils.VariableSubstitutionEngine class
 *
 */
public class TestVariableSubstitutionEngine extends TestCase {

	private MsgListener msgListener;
	private MessageLocation location;
	
	static class MsgListener implements IMessageListener {

		public List<IMessage> messages = new ArrayList();

		public boolean isHandlingMessage(IMessage msg) {
			return true;
		}
		public void emitMessage(IMessage msg) {
			messages.add(msg);
		}
		
	};

	protected void setUp() throws Exception {
		super.setUp();
		msgListener = new MsgListener(); 
		location = new MessageLocation(new Path("sample.txt"));
	}

	public void testNone() throws Exception {
		VariableSubstitutionEngine eng = new VariableSubstitutionEngine(
				msgListener,
				location);
		Map variables = new HashMap();
		
		variables.put("var1", "value1");
		
		String result = eng.substitute(variables, "This is me speaking.");
		assertTrue(msgListener.messages.size() == 0);
		assertEquals("This is me speaking.", result);
	}


	public void testSimple() throws Exception {
		VariableSubstitutionEngine eng = new VariableSubstitutionEngine(
				msgListener,
				location);
		Map variables = new HashMap();
		
		variables.put("var1", "value1");
		
		String result = eng.substitute(variables, "This is ${var1} speaking.");
		assertTrue(msgListener.messages.size() == 0);
		assertEquals("This is value1 speaking.", result);
	}

	public void testSimple2() throws Exception {
		VariableSubstitutionEngine eng = new VariableSubstitutionEngine(
				msgListener,
				location);
		eng.setVariableToken('(');
		Map variables = new HashMap();
		
		variables.put("var1", "value1");
		
		String result = eng.substitute(variables, "This is $(var1) speaking.");
		assertTrue(msgListener.messages.size() == 0);
		assertEquals("This is value1 speaking.", result);
	}

	public void testFull() throws Exception {
		VariableSubstitutionEngine eng = new VariableSubstitutionEngine(
				msgListener,
				location);
		Map variables = new HashMap();
		
		variables.put("var1", "value1");
		
		String result = eng.substitute(variables, "${var1}");
		assertTrue(msgListener.messages.size() == 0);
		assertEquals("value1", result);
	}

	public void testTwice() throws Exception {
		VariableSubstitutionEngine eng = new VariableSubstitutionEngine(
				msgListener,
				location);
		Map variables = new HashMap();
		
		variables.put("var1", "value1");
		variables.put("v2", "\"wow\"");
		
		String result = eng.substitute(variables, "This is ${var1} speaking, ${v2}.");
		assertTrue(msgListener.messages.size() == 0);
		assertEquals("This is value1 speaking, \"wow\".", result);
	}

	public void testTwice2() throws Exception {
		VariableSubstitutionEngine eng = new VariableSubstitutionEngine(
				msgListener,
				location);
		Map variables = new HashMap();
		
		variables.put("var1", "A Really Long Replacement.");
		
		String result = eng.substitute(variables, "function(${var1}::${var1})");
		assertTrue(msgListener.messages.size() == 0);
		assertEquals("function(A Really Long Replacement.::A Really Long Replacement.)", result);
	}

	public void testEscape() throws Exception {
		VariableSubstitutionEngine eng = new VariableSubstitutionEngine(
				msgListener,
				location);
		Map variables = new HashMap();
		
		variables.put("var1", "c:\\foo\\bar");
		
		String result = eng.substitute(variables, "Place the file in ${var1}");
		assertTrue(msgListener.messages.size() == 0);
		assertEquals("Place the file in c:\\foo\\bar", result);
	}

	public void testEscape2() throws Exception {
		VariableSubstitutionEngine eng = new VariableSubstitutionEngine(
				msgListener,
				location);
		eng.setVariableToken('(');
		Map variables = new HashMap();
		
		variables.put("var1", "c:\\foo\\bar");
		
		String result = eng.substitute(variables, "Place the ${file} in $(var1)");
		assertTrue(msgListener.messages.size() == 0);
		assertEquals("Place the ${file} in c:\\foo\\bar", result);
	}

	public void testNonRecurse() throws Exception {
		VariableSubstitutionEngine eng = new VariableSubstitutionEngine(
				msgListener,
				location);
		Map variables = new HashMap();
		
		variables.put("var1", "var2");
		variables.put("var2", "value1");
		
		String result = eng.substitute(variables, "This is ${var1} speaking.");
		assertTrue(msgListener.messages.size() == 0);
		assertEquals("This is var2 speaking.", result);
	}

	public void testError() throws Exception {
		VariableSubstitutionEngine eng = new VariableSubstitutionEngine(
				msgListener,
				location);
		Map variables = new HashMap();
		
		variables.put("var1", "333");
		
		String result = eng.substitute(variables, "This is ${something} speaking.");
		assertTrue(msgListener.messages.size() == 1);
		assertTrue(msgListener.messages.get(0).getMessageKey().endsWith("UnknownVariable"));
		assertEquals("This is ${something} speaking.", result);
	}
	
	/**
	 * Recursive substitution 
	 * @throws Exception
	 */
	 
	public void testRecursion() throws Exception {
		VariableSubstitutionEngine eng = new VariableSubstitutionEngine(
				msgListener,
				location);
		Map variables = new HashMap();
		
		variables.put("var1", "${var2}");
		variables.put("var2", "value1");
		
		eng.allowRecursion(true);
		String result = eng.substitute(variables, "This is ${var1} speaking.");
		assertEquals("This is value1 speaking.", result);
	}

	/**
	 * Recursive substitution 
	 * @throws Exception
	 */
	 
	public void testRecursion2() throws Exception {
		VariableSubstitutionEngine eng = new VariableSubstitutionEngine(
				msgListener,
				location);
		Map variables = new HashMap();
		
		variables.put("var1", "or ${var2} now");
		variables.put("var2", "value1");
		
		eng.allowRecursion(true);
		String result = eng.substitute(variables, "This is ${var1} speaking.");
		assertEquals("This is or value1 now speaking.", result);
	}

	/**
	 * Recursive substitution not supported.
	 * @throws Exception
	 */
	 
	public void testRecurseError() throws Exception {
		VariableSubstitutionEngine eng = new VariableSubstitutionEngine(
				msgListener,
				location);
		Map variables = new HashMap();
		
		variables.put("var1", "${var2}");
		variables.put("var2", "value1");
		
		//eng.allowRecursion(false);	// the default
		String result = eng.substitute(variables, "This is ${var1} speaking.");
		assertEquals(1, msgListener.messages.size());
		assertTrue(msgListener.messages.get(0).getMessageKey().endsWith("InvalidSubstitution"));
		assertEquals("This is ${var1} speaking.", result);
	}

	public void testRecurseError2() throws Exception {
		VariableSubstitutionEngine eng = new VariableSubstitutionEngine(
				msgListener,
				location);
		Map variables = new HashMap();
		
		variables.put("var1", "or ${var2} now");
		variables.put("var2", "value1");
		
		//eng.allowRecursion(false);	// the default
		String result = eng.substitute(variables, "This is ${var1} speaking.");
		assertEquals(1, msgListener.messages.size());
		assertTrue(msgListener.messages.get(0).getMessageKey().endsWith("InvalidSubstitution"));
		assertEquals("This is ${var1} speaking.", result);
	}
	
	public void testUnsatisfiedVariables() throws Exception {
		VariableSubstitutionEngine eng = new VariableSubstitutionEngine(
				msgListener,
				location);
		Map variables = new HashMap();
		
		variables.put("var1", "value1");
		variables.put("var2", "value2");
		
		String result = eng.substitute(variables, 
				"Pre-variables text ${var1} plus some more text before the unstatisfied " +
				"variabled ${UNSTATISFIED1} and ${ANOTHER} and then some more text and " +
				" then another use of ${var1} and ${var2} and som more text");
		
		assertEquals("Pre-variables text value1 plus some more text before the unstatisfied " +
				"variabled ${UNSTATISFIED1} and ${ANOTHER} and then some more text and " +
				" then another use of value1 and value2 and som more text", result);
	}

	public void testEmptyVariables() throws Exception {

		VariableSubstitutionEngine eng = new VariableSubstitutionEngine(
				msgListener,
				location);
		Map variables = new HashMap();
		
		variables.put("var1", "");
		
		String result = eng.substitute(variables, "This is ${var1} speaking.");
		assertEquals("This is  speaking.", result);

		result = eng.substitute(variables, "${var1}");
		assertEquals("", result);
		
		result = eng.substitute(variables, "");
		assertEquals("", result);
	}
}
