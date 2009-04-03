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
/* START_USECASES: CU6 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.vieweditor;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;

/**
 * This class listen the Drag event from the TreeViewer
 * and validates the type of data to be transfered (AG, NG and Commands).
 * AG = Anonymous Group
 * NG = Named Group
 *
 */
public class CommandElementDragListener extends DragSourceAdapter {
	private StructuredViewer viewer;
	
	/**
	 * Class constructor.
	 * @param viewer
	 */
	public CommandElementDragListener(StructuredViewer viewer) {
	      this.viewer = viewer;
	}
	/**
	 * Method declared on DragSourceListener
	 */
	public void dragFinished(DragSourceEvent event) {
	    if (!event.doit)
	      return;
	    viewer.refresh();
	}
	/**
	 * Method declared on DragSourceListener
	 */
	public void dragSetData(DragSourceEvent event) {
	   IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
	   Object[] elements = (Object[])selection.toList().toArray(new Object[selection.size()]);
	   if (CommandElementTransfer.getInstance().isSupportedType(event.dataType)) {
	      event.data = elements;
	   } 
	}
	/**
	 * Method declared on DragSourceListener
	 */
	public void dragStart(DragSourceEvent event) {
		IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
		Object[] elements = (Object[])selection.toList().toArray(new Object[selection.size()]);
		if (elements[0] instanceof CommandList){
		   event.detail = DND.DROP_NONE;
		   event.doit = false;
		   return;
		}
		event.doit = !viewer.getSelection().isEmpty();
	}
}
