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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Control;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.CarbideViewOperation;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlHandler;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.ui.editor.IEditingContext;

public class ChangeStringValueOperation extends CarbideViewOperation {
	
	private final IMMPStringValueProvider stringValueProvider;
	private final Control control;
	private final String newValue;
	private final String oldValue;

	public ChangeStringValueOperation(IMMPView mmpView,
			IEditingContext editingContext, 
			Control control, IMMPStringValueProvider stringValueProvider, 
			String newValue) {
		super(mmpView, "", editingContext, ControlHandler.getHandlerForControl(control)); //$NON-NLS-1$
		this.stringValueProvider = stringValueProvider;
		this.control = control;
		this.newValue = newValue;
		this.oldValue = stringValueProvider.fetchString(mmpView);
	}
	
	@Override
	public String getLabel() {
		String fmt = Messages.getString("ChangeStringValueOperation.undoFormatText"); //$NON-NLS-1$
		Object params[] = {stringValueProvider.getDisplayText()};
		return MessageFormat.format(fmt, params);
	}

	@Override
	public IStatus doOperation(boolean updateControl) {
		stringValueProvider.storeString(((IMMPView)getView()), newValue);
		if (updateControl) {
			setControl(control, newValue);
		}
		return Status.OK_STATUS;
	}

	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info) {
		stringValueProvider.storeString(((IMMPView)getView()), oldValue);
		setControl(control, oldValue);
		return Status.OK_STATUS;
	}
	
	public static void setControl(Control control, String value) {
		ControlHandler handler = ControlHandler.getHandlerForControl(control);
		Check.checkState(handler != null);
		handler.storeText(value);
	}
}
