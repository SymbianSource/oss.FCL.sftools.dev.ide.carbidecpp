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
package com.nokia.carbide.internal.win32.utils.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.nokia.carbide.internal.win32.utils.Win32UtilsPlugin;


/**
 * ActionResourceAction class - Allows an action to be received on a resource
 */
public abstract class ActionResourceAction implements
	IObjectActionDelegate 
{
	 /**
	   * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	   */
	  public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	  }

	  /** Holds selected project resource for run method access */
	  public IStructuredSelection selection;

	  /** Controls if trace stmts are written */
	  boolean traceEnabled = false;

	  /**
	   * Used to save a local handle to the selected project resource.
	   * 
	   * @see org.eclipse.ui.IActionDelegate#selectionChanged(IAction, ISelection)
	   *
	   * @param action action proxy that handles presentation
	   * portion of the plugin action
	   * @param selection current selection in the desktop
	   */
	   public void selectionChanged(IAction action, ISelection selection) {
	   this.selection = (IStructuredSelection) selection;

	  }

	  /**
	   * Uses a <code>MessageDialog</code> to show action results.
	   *  
	   * @param title
	   * @param msg
	   */
	  protected void resultInformation(String title, String msg) {
	    // Confirm Result
	    Shell shell = Win32UtilsPlugin.getDefault().getWorkbench()
	        .getActiveWorkbenchWindow().getShell();
	    MessageDialog.openInformation(shell, title, msg);
	  }

	  /**
	   * Uses a <code>MessageDialog</code> to show errors in action processing.
	   * 
	   * @param title
	   * @param msg
	   */
	  protected void resultError(String title, String msg) {
	    // Indicate Error
	    Shell shell = Win32UtilsPlugin.getDefault().getWorkbench()
	        .getActiveWorkbenchWindow().getShell();
	    MessageDialog.openError(shell, title, msg);
	  }
	}


