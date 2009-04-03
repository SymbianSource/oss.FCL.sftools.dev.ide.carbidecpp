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

package com.nokia.sdt.sourcegen.patcher;

import com.nokia.sdt.sourcegen.ISourceGenPatch;

/**
 * An instruction derived from a patch file.
 * 
 *
 */
public abstract class AbstractPatchInstruction {
	
	/** Match the instruction against the list of lines,
	 * returning the index at which to apply to change. 
	 * @param lines unmodifiable list of lines
	 * @return line index or -1 if cannot apply
	 */
	abstract int match(ISourceGenPatch patch, int index, IPatchHandler handler);
	
	/** Apply the patch to the given set of lines (modifying them)
	 * @param index adjusted index of line at which #match previously
	 * returned 
	 * @param lines modified list of lines
	 * @return new index after lines, or -1 if not applied
	 */
	abstract int apply(ISourceGenPatch patch, int index, IPatchHandler handler);
}
