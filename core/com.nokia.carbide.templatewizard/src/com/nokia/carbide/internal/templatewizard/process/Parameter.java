/*
* Copyright (c) 2006, 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.internal.templatewizard.process;

import com.nokia.carbide.internal.template.gen.Template.ParameterType;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.process.IParameter;
import com.nokia.cpp.internal.api.utils.core.VariableSubstitutionEngine;

import org.eclipse.emf.ecore.util.FeatureMap;

import java.util.*;

/**
 *
 */
public class Parameter implements IParameter {

	private VariableSubstitutionEngine substitutionEngine;
	private Map<String, String> attributeValues;
	private String parameterName;
	
	/**
	 */
	public Parameter(ParameterType parameterType, ITemplate template) {
		super();
		parameterName = (String) parameterType.getName();
		substitutionEngine = new VariableSubstitutionEngine(null, null);
		substitutionEngine.setVariableToken('(');
		attributeValues = new HashMap();
		FeatureMap attributes = parameterType.getAnyAttribute();
		for (Iterator iter = attributes.iterator(); iter.hasNext();) {
			FeatureMap.Entry entry = (FeatureMap.Entry) iter.next();
			String attributeName = entry.getEStructuralFeature().getName();
			String rawString = (String) entry.getValue();
			String attributeValue = substitutionEngine.substitute((Map) template.getTemplateValues(), rawString);
			attributeValues.put(attributeName, attributeValue);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.templatewizard.process.IParameter#getAttributeValue(java.lang.String)
	 */
	public String getAttributeValue(String name) {
		return attributeValues.get(name);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.templatewizard.process.IParameter#getName()
	 */
	public String getName() {
		return parameterName;
	}

	@Override
	public String toString() {
		return "Parameter(" + parameterName + ": " + attributeValues + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

}
