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

import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

import java.io.File;

import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.launch.ui.CLaunchConfigurationTab;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import cwdbg.PreferenceConstants;

public class StopModeDebuggerTab extends CLaunchConfigurationTab {

	private Button breakAtMain;
	private Text mainBreakSymbol;
	
	private Label startAddressLabel;
	private Text startAddress; 
	protected Button debugFromStartButton;
	protected Button runFromStartButton;
	protected Button softAttachButton;
	private Button autoButton;
	private Button armButton;
	private Button thumbButton;
	
	private Button targetInitFile;
	private Text targetInitFilePath;
	private Button targetInitFileBrowse;
	
	private Button memoryConfigFile;
	private Text memoryConfigFilePath;
	private Button memoryConfigFileBrowse;
	
	private Button resetTarget;
	
	private Combo processorsList;
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.STOP_MODE_DEBUGGER);
		
		GridLayout topLayout = new GridLayout();
		topLayout.numColumns = 2;
		comp.setLayout(topLayout);

		createVerticalSpacer(comp, 2);
		createBooleanGroup(comp, 2);
		createStartUpOptionsGroup(comp, 2);
		createInitFileGroup(comp, 2);
		createArmModeGroup(comp, 2);

		Dialog.applyDialogFont(parent);
		checkControlState();
	}

	protected void createBooleanGroup(Composite parent, int colSpan) {
		
		breakAtMain = createCheckButton(parent, Messages.getString("StopModeDebuggerTab.0")); //$NON-NLS-1$
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 1;
		breakAtMain.setLayoutData(data);
		breakAtMain.setFont(parent.getFont());
		breakAtMain.setToolTipText(Messages.getString("StopModeDebuggerTab.1")); //$NON-NLS-1$
		breakAtMain.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
				checkControlState();
			}
		});

		mainBreakSymbol = new Text(parent, SWT.BORDER);
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 1;
		mainBreakSymbol.setLayoutData(data);
		mainBreakSymbol.setToolTipText(Messages.getString("StopModeDebuggerTab.1")); //$NON-NLS-1$
		mainBreakSymbol.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
	}
	
	protected void createStartUpOptionsGroup(Composite parent, int colSpan) {
		
		Group group = new Group(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = colSpan;
		group.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = colSpan;
		group.setLayoutData(gd);
		group.setText(Messages.getString("StopModeDebuggerTab.28")); //$NON-NLS-1$
		group.setFont(parent.getFont());
		group.setToolTipText(Messages.getString("StopModeDebuggerTab.29")); //$NON-NLS-1$

		softAttachButton = new Button(group, SWT.RADIO);
		softAttachButton.setText(Messages.getString("StopModeDebuggerTab.34")); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = colSpan;
		softAttachButton.setLayoutData(gd);
		softAttachButton.setToolTipText(Messages.getString("StopModeDebuggerTab.35")); //$NON-NLS-1$
		softAttachButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
				checkControlState();
			}
		});
		
		debugFromStartButton = new Button(group, SWT.RADIO);
		debugFromStartButton.setText(Messages.getString("StopModeDebuggerTab.30")); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = colSpan;
		debugFromStartButton.setLayoutData(gd);
		debugFromStartButton.setToolTipText(Messages.getString("StopModeDebuggerTab.31")); //$NON-NLS-1$
		debugFromStartButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
				checkControlState();
			}
		});

		runFromStartButton = new Button(group, SWT.RADIO);
		runFromStartButton.setText(Messages.getString("StopModeDebuggerTab.32")); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = colSpan;
		runFromStartButton.setLayoutData(gd);
		runFromStartButton.setToolTipText(Messages.getString("StopModeDebuggerTab.33")); //$NON-NLS-1$
		runFromStartButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
				checkControlState();
			}
		});

		
		startAddressLabel = new Label(group, SWT.NONE);
		startAddressLabel.setText(Messages.getString("StopModeDebuggerTab.36")); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = 1;
		startAddressLabel.setLayoutData(gd);
		startAddressLabel.setToolTipText(Messages.getString("StopModeDebuggerTab.37")); //$NON-NLS-1$

		startAddress = new Text(group, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		startAddress.setLayoutData(gd);
		startAddress.setToolTipText(Messages.getString("StopModeDebuggerTab.37")); //$NON-NLS-1$
		startAddress.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
				checkControlState();
			}
		});	
		
		resetTarget = createCheckButton(group, Messages.getString("StopModeDebuggerTab.16")); //$NON-NLS-1$
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = colSpan;
		resetTarget.setLayoutData(gd);
		resetTarget.setToolTipText(Messages.getString("StopModeDebuggerTab.17")); //$NON-NLS-1$
		resetTarget.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

	}

	protected void createInitFileGroup(Composite parent, int colSpan){
		
		Group group = new Group(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		group.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);		
		gd.horizontalSpan = colSpan;
		group.setLayoutData(gd);
		group.setText(Messages.getString("StopModeDebuggerTab.2")); //$NON-NLS-1$
		group.setFont(parent.getFont());
		group.setToolTipText(Messages.getString("StopModeDebuggerTab.3")); //$NON-NLS-1$

		
		final Label processorsLabel = new Label(group, SWT.NONE);
		processorsLabel.setText(Messages.getString("StopModeDebuggerTab.4")); //$NON-NLS-1$
		GridData data = new GridData();
		data.horizontalSpan = 1;
		processorsLabel.setLayoutData(data);
		processorsLabel.setToolTipText(Messages.getString("StopModeDebuggerTab.5")); //$NON-NLS-1$

		processorsList = new Combo(group, SWT.READ_ONLY);
		processorsList.setItems(new String[] { "ARM920T", "ARM926TEJ", "ARM1136J-S", "OMAP3xx", "OMAP15xx", "OMAP16xx", "OMAP24xx", "OMAP34xx", "Generic" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$
		data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		data.horizontalSpan = 2;
		processorsList.setLayoutData(data);
		processorsList.setToolTipText(Messages.getString("StopModeDebuggerTab.5")); //$NON-NLS-1$
		processorsList.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		targetInitFile = createCheckButton(group, Messages.getString("StopModeDebuggerTab.6")); //$NON-NLS-1$		
		data = new GridData();
		data.horizontalSpan = 1;
		targetInitFile.setLayoutData(data);
		targetInitFile.setToolTipText(Messages.getString("StopModeDebuggerTab.7")); //$NON-NLS-1$
		targetInitFile.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
				checkControlState();
			}
		});

		
		targetInitFilePath = new Text(group, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		targetInitFilePath.setLayoutData(data);
		targetInitFilePath.setToolTipText(Messages.getString("StopModeDebuggerTab.7")); //$NON-NLS-1$
		targetInitFilePath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		targetInitFileBrowse = createPushButton(group, Messages.getString("StopModeDebuggerTab.8"), null); //$NON-NLS-1$
		targetInitFileBrowse.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("StopModeDebuggerTab.9")); //$NON-NLS-1$
				
				dialog.setFilterExtensions(new String[] {"*.cfg*", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
				dialog.setFilterNames(new String[] {Messages.getString("StopModeDebuggerTab.10"), Messages.getString("StopModeDebuggerTab.11")}); //$NON-NLS-1$ //$NON-NLS-2$

				String result = dialog.open();
				if (result != null && new File(result).exists()) {
					targetInitFilePath.setText(result);
					updateLaunchConfigurationDialog();
				}
			}
		});

		memoryConfigFile = createCheckButton(group, Messages.getString("StopModeDebuggerTab.12")); //$NON-NLS-1$;
		data = new GridData();
		data.horizontalSpan = 1;
		memoryConfigFile.setLayoutData(data);
		memoryConfigFile.setToolTipText(Messages.getString("StopModeDebuggerTab.13")); //$NON-NLS-1$
		memoryConfigFile.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
				checkControlState();
			}
		});

		memoryConfigFilePath = new Text(group, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		memoryConfigFilePath.setLayoutData(data);
		memoryConfigFilePath.setToolTipText(Messages.getString("StopModeDebuggerTab.13")); //$NON-NLS-1$
		memoryConfigFilePath.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		memoryConfigFileBrowse = createPushButton(group, Messages.getString("StopModeDebuggerTab.8"), null); //$NON-NLS-1$
		memoryConfigFileBrowse.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent evt) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

				dialog.setText(Messages.getString("StopModeDebuggerTab.14")); //$NON-NLS-1$
				dialog.setFilterExtensions(new String[] {"*.mem*", "*.*"}); //$NON-NLS-1$ //$NON-NLS-2$
				dialog.setFilterNames(new String[] {Messages.getString("StopModeDebuggerTab.15"), Messages.getString("StopModeDebuggerTab.11")}); //$NON-NLS-1$ //$NON-NLS-2$

				String result = dialog.open();
				if (result != null && new File(result).exists()) {
					memoryConfigFilePath.setText(result);
					updateLaunchConfigurationDialog();
				}
			}
		});		
	}

	protected void createArmModeGroup(Composite parent, int colSpan) {
		Group group = new Group(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		group.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = colSpan;
		group.setLayoutData(gd);
		group.setText(Messages.getString("StopModeDebuggerTab.18")); //$NON-NLS-1$
		group.setFont(parent.getFont());
		group.setToolTipText(Messages.getString("StopModeDebuggerTab.19")); //$NON-NLS-1$

		autoButton = new Button(group, SWT.RADIO);
		autoButton.setText(Messages.getString("StopModeDebuggerTab.22")); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = colSpan;
		autoButton.setLayoutData(gd);
		autoButton.setToolTipText(Messages.getString("StopModeDebuggerTab.19")); //$NON-NLS-1$
		autoButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		armButton = new Button(group, SWT.RADIO);
		armButton.setText(Messages.getString("StopModeDebuggerTab.20")); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = colSpan;
		armButton.setLayoutData(gd);
		armButton.setToolTipText(Messages.getString("StopModeDebuggerTab.19")); //$NON-NLS-1$
		armButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

		thumbButton = new Button(group, SWT.RADIO);
		thumbButton.setText(Messages.getString("StopModeDebuggerTab.21")); //$NON-NLS-1$
		gd = new GridData();
		gd.horizontalSpan = colSpan;
		thumbButton.setLayoutData(gd);
		thumbButton.setToolTipText(Messages.getString("StopModeDebuggerTab.19")); //$NON-NLS-1$
		thumbButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});

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
			breakAtMain.setSelection(configuration.getAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN , true ));
			mainBreakSymbol.setText(configuration.getAttribute( PreferenceConstants.J_PN_StopAtMainSymbol , "E32Main" )); //$NON-NLS-1$

			int startAddressValue = configuration.getAttribute( PreferenceConstants.J_PN_RomImgStartAddress , 0);
			startAddress.setText(new String("0x" + Integer.toHexString(startAddressValue))); //$NON-NLS-1$

			int debugMode = configuration.getAttribute( PreferenceConstants.J_PN_DebugRunFromStart, PreferenceConstants.J_PV_DebugRunFromStart_Debug);
			debugFromStartButton.setSelection(debugMode == PreferenceConstants.J_PV_DebugRunFromStart_Debug);
			runFromStartButton.setSelection(debugMode == PreferenceConstants.J_PV_DebugRunFromStart_Run);		
			softAttachButton.setSelection(debugMode == PreferenceConstants.J_PV_DebugRunFromStart_Attach);
			
			processorsList.select(configuration.getAttribute( PreferenceConstants.J_PN_TargetProcessor, 8 ));			
			targetInitFile.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_RunTargetInitFile, false ));
			targetInitFilePath.setText(configuration.getAttribute( PreferenceConstants.J_PN_TargetInitFilePath, "" )); //$NON-NLS-1$
			memoryConfigFile.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_RunMemConfigFile, false ));
			memoryConfigFilePath.setText(configuration.getAttribute( PreferenceConstants.J_PN_MemConfigFilePath, "" )); //$NON-NLS-1$
			resetTarget.setSelection(configuration.getAttribute( PreferenceConstants.J_PN_ResetTarget, false ));

			int defaultMode = configuration.getAttribute( PreferenceConstants.J_PN_DefaultInstructionSet, PreferenceConstants.J_PV_DefaultInstructionSet_Arm);
			autoButton.setSelection(defaultMode == PreferenceConstants.J_PV_DefaultInstructionSet_Auto);
			armButton.setSelection(defaultMode == PreferenceConstants.J_PV_DefaultInstructionSet_Arm);
			thumbButton.setSelection(defaultMode == PreferenceConstants.J_PV_DefaultInstructionSet_Thumb);
		
			checkControlState();
		} catch (CoreException e) {
			LaunchPlugin.log(e);
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		
		configuration.setAttribute( ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN, breakAtMain.getSelection());
		configuration.setAttribute( PreferenceConstants.J_PN_StopAtMainSymbol, mainBreakSymbol.getText());
		
		String startAddressText = startAddress.getText().trim().toLowerCase();
		
		int index = startAddressText.indexOf('x');
		if (index > 0)
		{
			startAddressText = startAddressText.substring(2); //ignore 0x or 0X.
		}
		try {
			Long longValue = Long.parseLong(startAddressText, 16);
			configuration.setAttribute( PreferenceConstants.J_PN_RomImgStartAddress , longValue.intValue());
		} catch (NumberFormatException e) {
			setErrorMessage(Messages.getString("StopModeDebuggerTab.38")); //$NON-NLS-1$	
			return;
		}
		
		if (debugFromStartButton.getSelection())
			configuration.setAttribute( PreferenceConstants.J_PN_DebugRunFromStart, PreferenceConstants.J_PV_DebugRunFromStart_Debug);
		else if (runFromStartButton.getSelection())
			configuration.setAttribute( PreferenceConstants.J_PN_DebugRunFromStart, PreferenceConstants.J_PV_DebugRunFromStart_Run);
		else if (softAttachButton.getSelection())
			configuration.setAttribute( PreferenceConstants.J_PN_DebugRunFromStart, PreferenceConstants.J_PV_DebugRunFromStart_Attach);

		configuration.setAttribute( PreferenceConstants.J_PN_TargetProcessor, processorsList.getSelectionIndex());
		configuration.setAttribute( PreferenceConstants.J_PN_RunTargetInitFile, targetInitFile.getSelection());
		configuration.setAttribute( PreferenceConstants.J_PN_TargetInitFilePath, targetInitFilePath.getText());
		configuration.setAttribute( PreferenceConstants.J_PN_RunMemConfigFile, memoryConfigFile.getSelection());
		configuration.setAttribute( PreferenceConstants.J_PN_MemConfigFilePath, memoryConfigFilePath.getText());
		configuration.setAttribute( PreferenceConstants.J_PN_ResetTarget, resetTarget.getSelection());
		
		if (autoButton.getSelection())
			configuration.setAttribute( PreferenceConstants.J_PN_DefaultInstructionSet, PreferenceConstants.J_PV_DefaultInstructionSet_Auto);
		else if (armButton.getSelection())
			configuration.setAttribute( PreferenceConstants.J_PN_DefaultInstructionSet, PreferenceConstants.J_PV_DefaultInstructionSet_Arm);
		else if (thumbButton.getSelection())
			configuration.setAttribute( PreferenceConstants.J_PN_DefaultInstructionSet, PreferenceConstants.J_PV_DefaultInstructionSet_Thumb);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return Messages.getString("StopModeDebuggerTab.23"); //$NON-NLS-1$
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getImage()
	 */
	public Image getImage() {
		return LaunchImages.get(LaunchImages.IMG_VIEW_DEBUGGER_TAB);
	}

	protected void checkControlState()
	{
		mainBreakSymbol.setEnabled(breakAtMain.getSelection());
		startAddress.setEnabled(!softAttachButton.getSelection());
		resetTarget.setEnabled(!softAttachButton.getSelection());
		targetInitFilePath.setEnabled(targetInitFile.getSelection());
		memoryConfigFilePath.setEnabled(memoryConfigFile.getSelection());
	}

	public boolean isValid(ILaunchConfiguration config) {
		
		setErrorMessage(null);
		setMessage(null);
		
		boolean result = super.isValid(config);
		if (result) {
		
			if (!softAttachButton.getSelection())
			{
				String startAddressText = startAddress.getText().trim().toLowerCase();
				if (startAddressText.length() < 3) {
					setErrorMessage(Messages.getString("StopModeDebuggerTab.38")); //$NON-NLS-1$
					result = false;
				} else {
					try {
						
						int index = startAddressText.indexOf('x');
						if (index > 0)
						{
							startAddressText = startAddressText.substring(2); //ignore 0x or 0X.
						}
						Long longValue = Long.parseLong(startAddressText, 16);
						int startAddressValue = longValue.intValue();
					}
					catch (NumberFormatException e) {
						setErrorMessage(Messages.getString("StopModeDebuggerTab.38")); //$NON-NLS-1$
						result = false;
						return result;
					}
				}
			}
			
			if (targetInitFile.getSelection()) {
				// check if init file is specified, if specified, check if it exists or not.
				String initFilePath = targetInitFilePath.getText().trim();
				if (initFilePath.length() < 1) {
					setErrorMessage(Messages.getString("StopModeDebuggerTab.24")); //$NON-NLS-1$
					result = false;
				} else {
					if (!new File(initFilePath).exists()) {
						setErrorMessage(Messages.getString("StopModeDebuggerTab.25")); //$NON-NLS-1$
						result = false;
					}
				} 			
			}

			if (memoryConfigFile.getSelection()) {
				// check if memory config file is specified, if specified, check if it exists or not.
				String memConfigFile = memoryConfigFilePath.getText().trim();
				if (memConfigFile.length() < 1) {
					setErrorMessage(Messages.getString("StopModeDebuggerTab.26")); //$NON-NLS-1$
					result = false;
				} else {
					if (!new File(memConfigFile).exists()) {
						setErrorMessage(Messages.getString("StopModeDebuggerTab.27")); //$NON-NLS-1$
						result = false;
					}
				} 			
			}

		}

		return result;
	}

	@Override
	public void activated(ILaunchConfigurationWorkingCopy workingCopy) {
		super.activated(workingCopy);
		
		// forces page to get focus so that help works without having to select some control first.
		getControl().setFocus();
	}
}
