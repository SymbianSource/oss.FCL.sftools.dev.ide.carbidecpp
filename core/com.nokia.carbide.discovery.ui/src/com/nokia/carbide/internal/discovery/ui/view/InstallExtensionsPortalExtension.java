/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.internal.discovery.ui.view;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.internal.discovery.ui.extension.AbstractDiscoveryPortalPageLayer;
import com.nokia.carbide.internal.discovery.ui.extension.IActionBar;
import com.nokia.carbide.internal.discovery.ui.extension.IActionUIUpdater;
import com.nokia.carbide.internal.discovery.ui.wizard.ExportWizard;
import com.nokia.carbide.internal.discovery.ui.wizard.ImportWizard;

public class InstallExtensionsPortalExtension extends AbstractDiscoveryPortalPageLayer {

	private final class MigrateBar implements IActionBar {
		@Override
		public String getTitle() {
			return "Migrate";
		}

		@Override
		public String[] getHighlightedActionIds() {
			return null;
		}

		@Override
		public IAction[] getActions() {
			List<IAction> actions = new ArrayList<IAction>();
			actions.add(new Action("Export...") {
				@Override
				public void run() {
					showWizard(new ExportWizard());
				}
			});
			actions.add(new Action("Import...") {
				@Override
				public void run() {
					showWizard(new ImportWizard());
				}
			});
			return (IAction[]) actions.toArray(new IAction[actions.size()]);
		}
	}

	@Override
	public Control createControl(Composite parent, IEditorPart part) {
		Control control = super.createControl(parent, part);

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(control, 
				"com.nokia.carbide.discovery.ui.view.DiscoveryView.catalogviewer"); //$NON-NLS-1$

		return control;
	}
	
	@Override
	public IActionBar[] createCommandBars(IEditorPart part, IActionUIUpdater updater) {
		List<IActionBar> commandBars = new ArrayList<IActionBar>(
				Arrays.asList(super.createCommandBars(part, updater)));
		commandBars.add(new MigrateBar());
		
		return (IActionBar[]) commandBars.toArray(new IActionBar[commandBars.size()]);
	}

	private void showWizard(IWorkbenchWizard wizard) {
		wizard.init(PlatformUI.getWorkbench(), StructuredSelection.EMPTY);
        WizardDialog dialog = new WizardDialog(part.getEditorSite().getShell(), wizard);
		dialog.setMinimumPageSize(550, 250);
        dialog.create();
        dialog.open();
	}
}
