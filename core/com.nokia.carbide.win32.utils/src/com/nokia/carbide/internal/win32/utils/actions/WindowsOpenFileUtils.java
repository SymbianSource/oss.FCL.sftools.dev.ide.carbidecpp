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

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class WindowsOpenFileUtils extends ActionResourceAction implements IViewActionDelegate  {

	/**
	 * ProcessResourceTree default constructor.
	 */
	public WindowsOpenFileUtils() {
	}
	
	/**
	 * Not used in this action - implemented per
	 * <code>IViewActionDelegate</code> interface.
	 * 
	 * @see org.eclipse.ui.IViewActionDelegate#init(IViewPart)
	 */
	public void init(IViewPart view) {
	}
	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		
		// First get the folder name for the selected workspace resource.
		// Then our simple operations will work on the folder where the resource lives.
		Shell shell = new Shell();
		
		IResource res = (IResource) selection.getFirstElement();
		String pathList = ""; //$NON-NLS-1$
		pathList = pathList.concat(Messages.getString("WindowsOpenFileUtils.2") + res + "\n"); //$NON-NLS-1$ //$NON-NLS-2$

		traceEnabled = false;
		
		String folderFullPath = ""; //$NON-NLS-1$
		if (res instanceof IFolder || res instanceof IProject){
			folderFullPath = res.getLocation().toOSString();
		} else {
			folderFullPath = res.getLocation().removeLastSegments(1).toOSString();
		}
		
		//  figure out what action logic to run based on xml id
		if (action.getId().equals("openInWindowsExplorer")) //$NON-NLS-1$
		{
			String exeCmd = "explorer.exe "; //$NON-NLS-1$
			exeCmd += "\""; //$NON-NLS-1$
			exeCmd += folderFullPath;
			exeCmd += "\""; //$NON-NLS-1$
			
			try {
				Win32CommandLaunch myCmdLaunch = new Win32CommandLaunch();
				myCmdLaunch.ExecuteProcess(exeCmd);
			}
			catch (CoreException e)
			{
				MessageDialog.openInformation(
						shell,
						Messages.getString("WindowsOpenFileUtils.10"), //$NON-NLS-1$
						""); //$NON-NLS-1$
			}
		}
		else if (action.getId().equals("openInCmdPrompt")) //$NON-NLS-1$
		{	
			String exeCmd = "cmd.exe /c start cd /d "; //$NON-NLS-1$
			exeCmd += folderFullPath;
			try {
				Win32CommandLaunch myCmdLaunch = new Win32CommandLaunch();
				myCmdLaunch.ExecuteProcess(exeCmd);
			}
			catch (CoreException e)
			{
				MessageDialog.openInformation(
						shell,
						Messages.getString("WindowsOpenFileUtils.14"), //$NON-NLS-1$
						""); //$NON-NLS-1$
			}
		}
	}
}
