/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.sourcemapping;

import com.nokia.sdt.emf.component.TwoWayMappingType;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;


interface IPropertyMapper {
    /** Tell whether the given declaration can be mapped to/from the given property
     * 
     * @param arrayType the array declarator, if any
     * @param type the basic type (if arrayType!=null, the element type)
     * @param propertyContext the source of the property
     * @param propertyId the ID of the property
     * @return true if mapping is allowed
     */
    boolean checkMapping(IAstArrayDeclarator arrayType, IAstSimpleDeclaration type, PropertyContext propertyContext, String propertyId);
    
    /** Create an expression expressing the mapping of a property to RSS
     * 
     * @param arrayType the array type (or null)
     * @param type the member or element type
     * @param mapping
     * @param defaultInit if not null, default value associated with type
     * @param canSkipDefault if true, allow returning null to skip initialization of default
     * @param propertyContext the source of the property
     * @param propertyId the ID of the property
     * @param existingValue if not null, the existing setting in RSS, which
     * should be returned if possible (unless there's an error)
     * @return expression, or null if canSkipDefault is true and the property matches the default type
     */
    IAstExpression mapToResource(IAstArrayDeclarator arrayType, IAstSimpleDeclaration type, 
            PropertyContext propertyContext, String propertyId, 
            TwoWayMappingType mapping, 
            IAstExpression defaultInit, boolean canSkipDefault,
            IAstExpression existingValue);
}