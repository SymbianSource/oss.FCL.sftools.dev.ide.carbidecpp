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
package com.nokia.carbide.cdt.internal.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;

public class CarbideSBSv2BuilderNature extends CarbideCPPBuilderNature {
	
	public static void addNature(IProject project, IProgressMonitor mon) throws CoreException {
		addNature(project, CarbideBuilderPlugin.CARBIDE_PROJECT_NATURE_ID, mon);
		addNature(project, CarbideBuilderPlugin.CARBIDE_SBSV2_PROJECT_NATURE_ID, mon);
	}

	public static void removeNature(IProject project, IProgressMonitor mon) throws CoreException {
		removeNature(project, CarbideBuilderPlugin.CARBIDE_PROJECT_NATURE_ID, mon);
		removeNature(project, CarbideBuilderPlugin.CARBIDE_SBSV2_PROJECT_NATURE_ID, mon);
	}
}
