/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.sdt.component.property.IPropertyValueSource;
import com.nokia.sdt.emf.dm.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.util.EList;

import java.util.*;

/**
 * This class saves and restores references to a node.
 * Use case - 
 * A node is removed and all nodes that reference the node are cleared.
 * At some point later, the node is restored, so references to the node must be restored.
 * This class does not assume that the referencing object still exists, 
 * so it stores names of nodes and names of properties.
 */
public class NodeReferencesRestorer {
	
	private List referenceInfos;
	private String referentName;
	
	class ReferenceInfo {
		private String nodeName;
		private String propertyName;
		
		public ReferenceInfo(String nodeName, String propertyName) {
			this.nodeName = nodeName;
			this.propertyName = propertyName;
		}

		public String getNodeName() {
			return nodeName;
		}

		public String getPropertyName() {
			return propertyName;
		}
	}

	class SaveNodeVisitor implements INodeVisitor {

		private NodeReferencesRestorer restorer;
		
		public SaveNodeVisitor(NodeReferencesRestorer restorer) {
			this.restorer = restorer;
		}

		public Object visit(INode node) {
			IPropertyContainer properties = node.getProperties();
			properties.visitProperties(new SavePropertyVisitor(restorer, node.getName()));
			return null;
		}
	}
	
	class SavePropertyVisitor implements IPropertyVisitor {
		
		private NodeReferencesRestorer restorer;
		private String nodeName;
		
		public SavePropertyVisitor(NodeReferencesRestorer restorer, String nodeName) {
			this.restorer = restorer;
			this.nodeName = nodeName;
		}
		
		public Object visit(IPropertyContainer container, EStringToIPropertyValueMapEntryImpl entry) {
			IPropertyValue pv = entry.getTypedValue();
			// We need to check for strings referencing the name,
			// both as simple values and sequences. Compound property 
			// values will be handled as the visitor recurses.
			if (pv.hasStringValue()) {
				StringValue currStringValue = pv.getStringValue();
				if (currStringValue.isReference() &&
					currStringValue.getValue().equals(restorer.getReferentName())) {
					restorer.storeReference(nodeName, entry.getTypedKey());
				}
			}
			else if (pv.hasSequenceValue()) {
				EList l = pv.getSequenceValue();
				for (ListIterator iter = l.listIterator(); iter.hasNext();) {
					IPropertyValue seqPV = (IPropertyValue) iter.next();
					if (seqPV.hasStringValue()) {
						StringValue currStringValue = seqPV.getStringValue();
						if (currStringValue.isReference() &&
								currStringValue.getValue().equals(getReferentName())) {
							restorer.storeReference(nodeName, entry.getTypedKey());
						}
					}
				}
			}
			return null;
		}
	}
	
	class RestoreNodeVisitor implements INodeVisitor {

		private NodeReferencesRestorer restorer;
		
		public RestoreNodeVisitor(NodeReferencesRestorer restorer) {
			this.restorer = restorer;
		}
		
		public Object visit(INode node) {
			List referenceInfos = restorer.getReferenceInfosForNodeName(node.getName());
			if (referenceInfos != null) {
				IPropertyValueSource valueSource = node.getProperties().getPropertyValueSource();
				Check.checkState(valueSource != null);
				for (Iterator iter = referenceInfos.iterator(); iter.hasNext();) {
					ReferenceInfo info = (ReferenceInfo) iter.next();
					valueSource.setReferencePropertyValue(info.getPropertyName(), referentName);
				}
			}
			return null;
		}
	}
	
	protected NodeReferencesRestorer(INode root, INode referentNode) {
		this.referentName = referentNode.getName();
		root.visitPreorder(new SaveNodeVisitor(this));
	}
	
	public String getReferentName() {
		return referentName;
	}

	public List getReferenceInfosForNodeName(String name) {
		List infos = null;
		for (Iterator iter = referenceInfos.iterator(); iter.hasNext();) {
			ReferenceInfo info = (ReferenceInfo) iter.next();
			if (info.getNodeName().equals(name)) {
				if (infos == null)
					infos = new ArrayList();
				infos.add(info);
			}
		}
		return infos;
	}

	public void storeReference(String nodeName, String propertyName) {
		if (referenceInfos == null)
			referenceInfos = new ArrayList();
		
		referenceInfos.add(new ReferenceInfo(nodeName, propertyName));
	}
	
	public void restoreReferences(INode root) {
		if (hasReferences()) {
			root.visitPreorder(new RestoreNodeVisitor(this));
		}
	}

	/**
	 * @param root the INode root for the reference search
	 * @param referentNode
	 * @return a new NodeReferences object containing the references or null if none exist
	 */
	public static NodeReferencesRestorer getReferencesForNode(INode root, INode referentNode) {
		NodeReferencesRestorer restorer = new NodeReferencesRestorer(root, referentNode);
		if (restorer.hasReferences())
			return restorer;
		
		return null;
	}

	private boolean hasReferences() {
		return referenceInfos != null;
	}
}
