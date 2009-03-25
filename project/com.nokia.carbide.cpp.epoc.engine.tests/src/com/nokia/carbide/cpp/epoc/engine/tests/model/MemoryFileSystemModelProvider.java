/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.tests.model;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.epoc.engine.model.IModelFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase;

public class MemoryFileSystemModelProvider extends ModelProviderBase {
	Map<IPath, String> fs;
	
	boolean saved = false;
	
	/**
	 * @param fs 
	 * @param modelFactory
	 */
	public MemoryFileSystemModelProvider(Map<IPath, String> fs, IModelFactory modelFactory) {
		super(modelFactory);
		this.fs = fs;
	}

	public Map<IPath, String> getFilesystem() {
		return fs;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase#getCanonicalPath(org.eclipse.core.runtime.IPath)
	 */
	@Override
	protected IPath getFullPath(IPath workspacePath) {
		return new Path(workspacePath.toOSString().toLowerCase());
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase#loadStorage(org.eclipse.core.runtime.IPath)
	 */
	@Override
	protected String loadStorage(IPath workspacePath) throws CoreException {
		return fs.get(workspacePath);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase#saveStorage(org.eclipse.core.runtime.IPath, java.lang.String)
	 */
	@Override
	protected void saveStorage(IPath workspacePath, String text) throws CoreException {
		saved = true;
		fs.put(workspacePath, text);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase#startTrackingStorage(org.eclipse.core.runtime.IPath)
	 */
	@Override
	protected void startTrackingStorage(IPath workspacePath) {
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase#stopTrackingStorage(org.eclipse.core.runtime.IPath)
	 */
	@Override
	protected void stopTrackingStorage(IPath workspacePath) {
		
	}
	
	@Override
	protected boolean hasTrackedStorageChanged(IPath fullPath) {
		return false;
	}
	
}