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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.core.executables.Executable;
import org.eclipse.cdt.debug.core.executables.ExecutablesManager;
import org.eclipse.cdt.debug.core.executables.IExecutablesChangeListener;
import org.eclipse.cdt.launch.AbstractCLaunchDelegate;
import org.eclipse.cdt.launch.ui.CLaunchConfigurationTab;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.cdt.debug.cw.symbian.SettingsData;
import com.nokia.cdt.internal.debug.launch.LaunchPlugin;
import com.nokia.cdt.internal.debug.launch.NokiaAbstractLaunchDelegate;

import cwdbg.PreferenceConstants;

public class ExecutablesTab extends CLaunchConfigurationTab implements IExecutablesChangeListener {

	public class ExecutablesTabHeader extends Composite {

		/**
		 * Create the composite
		 * 
		 * @param parent
		 * @param style
		 */
		public ExecutablesTabHeader(Composite parent, int style) {
			super(parent, style);
			final GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 2;
			setLayout(gridLayout);

			final Label specifyWhichExecutablesLabel = new Label(this, SWT.NONE);
			specifyWhichExecutablesLabel.setText(Messages.getString("ExecutablesTab.0")); //$NON-NLS-1$
			new Label(this, SWT.NONE);

			targetingRulesCombo = new Combo(this, SWT.READ_ONLY);
			targetingRulesCombo.setItems(new String[] { Messages.getString("ExecutablesTab.1"), //$NON-NLS-1$
					Messages.getString("ExecutablesTab.2"), Messages.getString("ExecutablesTab.3") }); //$NON-NLS-1$ //$NON-NLS-2$
			
			if (supportsTargetAll)
			{
				targetingRulesCombo.add(Messages.getString("ExecutablesTab.4")); //$NON-NLS-1$
			}
			targetingRulesCombo.setText(Messages.getString("ExecutablesTab.5")); //$NON-NLS-1$
			final GridData gd_targetingRulesCombo = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
			targetingRulesCombo.setLayoutData(gd_targetingRulesCombo);
			targetingRulesCombo.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					changeTargetingRule(targetingRulesCombo.getSelectionIndex());
					ExecutablesTab.this.setDirty(true);
					updateLaunchConfigurationDialog();
				}
			});
			//
		}

	}

	private Combo targetingRulesCombo;
	private ExecutablesBlock executablesBlock;
	private ILaunchConfiguration configuration;
	private Button addButton;
	private Button selectAllButton;
	private Button unselectAllButton;
	private int targetingRule;
	private boolean supportsTargetAll;
	private boolean disposed;
	private boolean wantsMainExecutable;
	private List<ExeFileToDebug> executablesToTarget;

	public ExecutablesTab(boolean supportsTargetAll) {
		this.supportsTargetAll = supportsTargetAll;
		executablesToTarget = Collections.emptyList(); // avoid NPE if accessed before filled
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), LaunchTabHelpIds.ALL_EXECUTABLES);

		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		comp.setLayout(layout);

		ExecutablesTabHeader header = new ExecutablesTabHeader(comp, SWT.NONE);
		header.setLayoutData(new GridData(GridData.FILL_HORIZONTAL, GridData.FILL_VERTICAL, true, false, 3, 1));

		executablesBlock = new ExecutablesBlock(this, wantsMainExecutable);
		executablesBlock.createControl(comp);
		Control control = executablesBlock.getControl();
		GridData data = new GridData(GridData.FILL_BOTH);
		control.setLayoutData(data);

		Composite buttonComp = new Composite(comp, SWT.NONE);
		buttonComp.setLayout(new GridLayout(3, false));
		buttonComp.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 3, 1));

		addButton = new Button(buttonComp, SWT.PUSH);
		addButton.setText(Messages.getString("ExecutablesTab.6")); //$NON-NLS-1$
		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(getShell(), SWT.NONE);
				dialog.setText(Messages.getString("ExecutablesTab.7")); //$NON-NLS-1$
				final String res = dialog.open();
				if (res != null) {
					Job importJob = new Job(Messages.getString("ExecutablesTab.8")) { //$NON-NLS-1$

						@Override
						public IStatus run(IProgressMonitor monitor) {
							ExecutablesManager.getExecutablesManager().importExecutables(new String[] { res }, monitor);
							return Status.OK_STATUS;
						}
					};
					importJob.schedule();
				}
			}
		});

		selectAllButton = new Button(buttonComp, SWT.PUSH);
		selectAllButton.setText(Messages.getString("ExecutablesTab.9")); //$NON-NLS-1$
		selectAllButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				executablesBlock.setAllChecked(true);
			}
		});

		unselectAllButton = new Button(buttonComp, SWT.PUSH);
		unselectAllButton.setText(Messages.getString("ExecutablesTab.10")); //$NON-NLS-1$
		unselectAllButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				executablesBlock.setAllChecked(false);
			}
		});

		ExecutablesManager.getExecutablesManager().addExecutablesChangeListener(this);
		Dialog.applyDialogFont(parent);
	}

	@Override
	public void dispose() {
		disposed = true;
		ExecutablesManager.getExecutablesManager().removeExecutablesChangeListener(this);
		super.dispose();
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
		this.configuration = configuration;
		wantsMainExecutable = SettingsData.isStopModeConfiguration(configuration);
		try {
			int targetingRule = configuration.getAttribute(SettingsData.LCS_ExecutableTargetingRule,
					SettingsData.LCS_ExeTargetingRule_AllInSDK); //$NON-NLS-1$
			targetingRulesCombo.select(targetingRule);
			changeTargetingRule(targetingRule);

		} catch (CoreException e) {
			LaunchPlugin.log(e);
		}
	}

	public static String getExecutablesToTarget(ILaunchConfiguration config, IProgressMonitor monitor) throws CoreException {
		int targetingRule = config.getAttribute(SettingsData.LCS_ExecutableTargetingRule, SettingsData.LCS_ExeTargetingRule_AllInSDK);
		String filesString = ""; //$NON-NLS-1$
		if (targetingRule == SettingsData.LCS_ExeTargetingRule_ExeList) {
			filesString = config.getAttribute(PreferenceConstants.J_PN_ExecutablesToDebug, ""); //$NON-NLS-1$			
		} else {
			List<ExeFileToDebug> exeFiles = ExecutablesTab.getExecutablesToTarget(targetingRule, config, monitor);
			filesString = ExecutablesTab.getExeFilesAsString(exeFiles.toArray(new ExeFileToDebug[exeFiles.size()]));
		}
		return filesString;
	}

	private static String getExeFilesAsString(ExeFileToDebug[] files) {
		String filesString = ""; //$NON-NLS-1$
		for (int i = 0; i < files.length; i++) {
			filesString += files[i].getExePath() + "," + (files[i].getEnabled() ? "1" : "0") + ","; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
		return filesString;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		int targetingRule = targetingRulesCombo.getSelectionIndex();
		configuration.setAttribute(SettingsData.LCS_ExecutableTargetingRule, targetingRule);
		if (targetingRule == SettingsData.LCS_ExeTargetingRule_ExeList) {
			String filesString = getExeFilesAsString(executablesBlock.getFiles());
			configuration.setAttribute(PreferenceConstants.J_PN_ExecutablesToDebug, filesString);
		}
		if (targetingRule == SettingsData.LCS_ExeTargetingRule_All)
			configuration.setAttribute(
					PreferenceConstants.J_PN_SymbolLoadingRule,
					PreferenceConstants.J_PV_SymbolLoadingRule_All);
		else
			configuration.setAttribute(
					PreferenceConstants.J_PN_SymbolLoadingRule,
					PreferenceConstants.J_PV_SymbolLoadingRule_Auto);
		
		if (!wantsMainExecutable) {
			// get the current program name because it needs to be set to some executable to target
			String programName = null;
			try {
				programName = AbstractCLaunchDelegate.getProgramName(configuration);
			} catch (CoreException e) {
			}
			
			// only do this when the current program name is not empty.  if it is, we'll be changing it
			// which causes the apply button to become enabled which is not expected behavior.  this will
			// be called later if/when they do specify the main program, so we'll make sure then that it's
			// actually being targeted.
			if (programName.length() > 0) {
				boolean resetProgramName = true;
				// check to see if the current program name is one of the executables to target
				for (ExeFileToDebug exeFileToDebug : executablesToTarget) {
					if (exeFileToDebug.getExePath().equalsIgnoreCase(programName)) {
						resetProgramName = false;
						break;
					}
				}
				if (resetProgramName) {
					// ensure one of the enabled files to target is set as the program name
					for (ExeFileToDebug exeFileToDebug : executablesToTarget) {
						if (exeFileToDebug.getEnabled()) {
							configuration.setAttribute(ICDTLaunchConfigurationConstants.ATTR_PROGRAM_NAME, exeFileToDebug.getExePath());
							break;
						}
					}
				}
			}
		}
	}

	private static List<ExeFileToDebug> getExecutablesForTheProject(ILaunchConfiguration configuration) {
		List<ExeFileToDebug> files = new ArrayList<ExeFileToDebug>();
		try {
			String launchExeName = AbstractCLaunchDelegate.getProgramName(configuration);
			IPath launchExePath = new Path(new File(launchExeName).getCanonicalPath()).removeLastSegments(1);
			
			ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(NokiaAbstractLaunchDelegate.getProject(configuration));
			if (cpi != null)
			{					
				List<ICarbideBuildConfiguration> buildConfigList = cpi.getBuildConfigurations();
				for (ICarbideBuildConfiguration currConfig : buildConfigList){
					for (IPath mmp : EpocEngineHelper.getMMPFilesForBuildConfiguration(currConfig)) {
						IPath hp = EpocEngineHelper.getHostPathForExecutable(currConfig, mmp);
						if (hp != null) {
							try {
								if (launchExePath.isPrefixOf(new Path(hp.toFile().getCanonicalPath())))
									files.add(new ExeFileToDebug(hp.toFile().getCanonicalPath(), true));
							} catch (IOException e) {}
						}
					}
				}
			}
		} catch (CoreException e1) { } catch (IOException e) { }
		return files;
	}

	static public List<ExeFileToDebug> getExecutablesToTarget(int targetingRule, ILaunchConfiguration launchConfig, IProgressMonitor monitor) {
		List<ExeFileToDebug> files = new ArrayList<ExeFileToDebug>();

		if (targetingRule == SettingsData.LCS_ExeTargetingRule_All) {
			files = getExecutablesForTheProject(launchConfig);
		}
		if (targetingRule == SettingsData.LCS_ExeTargetingRule_AllInProject) {
			files = getExecutablesForTheProject(launchConfig);
		}
		if (targetingRule == SettingsData.LCS_ExeTargetingRule_AllInSDK) {
			files = getExecutablesForTheSDK(launchConfig);
		}
		if (targetingRule == SettingsData.LCS_ExeTargetingRule_ExeList) {
			files = getExecutablesInConfiguration(launchConfig);
		}

		return files;

	}

	protected void changeTargetingRule(final int targetingRule) {

		this.targetingRule = targetingRule;
		final ExecutablesTab exeTab = this;
		if (configuration != null && !exeTab.isDisposed()) {
			UIJob refreshJob = new UIJob(Messages.getString("ExecutablesTab.12")) { //$NON-NLS-1$

				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {

					if (!exeTab.isDisposed()){
						executablesToTarget = getExecutablesToTarget(targetingRule, configuration, monitor);
						executablesBlock.setFiles(executablesToTarget);
						boolean enableSelectExe = targetingRule == SettingsData.LCS_ExeTargetingRule_ExeList;
						executablesBlock.setEnabled(enableSelectExe);
						addButton.setEnabled(enableSelectExe);
						selectAllButton.setEnabled(enableSelectExe);
						unselectAllButton.setEnabled(enableSelectExe);						
					}

					return Status.OK_STATUS;
				}
			};
			refreshJob.schedule();
		}

	}

	protected boolean isDisposed() {
		return disposed;
	}

	private static List<ExeFileToDebug> getExecutablesInConfiguration(ILaunchConfiguration configuration)
	{
		List<ExeFileToDebug> files = getExecutablesForTheSDK(configuration);
		
		String filesString;
		try {
			filesString = configuration.getAttribute(PreferenceConstants.J_PN_ExecutablesToDebug, ""); //$NON-NLS-1$
			if (filesString.length() > 0) {
				StringTokenizer tokenizer = new StringTokenizer(filesString, ","); //$NON-NLS-1$
				while (tokenizer.hasMoreTokens()) {
					String exe = tokenizer.nextToken();
					String enabled = tokenizer.nextToken();

					for (ExeFileToDebug exeFileToDebug : files) {
						if (exeFileToDebug.getExePath().equalsIgnoreCase(exe))
						{
							exeFileToDebug.setEnabled(enabled.equals("1")); //$NON-NLS-1$
							break;
						}
					}
				}
			}
		} catch (CoreException e) { } //$NON-NLS-1$
		return files;
	}
	
	private static List<ExeFileToDebug> getExecutablesForTheSDK(ILaunchConfiguration configuration) {
		List<ExeFileToDebug> files = new ArrayList<ExeFileToDebug>();
		try {
			String launchExeName = AbstractCLaunchDelegate.getProgramName(configuration);
			if (launchExeName == null)
				return Collections.emptyList();
			
			// ignore 'urel', 'udeb' and 'lib' directories when getting the binaries for the same target
			// removeLastSegments(2) will strip the filename and 'urel', 'udeb' or 'lib'
			IPath launchExeTargetPath = new Path(new File(launchExeName).getCanonicalPath()).removeLastSegments(2); 
			for (Executable executable : ExecutablesManager.getExecutablesManager().getExecutables(true)) {
				IPath exePath = executable.getPath();
				if (launchExeTargetPath.isPrefixOf(exePath))
						files.add(new ExeFileToDebug(exePath.toOSString(), true));
			}
		} catch (Exception e) {
			LaunchPlugin.log(e);
		}
		
		// now add the ones for the project as well since they may not be built yet and therefore
		// not returned by the executables manager.
		for (ExeFileToDebug exe : getExecutablesForTheProject(configuration)) {
			if (!files.contains(exe)) {
				files.add(exe);
			}
		}

		return files;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return Messages.getString("ExecutablesTab.Title"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getImage()
	 */
	public Image getImage() {
		return LaunchImages.get(LaunchImages.IMG_EXECUTABLES_TAB);
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

	public ILaunchConfiguration getLaunchConfiguration() {
		return configuration;
	}

	public void executablesChanged(List<Executable> executables) {
	}

	public void executablesListChanged() {
		changeTargetingRule(targetingRule);
	}
}
