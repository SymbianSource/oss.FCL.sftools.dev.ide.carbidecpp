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
/* START_USECASES: CU1, CU2 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.viewwizard;

import com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage;
import com.nokia.carbide.internal.api.templatewizard.ui.TemplateWizard;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.sdt.component.*;
import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.event.IEventDescriptor;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.component.symbian.properties.CompoundPropertySource;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.editor.EditorServices;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.emf.dm.*;
import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerConsts;
import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerPlugin;
import com.nokia.carbide.cpp.uiq.ui.UIQUserInterfacePlugin;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.symbian.dm.*;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.sdt.symbian.workspace.ISymbianProjectContext;
import com.nokia.sdt.symbian.workspace.impl.ProjectContextProvider;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.ui.UITaskUtils;
import com.nokia.sdt.workspace.*;

import org.eclipse.cdt.core.model.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.*;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.views.properties.IPropertySource;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ViewWizardManager {
	// store keys
	public static final String PROJECT_NAME_KEY = "PROJECT_NAME"; //$NON-NLS-1$
	public static final String PROJECT_KEY = "PROJECT"; //$NON-NLS-1$
	public static final String APPNAME_KEY = "APPLICATION_NAME"; //$NON-NLS-1$
	public static final String LANGUAGE_ID_KEY = "LANGUAGE_ID"; //$NON-NLS-1$
	public static final String APPUID_KEY = "APP_UID"; //$NON-NLS-1$
	public static final String ROOT_MODEL_KEY = "ROOT_MODEL"; //$NON-NLS-1$
	public static final String COMPONENT_SET_KEY = "COMPONENT_SET"; //$NON-NLS-1$
	public static final String CONTENT_COMPONENT_KEY = "CONTENT_COMPONENT"; //$NON-NLS-1$
	public static final String ROOT_CONTAINER_KEY = "ROOT_CONTAINER"; //$NON-NLS-1$
	public static final String CUSTOMIZER_COMMAND_FACTORY_KEY = "CUSTOMIZER_COMMAND_FACTORY"; //$NON-NLS-1$
	public static final String CONTAINER_BASENAME_KEY = "CONTAINER_BASENAME"; //$NON-NLS-1$
	public static final String UI_CONFIGURATION_MODE_KEY = "UI_CONFIGURATION_MODE"; //$NON-NLS-1$
	public static final String CONTAINER_TYPE_KEY = "CONTAINER_TYPE"; //$NON-NLS-1$
	public static final String LAYOUT_MANAGER_TYPE_KEY = "LAYOUT_MANAGER_TYPE"; //$NON-NLS-1$
	public static final String CONTAINER_COMPONENT_KEY = "CONTAINER_COMPONENT"; //$NON-NLS-1$
	public static final String TEMP_DATA_MODEL_KEY = "TEMP_DATA_MODEL"; //$NON-NLS-1$
	public static final String VIEW_MODEL_KEY = "VIEW_MODEL"; //$NON-NLS-1$
	public static final String DEFAULT_EVENT_HANDER_BODIES_KEY = "DEFAULT_EVENT_HANDLERS"; //$NON-NLS-1$
	public static final String MMP_FILE_PATH_KEY = "MMP_FILE_PATH"; //$NON-NLS-1$
	
	public static final String MODEL_EXTENSION = ".uidesign"; //$NON-NLS-1$
	
	// component IDs
	public static final String APPLICATION_COMPONENT_ID = UIQModelUtils.UIQ_APPLICATION;
	public static final String APPLICATION_DOCUMENT_COMPONENT_ID = UIQModelUtils.UIQ_APPLICATION_DOCUMENT;
	public static final String APPLICATION_UI_COMPONENT_ID = UIQModelUtils.UIQ_APPLICATION_UI;
	
	public static final String VIEWDIALOGBASE_COMPONENT_ID = UIQModelUtils.UIQ_VIEWDIALOGBASE;
	public static final String VIEW_COMPONENT_ID = UIQModelUtils.UIQ_CQIKVIEW;
	public static final String SINGLEPAGEVIEW_COMPONENT_ID = UIQModelUtils.UIQ_CQIKSINGLEPAGEVIEW;
	public static final String MULTIPAGEVIEW_COMPONENT_ID = UIQModelUtils.UIQ_CQIKMULTIPAGEVIEW;
	public static final String VIEWDIALOG_COMPONENT_ID = UIQModelUtils.UIQ_CQIKVIEWDIALOG;
	public static final String SIMPLEDIALOG_COMPONENT_ID = UIQModelUtils.UIQ_CQIKSIMPLEDIALOG;
	
	public static final String VIEW_CONFIGURATIONS_GROUP = UIQModelUtils.UIQ_VIEW_CONFIGURATIONS_GROUP;
	public static final String VIEW_CONFIGURATION = UIQModelUtils.UIQ_VIEW_CONFIGURATION;
	public static final String VIEW_LAYOUTS_GROUP = UIQModelUtils.UIQ_VIEW_LAYOUTS_GROUP;
	public static final String VIEW_LAYOUT = UIQModelUtils.UIQ_VIEW_LAYOUT;
	public static final String VIEW_PAGE = UIQModelUtils.UIQ_VIEW_PAGE;
	
	public static final String SIMPLEDIALOG_CONFIGURATIONS_GROUP = UIQModelUtils.UIQ_SIMPLEDIALOG_CONFIGURATIONS_GROUP;
	public static final String SIMPLEDIALOG_CONFIGURATION = UIQModelUtils.UIQ_SIMPLEDIALOG_CONFIGURATION;
	public static final String SIMPLEDIALOG_CONTAINERS_GROUP = UIQModelUtils.UIQ_SIMPLEDIALOG_CONTAINERS_GROUP;
	
	public static final String CQIKCONTAINER = UIQModelUtils.UIQ_CQIKCONTAINER;
	public static final String CONTROL_COLLECTION = UIQModelUtils.UIQ_CONTROL_COLLECTION;
	public static final String COMMAND_LISTS_GROUP = UIQModelUtils.UIQ_COMMAND_LISTS_GROUP;
	public static final String COMMAND_LIST = UIQModelUtils.UIQ_COMMAND_LIST;
	public static final String COMMAND_ID = UIQModelUtils.UIQ_COMMAND_ID;
	public static final String COMMAND = UIQModelUtils.UIQ_COMMAND;
	
	// property IDs
	public static final String UID_PROPERTY_NAME = "uid"; //$NON-NLS-1$
	public static final String CAPTION_PROPERTY_NAME = "caption"; //$NON-NLS-1$
	public static final String SHORT_CAPTION_PROPERTY_NAME = "shortCaption"; //$NON-NLS-1$
	public static final String DOCUMENT_BASE_PROPERTY_NAME = "documentBase"; //$NON-NLS-1$
	
	public static final String VIEWDIALOGBASE_PROPERTY_BASECLASSINFO = "baseClassInfo"; //$NON-NLS-1$
	public static final String VIEWDIALOG_PROPERTY_BASECLASSLIBRARY = "baseClassLibrary"; //$NON-NLS-1$
	public static final String VIEWDIALOG_PROPERTY_BASECLASSHEADER = "baseClassHeader"; //$NON-NLS-1$
	public static final String VIEWDIALOG_PROPERTY_BASECLASSNAME = "baseClassName"; //$NON-NLS-1$
	public static final String VIEW_PROPERTY_TYPE = "type"; //$NON-NLS-1$
	
	public static final String UICONFIGURATION_PROPERTY_UICONFIG = "uiconfig"; //$NON-NLS-1$
	public static final String UICONFIGURATION_PROPERTY_COMMANDLIST = "commandList"; //$NON-NLS-1$
	public static final String UICONFIGURATION_PROPERTY_VIEWORCONTAINER = "viewOrContainer"; //$NON-NLS-1$
	
	public static final String CQIKCONTAINER_PROPERTY_SCROLLABLE = "scrollable"; //$NON-NLS-1$
	
	public static final String COMMANDID_PROPERTY_USERCOMMANDID = "commandId"; //$NON-NLS-1$
	public static final String COMMANDID_PROPERTY_SYSTEMCOMMANDID = "systemCommandId"; //$NON-NLS-1$
	
	public static final String COMMAND_PROPERTY_TYPE = "type"; //$NON-NLS-1$
	public static final String COMMAND_PROPERTY_STATEFLAGS = "stateFlags"; //$NON-NLS-1$
	public static final String COMMAND_PROPERTY_STATEFLAGS_DEBUGONLY = "EQikCmdFlagDebugOnly"; //$NON-NLS-1$
	public static final String COMMAND_PROPERTY_TEXT = "text"; //$NON-NLS-1$
	public static final String COMMAND_PROPERTY_SHORTTEXT = "shortText"; //$NON-NLS-1$
	public static final String COMMAND_PROPERTY_COMMANDID = "commandId"; //$NON-NLS-1$
	
	// constants
	private static final String CHANGE_ITEM_HANDLER_NAME = "HandleChangeSelectedSettingItemL";//$NON-NLS-1$
	private static final String SELECTED_EVENT_ID = "selected"; //$NON-NLS-1$
	private static final String CLASS_NAME_PREFIX = "C";//$NON-NLS-1$
	private static final String APPLICATION_INSTANCE_NAME_POSTFIX = "Application";//$NON-NLS-1$
	private static final String APPLICATION_DOCUMENT_INSTANCE_NAME_POSTFIX = "Document";//$NON-NLS-1$
	private static final String APPLICATION_UI_INSTANCE_NAME_POSTFIX = "AppUi";//$NON-NLS-1$
	
	private static final String VIEW_CONSTANT_TYPE_SINGLEPAGE = "singlePage";//$NON-NLS-1$
	private static final String VIEW_CONSTANT_TYPE_MULTIPAGE = "multiPage";//$NON-NLS-1$
	private static final String VIEW_CONSTANT_TYPE_DIALOG = "dialog";//$NON-NLS-1$
	
	private static final String LIBRARY_VIEWS = "QikCore.lib";//$NON-NLS-1$
	private static final String LIBRARY_SIMPLEDIALOG = "qikdlg.lib";//$NON-NLS-1$
	
	private static final String HEADER_VIEW_SINGLEPAGE = "QikViewBase.h";//$NON-NLS-1$
	private static final String HEADER_VIEW_MULTIPAGE = "QikMultiPageViewBase.h";//$NON-NLS-1$
	private static final String HEADER_VIEW_DIALOG = "QikViewDialog.h";//$NON-NLS-1$
	private static final String HEADER_SIMPLEDIALOG = "QikSimpleDialog.h";//$NON-NLS-1$
	
	private static final String CLASS_VIEW_MULTIPAGE = "CQikMultiPageViewBase";//$NON-NLS-1$
	private static final String CLASS_VIEW_SINGLEPAGE = "CQikViewBase";//$NON-NLS-1$
	private static final String CLASS_VIEW_DIALOG = "CQikViewDialog";//$NON-NLS-1$
	private static final String CLASS_SIMPLEDIALOG = "CQikSimpleDialog";//$NON-NLS-1$
	
	// template keys
	public static final String TEMPLATE_KEY_APPUI_NAME = "appUiName"; //$NON-NLS-1$
    
	public static final int DEFAULT_HEIGHT = 208;
	public static final int DEFAULT_WIDTH = 176;
	
	// help context ids
	public static final String APPLICATION_DEFINITION_PAGE = "application_properties_page";//$NON-NLS-1$
	public static final String INITIAL_CONTENT_PAGE = "design_selection_page";//$NON-NLS-1$
	public static final String CUSTOMIZER_PAGE = "content_variants_page";//$NON-NLS-1$
	public static final String CONTAINER_SELECTION_PAGE = "container_page";//$NON-NLS-1$
	
	private ApplicationDefinitionPage appDefinitionPage;
	private InitialContentPage initialContentPage;
	private CustomizerPage customizerPage;
	private ContainerSelectionPage containerSelectionPage;
	private IComponentCustomizerUI customizerUI;
	private ViewWizardPageBase currentPage;
	private boolean extantRootModel;
	private Language appLanguages[];
	
	// not using dialog settings because no need for persistence, 
	// and can't store obj refs in DialogSettings
	private Map dataStore;
	
	private DesignerDataModelProvider dataModelProvider;
	private IComponentProvider componentProvider;
	
	private List pages;
	
	private IWizard wizard;
	private ITemplate template;

	static class DefaultEventBinding {
		private IDesignerDataModel dataModel;
		private IComponentInstance instance;
		private String eventName;
		private String handlerName;
		private String handlerText;

		public DefaultEventBinding(IDesignerDataModel model, IComponentInstance instance,
				String eventName, String handlerName, String handlerText) {
			this.dataModel = model;
			this.instance = instance;
			this.eventName = eventName;
			this.handlerName = handlerName;
			this.handlerText = handlerText;
		}
		
		/** Create the event binding and commit it to the data model */
		public void createEventBinding() {
			IComponentEventDescriptorProvider eventProvider = ModelUtils.getComponentEventDescriptorProvider(instance.getEObject());
			IEventDescriptor descriptor = eventProvider.findEventDescriptor(eventName);
			Command command = dataModel.createAddEventBindingCommand(instance.getEObject(), descriptor, handlerName); 
			Check.checkState(command.canExecute());
			command.execute();
		}
		
		/** Get the bound event */
		public IEventBinding getEventBinding() {
			return instance.findEventBinding(eventName);
		}
		
		/** Write the event handler to the file */
		public void writeEventHandler(IProjectContext pc) throws CoreException {
			IEventBinding binding = getEventBinding();
			Check.checkState(binding != null);
			SourceLocation location = pc.getSourceGenProvider().lookupEventBindingSource(binding);
			Check.checkState(location != null);
			
			ITranslationUnit tunit = (ITranslationUnit)CoreModel.getDefault().create(location.file);
			IBuffer buffer = tunit.getBuffer();
			
			char[] contents = tunit.getContents();

			// replace the TO-DO comment with the new one
			
			// replace the existing line with the handler text
			int endIdx = location.insertOffset;
			int endOffset = location.offset + location.length;
			while (endIdx < endOffset && contents[endIdx] != '\r' && contents[endIdx] != '\n')
				endIdx++;
			
			int currentTextLength = endIdx - location.insertOffset; 
			buffer.replace(location.insertOffset, currentTextLength, handlerText);
			
			buffer.save(null, true);
			buffer.close();
			tunit.close();
		}
	}

	interface IProjectSelectionListener {
		void projectSelectionChanged(IProject project);
	}
	
	private List<IProjectSelectionListener> projectSelectionListeners;
	
	/**
	 * 
	 */
	public ViewWizardManager() {
		FeatureUseTrackerPlugin.getFeatureUseProxy().startUsingFeature(FeatureUseTrackerConsts.CARBIDE_UI_DESIGNER);
		dataModelProvider = new DesignerDataModelProvider();
		ComponentSystem cs = ComponentSystem.getComponentSystem();
		try {
			componentProvider = cs.getProvider(ComponentProvider.PROVIDER_ID);
		} 
		catch (CoreException e) {
			Check.reportFailure(Messages.getString("ViewWizard.ComponentProviderError"), e); //$NON-NLS-1$
		}
	}
	
	public Map getDataStore() {
		if (dataStore == null)
			dataStore = new HashMap();

		return dataStore;
	}

	public DesignerDataModelProvider getDataModelProvider() {
		return dataModelProvider;
	}
	
	public IComponentProvider getComponentProvider() {
		return componentProvider;
	}
	
	public void setCurrentPage(ViewWizardPageBase page) {
		currentPage = page;
	}
	
	public ViewWizardPageBase getCurrentPage() {
		return currentPage;
	}

	public List createPages(IWorkbenchWizard wizard, IWorkbench workbench, IStructuredSelection selectedResources) {
		this.wizard = wizard;
		if (pages == null) {
			pages = new ArrayList();
			appDefinitionPage = new ApplicationDefinitionPage(selectedResources, this);
			pages.add(appDefinitionPage);
			initialContentPage = new InitialContentPage(this);
			pages.add(initialContentPage);
			customizerPage = new CustomizerPage(this);
			pages.add(customizerPage);
			containerSelectionPage = new ContainerSelectionPage(this);
			pages.add(containerSelectionPage);
		}
		return pages;
	}
	
	public IWizardDataPage[] createAdditionalPages(IWorkbenchWizard wizard, IWorkbench workbench, IStructuredSelection selection) {
		createPages(wizard, workbench, selection);
		return getPages();
	}
	
	public IWizardDataPage[] getPages() {
		Check.checkContract(pages != null);
		return (IWizardDataPage[]) pages.toArray(new IWizardDataPage[pages.size()]);
	}
	
	private void createRootModelFromWizardData(ISymbianProjectContext symbianProjectContext, boolean isLegacy) throws Exception {
		IProject project = (IProject) getDataStore().get(PROJECT_KEY);
		Check.checkContract(project != null);
		IDesignerDataModel dataModel = getDataModelProvider().createTemporary();
		dataModel.setComponentSet(getComponentSet());
		
		// set rls as the default localization format
		dataModel.setProperty(SymbianModelUtils.SYMBIAN_LOCALIZATION_FILE_FORMAT, SymbianModelUtils.RLS_FILE_FORMAT);
		
		// set the language in the string bundle
		IDesignerData data = ((DesignerDataModel) dataModel).getDesignerData();
		Integer languageID = (Integer) getDataStore().get(LANGUAGE_ID_KEY);
		Language language = new Language(languageID.intValue());
		data.getStringBundle().setDefaultLanguage(language);
		symbianProjectContext.setCurrentLanguage(language);
		
		// set properties
		String baseName = (String) getDataStore().get(APPNAME_KEY);
		Check.checkContract(baseName != null);
		dataModel.setProperty(DesignerDataModel.ROOT_APPLICATION_NAME, baseName);
		WizardUtils.addSourceGenPropertiesFromTemplate(dataModel, template, isLegacy);
		
		IComponent applicationComponent = 
			getComponentSet().lookupComponent(APPLICATION_COMPONENT_ID);
        String appInstanceName = baseName + APPLICATION_INSTANCE_NAME_POSTFIX;
        String appClassName = CLASS_NAME_PREFIX + appInstanceName; //$NON-NLS-1$
        EObject application = WizardUtils.addRootInstance(dataModel, applicationComponent, appInstanceName, appClassName); //$NON-NLS-1$
		IPropertySource propertySource = WizardUtils.getProperties(application);
		String appUID = (String) getDataStore().get(APPUID_KEY);
		if (appUID == null)
			appUID = getAppUIDFromTemplate();
		propertySource.setPropertyValue(UID_PROPERTY_NAME, appUID);
		propertySource.setPropertyValue(DOCUMENT_BASE_PROPERTY_NAME, baseName);
		
		IComponent applicationDocumentComponent = getComponentSet().lookupComponent(APPLICATION_DOCUMENT_COMPONENT_ID);
        String documentInstanceName = baseName + APPLICATION_DOCUMENT_INSTANCE_NAME_POSTFIX; 
        String documentClassName = CLASS_NAME_PREFIX + documentInstanceName;
        EObject document = WizardUtils.addChildObject(dataModel, applicationDocumentComponent, application, 
										documentInstanceName, documentClassName, IDesignerDataModel.AT_END);
		
        IComponent appuiComponent = getAppUiComponent();
		String appUiName = baseName + APPLICATION_UI_INSTANCE_NAME_POSTFIX; 
		String appUiClassName = CLASS_NAME_PREFIX + appUiName; 
		EObject appui = WizardUtils.addChildObject(dataModel, appuiComponent, document, 
										appUiName, appUiClassName, IDesignerDataModel.AT_END);
		propertySource = WizardUtils.getProperties(appui);
		propertySource.setPropertyValue(SHORT_CAPTION_PROPERTY_NAME, baseName);
		propertySource.setPropertyValue(CAPTION_PROPERTY_NAME, baseName);
		
		if (template != null)
			template.getTemplateValues().put(TEMPLATE_KEY_APPUI_NAME, appUiName);
		dataModel.setProperty(DesignerDataModel.ROOT_CONTAINER_OVERRIDE_ID, appUiName);
		
		getDataStore().put(ROOT_MODEL_KEY, dataModel);
	}
	
	private String getAppUIDFromTemplate() {
		Check.checkState(template != null);
		String appUID = (String) template.getTemplateValues().get("uid3"); //$NON-NLS-1$
		Check.checkState(appUID != null);
		return appUID;
	}

	private void createViewModelFromWizardData(Language language) throws Exception {
		IProject project = (IProject) getDataStore().get(PROJECT_KEY);
		Check.checkContract(project != null);
		DesignerDataModel dataModel = (DesignerDataModel) getDataModelProvider().createTemporary();
		dataModel.setComponentSet(getComponentSet());
		
		// ensure the view model's string bundle is synched with the application and the
		// current language is set
		ILocalizedStringBundle stringBundle = dataModel.getDesignerData().getStringBundle();
		if (appLanguages != null) {
			for (Language l : appLanguages) {
				stringBundle.addLocalizedStringTable(l);
			}
		}
		stringBundle.setDefaultLanguage(language);

		// set properties
		IDesignerDataModel rootModel = (IDesignerDataModel) getDataStore().get(ROOT_MODEL_KEY);
		WizardUtils.addSourceGenPropertiesFromModel(dataModel, rootModel);
		
		
		String rootComponentId = ((IComponent)getDataStore().get(CONTENT_COMPONENT_KEY)).getId();
		IComponent rootComponent = null;
		EObject rootInstance = null;
		if (rootComponentId.equals(SINGLEPAGEVIEW_COMPONENT_ID) ||
				rootComponentId.equals(MULTIPAGEVIEW_COMPONENT_ID) ||
				rootComponentId.equals(VIEWDIALOG_COMPONENT_ID)) {
			
			rootComponent = getComponentSet().lookupComponent(VIEW_COMPONENT_ID);
			String rootClassName = getViewRootClassName();
			rootInstance = WizardUtils.addRootInstance(dataModel, rootComponent, getViewRootName(), rootClassName);
			
			IPropertySource rootInstanceProperties = WizardUtils.getProperties(rootInstance);
			setIsAppUIContainerPropertyFromAttribute(getComponentSet().lookupComponent(rootComponentId), rootInstanceProperties);
			if (rootComponentId.equals(SINGLEPAGEVIEW_COMPONENT_ID)) {
				rootInstanceProperties.setPropertyValue(VIEW_PROPERTY_TYPE, VIEW_CONSTANT_TYPE_SINGLEPAGE); 
				setBaseClassInfoProperty(rootInstanceProperties, LIBRARY_VIEWS, HEADER_VIEW_SINGLEPAGE, CLASS_VIEW_SINGLEPAGE);
				
			} else if (rootComponentId.equals(MULTIPAGEVIEW_COMPONENT_ID)) {
				rootInstanceProperties.setPropertyValue(VIEW_PROPERTY_TYPE, VIEW_CONSTANT_TYPE_MULTIPAGE);
				setBaseClassInfoProperty(rootInstanceProperties, LIBRARY_VIEWS, HEADER_VIEW_MULTIPAGE, CLASS_VIEW_MULTIPAGE);
				
			}   else if (rootComponentId.equals(VIEWDIALOG_COMPONENT_ID)) {
				rootInstanceProperties.setPropertyValue(VIEW_PROPERTY_TYPE, VIEW_CONSTANT_TYPE_DIALOG);
				setBaseClassInfoProperty(rootInstanceProperties, LIBRARY_VIEWS, HEADER_VIEW_DIALOG, CLASS_VIEW_DIALOG);
			}
			
			IComponent viewConfigurationsComponent = getComponentSet().lookupComponent(VIEW_CONFIGURATIONS_GROUP);
			String viewConfigurationsName = "viewConfigurations";//$NON-NLS-1$
			EObject viewConfigurations = 
				WizardUtils.addChildObject(dataModel, viewConfigurationsComponent, rootInstance, 
												viewConfigurationsName, null, IDesignerDataModel.AT_END);
			
			IComponent viewConfigurationComponent = getComponentSet().lookupComponent(VIEW_CONFIGURATION);
			String viewConfigurationName = "viewConfiguration1";//$NON-NLS-1$
			EObject viewConfiguration = 
				WizardUtils.addChildObject(dataModel, viewConfigurationComponent, viewConfigurations, 
												viewConfigurationName, null, IDesignerDataModel.AT_END);
			
			IPropertySource viewConfigurationProperties = WizardUtils.getProperties(viewConfiguration);
			viewConfigurationProperties.setPropertyValue(UICONFIGURATION_PROPERTY_UICONFIG, getDataStore().get(UI_CONFIGURATION_MODE_KEY));
			viewConfigurationProperties.setPropertyValue(UICONFIGURATION_PROPERTY_COMMANDLIST, "commandList1");//$NON-NLS-1$
			
			IComponent viewLayoutsComponent = getComponentSet().lookupComponent(VIEW_LAYOUTS_GROUP);
			String viewLayoutsName = "viewLayouts";//$NON-NLS-1$
			EObject viewLayouts = 
				WizardUtils.addChildObject(dataModel, viewLayoutsComponent, rootInstance, 
						viewLayoutsName, null, IDesignerDataModel.AT_END);
			
			IComponent viewLayoutComponent = getComponentSet().lookupComponent(VIEW_LAYOUT);
			String viewLayoutName = "viewLayout1";//$NON-NLS-1$
			EObject viewLayout = 
				WizardUtils.addChildObject(dataModel, viewLayoutComponent, viewLayouts, 
												viewLayoutName, null, IDesignerDataModel.AT_END);			
			viewConfigurationProperties.setPropertyValue(UICONFIGURATION_PROPERTY_VIEWORCONTAINER, viewLayoutName);
			
			/*IComponent viewPageComponent = getComponentSet().lookupComponent(VIEW_PAGE);
			String viewPageName = "viewPage1";//$NON-NLS-1$
			EObject viewPage = 
				WizardUtils.addChildObject(dataModel, viewPageComponent, viewLayout, 
						viewPageName, null, IDesignerDataModel.AT_END);*/
			
			EObject container = WizardUtils.getSpecificObject(CQIKCONTAINER, dataModel);			
			IPropertySource containerProperties = WizardUtils.getProperties(container);
			containerProperties.setPropertyValue(CQIKCONTAINER_PROPERTY_SCROLLABLE, getDataStore().get(CONTAINER_TYPE_KEY));
			
			createLayoutManager(dataModel, container);
		} else if (rootComponentId.equals(SIMPLEDIALOG_COMPONENT_ID)) {
			rootComponent = getComponentSet().lookupComponent(SIMPLEDIALOG_COMPONENT_ID);
			String rootClassName = getViewRootClassName();
			rootInstance = WizardUtils.addRootInstance(dataModel, rootComponent, getViewRootName(), rootClassName);
			
			IPropertySource rootInstanceProperties = WizardUtils.getProperties(rootInstance);
			setIsAppUIContainerPropertyFromAttribute(rootComponent, rootInstanceProperties);
			setBaseClassInfoProperty(rootInstanceProperties, LIBRARY_SIMPLEDIALOG, HEADER_SIMPLEDIALOG, CLASS_SIMPLEDIALOG);
			
			IComponent dialogConfigurationsComponent = getComponentSet().lookupComponent(SIMPLEDIALOG_CONFIGURATIONS_GROUP);
			String dialogConfigurationsName = "dialogConfigurations";//$NON-NLS-1$
			EObject dialogConfigurations = 
				WizardUtils.addChildObject(dataModel, dialogConfigurationsComponent, rootInstance, 
												dialogConfigurationsName, null, IDesignerDataModel.AT_END);
			
			IComponent dialogConfigurationComponent = getComponentSet().lookupComponent(SIMPLEDIALOG_CONFIGURATION);
			String dialogConfigurationName = "dialogConfiguration1";//$NON-NLS-1$
			EObject dialogConfiguration = 
				WizardUtils.addChildObject(dataModel, dialogConfigurationComponent, dialogConfigurations, 
												dialogConfigurationName, null, IDesignerDataModel.AT_END);
			IPropertySource dialogConfigurationProperties = WizardUtils.getProperties(dialogConfiguration);
			dialogConfigurationProperties.setPropertyValue(UICONFIGURATION_PROPERTY_UICONFIG, getDataStore().get(UI_CONFIGURATION_MODE_KEY));
			dialogConfigurationProperties.setPropertyValue(UICONFIGURATION_PROPERTY_COMMANDLIST, "commandList1");//$NON-NLS-1$
			
			IComponent dialogContainersComponent = getComponentSet().lookupComponent(SIMPLEDIALOG_CONTAINERS_GROUP);
			String dialogCotnainersName = "containersGroup";//$NON-NLS-1$
			EObject dialogContainers = 
				WizardUtils.addChildObject(dataModel, dialogContainersComponent, rootInstance, 
						dialogCotnainersName, null, IDesignerDataModel.AT_END);
			
			IComponent containerComponent = getComponentSet().lookupComponent(CQIKCONTAINER);
			String containerName = "container1";//$NON-NLS-1$
			EObject container = 
				WizardUtils.addChildObject(dataModel, containerComponent, dialogContainers, 
												containerName, null, IDesignerDataModel.AT_END);
			IPropertySource containerProperties = WizardUtils.getProperties(container);
			containerProperties.setPropertyValue(CQIKCONTAINER_PROPERTY_SCROLLABLE, getDataStore().get(CONTAINER_TYPE_KEY));
			dialogConfigurationProperties.setPropertyValue(UICONFIGURATION_PROPERTY_VIEWORCONTAINER, containerName);
			
			createLayoutManager(dataModel, container);
		}
		
		addControlCollectionToViewModel(dataModel, rootInstance);
		addCommandRelatedInstances(dataModel, rootInstance, rootComponentId);
		
		IDisplayModel displayModel = dataModel.getDisplayModelForRootContainer(rootInstance);
		WizardUtils.addDefaultBoundsToInstance(rootInstance, displayModel, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		getDataStore().put(VIEW_MODEL_KEY, dataModel);
	}

	private void setIsAppUIContainerPropertyFromAttribute(
			IComponent rootComponent, IPropertySource rootInstanceProperties) {
		IAttributes attributes = (IAttributes)rootComponent.getAdapter(IAttributes.class);
		if (attributes != null && attributes.getAttribute(UIQModelUtils.VIEWDIALOG_ATTRIBUTE_ISAPPUICONTAINER) != null) {
			rootInstanceProperties.setPropertyValue(UIQModelUtils.VIEWDIALOG_PROPERTY_ISAPPUICONTAINER, attributes.getAttribute(UIQModelUtils.VIEWDIALOG_ATTRIBUTE_ISAPPUICONTAINER));
		}
	}

	private void setBaseClassInfoProperty(IPropertySource rootInstanceProperties, String library, String header, String name) {
		CompoundPropertySource baseClassInfo = (CompoundPropertySource)rootInstanceProperties.getPropertyValue(VIEWDIALOGBASE_PROPERTY_BASECLASSINFO);
		baseClassInfo.setPropertyValue(VIEWDIALOG_PROPERTY_BASECLASSLIBRARY, library);
		baseClassInfo.setPropertyValue(VIEWDIALOG_PROPERTY_BASECLASSHEADER, header);
		baseClassInfo.setPropertyValue(VIEWDIALOG_PROPERTY_BASECLASSNAME, name);
	}

	private void createLayoutManager(DesignerDataModel dataModel,
			EObject container) {
		String requiredLayoutManagerType = (String)getDataStore().get(LAYOUT_MANAGER_TYPE_KEY);
		EObject layoutManager = WizardUtils.getObjectWithAttribute(CommonAttributes.IS_LAYOUT_MANAGER, "true", dataModel);//$NON-NLS-1$
		boolean createLayoutManager = true;
		if (layoutManager != null) {
			if (((INode)layoutManager).getComponentId().equals(requiredLayoutManagerType))
				createLayoutManager = false;
			else {
				EList children = ((INode)container).getChildren();
				children.remove(layoutManager);
			}
		}  
		if (createLayoutManager) {
			IComponent layoutManagerComponent = getComponentSet().lookupComponent(requiredLayoutManagerType);
			String layoutManagerName = layoutManagerComponent.getInstanceNameRoot() + "1";//$NON-NLS-1$
			layoutManager = 
				WizardUtils.addChildObject(dataModel, layoutManagerComponent, container, 
						layoutManagerName, null, IDesignerDataModel.AT_END);
		}
	}
	
	private void addControlCollectionToViewModel(DesignerDataModel dataModel, EObject rootInstance) {
		IComponent controlCollectionComponent = getComponentSet().lookupComponent(CONTROL_COLLECTION);
		String controlCollectionName = "controlCollection";//$NON-NLS-1$
		EObject controlCollection = 
			WizardUtils.addChildObject(dataModel, controlCollectionComponent, rootInstance, 
					controlCollectionName, null, IDesignerDataModel.AT_END);
	}
	
	private void addCommandRelatedInstances(DesignerDataModel dataModel, EObject rootInstance, String rootComponentId) {
		IComponent commandListsGroupComponent = getComponentSet().lookupComponent(COMMAND_LISTS_GROUP);
		String commandListsGroupName = "commandLists";//$NON-NLS-1$
		EObject commandListsGroup = 
			WizardUtils.addChildObject(dataModel, commandListsGroupComponent, rootInstance, 
					commandListsGroupName, null, IDesignerDataModel.AT_END);
		
		IComponent commandListComponent = getComponentSet().lookupComponent(COMMAND_LIST);
		String commandListName = "commandList1";//$NON-NLS-1$
		EObject commandList = 
			WizardUtils.addChildObject(dataModel, commandListComponent, commandListsGroup, 
					commandListName, null, IDesignerDataModel.AT_END);
		
		if (!rootComponentId.equals(VIEWDIALOG_COMPONENT_ID)) {
			IComponent commandIdComponent = getComponentSet().lookupComponent(COMMAND_ID);
			String commandIdName = "exitCommandId";//$NON-NLS-1$
			EObject commandId = 
				WizardUtils.addChildObject(dataModel, commandIdComponent, commandListsGroup, 
						commandIdName, null, IDesignerDataModel.AT_END);
			IPropertySource commandIdProperties = WizardUtils.getProperties(commandId);			
			commandIdProperties.setPropertyValue(COMMANDID_PROPERTY_USERCOMMANDID, "");//$NON-NLS-1$
			commandIdProperties.setPropertyValue(COMMANDID_PROPERTY_SYSTEMCOMMANDID, "EEikCmdExit");//$NON-NLS-1$
			
			IComponent commandComponent = getComponentSet().lookupComponent(COMMAND);
			String commandName = "exitCommand";//$NON-NLS-1$
			EObject command = 
				WizardUtils.addChildObject(dataModel, commandComponent, commandList, 
						commandName, null, IDesignerDataModel.AT_END);
			IPropertySource commandProperties = WizardUtils.getProperties(command);			
			commandProperties.setPropertyValue(COMMAND_PROPERTY_TYPE, "Screen");//$NON-NLS-1$
			IPropertySource stateFlagsProperties = (IPropertySource)commandProperties.getPropertyValue(COMMAND_PROPERTY_STATEFLAGS);
			stateFlagsProperties.setPropertyValue(COMMAND_PROPERTY_STATEFLAGS_DEBUGONLY, "true");//$NON-NLS-1$
			commandProperties.setPropertyValue(COMMAND_PROPERTY_TEXT, "Exit");//$NON-NLS-1$
			commandProperties.setPropertyValue(COMMAND_PROPERTY_SHORTTEXT, "Exit");//$NON-NLS-1$
			commandProperties.setPropertyValue(COMMAND_PROPERTY_COMMANDID, "exitCommandId");//$NON-NLS-1$
		}
	}

	/**
	 * Get list of default event bindings
	 * @return
	 */
	private List getDefaultEventHandlerBodies() {
		Map store = getDataStore();
		List<DefaultEventBinding> bindings = (List<DefaultEventBinding>) store.get(DEFAULT_EVENT_HANDER_BODIES_KEY);
		if (bindings == null) {
			bindings = new ArrayList<DefaultEventBinding>();
			store.put(DEFAULT_EVENT_HANDER_BODIES_KEY, bindings);
		}
		return bindings;
	}
	
	/**
	 * Edit source code, writing the bodies of selected event handlers
	 * with our code.
	 * @param pc 
	 */
	private void writeDefaultEventHandlerBodies(IProjectContext pc) {
		if (pc.getSourceGenProvider() == null)
			return;
		List<DefaultEventBinding> bindings = getDefaultEventHandlerBodies();
		for (Iterator iter = bindings.iterator(); iter.hasNext();) {
			DefaultEventBinding bindingInfo = (DefaultEventBinding) iter.next();
			try {
				bindingInfo.writeEventHandler(pc);
			} catch (CoreException e) {
				IStatus status = Logging.newStatus(UIQUserInterfacePlugin.getDefault(), e);
				Logging.showErrorDialog(Messages.getString("ViewWizardManager.ErrorWritingEventHandlerTitle"), Messages.getString("ViewWizardManager.ErrorWritingEventHandlerMessage"), status); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}
	
	private IComponent getAppUiComponent() {
		return getComponentSet().lookupComponent(APPLICATION_UI_COMPONENT_ID);
	}
	
	private String getViewRootName() {
		return (String) getDataStore().get(CONTAINER_BASENAME_KEY);
	}

	private String getViewRootClassName() {
		return CLASS_NAME_PREFIX + getViewRootName();
	}
	
	private void addDesignReferenceToRootModel(IDesignerDataModel rootModel, IDesignerDataModelSpecifier viewDesignSpecifier) {
		Command command = SymbianModelUtils.addViewReference(rootModel, viewDesignSpecifier);
		if (command.canExecute())
			command.execute();
		
		command = UIQModelUtils.ensureInitialView(rootModel);
		if (command != null && command.canExecute()) {
			command.execute();
		}
	}

	// TODO: fix this -- it's intended to prevent two data models from being
    // generated at the same time, but it seems to conflict with the creation
    // of resources by sourcegen.
    static class DataModelConflictingRule implements ISchedulingRule {

        /* (non-Javadoc)
         * @see org.eclipse.core.runtime.jobs.ISchedulingRule#contains(org.eclipse.core.runtime.jobs.ISchedulingRule)
         */
        public boolean contains(ISchedulingRule rule) {
            return rule == this;
        }

        /* (non-Javadoc)
         * @see org.eclipse.core.runtime.jobs.ISchedulingRule#isConflicting(org.eclipse.core.runtime.jobs.ISchedulingRule)
         */
        public boolean isConflicting(ISchedulingRule rule) {
            return rule instanceof DataModelConflictingRule;
        }
        
    }
    
	public void generateModels(boolean minimalRootModel) {
		currentPage.leavingPage(null);
		ensureProjectExists();

		final IProject project = (IProject) getDataStore().get(PROJECT_KEY);
		Check.checkContract(project != null);
		ProjectContextProvider.beginProjectCreation(project);
		final WorkspaceContext wc = WorkspaceContext.getContext();
		final IProjectContext pc = wc.getContextForProject(project);
		Check.checkContract(pc != null);
		ProjectContextProvider.endProjectCreation(project);
		
		ISymbianProjectContext spc = (ISymbianProjectContext) pc.getAdapter(ISymbianProjectContext.class);
		
        if (!extantRootModel) {
    		try {
    		    createRootModelFromWizardData(spc, minimalRootModel);
    		} 
    		catch (Throwable t) {
    			Check.reportFailure(Messages.getString("ViewWizard.RootModelCreateError"), t); //$NON-NLS-1$
    		}
        }
        
		try {
			createViewModelFromWizardData(spc.getCurrentLanguage());
		} 
		catch (Throwable t) {
			Check.reportFailure(Messages.getString("ViewWizard.ViewModelCreateError"), t); //$NON-NLS-1$
		}
	}
	
	/**
	 * Save models and handle the delicate ordering of saving and sourcegen.
	 * <p>
	 * On initial project creation (newRootModel==true), we must write the root and view model
	 * files to disk and refresh the project context without generating source, so that
	 * both models are registered when sourcegen runs.
	 * <p>
	 * When creating a new view, the case is similar; we must register models before
	 * updating source.
	 *   
	 * @param newRootModel
	 * @param srcGenRoot true: generate source for root, false: don't
	 * @param monitor the progress monitor
	 */
	public void saveModels(boolean newRootModel, boolean srcGenRoot, IProgressMonitor monitor) {
		saveModelsOriginal(newRootModel, srcGenRoot, monitor);
		if (true)
			return;
		final IProject project = (IProject) getDataStore().get(PROJECT_KEY);
		Check.checkContract(project != null);
		final WorkspaceContext wc = WorkspaceContext.getContext();
		final IProjectContext pc = wc.getContextForProject(project);
		Check.checkContract(pc != null);
		ISymbianProjectContext spc = (ISymbianProjectContext) pc.getAdapter(ISymbianProjectContext.class);

		ISourceGenProvider sourceGenProvider = pc.getSourceGenProvider();

		// get root
		IDesignerDataModel rootModel = null;
		rootModel = (IDesignerDataModel) getDataStore().get(ROOT_MODEL_KEY);
		
		// turn off resource listening
		IDesignerDataModelEditor rootEditorBase = EditorServices.findEditorForModel(rootModel.getModelSpecifier());
		if (rootEditorBase != null)
			rootEditorBase.setListenToResourceChanges(false);

		IDesignerDataModelSpecifier viewSpecifier = createModelSpecifier(project, spc);

		// save view model without generating source
		IDesignerDataModel viewModel = null;
		//viewModel = (IDesignerDataModel) getDataStore().get(VIEW_MODEL_KEY);
		viewModel = saveViewModel(project, wc, pc, rootModel, viewSpecifier, monitor);

		// get or create specifier for root 
		IDesignerDataModelSpecifier rootModelSpecifier = null;
		if (newRootModel)
			rootModelSpecifier = spc.createNewRootModelSpecifier();
		else
			rootModelSpecifier = rootModel.getModelSpecifier();
		Check.checkState(rootModelSpecifier != null);

		// register view design reference in root model
		addDesignReferenceToRootModel(rootModel, viewSpecifier);

		// save root without generating source
		boolean rootGeneratedSource = WizardUtils.toggleSourceGeneration(rootModel, false);
		rootModel = saveRootModel(project, wc, rootModelSpecifier, monitor);
		WizardUtils.toggleSourceGeneration(rootModel, rootGeneratedSource);
		boolean viewGeneratedSource = WizardUtils.toggleSourceGeneration(rootModel, false);
		viewModel = saveViewModel(project, wc, pc, rootModel, viewSpecifier, monitor);
		WizardUtils.toggleSourceGeneration(rootModel, viewGeneratedSource);
		
		/*if (srcGenRoot) {
			WizardUtils.setupSourceGeneration(sourceGenProvider, rootModel, rootModel.getModelSpecifier());
			saveRootModel(project, wc, rootModelSpecifier, monitor);
		}*/

		// let project context detect the model changes
		pc.refresh(null);
		
		// ensure they're found...
		Check.checkState(pc.findSpecifierForPath(rootModelSpecifier.getPrimaryResourcePath()) != null);
		Check.checkState(pc.findSpecifierForPath(viewSpecifier.getPrimaryResourcePath()) != null);

		// now save again with sourcegen enabled
		
		// save view first, so root can access its RootAppUi resources
		//WizardUtils.setupSourceGeneration(sourceGenProvider, viewModel, viewSpecifier);
		//saveViewModel(project, wc, pc, rootModel, viewSpecifier, monitor);		

		// now save root
		if (srcGenRoot) {
			WizardUtils.setupSourceGeneration(sourceGenProvider, rootModel, rootModel.getModelSpecifier());
			saveRootModel(project, wc, rootModelSpecifier, monitor);
			
			WizardUtils.setupSourceGeneration(sourceGenProvider, viewModel, viewSpecifier);
			saveViewModel(project, wc, pc, rootModel, viewSpecifier, monitor);	
		}
		
		// after all that, add default event handler code
		writeDefaultEventHandlerBodies(pc);

		// re-enable resource listening
		if (rootEditorBase != null)
			rootEditorBase.setListenToResourceChanges(true);

		// don't show editor opening progress, since wizard provides progress
		/*
		EditorServices.setStifleProgress(true);				
		try {
			try {
				if (newRootModel)
					rootModel.getModelSpecifier().openInEditor();
			} catch (CoreException e) {
				Check.reportFailure(Messages.getString("ViewWizard.RootModelOpenError"), e); //$NON-NLS-1$
			}
	
			// reload root editor if open
			if (rootEditorBase != null)
				rootEditorBase.reload();
	
			try {
				viewModel.getModelSpecifier().openInEditor();
			} catch (CoreException e) {
				Check.reportFailure(Messages.getString("ViewWizard.ViewModelOpenError"), e); //$NON-NLS-1$
			}
		} finally {
			EditorServices.setStifleProgress(false);				
		}*/
	}
	
	public void saveModelsOriginal(boolean newRootModel, boolean srcGenRoot, IProgressMonitor monitor) {
		final IProject project = (IProject) getDataStore().get(PROJECT_KEY);
		Check.checkContract(project != null);
		final WorkspaceContext wc = WorkspaceContext.getContext();
		final IProjectContext pc = wc.getContextForProject(project);
		Check.checkContract(pc != null);
		ISymbianProjectContext spc = (ISymbianProjectContext) pc.getAdapter(ISymbianProjectContext.class);

		ISourceGenProvider sourceGenProvider = pc.getSourceGenProvider();

		// get root
		IDesignerDataModel rootModel = null;
		rootModel = (IDesignerDataModel) getDataStore().get(ROOT_MODEL_KEY);
		
		// turn off resource listening
		IDesignerDataModelEditor rootEditor = EditorServices.findEditorForModel(rootModel.getModelSpecifier());
		if (rootEditor != null)
			rootEditor.setListenToResourceChanges(false);

		IDesignerDataModelSpecifier viewSpecifier = createModelSpecifier(project, spc);

		// save view model without generating source
		IDesignerDataModel viewModel = null;
		viewModel = saveViewModel(project, wc, pc, rootModel, viewSpecifier, monitor);

		// get or create specifier for root 
		IDesignerDataModelSpecifier rootModelSpecifier = null;
		if (newRootModel)
			rootModelSpecifier = spc.createNewRootModelSpecifier();
		else
			rootModelSpecifier = rootModel.getModelSpecifier();
		Check.checkState(rootModelSpecifier != null);

		// register view design reference in root model
		addDesignReferenceToRootModel(rootModel, viewSpecifier);

		// save root without generating source
		boolean rootGeneratedSource = WizardUtils.toggleSourceGeneration(rootModel, false);
		rootModel = saveRootModel(project, wc, rootModelSpecifier, monitor);
		WizardUtils.toggleSourceGeneration(rootModel, rootGeneratedSource);

		// let project context detect the model changes
		pc.refresh(null);
		
		// ensure they're found...
		Check.checkState(pc.findSpecifierForPath(rootModelSpecifier.getPrimaryResourcePath()) != null);
		Check.checkState(pc.findSpecifierForPath(viewSpecifier.getPrimaryResourcePath()) != null);

		// now save again with sourcegen enabled
		
		// save view first, so root can access its RootAppUi resources
		WizardUtils.setupSourceGeneration(sourceGenProvider, viewModel, viewSpecifier);
		saveViewModel(project, wc, pc, rootModel, viewSpecifier, monitor);		

		// now save root
		if (srcGenRoot) {
			WizardUtils.setupSourceGeneration(sourceGenProvider, rootModel, rootModel.getModelSpecifier());
			saveRootModel(project, wc, rootModelSpecifier, monitor);
		}
		
		// after all that, add default event handler code
		writeDefaultEventHandlerBodies(pc);

		// re-enable resource listening
		if (rootEditor != null)
			rootEditor.setListenToResourceChanges(true);
		
		// don't show editor opening progress, since wizard provides progress
		
		EditorServices.setStifleProgress(true);				
		try {
			try {
				if (newRootModel)
					rootModel.getModelSpecifier().openInEditor();
			} catch (CoreException e) {
				Check.reportFailure(Messages.getString("ViewWizard.RootModelOpenError"), e); //$NON-NLS-1$
			}
	
			// reload root editor if open
			if (rootEditor != null)
				rootEditor.reload();
	
			try {
				viewModel.getModelSpecifier().openInEditor();
			} catch (CoreException e) {
				Check.reportFailure(Messages.getString("ViewWizard.ViewModelOpenError"), e); //$NON-NLS-1$
			}
		} finally {
			EditorServices.setStifleProgress(false);				
		}
	}

	private IDesignerDataModel saveRootModel(final IProject project, final WorkspaceContext wc, 
			IDesignerDataModelSpecifier rootSpecifier, IProgressMonitor monitor) {
		IDesignerDataModel rootModel;
		
		rootModel = (IDesignerDataModel) getDataStore().get(ROOT_MODEL_KEY);

		try {
			rootModel.saveModelAs(rootSpecifier, monitor);
			wc.projectChanged(project);
	    } 
		catch (Exception e) {
	        Check.reportFailure(Messages.getString("ViewWizard.RootModelSaveError"), e); //$NON-NLS-1$
	    }

		return rootModel;
	}

	/**
	 * Get the name used to name the *.uidesign file.
	 * @return filename base
	 */
	private String getViewModelName() {
		String containerName = (String) getDataStore().get(CONTAINER_BASENAME_KEY);
		Check.checkContract(containerName != null);
		return containerName;
	}
	
	private IDesignerDataModel saveViewModel(final IProject project, final WorkspaceContext wc, final IProjectContext pc,  
			IDesignerDataModel rootModel, IDesignerDataModelSpecifier viewSpecifier, IProgressMonitor monitor) {
		final IDesignerDataModel viewModel = (IDesignerDataModel) getDataStore().get(VIEW_MODEL_KEY);

	    try {
	        pc.refresh(null);
	        monitor.beginTask(Messages.getString("ViewWizard.SavingViewDataModel"), 2 /* view, then root */); //$NON-NLS-1$
			viewModel.saveModelAs(viewSpecifier, new SubProgressMonitor(monitor, 1));
			monitor.worked(1);
			wc.projectChanged(pc.getProject());
		} 
		catch (Exception e) {
			Check.reportFailure(Messages.getString("ViewWizard.ViewModelSaveError"), e); //$NON-NLS-1$
		}
				
		return viewModel;
	}

	/**
	 * @param project
	 * @param spc
	 * @return
	 */
	private IDesignerDataModelSpecifier createModelSpecifier(final IProject project, ISymbianProjectContext spc) {
		String viewFileName = getViewModelName() + MODEL_EXTENSION;
		IFile viewFile = project.getFile(viewFileName);
		final IDesignerDataModelSpecifier viewSpecifier = spc.createNewModelSpecifier(viewFile);
		return viewSpecifier;
	}
	
	private void ensureProjectExists() {
		IProject project = null;
		if (!getDataStore().containsKey(PROJECT_KEY)) {
			String projectName = (String) getDataStore().get(PROJECT_NAME_KEY);
			Check.checkContract(projectName != null);
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IWorkspaceRoot root = workspace.getRoot();
			project = root.getProject(projectName);
			Check.checkContract(project != null);
			getDataStore().put(PROJECT_KEY, project);
		}
		else
			project = (IProject) getDataStore().get(PROJECT_KEY);
		
//		SymbianProjectUtils.addUIDesignerProjectNatureToProject(project);
	}
	
	public void disposePages() {
		for (Iterator<ViewWizardPageBase> iter = pages.iterator(); iter.hasNext();) {
			iter.next().dispose();
		}
	}

	public void disposeModels(boolean all) {
		disposeStoredModel(TEMP_DATA_MODEL_KEY);
		if (all || !extantRootModel) {
			disposeStoredModel(ROOT_MODEL_KEY);
		}
		disposeStoredModel(VIEW_MODEL_KEY);
		FeatureUseTrackerPlugin.getFeatureUseProxy().stopUsingFeature(FeatureUseTrackerConsts.CARBIDE_UI_DESIGNER);
	}
	
	public void disposeStoredModel(String modelKey) {
		IDesignerDataModel dataModel = 
			(IDesignerDataModel) getDataStore().get(modelKey);
		if (dataModel != null) {
			dataModel.dispose();
			getDataStore().remove(modelKey);
		}
	}

	public IDesignerDataModel createEmptyViewModel() throws Exception {
		IDesignerDataModel dataModel = getDataModelProvider().createTemporary();
		dataModel.setComponentSet(getComponentSet());

		return dataModel;
	}
	
	public IComponentSet getComponentSet() {
		return (IComponentSet) getDataStore().get(COMPONENT_SET_KEY);
	}
	
	public boolean isLastPage(IWizardPage page) {
		return page.equals(containerSelectionPage);
	}
	
	public IWizardPage getNextPage(IWizardPage page) {
		if (page.equals(appDefinitionPage))
			return initialContentPage;
		
		if (page.equals(initialContentPage)) {
			if (customizerUI != null)
				return customizerPage;
			else
				return containerSelectionPage;
		}
		
		if (page.equals(customizerPage))
			return containerSelectionPage;
		
		return page.getWizard().getNextPage(page);
	}

	public IWizardPage getPreviousPage(IWizardPage page) {
		if (page.equals(appDefinitionPage))
			return page.getPreviousPage();
		
		if (page.equals(containerSelectionPage)) {
			if (customizerUI != null)
				return customizerPage;
			else
				return initialContentPage;
		}
		
		if (page.equals(customizerPage))
			return initialContentPage;
		
		if (page.equals(initialContentPage))
			return appDefinitionPage;
		
		return page.getWizard().getPreviousPage(page);
	}
	
	public IWizardPage getPage(String pageName) {
		IWizardPage page = wizard.getPage(pageName);
		if ((page == null) && (wizard instanceof TemplateWizard)) {
			WizardSelectionPage wizardSelectionPage = ((TemplateWizard) wizard).getChooseTemplatePage();
			IWizardNode selectedNode = wizardSelectionPage.getSelectedNode();
			IWizard nodeWizard = selectedNode.getWizard();
			page = nodeWizard.getPage(pageName);
		}
		
		return page;
	}

	public void setCustomizerUI(IComponentCustomizerUI customizerUI) {
		this.customizerUI = customizerUI;
		customizerPage.setCustomizerUI(customizerUI);
	}

	public boolean checkProjectWizard(boolean updateDataStore) {
		IWizardPage page = getPage("New Project Page"); //$NON-NLS-1$
		String projectName = null;
		if (page instanceof IWizardDataPage) {
			projectName = (String) ((IWizardDataPage) page).getPageValues().get("projectName"); //$NON-NLS-1$
			if ((projectName != null) && updateDataStore)
				getDataStore().put(PROJECT_NAME_KEY, projectName);
		}
		return projectName != null;
	}

	public void setTemplate(ITemplate template) {
		this.template = template;
	}

	public IWizard getWizard() {
		return wizard;
	}
	
	public void addProjectSelectionListener(IProjectSelectionListener listener) {
		if (projectSelectionListeners == null)
			projectSelectionListeners = new ArrayList<IProjectSelectionListener>();
		
		projectSelectionListeners.add(listener);
	}
	
	void fireProjectSelectionChanged(IProject project) {
		if (projectSelectionListeners == null)
			return;
		
		for (Iterator<IProjectSelectionListener> iter = projectSelectionListeners.iterator(); iter.hasNext();) {
			iter.next().projectSelectionChanged(project);
		}
	}

	public void setExtantRootModel(boolean hasRootModel) {
		extantRootModel = hasRootModel;
	}
	
	public void setApplicationLanguages(Language[] appLanguages) {
		this.appLanguages = appLanguages;
	}

	/**
	 * Find collisions between existing sources and those generated by a new model.
	 * @return list of project-relative paths
	 */
	public List<IPath> getSourceCollisions() {
		WorkspaceContext wc = WorkspaceContext.getContext();
		final IProject project = (IProject) getDataStore().get(PROJECT_KEY);
		final IProjectContext pc = wc.getContextForProject(project);
		Check.checkContract(pc != null);
		final ISymbianProjectContext spc = (ISymbianProjectContext) pc.getAdapter(ISymbianProjectContext.class);
		final IDesignerDataModelSpecifier modelSpecifier = createModelSpecifier(project, spc);

		// create a separate provider so we don't disturb the existing one
		ISourceGenProvider provider;
		try {
			provider = SourceGenUtils.loadSourceGenProvider(SymbianModelUtils.SOURCEGEN_PROVIDER_ID, project);
		} catch (CoreException e) {
			UIPlugin.log(e);
			return Collections.EMPTY_LIST;
		}
			//pc.getSourceGenProvider();
		Check.checkContract(provider != null);

		final List<IPath> collisions = new ArrayList<IPath>();
		
		// check view model (use runnable to get busy notification)
		IDesignerDataModel model = (IDesignerDataModel) getDataStore().get(VIEW_MODEL_KEY);
		final ISourceGenSession session = SymbianModelUtils.createSourceGenSession(provider, model, modelSpecifier);
		UITaskUtils.runImmediately(new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				List<IPath> sources = session.scanForGeneratedSources(monitor);
				filterCollisions(collisions, project, sources);
			}
		});
		session.dispose();
		provider.dispose();
		
		return collisions; 
	}

	/**
	 * Check whether any of the files in sources exists in project.
	 * @param project
	 * @param sources
	 * @return true: collisions found
	 */
	private void filterCollisions(List<IPath> collisions, IProject project, List<IPath> sources) {
		for (Iterator iter = sources.iterator(); iter.hasNext();) {
			IPath path = (IPath) iter.next();
			IResource rsrc = project.findMember(path);
			if (rsrc != null && rsrc.exists())
				collisions.add(path);
		}
	}
}
