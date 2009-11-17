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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;

import com.nokia.cdt.internal.debug.launch.LaunchPlugin;

import cwdbg.PreferenceConstants;

public class SymbianExceptionsTab extends AbstractLaunchConfigurationTab {

	private Button panicCheckbox;
	private Combo panicCombo;
	private Button swExcCheckbox;
	private Combo swExcCombo;
	private Button hwExcCheckbox;
	private Combo hwExcCombo;
	
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);
		
		GridLayout layout = new GridLayout(2, true);
		comp.setLayout(layout);
		
		panicCheckbox = new Button(comp, SWT.CHECK);
		panicCheckbox.setText(Messages.getString("SymbianExceptionsTab.Panic")); //$NON-NLS-1$
		panicCheckbox.setToolTipText(Messages.getString("SymbianExceptionsTab.PanicToolTip")); //$NON-NLS-1$
		panicCheckbox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (panicCombo != null) {
					panicCombo.setEnabled(panicCheckbox.getSelection());
					updateLaunchConfigurationDialog();
				}
			}
		});

		panicCombo = new Combo(comp, SWT.DROP_DOWN | SWT.READ_ONLY);
		panicCombo.add(Messages.getString("SymbianExceptionsTab.DebugThreads")); //$NON-NLS-1$
		panicCombo.add(Messages.getString("SymbianExceptionsTab.AnyThread")); //$NON-NLS-1$
		panicCombo.select(0);
		panicCombo.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				updateLaunchConfigurationDialog();
			}
		});
		
		swExcCheckbox = new Button(comp, SWT.CHECK);
		swExcCheckbox.setText(Messages.getString("SymbianExceptionsTab.SwExc")); //$NON-NLS-1$
		swExcCheckbox.setToolTipText(Messages.getString("SymbianExceptionsTab.SwExcToolTip")); //$NON-NLS-1$
		swExcCheckbox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (swExcCombo != null) {
					swExcCombo.setEnabled(swExcCheckbox.getSelection());
					updateLaunchConfigurationDialog();
				}
			}
		});

		swExcCombo = new Combo(comp, SWT.DROP_DOWN | SWT.READ_ONLY);
		swExcCombo.add(Messages.getString("SymbianExceptionsTab.DebugThreads")); //$NON-NLS-1$
		swExcCombo.add(Messages.getString("SymbianExceptionsTab.AnyThread")); //$NON-NLS-1$
		swExcCombo.select(0);
		swExcCombo.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				updateLaunchConfigurationDialog();
			}
		});

		hwExcCheckbox = new Button(comp, SWT.CHECK);
		hwExcCheckbox.setText(Messages.getString("SymbianExceptionsTab.HwExc")); //$NON-NLS-1$
		hwExcCheckbox.setToolTipText(Messages.getString("SymbianExceptionsTab.HwExcToolTip")); //$NON-NLS-1$
		hwExcCheckbox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (hwExcCombo != null) {
					hwExcCombo.setEnabled(hwExcCheckbox.getSelection());
					updateLaunchConfigurationDialog();
				}
			}
		});

		hwExcCombo = new Combo(comp, SWT.DROP_DOWN | SWT.READ_ONLY);
		hwExcCombo.add(Messages.getString("SymbianExceptionsTab.DebugThreads")); //$NON-NLS-1$
		hwExcCombo.add(Messages.getString("SymbianExceptionsTab.AnyThread")); //$NON-NLS-1$
		hwExcCombo.select(0);
		hwExcCombo.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				updateLaunchConfigurationDialog();
			}
		});

		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.STOP_MODE_EXCEPTIONS);
		Dialog.applyDialogFont(parent);
	}

	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			panicCheckbox.setSelection(configuration.getAttribute(PreferenceConstants.J_PN_StopModeEnablePanics, true));
			panicCombo.select(configuration.getAttribute(PreferenceConstants.J_PN_StopModeEnablePanicsForDebugThreadsOnly, true) ? 0 : 1);
			panicCombo.setEnabled(panicCheckbox.getSelection());

			swExcCheckbox.setSelection(configuration.getAttribute(PreferenceConstants.J_PN_StopModeEnableSwExcs, true));
			swExcCombo.select(configuration.getAttribute(PreferenceConstants.J_PN_StopModeEnableSwExcsForDebugThreadsOnly, true) ? 0 : 1);
			swExcCombo.setEnabled(swExcCheckbox.getSelection());

			hwExcCheckbox.setSelection(configuration.getAttribute(PreferenceConstants.J_PN_StopModeEnableHwExcs, true));
			hwExcCombo.select(configuration.getAttribute(PreferenceConstants.J_PN_StopModeEnableHwExcsForDebugThreadsOnly, true) ? 0 : 1);
			hwExcCombo.setEnabled(hwExcCheckbox.getSelection());
		} catch (CoreException e) {
			LaunchPlugin.log(e);
		}
	}

	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(PreferenceConstants.J_PN_StopModeEnablePanics, panicCheckbox.getSelection());
		configuration.setAttribute(PreferenceConstants.J_PN_StopModeEnablePanicsForDebugThreadsOnly, panicCombo.getSelectionIndex() == 0);

		configuration.setAttribute(PreferenceConstants.J_PN_StopModeEnableSwExcs, swExcCheckbox.getSelection());
		configuration.setAttribute(PreferenceConstants.J_PN_StopModeEnableSwExcsForDebugThreadsOnly, swExcCombo.getSelectionIndex() == 0);

		configuration.setAttribute(PreferenceConstants.J_PN_StopModeEnableHwExcs, hwExcCheckbox.getSelection());
		configuration.setAttribute(PreferenceConstants.J_PN_StopModeEnableHwExcsForDebugThreadsOnly, hwExcCombo.getSelectionIndex() == 0);
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
	}

	public String getName() {
		return Messages.getString("SymbianExceptionsTab.name"); //$NON-NLS-1$
	}

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
