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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;

import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

public class ExceptionsTab extends AbstractLaunchConfigurationTab {

	private Button[] m_buttons = new Button[24];
	private String[] m_buttonPropertyNames = new String[24];
	private Button checkAll;
	private Button clearAll;

	final static String s_cwPanelName = Messages.getString("ExceptionsTab.0"); //$NON-NLS-1$
	private int m_buttAddIndex = 0;

	private final String[] m_exceptionNames = { 
			"0x40010005 Control-C", //$NON-NLS-1$
			"0xC000008F Float Inexact",		 //$NON-NLS-1$
			"0x40010008 Control-Break", //$NON-NLS-1$
			"0xC0000090 Float Invalid Op",		 //$NON-NLS-1$
			"0x80000002 Data Misaligned", //$NON-NLS-1$
			"0xC0000092 Float Stack", //$NON-NLS-1$
			"0xC0000005 Access Violation", //$NON-NLS-1$
			"0xC0000091 Float Overflow", //$NON-NLS-1$
			"0xC0000006 In Page Error", //$NON-NLS-1$
			"0xC0000093 Float Underflow", //$NON-NLS-1$
			"0xC0000017 No Memory", //$NON-NLS-1$
			"0xC0000094 Int Div by Zero", //$NON-NLS-1$
			"0xC000001D Illegal Instruction", //$NON-NLS-1$
			"0xC0000095 Int Overflow", //$NON-NLS-1$
			"0xC0000025 Noncontinuable", //$NON-NLS-1$
			"0xC0000096 Privileged Instr", //$NON-NLS-1$
			"0xC0000026 Invalid Disposition", //$NON-NLS-1$
			"0xC00000FD Stack Overflow", //$NON-NLS-1$
			"0xC000008C Array Bounds", //$NON-NLS-1$
			"0xC0000135 DLL Not Found", //$NON-NLS-1$
			"0x0000008D Float Denormal", //$NON-NLS-1$
			"0xC0000142 DLL Init Failed", //$NON-NLS-1$
			"0xC000008E Float Div by Zero", //$NON-NLS-1$
			"0xE06D7363 C++ Exception" //$NON-NLS-1$
	};

	private void addCheckBox(Composite parent, int exceptionIndex)
	{
		m_buttons[m_buttAddIndex] = new Button(parent, SWT.CHECK);
		m_buttons[m_buttAddIndex].setText(m_exceptionNames[exceptionIndex]);
		m_buttons[m_buttAddIndex].setToolTipText(Messages.getString("ExceptionsTab.1")); //$NON-NLS-1$
		m_buttons[m_buttAddIndex].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		m_buttonPropertyNames[m_buttAddIndex] = SettingsData.getExceptionPropertyNames()[exceptionIndex];
		m_buttAddIndex++;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.EMULATION_EXCEPTIONS);
		
		GridLayout topLayout = new GridLayout();
		topLayout.numColumns = 1;
		comp.setLayout(topLayout);

		createVerticalSpacer(comp, 1);

		Group group = new Group(comp, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		group.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		group.setLayoutData(gd);
		group.setText(Messages.getString("ExceptionsTab.25")); //$NON-NLS-1$
		group.setFont(comp.getFont());
		group.setToolTipText(Messages.getString("ExceptionsTab.1")); //$NON-NLS-1$

		for (int i = 0; i < m_exceptionNames.length; i++) {
			addCheckBox(group, i);			
		}

		Composite buttonsComp = new Composite(comp, SWT.NONE);
		GridLayout buttonsLayout = new GridLayout();
		buttonsLayout.numColumns = 3;
		buttonsLayout.marginHeight = 0;
		buttonsLayout.marginWidth = 0;
		buttonsComp.setLayout(buttonsLayout);
		GridData buttonsGd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		buttonsComp.setLayoutData(buttonsGd);

		checkAll = createPushButton(buttonsComp, Messages.getString("ExceptionsTab.26"), null); //$NON-NLS-1$
		checkAll.setToolTipText(Messages.getString("ExceptionsTab.2")); //$NON-NLS-1$
		checkAll.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				for (int i=0; i<m_buttons.length; i++) {
					m_buttons[i].setSelection(true);
				}
				updateLaunchConfigurationDialog();
			}
		});

		clearAll = createPushButton(buttonsComp, Messages.getString("ExceptionsTab.27"), null); //$NON-NLS-1$
		clearAll.setToolTipText(Messages.getString("ExceptionsTab.3")); //$NON-NLS-1$
		clearAll.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				for (int i=0; i<m_buttons.length; i++) {
					m_buttons[i].setSelection(false);
				}
				updateLaunchConfigurationDialog();
			}
		});

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
		for (int i=0; i<m_buttons.length; i++) {
			try {
				m_buttons[i].setSelection(configuration.getAttribute(m_buttonPropertyNames[i], false));
			} catch (CoreException e) {
				LaunchPlugin.log(e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		for (int i=0; i<m_buttons.length; i++) {
			configuration.setAttribute(m_buttonPropertyNames[i], m_buttons[i].getSelection());
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return Messages.getString("ExceptionsTab.28"); //$NON-NLS-1$
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getImage()
	 */
	public Image getImage() {
		return LaunchImages.get(LaunchImages.IMG_VIEW_EXCEPTIONS_TAB);
	}

	@Override
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
		super.activated(workingCopy);
		
		// forces page to get focus so that help works without having to select some control first.
		getControl().setFocus();
	}

}
