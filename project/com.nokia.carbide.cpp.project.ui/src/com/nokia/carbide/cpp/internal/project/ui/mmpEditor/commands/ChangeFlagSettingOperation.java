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
import java.util.Set;

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
import com.nokia.cpp.internal.api.utils.ui.editor.IEditingContext;

public class ChangeFlagSettingOperation extends CarbideViewOperation {

	private Control control;
	private EMMPStatement statement;
	private boolean oldValue;
	private boolean newValue;
	
	public ChangeFlagSettingOperation(IMMPView view, 
			IEditingContext editingContext,
			Control control,
			EMMPStatement statement, 
			boolean newValue) {
		super(view, "", editingContext, ControlHandler.getHandlerForControl(control)); //$NON-NLS-1$
		this.control = control;
		this.statement = statement;
		this.newValue = newValue;
		oldValue = view.getFlags().contains(statement);
	}

	@Override
	public String getLabel() {
		String fmt = Messages.getString("ChangeFlagSettingOperation.undoFormatText"); //$NON-NLS-1$
		Object params[] = {statement.toString()};
		return MessageFormat.format(fmt, params);
	}
	
	@Override
	public IStatus doOperation(boolean updateControl) {
		Set<EMMPStatement> flags = ((IMMPView)getView()).getFlags();
		if (newValue) {
			flags.add(statement);
		} else {
			flags.remove(statement);
		}
		if (updateControl) {
			setControl(control, newValue);
		}
		return Status.OK_STATUS;
	}

	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info) {
		Set<EMMPStatement> flags = ((IMMPView)getView()).getFlags();
		if (oldValue) {
			flags.add(statement);
		} else {
			flags.remove(statement);
		}
		setControl(control, oldValue);
		return Status.OK_STATUS;
	}
	
	public static void setControl(Control control, boolean value) {
		ControlHandler handler = ControlHandler.getHandlerForControl(control);
		Check.checkState(handler != null);
		handler.setValue(value);
	}
}
