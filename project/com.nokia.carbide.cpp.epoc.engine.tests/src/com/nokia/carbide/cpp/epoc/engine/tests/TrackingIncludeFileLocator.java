/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.tests;

import com.nokia.carbide.cdt.builder.DefaultIncludeFileLocator;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

import org.eclipse.core.resources.IProject;

import java.io.File;
import java.util.*;

/**
 * This test version tracks all the files resolved through #includes.
 *
 */
public class TrackingIncludeFileLocator extends DefaultIncludeFileLocator {

	private Set<File> foundFiles = new HashSet<File>();
	
	/**
	 * @param project
	 * @param buildContext
	 */
	public TrackingIncludeFileLocator(IProject project,
			ISymbianBuildContext buildContext) {
		super(project, buildContext);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.BasicIncludeFileLocator#findIncludeFile(java.lang.String, boolean, java.io.File)
	 */
	@Override
	public synchronized File findIncludeFile(String file, boolean isUser, File currentDir) {
		File found = super.findIncludeFile(file, isUser, currentDir);
		if (found != null) {
			foundFiles.add(found);
		}
		return found;
	}
	
	/**
	 * Get a copy of the files located so far.
	 * @return
	 */
	public synchronized Collection<File> getLocatedFiles() {
		return new ArrayList<File>(foundFiles);
	}
}
