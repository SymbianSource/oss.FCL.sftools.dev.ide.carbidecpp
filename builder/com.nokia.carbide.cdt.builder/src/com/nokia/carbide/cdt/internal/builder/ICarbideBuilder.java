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

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import com.nokia.carbide.cdt.builder.builder.CarbideCommandLauncher;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;

public interface ICarbideBuilder {

	public void preBuildStep(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher);
	
	public void preCleanStep(ICarbideBuildConfiguration buildConfig);
	
	public boolean buildComponent(ICarbideBuildConfiguration buildConfig, IPath componentPath, boolean isTest, CarbideCommandLauncher launcher, IProgressMonitor monitor);
	
	public boolean cleanComponent(ICarbideBuildConfiguration buildConfig, IPath componentPath, boolean isTest, CarbideCommandLauncher launcher, IProgressMonitor monitor);

	public boolean freezeComponent(ICarbideBuildConfiguration buildConfig, IPath componentPath, boolean isTest, CarbideCommandLauncher launcher, IProgressMonitor monitor);

	public void compileFile(IPath file, ICarbideBuildConfiguration buildConfig, IPath fullMMPPath, CarbideCommandLauncher launcher, IProgressMonitor monitor) throws CoreException;
	
	public boolean buildAllComponents(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor);
	
	public boolean buildComponentSubset(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor);

	public void cleanAllComponents(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor);
	
	public void cleanComponentSubset(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor);

	public void freezeAllComponents(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor);
	
	public void freezeComponentSubset(ICarbideBuildConfiguration buildConfig, List<IPath> normalMakMakePaths, List<IPath> testMakMakePaths, CarbideCommandLauncher launcher, IProgressMonitor monitor);
	
	public boolean invokeBldmakeCommand(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher, String[] bldmakeArgs);

	public boolean invokeAbldCommand(ICarbideBuildConfiguration buildConfig, CarbideCommandLauncher launcher, String[] abldArgs);
	
	public String [] getResolvedEnvVars(ICarbideBuildConfiguration config);

	public boolean needsBldmakeMakefileGeneration(ICarbideBuildConfiguration config);

	public boolean generateAbldMakefileIfNecessary(ICarbideBuildConfiguration config, CarbideCommandLauncher launcher, IPath componentPath, boolean isTest, IProgressMonitor progress);
	
	public IPath getMakefileDirectory(ICarbideBuildConfiguration config);
}
