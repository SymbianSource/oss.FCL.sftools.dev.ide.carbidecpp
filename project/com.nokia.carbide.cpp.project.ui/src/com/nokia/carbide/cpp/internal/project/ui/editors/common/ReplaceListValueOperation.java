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
package com.nokia.carbide.cpp.internal.project.ui.editors.common;

import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.cpp.internal.api.utils.ui.editor.IEditingContext;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.*;

import java.text.MessageFormat;
import java.util.*;

public class ReplaceListValueOperation extends CarbideViewOperation {

	private final ControlHandler controlHandler;
	private final ICarbideListProvider listProvider;
	private final Map<Integer, Object> replaceMap;
	private final Map<Integer, Object> undoMap;

	public ReplaceListValueOperation(IView view,
			IEditingContext editingContext, 
			ControlHandler controlHandler, 
			ICarbideListProvider listProvider, 
			Map<Integer, Object> replaceMap) {
		super(view, "", editingContext, controlHandler); //$NON-NLS-1$
		this.controlHandler = controlHandler;
		this.listProvider = listProvider;
		this.replaceMap = replaceMap;
		
		List<Object> list = listProvider.fetchList(getView());
		undoMap = new HashMap<Integer, Object>();
		for (Map.Entry<Integer, Object> entry : replaceMap.entrySet()) {
			undoMap.put(entry.getKey(), list.get(entry.getKey()));
		}
	}
	
	@Override
	public String getLabel() {
		String fmt = Messages.ChangeListValueOperation_modifyStatement;
		Object params[] = {listProvider.getDisplayText()};
		return MessageFormat.format(fmt, params);
	}
	
	@Override
	public IStatus doOperation(boolean updateControl) {
		List<Object> list = listProvider.fetchList(getView());
		for (Map.Entry<Integer, Object> entry : replaceMap.entrySet()) {
			list.set(entry.getKey(), entry.getValue());
		}
		if (updateControl && controlHandler != null) {
			controlHandler.refresh();
		}
		return Status.OK_STATUS;
	}

	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		List<Object> list = listProvider.fetchList(getView());
		for (Map.Entry<Integer, Object> entry : undoMap.entrySet()) {
			list.set(entry.getKey(), entry.getValue());
		}
		if (controlHandler != null) {
			controlHandler.refresh();
		}		
		return Status.OK_STATUS;
	}
}
