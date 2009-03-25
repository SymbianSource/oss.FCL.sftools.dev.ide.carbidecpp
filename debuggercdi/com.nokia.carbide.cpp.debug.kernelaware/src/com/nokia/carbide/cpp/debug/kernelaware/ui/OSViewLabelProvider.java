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
* The base label provider for the Symbian OS view. This label provider provides
* font and color for labels of items in Symbian OS view. Different tabs in the
* OS View may want to extend it when needed.
* 
*/
package com.nokia.carbide.cpp.debug.kernelaware.ui;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

import com.nokia.carbide.cpp.debug.kernelaware.OSDataManager;
import com.nokia.carbide.cpp.debug.kernelaware.OSObjectProcess;
import com.nokia.carbide.cpp.debug.kernelaware.OSObjectThread;

public class OSViewLabelProvider extends LabelProvider implements
		IFontProvider, IColorProvider {

	// This is the viewer we are servicing.
	private Viewer viewer;

	OSViewLabelProvider(Viewer viewer) {
		this.viewer = viewer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IFontProvider#getFont(java.lang.Object)
	 */
	public Font getFont(Object element) {
		if (beingDebugged(element))
			return JFaceResources.getFontRegistry().getBold(
					viewer.getControl().getFont().toString()); // JFaceResources.DIALOG_FONT

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
	 */
	public Color getBackground(Object element) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
	 */
	public Color getForeground(Object element) {
		return null;
	}

	private boolean beingDebugged(Object element) {
		/*
		 * For a process or thread being debugged, display it in bold font in
		 * our view.
		 */
		OSDataManager osDM = (OSDataManager) viewer.getInput();
		if (osDM == null)
			return false;

		boolean beingDebugged = false;
		if (element instanceof OSObjectProcess) {
			OSObjectProcess proc = (OSObjectProcess) element;
			beingDebugged = osDM.getSession()
					.processBeingDebugged(proc.getID());
		} else if (element instanceof OSObjectThread) {
			OSObjectThread thread = (OSObjectThread) element;
			
			beingDebugged = osDM.getSession().threadBeingDebugged(thread.getProcessID(),
					thread.getID());
		}

		return beingDebugged;
	}
}
