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

package com.nokia.sdt.sourcegen.core;

import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.sdt.workspace.CoreMessageHandler;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;

import java.io.File;

/**
 * Utilities for messages generated while parsing.
 * 
 *
 */
public abstract class ParserMessages {
	public static void deleteMessagesForFiles(final ISourceFile[] files) {
		if (!Platform.isRunning())
			return;
		Job job = new Job(Messages.getString("ParserMessages.DeleteProblemMarkersJobName")) { //$NON-NLS-1$
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				for (int i = 0; i < files.length; i++) {
					IResource rsrc = resolveToResource(files[i]);
					if (rsrc != null)
						CoreMessageHandler.deleteMarkers(rsrc, IResource.DEPTH_ZERO);
				}
				return Status.OK_STATUS;
			}
			
		};
		job.schedule();
	}

	/**
	 * @param file
	 * @return
	 */
	public static IResource resolveToResource(ISourceFile sf) {
		File file = sf.getFile();
		IPath path = FileUtils.convertToWorkspacePath(new Path(file.getAbsolutePath()));
		if (path != null) {
			return ResourcesPlugin.getWorkspace().getRoot().findMember(path);
		}
		return null;
	}
}
