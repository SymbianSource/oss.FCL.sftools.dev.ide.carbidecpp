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
package com.nokia.sdt.displaymodel;

import com.nokia.sdt.looknfeel.feel.*;
import com.nokia.sdt.looknfeel.feel.impl.LookAndFeelResourceFactoryImpl;
import com.nokia.cpp.internal.api.utils.core.CacheMap;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.drawing.IFont;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Display;

import java.io.IOException;
import java.util.*;

public abstract class XMLLookAndFeel implements ILookAndFeel {
	
	private String id;
		// Use per-type maps so we could move to generics later
		// also gives separate names spaces for each
	private Map colors = new HashMap();
	private Map fonts = new HashMap();
	private Map images = new HashMap();
	private Map positions = new HashMap();
	private Map dimensions = new HashMap();
	private Map rects = new HashMap();
	private Map integers = new HashMap();
	private Map booleans = new HashMap();
	private Map strings = new HashMap();

    static class ScaledImageKey {
        private String key;
        private Point size;
        ScaledImageKey(String key, Point size) {
            this.key = key;
            this.size = new Point(size.x, size.y);
        }
        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        public boolean equals(Object obj) {
            if (!(obj instanceof ScaledImageKey))
                return false;
            return ((ScaledImageKey)obj).key.equals(key)
            && ((ScaledImageKey)obj).size.equals(size);
        }
        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        public int hashCode() {
            return key.hashCode() ^ size.hashCode();
        }
    }
	// disposable resources must be added here
	private CacheMap cache = new CacheMap();
	private boolean debug = false;
	
	public XMLLookAndFeel() {		
	}
	
	protected void initFromURI(URI xmlURI) throws IOException {
		id = xmlURI.toString();
		load(xmlURI);
	}
	
	public String getID() {
		return id;
	}
	
	public void load(URI xmlURI) throws IOException {
		LookAndFeelResourceFactoryImpl factory = new LookAndFeelResourceFactoryImpl();
		Resource resource = factory.createResource(xmlURI);
		resource.load(null);
		EList contents = resource.getContents();
		if (contents != null && contents.size() == 1) {
			DocumentRoot docRoot = (DocumentRoot) contents.get(0);
			LookAndFeelType lft = docRoot.getLookAndFeel();
			initializeFromModel(lft);
		}	
	}
	
	private void initializeFromModel(LookAndFeelType lft) {
        List list = lft.getString();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            StringType stringVal = (StringType) iter.next();
            strings.put(stringVal.getKey(), stringVal.getValue());
        }

		list = lft.getColor();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			ColorType color = (ColorType) iter.next();
			addColor(color.getKey(), color.getR(), color.getG(), color.getB());
		}
		
		list = lft.getFont();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			FontType font = (FontType) iter.next();
			addFont(font.getKey(), font.getInitData(), font.getSize());
		}
	
		list = lft.getImage();
        String base = getString(KEY_IMAGE_DIR);
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			ImageType mit = (ImageType) iter.next();
			addMaskedImage(mit.getKey(), base, mit.getImageFile(), null);
		}

		list = lft.getMaskedImage();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			MaskedImageType mit = (MaskedImageType) iter.next();
			addMaskedImage(mit.getKey(), base, mit.getImageFile(), mit.getMaskFile());
		}

		list = lft.getPosition();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			PositionType pos = (PositionType) iter.next();
			positions.put(pos.getKey(), new Point(pos.getX(), pos.getY()));
		}	
		
		list = lft.getDimension();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			DimensionType dim = (DimensionType) iter.next();
			dimensions.put(dim.getKey(), new Point(dim.getWidth(), dim.getHeight()));
		}
		
		list = lft.getRectangle();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			RectangleType rect = (RectangleType) iter.next();
			rects.put(rect.getKey(), 
					new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()));
		}
		
		list = lft.getInteger();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			IntegerType intVal = (IntegerType) iter.next();
			integers.put(intVal.getKey(), new Integer(intVal.getValue()));
		}
		
		list = lft.getBoolean();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			BooleanType boolVal = (BooleanType) iter.next();
			booleans.put(boolVal.getKey(), Boolean.valueOf(boolVal.isValue()));
		}

		initializeAliasList(lft.getStringAlias(), strings);
		initializeAliasList(lft.getColorAlias(), colors);
		initializeAliasList(lft.getFontAlias(), fonts);
		initializeAliasList(lft.getPositionAlias(), positions);
		initializeAliasList(lft.getDimensionAlias(), dimensions);
		initializeAliasList(lft.getRectangleAlias(), rects);
		initializeAliasList(lft.getIntegerAlias(), integers);
		initializeAliasList(lft.getBooleanAlias(), booleans);
	}
	
	private void initializeAliasList(List aliases, Map map) {
		for (Iterator iter = aliases.iterator(); iter.hasNext();) {
			AliasType alias = (AliasType) iter.next();
			Object target = map.get(alias.getRef());
			if (target != null)
				map.put(alias.getKey(), target);
			else
				warnMissingKey(alias.getRef());
		}
	}
	
	public void dispose() {
		cache.disposeAll();
	}
	
	public void addColor(String key, int r, int g, int b) {
		Color c = new Color(Display.getDefault(), r,g,b);
		addColor(key, c);
	}
	
	public void addColor(String key, Color color) {
		cache.put(color, color);
		colors.put(key, color);
	}

	protected abstract void addFont(String key, String initializationData, float size);

	protected void addFont(String key, IFont font) {
		fonts.put(key, font);
		cache.put(font, font);
	}
	
	protected abstract void addMaskedImage(String key, String baseDir, String imageFile, String maskFile);
	
	protected void addImage(String key, Image image) {
		images.put(key, image);
		cache.put(image, image);
	}
	
	public Color getColor(String key) {
		Color result = (Color) colors.get(key);
		if (result == null && debug)
			warnMissingKey(key);
		return result;
	}

	public IFont getFont(String key) {
		IFont result = (IFont) fonts.get(key);
		if (result == null && debug)
			warnMissingKey(key);
		return result;
	}
	
	public Image getImage(String key) {
		Image result = (Image) images.get(key);
		if (result == null && debug)
			warnMissingKey(key);
		return result;
	}
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.displaymodel.ILookAndFeel#getImage(java.lang.String, org.eclipse.swt.graphics.Point)
     */
    public Image getImage(String key, Point size) {
        Image result = (Image) images.get(key);
        if (result == null) {
            if (debug)
                warnMissingKey(key);
        } else {
            // scale image only if not native size
            if (result.getBounds().width != size.x
                || result.getBounds().height != size.y) {
                ScaledImageKey skey = new ScaledImageKey(key, size);
                Image scaledResult = (Image) images.get(skey);
                if (scaledResult == null) {
                    scaledResult = new Image(Display.getDefault(),
                            result.getImageData().scaledTo(size.x, size.y));
                    images.put(skey, scaledResult);
                }
                result = scaledResult;
            }
        }
        return result;
    }

	public Point getPosition(String key) {
		Point result = (Point) positions.get(key);
		if (result == null && debug)
			warnMissingKey(key);
		return result;
	}

	public Point getDimension(String key) {
		Point result = (Point) dimensions.get(key);
		if (result == null && debug)
			warnMissingKey(key);
		return result;
	}

	public Rectangle getRectangle(String key) {
		Rectangle result = (Rectangle) rects.get(key);
		if (result == null && debug)
			warnMissingKey(key);
		return result;
	}

	public int getInteger(String key, int defaultValue) {
		Integer value = (Integer) integers.get(key);
		if (value == null && debug)
			warnMissingKey(key);
		return value != null? value.intValue() : defaultValue;
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		Boolean value = (Boolean)booleans.get(key);
		if (value == null && debug)
			warnMissingKey(key);
		return value != null? value.booleanValue() : defaultValue;
	}
	
	public String getString(String key) {
	    String value = (String)strings.get(key);
	    if (value == null && debug)
	        warnMissingKey(key);
	    return value;
	}
	
	protected void warnMissingKey(String key) {
		Check.reportFailure(key, null);
	}
}
