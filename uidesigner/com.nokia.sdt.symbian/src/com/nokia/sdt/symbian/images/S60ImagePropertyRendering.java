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

import com.nokia.carbide.cpp.internal.project.ui.images.ISymbianMaskedFileImageModel;
import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.cpp.internal.api.utils.ui.IColorConverter;
import com.nokia.sdt.utils.ImageUtils;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.*;

/**
 * S60-specific 
 *
 */
public class S60ImagePropertyRendering extends SymbianImagePropertyRendering 
	implements IS60ImagePropertyRendering {

	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.images.ImageRenderingBase#doGetImageData(com.nokia.sdt.symbian.images.SymbianImageProvider, com.nokia.carbide.cpp.ui.images.IImageModel)
	 */
	@Override
	protected ImageData doGetImageData(GC gc,
			IImageModel model) throws CoreException {
		if (renderingModel.equals(MODEL_NAVI_PANE_TABS)
				&& model instanceof ISymbianMaskedFileImageModel) {
			ISymbianMaskedFileImageModel symbianModel = (ISymbianMaskedFileImageModel) model;

			if (symbianModel.getMaskSourceLocation() != null) {
				ImageData data = super.doGetImageData(gc, symbianModel);
				final RGB solidColor;
				if (gc != null)
					solidColor = gc.getForeground().getRGB();
				else
					solidColor = WHITE;
				
				data = ImageUtils.convertToDepth(data, 1, new IColorConverter() {
					public int convertPixel(PaletteData palette, RGB[] outColors, RGB rgb) {
						return palette.getPixel(solidColor);
					}
		        }, null);
				return data;
			}
		}
		return super.doGetImageData(gc, model);
	}
	
}
