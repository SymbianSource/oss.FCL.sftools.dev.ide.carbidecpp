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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.nokia.sdt.uidesigner.ui.editparts.ContentsLayoutEditPart;
import com.nokia.sdt.uidesigner.ui.figure.LayoutContentsFigure;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 * This is the object that GEF considers the root model object.
 * The root container(s) hang off this object when the editor is open.
 */
public class ContentsObject {
	private ContentsLayoutEditPart editPart;
	private List rootContainers = new ArrayList();
	private List listeners = new ArrayList();
	
	public interface RootContainerAddedListener {
		public void rootContainerAdded(EObject root);
	}

	public ContentsObject() {
	}
	
	public void addRootContainer(EObject rootContainer) {
		Check.checkArg(rootContainer);
		this.rootContainers.add(rootContainer);
		fireRootContainerAdded(rootContainer);
	}
	
	public void setRootContainer(EObject rootContainer) {
		rootContainers.clear();
		listeners.clear();
		addRootContainer(rootContainer);
	}
	
	private void fireRootContainerAdded(EObject root) {
	    for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			RootContainerAddedListener listener = (RootContainerAddedListener) iter.next();
			listener.rootContainerAdded(root);
		}
	}
	
	public void addListener(RootContainerAddedListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(RootContainerAddedListener listener) {
		listeners.remove(listener);
	}

	public LayoutContentsFigure getLayoutContentsFigure() {
		return (LayoutContentsFigure) editPart.getFigure();
	}

	public void setEditPart(ContentsLayoutEditPart editPart) {
		Check.checkArg(editPart);
		this.editPart = editPart;
	}

	public List getRootContainers() {
		return rootContainers;
	}
}
