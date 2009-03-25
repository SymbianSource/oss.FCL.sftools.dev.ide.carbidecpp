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

/** 
 * Operation to remove one or more items from an IView list.
 */
public class RemoveListValueOperation extends CarbideViewOperation {

	private final ControlHandler controlHandler;
	private final ICarbideListProvider listProvider;
	private final TreeMap<Integer, Object> itemMap;

	public RemoveListValueOperation(IView view,
			IEditingContext editingContext, 
			ControlHandler controlHandler, 
			ICarbideListProvider listProvider, 
			List<Integer> itemIndices) {
		super(view, "", editingContext, controlHandler); //$NON-NLS-1$
		this.controlHandler = controlHandler;
		this.listProvider = listProvider;
		
		// Record original objects for undo purposes
		List<Object> currItems = listProvider.fetchList(getView());
		itemMap = new TreeMap<Integer, Object>();
		for(int index : itemIndices) {
			itemMap.put(index, currItems.get(index));
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
		List<Object> itemsToRemove = new ArrayList<Object>();
		
		// The TreeMap interface specifies the keys will be in ascending order
		// We then remove in descending order to ensure indices aren't invalidated
		Integer[] indices = itemMap.keySet().toArray(new Integer[itemMap.size()]);
		for (int index : indices) {
			itemsToRemove.add(list.get(index));
		}
		for (int i = indices.length-1; i >= 0; --i) {
			int index = indices[i];
			list.remove(index);
		}
		if (updateControl && controlHandler != null) {
			List<Object> controlItems = controlHandler.modelToViewerElements(itemsToRemove);
			controlHandler.removeListItems(controlItems);
		}
		return Status.OK_STATUS;
	}

	@Override
	protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		List<Object> list = listProvider.fetchList(getView());
		List<Object> itemsToAdd = new ArrayList<Object>();
		for (Map.Entry<Integer, Object> entry : itemMap.entrySet()) {
			list.add(entry.getKey(), entry.getValue());
			itemsToAdd.add(entry.getValue());
		}
		if (controlHandler != null) {
			List<Object> controlItems = controlHandler.modelToViewerElements(itemsToAdd);
			controlHandler.addListItems(controlItems);
		}
		return Status.OK_STATUS;
	}
}
