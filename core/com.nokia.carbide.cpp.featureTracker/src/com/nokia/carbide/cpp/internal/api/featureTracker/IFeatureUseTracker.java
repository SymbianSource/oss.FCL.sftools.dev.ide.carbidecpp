/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies). 
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
	 * Start using the feature (a.k.a. 'checkout')
	 * @param featureName - The string of the feature to be used
	 */
	public void startUsingFeature(String featureName);
	
	/**
	 * Called after 'startUsingFeature' once a feature is no longer being used
	 * @param featureName - The string of the feature being used
	 */
	public void stopUsingFeature(String featureName);
}
