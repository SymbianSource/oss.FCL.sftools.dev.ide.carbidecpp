/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom;

/**
 * This location spans one or more locations, usually in different
 * documents.
 *
 */
public interface IMultiDocumentSourceRegion extends ISourceRegion {
	/**
	 * Add another location to this location
	 * @param location a non-null ISourceLocation
	 */
	void addSourceRegion(ISourceRegion startRegion);
}
