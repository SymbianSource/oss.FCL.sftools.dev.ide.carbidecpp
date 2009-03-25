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
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileViewBase;

import java.util.List;

/**
 * Interface to image-makefile specific commands.
 * <p>
 * Views are created with an IImageMakefileViewConfiguration.
 * 
 * @see IImageMakefileViewConfiguration
 */
public interface IImageMakefileView extends IView, IMakefileViewBase, IImageMakefileData {
	/**
	 * Access/modify the multi-image sources built in the Makefile
	 * (e.g., one per call to mifconv).
	 * <p>
	 * Multi-image sources may be added to or removed from the map. 
	 * <p>
	 * (note: "project" comes from IViewConfiguration#IViewParserConfiguration#getProjectRoot())
	 */
	List<IMultiImageSource> getMultiImageSources();
	
	/**
	 * Create a new, invalid multi-image source (not added)
	 * @see IMultiImageSource#isValid()
	 * @return new container
	 */
	IMultiImageSource createMultiImageSource();
	
	/**
	 * Set the default target under which to add new mifconv commands.
	 * If null, the filename of a multi-image source itself is the default target.
	 * @param targetName new name, or null
	 */
	void setDefaultImageTarget(String targetName);
	
	/**
	 * Get the default target under which to add new mifconv commands.
	 * If null, the filename of a multi-image source itself is the default target.
	 * @return target name, or null
	 */
	String getDefaultImageTarget();
	
	/**
	 * Get the string appearing in the Makefile that is the target of the multi-image source.
	 * This may, for instance, have $(EPOCROOT) or $(ZDIR) stuck to it, while the IMultiImageSource
	 * does not have this prefix.
	 * @param source
	 * @return target name
	 */
	String getUnexpandedMultiImageSourceTargetPath(IMultiImageSource source);

	IImageMakefileData getData();
}
