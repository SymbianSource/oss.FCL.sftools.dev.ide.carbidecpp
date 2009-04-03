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
import com.nokia.sdt.emf.dm.impl.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.SAXXMLHandler;

import java.util.Map;

/**
 * This override of SAXXMLHandler is used to inform
 * EMF's parser about how to map our custom XML to
 * EMF structural features, and to hook up
 * property values as they are parsed from our custom XML
 *
 */
public class XMLHandlerNXD extends SAXXMLHandler {
	
		// attributes that don't map to structural features, need
		// to be tracked manually
	
			// value from StringValue type constants
	int pendingStringValueType = StringValue.LITERAL;
			// distinguish null from empty property value
	boolean pendingStringIsDefaultAttr;
	
	public XMLHandlerNXD(XMLResource xmiResource, XMLHelper helper, Map options) {
		super(xmiResource, helper, options);
	}
	
	private boolean isPropertyElementTag(String tag) {
		return Names.PROPERTY.equals(tag) ||
			 Names.COMPOUND_PROPERTY.equals(tag) ||
			 Names.SEQUENCE_PROPERTY.equals(tag);
	}
	
	private void establishPropertyContainer() {
		// In node elements property elements are at the same level as child
		// components. Therefore, the "top object" of the object stack is not
		// known to be a property container. So whenever a property element is
		// seen we have to establish the appropriate container on the stack.
		IPropertyContainer container = null;
		EObject peekObject = (EObject) objects.peek();
		if (peekObject instanceof INode) {
			container = ((INode)peekObject).getProperties();
		}
		else if (peekObject instanceof EStringToIPropertyValueMapEntryImpl) {
			// child property of a compound property. 
			EStringToIPropertyValueMapEntryImpl entry = (EStringToIPropertyValueMapEntryImpl) peekObject;
			IPropertyValue pv = entry.getTypedValue();
			Check.checkState(pv != null && pv.hasCompoundValue());
			container = pv.getCompoundValue();
		}
		else if (peekObject instanceof IPropertyValue) {
			IPropertyValue pv = (IPropertyValue) peekObject;
			container = pv.getCompoundValue();
		}
		else if (peekObject instanceof IDesignerData) {
			container = ((IDesignerData)peekObject).getProperties();
		}
		if (container != null)
			objects.push(container);
		// else, perhaps invalid XML document, and allow default EMF handling
	}
	
	private boolean preHandleFeature(String prefix, String name) {
		boolean handled = false;
		if (isPropertyElementTag(name)) {
			establishPropertyContainer();
		}
		else if (Names.ELEMENT.equals(name) || Names.COMPOUND_ELEMENT.equals(name)) {
			EObject peekObject = (EObject) objects.peek();
			if (peekObject instanceof EStringToIPropertyValueMapEntryImpl) {
				EStringToIPropertyValueMapEntryImpl entry = (EStringToIPropertyValueMapEntryImpl) peekObject;
				IPropertyValueImpl pv = (IPropertyValueImpl) entry.getTypedValue();
				Check.checkState(pv != null && pv.hasSequenceValue());
				objects.push(pv);
			}
		}		
		return handled;
	}
	
	private void postHandleFeature(String prefix, String name) {
		// post process certain elements to prepare to handle child elements
		EObject peekObject = (EObject) objects.peek();
		if (Names.COMPOUND_PROPERTY.equals(name)) {
			if (peekObject instanceof EStringToIPropertyValueMapEntryImpl) {
				EStringToIPropertyValueMapEntryImpl entry = (EStringToIPropertyValueMapEntryImpl) peekObject;
				IPropertyContainer parentContainer = peekPropertyContainer();
				if (parentContainer != null) {
					IPropertyValue pv = parentContainer.createPropertyContainerForProperty(entry.getKey());
					entry.setTypedValue(pv);
				}
			}
		}
		else if (Names.SEQUENCE_PROPERTY.equals(name)) {
			if (peekObject instanceof EStringToIPropertyValueMapEntryImpl) {
				EStringToIPropertyValueMapEntryImpl entry = (EStringToIPropertyValueMapEntryImpl) peekObject;
				IPropertyContainer parentContainer = peekPropertyContainer();
				if (parentContainer != null) {
					IPropertyValue pv = parentContainer.createSequenceForProperty(entry.getKey());
					entry.setTypedValue(pv);
				}
			}
		}
	}
	
	protected EObject createObjectFromFeatureType(EObject peekObject, EStructuralFeature feature) {
		EObject result = null;
		// For a compound element of a sequence property we need to set up the 
		// property value with the property container on creation.
		if (peekObject instanceof IPropertyValue &&
				feature.getFeatureID() == DmPackage.IPROPERTY_VALUE__SEQUENCE_VALUE &&
			Names.COMPOUND_ELEMENT.equals(elements.peek())) {
			IPropertyContainer parentContainer = peekPropertyContainer();
			if (parentContainer != null) {
				result = parentContainer.createPropertyContainerForProperty(null);
			}
			setFeatureValue(peekObject, feature, result);
		    processObject(result);
		}
		
		if (result == null)
			result = super.createObjectFromFeatureType(peekObject, feature);
		return result;
	}


	protected void handleFeature(String prefix, String name)
	{
		boolean handled = preHandleFeature(prefix, name);
		if (!handled)
			super.handleFeature(prefix, name);

		postHandleFeature(prefix, name);
	}
	
	private boolean decodeStringTypeAttr(String value) {
		boolean result = false;
		if (Names.SIMPLE_PROPERTY_TYPE_LITERAL.equals(value)) {
			pendingStringValueType = StringValue.LITERAL;
			result = true;
		}
		else if (Names.SIMPLE_PROPERTY_TYPE_I18N.equals(value)) {
			pendingStringValueType = StringValue.LOCALIZED;
			result = true;
		}
		else if (Names.SIMPLE_PROPERTY_TYPE_MACRO.equals(value)) {
			pendingStringValueType = StringValue.MACRO;
			result = true;
		}
		else if (Names.SIMPLE_PROPERTY_TYPE_REFERENCE.equals(value)) {
			pendingStringValueType = StringValue.REFERENCE;
			result = true;
		}
		return result;
	}
	
	protected void setAttribValue(EObject object, String name, String value) {
		boolean handled = false;
		if (object instanceof EStringToIPropertyValueMapEntryImpl) {
			if (Names.SIMPLE_PROPERTY_TYPE.equals(name)) {
				handled = decodeStringTypeAttr(value);
			}
		}
		else if (object instanceof EStringToStringMapEntryImpl) {
			if (Names.DEFAULT.equals(name)) {
				pendingStringIsDefaultAttr = Boolean.valueOf(value).booleanValue();
				handled = true;
			}
		}
		else if (object instanceof IPropertyValue) {
			if (Names.SIMPLE_PROPERTY_TYPE.equals(name)) {
				handled = decodeStringTypeAttr(value);
			}
		}
		
		if (!handled)
			super.setAttribValue(object, name, value);
	}

	String peekElement(int pos) {
		String result = null;
		if (elements.size() >= pos) 
			result = (String) elements.get(elements.size()-pos);
		return result;
	}
	
	Object peekObject(int pos) {
		Object result = null;
		if (objects.size() >= pos)
			result = objects.get(objects.size()-pos);
		return result;
	}
	
	IPropertyContainer peekPropertyContainer() {
		IPropertyContainer result = null;
		int i = objects.size();
		while (result == null && --i >= 0) {
			if (objects.get(i) instanceof IPropertyContainer)
				result = (IPropertyContainer) objects.get(i);
		}
		return result;
	}
	
	ILocalizedStringTable peekLocalizedStringTable() {
		ILocalizedStringTable result = null;
		int i = objects.size();
		while (result == null && --i >= 0) {
			if (objects.get(i) instanceof ILocalizedStringTable)
				result = (ILocalizedStringTable) objects.get(i);
		}
		return result;
	}
	
	IMacroStringTable peekMacroTable() {
		IMacroStringTable result = null;
		int i = objects.size();
		while (result == null && --i >= 0) {
			if (objects.get(i) instanceof IMacroStringTable)
				result = (IMacroStringTable) objects.get(i);
		}
		return result;
	}

	public void endElement(String uri, String localName, String name) {
		super.endElement(uri, localName, name);
		if (isPropertyElementTag(name)) {
			Object popped = objects.pop();
			Check.checkState(popped instanceof IPropertyContainer);
		}
		else if (Names.ELEMENT.equals(name) || Names.COMPOUND_ELEMENT.equals(name)) {
			Object popped = objects.pop();
			Check.checkState(popped instanceof IPropertyValue);
		}
		pendingStringValueType = StringValue.LITERAL;
		pendingStringIsDefaultAttr = false;
	}

	/*
	 * This method maps incoming XML elements to the EMF structural feature
	 * The object parameter is the EMF object that will contain the feature. The 
	 * name parameter is the element or attribute names
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLHandler#getFeature(org.eclipse.emf.ecore.EObject, java.lang.String, java.lang.String, boolean)
	 */
	protected EStructuralFeature getFeature(EObject object, String prefix, String name, boolean isElement) {
		EStructuralFeature result = null;
		if (isElement && Names.COMPONENT.equals(name)) {
			if (object instanceof IDesignerData) {
		//		result = DmPackage.eINSTANCE.getIDesignerData_RootNode();
			}
			else if (object instanceof INode) {
				result = DmPackage.eINSTANCE.getINode_Children();
			}
		}
		else if (isElement && isPropertyElementTag(name)) {
			if (object instanceof IPropertyContainer) {
				result = DmPackage.eINSTANCE.getIPropertyContainer_Properties();
			}
		}
		else if (!isElement && Names.ID.equals(name)) {
			if (object instanceof EStringToIPropertyValueMapEntryImpl) {
				result = DmPackage.eINSTANCE.getEStringToIPropertyValueMapEntry_Key();
			}
		}
		else if (isElement && 
				(Names.ELEMENT.equals(name) || Names.COMPOUND_ELEMENT.equals(name))) {
			if (object instanceof IPropertyValue) {
				result = DmPackage.eINSTANCE.getIPropertyValue_SequenceValue();
			}
		}
		
		if (result == null)
			result = super.getFeature(object, prefix, name, isElement);
		return result;
	}
	
	/*
	 * We override EMF's default behavior to emit simple property values as
	 * text content, i.e. within <property>...</property> tags, rather than
	 * as an attribute value. This override tells EMF to gather text content 
	 * and put it in the property value feature.
	 * We need to check that the current tag is not a <compoundProperty>, since
	 * they of course do not have mixed content.
	 */
	protected EStructuralFeature getContentFeature(EObject object) {
		EStructuralFeature result = null;
		if (object instanceof EStringToIPropertyValueMapEntryImpl && 
			Names.PROPERTY.equals(elements.peek())) {
			result = DmPackage.eINSTANCE.getEStringToIPropertyValueMapEntry_Value();
		}
		else if (object instanceof IPropertyValue &&
			Names.ELEMENT.equals(elements.peek())) {
			result = DmPackage.eINSTANCE.getIPropertyValue_StringValue();
		}
		else if (object instanceof EStringToStringMapEntryImpl &&
				 Names.STRING_ENTRY.equals(elements.peek())) {
			result = DmPackage.eINSTANCE.getEStringToStringMapEntry_Value();
		}
		else if (object instanceof IResourceMapping) {
			result = DmPackage.eINSTANCE.getIResourceMapping_Value();
		}
		else if (object instanceof IEnumMapping) {
			result = DmPackage.eINSTANCE.getIEnumMapping_Value();
		}
		else if (object instanceof IElementMapping) {
			result = DmPackage.eINSTANCE.getIElementMapping_Value();
		}
		
		if (result == null)
			result = super.getContentFeature(object);
		return result;
	}

	/*
	 * This method initializes and stores property values. Since we've simpified the XML output
	 * there isn't enough there for EMF to do it without some help.
	 * All other cases are passed through to EMF for default handling.
	 */
	protected void setFeatureValue(EObject object, EStructuralFeature feature, Object value, int position) {
		boolean handled = false;
		if (object instanceof INode) {
			if (value instanceof IPropertyContainer) {
				INode node = (INode) object;
				node.getProperties().setFromPropertyContainer((IPropertyContainer) value);
				handled = true;
			}
		}
		else if (object instanceof IPropertyContainer) {
			IPropertyContainer container = (IPropertyContainer) object;
			if (value instanceof EStringToIPropertyValueMapEntryImpl) {
				// Set a property in a property container. There are two cases
				// to worry about duplicate properties. One is the name property, were
				// a default has been set, and another is if the XML has duplicates.
				EStringToIPropertyValueMapEntryImpl entry = (EStringToIPropertyValueMapEntryImpl) value;
				EMap map = container.getProperties();
				map.removeKey(entry.getKey());
				container.getProperties().add(value);
				handled = true;
			}
			else if (feature == DmPackage.eINSTANCE.getEStringToIPropertyValueMapEntry_Key() &&
					 value instanceof String) {
				handled = true;
			}
		}
		else if (object instanceof EStringToIPropertyValueMapEntryImpl) {
			EStringToIPropertyValueMapEntryImpl objEntry = (EStringToIPropertyValueMapEntryImpl) object;
			if (value instanceof String) {
				if (feature.getName().equals("value")) {
					IPropertyValue pv = DmFactory.eINSTANCE.createIPropertyValue();
					StringValue sv = new StringValue(pendingStringValueType, value.toString());
					pendingStringValueType = -1;
					pv.setStringValue(sv);
					objEntry.setValue(pv);
					handled = true;
				}
			}
		}
		else if (object instanceof IPropertyValue) {
			if (value instanceof String) {
				StringValue sv = new StringValue(pendingStringValueType, value.toString());
				pendingStringValueType = -1;
				IPropertyValue pv = (IPropertyValue) object;
				pv.setStringValue(sv);
				handled = true;
			}
			else if (value instanceof IPropertyValue) {
				IPropertyValue objectPV = (IPropertyValue) object;
				if (feature.getFeatureID() == DmPackage.IPROPERTY_VALUE__SEQUENCE_VALUE && 
						objectPV.hasSequenceValue()) {
					objectPV.getSequenceValue().add(value);
					handled = true;
				}
			}
		}
		else if (object instanceof EStringToStringMapEntryImpl) {
			// this could be an entry for an ILocalizedStringTable or IMacroStringTable,
			// we handle them the same
			EStringToStringMapEntryImpl entry = (EStringToStringMapEntryImpl) object;
			if (value instanceof String) {
				if (feature.getName().equals("value")) {
					if (!pendingStringIsDefaultAttr) {
						String strValue = TextUtils.unescape(value.toString());
						entry.setTypedValue(strValue);
					}
					handled = true;					
				}
			}
		}
		
		if (!handled)
			super.setFeatureValue(object, feature, value, position);
	}
	
	

}
