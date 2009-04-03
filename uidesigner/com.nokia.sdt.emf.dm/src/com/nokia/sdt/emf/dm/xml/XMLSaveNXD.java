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

import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.emf.dm.impl.EStringToIPropertyValueMapEntryImpl;
import com.nokia.sdt.emf.dm.impl.EStringToStringMapEntryImpl;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.XMLSaveImpl;

import java.util.*;

/**
 * Override default EMF implementation to
 * perform XML customization not possible with
 * the EMF XMLMap
 */
public class XMLSaveNXD extends XMLSaveImpl {

	public XMLSaveNXD(Map options, XMLHelper helper, String encoding) {
		super(options, helper, encoding);
	}
	
	public XMLSaveNXD(XMLHelper helper) {
		super(helper);
	}
	
	/**
	 * All elements are passed through this method. We override
	 * to customize how properties are emitted.
	 */
	protected void saveElement(EObject o, EStructuralFeature f) {
		if (o instanceof IPropertyContainer) {
			savePropertyContainer((IPropertyContainer) o);
		}
		else if (o instanceof EStringToStringMapEntryImpl) {
			EStringToStringMapEntryImpl mapEntry = (EStringToStringMapEntryImpl) o;
			
			// only write owned strings
			Set<String> userStrings = null;
			if (o.eContainer() instanceof IMacroStringTable) {
				userStrings = ((IMacroStringTable) o.eContainer()).getUserGeneratedStringKeys();
			} else if (o.eContainer() instanceof ILocalizedStringTable 
					&& o.eContainer().eContainer() instanceof ILocalizedStringBundle) {
				userStrings = ((ILocalizedStringBundle) o.eContainer().eContainer()).getUserGeneratedStringKeys();
			}
			if (userStrings == null || !userStrings.contains(mapEntry.getKey()))
				saveStringMapEntry(mapEntry);
		}
		else
			super.saveElement(o, f);
	}
	
	private void saveStringMapEntry(EStringToStringMapEntryImpl entry) {
		// emit <string key="the key">the value</value>
		// optional attribute 'default' to distinguish
		// a null from an empty string. A null value indicates
		// the string value corresponds to the default property values
		// whereas an empty string means the user specified a zero-length
		// string.
		doc.startElement(Names.STRING_ENTRY);
		doc.addAttribute(Names.ID, entry.getTypedKey());
		String value = entry.getTypedValue();
		if (value == null) {
			doc.addAttribute(Names.DEFAULT, "true");
		}
		else if (value.length() > 0) {
			doc.setMixed(true);
			String txtValue = entry.getTypedValue();
			txtValue = TextUtils.escape(txtValue, '"');
			doc.addText(TextUtils.escapeXML(txtValue));
		}
		doc.endElement();
	}

	private void savePropertyContainer(IPropertyContainer container) {
		for (Iterator iter = container.getProperties().entrySet().iterator(); iter.hasNext();) {
			EStringToIPropertyValueMapEntryImpl entry = (EStringToIPropertyValueMapEntryImpl) iter.next();
			saveProperty(entry);
		}
	}
	
	private void saveProperty(EStringToIPropertyValueMapEntryImpl entry) {
		IPropertyValue pv = entry.getTypedValue();
		if (pv.hasStringValue()) {
			saveSimpleProperty(entry.getTypedKey(), pv);
		}
		else if (pv.hasCompoundValue()) {
			saveCompoundProperty(entry.getTypedKey(), pv);
		}
		else if (pv.hasSequenceValue()) {
			saveSequenceProperty(entry.getTypedKey(), pv);
		}
	}
	
	private void addStringValueAttribute(StringValue sv) {
		String typeAttrValue = null;
		switch (sv.getType()) {
			case StringValue.LOCALIZED:
				typeAttrValue = Names.SIMPLE_PROPERTY_TYPE_I18N;
				break;
			case StringValue.MACRO:
				typeAttrValue = Names.SIMPLE_PROPERTY_TYPE_MACRO;
				break;
			case StringValue.REFERENCE:
				typeAttrValue = Names.SIMPLE_PROPERTY_TYPE_REFERENCE;
				break;
		}
		if (typeAttrValue != null) {
			doc.addAttribute(Names.SIMPLE_PROPERTY_TYPE, typeAttrValue);
		}		
	}
	
	private void saveSimpleProperty(String propertyName, IPropertyValue value) {
		// emit <property id="the id">the value</property>
		// with an optional type attribute. Values are:
		// literal, i18n, macro, componentRef.
		// Default is literal, which is not emitted
		doc.startElement(Names.PROPERTY);
		doc.addAttribute(Names.ID, propertyName);
		addStringValueAttribute(value.getStringValue());
		savePropertyValue(value);
		doc.endElement();
	}
	private void saveCompoundProperty(String propertyName, IPropertyValue value) {
		// emit <compoundProperty id="the id">
		//			...nested property & compound properties
		//	    </compoundProperty>
		doc.startElement(Names.COMPOUND_PROPERTY);
		doc.addAttribute(Names.ID, propertyName);
		savePropertyValue(value);
		doc.endElement();
	}
	
	private void saveSequenceProperty(String propertyName, IPropertyValue value) {
		// emit <sequenceProperty id="this id">
				// contained properties bracketed by <element></element>
		// </sequenceProperty>
		doc.startElement(Names.SEQUENCE_PROPERTY);
		doc.addAttribute(Names.ID, propertyName);
		savePropertyValue(value);
		doc.endElement();
	}
	
	private void savePropertyValue(IPropertyValue value) {
		if (value.hasStringValue()) {
			doc.setMixed(true);
			String txtValue = value.getStringValue().getValue();
			doc.addText(TextUtils.escapeXML(txtValue));		
		}
		else if (value.hasCompoundValue()) {
			EMap properties = value.getCompoundValue().getProperties();
			for (Iterator iter = properties.iterator(); iter.hasNext();) {
				EStringToIPropertyValueMapEntryImpl entry = (EStringToIPropertyValueMapEntryImpl) iter.next();
				saveProperty(entry);
			}			
		}
		else if (value.hasSequenceValue()) {
			List sequence = value.getSequenceValue();
			for (Iterator iter = sequence.iterator(); iter.hasNext();) {
				IPropertyValue element = (IPropertyValue) iter.next();
				if (element.hasStringValue()) {
					doc.startElement(Names.ELEMENT);
					addStringValueAttribute(element.getStringValue());
					savePropertyValue(element);
					doc.endElement();
				}
				else if (element.hasCompoundValue()) {
					doc.startElement(Names.COMPOUND_ELEMENT);
					savePropertyValue(element);
					doc.endElement();
				}
			}
		}
	}
}
