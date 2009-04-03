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

import java.text.MessageFormat;

public class ValidateDriveLetterFactory extends AbstractPropertyEditorFactory{

	public ILabelProvider createLabelProvider(EObject object, String propertyId) {
		return null;
	}
	
	public CellEditor createCellEditor(Composite parent, EObject object, String propertyId) {
		return null;
	}
	
	public ICellEditorValidator createCellEditorValidator(EObject obj, String propertyId) {
		return new DriveLetterValidator();
	}

	static class DriveLetterValidator implements ICellEditorValidator {
		
    	DriveLetterValidator() {
    	}

		public String isValid(Object value) {
			boolean valid = false;
			if (value != null) {
				String asString = value.toString().toLowerCase();
				if (asString.length() == 1 && 
						((asString.charAt(0) >= 'a' && asString.charAt(0) <= 'z'))) 
					valid = true;		
			}
			
			if (!valid)
				return MessageFormat.format(
						Messages.getString("ValidateDriveLetterFactory.Error"), new Object[] { value }); //$NON-NLS-1$
			
			return null;
		}
    	
    }
}
