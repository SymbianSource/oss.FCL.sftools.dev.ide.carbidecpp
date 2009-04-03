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
package com.nokia.sdt.symbian.ui.editors;

import com.nokia.sdt.component.property.AbstractPropertyEditorFactory;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.*;

import java.util.*;
import java.util.regex.Pattern;

public class AbstractRGBEditorFactory extends AbstractPropertyEditorFactory {
	
	static Pattern rgbPattern = Pattern.compile(","); //$NON-NLS-1$
	
	private static final String BUNDLE_NAME = "com.nokia.sdt.symbian.ui.editors.SystemColors"; //$NON-NLS-1$

	static HashMap sysColorToLocalized;
	static HashMap localizedToSysColor;
	static String noneDisplayString;
	static final String noneValue = ""; //$NON-NLS-1$
	
	public ILabelProvider createLabelProvider(EObject object, String propertyPath) {
		ILabelProvider result = null;
		try {
			result = new RGBLabelProvider(object);
		}
		catch (CoreException x) {
			UIPlugin.log(x);
			throw new RuntimeException(x);
		}
		return result;
	}
	
	protected static void initSysColorMaps() {
		if (sysColorToLocalized == null) {
			sysColorToLocalized = new HashMap();
			localizedToSysColor = new HashMap();
			ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);
			Enumeration keys = bundle.getKeys();
			while (keys.hasMoreElements()) {
				String sysColor = (String) keys.nextElement();
				String localized = bundle.getString(sysColor);
				sysColorToLocalized.put(sysColor, localized);
				localizedToSysColor.put(localized, sysColor);
			}
			noneDisplayString = Messages.getString("RGBEditorFactory.1"); //$NON-NLS-1$
		}
	}
	
	
	static RGB valueAsRGB(Object value, ILookAndFeel laf) {
		RGB result = null;
		if (value instanceof String) {
			// This implementation allows only decimal values
			// from 0-255 for each component.
			String[] split = rgbPattern.split(value.toString());
			if (split != null && split.length == 3) {
				int red = TextUtils.parseInt(split[0], -1);
				int green = TextUtils.parseInt(split[1], -1);
				int blue = TextUtils.parseInt(split[2], -1);
				try {
					// throws if a component is <0 or >255
					result = new RGB(red, green, blue);
				}
				catch (IllegalArgumentException x) {
				}
			}
			else if (laf != null){
				// check if a system color
				Color color =  laf.getColor((String)value);
				if (color != null) {
					result = color.getRGB();
				}
			}
		}
		return result;
	}
	
	static String rgbAsText(RGB value) {
		if (value == null) return null;
		StringBuffer buf = new StringBuffer();
		buf.append(Integer.toString(value.red));
		buf.append(","); //$NON-NLS-1$
		buf.append(Integer.toString(value.green));
		buf.append(","); //$NON-NLS-1$
		buf.append(Integer.toString(value.blue));
		return buf.toString();
	}

	// Based on code in ColorCellEditor
	private static Image makeRGBImage(RGB value, boolean transparentOnly) {
		ImageData id = createColorImage(value, transparentOnly);
		ImageData mask = id.getTransparencyMask();
		Image result = new Image(null, id, mask);
		return result;
	}

	private static final int DEFAULT_EXTENT = 16;
	  /**
     * Creates and returns the color image data for the given control
     * and RGB value. The image's size is either the control's item extent 
     * or the cell editor's default extent, which is 16 pixels square.
     *
     * @param color the color
     */
    private static ImageData createColorImage(RGB color, boolean transparentOnly) {

        int vsize = 10;
        int hsize = 16;
        int indent = 2;
        int extent = DEFAULT_EXTENT;
    
        int width = indent + hsize;
        int height = extent;

        int xoffset = indent;
        int yoffset = (height - vsize) / 2;

        RGB black = new RGB(0, 0, 0);
        PaletteData dataPalette = new PaletteData(new RGB[] { black, black,
                color });
        ImageData data = new ImageData(width, height, 4, dataPalette);
        data.transparentPixel = 0;
        if (!transparentOnly) {
	        int vend = vsize - 1;
	        int hend = hsize - 1;
	        for (int y = 0; y < vsize; y++) {
	            for (int x = 0; x < hsize; x++) {
	                if (x == 0 || y == 0 || x == hend || y == vend)
	                    data.setPixel(x + xoffset, y + yoffset, 1);
	                else
	                    data.setPixel(x + xoffset, y + yoffset, 2);
	            }
	        }
        }
        return data;
    }

    static class RGBLabelProvider extends LabelProvider {
    	
    	private ILookAndFeel laf;
    	private Image lastImage;
    	private static Image sysColorImage;
    	
    	RGBLabelProvider(EObject object) throws CoreException {
    		initSysColorMaps();
    		
    		IDisplayModel displayModel = ModelUtils.getDisplayModel(object);
    		if (displayModel != null) {
    			laf = displayModel.getLookAndFeel();
    		}
    		
    		if (sysColorImage == null) {
    			sysColorImage = makeRGBImage(new RGB(255, 255, 255), true);
    		}
    	}
    	
		public void dispose() {
			if (lastImage != null) {
				lastImage.dispose();
				lastImage = null;
			}
			super.dispose();
		}

		public Image getImage(Object element) {
			if (lastImage != null) {
				lastImage.dispose();
				lastImage = null;
			}
			
			Image result = null;
			RGB value = valueAsRGB(element, laf);
			if (value != null) {
				result = makeRGBImage(value, false);
				lastImage = result;
			}
			else {
				
				result = sysColorImage;
			}
			return result;
		}

		public String getText(Object element) {
			String result = (String)sysColorToLocalized.get(element);
			if (result == null) {
				if (element == null)
					result = noneDisplayString;
				else if (element instanceof String && ((String)element).length() == 0)
					result = noneDisplayString;
				else
					result = super.getText(element);
			}
			return result;
		}			
	}
    
    
}
