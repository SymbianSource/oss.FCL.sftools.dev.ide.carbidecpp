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

package com.nokia.cdt.internal.debug.launch.newwizard;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;
import com.nokia.cdt.internal.debug.launch.wizard.ILaunchCreationWizard;
import com.nokia.cdt.internal.debug.launch.wizard.LaunchOptions;

/**
 * New launch wizard for Carbide 3.0.
 * 
 * See https://xdabug001.ext.nokia.com/bugzilla/show_bug.cgi?id=10419
 */
public class LaunchWizard extends Wizard implements ILaunchCreationWizard {
	 
	private LaunchWizardData launchData;
	private UnifiedLaunchOptionsPage mainPage;
	private Button advancedButton;
	private boolean advancedEdit;
	private IPageChangedListener pageChangedListener;
	private boolean hasFinished;
	
	public LaunchWizard(LaunchOptions launchOptions, IService trkService) {
		launchData = new LaunchWizardData(launchOptions, trkService);
		mainPage = new UnifiedLaunchOptionsPage(launchData); 
		mainPage.initializeSettings();
		setWindowTitle("New Launch Configuration Wizard");
    }

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		addPage(mainPage);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#setContainer(org.eclipse.jface.wizard.IWizardContainer)
	 */
	@Override
	public void setContainer(final IWizardContainer wizardContainer) {
		super.setContainer(wizardContainer);
		
		// Thanks, JFace, for making it so hard to know when the UI is ready
		if (wizardContainer instanceof WizardDialog && advancedButton == null) {
			pageChangedListener = new IPageChangedListener() {
				
				public void pageChanged(PageChangedEvent event) {
					addAdvancedButton();
					((WizardDialog)getContainer()).removePageChangedListener(pageChangedListener);
				}
			};
			((WizardDialog)wizardContainer).addPageChangedListener(pageChangedListener);
		}
	}

	protected void addAdvancedButton() {
		if (advancedButton == null) {
			Composite parent = (Composite) ((Dialog) getContainer()).buttonBar;
			if (parent != null) {
				
				advancedButton = new Button(parent, SWT.CHECK);
				GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(advancedButton);
				((GridLayout) parent.getLayout()).numColumns++;
				advancedButton.moveBelow(parent.getChildren()[0]);
				
				advancedButton.setText("Edit advanced settings before launch");
				advancedButton.setToolTipText(MessageFormat.format(
						"Before finishing the wizard, edit settings in the ''{0} Configurations'' dialog.",
						launchData.getModeLabel()));
				advancedButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						updateDebugEditButton();
					}
				});
			}
			
			// Thanks, JFace, for deleting validation messages on the first display
			mainPage.validatePage();
		}
	}
	
	@Override
	public boolean canFinish() {
		if (advancedEdit)
			return true;
		return super.canFinish();
	}	

	protected void updateDebugEditButton() {
		Button finishButton = findFinishButton();
		if (finishButton != null) {
			advancedEdit = advancedButton.getSelection();
			if (advancedEdit) {
				finishButton.setText("Edit");
				finishButton.setToolTipText("Click to accept settings and edit advanced settings.");
				getContainer().updateButtons();
			} else {
				finishButton.setText(launchData.getModeLabel());
				finishButton.setToolTipText("Click to accept settings and launch the program.");
				getContainer().updateButtons();
			}
		}
	}

	/**
	 * Thanks, SWT and JFace, for making this so difficult
	 * @return the Finish button
	 */
	private Button findFinishButton() {
		if (getContainer() instanceof Dialog) {
			return findFinishButton((Composite) ((Dialog) getContainer()).buttonBar);
		}
		return null;
	}

	/**
	 * @param buttonBar
	 * @return
	 */
	private Button findFinishButton(Composite parent) {
		for (Control kid : parent.getChildren()) {
			if (kid instanceof Button) {
				if (kid.getData() instanceof Integer && (Integer) kid.getData() == IDialogConstants.FINISH_ID) {
					return (Button) kid;
				}
			}
			else if (kid instanceof Composite) {
				Button button = findFinishButton((Composite) kid);
				if (button != null)
					return button;
			}
		}
		return null;
	}

	@Override
	public boolean performFinish() {
		hasFinished = true;
		return true;
	}

	public boolean shouldOpenLaunchConfigurationDialog() {
		return hasFinished && advancedEdit;
	}

	public ILaunchConfigurationWorkingCopy getLaunchConfiguration() {
		if (!hasFinished)
			return null;
		
		ILaunchConfigurationWorkingCopy config = null;
		try {
			config = launchData.createConfiguration();
		} catch (CoreException e) {
			LaunchPlugin.log(e);
		}
		
		return config;
	}

	public void init() {
	}

	public int openWizard(Shell shell) {
		WizardDialog dialog = new WizardDialog(shell, this);
		return dialog.open();
	}
}
