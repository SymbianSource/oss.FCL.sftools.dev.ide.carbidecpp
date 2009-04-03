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


package com.nokia.sdt.series60.viewwizard;

import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.process.IParameter;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.series60.component.Series60ComponentPlugin;
import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

import java.text.MessageFormat;
import java.util.List;

public abstract class AbstractSetPropertyProcessRunner extends AbstractWizardManagerProcessRunner {
	
	private static final String PROPERTY_PARAMETER = "property";
	private static final String INSTANCE_NAME_ATTRIBUTE = "instanceName";
	private static final String PROPERTY_PATH_ATTRIBUTE = "propertyPath";
	private static final String VALUE_ATTRIBUTE = "value";
	
	private IDesignerDataModel model;
	private String instanceName;
	private String propertyPath;
	private String value;
	
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.series60.viewwizard.AbstractWizardManagerProcessRunner#init(com.nokia.carbide.template.engine.ITemplate, java.util.List)
	 */
	@Override
	protected void init(ITemplate template, List<IParameter> parameters) throws CoreException {
		super.init(template, parameters);
		
		IParameter propertyParameter = getRequiredParameterByName(PROPERTY_PARAMETER, parameters);
		this.instanceName = getRequiredAttributeFromParameter(propertyParameter, INSTANCE_NAME_ATTRIBUTE);
		this.propertyPath = getRequiredAttributeFromParameter(propertyParameter, PROPERTY_PATH_ATTRIBUTE);
		this.value = getRequiredAttributeFromParameter(propertyParameter, VALUE_ATTRIBUTE);
	}

	public void setModel(IDesignerDataModel model) {
		this.model = model;
	}
	
	public void setProperty() {
		EObject instance = model.findByNameProperty(instanceName);
		if (instance == null) {
			String messageFmt = Messages.getString("AbstractSetPropertyProcessRunner.InstanceNotFoundError"); //$NON-NLS-1$
			String message = MessageFormat.format(messageFmt, instanceName);
			IStatus status = Logging.newStatus(Series60ComponentPlugin.getDefault(), IStatus.ERROR, message);
			Logging.log(UIDesignerPlugin.getDefault(), status);
		}
		IStatus status = ModelUtils.writeProperty(instance, propertyPath, value);
		if ((status != null) && !status.isOK())
			Logging.log(Series60ComponentPlugin.getDefault(), status);
	}
}
