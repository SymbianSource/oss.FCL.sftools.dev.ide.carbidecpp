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

package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.IModelFactory;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the model provider implementation that works on java.io.File.
 * <p>
 * All paths are full paths.
 * <p>
 * It does not track changes to files.  
 * <p>
 * Do not use it when talking to workspace resources, or else the workspace
 * will become out of sync.
 *
 */
public class StandaloneModelProvider<Model, SharedModel> extends ModelProviderBase {

	private Map<IPath, Long> externalFileModDateMap;

	public StandaloneModelProvider(IModelFactory modelFactory) {
		super(modelFactory);
		this.externalFileModDateMap = new HashMap<IPath, Long>();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase#getCanonicalPath(org.eclipse.core.runtime.IPath)
	 */
	@Override
	protected IPath getFullPath(IPath path) {
		File file = path.toFile();
		try {
			file = file.getCanonicalFile();
		} catch (IOException e) {
			EpocEnginePlugin.log(e);
		}
		return new Path(file.getAbsolutePath());
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase#loadDocument(org.eclipse.core.runtime.IPath)
	 */
	@Override
	protected String loadStorage(IPath path) throws CoreException {
		File file = path.toFile();
		if (file != null && file.exists()) {
			externalFileModDateMap.put(path, file.lastModified());
			char[] text = FileUtils.readFileContents(file, null);
			return new String(text);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase#saveDocument(org.eclipse.core.runtime.IPath, org.eclipse.jface.text.IDocument)
	 */
	@Override
	protected void saveStorage(IPath path, String text)
			throws CoreException {
		File file = path.toFile();
		if (file != null) {
			char[] chars = text.toCharArray();
			FileUtils.writeFileContents(file, chars, null);
			externalFileModDateMap.put(path, file.lastModified());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase#startTrackingStorage(org.eclipse.core.runtime.IPath)
	 */
	protected void startTrackingStorage(IPath path) {
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ModelProviderBase#stopTrackingStorage(org.eclipse.core.runtime.IPath)
	 */
	protected void stopTrackingStorage(IPath path) {
	}
	
	@Override
	protected boolean hasTrackedStorageChanged(IPath fullPath) {
		Long modDate = externalFileModDateMap.get(fullPath);
		if (modDate != null && modDate.longValue() != fullPath.toFile().lastModified())
			return true;
		return false;
	}
}
