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
/**
 * 
 */
package com.nokia.sdt.component.symbian.properties;

import com.nokia.sdt.component.symbian.properties.TypeDescriptors.StringTypeDescriptor;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

public class ComponentReferenceTypeDescriptor extends StringTypeDescriptor {
		
	public ComponentReferenceTypeDescriptor() {
		super(TypeDescriptors.COMPONENT_REFERENCE_IMAGE, ""); //$NON-NLS-1$
	}
	
	public boolean isLocalizable() {
		return false;
	}

	public boolean isReference() {
		return true;
	}

	public CellEditor createPropertyEditor(Composite parent, int style) {
		return null;
	}
}