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
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.*;
import com.nokia.cpp.internal.api.utils.core.Message;

import org.eclipse.ui.views.properties.IPropertySource;

import java.util.Iterator;

/**
 * Map an enumeration property to a field of a resource
 * 
 *
 */
public class EnumPropertyMapper implements IPropertyMapper {

    private InstanceSourceMapper mapper;

    public EnumPropertyMapper(InstanceSourceMapper mapper) {
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
        if (memberKind == IAstSimpleDeclaration.K_STRUCT) {
            return false;
        }
        if (arrayType != null) {
            return false;
        }
        
        // this must have a nameAlgorithm, check later
        if (propertyId.equals(".") || propertyId.endsWith("->")) //$NON-NLS-1$ //$NON-NLS-2$
            return true;
        
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

    public IAstExpression mapToResource(IAstArrayDeclarator arrayType,
            IAstSimpleDeclaration type, PropertyContext propertyContext,
            String propertyId, TwoWayMappingType mapping,
            IAstExpression defaultInit, boolean canSkipDefault,
            IAstExpression existing) {
        
        // find the mapped enum
        NodePathLookupResult result = mapper.lookupProperty(propertyContext, propertyId, true);
        
        String propertyValue = result.result.toString();
        String exprValue = null;
        
        MappingEnumType me = (MappingEnumType) mapping;
        
        // ensure we get the headers defining the enums
        if (me.getHeaders() != null)
            mapper.ensureIncludedFiles(me.getHeaders());
        
        if (me.getMapEnum().size() == 0) {
            // use value directly
            exprValue = propertyValue;
        } else {
            // explicit mapping
            for (Iterator iter = me.getMapEnum().iterator(); iter.hasNext();) {
                MapEnumType mapEnum = (MapEnumType) iter.next();
                if (propertyValue.equals(mapEnum.getValue())) {
                    exprValue = mapEnum.getEnumerator();
                    break;
                }
            }
        
            if (exprValue == null) {
                if (!(mapping instanceof MapEnumElementType)) {
                    mapper.emit(Message.ERROR, "EnumPropertyMapper.UnknownEnum",  //$NON-NLS-1$
                            new Object[] { propertyId, propertyValue, 
                                mapper.mappingContext.getMemberName(), mapper.mappingContext.getComponent().getId() });
                } else {
                    mapper.emit(Message.ERROR, "EnumPropertyMapper.UnknownEnumInArray",  //$NON-NLS-1$
                        new Object[] { propertyValue, 
                            mapper.mappingContext.getComponent().getId() });
                }
                
                // assume existing value may be valid
                return existing;
            }
        }
        
        // we may generate the name via an algorithm, either for
        // a certain "unique" enum, or for all enums

        //IComponentInstance instance = mapper.mappingContext.getInstance();
        IComponentInstance instance = result.instance;
        if (propertyId.endsWith("->")) //$NON-NLS-1$
        	propertyId = "."; //$NON-NLS-1$
        
        if (me.getNameAlgorithm() != null && !me.getNameAlgorithm().equals("")) { //$NON-NLS-1$
            // See if this is used only for specific unique value...
            if (me.getUniqueValue() != null && exprValue.equals(me.getUniqueValue())) {
                // generate and register a unique name
            	return mapper.findOrCreateUniqueEnumeratorExpression(
                		me.getNameAlgorithm(), instance, propertyId);
            	
            }
            
            // ... else, we always generate a value from the algorithm.
            // We determine whether this enum should be unique by
            // whether uniqueValue is set (to "*")
            // 
            else if (me.getUniqueValue() != null && me.getUniqueValue().equals("*")) { //$NON-NLS-1$
                // generate and register a unique enumerator for any value
            	return mapper.findOrCreateUniqueEnumeratorExpression(
                        me.getNameAlgorithm(), instance, propertyId);
                
            } else if (me.getUniqueValue() == null) {
                // find the naming algorithm and generate a non-unique name
                String enumName = mapper.createEnumeratorName(
                        me.getNameAlgorithm(), instance, propertyId);
                if (enumName == null)
                    return null;

                return new AstIdExpression(new AstName(enumName, null));
            }
        }

        // remember the builtin enumerator for queries from one-way
        mapper.rssManipulator.getTypeHandler().registerEnumerator(instance.getName(), propertyId, me.getNameAlgorithm(), exprValue);

        return validateEnumerator(me, defaultInit, exprValue, canSkipDefault);
    }

    private IAstExpression validateEnumerator(MappingEnumType mapping, IAstExpression defaultInit, String exprValue, boolean canSkipDefault) {
        // find the enum in the globals
        IAstExpression expr = null;  
        IAstEnumerator emr = mapper.tu.findEnumerator(exprValue);
        if (emr != null) {
        	IAstLiteralExpression defaultValue = null;
        	if (emr.getEnumeration() != null)
        		defaultValue = emr.getEnumeration().getEffectiveValue(emr);
        		 
            // see if this name is the default value of the member
            if (canSkipDefault 
            	&& defaultInit != null) {
            	// compare by name
            	if (defaultInit instanceof IAstIdExpression
            			&& ((IAstIdExpression) defaultInit).getName().getName().equals(emr.getName().getName()))
            		return null;
            	
            	// compare by value
            	if (defaultInit instanceof IAstLiteralExpression
            			&& defaultValue != null
            			&& defaultInit.equalValue(defaultValue)) {
            		return null;
            	}
            }
            
            // or see if the enumerator is equivalent to zero,
            // which is what an uninitialized member has
            if (canSkipDefault 
            		&& defaultInit == null
            		&& defaultValue != null) {
            	if (defaultValue.getIntValue() == 0)
            		return null;
            }
                
            expr = new AstIdExpression(emr.getName());
        }
        else if (mapping.isValidate()) {
            // validate by default; can be turned off in case 
            // macros or expressions are mapped.
            if (mapping instanceof MapEnumMemberType) {
                MapEnumMemberType mem = (MapEnumMemberType) mapping;
                mapper.emit(Message.WARNING, "EnumPropertyMapper.PossiblyInvalidEnum",  //$NON-NLS-1$
                        new Object[] { mem.getProperty(), exprValue, 
                            mem.getMember(), mapper.mappingContext.getComponent().getId() });
            } else {
                mapper.emit(Message.WARNING, "EnumPropertyMapper.PossiblyInvalidEnumInArray",  //$NON-NLS-1$
                        new Object[] { exprValue, 
                        mapper.mappingContext.getComponent().getId() });
            }
            expr = new AstIdExpression(new AstName(exprValue, null));
        } else {
            expr = new AstPreprocessorTextNode(exprValue);
        }

        return expr;
    }

}
