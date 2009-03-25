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
package com.nokia.sdt.datamodel.util;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.uimodel.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;

import java.text.MessageFormat;

	/**
	 * Common code for name support
	 *
	 */
public abstract class NamePropertySupport {

	public static final int MAX_NAME_LENGTH = 64;
	
	public interface INameFilter {
		/**
		 * @param name
		 * @return true if name can be used
		 */
		boolean test(String name);
		
	}
	
	// for support of models with no IDesignerDataModel (test code)
	static int noModelNameCounter = 1;
		/**
		 * Check for legal generic identifier. Rules:<br>
		 * - starts with letter<br>
		 * - contains letters, numbers, '_'<br>
		 * - MAX_NAME_LENGTH or less<br>
         * - only contains ASCII chars<br>
		 * @param name
		 * @return true if accepted
		 */
	public static boolean isLegalName(String name) {
		// Check to see if it's a valid identifier 
		// It must start with a letter, consist entirely
		// of letters, numbers, or underscore, and be
		// MAX_NAME_LENGTH characters or less
        //
        // EJS: also restrict to ASCII, since all known
        // source formats don't support Unicode
		
		if (name == null || name.length() == 0 || name.length() > MAX_NAME_LENGTH)
			return false;
		
		char c = name.charAt(0);
		if (c >= 128 || !Character.isLetter(c))
			return false;
		
		for (int i = 1; i < name.length(); i++) {
			c = name.charAt(i);
			if (c >= 128 || (!Character.isLetterOrDigit(c) && c != '_'))
				return false;
		}
		return true;		
	}
	
	public static String illegalNameMessage(Object name) {
		String format = Messages.getString("NamePropertySupport.1"); //$NON-NLS-1$
		return MessageFormat.format(format, new Object[] {name});
	}

	public static String duplicateNameMessage(Object name) {
		String format = Messages.getString("NamePropertySupport.2"); //$NON-NLS-1$
		return MessageFormat.format(format, new Object[] {name});
	}

	public static String generateDefaultName(IDesignerDataModel model, IComponent component) {
		if (model != null && component != null) {
			IAttributes attr = (IAttributes) component.getAdapter(IAttributes.class);
			boolean forceSuffix = 
				!(attr.getBooleanAttribute(CommonAttributes.IS_EXCLUSIVE_CHILD_LAYOUT_OBJECT, false) &&
					attr.getBooleanAttribute(CommonAttributes.IS_TOP_LEVEL_ONLY_LAYOUT_OBJECT, false));
			return generateNameForModel(model, component, null, forceSuffix);
		}
		else {
			return "node" + noModelNameCounter++;
		}
	}
	
	public static String generateNameForModel(IDesignerDataModel model, IComponent component, 
												String baseName, boolean forceSuffix) {
		return generateNameForModel(model, component, baseName, forceSuffix, null);
	}
	
	public static String generateNameForModel(IDesignerDataModel model, IComponent component, 
				String baseName, boolean forceSuffix, INameFilter filter) {
		Check.checkState(model != null);
		if (baseName == null)
			baseName = model.getInstanceNameRoot(component);
		String goodName = null;
		if (!forceSuffix) {
			if (model.findByNameProperty(baseName) == null) {
				goodName = baseName;
			}
		}
		int i = 1;
		while (goodName == null) {
			String candidate = baseName + Integer.toString(i++);
			EObject test = model.findByNameProperty(candidate);
			if ((test == null) && ((filter == null) || filter.test(candidate))) {
				goodName = candidate;
			}
		}
		return goodName;
	}
}
