/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.symbian.images;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.images.IImagePropertyInfo;
import com.nokia.sdt.datamodel.images.IProjectImageInfo;
import com.nokia.sdt.datamodel.util.ModelUtils;

import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Script globals for image handling.
 * 
 *
 */
public abstract class ImageGlobals {
    /**
     * Get the project image info.
     * @param instance the instance in the project
     * @return the image info, or null
     */
    public static IProjectImageInfo getProjectImageInfo(IComponentInstance instance) {
    	if (instance == null)
    		return null;
    	IProjectImageInfo projectImageInfo = ModelUtils.getProjectImageInfo(instance.getEObject());
    	return projectImageInfo;
    }

    /**
     * Get the image property info for a particular property of an instance.
     * @param instance the instance owning the property
     * @param imageProperty the value of the image property
     * @return the image info, or null
     */
    public static IImagePropertyInfo getImagePropertyInfoFromProperty(IComponentInstance instance, IPropertySource imageProperty) {
    	if (instance == null)
    		return null;
    	return ModelUtils.getImagePropertyInfo(instance.getEObject(), imageProperty);
    }


}
