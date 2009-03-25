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

import com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.ITranslationUnitProvider;

import org.eclipse.core.runtime.IPath;

/**
 * This provides implementations for file lookup and TU caching.
 * Clients may extend to pass additional information to a specific
 * parser.
 *
 */
public interface IViewParserConfiguration {
	/**
	 * Get the full path of the project (ordinarily #getProject()#getLocation())
	 * @return full path, must not be null
	 */
	IPath getProjectLocation();
	
	/**
	 * Get the provider of previously cached translation units,
	 * for use by #include.
	 * @return instance of translation unit provider (never null)
	 */
	ITranslationUnitProvider getTranslationUnitProvider();
	
	/**
	 * Get the lookup semantics for #include files.
	 * @return instance of include file locator (never null)
	 */
	IIncludeFileLocator getIncludeFileLocator();
	
	/** 
	 * Get the provider for IDocument instances for files.
	 * <p>
	 * @return IModelDocumentProvider instance, must not be null
	 * @since Carbide.c++ 1.3 (this change breaks API since ITranslationUnitProvider is insufficient)
	 */
	IModelDocumentProvider getModelDocumentProvider();
}
