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
package com.nokia.cdt.internal.debug.launch;

import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.IBinary;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.debug.core.executables.Executable;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.sdk.core.ISBSv1BuildContext;

/**
 * A property tester that determines if a file is an executable.
 */
public class SymbianProjectPropertyTester extends PropertyTester {
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if ("isExecutable".equals(property)) { //$NON-NLS-1$
			return isExecutable(receiver);
		}
		if ("isCarbideProject".equals(property)) { //$NON-NLS-1$
			return isCarbideProject(receiver);
		}
		if ("isEmulator".equals(property)) { //$NON-NLS-1$
			return isEmulator(receiver);
		}
		if ("isNotEmulator".equals(property)) { //$NON-NLS-1$
			return !isEmulator(receiver);
		}
		return true;
	}

	private boolean isCarbideProject(Object receiver) {

		if (receiver instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) receiver).getAdapter(IResource.class);
			if (res != null) {
				IProject project = res.getProject();
				if (project != null) {
					if (CarbideBuilderPlugin.getBuildManager().getProjectInfo(project) != null)
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Look for executable.
	 * 
	 * @return true if the target resource has a <code>main</code> method,
	 *         <code>false</code> otherwise.
	 */
	private boolean isExecutable(Object receiver) {

		if (receiver instanceof Executable)
			return true;
		
		ICElement celement = null;
		if (receiver instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable)receiver).getAdapter(IResource.class);
			if (res != null) {
				celement = CoreModel.getDefault().create(res);
			}
		}
		return (celement != null && celement instanceof IBinary);
	}
	
	private boolean isEmulator(Object receiver) {
		if (receiver instanceof Executable) {
			return isEmulatorBinaryPath(((Executable) receiver).getPath());
		}
		if (receiver instanceof IBinary) {
			return isEmulatorBinaryPath(((IBinary) receiver).getPath());
		}
		if (receiver instanceof IAdaptable) {
			IResource res = (IResource) ((IAdaptable) receiver).getAdapter(IResource.class);
			if (res != null) {
				IProject project = res.getProject();
				if (project != null) {
					ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
					if (cpi != null) {
						ICarbideBuildConfiguration buildConfig = cpi.getDefaultConfiguration();
						// just check the platform for the default config
						if (buildConfig.getPlatformString().equals(ISBSv1BuildContext.EMULATOR_PLATFORM)) {
							return true;
						}
					}
					else {
						return getIsEmulatorFromExecutablesProject(project);
					}
				}
			}
		}
		return false;
	}

	private boolean getIsEmulatorFromExecutablesProject(IProject project) {
        ICProject cProject = CoreModel.getDefault().create(project);
        if (cProject != null) {
			try {
    			for (IBinary bin : cProject.getBinaryContainer().getBinaries()) {
    				if (bin.isExecutable()) {
    					IPath path = bin.getResource().getLocation();
    					if (isEmulatorBinaryPath(path)) {
							return true;
						}
    				}
    			}
			} catch (CModelException e) {
			}
        }
        return false;
	}

	private boolean isEmulatorBinaryPath(IPath binaryPath) {
		if (binaryPath != null) {
			for (String segment : binaryPath.segments()) {
				if (segment.equalsIgnoreCase("winscw")) //$NON-NLS-1$
					return true;
			}
		}
		
		return false;
	}
	
	
}
