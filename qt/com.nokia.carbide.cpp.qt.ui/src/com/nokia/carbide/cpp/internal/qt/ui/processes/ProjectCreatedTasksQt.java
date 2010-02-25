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
package com.nokia.carbide.cpp.internal.qt.ui.processes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cpp.internal.qt.core.QtCorePlugin;
import com.nokia.carbide.cpp.internal.qt.core.QtSDKUtils;
import com.nokia.carbide.cpp.internal.qt.ui.QtUIPlugin;
import com.nokia.carbide.cpp.project.ui.processes.ProjectCreatedTasks;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.process.IParameter;
import com.trolltech.qtcppproject.QtProject;
import com.trolltech.qtcppproject.qmake.QMakeRunner;

public class ProjectCreatedTasksQt extends ProjectCreatedTasks {

	public ProjectCreatedTasksQt() {
		super();
	}

	@SuppressWarnings("restriction")
	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {

		init(template, parameters);

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        final IProject project = root.getProject(getProjectName());
		if (project != null) {
			// add qt nature here
			QtCorePlugin.addQtNature(project, monitor);
			
			// enable the pro file listener by default
			new QtProject(project).setRunQMakeWhenProFileChanges(true);

			// set EPOCROOT to the default build config's SDK before calling qmake
			List listOfBuildConfigs = (List) template.getTemplateValues().get(SELECTED_BUILD_CONFIGS_VALUE_KEY);

			// set the default Qt SDK
			ISymbianSDK sdk = ((ISymbianBuildContext)listOfBuildConfigs.get(0)).getSDK();
			String qtSDKName = QtSDKUtils.getQtSDKNameForSymbianSDK(sdk);
			if (qtSDKName == null){
				QtSDKUtils.addQtSDKForSymbianSDK(sdk, false);
				qtSDKName = QtSDKUtils.getQtSDKNameForSymbianSDK(sdk);
			}
			
			if (qtSDKName != null){
				QtSDKUtils.setDefaultQtSDKForProject(project, qtSDKName);
			}
			
			IPath epocroot = new Path(((ISymbianBuildContext)listOfBuildConfigs.get(0)).getSDK().getEPOCROOT());
			Map<String, String> envMods = new HashMap<String, String>();
			envMods.put("EPOCROOT", epocroot.setDevice(null).toOSString());
			
			// Make sure we set the session property so when qmake is called we know the proper -spec to invoke
			project.setSessionProperty(CarbideBuilderPlugin.SBSV2_PROJECT, Boolean.valueOf(useSBSv2Builder));
			
			// run qmake to ensure bld.inf file exists
			String errMsg = QMakeRunner.runQMake(project, envMods, monitor);
			if (errMsg != null) {
				throw new CoreException(new Status(IStatus.ERROR, QtUIPlugin.PLUGIN_ID, errMsg));
			}
			
			super.process(template, parameters, monitor);
			
			// switch to the Qt perspective
			QtUIPlugin.switchToQtPerspective();

			// set the qmake generated pkg files to be built
			QtUIPlugin.setupSISBuilderSettings(project);
			
		}
	}
}
