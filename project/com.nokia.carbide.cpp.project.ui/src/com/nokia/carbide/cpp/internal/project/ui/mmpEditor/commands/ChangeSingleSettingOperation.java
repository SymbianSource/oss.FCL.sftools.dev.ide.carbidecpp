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
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Control;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.CarbideViewOperation;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlHandler;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.ui.editor.IEditingContext;

public class ChangeSingleSettingOperation extends CarbideViewOperation {

	private Control control;
	private EMMPStatement statement;
	private String oldValue;
	private String newValue;
	
	public ChangeSingleSettingOperation(IMMPView view, 
			IEditingContext editingContext,
			Control control,
			EMMPStatement statement, 
			String newValue) {
		super(view, "", editingContext, ControlHandler.getHandlerForControl(control)); //$NON-NLS-1$
		this.control = control;
		this.statement = statement;
		this.newValue = TextUtils.strlen(newValue) > 0? newValue : null;
		Map<EMMPStatement, String> sas = view.getSingleArgumentSettings();
		oldValue = sas.get(statement);
	}

	@Override
	public String getLabel() {
		String fmt = Messages.getString("ChangeSingleSettingOperation.undoFormatText"); //$NON-NLS-1$
		Object params[] = {statement.toString()};
		return MessageFormat.format(fmt, params);
	}
	
	@Override
	public IStatus doOperation(boolean updateControl) {
		Map<EMMPStatement, String> sas = ((IMMPView)getView()).getSingleArgumentSettings();
		sas.put(statement, newValue);
		if (updateControl) {
			setControl(control, newValue);
		}
		return Status.OK_STATUS;
	}

	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info) {
		Map<EMMPStatement, String> sas = ((IMMPView)getView()).getSingleArgumentSettings();
		sas.put(statement, oldValue);
		setControl(control, oldValue);
		return Status.OK_STATUS;
	}
	
	public static void setControl(Control control, String value) {
		ControlHandler handler = ControlHandler.getHandlerForControl(control);
		Check.checkState(handler != null);
		handler.storeText(value);
	}
}
