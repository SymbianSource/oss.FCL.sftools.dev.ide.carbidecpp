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


import java.util.List;

/**
 * Implement this interface to parse or create a command line.
 *
 */
public interface IImageBuilderCommandLineConverter {
	/** Parse the given command line and create a multi-image source, or
	 * null if errors encountered.
	 * @param view
	 * @param argv
	 * @return
	 */
	IMultiImageSource parse(IImageMakefileView view, List<String> argv);
	
	/**
	 * Create a command line from the given multi-image source.
	 * @param view
	 * @param container
	 * @param origArgv original arguments if updating command line
	 * @return
	 */
	List<String> create(IImageMakefileView view, IMultiImageSource container, List<String> origArgv);
}
