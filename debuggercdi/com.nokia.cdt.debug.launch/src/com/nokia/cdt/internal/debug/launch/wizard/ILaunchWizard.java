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

import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

public interface ILaunchWizard extends ISummaryTextItemContainer {
	ILaunchConfigurationWorkingCopy createLaunchConfiguration(IPath mmpPath, IPath exePath, IPath processToLaunchTargetPath);

	String getDescription();

	boolean shouldOpenLaunchConfigurationDialog();
}
