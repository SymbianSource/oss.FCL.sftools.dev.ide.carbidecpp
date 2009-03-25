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
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.NodePathLookupResult;
import com.nokia.sdt.emf.component.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstExpressionList;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Message;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * Maps a sequence type to an array property of a resource.
 * 
 *
 */
public class ArrayFromSequencePropertyMapper implements IPropertyMapper {

    private InstanceSourceMapper mapper;

    public ArrayFromSequencePropertyMapper(InstanceSourceMapper mapper) {
        this.mapper = mapper;
    }

    public boolean checkMapping(IAstArrayDeclarator arrayType,
            IAstSimpleDeclaration type, PropertyContext propertyContext,
            String propertyId) {

        // must map to an array, duh
        if (arrayType == null)
            return false;
        
        // array member requires property implementing ISequencePropertySource
        NodePathLookupResult result = mapper.lookupProperty(propertyContext, propertyId, false);

        if (result == null || !(result.result instanceof ISequencePropertySource))
            return false;
        
        return true;
    }

 
    public IAstExpression mapToResource(IAstArrayDeclarator arrayType,
            IAstSimpleDeclaration type, PropertyContext propertyContext,
            String propertyId, TwoWayMappingType mapping,
            IAstExpression defaultInit, boolean canSkipDefault,
            IAstExpression existing) {

        NodePathLookupResult result = mapper.lookupProperty(propertyContext, propertyId, false);
        ISequencePropertySource sequence = (ISequencePropertySource) result.result;

        if (canSkipDefault) {
            // check whether we can ignore the initialization 
            // due to a default array initializer
            // If necessary, check more complex initializers
            // (avoid for now due to unnecessary complexity)
            if (sequence.size() == 0) {
                if (defaultInit == null)
                    return null;
                if (defaultInit instanceof IAstExpressionList
                    && ((IAstExpressionList) defaultInit).getList().length == 0)
                    return null;
            }
        }

        Check.checkState(mapping instanceof MapArrayMemberType);    // only members have arrays
        MapArrayMemberType mam = (MapArrayMemberType) mapping;
        
        IAstExpressionList elist = null;
        if (existing != null) {
        	if (existing instanceof IAstExpressionList) 
        		elist = (IAstExpressionList) existing;
        	else
        		mapper.reportUnexpectedSyntax(existing, propertyId,
        				Messages.getString("ArrayFromSequencePropertyMapper.ArrayOfExpressions")); //$NON-NLS-1$
        }
		elist = mapArray(elist, 
        		type, result.instance, sequence, 
                mam, mam.getMember(), mam.getProperty());
        return elist;
    }

    /**
     * Map a sequence property to an array expression
     * 
     * @param mam the facet for mapping array members
     * @param sequence the sequence property source to use
     * @return the array initializer
     */
    private IAstExpressionList mapArray(IAstExpressionList elist,
    		IAstSimpleDeclaration memberType,
            IComponentInstance instance,
            ISequencePropertySource sequence, 
            MappingArrayType mam, String memberName, String propertyName) {

    	// don't update list; rewrite every time
    	if (elist != null)
    		elist.clear();
    	else
    		elist = new AstExpressionList();
        
        TwoWayMappingType mm = mam.getTwoWayMapping();
        
        if (mm == null) {
            mapper.emit(Message.ERROR, "ArrayFromSequencePropertyMapper.SubElementRequired",  //$NON-NLS-1$
                    new Object[] { propertyName, mapper.mappingContext.getComponent().getId(), memberName });
            return null;
        }

        IPropertyDescriptor descs[] = sequence.getPropertyDescriptors();
        
        // bail out early if empty 
        if (descs.length == 0) {
        	return elist;
        }
        
        // push the sequence context
        InstanceMappingContext ctx = new InstanceMappingContext(instance, sequence);
        ctx.setMemberName(memberName);
        
        IPropertyMapper propMapper = null;
        
        mapper.pushContext(ctx);

        // first, see if we're mapping into a property
        String intoProperty = null;
        if (mm instanceof MapIntoPropertyType) {
        	intoProperty = ((MapIntoPropertyType) mm).getProperty();
        	mm = ((MapIntoPropertyType) mm).getTwoWayMapping();
        }
        
        String thisProperty = descs[0].getId().toString() + (intoProperty != null ? "." + intoProperty  : ""); 
    	// then, promote mapElementToType to its actual mapping
        if (mm instanceof MapElementFromTypeType) {
       		mm = mapper.resolveTypeMapping(thisProperty, ((MapElementFromTypeType) mm).getTypeId());
    	}
        
        // get a mapper based on the array
        propMapper = mapper.getPropertyMapper(null, memberType,
        		thisProperty, 
        		mm);
        if (propMapper == null) {
            mapper.emit(Message.ERROR, "ArrayPropertyMapper.CannotMapElementsToArray",  //$NON-NLS-1$
                    new Object[] { propertyName + "." + thisProperty, mapper.mappingContext.getComponent().getId(), memberName }); //$NON-NLS-1$
            mapper.popContext();
            return null;
        }

        for (int i = 0; i < descs.length; i++) {
        	// set the element index
        	thisProperty = descs[i].getId().toString() + (intoProperty != null ? "." + intoProperty : "");
        	mapper.mappingContext.setArrayIndexProperty(thisProperty);
        	mapper.mappingContext.setArrayIndex(i);
            IAstExpression expr = propMapper.mapToResource(null, memberType, 
            		ctx.getPropertyContext(), 
            		thisProperty, 
            		mm, null, false,
            		null);
            if (expr == null) {
                // since we don't allow defaults, this must be an error, so don't
                // continue issuing reports
                break;
            }
            elist.addExpression(expr);
        }

        mapper.popContext();
        return elist;
    }

}
