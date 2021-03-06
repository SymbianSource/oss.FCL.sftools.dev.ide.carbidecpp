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
/* START_USECASES: CU1, CU2 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.viewwizard;

import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.process.IParameter;
import com.nokia.sdt.datamodel.IDesignerDataModel;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import java.util.List;

public class SetPropertyInViewProcessRunner extends AbstractSetPropertyProcessRunner {

	/* (non-Javadoc)
	 * @see com.nokia.carbide.templatewizard.process.AbstractProcess#process(com.nokia.carbide.template.engine.ITemplate, java.util.List, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void process(ITemplate template, List<IParameter> parameters, IProgressMonitor monitor) throws CoreException {
		super.process(template, parameters, monitor);

		setModel((IDesignerDataModel) getManager().getDataStore().get(ViewWizardManager.VIEW_MODEL_KEY));
		setProperty();
	}
}
