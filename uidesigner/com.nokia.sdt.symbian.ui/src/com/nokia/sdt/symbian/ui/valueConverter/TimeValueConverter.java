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
import com.nokia.sdt.symbian.ui.editors.TimeEditorFactory;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.ParseException;
import java.util.*;

public class TimeValueConverter implements ICompoundPropertyValueConverter {

	public Object getEditableValue(EObject object, IPropertySource ps) {
		Integer hour, min, sec;
		hour = (Integer) ps.getPropertyValue("hour"); //$NON-NLS-1$
		min = (Integer) ps.getPropertyValue("minute"); //$NON-NLS-1$
		sec = (Integer) ps.getPropertyValue("second"); //$NON-NLS-1$
		
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, hour.intValue());
		cal.set(Calendar.MINUTE, min.intValue());
		cal.set(Calendar.SECOND, sec.intValue());
		return cal.getTime();
	}

	public void applyEditableValue(EObject object, Object editableValue,
			IPropertySource ps) {
		Calendar cal = new GregorianCalendar();
		Date date = null;
		if (editableValue instanceof Date) {
			date = (Date) editableValue;
		}
		else if (editableValue != null) {
			try {
				date = parseTime(editableValue.toString());
			}
			catch (ParseException x) {
				UIPlugin.log(x);
			}
		}
		if (date == null) {
			throw new IllegalArgumentException();
		}
		cal.setTime(date);
		ps.setPropertyValue("hour", cal.get(Calendar.HOUR_OF_DAY)); //$NON-NLS-1$
		ps.setPropertyValue("minute", cal.get(Calendar.MINUTE)); //$NON-NLS-1$
		ps.setPropertyValue("second", cal.get(Calendar.SECOND)); //$NON-NLS-1$
	}

	static private Date parseTime(String time) throws ParseException {
		return TimeEditorFactory.parseTime(time);
	}

}
