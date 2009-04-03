/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.internal.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;

public class SharedImages implements ICarbideSharedImages {
	
	static final String KEY_PREFIX = "IMG_";
	private ImageRegistry registry;
	
	public SharedImages() {
		registry = new ImageRegistry();
		IPath basePath = new Path("icons");
		Class stringClass = java.lang.String.class;
		Field[] fields = getClass().getFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			if (fieldName.startsWith(KEY_PREFIX) &&
				field.getType() == stringClass) {
				int m = field.getModifiers();
				if (Modifier.isStatic(m) && Modifier.isFinal(m)) {
					try {
						Object imagePath = field.get(this);
						if (imagePath != null) {
							String imagePathStr = imagePath.toString();
							IPath path = basePath.append(imagePathStr);
							ImageDescriptor descriptor = createImageDescriptor(path);
							registry.put(imagePathStr, descriptor);
						} else {
							CarbideUIPlugin.log(null, "No path for image key "+fieldName);
						}
					} catch (IllegalArgumentException x) {
						CarbideUIPlugin.log(x);
					} catch (IllegalAccessException x) {
						CarbideUIPlugin.log(x);
					}
				}
			}
		}
	}

	public Image getImage(String key) {
		return registry.get(key);
	}

	public ImageDescriptor getImageDescriptor(String key) {
		return registry.getDescriptor(key);
	}
	
	private ImageDescriptor createImageDescriptor(IPath path) {
		ImageDescriptor result;
		URL url = FileLocator.find(CarbideUIPlugin.getDefault().getBundle(), path, null);
		if (url != null) {
			result = ImageDescriptor.createFromURL(url);
		} else {
			result = ImageDescriptor.getMissingImageDescriptor();
		}
		return result;
	}
}
