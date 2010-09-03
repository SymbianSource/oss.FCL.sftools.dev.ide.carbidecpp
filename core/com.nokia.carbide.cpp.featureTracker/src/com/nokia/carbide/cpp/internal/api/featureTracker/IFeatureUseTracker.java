/*
* Copyright (c) 2009-2010 Nokia Corporation and/or its subsidiary(-ies). 
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
*/
package com.nokia.carbide.cpp.internal.api.featureTracker;

/**
 * Interface to use for clients implementing the 'featureUseTracker'
 * extension point
 * 
 * Any use should guarantee not to block.
 */
public interface IFeatureUseTracker {
	
	/**
	 * Track the usage of the given feature
	 * @param featureName the name of the feature being used
	 * @since 3.0
	 */
	public void useFeature(String featureName);
}
