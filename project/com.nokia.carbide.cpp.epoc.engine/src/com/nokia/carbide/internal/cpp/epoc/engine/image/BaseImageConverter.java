/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.image;

import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;

import java.io.File;


public abstract class BaseImageConverter {

	protected File getWorkspaceFile(IPath workspacePath) {
		if (Platform.isRunning())
			return ResourcesPlugin.getWorkspace().getRoot().getLocation().append(workspacePath).toFile();
		else
			return workspacePath.toFile();
	}
	
	String readFileText(IPath path) throws CoreException {
		File file = path.isAbsolute() ? path.toFile() : getWorkspaceFile(path);
		char[] text = FileUtils.readFileContents(file, null);
		return new String(text);
	}

	String[] readFileLines(IPath workspacePath) throws CoreException {
		return readFileText(workspacePath).split("\r\n|\r|\n"); //$NON-NLS-1$
	}
}
