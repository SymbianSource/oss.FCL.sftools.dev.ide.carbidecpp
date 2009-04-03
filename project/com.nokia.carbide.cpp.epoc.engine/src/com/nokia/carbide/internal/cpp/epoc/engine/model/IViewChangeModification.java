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
* In a change, the nodes returned from @link{#getNodes()} are the
* original language nodes but are modified in place.  If necessary
* these may be duplicated but the original source nodes must still
* be valid.  If, for instance, a language node is changed by splitting
* it in two, only one may reference the original source nodes.
*
*
*/
package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
public interface IViewChangeModification extends IViewModification {
	/** 
	 * Get the parent into which to move the node(s).  References
	 * to an IASTTranslationUnit are aliased to that TU's #getNodes() node
	 * for convenience.
	 * <p>
	 * The parent may be a specific preparser DOM node (usually the
	 * translation unit) in order to add content to a specific file.
	 * <p>
	 * <code>null</code> is also allowed.
	 * <p>
	 * Note: currently we only allow the translation unit or its children
	 * to be the parent.  It is assumed that the language node can represent
	 * hierarchical trees and handle updates of its language children 
	 * appropriately.
	 * @return language node or preparser node or <code>null</code>
	 */
	IASTNode getParent();
	
	/**
	 * Get the replacement node(s).  This may return null to replace the existing
	 * @link{#getNodes()} in place.  It may also return nodes present in the @link{#getNodes()} list.
	 * @return new list of nodes to replace, or null for replace existing
	 */
	IASTNode[] getReplacementNodes();
}
