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

import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;
import com.nokia.carbide.cpp.internal.project.ui.images.CarbideImageModelFactory;
import com.nokia.carbide.cpp.internal.project.ui.images.IAIFImageContainerModel;
import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.cpp.internal.api.utils.core.Pair;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.symbian.SymbianPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.graphics.*;

/**
 * Default implementation of IImagePropertyRendering for Symbian
 *
 */
public class SymbianImageAIFRendering extends ImageRenderingBase implements ISymbianImageAIFRendering {
	
	private int imageIndex;
	private IPath projectAifFile;

	public SymbianImageAIFRendering() {
		reset();
	}
	
	public void setImageFromAIF(IComponentInstance instance,
			String propertyPath, ILookAndFeel laf, IPath projectAifFile,
			int imageIndex) {
		this.instance = instance;
		this.propertyPath = propertyPath;
		this.laf = laf;
		this.projectAifFile = projectAifFile;
		this.imageIndex = imageIndex;
	}

	private IImageModel getImageModel() {
		if (instance == null) {
			throw new IllegalStateException("No instance supplied for rendering"); //$NON-NLS-1$
		}
				
		ProjectImageInfo projectImageInfo = (ProjectImageInfo) ModelUtils.getProjectImageInfo(instance.getEObject());
		Pair<IPath, IMMPAIFInfo> aifInfoPair = projectImageInfo.getInfoForAifFile(projectAifFile);
		if (aifInfoPair == null)
			return null;
	
		IPath mmpPath = aifInfoPair.first;
		IMMPAIFInfo aifInfo = aifInfoPair.second;
	
		IAIFImageContainerModel aifModel = CarbideImageModelFactory.createAIFImageContainerModel(
				projectImageInfo.getCarbideProjectInfo(),
				mmpPath,
				aifInfo);
	
		try {
			IImageModel model = aifModel.getImageModel(imageIndex);
			return model;
		} catch (CoreException e) {
			SymbianPlugin.getDefault().log(e);
			return null;
		}
	}

	protected ImageData doGetImageData(GC gc) {
		IImageModel model = getImageModel();
		if (model != null) {
			try {
				return doGetImageData(gc, model);
			} catch (CoreException e) {
				SymbianPlugin.getDefault().log(e);
			}
		} 
		return null;
	}
	
	public ImageData getImageData() {
		return doGetImageData(null);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.images.IImageRendering#render(com.nokia.sdt.displaymodel.ILookAndFeel, org.eclipse.swt.graphics.GC, int, int)
	 */
	public void render(GC gc, int x, int y) {
		if (!anyImageRenderingParametersSupplied) {
			SymbianPlugin.getDefault().log("No rendering parameters supplied and no IImagePropertyRenderingInfo interface provided: using default rendering for "  //$NON-NLS-1$
					+ instance + ", aif = " + projectAifFile + " @ " +imageIndex); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		ImageData data = doGetImageData(gc);
		if (data == null)
			return;

		renderImage(gc.getDevice(), gc, x, y, data);
	}
}
