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

import java.util.List;

/**
 * An abstract base class to enable implementing 
 * <code>com.nokia.carbide.templatewizard.process.IProcess</code> <p>
 * Requires a parameter with name "project" and attribute "projectName".
 * @see AbstractProcess
 */
public abstract class AbstractProjectProcess extends AbstractProcess {

	private static final String PROJECT_NAME = "projectName"; //$NON-NLS-1$
	private static final String PROJECT_PARAMETER = "project"; //$NON-NLS-1$
	protected IParameter projectParameter;
	private String projectName;

	public AbstractProjectProcess() {
		super();
	}

	protected void init(ITemplate template, List<IParameter> parameters) throws CoreException {
		projectParameter = getRequiredParameterByName(PROJECT_PARAMETER, parameters);
		projectName = getRequiredAttributeFromParameter(projectParameter, PROJECT_NAME);
	}

	/**
	 * @return the value of the "projectName" attribute of the project parameter.
	 */
	protected String getProjectName() {
		return projectName;
	}
}