/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.symbian;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.dm.SymbianModelUtils.SDKType;
import com.nokia.sdt.utils.drawing.IFont;

import org.eclipse.swt.graphics.Point;
import org.osgi.framework.Version;

import java.util.regex.Pattern;

/**
 * Utilities for working with scalable text support in 
 * S60 2.8 and later.
 */
public abstract class ScalableText {
	
		// Maximum number of string variants recognized in a scalable string.
		// This is the S60 limit
	public static int MAX_VARIANTS = 5;
	public static char CHAR_SEPARATOR = '\u0001';
	public static String STR_SEPARATOR = "\u0001"; //$NON-NLS-1$
	
	static Pattern pattern = Pattern.compile(STR_SEPARATOR);
	
	public static boolean isScalableTextAvailable(IDesignerDataModel dataModel) {
	  	if (dataModel == null) return false;
	  	if (SymbianModelUtils.getModelSDK(dataModel) != SDKType.S60)
	  		return false;
		Version sdkVersion = SymbianModelUtils.getComponentVersions(dataModel);
		return sdkVersion.getMajor() >= 3 || 
		       (sdkVersion.getMajor() == 2 && sdkVersion.getMinor() >= 8);
	}
	
	public static String chooseScalableText(String text, IFont font, int availableWidth) {
		String result = null;
		String[] variants = splitScalableText(text);
		if (variants != null) {
			if (variants.length == 1) {
				result = variants[0];
			} else {
				
				// This is what S60 3.0 AknTextUtils::ChooseScalableText does
				// Find the longest fitting variant.
				// If none fit, use the shortest.
				
				String bestFit = null;
				int bestFitLength = Integer.MIN_VALUE;
				String shortest = null;
				int shortestLength = Integer.MAX_VALUE;
				for (String variant : variants) {
					Point extent = font.stringExtent(variant);
					if (extent.x <= availableWidth && 
						extent.x > bestFitLength) {
						bestFit = variant;
						bestFitLength = extent.x;
					}
					if (extent.x < shortestLength) {
						shortest = variant;
						shortestLength = extent.x;
					}
				}
				
				if (bestFit != null) {
					result = bestFit;
				} else {
					result = shortest;
				}
			}
		}
		return result;
	}
	
	/**
	 * Returns scalable strings in the order they are found.
	 * No more than MAX_VARIANTS is returned. Any additional
	 * separators are ignored, so in the event too many variants
	 * exist they are all concatenated onto the last string
	 * in the array
	 */
	public static String[] splitScalableText(String text) {
		String[] result = null;
		if (text != null) {
			result = pattern.split(text, MAX_VARIANTS);
		}
		return result;
	}

}
