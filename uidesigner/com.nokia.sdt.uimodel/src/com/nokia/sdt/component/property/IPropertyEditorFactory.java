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
package com.nokia.sdt.component.property;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * This interface is used to implement custom CellEditors 
 * and ILabelProviders for properties. The class specified in 
 * the component definition is an implementation of IPropertyEditorFactory. 
 *
 */
public interface IPropertyEditorFactory {
	
	/**
	 * Creates a new label provider. May return null to
	 * use the default label provider.
	 * @see IPropertyDescriptor#getLabelProvider()
	 */
	ILabelProvider createLabelProvider(EObject object, String propertyPath);
	
	/**
	 * Create a new cell editor. May return null to use
	 * the default cell editor.
	 * @see IPropertyDescriptor#createPropertyEditor
	 */
	CellEditor createCellEditor(Composite parent, EObject object, String propertyPath);
	
	/**
	 * Create a new validator. May return null.
	 */
	ICellEditorValidator createCellEditorValidator(EObject object, String propertyPath);
}
