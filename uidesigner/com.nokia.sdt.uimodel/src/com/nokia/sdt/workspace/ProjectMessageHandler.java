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

package com.nokia.sdt.workspace;

import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

/**
 * Handler for messages generated during sourcegen.
 * Such errors usually derive from component files,
 * templates, or problems finding headers and files.
 * 
 * 
 *
 */
public class ProjectMessageHandler extends CoreMessageHandler implements IMessageListener {

    IProject project;
    
    /**
     * 
     */
    public ProjectMessageHandler(IProject project) {
        super();
        Check.checkArg(project);
        this.project = project;
    }

	public boolean isHandlingMessage(IMessage msg) {
		// ignore messages for other projects
        IPath msgPath = msg.getPath();
        if (msgPath == null
         || msgPath.segmentCount() == 0 
         || !msgPath.segment(0).equals(project.getName())) {
            return false;
        }
		
        return true;
	}

    public void emitMessage(IMessage msg) {
        IResource rsrc = null;
        IPath msgPath = msg.getPath();
        if (msgPath != null)
            rsrc = root.findMember(msgPath);
        if (rsrc == null)
            rsrc = project;
        
        createMarker(rsrc, msg);
    }

    public void reset() {
    	if (project.isOpen()) {
    		super.deleteMarkers(project, IResource.DEPTH_INFINITE);
    	}
        super.reset();
    }
    

}
