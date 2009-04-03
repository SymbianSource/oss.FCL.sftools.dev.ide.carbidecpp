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

package com.nokia.carbide.cpp.epoc.engine.model;

import com.nokia.carbide.internal.cpp.epoc.engine.model.StandaloneModelProvider;
import com.nokia.carbide.internal.cpp.epoc.engine.model.WorkspaceModelProvider;

import org.eclipse.core.runtime.Platform;

/**
 * Provide access to the model provider implementations.
 *
 */
public abstract class ModelProviderFactory {

	/** Create the appropriate model provider based on whether the platform is running. */
	public static IModelProvider createModelProvider(IModelFactory modelFactory) {
		if (Platform.isRunning())
			return new WorkspaceModelProvider(modelFactory);
		else
			return new StandaloneModelProvider(modelFactory);
	}


	public static IModelProvider createWorkspaceModelProvider(IModelFactory modelFactory) {
		return new WorkspaceModelProvider(modelFactory);
	}

	public static IModelProvider createStandaloneModelProvider(IModelFactory modelFactory) {
		return new StandaloneModelProvider(modelFactory);
	}

}
