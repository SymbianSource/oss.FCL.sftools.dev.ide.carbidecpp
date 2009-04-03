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
package com.nokia.sdt.symbian.images;

import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IImagePropertyRenderingInfo;
import com.nokia.sdt.datamodel.images.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NodePathLookupResult;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.symbian.SymbianPlugin;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.ui.views.properties.IPropertySource;

public abstract class ImagePropertyRenderingBase extends ImageRenderingBase implements IImagePropertyRendering {

	private IImagePropertyInfo propertyInfo;
	private IPropertySource propertySource;
	private String multiImagePropertyAbstractImageId;
	
	public ImagePropertyRenderingBase() {
	}

	@Override
	public void reset() {
		super.reset();
		propertyInfo = null;
		propertySource = null;
		multiImagePropertyAbstractImageId = null;
	}
	
	
	public void setImageProperty(IComponentInstance instance, String propertyPath, ILookAndFeel laf) {
		this.instance = instance;
		this.propertySource = getPropertySource(instance, propertyPath);
		this.propertyPath = propertyPath;
		this.laf = laf;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.images.IImagePropertyRendering#setImagePropertyRenderingInfo(com.nokia.sdt.datamodel.adapter.IImagePropertyRenderingInfo)
	 */
	public void setImagePropertyRenderingInfo(IImagePropertyRenderingInfo info) {
		if (info != null) {
			anyImageRenderingParametersSupplied = true;
			viewableSize = info.getViewableSize(propertyPath, laf);
			alignmentWeights = info.getAlignmentWeights(propertyPath, laf);
			isScaling = info.isScaling(propertyPath, laf);
			isPreservingAspectRatio = info.isPreservingAspectRatio(propertyPath, laf);
		}
	}

	/**
	 * @param instance
	 * @param propertyPath
	 * @return
	 */
	private IPropertySource getPropertySource(IComponentInstance instance,
			String propertyPath) {
		Check.checkArg(instance);
		
		IPropertySource propertySource = null;
		
		if (propertyPath != null) {
			NodePathLookupResult result =
				ModelUtils.readProperty(instance.getEObject(), propertyPath, false);
			if (result.result == null) {
				// allow missing property
			} else {
				if (!(result.result instanceof IPropertySource)) {
					throw new IllegalStateException("Property path does not refer to property source: path=" + propertyPath + " in instance: " + instance); //$NON-NLS-1$ //$NON-NLS-2$
				}
				propertySource = (IPropertySource) result.result;
			}
		}
		return propertySource;
	}

	/**
	 * @param ps
	 * @return
	 */
	protected IImagePropertyInfo getImagePropertyInfo() {
		if (propertyInfo != null)
			return propertyInfo;
		
		if (instance == null || propertySource == null)
			return null;

		ImagePropertyInfo imageInfo = null;

		if (multiImagePropertyAbstractImageId != null) {
			// extract an image from multi-iamge property
			IMultiImagePropertyInfo multiInfo = ModelUtils.getMultiImagePropertyInfo(instance.getEObject(), propertySource);
			if (multiInfo == null) {
				SymbianPlugin.getDefault().log("Missing or invalid converterClass on '" + //$NON-NLS-1$
						((IPropertyInformation) propertySource).getPropertyTypeName(null) +"' ; cannot render "  //$NON-NLS-1$
						+ instance + ", property = " + propertyPath); //$NON-NLS-1$
				return null;
			}
			imageInfo = (ImagePropertyInfo) multiInfo.getImagePropertyInfoMap().get(multiImagePropertyAbstractImageId);
			if (imageInfo == null) {
				SymbianPlugin.getDefault().log("Unknown abstract image id '" + multiImagePropertyAbstractImageId + //$NON-NLS-1$
						"' on image property of type '" + //$NON-NLS-1$
						((IPropertyInformation) propertySource).getPropertyTypeName(null) +"' ; cannot render "  //$NON-NLS-1$
						+ instance + ", property = " + propertyPath); //$NON-NLS-1$
				return null;
			}
		} else {
			// get standard image property
			imageInfo = (ImagePropertyInfo) ModelUtils.getImagePropertyInfo(instance.getEObject(), propertySource);
		}

		
		if (imageInfo == null) {
			SymbianPlugin.getDefault().log("Missing or invalid converterClass on '" + //$NON-NLS-1$
					((IPropertyInformation) propertySource).getPropertyTypeName(null) +"' ; cannot render "  //$NON-NLS-1$
					+ instance + ", property = " + propertyPath); //$NON-NLS-1$
			return null;
		}
		
		return imageInfo;
	}

	public void setImagePropertySource(IPropertySource imageProperty) {
		this.propertySource = imageProperty;
		this.propertyInfo = null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.images.IImagePropertyRendering#setMultiImagePropertyAbstractImageId(java.lang.String)
	 */
	public void setMultiImagePropertyAbstractImageId(String abstractImageId) {
		this.multiImagePropertyAbstractImageId = abstractImageId;
	}
	
	public void setImagePropertyInfo(IComponentInstance instance, IImagePropertyInfo propertyInfo) {
		this.instance = instance;
		this.propertyInfo = propertyInfo;
		this.propertySource = null;
	}
}