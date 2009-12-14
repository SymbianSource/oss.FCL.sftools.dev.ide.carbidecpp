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
package com.nokia.cdt.internal.debug.launch.ui;

import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;
import com.nokia.cpp.internal.api.utils.core.HostOS;

import cwdbg.PreferenceConstants;

import org.eclipse.cdt.launch.ui.CLaunchConfigurationTab;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;

import java.util.*;

public class FileTransferTab extends CLaunchConfigurationTab {

	private FilesBlock fFilesBlock;
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.RUN_MODE_FILE_TRANSFER);
		
		GridLayout layout= new GridLayout();
		layout.numColumns= 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		comp.setLayout(layout);
					
		fFilesBlock = new FilesBlock(this);
		fFilesBlock.createControl(comp);
		Control control = fFilesBlock.getControl();
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 1;
		data.horizontalIndent = 18;
		data.verticalIndent = 18;
		control.setLayoutData(data);
		
//		fFilesBlock.restoreColumnSettings(JDIDebugUIPlugin.getDefault().getDialogSettings(), IJavaDebugHelpContextIds.JRE_PREFERENCE_PAGE);
		
		Dialog.applyDialogFont(parent);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			List<FileToTransfer> files = new ArrayList<FileToTransfer>();

			String conversionTag = configuration.getAttribute(SettingsData.TARGET_PATH_INCLUDES_FILENAME, "false"); //$NON-NLS-1$
			if (conversionTag.compareToIgnoreCase("true") != 0) { //$NON-NLS-1$
				// needs conversion - add the filename from the host path to the target path which was just a directory
				String filesString = configuration.getAttribute(PreferenceConstants.J_PN_FilesToTransfer, ""); //$NON-NLS-1$
				if (filesString.length() > 0) {
					StringTokenizer tokenizer = new StringTokenizer(filesString, ","); //$NON-NLS-1$
					while (tokenizer.hasMoreTokens()) {
						IPath hp = new Path(tokenizer.nextToken());
						IPath tp = HostOS.createPathFromString(tokenizer.nextToken());
						// ensure there was no filename before
						if (tp.getFileExtension() == null) {
							tp = tp.append(hp.lastSegment());
						}
						String enabled = tokenizer.nextToken();
						files.add(new FileToTransfer(hp.toOSString(), HostOS.convertPathToWindows(tp), enabled.compareTo("1") == 0)); //$NON-NLS-1$
					}
				}
			} else {
				String filesString = configuration.getAttribute(PreferenceConstants.J_PN_FilesToTransfer, ""); //$NON-NLS-1$
				if (filesString.length() > 0) {
					StringTokenizer tokenizer = new StringTokenizer(filesString, ","); //$NON-NLS-1$
					while (tokenizer.hasMoreTokens()) {
						String hp = tokenizer.nextToken();
						String tp = tokenizer.nextToken();
						String enabled = tokenizer.nextToken();
						files.add(new FileToTransfer(hp, tp, enabled.compareTo("1") == 0)); //$NON-NLS-1$
					}
				}
			}
			fFilesBlock.setFiles(files);
			if (SettingsData.isAppTRKConfiguration(configuration)) {
				fFilesBlock.getViewer().getControl().setToolTipText(Messages.getString("FileTransferTab.Tooltip")); //$NON-NLS-1$
			}
			
		} catch (CoreException e) {
			LaunchPlugin.log(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		FileToTransfer[] files = fFilesBlock.getFiles();
		String filesString = ""; //$NON-NLS-1$
		for (int i=0; i<files.length; i++) {
			filesString += files[i].getHostPath() + "," + files[i].getTargetPath() + "," + (files[i].getEnabled() ? "1" : "0") + ","; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		}
		configuration.setAttribute( PreferenceConstants.J_PN_FilesToTransfer, filesString);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return Messages.getString("FileTransferTab.1"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getImage()
	 */
	public Image getImage() {
		return LaunchImages.get(LaunchImages.IMG_VIEW_FILE_TRANSFER_TAB);
	}
	
	public void dataChanged() {
		updateLaunchConfigurationDialog();
	}

	@Override
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
		super.activated(workingCopy);
		
		// forces page to get focus so that help works without having to select some control first.
		getControl().setFocus();
	}

	@Override
	public boolean isValid(ILaunchConfiguration config) {
		
		setErrorMessage(null);
		setMessage(null);

		boolean result = super.isValid(config);
		if (result) {
			// if any host files don't exist, set a warning message.
			// note we don't want to do an error here because then
			// they wouldn't be able to kick off the launch.  they be
			// be building before launch which would build the missing
			// files before launching the debugger.
			for (FileToTransfer file : fFilesBlock.getFiles()) {
				if (file.getEnabled() && !new Path(file.getHostPath()).toFile().exists()) {
					setMessage(Messages.getString("FileTransferTab.HostFilesDontExist")); //$NON-NLS-1$
					break;
				}
			}
		}		
		return result;
	}
}
