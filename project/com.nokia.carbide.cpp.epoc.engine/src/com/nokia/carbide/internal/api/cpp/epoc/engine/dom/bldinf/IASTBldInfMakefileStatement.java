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
 * This statement is an makefile reference in the prj_mmpfiles block
 *
 */
public interface IASTBldInfMakefileStatement extends IASTBldInfMakMakeStatement {
	/** Get the keyword used, e.g. makefile, gnumakefile, nmakefile... 
	 * @returns never null*/
	IASTLiteralTextNode getType(); 
	/** Set the keyword used, e.g. makefile, gnumakefile, nmakefile... '
	 * @param type may not be null*/
	void setType(IASTLiteralTextNode type);

}
