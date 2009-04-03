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
package com.nokia.sdt.symbian;

import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class SymbianPlugin extends Plugin {

	//The shared instance.
	private static SymbianPlugin plugin;
    
    public static final String PI_NAME = "com.nokia.sdt.symbian"; //$NON-NLS-1$
	
	/**
	 * The constructor.
	 */
	public SymbianPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static SymbianPlugin getDefault() {
		return plugin;
	}
	
	public void log(Throwable t) {
		IStatus status = Logging.newSimpleStatus(0, t);
		Logging.log(this, status);
	}

	/**
	 * @param string
	 */
	public void log(String string) {
		IStatus status = Logging.newStatus(this, IStatus.ERROR, string, new Exception());
		Logging.log(this, status);
	}

}
