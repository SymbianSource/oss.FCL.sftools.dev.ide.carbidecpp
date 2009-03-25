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
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

/**
 * Process used in templates to create folders in a project.<p>
 * See the documentation for Creating Wizard Templates.
 */
public class CreateFolders extends AbstractProjectProcess {
	
	protected static final String FOLDER_PARAMETER = "folder"; //$NON-NLS-1$
	protected static final String PATH_ATTRIBUTE = "path"; //$NON-NLS-1$
	protected IProject project;
	private static final String NO_PROJECT_ERROR = Messages.getString("CreateFolders.NoProjectError"); //$NON-NLS-1$

	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		super.process(template, parameters, monitor);
		
        for (IParameter parameter : parameters) {
			if (parameter.getName().equals(FOLDER_PARAMETER)) {
				createFolder(parameter, monitor);
			}
		}
	}

	@Override
	protected void init(ITemplate template, List<IParameter> parameters) throws CoreException {
		super.init(template, parameters);
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        project = root.getProject(getProjectName());
		String error = MessageFormat.format(NO_PROJECT_ERROR, new Object[] { getProjectName() });
        failIfNull(project, error);
	}

	private void createFolder(IParameter parameter, IProgressMonitor monitor) throws CoreException {
		String pathString = getRequiredAttributeFromParameter(parameter, PATH_ATTRIBUTE);
		// make path canonical
		IPath projectPath = project.getLocation();
		try {
			pathString = projectPath.append(pathString).toFile().getCanonicalPath();
		} catch (IOException e) {
			throw new CoreException(Logging.newSimpleStatus(1, e));
		}
		IPath path = new Path(pathString);
		path = path.removeFirstSegments(projectPath.segmentCount());
		IContainer container = project;
		for (String segment : path.segments()) {
			IPath pathSegment = new Path(segment);
			IFolder folder = container.getFolder(pathSegment);
			if (!FileUtils.exists(folder)) {
				folder.create(true, true, monitor);
			}
			container = folder;
		}
	}

	protected Plugin getPlugin() {
		return TemplateWizardPlugin.getDefault();
	}
}
