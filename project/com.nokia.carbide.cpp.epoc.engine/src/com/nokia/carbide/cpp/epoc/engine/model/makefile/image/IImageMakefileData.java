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

package com.nokia.carbide.cpp.epoc.engine.model.makefile.image;

import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.IData;

import org.eclipse.cdt.make.core.makefile.*;

import java.util.List;

/**
 * This is the interface to reading image Makefile contents.  It is read-only.
 *
 */
public interface IImageMakefileData extends IData<IImageMakefileView> {

	/** Get CDT representation of makefile, with read-only access. */
	IMakefile getMakefile(); 
	
	/**
	 * Read the multi-image sources built in the Makefile
	 * (e.g., one per call to mifconv).
	 */
	List<IMultiImageSource> getMultiImageSources();
	
	/**
	 * Get the default target under which to add new mifconv commands.
	 * If null, the filename of a multi-image source itself is the default target.
	 * @return target name, or null
	 */
	String getDefaultImageTarget();
}
