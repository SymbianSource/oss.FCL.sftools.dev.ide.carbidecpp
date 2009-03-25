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

package com.nokia.sdt.symbian.ui.images;

import com.nokia.sdt.datamodel.images.IImagePropertyInfoBase;
import com.nokia.sdt.symbian.images.MultiImageInfo;

import org.eclipse.core.runtime.IStatus;

/**
 * 
 *
 */
public interface IImageEditorPane {

	/**
	 * Get the current image property, in a new object
	 * @return new dialog data
	 */
	IImagePropertyInfoBase getImagePropertyInfo();
	
	/**
	 * Set the current image property
	 * @param info
	 */
	void setImagePropertyInfo(IImagePropertyInfoBase info);
	
	void setMultiImageInfo(MultiImageInfo info);

	/**
	 * Validate current contents and update enabled/disabled states.
	 * @return IStatus for validation state
	 */
	IStatus validate();


	/**
	 * 
	 */
	void refresh();

}