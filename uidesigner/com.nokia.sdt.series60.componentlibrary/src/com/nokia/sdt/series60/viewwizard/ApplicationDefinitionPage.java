/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.series60.viewwizard;

import com.nokia.carbide.cdt.builder.DefaultMMPViewConfiguration;
import com.nokia.carbide.cdt.builder.EpocEngineHelper;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPModel;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.component.ComponentSetResult;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.component.symbian.ComponentSDKSelector;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.util.NamePropertySupport;
import com.nokia.sdt.editor.EditorServices;
import com.nokia.sdt.emf.dm.ILocalizedStringTable;
import com.nokia.sdt.emf.dm.Language;
import com.nokia.sdt.series60.component.Series60ComponentPlugin;
import com.nokia.sdt.symbian.SymbianLanguage;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.symbian.sdk.SdkUtilities;
import com.nokia.sdt.symbian.sdk.SdkUtilities.SelectableSDKInfo;
import com.nokia.sdt.symbian.workspace.ISymbianProjectContext;
import com.nokia.sdt.workspace.IProjectContext;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.osgi.framework.Version;

import java.text.MessageFormat;
import java.util.*;
import java.util.List;

public class ApplicationDefinitionPage extends ViewWizardPageBase {

	private Label addToProjectLabel;
	private ProjectsComboWidget projectCombo;
	private Label applicationNameLabel;
	private Text applicationNameText;
	private Label componentSDKLabel;
	private Combo componentSDKCombo;
	private Label languageLabel;
	private Combo languageCombo;
	
	private ISymbianSDK[] s60Sdks;
	private ISymbianSDK[] selectableSdks;
		
	public static final String PAGE_NAME = "ApplicationSelection"; //$NON-NLS-1$
	private static final String SDK_NAME = "com.nokia.series60"; //$NON-NLS-1$
	private static final String VERSION = "2.0"; //$NON-NLS-1$

	private IStructuredSelection selectedResources;
	private boolean hasRootModel;
	private boolean isProjectWizard;
	private IWizardPage previousPage;
	private boolean inited;
	
	public ApplicationDefinitionPage(IStructuredSelection selectedResources, ViewWizardManager manager) {
		super(PAGE_NAME, manager);
		setTitle(Messages.getString("ApplicationSelectionPage.PageTitle")); //$NON-NLS-1$
		setDescription(Messages.getString("ApplicationSelectionPage.PageDescription")); //$NON-NLS-1$
		this.selectedResources = selectedResources;
	}
	
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		
		Composite container = new Composite(parent, SWT.NULL);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 10;
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);
		
		addToProjectLabel = new Label(container, SWT.NONE);
		addToProjectLabel.setText(Messages.getString("ApplicationSelectionPage.AddToProjectPrompt")); //$NON-NLS-1$

		projectCombo = new ProjectsComboWidget(container, SWT.READ_ONLY, selectedResources);
		projectCombo.getControl().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				IProject project = projectCombo.getProjectFromDisplayName(projectCombo.getText());
				updatePageForProject(project);
				getWizardManager().getDataStore().put(ViewWizardManager.PROJECT_KEY, project);
				setPageComplete(validatePage());
			}
		});

		projectCombo.getControl().setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		projectCombo.getControl().setToolTipText(Messages.getString("ApplicationSelectionPage.ProjectTip")); //$NON-NLS-1$
		projectCombo.getControl().setData(NAME_KEY, "projectCombo"); //$NON-NLS-1$

		applicationNameLabel = new Label(container, SWT.NONE);
		applicationNameLabel.setText(Messages.getString("ApplicationSelectionPage.ApplicationNamePrompt")); //$NON-NLS-1$

		applicationNameText = new Text(container, SWT.BORDER);
		String initialAppName = Messages.getString("ApplicationSelectionPage.InitialApplicationName"); //$NON-NLS-1$
		getWizardManager().getDataStore().put(ViewWizardManager.APPNAME_KEY, initialAppName);
		applicationNameText.setText(initialAppName);
		applicationNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				getWizardManager().getDataStore().put(ViewWizardManager.APPNAME_KEY, applicationNameText.getText());
				setPageComplete(validatePage());
			}
		});
		applicationNameText.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		applicationNameText.setToolTipText(Messages.getString("ApplicationSelectionPage.AppNameTip")); //$NON-NLS-1$
		applicationNameText.setData(NAME_KEY, "applicationNameText"); //$NON-NLS-1$

		componentSDKLabel = new Label(container, SWT.NONE);
		componentSDKLabel.setText(Messages.getString("ApplicationSelectionPage.ComponentVersionsPrompt")); //$NON-NLS-1$

		componentSDKCombo = new Combo(container, SWT.READ_ONLY);
		componentSDKCombo.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				createComponentSet(selectableSdks[componentSDKCombo.getSelectionIndex()]);
			}
		});

		componentSDKCombo.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		componentSDKCombo.setToolTipText(Messages.getString("ApplicationSelectionPage.ComponentSDKTip")); //$NON-NLS-1$
		componentSDKCombo.setData(NAME_KEY, "componentSDKCombo"); //$NON-NLS-1$

		languageLabel = new Label(container, SWT.NONE);
		languageLabel.setText(Messages.getString("ApplicationSelectionPage.InitialLanguagePrompt")); //$NON-NLS-1$

		languageCombo = new Combo(container, SWT.READ_ONLY);
		languageCombo.setItems(SymbianLanguage.getDisplayNames());
		getWizardManager().getDataStore().put(ViewWizardManager.LANGUAGE_ID_KEY, 
				new Integer(SymbianLanguage.DEFAULT_LANGUAGE_ID));
		languageCombo.select(SymbianLanguage.getIndexFromLanguageID(SymbianLanguage.DEFAULT_LANGUAGE_ID));
		languageCombo.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		languageCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int languageID = SymbianLanguage.getLanguageIDFromIndex(languageCombo.getSelectionIndex());
				getWizardManager().getDataStore().put(ViewWizardManager.LANGUAGE_ID_KEY, new Integer(languageID));
			}
		});
		languageCombo.setToolTipText(Messages.getString("ApplicationSelectionPage.InitialLanguageTip")); //$NON-NLS-1$
		languageCombo.setData(NAME_KEY, "languageCombo"); //$NON-NLS-1$

        setControl(container);
        setHelpContextId(ViewWizardManager.APPLICATION_DEFINITION_PAGE);
	}
	
	private void cacheSeries60SDKInfo() {
		if (s60Sdks == null) {
			List<ISymbianSDK> allSdks = 
				SdkUtilities.getMatchingVendorSDKs(SdkUtilities.S60_VENDOR_PATTERN);
			Collections.sort(allSdks, SdkUtilities.compareBySdkId);
			s60Sdks = (ISymbianSDK[]) allSdks.toArray(new ISymbianSDK[allSdks.size()]);
		}
	}
	
	private List<ISymbianSDK> getBuildSDKsFromBuildContexts(List<ISymbianBuildContext> buildContexts) {
		List<ISymbianSDK> configSdks = new ArrayList<ISymbianSDK>();
		for (ISymbianBuildContext context : buildContexts) {
			ISymbianSDK sdk = context.getSDK();
			String family = sdk.getFamily();
			if (ISymbianSDK.S60_FAMILY_ID.equalsIgnoreCase(family) ||
				ISymbianSDK.SERIES60_FAMILY_ID.equalsIgnoreCase(family))
				configSdks.add(sdk);
		}
		return configSdks;
	}
	
	private void updateSelectableSdks(IProject project) {
		ISymbianSDK minimumConfigSdk = null;
		List<ISymbianSDK> configSdks = null;
		SelectableSDKInfo info = null;
		
		if (project != null) {
			// get the SDKs referenced by build configurations
			info = SdkUtilities.getSelectableSDKsForProject(project, SdkUtilities.S60_VENDOR_PATTERN);
		}
		else if (isProjectWizard()) {
			// get the SDKs from the BuildTargetsPage
			cacheSeries60SDKInfo();
			IWizardDataPage page = (IWizardDataPage) getWizardManager().getPage("Build Targets"); //$NON-NLS-1$
			List<ISymbianBuildContext> buildContexts =
				(List<ISymbianBuildContext>) page.getPageValues().get("selectedBuildConfigs"); //$NON-NLS-1$
			List<ISymbianSDK> buildSdks = getBuildSDKsFromBuildContexts(buildContexts);
			info = SdkUtilities.getSelectableSDKs(buildSdks, SdkUtilities.S60_VENDOR_PATTERN);
		}

		if (info != null) {
			configSdks = info.selectableSDKs;
			minimumConfigSdk = info.minimumConfiguredSDK;
		}
		
		if ((configSdks != null) && (configSdks.size() > 0)) {
			selectableSdks = (ISymbianSDK[]) configSdks.toArray(new ISymbianSDK[configSdks.size()]);
			String[] sdkNames = new String[selectableSdks.length];
			int selIdx = -1;
			for (int i = 0; i < selectableSdks.length; i++) {
				sdkNames[i] = selectableSdks[i].getUniqueId();
				if (selectableSdks[i] == minimumConfigSdk)
					selIdx = i;
			}
			componentSDKCombo.setItems(sdkNames);
			componentSDKCombo.select(selIdx >= 0 ? selIdx : 0);
			createComponentSet(minimumConfigSdk);
		} else {
			componentSDKCombo.removeAll();
            createComponentSet(null);
        }

	}

    private void createComponentSet(ISymbianSDK sdk) {
        Version version = null;
        if (sdk != null)
        	version = sdk.getSDKVersion(); 
        if (version == null)
            version = new Version(VERSION);
        
		ComponentSDKSelector filter = new ComponentSDKSelector(SDK_NAME, version);
		ComponentSetResult csResult = getWizardManager().getComponentProvider().queryComponents(filter);
		if (csResult.getStatus() != null) {
			Logging.log(Series60ComponentPlugin.getDefault(), csResult.getStatus());
			Logging.showErrorDialog(null, null, csResult.getStatus());
		}
		getWizardManager().getDataStore().put(ViewWizardManager.COMPONENT_SET_KEY, csResult.getComponentSet());
	}
    
	protected void enteringPage() {
		super.enteringPage();
		if (getWizardManager().checkProjectWizard(true)) {
			addToProjectLabel.setVisible(false);
			projectCombo.getControl().setVisible(false);
			setDescription(Messages.getString("ApplicationSelectionPage.PageDescription2")); //$NON-NLS-1$
			updatePageForProject(null);
		}
		else {
			applicationNameLabel.setVisible(false);
			applicationNameText.setVisible(false);
			if (projectCombo.isValid()) {
				IProject project = projectCombo.getProjectFromDisplayName(projectCombo.getText());
				updatePageForProject(project);
				getWizardManager().getDataStore().put(ViewWizardManager.PROJECT_KEY, project);
			} 
			else
				updatePageForProject(null);
		}
		
		setPageComplete(validatePage());
		inited = true;
	}
	
	public boolean handleApplicationEditorDirty() {
		IDesignerDataModel rootModel = 
			(IDesignerDataModel) getWizardManager().getDataStore().get(ViewWizardManager.ROOT_MODEL_KEY);
		if (rootModel != null) {
	    	IEditorPart editorPart = EditorServices.findEditor(rootModel);
	    	if ((editorPart != null) && editorPart.isDirty()) {
	    		String messageFmt = Messages.getString("ApplicationDefinitionPage.AppEditorDirtyMessageFormat"); //$NON-NLS-1$
	    		IProject project = (IProject) getWizardManager().getDataStore().get(ViewWizardManager.PROJECT_KEY);
	    		if (project != null) {
		    		String message = MessageFormat.format(messageFmt, new Object[] { project.getName() });
		    		if (MessageDialog.openQuestion(getShell(), Messages.getString("ApplicationDefinitionPage.AppEditorDirtyMessageTitle"), message)) //$NON-NLS-1$
		    			EditorServices.saveEditor(editorPart);
		    		else
		    			return false;
	    		}
	    	}
		}
		
		return true;
	}
	
	private boolean isProjectWizard() {
		if (!isProjectWizard) {
			isProjectWizard = getWizardManager().checkProjectWizard(false);
		}
		return isProjectWizard;
	}

	/**
	 * @param project
	 * 
	 * Update the other fields with values from the project
	 */
	private void updatePageForProject(final IProject project) {
		IProject currentProject = (IProject) getWizardManager().getDataStore().get(ViewWizardManager.PROJECT_KEY);
		if ((project != null) && project.equals(currentProject))
			return;
		
		DesignerDataModel dataModel = 
			(DesignerDataModel) getWizardManager().getDataStore().get(ViewWizardManager.ROOT_MODEL_KEY);
		if (dataModel != null)
			dataModel.dispose();
		
		Runnable runnable = new Runnable() {

			public void run() {
				hasRootModel = false;
				Language appLanguages[] = null;
				DesignerDataModel dataModel = (DesignerDataModel) WizardUtils.getExtantRootModel(project);
				if (dataModel != null) {
					EObject[] rootContainers = ((DesignerDataModel) dataModel).getRootContainers();
					hasRootModel = rootContainers != null && rootContainers.length > 0;
					
					// collect a list of all the languages in the application
					List stringTables = dataModel.getDesignerData().getStringBundle().getLocalizedStringTables();
					appLanguages = new Language[stringTables.size()];
					for (int i = 0; i < stringTables.size(); i++) {
						ILocalizedStringTable table = (ILocalizedStringTable) stringTables.get(i);
						appLanguages[i] = table.getLanguage();
					}
				}
				getWizardManager().setExtantRootModel(hasRootModel);
				getWizardManager().setApplicationLanguages(appLanguages);
				
				getWizardManager().getDataStore().put(ViewWizardManager.ROOT_MODEL_KEY, dataModel);
			}
		};
		
		BusyIndicator.showWhile(getShell().getDisplay(), runnable);
		
		dataModel = (DesignerDataModel) getWizardManager().getDataStore().get(ViewWizardManager.ROOT_MODEL_KEY);
		setDerivedFields(dataModel, project);
		getWizardManager().fireProjectSelectionChanged(project);
		if (project != null)
			getWizardManager().getDataStore().put(ViewWizardManager.PROJECT_NAME_KEY, project.getName());
	}
	
	private void setDerivedFields(IDesignerDataModel dataModel, IProject project) {
		// update the SDKs
		updateSelectableSdks(project);

		if (hasRootModel) {
			setDerivedFieldsFromModel((DesignerDataModel) dataModel);
		}
		else {
			setDerivedFieldsToDefault(project);
		}
		setDerivedFieldsVisible(!hasRootModel);
	}
	
	@SuppressWarnings("unchecked")
	private void setDerivedFieldsToDefault(IProject project) {
		String initialAppName = Messages.getString("ApplicationSelectionPage.InitialApplicationName"); //$NON-NLS-1$
		String projectName = null;
		if (project != null) {
			projectName = project.getName();
		}
		else if (isProjectWizard()) {
			projectName = (String) getWizardManager().getDataStore().get(ViewWizardManager.PROJECT_NAME_KEY);
		}
		
		if (projectName != null)
			initialAppName = TextUtils.legalizeIdentifier(projectName);
		
		if (!inited) {
			getWizardManager().getDataStore().put(ViewWizardManager.APPNAME_KEY, initialAppName);
			applicationNameText.setText(initialAppName);
		}
		
		if (!inited) {
			getWizardManager().getDataStore().put(ViewWizardManager.LANGUAGE_ID_KEY, 
					new Integer(SymbianLanguage.DEFAULT_LANGUAGE_ID));
			languageCombo.select(SymbianLanguage.getIndexFromLanguageID(SymbianLanguage.DEFAULT_LANGUAGE_ID));
		}
		
		String value = getUID3ValueFromProject(project);
		if (value != null)
			getWizardManager().getDataStore().put(ViewWizardManager.APPUID_KEY, value);

		getWizardManager().getDataStore().put(ViewWizardManager.HAS_NON_VIEW_SWITCHING_ROOT_MODEL_KEY, Boolean.FALSE);
	}
	
	@SuppressWarnings("unchecked")
	private String getUID3ValueFromProject(IProject project) {
		// TODO Temporary implementation, until we ask the user for the actual mmp file to use
		if (project != null) {
			IWorkspaceRoot workspaceRoot = project.getWorkspace().getRoot();
			getWizardManager().getDataStore().remove(ViewWizardManager.MMP_FILE_PATH_KEY);
			List<IPath> makMakeRefs = new ArrayList<IPath>();
			List<IPath> testMakMakeRefs = new ArrayList<IPath>(); // ignoring test files
			EpocEngineHelper.getAllMakMakeFiles(project, makMakeRefs, testMakMakeRefs);
			for (IPath path : makMakeRefs) {
				if (!FileUtils.getSafeFileExtension(path).equalsIgnoreCase("mmp"))
					continue;
				String uid3 = null;
				try {
					IFile[] files = workspaceRoot.findFilesForLocation(path);
					if (files == null || files.length == 0)
						continue;
					path = files[0].getFullPath();
					IMMPModel model = EpocEnginePlugin.getMMPModelProvider().getSharedModel(path);
					if (model == null)
						continue;
					IMMPView view = model.createView(
							new DefaultMMPViewConfiguration(project, null, new AllNodesViewFilter()));
					uid3 = view.getUid3(); // or null
					view.dispose();
					EpocEnginePlugin.getMMPModelProvider().releaseSharedModel(model);
				} 
				catch (Exception e) {
				}
				if (uid3 != null) {
					getWizardManager().getDataStore().put(ViewWizardManager.MMP_FILE_PATH_KEY, path);
					return uid3;
				}
			}
		}
		
		return null;
	}

	private void setDerivedFieldsFromModel(DesignerDataModel designerDataModel) {
		// get data model properties
	    String rootAppName = designerDataModel.getProperty(DesignerDataModel.ROOT_APPLICATION_NAME);
		Check.checkState(rootAppName != null);
		getWizardManager().getDataStore().put(ViewWizardManager.APPNAME_KEY, rootAppName);
		applicationNameText.setText(rootAppName);

        String vendor = designerDataModel.getProperty(ComponentProvider.VENDOR_PROPERTY);
        Check.checkState(vendor != null);
        String version = designerDataModel.getProperty(ComponentProvider.VERSION_PROPERTY);
        Check.checkState(version != null);
        ISymbianSDK sdk = SdkUtilities.getBestSDKForVendorAndVersion(vendor, version);
        if (sdk != null) {
            createComponentSet(sdk);
            cacheSeries60SDKInfo();
            for (int i = 0; i < s60Sdks.length; i++) {
                if (s60Sdks[i] == sdk) {
                    componentSDKCombo.select(i);
                    break;
                }
            }
        }
        
		IProjectContext projectContext = designerDataModel.getProjectContext();
		ISymbianProjectContext symbianProjectContext = 
			(ISymbianProjectContext) projectContext.getAdapter(ISymbianProjectContext.class);
		Check.checkState(symbianProjectContext != null);
		Language defaultLanguage = symbianProjectContext.getCurrentLanguage();
		int languageCode = defaultLanguage.getLanguageCode();
		Integer languageID = new Integer(languageCode);
		getWizardManager().getDataStore().put(ViewWizardManager.LANGUAGE_ID_KEY, languageID);
		languageCombo.select(SymbianLanguage.getIndexFromLanguageID(languageID.intValue()));
		
		// get root container properties
		EObject[] rootContainers = designerDataModel.getRootContainers();
		Check.checkState(rootContainers.length > 0);

		EObject appObject = WizardUtils.getApplicationObject(designerDataModel);
		if (appObject != null) {
			IPropertySource propertySource = WizardUtils.getProperties(appObject);
			Object uidProperty = propertySource.getPropertyValue(ViewWizardManager.UID_PROPERTY_NAME);
			Check.checkState(uidProperty != null);
			
			getWizardManager().getDataStore().put(ViewWizardManager.APPUID_KEY, uidProperty.toString());
		}
		
		getWizardManager().getDataStore().put(ViewWizardManager.HAS_NON_VIEW_SWITCHING_ROOT_MODEL_KEY, 
				Boolean.valueOf(WizardUtils.rootModelIsNotViewSwitching(designerDataModel)));
	}

	private void setDerivedFieldsVisible(boolean visible) {
		languageLabel.setVisible(visible);
		languageCombo.setVisible(visible);
		componentSDKLabel.setVisible(visible);
		componentSDKCombo.setVisible(visible);
		getShell().layout(true);
	}

	private boolean isValidApplicationName() {
		return NamePropertySupport.isLegalName(applicationNameText.getText());
	}
	
	/**
     * Returns whether this page's controls currently all contain valid 
     * values. Set error string, if invalid.
     *
     * @return <code>true</code> if all controls are valid, and
     *   <code>false</code> if at least one is invalid
     */
    protected boolean validatePage() {
    	if (!projectCombo.isValid() && 
    			!getWizardManager().getDataStore().containsKey(ViewWizardManager.PROJECT_NAME_KEY)) {
    		setErrorMessage(projectCombo.getErrorString());
    		return false;
    	}
    	
    	if (!hasRootModel) { // we don't validate the SDKs if project already has a root model
	    	if (componentSDKCombo.getSelectionIndex() < 0) {
	    		String string = Messages.getString(isProjectWizard() ?
	    				"ApplicationSelectionPage.NoSDKError" : //$NON-NLS-1$
	    				"ApplicationSelectionPage.NoCompatibleSDKError"	); //$NON-NLS-1$
				setErrorMessage(string);
	    		return false;
	    	}
    	}
        
        if (!isValidApplicationName()) {
        	setErrorMessage(Messages.getString("ApplicationSelectionPage.ValidApplicationNameError")); //$NON-NLS-1$
        	return false;
        }
        
        if (!handleApplicationEditorDirty()) {
        	setErrorMessage(Messages.getString("ApplicationDefinitionPage.AppEditorDirtyError")); //$NON-NLS-1$
        	return false;
        }

        setErrorMessage(null);
        setWarningMessage(null);
        return true;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizardPage#setPreviousPage(org.eclipse.jface.wizard.IWizardPage)
     */
    public void setPreviousPage(IWizardPage page) {
        previousPage = page;
    }

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizardPage#getPreviousPage()
	 */
	public IWizardPage getPreviousPage() {
		return previousPage;
	}

	public Map<String, Object> getPageValues() {
		Integer value = (Integer) getWizardManager().getDataStore().get(ViewWizardManager.LANGUAGE_ID_KEY);
		if (value != null) {
			String str = "";
			if (value.intValue() < 10)
				str += "0";
			str += value.toString();
			return Collections.singletonMap(ViewWizardManager.LANGUAGE_ID_KEY, (Object) str);
		}
		return super.getPageValues();
	}
}
