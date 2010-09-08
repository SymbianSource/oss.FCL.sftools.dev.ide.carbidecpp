/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.remoteconnections.settings.ui;

import java.io.File;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.IWizardContainer2;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Version;

import com.nokia.carbide.remoteconnections.Messages;
import com.nokia.carbide.remoteconnections.RemoteConnectionsActivator;
import com.nokia.carbide.remoteconnections.interfaces.AbstractConnectedService2;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatus.EStatus;
import com.nokia.carbide.remoteconnections.interfaces.IConnectedService.IStatusChangedListener;
import com.nokia.carbide.remoteconnections.interfaces.IConnection;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionFactory;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionFactory.IValidationErrorReporter;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionFactory2;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionFactory2.ISettingsChangedListener;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionType;
import com.nokia.carbide.remoteconnections.interfaces.IConnectionTypeProvider;
import com.nokia.carbide.remoteconnections.interfaces.IRemoteAgentInstallerProvider;
import com.nokia.carbide.remoteconnections.interfaces.IRemoteAgentInstallerProvider.IRemoteAgentInstaller;
import com.nokia.carbide.remoteconnections.interfaces.IRemoteAgentInstallerProvider.IRemoteAgentInstaller.IPackageContents;
import com.nokia.carbide.remoteconnections.interfaces.IService;
import com.nokia.carbide.remoteconnections.interfaces.IService2;
import com.nokia.carbide.remoteconnections.internal.api.IConnectedService2;
import com.nokia.carbide.remoteconnections.internal.registry.Registry;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.HostOS;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;
import com.nokia.cpp.internal.api.utils.core.Pair;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;

public class ConnectionSettingsPage extends WizardPage implements ISettingsChangedListener {
	
	public final class Tester extends Thread {
		@Override
		public void run() {
			((AbstractConnectedService2) connectedService).setExternalTesting();
			for (int i = 0; i < 3 && connectedService != null; i++) {
				connectedService.testStatus();
				try {
					if (i < 2)
						sleep(AbstractConnectedService2.TIMEOUT);
				} catch (InterruptedException e) {
					break;
				}
			}
			resetServiceTesting(false);
		}
	}

	private static final TreeNode LOADING_CONTENT_TREENODE = 
		new TreeNode(Messages.getString("ConnectionSettingsPage.GettingDataMessage")); //$NON-NLS-1$
	private static final TreeNode[] LOADING_CONTENT_INPUT = new TreeNode[] { LOADING_CONTENT_TREENODE };
	private static final String STATUS_NOT_TESTED = 
		Messages.getString("ConnectionSettingsPage.NotTestedStatusString"); //$NON-NLS-1$
	private static final Image FOLDER_ICON_IMG = 
		PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
				ISharedImages.IMG_OBJ_FOLDER).createImage();
	private static final String CONNECTION_PREFIX = "connection"; //$NON-NLS-1$
	private static final String INITIAL_NAME_FMT = "{0} {1}"; //$NON-NLS-1$
	private static final String UID = ".uid"; //$NON-NLS-1$
	private final SettingsWizard settingsWizard;
	private IConnectionType connectionType;
	private ComboViewer deviceOSComboViewer;
	private Group settingsGroup;
	private Composite setupTabComposite;
	private ComboViewer connectionTypeViewer;
	private Text nameText;
	private boolean modifiedName;
	private String generatedName;
	private boolean initialized;
	private Composite agentTestTabComposite;
	private ListViewer servicesListViewer;
	private Text serviceTestInfo;
	private Button serviceTestButton;
	private boolean isTesting;
	private Label statusLabel;
	private Text statusText;
	private IConnectionFactory connectionFactory;
	private IConnection connection;
	private IService service;
	private volatile IConnectedService connectedService;
	private IStatusChangedListener statusListener;
	private Tester tester;
	private SashForm installerSashForm;
	private TreeViewer installerTreeViewer;
	private Text installerInfoText;
	private Button installerSaveButton;
	private Button installButton;
	private String saveAsParent;
	private List<IRemoteAgentInstallerProvider> installerProviders;

	protected ConnectionSettingsPage(SettingsWizard settingsWizard) {
		super("settingspage"); //$NON-NLS-1$
		this.settingsWizard = settingsWizard;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		final TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		tabFolder.setData(UID, "ConnectionSettingsPage"); //$NON-NLS-1$
		
		createSetupTabComposite(tabFolder);
		createAgentTestTabComposite(tabFolder);
		createInstallTabComposite(tabFolder);
		
		RemoteConnectionsActivator.setHelp(tabFolder, ".connection_settings_page"); //$NON-NLS-1$
		
		setControl(tabFolder);
	}

	private void createSetupTabComposite(TabFolder tabFolder) {
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText(Messages.getString("ConnectionSettingsPage.SetupTabLabel")); //$NON-NLS-1$
		tabItem.setData(UID, "setupTab"); //$NON-NLS-1$
		setupTabComposite = new Composite(tabFolder, SWT.NONE);
		setupTabComposite.setLayout(new GridLayout(2, false));
		tabItem.setControl(setupTabComposite);
		
		boolean canEditConnection = !settingsWizard.isConnectionToEditDynamic();

		Label viewerLabel = new Label(setupTabComposite, SWT.NONE);
		viewerLabel.setText(Messages.getString("ConnectionTypePage.ViewerLabel")); //$NON-NLS-1$
		
		connectionTypeViewer = new ComboViewer(setupTabComposite, SWT.READ_ONLY);
		connectionTypeViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				Check.checkState(element instanceof IConnectionType);
				IConnectionType connectionType = (IConnectionType) element;
				return connectionType.getDisplayName() + " (" + getServicesString(connectionType) + ")"; //$NON-NLS-1$ //$NON-NLS-2$
			}
		});
		connectionTypeViewer.setContentProvider(new ArrayContentProvider());
		connectionTypeViewer.setInput(getConnectionTypes());
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		connectionTypeViewer.getControl().setLayoutData(gd);
		connectionTypeViewer.getControl().setData(UID, "viewer"); //$NON-NLS-1$
		connectionTypeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			private IConnectionType previousSelection;
			public void selectionChanged(SelectionChangedEvent event) {
				Object currentSelection = ((IStructuredSelection) event.getSelection()).getFirstElement();
				if (!currentSelection.equals(previousSelection)) {
					settingsWizard.connectionTypeChanged();
					previousSelection = (IConnectionType) currentSelection;
				}
			}
		});
		connectionTypeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				getWizard().getContainer().showPage(getNextPage());
			}
		});
		connectionTypeViewer.setSorter(new ViewerSorter() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				IConnectionType t1 = (IConnectionType) e1;
				IConnectionType t2 = (IConnectionType) e2;
				return t1.getDisplayName().compareToIgnoreCase(t2.getDisplayName());
			}
		});
		connectionTypeViewer.getCombo().select(getCurrentTypeIndex());
		connectionTypeViewer.getCombo().setEnabled(canEditConnection);

		Label nameLabel = new Label(setupTabComposite, SWT.NONE);
		nameLabel.setText(Messages.getString("ConnectionTypePage.NameLabel")); //$NON-NLS-1$
		nameText = new Text(setupTabComposite, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		nameText.setLayoutData(gd);
		nameText.setToolTipText(Messages.getString("ConnectionTypePage.NameTextToolTip")); //$NON-NLS-1$
		nameText.setData(UID, "nameText"); //$NON-NLS-1$
		nameText.setText(getInitialNameText());
		nameText.selectAll();
		nameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				modifiedName = true;
				setPageComplete(validatePage());
			}
		});
		nameText.setEnabled(canEditConnection);

		createSettingsGroup(setupTabComposite);
	}

	private void createAgentTestTabComposite(TabFolder tabFolder) {
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText(Messages.getString("ConnectionSettingsPage.AgentTestTabLabel")); //$NON-NLS-1$
		tabItem.setData(UID, "testTab"); //$NON-NLS-1$
		agentTestTabComposite = new Composite(tabFolder, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		agentTestTabComposite.setLayout(gridLayout);
		tabItem.setControl(agentTestTabComposite);
		
		Group group = new Group(agentTestTabComposite, SWT.NONE);
		group.setText(Messages.getString("ConnectionSettingsPage.ConnectionTestingLabel")); //$NON-NLS-1$
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(group);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, true).applyTo(group);
		
		createDeviceOSCombo(group);

		createServiceTestComposite(group);
	}

	private void createDeviceOSCombo(Composite parent) {
		Composite comboComposite = new Composite(parent, SWT.NONE);
		GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_composite.horizontalSpan = 2;
		comboComposite.setLayoutData(gd_composite);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		comboComposite.setLayout(gridLayout);

		Label deviceOSLabel = new Label(comboComposite, SWT.NONE);
		GridData gd_sdkLabel = new GridData();
		deviceOSLabel.setLayoutData(gd_sdkLabel);
		deviceOSLabel.setText(Messages.getString("ConnectionSettingsPage.DeviceOSLabel")); //$NON-NLS-1$
		
		deviceOSComboViewer = new ComboViewer(comboComposite, SWT.READ_ONLY);
		GridData gd_sdkcombo = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		gd_sdkcombo.widthHint = 150;
		deviceOSComboViewer.getCombo().setLayoutData(gd_sdkcombo);
		deviceOSComboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@SuppressWarnings("unchecked")
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) deviceOSComboViewer.getSelection();
				Pair<String, String> pair = (Pair<String, String>) selection.getFirstElement();
				setSelectionToInstallComposite(pair);
				if (connectedService instanceof IConnectedService2)
					((IConnectedService2) connectedService).setDeviceOS(pair.first, pair.second);
			}
		});
		deviceOSComboViewer.setContentProvider(new ArrayContentProvider());
		deviceOSComboViewer.setLabelProvider(new LabelProvider() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public String getText(Object element) {
				Check.checkState(element instanceof Pair);
				Pair<String, Version> pair = (Pair) element;
				return MessageFormat.format("{0} {1}", pair.first, pair.second); //$NON-NLS-1$
			}
		});
		deviceOSComboViewer.getControl().setToolTipText(Messages.getString("ConnectionSettingsPage.DeviceOSComboToolTip")); //$NON-NLS-1$
		deviceOSComboViewer.getControl().setData(UID, "deviceOSComboViewer"); //$NON-NLS-1$
	}
	
	private void createSettingsGroup(Composite parent) {
		settingsGroup = new Group(parent, SWT.NONE);
		GridLayout settingsLayout = new GridLayout();
		settingsGroup.setLayout(settingsLayout);
		GridData gd_settings = new GridData(GridData.FILL_HORIZONTAL);
		gd_settings.horizontalSpan = 2;
		settingsGroup.setLayoutData(gd_settings);
		settingsGroup.setText(Messages.getString("ConnectionSettingsPage.ConnectionSettingsGroupLabel")); //$NON-NLS-1$
	}

	private void createServiceTestComposite(Composite parent) {
		Composite serviceSelectionComposite = new Composite(parent, SWT.NONE);
		serviceSelectionComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		serviceSelectionComposite.setLayout(new GridLayout());
		Label label = new Label(serviceSelectionComposite, SWT.NONE);
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		label.setText(Messages.getString("ConnectionSettingsPage.ServicesListLabel")); //$NON-NLS-1$
		servicesListViewer = new ListViewer(serviceSelectionComposite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData gd_viewer = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		servicesListViewer.getControl().setLayoutData(gd_viewer);
		servicesListViewer.setContentProvider(new ArrayContentProvider());
		servicesListViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IService)
					return ((IService) element).getDisplayName();
				return null;
			}
		});
		servicesListViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) servicesListViewer.getSelection();
				IService curService = (IService) selection.getFirstElement();
				if (!curService.equals(service)) {
					service = curService;
					serviceTestButton.setEnabled(service.isTestable()); 
					resetServiceTesting(true);
				}
			}
		});
		servicesListViewer.getControl().setToolTipText(Messages.getString("ConnectionSettingsPage.ServicesListToolTip")); //$NON-NLS-1$
		servicesListViewer.getControl().setData(UID, "servicesListViewer"); //$NON-NLS-1$
		
		Composite testButtonComposite = new Composite(parent, SWT.NONE);
		testButtonComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		testButtonComposite.setLayout(new GridLayout());
		serviceTestInfo = new Text(testButtonComposite, SWT.READ_ONLY | SWT.WRAP);
		serviceTestButton = new Button(testButtonComposite, SWT.PUSH);
		GridData gd_button = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		serviceTestButton.setText(Messages.getString("ConnectionSettingsPage.StartServiceTestButtonLabel")); //$NON-NLS-1$
		Point minSize = serviceTestButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		gd_button.widthHint = Math.max(widthHint, minSize.x);
		GridData gd_text = new GridData(SWT.CENTER, SWT.FILL, true, true);
		gd_text.widthHint = gd_button.widthHint;
		serviceTestInfo.setLayoutData(gd_text);
		serviceTestInfo.setData(UID, "serviceTestInfo"); //$NON-NLS-1$
		serviceTestButton.setLayoutData(gd_button);
		serviceTestButton.setToolTipText(Messages.getString("ConnectionSettingsPage.ServiceTestButtonToolTip")); //$NON-NLS-1$
		serviceTestButton.setData(UID, "serviceTestButton"); //$NON-NLS-1$
		serviceTestButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (isTesting) {
					serviceTestButton.setText(Messages.getString("ConnectionSettingsPage.StartServiceTestButtonLabel")); //$NON-NLS-1$
					resetServiceTesting(true);
				}
				else {
					serviceTestButton.setText(Messages.getString("ConnectionSettingsPage.StopServiceTestButtonLabel")); //$NON-NLS-1$
					testService();
				}
			}
		});
		
		Composite statusComposite = new Composite(parent, SWT.NONE);
		statusComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		statusComposite.setLayout(new GridLayout());
		statusLabel = new Label(statusComposite, SWT.NONE);
		statusLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		statusLabel.setText(Messages.getString("ConnectionSettingsPage.StatusLabel")); //$NON-NLS-1$
		statusText = new Text(statusComposite, SWT.MULTI | SWT.READ_ONLY | SWT.BORDER | SWT.WRAP);
		statusText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		statusText.setText(STATUS_NOT_TESTED);
		statusText.setData(UID, "statusText"); //$NON-NLS-1$
	}

	private void createInstallTabComposite(TabFolder tabFolder) {
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText(Messages.getString("ConnectionSettingsPage.InstallTabLabel")); //$NON-NLS-1$
		tabItem.setData(UID, "installTab"); //$NON-NLS-1$
		Composite composite = new Composite(tabFolder, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		composite.setLayout(gridLayout);
		tabItem.setControl(composite);
		
		Composite installDebugAgentComposite = new Composite(tabFolder, SWT.NONE);
		installDebugAgentComposite.setLayout(new GridLayout(1, false));
		tabItem.setControl(installDebugAgentComposite);

		installerSashForm = new SashForm(installDebugAgentComposite, SWT.HORIZONTAL);
		GridData gd_sash = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_sash.widthHint = 420;
		gd_sash.heightHint = 280;
		installerSashForm.setLayoutData(gd_sash);

		installerTreeViewer = new TreeViewer(installerSashForm, SWT.BORDER);
		GridData gd_tree = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		installerTreeViewer.getTree().setLayoutData(gd_tree);
		installerTreeViewer.getControl().setData(UID, "installerTreeViewer"); //$NON-NLS-1$
		installerTreeViewer.setContentProvider(new TreeNodeContentProvider());
		installerTreeViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				TreeNode node = (TreeNode) element;
				Object value = node.getValue();
				if (value instanceof IRemoteAgentInstaller) {
					String label = ((IRemoteAgentInstaller) value).getLabel();
					return label == null ? Messages.getString("ConnectionSettingsPage.UnlabeledPackageLabel") : label; //$NON-NLS-1$
				}
				return value.toString();
			}
			
			@Override
			public Image getImage(Object element) {
				if (element.equals(LOADING_CONTENT_TREENODE))
					return null;
				
				TreeNode node = (TreeNode) element;
				Object value = node.getValue();
				if (value instanceof IRemoteAgentInstaller)
					return ((IRemoteAgentInstaller) value).getImage();
				
				return FOLDER_ICON_IMG;
			}
		});
		installerTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				TreeNode node = (TreeNode) ((StructuredSelection) event.getSelection()).getFirstElement();
				if (node == null)
					return;
				Object value = node.getValue();
				boolean isPackage = value instanceof IRemoteAgentInstaller;
				boolean installable = false;
				String information = null;
				if (isPackage) {
					IRemoteAgentInstaller installer = (IRemoteAgentInstaller) value;
					installable = installer.fileSupportsInstall();
					information = installer.getInformation();
				}
				if (information != null)
					installerInfoText.setText(information);
				else
					installerInfoText.setText(""); //$NON-NLS-1$
				installButton.setEnabled(isPackage && installable);
				installerSaveButton.setEnabled(isPackage);
			}
		});

		installerInfoText = new Text(installerSashForm, SWT.READ_ONLY | SWT.BORDER | SWT.WRAP);
		String errorText = Messages.getString("ConnectionSettingsPage.NoInstallerDataInfoString"); //$NON-NLS-1$
		errorText += "\n" + Messages.getString("ConnectionSettingsPage.NoInstallerDataInfoString2"); //$NON-NLS-1$ //$NON-NLS-2$
		installerInfoText.setText(errorText);
		installerInfoText.setData(UID, "installerInfoText"); //$NON-NLS-1$
		installerSashForm.setWeights(new int[] {160, 100 });

		Composite buttonsArea = new Composite(installDebugAgentComposite, SWT.NONE);
		buttonsArea.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
		gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		buttonsArea.setLayout(gridLayout);

		installerSaveButton = new Button(buttonsArea, SWT.NONE);
		final GridData gd_saveButton = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		gd_saveButton.widthHint = 125;
		installerSaveButton.setLayoutData(gd_saveButton);
		installerSaveButton.setText(Messages.getString("ConnectionSettingsPage.SaveButtonLabel")); //$NON-NLS-1$
		installerSaveButton.setEnabled(false);
		installerSaveButton.setToolTipText(Messages.getString("ConnectionSettingsPage.SaveButtonToolTip")); //$NON-NLS-1$
		installerSaveButton.setData(UID, "installerSaveButton"); //$NON-NLS-1$
		installerSaveButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						try {
							IRemoteAgentInstaller installer = getCurrentInstaller();
							saveAs(installer.getPackageContents(getContainer()));
						}
						catch (Exception e) {
							RemoteConnectionsActivator.logError(e);
						}
					}
				});
			}
		});

		installButton = new Button(buttonsArea, SWT.NONE);
		final GridData gd_installButton = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		gd_installButton.widthHint = 125;
		installButton.setLayoutData(gd_installButton);
		installButton.setText(Messages.getString("ConnectionSettingsPage.InstallButtonLabel")); //$NON-NLS-1$
		installButton.setEnabled(false);
		installButton.setToolTipText(Messages.getString("ConnectionSettingsPage.InstallButtonToolTip")); //$NON-NLS-1$
		installButton.setData(UID, "installButton"); //$NON-NLS-1$
		installButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						try {
							IRemoteAgentInstaller installer = getCurrentInstaller();
							attemptInstall(installer.getPackageContents(getContainer()));
						}
						catch (Exception e) {
							RemoteConnectionsActivator.logError(e);
						}
					}
				});
			}
		});
	}

	
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			if (!initialized) {
				initialized = true;
				connectionTypeViewer.setSelection(connectionTypeViewer.getSelection());
			}
			settingsWizard.connectionTypeChanged();
		}
	}

	public void updateDynamicUI() {
		IConnectionType currentConnectionType = getConnectionType();
		if (currentConnectionType != null && !currentConnectionType.equals(connectionType)) {
			setErrorMessage(null);
			setPageComplete(true);
			String buttonLabel = Messages.getString("ConnectionSettingsPage.StartServiceTestButtonLabel"); //$NON-NLS-1$
			serviceTestButton.setText(buttonLabel);
			connectionType = currentConnectionType;
			setTitle(MessageFormat.format(Messages.getString("ConnectionSettingsPage.PageTitleFmt"), connectionType.getDisplayName())); //$NON-NLS-1$
			setDescription(connectionType.getDescription());
			
			// update settings editing UI
			Control[] settingsUI = settingsGroup.getChildren();
			if (settingsUI.length > 0) {
				for (Control control : settingsUI) {
					control.dispose();
				}
			}
			connectionFactory = connectionType.getConnectionFactory();
			Map<String, String> initialSettings = null;
			IConnection connectionToEdit = settingsWizard.getConnectionToEdit();
			if (connectionToEdit != null && connectionToEdit.getConnectionType().equals(connectionType)) // show existing settings
				initialSettings = connectionToEdit.getSettings();
			IValidationErrorReporter errorReporter = new IValidationErrorReporter() {
				public void setErrorMessage(String newMessage) {
					ConnectionSettingsPage.this.setErrorMessage(newMessage);
					boolean noError = newMessage == null;
					setPageComplete(noError);
					String buttonLabel = Messages.getString("ConnectionSettingsPage.StartServiceTestButtonLabel"); //$NON-NLS-1$
					serviceTestButton.setText(buttonLabel);
				}
			};
			if (connectionFactory instanceof IConnectionFactory2) {
				((IConnectionFactory2)connectionFactory).createEditingUI(
						settingsGroup, errorReporter, initialSettings, this);
				settingsChanged();
			}
			else {
				connectionFactory.createEditingUI(settingsGroup, errorReporter, initialSettings);
				if (!modifiedName) {
					nameText.setText(getInitialNameText());
					modifiedName = false;
				}
			}
			settingsUI = settingsGroup.getChildren();
			if (settingsUI.length == 0) {
				CLabel label = new CLabel(settingsGroup, SWT.NONE);
				label.setText(MessageFormat.format(Messages.getString("ConnectionSettingsPage.NoSettingsString"), connectionType.getDisplayName())); //$NON-NLS-1$
				GridData gd = new GridData(SWT.LEFT, SWT.TOP, true, true);
				label.setLayoutData(gd);
			}
			else if (settingsWizard.isConnectionToEditDynamic()) {
				disableControls(settingsUI);
			}

			// update services list
			Collection<IService> compatibleServices = 
				Registry.instance().getCompatibleServices(connectionType);
			servicesListViewer.setInput(compatibleServices);
			if (!compatibleServices.isEmpty()) {
				servicesListViewer.getList().select(0);
				servicesListViewer.setSelection(servicesListViewer.getSelection());
			}
			servicesListViewer.getList().addFocusListener(new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					resetServiceTesting(true);
				}
			});
			
			Thread t = new Thread() {
				@Override
				public void run() {
					initializeInstallerData();
				}
			};
			t.start();
		}
		
		if (getControl().isVisible()) {
			setupTabComposite.layout(true, true);
			agentTestTabComposite.layout(true, true);
		}
		
		if (getControl().isVisible())
			((IWizardContainer2) getWizard().getContainer()).updateSize();
	}

	public void settingsChanged() {
		if (!modifiedName) {
			String currentName = nameText.getText();
			if (currentName == null || currentName.length() == 0 || currentName.equals(generatedName)) {
				String preferredName = connectionFactory.getSettingsFromUI().get(IConnectionFactory2.PREFERRED_CONNECTION_NAME);
				if (preferredName != null) {
					preferredName = ensureUniquePreferredName(preferredName);
					generatedName = preferredName;
					nameText.setText(preferredName);
				}
				else {
					nameText.setText(getInitialNameText());
				}
			}
			modifiedName = false;
		}
	}

	private void disableControls(Control[] controls) {
		for (Control control : controls) {
			if (control instanceof Composite)
				disableControls(((Composite) control).getChildren());
			control.setEnabled(false);
		}
	}

	private synchronized void initializeInstallerData() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				Control control = installerTreeViewer.getControl();
				if (control == null || control.isDisposed())
					return;
				
				if (installerTreeViewer.getContentProvider() == null)
					installerTreeViewer.setContentProvider(new TreeNodeContentProvider());
				installerTreeViewer.setInput(LOADING_CONTENT_INPUT);
				installerInfoText.setText(""); //$NON-NLS-1$
				deviceOSComboViewer.setInput(Collections.EMPTY_LIST);
			}
		});

		IConnectionTypeProvider connectionTypeProvider = RemoteConnectionsActivator.getConnectionTypeProvider();
		Collection<IService> services = connectionTypeProvider.getCompatibleServices(connectionType);
		getInstallerProviders(services);
		final TreeNode[] treeNodes = createTreeNodes(); // gets actual data from server
		
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				Control control = installerTreeViewer.getControl();
				if (control == null || control.isDisposed())
					return;
				
				// update installer tree
				if (installerTreeViewer.getContentProvider() == null)
					installerTreeViewer.setContentProvider(new TreeNodeContentProvider());
				installerTreeViewer.setInput(treeNodes);
				installerTreeViewer.refresh(true);
				
				if (treeNodes.length == 0) {
					String errorText;
					// TODO: the actual error condition needs to be recorded... 
					if (HostOS.IS_UNIX) {
						errorText = Messages.getString("ConnectionSettingsPage.NoInstallerSupport"); //$NON-NLS-1$
					} else {
						errorText = Messages.getString("ConnectionSettingsPage.NoInstallerDataInfoString"); //$NON-NLS-1$
						errorText += "\n" + Messages.getString("ConnectionSettingsPage.NoInstallerDataInfoString2"); //$NON-NLS-1$ //$NON-NLS-2$
					}
					installerInfoText.setText(errorText);
				}
				
				// update sdk combo
				List<Pair<String, String>> deviceOSPairs = createDeviceOSPairs();
				deviceOSComboViewer.setInput(deviceOSPairs);
				if (!deviceOSPairs.isEmpty()) {
					deviceOSComboViewer.getCombo().select(0);
					deviceOSComboViewer.setSelection(deviceOSComboViewer.getSelection());		
				}
				else {
					String errorLabel = Messages.getString("ConnectionSettingsPage.NoInstallerDataInfoString"); //$NON-NLS-1$
					deviceOSComboViewer.getCombo().setItems(new String[] {errorLabel});
					deviceOSComboViewer.getCombo().select(0);
					deviceOSComboViewer.getCombo().setEnabled(false);
				}
				deviceOSComboViewer.getCombo().addFocusListener(new FocusAdapter() {
					public void focusGained(FocusEvent e) {
						resetServiceTesting(true);
					}
				});
			}
		});

	}

	protected void setSelectionToInstallComposite(Pair<String, String> pair) {
		Object input = installerTreeViewer.getInput();
		if (input instanceof TreeNode[]) {
			TreeNode node = findTreeNodeForPair((TreeNode[]) input, pair);
			if (node != null) {
				installerTreeViewer.collapseAll();
				installerTreeViewer.setSelection(new StructuredSelection(node));
			}
		}
	}
	
	private TreeNode findTreeNodeForPair(TreeNode[] treeNodes, Pair<String, String> pair) {
		for (TreeNode treeNode : treeNodes) {
			Object value = treeNode.getValue();
			if (value instanceof IRemoteAgentInstaller) {
				TreeNode versionNode = treeNode.getParent();
				TreeNode familyNode = versionNode.getParent();
				if (ObjectUtils.equals(pair.first, familyNode.getValue()) && 
						ObjectUtils.equals(pair.second, versionNode.getValue()))
					return treeNode;
			}
			TreeNode[] children = treeNode.getChildren();
			if (children != null) {
				TreeNode treeNodeFromChildren = findTreeNodeForPair(children, pair);
				if (treeNodeFromChildren != null)
					return treeNodeFromChildren;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected void testService() {
		Map<String, String> settings = connectionFactory.getSettingsFromUI();
		boolean newConnection = connection == null || !connectionType.equals(connection.getConnectionType());
		if (newConnection) {
			if (connection != null)
				connection.dispose();
			connection = connectionFactory.createConnection(settings);
			connection.setDisplayName("TestConnection:"+connectionType.getDisplayName()); //$NON-NLS-1$
		}
		else {
			connection.updateSettings(settings);
		}
		if (newConnection || connectedService == null || !connectedService.getService().equals(service)) {
			disposeConnectedService();
			connectedService = 
				Registry.instance().createConnectedService(service, connection);
			IStructuredSelection selection = (IStructuredSelection) deviceOSComboViewer.getSelection();
			Pair<String, String> pair = (Pair<String, String>) selection.getFirstElement();
			if (pair != null && connectedService instanceof IConnectedService2) {
				((IConnectedService2) connectedService).setDeviceOS(pair.first, pair.second);
			}
			connectedService.addStatusChangedListener(statusListener = new IStatusChangedListener() {
				public void statusChanged(final IStatus status) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							if (!statusText.isDisposed())
								statusText.setText(status.getLongDescription());
							if (status.getEStatus().equals(EStatus.UP))
								resetServiceTesting(false);
						}
					});
				}
			});
			if (connectedService instanceof AbstractConnectedService2) {
				((AbstractConnectedService2) connectedService).setRunnableContext(getContainer());
				tester = new Tester();
				tester.start();
			}
			isTesting = true;
		}
	}

	@Override
	public void dispose() {
		disposeInstallerProviders();
		disposeConnectedService();
		if (connection != null)
			connection.dispose();
		super.dispose();
	}

	private void disposeConnectedService() {
		if (connectedService != null) {
			connectedService.removeStatusChangedListener(statusListener);
			connectedService.dispose();
			connectedService = null;
		}
	}
	
	public Map<String, String> getSettings() {
		if (connectionFactory == null) {
			IConnection connectionToEdit = settingsWizard.getConnectionToEdit();
			if (connectionToEdit == null || !connectionToEdit.getConnectionType().equals(getConnectionType())) {
				return null;
			}
			return connectionToEdit.getSettings();
		}
		return connectionFactory.getSettingsFromUI();
	}
	
	private void saveAs(IPackageContents packageContents) throws Exception {
		InputStream is = packageContents.getInputStream();
		if (is != null) {
			Shell shell = getShell();
			FileDialog dialog =  new FileDialog(shell, SWT.SAVE);
			dialog.setText(Messages.getString("ConnectionSettingsPage.SaveAsDialogTitle"));  //$NON-NLS-1$
			if (saveAsParent == null)
				saveAsParent = System.getProperty("user.home");  //$NON-NLS-1$
			BrowseDialogUtils.initializeFrom(dialog, new Path(saveAsParent).append(packageContents.getDefaultNameFileName()));
			dialog.setOverwrite(true); // prompt for overwrite
			String path = dialog.open();
			if (path != null) {
				IPath saveAsPath = new Path(path);
				saveAsParent = saveAsPath.removeLastSegments(1).toString();
				File file = saveAsPath.toFile();
				FileUtils.copyFile(is, file);
			}
		}
		else
			MessageDialog.openError(getShell(), "Package Error", MessageFormat.format("Cannot locate package file ({0})\n\n(server configuration problem?)", 
					packageContents.getDefaultNameFileName()));
	}
	
	private void attemptInstall(IPackageContents packageContents) throws Exception {
		File tempDir = FileUtils.getTemporaryDirectory();
		IPath path = new Path(tempDir.getAbsolutePath());
		IPath tempFilePath = path.append(packageContents.getDefaultNameFileName());
		File tempFile = tempFilePath.toFile();
		if (tempFile.exists())
			tempFile.delete();
		InputStream is = packageContents.getInputStream();
		if (is != null) {
			FileUtils.copyFile(is, tempFile);
			Program.launch(tempFile.getAbsolutePath());
		}
		else
			MessageDialog.openError(getShell(), "Package Error", MessageFormat.format(
					"Failed to download package ({0})\n\n(server configuration problem?)",
					packageContents.getDefaultNameFileName()));
	}
	

	private IRemoteAgentInstaller getCurrentInstaller() {
		TreeNode node = (TreeNode) ((StructuredSelection) installerTreeViewer.getSelection()).getFirstElement();
		if (node != null) {
			Object value = node.getValue();
			if (value instanceof IRemoteAgentInstaller)
				return (IRemoteAgentInstaller) value;
		}
		return null;
	}
	
	private synchronized TreeNode[] createTreeNodes() {
		Map<String, TreeNode> sdkFamilyToNodes = new LinkedHashMap<String, TreeNode>();
		for (IRemoteAgentInstallerProvider installerProvider : installerProviders) {
			List<String> familyNames = installerProvider.getSDKFamilyNames(null);
			for (String familyName : familyNames) {
				if (!sdkFamilyToNodes.containsKey(familyName))
					sdkFamilyToNodes.put(familyName, new TreeNode(familyName));
				TreeNode familyNode = sdkFamilyToNodes.get(familyName);
				createFamilySubNodes(familyNode, installerProvider);
			}
		}
		return (TreeNode[]) sdkFamilyToNodes.values().toArray(new TreeNode[sdkFamilyToNodes.values().size()]);
	}

	private void createFamilySubNodes(TreeNode familyNode, IRemoteAgentInstallerProvider installerProvider) {
		String familyName = familyNode.getValue().toString();
		List<String> versions = installerProvider.getVersions(familyName);
		List<TreeNode> childList = new ArrayList<TreeNode>();
		TreeNode[] children = familyNode.getChildren();
		if (children != null)
			childList.addAll(Arrays.asList(children));
		for (String version : versions) {
			TreeNode versionNode = getVersionNode(familyNode, version);
			if (versionNode == null) {
				versionNode = new TreeNode(version);
				versionNode.setParent(familyNode);
				childList.add(versionNode);
			}
			createInstallerNodes(versionNode, installerProvider);
		}
		familyNode.setChildren((TreeNode[]) childList.toArray(new TreeNode[childList.size()]));
	}
	
	private TreeNode getVersionNode(TreeNode familyNode, String version) {
		TreeNode[] children = familyNode.getChildren();
		if (children != null) {
			for (TreeNode node : children) {
				if (node.getValue().equals(version))
					return node;
			}
		}
		return null;
	}	

	private void createInstallerNodes(TreeNode versionNode, IRemoteAgentInstallerProvider installerProvider) {
		String familyName = versionNode.getParent().getValue().toString();
		String version = versionNode.getValue().toString();
		List<IRemoteAgentInstaller> installers = 
			installerProvider.getRemoteAgentInstallers(familyName, version);
		List<TreeNode> childList = new ArrayList<TreeNode>();
		TreeNode[] children = versionNode.getChildren();
		if (children != null)
			childList.addAll(Arrays.asList(children));
		for (IRemoteAgentInstaller installer : installers) {
			TreeNode installerNode = new TreeNode(installer);
			installerNode.setParent(versionNode);
			childList.add(installerNode);
		}
		versionNode.setChildren((TreeNode[]) childList.toArray(new TreeNode[childList.size()]));
	}
	
	private synchronized List<Pair<String, String>> createDeviceOSPairs() {
		List<Pair<String, String>> deviceOSPairs = new ArrayList<Pair<String, String>>();
		for (IRemoteAgentInstallerProvider installerProvider : installerProviders) {
			List<String> familyNames = installerProvider.getSDKFamilyNames(null);
			for (String familyName : familyNames) {
				List<String> versions = installerProvider.getVersions(familyName);
				for (String version : versions) {
					Pair<String, String> pair = new Pair<String, String>(familyName, version);
					if (!deviceOSPairs.contains(pair))
						deviceOSPairs.add(pair);
				}
			}
		}
		return deviceOSPairs;
	}
	
	private synchronized void getInstallerProviders(Collection<IService> services) {
		if (installerProviders != null) {
			// check to see if we already have this set of installer providers
			Set<String> serviceIds = new TreeSet<String>();
			for (IService service : services) {
				if (service.getInstallerProvider() != null)
					serviceIds.add(service.getIdentifier());
			}
			Set<String> existingServiceIds = new TreeSet<String>();
			for (IRemoteAgentInstallerProvider installerProvider : installerProviders) {
				existingServiceIds.add(installerProvider.getService().getIdentifier());
			}
			if (serviceIds.equals(existingServiceIds))
				return;
		}
		disposeInstallerProviders();
		installerProviders = new ArrayList<IRemoteAgentInstallerProvider>();
		for (IService service : services) {
			IRemoteAgentInstallerProvider installerProvider = service.getInstallerProvider();
			if (installerProvider != null)
				installerProviders.add(installerProvider);
		}
	}

	private synchronized void disposeInstallerProviders() {
		if (installerProviders != null) {
			for (IRemoteAgentInstallerProvider installerProvider : installerProviders) {
				installerProvider.dispose();
			}
			installerProviders.clear();
			installerProviders = null;
		}
	}
	
	private void resetServiceTesting(final boolean resetAll) {
		isTesting = false;
		if (service == null)
			return;
		// may be called from a test thread
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if (resetAll) {
					statusText.setText(STATUS_NOT_TESTED);
					serviceTestInfo.setText(service.getAdditionalServiceInfo());
					agentTestTabComposite.layout(true, true);
					boolean wantsDeviceOS = service.getInstallerProvider() != null;
					if (service instanceof IService2)
						wantsDeviceOS &= ((IService2) service).wantsDeviceOS();
					deviceOSComboViewer.getCombo().setEnabled(wantsDeviceOS);
				}
				disposeConnectedService();
				if (!serviceTestButton.isDisposed()) {
					String buttonLabel = Messages.getString("ConnectionSettingsPage.StartServiceTestButtonLabel"); //$NON-NLS-1$
					serviceTestButton.setText(buttonLabel);
				}
			}
		});
	}

	public IConnectionType getConnectionType() {
		return (IConnectionType) ((IStructuredSelection) connectionTypeViewer.getSelection()).getFirstElement();
	}
	
	public String getName() {
		return nameText.getText().trim();
	}
	
	@SuppressWarnings("unchecked")
	private int getCurrentTypeIndex() {
		IConnection connectionToEdit = settingsWizard.getConnectionToEdit();
		if (connectionToEdit != null) {
			Object input = connectionTypeViewer.getInput();
			if (input != null) {
				Collection<IConnectionType> connectionTypes = (Collection<IConnectionType>) input;
				for (int i = 0; i < connectionTypes.size(); i++) {
					IConnectionType connectionType = (IConnectionType) connectionTypeViewer.getElementAt(i);
					if (connectionToEdit.getConnectionType().equals(connectionType))
						return i;
				}
			}
		}
		return 0;
	}
	
	private Collection<IConnectionType> getConnectionTypes() {
		Collection<IConnectionType> connectionTypes = getValidConnectionTypes();
		IService serviceToRestrict = settingsWizard.getServiceToRestrict();
		if (serviceToRestrict != null) {
			List<IConnectionType> restrictedConnectionTypes = new ArrayList<IConnectionType>();
			Collection<String> compatibleConnectionTypeIds = 
				Registry.instance().getCompatibleConnectionTypeIds(serviceToRestrict);
			for (String connectionTypeId : compatibleConnectionTypeIds) {
				IConnectionType connectionType = 
					Registry.instance().getConnectionType(connectionTypeId);
				if (connectionTypes.contains(connectionType))
					restrictedConnectionTypes.add(connectionType);
			}
			return restrictedConnectionTypes;
		}
		
		return connectionTypes;
	}

	private String getInitialNameText() {
		IConnection connectionToEdit = settingsWizard.getConnectionToEdit();
		if (connectionToEdit != null) {
			generatedName = ""; //$NON-NLS-1$
			return connectionToEdit.getDisplayName();
		}
		
		long i = 1;
		while (true) {
			String name = MessageFormat.format(INITIAL_NAME_FMT, CONNECTION_PREFIX, Long.toString(i++));
			if (isNameUnique(name)) {
				generatedName = name;
				return name;
			}
		}
	}

	private String ensureUniquePreferredName(String preferredName) {
		if (isNameUnique(preferredName)) {
			return preferredName;
		}

		long i = 1;
		while (true) {
			String name = MessageFormat.format(INITIAL_NAME_FMT, preferredName, Long.toString(i++));
			if (isNameUnique(name)) {
				return name;
			}
		}
	}

	private String getServicesString(IConnectionType connectionType) {
		StringBuilder servicesString = new StringBuilder();
		Collection<IService> services = 
			Registry.instance().getCompatibleServices(connectionType);
		if (services == null || services.isEmpty())
			return ""; //$NON-NLS-1$
		for (Iterator<IService> iterator = services.iterator(); iterator.hasNext();) {
			servicesString.append(iterator.next().getDisplayName());
			if (iterator.hasNext())
				servicesString.append(", "); //$NON-NLS-1$
		}
		
		return MessageFormat.format(Messages.getString("ConnectionTypePage.SupportedServicesLabel"), new Object[] { servicesString.toString() }); //$NON-NLS-1$
	}

	private Collection<IConnectionType> getValidConnectionTypes() {
		// valid connection types have at least one compatible service, or are the actual connection type of the connection being edited
		IConnection connectionToEdit = settingsWizard.getConnectionToEdit();
		IConnectionType connectionTypeToEdit = connectionToEdit != null ? connectionToEdit.getConnectionType() : null;
		Collection<IConnectionType> allConnectionTypes = 
		Registry.instance().getConnectionTypes();
		Collection<IConnectionType> connectionTypes = new ArrayList<IConnectionType>();
		for (IConnectionType connectionType : allConnectionTypes) {
			if (!Registry.instance().getCompatibleServices(connectionType).isEmpty() ||
					connectionType.equals(connectionTypeToEdit))
				connectionTypes.add(connectionType);
		}
		return connectionTypes;
	}

	private boolean isNameUnique(String name) {
		boolean inUse = Registry.instance().connectionNameInUse(name);
		IConnection connectionToEdit = settingsWizard.getConnectionToEdit();
		if (connectionToEdit != null && inUse)
			inUse = !name.equals(connectionToEdit.getDisplayName());
		
		return !inUse;
	}

	private boolean validatePage() {
		setErrorMessage(null);
		String name = getName();
		boolean isValid = isNameUnique(name);
		if (!isValid) {
			setErrorMessage(MessageFormat.format(Messages.getString("ConnectionTypePage.ConnectionNameInUseError"), new Object[] { name } )); //$NON-NLS-1$
		}
		
		return isValid;
	}
	
}
