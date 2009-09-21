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
package com.nokia.carbide.cpp.internal.builder.utils.handlers;

import java.util.List;

import org.eclipse.cdt.ui.newui.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.osgi.framework.Version;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cpp.internal.sdk.core.model.SDKManager;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;

public class BuilderUtilsPropertyTester extends PropertyTester {
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if ("canCompile".equals(property)) { //$NON-NLS-1$
			return canCompile(receiver);
		} else if ("canPreprocess".equals(property)) { //$NON-NLS-1$
			return canPreprocess(receiver);
		} else if ("isComponent".equals(property)) { //$NON-NLS-1$
			return isComponent(receiver);
		} else if ("isProject".equals(property)) { //$NON-NLS-1$
			return isProject(receiver);
		} else if ("isPKGFile".equals(property)) { //$NON-NLS-1$
			return isPKGFile(receiver);
		} else if ("canABLD".equals(property)) { //$NON-NLS-1$
			return canABLD(receiver);
		}
		
		return true;
	}

	private boolean canCompile(Object receiver) {

		if (receiver instanceof ISelection) {
			// compile should be enabled if all selected files are source files which belong
			// to Carbide projects
			List<IFile> selectedFiles = CompileHandler.getFilesToCompile((ISelection)receiver);
			if (selectedFiles.size() > 0) {
				for (IFile file : selectedFiles) {
					if (!CarbideBuilderPlugin.getBuildManager().isCarbideProject(file.getProject())) {
						return false;
					}
					
					if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(file.getProject())) {
						Version sbsVers = SDKCorePlugin.getSDKManager().getSBSv2Version(false);
						if (sbsVers.compareTo(SDKCorePlugin.getSDKManager().getMinimumSupportedSBSv2Version()) >= 0)
							return true;
						else
							return false;
					}
				}
				return true;
			}
		}

		return false;
	}

	private boolean canPreprocess(Object receiver) {

		if (receiver instanceof ISelection) {
			IFile selectedFile = PreprocessHandler.getFileToPreprocess((ISelection)receiver);
			if (selectedFile != null) {
				if (CarbideBuilderPlugin.getBuildManager().isCarbideProject(selectedFile.getProject())) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean isComponent(Object receiver) {

		if (receiver instanceof ISelection) {
			return ComponentCommandHandler.enabledForSelection((ISelection)receiver);
		}

		return false;
	}

	private boolean isProject(Object receiver) {

		if (receiver instanceof ISelection) {
			List<IProject> selectedProjects = ProjectCommandHandler.getProjects((ISelection)receiver);
			if (selectedProjects.size() > 0) {
				// already know there are Carbide projects
				return true;
			}
		}

		return false;
	}

	private boolean isPKGFile(Object receiver) {

		if (receiver instanceof ISelection) {
			List<IFile> selectedFiles = BuildPKGCommandHandler.getPKGFiles((ISelection)receiver);
			if (selectedFiles.size() > 0) {
				for (IFile file : selectedFiles) {
					if (!CarbideBuilderPlugin.getBuildManager().isCarbideProject(file.getProject())) {
						return false;
					}
				}
				return true;
			}
		}

		return false;
	}

	private boolean canABLD(Object receiver) {

		if (receiver instanceof ISelection) {
			List<IProject> selectedProjects = ABLDCommandHandler.getProjects((ISelection)receiver);
			if (selectedProjects.size() > 0) {
				// already know there are Carbide projects
				return true;
			}
		}

		return false;
	}
}
