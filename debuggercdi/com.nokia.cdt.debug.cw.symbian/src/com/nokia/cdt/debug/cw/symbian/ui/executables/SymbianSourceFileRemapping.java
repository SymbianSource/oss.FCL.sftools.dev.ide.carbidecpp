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
package com.nokia.cdt.debug.cw.symbian.ui.executables;

import org.eclipse.cdt.debug.core.CDebugCorePlugin;
import org.eclipse.cdt.debug.core.executables.ISourceFileRemapping;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.sourcelookup.containers.LocalFileStorage;

import com.nokia.cdt.debug.cw.symbian.SymbianSourceContainer;

public class SymbianSourceFileRemapping implements ISourceFileRemapping {

	public String remapSourceFile(IPath executable, String filePath) {

		try {
			// handle the case where the common lookup finds the file in the workspace
			Object[] foundElements = CDebugCorePlugin.getDefault().getCommonSourceLookupDirector().findSourceElements(filePath);
			if (foundElements.length == 1 && foundElements[0] instanceof IFile) {
				IFile newLocation = (IFile) foundElements[0];
				return newLocation.getLocation().toOSString();
			}
		} catch (CoreException e1) {
		}
		
		String epocRoot = "";
		String[] segs = executable.segments();
		for (int i = 0; i < segs.length; i++) {
			if (segs[i].equalsIgnoreCase("epoc32"))
				epocRoot = executable.removeLastSegments(segs.length - i).toOSString();				
		}
		if (epocRoot.length() > 0)
		{
			SymbianSourceContainer symbianSourceContainer = new SymbianSourceContainer(new Path(epocRoot));
			try {
				Object[] foundElements = symbianSourceContainer.findSourceElements(filePath);
				if (foundElements.length == 1)
				{
					if (foundElements[0] instanceof LocalFileStorage)
					{
						LocalFileStorage newLocation = (LocalFileStorage) foundElements[0];
						filePath = newLocation.getFullPath().toOSString();
					}
					else
					if (foundElements[0] instanceof IResource)
					{
						IResource newResource = (IResource) foundElements[0];
						filePath = newResource.getLocation().toOSString();
					}
				}

			} catch (CoreException e) {}
		}
		
		return filePath;
	}

}
