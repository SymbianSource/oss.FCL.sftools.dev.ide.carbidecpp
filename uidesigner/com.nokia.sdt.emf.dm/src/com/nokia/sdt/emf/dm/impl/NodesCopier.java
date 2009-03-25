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

import com.nokia.sdt.emf.dm.IDesignerData;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.sdt.emf.dm.impl.NodeCopier.IFilter;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.*;

public class NodesCopier {
	
	private List nodeCopiers;
	
	public NodesCopier(List srcNodes, boolean preserveEventBindings, IFilter filter) {
		Check.checkState(srcNodes != null);
		// make an array of NodeCopier
		nodeCopiers = new ArrayList();
		// share the same IDesignerData for all of them
		IDesignerData modelRoot = null;
		for (Iterator iter = srcNodes.iterator(); iter.hasNext();) {
			INode node = (INode) iter.next();
			// the first one creates the IDesignerData
			if (modelRoot == null) {
				NodeCopier copier = new NodeCopier(node, preserveEventBindings, filter);
				modelRoot = copier.getRoot();
				nodeCopiers.add(copier);
			}
			else {
				nodeCopiers.add(new NodeCopier(modelRoot, node, true, filter));
			}
		}
	}
	
	public NodesCopier(List srcNodes) {
		this(srcNodes, false, null);
	}

	public INode getCopy(int index) {
		Check.checkState(nodeCopiers != null);
		Check.checkState(index < nodeCopiers.size());
		return ((NodeCopier) nodeCopiers.get(index)).getCopy();
	}
	
	public List<INode> getCopies() {
		List<INode> copies = new ArrayList();
		for (Iterator<NodeCopier> iter = nodeCopiers.iterator(); iter.hasNext();) {
			copies.add(iter.next().getCopy());
		}
		
		return copies;
	}
}
