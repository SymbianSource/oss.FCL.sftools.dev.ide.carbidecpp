/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.symbian.updater;

import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.process.AbstractProjectProcess;
import com.nokia.carbide.templatewizard.process.IParameter;
import com.nokia.sdt.symbian.SymbianPlugin;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;

import java.util.List;

/**
 * Sets a session property on the project to tell the ProjectRefactoringProcessor to ignore this project.
 */
public class SetIgnoreAutoUpgrade extends AbstractProjectProcess {

	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		super.process(template, parameters, monitor);
        final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(getProjectName());
		if (project != null) {
			project.setSessionProperty(ProjectRefactoringProcessor.IGNORE_PROJECT, Boolean.TRUE);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.templatewizard.process.AbstractProcess#getPlugin()
	 */
	@Override
	protected Plugin getPlugin() {
		return SymbianPlugin.getDefault();
	}

}
