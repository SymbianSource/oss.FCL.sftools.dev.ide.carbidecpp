/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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
import com.nokia.sdt.utils.drawing.GC;

import org.eclipse.swt.graphics.Point;

/**
 *  This interface is used to render the graphics of
 *  a layout object, as well as provides information related to rendering.
 *  Example javascript protoype implementing this interface:
 *	<blockquote><pre>
 *
 * function CVisualAppearance() {
 *
 * }
 *
 * CVisualAppearance.prototype.draw = function(instance, laf, graphics) {
 *
 * }
 *
 * CVisualAppearance.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
 *
 * }
 *	</pre></blockquote>
 */
public interface IVisualAppearance extends IModelAdapter {

    /**
     * Draw the component instance's appearance
     * @param gc a GC which is set up to draw in the bounding box of
     * the component instance
     * @param laf provides parameters and SWT resources useful for rendering
     */
	void draw(GC gc, ILookAndFeel laf);
	
	/**
	 * Return the preferred size of this instance.
	 * This is called by code layout code
	 * @param wHint
	 * @param hHint
	 * @return
	 */
	Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf);
}
