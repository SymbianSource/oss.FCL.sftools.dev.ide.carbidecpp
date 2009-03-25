/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.carbide.cpp.epoc.engine.model.*;

import org.eclipse.core.runtime.IPath;


public abstract class BaseData<T extends IView> implements IData<T> {

	protected IPath modelPath;
	protected IPath projectPath;
	protected IPath[] referencedFiles;

	/**
	 * 
	 */
	public BaseData(T view) {
		this.modelPath = ((IModel)view.getModel()).getPath();
		this.projectPath = view.getViewConfiguration().getViewParserConfiguration().getProjectLocation();
		this.referencedFiles = view.getReferencedFiles();
	}

	public IPath getProjectPath() {
		return projectPath;
	}

	public IPath getModelPath() {
		return modelPath;
	}

	public IPath[] getReferencedFiles() {
		return referencedFiles;
	}

}