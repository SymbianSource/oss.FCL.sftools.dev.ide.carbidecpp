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


package com.nokia.sdt.component.symbian.visual.javascript;
import com.nokia.sdt.component.symbian.scripting.ScriptGlobals;
import com.nokia.sdt.displaymodel.GlobalCache;
import com.nokia.cpp.internal.api.utils.core.IDisposable;
import com.nokia.sdt.utils.ImageUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.mozilla.javascript.Context;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Image support for use by scripts.
 *  
 * 
 *
 */
public class Images {
    static final String ANON_IMAGE_CACHE_ID = "Javascript.ANON_IMAGE_CACHE";
    static final String NAMED_IMAGE_CACHE_ID = "Javascript.NAMED_IMAGE_CACHE";
    static class AnonImageCache implements IDisposable {
        public List list = new ArrayList();
        public void dispose() {
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                Image image = (Image) iter.next();
                if (!image.isDisposed())
                    image.dispose();
            }
            list.clear();
        }
    }
    static class NamedImageCache implements IDisposable {
        public Map map = new HashMap();
        public void dispose() {
            for (Iterator iter = map.values().iterator(); iter.hasNext();) {
                Image image = (Image) iter.next();
                if (!image.isDisposed())
                    image.dispose();
            }
            map.clear();
        }
    }

    static private List getAnonImages() {
        AnonImageCache cache = (AnonImageCache) GlobalCache.getCache().get(ANON_IMAGE_CACHE_ID);
        if (cache == null) {
            cache = new AnonImageCache();
            GlobalCache.getCache().put(ANON_IMAGE_CACHE_ID, cache);
        }
        return cache.list;
    }
    
   
    /** Create new image with RGBA colormap.
     * The image is automatically freed and needn't be disposed
     * explicitly.
     */
    public Image newImage(Device device, int width, int height) {
        Image image = new Image(device, ImageUtils.createStandard32BitImageData(width, height));
        getAnonImages().add(image);
        return image;
    }

    static private Map getNamedImages() {
        NamedImageCache cache = (NamedImageCache) GlobalCache.getCache().get(NAMED_IMAGE_CACHE_ID);
        if (cache == null) {
            cache = new NamedImageCache();
            GlobalCache.getCache().put(NAMED_IMAGE_CACHE_ID, cache);
        }
        return cache.map;
    }

    /**
     * Get an image, accessed by key (filename).  Such an image will
     * be loaded only once.  Please use copyImage() if you want to
     * change a shared image.  The image shouldn't be disposed
     * explicitly when created through this call.
     * @param device
     * @param relPath path relative to the current script
     */
    public Image getImage(Device device, String relPath) {
        File file;
        File path = new File(relPath);
        if (!path.isAbsolute())
            file = new File(ScriptGlobals.getIncludeBase(), relPath);
        else
            file = path;
        
        try {
            file = file.getCanonicalFile();
        } catch (IOException e) {
            // leave alone
        }
        
        String key = file.getAbsolutePath();
        Image image = (Image) getNamedImages().get(key);
        if (image == null || image.isDisposed()) {
            try {
                image = new Image(device, key);
            } catch (Exception e) {
                Context.throwAsScriptRuntimeEx(e);
            }
            getNamedImages().put(key, image);
        }
        
        return image;
    }

    /**
     * Copy an image (from anywhere).  The copy needn't be disposed
     * explicitly when created through this call. 
     */
    public Image copyImage(Device device, Image image) {
        Image copy = new Image(device, image, SWT.IMAGE_COPY);
        getAnonImages().add(copy);
        return copy;
    }
    
    /**
     * Create an image from a foreground and a mask.
     */
    public Image createIcon(Image bitmap, Image bitmapMask) {
        Image image = ImageUtils.createMaskedImage(Display.getDefault(),
                bitmap.getImageData(), bitmapMask.getImageData());
        getAnonImages().add(image);
        return image;
    }
    
    /**
     * Create an image from a foreground and a mask.
     */
    public Image createAlphaMaskedIcon(Image bitmap, Image alphaMask) {
        Image image = ImageUtils.createAlphaMaskedImage(Display.getDefault(),
                bitmap.getImageData(), alphaMask.getImageData());
        getAnonImages().add(image);
        return image;
    }
}
