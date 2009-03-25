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
package com.nokia.sdt.component.symbian.designerimages;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IComponentAdapter;
import com.nokia.sdt.component.adapter.IDesignerImages;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.sdt.displaymodel.GlobalCache;
import com.nokia.sdt.emf.component.DesignerImagesType;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

/**
 * Implements the IDesignerImages component facet.
 * This class maintains a global cache of images referenced by components.
 * The cache is keyed by the canonical path of the image file.
 * All images are assumed to be relative to the base directory for a component, which is the
 * directory containing the main XML file.
 * Support is provided for flushing all images for a single component (to handle reloading
 * scenarios, as well as flushing all images.
 */
public class DesignerImageAdapter implements IDesignerImages, IComponentAdapter {

	private Plugin plugin;
	private IComponent component;
//	private DesignerImagesType designerImages;
	
	private String smallIconKey;
	private String largeIconKey;
	private String staticImageKey;
	private String thumbnailKey;
	
	private final static String IMAGE_CACHE_ID = "DesignerImageAdapter.ImageCache";
    static class ImageCache implements IDisposable {
        public Map map = new HashMap();
        public void dispose() {
            for (Iterator iter = map.values().iterator(); iter.hasNext();) {
                Image image = (Image) iter.next();
                image.dispose();
            }
            map.clear();
        }
    }

	
	DesignerImageAdapter(Plugin plugin, IComponent component, DesignerImagesType designerImages) {
		//Check.checkArg(plugin); // EJS: not needed
		Check.checkArg(component);
		Check.checkArg(designerImages);
		Check.checkArg(component instanceof IFacetContainer);
		this.plugin = plugin;
		this.component = component;
//		this.designerImages = designerImages;
		
		smallIconKey = makeKey(designerImages.getSmallIconFile());
		largeIconKey = makeKey(designerImages.getLargeIconFile());
		staticImageKey = makeKey(designerImages.getLayoutImageFile());
		thumbnailKey = makeKey(designerImages.getThumbnailFile());
	}
	
		// The key for each image is the canonical absolute path
	private String makeKey(String relPath) {
		String result = null;
		if (relPath != null && relPath.length() > 0) {
			// handle case where relPath is actually an absolute path
			IPath path = new Path(relPath);
			if (path.isAbsolute())
				return relPath;
			
			IFacetContainer fc = (IFacetContainer) component;
			File baseDir = fc.getBaseDirectory();
			File imageFile = new File(baseDir, relPath);
			try {
				result = imageFile.getCanonicalPath();
			}
			catch (IOException x) {
				Object args[] = {component.getId(), imageFile.getAbsolutePath()};
				String msg = MessageFormat.format(Messages.getString("badPath"), args);
				Logging.log(plugin, Logging.newStatus(plugin, IStatus.ERROR, msg));
			}
		}
		return result;
	}
	
	private static synchronized Image loadImage(String key) {
		Image result = (Image)getImageMap().get(key);
		if (result == null) {
            try {
    			ImageData id = new ImageData(key);
    			ImageDescriptor idesc = ImageDescriptor.createFromImageData(id);
    			result = idesc.createImage();
    			getImageMap().put(key, result);
//    			System.out.println("Loading image: " + id);
            } catch (Exception e) {
                Logging.log(ComponentSystemPlugin.getDefault(), 
                        Logging.newStatus(ComponentSystemPlugin.getDefault(), e));
                result = new Image(Display.getDefault(), 1, 1);
            }
		}
		return result;
	}
	
	public Image getSmallIcon() {
		return smallIconKey != null? loadImage(smallIconKey) : null;
	}

	public Image getLargeIcon() {
		return largeIconKey != null? loadImage(largeIconKey) : null;
	}

	public Image getStaticLayoutImage() {
		return staticImageKey != null? loadImage(staticImageKey) : null;
	}

	public Image getThumbnailImage() {
		return thumbnailKey != null? loadImage(thumbnailKey) : null;
	}
    
	public IComponent getComponent() {
		return component;
	}

    static private Map getImageMap() {
        ImageCache cache = (ImageCache) GlobalCache.getCache().get(IMAGE_CACHE_ID);
        if (cache == null) {
            cache = new ImageCache();
            GlobalCache.getCache().put(IMAGE_CACHE_ID, cache);
        }
        return cache.map;
    }

	public ImageDescriptor getLargeIconDescriptor() {
		if (largeIconKey == null) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
		try {
			return ImageDescriptor.createFromURL(new URL("file", null, largeIconKey));
		} catch (MalformedURLException e) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
	}

	public ImageDescriptor getSmallIconDescriptor() {
		if (smallIconKey == null) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
		try {
			return ImageDescriptor.createFromURL(new URL("file", null, smallIconKey));
		} catch (MalformedURLException e) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
	}

	
}
