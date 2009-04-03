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
/**
 * 
 */
package com.nokia.sdt.sourcegen.doms.rss;

import com.nokia.cpp.internal.api.utils.core.IDisposable;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.runtime.IPath;

/**
 * A proxy for data models generating RSS.
 * 
 *
 */
public interface IRssModelProxy extends IDisposable {
	/**
	 * Get the project info
	 */
	public IRssProjectInfo getProjectInfo();
	
	/**
	 * Get the model specifier, if defined.
	 * @return
	 */
	public IDesignerDataModelSpecifier getModelSpecifier();

	/**
	 * Load or retrieve cached data model.
	 * The model is owned by the proxy and disposed.
	 * @return data model or null
	 */
	public IDesignerDataModel requestDataModel();

	/**
	 * Update the model specifier.
	 * @param dmSpec
	 */
	public void setModelSpecifier(IDesignerDataModelSpecifier dmSpec);

	/**
	 * Set the loaded model.  This is owned externally and not
	 * disposed.  Any loaded cached model is disposed (unless
	 * it is the same as the argument).
	 */
	public void setDataModel(IDesignerDataModel dataModel);

	/**
	 * Tell if the model is the root model.
	 */
	public boolean isRootModel();

	/**
	 * Get the path of the model
	 * @return workspace path
	 */
	public IPath getDesignPath();

	/**
	 * Get the base RSS filename for the model, e.g. "MyDesign"
	 * @return base filename
	 */
	public String getRssBaseName();

	/**
	 * Set the base name, also updating the filename
	 * @param baseName
	 */
	public void setRssBaseName(String baseName);
	
	/**
	 * Get the filename to use for the given model's main RSS file
	 * (rss or rssi)
	 */
	public String getRssFileName();

	/**
	 * Set the filename to use for the model's RSS file.
	 * @param fileName
	 */
	public void setRssFileName(String fileName);
}
