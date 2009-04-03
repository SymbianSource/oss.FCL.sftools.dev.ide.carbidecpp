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
package com.nokia.sdt.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * This class is used to walk the project tree retrieve a list of
 * files by file extension. Case is ignored.
 */
public class ProjectFileResourceProxyVisitor implements IResourceProxyVisitor {
	
	private List<IPath> requestedFiles = new ArrayList<IPath>(); //$NON-NLS-1$
	private String fileExtension = ""; //$NON-NLS-1$
	boolean continueRecursion;
	boolean ignoreDerived;
	
	public ProjectFileResourceProxyVisitor(String ext, boolean isRecursive) {
		this(ext, isRecursive, false);
	}
	
	public ProjectFileResourceProxyVisitor(String ext, boolean isRecursive, boolean ignoreDerived) {
		fileExtension = ext;
		continueRecursion = isRecursive;
		this.ignoreDerived = ignoreDerived;
	}

	public boolean visit(IResourceProxy resourceProxy) throws CoreException {
		
		// Is this a resource we should even consider?
		if (resourceProxy.getType() == IResource.FILE) {
			IResource resource = resourceProxy.requestResource();
			
			String ext = resource.getFileExtension();
			if (ext != null){
				if (ext.equalsIgnoreCase(fileExtension)) {
					// ignore derived resources if requested
					if (!ignoreDerived || !resource.isDerived()) {
						requestedFiles.add(resource.getProjectRelativePath());
					}
				}
			}
		}

		// Recurse into subdirectories
		return continueRecursion;
	}
	
	public List<IPath> getRequestedFiles(){
		return requestedFiles;
	}
	
}