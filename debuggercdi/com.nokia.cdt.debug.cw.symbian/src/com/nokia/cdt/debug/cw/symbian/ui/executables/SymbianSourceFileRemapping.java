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

import org.eclipse.cdt.debug.core.executables.Executable;
import org.eclipse.cdt.debug.core.executables.ISourceFileRemapping;
import org.eclipse.cdt.debug.core.sourcelookup.ICSourceLocator;
import org.eclipse.cdt.debug.internal.core.sourcelookup.CSourceLookupDirector;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ISourceLocator;
import org.eclipse.debug.core.sourcelookup.containers.LocalFileStorage;

import com.nokia.cdt.debug.cw.symbian.SymbianSourceContainer;

public class SymbianSourceFileRemapping implements ISourceFileRemapping {

	public String remapSourceFile(Executable executable, String filePath) {

		String epocRoot = "";
		String[] segs = executable.getPath().segments();
		for (int i = 0; i < segs.length; i++) {
			if (segs[i].equalsIgnoreCase("epoc32"))
				epocRoot = executable.getPath().removeLastSegments(segs.length - i).toOSString();				
		}
		if (epocRoot.length() > 0)
		{
			SymbianSourceContainer symbianSourceContainer = new SymbianSourceContainer(new Path(epocRoot));
			try {
				Object[] foundElements = symbianSourceContainer.findSourceElements(filePath);
				
				if (foundElements.length == 0)
				{
					Object foundElement = null;
					ILaunchManager launchMgr = DebugPlugin.getDefault().getLaunchManager();
					ILaunch[] launches = launchMgr.getLaunches();
					for (ILaunch launch : launches) {
						ISourceLocator locator = launch.getSourceLocator();
						if ( locator instanceof ICSourceLocator || locator instanceof CSourceLookupDirector ) {
							if ( locator instanceof ICSourceLocator )
								foundElement = ((ICSourceLocator)locator).findSourceElement( filePath );
							else
								foundElement = ((CSourceLookupDirector)locator).getSourceElement( filePath );
						}
					}
					if (foundElement != null)
						foundElements = new Object[] {foundElement};				
				}
				
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
