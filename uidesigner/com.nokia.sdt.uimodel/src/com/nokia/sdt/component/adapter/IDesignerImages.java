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
package com.nokia.sdt.component.adapter;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * Accessor for the standard images used by the UI designer.
 * Clients should assume these images are already cached and
 * do not need to implement additional caching.
 */
public interface IDesignerImages extends IComponentAdapter {

	/**
	 * Returns the small icon used in the component toolbox
	 */
	Image	getSmallIcon();
	
	/**
	 * Returns the large icon used in the component toolbox and for
	 * non-layout components
	 */
	Image	getLargeIcon();
	
	/**
	 * Returns the optional static layout image. This image
	 * is used in the UI editor for components that do not dynamically
	 * render. A default one is supplied if the component does not
	 * render or provide its own static image
	 */
	Image 	getStaticLayoutImage();
	
	/**
	 * Returns the optional thumbnail image for use in a wizard page.
	 */
	Image	getThumbnailImage();

	/**
	 * Returns the descriptor for the small icon used in the component toolbox
	 */
	ImageDescriptor	getSmallIconDescriptor();
	
	/**
	 * Returns the descriptor for the large icon used in the component toolbox and for
	 * non-layout components
	 */
	ImageDescriptor	getLargeIconDescriptor();
}
