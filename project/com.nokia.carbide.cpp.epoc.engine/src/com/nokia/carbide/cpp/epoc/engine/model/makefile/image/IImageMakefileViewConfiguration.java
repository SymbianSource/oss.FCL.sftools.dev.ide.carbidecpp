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

import com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileViewConfiguration;

/**
 * This configuration provides the expected names for the
 * variables and tools used in processing image makefiles.
 *
 */
public interface IImageMakefileViewConfiguration extends
		IMakefileViewConfiguration {

	/** get name of tool that builds images (e.g. mifconv) */
	String getImageBuilderName(); 
	
	/** Get a converter for creating/rewriting the image builder command lines */
	IImageBuilderCommandLineConverter getImageBuilderCommandLineConverter();
}
