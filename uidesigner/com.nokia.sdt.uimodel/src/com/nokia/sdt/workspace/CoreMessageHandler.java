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

import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.io.File;
import java.util.*;

/**
 * Handler for messages generated during sourcegen.
 * Such errors usually derive from component files,
 * templates, or problems finding headers and files.
 * 
 * 
 *
 */
public class CoreMessageHandler {

    public static final String messageMarkerType = UIModelPlugin.PLUGIN_ID + ".messageMarker";

    protected IWorkspaceRoot root;

    private Set messages;
    private List markers;
    
    /**
     * 
     */
    public CoreMessageHandler() {
        this.root = Platform.isRunning() ?
                ResourcesPlugin.getWorkspace().getRoot() :
                    null;
        this.messages = new HashSet();
        this.markers = new ArrayList();
    }

    /** 
     * Delete markers associated with the given resource 
     * @param rsrc
     * @see IResource#DEPTH_ZERO
     * @see IResource#DEPTH_ONE
     * @see IResource#DEPTH_INFINITE
     */
    public static void deleteMarkers(IResource rsrc, int depth) {
        try {
            // DEBUG CODE when things get out of hand -- no other way to clear these!
            //ResourcesPlugin.getWorkspace().getRoot().deleteMarkers(null, true, IResource.DEPTH_INFINITE);
            rsrc.deleteMarkers(messageMarkerType, false, depth);
        } catch (CoreException e) {
            Logging.log(UIModelPlugin.getDefault(),
                    Logging.newStatus(UIModelPlugin.getDefault(), e));
        }
    }

    /**
     * Reset the markers based off the given resource
     */
    public synchronized void reset() {
        messages.clear();
        for (Iterator iter = markers.iterator(); iter.hasNext();) {
            IMarker marker = (IMarker) iter.next();
            try {
                marker.delete();
            } catch (CoreException e) {
                Logging.log(UIModelPlugin.getDefault(),
                        Logging.newStatus(UIModelPlugin.getDefault(), e));
            }
        }
        markers.clear();
    }

    /**
     * Map the given file to a path in the current workspace.
     * @param file
     * @return the IFile for the file
     */
    protected IFile fileToWorkspace(IProject project, File file) {
        IPath path = new Path(file.getAbsolutePath());
        IPath rootPath = project.getLocation();
        int match = path.matchingFirstSegments(rootPath);
        path = path.removeFirstSegments(match);
        IFile wsFile = project.getFile(path);
        return wsFile;
    }

    public synchronized IMarker createMarker(IResource rsrc, IMessage msg) {
        // ignore repeated messages
        if (messages.contains(msg)) {
            return null;
        }
        messages.add(msg);
        
        IMarker marker = null;
        marker = msg.createMarker(rsrc, messageMarkerType);
        if (marker != null)
        	markers.add(marker);
        
        return marker;
    }

}
