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

import java.io.File;
import java.text.MessageFormat;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.CProjectDescriptionEvent;
import org.eclipse.cdt.core.settings.model.ICProjectDescriptionListener;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ISISBuilderInfo;
import com.nokia.cdt.internal.debug.launch.newwizard.LaunchOptionsData.EExeSelection;
import com.nokia.cpp.internal.api.utils.core.PathUtils;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;

/**
 *	This dialog allows in-depth configuration of the debug/run process options.
 */
public class DebugRunProcessDialog extends AbstractLaunchSettingsDialog implements ICProjectDescriptionListener {
	private ComboViewer projectExecutableViewer;
	private Text remoteProgramEntry;
	private Button projectExecutableRadioButton;
	private Button remoteExecutableRadioButton;
	private Button attachToProcessRadioButton;
	
	private Label packageInfoLabel;
	private Button installPackageCheckbox;
	private Combo sisFile;
	private Text sisEdit;
	private Button sisBrowse;
	private Composite installPackageUI;
	
	protected DebugRunProcessDialog(Shell shell, LaunchOptionsData data) {
		super(shell, data);
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = initDialogArea(parent, 
				MessageFormat.format("Change {0} Process", data.getModeLabel()),
				data.isDebug() ? LaunchWizardHelpIds.WIZARD_DIALOG_CHANGE_DEBUG_PROCESS : 
					LaunchWizardHelpIds.WIZARD_DIALOG_CHANGE_RUN_PROCESS);


		createProcessSelector(composite);
		
		Label sep = new Label(composite, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(sep);
		
		createPackageConfiguration(composite);

		initUI();
		
		validate();

		return composite;
	}


	private void createProcessSelector(Composite composite) {
		Label label;
		
		label = new Label(composite, SWT.WRAP);
		label.setText(MessageFormat.format("{0} method:", data.getModeLabel()));
		label.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		
		GridDataFactory.fillDefaults().grab(true, false).applyTo(composite);
		
		Composite radioGroup = new Composite(composite, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(radioGroup);
		GridLayoutFactory.fillDefaults().extendedMargins(INDENT, 0, 0, 0).numColumns(2).applyTo(radioGroup);

		createProjectExecutableRadioButton(radioGroup);
		createRemoteExecutableRadioButton(radioGroup);
		
		label = new Label(radioGroup, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(label);
		
		createAttachToProcessRadioButton(radioGroup);
		
		String msg;
		if (data.isDebug())
			msg = "Configure how to debug the program.  The initial settings reflect the debug capabilities of the selected device and the SIS builder settings.";
		else
			msg = "Configure how to run the program.";
		setMessage(msg);
		
		switch (data.getExeSelection()) {
		case USE_PROJECT_EXECUTABLE:
			projectExecutableRadioButton.setSelection(true);
			break;
		case USE_REMOTE_EXECUTABLE:
			remoteExecutableRadioButton.setSelection(true);
			break;
		case ATTACH_TO_PROCESS:
			attachToProcessRadioButton.setSelection(true);
			break;
		}
		handleProjectExecutableRadioSelected();
		handleRemoteExecutableRadioSelected();
		handleAttachToProcessRadioSelected();
	}

	private void createPackageConfiguration(Composite composite) {
		Label label;
		
		label = new Label(composite, SWT.WRAP);
		label.setText(MessageFormat.format("Deploy method:", data.getModeLabel()));
		label.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
		
		packageInfoLabel = new Label(composite, SWT.WRAP);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(packageInfoLabel);
		composite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				packageInfoLabel.pack();
			}
		});
		
		installPackageCheckbox = new Button(composite, SWT.CHECK);
		GridDataFactory.fillDefaults().applyTo(installPackageCheckbox);
		
		installPackageCheckbox.setText("Install package before launch");
		installPackageUI = new Composite(composite, SWT.NONE);
		GridDataFactory.fillDefaults().indent(INDENT, 0).applyTo(installPackageUI);
		
		createSISContents(installPackageUI);
		
		
		installPackageCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				data.setInstallPackage(installPackageCheckbox.getSelection());
				updatePackageUI();
			}
		});
		
		
		if (data.requiresInstallPackage()) {
			installPackageCheckbox.setSelection(true);
		}
		
		updateSisFile();
		updatePackageUI();
	}
	
    protected void createSISContents(Composite composite) {
        GridLayoutFactory.fillDefaults().margins(6, 6).numColumns(2).applyTo(composite);
            
		final IProject project = data.getProject();
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			final Label sisLabel = new Label(composite, SWT.NONE);
			sisLabel.setText("SIS File to Install:");
			GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.CENTER).span(1, 1).applyTo(sisLabel);
			sisLabel.setToolTipText("Specify which SIS file to install on the phone prior to launching");
			sisLabel.setData(UID, "DebugRunProcessDialog.sisLabel");

			sisFile = new Combo(composite, SWT.READ_ONLY);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).span(1, 1).grab(true, false).applyTo(sisLabel);
			sisFile.setToolTipText("Specify which SIS file to install on the phone prior to launching"); 
			sisFile.add("None"); //$NON-NLS-1$
			sisFile.setData(UID, "DebugRunProcessDialog.sisFile");
			
			sisFile.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					updateSisFile();
				}
			});
			
			ICarbideBuildConfiguration config = cpi.getDefaultConfiguration();
			for (ISISBuilderInfo info : config.getSISBuilderInfoList()) {
				IPath sisPath = info.getSigningType() == ISISBuilderInfo.DONT_SIGN ? info.getUnsignedSISFullPath() : info.getSignedSISFullPath();
				sisFile.add(sisPath.toOSString());
			}
			
			// select the first sis file if any, otherwise select none
			if (sisFile.getItemCount() > 1) {
				sisFile.select(1);
			} else {
				sisFile.select(0);
			}

			// listen for events so we can detect if they click on the link below and add new sis info.
			CoreModel.getDefault().getProjectDescriptionManager().addCProjectDescriptionListener(this, CProjectDescriptionEvent.APPLIED);

			composite.addDisposeListener(new DisposeListener() {
				
				public void widgetDisposed(DisposeEvent e) {
					CoreModel.getDefault().getProjectDescriptionManager().removeCProjectDescriptionListener(DebugRunProcessDialog.this);
				}
			});
			
			Link link = new Link(composite, SWT.NONE);
			link.setText("<a>" + "Modify SIS builder settings for build configuration" + "...</a>");
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).span(2, 1).grab(true, false).applyTo(link);
			link.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					PreferencesUtil.createPropertyDialogOn(getShell(), project, "com.nokia.carbide.cdt.internal.builder.ui.CarbideBuildConfigurationsPage", null, null).open();
				}
			});
			link.setData(UID, "DebugRunProcessDialog.link");
		} else {
			// not a Carbide project, just an executable.  show a browse/edit combo
			// to let them select a sis file if they want to.
			final Label sisLabel = new Label(composite, SWT.NONE);
			sisLabel.setText("SIS File to Install:"); //$NON-NLS-1$
			GridDataFactory.swtDefaults().span(2, 1).applyTo(sisLabel);
			sisLabel.setToolTipText("Specify which SIS file to install on the phone prior to launching");
			sisLabel.setData(UID, "DebugRunProcessDialog.sisLabel");

			sisEdit = new Text(composite, SWT.BORDER);
			GridDataFactory.fillDefaults().span(1, 1).align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(sisEdit);
			sisEdit.setToolTipText("Specify which SIS file to install on the phone prior to launching");
			sisEdit.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					updateSisFile();
					validate();
				}
			});
			sisEdit.setData(UID, "DebugRunProcessDialog.sisEdit");

			sisBrowse = new Button(composite, SWT.NONE);
			sisBrowse.setText("Browse...");
			sisBrowse.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
			sisBrowse.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					FileDialog dialog = new FileDialog(getShell(), SWT.NONE);

					dialog.setText("Select installation file");
					dialog.setFilterExtensions(new String[] {"*.sis*", "*.*"});
					dialog.setFilterNames(new String[] {"Installation Files", "All Files"});

					BrowseDialogUtils.initializeFrom(dialog, sisEdit);

					String result = dialog.open();
					if (result != null && new File(result).exists()) {
						sisEdit.setText(result);
						updateSisFile();
						validate();
					}
				}
			});
			sisBrowse.setData(UID, "DebugRunProcessDialog.sisBrowse");
		}
    }
    
    
	/**
	 * 
	 */
	protected void updateSisFile() {
		String sisPath;
    	if (sisFile != null) {
        	sisPath = sisFile.getSelectionIndex() == 0 ? "" : sisFile.getText(); //$NON-NLS-1$
        	data.setSisPath(sisPath);
    	} else if (sisEdit != null) {
    		sisPath = sisEdit.getText();
    		data.setSisPath(sisPath);
    	}
	}


	private void updatePackageUI() {
		installPackageUI.setEnabled(data.isInstallPackage());
		for (Control kid : installPackageUI.getChildren())
			kid.setEnabled(data.isInstallPackage());
	}


	public void handleEvent(CProjectDescriptionEvent event) {
		if (getShell().isDisposed()) {
			return;
		}
		
		IProject project = event.getProject() ;
		
		if (project != data.getProject()) {
			return;
		}
		
		if (sisFile != null) {
			ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (cpi != null) {
				sisFile.removeAll();

				sisFile.add("None");
				
				ICarbideBuildConfiguration config = cpi.getDefaultConfiguration();
				for (ISISBuilderInfo info : config.getSISBuilderInfoList()) {
					IPath sisPath = info.getSigningType() == ISISBuilderInfo.DONT_SIGN ? info.getUnsignedSISFullPath() : info.getSignedSISFullPath();
					sisFile.add(sisPath.toOSString());
				}
				
				// select the first sis file if any, otherwise select none
				if (sisFile.getItemCount() > 1) {
					sisFile.select(1);
				} else {
					sisFile.select(0);
				}
			}
		}
	}
	
	
	
	
	
	
	
	protected void initUI() {
		projectExecutableViewer.setInput(data.getExes());
		
		if (data.getExeSelection() == EExeSelection.USE_PROJECT_EXECUTABLE && data.getExeSelectionPath() != null) {
			projectExecutableViewer.setSelection(new StructuredSelection(data.getExeSelectionPath()));
			projectExecutableRadioButton.forceFocus();
		}
		
		if (data.getExeSelection() == EExeSelection.USE_REMOTE_EXECUTABLE && data.getExeSelectionPath() != null) {
			IPath exeSelectionPath = createSuggestedRemotePath(data.getExeSelectionPath());
			remoteProgramEntry.setText(PathUtils.convertPathToWindows(exeSelectionPath));
			remoteExecutableRadioButton.forceFocus();
		}
		
		if (data.getExeSelection() == EExeSelection.ATTACH_TO_PROCESS) {
			attachToProcessRadioButton.forceFocus();
		}
	}


	private IPath createSuggestedRemotePath(IPath exeSelectionPath) {
		String filename = exeSelectionPath.lastSegment();
		return PathUtils.createPath("C:/sys/bin").append(filename);
	}

	/**
	 * Allow selecting an executable detected to be built by the program.
	 * @param radioGroup
	 */
	private void createProjectExecutableRadioButton(Composite radioGroup) {
		projectExecutableRadioButton = new Button(radioGroup, SWT.RADIO);
		GridDataFactory.fillDefaults().grab(false, false).applyTo(projectExecutableRadioButton);
		projectExecutableRadioButton.setText("Launch project &executable:");
		projectExecutableRadioButton.setData(UID, "radio_project_executable");
		
		projectExecutableViewer = new ComboViewer(radioGroup, SWT.READ_ONLY);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(projectExecutableViewer.getControl());
		projectExecutableViewer.getControl().setData(UID, "combo_project_executable");
		
		projectExecutableViewer.setContentProvider(new ArrayContentProvider());
		projectExecutableViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IPath)
					return ((IPath) element).lastSegment();
				return super.getText(element);
			}
		});
		
		projectExecutableRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleProjectExecutableRadioSelected();
			}

		});
		
		projectExecutableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			public void selectionChanged(SelectionChangedEvent event) {
				Object sel = ((IStructuredSelection) event.getSelection()).getFirstElement();
				if (sel instanceof IPath) {
					data.setExeSelectionPath((IPath) sel); 
					
					// track the default remote program from the executable, for easy editing
					if (remoteProgramEntry != null) {
						IPath exeSelectionPath = createSuggestedRemotePath(data.getExeSelectionPath());
						remoteProgramEntry.setText(PathUtils.convertPathToWindows(exeSelectionPath)); 
					}
					
					validate();
				}
			}
		});
	}

	private void handleProjectExecutableRadioSelected() {
		if (projectExecutableRadioButton.getSelection()) {
			projectExecutableViewer.getControl().setEnabled(true);
			data.setExeSelection(EExeSelection.USE_PROJECT_EXECUTABLE);
			
			validate();
		} else {
			projectExecutableViewer.getControl().setEnabled(false);
			// another button becomes active and sets the new launch process
		}
	}

	/**
	 * Allow user to enter an executable path.
	 * @param radioGroup
	 */
	private void createRemoteExecutableRadioButton(Composite radioGroup) {
		remoteExecutableRadioButton = new Button(radioGroup, SWT.RADIO);
		GridDataFactory.fillDefaults().grab(false, false).applyTo(remoteExecutableRadioButton);
		remoteExecutableRadioButton.setText("Launch &remote program:");
		
		remoteExecutableRadioButton.setData(UID, "radio_remote_program");
		
		remoteProgramEntry = new Text(radioGroup, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(remoteProgramEntry);
		
		remoteProgramEntry.setData(UID, "text_remote_program");
		
		remoteExecutableRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleRemoteExecutableRadioSelected();
			}

		});
		
		remoteProgramEntry.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				data.setExeSelectionPath(new Path(remoteProgramEntry.getText().trim()));
				validate();
			}
		});
		
	}

	private void handleRemoteExecutableRadioSelected() {
		if (remoteExecutableRadioButton.getSelection()) {
			remoteProgramEntry.setEnabled(true);
			data.setExeSelection(EExeSelection.USE_REMOTE_EXECUTABLE);
			String symbianPath = PathUtils.convertPathToWindows(remoteProgramEntry.getText());
			data.setExeSelectionPath(new Path(symbianPath));
			validate();
		} else {
			remoteProgramEntry.setEnabled(false);
			// another button becomes active and sets the new launch process
		}
	}

	/**
	 * Allow user to attach to a process.
	 * @param radioGroup
	 */
	private void createAttachToProcessRadioButton(Composite radioGroup) {
		attachToProcessRadioButton = new Button(radioGroup, SWT.RADIO);
		GridDataFactory.fillDefaults().grab(false, false).applyTo(attachToProcessRadioButton);
		attachToProcessRadioButton.setText("&Attach to process:");
		
		attachToProcessRadioButton.setData(UID, "radio_attach_to_process");
		
		Label label = new Label(radioGroup, SWT.WRAP);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.LEFT, SWT.CENTER).applyTo(label);
		
		label.setText("(selected at launch time)");
		
		attachToProcessRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleAttachToProcessRadioSelected();
			}

		});
		
	}
	
	private void handleAttachToProcessRadioSelected() {
		if (attachToProcessRadioButton.getSelection()) {
			data.setExeSelection(EExeSelection.ATTACH_TO_PROCESS);
			data.setExeSelectionPath(null);
			validate();
		} else {
			// another button becomes active and sets the new launch process
		}
	}

	@Override
	protected void validate() {
		IStatus status = Status.OK_STATUS;
		
		// check launch method
		IPath exePath = data.getExeSelectionPath(); 
		switch (data.getExeSelection()) {
		case USE_PROJECT_EXECUTABLE:
			if (exePath.isEmpty()) {
				status = error("The project builds no executables.");
			}
			break;
		case USE_REMOTE_EXECUTABLE:
			if (exePath.isEmpty()) {
				status = error("Enter a non-empty executable path.");
			} else {
				String exePathString = exePath.toString();
				char drive = exePathString.charAt(0);
				char colon = exePathString.length() < 2 ? 0x0 : exePathString.charAt(1);
				char root = exePathString.length() < 3 ? 0x0 : exePathString.charAt(2);
				char lastChar = exePathString.charAt(exePathString.length() - 1);
				if (!Character.isLetter(drive) || colon != ':' || (root != '\\' && root != '/') || 
						lastChar == '\\' || lastChar == '/' || lastChar == ':') { 
					status = error("The executable path must be absolute.");
				} else if (exePath.getFileExtension() == null) {
					status = warning("The executable path should end in a filename.");
				}
			}
			break;
		case ATTACH_TO_PROCESS:
			break;
		}
		
		// check SIS selection
		if (data.isInstallPackage()) {
	    	if (sisEdit != null) {
	    		String text = sisEdit.getText().trim();
	    		if (text.length() > 0) {
	    			// empty is allowed, but if they specify something, make sure
	    			// it exists
	    			File file = new File(text);
	    			if (!file.exists()) {
	    				status = error("The SIS file ''{0}'' does not exist.", text);
	    			}
	    		}
	    	}
		}
		
		updateStatus(status);
	}
}

