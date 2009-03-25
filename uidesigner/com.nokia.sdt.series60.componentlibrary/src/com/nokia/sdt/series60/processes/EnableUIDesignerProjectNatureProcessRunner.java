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
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;

import java.util.List;

/**
 * Enable the UI Designer Project Nature on generated projects 
 * 
 *
 */
public class EnableUIDesignerProjectNatureProcessRunner extends AbstractProjectProcess {

	/* (non-Javadoc)
	 * @see com.nokia.carbide.templatewizard.processes.AbstractProjectProcess#process(com.nokia.carbide.template.engine.ITemplate, java.util.List, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		super.process(template, parameters, monitor);

		String projectName = getProjectName();
		IProject project = (IProject) ResourcesPlugin.getWorkspace().getRoot().findMember(projectName);
		Check.checkState(project != null);
//		SymbianProjectUtils.addUIDesignerProjectNatureToProject(project);
	}

	@Override
	protected Plugin getPlugin() {
		return Series60ComponentPlugin.getDefault();
	}
}
