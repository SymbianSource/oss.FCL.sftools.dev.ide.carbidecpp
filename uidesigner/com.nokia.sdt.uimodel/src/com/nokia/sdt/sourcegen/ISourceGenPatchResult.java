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

import java.util.Collection;

/**
 * Results of a sourcegen patch operation.
 * 
 *
 */
public interface ISourceGenPatchResult {
	/** 
	 * Get the patches applied successfully.
	 * @return non-modifiable list of patches
	 */
	Collection<ISourceGenPatch> getAppliedPatches();
	
	/**
	 * Get the patches that did not apply successfully.
	 * @return non-modifiable list of patches
	 */
	Collection<ISourceGenPatch> getFailedPatches();
}
