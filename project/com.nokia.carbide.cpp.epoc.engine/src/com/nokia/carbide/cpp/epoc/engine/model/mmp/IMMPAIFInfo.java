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

package com.nokia.carbide.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.cpp.epoc.engine.image.*;

import org.eclipse.core.runtime.IPath;

import java.util.List;

/**
 * Abstract representation of the AIF statement.
 *
 */
public interface IMMPAIFInfo {
	/** tell if this is valid, e.g., initialized: target, source, resource must be set */
	boolean isValid();
	
	/** Get the TARGETPATH-relative file */
	IPath getTarget();
	/** Set the TARGETPATH-relative target file */
	void setTarget(IPath path);

	/** Get the project-relative resource file */
	IPath getResource();
	/** Set the project-relative resource file */
	void setResource(IPath path);
	
	/** Get color flag for bitmaps */
	boolean isColor();
	/** Set color flag for bitmaps */
	void setColor(boolean color);
	/** Get color depth for bitmaps; may be 0 if no bitmaps */
	int getColorDepth();
	/** Set color depth for bitmaps */
	void setColorDepth(int depth);
	/** Get mask depth for bitmaps; may be 0 */
	int getMaskDepth();
	/** Set mask depth for bitmaps; may be 0 */
	void setMaskDepth(int depth);
	/** Access/modify the project-relative bitmaps -- this does not include SVG files! */
	List<IBitmapSourceReference> getSourceBitmaps();
	/** Set the project-relative bitmaps -- this does not include SVG files! */
	void setSourceBitmaps(List<IBitmapSourceReference> bitmaps);
	
	/** create a new, empty, invalid bitmap source reference (not added)
	 * @see IBitmapSourceReference#isValid()
	 */
	IBitmapSourceReference createBitmapSourceReference();

	/**
	 * Get a copy of the image format.
	 * @return
	 */
	ImageFormat getImageFormat();

	/**
	 * Deep copy the contents
	 * @return
	 */
	IMMPAIFInfo copy();

	/**
	 * Set contents from another.
	 * @param aifInfo
	 */
	void set(IMMPAIFInfo aifInfo);
}
