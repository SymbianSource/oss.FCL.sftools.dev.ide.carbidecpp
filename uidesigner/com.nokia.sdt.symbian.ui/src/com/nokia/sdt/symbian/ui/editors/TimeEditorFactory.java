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

public class TimeEditorFactory extends AbstractPropertyEditorFactory {
	
	private static final Pattern GENERIC_TIME_PATTERN = Pattern.compile("H(\\d+)\\s*M(\\d+)\\s*S(\\d+)", Pattern.CASE_INSENSITIVE);

	public ILabelProvider createLabelProvider(EObject object, String propertyId) {
		ILabelProvider result = null;
		try {
			result = new TimeLabelProvider(object);
		}
		catch (CoreException x) {
			UIPlugin.log(x);
			throw new RuntimeException(x);
		}
		return result;
	}
	
	public CellEditor createCellEditor(Composite parent, EObject object, String propertyId) {
		CellEditor result = new TimeCellEditor();
		result.setValidator(createCellEditorValidator(object, propertyId));
		result.create(parent);
		return result;
	}
	
	public ICellEditorValidator createCellEditorValidator(EObject obj, String propertyId) {
		return new TimeValidator();
	}

	static public DateFormat getTimeInstance() {
		return SimpleDateFormat.getTimeInstance();
	}

	static public Date parseTime(String time) throws ParseException {
		// parse various formats in case the user disagrees with the display;
		// order from most complex to simplest so we don't lose data (e.g. seconds)
		int types[] = { DateFormat.FULL,  DateFormat.LONG, DateFormat.MEDIUM, DateFormat.SHORT }; 
		ParseException last = null;
		for (int i = 0; i < types.length; i++) {
			DateFormat df = SimpleDateFormat.getTimeInstance(types[i]);
			try {
				return df.parse(time);
			} catch (ParseException e) {
				last = e;
			}
		}
		
		// try fallback case which is not locale-dependent
		Matcher matcher = GENERIC_TIME_PATTERN.matcher(time);
		if (matcher.matches()) {
			try {
				int hour = Integer.parseInt(matcher.group(1));
				int minute = Integer.parseInt(matcher.group(2));
				int second = Integer.parseInt(matcher.group(3));
				Calendar calendar = Calendar.getInstance();
				calendar.set(2000, 0, 1, hour, minute, second);
				return calendar.getTime();
			} catch (NumberFormatException e) {
				// fail with parse exception
			}
		}
		
		throw last;
	}
			
    static class TimeLabelProvider extends LabelProvider {
    	
    	TimeLabelProvider(EObject object) throws CoreException {
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
				result = getTimeInstance().format(date);
			}
			else {
				result = element.toString();
			}
			return result;			
		}
	}
    
    static class TimeValidator implements ICellEditorValidator {
    	
    	TimeValidator() {
    	}

		public String isValid(Object value) {
			if (value instanceof Date)
				return null;
			   
			String time = value.toString();
			
			try {
				parseTime(time);
				return null;
			} catch (ParseException e) {
				return e.getLocalizedMessage();
			}			
		}
    	
    }
    
    static class TimeCellEditor extends TextCellEditor {

		/**
		 * @param result
		 */
		public TimeCellEditor() {
		}
		
		/* (non-Javadoc)
    	 * @see org.eclipse.jface.viewers.TextCellEditor#doSetValue(java.lang.Object)
    	 */
    	@Override
    	protected void doSetValue(Object value) {
    		if (value instanceof Date) {
    			Date date = (Date) value;
    			String time = getTimeInstance().format(date);
    			super.doSetValue(time);
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
    		String time = (String) super.doGetValue();
    		Date date = null;
			try {
				date = parseTime(time);
			} catch (ParseException e) {
				return ""; //$NON-NLS-1$
			}
			return date;
    	}
    }


}
