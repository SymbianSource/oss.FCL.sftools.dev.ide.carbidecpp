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

package com.nokia.sdt.symbian.ui.images;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.internal.project.ui.editors.images.*;
import com.nokia.carbide.cpp.ui.images.IFileImageModel;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.sdt.symbian.images.BuildLogic;
import com.nokia.sdt.symbian.ui.UIPlugin;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

/**
 * This dialog allows the user to select a set of images from the project
 * and assign them to target locations for the bld.inf PRJ_EXPORTS and the PKG file.
 *
 */
public class AddProjectImagesForExportDialog extends SelectProjectFileImageModelsDialog {

	/**
	 * 
	 */
	private static final String DEFAULT_EXPORT_PATH_SETTING = "AddImages.DefaultExportPath"; //$NON-NLS-1$

	protected ExportedImageLocationTableComposite exportsTable;
	protected ArrayList<NewImageExport> newExports;

	protected Composite topHalf;
	
	/**
	 * @param shell
	 */
	public AddProjectImagesForExportDialog(Shell shell, IProject project) {
		this(shell, project, 
				Messages.getString("AddProjectImagesForExportDialog.ExportProjectImagesHelp")); //$NON-NLS-1$
	}

	/**
	 * @param shell
	 */
	protected AddProjectImagesForExportDialog(Shell shell, IProject project, String helpText) {
		super(shell, project, helpText);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.editors.images.SelectOrAddImagesDialogBase#okPressed()
	 */
	@Override
	protected void okPressed() {
		saveSetting(DEFAULT_EXPORT_PATH_SETTING, exportsTable.getDefaultExportPath().toOSString());
		super.okPressed();
		establishNewExports();
	}
	
	/** Override as needed to create 'newExports' list from the exportsTable */
	protected void establishNewExports() {
		// for project images, they're already in the project
		newExports = new ArrayList<NewImageExport>();
		for (Map.Entry<IFileImageModel, IPath> entry : exportsTable.getMappings().entrySet()) {
			Check.checkState(entry.getKey().getSourcePath() != null);
			NewImageExport export = new NewImageExport(entry.getKey().getSourcePath(), 
					entry.getValue());
			newExports.add(export);
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.editors.images.SelectOrAddImagesDialogBase#handleFilesCopied()
	 */
	@Override
	protected void handleFilesCopied() {
		super.handleFilesCopied();
	}
	
	
	/**
	 * @return the newExports
	 */
	public List<NewImageExport> getNewExports() {
		return newExports != null ? newExports : Collections.EMPTY_LIST;
	}
	
	/** Get full path to sis file; may be null */
	public IPath getTargetPkgFile() {
		IPath targePkgFile = this.exportsTable.getTargetPkgFile();
		if (targePkgFile == null)
			return null;
		return projectPath.append(targePkgFile);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.internal.images.AddImagesDialogBase#getDialogSettings()
	 */
	@Override
	protected IDialogSettings getDialogSettings() {
		if (UIPlugin.getDefault() != null) {
			return UIPlugin.getDefault().getDialogSettings();
		} else {
			return new DialogSettings("root"); //$NON-NLS-1$
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.editors.images.SelectOrAddImagesDialogBase#createDialogContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createDialogContents(Composite composite) {
		getShell().setText(Messages.getString("AddProjectImagesForExportDialog.ExportProjectImagesTitle")); //$NON-NLS-1$
		SashForm sash = new SashForm(composite, SWT.VERTICAL | SWT.BORDER | SWT.SMOOTH);
		sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		topHalf = new Composite(sash, SWT.NONE);
		topHalf.setLayout(new GridLayout(1, false));
		
		initializeTopHalf();
		
		super.createDialogContents(topHalf);
		
		exportsTable = new ExportedImageLocationTableComposite(sash, SWT.NONE);
		GridData gridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		//gridData.minimumHeight = 300;
		exportsTable.setLayoutData(gridData);
		
		exportsTable.setDefaultExportPath(new Path(
				getStringSettingWithDefault(DEFAULT_EXPORT_PATH_SETTING, "c:\\private\\${UID3}"))); //$NON-NLS-1$
		
		ICarbideProjectInfo info = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (info != null) {
			exportsTable.setDefaultUID(BuildLogic.getProjectUID3(info));
			exportsTable.setPkgFileSelection(findProjectPkgFiles(info));
		}

		sash.setWeights(new int[] { 70,  30 });

	}
	
	/**
	 *	Override and initialize the 'topHalf' composite before contents are added. 
	 */
	protected void initializeTopHalf() {
		
	}

	/**
	 * Find the 
	 * @param info
	 * @return
	 */
	protected IPath[] findProjectPkgFiles(ICarbideProjectInfo info) {
		
		/*
		 // ok, this isn't what we use
		 * List<ISISBuilderInfo> builderInfoList = info.getDefaultConfiguration().getSISBuilderInfoList();
		IPath[] paths = new IPath[builderInfoList.size()];
		int idx = 0;
		for (ISISBuilderInfo sisInfo : builderInfoList) {
			paths[idx++] = FileUtils.removePrefixFromPath(projectPath, sisInfo.getPKGFullPath());
		}
		return paths;
		*/
		File[] pkgFiles = FileUtils.listFilesInTree(projectPath.toFile(), new FileFilter() {

			public boolean accept(File pathname) {
				if (pathname.isDirectory())
					return true;
				return pathname.getName().toLowerCase().endsWith(".pkg"); //$NON-NLS-1$
			}
			
		}, false);
		
		IPath[] paths = new IPath[pkgFiles.length];
		int idx = 0;
		for (File pkgFile : pkgFiles) {
			paths[idx++] = FileUtils.removePrefixFromPath(projectPath, 
					new Path(pkgFile.getAbsolutePath()));
		}
		
		return paths;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.editors.images.SelectOrAddImagesDialogBase#handleThumbnailViewerSelectionChanged(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	protected void handleThumbnailViewerSelectionChanged(
			IStructuredSelection selection) {
		super.handleThumbnailViewerSelectionChanged(selection);
		if (exportsTable != null)
			exportsTable.resync(selectedImages);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.editors.images.SelectProjectFileImageModelsDialog#acceptFile(java.io.File)
	 */
	@Override
	public boolean acceptFile(File file) {
		// TODO: filter out stuff already exported... 
		return super.acceptFile(file);
	}
	
}
