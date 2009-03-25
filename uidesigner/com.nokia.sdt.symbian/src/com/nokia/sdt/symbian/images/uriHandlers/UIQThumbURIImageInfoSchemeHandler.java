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

package com.nokia.sdt.symbian.images.uriHandlers;

import com.nokia.carbide.cpp.internal.project.ui.images.CarbideImageModelFactory;
import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.sdt.symbian.Messages;
import com.nokia.sdt.symbian.images.IURIImageSchemeHandler;
import com.nokia.sdt.symbian.images.ProjectImageInfo;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import org.eclipse.core.runtime.*;
import org.eclipse.swt.graphics.Point;

import java.io.File;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handler for "file" and "" schemes.  
 *
 */
public class UIQThumbURIImageInfoSchemeHandler extends ProjectBasedURIImageInfoSchemeHandlerBase implements
		IURIImageSchemeHandler {

	private static final Pattern WIDTH_HEIGHT_PATTERN = Pattern.compile("width=(-?\\d+)&height=(-?\\d+)"); //$NON-NLS-1$
	
	public UIQThumbURIImageInfoSchemeHandler(ProjectImageInfo projectImageInfo) {
		super(projectImageInfo);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.images.IURIImageInfoSchemeHandler#getImageDescriptor(java.lang.String, java.lang.String, java.lang.String)
	 */
	public IImageModel getImageModel(String scheme, String path,
			String query) {
		if (query == null)
			return null;
		
		Matcher matcher = WIDTH_HEIGHT_PATTERN.matcher(query);
		if (!matcher.matches())
			return null;
		
		int width = Integer.parseInt(matcher.group(1));
		int height = Integer.parseInt(matcher.group(2));
		if (width <= 0 || height <= 0) 
			return null;
		
		final Point size = new Point(width, height);
		
		final IPath hostPath = convertToHostImage(path);
		if (hostPath == null)
			return null;
		
		File file = hostPath.toFile();
		if (!file.exists()) {
			return null;
		}
		
		IImageModel model = CarbideImageModelFactory.createFileImageModel(
				projectImageInfo.getExternalImageContainerModel(),
				hostPath);
		
		return CarbideImageModelFactory.createFixedSizeImageModelWrapper(model, size);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.images.IURIImageInfoSchemeHandler#validate(java.lang.String, java.lang.String, java.lang.String)
	 */
	public IStatus validate(String scheme, String path, String query) {
		if (query == null) {
			return createStatus(IStatus.ERROR,
					MessageFormat.format(
					Messages.getString("UIQThumbURIImageInfoSchemeHandler.MissingQueryFormat"), //$NON-NLS-1$
					new Object[] { WIDTH_HEIGHT_PATTERN.toString() }));
		}
		
		Matcher matcher = WIDTH_HEIGHT_PATTERN.matcher(query);
		if (!matcher.matches()) {
			return createStatus(IStatus.ERROR,
					MessageFormat.format(
					Messages.getString("UIQThumbURIImageInfoSchemeHandler.InvalidQueryFormat"), //$NON-NLS-1$
					new Object[] { query, WIDTH_HEIGHT_PATTERN.toString() }));
		}
		
		IPath hostPath = convertToHostImage(path);
		
		// only allow SVG in newer SDKs
		if (!projectImageInfo.supportsSVG()
				&& FileUtils.getSafeFileExtension(hostPath).equalsIgnoreCase("svg")) { //$NON-NLS-1$
			return createStatus(IStatus.ERROR,
					Messages.getString("UIQThumbURIImageInfoSchemeHandler.NoSVGSupport")); //$NON-NLS-1$
		}

		// don't care if the file doesn't exist, here
		return Status.OK_STATUS;
	}
}
