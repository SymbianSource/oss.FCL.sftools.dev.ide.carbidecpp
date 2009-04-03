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
package com.nokia.sdt.component.symbian.sourcemapping;

import com.nokia.sdt.emf.component.MappingFixedType;
import com.nokia.sdt.emf.component.TwoWayMappingType;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstIdExpression;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstName;
import com.nokia.cpp.internal.api.utils.core.Check;


/**
 * Maps a literal, fixed value into a resource
 * 
 *
 */
public class FixedPropertyMapper implements IPropertyMapper {

    public FixedPropertyMapper(InstanceSourceMapper mapper) {
    }

    /**
     * Check whether we can map a simple member from a property of instance,
     * using default type mappings. 
     * @param type the member type to map to
     * @param propertyContext the property we're mapping
     * @return true if possible
     */
    public boolean checkMapping(IAstArrayDeclarator array, IAstSimpleDeclaration type, 
            PropertyContext propertyContext, String propertyId) {
        int memberKind = type.getKind();
        
        // these should never be initialized
        if (memberKind == IAstSimpleDeclaration.K_SRLINK) {
            return false;
        }

        // wrong mapping elements?
        if (memberKind == IAstSimpleDeclaration.K_STRUCT) {
            return false;
        }
        if (array != null) {
            return false;
        }

        return true;
    }

    public IAstExpression mapToResource(IAstArrayDeclarator array, IAstSimpleDeclaration type,
            PropertyContext propertyContext, String propertyId,
            TwoWayMappingType mapping, IAstExpression defaultInit, boolean canSkipDefault,
            IAstExpression existing) {

        Check.checkState(array == null);
        MappingFixedType mft = (MappingFixedType) mapping;
        String value = mft.getValue();
        
        if (canSkipDefault) {
            // check equivalence to default
            if (defaultInit != null && defaultInit instanceof IAstIdExpression
                    && ((IAstIdExpression) defaultInit).getName().getName().equals(value))
                return null;
        }

        // ignore existing
        return new AstIdExpression(new AstName(value, null));
    }

}
