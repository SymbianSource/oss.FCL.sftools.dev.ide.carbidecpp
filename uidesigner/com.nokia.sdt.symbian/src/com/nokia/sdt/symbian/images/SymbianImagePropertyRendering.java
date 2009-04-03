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

package com.nokia.sdt.symbian.images;

import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.sdt.datamodel.adapter.IImagePropertyRenderingInfo;
import com.nokia.sdt.symbian.SymbianPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;

/**
 * Default implementation of ISymbianImagePropertyRendering
 * 
 *
 */
public class SymbianImagePropertyRendering extends ImagePropertyRenderingBase implements ISymbianImagePropertyRendering {

	public SymbianImagePropertyRendering() {
		reset();
	}
	
	private IImageModel getImageModel() {
		ImagePropertyInfo imageInfo = (ImagePropertyInfo) getImagePropertyInfo();
		if (imageInfo == null)
			return null;
		
		IImageModel model = SymbianImageModelFactory.createImagePropertyInfoImageModel(imageInfo);
		return model;
	}

	protected ImageData doGetImageData(GC gc) {
		IImageModel model = getImageModel();
		if (model == null)
			return null;
		
		try {
			return doGetImageData(gc, model);
		} catch (CoreException e) {
			SymbianPlugin.getDefault().log(e);
			return null;
		}
	}
	
	public ImageData getImageData() {
		return doGetImageData(null);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.images.IImageRendering#render(com.nokia.sdt.displaymodel.ILookAndFeel, org.eclipse.swt.graphics.GC, int, int)
	 */
	public void render(GC gc, int x, int y) {
		if (!anyImageRenderingParametersSupplied) {
			// last chance: get rendering params from instance itself
			if (instance != null) {
				IImagePropertyRenderingInfo imagePropertyRenderingInfo = null;
				imagePropertyRenderingInfo = (IImagePropertyRenderingInfo) EcoreUtil.getRegisteredAdapter(
						instance.getEObject(), IImagePropertyRenderingInfo.class);
				setImagePropertyRenderingInfo(imagePropertyRenderingInfo);
			}
			if (!anyImageRenderingParametersSupplied) {
				SymbianPlugin.getDefault().log("No rendering parameters supplied and no IImagePropertyRenderingInfo interface provided: using default rendering for "  //$NON-NLS-1$
						+ instance + ", property = " + propertyPath); //$NON-NLS-1$
			}
		}

		ImageData data = doGetImageData(gc);
		if (data == null)
			return;
		renderImage(gc.getDevice(), gc, x, y, data);
	}

}
