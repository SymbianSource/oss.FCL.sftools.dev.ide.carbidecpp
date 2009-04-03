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

import com.nokia.sdt.component.property.ISequencePropertySource;
import com.nokia.sdt.datamodel.util.NodePathLookupResult;
import com.nokia.sdt.emf.component.MappingReferenceType;
import com.nokia.sdt.emf.component.TwoWayMappingType;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstIdExpression;

import org.eclipse.ui.views.properties.IPropertySource;

public class ReferencePropertyMapper implements IPropertyMapper {

    private InstanceSourceMapper mapper;

    public ReferencePropertyMapper(InstanceSourceMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Check whether we can map a reference member from a property of instance,
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
        if (memberKind != IAstSimpleDeclaration.K_LINK
                && memberKind != IAstSimpleDeclaration.K_LLINK) {
            return false;
        }
        if (array != null) {
            return false;
        }

        NodePathLookupResult result = mapper.lookupProperty(propertyContext, propertyId, true);
        if (result == null)
            return false;
        Object property = result.result;
        if (property == null)
            return false;
        
        if (property instanceof ISequencePropertySource) {
            // don't map arrays to members
            return false;
        } else if (property instanceof IPropertySource) {
            // don't map structs to members 
            return false;
        }

        return true;
    }

    public IAstExpression mapToResource(IAstArrayDeclarator array, IAstSimpleDeclaration type,
            PropertyContext propertyContext, String propertyId,
            TwoWayMappingType mapping, IAstExpression defaultInit, boolean canSkipDefault,
            IAstExpression existing) {
        
    	// ignore existing
    	
        NodePathLookupResult result = mapper.lookupProperty(propertyContext, propertyId, true);
        Object property = result.result;
        
        if (property.toString().equals("")) //$NON-NLS-1$
            return null;
        IAstResourceDefinition rsrc = mapper.mapReferencedResource(property.toString(), 
                ((MappingReferenceType) mapping).getRsrcId());
        if (rsrc == null)
            return null;
        return new AstIdExpression(rsrc.getName()); 
    }

}
