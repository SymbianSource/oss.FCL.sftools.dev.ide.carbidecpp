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

import com.nokia.carbide.cpp.epoc.engine.model.EGeneratedHeaderFlags;

import org.eclipse.core.runtime.IPath;

import java.util.List;

/**
 * This interface represents a set of image sources that are built together into
 * a single output. This acts as a modifiable container for such images, and
 * also provides synthesized build-time information.
 * 
 */
public interface IMultiImageSource {
	/**
	 * Tell if the container is valid, e.g. initialized: must have a target file.
	 */
	boolean isValid();

	/**
	 * Deep copy the contents into a new multi-image source. Does not copy
	 * immutable objects, but copies the sources lists and its contained image
	 * sources.
	 */
	IMultiImageSource copy();

	/**
	 * Set contents from another multi-image source. This preserves the identity
	 * of the sources list, but nothing else.
	 * 
	 * @param multiImageSource
	 */
	void set(IMultiImageSource multiImageSource);

	/**
	 * Get the synthesized full path to the target file. If it is a relative
	 * path, it's target directory-relative (e.g. "resource\\apps\\foo.mif"),
	 * else it's a full path on the local filesystem.
	 * 
	 * @return
	 * @see #getTargetFile()
	 * @see #getTargetPath()
	 */
	IPath getTargetFilePath();

	/**
	 * Get the target filename
	 */
	String getTargetFile();

	/**
	 * Set the target filename
	 * source path
	 */
	void setTargetFile(String name);

	/** Get the header generation flag */
	EGeneratedHeaderFlags getHeaderFlags();

	/** Set the header generation flag */
	void setHeaderFlags(EGeneratedHeaderFlags flag);

	/**
	 * Get the target directory-relative path (without filename), e.g.
	 * "system\\apps". If a full path, it is on the local filesystem.
	 */
	IPath getTargetPath();

	/**
	 * Get the target directory-relative path (without filename), e.g.
	 * "system\\apps", or a full path on the local filesystem.
	 */
	void setTargetPath(IPath path);

	/** Get the bitmap sources, never null. */
	List<IImageSource> getSources();

	/** Set the bitmap sources, may not be null. */
	void setSources(List<IImageSource> sources);

	/** Tell whether the sources contain a disallowed file type. */
	boolean hasDisallowedSources();

	/** Tell whether the container allows BMP files. */
	boolean isBitmapSourceAllowed();

	/** Tell whether the container allows SVG files. */
	boolean isSVGSourceAllowed();

	/**
	 * Create (doesn't add) an unknown image source. These are always
	 * allowed.
	 */
	IImageSource createUnknownImageSource();

	/**
	 * Create (doesn't add) a new BMP image source. Returns null if disallowed
	 * filetype.
	 */
	IBitmapSource createBitmapSource();

	/**
	 * Create (doesn't add) a new SVG image source. Returns null if disallowed
	 * filetype.
	 */
	ISVGSource createSVGSource();

	/**
	 * Get the generated EPOCROOT-relative header filepath
	 * 
	 * @return relative filepath (if starting with epoc32, EPOCROOT-relative),
	 * full path in filesystem, or null for no generated header
	 */
	IPath getGeneratedHeaderFilePath();

	/**
	 * Tell whether the generated header can be set.
	 */
	boolean canSetGeneratedHeaderFilePath();

	/**
	 * Set the generated EPOCROOT-relative header (starting with epoc32),
	 * or full path or null.  Forces #getHeaderFlags() to Header or NoHeader.
	 * <p>
	 * 
	 * @throws UnsupportedOperationException
	 *             if #canSetGeneratedHeaderFilePath() returns false
	 */
	void setGeneratedHeaderFilePath(IPath path);

	/**
	 * Get the generated EPOCROOT-relative header filepath, even if the current
	 * multi-image source will not generate a header
	 * 
	 * @return generated filepath (if starting with \\epoc32, EPOCROOT-relative)
	 */
	IPath getDefaultGeneratedHeaderFilePath();

	/**
	 * Get the generated enumerator for the bitmap, or null for none.
	 */
	String getGeneratedImageEnumerator(IImageSource image);

	/**
	 * Get the generated enumerator for the bitmap, or null for none.
	 */
	String getGeneratedMaskEnumerator(IImageSource image);

	/**
	 * Find an entry that matches the file. This is not an exact path match, but
	 * a case-insensitive match of the filename, which will match the entry as
	 * far as the programmer is concerned (i.e. the enums will be the same).
	 * 
	 * @param filePath
	 * @return matching image source or null
	 */
	IImageSource findMatchingSource(IPath filePath);
}
