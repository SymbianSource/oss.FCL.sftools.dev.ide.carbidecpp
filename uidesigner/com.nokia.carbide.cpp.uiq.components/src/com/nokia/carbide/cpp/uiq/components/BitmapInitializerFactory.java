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

package com.nokia.carbide.cpp.uiq.components;

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.adapter.IInitializer;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.images.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public class BitmapInitializerFactory implements IImplFactory {

	// This is hardcoded.  Needs update if more images are added.
	public static final String BITMAPBUTTON_IMAGE_PROPERTY_ID = "image1"; //$NON-NLS-1$

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.IImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject componentInstance) {
		return new BitmapInitializer(componentInstance);
	}

	static class BitmapInitializer implements IInitializer {

		private EObject componentInstance;

		/**
		 * @param componentInstance
		 */
		public BitmapInitializer(EObject componentInstance) {
			this.componentInstance = componentInstance;
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IInitializer#initialize()
		 */
		public void initialize(boolean isConfigured) {
			if (isConfigured) return;
			// if any image properties, initialize them to something
			IPropertySource ps = ModelUtils.getPropertySource(componentInstance);
			Check.checkState(ps != null);
			IPropertyDescriptor[] properties = ps.getPropertyDescriptors();
			Check.checkState(properties != null);
			
			// NOTE: only handles the first image found
			String imageProperty = null;
			for (int i = 0; i < properties.length; i++) {
				if (properties[i].getId().equals(BITMAPBUTTON_IMAGE_PROPERTY_ID)) {
					imageProperty = BITMAPBUTTON_IMAGE_PROPERTY_ID;
					break;
				}
			}
			if (imageProperty == null)
				return;
			
			// get some image
			ProjectImageInfo projectImageInfo = (ProjectImageInfo) ModelUtils.getProjectImageInfo(componentInstance);
			if (projectImageInfo == null)
				return;
				
			// get a mbm file if possible, else a mif file
			MultiImageInfo[] imageInfos = projectImageInfo.getMultiImageInfos();
			MultiImageInfo imgFile = null;
			MultiImageInfo mifFile = null;
			for (MultiImageInfo info : imageInfos) {
				if (info.getFileType() == MultiImageInfo.MBM_FILE) {
					imgFile = info;
					break;
				} else
					mifFile = info;
			}
			if (imgFile == null && mifFile != null)
				imgFile = mifFile;
			if (imgFile == null)
				return;
				
			// now get the first image, assumed to be a bitmap (not a mask)
			String[] enums = imgFile.getImageEnumeratorList();
			if (enums.length == 0)
				return;
			
			ImageInfo bitmapInfo = imgFile.findImageByEnumerator(enums[0]);
			Check.checkState(bitmapInfo != null);
			ImageInfo maskInfo = imgFile.getMaskForImage(bitmapInfo);
			
			IPropertySource imagePs = (IPropertySource) ps.getPropertyValue(imageProperty); 
			
			ImagePropertyInfo imagePropertyInfo = new ImagePropertyInfo(imgFile, bitmapInfo, maskInfo);
			SymbianImageValueConverter converter = new SymbianImageValueConverter();
			converter.applyEditableValue(componentInstance, imagePropertyInfo, imagePs);
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IModelAdapter#getEObject()
		 */
		public EObject getEObject() {
			return componentInstance;
		}
		
	}
}
