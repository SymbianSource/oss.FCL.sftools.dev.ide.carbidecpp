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
package com.nokia.carbide.cpp.internal.project.ui.images;

import com.nokia.carbide.cdt.builder.*;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.MMPViewRunnableAdapter;
import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSourceReference;
import com.nokia.carbide.cpp.epoc.engine.image.IImageSourceReference;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.carbide.cpp.internal.project.ui.Messages;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.editors.images.AIFEditorContext;
import com.nokia.carbide.cpp.internal.project.ui.editors.images.AIFEditorDialog;
import com.nokia.carbide.cpp.ui.images.*;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.sdt.utils.ProjectFileResourceProxyVisitor;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.progress.UIJob;

import java.util.ArrayList;
import java.util.List;

public class AIFImageContainerModel extends ModelFileBasedImageContainerModelBase implements
		IAIFImageContainerModel {

	private final IMMPAIFInfo aifInfo;
	private final IImageConverterFactory converterFactory;

	/**
	 */
	public AIFImageContainerModel(
			IImageLoader imageLoader,
			IImageConverterFactory converterFactory,
			IPath projectPath,
			IPath modelFile,
			IMMPAIFInfo aifInfo) {
		super(imageLoader, projectPath, modelFile);
		this.converterFactory = converterFactory;
		this.aifInfo = aifInfo;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageContainerModel#getTargetPath()
	 */
	public IPath getTargetPath() {
		return aifInfo.getTarget();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageContainerModel#createImageModels()
	 */
	public IImageModel[] createImageModels() {
		List<IImageModel> models = new ArrayList<IImageModel>();
		for (IImageSourceReference imageSource : aifInfo.getSourceBitmaps()) {
			models.add(new ImageSourceReferenceModel(
						this, baseLocation, imageSource,
						aifInfo.getImageFormat()));
		}
		return (IImageModel[]) models.toArray(new IImageModel[models.size()]);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageContainerModel#getImageConverterFactory()
	 */
	public IImageConverterFactory getImageConverterFactory() {
		return converterFactory;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.images.IAIFImageContainerModel#getImageModel(int)
	 */
	public IImageModel getImageModel(int imageIndex) throws CoreException {
		IBitmapSourceReference bitmapRef = null;
		
		if (imageIndex >= 0) {
			if (imageIndex < aifInfo.getSourceBitmaps().size()) {
				bitmapRef = aifInfo.getSourceBitmaps().get(imageIndex);
				return new ImageSourceReferenceModel(
						this, baseLocation, bitmapRef,
						aifInfo.getImageFormat());
			}
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.ModelFileBasedImageContainerModelBase#createEditorProvider()
	 */
	@Override
	public IImageContainerEditorProvider createEditorProvider() {
		if (modelPath == null)
			return null;
		
		final IFile wsFile = FileUtils.convertFileToIFile(modelPath.toFile());
		if (wsFile == null)
			return null;
		
		return new IImageContainerEditorProvider() {

			public Job createEditingJob(final Shell shell) {
				return new UIJob(Messages.getString("AIFImageContainerModel.EditingJobLabel")) { //$NON-NLS-1$

					@Override
					public IStatus runInUIThread(IProgressMonitor monitor) {
						ICarbideProjectInfo projectInfo = CarbideBuilderPlugin.getBuildManager().
							getProjectInfo(wsFile.getProject());
						if (projectInfo != null) {
							editAifFile(shell, projectInfo, modelPath, aifInfo);
							return Status.OK_STATUS;
						} else {
							return Status.CANCEL_STATUS;
						}
					}
				};
			}
			
		};
	}
	

	/**
	 * Edit the selected multi-image file by bringing up the AIF editor dialog.
	 * 
	 * @param shell
	 * @param projectImageInfo 
	 * @param aifInfo the info, which is edited and updated
	 */
	public void editAifFile(
			final Shell shell,
			final ICarbideProjectInfo projectInfo, final IPath mmpPath, final IMMPAIFInfo aifInfo) {
		
		if (aifInfo == null)
			return;
		
		EpocEnginePlugin.runWithMMPView(mmpPath, new DefaultMMPViewConfiguration(projectInfo, new AllNodesViewFilter()),
			new MMPViewRunnableAdapter() {

				public Object run(IMMPView view) {
					AIFEditorContext context = new AIFEditorContext();
					context.initFromAIF(projectInfo,
							mmpPath,
							aifInfo, 
							view.getAifs(),
							new MMPViewPathHelper(projectInfo.getDefaultConfiguration()),
							getRSSFiles(projectInfo.getProject()));
					AIFEditorDialog dialog = new AIFEditorDialog(shell, context);
					if (dialog.open() == Dialog.OK && context.isDirty()) {
						context.doSave();
					}
					return null;
				}
		
		});
		
	}
	
	private static List<IPath> getRSSFiles(IProject project) {
		ProjectFileResourceProxyVisitor visitor = new ProjectFileResourceProxyVisitor("rss", true); //$NON-NLS-1$
		List<IPath> result = null;
		try {
			project.accept(visitor, IResource.NONE);
			result = visitor.getRequestedFiles();
		} catch (CoreException x) {
			ProjectUIPlugin.log(x);
		}
		return result;
	}

}
