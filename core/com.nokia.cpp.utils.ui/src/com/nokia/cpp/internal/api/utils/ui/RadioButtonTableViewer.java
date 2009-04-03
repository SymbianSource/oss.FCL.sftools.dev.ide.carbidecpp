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
package com.nokia.cpp.internal.api.utils.ui;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import com.nokia.cpp.internal.api.utils.core.ListenerList;

import java.util.*;
import java.util.Map.Entry;

/**
 * A TableViewer supporting embedded radio button controls.
 * The content provider should implement the IRadioButtonContentProvider
 * interface in order to tell the viewer which items have radio buttons
 * and which is the initially selected item.
 */
public class RadioButtonTableViewer extends TableViewer {
	
	class RadioItem {
		TableEditor editor;
		Button button;
	}
	
	/**
	 * Callback to notify clients when the currently
	 * selected item has changed.
	 */
	public interface IRadioStateListener {
		/** Called when current radio button changes.
		 * @param prevSelected previous selected item, or null
		 * @param newSelected new selected item, or null
		 */
		void radioStateChanged(Object prevSelected, Object newSelected);
	}
	
	public interface IRadioButtonContentProvider {
		/**
		 * Return the element whose radio button should be selected
		 */
		Object getSelectedElement();

		/**
		 * Return true if the element should have a radio button
		 */
		boolean hasRadioButton(Object element);
	}
	
	private int radioColumn = 0;
	private boolean forceSelectedItem = true;
	private Map<Object, RadioItem> itemMap = new HashMap<Object, RadioItem>();
	private ListenerList<IRadioStateListener> listeners = new ListenerList<IRadioStateListener>();
	private int style = 0;

	public RadioButtonTableViewer(Table table) {
		super(table);
	}

	public void addRadioStateListener(IRadioStateListener listener) {
		listeners.add(listener);
	}
	
	public void removeRadioStateListener(IRadioStateListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Set the column to hold radio buttons. Must be set
	 * before the viewer is initially populated.
	 * @param column the column index
	 */
	public void setRadioColumn(int column) {
		this.radioColumn = column;
	}
	
	/**
	 * When true, the viewer attempts to make
	 * @param force
	 */
	public void setForceSelectedIem(boolean force) {
		this.forceSelectedItem = force;
	}
	
	@Override
	protected void inputChanged(Object input, Object oldInput) {
		super.inputChanged(input, oldInput);
		IContentProvider cp = getContentProvider();
		if (cp instanceof IRadioButtonContentProvider) {
			IRadioButtonContentProvider rbcp = (IRadioButtonContentProvider) cp;
			Object selectedElement = rbcp.getSelectedElement();
			if (selectedElement != null) {
				setSelectedItem(selectedElement);
			}
			else if (forceSelectedItem) {
				forceSelectedItem();
			}
		}
	}

	private void fireRadioStateChanged(Object oldSelected, Object newSelected) {
		for (IRadioStateListener l : listeners) {
			l.radioStateChanged(oldSelected, newSelected);
		}
	}
	
	public void setSelectedItem(Object item) {
		RadioItem ri = itemMap.get(item);
		if (ri != null) {
			for (RadioItem currRI : itemMap.values()) {
				currRI.button.setSelection(currRI == ri);
			}
		}
	}
	
	public Object getSelectedItem() {
		Object result = null;
		for (Iterator<Entry<Object, RadioItem>> iter = itemMap.entrySet().iterator(); iter.hasNext();) {
			Entry<Object, RadioItem> entry = iter.next();
			if (entry.getValue().button.getSelection()) {
				result = entry.getKey();
				break;
			}
		}
		return result;
	}
	
	private void updateSelectedRadio(Button changedButton, boolean isSelected) {
		Object oldSelected = null;
		Object newSelected = null;
		// ensure single item only is selected
		if (isSelected) {
			for (Iterator<Entry<Object, RadioItem>> iter = itemMap.entrySet().iterator(); iter.hasNext();) {
				Entry<Object, RadioItem> entry = iter.next();
				Button currBtn = entry.getValue().button;
				if (currBtn.getSelection()) {
					if (currBtn == changedButton) {
						newSelected = entry.getKey();
					} else {
						oldSelected = entry.getKey();
						currBtn.setSelection(false);
					}
				}
			}	
			
			fireRadioStateChanged(oldSelected, newSelected);
		}
		// ensure some item is selected, if possible
		else if (forceSelectedItem) {
			forceSelectedItem();
		}
	}
	
	public void forceSelectedItem() {
		if (getSelectedItem() == null) {
			Object newSelected = null;
			// selected first eligible item, from top down
			for (int i = 0; i < getTable().getItemCount(); i++) {
				Object curr = getElementAt(i);
				RadioItem ri = itemMap.get(curr);
				if (ri != null) {
					ri.button.setSelection(true);
					newSelected = curr;
					break;
				}
			}
			if (newSelected != null) {
				fireRadioStateChanged(null, newSelected);
			}	
		}
	}
	
	/**
	 * Set additional style bits for radio button appearance.
	 * Most likely style added may be FLAT when in a non-3D view.
	 * @param style int
	 * @see SWT#FLAT
	 */
	public void setStyle(int style) {
		this.style  = style;
	}

	/**
	 * We override associate to maintain each items optional
	 * radio button information.
	 */
	@Override
	protected void associate(Object element, Item item) {
		super.associate(element, item);
		IContentProvider contentProvider = getContentProvider();
		if (contentProvider instanceof IRadioButtonContentProvider) {
			Table table = getTable();
			IRadioButtonContentProvider rbcp = (IRadioButtonContentProvider) contentProvider;
			RadioItem ri = itemMap.get(element);
			if (rbcp.hasRadioButton(element)) {
				if (ri == null) {
					ri = new RadioItem();
					itemMap.put(element, ri);
					ri.editor = new TableEditor(table);
					ri.button = new Button(table, SWT.RADIO | style);
					ri.button.pack();
					ri.button.setBackground(table.getBackground());
					ri.editor.grabHorizontal = true;
					ri.editor.setEditor(ri.button, (TableItem)item, radioColumn);
					ri.editor.minimumWidth = ri.button.getSize().x;
					ri.editor.horizontalAlignment = SWT.LEFT;
					
					ri.button.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							Button btn = (Button) e.getSource();
							updateSelectedRadio(btn, btn.getSelection());
						}
					});
				}
			}
			else if (ri != null) {
				ri.editor.dispose();
				ri.button.dispose();
				itemMap.remove(element);
			}
		}
	}
	
	/**
	 * We override disassociate to clean up any
	 * allocated radio button information and to 
	 * help implement force selection.
	 */
	@Override
	protected void disassociate(Item item) {
		boolean wasSelected = false;
		Object element = item.getData();
		RadioItem ri = itemMap.get(element);
		if (ri != null) {
			wasSelected = ri.button.getSelection();
			ri.editor.dispose();
			ri.button.dispose();
			itemMap.remove(element);
		}
		super.disassociate(item);
		
		if (wasSelected && forceSelectedItem) {
			Display.getCurrent().asyncExec(new Runnable() {
				public void run() {
					forceSelectedItem();
				}
			});
		}
	}
}
