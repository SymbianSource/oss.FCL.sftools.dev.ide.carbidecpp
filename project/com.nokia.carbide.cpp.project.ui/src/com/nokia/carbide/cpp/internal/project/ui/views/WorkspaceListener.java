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

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IViewSite;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;

/**
 * Resource listener helper for the SymbianProjectNavigatorView.  Keeps the view in sync with
 * changes such as projects being opened/closed/renamed/created/deleted, as well as files being
 * changed or removed.
 */

public class WorkspaceListener implements IResourceChangeListener {
	
	private TreeViewer viewer;
	private IViewSite viewSite;
	private SPNViewContentProvider contentProvider;
	
	
	WorkspaceListener(TreeViewer theViewer, IViewSite theViewSite, SPNViewContentProvider contentProvider) {
		viewer = theViewer;
		viewSite = theViewSite;
		this.contentProvider = contentProvider;
	}

	public void resourceChanged(IResourceChangeEvent event) {
		// note that we're ignoring PRE_CLOSE and PRE_DELETE as we're handling those cases
		// anway in POST_CHANGE
		if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
			// resource changed events always start at the workspace root, so projects
			// are the next level down
			final IResourceDelta[] projects = event.getDelta().getAffectedChildren();
			for (final IResourceDelta projectDelta : projects) {
				if ((projectDelta.getFlags() & IResourceDelta.OPEN) != 0) {
					if (projectDelta.getKind() == IResourceDelta.ADDED) {
						// project was created.  refresh the root.
						viewer.getControl().getDisplay().asyncExec(new Runnable() {
							public void run() {
								viewer.refresh(viewSite, false);
								
								// select the new project in the SPN view
								viewer.setSelection(new StructuredSelection(CoreModel.getDefault().create((IProject)projectDelta.getResource())));
							}
						});
					} else {
						// it was opened or closed.  just refresh it in the viewer.
						viewer.getControl().getDisplay().asyncExec(new Runnable() {
							public void run() {
								viewer.refresh(CoreModel.getDefault().create((IProject)projectDelta.getResource()), true);
							}
						});
					}
					continue;
				} else if ((projectDelta.getKind() == IResourceDelta.REMOVED)) {
					// project was removed.  refresh the root.
					viewer.getControl().getDisplay().asyncExec(new Runnable() {
						public void run() {
							viewer.refresh(viewSite, false);
							contentProvider.projectDeleted((IProject)projectDelta.getResource());
						}
					});
					continue;
				} else if ((projectDelta.getFlags() & IResourceDelta.DESCRIPTION) != 0) {
					// the project description has changed.  this happens when natures are added
					// or removed from a project, or when project settings change.  this will pick
					// up when a Carbide nature has been added or removed from a project, among
					// other things.  but we need refresh the view to add/remove projects when
					// their nature changes, so hopefully this will not bee too inefficient.
					viewer.getControl().getDisplay().asyncExec(new Runnable() {
						public void run() {
							viewer.refresh(viewSite, false);
						}
					});
				}

				// if we made it here then some child resource(s) of a project have changed.  we're now only
				// interested in modified or removed files
				IResource projectResource = projectDelta.getResource();
				if (projectResource != null && projectResource instanceof IProject) {
					// if the project is not a Carbide.c++ project then ignore the change
					IProject changedProject = (IProject)projectResource;
					try {
						if (changedProject.isAccessible() && changedProject.hasNature(CarbideBuilderPlugin.CARBIDE_PROJECT_NATURE_ID)) {
							// visit the children
							visitChildren(projectDelta);
						}
					} catch (CoreException e) {
						ProjectCorePlugin.log(e.getStatus());
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private void visitChildren(IResourceDelta delta) {
		IResourceDelta[] removedChildren = delta.getAffectedChildren(IResourceDelta.REMOVED);
		if (removedChildren.length > 0) {
			// notify the content provider of any removed files
			for (IResourceDelta child : removedChildren) {
				final IResource resource = child.getResource();
				if (resource != null) {
					if (resource instanceof IFile) {
						viewer.getControl().getDisplay().asyncExec(new Runnable() {
							public void run() {
								viewer.remove(resource);
							}
						});
					} else if (resource instanceof IFolder) {
						visitChildren(child);
					}
				}
			}
		}

		IResourceDelta[] changedChildren = delta.getAffectedChildren(IResourceDelta.CHANGED);
		if (changedChildren.length > 0) {
			// notify the content provider of any changed files
			for (IResourceDelta child : changedChildren) {
				// ignore marker deltas
				if ((child.getFlags() & IResourceDelta.MARKERS) != 0) {
					continue;
				}

				IResource resource = child.getResource();
				if (resource != null) {
					if (resource instanceof IFile) {
						contentProvider.fileChanged((IFile)resource);
					} else if (resource instanceof IFolder) {
						visitChildren(child);
					}
				}
			}
		}
	}
}
