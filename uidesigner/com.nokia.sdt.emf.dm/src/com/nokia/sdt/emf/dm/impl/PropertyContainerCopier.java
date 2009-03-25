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
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.component.property.IPropertyValueSource.UndoValue;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.emf.dm.util.PropertyValueSwitch;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.*;

/**
 * Utility class that can copy property values in one data model
 * to a container in another model, and then restore the values
 * in support of undo.
 * 
 *
 */
public class PropertyContainerCopier implements UndoValue {
	
	private IPropertyContainer originalContainer;
	private IPropertyContainer copyContainer;
	
	private IDesignerData localModelRoot;
	
	private static final String PROPERTY_ID = "copiedValue";
	
	/**
	 * Construct an instance that copies property values from
	 * a source IPropertyContainer to an existing destination
	 * container in another, existing model.
	 */
	public PropertyContainerCopier(IPropertyContainer srcContainer,
			IPropertyContainer destContainer, boolean validateReferences, 
			boolean preserveLocalizedStringKeys) {
		Check.checkArg(srcContainer);
		Check.checkArg(destContainer);
		this.originalContainer = srcContainer;
		this.copyContainer = destContainer;
		
		copyValues(originalContainer, copyContainer, validateReferences, preserveLocalizedStringKeys);
	}

	/**
	 * Constructs an instance that copies property values
	 * from a source IPropertyContainer to an internally
	 * created temporary model. Useful mostly for undo.
	 */
	public PropertyContainerCopier(IPropertyContainer srcContainer) {
		Check.checkArg(srcContainer);
		this.originalContainer = srcContainer;
		
		// set up a simple temporary model with a simple root object
		// to hold the value we're copying
		localModelRoot = DmFactory.eINSTANCE.createIDesignerData();
		INode node = DmFactory.eINSTANCE.createINode();
		localModelRoot.getRootContainers().add(node);
		
		// setup a compound property nameed PROPERTY_ID to hold the
		// copied property values
		IPropertyValue pv = node.getProperties().createPropertyContainerForProperty(PROPERTY_ID);
		
		copyContainer = pv.getCompoundValue();
		
		copyValues(originalContainer, copyContainer, false, true);
	}
	
	/**
	 * Retrieve the create copy
	 */
	public IPropertyContainer getCopy() {
		return copyContainer;
	}
	
	/**
	 * Undo any changes to the container. We restore
	 * the previous values and delete any new values.
	 * @param validateReferences true if component reference properties should be
	 * validated. If false they are copied without validation
	 * @param preserveLocalizedStringKeys if true, keys (macro names) for localized
	 * strings are maintained if not present in the destination. If false, or when
	 * the key is already is in use, a new key is generated in the destination.
	 */
	public void undo(boolean validateReferences, boolean preserveLocalizedStringKeys) {
		removeLocalizedStringsAndMacros(originalContainer);
		copyValues(copyContainer, originalContainer, validateReferences, preserveLocalizedStringKeys);
		removeExtraValues(copyContainer, originalContainer);
	}
	
	private void removeLocalizedStringsAndMacros(IPropertyContainer container) {
		container.visitProperties(new IPropertyVisitor() {
			public Object visit(IPropertyContainer container, EStringToIPropertyValueMapEntryImpl entry) {
				IPropertyValue value = entry.getTypedValue();
				if (value.hasStringValue()) {
					StringValue stringValue = value.getStringValue();
					if (stringValue.isLocalized() || stringValue.isMacro()) {
						container.releasePropertyValue(value);
					}
				}
				return null;
			}
		});
	}
	
	private void removeExtraValues(IPropertyContainer srcContainer, IPropertyContainer destContainer) {
		// not using an iterator over the keySet here because it seems to be buggy, returns null inappropriately
		Object[] keys = destContainer.getProperties().keySet().toArray();
		for (Object key : keys) {
			if (!srcContainer.getProperties().containsKey(key)) {
				destContainer.getProperties().removeKey(key);
			}
		}	
	}
	
	private StringValue copyStringValue(IDesignerData srcRoot, StringValue srcValue,
					IDesignerData destRoot, boolean validateReferences, boolean preserveLocalizedStringKeys) {
		StringValue result = null;
		if (srcValue.isLiteral()) {
			result = new StringValue(StringValue.LITERAL, srcValue.getValue());
		}
		else if (srcValue.isReference()) {
			if (validateReferences) {
				// only copy value if it exists in the destination
				INode foundNode = destRoot.findByNameProperty(srcValue.getValue());
				if (foundNode != null) 
					result = new StringValue(StringValue.REFERENCE, srcValue.getValue());
			}
			else {
				result = new StringValue(StringValue.REFERENCE, srcValue.getValue());
			}
		}
		else if (srcValue.isLocalized()) {
			ILocalizedStringBundle srcBundle = srcRoot.getStringBundle();
			ILocalizedStringBundleImpl destBundle = 
				(ILocalizedStringBundleImpl) destRoot.getStringBundle();
			String key = srcValue.getValue();
			Map map = srcBundle.findStrings(key);
			Check.checkContract(map != null);
			if (!preserveLocalizedStringKeys || 
					((destBundle.getLocalizedStringTables() != null) && destBundle.hasStringKey(key)))
				key = destBundle.getKeyProvider().assignLocalizedStringKey();
			for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
				Map.Entry entry = (Map.Entry) iter.next();
				destBundle.registerString((Language)entry.getKey(), 
						key, (String)entry.getValue());
			}
			result = new StringValue(StringValue.LOCALIZED, key);
		}
		else if (srcValue.isMacro()) {
			IMacroStringTable srcMacroTable = srcRoot.getMacroTable();
			String macroValue = (String) srcMacroTable.getStringMacros().get(srcValue.getValue());
			IMacroStringTable destMacroTable = destRoot.getMacroTable();
			if (destMacroTable.getStringMacros().containsKey(srcValue.getValue()))
				result = destMacroTable.addMacro(macroValue);
			else
				result = destMacroTable.addMacroWithKey(srcValue.getValue(), macroValue);
			// Returns null if key is defined with a different value. 
			if (result == null) {
				result = new StringValue(StringValue.MACRO, srcValue.getValue());
				destMacroTable.updateMacro(result, macroValue);
			}
			Check.checkState(result != null);
		}
		else
			throw new IllegalStateException();
		return result;
	}
	
	private IDesignerData getDesignerData(IPropertyContainer container) {
		IDesignerData result = ((IPropertyContainerImpl)container).getDesignerData();
		Check.checkState(result != null);
		return result;
	}
	
	private void copyValues(final IPropertyContainer fromContainer, final IPropertyContainer toContainer, 
			final boolean validateReferences, final boolean preserveLocalizedStringKeys) {
		// turn off notifications until copy is complete
		boolean saveDeliver = toContainer.eDeliver();
		toContainer.eSetDeliver(false);
		
		for (Iterator iter = fromContainer.getProperties().keySet().iterator(); iter.hasNext();) {
			final String key = (String) iter.next();
			final IPropertyValue srcValue = (IPropertyValue) fromContainer.getProperties().get(key);
			
			PropertyValueSwitch pswitch = new PropertyValueSwitch() {
				protected Object handleStringValue(IPropertyValue pv) {
					StringValue newSV = copyStringValue(getDesignerData(fromContainer),
							pv.getStringValue(), getDesignerData(toContainer), 
							validateReferences, preserveLocalizedStringKeys);
						if (newSV != null) {
						IPropertyValue newPV = DmFactory.eINSTANCE.createIPropertyValue();
						newPV.setStringValue(newSV);
						// We are specifically not checking existing format, but
						// setting the format to whatever it is in the from container
						toContainer.getProperties().put(key, newPV);
					}
					return null;
				}

				protected Object handleCompoundValue(IPropertyValue pv) {
					IPropertyValue newPV = toContainer.createPropertyContainerForProperty(key);
					toContainer.getProperties().put(key, newPV);
					copyValues(pv.getCompoundValue(), newPV.getCompoundValue(), 
							validateReferences, preserveLocalizedStringKeys);
					return null;
				}

				protected Object handleSequenceValue(IPropertyValue pv) {
					IPropertyValue newPV = toContainer.createSequenceForProperty(key);
					List fromList = pv.getSequenceValue();
					List toList = newPV.getSequenceValue();
					for (Iterator iter = fromList.iterator(); iter.hasNext();) {
						IPropertyValue element = (IPropertyValue) iter.next();
						if (element.hasStringValue()) {
							StringValue newSV = copyStringValue(getDesignerData(fromContainer),
									element.getStringValue(), getDesignerData(toContainer), 
									validateReferences, preserveLocalizedStringKeys);
							IPropertyValue newElementPV = DmFactory.eINSTANCE.createIPropertyValue();
							newElementPV.setStringValue(newSV);
							toList.add(newElementPV);
						}
						else if (element.hasCompoundValue()) {
							IPropertyValue newElementPV = toContainer.createPropertyContainerForProperty(null);
							copyValues(element.getCompoundValue(), newElementPV.getCompoundValue(), 
									validateReferences, preserveLocalizedStringKeys);
							toList.add(newElementPV);
						}
					}
					toContainer.getProperties().put(key, newPV);
					return null;
				}				
			};
			
			pswitch.doSwitch(srcValue);
		}
		
		// reset notifications
		toContainer.eSetDeliver(saveDeliver);
	}

}
	