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

import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.sdt.symbian.images.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ICellEditorValidator;

public class ImageCellEditorValidator implements ICellEditorValidator {

    public ImageCellEditorValidator(EObject object, String propertyId) {
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ICellEditorValidator#isValid(java.lang.Object)
     */
    public String isValid(Object value) {
        if (!(value instanceof ImagePropertyInfo))
            return null;
        
        ImagePropertyInfo data = (ImagePropertyInfo) value;

        IImageModel model = SymbianImageModelFactory.createImagePropertyInfoImageModel(
        		data);
        if (model == null)
        	return null;		// a missing image is not invalid here
        
        IStatus status = model.validate();
        
        // in a cell editor validator, we only report errors, not warnings
        if (status == null || status.getSeverity() != IStatus.ERROR )
            return null;
        return status.getMessage();
    }
}