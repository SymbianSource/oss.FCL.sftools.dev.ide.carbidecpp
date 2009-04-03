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
package com.nokia.carbide.cpp.internal.project.ui.editors.common;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.Section;

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.swtdesigner.ResourceManager;

public class FormUtilities {

	public static void addHelpToolbarItem(final Form form, final String href, final String tooltip) {
		IToolBarManager manager = form.getToolBarManager();
		Action helpAction = new Action("help") { //$NON-NLS-1$
			public void run() {
				BusyIndicator.showWhile(form.getDisplay(), new Runnable() {
					public void run() {
						PlatformUI.getWorkbench().getHelpSystem().displayHelpResource(href);
					}
				});
			}
		};
		helpAction.setToolTipText(tooltip);
		ImageDescriptor id = ResourceManager.getPluginImageDescriptor(ProjectUIPlugin.getDefault(), "icons/help.png"); //$NON-NLS-1$
		helpAction.setImageDescriptor(id);
		manager.add(helpAction);
		form.updateToolBar();
	}
	
	public static void addHelpContextToolbarItem(final Form form, final String contextID, final String tooltip) {
		IToolBarManager manager = form.getToolBarManager();
		Action helpAction = new Action("help") { //$NON-NLS-1$
			public void run() {
				BusyIndicator.showWhile(form.getDisplay(), new Runnable() {
					public void run() {
						PlatformUI.getWorkbench().getHelpSystem().displayHelp(contextID);
					}
				});
			}
		};
		helpAction.setToolTipText(tooltip);
		ImageDescriptor id = ResourceManager.getPluginImageDescriptor(ProjectUIPlugin.getDefault(), "icons/help.png"); //$NON-NLS-1$
		helpAction.setImageDescriptor(id);
		manager.add(helpAction);
		form.updateToolBar();
	}
	
	public static void addHelpIcon(FormToolkit tk, Section section) {
		ImageHyperlink info = new ImageHyperlink(section, SWT.NULL);
		tk.adapt(info, true, true);
		info.setImage(ResourceManager.getPluginImage(ProjectUIPlugin.getDefault(), "icons/help.png")); //$NON-NLS-1$
		info.setBackground(section.getTitleBarGradientBackground());
		section.setTextClient(info);
	}
}
