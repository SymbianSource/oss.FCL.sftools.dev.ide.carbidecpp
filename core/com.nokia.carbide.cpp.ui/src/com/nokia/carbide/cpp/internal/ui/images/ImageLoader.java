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

package com.nokia.carbide.cpp.internal.ui.images;

import com.nokia.carbide.cpp.internal.ui.Messages;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.images.IImageDataProvider;
import com.nokia.carbide.cpp.ui.images.IImageLoader;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.*;

import java.io.File;
import java.text.MessageFormat;

/**
 * Loader for images known to Carbide.
 * <p>  
 * This keeps images in their native format, optionally scaled
 * to a given size (expected to be used mainly for SVG images).
 *
 */
public class ImageLoader implements IImageLoader {
    
    public ImageLoader() {
        super();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.carbide.cpp.project.ui.images.IImageLoader#createImageDataProvider(org.eclipse.core.runtime.IPath)
     */
    public IImageDataProvider createImageDataProvider(IPath path)
    		throws CoreException {
    	File file = path.toFile();
        if (!file.exists()) {
        	throw new CoreException(
    				Logging.newStatus(CarbideUIPlugin.getDefault(),
    						IStatus.ERROR,
    						MessageFormat.format(
    								Messages.getString("ImageLoader.CouldNotFindImageFormat"), //$NON-NLS-1$
    								new Object[] { path })));
        }
    	try {
    		return createImageDataProviderCore(path);
    	} catch (CoreException e) {
    		throw e;
    	} catch (Throwable t) {
    		throw new CoreException(
    				Logging.newStatus(CarbideUIPlugin.getDefault(),
    						IStatus.ERROR,
    						MessageFormat.format(
    								Messages.getString("ImageLoader.CouldNotLoadImageFormat"), //$NON-NLS-1$
    								new Object[] { path }),
    						t));
    	}
    }
    
    private IImageDataProvider createImageDataProviderCore(IPath path) throws Throwable {
    	
        String ext = path.getFileExtension();
        if (ext != null && (ext.compareToIgnoreCase("svg") == 0 //$NON-NLS-1$
                || ext.compareToIgnoreCase("svgt") == 0)) { //$NON-NLS-1$
        	return new SVGImageDataProvider(path.toFile());
        } else {
    		return new BitmapImageDataProvider(path.toFile());
        }
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.IDisposable#dispose()
     */
    public void dispose() {
    }
    
}
