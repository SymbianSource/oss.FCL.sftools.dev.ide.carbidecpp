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
package com.nokia.cdt.internal.debug.launch.wizard;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

/**
 * Contributes additional wizards to the Carbide launch wizard.
 */
public interface ILaunchWizardContributor {

	/**
	 * Returns the launch wizard to contribute
	 * @param mmps the list of mmp paths
	 * @param exes the corresponding list of executable paths built by the mmps
	 * @param defaultExecutable the path to the default executable
	 * @param project the project
	 * @param configurationName the configuration name
	 * @return the contributed wizard
	 */
	AbstractLaunchWizard getWizard(List<IPath> mmps, List<IPath> exes, IPath defaultExecutable, IProject project, String configurationName);
}
