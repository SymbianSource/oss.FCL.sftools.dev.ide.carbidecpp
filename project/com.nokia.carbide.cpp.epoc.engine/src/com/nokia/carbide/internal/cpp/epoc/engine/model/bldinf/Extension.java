/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf;

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;

import java.util.*;


public class Extension implements IExtension {

	private List<IPath> dependencies = new ArrayList<IPath>();
	private Map<String, String> options = new LinkedHashMap<String, String>();
	private List<IPath> sources = new ArrayList<IPath>();
	private IPath targetPath;
	private IPath templatePath;
	private String toolName;

	public Extension() {
	}
	
	public Extension(Extension other) {
		this.dependencies = new ArrayList<IPath>(other.dependencies);
		this.options = new LinkedHashMap<String, String>(other.options);
		this.sources = new ArrayList<IPath>(other.sources);
		this.targetPath = other.targetPath;
		this.templatePath = other.templatePath;
		this.toolName = other.toolName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dependencies == null) ? 0 : dependencies.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result + ((sources == null) ? 0 : sources.hashCode());
		result = prime * result
				+ ((targetPath == null) ? 0 : targetPath.hashCode());
		result = prime * result
				+ ((templatePath == null) ? 0 : templatePath.hashCode());
		result = prime * result
				+ ((toolName == null) ? 0 : toolName.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Extension))
			return false;
		final Extension other = (Extension) obj;
		if (dependencies == null) {
			if (other.dependencies != null)
				return false;
		} else if (!dependencies.equals(other.dependencies))
			return false;
		if (options == null) {
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
		if (sources == null) {
			if (other.sources != null)
				return false;
		} else if (!sources.equals(other.sources))
			return false;
		if (targetPath == null) {
			if (other.targetPath != null)
				return false;
		} else if (!BldInfView.equalPath(targetPath, other.targetPath))
			return false;
		if (templatePath == null) {
			if (other.templatePath != null)
				return false;
		} else if (!BldInfView.equalPath(templatePath, other.templatePath))
			return false;
		if (toolName == null) {
			if (other.toolName != null)
				return false;
		} else if (!toolName.equalsIgnoreCase(other.toolName))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension#isValid()
	 */
	public boolean isValid() {
		return templatePath != null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension#getDependencies()
	 */
	public List<IPath> getDependencies() {
		return dependencies;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension#getOptions()
	 */
	public Map<String, String> getOptions() {
		return options;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension#getSources()
	 */
	public List<IPath> getSources() {
		return sources;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension#getTargetPath()
	 */
	public IPath getTargetPath() {
		return targetPath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension#getTemplatePath()
	 */
	public IPath getTemplatePath() {
		return templatePath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension#getToolName()
	 */
	public String getToolName() {
		return toolName;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension#setTargetPath(org.eclipse.core.runtime.IPath)
	 */
	public void setTargetPath(IPath path) {
		this.targetPath = path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension#setTemplatePath(org.eclipse.core.runtime.IPath)
	 */
	public void setTemplatePath(IPath path) {
		Check.checkArg(path);
		this.templatePath = path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension#setToolName(java.lang.String)
	 */
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExtension#copy()
	 */
	public IExtension copy() {
		return new Extension(this);
	}
}
