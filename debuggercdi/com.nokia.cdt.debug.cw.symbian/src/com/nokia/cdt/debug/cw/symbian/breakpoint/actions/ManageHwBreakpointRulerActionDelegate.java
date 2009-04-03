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
package com.nokia.cdt.debug.cw.symbian.breakpoint.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.texteditor.AbstractRulerActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;

public class ManageHwBreakpointRulerActionDelegate extends
	AbstractRulerActionDelegate {
	
	private ToggleHwBreakpointRulerAction fTargetAction;
	private IEditorPart fActiveEditor;
	private IAction fAction;
  
	/* (non-Javadoc)
	 * @see org.eclipse.ui.texteditor.AbstractRulerActionDelegate#createAction(ITextEditor, IVerticalRulerInfo)
	 */
	public IAction createAction( ITextEditor editor, IVerticalRulerInfo rulerInfo ) {
		fTargetAction = new ToggleHwBreakpointRulerAction( editor, rulerInfo );
		return fTargetAction;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor( IAction callerAction, IEditorPart targetEditor ) {
		if ( fActiveEditor != null ) {
			if ( fTargetAction != null ) {
				fTargetAction.dispose();
				fTargetAction = null;
			}
		}
		fActiveEditor = targetEditor;
		super.setActiveEditor( callerAction, targetEditor );
	}
	
	public void selectionChanged(IAction action, ISelection selection) {		
		this.fAction = action;
		super.selectionChanged(action, selection);		
	}
}
