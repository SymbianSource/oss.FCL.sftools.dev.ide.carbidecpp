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

import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.component.property.ISequencePropertySource;
import com.nokia.sdt.datamodel.util.NodePathLookupResult;
import com.nokia.sdt.emf.component.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTextNode;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IMacro;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Message;

import org.eclipse.ui.views.properties.IPropertySource;

public class SimplePropertyMapper implements IPropertyMapper {

    private InstanceSourceMapper mapper;

    public SimplePropertyMapper(InstanceSourceMapper mapper) {
        this.mapper = mapper;
    }

    private String getMemberName(TwoWayMappingType type) {
        if (type instanceof MapSimpleMemberType)
            return ((MapSimpleMemberType) type).getMember();
        else if (type instanceof MapSimpleElementType) 
            return Messages.getString("SimplePropertyMapper.ArrayElement"); //$NON-NLS-1$
        return "<???>"; //$NON-NLS-1$
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
        if (memberKind == IAstSimpleDeclaration.K_STRUCT
                || memberKind == IAstSimpleDeclaration.K_LINK
                || memberKind == IAstSimpleDeclaration.K_LLINK) {
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
        
        switch (type.getKind()) {
        case IAstSimpleDeclaration.K_BYTE:
        case IAstSimpleDeclaration.K_WORD:
        case IAstSimpleDeclaration.K_LONG:
        {
            String value = property.toString();
            
            // check for booleans
            if (value.equals("true")) //$NON-NLS-1$
                value = "1"; //$NON-NLS-1$
            else if (value.equals("false")) //$NON-NLS-1$
                value = "0"; //$NON-NLS-1$
            
            if (canSkipDefault) {
                try {
                    // check for enum matching
                    if (defaultInit instanceof IAstIdExpression) {
	                    IAstEnumerator emr = mapper.tu.findEnumerator(((IAstIdExpression)defaultInit).getName().getName());
	                    if (emr != null) {
	                    	// same enum value?
	                    	if (emr.getName().getName().equals(value)) {
	                    		return null;
	                    	}
	                    	// or see if the int property holds the enum's actual value
	                    	if (emr.getEnumeration() != null)
	                    		defaultInit = emr.getEnumeration().getEffectiveValue(emr);
	                    }
                    } else if (defaultInit != null) {
                    	defaultInit = defaultInit.simplify();
                    }
                    
                    // check equivalence to default
                    long currentInt = Long.parseLong(value);
                    if (defaultInit == null && currentInt == 0)
                        return null;
                    if (defaultInit != null && defaultInit instanceof IAstLiteralExpression
                        && ((IAstLiteralExpression) defaultInit).getIntValue() == currentInt)
                    	return null;

                } catch (NumberFormatException e) {
                    // whatever it is, it's different
                }
            }
            try {
                return new AstLiteralExpression(IAstLiteralExpression.K_INTEGER, value);
            } catch (IllegalArgumentException e) {
            	// check for enum matching, which is allowed, before complaining
                IAstEnumerator emr = mapper.tu.findEnumerator(value);
                if (emr == null) {
	                mapper.emit(Message.WARNING, 
	                        "SimplePropertyMapper.BadIntValue", //$NON-NLS-1$
	                        new Object[] { 
	                        value, propertyId,
	                        getMemberName(mapping),
	                        mapper.mappingContext.getComponent().getId()
	                        });
                }
                return new AstPreprocessorTextNode(value);
            }
        }
        
        case IAstSimpleDeclaration.K_DOUBLE:
        {
            String value = property.toString();

            if (canSkipDefault) {
                try {
                   // check equivalence to default
                    double currentDbl = Double.parseDouble(value);
                    if (defaultInit == null && currentDbl == 0)
                        return null;
                    if (defaultInit != null) {
                    	defaultInit = defaultInit.simplify();
                    	if (defaultInit instanceof IAstLiteralExpression
                            && ((IAstLiteralExpression) defaultInit).getFloatValue() == currentDbl)
                    		return null;
                    }
                } catch (NumberFormatException e) {
                    // whatever it is, it's different
                }
            }
            try {
                return new AstLiteralExpression(IAstLiteralExpression.K_FLOAT, value);
            } catch (IllegalArgumentException e) {
                mapper.emit(Message.WARNING, 
                        "SimplePropertyMapper.BadFloatValue", //$NON-NLS-1$
                        new Object[] { 
                        value, propertyId,
                        getMemberName(mapping),
                        mapper.mappingContext.getComponent().getId()
                        });
                return new AstPreprocessorTextNode(value);
            }
                
        }
        
        case IAstSimpleDeclaration.K_BUF:
        case IAstSimpleDeclaration.K_BUF8:
        case IAstSimpleDeclaration.K_LTEXT:
        case IAstSimpleDeclaration.K_LTEXT16:
        case IAstSimpleDeclaration.K_TEXT:
        case IAstSimpleDeclaration.K_TEXT16:
        case IAstSimpleDeclaration.K_LTEXT8:
        {
            // check for localized strings
            String symbol = null;
            if (result.properties instanceof IPropertyInformation) {
                IPropertyInformation pi = (IPropertyInformation) result.properties;
                symbol = pi.getPropertyValueSymbol(result.propertyName);
            }
            
            String value = property.toString();
            if (canSkipDefault && symbol == null) {
                if (defaultInit == null && value.equals("")) //$NON-NLS-1$
                    return null;
                if (defaultInit != null && defaultInit instanceof IAstLiteralExpression
                        && ((IAstLiteralExpression) defaultInit).getStringValue().equals(value))
                    return null;
            }
            
            if (symbol != null) {
                // Emit a macro reference.  For now, just make a
                // new orphan macro instead of trying to find the
                // "right" one (there are multiple definitions depending
                // on the default language).
                
                // Note: ITranslationUnit#importLocalizedStrings() 
                // does real the macro definition / loc file work.
                IMacro macro = new ObjectStyleMacro(symbol, 
                        AstLiteralExpression.quoteForRss(value, '"')); //$NON-NLS-1$ //$NON-NLS-2$
                return new AstPreprocessorMacroExpression(macro, 
                        new IAstPreprocessorTextNode[0]); 
            }
            else {
                return new AstLiteralExpression(IAstLiteralExpression.K_STRING, value);
            }
        }
        
        case IAstSimpleDeclaration.K_LINK:
        case IAstSimpleDeclaration.K_LLINK:
        case IAstSimpleDeclaration.K_STRUCT:
        case IAstSimpleDeclaration.K_SRLINK:
        default:
            // shouldn't get here
            Check.checkState(false);
            return null;
        }
    }

}
