/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.template.engine;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import java.util.Map;

/**
 * This interface, available through {@link ITemplate#getLoadedTemplate()},
 * provides the runtime model for template XML and a way to create its UI.  
 */
public interface ILoadedTemplate {
	/**
	 * Get the owning template.
	 * @return ITemplate, never <code>null</code>
	 */
	ITemplate getTemplate();
	
	/** 
	 * Get the localized label for the template.
	 * @return <code>java.lang.String</code>
	 */
	String getLabel();
	
	/** 
	 * Get the localized description for the template.
	 * @return <code>java.lang.String</code>
	 */
	String getDescription();
	
	/**
	 * Get metadata attached to the template from &lt;metadata&gt; elements on &lt;template&gt;
	 * @return Map, never <code>null</code>
	 */
	Map<String, String> getMetadata();

	/**
	 * Create the UI for the template, for use by a wizard.
	 * using the given interface.
	 */
	ILoadedTemplateUI createLoadedTemplateUI();
	
	/**
	 * Run a template's processes using its current template values and return a status
	 * (usually MultiStatus) for any processes that fail.
	 * @param monitor
	 * @return IStatus, never <code>null</code>
	 */
	IStatus runProcesses(IProgressMonitor monitor);

}
