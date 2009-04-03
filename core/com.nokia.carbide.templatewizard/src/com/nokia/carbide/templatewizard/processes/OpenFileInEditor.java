/*
* Copyright (c) 2006, 2008 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.templatewizard.processes;

import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.Messages;
import com.nokia.carbide.templatewizard.TemplateWizardPlugin;
import com.nokia.carbide.templatewizard.process.AbstractProjectProcess;
import com.nokia.carbide.templatewizard.process.IParameter;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.ui.*;
import org.eclipse.ui.part.FileEditorInput;

import java.text.MessageFormat;
import java.util.List;

/**
 * Opens the specified file in an editor. The file name is specified in a required parameter attribute 'fileName'.
 * The optional attribute 'editorId' can be used to declare a specific editor to use. If the editor id is not specified
 * or if the editor is not found, then the default editor for the file is used to open it.
 * @see IContainer#findMember(String) for 'fileName' attribute semantics
 */
public class OpenFileInEditor extends AbstractProjectProcess {

	private static final String NO_EDITOR_ERROR = Messages.getString("OpenFileInEditor.NoSuchEditorId"); //$NON-NLS-1$
	private static final String NO_DEFAULT_EDITOR_ERROR = Messages.getString("OpenFileInEditor.NoDefaultEditorFor"); //$NON-NLS-1$
	private String fileName;
	private String editorId; 
	private static final String FILENAME = "fileName"; //$NON-NLS-1$
	private static final String NO_FILENAME_ATTR_ERROR = 
		Messages.getString("OpenFileInEditor.ProjectWithoutFilenameError"); //$NON-NLS-1$
	private static final String EDITOR_ID = "editorId"; //$NON-NLS-1$

	public OpenFileInEditor() {
		super();
		setRunInUIThread(true);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.templatewizard.processes.AbstractProjectProcess#init(com.nokia.carbide.template.engine.ITemplate, java.util.List)
	 */
	@Override
	protected void init(ITemplate template, List<IParameter> parameters) throws CoreException {
		super.init(template, parameters);

		String error = MessageFormat.format(NO_FILENAME_ATTR_ERROR, new Object[] { getProcessName() });
		fileName = projectParameter.getAttributeValue(FILENAME);
		failIfNull(fileName, error);
		editorId = projectParameter.getAttributeValue(EDITOR_ID);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.templatewizard.processes.AbstractProjectProcess#process(com.nokia.carbide.template.engine.ITemplate, java.util.List, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		super.process(template, parameters, monitor);


		String projectName = getProjectName();
		
		IProject project = (IProject) ResourcesPlugin.getWorkspace().getRoot().findMember(projectName);
		failIfNull(project, MessageFormat.format(
				Messages.getString("OpenFileInEditor.NoSuchProject"), projectName)); //$NON-NLS-1$
		
		IResource file = (IResource) project.findMember(fileName);
		failIfFalse(file instanceof IFile, MessageFormat.format(
				Messages.getString("OpenFileInEditor.NoSuchFile"), fileName)); //$NON-NLS-1$

		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchPage activePage = workbench.getActiveWorkbenchWindow().getActivePage();
		IEditorRegistry registry = workbench.getEditorRegistry();
		IEditorDescriptor ed = null;
		if (editorId != null) {
			ed = registry.findEditor(editorId);
		}
		if (ed == null) {
			ed = registry.getDefaultEditor(fileName);
			if (ed != null)
				editorId = ed.getId();
		}
		
		String error = editorId != null ?
			MessageFormat.format(NO_EDITOR_ERROR, new Object[] { editorId }) :
			MessageFormat.format(NO_DEFAULT_EDITOR_ERROR, new Object[] { fileName});
		failIfNull(ed, error);

		try {
			activePage.openEditor(new FileEditorInput((IFile) file), editorId);
		} catch (PartInitException e) {
			fail(e.getMessage(), e);
		}
	}

	@Override
	protected Plugin getPlugin() {
		return TemplateWizardPlugin.getDefault();
	}
}
