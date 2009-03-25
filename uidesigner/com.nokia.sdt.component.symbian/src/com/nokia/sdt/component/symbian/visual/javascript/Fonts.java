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

import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.scripting.ScriptGlobals;
import com.nokia.sdt.displaymodel.GlobalCache;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.utils.drawing.FontFactory;
import com.nokia.sdt.utils.drawing.IFont;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Provide a set of common fonts for use by rendering code.
 * 
 * 
 *
 */
public class Fonts {
    static final String FONT_CACHE_ID = "Javascript.FONT_CACHE";
    static class FontCache implements IDisposable {
        public Map map = new HashMap();
        public void dispose() {
            for (Iterator iter = map.values().iterator(); iter.hasNext();) {
                IFont font = (IFont) iter.next();
                font.dispose();
            }
            map.clear();
        }
    }

    static private Map getFontMap() {
        FontCache cache = (FontCache) GlobalCache.getCache().get(FONT_CACHE_ID);
        if (cache == null) {
            cache = new FontCache();
            GlobalCache.getCache().put(FONT_CACHE_ID, cache);
        }
        return cache.map;
    }
    
    /**
     * Load a (cached) font file from a location relative to the 
     * com.nokia.sdt.component.symbian plugin's /data directory
     * For use from script, data automatically freed.
     * 
     * @param relPath relative path to font file
     * @param size the point size of the font
     * @return font; may be null
     */
    static public IFont getGlobalFont(String relPath, float size) {
    	IFont result = null;
    	try {
			File file = FileUtils.pluginRelativeFile(ComponentSystemPlugin.getDefault(), relPath);
			result = getFont(file, size);
    	} catch (IOException x) {
			ComponentSystemPlugin.log(x);
		}
    	return result;
    }
        
    /**
     * Load a (cached) font file from a location relative to the component's definition.
     * For use from script, data automatically freed.
     * 
     * @param relPath relative path to font file
     * @param size the point size of the font
     * @return font; may be null
     */
    static public IFont getLocalFont(String relPath, int size) {
        File file;
        File path = new File(relPath);
        if (!path.isAbsolute())
            file = new File(ScriptGlobals.getIncludeBase(), relPath);
        else
            file = path;

        return getFont(file, size);
    }
    
    static class FontInfo {
        public FontInfo(String absPath, float size) {
            this.absPath = absPath;
            this.size = size;
        }
        public boolean equals(Object obj) {
            if (!(obj instanceof FontInfo))
                return false;
            return ((FontInfo) obj).absPath.equals(absPath)
                && ((FontInfo) obj).size == size;
        }
        public int hashCode() {
            return absPath.hashCode() ^ ((int)size << 16);
        }
        String absPath;
        float size;
    }
    
    public static IFont getFont(File fontFile, float size) {
        Check.checkArg(fontFile);
        String absPath = fontFile.getAbsolutePath();
        FontInfo fi = new FontInfo(absPath, size);
        IFont font = (IFont) getFontMap().get(fi);
        if (font == null) {
            font = FontFactory.createFromFile(fontFile, size);
            if (font != null)
                getFontMap().put(fi, font);
        }
        return font;
    }
}
