/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerConsts;
import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerPlugin;
import com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage;
import com.nokia.carbide.internal.api.templatewizard.ui.TemplateWizard;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.ui.UITaskUtils;
import com.nokia.sdt.component.*;
import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.customizer.IComponentCustomizerCommandFactory;
import com.nokia.sdt.component.customizer.ICustomizeComponentCommand;
import com.nokia.sdt.component.event.IEventDescriptor;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.editor.EditorServices;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.series60.component.Series60ComponentPlugin;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.symbian.S60ComponentAttributes;
import com.nokia.sdt.symbian.dm.*;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.sdt.symbian.workspace.ISymbianProjectContext;
import com.nokia.sdt.symbian.workspace.impl.ProjectContextProvider;
import com.nokia.sdt.workspace.*;

import org.eclipse.cdt.core.model.*;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.emf.common.command.Command;
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
	public static final String HAS_NON_VIEW_SWITCHING_ROOT_MODEL_KEY = "HAS_NON_VIEW_SWITCHING_ROOT_MODEL"; //$NON-NLS-1$
	public static final String WANTS_VIEW_SWITCHING_KEY = "WANTS_VIEW_SWITCHING"; //$NON-NLS-1$
	public static final String CONTAINER_COMPONENT_KEY = "CONTAINER_COMPONENT"; //$NON-NLS-1$
	public static final String TEMP_DATA_MODEL_KEY = "TEMP_DATA_MODEL"; //$NON-NLS-1$
	public static final String VIEW_MODEL_KEY = "VIEW_MODEL"; //$NON-NLS-1$
	public static final String DEFAULT_EVENT_HANDER_BODIES_KEY = "DEFAULT_EVENT_HANDLERS"; //$NON-NLS-1$
	public static final String MMP_FILE_PATH_KEY = "MMP_FILE_PATH"; //$NON-NLS-1$
	
	public static final String MODEL_EXTENSION = ".uidesign"; //$NON-NLS-1$
	// component IDs
	public static final String APPLICATION_COMPONENT_ID = S60ModelUtils.S60_APPLICATION;
	public static final String APPUI_COMPONENT_ID = S60ModelUtils.S60_APPUI;
	public static final String VIEW_APPUI_COMPONENT_ID = S60ModelUtils.S60_VIEW_APPUI;
	public static final String AVKON_VIEW_COMPONENT_ID = S60ModelUtils.S60_AVKON_VIEW;
	public static final String DEFAULT_APPUI_COMPONENT_ID = S60ModelUtils.S60_DEFAULT_APPUI;
	public static final String STATUS_PANE_COMPONENT_ID = S60ModelUtils.S60_STATUSPANE;
	public static final String DOCUMENT_COMPONENT_ID = "com.nokia.sdt.series60.CAknDocument"; //$NON-NLS-1$
	public static final String TEST_COMPONENT_ID = "com.nokia.sdt.series60.WizardTestContainer"; //$NON-NLS-1$
	public static final String STATUS_PANE_TITLE_COMPONENT_ID = "com.nokia.sdt.series60.StatusPaneTitle"; //$NON-NLS-1$
	public static final String STATUS_PANE_CAPTION_COMPONENT_ID = "com.nokia.sdt.series60.StatusPaneCaption"; //$NON-NLS-1$
	public static final String STATUS_PANE_APP_ICON_COMPONENT_ID = "com.nokia.sdt.series60.StatusPaneContextAppIcon"; //$NON-NLS-1$
	public static final String CBA_COMPONENT_ID = "com.nokia.sdt.series60.CBA"; //$NON-NLS-1$
	public static final String CBA_RESTRICTED_COMPONENT_ID = "com.nokia.sdt.series60.CBARestricted"; //$NON-NLS-1$
	public static final String MENUBAR_COMPONENT_ID = "com.nokia.sdt.series60.MenuBar"; //$NON-NLS-1$
	public static final String MENUPANE_COMPONENT_ID = "com.nokia.sdt.series60.MenuPane"; //$NON-NLS-1$
	public static final String MENUITEM_COMPONENT_ID = "com.nokia.sdt.series60.MenuItem"; //$NON-NLS-1$
	public static final String SETTING_ITEM_LIST_COMPONENT_ID = "com.nokia.sdt.series60.CAknSettingItemList"; //$NON-NLS-1$
    // property IDs
	public static final String UID_PROPERTY_NAME = "uid"; //$NON-NLS-1$
	public static final String STATUS_PANE_TITLE_TEXT_PROPERTY_ID = "titleText"; //$NON-NLS-1$
	public static final String CAPTION_PROPERTY_NAME = "caption"; //$NON-NLS-1$
	public static final String STATUS_PANE_CAPTION_SHORT_CAPTION_PROPERTY_NAME = "shortCaption"; //$NON-NLS-1$
	public static final String STATUS_PANE_CAPTION_LONG_CAPTION_PROPERTY_NAME = "longCaption"; //$NON-NLS-1$
	public static final String LONG_CAPTION_PROPERTY_NAME = "shortCaption"; //$NON-NLS-1$
	public static final String CBA_INFO_PROPERTY_NAME = "info"; //$NON-NLS-1$
	public static final String EXITS_APP_PROPERTY_NAME = "exitsApp"; //$NON-NLS-1$
	public static final String INITIAL_FOCUS_PROPERTY_NAME = "initialFocus"; //$NON-NLS-1$
	public static final String LEFT_TEXT_PROPERTY_NAME = "leftText"; //$NON-NLS-1$
	public static final String NAME_PROPERTY_NAME = "name"; //$NON-NLS-1$
	public static final String MENU_ITEM_TEXT_PROPERTY_NAME = "text"; //$NON-NLS-1$
	public static final String DOCUMENT_BASE_PROPERTY_NAME = "documentBase"; //$NON-NLS-1$
	// constants
	private static final String CHANGE_ITEM_HANDLER_NAME = "HandleChangeSelectedSettingItemL";//$NON-NLS-1$
	private static final String SELECTED_EVENT_ID = "selected"; //$NON-NLS-1$

	// template keys
	public static final String TEMPLATE_KEY_APPUI_NAME = "appUiName"; //$NON-NLS-1$
    
	public static final int DEFAULT_HEIGHT = 208;
	public static final int DEFAULT_WIDTH = 176;
	
	// help context ids
	public static final String APPLICATION_DEFINITION_PAGE = "application_properties_page";
	public static final String INITIAL_CONTENT_PAGE = "design_selection_page";
	public static final String CUSTOMIZER_PAGE = "content_variants_page";
	public static final String CONTAINER_SELECTION_PAGE = "container_page";
	
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
		
		// set the language in the string bundle
		IDesignerData data = ((DesignerDataModel) dataModel).getDesignerData();
		Integer languageID = (Integer) getDataStore().get(LANGUAGE_ID_KEY);
		Language language = new Language(languageID.intValue());
		data.getStringBundle().setDefaultLanguage(language);
		symbianProjectContext.setCurrentLanguage(language);
		
		// set properties
		String appName = (String) getDataStore().get(APPNAME_KEY);
		Check.checkContract(appName != null);
		dataModel.setProperty(DesignerDataModel.ROOT_APPLICATION_NAME, appName);
		WizardUtils.addSourceGenPropertiesFromTemplate(dataModel, template, isLegacy);
		
		IComponent applicationComponent = 
			getComponentSet().lookupComponent(APPLICATION_COMPONENT_ID);
        String appInstanceName = appName + "Application"; //$NON-NLS-1$
        EObject application = WizardUtils.addRootInstance(dataModel, applicationComponent, appInstanceName, "C" + appInstanceName); //$NON-NLS-1$
		IPropertySource propertySource = WizardUtils.getProperties(application);
		String appUID = (String) getDataStore().get(APPUID_KEY);
		if (appUID == null)
			appUID = getAppUIDFromTemplate();
		propertySource.setPropertyValue(UID_PROPERTY_NAME, appUID);
		propertySource.setPropertyValue(DOCUMENT_BASE_PROPERTY_NAME, appName);
		
		IComponent documentComponent = getComponentSet().lookupComponent(DOCUMENT_COMPONENT_ID);
        String documentInstanceName = appName + "Document"; //$NON-NLS-1$
        EObject document = WizardUtils.addChildObject(dataModel, documentComponent, application, 
										documentInstanceName, "C" + documentInstanceName, IDesignerDataModel.AT_END); //$NON-NLS-1$
		IComponent appuiComponent = getAppUiComponent();
		String appuiName = appName + "AppUi"; //$NON-NLS-1$
		
		EObject appui = WizardUtils.addChildObject(dataModel, appuiComponent, document, 
										appuiName, "C" + appuiName, IDesignerDataModel.AT_END); //$NON-NLS-1$
//		propertySource = WizardUtils.getProperties(appui);
		if (template != null)
			template.getTemplateValues().put(TEMPLATE_KEY_APPUI_NAME, appuiName);
		dataModel.setProperty(DesignerDataModel.ROOT_CONTAINER_OVERRIDE_ID, appuiName);
		IDisplayModel displayModel = dataModel.getDisplayModelForRootContainer(appui);
		addRootStatusAndCBA(dataModel, appui, appName, null, false);

		WizardUtils.addDefaultBoundsToInstance(appui, displayModel, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		// only add a menubar if not view-switching
		if (!isViewSwitching()) {
			EObject nonLayoutRoot = displayModel.getNonLayoutRoot();
			addOptionsMenu(dataModel, nonLayoutRoot, nonLayoutRoot);
		}

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

		boolean viewSwitching = isViewSwitching();
		IComponent rootComponent = getViewRootComponent(viewSwitching);
		String rootClassName = getViewRootClassName(viewSwitching);
		EObject rootInstance = WizardUtils.addRootInstance(dataModel, rootComponent, getViewRootName(viewSwitching), rootClassName);
		IDisplayModel displayModel = dataModel.getDisplayModelForRootContainer(rootInstance);
		
		IComponent containerComponent = (IComponent) getDataStore().get(CONTAINER_COMPONENT_KEY);
		String containerName = (String) getDataStore().get(CONTAINER_BASENAME_KEY);
		String containerClassName = "C" + TextUtils.titleCase(containerName); //$NON-NLS-1$
		EObject container = 
			WizardUtils.addChildObject(dataModel, containerComponent, rootInstance, 
											containerName, containerClassName, IDesignerDataModel.AT_END);

		boolean restrictedCBA = useRestrictedCBA(container);
		if (isViewSwitching()) {
			addViewStatusAndCBA(dataModel, rootInstance, 
					getViewModelName(), 
					getDefaultCBAValue(rootInstance, container), restrictedCBA);
		} 
		else {
			// for non-view-switching, only add a cba if container wants it in parent
			if (Utilities.hasBooleanAttribute(container, S60ComponentAttributes.USES_CBA_IN_PARENT)) {
				addCBA(dataModel, rootInstance, getDefaultCBAValue(rootInstance, container), restrictedCBA);
			}
		}
		
//		if (!hasViews(project))
//			setExitsApp(container);
		
		WizardUtils.addDefaultBoundsToInstance(rootInstance, displayModel, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		// only add a menubar if view-switching -or- if the container is the non-layout root
		// don't add options menu if the container has a builtin options menu
		boolean hasBuiltInMenu = 
			Utilities.hasBooleanAttribute(container, S60ComponentAttributes.HAS_BUILTIN_OPTIONS_MENU);
		boolean addOptionsMenu = isViewSwitching() && !hasBuiltInMenu; 
		EObject nonLayoutRoot = displayModel.getNonLayoutRoot();
		if (!addOptionsMenu && !hasBuiltInMenu)
			addOptionsMenu = Utilities.getStringAttribute(nonLayoutRoot, 
					S60ComponentAttributes.OPTIONS_MENU_PROPERTY_NAME) != null;

		if (addOptionsMenu) {
			EObject optionsMenu = addOptionsMenu(dataModel, nonLayoutRoot, nonLayoutRoot);
			if (ModelUtils.isInstanceOf(container, SETTING_ITEM_LIST_COMPONENT_ID)) {
				// add default items
				populateSettingItemListOptionsMenu(dataModel, container, optionsMenu);
			}
		}
		
		
		IComponent contentComponent = (IComponent) getDataStore().get(CONTENT_COMPONENT_KEY);
		if ((contentComponent != null) && 
				(!contentComponent.getId().equals(containerComponent.getId()))) {
			EObject contentObject = WizardUtils.addChildObject(dataModel, contentComponent, container, 
																	null, null, IDesignerDataModel.AT_END);
		
			IComponentCustomizerCommandFactory customizerCommandFactory = 
				(IComponentCustomizerCommandFactory) getDataStore().get(CUSTOMIZER_COMMAND_FACTORY_KEY);
			if (customizerCommandFactory != null) {
				ICustomizeComponentCommand command = 
					customizerCommandFactory.createCustomizeComponentCommand(contentObject);
				Check.checkState(command != null);
				if (command.canExecute()) {
					String error = command.execute();
					if (error != null)
						Check.reportFailure(error, new Exception());
				}
			}
			
			// Set initialFocus property if the container has one. This
			// assumes every content component is derived from CCoeControl.
			// If not, an additional check is needed here.
			IPropertySource containerPS = ModelUtils.getPropertySource(container);
			if (ModelUtils.hasProperty(containerPS, INITIAL_FOCUS_PROPERTY_NAME)) {
				IPropertySource contentPS = ModelUtils.getPropertySource(contentObject);
				containerPS.setPropertyValue(INITIAL_FOCUS_PROPERTY_NAME, contentPS.getPropertyValue(NAME_PROPERTY_NAME));
			}
		}
		
		getDataStore().put(VIEW_MODEL_KEY, dataModel);
	}

//	private boolean hasViews(IProject project) {
//		WorkspaceContext wc = WorkspaceContext.getContext();
//		final IProjectContext pc = wc.getContextForProject(project);
//		Check.checkContract(pc != null);
//		IDesignerDataModelSpecifier[] modelSpecifiers = pc.getModelSpecifiers();
//		for (int i = 0; i < modelSpecifiers.length; i++) {
//			IDesignerDataModelSpecifier specifier = modelSpecifiers[i];
//			ISymbianDataModelSpecifier symbianSpecifier = 
//				(ISymbianDataModelSpecifier) specifier.getAdapter(ISymbianDataModelSpecifier.class);
//			if (!symbianSpecifier.isRoot())
//				return true;
//		}
//		
//		return false;
//	}
//
//	private void setExitsApp(EObject container) {
//		IPropertySource properties = Utilities.getPropertySource(container);
//		properties.setPropertyValue(EXITS_APP_PROPERTY_NAME, "true"); //$NON-NLS-1$
//	}

	private String getDefaultCBAValue(EObject rootInstance, EObject containerInstance) {
		// if the container specifies the CBA for the parent, then look for the default in the container
		// else look for the default in the root instance
		EObject instanceWithDefault = 
			Utilities.hasBooleanAttribute(containerInstance, S60ComponentAttributes.USES_CBA_IN_PARENT) ?
					containerInstance : rootInstance;
		
		return 
			Utilities.getStringAttribute(instanceWithDefault, S60ComponentAttributes.DEFAULT_CBA_VALUE);
	}
	
	private void addRootStatusAndCBA(IDesignerDataModel dataModel, EObject parent, String statusTitle, 
																String defaultCBAValue, boolean restrictedCBA) {
		addCBA(dataModel, parent, defaultCBAValue, restrictedCBA);
		IComponent statusPaneComponent = getComponentSet().lookupComponent(STATUS_PANE_COMPONENT_ID);
		EObject statusPane = WizardUtils.addChildObject(dataModel, statusPaneComponent, parent, 
												"statusPane", null, IDesignerDataModel.IN_FRONT); //$NON-NLS-1$
		if (statusPane != null) {
			if (statusTitle != null) {
			    IComponent captionComponent = getComponentSet().lookupComponent(STATUS_PANE_CAPTION_COMPONENT_ID);
		        EObject caption = WizardUtils.addChildObject(dataModel, captionComponent, statusPane, 
		        									"caption", null, IDesignerDataModel.IN_FRONT); //$NON-NLS-1$
				IPropertySource ps = WizardUtils.getProperties(caption);
				if (ps != null) {
					ps.setPropertyValue(STATUS_PANE_CAPTION_LONG_CAPTION_PROPERTY_NAME, statusTitle); //$NON-NLS-1$
					ps.setPropertyValue(STATUS_PANE_CAPTION_SHORT_CAPTION_PROPERTY_NAME, statusTitle); //$NON-NLS-1$
				}
			}

			// add the application icon if it is required
			// (read: not user removable)
		    IComponent appIconComponent = getComponentSet().lookupComponent(STATUS_PANE_APP_ICON_COMPONENT_ID);
		    if (appIconComponent != null) {
		    	IAttributes attrs = (IAttributes) appIconComponent.getAdapter(IAttributes.class);
		    	if (attrs != null && attrs.getBooleanAttribute(CommonAttributes.IS_NOT_USER_REMOVABLE, false)) {
		    		/*EObject appIcon =*/ WizardUtils.addChildObject(dataModel, appIconComponent, statusPane, 
		    				"appIcon", null, IDesignerDataModel.AT_END); //$NON-NLS-1$
		    	}
		    }
		}
	}

	private void addViewStatusAndCBA(IDesignerDataModel dataModel, EObject parent, String statusTitle, 
																String defaultCBAValue, boolean restrictedCBA) {
		addCBA(dataModel, parent, defaultCBAValue, restrictedCBA);
		IComponent statusPaneComponent = getComponentSet().lookupComponent(STATUS_PANE_COMPONENT_ID);
		EObject statusPane = WizardUtils.addChildObject(dataModel, statusPaneComponent, parent, 
												"statusPane", null, IDesignerDataModel.IN_FRONT); //$NON-NLS-1$
		if (statusPane != null && statusTitle != null) {
		    IComponent titleComponent = getComponentSet().lookupComponent(STATUS_PANE_TITLE_COMPONENT_ID);
            EObject title = WizardUtils.addChildObject(dataModel, titleComponent, statusPane, 
            									"title", null, IDesignerDataModel.IN_FRONT); //$NON-NLS-1$
			IPropertySource ps = WizardUtils.getProperties(title);
			if (ps != null) {
				ps.setPropertyValue(STATUS_PANE_TITLE_TEXT_PROPERTY_ID, statusTitle); //$NON-NLS-1$
			}
		}
	}
	
	private void addCBA(IDesignerDataModel dataModel, EObject parent, String defaultValue, boolean restricted) {
		String cbaId = restricted ? CBA_RESTRICTED_COMPONENT_ID : CBA_COMPONENT_ID;
		IComponent cba = getComponentSet().lookupComponent(cbaId);
		EObject cbaInstance = WizardUtils.addChildObject(dataModel, cba, parent, 
												"controlPane", null, IDesignerDataModel.IN_FRONT); //$NON-NLS-1$
		if (defaultValue != null) {
			IPropertySource cbaProperties = Utilities.getPropertySource(cbaInstance);
			cbaProperties.setPropertyValue(CBA_INFO_PROPERTY_NAME, defaultValue);
		}
	}
	
	private boolean useRestrictedCBA(EObject container) {
		return Utilities.hasBooleanAttribute(container, S60ComponentAttributes.USE_RESTRICTED_CBA);
	}
	
	private EObject addOptionsMenu(IDesignerDataModel dataModel, EObject parent, EObject referenceOwner) {
		IComponent menuBarComponent = getComponentSet().lookupComponent(MENUBAR_COMPONENT_ID);
		String optionsMenuName = "optionsMenu"; //$NON-NLS-1$
		EObject optionsMenu = WizardUtils.addChildObject(dataModel, menuBarComponent, parent, 
											optionsMenuName, null, IDesignerDataModel.AT_END);
		IPropertySource propertySource = WizardUtils.getProperties(referenceOwner);
		String optionsMenuPropertyName = Utilities.getStringAttribute(referenceOwner,
				S60ComponentAttributes.OPTIONS_MENU_PROPERTY_NAME);
		propertySource.setPropertyValue(optionsMenuPropertyName, optionsMenuName);
		return optionsMenu;
	}

	/**
	 * Add the "Change" item to a setting item list's view's Options menu.  
	 * The setting item has a ready-made routine, ChangeSelectedItemL(), for this.
	 * It can leave, which is stupid, but oh well.
	 */
	private void populateSettingItemListOptionsMenu(IDesignerDataModel dataModel, EObject settingsList, EObject optionsMenu) {
		IComponent menuPaneComponent = getComponentSet().lookupComponent(MENUPANE_COMPONENT_ID);
		EObject optionsPane = WizardUtils.addChildObject(dataModel, menuPaneComponent, optionsMenu, 
				null, null, IDesignerDataModel.AT_END);
		IComponent menuItemComponent = getComponentSet().lookupComponent(MENUITEM_COMPONENT_ID);
		EObject changeItem = WizardUtils.addChildObject(dataModel, menuItemComponent, optionsPane, 
				null, null, IDesignerDataModel.AT_END);
		IPropertySource propertySource = WizardUtils.getProperties(changeItem);
		propertySource.setPropertyValue(MENU_ITEM_TEXT_PROPERTY_NAME, "Change"); //$NON-NLS-1$
		
		// wire up the event handler and set its body for later insertion
		DefaultEventBinding binding = new DefaultEventBinding(
				dataModel, ModelUtils.getComponentInstance(changeItem),
				SELECTED_EVENT_ID, CHANGE_ITEM_HANDLER_NAME,
				// This makes assumptions abouts the way instance member names are constructed
				"i" + TextUtils.titleCase(ModelUtils.getComponentInstance(settingsList).getName()) 
				+ "->ChangeSelectedItemL();"); //$NON-NLS-1$
		binding.createEventBinding();
		
		getDefaultEventHandlerBodies().add(binding);
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
				IStatus status = Logging.newStatus(Series60ComponentPlugin.getDefault(), e);
				Logging.showErrorDialog(Messages.getString("ViewWizardManager.ErrorWritingEventHandlerTitle"), Messages.getString("ViewWizardManager.ErrorWritingEventHandlerMessage"), status); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

	private boolean isViewSwitching() {
		Boolean value = (Boolean) getDataStore().get(WANTS_VIEW_SWITCHING_KEY);
		return (value != null) && value.booleanValue();
	}
	
	private IComponent getAppUiComponent() {
		return isViewSwitching() ?
			getComponentSet().lookupComponent(VIEW_APPUI_COMPONENT_ID) :
			getComponentSet().lookupComponent(APPUI_COMPONENT_ID);
	}

	private IComponent getViewRootComponent(boolean viewSwitching) {
		return viewSwitching ?
			getComponentSet().lookupComponent(AVKON_VIEW_COMPONENT_ID) :
			getComponentSet().lookupComponent(DEFAULT_APPUI_COMPONENT_ID);
	}
	
	private String getViewRootName(boolean viewSwitching) {
		if (viewSwitching) {
			return S60ModelUtils.getAvkonViewName((String) getDataStore().get(CONTAINER_BASENAME_KEY));
		}
		else {
			IDesignerDataModel rootModel = (IDesignerDataModel) getDataStore().get(ROOT_MODEL_KEY);
			EObject rootContainer = rootModel.getRootContainers()[0];
			return WizardUtils.getComponentInstance(rootContainer).getName();
		}
	}

	private String getViewRootClassName(boolean viewSwitching) {
		if (viewSwitching) {
			return "C" + getViewRootName(viewSwitching); //$NON-NLS-1$
		}
		else {
			IDesignerDataModel rootModel = (IDesignerDataModel) getDataStore().get(ROOT_MODEL_KEY);
			EObject rootContainer = rootModel.getRootContainers()[0];
			return (String) WizardUtils.getProperties(rootContainer).getPropertyValue(
																	WizardUtils.CLASS_NAME_PROPERTY_ID);
		}
	}
	
	private void addDesignReferenceToRootModel(IDesignerDataModel rootModel, IDesignerDataModelSpecifier viewDesignSpecifier) {
		Command command = SymbianModelUtils.addViewReference(rootModel, viewDesignSpecifier);
		if (command.canExecute())
			command.execute();
		
		command = S60ModelUtils.ensureInitialView(rootModel);
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
