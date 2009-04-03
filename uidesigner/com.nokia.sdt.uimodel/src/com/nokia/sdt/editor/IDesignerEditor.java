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
package com.nokia.sdt.editor;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.displaymodel.IDisplayModel;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.*;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Control;

import java.util.List;

public interface IDesignerEditor extends IDesignerDataModelEditorPage {
	
	public static final String VIEW_EDITOR_ID = "com.nokia.sdt.uidesigner.viewEditor"; //$NON-NLS-1$
	
	public interface TransientListener {
		void enteredTransientMode();
		void prepareToExitTransientMode();
		void exitingTransientMode();
	}
	
	public interface IKeyEventProvider {
		void addKeyListener(KeyListener listener);
		void removeKeyListener(KeyListener listener);
		List getListeners();
	}
	
	Object getModelObject();

	IResource getDataModelResource();
	
	KeyHandler getCommonKeyHandler();
	
	GraphicalViewer getUpperGraphicalViewer();
	
	GraphicalViewer getLowerGraphicalViewer();
	
	EditPartViewer getOutlineViewer();
	
	ActionRegistry getActionRegistry();

	ISelectionManager getSelectionManager();
	
	DefaultEditDomain getEditDomain();
	
	void updateSelectionActions();
	
	void setTransientMode(Object transientRootObject);
	
	Object getTransientRootObject();
	
	void setLayoutMode();
	
	boolean isTransientMode();
	
	boolean isComponentEditing();
	
	IFigure getLayoutContentsFigure();
	
	IFigure getTransientLayerRootFigure();
	
	void addTransientListener(TransientListener listener);
	
	void removeTransientListener(TransientListener listener);
	
	Object getNonLayoutRoot();
	
	void setKeyEventProvider(IKeyEventProvider provider);
	
	IKeyEventProvider getKeyEventProvider();

	void updatePalette(boolean force);
	
	Object getAdapter(Class type);

	void refreshFromModel();

	IDisplayModel getDisplayModel();

	IDesignerDataModel getDataModel();
	
	void prepareForReload();
	
	void initDataModel() throws CoreException;
	
	Control getControl();
}