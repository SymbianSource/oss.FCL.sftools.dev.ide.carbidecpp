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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;

/**
 * This is a single line in the PRJ_EXPORTS block
 *
 */
public interface IASTBldInfExportStatement extends IASTBldInfStatement {
	/** Get source path, may not be null */
	IASTLiteralTextNode getSourcePath();
	/** Set source path, may not be null */
	void setSourcePath(IASTLiteralTextNode path);
	/** Get target path, may be null */
	IASTLiteralTextNode getTargetPath();
	/** Set target path, may be null */
	void setTargetPath(IASTLiteralTextNode path);
	/** Get zip modifer, either “:zip” or null */
	IASTLiteralTextNode getZipModifier(); // may be null
	/** Set zip modifer, must read “:zip” or be null */
	void setZipModifier(IASTLiteralTextNode modifier); 

}
