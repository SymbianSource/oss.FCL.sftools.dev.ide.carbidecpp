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
package com.nokia.sdt.symbian.ui.valueConverter;

import com.nokia.sdt.component.property.ICompoundPropertyValueConverter;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.sdt.symbian.ui.editors.Range;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.ParseException;

public class RangeValueConverter implements ICompoundPropertyValueConverter {

	public Object getEditableValue(EObject object, IPropertySource ps) {
		Integer min, max;
		String separator;
		min = (Integer) ps.getPropertyValue("minimum"); //$NON-NLS-1$
		max = (Integer) ps.getPropertyValue("maximum"); //$NON-NLS-1$
		separator = (String) ps.getPropertyValue("separatorText"); //$NON-NLS-1$

		return new Range(min.intValue(), max.intValue(), separator);
	}

	public void applyEditableValue(EObject object, Object editableValue,
			IPropertySource ps) {
		Range range = null;
		if (editableValue instanceof Range) {
			range = (Range) editableValue;
		}
		else if (editableValue != null) {
			try {
				range = Range.parseRange(editableValue.toString());
			}
			catch (ParseException x) {
				UIPlugin.log(x);
			}
		}
		if (range == null) {
			throw new IllegalArgumentException();
		}
		
		ps.setPropertyValue("minimum", range.minimum); //$NON-NLS-1$
		ps.setPropertyValue("maximum", range.maximum); //$NON-NLS-1$
		ps.setPropertyValue("separatorText", range.separatorText); //$NON-NLS-1$
	}

}
