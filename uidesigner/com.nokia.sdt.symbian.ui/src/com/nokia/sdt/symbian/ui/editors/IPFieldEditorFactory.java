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

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPFieldEditorFactory extends AbstractPropertyEditorFactory {
	
	public ILabelProvider createLabelProvider(EObject object, String propertyId) {
		ILabelProvider result = null;
		try {
			result = new IPFieldLabelProvider(object);
		}
		catch (CoreException x) {
			UIPlugin.log(x);
			throw new RuntimeException(x);
		}
		return result;
	}
	
	public CellEditor createCellEditor(Composite parent, EObject object, String propertyId) {		
		CellEditor result = new IPFieldCellEditor();
		result.setValidator(createCellEditorValidator(object, propertyId));
		result.create(parent);
		return result;
	}
	
	public ICellEditorValidator createCellEditorValidator(EObject obj, String propertyId) {
		return new IPFieldValidator();
	}

	public static class IPField {
		private static final String SEPARATOR = ".";
		private int[] fields;
		private static Pattern pattern;
		
		public IPField() {
			fields = new int[4];
			if (pattern == null)
				pattern = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)"); //$NON-NLS-1$
		}
		
		public int getField(int i) {
			return fields[i];
		}
		
		public String setFromString(String s) {
			Matcher m = pattern.matcher(s);
			if (m.matches()) {
				for (int i = 0; i < fields.length; i++) {
					try {
						int num = Integer.parseInt(m.group(i + 1));
						if (num > 255) {
							String fmt = Messages.getString("IPFieldEditorFactory.ValueOutOfRange"); //$NON-NLS-1$
							throw new NumberFormatException(
									MessageFormat.format(fmt, new Object[] { new Integer(num) }));
						}
						fields[i] = num;
					} 
					catch (NumberFormatException e) {
						String fmt = Messages.getString("IPFieldEditorFactory.FieldValueOutOfRange"); //$NON-NLS-1$
						return MessageFormat.format(fmt, new Object[] { new Integer(i + 1), e.getLocalizedMessage() });
					}
				}
				return null;
			}
			
			String fmt = Messages.getString("IPFieldEditorFactory.ParseError"); //$NON-NLS-1$
			return MessageFormat.format(fmt, new Object[] {s} );
		}
		
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < fields.length; i++) {
				buffer.append(fields[i]);
				if ((i + 1) < fields.length)
					buffer.append(SEPARATOR);
			}
			return buffer.toString();
		}

		public void setFromValues(int[] values) {
			int numFields = Math.min(values.length, fields.length);
			for (int i = 0; i < numFields; i++) {
				fields[i] = values[i];
			}
		}
	}
	
	static public IPField parseIPField(String s) throws ParseException {
		IPField ipf = new IPField();
		String parseError = ipf.setFromString(s);
		if (parseError != null)
			throw new ParseException(parseError, 0);
		
		return ipf;
	}

	static class IPFieldLabelProvider extends LabelProvider {
    	
    	IPFieldLabelProvider(EObject object) throws CoreException {
    	}
    	
		public Image getImage(Object element) {
			return null;
		}

		public String getText(Object element) {
			if (element instanceof IPropertySource) {
				IPropertySource ps = (IPropertySource) element;
				return ps.getEditableValue().toString();
			}

			return element.toString();
		}
	}
    
    static class IPFieldValidator implements ICellEditorValidator {
    	
    	IPFieldValidator() {
    	}

		public String isValid(Object value) {
			if (value instanceof IPField)
				return null;
			
			String str = value.toString();
			
			try {
				parseIPField(str);
				return null;
			} catch (ParseException e) {
				return e.getLocalizedMessage();
			}			
		}
    	
    }
    
    static class IPFieldCellEditor extends TextCellEditor {

		/**
		 * @param result
		 */
		public IPFieldCellEditor() {
		}
		
		/* (non-Javadoc)
    	 * @see org.eclipse.jface.viewers.TextCellEditor#doSetValue(java.lang.Object)
    	 */
    	@Override
    	protected void doSetValue(Object value) {
    		if (value instanceof IPField) {
    			super.doSetValue(((IPField) value).toString());  			
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
    		String str = (String) super.doGetValue();
    		IPField IPField = null;
			try {
				IPField = parseIPField(str);
			} catch (ParseException e) {
				return ""; //$NON-NLS-1$
			}
			return IPField;
    	}
    }

}
