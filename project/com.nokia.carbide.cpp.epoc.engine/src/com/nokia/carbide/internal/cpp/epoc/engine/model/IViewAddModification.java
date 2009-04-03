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
package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.cpp.internal.api.utils.core.*;

public interface IViewAddModification extends IViewModification {
	/** 
	 * Get the parent into which to add the new node(s).  References
	 * to an IASTTranslationUnit are aliased to that TU's #getNodes() node
	 * for convenience.
	 * <p>
	 * The parent may be a specific preparser DOM node (usually the
	 * translation unit) in order to add content to a specific file.
	 * 
	 * @return language node or preparser node
	 */
	IASTNode getParent();
	
	/** Get the nodes between which to add the modification.  This may
	 * represent an enclosed addition, an addition before a node,
	 * an addition after a node, or an addition anywhere.
	 *
	 * @return pair of nodes, where the first is the 'before' node, or
	 * null, and the second is the 'after' node, or null.  If both are
	 */
	Pair<IASTNode, IASTNode> getInsertAnchors();
}
