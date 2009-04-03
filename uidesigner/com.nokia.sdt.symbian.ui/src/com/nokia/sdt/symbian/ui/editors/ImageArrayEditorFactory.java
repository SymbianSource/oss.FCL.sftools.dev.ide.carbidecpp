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


package com.nokia.sdt.symbian.ui.editors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ILabelProvider;

/**
 * Creates editor for arrays of images.
 */
public class ImageArrayEditorFactory extends AbstractArrayPropertyEditorFactory {

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.property.IPropertyEditorFactory#createCellEditorValidator(org.eclipse.emf.ecore.EObject, java.lang.Object)
	 */
	public ICellEditorValidator createCellEditorValidator(EObject object, String propertyId) {
		return new ImageCellEditorValidator(object, propertyId.toString());
	}

	@Override
	public ILabelProvider createElementLabelProvider(final EObject object, Object propertyID) {
		return new ImageLabelProvider(object);
	}
	
	@Override
	protected boolean isFixedLengthArrayProperty(EObject object, Object propertyID) {
		return false;
	}
}
