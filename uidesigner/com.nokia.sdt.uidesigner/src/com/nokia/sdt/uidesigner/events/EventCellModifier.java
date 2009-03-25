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
/**
 * 
 */
package com.nokia.sdt.uidesigner.events;

import com.nokia.sdt.component.event.HandlerMethodInformation;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.sdt.uidesigner.events.EventModel.EventItem;
import com.nokia.sdt.uidesigner.ui.utils.Messages;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.*;

/**
 * ICellModifier is used by TreeViewer to support in-place cell editing
 */
class EventCellModifier implements ICellModifier {

	private EventPage eventPage;

	EventCellModifier(EventPage page) {
		eventPage = page;
	}

	public boolean canModify(Object element, String property) {
		return element instanceof EventModel.EventItem &&
			   eventPage.columnProperties[1].equals(property);
	}

	public Object getValue(Object element, String property) {
		Object result = null;
		if (element instanceof EventModel.EventItem &&
			   eventPage.columnProperties[1].equals(property)) {
			EventModel.EventItem item = (EventItem) element;
			if (item.binding != null) {
				result = item.binding.getHandlerName();
			}
			else {
				IComponentInstance ci = eventPage.model.getInstances()[0];
				HandlerMethodInformation info = item.descriptor.generateHandlerMethodInfo(ci, null);
				if (info != null) {
					result = info.getDisplayText();
				}
			}
		}
		return result;
	}

	public void modify(Object element, String property, Object value) {
		
		// This method should be called to commit user edits.
		// However, the TreeViewer implementation also calls
		// it at the start of editing. We put the default
		// handler name up for editing when no binding exists. We
		// do not want to commit that and create a new binding
		// prior to the user accepting the empty. Hence we add some
		// code around the cell editor's dirty state
		boolean dirty = eventPage.cellEditorIsDirty();
		if (!dirty) return;
		
		// TreeViewer passes a TreeItem instead of the model object
		if (element instanceof TreeItem) {
			element = ((TreeItem)element).getData();
		}
		if (element instanceof EventModel.EventItem &&
				   eventPage.columnProperties[1].equals(property)) {
			
			EventModel.EventItem item = (EventItem) element;
			String handlerName = null;
			if (value != null) {
				handlerName = value.toString();
				if (handlerName != null && handlerName.length() == 0)
					handlerName = null;
			}
			
			if (handlerName != null && item.binding != null && 
				handlerName.equals(item.binding.getHandlerName())) {
				EventCommands.navigateToHandlerCode(eventPage, item.binding, false);
			} else {
				IComponentInstance instances[] = eventPage.model.getInstances();
				
				Command command = EventCommands.createEventBindingCommand(
						instances, item.descriptor, handlerName);
				
				eventPage.executeEMFCommand(command);

				// If we're adding or modifying bindings then we need to
				// get permission to save the model before navigating. 
				// Not required if we're deleting bindings.
				boolean okToSave = false;
				if (handlerName != null) {
					IDesignerDataModel model = instances[0].getDesignerDataModel();
					okToSave = EventCommands.userConfirmationToSaveModelDialog(
								eventPage.getParentShell(), model);
				}
								
				// If command is successful then navigate to the code
				IEventBinding binding = EventCommands.getEventBindingFromCommandResult(command);
				if (binding != null) {
					if (okToSave && eventPage.saveDataModel()) {
						// don't move into EventCommands#navigateToHandlerCode since 
						// it tries to save on error -- which we just did
						IStatus status = item.descriptor.gotoHandlerCode(binding, true);
						if (status != null)
							Logging.showErrorDialog(Messages.getString("EventCellModifier.0"), Messages.getString("EventCellModifier.1"), status); //$NON-NLS-1$ //$NON-NLS-2$
					} 
				}
			}
		}
	}
	
}