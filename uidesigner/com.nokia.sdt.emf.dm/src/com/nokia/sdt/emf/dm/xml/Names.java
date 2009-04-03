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
package com.nokia.sdt.emf.dm.xml;


/**
 * Element and attribute names used in data model XML
 */
public interface Names {

	public static final String DESIGNER_DATA = "designerData"; //$NON-NLS-1$
	public static final String ID = "id"; //$NON-NLS-1$
	public static final String COMPONENT = "component"; //$NON-NLS-1$
	public static final String PROPERTY = "property"; //$NON-NLS-1$
	public static final String COMPOUND_PROPERTY = "compoundProperty"; //$NON-NLS-1$
	public static final String SEQUENCE_PROPERTY = "sequenceProperty"; //$NON-NLS-1$
	public static final String ELEMENT = "element"; //$NON-NLS-1$
	public static final String COMPOUND_ELEMENT = "compoundElement"; //$NON-NLS-1$
	public static final String LOCALIZED_STRING_TABLE = "stringTable"; //$NON-NLS-1$
	public static final String STRING_ENTRY = "string"; //$NON-NLS-1$
	public static final String SIMPLE_PROPERTY_TYPE = "type"; //$NON-NLS-1$
	public static final String SIMPLE_PROPERTY_TYPE_LITERAL = "literal"; //$NON-NLS-1$
	public static final String SIMPLE_PROPERTY_TYPE_I18N = "i18n"; //$NON-NLS-1$
	public static final String SIMPLE_PROPERTY_TYPE_MACRO = "macro"; //$NON-NLS-1$
	public static final String SIMPLE_PROPERTY_TYPE_REFERENCE = "componentRef"; //$NON-NLS-1$
	public static final String DEFAULT = "default"; //$NON-NLS-1$
	public static final String EVENT_BINDING = "eventBinding";//$NON-NLS-1$
	public static final String EVENT_BINDING_HANDLER_DISPLAY_NAME = "handlerDisplay";//$NON-NLS-1$
	public static final String EVENT_BINDING_HANDLER_SYMBOL = "handlerSymbol";//$NON-NLS-1$
	public static final String COMPONENT_MANIFEST_ENTRY = "manifestEntry";//$NON-NLS-1$
	public static final String GENERATED_FILES_ENTRY = "file";//$NON-NLS-1$
	
	public static final String RESOURCE_MAPPING = "resourceMapping"; //$NON-NLS-1$
	public static final String ENUM_MAPPING = "enumMapping";
	public static final String ARRAY_MAPPING = "arrayMapping";
	public static final String ELEMENT_MAPPING = "arrayElement";
}
