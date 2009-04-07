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

package com.nokia.carbide.cpp.epoc.engine.model.bldinf;

import org.eclipse.core.runtime.IPath;

import java.util.List;
import java.util.Map;

/**
 * This interface represents the content of an entry in PRJ_EXTENSIONS block.
 * <p>
 * It's recommended to use a utility class (like BldInfViewPathHelpers) to interpret
 * and set the paths in this extension and resolve paths to actual project or filesystem
 * files.
 *
 */
public interface IExtension {
	/** Check whether the template path is set. */
	boolean isValid();
	
	/** Get EPOCROOT\epoc32\tools\makefile_templates - relative path to extension makefile template.  As in the bld.inf, no extension will be present.
	 * Either .mk or .meta is appended to find the paired files for the makefile. */
	IPath getTemplatePath();
	
	/** Set EPOCROOT\epoc32\tools\makefile_templates - relative path to extension makefile template.  As in the bld.inf, no extension should be present. */
	void setTemplatePath(IPath path);
	
	/** Get the project-relative target path, may be null */
	IPath getTargetPath();
	
	/** Set the project-relative target path, may be null */
	void setTargetPath(IPath path);
	
	/** Access/modify the list of project-relative sources. */
	List<IPath> getSources();
	
	/** Access/modify the list of dependencies.  No interpretation is performed.  As in the bld.inf, no extension will be present. */
	List<IPath> getDependencies();
	
	/** Get the name associated with the extension, may be null for unnamed extension */
	String getName();
	
	/** Set the name associated with the extension, may be null for unnamed extension */
	void setName(String name);
	
	/** Get the tool name, may be null */
	String getToolName();
	
	/** Set the tool name, may be null */
	void setToolName(String toolName);
	
	/** Access/modify the option map */
	Map<String, String> getOptions();

	/**
	 * Return a copy of the data
	 * @return
	 */
	IExtension copy();
}
