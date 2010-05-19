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
* Contributors:
*
* Description: 
*
*/
package com.nokia.carbide.cpp;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.ui.plugin.*;
import org.eclipse.equinox.p2.ui.Policy;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * The main plugin class to be used in the desktop.
 */
public class ProductPlugin extends AbstractUIPlugin {

	//The shared instance.
	private static ProductPlugin plugin;
	
	private ServiceRegistration policyRegistration;

	/**
	 * The constructor.
	 */
	public ProductPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		Policy policy = new Policy();
		policy.setRestartPolicy(Policy.RESTART_POLICY_PROMPT);
		Map<String, Integer> map = Collections.singletonMap("service.ranking", 100);
		policyRegistration = context.registerService(Policy.class.getName(), policy, new Hashtable<Object, Object>(map));
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		policyRegistration.unregister();
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static ProductPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("com.nokia.carbide.cpp", path); //$NON-NLS-1$
	}
}
