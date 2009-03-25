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

package com.nokia.sdt.component.symbian.componentchecker;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.ITypeDescriptor;
import com.nokia.sdt.component.symbian.Component;
import com.nokia.sdt.component.symbian.properties.CompoundPropertyTypeDescriptor;
import com.nokia.sdt.component.symbian.properties.EnumPropertyTypeDescriptor;
import com.nokia.sdt.emf.component.*;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import java.text.MessageFormat;
import java.util.*;

import junit.framework.*;

/**
 * Base class for one test on a component.  This packages several
 * other tests, but does not use TestSuite to save some effort
 * packaing every test in a new class.
 * <p>
 * Note: the JUnit test runner in Eclipse is buggy and does not
 * want more than one error or failure reported per test.  This
 * is why we wrap errors in the "failures" list (we also do this
 * for ComponentChecker to provide convenient import/export).
 * 
 *
 */
public abstract class BaseComponentTest implements Test {

	protected ComponentChecker checker;
	protected IComponent component;
	private TestResult result;
	private List<Report> failures;
	protected ILocalizedStrings componentStrings;

	BaseComponentTest(ComponentChecker checker, IComponent component) {
		this.checker = checker;
		this.component = component;
		this.componentStrings = ((Component)component).getLocalizedStrings();
		this.failures = new ArrayList<Report>();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() +": "+ component.getId()
		  + ": " + ((Component)component).getComponentFile().getName();
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.Test#countTestCases()
	 */
	public int countTestCases() {
		return 1;
	}

	abstract void runTests();
	
	/* (non-Javadoc)
	 * @see junit.framework.Test#run(junit.framework.TestResult)
	 */
	public void run(TestResult result) {
		this.result = result;
		
		result.startTest(this);
		
		try {
			runTests();
			
			reportResults();
		} catch (AssertionFailedError e) {
			result.addFailure(this, e);
		} catch (Throwable t) {
			result.addError(this, t);
		}
		
		
		result.endTest(this);
	}
	
	
	
	protected void check(boolean success, Severity severity, IComponent component, String testName, String message) {
		Report report = new Report(component.getId(), testName, severity, message);
		if (!success) {
			checker.failures.add(report);
			failures.add(report);
		}
		
	}
	
	/**
	 * Report results gathered for this test.
	 * @throws Exception
	 */
	public void reportResults() throws Exception {
		StringBuffer buffer = new StringBuffer();
		if (checker.errorBuffer.length() > 0) {
			buffer.append("*** ERRORS IN COMPONENTS:\n");
			buffer.append(checker.errorBuffer);
			checker.errorBuffer.setLength(0);
			buffer.append("\n");
		}

		if (failures.size() > 0) {
			//buffer.append("*** FAILURES:\n");
			for (Iterator iter = failures.iterator(); iter.hasNext();) {
				Report failure = (Report) iter.next();
				if (!checker.isExpected(failure)) {
					buffer.append(failure);
					buffer.append("\n");
				}
			}
		}
		
		if (buffer.length() > 0) { 
			result.addFailure(this, new AssertionFailedError(buffer.toString()));
		}
		
		
	}
	
	/**
	 * Utility to check that the given key (read from a facet)
	 * is defined (not empty) and that it can be resolved to a string
	 * in the current language, issuing a report if not.
	 * @param key
	 * @param label
	 * @param testName
	 * @param propertyId
	 * @param extensionSetName
	 */
	protected void checkStringKey(String key, String label, String testName, ILocalizedStrings extraStrings, String propertyId, String parentPropertyId, String extensionSetName) {
		String prop = parentPropertyId != null ? parentPropertyId + "." + propertyId : propertyId;
		String fmt = extensionSetName != null ?
				"no {0} on {1} (extension set {2})" :
					"no {0} on {1}";
		check(TextUtils.strlen(key) > 0,
				Severity.ERROR,
				component, testName, 
				MessageFormat.format(fmt, 
						new Object[] { label, prop, extensionSetName }));
		if (key != null) {
			String value = componentStrings.getString(key);
			if (isUndefinedString(key, value) && extraStrings != null) {
				value = extraStrings.getString(key);
			}
			fmt = extensionSetName != null ?
					"missing value for {0}=\"{1}\" on {2} (extension set {3})" :
						"missing value for {0}=\"{1}\" on {2}";
					
			if (isUndefinedString(key, value)) {
				check(false,
						Severity.ERROR,
						component, testName, 
						MessageFormat.format(fmt, 
								new Object[] { label, key, prop, extensionSetName }));
				
			}
		}
		
	}

	/**
	 * @param value
	 * @return
	 */
	private boolean isUndefinedString(String key, String value) {
		return value == null || value.equals("!"+key+"!");
	}

	protected CompoundPropertyTypeDescriptor getCompoundProperties(AbstractPropertyType ap) {
		if (ap instanceof CompoundPropertyType) {
			CompoundPropertyType cpt = (CompoundPropertyType) ap;
			String type = cpt.getType();
			ITypeDescriptor descriptor = component.getComponentSet().lookupTypeDescriptor(type);
			return (CompoundPropertyTypeDescriptor) descriptor;
		} else
			return null;
	}
	
	protected EnumPropertyTypeDescriptor getEnumProperty(AbstractPropertyType ap) {
		if (ap instanceof EnumPropertyType) {
			EnumPropertyType ept = (EnumPropertyType) ap;
			String type = ept.getType();
			ITypeDescriptor descriptor = component.getComponentSet().lookupTypeDescriptor(type);
			return (EnumPropertyTypeDescriptor) descriptor;
		}
		
		return null;
	}
}
