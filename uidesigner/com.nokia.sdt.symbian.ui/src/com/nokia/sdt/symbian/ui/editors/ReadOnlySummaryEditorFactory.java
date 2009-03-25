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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public class ReadOnlySummaryEditorFactory extends AbstractPropertyEditorFactory {

	public ILabelProvider createLabelProvider(EObject obj, String propertyId) {
		return new LabelProvider() {

			public String getText(Object element) {
				String result = null;
				if (element instanceof IPropertySource) {
					result = summarizePropertySource((IPropertySource)element);
				}
				else if (element != null) {
					result = element.toString();
				}
				return result;
			}
			
		};
	}
	
	static String summarizePropertySource(IPropertySource ps) {
		StringBuffer s = new StringBuffer("{"); //$NON-NLS-1$
		IPropertyDescriptor[] propertyDescriptors = ps.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			IPropertyDescriptor pd = propertyDescriptors[i];
			String id = pd.getDisplayName();
			Object value = ps.getPropertyValue(pd.getId());
			if (i > 0)
				s.append(";"); //$NON-NLS-1$
			s.append(id);
			s.append("="); //$NON-NLS-1$
			s.append(value);
		}
		s.append("}"); //$NON-NLS-1$
		return s.toString();
	}

	public CellEditor createCellEditor(Composite parent, EObject obj, String propertyId) {
		return null;
	}

	public ICellEditorValidator createCellEditorValidator(EObject obj, String propertyId) {
		return null;
	}

}
