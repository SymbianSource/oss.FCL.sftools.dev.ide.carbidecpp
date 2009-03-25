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
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.*;
import com.nokia.cpp.internal.api.utils.core.Message;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.*;

/**
 * Map an bitmask property to a field of a resource
 * 
 *
 */
public class BitmaskPropertyMapper implements IPropertyMapper {

    private InstanceSourceMapper mapper;

    public BitmaskPropertyMapper(InstanceSourceMapper mapper) {
        this.mapper = mapper;
    }

    public boolean checkMapping(IAstArrayDeclarator arrayType,
            IAstSimpleDeclaration type, PropertyContext propertyContext,
            String propertyId) {
        int memberKind = type.getKind();
        
        // these should never be initialized
        if (memberKind == IAstSimpleDeclaration.K_SRLINK) {
            return false;
        }

        // wrong mapping elements?
        if (memberKind != IAstSimpleDeclaration.K_BYTE
                && memberKind != IAstSimpleDeclaration.K_LONG
                && memberKind != IAstSimpleDeclaration.K_WORD)
            return false;

        if (arrayType != null) {
            return false;
        }
        
        // the property must be a property source, so we can find
        // boolean properties inside
        NodePathLookupResult result = mapper.lookupProperty(propertyContext, propertyId, true);
        if (result == null)
            return false;
        Object property = result.result;
        if (property == null)
            return false;
        if (!(property instanceof IPropertySource)) {
            return false;
        }

        return true;
    }

    public IAstExpression mapToResource(IAstArrayDeclarator arrayType,
            IAstSimpleDeclaration type, PropertyContext propertyContext,
            String propertyId, TwoWayMappingType mapping,
            IAstExpression defaultInit, boolean canSkipDefault,
            IAstExpression existing) {
    	
    	// TODO: don't ignore existing value
        
        // find the property
        NodePathLookupResult result = mapper.lookupProperty(propertyContext, propertyId, true);

        IPropertySource properties = (IPropertySource) result.result;
        
        MappingBitmaskType mb = (MappingBitmaskType) mapping;

        // populate the set of properties we expect to visit
        Set expectedProperties = new HashSet();
        if (mb.getIncludedProperties() != null) {
            expectedProperties.addAll(mb.getIncludedProperties());
        } else {
            // not specified --> all entries mapped
            IPropertyDescriptor[] descs = properties.getPropertyDescriptors();
            for (int i = 0; i < descs.length; i++) {
                expectedProperties.add(descs[i].getId().toString());
            }
        }

        // keep track of the properties which we actually recorded
        Set visitedProperties = new HashSet();
        
        // keep track of the properties we saw as single-property mappings
        Set singletonProperties = new HashSet();
        
        // descend into the property source
        InstanceMappingContext imContext = new InstanceMappingContext(mapper.mappingContext.getInstance(), properties);
        mapper.pushContext(imContext);
        PropertyContext context = imContext.getPropertyContext();
        
        // iterate all the properties
        IAstExpression orExpr = null;
        for (Iterator iter = mb.getMapBitmaskValue().iterator(); iter.hasNext();) {
            MapBitmaskValueType mapValue = (MapBitmaskValueType) iter.next();
            List mbProperties = mapValue.getProperties(); 
            if (mbProperties.size() == 1) {
                singletonProperties.add(mbProperties.get(0));
            }
            
            // see if every property mentioned has a true value
            // (an empty list matches)
            boolean matches = true;
            boolean empty = true;
            for (Iterator iterator = mbProperties.iterator(); iterator
                    .hasNext();) {
                String prop = (String) iterator.next();
                empty = false;
                boolean propValue = getBooleanProperty(context, prop);
                if (!propValue) {
                    matches = false;
                    break;
                }
                if (visitedProperties.contains(prop)) {
                    matches = false;
                    break;
                }
            }
            
            // if all the considered properties match and none of them are
            // already handled, append the value, remove these properties 
            // from consideration, and continue
            if (matches && !(empty && visitedProperties.size() > 0)) {
                if (orExpr == null)
                    orExpr = new AstIdExpression(new AstName(mapValue.getValue()));
                else
                    orExpr = new AstBinaryExpression(IAstBinaryExpression.K_OR, 
                            orExpr, 
                            new AstIdExpression(new AstName(mapValue.getValue())));
                
                for (Iterator iterator = mbProperties.iterator(); iterator.hasNext();) {
                    String prop = (String) iterator.next();
                    visitedProperties.add(prop);
                }
            }
        }

        mapper.popContext();

        // ensure that all the mapBitmaskValue elements would match
        // any possible combination
        if (!singletonProperties.equals(expectedProperties)) {
            mapper.emit(Message.ERROR, "BitmaskPropertyMapper.IncompleteMapping",  //$NON-NLS-1$
                    new Object[] {
                        getPropertiesString(expectedProperties),
                        getPropertiesString(singletonProperties),
                        mapper.mappingContext.getComponent().getId() });
        }
        
        if (orExpr == null) {
            orExpr = new AstLiteralExpression(IAstLiteralExpression.K_INTEGER, "0"); //$NON-NLS-1$
        }
        
        // Check whether the default value can be reduced and if
        // this equals our calculated value.  Currently this will work
        // only if:
        //  1) the orExpr is 0 and the default is 0 or a macro expanding to 0
        //  2) the orExpr is a name or OR of names and the default uses an enum expression
        if (defaultInit != null) {
            IAstExpression defaultSimplified = defaultInit.simplify();
            IAstExpression orExprSimplified = orExpr.simplify();
            if (defaultSimplified.equalValue(orExprSimplified))
                return null;
        }
        
        return orExpr;
    }
    
    private String getPropertiesString(Set expectedProperties) {
        StringBuffer buf = new StringBuffer();
        for (Iterator iter = expectedProperties.iterator(); iter.hasNext();) {
            String prop = (String) iter.next();
            if (buf.length() > 0)
                buf.append(' ');
            buf.append(prop);
        }
        return buf.toString();
    }

    private boolean getBooleanProperty(PropertyContext context, String propertyName) {
        NodePathLookupResult propResult = mapper.lookupProperty(context, propertyName, true);
        if (propResult == null)
            return false;
        if (!(propResult.result instanceof Boolean)) {
            mapper.emit(Message.ERROR, "BitmaskPropertyMapper.NonBooleanProperty",  //$NON-NLS-1$
                    new Object[] { propertyName, mapper.mappingContext.getComponent().getId() });
            return false;
        }
        return ((Boolean) propResult.result).booleanValue();
    }

}
