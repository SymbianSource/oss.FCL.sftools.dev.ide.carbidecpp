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
import com.nokia.sdt.symbian.ui.editors.TimeAndDateEditorFactory;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.ParseException;
import java.util.*;

public class TimeAndDateValueConverter implements ICompoundPropertyValueConverter {

	public Object getEditableValue(EObject object, IPropertySource ps) {
		Integer hour = (Integer) ps.getPropertyValue("hour"); //$NON-NLS-1$
		Integer min = (Integer) ps.getPropertyValue("minute"); //$NON-NLS-1$
		Integer sec = (Integer) ps.getPropertyValue("second"); //$NON-NLS-1$
		
		int month = Integer.parseInt((String) ps.getPropertyValue("month")); //$NON-NLS-1$
		int day = (Integer) ps.getPropertyValue("day"); //$NON-NLS-1$
		Integer year = (Integer) ps.getPropertyValue("year"); //$NON-NLS-1$
		
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, hour.intValue());
		cal.set(Calendar.MINUTE, min.intValue());
		cal.set(Calendar.SECOND, sec.intValue());
		cal.set(Calendar.MONTH, Calendar.JANUARY + month);
		cal.set(Calendar.DAY_OF_MONTH, day + 1);
		cal.set(Calendar.YEAR, year.intValue());
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
				date = parseTimeAndDate(editableValue.toString());
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
		ps.setPropertyValue("month", Integer.toString(cal.get(Calendar.MONTH) - Calendar.JANUARY)); //$NON-NLS-1$
		int day = cal.get(Calendar.DAY_OF_MONTH);
		ps.setPropertyValue("day", day - 1); //$NON-NLS-1$
		ps.setPropertyValue("year", cal.get(Calendar.YEAR)); //$NON-NLS-1$
	}

	static private Date parseTimeAndDate(String time) throws ParseException {
		return TimeAndDateEditorFactory.parseTimeAndDate(time);
	}
}
