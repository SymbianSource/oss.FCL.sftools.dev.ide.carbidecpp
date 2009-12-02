/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.image;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to convert back/forth from the compact
 * is-color/depth/mask depth strings used in Symbian build files.
 *
 */
public class ImageFormat {
	// tolerate but ignore extra 'c'
	private static final Pattern FORMAT_PATTERN = Pattern.compile("[/-]?(c?)(\\d+)(,c?(\\d+))?", //$NON-NLS-1$
			Pattern.CASE_INSENSITIVE);
	public boolean isColor;
	public int depth;
	public int maskDepth;
	
	public static boolean matches(String format) {
		return FORMAT_PATTERN.matcher(format).matches();
	}
	
	public ImageFormat(String format) {
		Matcher matcher = FORMAT_PATTERN.matcher(format);
		if (!matcher.matches())
			throw new IllegalArgumentException("Invalid bitmap format: " + format); //$NON-NLS-1$
		this.isColor = matcher.group(1).length() != 0; 
		this.depth = Integer.parseInt(matcher.group(2)); 
		this.maskDepth = matcher.group(3) != null ? Integer.parseInt(matcher.group(4)) : 0; 
	}
	
	public ImageFormat(boolean isColor, int depth, int maskDepth) {
		this.isColor = isColor;
		this.depth = depth;
		this.maskDepth = maskDepth;
	}

	public ImageFormat(boolean isColor, int depth) {
		this.isColor = isColor;
		this.depth = depth;
		this.maskDepth = 0;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (isColor)
			builder.append('c');
		builder.append(depth);
		if (maskDepth > 0) {
			builder.append(',');
			builder.append(maskDepth);
		}
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ImageFormat))
			return false;
		ImageFormat other = (ImageFormat) obj;
		return other.isColor == isColor && other.depth == depth && other.maskDepth == maskDepth;
	}
	
	public boolean isColor() {
		return isColor;
	}
 
	/**
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}
	
	/**
	 * @return the maskDepth
	 */
	public int getMaskDepth() {
		return maskDepth;
	}
	
}
