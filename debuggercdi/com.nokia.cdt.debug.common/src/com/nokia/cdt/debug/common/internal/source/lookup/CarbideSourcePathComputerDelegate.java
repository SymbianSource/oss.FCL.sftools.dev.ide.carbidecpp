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

import java.util.ArrayList;

import org.eclipse.cdt.debug.core.CDebugCorePlugin;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.core.sourcelookup.AbsolutePathSourceContainer;
import org.eclipse.cdt.debug.core.sourcelookup.MappingSourceContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputerDelegate;
import org.eclipse.debug.core.sourcelookup.containers.ProjectSourceContainer;

import com.nokia.cdt.debug.common.CarbideCommonDebuggerPlugin;

/**
 * Computes the default source lookup path for a launch configuration.
 */
public class CarbideSourcePathComputerDelegate implements ISourcePathComputerDelegate {

	/** From legacy DE support. */
	public static final String PSC_FindSourceOutsideWorkspace = "com.freescale.cdt.debug.cw.PN_FindSourceOutsideWorkspace"; // $//$NON-NLS-N$
	
	/** Plugin ID to look up Carbide global debugger prefs */
	public static final String CWPluginID = "com.freescale.cdt.debug.cw.core"; // $//$NON-NLS-N$
	
	/** 
	 * Constructor for CSourcePathComputerDelegate. 
	 */
	public CarbideSourcePathComputerDelegate() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.sourcelookup.ISourcePathComputerDelegate#computeSourceContainers(org.eclipse.debug.core.ILaunchConfiguration, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ISourceContainer[] computeSourceContainers( ILaunchConfiguration configuration, IProgressMonitor monitor ) throws CoreException {
		ISourceContainer[] common = CDebugCorePlugin.getDefault().getCommonSourceLookupDirector().getSourceContainers();
		ArrayList<ISourceContainer> containers = new ArrayList<ISourceContainer>( common.length + 1 );
		for ( int i = 0; i < common.length; ++i ) {
			ISourceContainer sc = common[i];
			if ( sc.getType().getId().equals( MappingSourceContainer.TYPE_ID ) )
				sc = ((MappingSourceContainer)sc).copy();
			containers.add( sc );
		}
		String projectName = configuration.getAttribute( ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME, (String)null );
		if (projectName != null && projectName.length() > 0) {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject( projectName );
			if ( project.exists() ) {
				containers.add( 0, new ProjectSourceContainer( project, true ) );
			}
		}
		
		if (Platform.getPreferencesService().getBoolean( CWPluginID, PSC_FindSourceOutsideWorkspace, false, null)){
			containers.add( 0, new AbsolutePathSourceContainer() );
		}
		return (ISourceContainer[])containers.toArray( new ISourceContainer[containers.size()] );
	}
}
