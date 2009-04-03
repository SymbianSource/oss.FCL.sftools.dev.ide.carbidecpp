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


package com.nokia.carbide.templatewizard.processes;

import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.TemplateWizardPlugin;
import com.nokia.carbide.templatewizard.process.AbstractProcess;
import com.nokia.carbide.templatewizard.process.IParameter;

import org.eclipse.core.runtime.*;

import java.util.List;

/**
 * Process used in templates to create variables used for
 * text substitution in other template files.
 * @since 2.0
 */
public class CreateTemplateVariable extends AbstractProcess {
	
	protected static final String VARIABLE_PARAMETER = "variable"; //$NON-NLS-1$
	protected static final String NAME_ATTRIBUTE = "variableName"; //$NON-NLS-1$
	protected static final String VALUE_ATTRIBUTE = "value"; //$NON-NLS-1$

	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		super.process(template, parameters, monitor);
		
        for (IParameter parameter : parameters) {
			if (parameter.getName().equals(VARIABLE_PARAMETER)) {
				createVariable(template, parameter, monitor);
			}
		}
	}

	@Override
	protected void init(ITemplate template, List<IParameter> parameters) throws CoreException {
	}

	private void createVariable(ITemplate template, IParameter parameter, IProgressMonitor monitor) throws CoreException {
		String name = getRequiredAttributeFromParameter(parameter, NAME_ATTRIBUTE);
		String value = getRequiredAttributeFromParameter(parameter, VALUE_ATTRIBUTE);
		template.getTemplateValues().put(name, value);
	}

	protected Plugin getPlugin() {
		return TemplateWizardPlugin.getDefault();
	}
}
