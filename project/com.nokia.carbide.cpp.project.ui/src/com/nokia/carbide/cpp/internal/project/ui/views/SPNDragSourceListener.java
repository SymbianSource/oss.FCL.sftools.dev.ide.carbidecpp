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
package com.nokia.carbide.cpp.internal.project.ui.views;

import java.util.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.ui.part.ResourceTransfer;

public class SPNDragSourceListener implements DragSourceListener {

    private TreeViewer fTreeViewer;
    private ArrayList<IResource> fSelectedNodes = new ArrayList<IResource>();

    public SPNDragSourceListener(TreeViewer viewer) {
        fTreeViewer = viewer;
    }

    public void dragStart(DragSourceEvent event) {
        fSelectedNodes.clear();
        if (event.doit) {
            IStructuredSelection sel = (IStructuredSelection) fTreeViewer.getSelection();
            for (Iterator iter = sel.iterator(); iter.hasNext();) {
                Object element = iter.next();
                if (element instanceof IAdaptable) {
                	IResource resource = (IResource)((IAdaptable)element).getAdapter(IResource.class);
                	if (resource != null) {
                    	fSelectedNodes.add(resource);
                	}
                }
            }
            event.doit = !fSelectedNodes.isEmpty();
        }
    }

    public void dragSetData(DragSourceEvent event) {
    	if (ResourceTransfer.getInstance().isSupportedType(event.dataType)) {
            event.data = getResources();
        } else if (FileTransfer.getInstance().isSupportedType(event.dataType)) {
            event.data = getFiles();
        }
    }

    private String[] getFiles() {
        List<String> files = new ArrayList<String>(fSelectedNodes.size());
        for (IResource resource : fSelectedNodes) {
        	if (resource instanceof IFile) {
            	IPath path = resource.getLocation();
            	if (path != null) {
                	files.add(path.toOSString());
            	}
        	}
        }
        return (String[]) files.toArray(new String[files.size()]);
    }

    private IResource[] getResources() {
        return (IResource[]) fSelectedNodes.toArray(new IResource[fSelectedNodes.size()]);
    }

	public void dragFinished(DragSourceEvent event) {
        fSelectedNodes.clear();
    }
}
