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

package com.nokia.carbide.cpp.epoc.engine.model;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;

import java.util.Collection;

/**
 * This interface defines the configuration for a view.
 *
 */
public interface IViewConfiguration {

	/** Get the filter defining how to handle conditional directives */
	IViewFilter getViewFilter();
	
	/** Get the fixed macros (macro name or name=value).
	 * This array should not change after creation since this
	 * configuration is stored in a view.
	 */
	Collection<IDefine> getMacros();

	/**
	 * Get the configuration for the parser.  This may differ based
	 * on SDK (i.e. different include paths or different keywords, etc.)
	 */
	IViewParserConfiguration getViewParserConfiguration();
}
