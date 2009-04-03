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

package com.nokia.sdt.series60.processes;

import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.process.AbstractProjectProcess;
import com.nokia.carbide.templatewizard.process.IParameter;
import com.nokia.sdt.series60.component.Series60ComponentPlugin;

import org.eclipse.core.runtime.*;

import java.util.List;

/**
 * Add variables for variants of the "projectName" variable needed in
 * generated source
 * 
 *
 */
public class AddProjectNameVariantsProcessRunner extends AbstractProjectProcess {

	/* (non-Javadoc)
	 * @see com.nokia.carbide.templatewizard.processes.AbstractProjectProcess#process(com.nokia.carbide.template.engine.ITemplate, java.util.List, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		super.process(template, parameters, monitor);
		String projectName = getProjectName();
		
		if (projectName.length() > 0)
			projectName = Character.toUpperCase(projectName.charAt(0)) + projectName.substring(1);
		
		// the naming "Titlelower" is intended to reflect the way a camelcased name would be mangled
		template.getTemplateValues().put("projectName$Titlelower", projectName);
	}

	@Override
	protected Plugin getPlugin() {
		return Series60ComponentPlugin.getDefault();
	}
}
