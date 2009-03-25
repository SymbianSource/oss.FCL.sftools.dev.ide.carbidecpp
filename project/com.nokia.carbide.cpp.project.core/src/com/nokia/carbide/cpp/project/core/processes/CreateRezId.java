/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.cpp.project.core.processes;

import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.process.AbstractProjectProcess;
import com.nokia.carbide.templatewizard.process.IParameter;

import org.eclipse.core.runtime.*;

import java.util.List;

/**
 * Process used in templates to create a 4 character string based on the project name, 
 * which is used in the main resource file in the NAME statement.<p>
 * See the documentation for Creating Wizard Templates.
 */
public class CreateRezId extends AbstractProjectProcess {

	private static final int NUM_REZ_ID_CHARS = 4;
	private static final String REZ_ID_KEY = "rezId"; //$NON-NLS-1$
	private static final String[] EXCLUDED_VALUES = { 
		"EKA2", //$NON-NLS-1$
		"WORD", //$NON-NLS-1$
		"BYTE", //$NON-NLS-1$
		"LONG", //$NON-NLS-1$
		"ENUM", //$NON-NLS-1$
		"TEXT", //$NON-NLS-1$
		"BUF8", //$NON-NLS-1$
		"LINK", //$NON-NLS-1$
		"PLUS", //$NON-NLS-1$
		"CHAR", //$NON-NLS-1$
		"NAME", //$NON-NLS-1$
		"UID2", //$NON-NLS-1$
		"UID3" //$NON-NLS-1$
	};

	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		super.process(template, parameters, monitor);
		
		// add the rezId variable from the project name
		String rezIdValue = createRezIdValue();
		template.getTemplateValues().put(REZ_ID_KEY, rezIdValue);

	}
	
	private String createRezIdValue() {
		String rezIdValue = getProjectName().toUpperCase();
		rezIdValue = rezIdValue.replaceAll("-||_", ""); //$NON-NLS-1$ //$NON-NLS-2$
		int projectNameLen = rezIdValue.length();
		if (projectNameLen > NUM_REZ_ID_CHARS)
			rezIdValue = rezIdValue.substring(0, NUM_REZ_ID_CHARS);
		else {
			for (int i = 0; i < NUM_REZ_ID_CHARS - projectNameLen; i++)
				rezIdValue += 'X';
		}
		
		for (String excluded : EXCLUDED_VALUES) {
			if (rezIdValue.equalsIgnoreCase(excluded)) {
				// replace the last char with an X
				rezIdValue = rezIdValue.substring(0, 3) + 'X';
				break;
			}
		}
		return rezIdValue;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.templatewizard.process.AbstractProcess#getPlugin()
	 */
	protected Plugin getPlugin() {
		return ProjectCorePlugin.getDefault();
	}

}
