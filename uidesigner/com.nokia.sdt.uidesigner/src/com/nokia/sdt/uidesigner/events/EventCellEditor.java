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
package com.nokia.sdt.uidesigner.events;

import com.nokia.sdt.component.event.IEventDescriptor;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

/**
 * Specialization of TextCellEditor to support
 * some code needed to get TreeViewer's in-place
 * editing working.
 *
 */
public class EventCellEditor extends TextCellEditor {
	
	private boolean isEphemeral;
	private IEventDescriptor descriptor;
	private Action doubleClickAction;

	public EventCellEditor(Composite parent) {
		super(parent);
		
		this.setValidator(new ICellEditorValidator() {

			public String isValid(Object value) {
				String result = null;
				if (value != null && !"".equals(value) && descriptor != null) {
					result = descriptor.isValidHandlerName(value.toString());
				}
				return result;
			}
		});
	}
	
	protected Control createControl(Composite parent) {
		Text text = (Text) super.createControl(parent);
		text.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if (doubleClickAction != null) {
					doubleClickAction.run();
				}
			}
		});
		return text;
	}
	
	void setDescriptor(IEventDescriptor descriptor) {
		this.descriptor = descriptor;
	}
	
	void markEphemeral() {
		isEphemeral = true;
	}
		
	protected void handleDefaultSelection(SelectionEvent event) {
		
		// Changes from the default behavior, which is to save if modified
		// and ignore otherwise.
		// - ephemeral, i.e. proposed default value, is an unmodified state.
		//   if the user wants to accept the proposed value and create a binding
		//   we have to mark dirty
		// - hitting enter should navigate, whether the value is clean or dirty.
		//   so with an unmodified value we have to manually invoke the action.
		
		if (isEphemeral) {
			markDirty();
		}
		
		if (isDirty()) {
	        deactivate();
		} else if (doubleClickAction != null) {
			doubleClickAction.run();
		}
	}

	protected void editOccured(ModifyEvent e) {
		isEphemeral = false;
		super.editOccured(e);
	}

	public void setDoubleClickAction(Action action) {
		this.doubleClickAction = action;
	}
}
