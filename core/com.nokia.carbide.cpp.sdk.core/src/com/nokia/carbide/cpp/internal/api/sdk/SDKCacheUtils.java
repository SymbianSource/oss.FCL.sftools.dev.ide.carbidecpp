/*******************************************************************************
 * Copyright (c) 2010 Nokia and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Nokia - Initial API and implementation
 *******************************************************************************/

package com.nokia.carbide.cpp.internal.api.sdk;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import org.eclipse.core.runtime.IPath;

import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.CacheUtils;

public class SDKCacheUtils extends CacheUtils {

	public SDKCacheUtils(IPath defaultLocation) {
		super(defaultLocation);
	}

	protected CacheEntry loadCachedData(IPath location, String cacheIdentifier) {
		IPath flushPath = location.append(Integer.toString(cacheIdentifier.hashCode())).addFileExtension("txt");

		if (flushPath.toFile().exists()) {
			try {
				final ClassLoader classLoader = SDKCorePlugin.getDefault().getClass().getClassLoader();
				FileInputStream fis = new FileInputStream(flushPath.toFile());
				ObjectInputStream ois = new ObjectInputStream(fis) {

					@Override
					protected Class<?> resolveClass(ObjectStreamClass desc)
					throws IOException, ClassNotFoundException {
						String name = desc.getName();
						try {
							return classLoader.loadClass(name);
						} catch (ClassNotFoundException e) {
							return super.resolveClass(desc);
						}
					}};
					return new CacheEntry(ois);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

}
