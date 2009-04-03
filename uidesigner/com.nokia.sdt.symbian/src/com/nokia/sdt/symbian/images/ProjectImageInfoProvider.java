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

package com.nokia.sdt.symbian.images;

import com.nokia.cpp.internal.api.utils.core.CacheMap;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.IDisposable;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.images.IProjectImageInfoProvider;
import com.nokia.sdt.displaymodel.GlobalCache;
import com.nokia.sdt.workspace.IProjectContext;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

/**
 * Provide image info for the project.
 * 
 *
 */
public class ProjectImageInfoProvider extends AdapterImpl implements IProjectImageInfoProvider, IDisposable {

	private static final String PROJECT_TO_IMAGE_MAP_ID = "ProjectImageInfoProvider.MAP"; //$NON-NLS-1$
	
	/**
	 * Get the map
	 * @return Map<IProject, ProjectImageInfo>
	 */
    private CacheMap getProjectToImageDataMap() {
    	CacheMap map = (CacheMap) GlobalCache.getCache().get(PROJECT_TO_IMAGE_MAP_ID);
    	if (map == null) {
    		map = new CacheMap();
    		GlobalCache.getCache().put(PROJECT_TO_IMAGE_MAP_ID, map);
    	}
    	return map;
    }
    
    private IDesignerDataModel model;
    
    public ProjectImageInfoProvider(IDesignerDataModel model) {
        Check.checkArg(model);
        this.model = model;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
     */
    public boolean isAdapterForType(Object type) {
        return type.equals(IProjectImageInfoProvider.class);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.IDisposable#dispose()
     */
    public void dispose() {
    	getProjectToImageDataMap().disposeAll();
    }
    
    /** Get the information about images and image lists accessible
     * to the given instance.
     * @return project image data, or null
     */
    public ProjectImageInfo getProjectImageInfo() {
        IProjectContext projectContext = model.getProjectContext();
        if (projectContext == null)
            return null;
        IProject project = projectContext.getProject();
        if (project == null)
            return null;
        
        ProjectImageInfo data = (ProjectImageInfo) getProjectToImageDataMap().get(project);
        if (data != null) {
            // if the project info changed while this object was in the map...
            if (data.isDirty()) {
            	data.refresh();
            }
        }
        if (data == null) {
            data = new ProjectImageInfo(project);
            getProjectToImageDataMap().put(project, data);
        }
        return data;
    }


}
