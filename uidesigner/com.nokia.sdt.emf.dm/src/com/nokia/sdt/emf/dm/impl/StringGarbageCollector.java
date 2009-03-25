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

import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.emf.dm.util.PropertyValueSwitch;

import java.util.*;

public class StringGarbageCollector {
	
	private Set referencedLocalized = new HashSet();
	private Set referencedMacros = new HashSet();
	private NodeVisitor nodeVisitor = new NodeVisitor();
	private PropertyVisitor propertyVisitor = new PropertyVisitor();
	
	class NodeVisitor implements INodeVisitor {
		
		public Object visit(INode node) {
			node.getProperties().visitProperties(propertyVisitor);
			return null;
		}
	}
	
	class PropertyVisitor implements IPropertyVisitor {

		public Object visit(IPropertyContainer container, EStringToIPropertyValueMapEntryImpl entry) {
			PropertyValueSwitch pvs = new PropertyValueSwitch() {
				@Override
				protected Object handleStringValue(IPropertyValue pv) {
					checkString(pv.getStringValue());
					return null;
				}

				@Override
				protected Object handleCompoundValue(IPropertyValue pv) {
					pv.getCompoundValue().visitProperties(PropertyVisitor.this);
					return null;
				}

				@Override
				protected Object handleSequenceValue(IPropertyValue pv) {
					for (Iterator iter = pv.getSequenceValue().iterator(); iter.hasNext();) {
						IPropertyValue seqPV = (IPropertyValue) iter.next();
						if (seqPV.hasStringValue()) {
							checkString(seqPV.getStringValue());
						} else if (seqPV.hasCompoundValue()) {
							seqPV.getCompoundValue().visitProperties(PropertyVisitor.this);
						}
					}
					return null;
				}
			};
			
			pvs.doSwitch(entry.getTypedValue());
			return null;
		}
	
		private void checkString(StringValue sv) {
			int type = sv.getType();
			if (type == StringValue.LOCALIZED) {
				referencedLocalized.add(sv.getValue());
			}
			else if (type == StringValue.MACRO) {
				referencedLocalized.add(sv.getValue());
			}
		}
	}
	
	public void gc(IDesignerData root) {
		root.visitNodes(nodeVisitor);
		
		Set unreferenced = new HashSet();
		ILocalizedStringBundle stringBundle = root.getStringBundle();
		if (stringBundle.getLocalizedStringTables().size() > 0) {
			ILocalizedStringTable lst = (ILocalizedStringTable) stringBundle.getLocalizedStringTables().get(0);
			for (Iterator iter = lst.getStrings().keySet().iterator(); iter.hasNext();) {
				Object key = iter.next();
				if (!referencedLocalized.contains(key)) {
					unreferenced.add(key);
				}
			}
			
			for (Iterator iter = unreferenced.iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				if (!stringBundle.getUserGeneratedStringKeys().contains(key))
					stringBundle.removeLocalizedStringAllLanguages(key);
			}
		}
		
		unreferenced.clear();
		IMacroStringTable macroTable = root.getMacroTable();
		for (Iterator iter = macroTable.getStringMacros().keySet().iterator(); iter.hasNext();) {
			Object key = iter.next();
			if (!referencedMacros.contains(key) 
				&& !macroTable.getUserGeneratedStringKeys().contains(key)) {
				iter.remove();
			}		
		}
	
	}

}
