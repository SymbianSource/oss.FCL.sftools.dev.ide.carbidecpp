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

import com.nokia.cpp.internal.api.utils.core.Pair;

import org.osgi.framework.Version;

import java.util.Map;

/**
 * This interface is used by ISourceGenSession#getPatchInfo() to
 * determine which component changes have occurred to identify 
 * applicable patches.
 * 
 *
 */
public interface ISourceGenComponentVersionProvider {
	/** Get the "from" and "to" component versions for updating source.
	 * @return map of component id to pair of from, to versions.
	 */
	public Map<String, Pair<Version, Version>> getComponentVersionDeltas();
	
	
}
