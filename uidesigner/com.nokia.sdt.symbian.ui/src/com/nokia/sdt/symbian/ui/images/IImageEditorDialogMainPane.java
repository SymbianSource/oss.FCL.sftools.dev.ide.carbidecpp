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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 *
 */
public interface IImageEditorDialogMainPane {

	/**
	 * @return
	 */
	IImagePropertyInfoBase getImagePropertyInfo();

	/**
	 * @param dataBlock
	 */
	void setImagePropertyInfo(IImagePropertyInfoBase dataBlock);

	/**
	 * @return
	 */
	IStatus validate();
	
	void addButtons(Composite parent);
	
	void show();
	void hide();
	
	void dispose();

}
