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
package com.nokia.cpp.internal.api.utils.ui.editor;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.widgets.Shell;

/**
 * Interface used with org.eclipse.core.commands.operations.IUndoableOperation. * 
 * We use this to allow the command to cooperate with restoring the editing context.
 * For example a multi-page editor may execute undo on a page different from the
 * page holding the modified item(s). Upon undo, the page should be changed so the
 * user can see what was undone. The command itself need not know that details, but
 * it does need to know to query the info parameter for an IEditingContext and invoke
 * the show method.
 * 
 * Implementations of this interface should also implement getAdapter to return a Shell,
 * as per the comments in IUndoableOperation.
 */
public interface IEditingContext {
	
	/**
	 * Show the UI context, e.g. page, visible gui controls, etc., appropriate
	 * to the command.
	 * @return IStatus indicating  success or failure.
	 */
	IStatus show();
	
	/**
	 * Return the most relevant shell for this context, e.g. for dialogs
	 */
	Shell getShell();
}