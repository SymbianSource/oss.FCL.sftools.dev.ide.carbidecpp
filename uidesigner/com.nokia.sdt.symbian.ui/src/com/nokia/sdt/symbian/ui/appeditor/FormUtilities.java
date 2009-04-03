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
package com.nokia.sdt.symbian.ui.appeditor;

import com.nokia.sdt.symbian.ui.UIPlugin;
import com.swtdesigner.ResourceManager;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.*;

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
		ImageDescriptor id = ResourceManager.getPluginImageDescriptor(UIPlugin.getDefault(), "icons/help.png"); //$NON-NLS-1$
		helpAction.setImageDescriptor(id);
		manager.add(helpAction);
		form.updateToolBar();
	}
	
	public static void addHelpIcon(FormToolkit tk, Section section) {
		ImageHyperlink info = new ImageHyperlink(section, SWT.NULL);
		tk.adapt(info, true, true);
		info.setImage(ResourceManager.getPluginImage(UIPlugin.getDefault(), "icons/help.png")); //$NON-NLS-1$
		info.setBackground(section.getTitleBarGradientBackground());
		section.setTextClient(info);
	}
}
