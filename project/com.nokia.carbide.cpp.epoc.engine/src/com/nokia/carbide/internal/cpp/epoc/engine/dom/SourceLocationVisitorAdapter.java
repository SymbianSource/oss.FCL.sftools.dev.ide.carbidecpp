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
* Default implementation of {@link ISourceRegionVisitor} which iterates all the locations.
*
*
*/
package com.nokia.carbide.internal.cpp.epoc.engine.dom;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IMultiDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegionVisitor;
public class SourceLocationVisitorAdapter implements ISourceRegionVisitor {

	public int visit(IDocumentSourceRegion region) {
		return VISIT_CHILDREN;
	}

	public int visit(IMultiDocumentSourceRegion region) {
		return VISIT_CHILDREN;
	}

	public int visit(ISourceRegion region) {
		return VISIT_CHILDREN;
	}

}
