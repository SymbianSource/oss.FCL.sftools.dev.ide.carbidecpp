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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.dialogs.PropertyPage;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.internal.api.builder.ui.MMPSelectionUI;
import com.nokia.carbide.cdt.internal.api.builder.ui.MMPSelectionUI.FileInfo;
import com.nokia.carbide.cdt.internal.builder.CarbideProjectInfo;
import com.nokia.carbide.cdt.internal.builder.CarbideProjectModifier;
import com.nokia.carbide.cpp.internal.qt.core.QtCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

public class CarbideCPPProjectSettingsPage extends PropertyPage {
	
    private	Label buildDirDynLabel;
	private Button buildFromBldInfButton;
	private Button selectedComponentsButton;
    private MMPSelectionUI selectionUI;
	
	private Button fUseProjectSettings;
	private Link fLink;
	private Group optionsGroup;
	private BuildSettingsUI buildSettingsUI;
	
	
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData());
		
		buildDirDynLabel = new Label(composite, SWT.NONE);
		buildDirDynLabel.setToolTipText(Messages.getString("CarbideCPPProjectSettingsPage.Build_Dir_ToolTip")); //$NON-NLS-1$
		buildDirDynLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Group buildTargetGroup = new Group(composite, SWT.NONE);
		buildTargetGroup.setText(Messages.getString("CarbideCPPProjectSettingsPage.Build_Target")); //$NON-NLS-1$
		buildTargetGroup.setToolTipText(Messages.getString("CarbideCPPProjectSettingsPage.Target_Group_ToolTip")); //$NON-NLS-1$
		buildTargetGroup.setLayout(new GridLayout(2, false));
		buildTargetGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
				
		buildFromBldInfButton = new Button(buildTargetGroup, SWT.RADIO);
		buildFromBldInfButton.setText("Bld.inf"); //$NON-NLS-1$
		buildFromBldInfButton.setToolTipText(Messages.getString("CarbideCPPProjectSettingsPage.Build_From_Inf_ToolTip")); //$NON-NLS-1$
		buildFromBldInfButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		buildFromBldInfButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableOrDisableControls();
			}
		});
		
		selectedComponentsButton = new Button(buildTargetGroup, SWT.RADIO);
		selectedComponentsButton.setText(Messages.getString("CarbideCPPProjectSettingsPage.Selected_Components")); //$NON-NLS-1$
		selectedComponentsButton.setToolTipText(Messages.getString("CarbideCPPProjectSettingsPage.select_components_tooltip")); //$NON-NLS-1$
		selectedComponentsButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		selectedComponentsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableOrDisableControls();
			}
		});

		selectionUI = new MMPSelectionUI(buildTargetGroup, SWT.NONE, 
				PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		selectionUI.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		selectionUI.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				enableOrDisableControls();
			}
		});
		
		// spacer
		new Label(composite, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));

		fUseProjectSettings = new Button(composite, SWT.CHECK);
		fUseProjectSettings.setText(Messages.getString("CarbideCPPProjectSettingsPage.EnableProjectSettings")); //$NON-NLS-1$
		fUseProjectSettings.setToolTipText(Messages.getString("CarbideCPPProjectSettingsPage.EnableProjectSettingsToolTip")); //$NON-NLS-1$
		fUseProjectSettings.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		fUseProjectSettings.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				enableOrDisableControls();
			}
		});
	
		fLink = new Link(composite, SWT.NONE);
		fLink.setText("<a>" + Messages.getString("CarbideCPPProjectSettingsPage.ConfigureWorkspaceSetting") + "...</a>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		fLink.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		fLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PreferencesUtil.createPreferenceDialogOn(getShell(), "com.nokia.carbide.cdt.internal.builder.ui.BuilderPreferencePage", null, null).open(); //$NON-NLS-1$
			}
		});
		
		optionsGroup = new Group(composite, SWT.NONE);
		optionsGroup.setText(Messages.getString("CarbideCPPProjectSettingsPage.ProjectSettingsGroup")); //$NON-NLS-1$
		optionsGroup.setToolTipText(Messages.getString("CarbideCPPProjectSettingsPage.ProjectSettingsGroupToolTip")); //$NON-NLS-1$
		optionsGroup.setLayout(new GridLayout(2, false));
		optionsGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		boolean sbsv2Project = false;
		IProject project = getProject();
		if (project != null) {
			sbsv2Project = CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(project);
		}

		buildSettingsUI = new BuildSettingsUI(parent.getShell(), sbsv2Project, true);
		buildSettingsUI.createControl(optionsGroup);

		noDefaultAndApplyButton();
		
		// read in the settings and populate the prefs...
		if (project != null){
			loadProjectSettings(project);
		}
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, CarbideCPPBuilderUIHelpIds.CARBIDE_BUILDER_PROJECT_PAGE);

		return composite;
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
	
	private boolean savePageSettings(IProject project) {
		
		boolean success = false;
		boolean settingsChanged = false;
		
		if (project == null){
			return false;
		}
		
        CarbideProjectModifier cpi = (CarbideProjectModifier)CarbideBuilderPlugin.getBuildManager().getProjectModifier(getProject());
		if (cpi != null){
			
			// save common settings
			if (buildFromBldInfButton.getSelection() != cpi.isBuildingFromInf()) {
				settingsChanged = true;
			}
			String buildFromInfFlag = "true"; //$NON-NLS-1$
			if (!buildFromBldInfButton.getSelection()){
				buildFromInfFlag = "false"; //$NON-NLS-1$
			}
			cpi.writeProjectSetting(ICarbideProjectInfo.BLD_FROM_INF_PROPS_KEY, buildFromInfFlag);

			String overridingWorkspaceSettings = "true"; //$NON-NLS-1$
			if (!fUseProjectSettings.getSelection()){
				overridingWorkspaceSettings = "false"; //$NON-NLS-1$
			}
			cpi.writeProjectSetting(CarbideProjectInfo.OVERRIDE_WORKSPACE_SETTINGS_KEY, overridingWorkspaceSettings);

			String buildingTestComps = "true"; //$NON-NLS-1$
			if (!buildSettingsUI.getBuildTestComponents()){
				buildingTestComps = "false"; //$NON-NLS-1$
			}
			cpi.writeProjectSetting(CarbideProjectInfo.BUILD_TEST_COMPS_PROPS_KEY, buildingTestComps);

			String useConcurrentBuildingVal = "true"; //$NON-NLS-1$
			if (!buildSettingsUI.getUseConcurrentBuilding()){
				useConcurrentBuildingVal = "false"; //$NON-NLS-1$
			}
			cpi.writeProjectSetting(CarbideProjectInfo.USE_CONCURRENT_BUILDING, useConcurrentBuildingVal);

			cpi.writeProjectSetting(CarbideProjectInfo.CONCURRENT_BUILD_JOBS, Integer.toString(buildSettingsUI.getNumConcurrentBuildJobs()));
			
			String useIncrementalBuilderVal = "true"; //$NON-NLS-1$
			if (!buildSettingsUI.getUseIncrementalBuilder()){
				useIncrementalBuilderVal = "false"; //$NON-NLS-1$
			}
			cpi.writeProjectSetting(CarbideProjectInfo.USE_INCREMENTAL_BUILDER, useIncrementalBuilderVal);

			if (!CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(project)) {
				// save SBSv1 settings
				cpi.writeProjectSetting(CarbideProjectInfo.CLEAN_LEVEL, Integer.toString(buildSettingsUI.getDefaultCleanLevelv1()));

				String manageDepsCheckVal = "true"; //$NON-NLS-1$
				if (!buildSettingsUI.getManageDependencies()){
					manageDepsCheckVal = "false"; //$NON-NLS-1$
				}
				cpi.writeProjectSetting(CarbideProjectInfo.MANAGE_DEPENDENCIES, manageDepsCheckVal);

				String promptForMMPChangedActionVal = "true"; //$NON-NLS-1$
				if (!buildSettingsUI.getPromptForMMPChangedAction()){
					promptForMMPChangedActionVal = "false"; //$NON-NLS-1$
				}
				cpi.writeProjectSetting(CarbideProjectInfo.PROMPT_FOR_MMP_CHANGED_ACTION, promptForMMPChangedActionVal);

				cpi.writeProjectSetting(CarbideProjectInfo.DEFAULT_MMP_CHANGED_ACTION, Integer.toString(buildSettingsUI.getDefaultMMPChangedAction()));
			} else {
				// save SBSv2 settings
				cpi.writeProjectSetting(CarbideProjectInfo.CLEAN_LEVEL, Integer.toString(buildSettingsUI.getDefaultCleanLevelv2()));

				String useKeepGoing = "true"; //$NON-NLS-1$
				if (!buildSettingsUI.getKeepGoing()){
					useKeepGoing = "false"; //$NON-NLS-1$
				}
				cpi.writeProjectSetting(CarbideProjectInfo.USE_KEEP_GOING, useKeepGoing);

				String useDebugMode = "true"; //$NON-NLS-1$
				if (!buildSettingsUI.getDebugMode()){
					useDebugMode = "false"; //$NON-NLS-1$
				}
				cpi.writeProjectSetting(CarbideProjectInfo.USE_DEBUG_MODE, useDebugMode);

				String overrideMakeEngine = "true"; //$NON-NLS-1$
				if (!buildSettingsUI.getOverrideDefaultMakeEngine()){
					overrideMakeEngine = "false"; //$NON-NLS-1$
				}
				cpi.writeProjectSetting(CarbideProjectInfo.OVERRIDE_MAKE_ENGINE, overrideMakeEngine);

				cpi.writeProjectSetting(CarbideProjectInfo.MAKE_ENGINE_TO_USE, buildSettingsUI.getMakeEngine());
				
				cpi.writeProjectSetting(CarbideProjectInfo.EXTRA_SBSV2_ARGS, buildSettingsUI.getExtraSBSv2Args());
				
				cpi.writeProjectSetting(CarbideProjectInfo.BUILD_ALIAS_APPENDER, buildSettingsUI.getBuildAliasAppendText());
			}

			List<String> checkedComponents = getCheckedComponentFilenames();
			List<String> infCheckedComps = cpi.getInfBuildComponentsRawSettings();
			
			if (!settingsChanged && !checkedComponents.equals(infCheckedComps)) {
				settingsChanged = true;
			}
			
			StringBuilder cmpListBuilder = new StringBuilder();
			if (!checkedComponents.isEmpty()){
				for (String checkedComponent : checkedComponents) {
					cmpListBuilder.append(checkedComponent);
					cmpListBuilder.append(';');
				}
			}
			cpi.writeProjectSetting(ICarbideProjectInfo.INF_COMPONENTS_PROPS_KEY, cmpListBuilder.toString());

			if (settingsChanged) {
				projectPropertyChanged(cpi);
			}
			success = cpi.saveChanges();
		}
		
		return success;
	}
	
	private void loadProjectSettings(IProject project) {
		
        CarbideProjectInfo cpi = (CarbideProjectInfo)CarbideBuilderPlugin.getBuildManager().getProjectInfo(getProject());
        if (cpi != null) {
        	// load common settings
			buildFromBldInfButton.setSelection(cpi.isBuildingFromInf());
			selectedComponentsButton.setSelection(!cpi.isBuildingFromInf());
    		buildSettingsUI.setUseConcurrentBuilding(cpi.isConcurrentBuildingEnabledProjectValue());
    		buildSettingsUI.setNumConcurrentBuildJobs(cpi.concurrentBuildJobsProjectValue());
    		buildSettingsUI.setUseIncrementalBuilder(cpi.incrementalBuilderEnabledProjectValue());
    		buildSettingsUI.setBuildTestComponents(cpi.isBuildingTestCompsProjectValue());
        	
    		fUseProjectSettings.setSelection(cpi.overrideWorkspaceBuildSettingsProjectValue());

    		if (!CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(project)) {
    			// load sbsv1 settings
        		buildSettingsUI.setDefaultCleanLevelv1(cpi.getCleanLevelProjectValue());
        		buildSettingsUI.setManageDependencies(cpi.areMakefilesManagedProjectValue());
        		buildSettingsUI.setPromptForMMPChangedAction(cpi.promptForMMPChangedActionProjectValue());
        		buildSettingsUI.setDefaultMMPChangedAction(cpi.defaultMMPChangedActionProjectValue());
    		} else {
    			// load sbsv2 settings
        		buildSettingsUI.setDefaultCleanLevelv2(cpi.getCleanLevelProjectValue());
        		buildSettingsUI.setKeepGoing(cpi.useKeepGoingProjectValue());
        		buildSettingsUI.setDebugCheck(cpi.useDebugModeProjectValue());
        		buildSettingsUI.setOverrideDefaultMakeEngine(cpi.overrideMakeEngineProjectValue());
        		buildSettingsUI.setMakeEngineText(cpi.makeEngineProjectValue());
        		buildSettingsUI.setExtraSBSv2Args(cpi.extraSBSv2ArgsProjectValue());
        		buildSettingsUI.setBuildAliasAppendText(cpi.buildAliasAppendTextValue());
    		}
    		
    		initMMPSelectionUI(cpi);
    		
    		// Add text for location of build file....
    		boolean bldInfHasErrors = false;
    		if (cpi.getAbsoluteBldInfPath() == null){
    			buildDirDynLabel.setText(Messages.getString("CarbideCPPProjectSettingsPage.Bld_Inf_Does_Not_Exist")); //$NON-NLS-1$
    			bldInfHasErrors = true;
    		}else {
    			if (!cpi.getAbsoluteBldInfPath().toFile().exists()){
    				buildDirDynLabel.setText(Messages.getString("CarbideCPPProjectSettingsPage.Bld_Inf_File_Does_Not_Exist") + cpi.getAbsoluteBldInfPath().toOSString() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
    				bldInfHasErrors = true;
    			} else {
    				buildDirDynLabel.setText(Messages.getString("CarbideCPPProjectSettingsPage.bld_inf_file") + cpi.getAbsoluteBldInfPath().toOSString()); //$NON-NLS-1$
    			}
    		}
    		
    		if (bldInfHasErrors){
    			buildDirDynLabel.setForeground(getShell().getDisplay().getSystemColor(SWT.COLOR_RED));
    			buildDirDynLabel.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
    		} else {
    			buildDirDynLabel.setForeground(getShell().getDisplay().getSystemColor(SWT.COLOR_BLACK));
    			buildDirDynLabel.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
    		}

    		// get the list of normal and test project extensions
    		List<ISymbianBuildContext> buildConfigList = new ArrayList<ISymbianBuildContext>();
			buildConfigList.addAll(cpi.getBuildConfigurations());

    		enableOrDisableControls();
        }
	}
	
	public boolean performOk() {
		IProject project = getProject();
		if (project != null){
			savePageSettings(project);
		}
		super.performOk();
		return true;
	}
	
	private void enableOrDisableControls() {
		boolean buildFromInf = buildFromBldInfButton.getSelection();
		boolean useProjectSettings = fUseProjectSettings.getSelection();
		boolean isQtProject = false;
		IProject project = getProject();
		if (project != null) {
			try {
				if (project.hasNature(QtCorePlugin.QT_PROJECT_NATURE_ID)) {
					isQtProject = true;
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		
		selectionUI.setEnabled(!buildFromInf);
		optionsGroup.setEnabled(useProjectSettings);
		buildSettingsUI.setEnabled(useProjectSettings);
		buildSettingsUI.setBuildTestComponentsEnabledState(buildFromInf && useProjectSettings);
		
		if (!CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(project)) {
			buildSettingsUI.setMMPChangedActionEnabledState(useProjectSettings && !isQtProject);
		}

		checkValid();
	}
	
	private void initMMPSelectionUI(CarbideProjectInfo cpi) {
		selectionUI.setToolTipText(Messages.getString("CarbideCPPProjectSettingsPage.checked_items_are_built_tooltip")); //$NON-NLS-1$

		// set layout data
		final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.widthHint = 270;
		gridData.heightHint = 190;
		selectionUI.setLayoutData(gridData);
		
		// set the data
		selectionUI.setBldInfFile(cpi.getAbsoluteBldInfPath(), cpi.getBuildConfigurations(), CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(cpi.getProject()));
		
		// set checked state
		selectionUI.setAllChecked(false);
		IStructuredSelection selection = (IStructuredSelection) selectionUI.getSelection();
		
		// Get the list of components from INF file that are to be built, these are the checked elements
		List<String> infCheckedComponentsList = cpi.getInfBuildComponentsRawSettings();
		for (String compstr : infCheckedComponentsList) {
			if (compstr.endsWith(ICarbideProjectInfo.TEST_COMPONENT_LABEL)) {
				// remove the test component label and space!
				compstr = compstr.substring(0, compstr.length() - ICarbideProjectInfo.TEST_COMPONENT_LABEL.length() - 1);
			}
			IPath path = new Path(compstr);
			for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
				FileInfo info = (FileInfo) iterator.next();
				if (info.hasPath(path)) {
					info.setChecked(true);
				}
			}
		}
		selectionUI.setSelection(selection);
	}
	
	private List<String> getCheckedComponentFilenames() {
		List<String> componentFileNames = new ArrayList<String>();
		IStructuredSelection selection = (IStructuredSelection) selectionUI.getSelection();
		for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
			FileInfo info = (FileInfo) iterator.next();
			if (info.isChecked())
				componentFileNames.add(info.getFileName() + (info.isTest() ? " " + ICarbideProjectInfo.TEST_COMPONENT_LABEL : "")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return componentFileNames;
	}
	
	 /**
	  *  Extends <code>LabelProvider</code> with the default implementation 
	  *  and implements<code>ITableLabelProvider</code> with the methods
	  *	 to provide the text and/or image for each column of a given element.  
	  *	 Used by table viewers.
	  */
	
	static class InfComponentsLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider  {

		/**
		 * Returns the label image for the given column of the given element.
		 * The default implementation returns null.
		 * 
		 * @return image object
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		/**
		 * Returns the label text for the given column of the given element.
		 * 
		 * @return string is the label text for the given column.
		 */
		public String getColumnText(Object arg0, int column) {
			return (String) arg0;
		}

		private Color lBlack = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
		//private Color lRed =  Display.getDefault().getSystemColor(SWT.COLOR_RED);
		
		public Color getForeground(Object obj, int index) {
			return lBlack;
		}
		
		public Color getBackground(Object element, int columnIndex) {
			return null;
		}
	}
	
	/** 
	 * This implementation of <code>IStructuredContentProvider</code> handles
	 * 	the case where the viewer input is an unchanging array or collection of elements.
	 * 
	 */
	static class InfComponentsContentProvider implements IStructuredContentProvider {
		

		/**
		 * Returns the elements in the input
		 * 
		 * @return array of objects.
		 */
		@SuppressWarnings("unchecked")
		public Object[] getElements(Object arg0) {
			
			List tempList = (ArrayList)arg0;
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
			//do nothing
		}
	}

	private void checkValid() {
		setMessage(null);
		
		// if there are any new-style prj extensions, warn them that they won't be built
		// when not building the entire bld.inf.
        if (selectedComponentsButton.getSelection()) {
    		String warning = selectionUI.getExtensionsWarningMessage();
    		if (warning != null) {
    			setMessage(warning, WARNING);
    		}
    	}
	}

	private void projectPropertyChanged(ICarbideProjectInfo cpi) {
		CarbideBuilderPlugin.fireProjectPropertyChanged(cpi);
	}
	
}
