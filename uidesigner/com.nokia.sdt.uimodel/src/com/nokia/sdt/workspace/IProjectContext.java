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
package com.nokia.sdt.workspace;

import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.sourcegen.ISourceGenProvider;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;


/**
 * Interface onto UI Designer project in the workspace.
 * 
 * Note: In Eclipse IProject instances are not maintained
 * across project renames. Instead the old IProject is "deleted"
 * and a new one added. Given that, IProjectContexts are not 
 * preserved across renames. So it's better to dynamically
 * get references rather than cache them.
 */
public interface IProjectContext extends IAdaptable {

	/**
	 * Release any resources owned by this project context.
	 * Includes any cached data models and images
	 */
	void dispose();
	
	/**
	 * Called when the workspace context has noticed
	 * that resource changes have occured in the project
	 * @return true if the project context is considered changed from
	 * the perspective of clients
	 * @param delta is provided if the refresh is in response to a
	 * workspace resource change event, otherwise its null. It can
	 * optionally be used as a hint in updating.
	 */
	boolean refresh(IResourceDelta delta);
	
	/**
	 * @return the underlying Eclipse project
	 */
	IProject getProject();
	
	IDesignerDataModelSpecifier[] getModelSpecifiers();
	
	/**
	 * Determine if the given path refers to a resource in
	 * a data model. If so, returns the specifier for that model
	 * @param relPath
	 * @return specifier
	 */
	IDesignerDataModelSpecifier findSpecifierForPath(IPath relPath);
	
	void unloadCachedModels();
    
    /**
     * Clear the messages generated for this project or
     * for components used in this project,
     * e.g. upon a save, reload, or source generation step.
     */
    void resetMessages(IComponentSet set);
    
    /**
     * Get the sourcegen provider, creating one if necessary.
     */
    ISourceGenProvider getSourceGenProvider();

	/**
	 * Set the sourcegen provider (for testing, to pre-supply
	 * the provider and avoid generating the default one).
	 * @param sgProvider
	 */
	void setSourceGenProvider(ISourceGenProvider sgProvider);
}
