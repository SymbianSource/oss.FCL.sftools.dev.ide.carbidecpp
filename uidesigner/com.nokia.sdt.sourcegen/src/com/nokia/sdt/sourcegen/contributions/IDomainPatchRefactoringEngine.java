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

package com.nokia.sdt.sourcegen.contributions;

import org.eclipse.ltk.core.refactoring.Change;

import java.util.List;
import java.util.Map;

/**
 * Interface encapsulating results of applying patches.
 * This provides the actual change objects required to apply patches.
 * 
 *
 */
public interface IDomainPatchRefactoringEngine {
	/**
	 * Apply patch contributions.
	 * @param context
	 * @param patches 
	 */
	void applyPatches(List<SourceGenPatch> patches);
	
	/** 
	 * Get the changes, which when applied, modify ISourceGenManipulators 
	 */
	Change getChanges();
	
	/**
	 * Get the map of patches to text changes (<SourceGenPatch, TextEditChange>)
	 */
	Map getPatchToTextEditMap();
}
