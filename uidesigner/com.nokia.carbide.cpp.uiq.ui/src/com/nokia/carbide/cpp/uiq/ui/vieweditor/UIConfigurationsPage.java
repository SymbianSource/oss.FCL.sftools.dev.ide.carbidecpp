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
/* START_USECASES: CU5 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.vieweditor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IComponentInstanceChildListener;
import com.nokia.sdt.datamodel.adapter.IComponentInstancePropertyListener;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NamePropertySupport;
import com.nokia.sdt.datamodel.util.SetPropertyCommand;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.editor.IDesignerDataModelEditorPage;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.symbian.dm.*;
import com.nokia.sdt.symbian.ui.appeditor.FormUtilities;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * This class creates the page UI Configurations of the View Editor.
 * It manages all the other dialogs launched from this page.
 *
 */
public class UIConfigurationsPage extends FormPage implements IDesignerDataModelEditorPage {

	public static final String UICONFIGS_PAGE_ID = "uiConfigs"; //$NON-NLS-1$
	// private static final String HELP_CONTEXT_ID = UIPlugin.PLUGIN_ID + "." +
	// "appEditorLanguagesPageContext"; //$NON-NLS-1$

	private IDesignerDataModelEditor editor;
	private ILocalizedStringBundle bundle;
	private IDesignerData.IModelPropertyListener modelPropertyListener;
	private ILocalizedStringBundle.IListener bundleListener;
	private EObject viewConfigurationsGroup;
	private EObject layoutConfigurationGroup;
	private EObject commandListConfigurationGroup;
	// GUI
	private SectionPart layoutsSectionPart;
	private Table layoutsTable;
	private Button removeBtnLayout;
	private Button renameBtnLayout;
	private Button duplicateBtnLayout;
	private Button addBtnLayout;
	private Table uiConfigsTable;
	private Vector<CCombo[]> uiConfigsTableCombos;
	private Button removeBtnUIConfig;
	private Button duplicateBtnUIConfig;
	private Button addBtnUIConfig;
	// Model Ids for Views
	private static final String VIEW_LAYOUTS_GROUP = UIQModelUtils.UIQ_VIEW_LAYOUTS_GROUP;
	private static final String VIEW_LAYOUT = UIQModelUtils.UIQ_VIEW_LAYOUT;
	private  final String VIEW_CONFIGURATIONS_GROUP = UIQModelUtils.UIQ_VIEW_CONFIGURATIONS_GROUP;
	private static final String VIEW_CONFIGURATION = UIQModelUtils.UIQ_VIEW_CONFIGURATION;
	public static final String VIEW_PAGE = UIQModelUtils.UIQ_VIEW_PAGE;
	// Model Ids for SimpleDialogs
	private static final String SIMPLEDIALOG_CONTAINERS_GROUP = UIQModelUtils.UIQ_SIMPLEDIALOG_CONTAINERS_GROUP;
	private static final String UIQ_CQIKCONTAINER = UIQModelUtils.UIQ_CQIKCONTAINER;
	private static final String SIMPLEDIALOG_CONFIGURATIONS_GROUP = UIQModelUtils.UIQ_SIMPLEDIALOG_CONFIGURATIONS_GROUP;
	private static final String SIMPLEDIALOG_CONFIGURATION = UIQModelUtils.UIQ_SIMPLEDIALOG_CONFIGURATION;
	// Generic IDs
	private String idDesign;
	private final String UIQ_CQIKVIEW= UIQModelUtils.UIQ_CQIKVIEW;
	private final String UI_COMMAND_LISTS_GROUP = UIQModelUtils.UIQ_COMMAND_LISTS_GROUP; 
	private final String UI_COMMAND_LIST = UIQModelUtils.UIQ_COMMAND_LIST;
	private final String UI_LAYOUTS_GROUP;
	private final String UI_LAYOUT;
	private final String UI_CONFIGURATIONS_GROUP;
	private final String UI_CONFIGURATION;
	//Model
	private IComponentInstancePropertyListener uiConfigPropertyListener;
	private IComponentInstancePropertyListener layoutPropertyListener;
	private IComponentInstancePropertyListener commandListPropertyListener;
	private IComponentInstanceChildListener uiConfigurationsGroupChildListener;
	private IComponentInstanceChildListener layoutsGroupChildListener;
	private IComponentInstanceChildListener commandListGroupChildListener;
	private UIConfigurationPageUtils uiConfigurationPageUtils;
	//Properties IDs
	public static final String UICONFIGURATION_PROPERTY_UICONFIG = "uiconfig"; 
	public static final String UICONFIGURATION_PROPERTY_COMMANDLIST = "commandList"; 
	public static final String UICONFIGURATION_PROPERTY_VIEWORCONTAINER = "viewOrContainer"; 
	public static final String UICONFIGURATION_PROPERTY_NAME = "name";
	
	public static final String VIEWLAYOUT_PROPERTY_NAME = "name";
	public static final String VIEWPAGE_PROPERTY_TAB_IMAGE = "tabImage";
	public static final String VIEWPAGE_PROPERTY_TAB_CAPTION = "tabCaption";
	
	public static final String CQIKCONTAINER_PROPERTY_SCROLLABLE = "scrollable";
	public static final String CQIKCONTAINER_PROPERTY_LAYOUTMANAGER = "layoutManager";
	public static final String LAYOUTMANAGER_PROPERTY_TYPE = "type";
	
	public static final int COLUMN_NAME_LAYOUT = 0;
	public static final int COLUMN_NAME_UI_CONFIG = 1;
	public static final int COLUMN_TYPE_UI_CONFIG = 0;
	/**
	 * Class constructor. Initializes all the instance variables.
	 * @param editor
	 */
	public UIConfigurationsPage(IDesignerDataModelEditor editor) {
		super(editor.getFormEditor(), UICONFIGS_PAGE_ID, Messages
				.getString("UIConfigurationsPage.TabTitle")); //$NON-NLS-1$
		this.editor = editor;
		
		uiConfigurationPageUtils = new UIConfigurationPageUtils(editor);//create model functions
		uiConfigurationPageUtils.initLabels();
		uiConfigurationPageUtils.initLabelsUIConfig();
		DesignerDataModel model = (DesignerDataModel) editor.getDataModel();
		DesignerDataModel modelImpl = (DesignerDataModel) model;
		IDesignerData designerData = modelImpl.getDesignerData();
		IComponentInstance instance[] = model.getRootComponentInstances();
		idDesign=instance[0].getComponentId();
						
		if (designerData != null) {
			bundle = designerData.getStringBundle();
		}
		Check.checkArg(bundle != null);
		
		if (idDesign.compareTo(UIQ_CQIKVIEW) == 0) {
			UI_CONFIGURATIONS_GROUP = VIEW_CONFIGURATIONS_GROUP;
			UI_CONFIGURATION = VIEW_CONFIGURATION;
			UI_LAYOUTS_GROUP = VIEW_LAYOUTS_GROUP;
			UI_LAYOUT = VIEW_LAYOUT;
		} else {
			UI_CONFIGURATIONS_GROUP = SIMPLEDIALOG_CONFIGURATIONS_GROUP;
			UI_CONFIGURATION = SIMPLEDIALOG_CONFIGURATION;
			UI_LAYOUTS_GROUP = SIMPLEDIALOG_CONTAINERS_GROUP;
			UI_LAYOUT = UIQ_CQIKCONTAINER;
		}
		
		viewConfigurationsGroup = UIConfigurationPageUtils.getObjectById(editor.getDataModel(),
				UI_CONFIGURATIONS_GROUP);
		IComponentInstance uiConfigurationsGroup = ModelUtils.getComponentInstance(viewConfigurationsGroup);
		uiConfigurationsGroupChildListener = new IComponentInstanceChildListener() {
			public void childAdded(EObject parent, EObject child) {
				//System.out.println("CHILD_ADDED: " + child);
				ModelUtils.getComponentInstance(child).addPropertyListener(uiConfigPropertyListener);
				addUIConfigurationToGUI(child);
				calculateRemoveUIConfigsEnablement();
			}

			public void childRemoved(EObject parent, EObject child) {
				//System.out.println("CHILD_REMOVED: " + child);
				resetUIConfigurationFromGUI();
				calculateRemoveUIConfigsEnablement();
			}
			
			public void childrenReordered(EObject parent) {
				resetUIConfigurationFromGUI();
			}
		};
		uiConfigurationsGroup.addChildListener(uiConfigurationsGroupChildListener);
		
		//add property listener
		uiConfigPropertyListener = new IComponentInstancePropertyListener() {
        public void propertyChanged(EObject componentInstance, Object propertyId) {
                //System.out.println("Refresh changes: " + (String)propertyId );
        	    refreshChangesGUI(componentInstance,propertyId);
		        }
		};
		
		//add layout listener
		layoutConfigurationGroup = UIConfigurationPageUtils.getObjectById(editor.getDataModel(),
				UI_LAYOUTS_GROUP);
		IComponentInstance layoutGroup = ModelUtils.getComponentInstance(layoutConfigurationGroup);
		layoutsGroupChildListener = new IComponentInstanceChildListener() {
			public void childAdded(EObject parent, EObject child) {
				//System.out.println("CHILD_ADDED LAYOUT: " + child);
				ModelUtils.getComponentInstance(child).addPropertyListener(layoutPropertyListener);
				if (layoutsTable != null){
					addItemsLayoutListToGUI(uiConfigurationPageUtils.getPropertyValueByInstance(child
							,VIEWLAYOUT_PROPERTY_NAME));
					resetCombos(0);
				}
			}

			public void childRemoved(EObject parent, EObject child) {
				//System.out.println("CHILD_REMOVED LAYOUT: " + child);
				if (layoutsTable != null) {
					refreshCombos(child, 0, "remove");
					removeLayoutFromGUI(child);
				}
			}
			
			public void childrenReordered(EObject parent) {
				if (layoutsTable != null){
					refreshChangesLayoutGUI(VIEWLAYOUT_PROPERTY_NAME);//for reset layouts table
					resetCombos(0);
				}
			}
		};
		layoutGroup.addChildListener(layoutsGroupChildListener);
		
		layoutPropertyListener = new IComponentInstancePropertyListener() {
	        public void propertyChanged(EObject componentInstance, Object propertyId) {
	                //System.out.println("Refresh changes: " + (String)propertyId );
	                if (layoutsTable != null){
	                	refreshChangesLayoutGUI(propertyId);
	                	resetUIConfigurationFromGUI();
	                }
			       }
			};
		
		//add commandList listener
		commandListConfigurationGroup = UIConfigurationPageUtils.getObjectById(editor.getDataModel()
				,UI_COMMAND_LISTS_GROUP);
		IComponentInstance commandListGroup = ModelUtils.getComponentInstance(commandListConfigurationGroup);
		commandListGroupChildListener = new IComponentInstanceChildListener() {
			public void childAdded(EObject parent, EObject child) {
				//System.out.println("CHILD_ADDED COMMAND: " + child);
				if (layoutsTable != null) {
					ModelUtils.getComponentInstance(child).addPropertyListener(
							commandListPropertyListener);
					resetUIConfigurationFromGUI();
				}
			}

			public void childRemoved(EObject parent, EObject child) {
				//System.out.println("CHILD_REMOVED COMMAND: " + child);
				if(layoutsTable != null)
					refreshCombos(child,1,"remove");
			}
			
			public void childrenReordered(EObject parent) {
				if(layoutsTable != null)
					resetCombos(1);
			}
		};
		commandListGroup.addChildListener(commandListGroupChildListener);
		commandListPropertyListener = new IComponentInstancePropertyListener() {
	        public void propertyChanged(EObject componentInstance, Object propertyId) {
	                //System.out.println("Refresh changes: " + (String)propertyId );
	                if(layoutsTable != null)	
	                	resetCombos(1);	        	    
			        }
			};
		
		uiConfigsTableCombos = new Vector<CCombo[]>();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.editor.IDesignerDataModelEditorPage#reload()
	 */
	public void reload() {
		refresh();
	}
	
	/**
	 * Restores the page when the model is reverted
	 */
	private void refresh() {		
		if (layoutsTable == null) {
			return;
		}
		// restore UI Configs GUI
		TableItem[] items = uiConfigsTable.getItems();
		int i = 0;
		for(TableItem itemDelete: items){
			uiConfigsTableCombos.get(i)[0].dispose();
			uiConfigsTableCombos.get(i)[1].dispose();
			i++;
			itemDelete.dispose();
		}
		uiConfigsTableCombos.removeAllElements();
		setUIConfigurationsSection();
		calculateRemoveUIConfigsEnablement();
		
		// restore layouts GUI
		layoutsTable.removeAll();
		setLayoutsSection();
		calculateRemoveLayoutsEnablement();
		
		// restore references to model
		viewConfigurationsGroup = UIConfigurationPageUtils.getObjectById(editor.getDataModel(), UI_CONFIGURATIONS_GROUP);
		layoutConfigurationGroup = UIConfigurationPageUtils.getObjectById(editor.getDataModel(), UI_LAYOUTS_GROUP);
		commandListConfigurationGroup = UIConfigurationPageUtils.getObjectById(editor.getDataModel(), UI_COMMAND_LISTS_GROUP);
		
		// restore listeners		
		IComponentInstance uiConfigurationsGroup = ModelUtils.getComponentInstance(viewConfigurationsGroup);
		uiConfigurationsGroup.addChildListener(uiConfigurationsGroupChildListener);
		
		IComponentInstance layoutGroup = ModelUtils.getComponentInstance(layoutConfigurationGroup);
		layoutGroup.addChildListener(layoutsGroupChildListener);
		
		IComponentInstance commandListGroup = ModelUtils.getComponentInstance(commandListConfigurationGroup);
		commandListGroup.addChildListener(commandListGroupChildListener);
	}

	@Override
	/**
	 * Dispose DataModel
	 */
	public void dispose() {
		DesignerDataModel dm = (DesignerDataModel) editor.getDataModel();
		dm.getDesignerData().removeModelPropertyListener(modelPropertyListener);
		bundle.removeListener(bundleListener);
		super.dispose();
	}
	
	@Override
	/**
	 * 
	 */
	public boolean selectReveal(Object object) {
		boolean result = false;
		if (object == layoutsTable) {
			result = true;
			layoutsTable.setFocus();
		} else if (object == uiConfigsTable) {
			result = true;
			uiConfigsTable.setFocus();
		} else if (object instanceof String) {
			String key = (String)object;
			if (!key.startsWith("Combo-"))
				return false;
			else
				result = true;
			for (int i=0; i<uiConfigsTableCombos.size(); i++) {
				if (uiConfigsTableCombos.get(i)[0].getData("key").equals(key)) {
					uiConfigsTableCombos.get(i)[0].setFocus();
					break;
				} else if (uiConfigsTableCombos.get(i)[1].getData("key").equals(key)) {
					uiConfigsTableCombos.get(i)[1].setFocus();
					break;
				}
			}
		}
		return result;
	}

	@Override
	/**
	 * 
	 */
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		// WorkbenchUtils.setHelpContextId(getPartControl(), HELP_CONTEXT_ID);
	}
	
	/**
	 * Adds or deletes the new instances to/from the combos of the UI configurations table.
	 * @param instanceLayout
	 * @param combo - 0 for combo layout and 1 for combo commandList
	 * @param type - "add" or "remove"  
	 */
	private void refreshCombos(EObject instanceLayout,int combo,String type){
		if (type.equals("add")) {
			String value = uiConfigurationPageUtils.getPropertyValueByInstance(instanceLayout, VIEWLAYOUT_PROPERTY_NAME);
			for (int i = 0; i < uiConfigsTable.getItemCount(); i++) {
				uiConfigsTableCombos.get(i)[combo].add(value);
			}
		} else {
			resetCombos(combo);
		}
	}
	
	/**
	 * Reset the combo's values
	 * @param combo - 0 for layout and 1 for command list.
	 */
	private void resetCombos(int combo){
		Vector<String> currentItems;
		String valueSelected = "";
		int index = -1; //if it doesn't found the element
		if(combo == 0){ //0 layout
			currentItems = uiConfigurationPageUtils.getCurrentObjectsFromModel(UI_LAYOUT);
		}
		else{// 1 command list
			currentItems = uiConfigurationPageUtils.getCurrentObjectsFromModel(UI_COMMAND_LIST);
		}
		for(int i = 0; i < uiConfigsTable.getItemCount(); i++){
			int indexBefore = uiConfigsTableCombos.get(i)[combo].getSelectionIndex();
			if (indexBefore != -1)
				valueSelected = uiConfigsTableCombos.get(i)[combo].getItem(indexBefore);
						
			uiConfigsTableCombos.get(i)[combo].removeAll();
			for (String nameItem : currentItems) {
				uiConfigsTableCombos.get(i)[combo].add(nameItem);
			}
			index = uiConfigsTableCombos.get(i)[combo].indexOf(valueSelected);
			if (index != -1){
				uiConfigsTableCombos.get(i)[combo].select(index);
			}
		}
	}	
	
	/**
	 * Refresh the changes to the model on the Layout GUI. If the property changed is
	 * "name" then all the Layout GUI is reset.
	 * @param propertyId - Property changed
	 */
	private void refreshChangesLayoutGUI(Object propertyId){
		String property;
		property = (String)propertyId;
		if(property.equals(VIEWLAYOUT_PROPERTY_NAME)){
			layoutsTable.removeAll();
			setLayoutsSection();			
		}
	}
	
	/**
	 * Refresh the changes to the model on the UI configuration GUI. If the property changed is
	 * "name" then all the configuration GUI is reset.
	 * @param componentInstance
	 * @param propertyId
	 */
	private void refreshChangesGUI(EObject componentInstance,Object propertyId){
		if (uiConfigsTable != null) {
			IPropertySource uiConfigurationProperties; 
			String property,name,propertyValue;
			TableItem[] items = uiConfigsTable.getItems();
			property = (String)propertyId;
			int i = 0;
			uiConfigurationProperties = ModelUtils.getPropertySource(componentInstance);
			propertyValue = (String) uiConfigurationProperties.getPropertyValue(propertyId);
			name = (String) uiConfigurationProperties.getPropertyValue(UICONFIGURATION_PROPERTY_NAME);
			
			if (propertyId.equals(UICONFIGURATION_PROPERTY_NAME)){
				for(TableItem itemDelete: items){
					uiConfigsTableCombos.get(i)[0].dispose();
					uiConfigsTableCombos.get(i)[1].dispose();
					i++;
					itemDelete.dispose();
				}
				uiConfigsTableCombos.removeAllElements();
				setUIConfigurationsSection();
			}
			else{
				for(TableItem item: items){
					if(item.getText(1).compareTo(name) == 0){
						if (property.compareTo(UICONFIGURATION_PROPERTY_UICONFIG) == 0){
							item.setText(0,uiConfigurationPageUtils.getLabelUIConfig(propertyValue));
						}
						else if (property.compareTo(UICONFIGURATION_PROPERTY_VIEWORCONTAINER) == 0){
							uiConfigsTableCombos.get(i)[0].select(uiConfigsTableCombos.get(i)[0].indexOf(propertyValue));
						}
						else if (property.compareTo(UICONFIGURATION_PROPERTY_COMMANDLIST) == 0){ //get(row)[combo] //0 layout combo 1 commanlist combo						
							uiConfigsTableCombos.get(i)[1].select(uiConfigsTableCombos.get(i)[1].indexOf(propertyValue));
						} 
					}
					i++;
				}
			}
		}
	}
	
	/**
	 * Refresh the changes on the GUI to the model.
	 * @param uiConfigurationProperties
	 * @param nameCombo
	 */
	public void refreshChangesModel(IPropertySource uiConfigurationProperties,String nameCombo){
		String name,propertyValue;
		int i = 0;
		name = (String) uiConfigurationProperties.getPropertyValue(UICONFIGURATION_PROPERTY_NAME);
		TableItem[] items = uiConfigsTable.getItems();
		for(TableItem item: items){
			if(item.getText(1).compareTo(name) == 0 && nameCombo.equals("layout")){
				propertyValue = uiConfigsTableCombos.get(i)[0].getItem(uiConfigsTableCombos.get(i)[0].getSelectionIndex());
				createAndExecuteSetPropertyCommand( uiConfigsTableCombos.get(i)[0].getData("key"), uiConfigurationProperties, UICONFIGURATION_PROPERTY_VIEWORCONTAINER, 
						propertyValue);
			} else if(item.getText(1).compareTo(name)==0 & nameCombo.equals("command")){
				if (uiConfigsTableCombos.get(i)[1].getSelectionIndex() == 0){
					propertyValue = "";
				}
				else{
					propertyValue = uiConfigsTableCombos.get(i)[1].getItem(uiConfigsTableCombos.get(i)[1].getSelectionIndex());
				}
				createAndExecuteSetPropertyCommand(uiConfigsTableCombos.get(i)[1].getData("key"), uiConfigurationProperties, UICONFIGURATION_PROPERTY_COMMANDLIST, 
						propertyValue);
			}
			i++;
		}		
	}
	
	/**
	 * Creates the content of the form.
	 */
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		final ScrolledForm form = managedForm.getForm();
		form.setText(Messages.getString("UIConfigurationsPage.Title")); //$NON-NLS-1$
		Composite body = form.getBody();
		body.setLayout(new TableWrapLayout());
		toolkit.paintBordersFor(body);
		String href =
		 "/com.nokia.carbide.cpp.uiq.help/html/tasks/ui_configuration/task_uiconfig.htm";
		 //$NON-NLS-1$
		 FormUtilities.addHelpToolbarItem(form.getForm(), href,
				 Messages.getString("UIConfigurationsPage.HelpLink")); //$NON-NLS-1$
		createLayoutsSection(managedForm, toolkit, body);
		createUIConfigurationsSection(toolkit, body);
	}

	/**
	 * Creates the Layout Section of the page.
	 * @param managedForm
	 * @param toolkit
	 * @param body
	 */
	private void createLayoutsSection(IManagedForm managedForm,
			FormToolkit toolkit, Composite body) {
		Section layoutsSection = toolkit.createSection(body,
				Section.DESCRIPTION | Section.TITLE_BAR);
		layoutsSectionPart = new SectionPart(layoutsSection);
		managedForm.addPart(layoutsSectionPart);

		final TableWrapData layoutsSectionTableWrapData = new TableWrapData(
				TableWrapData.LEFT, TableWrapData.TOP);
		layoutsSectionTableWrapData.heightHint = 294;
		layoutsSectionTableWrapData.grabHorizontal = true;
		layoutsSection.setLayoutData(layoutsSectionTableWrapData);
		layoutsSection.setText(Messages
				.getString("UIConfigurationsPage.LayoutsSection.Title")); //$NON-NLS-1$
		layoutsSection.setDescription(Messages
				.getString("UIConfigurationsPage.LayoutsSection.Description")); //$NON-NLS-1$

		final Composite layoutsComposite = toolkit.createComposite(
				layoutsSection, SWT.NONE);
		toolkit.adapt(layoutsComposite);
		layoutsComposite.setLayout(new FormLayout());
		layoutsSection.setClient(layoutsComposite);
		toolkit.paintBordersFor(layoutsComposite);
		// layout configuration table ini.. it contents the list
		layoutsTable = toolkit.createTable(layoutsComposite, SWT.BORDER
				| SWT.V_SCROLL | SWT.H_SCROLL);
		final FormData layoutsTableFormData = new FormData();
		layoutsTableFormData.bottom = new FormAttachment(100, -9);
		layoutsTableFormData.left = new FormAttachment(0, 7);
		layoutsTableFormData.right = new FormAttachment(0, 430);
		layoutsTableFormData.top = new FormAttachment(0, 6);
		layoutsTable.setLayoutData(layoutsTableFormData);
		//listener for selection, to enable the button rename, duplicate and remove
		layoutsTable.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
		    	  duplicateBtnLayout.setEnabled(true);
		    	  renameBtnLayout.setEnabled(true);
		      } 
		    });
				
		// layout configuration table end..
		layoutsTable.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {// listener events keyboard
				if (e.character == SWT.DEL && removeBtnLayout.isEnabled()) {
					removeLayout();
				}
			}
		});

		final Composite btnsLayoutComposite = toolkit.createComposite(
				layoutsComposite, SWT.NONE);

		toolkit.adapt(btnsLayoutComposite);
		final RowLayout btnsRowLayout = new RowLayout(SWT.VERTICAL);
		btnsRowLayout.justify = true;
		btnsRowLayout.fill = true;
		btnsLayoutComposite.setLayout(btnsRowLayout);
		final FormData frmDataBtnsLayout = new FormData();
		frmDataBtnsLayout.bottom = new FormAttachment(0, 150);// height of the rowlayout
		frmDataBtnsLayout.top = new FormAttachment(0, 6);
		frmDataBtnsLayout.right = new FormAttachment(0, 520);
		frmDataBtnsLayout.left = new FormAttachment(layoutsTable, 5,
				SWT.DEFAULT);
		btnsLayoutComposite.setLayoutData(frmDataBtnsLayout);
		toolkit.paintBordersFor(btnsLayoutComposite);
		
		addBtnLayout = toolkit.createButton(btnsLayoutComposite, Messages
				.getString("UIConfigurationsPage.AddLayoutButton"), SWT.NONE); //$NON-NLS-1$
		addBtnLayout.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				addLayoutDialog();
			}
		}); // Button add

		renameBtnLayout = toolkit
				.createButton(
						btnsLayoutComposite,
						Messages
								.getString("UIConfigurationsPage.RenameLayoutButton"), SWT.NONE); //$NON-NLS-1$
		renameBtnLayout.setEnabled(false);
		renameBtnLayout.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				renameLayout();
			}
		});// Button rename

		duplicateBtnLayout = toolkit
				.createButton(
						btnsLayoutComposite,
						Messages
								.getString("UIConfigurationsPage.DuplicateLayoutButton"), SWT.NONE); //$NON-NLS-1$
		duplicateBtnLayout.setEnabled(false);
		duplicateBtnLayout.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				duplicateLayout();
			}
		});// Duplicate button

		removeBtnLayout = toolkit
				.createButton(
						btnsLayoutComposite,
						Messages
								.getString("UIConfigurationsPage.RemoveLayoutButton"), SWT.NONE); //$NON-NLS-1$
		removeBtnLayout.setEnabled(false);
		removeBtnLayout.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				removeLayout();
			}
		});// Remove button
		setLayoutsSection();
	}

	/**
	 * Creates the UI configuration section of the page.
	 * @param toolkit
	 * @param body
	 */
	private void createUIConfigurationsSection(FormToolkit toolkit,
			Composite body) {
		Section uiConfigurationsSection = toolkit.createSection(body,
				Section.DESCRIPTION | Section.TITLE_BAR);
		uiConfigurationsSection
				.setText(Messages
						.getString("UIConfigurationsPage.UIConfigurationsSection.Title")); //$NON-NLS-1 //$NON-NLS-1$
		uiConfigurationsSection
				.setDescription(Messages
						.getString("UIConfigurationsPage.UIConfigurationsSection.Description")); //$NON-NLS-1$		

		final TableWrapData uiConfigurationsTableWrapData = new TableWrapData(
				TableWrapData.FILL, TableWrapData.TOP);
		uiConfigurationsTableWrapData.heightHint = 294;
		uiConfigurationsTableWrapData.grabHorizontal = true;
		uiConfigurationsSection.setLayoutData(uiConfigurationsTableWrapData);

		final Composite uiConfigurationsComposite = toolkit.createComposite(
				uiConfigurationsSection, SWT.NONE);
		toolkit.adapt(uiConfigurationsComposite);
		uiConfigurationsComposite.setLayout(new FormLayout());
		uiConfigurationsSection.setClient(uiConfigurationsComposite);
		toolkit.paintBordersFor(uiConfigurationsComposite);
		// layout configuration table ini.. it contents the list
		uiConfigsTable = toolkit.createTable(uiConfigurationsComposite,
				SWT.BORDER);
		uiConfigsTable.setLinesVisible(true);
		uiConfigsTable.setHeaderVisible(true);
		final FormData uiConfigsTableFormData = new FormData();
		uiConfigsTableFormData.bottom = new FormAttachment(100, -9);
		uiConfigsTableFormData.left = new FormAttachment(0, 7);
		uiConfigsTableFormData.right = new FormAttachment(0, 430);
		uiConfigsTableFormData.top = new FormAttachment(0, 6);
		uiConfigsTable.setLayoutData(uiConfigsTableFormData);
		uiConfigsTable.setSize(uiConfigsTable.computeSize(SWT.DEFAULT, 200));
		//listener for selection, to enable the button rename, duplicate and remove
		uiConfigsTable.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
		    	  duplicateBtnUIConfig.setEnabled(true);
		      }
		    });
		uiConfigsTable.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {// listener events keyboard
				if (e.character == SWT.DEL && removeBtnUIConfig.isEnabled()) {
					removeUIConfig();
				}
			}
		});
		
		final TableColumn columnUIConfig = new TableColumn(uiConfigsTable,
				SWT.NONE);
		columnUIConfig.setWidth(135);
		columnUIConfig.setText(Messages
				.getString("UIConfigurationsPage.UIConfigurationColumn")); //$NON-NLS-1$
		
		final TableColumn columnUIConfigName = new TableColumn(uiConfigsTable,
				SWT.NONE);
		columnUIConfigName.setWidth(0);
		columnUIConfigName.setResizable(false);
		columnUIConfigName.setText(Messages
				.getString("UIConfigurationsPage.nameColumn")); //$NON-NLS-1$
		
		final TableColumn columnLayoutConfig = new TableColumn(uiConfigsTable,
				SWT.NONE);
		columnLayoutConfig.setWidth(135);
		columnLayoutConfig.setResizable(false);
		columnLayoutConfig.setText(Messages
				.getString("UIConfigurationsPage.LayoutColumn")); //$NON-NLS-1$
		
		final TableColumn columnCommandList = new TableColumn(uiConfigsTable,
				SWT.NONE);
		columnCommandList.setWidth(145);
		columnLayoutConfig.setResizable(false);
		columnCommandList.setText(Messages
				.getString("UIConfigurationsPage.CommandListColumn")); //$NON-NLS-1$

		final Composite btnsUIConfigComposite = toolkit.createComposite(
				uiConfigurationsComposite, SWT.NONE);
		
		toolkit.adapt(btnsUIConfigComposite);
		final RowLayout btnsRowLayout = new RowLayout(SWT.VERTICAL);
		btnsRowLayout.justify = true;
		btnsRowLayout.fill = true;
		btnsUIConfigComposite.setLayout(btnsRowLayout);
		final FormData frmDataBtnsView = new FormData();
		frmDataBtnsView.bottom = new FormAttachment(0, 150);// height of the rowlayout
		frmDataBtnsView.top = new FormAttachment(0, 6);
		frmDataBtnsView.right = new FormAttachment(0, 520);
		frmDataBtnsView.left = new FormAttachment(uiConfigsTable, 5,
				SWT.DEFAULT);
		btnsUIConfigComposite.setLayoutData(frmDataBtnsView);
		toolkit.paintBordersFor(btnsUIConfigComposite);
		
		addBtnUIConfig = toolkit
				.createButton(
						btnsUIConfigComposite,
						Messages
								.getString("UIConfigurationsPage.AddUIConfigurationButton"), SWT.NONE); //$NON-NLS-1$
		addBtnUIConfig.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				addUIConfigsDialog();//call to the Dialog
				
			}
		}); // Button add

		duplicateBtnUIConfig = toolkit
				.createButton(
						btnsUIConfigComposite,
						Messages
								.getString("UIConfigurationsPage.DuplicateUIConfigurationButton"), SWT.NONE); //$NON-NLS-1$
		duplicateBtnUIConfig.setEnabled(false);
		duplicateBtnUIConfig.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				duplicateUIConfig();
			}
		});// Duplicate button

		removeBtnUIConfig = toolkit
				.createButton(
						btnsUIConfigComposite,
						Messages
								.getString("UIConfigurationsPage.RemoveUIConfigurationButton"), SWT.NONE); //$NON-NLS-1$
		
		removeBtnUIConfig.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				removeUIConfig();				
			}
		});// Remove button
		setUIConfigurationsSection();
		calculateRemoveUIConfigsEnablement();
	}

	/**
	 * Initializes the values of the UI Configurations section.
	 */
	private void setUIConfigurationsSection(){
		Vector<String> currentViewConfigs = uiConfigurationPageUtils.getCurrentObjectsFromModel(UI_CONFIGURATION);
		
		for (String viewConfigName : currentViewConfigs) {
			ModelUtils.getComponentInstance(ModelUtils.lookupReference(editor.getDataModel()
					,viewConfigName).getEObject()).addPropertyListener(uiConfigPropertyListener);
			addUIConfigurationToGUI(ModelUtils.lookupReference(editor.getDataModel()
					,viewConfigName).getEObject());
		}		
	}
	
	/**
	 * Initializes the values of the Layouts section.
	 */
	private void setLayoutsSection(){
		Vector<String> currentLayouts = uiConfigurationPageUtils.getCurrentObjectsFromModel(UI_LAYOUT);
		IComponentInstance layoutComponent; 
		for (String layoutName : currentLayouts) {
			ModelUtils.getComponentInstance(ModelUtils.lookupReference(editor.getDataModel()
					,layoutName).getEObject()).addPropertyListener(layoutPropertyListener);
			layoutComponent = ModelUtils.lookupReference(editor.getDataModel(),
					layoutName);
			if(!layoutComponent.getParent().toString().contains("com.nokia.carbide.uiq.CQikContainer"))
			{	
				addItemsLayoutListToGUI(layoutName);	
			}
		}
	}
	
	/**
	 * Rename the Layout on the model.
	 * @param newName
	 * @param instance
	 */
	private void renameLayoutModel(String newName,EObject instance){
		createAndExecuteSetPropertyCommand(layoutsTable, ModelUtils.getPropertySource(instance), VIEWLAYOUT_PROPERTY_NAME, newName);
	}
	
	/**
	 * Change the specified property using a command
	 * @param inputObject
	 * @param propertySource
	 * @param propertyname
	 * @param newValue
	 */
	private void createAndExecuteSetPropertyCommand(Object inputObject, IPropertySource propertySource, String propertyName, String newValue){
		FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), inputObject);
		CompoundCommand cc = new CompoundCommand();
		SetPropertyCommand setViewLayoutNameCommand = new SetPropertyCommand(propertySource,
			propertyName, newValue);
		cc.append(setViewLayoutNameCommand);
		EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
		editor.getCommandStack().execute(wrapper);
	}

	/**
	 * Duplicates the layout selected in the table. It creates a new instance with
	 * the same properties, only changing the name.
	 * @param newName
	 * @param originalInstance
	 */
	private void duplicateLayoutModel(String newName,EObject originalObject){
		EObject originalParent = ModelUtils.getComponentInstance(originalObject).getParent();
		EObject newObject = duplicateModelInstances(originalObject,originalParent);
		copyInstanceProperties(originalObject, newObject);
		
		int design;
		if (idDesign.compareTo(UIQ_CQIKVIEW) == 0){
			design = 0;
		} else {
			design = 1;
		}	
		
		EObject newContainer = null;
		EObject originalContainer = null;
		EObject newLayoutManager = null;
		EObject originalLayoutManager = null;
		if (design == 0){ // If the model is a View, set the properties for the view page
			EObject newViewPage = ModelUtils.findFirstComponentInstance(newObject, VIEW_PAGE, true);
			EObject originalViewPage = ModelUtils.findFirstComponentInstance(originalObject, VIEW_PAGE, true);
			copyInstanceProperties(originalViewPage, newViewPage);
			
			newContainer=ModelUtils.findFirstComponentInstance(newViewPage, UIQ_CQIKCONTAINER, true);
			originalContainer=ModelUtils.findFirstComponentInstance(originalViewPage, UIQ_CQIKCONTAINER, true);

		}
		else {
			newContainer=ModelUtils.findFirstComponentInstance(newObject, UIQ_CQIKCONTAINER, true);
			originalContainer=ModelUtils.findFirstComponentInstance(originalObject, UIQ_CQIKCONTAINER, true);
		}
			
		copyInstanceProperties(originalContainer, newContainer);


		originalLayoutManager = ModelUtils.findImmediateChildWithAttributeValue(originalContainer
				,CommonAttributes.IS_LAYOUT_MANAGER, "true"); //returns the original layout manager
		
		newLayoutManager = ModelUtils.findImmediateChildWithAttributeValue(newContainer
				,CommonAttributes.IS_LAYOUT_MANAGER, "true"); //returns the new layout manager

		if(ModelUtils.getComponentInstance(originalLayoutManager).getComponentId() != 
		   ModelUtils.getComponentInstance(newLayoutManager).getComponentId()){
			createLayoutManager(ModelUtils.getComponentInstance(originalLayoutManager).getComponentId(),newContainer);
			newLayoutManager = ModelUtils.findImmediateChildWithAttributeValue(newContainer
					,CommonAttributes.IS_LAYOUT_MANAGER, "true"); //returns the new layout manager			
		}
		copyInstanceProperties(originalLayoutManager, newLayoutManager);

		IPropertySource layoutProperties = ModelUtils.getPropertySource(newObject);
		layoutProperties.setPropertyValue("name",newName);		
		
	}

	private void copyInstanceProperties(EObject originalObject,
			EObject newObject) {
		IPropertySource originalPropertySource = ModelUtils.getPropertySource(originalObject);
		IPropertySource newPropertySource = ModelUtils.getPropertySource(newObject);
		copyPropertySource(originalPropertySource, newPropertySource);
	}

	private EObject duplicateModelInstances(EObject originalObject, EObject newObjectParent) {
		CompoundCommand cc = new CompoundCommand();
		cc.setLabel(getCommandLabelForLayout());
		
		IComponentInstance originalComponentInstance = ModelUtils.getComponentInstance(originalObject);
		IComponent component = originalComponentInstance.getComponent();
		Check.checkContract(component != null);
		EObject newObject = editor.getDataModel().createNewComponentInstance(
				component);		
		
		Command addNewObjectCommand = editor.getDataModel().createAddNewComponentInstanceCommand(
				newObjectParent, newObject, 0);
		cc.append(addNewObjectCommand);
		
		FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), layoutsTable);
		EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
		editor.getCommandStack().execute(wrapper);

		return newObject;
	}
	
	
	private String getCommandLabelForLayout(){
		if (idDesign.compareTo(UIQ_CQIKVIEW) == 0){
			return Messages.getString("LayoutsDialog.tipCommand.addView"); 
		}
		else{
			return Messages.getString("LayoutsDialog.tipCommand.addDialog"); 
		}		
	}

	private void copyPropertySource(IPropertySource originalProperties,
			IPropertySource newProperties) {
		IPropertyDescriptor[] descriptors = originalProperties.getPropertyDescriptors();
		
		IPropertyDescriptor[] newDescriptors = newProperties.getPropertyDescriptors();
		Hashtable<String, Object> newPropertiesValues = new Hashtable<String, Object>();
		for (IPropertyDescriptor descriptor : newDescriptors){			
			newPropertiesValues.put((String)descriptor.getId(), 
							newProperties.getPropertyValue(descriptor.getId()));			
		}
	
		for (IPropertyDescriptor descriptor : descriptors){			
			String propertyName = (String)descriptor.getId();
			
			if(!propertyName.equals("name") && !propertyName.equals("referenceLayoutManager")){
				Object originalPropertyValue = originalProperties.getPropertyValue(propertyName);
				
				
				if(originalPropertyValue instanceof IPropertySource){
					copyPropertySource((IPropertySource)originalPropertyValue,(IPropertySource)newProperties.getPropertyValue(propertyName));
				}
				else{
					if(newPropertiesValues.containsKey(propertyName)){
						newProperties.setPropertyValue(propertyName,originalPropertyValue);
					}else{
//						System.out.println("FIN[" + propertyName + "]=" + newProperties.getPropertyValue(propertyName));
					}
				}
			}
		}
	}
	
	/**
	 * Removes layouts from the model
	 * @param indices - Indices of the elements to be deleted.
	 */
	private void removeLayoutFromModel(int[] indices){
		List<EObject> layoutsToRemove = new ArrayList<EObject>();
		
		for (int i : indices) {
			TableItem item = layoutsTable.getItem(i);
			String instanceName = item.getText(0);
			EObject layoutInstance = editor.getDataModel().findByNameProperty(instanceName);
			//uiConfigurationPageUtils.getPropertyValueByInstance(layoutInstance
			//		,VIEWLAYOUT_PROPERTY_NAME);
			layoutsToRemove.add(layoutInstance);
		}
		
		CompoundCommand cc = new CompoundCommand();
		cc.setLabel(Messages.getString("LayoutsDialog.Delete.tipCommand")); //$NON-NLS-1$
		Command removeCommand = editor.getDataModel().createRemoveComponentInstancesCommand(layoutsToRemove);		
		cc.append(removeCommand);
		FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), layoutsTable);
		EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
		editor.getCommandStack().execute(wrapper);	
	}
	
	/**
	 * Remove the layout from the GUI.
	 * @param layoutInstance - The instance to be removed.
	 */
	private void removeLayoutFromGUI(EObject layoutInstance){
		refreshChangesLayoutGUI(VIEWLAYOUT_PROPERTY_NAME);
		calculateRemoveLayoutsEnablement();
	}
	
	/**
	 * Add the layout to the table.
	 * @param nameLayout - Name of the layout
	 */
	private void addItemsLayoutListToGUI(String nameLayout) {
		TableItem item = new TableItem(layoutsTable, SWT.NONE);
		item.setText(nameLayout);
		calculateRemoveLayoutsEnablement();
	}
	
	/**
	 * Enables or disables the remove layout button based on the number of existing layouts
	 */
	private void calculateRemoveLayoutsEnablement() {
		if (layoutsTable.getItemCount() > 1) { 
			removeBtnLayout.setEnabled(true);
		} else {
			removeBtnLayout.setEnabled(false);
		}
	}
	
	/**
	 * Enables or disables the remove layout button based on the number of existing layouts
	 */
	private void calculateRemoveUIConfigsEnablement() {
		if (uiConfigsTable != null) {
			if (uiConfigsTable.getItemCount() > 1) { 
				removeBtnUIConfig.setEnabled(true);
			} else {
				removeBtnUIConfig.setEnabled(false);
			}
		}
	}
	/**
	 * Add layout to the model.
	 * @param nameLayout - Name of the layout
	 * @param commandListLayout - Command list for the layout
	 * @param tabCaption - Tab caption 
	 * @param tabImageObj - Object with the image property
	 * @param commandListView - Command list for the view
	 * @param scrollContainer - Scroll property for the container
	 * @param layoutManager - Type of layout manager (label)
	 */
	private void addItemsLayoutListToModel(String nameLayout,String commandListLayout,String tabCaption,
				Object tabImageObj,String commandListView,String scrollContainer,String layoutManager){
		int design = 0; //0 view , 1 simple dialog
		EObject container;
		IPropertySource layoutProperties;
		IPropertySource viewPageProperties; 
		IPropertySource containerProperties,layoutManagerProperties;
		IComponentSet componentSet = editor.getDataModel().getComponentSet();
		Check.checkContract(componentSet != null);
		IComponent componentLayout = componentSet.lookupComponent(UI_LAYOUT);
		Check.checkContract(componentLayout != null);
		EObject layoutChild = editor.getDataModel().createNewComponentInstance(
				componentLayout);
	
			CompoundCommand cc = new CompoundCommand();
			if (idDesign.compareTo(UIQ_CQIKVIEW) == 0){
				cc.setLabel(Messages.getString("LayoutsDialog.tipCommand.addView")); 
			}
			else{
				design = 1;
				cc.setLabel(Messages.getString("LayoutsDialog.tipCommand.addDialog")); 
			}
			Command addLayoutCommand = editor.getDataModel()
					.createAddNewComponentInstanceCommand(
							layoutConfigurationGroup, layoutChild, 0);
			cc.append(addLayoutCommand);
			
			FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), layoutsTable);
			EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
			editor.getCommandStack().execute(wrapper);
			
			layoutProperties = ModelUtils.getPropertySource(layoutChild);
			layoutProperties.setPropertyValue(VIEWLAYOUT_PROPERTY_NAME,nameLayout);
			if (design == 0){ // If the model is a View, set the properties for the view page
				layoutProperties.setPropertyValue(UICONFIGURATION_PROPERTY_COMMANDLIST,commandListLayout);
				EObject viewPage=ModelUtils.findFirstComponentInstance(layoutChild, VIEW_PAGE, true);
				viewPageProperties=ModelUtils.getPropertySource(viewPage);
				viewPageProperties.setPropertyValue(VIEWPAGE_PROPERTY_TAB_CAPTION,tabCaption);
				if (tabImageObj != null){
					viewPageProperties.setPropertyValue(VIEWPAGE_PROPERTY_TAB_IMAGE,tabImageObj);
				}
				
				viewPageProperties.setPropertyValue(UICONFIGURATION_PROPERTY_COMMANDLIST,commandListView);
				container=ModelUtils.findFirstComponentInstance(viewPage, UIQ_CQIKCONTAINER, true);
			}
			else{
				container=ModelUtils.findFirstComponentInstance(layoutChild, UIQ_CQIKCONTAINER, true);
			}
			//set container properties
			containerProperties = ModelUtils.getPropertySource(container);
			containerProperties.setPropertyValue(CQIKCONTAINER_PROPERTY_SCROLLABLE
					,UIConfigurationPageUtils.containerScrollHashTable.get(scrollContainer));
			layoutManagerProperties = (IPropertySource)containerProperties.getPropertyValue(CQIKCONTAINER_PROPERTY_LAYOUTMANAGER);
			//Create the layout manager and set the property
			/*layoutManagerProperties.setPropertyValue(LAYOUTMANAGER_PROPERTY_TYPE,
					uiConfigurationPageUtils.layoutManagersHashTable.get(layoutManager));*/
			layoutManagerProperties.setPropertyValue(LAYOUTMANAGER_PROPERTY_TYPE,layoutManager);
			createLayoutManager(UIConfigurationPageUtils.layoutManagersHashTable.get(layoutManager)
								,container);
	}
	
	/**
	 * Creates the layout manager
	 * @param layoutId - Id for the layout manager type
	 * @param container - Container for the layout manager
	 */
	private void createLayoutManager(String layoutId,EObject container){
		EObject layoutManager;
		
		layoutManager = ModelUtils.findImmediateChildWithAttributeValue(container
				,CommonAttributes.IS_LAYOUT_MANAGER, "true"); //returns the actual layout manager
		if ((layoutId == null) ||(((INode)layoutManager).getComponentId().equals(layoutId))){
			return;
		}
		else{
			IComponent layoutManagerComponent = editor.getDataModel().getComponentSet().lookupComponent(layoutId);
			if(layoutManagerComponent == null)	System.out.println("*********Layout is null************");
			Check.checkContract(layoutManagerComponent != null);
			EObject gridChild = editor.getDataModel().createNewComponentInstance(
					layoutManagerComponent);
			CompoundCommand cc = new CompoundCommand();
			Command addLayoutManagerCommand = editor.getDataModel()
						.createAddNewComponentInstanceCommand(
								container, gridChild, 0);
			cc.append(addLayoutManagerCommand);
			
			FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), layoutsTable);
			EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
			editor.getCommandStack().execute(wrapper);			
		}
	}
	
	/**
	 * Add the UI configuration to the table.
	 * @param UIConfiguration - EObject of the new instance
	 */
	private void addUIConfigurationToGUI(EObject UIConfiguration) {
		if (uiConfigsTable != null) {
			final IPropertySource uiConfigurationProperties = ModelUtils.getPropertySource(UIConfiguration);
			TableItem item = new TableItem(uiConfigsTable, SWT.NONE);
			TableEditor editLayout = new TableEditor(uiConfigsTable);
			TableEditor editCommand = new TableEditor(uiConfigsTable);
			CCombo comboLayout = new CCombo(uiConfigsTable, SWT.READ_ONLY);
			CCombo comboCommand = new CCombo(uiConfigsTable, SWT.READ_ONLY);
			// Edit UI Config (Column 1)
			String labelUIConfig = uiConfigurationPageUtils.getLabelUIConfig(
					(String)uiConfigurationProperties.getPropertyValue(UICONFIGURATION_PROPERTY_UICONFIG));
			item.setText(0, labelUIConfig); // column,text 
			// Hidden instance name (Column 2)
			item.setText(1, (String)uiConfigurationProperties.getPropertyValue(UICONFIGURATION_PROPERTY_NAME)); // column,text //$NON-NLS-1$
			// Edit Layout (Column 3)
			Vector<String> currentLayouts = uiConfigurationPageUtils.getCurrentObjectsFromModel(UI_LAYOUT);
			for (String layoutName : currentLayouts) {
				comboLayout.add(layoutName);
			}
			String layoutPropertyValue = (String)uiConfigurationProperties.getPropertyValue(
					UICONFIGURATION_PROPERTY_VIEWORCONTAINER);
			for (int i = 0; i < comboLayout.getItemCount(); i++) {			
				if (comboLayout.getItem(i).equals(layoutPropertyValue)) {
					comboLayout.select(i);
					break;
				}
			}		
			editLayout.minimumWidth = comboLayout.computeSize(uiConfigsTable
					.getColumn(2).getWidth(), SWT.DEFAULT).x;
			uiConfigsTable.getColumn(2).setWidth(editLayout.minimumWidth);
			editLayout.setEditor(comboLayout, item, 2);
			
			comboLayout.addSelectionListener(new SelectionListener() {
			   public void widgetDefaultSelected(SelectionEvent arg0) {
			   }
			   public void widgetSelected(SelectionEvent arg0) {
			       refreshChangesModel(uiConfigurationProperties,"layout");
			   }
			});
			// Edit Command (Column 4)
			Vector<String> currentCommandLists = uiConfigurationPageUtils.getCurrentObjectsFromModel(UI_COMMAND_LIST);
			comboCommand.add(Messages.getString("CommandListCombo.option"));
			for (String commandListName : currentCommandLists) {
				comboCommand.add(commandListName);
			}
			
			for (int i = 0; i < comboCommand.getItemCount(); i++) {			
				String commandListPropertyValue = (String)uiConfigurationProperties.getPropertyValue(
						UICONFIGURATION_PROPERTY_COMMANDLIST);
				if (comboCommand.getItem(i).equals(commandListPropertyValue)) {
					comboCommand.select(i);
					break;
				}
			}
			editCommand.minimumWidth = comboCommand.computeSize(uiConfigsTable
					.getColumn(3).getWidth(), SWT.DEFAULT).x;
			uiConfigsTable.getColumn(3).setWidth(editCommand.minimumWidth);
	
			editCommand.setEditor(comboCommand, item, 3);
			comboCommand.addSelectionListener(new SelectionListener() {
				   public void widgetDefaultSelected(SelectionEvent arg0) {
				   }
				   public void widgetSelected(SelectionEvent arg0) {
				      refreshChangesModel(uiConfigurationProperties,"command");
				   }
				});
			
			CCombo[] newCombo = new CCombo[] {comboLayout, comboCommand};
			newCombo[0].setData("key", "Combo-0-" + uiConfigsTableCombos.size());
			newCombo[1].setData("key", "Combo-1-" + uiConfigsTableCombos.size());
			uiConfigsTableCombos.add(newCombo);
		}
	}
	
	/**
	 * Add UI configuration to the model
	 * @param uiConfigMode - Label for the UI configuration mode
	 * @param layout - Layout's name
	 * @param commandList - Command List's name
	 */
	private void addUIConfigurationToModel(String uiConfigMode, String layout, String commandList) {
		IPropertySource uiConfigurationProperties; 
		IComponentSet componentSet = editor.getDataModel().getComponentSet();
		Check.checkContract(componentSet != null);
		IComponent component = componentSet.lookupComponent(UI_CONFIGURATION);
		Check.checkContract(component != null);
		EObject child = editor.getDataModel().createNewComponentInstance(
				component);
	
			CompoundCommand cc = new CompoundCommand();
			cc.setLabel(Messages.getString("AddUIConfigDialog.tipCommand")); //$NON-NLS-1$
			Command addViewConfigurationCommand = editor.getDataModel()
					.createAddNewComponentInstanceCommand(
							viewConfigurationsGroup, child, 0);
			cc.append(addViewConfigurationCommand);
			
			FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), uiConfigsTable);
			EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
			editor.getCommandStack().execute(wrapper);
			//set properties
			uiConfigurationProperties = ModelUtils.getPropertySource(child);
			uiConfigurationProperties
					.setPropertyValue(UICONFIGURATION_PROPERTY_NAME,
							(String) uiConfigurationProperties
									.getPropertyValue(UICONFIGURATION_PROPERTY_NAME));
			uiConfigurationProperties.setPropertyValue(UICONFIGURATION_PROPERTY_VIEWORCONTAINER,
					layout);
			uiConfigurationProperties.setPropertyValue(UICONFIGURATION_PROPERTY_COMMANDLIST,
					commandList);
			uiConfigurationProperties					
					.setPropertyValue(UICONFIGURATION_PROPERTY_UICONFIG, 
							UIConfigurationPageUtils.uiConfigModesLocalizedMap.get(uiConfigMode));
	}
	
	/**
	 * Remove all the UI configurations from the table, and the corresponding combos
	 * from the table.
	 */
	private void removeAllUIConfigs(){ 
		if (uiConfigsTable != null) {
			for(int i = 0; i < uiConfigsTableCombos.size(); i++) {
				uiConfigsTableCombos.get(i)[0].dispose();
				uiConfigsTableCombos.get(i)[1].dispose();
			}
			uiConfigsTableCombos.removeAllElements();
			uiConfigsTable.removeAll();
		}
	}
	
	private boolean isResetingUIConfigurationFromGUI = false;
	/**
	 * Reset the UI configurations table.
	 */
	private void resetUIConfigurationFromGUI() {
		if (!isResetingUIConfigurationFromGUI) {
			isResetingUIConfigurationFromGUI = true;
			removeAllUIConfigs();
			setUIConfigurationsSection();
			isResetingUIConfigurationFromGUI = false;
		}
	}
		
	/**
	 * Remove UI configurations from the model.
	 * @param indices - The indices of the items selected to be remove.
	 */
	private void removeUIConfigurationsFromModel(int[] indices) {
		List<EObject> uiConfigsToRemove = new ArrayList<EObject>();
		
		for (int i : indices) {
			TableItem item = uiConfigsTable.getItem(i);
			for (int j = 0; j < uiConfigsTable.getColumnCount(); j++) {
				//System.out.println(j + "-" + item.getText(j));
			}			
			String uiConfigInstanceName = item.getText(1);
			EObject uiConfiguration = editor.getDataModel().findByNameProperty(uiConfigInstanceName);
			uiConfigsToRemove.add(uiConfiguration);
		}
		
		CompoundCommand cc = new CompoundCommand();
		cc.setLabel(Messages.getString("DeleteUIConfigDialog.tipCommand")); //$NON-NLS-1$
		Command removeCommand = editor.getDataModel().createRemoveComponentInstancesCommand(uiConfigsToRemove);		
		cc.append(removeCommand);
		
		FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), uiConfigsTable);
		EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
		editor.getCommandStack().execute(wrapper);
	}
	
	/**
	 * Launches the Add UI configurations dialog
	 */ 
	private void addUIConfigsDialog(){ //0 Add  - 1 Duplicate		 
		 List<String> namesUIConfig = getUIConfigsNamesGUI();
		 ActionsUIConfigDialog addUIConfigDialog = new ActionsUIConfigDialog(editor.getEditorSite().getShell(),0,
				this.uiConfigurationPageUtils,UI_LAYOUT,
				namesUIConfig);		 
		int dialogResult = addUIConfigDialog.open();		
		if (dialogResult == ActionsUIConfigDialog.OK) {
					addUIConfigurationToModel(addUIConfigDialog.getUiConfigValue()
							,addUIConfigDialog.getLayoutValue(), addUIConfigDialog.getCommandListValue());
					}	
	    }

	/**
	 * Gets the current selected row at the corresponding table, and obtains
	 * the value of the field.
	 * @param field - number of the column
	 * @param table
	 * @return value of the field.
	 */
	private String getFieldCurrentRow(Table table, int field){
		String value = "";
		TableItem[] items = table.getSelection();
		if (items.length == 0){
				value = table.getItem(0).getText(field);
				table.select(0);
			}
			else{
				value = items[0].getText(field);
			}
		return value;
	}
	
	/**
	 * Launches the Duplicate UI configuration dialog.
	 */
	private void duplicateUIConfig(){
		String nameUIConfig;
		String nameLayout;
		String nameCommandList;
		String strModeUIConfig;
		String modeUIConfig;
		List<String> namesUIConfig = getUIConfigsNamesGUI();
	
		nameUIConfig = getFieldCurrentRow(uiConfigsTable,COLUMN_NAME_UI_CONFIG);
		nameLayout = uiConfigurationPageUtils.getPropertyValueByName(nameUIConfig
				,UICONFIGURATION_PROPERTY_VIEWORCONTAINER);
		nameCommandList = uiConfigurationPageUtils.getPropertyValueByName(nameUIConfig
				,UICONFIGURATION_PROPERTY_COMMANDLIST);
		strModeUIConfig = uiConfigurationPageUtils.getPropertyValueByName(nameUIConfig
				,UICONFIGURATION_PROPERTY_UICONFIG);
		modeUIConfig = uiConfigurationPageUtils.getLabelUIConfig(strModeUIConfig);
		ActionsUIConfigDialog addUIConfigDialog = new ActionsUIConfigDialog(
				editor.getEditorSite().getShell(),1,
				this.uiConfigurationPageUtils,UI_LAYOUT,
				namesUIConfig);
		int dialogResult = addUIConfigDialog.open();
		if (dialogResult == ActionsUIConfigDialog.OK){
			if(addUIConfigDialog.getUiConfigValue().equals(modeUIConfig)){
				//It can't be the same mode
				dialogResult = ActionsUIConfigDialog.CANCEL;						
			}
			else{
				dialogResult = ActionsUIConfigDialog.OK;
				addUIConfigurationToModel(addUIConfigDialog.getUiConfigValue(),
						nameLayout,nameCommandList);
			}
		}	
	}
	
	/**
	 * Launches the Remove UI configuration dialog.
	 */
	private void removeUIConfig() {		 
		String nameUIConfig;
		
		nameUIConfig = getFieldCurrentRow(uiConfigsTable,COLUMN_NAME_UI_CONFIG);
		MessageBox confirm = new MessageBox(Display.getCurrent().getActiveShell(),
				SWT.ICON_QUESTION | SWT.YES | SWT.NO);
	    String text = Messages.getString("UIConfigDialog.confirmDelete1");
	    String textFormat = MessageFormat.format(text, nameUIConfig);
	    confirm.setMessage(textFormat);
		confirm.setText(Messages.getString("UIConfigDialog.deleteTitle"));
		if (confirm.open() == SWT.YES) {
		  removeUIConfigurationsFromModel(uiConfigsTable.getSelectionIndices());
		}
	}
		
	/**
	 * Launches the Add Layout dialog. 
	 */
	private void addLayoutDialog(){ //0 View  - 1 SimpleDialog
		int design = 1;
		if(idDesign.compareTo(UIQ_CQIKVIEW) == 0){
			design = 0;
		}	
		LayoutsDialog addDialog = new LayoutsDialog(editor.getEditorSite().getShell(),design,editor,
			this.uiConfigurationPageUtils,
			UI_LAYOUT,
			VIEW_PAGE,
			layoutConfigurationGroup);
		int dialogResult = addDialog.open();
		if (dialogResult == LayoutsDialog.OK) {
					if (design == 0){ // view
						addItemsLayoutListToModel(addDialog.getNameLayout(),
								addDialog.getCommandListLayout(),
								addDialog.getTabCaption(),addDialog.getTabImageObj(),
								addDialog.getCommanListView(),
								addDialog.getScrollContainer(),addDialog.getLayoutManager());
					}
					else{ //simple dialog
						addItemsLayoutListToModel(addDialog.getNameLayout(),
								addDialog.getCommandListLayout(),
								"",null,"",
								addDialog.getScrollContainer(),addDialog.getLayoutManager());
					}
					layoutsTable.setFocus();
				}
	}
	
	/**
	 * Launches the Rename Layout dialog.
	 */
	private void renameLayout(){ //0 Rename - 1 Duplicate
		String oldName;
		oldName = getFieldCurrentRow(layoutsTable,COLUMN_NAME_LAYOUT);
		Vector<String> currentLayouts = uiConfigurationPageUtils.getCurrentObjectsFromModel(UI_LAYOUT);
		IComponentInstance layoutInstance = ModelUtils.lookupReference(editor.getDataModel(),oldName);	
		ActionsLayoutsDialog renameDialog = new ActionsLayoutsDialog(editor.getEditorSite().getShell(),0,
				oldName,
				currentLayouts,
				editor.getDataModel());
		int dialogResult = renameDialog.open();
		if (dialogResult == ActionsLayoutsDialog.OK) {
			renameLayoutModel(renameDialog.getName(),layoutInstance.getEObject());
			layoutsTable.setFocus();
		}
	}
	
	/**
	 * Launches the duplicate layout dialog.
	 */
	private void duplicateLayout(){ //0 Rename - 1 Duplicate
		String name;
		name = getFieldCurrentRow(layoutsTable,COLUMN_NAME_LAYOUT);
		Vector<String> currentLayouts = uiConfigurationPageUtils.getCurrentObjectsFromModel(UI_LAYOUT);
		
		IComponentInstance layoutInstance = ModelUtils.lookupReference(editor.getDataModel(),name);	
		name=NamePropertySupport.generateNameForModel(editor.getDataModel(),
				layoutInstance.getComponent(), "NewLayoutConfig", true);
		ActionsLayoutsDialog duplicateDialog = new ActionsLayoutsDialog(editor.getEditorSite().getShell(),1,
			name,
			currentLayouts,
			editor.getDataModel());
		int dialogResult = duplicateDialog.open();
		if (dialogResult == ActionsLayoutsDialog.OK) {
					duplicateLayoutModel(duplicateDialog.getName(),layoutInstance.getEObject());
				}			
	}
	
	/**
	 * Launches the Remove Layout dialog.
	 */
	private void removeLayout() {
		String nameLayout;
		nameLayout = getFieldCurrentRow(layoutsTable,COLUMN_NAME_LAYOUT);
		
		int[] indices = layoutsTable.getSelectionIndices();
		MessageBox confirm = new MessageBox(Display.getCurrent().getActiveShell(),
				SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		String text = Messages.getString("LayoutDialog.confirmDelete1");
		String textFormat = MessageFormat.format(text, nameLayout);
		confirm.setMessage(textFormat);		
		confirm.setText(Messages.getString("LayoutDialog.deleteTitle"));
		if ( confirm.open() == SWT.YES ) {
			removeLayoutFromModel(indices);
		  }	
	}
	
	/**
	 * Returns a list with the names of the UI configurations on the table of the GUI
	 * @return - list of UI configurations.
	 */
	public List<String> getUIConfigsNamesGUI() {
		 TableItem item;
		 List<String> namesUIConfig = new ArrayList<String>();
		 for(int i = 0; i < uiConfigsTable.getItemCount(); i++){
			 item = uiConfigsTable.getItem(i);
			 namesUIConfig.add(item.getText(0));
		 }
		return namesUIConfig;
	}
}
