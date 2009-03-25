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

import com.nokia.cpp.internal.api.utils.core.ObjectUtils;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * TreeViewer's support of in-place cell editing 
 * is a little immature and uses private classes
 * that we can't override. This derived class
 * is for code to make it work.
 * 
 *
 */
public class EventTreeViewer extends TreeViewer {

	public EventTreeViewer(Composite parent, int style) {
		super(parent, style);
	}
	
	public void setSelection(ISelection selection, boolean reveal) {
		// don't cause spurious events selecting things that are already
		// selected
		ISelection curr = this.getSelection();
		if (!ObjectUtils.equals(selection, curr)) {
			super.setSelection(selection, reveal);
		}
	}
}
