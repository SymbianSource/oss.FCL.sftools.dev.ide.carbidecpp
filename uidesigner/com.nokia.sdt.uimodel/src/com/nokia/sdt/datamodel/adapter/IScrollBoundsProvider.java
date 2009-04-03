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


package com.nokia.sdt.datamodel.adapter;

import com.nokia.sdt.displaymodel.ILookAndFeel;

import org.eclipse.swt.graphics.Rectangle;

/**
 *  This interface is used to define the scroll bounds for a scrolling container.
 *  This is optional for scrolling containers that can have scroll area smaller than its bounds.
 *  Example javascript protoype implementing this interface:
 *	<blockquote><pre>
 *
 * function CScrollBoundsProvider() {
 *
 * }
 *
 * CScrollBoundsProvider.prototype.getScrollBounds = function(instance, laf) {
 *
 * }
 *	</pre></blockquote>
 */
public interface IScrollBoundsProvider extends IModelAdapter {
	
	/**
	 * @return the current scroll bounds Rectangle in local coordinates
	 */
	Rectangle getScrollBounds(ILookAndFeel laf);

}
