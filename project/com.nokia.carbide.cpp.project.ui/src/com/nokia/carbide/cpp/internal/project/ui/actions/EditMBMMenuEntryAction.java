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
import com.nokia.carbide.cdt.builder.DefaultMMPViewConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.IMMPViewRunnable;
import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;

public class EditMBMMenuEntryAction extends EditMBMMIFMenuEntryActionBase {

	public EditMBMMenuEntryAction() {
	}

	@Override
	List<IMultiImageSource> getMultiImageSources() {
		if (modelFile == null)
			return null;
		ICarbideProjectInfo projectInfo = CarbideBuilderPlugin.getBuildManager().getProjectInfo(modelFile.getProject());
		return (List<IMultiImageSource>) EpocEnginePlugin.runWithMMPView(
				modelFile.getLocation(),
				new DefaultMMPViewConfiguration(projectInfo, new AllNodesViewFilter()),
				new IMMPViewRunnable() {

					public Object failedLoad(CoreException exception) {
						return failedLoadHandler(exception);
					}

					public Object run(IMMPView view) {
						return view.getMultiImageSources();
					}
					
				});
	}

	@Override
	protected void editEntry(final IPath targetPath) {
		if (modelFile == null)
			return;
		ICarbideProjectInfo projectInfo = CarbideBuilderPlugin.getBuildManager().getProjectInfo(modelFile.getProject());
		EpocEnginePlugin.runWithMMPView(
				modelFile.getLocation(),
				new DefaultMMPViewConfiguration(projectInfo, new AllNodesViewFilter()),
				new IMMPViewRunnable() {

					public Object failedLoad(CoreException exception) {
						failedLoadHandler(exception);
						return null;
					}

					public Object run(IMMPView view) {
						IMultiImageSource mis = findMultiImageSource(view, targetPath);
						editMbmMifFileInView(view, mis);
						return null;
					}
					
				});
	}
}
