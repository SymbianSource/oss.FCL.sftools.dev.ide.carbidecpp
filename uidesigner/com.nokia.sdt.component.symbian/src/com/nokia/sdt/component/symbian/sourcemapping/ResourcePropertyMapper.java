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

import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Map a compound property to a STRUCT (resource) member of a resource
 * 
 *
 */
public class ResourcePropertyMapper implements IPropertyMapper {

    private InstanceSourceMapper mapper;

    public ResourcePropertyMapper(InstanceSourceMapper mapper) {
        this.mapper = mapper;
    }

    public boolean checkMapping(IAstArrayDeclarator arrayType,
            IAstSimpleDeclaration type, PropertyContext propertyContext,
            String propertyId) {
        
        // no arrays here
        if (arrayType != null)
            return false;
        // need a struct or link member
        if (type.getKind() != IAstSimpleDeclaration.K_STRUCT
                && type.getKind() != IAstSimpleDeclaration.K_LINK
                && type.getKind() != IAstSimpleDeclaration.K_LLINK)
            return false;
        
        NodePathLookupResult result = mapper.lookupProperty(propertyContext, propertyId, false);
        
        return result != null /*&& (result.result instanceof IPropertySource)*/;
    }

    public IAstExpression mapToResource(IAstArrayDeclarator arrayType,
            IAstSimpleDeclaration type, PropertyContext propertyContext,
            String propertyId, TwoWayMappingType mapping,
            IAstExpression defaultInit, boolean canSkipDefault,
            IAstExpression existing) {

    	MappingResourceType mr = (MappingResourceType) mapping;
        
        // recurse
        NodePathLookupResult result = mapper.lookupProperty(propertyContext, propertyId, false); 
        Object property = result.result;
        
        String rsrcName = null;
        int mapFlags;

        IAstResource existingRsrc = null;
        
        if (type.getKind() != IAstSimpleDeclaration.K_STRUCT) {

        	// LINK or LLINK needs a separate resource
        	Check.checkState(mapping instanceof MappingResourceType);
        	
        	if (existing != null) {
        		// ignore a value of 0
        		if (existing instanceof IAstLiteralExpression) {
        			// this may be zero, which is allowed, meaning "no resource"
        			if (((IAstLiteralExpression) existing).getValue().equals("0")) { //$NON-NLS-1$
        				existing = null;
        			} else {
        				// an explicit non-zero resource id?  We can't handle this but we'll warn about it before ignoring the value.
        				mapper.reportUnexpectedSyntax(existing, propertyId, Messages.getString("ResourcePropertyMapper.ResourceExpression")); //$NON-NLS-1$
        				existing = null;
        			}
        		} else if (!(existing instanceof IAstIdExpression)) {
        			// expected "myfield = r_myresource;" 
        			mapper.reportUnexpectedSyntax(existing, propertyId, Messages.getString("ResourcePropertyMapper.ResourceExpression")); //$NON-NLS-1$
        			existing = null;
        		} else {
        			// It IS an ID expression, but it may be an "0" again if mapped by mapFixedMember.
        			// Treat this as non-existing.
        			if (((IAstIdExpression) existing).getName().getName().equals("0")) { //$NON-NLS-1$
        				existing = null;
        			}
        		}
        	}
        	
        	if (existing != null) {
        		rsrcName = ((IAstIdExpression) existing).getName().getName();
        		existingRsrc = mapper.tu.findResourceDefinition(rsrcName);
        	} else {
	            // We can have multiple matches on the name,
	            // so get a more unique name.  Base it off the member. 
        		String theMember = mapper.mappingContext.getMemberName();
        		if (mapping instanceof MapResourceMemberType)
        			theMember = ((MapResourceMemberType) mapping).getMember();
	            rsrcName = mapper.rssManipulator.getResourceHandler().getDerivedResourceName(
                    mapper.mappingContext.getInstance().getDesignerDataModel(), 
                    mapper.mappingContext.getInstance().getName() + "_" +  //$NON-NLS-1$
                    theMember);
        	}
        	
            mapFlags = InstanceSourceMapper.MAP_STATEMENT;
        } else {
        	if (existing != null && !(existing instanceof IAstResourceExpression)) {
        		mapper.reportUnexpectedSyntax(existing, propertyId, Messages.getString("ResourcePropertyMapper.ResourceExpression")); //$NON-NLS-1$
        		existing = null;
        	}
        	if (existing != null)
        		existingRsrc = (IAstResource) existing;
     
            mapFlags = InstanceSourceMapper.MAP_EXPRESSION;
        }
            
        // check for mapping an array element: no struct/headers and property="."
        if (mr.getStruct() == null && mr instanceof MapResourceMemberType && 
                ((MapResourceMemberType) mr).getProperty().equals(".")) { //$NON-NLS-1$
            mr = SourceMappingAdapterXML.getPrimaryMapResourceType(mapper.mappingContext.getInstance().getComponent());
            if (mr == null)
            	return null;
        }

        InstanceMappingContext ctx = mapper.mappingContext.copy();
        if (property instanceof IPropertySource)
            ctx.setProperties((IPropertySource) property);
        ctx.setRsrcName(rsrcName);
        mapper.pushContext(ctx);
        IAstResource rsrc = mapper.mapResource(mr, mapFlags, (IAstResource) existingRsrc);
        mapper.popContext();
        
        if (rsrc == null)
            return null;
        
        if (rsrcName != null) {
            Check.checkState(rsrc instanceof IAstResourceDefinition);
            return new AstIdExpression(((IAstResourceDefinition) rsrc).getName());
        } else {
            Check.checkState(rsrc instanceof IAstResourceExpression);
            return (IAstResourceExpression) rsrc;
        }
    }
}
