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
package com.nokia.carbide.cpp.project.ui.processes;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.process.AbstractProjectProcess;
import com.nokia.carbide.templatewizard.process.IParameter;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.util.*;

public class ProjectCreatedTasks extends AbstractProjectProcess {

	protected static final String SELECTED_BUILD_CONFIGS_VALUE_KEY = "selectedBuildConfigs"; //$NON-NLS-1$

	private static final String BLD_INF_PATH_ATTRIBUTE = "bldInfPath"; //$NON-NLS-1$
	private static final String TARGET_MMP_FILENAME_ATTRIBUTE = "targetMMPFileName"; //$NON-NLS-1$
	private static final String PKG_FILE_PATH_ATTRIBUTE = "pkgFilePath"; //$NON-NLS-1$
	private static final String SBSV2_BUILDER_ATTRIBUTE = "useSBSv2Builder"; //$NON-NLS-1$
	private static final String EMULATOR_PLATFORM = "WINSCW"; //$NON-NLS-1$
	
	protected String bldInfPath;
	protected String targetMMPFileName;
	protected String pkgFilePath;
	protected boolean useSBSv2Builder;
	
	public ProjectCreatedTasks() {
		super();
		setRunInUIThread(true);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.templatewizard.process.AbstractProcess#process(com.nokia.carbide.template.engine.ITemplate, java.util.List, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		super.process(template, parameters, monitor);
		
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        final IProject project = root.getProject(getProjectName());
		if (project != null) {
			ProjectUIPlugin.projectCreated(project);

			// get the build configurations list
			List<ISymbianBuildContext> buildConfigs = new ArrayList<ISymbianBuildContext>();
			Object object = template.getTemplateValues().get(SELECTED_BUILD_CONFIGS_VALUE_KEY);
			if (object instanceof List) {
				List listOfBuildConfigs = (List) object;
				for (Object obj : listOfBuildConfigs) {
					Check.checkContract(obj instanceof ISymbianBuildContext);
					ISymbianBuildContext symbianBuildContext = (ISymbianBuildContext)obj;
					buildConfigs.add(symbianBuildContext);
				}
			}

			Map<ISymbianBuildContext, String> pkgMappings = null;
			if (pkgFilePath != null) {
				pkgMappings = new HashMap<ISymbianBuildContext, String>();
				for (ISymbianBuildContext config : buildConfigs) {
					if (platformRequiresPKGSpec(config))
						pkgMappings.put(config, pkgFilePath);
				}
			}
			
			project.setSessionProperty(CarbideBuilderPlugin.SBSV2_PROJECT, Boolean.valueOf(useSBSv2Builder));
		
			ProjectCorePlugin.postProjectCreatedActions(project, bldInfPath, buildConfigs, new ArrayList<String>(), targetMMPFileName, pkgMappings, monitor);
		}
	}
	
	private static boolean platformRequiresPKGSpec(ISymbianBuildContext context) {
		return !EMULATOR_PLATFORM.equalsIgnoreCase(context.getPlatformString());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.templatewizard.process.AbstractProcess#getPlugin()
	 */
	@Override
	protected Plugin getPlugin() {
		return ProjectCorePlugin.getDefault();
	}

	@Override
	protected void init(ITemplate template, List<IParameter> parameters) throws CoreException {
		super.init(template, parameters);

		bldInfPath = 
			getRequiredAttributeFromParameter(projectParameter, BLD_INF_PATH_ATTRIBUTE);
		targetMMPFileName = 
			getRequiredAttributeFromParameter(projectParameter, TARGET_MMP_FILENAME_ATTRIBUTE);
		pkgFilePath = 
			projectParameter.getAttributeValue(PKG_FILE_PATH_ATTRIBUTE); // not required
		
		Object useSBSv2BuilderValue = template.getTemplateValues().get(SBSV2_BUILDER_ATTRIBUTE);
		if (useSBSv2BuilderValue != null && useSBSv2BuilderValue instanceof Boolean) {
			useSBSv2Builder = ((Boolean)useSBSv2BuilderValue).booleanValue();
		}
	}

}
