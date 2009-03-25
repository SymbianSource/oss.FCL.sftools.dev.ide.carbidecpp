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
package com.nokia.carbide.cdt.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.epoc.engine.model.IModelDocumentProvider;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefaultModelDocumentProvider;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefaultTranslationUnitProvider;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.ITranslationUnitProvider;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

public class DefaultViewParserConfiguration implements IViewParserConfiguration {

	private IProject project;
	private IPath bldInfPath;
	private IIncludeFileLocator includeFileLocator = null;
	private IPath projectPath;
	private ISymbianBuildContext buildContext;
	
	/**
	 * Create a view parser configuration for the given project or
	 * bld.inf path, and optionally a build context.
	 * <p>
	 * One of the project or build context may be null, but not both.
	 * This provides the root for the MMP/bld.inf models.
	 * <p>
	 * The SDK is searched for #includes and may be null.
	 */
	public DefaultViewParserConfiguration(IProject project, ISymbianBuildContext buildContext, IPath bldInfPath) {
		this.project = project;
		this.buildContext = buildContext;
		this.bldInfPath = bldInfPath;
	}
	
	/**
	 * Create a view parser configuration for the given project path.
	 */
	public DefaultViewParserConfiguration(IPath projectPath) {
		this.projectPath = projectPath;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bldInfPath == null) ? 0 : bldInfPath.hashCode());
		result = prime * result
				+ ((buildContext == null) ? 0 : buildContext.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result
				+ ((projectPath == null) ? 0 : projectPath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DefaultViewParserConfiguration other = (DefaultViewParserConfiguration) obj;
		if (bldInfPath == null) {
			if (other.bldInfPath != null)
				return false;
		} else if (!bldInfPath.equals(other.bldInfPath))
			return false;
		if (buildContext == null) {
			if (other.buildContext != null)
				return false;
		} else if (!buildContext.equals(other.buildContext))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (projectPath == null) {
			if (other.projectPath != null)
				return false;
		} else if (!projectPath.equals(other.projectPath))
			return false;
		return true;
	}

	public IIncludeFileLocator getIncludeFileLocator() {
		if (includeFileLocator == null) {
			includeFileLocator = new DefaultIncludeFileLocator(project, buildContext);
		}
		return includeFileLocator;
	}

	/**
	 * For test purposes.  
	 * @param includeFileLocator the includeFileLocator to set
	 */
	public void setIncludeFileLocator(
			IIncludeFileLocator includeFileLocator) {
		this.includeFileLocator = includeFileLocator;
	}
	
	/** For testing purposes: usually the project provides the path, 
	 * or bld.inf's drive is the 'project' while importing */
	public void setProjectPath(IPath projectPath) {
		this.projectPath = projectPath;
	}
	
	public IPath getProjectLocation() {
		if (project != null) {
			return CarbideBuilderPlugin.getProjectRoot(project);
		}
		if (projectPath != null) {
			return projectPath;
		}
		if (bldInfPath != null) {
			String device = bldInfPath.getDevice();
			if (device != null) {
				return new Path(device).makeAbsolute();
			} else {
				// maybe a network path
				if (bldInfPath.isUNC()) {
					// when there's no project and it's local, we just return the drive letter.  for network
					// paths, I assume we just return the machine name?
					return bldInfPath.removeLastSegments(bldInfPath.segmentCount()-1);
				}
			}
		}
		assert(false); // should never happen
		return null;
	}

	public ITranslationUnitProvider getTranslationUnitProvider() {
		return DefaultTranslationUnitProvider.getInstance();
	}

	public IModelDocumentProvider getModelDocumentProvider() {
		return DefaultModelDocumentProvider.getInstance();
	}
}
