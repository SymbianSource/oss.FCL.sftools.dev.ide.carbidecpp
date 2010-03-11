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

package com.nokia.cdt.debug.common.internal.source.lookup;

import org.eclipse.cdt.debug.core.sourcelookup.AbsolutePathSourceContainer;
import org.eclipse.cdt.debug.core.sourcelookup.MappingSourceContainer;
import org.eclipse.cdt.debug.internal.core.sourcelookup.MapEntrySourceContainer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.ISourceLookupDirector;

import com.nokia.cdt.debug.common.CarbideCommonDebuggerPlugin;

/**
 * Used to attempt to map missing files to ones in EPOCROOT.
 * For example, if source files are built on drive M but
 * the SDK and sources are on drive R then it will replace
 * the source's device ID with the EPOCROOT path and see
 * if the file exists. If so a path mapping entry will be 
 * created to map from that drive to EPOCROOT.
 */
public class SymbianSourceContainer extends MappingSourceContainer {

	private Path epocRoot;
	public static final String TYPE_ID = CarbideCommonDebuggerPlugin.PLUGIN_ID + ".containerType.mapping";	 //$NON-NLS-1$
	public static final String CONTAINER_NAME = "Symbian SDK Root Locator";	 //$NON-NLS-1$

	public SymbianSourceContainer(Path epocRoot)
	{
		super(CONTAINER_NAME);
		this.epocRoot = epocRoot;
	}

	@Override
	public ISourceContainerType getType() {
		return getSourceContainerType( TYPE_ID );
	}

	protected Object[] findSourceElements(String name, ISourceContainer[] containers) throws CoreException {
		Object[] result = super.findSourceElements(name, containers);
		if (result.length == 0)
		{
			Path sourcePath = new Path( name );
			MapEntrySourceContainer mapping = new MapEntrySourceContainer(sourcePath.uptoSegment(0), epocRoot);
			ISourceLookupDirector director = this.getDirector();
			if (director != null)
				mapping.init(director);
			result = mapping.findSourceElements(name);
			if (result.length > 0)
			{
				if (!mapping.getBackendPath().equals(mapping.getLocalPath())){
					addMapEntry(mapping);
				}
				if (director != null)
				{
					ILaunchConfigurationWorkingCopy configuration = director.getLaunchConfiguration().getWorkingCopy();
					configuration.setAttribute(ILaunchConfiguration.ATTR_SOURCE_LOCATOR_MEMENTO, director.getMemento());
					configuration.setAttribute(ILaunchConfiguration.ATTR_SOURCE_LOCATOR_ID, director.getId());
					configuration.doSave();					
				}
			}
			else
			{
				// Check to see if this is an absolute path inside the SDK.
				// Absolute paths are usually caught by the AbsolutePathSourceContainer but
				// if there is not one present then this will work for files in the SDK.
				if (epocRoot.isPrefixOf(sourcePath))
				{
					result = new AbsolutePathSourceContainer().findSourceElements(name);
				}
			}
		}
		return result;
	}

	public IPath getEpocRoot() {
		return epocRoot;
	}

}
