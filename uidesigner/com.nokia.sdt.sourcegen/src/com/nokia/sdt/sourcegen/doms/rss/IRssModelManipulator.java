/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
/**
 * 
 */
package com.nokia.sdt.sourcegen.doms.rss;

import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.core.ResourceTracker;

/**
 * This is the main interface to the RSS engine.
 * 
 *
 */
public interface IRssModelManipulator {

	/**
	 * Get the model proxy
	 */
	public IRssModelProxy getModelProxy();
	
	/**
	 * Get the name generator
	 */
	public INameGenerator getNameGenerator();
	
	/**
	 * Get the include handler
	 */
	public IIncludeFileLocator getIncludeHandler();
	
	/**
	 * Get the source formatter
	 */
	public ISourceFormatter getSourceFormatter();
	
	/**
	 * Get the resource tracker, which manages instance -> resource
	 * mappings for the model.
	 * @return a resource tracker
	 */
	public ResourceTracker getResourceTracker();

	/**
	 * Get the file manager, which manages the various
	 * files associated with a model
	 */
	//public IRssProjectFileManager getFileManager();
	
	/**
	 * Get the type handler, which manages
	 * enums and structs
	 */
	public IRssModelTypeHandler getTypeHandler();
	
	/**
	 * Get the resource handler
	 */
	public IRssModelResourceHandler getResourceHandler();
	
	/**
	 * Get the variable provider
	 */
	public IVariableProvider getVariableProvider();
}
