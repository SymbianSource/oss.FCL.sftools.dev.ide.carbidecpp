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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IFilter;
import com.nokia.carbide.cdt.builder.EMMPPathContext;
import com.nokia.carbide.cdt.builder.InvalidDriveInMMPPathException;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.*;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.EMMPListSelector;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.ui.editor.IEditingContext;

public class SourcesTreeHandler extends ControlHandler {

	private MMPEditorContext editorContext;
	private IEditingContext editingContext;
	private SourceSelectionViewer sourceViewer;
	private final SourcesSectionPart section;

	public SourcesTreeHandler(MMPEditorContext editorContext, IEditingContext editingContext, 
			SourceSelectionViewer viewer, SourcesSectionPart section) {
		super(viewer, true);
		this.editorContext = editorContext;
		this.editingContext = editingContext;
		this.sourceViewer = viewer;
		this.section = section;
	}
	
	@Override
	protected void doRefresh() {
		section.refresh();
	}

	@Override
	protected void checkStateChanged(Object element, final boolean checked) {
		final List<IPath> modelSources = editorContext.mmpView.getSources();
		List<IPath> list = new ArrayList<IPath>();
		if (element instanceof IFile) {
			IFile file = (IFile) element;
			IPath filePath = file.getProjectRelativePath();
			try {
				filePath = editorContext.pathHelper.convertProjectOrFullPathToMMP(EMMPPathContext.SOURCE, filePath);
			} catch (InvalidDriveInMMPPathException e) {
				// shouldn't happen
				ProjectUIPlugin.log(e);
				filePath = e.getPathNoDevice();
			}
			// remove won't work if file system and mmp differ in case, so
			// ensure they match
			if (!checked) {
				filePath = findFilePathInModelSources(filePath, modelSources);
				Check.checkState(filePath != null);
			}
			list.add(filePath);
		} else if (element instanceof IContainer) {
			// Flatten hierarchy into list of files. For undo/redo we want
			// just the files that need changing, not all the contained files.
			flattenContainer((IContainer) element, list, new IFilter() {
				public boolean select(Object toTest) {
					IPath filePath;
					try {
						filePath = editorContext.pathHelper.convertProjectOrFullPathToMMP(EMMPPathContext.SOURCE, (IPath) toTest);
					} catch (InvalidDriveInMMPPathException e) {
						// shouldn't happen
						ProjectUIPlugin.log(e);
						filePath = e.getPathNoDevice();
					}
					boolean isInModel = findFilePathInModelSources(filePath, modelSources) != null;
					return checked != isInModel;
				}
			});
		}
		if (list.size() > 0) {
			execListCommand(checked, list, modelSources);
		}
	}
	
	/**
	 * Search the sources list for the given path, using case-insensitive comparison
	 * @return the path as its specified in the mmp, or null if not found
	 */
	IPath findFilePathInModelSources(IPath path, List<IPath> modelSources) {
		IPath result = null;
		String pathStr = path.toString();
		for (IPath modelPath : modelSources) {
			if (pathStr.equalsIgnoreCase(modelPath.toString())) {
				result = modelPath;
				break;
			}
		}
		// if not found by project relative path search by full file system path
		if (result == null) {
			pathStr = editorContext.project.getLocation().append(path).toString();
			for (IPath modelPath : modelSources) {
				if (pathStr.equalsIgnoreCase(modelPath.toString())) {
					result = modelPath;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * Flatten hierarchy under container into a list of files
	 */
	private void flattenContainer(IContainer container, List<IPath> list, IFilter filter) {
		Object[] children = sourceViewer.getFilteredChildren(container);
		for (Object obj : children) {
			if (obj instanceof IFile) {
				IFile file = (IFile) obj;
				IPath path = file.getProjectRelativePath();
				if (filter.select(path)) {
					list.add(file.getProjectRelativePath());
				}
			} else if (obj instanceof IContainer) {
				flattenContainer((IContainer) obj, list, filter);
			}
		}		
	}
	
	private void execListCommand(boolean addingFiles, List<IPath> items, 
			List<IPath> modelSources) {
		if (addingFiles) {
			AddListValueOperation op = new AddListValueOperation(
					editorContext.mmpView, 
					editingContext,
					this,
					EMMPListSelector.SOURCES,
					items);
			editorContext.executeOperation(op);
			
		} else {
			List<Integer> indices = new ArrayList<Integer>();
			for(IPath path : items) {
				int index = modelSources.indexOf(path);
				if (index >= 0) {
					indices.add(index);
				}
			}
			RemoveListValueOperation op = new RemoveListValueOperation(
					editorContext.mmpView, 
					editingContext,
					this,
					EMMPListSelector.SOURCES,
					indices);
			editorContext.executeOperation(op);
		}
	}
	
	Object resolveElement(Object element) {
		if (element instanceof IPath) {
			IProject project = (IProject) sourceViewer.getInput();
			IFile resource = resolveMMPSourceFile(project, (IPath)element);
			element = resource;
		}
		return element;
	}
	
	/**
	 * Items coming from model command undo/redo will be IPath and must be
	 * resolved pack to IFile
	 */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	List resolveList(List items) {
		List list = new ArrayList();
		for (Object obj : items) {
			list.add(resolveElement(obj));
		}
		return list;
	}
	
	public void addListItems(List items) {
		items = resolveList(items);
		for (Object obj : items) {
			sourceViewer.setChecked(obj, true);
		}
		sourceViewer.updateContainerCheckStates(items);
	}
	
	public void removeListItems(List items) {
		items = resolveList(items);
		for (Object obj : items) {
			sourceViewer.setChecked(obj, false);
		}
		sourceViewer.updateContainerCheckStates(items);
	}
	
	static IFile resolve(IContainer container, IPath path) throws CoreException {
		IFile result = null;
		if (path.segmentCount() > 0) {
			String seg0 = path.segment(0);
			IResource seg0Resource = container.findMember(seg0);
			if (seg0Resource == null) {
				IResource[] members = container.members();
				if (members != null) {
					for (IResource r : members) {
						if (r.getName().equalsIgnoreCase(seg0)) {
							seg0Resource = r;
							break;
						}
					}
				}
			}
			if (seg0Resource != null) {
				if (seg0Resource instanceof IFile) {
					if (path.segmentCount() == 1) {
						result = (IFile) seg0Resource;
					}
				} else if (seg0Resource instanceof IContainer && path.segmentCount() > 1){
					IPath remainder = path.removeFirstSegments(1);
					result = resolve((IContainer)seg0Resource, remainder);
				}
			}
		}
		return result;
	}
	
	static IFile resolveMMPSourceFile(IProject project, IPath path) {
		IResource r = project.findMember(path);
		IFile result = null;
		if (r instanceof IFile) {
			result = (IFile) r;
		} else {
			try {
				result = resolve(project, path);
			} catch (CoreException x) {
				ProjectUIPlugin.log(x);
			}
		}
		return result;
	}
}
