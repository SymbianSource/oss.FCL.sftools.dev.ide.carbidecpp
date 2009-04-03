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


package com.nokia.sdt.displaymodel.adapter;

import com.nokia.sdt.datamodel.adapter.IModelAdapter;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 * <br><br>
 * The main interface to an object in the layout area
 */
public interface ILayoutObject extends IModelAdapter {

	/**
	 * Get a copy of the object's bounds.  Changes to this rectangle are ignored.
	 * @return a copy of the bounds of the object as <code>org.eclipse.swt.graphics.Rectangle</code>
	 * (parent-relative coordinates from top-left corner)
	 */
	public Rectangle getBounds();
	
	/**
	 * Set the bounds for the object.  The rectangle is copied and further changes
	 * to it are ignored.
	 * @param newBounds the new Rectangle representing the bounds of the object
	 * (parent-relative coordinates from top-left corner)
	 */
	public void setBounds(Rectangle newBounds);
	
	/**
	 * @return image of the object as <code>org.eclipse.swt.graphics.Image</code>
	 * Transfers ownership of the returned image to the caller.
	 */
	public Image getImage();
	
	/**
	 * @param listener the <code>Listener</code> to be added
	 */
	public void addListener(LayoutObjectListener listener);
	
	/**
	 * @param listener the <code>Listener</code> to be removed
	 */
	public void removeListener(LayoutObjectListener listener);
	
	/**
	 * @return the ILayoutContainer parent or null if root container
	 */
	public ILayoutContainer getParentContainer();
    
    /**
     * Force a redraw of the given layout object
     */
    public void forceRedraw();
}
