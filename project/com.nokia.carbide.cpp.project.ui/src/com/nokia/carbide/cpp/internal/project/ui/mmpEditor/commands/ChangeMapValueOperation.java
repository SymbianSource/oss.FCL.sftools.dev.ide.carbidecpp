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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Control;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.CarbideViewOperation;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlHandler;
import com.nokia.cpp.internal.api.utils.ui.editor.IEditingContext;

/**
 * Operation to modify a map entry in the mmp view.
 * This operation removes null values, it does not maintain
 * map entries whose value is null.
 */
public class ChangeMapValueOperation extends CarbideViewOperation {

	private final EMMPStatement statement;
	private final Map mmpStatementMap;
	private final Object mapKey;
	private final Object oldValue;
	private final Object mapValue;
	private final Control control;

	/**
	 * Constructs the operation
	 * @param mmpView the affected IMMPView
	 * @param statement the statement related to the map
	 * @param mmpStatementMap the map containing the values, obtained from the mmpView
	 * @param mapKey the key to the value being modified
	 * @param control the GUI control to be refreshed for undo, redo
	 * @param editingContext editingContent for the operation
	 */
	public ChangeMapValueOperation(IMMPView mmpView, 
			EMMPStatement statement, 
			Map mmpStatementMap,
			Object mapKey, Object mapValue,
			Control control,
			IEditingContext editingContext) {
		super(mmpView, "", editingContext, ControlHandler.getHandlerForControl(control)); //$NON-NLS-1$
		this.statement = statement;
		this.mmpStatementMap = mmpStatementMap;
		this.mapKey = mapKey;
		this.mapValue = mapValue;
		this.control = control;
		this.oldValue = mmpStatementMap.get(mapKey);
	}
	
	@Override
	public String getLabel() {
		String fmt = Messages.getString("ChangeMapValueOperation.undoFormat");		 //$NON-NLS-1$
		Object params[] = {statement.toString()};
		return MessageFormat.format(fmt, params);
	}

	@SuppressWarnings("unchecked") //$NON-NLS-1$
	@Override
	public IStatus doOperation(boolean updateControl) {
		if (mapValue != null) {
			mmpStatementMap.put(mapKey, mapValue);
		} else {
			mmpStatementMap.remove(mapKey);
		}
		if (updateControl) {
			ControlHandler handler = ControlHandler.getHandlerForControl(control);
			if (handler != null) {
				handler.refresh();
			}
		}
		return Status.OK_STATUS;
	}

	@SuppressWarnings("unchecked") //$NON-NLS-1$
	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		if (oldValue != null) {
			mmpStatementMap.put(mapKey, oldValue);
		} else {
			mmpStatementMap.remove(mapKey);
		}
		ControlHandler handler = ControlHandler.getHandlerForControl(control);
		if (handler != null) {
			handler.refresh();
		}
		return Status.OK_STATUS;
	}
}
