/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class OpenInCmdPromptHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection s = HandlerUtil.getActiveMenuSelection(event);
		if (s != null && s instanceof IStructuredSelection) {
			Object o = ((IStructuredSelection) s).getFirstElement();
			if (o instanceof IAdaptable) {
				IResource r = (IResource) ((IAdaptable) o).getAdapter(IResource.class);
				if (r != null)
					WindowsOpenFileUtils.executeAction(WindowsOpenFileUtils.OPEN_IN_COMMAND_PROMPT, r);
			}
		}
		return null;
	}

}
