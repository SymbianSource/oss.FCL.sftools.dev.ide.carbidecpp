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
package com.nokia.carbide.cdt.internal.builder.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PropertyPage;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectModifier;
import com.nokia.carbide.cdt.builder.project.IEnvironmentVariable;
import com.nokia.carbide.cdt.builder.project.IEnvironmentVarsInfo;
import com.nokia.carbide.cdt.internal.api.builder.ui.ManageConfigurationsDialog;
import com.nokia.carbide.cdt.internal.builder.CarbideBuildConfiguration;
import com.nokia.carbide.cdt.internal.builder.EnvironmentVariable;

public class CarbideBuildConfigurationsPage extends PropertyPage {
	
	// need to override Combo for edited text
	// since feature request for image support in Combo had
	// been ignored since 2001 
	// https://bugs.eclipse.org/bugs/show_bug.cgi?id=4510
	private class BuildConfigCombo {
		Combo combo;

		public BuildConfigCombo(Composite parent, int style) {
			combo = new Combo(parent, style);
		}
		
		public void add(String config) {
			combo.add(CarbideBuildConfiguration.toMarkedConfig(config));
		}
		
		public String getItem(int index) {
			return CarbideBuildConfiguration.fromMarkedConfig(combo.getItem(index));
		}
				
		public String getText() {
			return CarbideBuildConfiguration.fromMarkedConfig(combo.getText());
		}

		public void addModifyListener(
				ModifyListener bldCongifComboModifyListener) {
			combo.addModifyListener(bldCongifComboModifyListener);
		}

		public void setLayoutData(GridData activeConfigGridData) {
			combo.setLayoutData(activeConfigGridData);
		}

		public void setVisibleItemCount(int i) {
			combo.setVisibleItemCount(i);
		}

		public void select(int index) {
			combo.select(index);
		}

		public void removeAll() {
			combo.removeAll();
		}
	}
	
	// sis builder tab
	SisFilesBlock sisFilesBlock;
	
	// Env vars tab
	private Button newEnvVarButton;
	private Button editEnvVarButton;
	private Button removeEnvVarButton;
	private TableViewer envVarsTableViewer;
	private Combo varUseCombo;
	
	private static final String VAR_USE_DEFAULT_STRING = "Default"; //$NON-NLS-1$
	private static final String VAR_USE_PREPEND_STRING = "Prepend"; //$NON-NLS-1$
	private static final String VAR_USE_APPEND_STRING = "Append"; //$NON-NLS-1$
	private static final String VAR_USE_REPLACE_STRING = "Replace"; //$NON-NLS-1$
	private static final String VAR_USE_UNDEFINE_STRING = "Undefine"; //$NON-NLS-1$
	
	private static final String UNDEFINED_VAR = "[undefined]"; //$NON-NLS-1$
	
	private List<IEnvironmentVariable> envVarList = new ArrayList<IEnvironmentVariable>(); // The list of variables in the table viewer - contains a mix of default system values and override values
	private List<IEnvironmentVariable> envVarListOrig = new ArrayList<IEnvironmentVariable>(); //  copy at ui load to compare changes
	
	// arguments tab
	ArgumentsTabComposite argumentsTabcomposite;
	
	// paths and symbols tab
	PathsAndSymbolsTabComposite pathsAndSymbolsTabComposite;

	// rom builder tab
	ROMBuilderTabComposite romBuilderTabComposite;
	
	// SBSv2 config data tab
	SBSv2BuildConfigTabComposite sbsv2BuildConfigTabComposite;
	
	// Configuration management/switching
	private IProject project;
	private BuildConfigCombo buildConfigurationCombo;
	private Button manageButton;

	private String lastSelectedConfigName = ""; //$NON-NLS-1$
	
	private ModifyListener bldCongifComboModifyListener = new ModifyListener() {
		public void modifyText(ModifyEvent e) {
			// do work here when config changes....
			handleConfigurationChangeEvent(true);
		}
	};
	
	protected Control createContents(Composite parent) {
		
		initProjectData();
		
		Composite topPane = new Composite(parent, SWT.NONE);
		topPane.setLayout(new GridLayout());
		topPane.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Group activeConfigurationGroup = new Group(topPane, SWT.NONE);
		final GridData activeConfigGridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		activeConfigGridData.widthHint = 250;
		activeConfigurationGroup.setText(Messages.getString("CarbideBuildConfigurationsPage.Active_Configuration")); //$NON-NLS-1$
		activeConfigurationGroup.setLayoutData(activeConfigGridData);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		activeConfigurationGroup.setLayout(gridLayout);

		final Label buildConfigurationLabel = new Label(activeConfigurationGroup, SWT.NONE);
		buildConfigurationLabel.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Change_Config_ToolTip")); //$NON-NLS-1$
		buildConfigurationLabel.setText(Messages.getString("CarbideBuildConfigurationsPage.Configuration_Label")); //$NON-NLS-1$

		buildConfigurationCombo = new BuildConfigCombo(activeConfigurationGroup, SWT.READ_ONLY);
		buildConfigurationCombo.setVisibleItemCount(15);
		buildConfigurationCombo.setLayoutData(activeConfigGridData);
		
		buildConfigurationCombo.addModifyListener(bldCongifComboModifyListener);
		
		manageButton = new Button(activeConfigurationGroup, SWT.NONE);
		manageButton.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Manage_Button_ToolTip")); //$NON-NLS-1$
		manageButton.setText(Messages.getString("CarbideBuildConfigurationsPage.Manage")); //$NON-NLS-1$
		addButtonListener(manageButton);
		
		final TabFolder tabFolder = new TabFolder(topPane, SWT.NONE);
		final GridData tabFolderGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		tabFolderGridData.heightHint = 430;
		tabFolderGridData.widthHint = 550;
		tabFolder.setLayoutData(tabFolderGridData);
		
		////////////////////// SIS Builder Tab ////////////////
		final TabItem sisTabItem = new TabItem(tabFolder, SWT.NONE);
		sisTabItem.setText(Messages.getString("CarbideBuildConfigurationsPage.SIS_Builder_Tab")); //$NON-NLS-1$
		sisTabItem.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.SIS_Builder_Tab_ToolTip")); //$NON-NLS-1$

		final Composite sisComposite = new Composite(tabFolder, SWT.NONE);
		sisComposite.setLayout(new GridLayout(2, false));
		sisTabItem.setControl(sisComposite);

		sisFilesBlock = new SisFilesBlock(project);
		sisFilesBlock.createControl(sisComposite);
		Control control = sisFilesBlock.getControl();
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 1;
		data.horizontalIndent = 18;
		data.verticalIndent = 18;
		control.setLayoutData(data);

		//////////////////// SBSv2 Config Data Tab /////////////
		if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(project)) {
			
			TabItem sbsV2TabItem = new TabItem(tabFolder, SWT.NONE);
			sbsV2TabItem.setText(Messages.getString("CarbideBuildConfigurationsPage.SBSv2_Tab")); //$NON-NLS-1$
			sbsV2TabItem.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Environement_Tab_ToolTip")); //$NON-NLS-1$
			
			sbsv2BuildConfigTabComposite = new SBSv2BuildConfigTabComposite(sbsV2TabItem);
			sbsv2BuildConfigTabComposite.createControls();
			sbsV2TabItem.setControl(sbsv2BuildConfigTabComposite);
		}
		////////////////////////////////////////////////////////
		
		
		////////////////////// Environment Tab ////////////////
		TabItem envTabItem = new TabItem(tabFolder, SWT.NONE);
		envTabItem.setText(Messages.getString("CarbideBuildConfigurationsPage.Environment_Tab")); //$NON-NLS-1$
		envTabItem.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Environement_Tab_ToolTip")); //$NON-NLS-1$

		final Composite envVarsComposite = new Composite(tabFolder, SWT.NONE);
		envVarsComposite.setLayout(new GridLayout(2, false));
		envTabItem.setControl(envVarsComposite);

		final Group environmentVariablesGroup = new Group(envVarsComposite, SWT.NONE);
		environmentVariablesGroup.setText(Messages.getString("CarbideBuildConfigurationsPage.Env_Vars_Group")); //$NON-NLS-1$
		environmentVariablesGroup.setLayout(new GridLayout());
		environmentVariablesGroup.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, true));

		envVarsTableViewer = new TableViewer(environmentVariablesGroup, SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		Table table = envVarsTableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.DEFAULT, SWT.TOP, true, true));

		final TableColumn envVarTableColumn = new TableColumn(table, SWT.NONE);
		envVarTableColumn.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Env_Var_Col_ToolTip")); //$NON-NLS-1$
		envVarTableColumn.setWidth(100);
		envVarTableColumn.setText(Messages.getString("CarbideBuildConfigurationsPage.Variable")); //$NON-NLS-1$
		
		final TableColumn envVarValueColumn = new TableColumn(table, SWT.NONE);
		envVarValueColumn.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Value_Col_ToolTip")); //$NON-NLS-1$
		envVarValueColumn.setWidth(2048);
		envVarValueColumn.setText(Messages.getString("CarbideBuildConfigurationsPage.Value")); //$NON-NLS-1$
		
		envVarsTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				handleTableSelectionChanged(event);
			}
		});
		
		envVarsTableViewer.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				if (!envVarsTableViewer.getSelection().isEmpty()) {
					handleEnvAddEditButtonSelected(true);
				}
			}
		});
		
		envVarsTableViewer.setContentProvider(new EnvironmentVariableContentProvider());
		envVarsTableViewer.setLabelProvider(new EnvironmentVariableLabelProvider());
		
		final Composite envVarsButtonsComposite = new Composite(envVarsComposite, SWT.NONE);
		envVarsButtonsComposite.setLayout(new FormLayout());
		envVarsButtonsComposite.setLayoutData(new GridData(90, 200));

		newEnvVarButton = new Button(envVarsButtonsComposite, SWT.NONE);
		newEnvVarButton.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.New_Env_Var_ToolTip")); //$NON-NLS-1$
		final FormData formData = new FormData();
		formData.bottom = new FormAttachment(0, 30);
		formData.top = new FormAttachment(0, 5);
		formData.left = new FormAttachment(0, 5);
		formData.right = new FormAttachment(0, 85);
		newEnvVarButton.setLayoutData(formData);
		newEnvVarButton.setText(Messages.getString("CarbideBuildConfigurationsPage.New")); //$NON-NLS-1$
		addButtonListener(newEnvVarButton);
		
		editEnvVarButton = new Button(envVarsButtonsComposite, SWT.NONE);
		editEnvVarButton.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Modify_Env_Var_ToolTip")); //$NON-NLS-1$
		final FormData formData_1 = new FormData();
		formData_1.top = new FormAttachment(0, 35);
		formData_1.left = new FormAttachment(0, 5);
		formData_1.right = new FormAttachment(0, 85);
		editEnvVarButton.setLayoutData(formData_1);
		editEnvVarButton.setText(Messages.getString("CarbideBuildConfigurationsPage.Edit")); //$NON-NLS-1$
		addButtonListener(editEnvVarButton);
		
		removeEnvVarButton = new Button(envVarsButtonsComposite, SWT.NONE);
		removeEnvVarButton.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Undefine_ToolTip")); //$NON-NLS-1$
		final FormData formDataRemoveButton = new FormData();
		formDataRemoveButton.top = new FormAttachment(0, 65);
		formDataRemoveButton.left = new FormAttachment(0, 5);
		formDataRemoveButton.right = new FormAttachment(0, 85);
		removeEnvVarButton.setLayoutData(formDataRemoveButton);
		removeEnvVarButton.setText(Messages.getString("CarbideBuildConfigurationsPage.Undefine")); //$NON-NLS-1$
		addButtonListener(removeEnvVarButton);

		final Group variableUseGroup = new Group(envVarsComposite, SWT.NONE);
		variableUseGroup.setToolTipText(Messages.getString("CarbideBuildConfigurationsPage.Variable_Use_ToolTip")); //$NON-NLS-1$
		variableUseGroup.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		variableUseGroup.setText(Messages.getString("CarbideBuildConfigurationsPage.Variable_Use")); //$NON-NLS-1$
		variableUseGroup.setLayout(new GridLayout());

		varUseCombo = new Combo(variableUseGroup, SWT.READ_ONLY);
		varUseCombo.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));
		varUseCombo.add(VAR_USE_DEFAULT_STRING);
		varUseCombo.add(VAR_USE_PREPEND_STRING);
		varUseCombo.add(VAR_USE_APPEND_STRING);
		varUseCombo.add(VAR_USE_REPLACE_STRING);
		varUseCombo.add(VAR_USE_UNDEFINE_STRING);
		varUseCombo.select(0);
		new Label(envVarsComposite, SWT.NONE);
		
		addComboListener(varUseCombo);
		
		/////////////////// Build Options Tab /////////////////
		// only for SBSv1
		if (!CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(project)) {
			TabItem argumentsTabItem = new TabItem(tabFolder, SWT.NONE);
			argumentsTabItem.setText(Messages.getString("ArgumentsTab.Title")); //$NON-NLS-1$
			argumentsTabItem.setToolTipText(Messages.getString("ArgumentsTab.ToolTip")); //$NON-NLS-1$

			argumentsTabcomposite = new ArgumentsTabComposite(argumentsTabItem);
			argumentsTabcomposite.createControls();
			argumentsTabItem.setControl(argumentsTabcomposite);
		}

		/////////////////// Paths and Symbols Tab /////////////////
		TabItem pathsAndSymbolsTabItem = new TabItem(tabFolder, SWT.NONE);
		pathsAndSymbolsTabItem.setText(Messages.getString("PathsAndSymbolsTab.Title")); //$NON-NLS-1$
		pathsAndSymbolsTabItem.setToolTipText(Messages.getString("PathsAndSymbolsTab.ToolTip")); //$NON-NLS-1$

		pathsAndSymbolsTabComposite = new PathsAndSymbolsTabComposite(pathsAndSymbolsTabItem);
		pathsAndSymbolsTabComposite.createControls();
		pathsAndSymbolsTabItem.setControl(pathsAndSymbolsTabComposite);

		/////////////////// ROM Builder Tab /////////////////
		TabItem romBuilderTabItem = new TabItem(tabFolder, SWT.NONE);
		romBuilderTabItem.setText(Messages.getString("CarbideRomBuilderTab.Title")); //$NON-NLS-1$
		romBuilderTabItem.setToolTipText(Messages.getString("CarbideRomBuilderTab.ToolTip")); //$NON-NLS-1$

		romBuilderTabComposite = new ROMBuilderTabComposite(romBuilderTabItem);
		romBuilderTabComposite.createControls();
		romBuilderTabItem.setControl(romBuilderTabComposite);

		// read in the settings and populate the prefs...
		if (project != null) {
			loadConfigurationSettings();
			ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (cpi != null) {
				ICarbideBuildConfiguration config = cpi.getDefaultConfiguration();
				if (config != null) {
					sisFilesBlock.initData(config);
					if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(project)) {
						sbsv2BuildConfigTabComposite.initData(config);
					}
					if (argumentsTabcomposite != null) {
						argumentsTabcomposite.initData(config);
					}
					pathsAndSymbolsTabComposite.initData(config);
					romBuilderTabComposite.initData(config);
				}
			}
			setUpEnvVarsTable(new String[0], null);
			addEnvironementTableSelectionListener();
		}
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, CarbideCPPBuilderUIHelpIds.CARBIDE_BUILDER_CONFIGURATIONS_PAGE);

		return topPane;
	}
	
	/**
	 * Initialize global project data, configuration settings
	 *
	 */
	public void initProjectData() {
		project = getProject();
	}
	
	public IProject getProject() {
		Object tempElement = getElement();
		IProject project = null;
		if (tempElement != null) {
			if (tempElement instanceof IProject) {
				project = (IProject) tempElement;
			} else if (tempElement instanceof ICProject) {
				project = ((ICProject)tempElement).getProject();
			}
		}
			
		return project;
	}
	

	/**
	 * Content provider for the environment table
	 */
	protected class EnvironmentVariableContentProvider implements IStructuredContentProvider {

		public Object[] getElements(Object inputElement) {
			
			List tempList = (ArrayList)inputElement;
			Object[] obj=new Object[tempList.size()];
			
			int i = 0;
			ListIterator li = tempList.listIterator();
		    while (li.hasNext()) {
		        	obj[i++] = li.next();
		        }
			
			return obj;
		}
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			if (newInput == null) {
				return;
			}
			if (viewer instanceof TableViewer) {
				TableViewer tableViewer = (TableViewer)viewer;
				if (tableViewer.getTable().isDisposed()) {
					return;
				}
				tableViewer.setSorter(new ViewerSorter() {

					public int compare(Viewer iviewer, Object e1, Object e2) {
						if (e1 == null) {
							return -1;
						} else if (e2 == null) {
							return 1;
						} else {
							return ((IEnvironmentVariable)e1).getName().compareToIgnoreCase( ((IEnvironmentVariable)e2).getName());
						}
					}
				});
			}
		}
	}

	/**
	 * Label provider for the environment table
	 */
	public class EnvironmentVariableLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider, IFontProvider {

		public String getColumnText(Object element, int columnIndex) {
			String result = null;
			if (element != null && element instanceof IEnvironmentVariable) {
				IEnvironmentVariable var = (IEnvironmentVariable)element;
				switch (columnIndex) {
					case 0 : // variable
						result = var.getName();
						break;
					case 1 : // value
						result = var.getValue();
						break;
				}
			}
			return result;
		}
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		
		
		public Color getForeground(Object obj, int index) {
			return null;
		}
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IFontProvider#getFont(java.lang.Object)
		 */
		public Font getFont(Object obj) {
			if (obj instanceof IEnvironmentVariable) {
				IEnvironmentVariable eVar = (IEnvironmentVariable)obj;
				if (eVar.getUse() != IEnvironmentVariable.ENV_VAR_USE_DEFAULT) {
					return JFaceResources.getFontRegistry().getBold(
							envVarsTableViewer.getControl().getFont().toString()); // JFaceResources.DIALOG_FONT
				}
			}
			return null;
		}
		
		
		public Color getBackground(Object element, int columnIndex) {
			return null;
		}
	}

	public boolean performOk() {
		
		ICarbideProjectModifier cpm = CarbideBuilderPlugin.getBuildManager().getProjectModifier(project);
		if (cpm != null) {
			// check to see if the default build config has changed...
			String selectedConfigString = buildConfigurationCombo.getText();
			String defaultConfigName = cpm.getDefaultBuildConfigName();
			
			if (!selectedConfigString.equals(defaultConfigName)) {
				if (cpm.getNamedConfiguration(selectedConfigString) != null){
					cpm.setDefaultConfiguration(cpm.getNamedConfiguration(selectedConfigString));
				}
				cpm.saveChanges();
			}
			
			ICarbideBuildConfiguration selectedConfig = cpm.getNamedConfiguration(lastSelectedConfigName);
			if (selectedConfig != null) {
				// config may have been deleted
				if (!compareConfigurationSettings(selectedConfig, true)) {
					selectedConfig.saveConfiguration(true);
				}
			}
		}
		
		super.performOk();
		return true;
	}
	
	private void loadConfigurationSettings() {

		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			String defaultConfigName = cpi.getDefaultBuildConfigName();
			 List<ICarbideBuildConfiguration> buildConfigList = cpi.getBuildConfigurations();
			buildConfigurationCombo.removeAll();
			if (buildConfigList != null) {
				int i = 0; int index = 0;
				for (ICarbideBuildConfiguration currConfig : buildConfigList) {
					
					buildConfigurationCombo.add(currConfig.getDisplayString());

					if (defaultConfigName.equals(currConfig.getDisplayString())) {
						index = i;
					}
					i++;
				}
				buildConfigurationCombo.select(index);
				lastSelectedConfigName = buildConfigurationCombo.getText();
			}
		}
	}
	
	/**
	 * Sets the listener event to a button.
	 * 
	 * @param aButton
	 */
	private void addButtonListener( final Button aButton ) {
		SelectionListener listener = new SelectionAdapter() {
			public void widgetSelected( SelectionEvent e )  {
				if (e.getSource().equals(manageButton)) {
					handleManageButtonAction();
				}
				else if (e.getSource().equals(newEnvVarButton)) {
					handleNewEnvVar();
				}
				else if (e.getSource().equals(editEnvVarButton)) {
					handleEditEnvVar();
				}
				else if (e.getSource().equals(removeEnvVarButton)) {
					handleRemoveEnvVarButton();
				}
			}
		};
		aButton.addSelectionListener(listener);
	}
	
	private void addComboListener( final Combo combo ) {
		SelectionListener listener = new SelectionAdapter() {
			public void widgetSelected( SelectionEvent e )  {
				if (e.getSource().equals(varUseCombo)) {
					handleVarUseComboAction();
				}
				
			}
		};
		combo.addSelectionListener(listener);
	}
	
	private void addEnvironementTableSelectionListener() {
		envVarsTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			   public void selectionChanged(SelectionChangedEvent event) {

			       if(event.getSelection() instanceof IStructuredSelection) {
			           IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			           if (selection.size() == 1) {
			        	   IEnvironmentVariable envVar = (IEnvironmentVariable)selection.getFirstElement();
			        	   editEnvVarButton.setEnabled(true);
			        	   removeEnvVarButton.setEnabled(true);	
			        	   varUseCombo.setEnabled(true);
			        	   setVariableUseCombo(envVar);
			        
			           }else {
			        	   editEnvVarButton.setEnabled(false);
			        	   removeEnvVarButton.setEnabled(false);
			        	   varUseCombo.setEnabled(false);
			           }	
			       }
			   }
			} );
	}
	
	private void handleManageButtonAction() {
		
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			ManageConfigurationsDialog dlg = new ManageConfigurationsDialog(getShell(), cpi);
			if (dlg.open() == Window.OK) {
				buildConfigurationCombo.addModifyListener(bldCongifComboModifyListener);
				lastSelectedConfigName = buildConfigurationCombo.getText();
				loadConfigurationSettings();
				handleConfigurationChangeEvent(false);
				buildConfigurationCombo.addModifyListener(bldCongifComboModifyListener);
			}
		}
	}

	/**
	 * Check to see if any of the current configuration settings have changed in the UI.
	 * @param selectedConfig - Configuration currently working with
	 * @param writeToConfig - Some configuration don't update their object data in memory (on the UI) so this
	 * this parameter is used to insure that all the data in objects is up to date with the UI
	 * @return true if nothing has changed
	 */
	private boolean compareConfigurationSettings(ICarbideBuildConfiguration selectedConfig, boolean writeToConfig) {
		IEnvironmentVarsInfo envVarsInfo = selectedConfig.getEnvironmentVarsInfo();
		
		// Compare each of the settings to see if they are the same
		boolean sisSettingsEqual = sisFilesBlock.compareConfigurationSettings(selectedConfig, writeToConfig);
		
		boolean sbsv2ConfigEqual = true;
		if (sbsv2BuildConfigTabComposite != null) { 
			sbsv2ConfigEqual = sbsv2BuildConfigTabComposite.compareConfigurationSettings(selectedConfig, writeToConfig);
		}
		// Compare envVars settings
		boolean envVarsSettingsEqual = envVarList.size() == envVarListOrig.size() && envVarList.equals(envVarListOrig);
		if (!envVarsSettingsEqual && writeToConfig) {
			envVarsInfo.setModifiedEnvVarsList(getModifiedEnvVarList());
		}
		
		boolean argsSettingsEqual = true;
		if (argumentsTabcomposite != null) {
			argsSettingsEqual = argumentsTabcomposite.compareConfigurationSettings(selectedConfig, writeToConfig);
		}

		boolean pathsAndSynmbolsSettingsEqual = pathsAndSymbolsTabComposite.compareConfigurationSettings(selectedConfig, writeToConfig);

		boolean romBuilderSettingsEqual = romBuilderTabComposite.compareConfigurationSettings(selectedConfig, writeToConfig);

		return sisSettingsEqual && sbsv2ConfigEqual && envVarsSettingsEqual && argsSettingsEqual && pathsAndSynmbolsSettingsEqual && romBuilderSettingsEqual;
	}
	
	private void saveConfigurationSettings(ICarbideBuildConfiguration config) {
		
		compareConfigurationSettings(config, true);
		config.saveConfiguration(true);
		
	}
	
    private List<IEnvironmentVariable> setUpEnvVarsTable(String defaultVars[], List<IEnvironmentVariable> modEnvVarList) {
        
    	ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
    	if (cpi == null) {
    		return Collections.EMPTY_LIST;
    	}

    	ICarbideBuildConfiguration selectedConfig = cpi.getNamedConfiguration(lastSelectedConfigName);
    	if (defaultVars == null || defaultVars.length == 0) {
    		defaultVars = CarbideCPPBuilder.getResolvedEnvVars(selectedConfig);
    	}
    	if (defaultVars.length == 0) {
    		return Collections.EMPTY_LIST;
    	}
    	if (modEnvVarList == null) {
    		modEnvVarList = selectedConfig.getEnvironmentVarsInfo().getModifiedEnvVarsListFromSettings();
    		envVarListOrig.clear();
    	}
    	
    	envVarList.clear();
    	
    	Table table = envVarsTableViewer.getTable();
    	table.removeAll();
    	for (String currVar : defaultVars) {
    		boolean isMod = false;
    		// see if the modified env var from the settings is a raw var....
    		for (IEnvironmentVariable currModEnv : modEnvVarList) {
    			String[] rawSplit = currVar.split("="); //$NON-NLS-1$
    			if (rawSplit.length == 2) {
    				if (rawSplit[0].toUpperCase().equals(currModEnv.getName().toUpperCase())) {
    					// this var has been modified so add it to the UI list as such
    					isMod = true;
    	    			envVarList.add(new EnvironmentVariable(currModEnv.getName(), currModEnv.getValue(), currModEnv.getUse()));
       	    			envVarListOrig.add(new EnvironmentVariable(currModEnv.getName(), currModEnv.getValue(), currModEnv.getUse()));
    				}
    			}
    		}
    		
    		if (!isMod) {
    			// use the default value for this var.
	    		String[] varValueSplit = currVar.split("="); //$NON-NLS-1$
	    		if (varValueSplit.length == 2) {
	    			envVarList.add(new EnvironmentVariable(varValueSplit[0], varValueSplit[1], IEnvironmentVariable.ENV_VAR_USE_DEFAULT));
	    			envVarListOrig.add(new EnvironmentVariable(varValueSplit[0], varValueSplit[1], IEnvironmentVariable.ENV_VAR_USE_DEFAULT));
	    			
	    		} else {
	    			// can you have = in a variable ??
	    		}
    		}
    	}
    	envVarsTableViewer.setInput(envVarList);
    	envVarsTableViewer.getTable().select(0);

    	return envVarList;
    }
    
    private void handleNewEnvVar() {
    	handleEnvAddEditButtonSelected(false);
    }
    
    private void handleEditEnvVar() {
		IEnvironmentVariable selectedEnvVar = (IEnvironmentVariable)((IStructuredSelection)envVarsTableViewer.getSelection()).getFirstElement();
		if (selectedEnvVar != null) {
			handleEnvAddEditButtonSelected(true);
		}
	}
    
    private void handleRemoveEnvVarButton() {
		IEnvironmentVariable selectedEnvVar = (IEnvironmentVariable)((IStructuredSelection)envVarsTableViewer.getSelection()).getFirstElement();
		if (selectedEnvVar != null) {
			if (selectedEnvVar.getName().toUpperCase().equals(IEnvironmentVariable.EPOCROOT_ENV_VAR_NAME) ||
				selectedEnvVar.getName().toUpperCase().equals(IEnvironmentVariable.PATH_ENV_VAR_NAME) ||
				selectedEnvVar.getName().toUpperCase().equals(IEnvironmentVariable.MWCSYM2INCLUDES_ENV_VAR_NAME) ||
				selectedEnvVar.getName().toUpperCase().equals(IEnvironmentVariable.MWCSYM2LIBRARIES_ENV_VAR_NAME) ||
				selectedEnvVar.getName().toUpperCase().equals(IEnvironmentVariable.MWCSYM2LIBRARYFILES_ENV_VAR_NAME))  {
				// PATH and EPOCROOT cannot be undefined
				MessageDialog.openInformation(getShell(), Messages.getString("CarbideBuildConfigurationsPage.Undefined_Variable"), selectedEnvVar.getName() + " " + Messages.getString("CarbideBuildConfigurationsPage.Cant_Undefine_Var")); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				selectedEnvVar.setValue(UNDEFINED_VAR);
				selectedEnvVar.setUse(IEnvironmentVariable.ENV_VAR_USE_UNDEFINE);
				envVarsTableViewer.refresh();
				varUseCombo.setText(VAR_USE_UNDEFINE_STRING);
			}
		}
	}
    
    private void handleVarUseComboAction() {
		IEnvironmentVariable selectedEnvVar = (IEnvironmentVariable)((IStructuredSelection)envVarsTableViewer.getSelection()).getFirstElement();
		if (selectedEnvVar != null) {
			String varUseString = varUseCombo.getText();
			if (varUseString.equals(VAR_USE_UNDEFINE_STRING)) {
				if (selectedEnvVar.getName().toUpperCase().equals(IEnvironmentVariable.EPOCROOT_ENV_VAR_NAME) ||
					selectedEnvVar.getName().toUpperCase().equals(IEnvironmentVariable.PATH_ENV_VAR_NAME))  {
					// PATH and EPOCROOT cannot be undefined
					MessageDialog.openInformation(getShell(), Messages.getString("CarbideBuildConfigurationsPage.Undefined_Variable"), Messages.getString("CarbideBuildConfigurationsPage.Cant_Undefine_Var")); //$NON-NLS-1$ //$NON-NLS-2$
					return;
				} else {		
					selectedEnvVar.setValue(UNDEFINED_VAR);
					selectedEnvVar.setUse(IEnvironmentVariable.ENV_VAR_USE_UNDEFINE);
				}
			} else if (varUseString.equals(VAR_USE_APPEND_STRING)) {
				selectedEnvVar.setUse(IEnvironmentVariable.ENV_VAR_USE_APPEND);
			} else if (varUseString.equals(VAR_USE_REPLACE_STRING)) {
				selectedEnvVar.setUse(IEnvironmentVariable.ENV_VAR_USE_REPLACE);
			} else if (varUseString.equals(VAR_USE_PREPEND_STRING)) {
				selectedEnvVar.setUse(IEnvironmentVariable.ENV_VAR_USE_PREPEND);
			} else if (varUseString.equals(VAR_USE_DEFAULT_STRING)) {
				// Get the env var value and set it back to the default
				String[] rawEnvVars = CarbideCPPBuilder.getRawEnvVars();
				boolean foundVar = false;
				for (String currEnvVar : rawEnvVars) {
					String[] splitVar = currEnvVar.split("="); //$NON-NLS-1$
					if (splitVar.length == 2) {
						if (splitVar[0].toUpperCase().equals(selectedEnvVar.getName().toUpperCase())) {
							selectedEnvVar.setValue(splitVar[1]);
							selectedEnvVar.setUse(IEnvironmentVariable.ENV_VAR_USE_DEFAULT);
							foundVar = true;
							break;
						}
					}
				}
				if (!foundVar) {
					MessageDialog.openError(getShell(), Messages.getString("CarbideBuildConfigurationsPage.Cannot_set_default_value"), Messages.getString("CarbideBuildConfigurationsPage.The_system_variable") + selectedEnvVar.getName() + Messages.getString("CarbideBuildConfigurationsPage.does_not_exist") ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					setVariableUseCombo(selectedEnvVar);
				}
			} 
			
			envVarsTableViewer.refresh();
		}
    }
    
    /**
	 * Responds to a selection changed event in the environment table
	 * 
	 * @param event
	 *            the selection change event
	 */
	protected void handleTableSelectionChanged(SelectionChangedEvent event) {
		int size = ((IStructuredSelection)event.getSelection()).size();
		editEnvVarButton.setEnabled(size == 1);
		removeEnvVarButton.setEnabled(size > 0);
	}
	
	/**
	 * Creates an editor for the value of the selected environment variable.
	 * @param isEdit - True to edit selected var. False to create a new one
	 */
	protected void handleEnvAddEditButtonSelected(boolean isEdit) {
		IEnvironmentVariable var = null;
		if (isEdit) {
			// edit an existing var
			IStructuredSelection sel = (IStructuredSelection)envVarsTableViewer.getSelection();
			var = (IEnvironmentVariable)sel.getFirstElement();
		} else {
			// create  new var
			var = new EnvironmentVariable("", "", IEnvironmentVariable.ENV_VAR_USE_REPLACE); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (var == null) {
			return;
		}
		
		String originalName = var.getName();
		String originalValue = var.getValue();
		AddEditEnvVarDialog dialog;
		if (isEdit) {
			dialog = new AddEditEnvVarDialog(getShell(), "Edit Environement Variable"); //$NON-NLS-1$
		} else{
			dialog = new AddEditEnvVarDialog(getShell(), "Add Environement Variable"); //$NON-NLS-1$
		}
		dialog.addTextField(Messages.getString("CarbideBuildConfigurationsPage.Variable_Name"), originalName, false); //$NON-NLS-1$
		dialog.addVariablesField(Messages.getString("CarbideBuildConfigurationsPage.Variable_Value"), originalValue, false); //$NON-NLS-1$

		if (dialog.open() != Window.OK) {
			return;
		}
		String name = dialog.getStringValue(Messages.getString("CarbideBuildConfigurationsPage.Variable_Name")); //$NON-NLS-1$
		String newValue = dialog.getStringValue(Messages.getString("CarbideBuildConfigurationsPage.Variable_Value")); //$NON-NLS-1$
		
		if (!originalName.equals(name)) {
			// create a new variable and remove the old one
			EnvironmentVariable newVar = new EnvironmentVariable(name, newValue, IEnvironmentVariable.ENV_VAR_USE_REPLACE);
			addNewEnvironmentVariable(newVar);
			if (isEdit) {
				envVarsTableViewer.remove(var);
			}
		} else {
			// else just modify the existing if the name did not change.
			if (!originalValue.equals(newValue)) {
				var.setValue(newValue);
				var.setName(name);
				if (var.getUse() == IEnvironmentVariable.ENV_VAR_USE_DEFAULT) {
					var.setUse(IEnvironmentVariable.ENV_VAR_USE_REPLACE); 
				}
				if (isEdit) {
					addNewEnvironmentVariable(var);
				}
			}
		}
		
		envVarsTableViewer.refresh();
	}
	
	/**
	 * Adds a new EnvironmentVariable to the variable list. Removes any duplicates.
	 * @param newVar
	 * @return true on success
	 */
	private void addNewEnvironmentVariable(IEnvironmentVariable newVar) {
		
		for (IEnvironmentVariable currVar : envVarList) {
			if (currVar.getName().toUpperCase().equals(newVar.getName().toUpperCase())) {
				envVarList.remove(currVar);
				break;
			}
		}
		
		envVarList.add(newVar);
	}
	
	private void setVariableUseCombo(IEnvironmentVariable envVar) {
		switch (envVar.getUse()) {
			case IEnvironmentVariable.ENV_VAR_USE_APPEND:
			{
				varUseCombo.setText(VAR_USE_APPEND_STRING);
				break;
			}
			case IEnvironmentVariable.ENV_VAR_USE_PREPEND:
			{
				varUseCombo.setText(VAR_USE_PREPEND_STRING);
				break;
			}
			case IEnvironmentVariable.ENV_VAR_USE_REPLACE:
			{
				varUseCombo.setText(VAR_USE_REPLACE_STRING);
				break;
			}
			case IEnvironmentVariable.ENV_VAR_USE_UNDEFINE:
			{
				varUseCombo.setText(VAR_USE_UNDEFINE_STRING);
				break;
			}
			default:
			case IEnvironmentVariable.ENV_VAR_USE_DEFAULT:
			{
				varUseCombo.setText(VAR_USE_DEFAULT_STRING);
				break;
			}
		}
	}
	
	List<IEnvironmentVariable> getModifiedEnvVarList() {
		List<IEnvironmentVariable> modVars = new ArrayList<IEnvironmentVariable>();
		
		for (IEnvironmentVariable currVar : envVarList) {
			if (currVar.getUse() != IEnvironmentVariable.ENV_VAR_USE_DEFAULT) {
				modVars.add(currVar);
			}
		}
		
		return modVars;
	}

	private void handleConfigurationChangeEvent(boolean comparePrefs) {
		if (lastSelectedConfigName.length() == 0) {
			lastSelectedConfigName = buildConfigurationCombo.getText();
			return;
		}
		
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi != null) {
			ICarbideBuildConfiguration selectedConfig = cpi.getNamedConfiguration(lastSelectedConfigName);
			if (selectedConfig == null) {
				return; 
			}

			if (comparePrefs) {
				if (!compareConfigurationSettings(selectedConfig, false)) {
					// message box that stuff has changed, do you want to save?
					if (true == MessageDialog.openQuestion(getShell(), Messages.getString("CarbideBuildConfigurationsPage.Apply_Configuration_Changes"), Messages.getString("CarbideBuildConfigurationsPage.Apply_Config_Changes_Msg"))) { //$NON-NLS-1$ //$NON-NLS-2$
						// save the configuration data...
						saveConfigurationSettings(selectedConfig);
					}
				}
			}
		} else {
			return; // can't do anything without ICarbideProjectInfo...
		}
		
		// Load the new data...
		lastSelectedConfigName = buildConfigurationCombo.getText();
		ICarbideBuildConfiguration lastConfig = cpi.getNamedConfiguration(lastSelectedConfigName);
		if (lastConfig != null) {
			sisFilesBlock.initData(lastConfig);
			if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(project)) {
				sbsv2BuildConfigTabComposite.initData(lastConfig);
			}
			setUpEnvVarsTable(new String[0], null); // refresh env vars info
			if (argumentsTabcomposite != null) {
				argumentsTabcomposite.initData(lastConfig);
			}
			pathsAndSymbolsTabComposite.initData(lastConfig);
			romBuilderTabComposite.initData(lastConfig);
		}
	}
	
	@Override
	protected void performDefaults() {
		
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);

		sisFilesBlock.performDefaults();

		IEnvironmentVarsInfo envVarsDefaults = cpi.getNamedConfiguration(lastSelectedConfigName).getEnvironmentVarsInfo();
		String[] defaultVars = envVarsDefaults.getDefaultEnvVarsSettings(cpi, cpi.getNamedConfiguration(lastSelectedConfigName));
		setUpEnvVarsTable(defaultVars, envVarsDefaults.getDefaultEnvVarsList(cpi, cpi.getNamedConfiguration(lastSelectedConfigName)));
		
		if (argumentsTabcomposite != null) {
			argumentsTabcomposite.performDefaults(cpi.getNamedConfiguration(lastSelectedConfigName).getSDK());
		}
		pathsAndSymbolsTabComposite.performDefaults(cpi.getNamedConfiguration(lastSelectedConfigName).getSDK());
		romBuilderTabComposite.performDefaults(cpi.getNamedConfiguration(lastSelectedConfigName).getSDK());
		
		super.performDefaults();
	}
	
}
