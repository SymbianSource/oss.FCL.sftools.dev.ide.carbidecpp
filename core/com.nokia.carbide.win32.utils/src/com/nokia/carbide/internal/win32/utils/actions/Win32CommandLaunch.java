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

/*
 * Created on Apr 13, 2005
 *
 */
package com.nokia.carbide.internal.win32.utils.actions;

import java.io.IOException;

import org.eclipse.cdt.core.CommandLauncher;
import org.eclipse.core.runtime.CoreException;

/**
 * Simplest class to execute a Win32 command.
 */
public class Win32CommandLaunch extends CommandLauncher {

	public Win32CommandLaunch(){
		super();
	}
	
	/**
	 *  execute a windows process
	 * @param String cmd - A windows command that can be executed.
	 * @return Process object
	 * @throws CoreException on error
	 */
	public Process ExecuteProcess(String cmd) throws CoreException
	{
		 try {
            fProcess=Runtime.getRuntime().exec(cmd);
        }
        catch (IOException e)
        {
            setErrorMessage(e.getMessage());
            return null;
        }
        return fProcess;
	}

}

