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
package com.nokia.sdt.component.symbian.reconcileProperty;

import com.nokia.sdt.component.symbian.scripting.WrappedInstance;

public interface IScriptReconcileProperty {
    /**
     * Generates the display value (editable value) for a given property value of some property type.
     * This is most useful when the propertyValue is an IPropertySource
     * and the display value is the summary (parent property's) editable value.
     * 
     * @param instance the instance holding the property
     * @param propertyTypeName name of the compound property's type
     * @param propertyValue value of the property (WrappedProperties)
     * @return the display value
     */
    Object createDisplayValue(WrappedInstance instance, String propertyTypeName, Object propertyValue);
    
    /**
     * @param instance the instance holding the property
     * @param propertyTypeName name of the compound property's type
     * @return true if the display value can be applied, otherwise no cell editor is shown
     */
    boolean isDisplayValueEditable(WrappedInstance instance, String propertyTypeName);
    
    /**
     * Applies the display value for some property type to the property value.
     * This is most useful when the propertyValue is an IPropertySource
     * and the display value is the summary (parent property's) editable value.
     * 
     * @param instance the instance holding the property
     * @param propertyTypeName name of the compound property's type
     * @param displayValue the displayed/edited value
     * @param propertyValue value of the property (WrappedProperties)
     */
    void applyDisplayValue(WrappedInstance instance, String propertyTypeName, Object displayValue, Object propertyValue);
}
