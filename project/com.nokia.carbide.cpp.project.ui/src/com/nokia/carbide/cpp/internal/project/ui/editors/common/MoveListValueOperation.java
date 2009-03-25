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

public class MoveListValueOperation extends CarbideViewOperation {

	private final ControlHandler controlHandler;
	private final ICarbideListProvider listProvider;
	private final Map<Integer, Integer> itemMap;
	private final List<Object> originalList;

	/**
	 * Create an operation to move list items from one index to another.
	 * @param view
	 * @param editingContext
	 * @param controlHandler
	 * @param listProvider
	 * @param itemMap provides the old and new index for each item to be moved. 
	 * A TreeMap is specified because it can provide an ordered set of keys, which is required
	 * internally by this class.
	 */
	public MoveListValueOperation(IView view,
			IEditingContext editingContext, 
			ControlHandler controlHandler, 
			ICarbideListProvider listProvider, 
			Map<Integer, Integer> itemMap) {
		super(view, "", editingContext, controlHandler); //$NON-NLS-1$
		this.controlHandler = controlHandler;
		this.listProvider = listProvider;
		this.itemMap = itemMap;
		
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
		// The TreeMap interface specifies the keys will be in ascending order
		// We then remove in descending order to ensure indices aren't invalidated
		Integer[] indices = itemMap.keySet().toArray(new Integer[itemMap.size()]);
		for (int oldIndex : indices) {
			Object obj = list.remove(oldIndex);
			int newIndex = itemMap.get(oldIndex);
			list.add(newIndex, obj);
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
		list.clear();
		list.addAll(originalList);
		if (controlHandler != null) { 
			controlHandler.refresh();
		}
		return Status.OK_STATUS;
	}
}
