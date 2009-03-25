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

import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.IMessageListener;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;

/**
 * Handler for messages generated outside of any
 * specific build context.
 * 
 * 
 *
 */
public class WorkspaceMessageHandler extends CoreMessageHandler implements IMessageListener {

    private boolean firstUse;
    
    /**
     * 
     */
    public WorkspaceMessageHandler() {
        super();
        firstUse = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.cpp.internal.api.utils.core.IMessageListener#isHandlingMessage(com.nokia.cpp.internal.api.utils.core.IMessage)
     */
    public boolean isHandlingMessage(IMessage msg) {
    	return isWorkspaceMessage(root, msg);
    }
    
    public static boolean isWorkspaceMessage(IContainer root, IMessage msg) {
        // ignore messages for projects with contexts
        IPath msgPath = msg.getPath();
        if (msgPath != null
         && msgPath.segmentCount() > 0) {
        	IResource rsrc = root.findMember(msgPath.segment(0));
        	if (rsrc != null && rsrc.getType() == IResource.PROJECT) {
        		IProject project = (IProject) rsrc;
        		if (WorkspaceContext.getContext().getContextForProject(project) != null)
        			return false;
        	}
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.IMessageHandler#emitMessage(com.nokia.sdt.sourcegen.ISourceGenMessage)
     */
    public void emitMessage(IMessage msg) {
        IResource rsrc = null;
        IPath msgPath = msg.getPath();
        if (msgPath != null)
            rsrc = root.findMember(msgPath);
        if (rsrc == null)
            rsrc = root;
     	
        createMarker(rsrc, msg);
    }
    
    public void reset() {
        if (firstUse) {
            // remove any left over from last time 
            deleteMarkers(root, IResource.DEPTH_INFINITE);
            firstUse = false;
        } else {
	        // remove markers attached to root
	        deleteMarkers(root, IResource.DEPTH_ZERO);
	        
	        // remove markers attached to project w/o context
	        IProject[] projects = root.getProjects();
	        for (int i = 0; i < projects.length; i++) {
				if (WorkspaceContext.getContext().getContextForProject(projects[i]) == null)
					if (projects[i].isOpen())
						deleteMarkers(projects[i], IResource.DEPTH_INFINITE);
			}
        }
        
        super.reset();

        /*
        if (set != null) {
            // remove markers attached to components (and children/parallel files)
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            for (Iterator iter = set.iterator(); iter.hasNext();) {
                IComponent component = (IComponent) iter.next();
                MessageLocation loc = component.createMessageLocation();
                if (loc != null) {
                    IPath newPath = FileUtils.convertToWorkspacePath(loc.getLocation());
                    if (newPath != null) {
                        IResource rsrc = root.findMember(newPath);
                        if (rsrc != null) {
                            if (rsrc instanceof IFile)
                                super.deleteMarkers(((IFile)rsrc).getParent(), IResource.DEPTH_ONE);
                            else
                                super.deleteMarkers(((IFile)rsrc).getParent(), IResource.DEPTH_ONE);
                        }
                    }
                }
            }
        }*/
    }
}
