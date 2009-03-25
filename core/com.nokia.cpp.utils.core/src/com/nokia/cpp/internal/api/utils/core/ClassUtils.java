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
package com.nokia.cpp.internal.api.utils.core;

import org.eclipse.core.runtime.*;
import org.osgi.framework.Bundle;

import com.nokia.cpp.utils.core.noexport.UtilsCorePlugin;

import java.text.MessageFormat;

/**
 * Utilities for things involving dynamic instantation, reflection, etc.
 *
 */
public abstract class ClassUtils {

	/**
	 * Load a class by name. 
	 * @param bundle The bundle providing the class loader context
	 * @param className the class to load, with a "&lt;bundle-id/&gt;" prefix to
	 * look in an alternate bundle
	 * @param expectedClass a class that the result must be assignment compatible with.
	 * @return the instantiated object, or null if loading or instantiation failed.
	 * @throws ClassNotFoundException if class is not found in bundle or in className bundle specifier
	 * @throws ClassCastException if class is not assignable to expectedClass
	 */
	public static Class<?> loadClass(Bundle bundle, String className, Class expectedClass) 
												throws ClassNotFoundException, ClassCastException {
		Check.checkArg(className);
		Check.checkArg(expectedClass);
		
		// check for class outside bundle
		int idx = className.indexOf('/');
		if (idx != -1) {
			bundle = Platform.getBundle(className.substring(0, idx));
			if (bundle != null)
				className = className.substring(idx + 1);  
		}
		if (bundle == null && UtilsCorePlugin.getDefault() != null)
			bundle = UtilsCorePlugin.getDefault().getBundle();
		
		Class<?> cls = null;
		if (bundle != null) {
			cls = bundle.loadClass(className);
			cls = cls.asSubclass(expectedClass);
		}
		return cls;
	}
	
	/**
	 * Instantiate a class. Optionally logs
	 * an error if the class could not be instantiated.
	 * For logging to occur the logPlugin and logMessagePattern parameters
	 * must be provided.
	 * @param cls The loaded class.
	 * @param logPlugin The plugin to associate with the log message
	 * @param logMessagePattern The message text. This will be passed to
	 * MessageFormat.format along with the logMessageParams to dynamically
	 * create the message string.
	 * @param logMessageParams Optional parameters for the log message
	 * @return the instantiated object, or null if instantiation failed.
	 */
	public static Object createInstanceFromClass(Class cls,
						Plugin logPlugin, String logMessagePattern, Object logMessageParams[]) {
		Object result = null;
		Throwable thr = null;
		if (cls == null) {
			thr = new NullPointerException();
		} else {
			try {
				result = cls.newInstance();
			} catch (Throwable t) {
				thr = t;
			}
		}
		if (thr != null) {
			// catch any throwables from user code
			if (logPlugin != null && logMessagePattern != null) {
				String msg = MessageFormat.format(logMessagePattern, logMessageParams);
				Logging.log(logPlugin, 
						Logging.newStatus(logPlugin, IStatus.ERROR, msg, thr));
			}
		}
		
		return result;
	}
	
	/**
	 * Load and instantiate a class by name. Optionally logs
	 * an error if the class could not be loaded or instantiated.
	 * For logging to occur the logPlugin and logMessagePattern parameters
	 * must be provided.
	 * @param bundle The bundle providing the class loader context
	 * @param className the class to load, with a "&lt;bundle-id/&gt;" prefix to
	 * look in an alternate bundle
	 * @param expectedClass the expected type of the created object. Only objects
	 * compatible with this class are returned.
	 * @param logPlugin The plugin to associate with the log message
	 * @param logMessagePattern The message text. This will be passed to
	 * MessageFormat.format along with the logMessageParams to dynamically
	 * create the message string.
	 * @param logMessageParams Optional parameters for the log message
	 * @return the instantiated object, or null if loading or instantiation failed.
	 */
	public static Object loadAndCreateInstance(Bundle bundle, String className,  Class expectedClass,
						Plugin logPlugin, String logMessagePattern, Object logMessageParams[]) {
		Object result = null;
		Throwable caughtThrowable = null;
		
		try {
			Class cls = loadClass(bundle, className, expectedClass);
			result = cls.newInstance();
		} catch (Throwable thr) {
			// catch any throwables from user code
			caughtThrowable = thr;
		}
		
		if (result == null && logPlugin != null && logMessagePattern != null) {
			String msg = MessageFormat.format(logMessagePattern, logMessageParams);
			Logging.log(logPlugin, 
					Logging.newStatus(logPlugin, IStatus.ERROR, msg, caughtThrowable));
		}
		return result;
	}
}
