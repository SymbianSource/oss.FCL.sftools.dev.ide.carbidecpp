/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.symbian.ui.editors;

import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.images.IImagePropertyInfo;
import com.nokia.sdt.datamodel.images.IMultiImagePropertyInfo;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.images.*;
import com.nokia.sdt.symbian.ui.UIPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.views.properties.IPropertySource;

class ImageLabelProvider extends LabelProvider {
    
	private EObject object;
    private Image disposableImage;
    
    ImageLabelProvider(EObject object) {
        super();
        this.object = object;
    }
    
    public void dispose() {
        super.dispose();
        if (disposableImage != null) {
        	disposableImage.dispose();
        	disposableImage = null;
        }
    }

    /**
     * Return a tiny thumbnail.  Note: it seems the size of this thumbnail
     * determines the size of EVERY thumbnail, so fix it to 14x14.
     */
    public Image getImage(Object element) {
    	ProjectImageInfo projectImageInfo = (ProjectImageInfo) ModelUtils.getProjectImageInfo(object);
        if (projectImageInfo == null)
            return null;
        
        if (disposableImage != null) {
        	disposableImage.dispose();
        	disposableImage = null;
        }
        
        if (element instanceof ImagePropertyInfo) {
            ImagePropertyInfo data = (ImagePropertyInfo) element;
            IImageModel model = SymbianImageModelFactory.createImagePropertyInfoImageModel(
            		data);
            if (model != null) {
            	try {
					disposableImage = model.getImageDescriptor(new Point(14, 14)).createImage();
					return disposableImage;
				} catch (CoreException e) {
					UIPlugin.log(e);
				}
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * The summary of the image.
     */
    public String getText(Object element) {
    	String text = null;
    	if (element instanceof IImagePropertyInfo) {
    		IImagePropertyInfo data = (IImagePropertyInfo) element;
    		text = data.getText();
    	} else if (element instanceof IMultiImagePropertyInfo) {
    		IMultiImagePropertyInfo data = (IMultiImagePropertyInfo) element;
    		text = data.getText();
    	} else if (element instanceof IPropertySource) {
    		IComponentInstance instance = ModelUtils.getComponentInstance(object);
			if (instance != null && element instanceof IPropertySource) {
				IPropertySource ps = (IPropertySource) element;
				IImagePropertyInfo imageInfo = ModelUtils.getImagePropertyInfo(object, ps);
				if (imageInfo != null)
					text = imageInfo.getText();
				else {
					IMultiImagePropertyInfo multiInfo = ModelUtils.getMultiImagePropertyInfo(object, ps);
					if (multiInfo != null)
						text = multiInfo.getText();
					else
						text = "???"; //$NON-NLS-1$
				}
			} else {
				text = super.getText(element);
			}
    	} else {
    		text = "???"; //$NON-NLS-1$
    	}
    	return text.replaceAll("&", "&&"); //$NON-NLS-1$ //$NON-NLS-2$ // avoid any unintentional underscores 
    }
}