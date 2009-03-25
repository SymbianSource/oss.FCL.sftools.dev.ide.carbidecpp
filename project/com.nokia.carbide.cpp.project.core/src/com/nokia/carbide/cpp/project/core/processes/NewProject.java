/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.cpp.project.core.processes;

import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.process.AbstractProjectProcess;
import com.nokia.carbide.templatewizard.process.IParameter;

import org.eclipse.core.runtime.*;

import java.util.List;

/**
 * Process used in templates to create a new Carbide project.<p>
 * See the documentation for Creating Wizard Templates.
 */
public class NewProject extends AbstractProjectProcess {

	private static final String LOCATION = "location"; //$NON-NLS-1$
	private static final String USE_DEFAULT_LOCATION = "useDefaultLocation"; //$NON-NLS-1$
	private boolean useDefaultLocation;
	private String location;
	
	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		super.process(template, parameters, monitor);

		// create the project
		String loc = useDefaultLocation ? null : location;

		ProjectCorePlugin.createProject(getProjectName(), loc);
	}

	@Override
	protected void init(ITemplate template, List<IParameter> parameters) throws CoreException {
		super.init(template, parameters);
		
		location = (String) template.getTemplateValues().get(LOCATION);
		useDefaultLocation = 
			((Boolean) template.getTemplateValues().get(USE_DEFAULT_LOCATION)).booleanValue();
	}

	protected Plugin getPlugin() {
		return ProjectCorePlugin.getDefault();
	}
}
