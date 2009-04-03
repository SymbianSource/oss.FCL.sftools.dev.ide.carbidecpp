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
* Adapter for this runnable which handles errors by logging to the Error Log.
*
*
*/
package com.nokia.carbide.cpp.epoc.engine;

import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;

import java.io.FileNotFoundException;
import java.text.MessageFormat;
public abstract class ViewRunnableAdapter {

	protected IPath modelPath;

	/** Package-level API to record the model of the path for use by #failedLoad(). */
	public void setModelPath(IPath modelPath) {
		this.modelPath = modelPath;
	}

	public Object failedLoad(CoreException exception) {
		IStatus status;
		if (exception != null) {
			status = Logging.newStatus(EpocEnginePlugin.getDefault(), IStatus.ERROR, 
					MessageFormat.format("Failed to load model: ''{0}''", //$NON-NLS-1$
							new Object[] { modelPath.toOSString() }),
					null);
			EpocEnginePlugin.log(status);
			EpocEnginePlugin.log(exception.getStatus());
		} else {
			status = Logging.newStatus(EpocEnginePlugin.getDefault(), IStatus.ERROR, 
					MessageFormat.format("Could not find or load model: ''{0}''", //$NON-NLS-1$
							new Object[] { modelPath.toOSString() }),
					new FileNotFoundException());
			EpocEnginePlugin.log(status);
		}
		return null;
	}

}
