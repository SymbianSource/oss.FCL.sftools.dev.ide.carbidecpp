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
package com.nokia.carbide.cdt.internal.builder;

import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.cdt.ui.newui.AbstractPage;
import org.eclipse.cdt.ui.newui.CDTPropertyManager;
import org.eclipse.cdt.ui.newui.IConfigManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.window.Window;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.internal.api.builder.ui.ManageConfigurationsDialog;

/**
 * Used to tell CDT if we can manage the configs of the given project(s).
 *
 */
public class CarbideConfigManager implements IConfigManager {

	public boolean canManage(IProject[] obs) {
		// can only manage one project's configs at a time
		if (obs != null && obs.length == 1) {
			IProject project = obs[0];
			try {
				if (project.isAccessible() && project.hasNature(CarbideBuilderPlugin.CARBIDE_PROJECT_NATURE_ID)) {
					return true;
				}
			} catch (CoreException e) {
			}
		}
		return false;
	}

	public boolean manage(IProject[] obs, boolean doOk) {
		if (!canManage(obs)) {
			return false;
		}
		
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(obs[0]);
		
		ManageConfigurationsDialog dlg = new ManageConfigurationsDialog(CUIPlugin.getActiveWorkbenchShell(), cpi);
		if (dlg.open() == Window.OK) {
			if (doOk) {
				CDTPropertyManager.getProjectDescription(obs[0]);
				CDTPropertyManager.performOk(dlg.getShell());
			}
			AbstractPage.updateViews(obs[0]);
			return true;
		}
		return false;
	}

}
