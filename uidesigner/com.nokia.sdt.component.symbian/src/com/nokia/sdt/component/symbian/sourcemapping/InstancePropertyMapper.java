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

import com.nokia.sdt.datamodel.util.NodePathLookupResult;
import com.nokia.sdt.emf.component.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstIdExpression;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Message;


public class InstancePropertyMapper implements IPropertyMapper {

    private InstanceSourceMapper mapper;

    public InstancePropertyMapper(InstanceSourceMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Check whether we can map an instance to a reference property.
     * This is used when a specific "child of type" is mapped.
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
        if (memberKind != IAstSimpleDeclaration.K_LLINK 
                && memberKind != IAstSimpleDeclaration.K_LINK
                && memberKind != IAstSimpleDeclaration.K_STRUCT) {
            return false;
        }
        if (array != null) {
            return false;
        }

        // the property should resolve to an instance,
        // i.e. the 'result' is the instance's property source
        NodePathLookupResult result = mapper.lookupProperty(propertyContext, propertyId, false);
        return result != null && result.instance != null &&  result.result == result.properties;
    }

    public IAstExpression mapToResource(IAstArrayDeclarator array, IAstSimpleDeclaration type,
            PropertyContext propertyContext, String propertyId,
            TwoWayMappingType mapping, IAstExpression defaultInit, boolean canSkipDefault,
            IAstExpression existing) {

    	// ignore existing: we should find any existing resource this way

        NodePathLookupResult result = mapper.lookupProperty(propertyContext, propertyId, false);
        String rsrcId = null;
        if (mapping instanceof MappingInstanceType)
            rsrcId = ((MappingInstanceType) mapping).getRsrcId();
        else if (mapping instanceof MappingReferenceType)
            rsrcId = ((MappingReferenceType) mapping).getRsrcId();
        else
            Check.checkState(false);
        
        int mapFlags = (type.getKind() != IAstSimpleDeclaration.K_STRUCT)
            ? InstanceSourceMapper.MAP_STATEMENT 
                    : InstanceSourceMapper.MAP_EXPRESSION;
        
        mapFlags |= InstanceSourceMapper.MAP_FORCE;
        
        IAstResource[] rsrcs = mapper.findOrCreateResources(
                result.instance, result.properties, 
                mapFlags, rsrcId);
        
        if (rsrcs == null)
            return null;
        if (rsrcs.length != 1) {
            mapper.emit(Message.ERROR, "InstancePropertyMapper.AmbiguousResourceForReference",  //$NON-NLS-1$
                    new Object[] {
                    result.instance.getName(), 
                    result.instance.getComponentId(),
                    mapper.mappingContext.getInstance().getName(), 
                    mapper.mappingContext.component.getId(),
                    rsrcId != null ? rsrcId : Messages.getString("InstancePropertyMapper.NullResourceId"), //$NON-NLS-1$
                    rsrcs.length
            });
            if (rsrcs.length == 0)
                return null;
        }
        if (type.getKind() == IAstSimpleDeclaration.K_STRUCT) {
            return (IAstResourceExpression)rsrcs[0];
        } else {
            return new AstIdExpression(((IAstResourceDefinition) rsrcs[0]).getName());
        }
    }

}
