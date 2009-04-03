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
import com.nokia.sdt.displaymodel.GlobalCache;
import com.nokia.cpp.internal.api.utils.core.IDisposable;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import java.util.*;

/**
 * Sample colors for use by scripts.
 *  
 * 
 *
 */
public class Colors {
    static final String COLOR_CACHE_ID = "Javascript.COLOR_CACHE";
    static class ColorCache implements IDisposable {
        public Map map = new HashMap();
        public void dispose() {
            for (Iterator iter = map.values().iterator(); iter.hasNext();) {
                Color color = (Color) iter.next();
                if (!color.isDisposed())
                    color.dispose();
            }
            map.clear();
        }
    }

    static private Map getColorMap() {
        ColorCache cache = (ColorCache) GlobalCache.getCache().get(COLOR_CACHE_ID);
        if (cache == null) {
            cache = new ColorCache();
            GlobalCache.getCache().put(COLOR_CACHE_ID, cache);
        }
        return cache.map;
    }
    
   
    /** Get a (cached) color (each element ranges from 0 to 255) (automatically freed) */
    public static Color getColor(int red, int green, int blue) {
        int comb = (red << 16) | (green << 8) | blue;
        Integer key = new Integer(comb);
        Color c = (Color) getColorMap().get(key);
        if (c == null) {
            c = new Color(Display.getDefault(), red, green, blue);
            getColorMap().put(key, c);
        }
        return c;
    }
    
}
