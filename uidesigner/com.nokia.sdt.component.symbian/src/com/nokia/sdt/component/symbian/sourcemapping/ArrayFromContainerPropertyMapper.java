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

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NodePathLookupResult;
import com.nokia.sdt.emf.component.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstExpressionList;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.*;

/**
 * Maps a container's children an array member of a resource
 * 
 *
 */
public class ArrayFromContainerPropertyMapper implements IPropertyMapper {

    private InstanceSourceMapper mapper;

    public ArrayFromContainerPropertyMapper(InstanceSourceMapper mapper) {
        this.mapper = mapper;
    }

    public boolean checkMapping(IAstArrayDeclarator arrayType,
            IAstSimpleDeclaration type, PropertyContext propertyContext,
            String propertyId) {

        // must map to an array, duh
        if (arrayType == null)
            return false;
        
        // we expect to map to an array of STRUCTs, LLINKs, etc.
        if (type.getKind() != IAstSimpleDeclaration.K_LINK
                && type.getKind() != IAstSimpleDeclaration.K_LLINK
                && type.getKind() != IAstSimpleDeclaration.K_STRUCT)
            return false;
        
        // needs an instance
        NodePathLookupResult result = mapper.lookupProperty(propertyContext, propertyId, false);

        return (result != null && result.result instanceof IPropertySource);
    }

 
    public IAstExpression mapToResource(IAstArrayDeclarator arrayType,
            IAstSimpleDeclaration type, PropertyContext propertyContext,
            String propertyId, TwoWayMappingType mapping,
            IAstExpression defaultInit, boolean canSkipDefault,
            IAstExpression existingValue) {

        NodePathLookupResult result = mapper.lookupProperty(propertyContext, propertyId, false);
    	
        EObject[] kids = result.instance.getChildren();
        if (kids == null)
            return null;

        if (canSkipDefault) {
            // check whether we can ignore the initialization 
            // due to a default array initializer
            // If necessary, check more complex initializers
            // (avoid for now due to unnecessary complexity)
            if (kids.length == 0) {
                if (defaultInit == null)
                    return null;
                if (defaultInit instanceof IAstExpressionList
                    && ((IAstExpressionList) defaultInit).getList().length == 0)
                    return null;
            }
        }

        Check.checkState(mapping instanceof MapArrayMemberType);    // only members have arrays
        MapArrayMemberType mam = (MapArrayMemberType) mapping;
        
        IAstExpressionList elist = (IAstExpressionList) existingValue;
        elist = mapArray(elist, type, kids, 
                propertyContext, propertyId,
                mam, mam.getMember(), mam.getProperty());
        return elist;
    }

    /**
     * Map a list of children to an array expression
     * @param elist the list to populate
     * @param mam the facet for mapping array members
     * @param kids the array of child EObjects
     * @param propertyId 
     * @return the array initializer
     */
    private IAstExpressionList mapArray(IAstExpressionList elist, IAstSimpleDeclaration memberType, 
            EObject[] kids, PropertyContext propertyContext,
            String propertyId, MappingArrayType mam, String memberName, String propertyName) {

    	if (elist == null)
    		elist = new AstExpressionList();
    	
    	IArrayInstanceElementMapper elementMapper;
    	

        // this may contain either a two-way mapping or a "select" with a two-way mapping choice
        
        TwoWayMappingType mm = mam.getTwoWayMapping();
        
        if (mm != null) {
            if (!(mm instanceof MapResourceElementType)) {
                mapper.emit(Message.ERROR,
                        "ArrayFromContainerPropertyMapper.ResourceOrNoSubElementExpected",  //$NON-NLS-1$
                        new Object[] { propertyName, mapper.mappingContext.getComponent().getId(), memberName });
                return null;
            }
            
            // find a way to map the property to the base type of the member
            IPropertyMapper propMapper = mapper.getPropertyMapper(null, memberType, propertyName, mm);
            if (propMapper == null) {
                mapper.emit(Message.ERROR, "ArrayPropertyMapper.CannotMapElementsToArray",  //$NON-NLS-1$
                        new Object[] { propertyName, mapper.mappingContext.getComponent().getId(), memberName });
                return null;
            }
            
            // complex mapping of children: some processing occurs in parent
            // and some in children
            MapResourceElementType mr = (MapResourceElementType) mm;
            
            elementMapper = new ArrayOfComplexResourcesElementMapper(mapper, propertyId, memberName, mr);
            
        } else if (mam.getSelect() != null) {
            elementMapper = new ArrayOfSelectsElementMapper(mapper, mam.getSelect(), memberName);
        } else {
            elementMapper = new ArrayOfSimpleResourcesElementMapper(mapper, memberType, memberName);
        }

        InstanceMappingContext ctx = mapper.mappingContext.copy();
        ctx.setArrayElementMapper(elementMapper);
        mapper.pushContext(ctx);
        
        try {
        	mapArrayElements(elist, kids, elementMapper);
        } finally {
        	mapper.popContext();
        }
    	
    	// after all that, if there are no expressions, return null
    	// TODO: don't return null because this causes RCOMP warnings
        if (elist.size() == 0)
        	return null;

        return elist;
    }

	/**
	 * @param elist
	 * @param kids
	 * @param elementMapper
	 */
	private void mapArrayElements(IAstExpressionList elist, EObject[] kids, IArrayInstanceElementMapper elementMapper) {
		// Track the known elements in the original expression list
        // and the unknown items that follow them.
        // Only currently known instances are keys.
        Map<IComponentInstance, Pair<IAstExpression, List<IAstExpression>>> knownElementMapping = null;
        
        // These unknown elements precede any known elements
        List<IAstExpression> initialUnknownElements = null;
        
        // handle existing elements in RSS to see if they're
        // obsolete or not
    	if (elementMapper.canIdentifyInstances()) {
    		
    		knownElementMapping = new HashMap<IComponentInstance, Pair<IAstExpression, List<IAstExpression>>>();
    		initialUnknownElements = new ArrayList<IAstExpression>();

    		// temporary list of unknown expressions following the last known one
    		IComponentInstance lastKnown = null;
    		List<IAstExpression> followers = new ArrayList<IAstExpression>();
    		
    		// delete obsolete entries, identify known ones, and
    		// associate unknown elements with preceding known elements
    		for (Iterator<IAstExpression> iter = elist.iterator(); iter.hasNext();) {
    			IAstExpression expr = iter.next();
    			String instanceName = elementMapper.lookupInstance(expr);

    			if (instanceName != null) {
    				// see if that's still a valid mapping
    				IComponentInstance instance = ModelUtils.getComponentInstance(
						mapper.container.getDesignerDataModel().findByNameProperty(instanceName));

					if (instance != null && objectInList(kids, instance)) {
						// attach followers to previous known instance
		    			if (lastKnown != null)
		    				knownElementMapping.get(lastKnown).second = followers;
		    			else
		    				initialUnknownElements = followers;
		    			
		    			followers = new ArrayList<IAstExpression>();

		    			// remember mapping
						knownElementMapping.put(instance, new Pair<IAstExpression, List<IAstExpression>>(expr, null));
						
						lastKnown = instance;
					}
					else {
						// removed instance means expression disappears
						iter.remove();
						//elementMapper.removedInstance(pair.first, pair.second);
					}
				} else {
					// remember unknown element
					followers.add(expr);
				}
			}
    		
    		// track leftovers
    		if (followers.size() > 0) {
    			if (lastKnown != null)
    				knownElementMapping.get(lastKnown).second = followers;
    			else
    				initialUnknownElements = followers;
    		}
    	} else {
    		// No way to distinguish elements; 
    		// we must have already incorporated any changes into the data model.
    	}

    	// clear out outgoing expression list and clear out parents
    	// so we can add them again
    	elist.clear();
    	
    	if (initialUnknownElements != null)
    		elist.addAllExpressions(initialUnknownElements);
    	
    	for (int kidIdx = 0; kidIdx < kids.length; kidIdx++) {
    		// each element could be either known or unknown
            IComponentInstance instance = ModelUtils.getComponentInstance(kids[kidIdx]);
            Check.checkState(instance != null);
            IPropertySource properties = ModelUtils.getPropertySource(kids[kidIdx]);

            IAstExpression existing = null;
            List<IAstExpression> followers = null;
            
            // deal with the existing element, if necessary
            if (knownElementMapping != null) {
				Pair<IAstExpression, List<IAstExpression>> pair = knownElementMapping.get(instance);
				if (pair != null) {
					existing = pair.first;
					followers = pair.second;
				}
            }
         
            mapper.mappingContext.setArrayIndex(kidIdx);
            IAstExpression expr = elementMapper.createOrUpdate(existing, instance, properties);
            
            // null is not necessarily an error
            if (expr != null) {
            	elist.addExpression(expr);
            }
            if (followers != null)
            	elist.addAllExpressions(followers);
    	}
	}

    private boolean objectInList(EObject[] kids, IComponentInstance instance) {
    	for (int i = 0; i < kids.length; i++) {
			if (kids[i] == instance.getEObject())
				return true;
		}
		return false;
	}

}
