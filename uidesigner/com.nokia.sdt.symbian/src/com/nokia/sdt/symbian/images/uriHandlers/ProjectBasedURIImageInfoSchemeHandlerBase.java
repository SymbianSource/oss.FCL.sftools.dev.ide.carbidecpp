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

import com.nokia.carbide.cpp.ui.images.*;
import com.nokia.sdt.symbian.images.ProjectImageInfo;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;

import org.eclipse.core.runtime.IPath;

/**
 * Base handler for schemes that reference image files in the project.
 * 
 *
 */
public class ProjectBasedURIImageInfoSchemeHandlerBase extends NullURIImageInfoSchemeHandler {

	protected final ProjectImageInfo projectImageInfo;
	protected IPath projectLocation;

	/**
	 * @param projectImageInfo 
	 * 
	 */
	public ProjectBasedURIImageInfoSchemeHandlerBase(ProjectImageInfo projectImageInfo) {
		Check.checkArg(projectImageInfo);
		this.projectImageInfo = projectImageInfo;
		this.projectLocation = ProjectUtils.getRealProjectLocation(projectImageInfo.getProject());
	}

	/**
	 * Take the URI path and convert it to the full path on the host.
	 * @param projectImageInfo_ the project image info
	 * @param path the URI path
	 * @return full path on host, or <code>null</code> 
	 */
	protected IPath convertToHostImage(String pathString) {
		// need to iterate map due to likelihood of non-canonical strings
		for (IImageModel model : projectImageInfo.getProjectImageModels()) {
			if (model instanceof IURIRepresentableImageModel
					&& model instanceof IFileImageModel) {
				IURIRepresentableImageModel uriModel = (IURIRepresentableImageModel) model;
				if (pathString.equalsIgnoreCase(uriModel.getURI())) {
					return ((IFileImageModel)uriModel).getSourceLocation();
				}
			}
		}
		return null;
	}

}