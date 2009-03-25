/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.uidesigner.ui.editparts.policy;

import com.nokia.sdt.datamodel.adapter.ISetValueCommandExtender;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.uidesigner.ui.command.ChangePropertyCommand;
import com.nokia.sdt.uidesigner.ui.editparts.ModelObjectEditPart;
import com.nokia.sdt.uidesigner.ui.editparts.NonLayoutObjectEditPart;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

public class NonLayoutObjectDirectEditPolicy extends DirectEditPolicy {

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.DirectEditPolicy#getDirectEditCommand(org.eclipse.gef.requests.DirectEditRequest)
	 */
	protected Command getDirectEditCommand(DirectEditRequest request) {
		String labelText = (String) request.getCellEditor().getValue();
		if (labelText == null)
			return null;
		
		NonLayoutObjectEditPart editPart = (NonLayoutObjectEditPart) getHost();
		String propertyPath = (String) request.getDirectEditFeature();
		if (propertyPath == null)
			throw new IllegalStateException();
		
		Command command = 
			new ChangePropertyCommand((EObject) editPart.getModel(), 
					propertyPath, labelText, request.getCellEditor().getValidator());

		ISetValueCommandExtender commandExtender = 
			Adapters.getSetValueCommandExtender((EObject) editPart.getModel());
		if (commandExtender != null) {
			String propertyName = ModelUtils.getPropertyNameFromPropertyPath(propertyPath);
			command = commandExtender.getExtendedCommand(propertyName, labelText, command);
		}

		return command;
	}
	
	private void setStatusLineErrorMessage(String errorMessage) {
		Check.checkState(getHost() instanceof ModelObjectEditPart);
		((ModelObjectEditPart) getHost()).setStatusLineErrorMessage(errorMessage);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.DirectEditPolicy#showCurrentEditValue(org.eclipse.gef.requests.DirectEditRequest)
	 */
	protected void showCurrentEditValue(DirectEditRequest request) {
		String errorMessage = request.getCellEditor().getErrorMessage();
		setStatusLineErrorMessage(errorMessage);
		
		String value = (String) request.getCellEditor().getValue();
		//code to prevent async layout from placing the cell editor twice.
		getHostFigure().getUpdateManager().performUpdate();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.DirectEditPolicy#eraseSourceFeedback(org.eclipse.gef.Request)
	 */
	public void eraseSourceFeedback(Request request) {
		setStatusLineErrorMessage(""); //$NON-NLS-1$
		super.eraseSourceFeedback(request);
	}

}
