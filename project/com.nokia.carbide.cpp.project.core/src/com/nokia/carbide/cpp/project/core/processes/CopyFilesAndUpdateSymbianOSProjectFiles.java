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
package com.nokia.carbide.cpp.project.core.processes;

import com.nokia.carbide.cpp.internal.api.project.core.ResourceChangeListener;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.process.IParameter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Copy files and have any new mmp/mk or source files added to the bld.inf or mmp file
 *
 */
public class CopyFilesAndUpdateSymbianOSProjectFiles extends CopyFilesAndFormatAsCpp {

	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		super.process(template, parameters, monitor);
		
		// now update the mmp file
		List<IFile> newFiles = new ArrayList<IFile>();
        for (IParameter parameter : parameters) {
			if (parameter.getName().equals(FILE_PARAMETER)) {
				String targetPathStr = getRequiredAttributeFromParameter(parameter, TARGET_PATH_ATTRIBUTE);
				IFile targetFile = project.getFile(targetPathStr);
				if (targetFile.exists()) {
					newFiles.add(targetFile);
				}
			}
		}
        
        if (newFiles.size() > 0) {
        	ResourceChangeListener.workspacefilesCreated(project, newFiles, true);
        }
	}

	@Override
	protected void init(ITemplate template, List<IParameter> parameters) throws CoreException {
		super.init(template, parameters);
	}

	protected Plugin getPlugin() {
		return ProjectCorePlugin.getDefault();
	}
}
