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
/* START_USECASES: CU1, CU2 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui;

import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.*;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class UIQUserInterfacePlugin extends AbstractUIPlugin {
	private static UIQUserInterfacePlugin plugin;
	public static String ID = "com.nokia.carbide.cpp.uiq.ui"; //$NON-NLS-1$
	
	public UIQUserInterfacePlugin() {
		plugin = this;
	}
	
	public File pluginRelativeFile(String filePath) {
		File result = null;
		URL url = Platform.find(getBundle(), new Path(filePath));
		if (url != null) {
			try {
				url = Platform.resolve(url);
				if ("file".equals(url.getProtocol())) {
					result = new File(url.getPath());
				}
			} catch (IOException x) {
				log(x);
			}
		}
		return result;
	}
	
	public static UIQUserInterfacePlugin getDefault() {
		return plugin;
	}
		
	static public void log(IStatus status) {
		Logging.log(plugin, status);
	}
	
	static public void log(Throwable thr) {
		Logging.log(plugin, Logging.newStatus(plugin, thr));
	}
	
	static public void log(Throwable thr, String msg) {
		Logging.log(plugin, Logging.newStatus(plugin, IStatus.ERROR, msg, thr));
	}
}
