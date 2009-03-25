/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;

/**
 * AIF statement.<p>
 * The bitmaps are represented in the remaining list arguments
 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPListArgumentStatement#getArguments()
 *
 */
public interface IASTMMPAifStatement extends IASTMMPListArgumentStatementBase {
	public static final String AIF_KEYWORD = "AIF"; //$NON-NLS-1$
	
	/** Get the target filename.  Never null. */
	IASTLiteralTextNode getTargetFile();
	/** Set the target filename.  May not be null. */
	void setTargetFile(IASTLiteralTextNode targetFile);
	/** Get MMP-relative source path, with trailing backslash, for finding resource and bitmaps mentioned */
	IASTLiteralTextNode getSourcePath();
	void setSourcePath(IASTLiteralTextNode sourcePath);
	/** Get the resource filename.  Never null. */
	IASTLiteralTextNode getResource();
	/** Set the resource filename.  May not be null. */
	void setResource(IASTLiteralTextNode resource);
	/** Get color depth, may be null */
	IASTLiteralTextNode getColorDepth();
	/** Set color depth, may be null */
	void setColorDepth(IASTLiteralTextNode depth);
}
