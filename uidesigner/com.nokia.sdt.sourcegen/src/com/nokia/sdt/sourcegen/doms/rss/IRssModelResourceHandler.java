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

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;

/**
 * This interface manages access to resources for a
 * translation unit.
 * 
 *
 */
public interface IRssModelResourceHandler {
	/**
     * Make a name derived from the given name.
     * This name is not guaranteed to be unique.
     * @param model the model owning the instance
     * @param startName the starting name, usually instance + property
     * @return new name suitable for identifier
     */
    public String getDerivedResourceName(IDesignerDataModel model, String startName);
 
    /**
     * Get a resource with the given type and name,
     * either finding an existing resource or creating a new one.
     * <p>  
     * This returns <code>null</code> if the named
     * resource is already occupied by a resource of a different
     * type or if the file containing the resource is read-only.
     * 
     * @param tu the master translation unit
     * @param decl struct type
     * @param rsrcName desired resource name
     * @return IAstResourceDefinition or null if conflict
     */
    public IAstResourceDefinition findOrCreateUniqueResourceDefinition(
    		ITranslationUnit tu, IAstStructDeclaration decl,
    		String rsrcName);
 
    /**
     * Tell if resource names need to be unique across the project.
     */
    public void setGeneratingProjectUniqueResources(boolean flag);
    
    /**
     * Tell if resource names need to be unique across the project.
     */
    public boolean isGeneratingProjectUniqueResources();
}
