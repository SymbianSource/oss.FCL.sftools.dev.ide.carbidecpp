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

import org.eclipse.swt.graphics.Point;

/**
 *  This interface is used to layout the children of a layout container.
 *  Example javascript protoype implementing this interface:
 *	<blockquote><pre>
 *function CLayout() {
 *
 *}
 *
 *CLayout.prototype.layout = function(instance, laf) {
 *
 *}
 *
 *CLayout.prototype.getPreferredSize = function(instance, laf, wHint, hHint) {
 *
 *}
 *
 *	</pre></blockquote>
 */
public interface ILayout extends IModelAdapter {

    /**
     * Layout the container's children
     */
	void layout(ILookAndFeel laf);
	
	/**
	 * Return the preferred size of this instance.
	 * This is called by layout code for nested containers
	 * It need not be fully implemented otherwise.
	 * @param wHint
	 * @param hHint
	 * @param laf
	 * @return
	 */
	Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf);
}
