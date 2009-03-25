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

/**
 * This interface encapsulates non-owned source changes required
 * when component versions change. 
 * 
 *
 */
public interface ISourceGenPatchInfo {
	/**
	 * Get the array of patches required to update sources
	 * @return array of patches, never null
	 */
	ISourceGenPatch[] getSourceGenPatches();
}
