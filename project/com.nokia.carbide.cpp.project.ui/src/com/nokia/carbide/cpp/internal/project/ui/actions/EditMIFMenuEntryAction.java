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
package com.nokia.carbide.cpp.internal.project.ui.actions;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.DefaultImageMakefileViewConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.IImageMakefileViewRunnable;
import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileView;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;

public class EditMIFMenuEntryAction extends EditMBMMIFMenuEntryActionBase {

	public EditMIFMenuEntryAction() {
	}

	@Override
	List<IMultiImageSource> getMultiImageSources() {
		if (modelFile == null)
			return null;
		ICarbideProjectInfo projectInfo = CarbideBuilderPlugin.getBuildManager().getProjectInfo(modelFile.getProject());
		return (List<IMultiImageSource>) EpocEnginePlugin.runWithImageMakefileView(
				modelFile.getLocation(),
				new DefaultImageMakefileViewConfiguration(projectInfo, new AllNodesViewFilter()),
				new IImageMakefileViewRunnable() {

					public Object failedLoad(CoreException exception) {
						return failedLoadHandler(exception);
					}

					public Object run(IImageMakefileView view) {
						return view.getMultiImageSources();
					}
					
				});
	}

	@Override
	protected void editEntry(final IPath targetPath) {
		if (modelFile == null)
			return;
		ICarbideProjectInfo projectInfo = CarbideBuilderPlugin.getBuildManager().getProjectInfo(modelFile.getProject());
		EpocEnginePlugin.runWithImageMakefileView(
				modelFile.getLocation(),
				new DefaultImageMakefileViewConfiguration(projectInfo, new AllNodesViewFilter()),
				new IImageMakefileViewRunnable() {

					public Object failedLoad(CoreException exception) {
						failedLoadHandler(exception);
						return null;
					}

					public Object run(IImageMakefileView view) {
						IMultiImageSource mis = findMultiImageSource(view, targetPath);
						editMbmMifFileInView(view, mis);
						return null;
					}
					
				});
	}
}
