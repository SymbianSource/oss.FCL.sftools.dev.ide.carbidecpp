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
package com.nokia.carbide.cpp.internal.project.ui.editors.images;

/**
 * An operation on the multiple image list page.  Has access to the current page.
 *
 */
public abstract class MultiImageListPageOperation extends MultiImageEditorOperation {

	/**
	 * @param label
	 */
	public MultiImageListPageOperation(String label, MultiImageListPage page) {
		super(label, page.getEditorContext());
	}
}
