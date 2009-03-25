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
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import java.text.ParseException;

public class RangeEditorFactory extends AbstractPropertyEditorFactory {
	
	public ILabelProvider createLabelProvider(EObject object, String propertyId) {
		ILabelProvider result = null;
		try {
			result = new RangeLabelProvider(object);
		}
		catch (CoreException x) {
			UIPlugin.log(x);
			throw new RuntimeException(x);
		}
		return result;
	}
	
	public CellEditor createCellEditor(Composite parent, EObject object, String propertyId) {
		CellEditor result = new RangeCellEditor();
		result.setValidator(createCellEditorValidator(object, propertyId));
		result.create(parent);
		return result;
	}
	
	public ICellEditorValidator createCellEditorValidator(EObject obj, String propertyId) {
		return new RangeValidator();
	}

	
    static class RangeLabelProvider extends LabelProvider {
    	
    	RangeLabelProvider(EObject object) throws CoreException {
    	}
    	
		public Image getImage(Object element) {
			return null;
		}

		public String getText(Object element) {
			if (element != null)
				return element.toString();
			return null;
		}
	}
    
    static class RangeValidator implements ICellEditorValidator {
    	
    	RangeValidator() {
    	}

		public String isValid(Object value) {
			if (value instanceof Range)
				return null;
			   
			String r = value.toString();
			
			try {
				Range.parseRange(r);
				return null;
			} catch (ParseException e) {
				return e.getLocalizedMessage();
			}			
		}
    	
    }
    
    static class RangeCellEditor extends TextCellEditor {

		/**
		 * @param result
		 */
		public RangeCellEditor() {
		}
		
		/* (non-Javadoc)
    	 * @see org.eclipse.jface.viewers.TextCellEditor#doSetValue(java.lang.Object)
    	 */
    	@Override
    	protected void doSetValue(Object value) {
    		Check.checkArg((value instanceof Range) || (value instanceof String));
			super.doSetValue(value.toString());
			fireEditorValueChanged(true, true);
    	}

    	/* (non-Javadoc)
    	 * @see org.eclipse.jface.viewers.TextCellEditor#doGetValue()
    	 */
    	@Override
    	protected Object doGetValue() {
    		String r = (String) super.doGetValue();
    		Range range = null;
			try {
				range = Range.parseRange(r);
			} catch (ParseException e) {
				return ""; //$NON-NLS-1$
			}
			return range;
    	}
    }



}
