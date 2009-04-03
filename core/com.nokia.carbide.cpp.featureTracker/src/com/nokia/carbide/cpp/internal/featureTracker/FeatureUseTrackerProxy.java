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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.nokia.carbide.cpp.internal.api.featureTracker.IFeatureUseTracker;

/**
 * Proxy to a client implementing the 'featureUseTracker' extension point.
 * There can only be one client registered as a time to this extension.
 *
 */
public class FeatureUseTrackerProxy implements IFeatureUseTracker {

	public static final String FEATURE_USE_EXTENSION_ID = FeatureUseTrackerPlugin.PLUGIN_ID
			+ ".featureUseTracker"; //$NON-NLS-1$

	private IFeatureUseTracker featureClient;
	private boolean featureClientInited;

	public void startUsingFeature(String featureName) {
		if (featureClient == null && featureClientInited) {
			return; // no client plug-in installed, do nothing because we've all ready checked

		} else if (featureClient == null) {
			featureClient = checkForFeatureExtension();
			featureClientInited = true;
		}

		if (featureClient != null) {
			featureClient.startUsingFeature(featureName);
		}

	}

	public void stopUsingFeature(String featureName) {
		if (featureClient == null) {
			return; // no feature client, nothing to do
		} else {
			featureClient.stopUsingFeature(featureName);
		}

	}
	
	/**
	 * Find clients of the 'featureUseTracker' extension point and return the first one
	 * @return the first client that is found implementing IFeatureUseTracker
	 */
	private IFeatureUseTracker checkForFeatureExtension() {

		IFeatureUseTracker result = null;
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
							result = (IFeatureUseTracker) providerElement
									.createExecutableExtension("class"); //$NON-NLS-1$
						} catch (CoreException e) {
							// ignore
							// e.printStackTrace();
						}
						return result;

					}

				}

			}
		}
		return result;

	}

}
