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


/**
 * 
 *
 */
public interface IS60ImagePropertyRendering extends
		ISymbianImagePropertyRendering {

	/**
	 * Rendering model for S60 navi pane tabs, where images without
	 * masks are rendered as monochrome.
	 * @see {@link #setRenderingModel(String)}
	 */
	static final String MODEL_NAVI_PANE_TABS = "navi-pane-tabs"; //$NON-NLS-1$
}
