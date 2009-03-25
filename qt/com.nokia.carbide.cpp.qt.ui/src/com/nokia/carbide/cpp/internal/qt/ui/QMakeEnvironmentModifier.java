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
* Called by Qt project plugin before qmake is invoked to allow us to alter
* the environment.  We need to set EPOCROOT.
*
*/
package com.nokia.carbide.cpp.internal.qt.ui;

import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.trolltech.qtcppproject.qmake.IQMakeEnvironmentModifier;

public class QMakeEnvironmentModifier implements IQMakeEnvironmentModifier {

	public Properties getModifiedEnvironment(IProject project, Properties environment) {
		
		// make sure it's a Carbide project
		if (CarbideBuilderPlugin.getBuildManager().isCarbideProject(project)) {
			ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (cpi != null) {
				ICarbideBuildConfiguration config = cpi.getDefaultConfiguration();
				if (config != null) {
					ISymbianSDK sdk = config.getSDK();
					if (sdk != null) {
						// set EPOCROOT for the SDK of the current build configuration
						IPath epocRoot = new Path(sdk.getEPOCROOT());
						environment.put("EPOCROOT", epocRoot.setDevice(null).toOSString()); //$NON-NLS-1$
					}
				}
			}
		}

		return environment;
	}

}
