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

package com.nokia.sdt.sourcegen;

import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

/**
 * Encapsulates the information and method for applying patches
 * to generated sources.  This is intended for use in implementing
 * a Refactoring or RefactoringParticipant.  
 * 
 *
 */
public interface ISourceGenUpgradingProvider {
	/**
	 * Get the status of the refactoring, reflecting problems when
	 * patches cannot be applied.
	 * @return
	 */
	RefactoringStatus getValidationStatus();
	
	/**
	 * Get the changes to be made.  
	 * @return
	 */
	Change getChanges();

	/**
	 * Detect which patches were skipped.
	 */
	void detectSkippedPatches();

	/**
	 * Get the info about the known patches, failed patches, and skipped
	 * patches.  Only after #detectSkippedPatches() is run does this provide
	 * a complete picture (else it looks like everything was applied).
	 * @return
	 */
	PatchContext getPatchContext();
	
}
