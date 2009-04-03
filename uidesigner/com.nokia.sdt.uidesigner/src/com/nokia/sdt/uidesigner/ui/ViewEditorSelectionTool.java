/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.uidesigner.ui;

import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.editor.IDesignerEditor.IKeyEventProvider;
import com.nokia.sdt.preferences.PreferenceConstants;
import com.nokia.sdt.uidesigner.events.BindDefaultEventAction;
import com.nokia.sdt.uidesigner.ui.editparts.ModelObjectEditPart;
import com.nokia.sdt.uimodel.UIModelPlugin;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.SelectionTool;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;

import java.util.*;

/**
 * 
 * 
 * Handles non-layout object direct editing from typing when a single non-layout object is selected
 */
public class ViewEditorSelectionTool extends SelectionTool implements IKeyEventProvider {

    private List keyListeners;
    private IDesignerEditor editor;
    
	public ViewEditorSelectionTool(IDesignerEditor editor) {
		this.editor = editor;
		editor.setKeyEventProvider(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.SelectionTool#handleKeyDown(org.eclipse.swt.events.KeyEvent)
	 */
	protected boolean handleKeyDown(KeyEvent e) {
		boolean handled = fireKeyPressed(e);
		
		if (!handled)
			handled = super.handleKeyDown(e);
		
		if (!handled) {
			List list = getCurrentViewer().getSelectedEditParts();
			if (list.size() == 1) {
				Object obj = list.get(0);
				if (obj instanceof ModelObjectEditPart) {
					ModelObjectEditPart editPart = (ModelObjectEditPart) obj;
					if (Character.isLetterOrDigit(e.character)) {
						DirectEditRequest request = new DirectEditRequest();
						request.setLocation(new Point(-1, -1));
						request.getExtendedData().put(ModelObjectEditPart.INITIAL_KEY_EVENT, e);
						editPart.performRequest(request);
						handled = true;
					}
					else if (e.keyCode == SWT.F2) {
						DirectEditRequest request = new DirectEditRequest();
						request.setLocation(new Point(-1, -1));
						editPart.performRequest(request);
						handled = true;
					}
					else if (e.keyCode == SWT.F3) {
				        ActionRegistry actionRegistry = editor.getActionRegistry();
						IAction action = actionRegistry.getAction(BindDefaultEventAction.ID);
				        if (action.isEnabled())
				        	action.run();
					}
				}
			}
		}
		if (e.character == SWT.ESC)
			editor.setLayoutMode();
		
		return handled;
	}
    
	protected boolean handleKeyUp(KeyEvent e) {
		boolean handled = fireKeyReleased(e);
		
		if (!handled)
			super.handleKeyUp(e);
		
		return handled;
	}

	public void mouseDoubleClick(MouseEvent e, EditPartViewer viewer) {
        super.mouseDoubleClick(e, viewer);
		String value = 
			UIModelPlugin.getDefault().getPreferenceStore().getString(
					PreferenceConstants.P_DOUBLE_CLICK_BEHAVIOR);
		if (value.equals(PreferenceConstants.EDIT_LABEL)) {
			List list = viewer.getSelectedEditParts();
			if (list.size() == 1) {
				Object obj = list.get(0);
				if (obj instanceof ModelObjectEditPart) {
					ModelObjectEditPart editPart = (ModelObjectEditPart) obj;
					DirectEditRequest request = new DirectEditRequest();
					request.setLocation(new Point(e.x, e.y));
					editPart.performRequest(request);
				}
			}
		}
		else { // handle event
	        ActionRegistry actionRegistry = editor.getActionRegistry();
			IAction action = actionRegistry.getAction(BindDefaultEventAction.ID);
	        if (action.isEnabled())
	        	action.run();
        }
    }
    
	public void addKeyListener(KeyListener keyListener) {
		if (keyListeners == null)
			keyListeners = new ArrayList();
		
		keyListeners.add(0, keyListener); // add to the beginning
	}

	public void removeKeyListener(KeyListener keyListener) {
		if (keyListeners == null)
			return;
		
		keyListeners.remove(keyListener);
	}

    private boolean fireKeyPressed(KeyEvent e) {
		if (keyListeners == null)
			return false;
		
		for (Iterator iter = (new ArrayList(keyListeners)).iterator(); iter.hasNext();) {
			KeyListener listener = (KeyListener) iter.next();
			if (e.doit)
				listener.keyPressed(e);
			else
				break;
		}
		
		return !e.doit;
	}

	private boolean fireKeyReleased(KeyEvent e) {
		if (keyListeners == null)
			return false;
		
		for (Iterator iter = (new ArrayList(keyListeners)).iterator(); iter.hasNext();) {
			KeyListener listener = (KeyListener) iter.next();
			if (e.doit)
				listener.keyReleased(e);
			else
				break;
		}
		
		return !e.doit;
	}

	public List getListeners() {
		return keyListeners;
	}
}
