/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.sdt.component.symbian.test;

import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.property.ISequencePropertySource;
import com.nokia.sdt.component.symbian.Component;
import com.nokia.sdt.component.symbian.properties.ArrayEditableValue;
import com.nokia.sdt.component.symbian.properties.ComponentPropertySource;
import com.nokia.sdt.testsupport.ComponentHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.*;

import junit.framework.TestCase;

public class ArrayPropertyTest extends TestCase {
	
	IComponentSet componentSet;
	IPropertySource fixture;


	protected void setUp() throws Exception {
		componentSet = ComponentHelpers.querySDKFilter(
				TestDataModelHelpers.getDefaultComponentProvider(), 
				"com.nokia.series60", "2.0");
		Component testComponent = (Component) componentSet.lookupComponent("com.nokia.test.arrayTestComponent");

		ComponentPropertySource cps = new ComponentPropertySource(
				testComponent,
				new MockPropertyValueSource("test"),
				new MockLocalizedStrings());
		fixture = cps;
	}
	
	public void testIntArray() {
		Object intArray = fixture.getPropertyValue("int-array");
		assertNotNull(intArray);
		assertTrue(intArray instanceof ISequencePropertySource);
		
		ISequencePropertySource sps = (ISequencePropertySource) intArray;
		
		assertTrue(sps.size()==0);
		sps.addSimpleProperty(ISequencePropertySource.AT_END, "100");		
		sps.addSimpleProperty(ISequencePropertySource.AT_END, "101");
		sps.addSimpleProperty(ISequencePropertySource.AT_END, "102");
		
		assertTrue(sps.size() == 3);
		int count = 0;
		for (Iterator iter = sps.iterator(); iter.hasNext();) {
			Object curr = iter.next();
			assertTrue(curr instanceof String);
			++count;
		}
		assertTrue(count == 3);
		
		sps.remove(1);		
		IPropertyDescriptor pds[] = sps.getPropertyDescriptors();
		assertNotNull(pds);
		assertTrue(pds.length == 2);
		assertEquals(pds[0].getId(), "0");
		assertEquals(pds[1].getId(), "1");
		assertEquals(new Integer(100), sps.getPropertyValue("0"));
		assertEquals(new Integer(102), sps.getPropertyValue("1"));
		assertNull(sps.getPropertyValue("xyz"));
	}
	
	public void test1() {
		
		Object stringArray = fixture.getPropertyValue("string-array");
		assertNotNull(stringArray);
		Object nested = fixture.getPropertyValue("nested");
		assertNotNull(nested);
	}
	
	public void testFooArray() {
		Object fooArray = fixture.getPropertyValue("foo-array");
		assertNotNull(fooArray);
		assertTrue(fooArray instanceof ISequencePropertySource);
		
		ISequencePropertySource sps = (ISequencePropertySource) fooArray;
		
		IPropertySource value1 = sps.addCompoundProperty(ISequencePropertySource.AT_END);
		value1.setPropertyValue("f1", "99");
		value1.setPropertyValue("f2", "true");
		
		IPropertySource value2 = sps.addCompoundProperty(ISequencePropertySource.AT_END);
		value2.setPropertyValue("f1", "3");
		assertTrue(sps.size() == 2);
		
		Object v1 = sps.get(0);
		assertTrue(v1 instanceof IPropertySource);
		IPropertySource testV1 = (IPropertySource) v1;
		assertEquals(new Integer(99), testV1.getPropertyValue("f1"));
		assertEquals(Boolean.TRUE, testV1.getPropertyValue("f2"));
		assertNull(testV1.getPropertyValue("fake"));
		
		// access via property source
		Object value = sps.getPropertyValue("0");
		assertNotNull(value);
		assertTrue(value instanceof IPropertySource);
		testV1 = (IPropertySource) value;
		assertEquals(new Integer(99), testV1.getPropertyValue("f1"));
		assertEquals(Boolean.TRUE, testV1.getPropertyValue("f2"));
		assertNull(testV1.getPropertyValue("fake"));
	}
	
	public void testArraySizeChange() {
		Object intArray = fixture.getPropertyValue("int-array");
		ISequencePropertySource sps = (ISequencePropertySource) intArray;
	
		sps.addSimpleProperty(0, "100");
		
		IPropertyDescriptor pd1[] = sps.getPropertyDescriptors();
		assertNotNull(pd1);
		assertTrue(pd1.length == 1);
		
		sps.addSimpleProperty(1, "101");
		IPropertyDescriptor pd2[] = sps.getPropertyDescriptors();
		assertNotNull(pd2);
		assertTrue(pd2.length == 2);
	}
	
	public void testModifyValues() {
		Object intArray = fixture.getPropertyValue("int-array");
		ISequencePropertySource sps = (ISequencePropertySource) intArray;
	
		sps.addSimpleProperty(0, "100");
		sps.addSimpleProperty(1, "101");
		
		sps.setPropertyValue("0", "99");
		assertEquals(new Integer(99), sps.getPropertyValue("0"));
	}

    public void testRefArray() {
        Object refArray = fixture.getPropertyValue("ref-array");
        assertNotNull(refArray);
        assertTrue(refArray instanceof ISequencePropertySource);
        
        ISequencePropertySource sps = (ISequencePropertySource) refArray;
        assertTrue(sps.size()==0);
        sps.addSimpleProperty(0, "ref1");        
        
        assertTrue(sps.size() == 1);

        IPropertyDescriptor pds[] = sps.getPropertyDescriptors();
        assertNotNull(pds);
        assertTrue(pds.length == 1);
        assertEquals(pds[0].getId(), "0");
        assertEquals("ref1", sps.getPropertyValue("0"));
        assertNull(sps.getPropertyValue("xyz"));
    }
    
    public void testEditableValue() {
    	Object lsa = fixture.getPropertyValue("loc-string-array");
    	assertNotNull(lsa);
    	assertTrue(lsa instanceof ISequencePropertySource);
    	ISequencePropertySource sps = (ISequencePropertySource) lsa;
    	
    	sps.addSimpleProperty(0, "one");
    	sps.addSimpleProperty(1, "two");
    	
    	Object ev = sps.getEditableValue();
    	assertTrue(ev instanceof ArrayEditableValue);
    	ArrayEditableValue editValue = (ArrayEditableValue) ev;
    	List list = editValue.getValue().toList();
    	assertTrue(list.contains("one"));
    	
    	list.remove("two");
    	list.add("three");
    	fixture.setPropertyValue("loc-string-array", list);
    	
    	lsa = fixture.getPropertyValue("loc-string-array");
    	sps = (ISequencePropertySource) lsa;
    	list = sps.toList();
    	assertTrue(list.size() == 2);
    	assertTrue(list.contains("one"));
    	assertFalse(list.contains("two"));
    	assertTrue(list.contains("three"));    	
    }
    
    public void testSetLocalizedArray() {
    	List list = new ArrayList();
    	list.add("red");
    	list.add("green");
    	
    	fixture.setPropertyValue("loc-string-array", list);
      	Object lsa = fixture.getPropertyValue("loc-string-array");
    	assertNotNull(lsa);
    	assertTrue(lsa instanceof ISequencePropertySource);
    	ISequencePropertySource sps = (ISequencePropertySource) lsa;
    	
    	List sequence = sps.toList();
    	assertTrue(sequence.size()==2);
    	assertTrue(sequence.contains("red"));
    	assertTrue(sequence.contains("green"));
    	
    	list = new ArrayList();
    	list.add("red");
    	list.add("green");
    	list.add("blue");
      	fixture.setPropertyValue("loc-string-array", list);
    	sequence = sps.toList();
    	assertTrue(sequence.size()==3);
    	assertTrue(sequence.contains("red"));
    	assertTrue(sequence.contains("green"));
      	assertTrue(sequence.contains("blue"));
  
    	list = new ArrayList();
    	list.add("red");
    	fixture.setPropertyValue("loc-string-array", list);
    	sequence = sps.toList();
    	assertTrue(sequence.size()==1);
    	assertTrue(sequence.contains("red"));
    } 
}
