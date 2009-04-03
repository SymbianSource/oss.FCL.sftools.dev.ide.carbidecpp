/*
* Copyright (c) 2006, 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.templatewizard.process;

import com.nokia.carbide.template.engine.ITemplate;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import java.util.List;
import java.util.Map;

/**
 * Represents a process specified as a &lt;process&gt; element in a template xml file,
 * and executed after successful completion of the wizard specified by the template.<br>
 * Classes implementing this interface require a 0-argument public constructor, 
 * and will be instantiated separately for each process element existing in a template xml file.<br>
 * State can be shared between processes through the {@link Map}
 * returned from <code>{@link ITemplate#getTemplateValues()}</code><br>
 */
public interface IProcess {
	
	/**
	 * The main method called to execute this process.
	 * 
	 * @param template the {@link ITemplate} for this process
	 * @param parameters a list of {@link IParameter}
	 * @param monitor the {@link IProgressMonitor}
	 * @throws CoreException if the process fails
	 */
	void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException;
	
	/**
	 * This method is called before executing this process, to determine whether it should be executed in
	 * the UI thread. 
	 * 
	 * @see org.eclipse.swt.widgets.Display#syncExec(Runnable)
	 * @return <code>true</code> to ensure this process is executed in the UI thread.
	 */
	boolean mustRunInUIThread();
}

