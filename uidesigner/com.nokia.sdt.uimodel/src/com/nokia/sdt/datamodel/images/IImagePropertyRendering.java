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
package com.nokia.sdt.datamodel.images;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IImagePropertyRenderingInfo;
import com.nokia.sdt.displaymodel.ILookAndFeel;

import org.eclipse.swt.graphics.GC;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * This interface is used to render an image defined in a well-known compound
 * property in a loaded data model.  The client obtains an instance of this
 * interface, invokes various setters, and then either renders
 * ({@link #render(GC, int, int)} or retrieves (@link #getImage(Device)}
 * the image.
 * 
 * 
 * 
 */
public interface IImagePropertyRendering extends IImageRendering {
	/**
	 * Set the instance and property path to use for reading the property and
	 * for querying IImagePropertyInfo. The property path should refer to a
	 * compound property implementing an ICompoundPropertyValueConverter
	 * converterClass that provides an IImageInfo instance.
	 * <p>
	 * Any {@link IImagePropertyRenderingInfo} implementation on the instance is
	 * used to configure rendering parameters.  Other setters may be
	 * used to override these settings.
	 * @param instance the instance holding the property
	 * @param propertyPath the node path to the property, may be null
	 * @param laf
	 *            the look and feel instance for the current layout, may be null
	 */
	void setImageProperty(IComponentInstance instance, String propertyPath, ILookAndFeel laf);

	/**
	 * Set the property source from which to read the image properties.
	 * <p>
	 * This overrides the property source implicitly read from the instance
	 * provided to {@link #setImageProperty(IComponentInstance, String, ILookAndFeel)}, which
	 * may be useful for validating a potential changed property before
	 * committing it to the instance.
	 */
	void setImagePropertySource(IPropertySource imageProperty);

	/**
	 * For multi-image properties, specify which abstract image id to render.
	 * <p>
	 * Requires that either {@link #setImageProperty(IComponentInstance, String, ILookAndFeel)} 
	 * or {@link #setImagePropertySource(IPropertySource)} has been called.
	 * @see IMultiImagePropertyInfo
	 */
	void setMultiImagePropertyAbstractImageId(String abstractImageId);
	
	/**
	 * Set the image property info directly rather than converting through
	 * a property source.  If this is used, then {@link #setImagePropertyRenderingInfo()}
	 * should be called to provide rendering parameters, or other setters should be
	 * used to override these settings.  
	 * <p>
	 * This supercedes {@link #setMultiImagePropertyAbstractImageId(String)} -- you should
	 * get the desired abstract image id from a {@link IMultiImagePropertyInfo} first
	 * and pass it to this setter.
	 * @param instance the instance associated with property
	 * @param propertyInfo image property info
	 */
	void setImagePropertyInfo(IComponentInstance instance, IImagePropertyInfo propertyInfo);
	
	/**
	 * Use the image property info handler to override the rendering
	 * parameters, rather than another #setImageProperty() variant.  
	 * This may be used to override the {@link IImagePropertyRendering} obtained
	 * automatically through setting the image property from a property source.
	 * @param info the rendering information block
	 */
	void setImagePropertyRenderingInfo(IImagePropertyRenderingInfo info);


	
}
