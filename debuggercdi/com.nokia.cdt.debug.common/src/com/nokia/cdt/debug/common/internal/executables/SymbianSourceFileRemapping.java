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
package com.nokia.cdt.debug.common.internal.executables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.eclipse.cdt.debug.core.CDebugCorePlugin;
import org.eclipse.cdt.debug.core.executables.ISourceFileRemapping;
import org.eclipse.cdt.debug.core.sourcelookup.MappingSourceContainer;
import org.eclipse.cdt.debug.internal.core.sourcelookup.CSourceLookupDirector;
import org.eclipse.cdt.debug.internal.core.sourcelookup.MapEntrySourceContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.containers.LocalFileStorage;

import com.nokia.cdt.debug.common.internal.source.lookup.SymbianSourceContainer;

public class SymbianSourceFileRemapping implements ISourceFileRemapping {

	public final String foundMappingsContainerName = "Found Mappings"; //$NON-NLS-1$
	
	private Job saveThePrefsJob = new Job(""){

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			System.out.println("save prefs");
			CDebugCorePlugin.getDefault().savePluginPreferences();
			return Status.OK_STATUS;
		}};

	@SuppressWarnings("restriction")
	private void addSourceMappingToDirector(IPath missingPath, IPath newSourcePath, AbstractSourceLookupDirector director) throws CoreException {

		ArrayList<ISourceContainer> containerList = new ArrayList<ISourceContainer>(Arrays.asList(director.getSourceContainers()));

		boolean hasFoundMappings = false;

		MappingSourceContainer foundMappings = null;
		
		for (Iterator<ISourceContainer> iter = containerList.iterator(); iter.hasNext() && !hasFoundMappings;) {
			ISourceContainer container = (ISourceContainer) iter.next();
			if (container instanceof MappingSourceContainer)
			{
				hasFoundMappings = container.getName().equals(foundMappingsContainerName);
				if (hasFoundMappings)
					foundMappings = (MappingSourceContainer) container;
			}
		}

		if (!hasFoundMappings) {
			foundMappings = new MappingSourceContainer(foundMappingsContainerName);
			foundMappings.init(director);
			containerList.add(foundMappings);
			director.setSourceContainers((ISourceContainer[]) containerList.toArray(new ISourceContainer[containerList.size()]));
		}
		
		foundMappings.addMapEntry(new MapEntrySourceContainer(missingPath, newSourcePath));
	}

	@SuppressWarnings("restriction")
	private void addSourceMappingToCommon(IPath missingPath, IPath newSourcePath) throws CoreException {
		CSourceLookupDirector director = CDebugCorePlugin.getDefault().getCommonSourceLookupDirector();
		addSourceMappingToDirector(missingPath, newSourcePath, director);
		// We need to save the prefs to make sure this is persistent. But if we do it immediately
		// here it will be slow and redundant if we are being used to remap a large collection
		// of source files. So we save the prefs later in a job.
		saveThePrefsJob.cancel();
		saveThePrefsJob.schedule(1000);
	}

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
					IPath missingPath = new Path(filePath);
					IPath foundPath = null;
					if (foundElements[0] instanceof LocalFileStorage)
					{
						LocalFileStorage newLocation = (LocalFileStorage) foundElements[0];
						foundPath = newLocation.getFullPath();
						filePath = foundPath.toOSString();
					}
					else
					if (foundElements[0] instanceof IResource)
					{
						IResource newResource = (IResource) foundElements[0];
						foundPath = newResource.getLocation();
						filePath = foundPath.toOSString();
					}
					addSourceMappingToCommon(missingPath, foundPath);
				}

			} catch (CoreException e) {}
		}
		
		return filePath;
	}

}
