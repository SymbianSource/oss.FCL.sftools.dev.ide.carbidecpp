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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateEditorFactory extends AbstractPropertyEditorFactory {
	
	private static final Pattern GENERIC_DATE_PATTERN = Pattern.compile("Y(\\d+)\\s*M(\\d+)\\s*D(\\d+)", Pattern.CASE_INSENSITIVE);

	public ILabelProvider createLabelProvider(EObject object, String propertyPath) {
		ILabelProvider result = null;
		try {
			result = new DateLabelProvider(object);
		}
		catch (CoreException x) {
			UIPlugin.log(x);
			throw new RuntimeException(x);
		}
		return result;
	}
	
	public CellEditor createCellEditor(Composite parent, EObject object, String propertyPath) {		
		CellEditor result = new DateCellEditor();
		result.setValidator(createCellEditorValidator(object, propertyPath));
		result.create(parent);
		return result;
	}
	
	public ICellEditorValidator createCellEditorValidator(EObject obj, String propertyId) {
		return new DateValidator();
	}


	static public DateFormat getDateInstance() {
		return SimpleDateFormat.getDateInstance(DateFormat.MEDIUM);
	}

	static public Date parseDate(String Date) throws ParseException {
		// parse various formats in case the user disagrees with the display;
		// order from most complex to simplest so we don't lose data (e.g. seconds)
		int types[] = { DateFormat.FULL, DateFormat.LONG, DateFormat.MEDIUM, DateFormat.SHORT }; 
		ParseException last = null;
		for (int i = 0; i < types.length; i++) {
			DateFormat df = SimpleDateFormat.getDateInstance(types[i]);
			try {
				return df.parse(Date);
			} catch (ParseException e) {
				last = e;
			}
		}

		// try fallback case which is not locale-dependent
		Matcher matcher = GENERIC_DATE_PATTERN.matcher(Date);
		if (matcher.matches()) {
			try {
				int year = Integer.parseInt(matcher.group(1));
				int month = Integer.parseInt(matcher.group(2));
				int date = Integer.parseInt(matcher.group(3));
				Calendar calendar = Calendar.getInstance();
				calendar.set(year, month - 1, date);
				return calendar.getTime();
			} catch (NumberFormatException e) {
				// fail with parse exception
			}
		}

		throw last;
	}
	/**
	 * 
	 * @param month 0-11
	 * @param day 0-30
	 * @param year xxxx
	 */
	public static String formatDate(int month, int day, int year) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.MONTH, Calendar.JANUARY + month);
		cal.set(Calendar.DAY_OF_MONTH, day + 1);
		cal.set(Calendar.YEAR, year);
		return getDateInstance().format(cal.getTime());
		
	}

	static class DateLabelProvider extends LabelProvider {
    	
    	DateLabelProvider(EObject object) throws CoreException {
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
				result = getDateInstance().format(date);
			}
			else {
				result = element.toString();
			}
			return result;
		}
	}
    
    static class DateValidator implements ICellEditorValidator {
    	
    	DateValidator() {
    	}

		public String isValid(Object value) {
			if (value instanceof Date)
				return null;
			
			String date = value.toString();
			
			try {
				parseDate(date);
				return null;
			} catch (ParseException e) {
				return e.getLocalizedMessage();
			}			
		}
    	
    }
    
    static class DateCellEditor extends TextCellEditor {

		/**
		 * @param result
		 */
		public DateCellEditor() {
		}
		
		/* (non-Javadoc)
    	 * @see org.eclipse.jface.viewers.TextCellEditor#doSetValue(java.lang.Object)
    	 */
    	@Override
    	protected void doSetValue(Object value) {
    		if (value instanceof Date) {
    	 		Date date = (Date) value;
    			String time = getDateInstance().format(date);
    			super.doSetValue(time);  			
    		}
    		else if (value instanceof String) {
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
    		String time = (String) super.doGetValue();
    		Date date = null;
			try {
				date = parseDate(time);
			} catch (ParseException e) {
				return ""; //$NON-NLS-1$
			}
			return date;
    	}
    }

}
