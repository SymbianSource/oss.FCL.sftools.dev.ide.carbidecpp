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
package com.nokia.carbide.cpp.uiq.ui.vieweditor;


import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Form;

/**
 * Manages the set of ControlHandlers for a view
 */
public class ControlManagerCommands {
	
	private final List<ControlHandlerCommands> handlers = new ArrayList<ControlHandlerCommands>();
	private final List<ControlManagerCommands> subManagers = new ArrayList<ControlManagerCommands>();
	private Map<Control, String> extantValidationFailures = new HashMap<Control, String>();
	private IStatusLineManager statusLineManager;
	private Form form;
	private boolean inRefresh;
	private Map<ControlHandlerCommands, List<ControlHandlerCommands>> validationDependencies = new HashMap<ControlHandlerCommands, List<ControlHandlerCommands>>();

	public ControlManagerCommands() {
	}
	
	public void enableStatusLineValidationMessages(IStatusLineManager statusLineManager) {
		this.statusLineManager = statusLineManager;
	}
	
	public void enableFormValidationMessages(Form form) {
		this.form = form;
	}
	
	public void add(ControlHandlerCommands handler) {
		handlers.add(handler);
		handler.addListener(new ControlHandlerCommands.ControlHandlerAdapter() {
			@Override
			public void controlSelected(Control control) {
				handleValueModified(control);
			}
			
			@Override
			public void valueModified(Control control) {
				handleValueModified(control);
			}

			@Override
			public void validationSucceeded(Control control) {
				handleValidationSucceeded(control);
				if (!inRefresh)
					updateValidationMessage();
			}

			@Override
			public void validationFailed(Control control, String message) {
				handleValidationFailure(control, message);
				if (!inRefresh)
					updateValidationMessage();
			}
		});
	}
	
	protected void handleValueModified(Control control) {
		ControlHandlerCommands handler = ControlHandlerCommands.getHandlerForControl(control);
		if (handler != null) {
			List<ControlHandlerCommands> deps = validationDependencies.get(handler);
			if (deps != null) {
				for (ControlHandlerCommands dep : deps) {
					dep.validate();
				}
			}
		}
	}
	
	protected void handleValidationSucceeded(Control control) {
		extantValidationFailures.remove(control);
	}

	protected void handleValidationFailure(Control control, String message) {
		extantValidationFailures.put(control, message);
	}

	private void setValidationFailureMessage(String message) {
		if (statusLineManager != null) {
			statusLineManager.setErrorMessage(message);
		}
		if (form != null) {
			form.setMessage(message, IMessageProvider.ERROR);
		}
	}

	private void clearValidationFailure() {
		Check.checkState(extantValidationFailures.isEmpty());
		if (statusLineManager != null) {
			statusLineManager.setErrorMessage(null);
		}
		if (form != null) {
			form.setMessage(null);
		}
	}
	
	private void updateValidationMessage() {
		if (extantValidationFailures.isEmpty())
			clearValidationFailure();
		else {
			setValidationFailureMessage(extantValidationFailures.values().iterator().next());
		}
	}

	public void remove(ControlHandlerCommands handler) {
		handlers.remove(handler);
	}
	
	public void add(ControlManagerCommands manager) {
		subManagers.add(manager);
		if (statusLineManager != null) {
			manager.enableStatusLineValidationMessages(statusLineManager);
		}
	}
	
	public void remove(ControlManagerCommands manager) {
		subManagers.remove(manager);
	}
	
	/**
	 * Repopulate all controls from data
	 */
	public void refresh() {
		if (inRefresh) {
			return;
		}
		try {
			inRefresh = true;
			for (ControlHandlerCommands handler : handlers) {
				handler.refresh();
			}
			for (ControlManagerCommands manager : subManagers) {
				manager.refresh();
			}
			updateValidationMessage();
		} finally {
			inRefresh = false;
		}
	}
	
	static class ValidationFailure {
			// control failing validation
		public Control control;
			// viewer for control, if any
		public StructuredViewer viewer;
			// validation failure details
		public IStatus status;
	}
	
	/**
	 * Validate all control settings. Stops at first failure
	 * @return null if successful, or ValidationFailure
	 */
	public ValidationFailure validate() {
		ValidationFailure result = null;
		for (ControlHandlerCommands handler : handlers) {
			IStatus status = handler.validate();
			if (status != null && !status.isOK()) {
				result = new ValidationFailure();
				result.control = handler.getControl();
				result.viewer = handler.getViewer();
				result.status = status;
				break;
			}
		}
		if (result == null) {
			for (ControlManagerCommands manager : subManagers) {
				ValidationFailure subResult = manager.validate();
				if (subResult != null) {
					result = subResult;
					break;
				}
			}
		}
		return result;
	}
	
	public void addValidationDependency(ControlHandlerCommands fromControl, ControlHandlerCommands toControl) {
		List<ControlHandlerCommands> deps = validationDependencies.get(fromControl);
		if (deps == null) {
			deps = new ArrayList<ControlHandlerCommands>();
			deps.add(toControl);
			validationDependencies.put(fromControl, deps);
		} else if (!deps.contains(toControl)) {
			deps.add(toControl);
		}
	}
}
