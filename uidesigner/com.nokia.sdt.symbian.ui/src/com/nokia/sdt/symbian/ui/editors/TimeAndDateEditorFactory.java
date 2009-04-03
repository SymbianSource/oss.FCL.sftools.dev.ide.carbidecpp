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
package com.nokia.sdt.symbian.ui.editors;

import com.nokia.sdt.component.property.AbstractPropertyEditorFactory;
import com.nokia.sdt.symbian.ui.UIPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeAndDateEditorFactory extends AbstractPropertyEditorFactory {
	
	private static final Pattern GENERIC_DATE_AND_TIME_PATTERN = 
		Pattern.compile("Y(\\d+)\\s*M(\\d+)\\s*D(\\d+)\\s*H(\\d+)\\s*M(\\d+)\\s*S(\\d+)",
				Pattern.CASE_INSENSITIVE);

	public ILabelProvider createLabelProvider(EObject object, String propertyId) {
		ILabelProvider result = null;
		try {
			result = new TimeAndDateLabelProvider(object);
		}
		catch (CoreException x) {
			UIPlugin.log(x);
			throw new RuntimeException(x);
		}
		return result;
	}
	
	public CellEditor createCellEditor(Composite parent, EObject object, String propertyId) {
		CellEditor result = new TimeAndDateCellEditor();
		result.setValidator(createCellEditorValidator(object, propertyId));
		result.create(parent);
		return result;
	}
	
	public ICellEditorValidator createCellEditorValidator(EObject obj, String propertyId) {
		return new TimeAndDateValidator();
	}

	static public DateFormat getTimeAndDateInstance() {
		return SimpleDateFormat.getDateTimeInstance();
	}

	static public Date parseTimeAndDate(String timeAndDate) throws ParseException {
		// parse various formats in case the user disagrees with the display;
		// order from most complex to simplest so we don't lose data (e.g. seconds)
		int types[] = { DateFormat.FULL, DateFormat.LONG, DateFormat.MEDIUM, DateFormat.SHORT };  
		ParseException last = null;
		for (int i = 0; i < types.length; i++) {
			// no mixes of types, use same type for both time and date
			DateFormat df = SimpleDateFormat.getDateTimeInstance(types[i], types[i]);
			try {
				return df.parse(timeAndDate);
			} catch (ParseException e) {
				last = e;
			}
		}
		
		// try fallback case which is not locale-dependent
		Matcher matcher = GENERIC_DATE_AND_TIME_PATTERN.matcher(timeAndDate);
		if (matcher.matches()) {
			try {
				int year = Integer.parseInt(matcher.group(1));
				int month = Integer.parseInt(matcher.group(2));
				int date = Integer.parseInt(matcher.group(3));
				int hour = Integer.parseInt(matcher.group(4));
				int minute = Integer.parseInt(matcher.group(5));
				int second = Integer.parseInt(matcher.group(6));
				Calendar calendar = Calendar.getInstance();
				calendar.set(year, month - 1, date, hour, minute, second);
				return calendar.getTime();
			} catch (NumberFormatException e) {
				// fail with parse exception
			}
		}
		
		throw last;
	}
			
    static class TimeAndDateLabelProvider extends LabelProvider {
    	
    	TimeAndDateLabelProvider(EObject object) throws CoreException {
    	}
    	
		public Image getImage(Object element) {
			return null;
		}

		public String getText(Object element) {
			String result;
			if (element instanceof IPropertySource) {
				IPropertySource ps = (IPropertySource) element;
				result = ps.getEditableValue().toString();
			}
			else if (element instanceof Date) {
				Date date = (Date) element;
				result = getTimeAndDateInstance().format(date);
			}
			else {
				result = element.toString();
			}
			return result;			
		}
	}
    
    static class TimeAndDateValidator implements ICellEditorValidator {
    	
    	TimeAndDateValidator() {
    	}

		public String isValid(Object value) {
			if (value instanceof Date)
				return null;
			   
			String timeAndDate = value.toString();
			
			try {
				parseTimeAndDate(timeAndDate);
				return null;
			} catch (ParseException e) {
				return e.getLocalizedMessage();
			}			
		}
    	
    }
    
    static class TimeAndDateCellEditor extends TextCellEditor {

		/**
		 * @param result
		 */
		public TimeAndDateCellEditor() {
		}
		
		/* (non-Javadoc)
    	 * @see org.eclipse.jface.viewers.TextCellEditor#doSetValue(java.lang.Object)
    	 */
    	@Override
    	protected void doSetValue(Object value) {
    		if (value instanceof Date) {
    			Date date = (Date) value;
    			String timeAndDate = getTimeAndDateInstance().format(date);
    			super.doSetValue(timeAndDate);
    		}
    		else if (value instanceof String){
    			super.doSetValue(value.toString());
    		}
    		else
    			throw new IllegalArgumentException();
			fireEditorValueChanged(true, true);
    	}

    	/* (non-Javadoc)
    	 * @see org.eclipse.jface.viewers.TextCellEditor#doGetValue()
    	 */
    	@Override
    	protected Object doGetValue() {
    		String timeAndDate = (String) super.doGetValue();
    		Date date = null;
			try {
				date = parseTimeAndDate(timeAndDate);
			} catch (ParseException e) {
				return ""; //$NON-NLS-1$
			}
			return date;
    	}
    }


}
