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

package com.nokia.sdt.symbian.dm;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.sourcegen.INameGenerator;
import com.nokia.sdt.symbian.ISymbianNameGenerator;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.IDisposable;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Provide names for use in deriving filenames.
 * 
 *
 */
public class SymbianNameGenerator implements ISymbianNameGenerator, IDisposable {

	private String projectName;
	
    /**
     * Create a name generator for a model, using the given directory
     * as the project location (for testing).
     */
    public SymbianNameGenerator(String projectName) {
        Check.checkArg(projectName);
        this.projectName = projectName;
    }

    /**
     * Create a name generator for a model known to be loaded in a running
     * workspace.
     */
    public SymbianNameGenerator(IProject project) {
        Check.checkArg(project);
        this.projectName = project.getName();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.IDisposable#dispose()
     */
    public void dispose() {
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.INameGenerator#getApplicationName()
     */
    public String getApplicationName() {
        String name = projectName;
        return name;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.INameGenerator#getViewName()
     */
    public String getViewName(EObject root) {
        IComponentInstance instance = (IComponentInstance) EcoreUtil.getRegisteredAdapter(root, IComponentInstance.class);
        Check.checkArg(instance);
        return instance.getName();
    }

    protected String getPropertyOrDot(IDesignerDataModel dataModel, String propertyId) {
        String value = dataModel.getProperty(propertyId);
        if (value != null)
            return value;
        else
            return "."; //$NON-NLS-1$
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.INameGenerator#getProjectRelativeDirectory(java.lang.String)
     */
    public String getProjectRelativeDirectory(IDesignerDataModel dataModel, String id) {
        String path;
        if (id.equals(INameGenerator.SOURCE_DIRECTORY_ID))
            path = getPropertyOrDot(dataModel, DesignerDataModel.SOURCE_DIRECTORY_ID);
        else if (id.equals(INameGenerator.INCLUDE_DIRECTORY_ID))
            path = getPropertyOrDot(dataModel, DesignerDataModel.INCLUDE_DIRECTORY_ID);
        else if (id.equals(ISymbianNameGenerator.RESOURCE_DIRECTORY_ID))
            path = getPropertyOrDot(dataModel, DesignerDataModel.RESOURCE_DIRECTORY_ID);
        else if (id.equals(ISymbianNameGenerator.BUILD_DIRECTORY_ID))
            path = getPropertyOrDot(dataModel, DesignerDataModel.BUILD_DIRECTORY_ID);
        else
            path = "."; //$NON-NLS-1$
        return path;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.ISymbianNameGenerator#getRssBaseName(com.nokia.sdt.workspace.IDesignerDataModelSpecifier)
     */
    public String getRssBaseName(IDesignerDataModelSpecifier dmSpec) {
    	return SymbianModelUtils.getModelBaseName(dmSpec);
    }
}
