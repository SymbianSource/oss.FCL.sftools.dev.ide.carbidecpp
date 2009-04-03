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
 
package com.nokia.carbide.internal.bugreport.resources;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.nokia.carbide.internal.bugreport.resources.ImageKeys;

/**
 * Class for handling bug_reporter icons.
 *
 */
public class ImageResourceManager {
	
	/**
	 * Loads images from given path.
	 * @param imagesPath path where images are found.
	 */
	public static void loadImages(String imagesPath){
		
    	Display disp = Display.getCurrent();
    	
    	ImageRegistry imgReg = JFaceResources.getImageRegistry();
    	
    	Image img = new Image( disp, imagesPath + "\\bug_report_with_banner_75x66.png" );  	 //$NON-NLS-1$
        imgReg.put( ImageKeys.WIZARD_BANNER, img );
	}
	
	/**
	 * Gets the image descriptor for given key.
	 * @param key 
	 * @return imageDescriptor
	 */
	public static ImageDescriptor getImageDescriptor( String key ){
    	ImageRegistry imgReg = JFaceResources.getImageRegistry();
    	return  imgReg.getDescriptor( key );		
	}	

	/**
	 * Gets the image of the given key.
	 * @param key
	 * @return Image
	 */
	public static Image getImage( String key ){
    	ImageRegistry imgReg = JFaceResources.getImageRegistry();    	
    	return  imgReg.get(key);		
	}	

	/**
	 * This class is not meant to be instantiated.
	 *
	 */		
	private ImageResourceManager() {
	}
}
