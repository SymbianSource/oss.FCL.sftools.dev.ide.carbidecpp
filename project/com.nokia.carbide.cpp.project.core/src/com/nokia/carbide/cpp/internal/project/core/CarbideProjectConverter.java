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
package com.nokia.carbide.cpp.internal.project.core;

import java.io.File;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.ICDescriptor;
import org.eclipse.cdt.core.ICExtensionReference;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICStorageElement;
import org.eclipse.cdt.core.settings.model.extension.ICProjectConverter;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.internal.api.builder.CarbideConfigurationDataProvider;

public class CarbideProjectConverter implements ICProjectConverter {

	private static final String ROM_BUILDER_PLUGIN_ID = "com.nokia.carbide.cdt.rombuilder"; //$NON-NLS-1$
	private static final String ROM_BUILD_COMMAND_LINE = "rombuildcommandline"; //$NON-NLS-1$
	private static final String ROM_BUILD_WORKING_DIR = "rombuildworkingdir"; //$NON-NLS-1$

	public boolean canConvertProject(IProject project, String oldOwnerId, ICProjectDescription des) {

		try {
			ICDescriptor cdesc = CCorePlugin.getDefault().getCProjectDescription(project, false);
			if (cdesc != null) {
				ICExtensionReference[] cext = cdesc.get(CarbideBuilderPlugin.getCarbideBuilderExtensionID());
				if (cext.length > 0) {
					return true;
				}
			}
		} catch (CoreException e) {
		}
		return false;
	}

	public ICProjectDescription convertProject(IProject project,
			IProjectDescription eclipseProjDes, String oldOwnerId,
			ICProjectDescription des) throws CoreException {

		// create the c project description
		ICProjectDescription projDes = CCorePlugin.getDefault().createProjectDescription(project, false, true);

		// get the old style descriptor and read the settings
		ICDescriptor cdesc = CCorePlugin.getDefault().getCProjectDescription(project, false);
		if (cdesc != null) {
			// get the old style project settings
			ICExtensionReference[] cext = cdesc.get(CarbideBuilderPlugin.getCarbideBuilderExtensionID());
			if (cext.length > 0) {
				
				// this does most of the work
				CarbideConfigurationDataProvider.convertProject(projDes, cext);
				
				// now convert the rom builder settings
				ICStorageElement storage = projDes.getStorage(ROM_BUILDER_PLUGIN_ID, true);
				if (storage != null) {
					for (int i = 0; i < cext.length; i++) {
						String orig = cext[i].getExtensionData(ROM_BUILD_COMMAND_LINE);
						if (orig != null) {
							storage.setAttribute(ROM_BUILD_COMMAND_LINE, orig);
						}

						orig = cext[i].getExtensionData(ROM_BUILD_WORKING_DIR);
						if (orig != null) {
							storage.setAttribute(ROM_BUILD_WORKING_DIR, orig);
						}
					}
				}
			}
		}

		// rename the .cdtproject file so the user knows it's no longer used
		File oldCdtProjectFile = project.getLocation().append(".cdtproject").toFile(); //$NON-NLS-1$
		if (oldCdtProjectFile.exists()) {
			oldCdtProjectFile.renameTo(project.getLocation().append(".cdtproject_obsolete").toFile()); //$NON-NLS-1$
			try {
				project.refreshLocal(IResource.DEPTH_ONE, null);
			} catch (CoreException e) {
				e.printStackTrace();
				// not a big deal - could happen if the workspace tree is locked for modifications or something.
			}
		}

		return projDes;
	}

}
