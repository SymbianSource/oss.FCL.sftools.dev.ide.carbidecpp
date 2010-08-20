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

package com.nokia.carbide.cpp.internal.featureTracker;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.nokia.carbide.cpp.internal.api.featureTracker.IFeatureUseTracker;

/**
 * Proxy to a client implementing the 'featureUseTracker' extension point.
 *
 */
public class FeatureUseTrackerProxy implements IFeatureUseTracker {

	public static final String FEATURE_USE_EXTENSION_ID = FeatureUseTrackerPlugin.PLUGIN_ID
			+ ".featureUseTracker"; //$NON-NLS-1$

	private List<IFeatureUseTracker> featureClients = new ArrayList<IFeatureUseTracker>();
	private boolean featureClientInited;

	public void startUsingFeature(String featureName) {
		if (!featureClientInited) {
			checkForClients();
			featureClientInited = true;
		}

		for (IFeatureUseTracker client : featureClients) {
			client.startUsingFeature(featureName);
		}
	}

	public void stopUsingFeature(String featureName) {
		for (IFeatureUseTracker client : featureClients) {
			client.stopUsingFeature(featureName);
		}
	}
	
	/**
	 * Find clients of the 'featureUseTracker' extension point
	 */
	private void checkForClients() {

		IExtensionRegistry er = Platform.getExtensionRegistry();
		IExtensionPoint ep = er.getExtensionPoint(FEATURE_USE_EXTENSION_ID);
		IExtension[] extensions = ep.getExtensions();

		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			IConfigurationElement[] ces = extension.getConfigurationElements();
			if (ces != null && ces.length >= 1) {
				IConfigurationElement providerElement = ces[0];
				String name = providerElement.getAttribute("name"); //$NON-NLS-1$
				if (name != null) {
					if (providerElement.getAttribute("class") != null) { //$NON-NLS-1$

						try {
							featureClients.add((IFeatureUseTracker) providerElement
									.createExecutableExtension("class")); //$NON-NLS-1$
						} catch (CoreException e) {
							// ignore
						}
					}
				}
			}
		}
	}

}
