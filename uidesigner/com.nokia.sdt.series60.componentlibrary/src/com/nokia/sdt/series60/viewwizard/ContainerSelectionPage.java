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

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.*;
import com.nokia.sdt.component.customizer.IComponentCustomizerCommandFactory;
import com.nokia.sdt.component.customizer.ICustomizeComponentCommand;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.NamePropertySupport;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.workspace.IProjectContext;
import com.nokia.sdt.workspace.WorkspaceContext;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.*;

public class ContainerSelectionPage extends ViewWizardPageBase {
	
	private class ContainerTypes {
		
		private java.util.List<IComponent> containers;
		private java.util.List<IComponent> validContainers;
		private String[] names;

		public ContainerTypes() {
			getContainers();
		}
		
		public void getContainers() {
			containers = new ArrayList();
			IComponentSet componentSet = getWizardManager().getComponentSet();
			for (IComponent component : componentSet) {
				if (isContainer(component))
					containers.add(component);
			}
			Collections.sort(containers, new Comparator<IComponent>() {
				public int compare(IComponent c1, IComponent c2) {
					return c1.getFriendlyName().compareTo(c2.getFriendlyName());
				}
			});
		}
		
		public IComponent get(int index) {
			return validContainers.get(index);
		}
		
		private boolean isContainer(IComponent component) {
			// if it's abstract, it can't be used
			if (component.isAbstract())
				return false;
			
			IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
			boolean hasTopLevelContainerAttribute = 
				attributes.getBooleanAttribute(CommonAttributes.IS_TOP_LEVEL_CONTENT_CONTAINER, false);

			// if it's not a container, it can't be used
			if (!hasTopLevelContainerAttribute)
				return false;
			
			return true;
		}
		
		public void setContentComponet(IComponent contentComponent) {
			validContainers = new ArrayList();
			for (IComponent component : containers) {
				if (isValidContainer(component, contentComponent))
					validContainers.add(component);
			}
			names = null;
		}
		
		private boolean isValidContainer(IComponent component, IComponent contentComponent) {
			// if there is no initial content, any container is valid
			if (contentComponent == null) {
				return true;
			}
			
			// if the content component is also a container, it can only be the valid container,
			// so only return true if this container is the actual contentComponent
			// if the content is a content container then it is the only container allowed
			IAttributes attributes = (IAttributes) contentComponent.getAdapter(IAttributes.class);
			if (attributes.getBooleanAttribute(CommonAttributes.IS_TOP_LEVEL_CONTENT_CONTAINER, false)) {
				return component.getId().equals(contentComponent.getId());
			}
			
			boolean isValidContainer = false;
			IDesignerDataModel dataModel = null;
			try {
				dataModel = getWizardManager().createEmptyViewModel();
				EObject rootContainer = WizardUtils.addRootInstance(dataModel, component, null, null);
				dataModel.getDisplayModelForRootContainer(rootContainer);
				
				// check if the root container vetoes it for the palette
				ILayoutContainer container =
					(ILayoutContainer) EcoreUtil.getRegisteredAdapter(rootContainer, ILayoutContainer.class);
				if (!container.isValidComponentInPalette(contentComponent))
					return false;
				
				// since the content object is not a container, check if it had a customizer UI
				IComponentCustomizerCommandFactory customizerCommandFactory = 
					(IComponentCustomizerCommandFactory) getWizardManager().getDataStore().get(
							ViewWizardManager.CUSTOMIZER_COMMAND_FACTORY_KEY);
				if (customizerCommandFactory == null) {
					// if there is no command factory, just return true
					return true;
				}
				
				// Check to see if the content's customizer command will execute without error
				// if this potential container contains the content instance
				EObject contentObject = WizardUtils.addChildObject(dataModel, contentComponent, rootContainer, 
																			null, null, IDesignerDataModel.AT_END);

				ICustomizeComponentCommand customizeCommand = 
					customizerCommandFactory.createCustomizeComponentCommand(contentObject);
				if (customizeCommand.canExecute()) {
					isValidContainer = customizeCommand.execute() == null;
				}
			} 
			catch (Exception e) {
			}
			finally {
				if (dataModel != null)
					dataModel.dispose();
			}

			return isValidContainer;
		}

		public String[] getNames() {
			if (names != null)
				return names;
			
			Check.checkState(validContainers != null);
			names = new String[validContainers.size()];
			int i = 0;
			for (IComponent container : validContainers) {
				names[i++] = container.getFriendlyName();
			}
			
			return names;
		}
	}

	public static final String PAGE_NAME = "ContainerSelection"; //$NON-NLS-1$
	private Text descriptionText;
	private List containerTypeList;
	private Text containerBaseNameText;
	private Button viewSwitchingCheck;
	private Text viewSwitchDescriptionText;
	private ContainerTypes containerTypes;
	private IComponent contentComponent;
	private boolean hasContainerSelection;
	private boolean inited;
	
	public ContainerSelectionPage(ViewWizardManager manager) {
		super(PAGE_NAME, manager);
		setTitle(Messages.getString("ContainerSelectionPage.PageTitle")); //$NON-NLS-1$
		setDescription(Messages.getString("ContainerSelectionPage.PageDescription")); //$NON-NLS-1$
		setPageComplete(false);
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FormLayout());
		//
		setControl(container);
        setHelpContextId(ViewWizardManager.CONTAINER_SELECTION_PAGE);

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
		final FormData formData_10_1_1 = new FormData();
		formData_10_1_1.bottom = new FormAttachment(containerNameLabel, 0, SWT.BOTTOM);
		formData_10_1_1.top = new FormAttachment(containerNameLabel, -6, SWT.TOP);
		formData_10_1_1.left = new FormAttachment(0, 130);
		formData_10_1_1.right = new FormAttachment(100, -169);
		containerBaseNameText.setLayoutData(formData_10_1_1);
		containerBaseNameText.setToolTipText(Messages.getString("ContainerSelectionPage.BaseNameTip")); //$NON-NLS-1$
		containerBaseNameText.setData(NAME_KEY, "containerBaseNameText"); //$NON-NLS-1$

		final Label containerTypeLabel = new Label(container, SWT.NONE);
		final FormData formData_3 = new FormData();
		formData_3.top = new FormAttachment(0, 65);
		formData_3.left = new FormAttachment(containerNameLabel, 0, SWT.LEFT);
		formData_3.bottom = new FormAttachment(0, 78);
		formData_3.right = new FormAttachment(containerBaseNameText, 0, SWT.LEFT);
		containerTypeLabel.setLayoutData(formData_3);
		containerTypeLabel.setText(Messages.getString("ContainerSelectionPage.TypeLabel")); //$NON-NLS-1$
		
		containerTypeList = new List(container, SWT.V_SCROLL | SWT.BORDER);
		containerTypeList.setData(NAME_KEY, "containerTypeCombo"); //$NON-NLS-1$

		containerTypeList.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleContainerTypeChanged();
			}
		});

		final FormData formData_4 = new FormData();
		formData_4.bottom = new FormAttachment(0, 105);
		formData_4.top = new FormAttachment(containerTypeLabel, -4, SWT.TOP);
		formData_4.left = new FormAttachment(0, 130);
		formData_4.right = new FormAttachment(100, -169);
		containerTypeList.setLayoutData(formData_4);
		
		viewSwitchingCheck = new Button(container, SWT.CHECK);
		viewSwitchingCheck.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleViewSwitchChanged(viewSwitchingCheck.getSelection());
			}
		});
		final FormData formData = new FormData();
		formData.bottom = new FormAttachment(0, 140);
		formData.top = new FormAttachment(0, 120);
		formData.right = new FormAttachment(0, 205);
		formData.left = new FormAttachment(0, 15);
		viewSwitchingCheck.setLayoutData(formData);
		viewSwitchingCheck.setText(Messages.getString("ContainerSelectionPage.ViewSwitchLabel")); //$NON-NLS-1$
		viewSwitchingCheck.setToolTipText(Messages.getString("ContainerSelectionPage.ViewSwitchTip")); //$NON-NLS-1$
		viewSwitchingCheck.setData(NAME_KEY, "viewSwitchingCheck"); //$NON-NLS-1$

		viewSwitchDescriptionText = new Text(container, SWT.READ_ONLY | SWT.BORDER | SWT.WRAP);
		final FormData formData_1_1 = new FormData();
		formData_1_1.bottom = new FormAttachment(100, -15);
		formData_1_1.right = new FormAttachment(containerTypeList, 0, SWT.RIGHT);
		formData_1_1.top = new FormAttachment(0, 145);
		formData_1_1.left = new FormAttachment(0, 15);
		viewSwitchDescriptionText.setLayoutData(formData_1_1);
		viewSwitchDescriptionText.setData(NAME_KEY, "viewSwitchDescriptionText"); //$NON-NLS-1$

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
		formData_1.bottom = new FormAttachment(viewSwitchDescriptionText, 0, SWT.BOTTOM);
		formData_1.top = new FormAttachment(descriptionTitleLabel, 5, SWT.BOTTOM);
		formData_1.left = new FormAttachment(descriptionTitleLabel, 0, SWT.LEFT);
		formData_1.right = new FormAttachment(descriptionTitleLabel, 127, SWT.LEFT);
		descriptionText.setLayoutData(formData_1);
		descriptionText.setData(NAME_KEY, "descriptionText"); //$NON-NLS-1$

		container.setTabList(new Control[] {containerBaseNameText, containerTypeList, viewSwitchingCheck});
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

	private void setControlsForContentObject() {
		// if the content component is the same, don't need to change anything
		IComponent prevContentComponent = 
			(IComponent) getWizardManager().getDataStore().get(ViewWizardManager.CONTENT_COMPONENT_KEY);
		if ((contentComponent != null) && contentComponent.equals(prevContentComponent))
			return;
		
		contentComponent = prevContentComponent;
		if (containerTypes == null)
			containerTypes = new ContainerTypes();
		containerTypes.setContentComponet(contentComponent);
		
		String[] prevItems = containerTypeList.getItems();
		String[] containerNames = containerTypes.getNames();
		if (!Arrays.equals(prevItems, containerNames)) {
			containerTypeList.setItems(containerNames);
			containerTypeList.select(0);
		}
		hasContainerSelection = containerNames.length > 1;

		handleContainerTypeChanged();
	}

	protected void handleContainerTypeChanged() {
		int index = containerTypeList.getSelectionIndex();
		// this can happen if SDKs are hosed and no containers match
		if (index < 0)
			return;
		IComponent containerComponent = containerTypes.get(index);
		IDocumentation documentation = (IDocumentation) containerComponent.getAdapter(IDocumentation.class);
		String wizardDescription = documentation.getWizardDescription();
		if (wizardDescription == null) 
			wizardDescription = ""; //$NON-NLS-1$
		if (!hasContainerSelection) {
			String noSelectionText = Messages.getString("ContainerSelectionPage.NoSelectionText"); //$NON-NLS-1$
			containerTypeList.setToolTipText(noSelectionText);
		}
		else
			containerTypeList.setToolTipText(Messages.getString("ContainerSelectionPage.ContainerTypeTip")); //$NON-NLS-1$
		
		descriptionText.setText(wizardDescription);
		setPageComplete(validatePage());
		getWizardManager().getDataStore().put(ViewWizardManager.CONTAINER_COMPONENT_KEY, containerComponent);
	}
	
	protected void handleViewSwitchChanged(boolean supportViewSwitching) {
		getWizardManager().getDataStore().put(ViewWizardManager.WANTS_VIEW_SWITCHING_KEY, Boolean.valueOf(supportViewSwitching));
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
					!containerBaseName.equalsIgnoreCase(projectName);
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
        
        if (containerTypeList.getSelectionIndex() < 0) {
        	setErrorMessage(Messages.getString("ContainerSelectionPage.TypeSelectionError")); //$NON-NLS-1$
        	return false;
        }

        setErrorMessage(null);
        return true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.symbian.ui.wizard.ViewWizardPageBase#enteringPage()
     */
    protected void enteringPage() {
    	super.enteringPage();
    	// This check must be made any time the page is entered, in case the user switched projects
		Boolean value = (Boolean) getWizardManager().getDataStore().get(
					ViewWizardManager.HAS_NON_VIEW_SWITCHING_ROOT_MODEL_KEY);
		boolean hasNonViewSwitchingRootModel = (value != null) && value.booleanValue();
		if (hasNonViewSwitchingRootModel) {
			viewSwitchingCheck.setSelection(false);
			viewSwitchingCheck.setEnabled(false);
			viewSwitchDescriptionText.setText(
					Messages.getString("ContainerSelectionPage.ViewSwitchOffDescription")); //$NON-NLS-1$
		}
		else {
			if (!inited)
				viewSwitchingCheck.setSelection(true);
			viewSwitchingCheck.setEnabled(true);
			viewSwitchDescriptionText.setText(
				Messages.getString("ContainerSelectionPage.ViewSwitchDescription")); //$NON-NLS-1$
		}
		handleViewSwitchChanged(viewSwitchingCheck.getSelection());

        // ensure the base name is ready to be edited and changed
		if (!inited) {
			containerBaseNameText.setText(getSuggestedViewName());
			inited = false; // only if the user inits
		}
        containerBaseNameText.setFocus();
        containerBaseNameText.selectAll();

        setControlsForContentObject();
    }

	@Override
	public boolean isPageComplete() {
		IComponent component = 
			(IComponent) getWizardManager().getDataStore().get(ViewWizardManager.CONTENT_COMPONENT_KEY);
		if (component != null && !component.equals(contentComponent)) {
			return false;
		}
		
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
