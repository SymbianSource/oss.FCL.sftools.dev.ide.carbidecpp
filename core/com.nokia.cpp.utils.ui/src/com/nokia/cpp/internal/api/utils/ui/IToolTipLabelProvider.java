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
package com.nokia.cpp.internal.api.utils.ui;

import org.eclipse.jface.viewers.ILabelProvider;

/**
 * This interface may be implemented on a label provider used in
 * the ThumbnailGridViewer to provide tooltips for items.
 *
 */
public interface IToolTipLabelProvider extends ILabelProvider {
	/** Return the tooltip, or null */
	String getToolTipText(Object element);
}
