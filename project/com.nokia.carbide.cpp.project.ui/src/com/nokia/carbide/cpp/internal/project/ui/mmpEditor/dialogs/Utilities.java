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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs;

import com.nokia.carbide.cdt.builder.*;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import java.text.MessageFormat;

public abstract class Utilities {
	
	static final long maxUID = 0xFFFFFFFFL;

	static boolean validateUID(String uid) {
		boolean result = false;
		if (TextUtils.strlen(uid) > 0) {
			try {
				Long value = Long.decode(uid);
				result = value >= 0 && value <= maxUID;
			} catch (NumberFormatException x) {
			}
		}
		return result;
	}
	
	/**
	 * Check if a path is going to be ok to use in an MMP file. We define
	 * this as either in the project or
	 * @param path
	 * @param buildConfig
	 * @return
	 */
	public static boolean isValidMMPPath(IPath path, ICarbideBuildConfiguration buildConfig) {
		boolean result = true;
		EpocEnginePathHelper helper = new EpocEnginePathHelper(buildConfig);
		path = helper.convertPathToView(path);
		// This path has to be on the same drive as the SDK. We aren't going
		// to try to detect different subst'ed drives that map to the same
		// real drive, but we also can't put anything into the MMP that
		// has a drive letter. So if the path has a device we check if it's the 
		// same device as the SDK. 
		if (path.getDevice() != null) {
			IPath epocRoot = new Path(buildConfig.getSDK().getEPOCROOT());
			String epocRootDevice = epocRoot.getDevice();
			if (epocRootDevice != null && 
				!epocRootDevice.equalsIgnoreCase(path.getDevice())) {
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * Check if this path has a drive letter rendering it invalid for use in an MMP file.
	 * All files must be on the SDK drive. This routine tries to convert the
	 * path to a suitable path. If it can't be, it prompts the user to approve
	 * stripping the drive letter. If the user doesn't approve then null is returned.
	 */
	public static IPath validateandConfirmMMPPath(IPath path, 
			ICarbideBuildConfiguration buildConfig,
			EMMPPathContext pathContext,
			MMPViewPathHelper pathHelper,
			Shell shell) {
		IPath result = path;
		
		try {
			result = pathHelper.convertProjectOrFullPathToMMP(pathContext, path);
		} catch (InvalidDriveInMMPPathException e) { 
			IPath epocRoot = new Path(buildConfig.getSDK().getEPOCROOT());
			String epocRootDevice = epocRoot.getDevice();
			String title = Messages.getString("Utilities.InvalidMMPPathTitle"); //$NON-NLS-1$
			boolean stripDevice;
			if (epocRootDevice != null) {
				String fmt = Messages.getString("Utilities.InvalidMMPPathDifferentDriveMessage"); //$NON-NLS-1$
				String msg = MessageFormat.format(fmt, epocRootDevice, path.getDevice());
				stripDevice = MessageDialog.openConfirm(shell, title, msg);
			} else {
				String fmt = Messages.getString("Utilities.InvalidMMPPathNoDriveMessage"); //$NON-NLS-1$
				String msg = MessageFormat.format(fmt, path.getDevice());
				stripDevice = MessageDialog.openConfirm(shell, title, msg);
			}
			if (stripDevice) {
				result = e.getPathNoDevice(); 
			} else {
				result = null;
			}
		}
		return result;
	}
	
}
