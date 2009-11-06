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

public interface ILaunchCreationWizardFactory {
	public LaunchCreationWizard create(IProject project, String configurationName, 
			List<IPath> mmps, List<IPath> exes, IPath defaultExecutable,  
			boolean isEmulation, boolean emulatorOnly, String mode) throws Exception;
}
