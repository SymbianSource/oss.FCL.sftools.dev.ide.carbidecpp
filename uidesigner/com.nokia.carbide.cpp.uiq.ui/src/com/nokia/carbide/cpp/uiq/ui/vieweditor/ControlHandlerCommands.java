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
import com.nokia.cpp.internal.api.utils.core.ListenerList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.*;

import java.util.*;
import java.util.List;

/**
 * Base class of framework providing these services:
 * - populate control/viewer from data model (in subclasses that are model-aware)
 * - respond to control/viewer events by creating undoable operations (in subclasses that are model-aware)
 * - utilities for populating control/viewer values without triggering undo stack modifications
 * - validation that's simpler than SWT verify events
 * 
 * The handler is attached to the control's data map. Given a Control the handler can
 * be retrieved view getHandlerForControl. Given a Viewe the handler can be retrieved via
 * getHandlerForViewer.
 */
public class ControlHandlerCommands {
	
	public interface IValidator {
		/**
		 * Return null or OK status if control state is valid
		 */
		IStatus validate(Control control);
	}
	
	private final Control control;
		// optional viewer associated with the control
	private final StructuredViewer viewer;
	private final IValidator validator;
	private final Listener swtListener;
	private final boolean viewerCaseSensitive;
	// optional label associated with the control
	private Control label;
	private boolean updatingControl;
	private ICheckStateListener checkStateListener;
	private ListenerList<ControlHandlerListener> listeners = new ListenerList<ControlHandlerListener>();
	private String listDelimiter = "\\ "; //$NON-NLS-1$
	
	static final String DATA_KEY = ControlHandlerCommands.class.getPackage() + "." + ControlHandlerCommands.class.getName(); //$NON-NLS-1$
	
	interface ControlHandlerListener {
		void valueModified(Control control);
		void controlSelected(Control control);
		void validationFailed(Control control, String message);
		void validationSucceeded(Control control);
	}
	
	public static class ControlHandlerAdapter implements ControlHandlerListener {
		public void valueModified(Control control) {
		}
		public void controlSelected(Control control) {
		}
		public void validationFailed(Control control, String message) {
		}
		public void validationSucceeded(Control control) {
		}
	}
	
	public ControlHandlerCommands(Control control, IValidator validator) {
		this(control, null, validator, true);
	}
	
	/**
	 * Initialize to use a structured view to interact with the control
	 * @param viewer the viewer
	 * @param caseSensitive true if when setting the value to match with case
	 * insensitive comparisons
	 */
	public ControlHandlerCommands(StructuredViewer viewer, boolean caseSensitive) {
		this(viewer.getControl(), viewer, null, caseSensitive);
	}
	
	protected ControlHandlerCommands(final Control control, final StructuredViewer viewer,
			IValidator validator, boolean caseSensitive) {
		Check.checkState(getHandlerForControl(control) == null);
		this.control = control;
		this.viewer = viewer;
		this.validator = validator;
		this.viewerCaseSensitive = caseSensitive;
		
		swtListener = new Listener() {
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.Modify:
					if (!updatingControl) {
						textModified();
					}
					break;
				case SWT.Selection:
					if (!updatingControl) {
						controlSelected();
					}
					break;
				case SWT.Dispose:
					control.setData(DATA_KEY, null);
					break;
				case SWT.FocusIn:
					controlFocused();
					break;
				}
			}			
		};
		control.addListener(SWT.Modify, swtListener);
		control.addListener(SWT.Selection, swtListener);
		control.addListener(SWT.Dispose, swtListener);
		control.addListener(SWT.FocusIn, swtListener);
		
		if (viewer instanceof ICheckable) {
			checkStateListener = new ICheckStateListener() {
				public void checkStateChanged(CheckStateChangedEvent event) {
					ControlHandlerCommands.this.checkStateChanged(event.getElement(), event.getChecked());
				}
			};
			ICheckable checkable = (ICheckable) viewer;
			checkable.addCheckStateListener(checkStateListener);
		}
		
		control.setData(DATA_KEY, this);
		Check.checkState(getHandlerForControl(control) == this);
	}
	
	public Control getControl() {
		return control;
	}
	
	public StructuredViewer getViewer() {
		return viewer;
	}
	
	public void setLabel(Control label) {
		this.label = label;
	}
	
	public Control getLabel() {
		return label;
	}
	
	public static ControlHandlerCommands getHandlerForControl(Control control) {
		ControlHandlerCommands result = null;
		if (control != null) {
			Object obj = control.getData(DATA_KEY);
			if (obj instanceof ControlHandlerCommands) {
				result = (ControlHandlerCommands) obj;
			}
		}
		return result;
	}
	
	public static ControlHandlerCommands getHandlerForViewer(StructuredViewer viewer) {
		return getHandlerForControl(viewer.getControl());
	}
	
	protected void textModified() {
		fireTextModified();
		validate();
	}
	
	protected void controlSelected() {
		fireControlSelected();
	}
	
	protected void controlFocused() {
		validate();
	}
	
	/**
	 * Utility to pull the text of of various kinds of Control.
	 */
	public static String getControlText(Control control) {
		String result = null;
		Object value = getControlValue(control);
		if (value instanceof List) {
			ControlHandlerCommands handler = getHandlerForControl(control);
			if (handler != null) {
				result = handler.listToText((List)value);
			}
		} else if (value != null) {
			result = value.toString();
		}
		return result;
	}
	
	public static Object getControlValue(Control control) {
		Object result = null;
		ControlHandlerCommands handler = getHandlerForControl(control);
		if (handler != null && handler.viewer != null) {
			ISelection selection = handler.viewer.getSelection();
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection ss = (IStructuredSelection) selection;
				List list = ss.toList();
				if (list.size() == 1) {
					result = list.get(0);
				} else {
					result = list;
				}
			}
		}
		else if (control instanceof Text) {
			result = ((Text)control).getText();
		} else if (control instanceof CCombo) {
			result = ((CCombo)control).getText();
		} else if (control instanceof Button) {
			result = ((Button)control).getSelection();
		}
		return result;
	}
	
	/**
	 * Match the incoming string to the casing in the viewer data
	 */
	private static String matchViewerStringCase(StructuredViewer viewer, String value) {
		String result = value;
		IContentProvider contentProvider = viewer.getContentProvider();
		if (contentProvider instanceof IStructuredContentProvider) {
			IStructuredContentProvider scp = (IStructuredContentProvider) contentProvider;
			Object[] elements = scp.getElements(viewer.getInput());
			for (Object element : elements) {
				String strElement = element.toString();
				if (value.equalsIgnoreCase(strElement)) {
					result = strElement;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * Utility to set the text in various kinds of Control
	 */
	public static void setControlText(Control control, String value) {
		if (value == null) value = ""; //$NON-NLS-1$
		ControlHandlerCommands handler = getHandlerForControl(control);
		if (handler != null && handler.viewer != null) {
			if (!handler.viewerCaseSensitive) {
				value = matchViewerStringCase(handler.viewer, value);
			}
			IStructuredSelection ss = new StructuredSelection(value);
			handler.viewer.setSelection(ss, true);
		}
		else if (control instanceof Text) {
			Text t = (Text) control;
			t.setText(value);
			t.setSelection(value.length());
		} else if (control instanceof Combo) {
			Combo c = (Combo) control;
			c.setText(value);
		}
	}
	
	public IStatus validate() {
		IStatus result = Status.OK_STATUS;
		if (validator != null) {
			result = validator.validate(control);
			if (result == null || result.isOK()) {
				clearLabelModifications();
				// this is needed so UI can clear out error indications
				fireValidationSucceeded();
			} else {
				if (label != null) {
					label.setForeground(JFaceColors.getErrorText(control.getDisplay()));
				}
				fireValidationFailed(result.getMessage());
			}
		}
		return result;
	}
	
	public void clearLabelModifications() {
		if (label != null) {
			label.setForeground(null);
		}
	}
	
	protected void whileUpdatingControl(Runnable r) {
		boolean savedUpdating = updatingControl;
		updatingControl = true;
		try {
			r.run();
		} finally {
			updatingControl = savedUpdating;
		}
	}
	
	/**
	 * Set the control's text without modifying the operation stack
	 * @param value
	 */
	public void storeText(final String value) {
		whileUpdatingControl(new Runnable() {
			public void run() {
				setControlText(control, value);
				validate();
			}
		});
	}
	
	public void setValue(Object valueParam) {
		final Object value = valueParam != null? valueParam : ""; //$NON-NLS-1$
		whileUpdatingControl(new Runnable() {
			public void run() {
				if (viewer != null) {
					if (value instanceof List) {
						IStructuredSelection ss = new StructuredSelection((List)value);
						viewer.setSelection(ss, true);
					} else if (value instanceof Object[]) {
						IStructuredSelection ss = new StructuredSelection((Object[])value);
						viewer.setSelection(ss, true);
					} else {
						IStructuredSelection ss = new StructuredSelection(value);
						viewer.setSelection(ss, true);
					}
				} else {
					if (control instanceof Button) {
						Button btn = (Button) control;
						btn.setSelection(Boolean.valueOf(value.toString()));
					} else {
						if (value instanceof List) {
							setControlText(control, listToText((List) value));
						} else {
							setControlText(control, value.toString());
						}
					}
				}
				validate();
			}
		});
	}
	
	/**
	 * Hook to translate viewer element objects to underlying model
	 * objects. Default version returns the input umodified
	 */
	public List<Object> viewerToModelElements(List<Object> elements) {
		return elements;
	}
	
	/**
	 * Hook to translate model objects to viewer objects. Default
	 * version returns the input unmodified
	 */
	public List<Object> modelToViewerElements(List<Object> elements) {
		return elements;
	}
	
	/**
	 * Set the input on a viewer without modifying the operation stack
	 */
	public void setViewerInput(final Object input) {
		Check.checkState(viewer != null);
		whileUpdatingControl(new Runnable() {
			public void run() {
				viewer.setInput(input);
				validate();
			}
		});
	}
	
	protected void checkStateChanged(Object element, boolean checked) {
		
	}
	
	public void addListItems(List items) {
		boolean savedUpdating = updatingControl;
		updatingControl = true;
		try {
			if (viewer != null) {
				refresh();
			} else {
				StringBuffer buf = new StringBuffer(getControlText(control));
				if (buf.length() > 0) {
					buf.append(listDelimiter);
				}
				buf.append(listToText(items));
				storeText(buf.toString());
			}
		} finally {
			updatingControl = savedUpdating;
		}
	}
	
	/**
	 * Do anything needed to update the control from the model.
	 */
	public void refresh() {
		boolean savedUpdating = updatingControl;
		updatingControl = true;
		try {
			clearLabelModifications();
			doRefresh();
		} finally {
			updatingControl = savedUpdating;
		}
	}
	
	/**
	 * Subclasses that cannot relying on simple Viewer refreshing
	 * must override this to populate the control value.
	 */
	protected void doRefresh() {
		if (viewer != null) {
			viewer.refresh();
		}
	}
	
	public void setListDelimiter(String delimiter) {
		this.listDelimiter = delimiter;
	}
	
	public void removeListItems(List items) {
		boolean savedUpdating = updatingControl;
		updatingControl = true;
		try {
			if (viewer != null) {
				refresh();
			} else {
				String[] strings = splitText();
				ArrayList<String> list = new ArrayList<String>();
				for (String str : strings) {
					list.add(str);
				}
				list.removeAll(items);
				storeText(listToText(list));
			}
		} finally {
			updatingControl = savedUpdating;
		}
	}
	
	public String[] splitText() {
		String text = getControlText(control);
		String[] strings = text.split(listDelimiter);
		return strings;
	}
	
	public String listToText(List items) {
		StringBuffer result = new StringBuffer();
		if (items != null) {
			for (Object object : items) {
				if (result.length() > 0) {
					result.append(listDelimiter);
				}
				result.append(object);
			}
		}
		return result.toString();
	}
	
	public void addListener(ControlHandlerListener l) {
		listeners.add(l);
	}
	
	public void removeListener(ControlHandlerListener l) {
		listeners.remove(l);
	}
	
	protected void fireTextModified() {
		for (ControlHandlerListener l : listeners) {
			l.valueModified(control);
		}
	}
	
	protected void fireControlSelected() {
		for (ControlHandlerListener l : listeners) {
			l.controlSelected(control);
		}
	}

	protected void fireValidationFailed(String message) {
		for (ControlHandlerListener l : listeners) {
			l.validationFailed(control, message);
		}
	}

	protected void fireValidationSucceeded() {
		for (ControlHandlerListener l : listeners) {
			l.validationSucceeded(control);
		}
	}
}
