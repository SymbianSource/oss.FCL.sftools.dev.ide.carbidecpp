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
package com.nokia.carbide.cpp.internal.project.ui.images.providers;

import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import java.util.*;

/**
 * The content of the thumbnail for view project images.  This is fed
 * a base directory (e.g. a project's location).  
 * Any image fles in the project (optionally excluding those
 * already referenced by the editor) and not illegal for bmconv/mifconv are shown.
 *
 */
public class ProjectImageContentProvider extends BaseImageContentProvider {

	private boolean excludeReferenced;

	public ProjectImageContentProvider(IMultiImageSource multiImageSource, boolean excludeReferenced) {
		super(multiImageSource);
		this.excludeReferenced = excludeReferenced;
	}

	public ProjectImageContentProvider(IMMPAIFInfo aifInfo, boolean excludeReferenced) {
		super(aifInfo);
		this.excludeReferenced = excludeReferenced;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		List<IPath> files = Collections.EMPTY_LIST;
		if (inputElement instanceof IProject) {
			// scan image files
			files = new ArrayList<IPath>();
			scanProjectImageFiles((IProject) inputElement, files);
		} else if (inputElement instanceof IPath[]) {
			IPath[] paths = (IPath[]) inputElement;
			files = new ArrayList<IPath>();
			for (IPath path : paths)
				files.add(path);
		}
		if (files.size() > 0) {
			Collections.sort(files, new Comparator<IPath>() {

				public int compare(IPath o1, IPath o2) {
					return o1.toOSString().compareToIgnoreCase(o2.toOSString());
				}
				
			});

		}
		filterImageFiles(files, true);
		return (IPath[]) files.toArray(new IPath[files.size()]);
	}
	
	/**
	 * Find apparent image files in the project.
	 * @param project
	 * @param files
	 */
	private void scanProjectImageFiles(IProject project, final List<IPath> files) {
		IResourceProxyVisitor visitor = new IResourceProxyVisitor() {

			public boolean visit(IResourceProxy proxy) throws CoreException {
				String lowerName = proxy.getName().toLowerCase(); 
				if (proxy.getType() == IResource.FILE) {
					IPath filePath = null;
					if (allowBMP && lowerName.endsWith(".bmp")) { //$NON-NLS-1$
						filePath = proxy.requestFullPath().removeFirstSegments(1).makeRelative();
					} else if (allowSVG && lowerName.endsWith(".svg")) { //$NON-NLS-1$
						filePath = proxy.requestFullPath().removeFirstSegments(1).makeRelative();
					}
					if (filePath != null) {
						if (multiImageSource == null || !excludeReferenced || 
								multiImageSource.findMatchingSource(filePath) == null) {
							files.add(filePath);
						}
					}
				}
				return true;
			}
			
		};
		try {
			project.accept(visitor, 0);
		} catch (CoreException e) {
			ProjectUIPlugin.log(e);
		}
	}

}
