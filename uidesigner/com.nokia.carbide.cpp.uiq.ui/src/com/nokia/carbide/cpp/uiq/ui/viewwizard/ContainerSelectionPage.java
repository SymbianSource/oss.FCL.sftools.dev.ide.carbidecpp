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
/* START_USECASES: CU2 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.viewwizard;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.*;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.NamePropertySupport;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.workspace.IProjectContext;
import com.nokia.sdt.workspace.WorkspaceContext;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.*;

public class ContainerSelectionPage extends ViewWizardPageBase {
	
	public static final String PAGE_NAME = "ContainerSelection"; //$NON-NLS-1$
	private Text containerBaseNameText;
	private Combo uiConfigModesCombo;
	private Combo containerTypesCombo;
	private Combo layoutManagersCombo;
	private Text descriptionText;
	private boolean inited;
	
	Hashtable<String, String> uiConfigModesLocalizedMap;
	ArrayList<String> uiConfigModesDescriptions;
	String[] uiConfigModeLabels;
	Hashtable<String, String> containerTypesLocalizedMap;
	ArrayList<String> containerTypesDescriptions;
	String[] containerTypesLabels;
	Hashtable<String, String> layoutManagersLocalizedMap;
	ArrayList<String> layoutManagersDescriptions;
	String[] layoutManagersLabels;
	
	public ContainerSelectionPage(ViewWizardManager manager) {
		super(PAGE_NAME, manager);
		setTitle(Messages.getString("ContainerSelectionPage.PageTitle")); //$NON-NLS-1$
		setDescription(Messages.getString("ContainerSelectionPage.PageDescription")); //$NON-NLS-1$
		setPageComplete(false);
		initializeLocalizationMaps();
	}
	
	private void initializeLocalizationMaps() {
		String[] uiConfigModesKeys = new String[] {
				"ContainerSelectionPage.softkeyPortraitStyle", //$NON-NLS-1$
				"ContainerSelectionPage.softkeyTouchPortraitStyle", //$NON-NLS-1$ 
				"ContainerSelectionPage.penTouchPortraitStyle"}; //$NON-NLS-1$
		String[] uiConfigModesValues = new String[] {
				"KQikSoftkeyStylePortrait", //$NON-NLS-1$
				"KQikSoftkeyStyleTouchPortrait", //$NON-NLS-1$ 
				"KQikPenStyleTouchPortrait"}; //$NON-NLS-1$
		uiConfigModesLocalizedMap = new Hashtable<String, String>();
		uiConfigModesDescriptions = new ArrayList<String>();
		uiConfigModeLabels = initializeLocalizedMap(uiConfigModesKeys, uiConfigModesValues, uiConfigModesLocalizedMap, uiConfigModesDescriptions);
		
		String[] containerTypesKeys = new String[] {
				"ContainerSelectionPage.nonScrollable", //$NON-NLS-1$
				"ContainerSelectionPage.scrollable"}; //$NON-NLS-1$
		String[] containerTypesValues = new String[] {
				"EQikCtContainer", //$NON-NLS-1$
				"EQikCtScrollableContainer"}; //$NON-NLS-1$
		containerTypesLocalizedMap = new Hashtable<String, String>();
		containerTypesDescriptions = new ArrayList<String>();
		containerTypesLabels = initializeLocalizedMap(containerTypesKeys, containerTypesValues, containerTypesLocalizedMap, containerTypesDescriptions);
	}
	
	private void initializeLayoutManagerLocalizationMap() {
		layoutManagersLocalizedMap = new Hashtable<String, String>();
		layoutManagersDescriptions = new ArrayList<String>();
		IComponentSet componentSet = getWizardManager().getComponentSet();
		if (componentSet != null) {
			ArrayList<String> layoutManagerKeysList = new ArrayList<String>();
			ArrayList<String> layoutManagerLabelsList = new ArrayList<String>();
			for (Iterator<IComponent> iter = componentSet.iterator(); iter.hasNext();) {
				IComponent component = iter.next();
				IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
				if (!component.isAbstract() &&
						attributes.getBooleanAttribute(CommonAttributes.IS_LAYOUT_MANAGER, false)) {
					layoutManagerKeysList.add(component.getId());
					layoutManagerLabelsList.add(component.getFriendlyName());
				}
			}
			
			String[] layoutManagersKeys = new String[layoutManagerKeysList.size()];
			layoutManagersLabels = new String[layoutManagerKeysList.size()];
			for (int i=0; i<layoutManagerKeysList.size(); i++) { 
				layoutManagersKeys[i] = (String)layoutManagerKeysList.get(i);
				layoutManagersLabels[i] = (String)layoutManagerLabelsList.get(i);
				layoutManagersLocalizedMap.put(layoutManagersLabels[i], layoutManagersKeys[i]);
				layoutManagersDescriptions.add(getComponentLocalizedDescription(componentSet.lookupComponent(layoutManagersKeys[i])));
			}
		} else {
			layoutManagersLabels = new String[]{};
		}
	}
	
	private String getComponentLocalizedDescription(IComponent component) {
		IDocumentation documentation = (IDocumentation)component.getAdapter(IDocumentation.class);
		String wizardDescription = documentation.getWizardDescription();
		if (wizardDescription == null) 
			wizardDescription = ""; //$NON-NLS-1$
		return wizardDescription;
	}
	
	private String[] initializeLocalizedMap(String[] keys,
			String[] values, 
			Hashtable<String, String> labelValueMap,
			ArrayList<String> descriptions) {
		String[] labels = new String[keys.length];
		
		for (int i=0; i<keys.length; i++) {
			String label = Messages.getString(keys[i]);
			labelValueMap.put(label, values[i]);
			if (descriptions != null) {
				String decription = Messages.getString(keys[i] + ".description"); //$NON-NLS-1$
				descriptions.add(decription);
			}
			labels[i] = label;
		}
		
		return labels;
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FormLayout());
		
		setControl(container);
        setHelpContextId(ViewWizardManager.CONTAINER_SELECTION_PAGE);

        // 1/4 Base Name
		final Label containerNameLabel = new Label(container, SWT.NONE);
		final FormData formData_9_1_1 = new FormData();
		formData_9_1_1.bottom = new FormAttachment(0, 50);
		formData_9_1_1.top = new FormAttachment(0, 37);
		formData_9_1_1.right = new FormAttachment(0, 125);
		formData_9_1_1.left = new FormAttachment(0, 15);
		containerNameLabel.setLayoutData(formData_9_1_1);
		containerNameLabel.setText(Messages.getString("ContainerSelectionPage.ClassNameLabel")); //$NON-NLS-1$
		
		containerBaseNameText = new Text(container, SWT.BORDER);
		containerBaseNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage());
				String text = containerBaseNameText.getText();
				if (text.length() == 0)
					return;
				getWizardManager().getDataStore().put(ViewWizardManager.CONTAINER_BASENAME_KEY, text);
				inited = true;
			}
		});
		containerBaseNameText.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent arg0) {
					updateDescriptionText(Messages.getString("ContainerSelectionPage.baseName.description")); //$NON-NLS-1$
			}

			public void focusLost(FocusEvent arg0) {
			}
			
		});
		final FormData formData_10_1_1 = new FormData();
		formData_10_1_1.bottom = new FormAttachment(containerNameLabel, 0, SWT.BOTTOM);
		formData_10_1_1.top = new FormAttachment(containerNameLabel, -6, SWT.TOP);
		formData_10_1_1.left = new FormAttachment(0, 130);
		formData_10_1_1.right = new FormAttachment(100, -169);
		containerBaseNameText.setLayoutData(formData_10_1_1);
		containerBaseNameText.setToolTipText(Messages.getString("ContainerSelectionPage.BaseNameTip")); //$NON-NLS-1$
		containerBaseNameText.setData(NAME_KEY, "containerBaseNameText"); //$NON-NLS-1$
		
		// 2/4 UI Config Mode
		final Label uiConfigModeLabel = new Label(container, SWT.NONE);
		final FormData formData_11_1_1 = new FormData();
		formData_11_1_1.bottom = new FormAttachment(0, 77);
		formData_11_1_1.top = new FormAttachment(0, 64);
		formData_11_1_1.right = new FormAttachment(0, 125);
		formData_11_1_1.left = new FormAttachment(0, 15);
		uiConfigModeLabel.setLayoutData(formData_11_1_1);
		uiConfigModeLabel.setText(Messages.getString("ContainerSelectionPage.UIConfigModeLabel")); //$NON-NLS-1$
		
		uiConfigModesCombo = new Combo(container, SWT.READ_ONLY);
		uiConfigModesCombo.setItems(uiConfigModeLabels);
		uiConfigModesCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				getWizardManager().getDataStore().put(ViewWizardManager.UI_CONFIGURATION_MODE_KEY, uiConfigModesLocalizedMap.get(uiConfigModesCombo.getText()));
				setPageComplete(validatePage());
				
				updateDescriptionTextWithUiConfigModesCombo();
			}
		});
		uiConfigModesCombo.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent arg0) {
				updateDescriptionTextWithUiConfigModesCombo();
			}

			public void focusLost(FocusEvent arg0) {
			}
			
		});
		uiConfigModesCombo.select(0);

		final FormData formData_12_1_1 = new FormData();
		formData_12_1_1.bottom = new FormAttachment(uiConfigModeLabel, 0, SWT.BOTTOM);
		formData_12_1_1.top = new FormAttachment(uiConfigModeLabel, -6, SWT.TOP);
		formData_12_1_1.left = new FormAttachment(0, 130);
		formData_12_1_1.right = new FormAttachment(100, -169);
		uiConfigModesCombo.setLayoutData(formData_12_1_1);
		
		// 3/4 Container Type
		final Label containerTypeLabel = new Label(container, SWT.NONE);
		final FormData formData_13_1_1 = new FormData();
		formData_13_1_1.bottom = new FormAttachment(0, 104);
		formData_13_1_1.top = new FormAttachment(0, 91);
		formData_13_1_1.right = new FormAttachment(0, 125);
		formData_13_1_1.left = new FormAttachment(0, 15);
		containerTypeLabel.setLayoutData(formData_13_1_1);
		containerTypeLabel.setText(Messages.getString("ContainerSelectionPage.ContainerTypeLabel")); //$NON-NLS-1$
		
		containerTypesCombo = new Combo(container, SWT.READ_ONLY);
		containerTypesCombo.setItems(containerTypesLabels);
		containerTypesCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				getWizardManager().getDataStore().put(ViewWizardManager.CONTAINER_TYPE_KEY, containerTypesLocalizedMap.get(containerTypesCombo.getText()));
				setPageComplete(validatePage());
				
				updateDescriptionTextWithContainerTypesCombo();
			}
		});
		containerTypesCombo.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent arg0) {
				updateDescriptionTextWithContainerTypesCombo();
			}

			public void focusLost(FocusEvent arg0) {
			}
			
		});
		
		containerTypesCombo.select(1);
		final FormData formData_14_1_1 = new FormData();
		formData_14_1_1.bottom = new FormAttachment(containerTypeLabel, 0, SWT.BOTTOM);
		formData_14_1_1.top = new FormAttachment(containerTypeLabel, -6, SWT.TOP);
		formData_14_1_1.left = new FormAttachment(0, 130);
		formData_14_1_1.right = new FormAttachment(100, -169);
		containerTypesCombo.setLayoutData(formData_14_1_1);
		
		// 4/4 Layout Manager
		final Label layoutManagerLabel = new Label(container, SWT.NONE);
		final FormData formData_15_1_1 = new FormData();
		formData_15_1_1.bottom = new FormAttachment(0, 131);
		formData_15_1_1.top = new FormAttachment(0, 118);
		formData_15_1_1.right = new FormAttachment(0, 125);
		formData_15_1_1.left = new FormAttachment(0, 15);
		layoutManagerLabel.setLayoutData(formData_15_1_1);
		layoutManagerLabel.setText(Messages.getString("ContainerSelectionPage.LayoutManagerLabel")); //$NON-NLS-1$
		
		layoutManagersCombo = new Combo(container, SWT.READ_ONLY);
		layoutManagersCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				getWizardManager().getDataStore().put(ViewWizardManager.LAYOUT_MANAGER_TYPE_KEY, layoutManagersLocalizedMap.get(layoutManagersCombo.getText()));
				setPageComplete(validatePage());
				inited = true;
				
				updateDescriptionTextWithLayoutManagersCombo();
			}
		});
		layoutManagersCombo.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent arg0) {
				updateDescriptionTextWithLayoutManagersCombo();
			}

			public void focusLost(FocusEvent arg0) {
			}
			
		});

		final FormData formData_16_1_1 = new FormData();
		formData_16_1_1.bottom = new FormAttachment(layoutManagerLabel, 0, SWT.BOTTOM);
		formData_16_1_1.top = new FormAttachment(layoutManagerLabel, -6, SWT.TOP);
		formData_16_1_1.left = new FormAttachment(0, 130);
		formData_16_1_1.right = new FormAttachment(100, -169);
		layoutManagersCombo.setLayoutData(formData_16_1_1);
		
		final Label descriptionTitleLabel = new Label(container, SWT.NONE);
		final FormData formData_3_1 = new FormData();
		formData_3_1.top = new FormAttachment(0, 10);
		formData_3_1.bottom = new FormAttachment(0, 26);
		formData_3_1.right = new FormAttachment(100, -24);
		formData_3_1.left = new FormAttachment(containerBaseNameText, 18, SWT.DEFAULT);
		descriptionTitleLabel.setLayoutData(formData_3_1);
		descriptionTitleLabel.setText(Messages.getString("ContainerSelectionPage.DescriptionLabel")); //$NON-NLS-1$
		
		descriptionText = new Text(container, SWT.READ_ONLY | SWT.BORDER | SWT.WRAP);
		final FormData formData_1 = new FormData();
		/*formData_1.bottom = new FormAttachment(layoutManagersCombo, 0, SWT.BOTTOM);
		formData_1.top = new FormAttachment(descriptionTitleLabel, 5, SWT.BOTTOM);
		formData_1.left = new FormAttachment(descriptionTitleLabel, 0, SWT.LEFT);
		formData_1.right = new FormAttachment(descriptionTitleLabel, 127, SWT.LEFT);*/
		/*formData_1.bottom = new FormAttachment(100, -73);
		formData_1.top = new FormAttachment(0, 31);
		formData_1.right = new FormAttachment(100, -9);
		formData_1.left = new FormAttachment(100, -150);*/
		formData_1.bottom = new FormAttachment(100, -20);
		formData_1.top = new FormAttachment(0, 31);
		formData_1.right = new FormAttachment(100, -9);
		formData_1.left = new FormAttachment(100, -150);
		descriptionText.setLayoutData(formData_1);
		descriptionText.setData(NAME_KEY, "descriptionText"); //$NON-NLS-1$
	}
	
	final private void updateDescriptionTextWithCombo(Combo combo, ArrayList<String>descriptions) {
		int index = combo.getSelectionIndex();
		if (index < 0)
			updateDescriptionText(""); //$NON-NLS-1$
		else {
			updateDescriptionText(descriptions.get(index));
		}
	}
	
	final private void updateDescriptionTextWithUiConfigModesCombo() {
		updateDescriptionTextWithCombo(uiConfigModesCombo, uiConfigModesDescriptions);
	}
	
	final private void updateDescriptionTextWithContainerTypesCombo() {
		updateDescriptionTextWithCombo(containerTypesCombo, containerTypesDescriptions);
	}
	
	final private void updateDescriptionTextWithLayoutManagersCombo() {
		updateDescriptionTextWithCombo(layoutManagersCombo, layoutManagersDescriptions);
	}

	private String getSuggestedViewName() {
		String name = (String) getWizardManager().getDataStore().get(ViewWizardManager.APPNAME_KEY);
		IComponent component = 
			(IComponent) getWizardManager().getDataStore().get(ViewWizardManager.CONTENT_COMPONENT_KEY);
		if (component != null)
			name += TextUtils.titleCase(component.getInstanceNameRoot());
		else
			name += "Container"; //$NON-NLS-1$
		
		String suggestedName = name;
		IProject project = (IProject) getWizardManager().getDataStore().get(ViewWizardManager.PROJECT_KEY);
		IDesignerDataModel rootModel = 
			(IDesignerDataModel) getWizardManager().getDataStore().get(ViewWizardManager.ROOT_MODEL_KEY);
		for (int i = 2; !isUniqueContainerName(suggestedName, project, rootModel); i++) {
			suggestedName = name;
			suggestedName += i;
		}
		
		return suggestedName;
	}

	private boolean isValidContainerName() {
		String containerBaseName = containerBaseNameText.getText();
		IProject project = (IProject) getWizardManager().getDataStore().get(ViewWizardManager.PROJECT_KEY);
		String projectName = null;
		if (project != null)
			projectName = project.getName();
		else
			projectName = (String) getWizardManager().getDataStore().get(ViewWizardManager.PROJECT_NAME_KEY);
		return NamePropertySupport.isLegalName(containerBaseName) &&
					!containerBaseName.equalsIgnoreCase(projectName) &&
					containerBaseName.length() <= 63;
	}
	
	private static boolean isUniqueContainerName(String name, IProject project, IDesignerDataModel rootModel) {
		boolean isUnique = true;
		if (project != null) {
			WorkspaceContext wc = WorkspaceContext.getContext();
			IProjectContext pc = wc.getContextForProject(project);
			String potentialModelName = name + ViewWizardManager.MODEL_EXTENSION;
			IPath path = project.getProjectRelativePath();
			path = path.append(potentialModelName);
			isUnique = (pc == null) || pc.findSpecifierForPath(path) == null;
			if (isUnique && (rootModel != null)) {
				// check the design ref container names
				EObject appUi = SymbianModelUtils.findAppUi(rootModel);
				if (appUi != null) {
					IComponentInstance appUiInstance = Utilities.getComponentInstance(appUi);
					EObject[] children = appUiInstance.getChildren();
					for (int i = 0; i < children.length; i++) {
						EObject child = children[i];
						if (SymbianModelUtils.isDesignReference(child)) {
							IPropertySource ps = Utilities.getPropertySource(child);
							String containerName = (String) ps.getPropertyValue(SymbianModelUtils.DESIGNREF_BASENAME);
							if (name.equalsIgnoreCase(containerName))
								return false;
						}
					}
				}
			}
		}
		
		return isUnique;
	}
	
	private boolean isUniqueContainerName() {
		IProject project = (IProject) getWizardManager().getDataStore().get(ViewWizardManager.PROJECT_KEY);
		String potentialViewName = containerBaseNameText.getText();
		IDesignerDataModel rootModel = 
			(IDesignerDataModel) getWizardManager().getDataStore().get(ViewWizardManager.ROOT_MODEL_KEY);
		
		return isUniqueContainerName(potentialViewName, project, rootModel);
	}
	
	/**
     * Returns whether this page's controls currently all contain valid 
     * values. Set error string, if invalid.
     *
     * @return <code>true</code> if all controls are valid, and
     *   <code>false</code> if at least one is invalid
     */
    protected boolean validatePage() {
        if (!isValidContainerName()) {
        	setErrorMessage(Messages.getString("ContainerSelectionPage.ValidNameError")); //$NON-NLS-1$
        	return false;
        }
        
        if (!isUniqueContainerName()) {
        	setErrorMessage(Messages.getString("ContainerSelectionPage.UniqueNameError")); //$NON-NLS-1$
        	return false;
        }
        
        if (((IComponent)getWizardManager().getDataStore().get(ViewWizardManager.CONTENT_COMPONENT_KEY)).getId().equals(ViewWizardManager.SIMPLEDIALOG_COMPONENT_ID)) {
        		if (getWizardManager().getDataStore().get(ViewWizardManager.CONTAINER_TYPE_KEY) != null && 
        				getWizardManager().getDataStore().get(ViewWizardManager.CONTAINER_TYPE_KEY).equals("EQikCtContainer")) { //$NON-NLS-1$
        			setErrorMessage(Messages.getString("ContainerSelectionPage.ContainerTypeError")); //$NON-NLS-1$
                	return false;
        		}
        }

        setErrorMessage(null);
        return true;
    }
    
    protected void updateDescriptionText(IComponent activeComponent) {
		IDocumentation documentation = (IDocumentation) activeComponent.getAdapter(IDocumentation.class);
		String wizardDescription = documentation.getWizardDescription();
		if (wizardDescription == null) 
			wizardDescription = ""; //$NON-NLS-1$
		
		updateDescriptionText(wizardDescription);
	}
    
    protected void updateDescriptionText(String text) {		
		descriptionText.setText(text);
		setPageComplete(validatePage());
	}

    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.ui.wizard.ViewWizardPageBase#enteringPage()
     */
    protected void enteringPage() {
    	super.enteringPage();

        // ensure the base name is ready to be edited and changed
		if (!inited) {
			containerBaseNameText.setText(getSuggestedViewName());
			
			initializeLayoutManagerLocalizationMap();
			layoutManagersCombo.setItems(layoutManagersLabels);
			layoutManagersCombo.select(0);
			for (int i=0; i<layoutManagersLabels.length; i++) {
				if (this.layoutManagersLocalizedMap.get(layoutManagersLabels[i]).equals("com.nokia.carbide.uiq.RowLayoutManager")) {
					layoutManagersCombo.select(i);
					break;
				}
			}
			
			getWizardManager().getDataStore().put(ViewWizardManager.UI_CONFIGURATION_MODE_KEY, uiConfigModesLocalizedMap.get(uiConfigModesCombo.getItem(uiConfigModesCombo.getSelectionIndex())));
			getWizardManager().getDataStore().put(ViewWizardManager.CONTAINER_TYPE_KEY, containerTypesLocalizedMap.get(containerTypesCombo.getItem(containerTypesCombo.getSelectionIndex())));
			getWizardManager().getDataStore().put(ViewWizardManager.LAYOUT_MANAGER_TYPE_KEY, layoutManagersLocalizedMap.get(layoutManagersCombo.getItem(layoutManagersCombo.getSelectionIndex())));
			setPageComplete(validatePage());
			
			inited = false; // only if the user inits
		}
		
        containerBaseNameText.setFocus();
        containerBaseNameText.selectAll();
    }

	@Override
	public boolean isPageComplete() {		
		return super.isPageComplete();
	}

	/* (non-Javadoc)
	 * @see com.symbian.cdt.templateengine.ui.IWizardDataPage#getPageData()
	 */
	public Map<String, Object> getPageValues() {
		return Collections.singletonMap(
				AbstractWizardManagerProcessRunner.MANAGER_VALUE_STORE_KEY, 
				(Object) getWizardManager());
	}
}
