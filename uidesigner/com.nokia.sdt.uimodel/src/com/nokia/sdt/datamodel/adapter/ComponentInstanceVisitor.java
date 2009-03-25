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

package com.nokia.sdt.datamodel.adapter;

import com.nokia.sdt.datamodel.util.ModelUtils;

import org.eclipse.emf.ecore.EObject;

public abstract class ComponentInstanceVisitor {
	
	public interface Visitor {

		Object visit(IComponentInstance ci);
	}
	
	public static Object preOrderTraversal(EObject root, Visitor v) {
		Object result = null;
		IComponentInstance rootCI = ModelUtils.getComponentInstance(root);
		if (rootCI != null) {
			result = v.visit(rootCI);
			if (result == null) {
				EObject[] children = rootCI.getChildren();
				if (children != null) {
					for (int i = 0; i < children.length; i++) {
						result = preOrderTraversal(children[i], v);
						if (result != null)
							break;
					}
				}
			}
		}
		return result;
	}
	
	public static Object preOrderTraversal(EObject roots[], Visitor v) {
		Object result = null;
		for (int i = 0;  i < roots.length; i++) {
			result = preOrderTraversal(roots[i], v);
			if (result != null)
				break;
		}
		return result;
	}

}
