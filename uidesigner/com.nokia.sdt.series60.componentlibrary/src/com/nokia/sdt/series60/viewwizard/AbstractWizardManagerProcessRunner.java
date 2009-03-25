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
package com.nokia.sdt.series60.viewwizard;

import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.process.AbstractProjectProcess;
import com.nokia.carbide.templatewizard.process.IParameter;
import com.nokia.sdt.series60.component.Series60ComponentPlugin;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Plugin;

import java.util.List;

public abstract class AbstractWizardManagerProcessRunner extends AbstractProjectProcess {

	public final static String MANAGER_VALUE_STORE_KEY = "com.nokia.sdt.series60.viewwizard.ViewWizardManager"; //$NON-NLS-1$
	
	private ViewWizardManager manager;

	/* (non-Javadoc)
	 * @see com.nokia.carbide.templatewizard.processes.AbstractProjectProcess#init(com.nokia.carbide.template.engine.ITemplate, java.util.List)
	 */
	@Override
	protected void init(ITemplate template, List<IParameter> parameters) throws CoreException {
		super.init(template, parameters);
		
		manager = (ViewWizardManager) template.getTemplateValues().get(MANAGER_VALUE_STORE_KEY);
		manager.setTemplate(template);
	}

	/**
	 * Accessor for the subclasses after calling super.process.
	 * @return ViewWizardManager
	 */
	protected ViewWizardManager getManager() {
		return manager;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.templatewizard.process.AbstractProcess#getPlugin()
	 */
	@Override
	protected Plugin getPlugin() {
		return Series60ComponentPlugin.getDefault();
	}
}
