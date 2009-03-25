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

import org.eclipse.core.runtime.*;

import java.text.MessageFormat;
import java.util.List;

/**
 * Undoable operation to add items to an IView.
 */
public class AddListValueOperation extends CarbideViewOperation {

	private final ControlHandler controlHandler;
	private final ICarbideListProvider listProvider;
	private final List items;

	public AddListValueOperation(IView view,
			IEditingContext editingContext, 
			ControlHandler controlHandler, 
			ICarbideListProvider listProvider, 
			List items) {
		super(view, "", editingContext, controlHandler); //$NON-NLS-1$
		this.controlHandler = controlHandler;
		this.listProvider = listProvider;
		this.items = items;
		
	}
	
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	@Override
	public IStatus doOperation(boolean updateControl) {
		List<Object> list = listProvider.fetchList(getView());
		list.addAll(items);
		if (updateControl && controlHandler != null) {
			List<Object> controlItems = controlHandler.modelToViewerElements(items);
			controlHandler.addListItems(controlItems);
		}
		return Status.OK_STATUS;
	}
	
	@Override
	public String getLabel() {
		String fmt = Messages.ChangeListValueOperation_modifyStatement;
		Object params[] = {listProvider.getDisplayText()};
		return MessageFormat.format(fmt, params);
	}
	
	@Override
	protected IStatus doExecute(IProgressMonitor monitor, IAdaptable info) {
		doOperation(false);
		return Status.OK_STATUS;
	}

	@Override
	protected IStatus doRedo(IProgressMonitor monitor, IAdaptable info) {
		doOperation(true);
		return Status.OK_STATUS;
	}

	@SuppressWarnings("unchecked") //$NON-NLS-1$
	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info) {
		List<Object> list = listProvider.fetchList(getView());
		for (int i = 0; i < items.size(); i++) {
			list.remove(list.size()-1);
		}
		if (controlHandler != null) {
			controlHandler.removeListItems(controlHandler.modelToViewerElements(items));
		}
		return Status.OK_STATUS;
	}
}
