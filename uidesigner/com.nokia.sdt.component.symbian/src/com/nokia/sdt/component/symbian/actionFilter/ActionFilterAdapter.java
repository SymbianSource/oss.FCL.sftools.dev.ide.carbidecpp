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


package com.nokia.sdt.component.symbian.actionFilter;

import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NodePathLookupResult;
import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ClassUtils;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.IActionFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implements a filter for actions enabled on context menus.
 * <p>
 * This adapter handles the "(name, value)" filter for an objectContribution
 * by treating the "name" as a function on a list of node path expressions 
 * to match against "value".  
 * <p>
 * "name" is in the form "&lt;selector&gt;:&lt;path&gt;[||...]".
 * <p>
 * If &lt;selector&gt; is one of the tokens "property", "attribute",
 * "property-exists", "attribute-exists"
 * &lt;path&gt; is the node path from the current component which 
 * points to either an attribute or a property.
 * "value", then, is the value against which to check.  For "property"
 * and "attribute", the specific value to test is here.  Otherwise,
 * for the "-exists" variants, the value must be "true" or "false".
 * <p>
 * If &lt;selector&gt; is "implements", then "path" is the fully qualified
 * name of an implementation class (in the XML &lt;implementations&gt;
 * list), and "value" is either "true" or "false".
 * <p>
 * If &lt;selector&gt; is "direct-image-editing-enabled", then the colon and path
 * are empty and the "value" is either "true" or "false" depending on whether
 * the IDirectImageEdit interface is implemented AND returns more than one
 * property. 
 * 
 * 
 *
 */
public class ActionFilterAdapter extends AdapterImpl implements IActionFilter {

	private static final String PROPERTY_FUNCTION = "property"; //$NON-NLS-1$
	private static final String PROPERTY_EXISTS_FUNCTION = "property-exists"; //$NON-NLS-1$
	private static final String ATTRIBUTE_FUNCTION = "attribute"; //$NON-NLS-1$
	private static final String ATTRIBUTE_EXISTS_FUNCTION = "attribute-exists"; //$NON-NLS-1$
	private static final String ATTRIBUTE_MATCHES_FUNCTION = "attribute-matches"; //$NON-NLS-1$
	private static final String IMPLEMENTS_FUNCTION = "implements"; //$NON-NLS-1$
	private static final String ACTION_FILTER_TEST_PASSES_FUNCTION = "action-filter-test-passes"; //$NON-NLS-1$

	public ActionFilterAdapter(Notifier target) {
		setTarget(target);
	}

	public boolean testAttribute(Object target, String name, String value) {
		// allow a list of expressions to match
		String[] exprs = name.split("\\|\\|"); //$NON-NLS-1$
		for (int i = 0; i < exprs.length; i++) {
			Pattern pattern = Pattern.compile("([^:]*):?(.*)"); //$NON-NLS-1$
			Matcher matcher = pattern.matcher(exprs[i]);
			Check.checkState(matcher.matches());	// bad syntax (see comments)
			
			String test = matcher.group(1);
			String testValue = matcher.group(2);
			if (test.equals(PROPERTY_FUNCTION) || test.equals(PROPERTY_EXISTS_FUNCTION)) {
				NodePathLookupResult result = ModelUtils.readProperty((EObject) getTarget(), testValue, true);
				if (result.status == null && 
						(test.equals(PROPERTY_EXISTS_FUNCTION) 
								|| value.equals(result.result.toString())))
					return true;
			}
			else if (test.equals(ATTRIBUTE_FUNCTION) || 
							test.equals(ATTRIBUTE_EXISTS_FUNCTION) ||
									test.equals(ATTRIBUTE_MATCHES_FUNCTION)) {
				NodePathLookupResult result = 
					ModelUtils.readProperty((EObject) getTarget(), testValue, true);
				if (result.status == null && 
						(test.equals(ATTRIBUTE_EXISTS_FUNCTION) 
							|| value.equals(result.result.toString())
								|| (test.equals(ATTRIBUTE_MATCHES_FUNCTION) &&
										result.result.toString().matches(value))))
					return true;
			}
			else if (test.equals(IMPLEMENTS_FUNCTION)) {
				Class klass;
				try {
					// find the referenced class
					klass = UIModelPlugin.getDefault().getBundle().loadClass(testValue);
					
					// see if it's implemented
					Object impl = EcoreUtil.getRegisteredAdapter((EObject) getTarget(), klass);
					if (value.equals(Boolean.valueOf(impl != null).toString())) {
						return true;
					}
				} catch (ClassNotFoundException e) {
					ComponentSystemPlugin.log(e);
				}
			}
			else if (test.equals(ACTION_FILTER_TEST_PASSES_FUNCTION)) {
				IActionFilterTest testFunc = (IActionFilterTest) ClassUtils.loadAndCreateInstance(
						ComponentSystemPlugin.getDefault().getBundle(),
						testValue,
						IActionFilterTest.class,
						ComponentSystemPlugin.getDefault(),
						Messages.getString("ActionFilterAdapter.CannotLoadClass"), //$NON-NLS-1$
						new Object[] { testValue });
				if (testFunc != null && testFunc.test(target, (Notifier) getTarget()))
					return true;
			}
		}
		
		return false;
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(IActionFilter.class);
	}

}
