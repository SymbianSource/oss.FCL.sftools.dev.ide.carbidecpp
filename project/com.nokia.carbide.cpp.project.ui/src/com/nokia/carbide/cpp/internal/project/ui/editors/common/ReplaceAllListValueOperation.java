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
import java.util.ArrayList;
import java.util.List;

public class ReplaceAllListValueOperation extends CarbideViewOperation {

	private final ControlHandler controlHandler;
	private final ICarbideListProvider listProvider;
	private final List<Object> itemsToAdd;
	private final List<Object> originalList;

	public ReplaceAllListValueOperation(IView view,
			IEditingContext editingContext, 
			ControlHandler controlHandler, 
			ICarbideListProvider listProvider, 
			List<Object> items) {
		super(view, "", editingContext, controlHandler); //$NON-NLS-1$
		this.controlHandler = controlHandler;
		this.listProvider = listProvider;
		this.itemsToAdd = items;
		
		originalList = new ArrayList<Object>(listProvider.fetchList(getView()));
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
		if (updateControl && controlHandler != null) {
			List<Object> controlItems = controlHandler.modelToViewerElements(list);
			controlHandler.removeListItems(controlItems);
		}
		list.clear();
		list.addAll(itemsToAdd);
		if (updateControl && controlHandler != null) {
			List<Object> controlItems = controlHandler.modelToViewerElements(itemsToAdd);
			controlHandler.addListItems(controlItems);
		}
		return Status.OK_STATUS;
	}

	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		List<Object> list = listProvider.fetchList(getView());
		list.clear();
		list.addAll(originalList);
		if (controlHandler != null) { 
			controlHandler.refresh();
			List<Object> controlItems = controlHandler.modelToViewerElements(list);
			controlHandler.setValue(controlItems);
		}
		return Status.OK_STATUS;
	}
}
