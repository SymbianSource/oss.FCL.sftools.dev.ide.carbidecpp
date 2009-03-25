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


package com.nokia.sdt.component.property;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * Allows us to extend the standard property descriptor interface
 */
public interface IPropertyDescriptorExtensions {

    /**
     * @param parent a composite
     * @param style appropriate SWT style bits
     * @see CellEditor#setStyle() for style parameter
     * @return a cell editor
     */
    public CellEditor createPropertyEditor(Composite parent, int style);
    
    /**
     * @param parent a composite
     * @param style appropriate SWT style bits
     * @see CellEditor#setStyle() for style parameter
     * @return a text cell editor for use in direct label editing
     */
    public TextCellEditor createDirectLabelPropertyEditor(Composite parent, int style);
    
    /**
     * @return true if values of the property are localizable.
     * Composite types providing subproperties should return false, not the status of whether
     * any child values are localizable.
     */
    public boolean isPropertyLocalizable();

}
