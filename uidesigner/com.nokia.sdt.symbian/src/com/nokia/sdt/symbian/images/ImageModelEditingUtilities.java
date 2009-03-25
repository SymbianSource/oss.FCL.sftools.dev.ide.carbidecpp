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

package com.nokia.sdt.symbian.images;

import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.internal.project.ui.actions.*;
import com.nokia.carbide.cpp.internal.project.ui.views.IMBMMIFFileEntry;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.widgets.Shell;

public abstract class ImageModelEditingUtilities {
	/**
	 * Edit the selected multi-image file by bringing up the MBM/MIF editor dialog.
	 * 
	 * @param shell
	 * @param projectImageInfo 
	 * @param multiImageInfo the info, which is edited and updated
	 */
	public static void editMbmMifFile(
			Shell shell,
			ICarbideProjectInfo projectInfo, MultiImageInfo multiImageInfo) {
		if (multiImageInfo == null)
			return;
		
		String modelPath = multiImageInfo.getSourceFile();
		final IFile modelFile = (IFile) projectInfo.getProject().findMember(modelPath);
		if (modelFile == null)
			return;
		
		IMultiImageSource multiImageSource = 
			editMbmMifFile(shell, projectInfo, modelFile.getLocation(),
				multiImageInfo.getFileType() == MultiImageInfo.MIF_FILE,
				multiImageInfo.getBinaryFile());
		
		// refresh the multi-image file
		if (multiImageSource != null) {
			multiImageInfo.revert(multiImageSource);
		}
	}
	
	/**
	 * Edit the selected multi-image file by bringing up the MBM/MIF editor dialog.
	 * 
	 * @param shell
	 * @param projectImageInfo 
	 * @param multiImageInfo the info, which is edited and updated
	 */
	public static IMultiImageSource editMbmMifFile(
			Shell shell,
			ICarbideProjectInfo projectInfo, 
			IPath modelPath,
			boolean allowSVG,
			final IPath targetFilePath) {
		// TODO: make the internal APIs cleaner... rather than this code dealing with Actions here
		final IFile wsFile = FileUtils.convertFileToIFile(modelPath.toFile());
		if (wsFile == null)
			return null;

		IMBMMIFFileEntry entry = null;
		
		EditMBMMIFFileEntryActionBase editor;
		entry = new IMBMMIFFileEntry() {
			
			public IFile getModelFile() {
				return wsFile;
			}
			
			public IPath getTargetFilePath() {
				return targetFilePath;
			}
			
		};

		if (!allowSVG) {
			editor = new EditMBMFileEntryAction();
		} else {
			editor = new EditMIFFileEntryAction();
		}

		editor.setShell(shell);
		editor.setMBMMIFFileEntry(entry);
		editor.run(null);
		
		return editor.getLastMultiImageSource();
	}
	
}
