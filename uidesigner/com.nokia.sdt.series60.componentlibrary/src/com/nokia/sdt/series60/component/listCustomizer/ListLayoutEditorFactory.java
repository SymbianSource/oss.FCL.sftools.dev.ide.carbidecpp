/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.series60.component.listCustomizer;

import com.nokia.sdt.component.property.AbstractPropertyEditorFactory;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.Composite;

/**
 * Creates editor for string list properties.
 * 
 */
public class ListLayoutEditorFactory extends AbstractPropertyEditorFactory {

	protected ILabelProvider labelProvider;

	protected ListLayoutData data;
	
	/**
	 * 
	 */
	public ListLayoutEditorFactory() {
		
	}
	
	protected void ensureData(EObject eObject) {
		if (data == null) {
			data = new ListLayoutData(ListLayoutData.getInstanceListBoxStyles(eObject));
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.component.property.IPropertyEditorFactory#createLabelProvider(org.eclipse.emf.ecore.EObject,
	 *      java.lang.Object)
	 */
	public ILabelProvider createLabelProvider(final EObject object, String propertyId) {
		return labelProvider = new LabelProvider() {
		    public String getText(Object element) {
		    	if ((element == null) || (element.toString().length() == 0))
		    		return ""; //$NON-NLS-1$
		    	ensureData(object);
		        return data.valueToDisplayMap.get(element.toString());
		    }
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.component.property.IPropertyEditorFactory#createCellEditor(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.emf.ecore.EObject, java.lang.Object)
	 */
	public CellEditor createCellEditor(Composite parent, EObject object,
			String propertyId) {
		CellEditor cellEditor = new ListLayoutCellEditor(labelProvider, object);
		cellEditor.create(parent);
		return cellEditor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.component.property.IPropertyEditorFactory#createCellEditorValidator(org.eclipse.emf.ecore.EObject,
	 *      java.lang.Object)
	 */
	public ICellEditorValidator createCellEditorValidator(EObject object,
			String propertyId) {
		return null;
	}

}
