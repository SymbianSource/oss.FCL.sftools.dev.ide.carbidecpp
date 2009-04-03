/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen;

import com.nokia.sdt.datamodel.IDesignerDataModel;

import org.eclipse.emf.ecore.EObject;

/**
 * This interface is used to provide names (files and classes)
 * to the source generator.  An instance is associated with
 * a source gen session.
 * 
 * 
 *
 */
public interface INameGenerator {
    /** Get the application base name, which should mirror the executable,
     * for use in generating the default .rss filename (i.e. "MyProject") */
    public String getApplicationName();
    
    /** Get the view name for the given data model */
    public String getViewName(EObject root);
    
    /** 
     * Get the directory path for the given id, 
     * relative to the project.
     * @param dataModel 
     * @param id a fixed name for the kind of directory to find
     * @return a project-relative path (either "." or "" can be
     * returned to refer to the project itself)
     */
    public String getProjectRelativeDirectory(IDesignerDataModel dataModel, String id);
    
    /** Identifier for source directory */
    public static final String SOURCE_DIRECTORY_ID = "src"; //$NON-NLS-1$
    /** Identifier for include directory */
    public static final String INCLUDE_DIRECTORY_ID = "inc"; //$NON-NLS-1$
}
