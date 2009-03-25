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
/* START_USECASES: CU6 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.vieweditor;

import com.nokia.sdt.component.*;
import com.nokia.sdt.component.event.HandlerMethodInformation;
import com.nokia.sdt.component.event.IEventDescriptor;
import com.nokia.sdt.component.symbian.properties.EnumPropertyTypeDescriptor;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.*;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.editor.IDesignerDataModelEditorPage;
import com.nokia.sdt.emf.dm.IDesignerData;
import com.nokia.sdt.emf.dm.ILocalizedStringBundle;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.symbian.dm.UIQModelUtils;
import com.nokia.sdt.symbian.ui.appeditor.FormUtilities;
import com.nokia.sdt.uidesigner.events.EventCommands;
import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.ui.editor.FormEditorEditingContext;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.MessageFormat;
import java.util.*;
import java.util.List;

/**
 * CommandsPage is a class for the third page of the ViewEditor. It creates a page
 * with 3 sections. The first one has a Tree with all the Command elements, the 
 * second one has controls for editing their properties. And the third section has 
 * a list of the Command IDs.
 */
public class CommandsPage extends FormPage implements IDesignerDataModelEditorPage {	
	public static final String COMMANDS_PAGE_ID = "commands"; //$NON-NLS-1$
	public IDesignerDataModelEditor editor;
	private ILocalizedStringBundle bundle;
	private EObject commandListConfigurationGroup;
	public CommandElementModel modelCommands;
	private UIConfigurationPageUtils pageUtils;
	private String uiDesignName;
	private Object selected = null;
	private Hashtable<String, String> typeHashTable;
	private Hashtable<String, String> typeValueDisplayHashTable;
	private final ControlManagerCommands controlManager = new ControlManagerCommands();
	//GUI
	//Command List section
	private Section commandListSection;
	private Button listBtn;
	private Button commandBtn;
	private Button namedGroupBtn;
	private Button anonymousGroupBtn;
	public Button duplicateBtn;
	private Button editImageBtn;
	private Button removeBtn;
	private TreeViewer commandsTreeViewer;
	private Tree	commandsTree;
	private TreeColumn treeColumnElements;
	private TreeColumn treeColumnCommandID;
	private CellEditor cellEditors[];
	private CommandListLabelProvider labelProvider;
	private CommandElementDragListener dragListener;
	private CommandTreeDropAdapter dropAdapter;
	//Editing Section
	private Section editingSection;
	private Text nameTxt;
	private Text txtTxt;
	private Text shortTxtTxt;
	private Combo idCombo;
	private Text priorityTxt;
	private Combo typeCombo;
	private Text namedGrpLinkTxt;
	private Combo anonymousGrpCombo;
	private Combo namedGrpCombo;
	private Button stateEditBtn;
	private Button cpfEditBtn;
	private Label nameLbl;
	private Label priorityLabel;
	//Command ID Section
	private Section commandIdSection;
	private Table commandIDTable;
	private TableColumn commandIdColumn;
	private TableColumn commandIdEColumn;
	private TableColumn enumerateColumn;
	private TableColumn functionColumn;
	private Button newBtn;
	private Button goToCodeBtn;
	private Button removeCommandIdBtn;
	private Vector<Text> textEditorsList;
	private Vector<Control> tableEditorList;
	private Text nameCommandID;
	private Text nameEvent;
	private SingleSettingTextHandlerCommands handlerCommandID; 
	private SingleSettingTextHandlerCommands handlerEvent;
	//Edit properties
	private PropertyEditor propertyEditor;
	//Columns IDS
	private final int COLUMN_COMMAND_ID_NAME = 0;
	private final int COLUMN_COMMAND_ID_COMMANDID = 1;
	private final int COLUMN_COMMAND_ID_ENUMERATOR = 2;
	private final int COLUMN_COMMAND_ID_FUNCTION = 3;	
	//Generic IDs
	private final String UI_COMMAND_LISTS_GROUP = UIQModelUtils.UIQ_COMMAND_LISTS_GROUP; 
	private final String UI_COMMAND_LIST = UIQModelUtils.UIQ_COMMAND_LIST;
	private final String UI_COMMAND = UIQModelUtils.UIQ_COMMAND;
	private final String UI_COMMAND_ID = UIQModelUtils.UIQ_COMMAND_ID;
	private final String UI_ANONYMOUS_GROUP = "com.nokia.carbide.uiq.AnonymousGroup"; //$NON-NLS-1$
	private final String UI_NAMED_GROUP = "com.nokia.carbide.uiq.NamedGroup"; //$NON-NLS-1$
	private final String COMMAND_TYPES = "com.nokia.carbide.uiq.command.TQikCommandType"; //$NON-NLS-1$
	private final String COMMAND_SYSTEM_IDS = "com.nokia.carbide.uiq.CommandId.SystemCommandIds";
	//Properties
	private static final String COMMAND_ID_PROPERTY_NAME = "name"; //$NON-NLS-1$
	private static final String COMMAND_ID_PROPERTY_COMMAND_ID = "commandId"; //$NON-NLS-1$
	private static final String COMMAND_ID_PROPERTY_SYSTEM_ID = "systemCommandId"; //$NON-NLS-1$
	private static final String COMMAND_ID_EVENT = "commandEvent"; //$NON-NLS-1$
	private static final String COMMAND_PROPERTY_NAME = "name"; //$NON-NLS-1$
	private static final String COMMAND_PROPERTY_TEXT = "text"; //$NON-NLS-1$
	private static final String COMMAND_PROPERTY_SHORTTEXT = "shortText"; //$NON-NLS-1$
	private static final String COMMAND_PROPERTY_TYPE = "type"; //$NON-NLS-1$
	private static final String COMMAND_PROPERTY_PRIORITY = "priority"; //integer //$NON-NLS-1$
	private static final String COMMAND_PROPERTY_COMMAND_ID = "commandId"; //$NON-NLS-1$
	private static final String COMMAND_PROPERTY_NAMED_GROUP = "namedGroup"; //$NON-NLS-1$
	private static final String COMMAND_PROPERTY_ANONYMOUS_GROUP = "anonymousGroup"; //$NON-NLS-1$
	private static final String COMMAND_PROPERTY_STATE_FLAGS = "stateFlags"; //$NON-NLS-1$
	private static final String COMMAND_PROPERTY_CPF_FLAGS = "cpfFlags"; //$NON-NLS-1$
	private static final String COMMANDID_PROPERTY_NAME = "commandIDName"; //$NON-NLS-1$
	//Listeners
	private IComponentInstanceChildListener commandListGroupChildListener;
	private IComponentInstanceChildListener commandListChildListener;
	private IComponentInstancePropertyListener commandListPropertyListener;
	private IComponentInstancePropertyListener commandIDPropertyListener;
	private IComponentInstancePropertyListener commandPropertyListener;
	private IComponentInstancePropertyListener namedGroupPropertyListener;
	private IComponentInstancePropertyListener anonymousGroupPropertyListener;
	private IEventBindingListener commandIdEventListener;
	/** 
	 * Class constructor.
	 */
	public CommandsPage(IDesignerDataModelEditor editor) {
		super(editor.getFormEditor(), COMMANDS_PAGE_ID, Messages
				.getString("CommandsPage.TabTitle")); //$NON-NLS-1$
		this.editor = editor;
		tableEditorList = new Vector<Control>();
		typeHashTable = new Hashtable<String,String>();
		typeValueDisplayHashTable = new Hashtable<String,String>(); 
		DesignerDataModel model = (DesignerDataModel) editor.getDataModel();
		DesignerDataModel modelImpl = (DesignerDataModel) model;
		IDesignerData designerData = modelImpl.getDesignerData();
		IComponentInstance instance[] = model.getRootComponentInstances();
		
		uiDesignName=instance[0].getName();
		modelCommands = new CommandElementModel(editor, commandsTreeViewer, uiDesignName);
		pageUtils = new UIConfigurationPageUtils(editor);  						
		if (designerData != null) {
			bundle = designerData.getStringBundle();
		}
		Check.checkArg(bundle != null);
		
		commandListConfigurationGroup = UIConfigurationPageUtils.getObjectById(editor.getDataModel(),
				UI_COMMAND_LISTS_GROUP);
		IComponentInstance commandListGroup = ModelUtils.getComponentInstance(commandListConfigurationGroup);
		//initialize listeners...
		initCommandListGroupChildListener();
		commandListGroup.addChildListener(commandListGroupChildListener);
		initCommandListChildListener();
		//add property listener
		initPropertiesListeners();
		controlManager.enableStatusLineValidationMessages(editor.getEditorSite().getActionBars().getStatusLineManager());
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
		if (commandsTreeViewer == null){
			return;
		}
		//System.out.println("***REVERT COMMANDS PAGE****"); //$NON-NLS-1$
		// restore references to model
		commandListConfigurationGroup = UIConfigurationPageUtils.getObjectById(editor.getDataModel(),
				UI_COMMAND_LISTS_GROUP);
		// restore listeners
		IComponentInstance commandListGroup = ModelUtils.getComponentInstance(commandListConfigurationGroup);
		commandListGroup.addChildListener(commandListGroupChildListener);
		modelCommands.setListener(commandListChildListener);
		modelCommands.setPropertyListeners(commandListPropertyListener,
											commandIDPropertyListener,
											commandPropertyListener,
											namedGroupPropertyListener,
											anonymousGroupPropertyListener);
		// restore Command Lists Tree
		modelCommands.resetModel();
		commandsTreeViewer.setInput(modelCommands.getCommandListElements());
		commandsTreeViewer.expandAll();
		// restore Command ID section
		resetCommandIdGUI();
		commandIDTable.deselectAll();
		if (modelCommands.getCommandListElements().size() > 0){
			commandsTreeViewer.setSelection(new StructuredSelection(modelCommands.getCommandListElements().get(0)),true);
		}
	}
	/**
	 * Create the content of the Form. This method calls the methods to create each section.
	 * @param managedForm
	 */
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		final ScrolledForm form = managedForm.getForm();		
		Composite body = form.getBody();
		body.setLayout(new FormLayout());		
		toolkit.paintBordersFor(body);
		String href =
			 "/com.nokia.carbide.cpp.uiq.help/html/tasks/commands/task_commands.htm";
			 //$NON-NLS-1$
		FormUtilities.addHelpToolbarItem(form.getForm(), href,
			 Messages.getString("CommandsPage.HelpLink")); //$NON-NLS-1$
		//Create sections		
		CreateCommandListSection(toolkit,body);		
		CreateEditingSection(toolkit,body);
		CreateCommandIDSection(toolkit,body);		
	}
	
	@Override
	/**
	 * 
	 */
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		controlManager.enableFormValidationMessages(getManagedForm().getForm().getForm());
		if (modelCommands.getCommandListElements().size() > 0)
			controlManager.validate();		
		// WorkbenchUtils.setHelpContextId(getPartControl(), HELP_CONTEXT_ID);
	}
	
	/**
	 * Create the commands List section. The TreeViewer and the buttons.
	 * @param toolkit
	 * @param body
	 */
	private void CreateCommandListSection(FormToolkit toolkit,Composite body){
		commandListSection = toolkit.createSection(body, Section.DESCRIPTION | Section.TITLE_BAR | Section.FOCUS_TITLE );
		final FormData formDataCommandListSection = new FormData();
		formDataCommandListSection.right = new FormAttachment(0, 415);
		formDataCommandListSection.left = new FormAttachment(0, 0);
		formDataCommandListSection.bottom = new FormAttachment(0, 300);
		formDataCommandListSection.top = new FormAttachment(0, 0);
		commandListSection.setLayoutData(formDataCommandListSection);
		commandListSection.setText(Messages
				.getString("CommandsPage.commandListSection.Title")); //$NON-NLS-1$
		commandListSection.setDescription(Messages
				.getString("CommandsPage.commandListSection.description")); //$NON-NLS-1$
		final Composite comandListSectionComposite = toolkit.createComposite(commandListSection);//new Composite(commandListSection, SWT.None);
		comandListSectionComposite.setLayout(new FormLayout());
		commandListSection.setClient(comandListSectionComposite);
		toolkit.adapt(comandListSectionComposite);
		
		commandsTreeViewer = new TreeViewer(comandListSectionComposite,SWT.FULL_SELECTION | SWT.BORDER);
		commandsTree = commandsTreeViewer.getTree();
		final FormData treeFrmData = new FormData();
		treeFrmData.right = new FormAttachment(0, 240);
		treeFrmData.bottom = new FormAttachment(100, -5);
		treeFrmData.top = new FormAttachment(0, 5);
		treeFrmData.left = new FormAttachment(0, 5);
		commandsTree.setLayoutData(treeFrmData);
		toolkit.adapt(commandsTree, true, true);
		
		commandsTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			   public void selectionChanged(SelectionChangedEvent event) {
				   if(event.getSelection().isEmpty()) {					   
					   editingSection.setVisible(false);
			           return;
			       }
				   
				   if(event.getSelection() instanceof IStructuredSelection) {
					   editingSection.setVisible(true);
					   IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			           Object element = selection.getFirstElement();
			           selected = element; 
			           editImageBtn.setEnabled(true);
			           anonymousGroupBtn.setEnabled(true);
			           duplicateBtn.setEnabled(true);
			           if (element instanceof CommandList){//Disable Edit Image button
			        	   duplicateBtn.setEnabled(false);
			        	   editImageBtn.setEnabled(false);
			        	   editingSection.setText(Messages.getString(
			        			   	"CommandsPage.commandEditingSection.title.editCommandList"));
			        	   displayPropertiesCommandList((CommandList)element);
			           }
			           if (element instanceof CommandModel){//Disable Edit Image button
			        	   editingSection.setText(Messages.getString(
	        			   	"CommandsPage.commandEditingSection.title.editCommand"));
			        	   displayPropertiesCommandOrNamed((CommandModel)element, true);
			           }
			           if (element instanceof AnonymousGroup){//Disable Edit Image button
			        	   anonymousGroupBtn.setEnabled(false);
			        	   editImageBtn.setEnabled(false);
			        	   editingSection.setText(Messages.getString(
	        			   	"CommandsPage.commandEditingSection.title.editAnonymousGroup"));
			        	   displayPropertiesAnonymousGroup((AnonymousGroup)element);
			           }
			           if (element instanceof NamedGroup){//Disable Edit Image button
			        	   editingSection.setText(Messages.getString(
	        			   	"CommandsPage.commandEditingSection.title.editNamedGroup"));
			        	   displayPropertiesCommandOrNamed((NamedGroup)element, false);
			           }
			       }
			   }
			});
		
		treeColumnElements = new TreeColumn(commandsTree, SWT.NONE);
		treeColumnElements.setWidth(120);
		treeColumnElements.setText(Messages
				.getString("CommandsPage.commandListSection.tree.column1.label")); //$NON-NLS-1$

		treeColumnCommandID = new TreeColumn(commandsTree, SWT.NONE);
		treeColumnCommandID.setWidth(100);
		treeColumnCommandID.setText(Messages
				.getString("CommandsPage.commandListSection.tree.column2.label")); //$NON-NLS-1$
		commandsTree.setHeaderVisible(true);		
		
		listBtn = toolkit.createButton(comandListSectionComposite, "", SWT.FLAT);
		final FormData listBtnFrmData = new FormData();
		listBtnFrmData.left = new FormAttachment(commandsTree, 5, SWT.RIGHT);
		listBtn.setLayoutData(listBtnFrmData);
		toolkit.adapt(listBtn, true, true);
		listBtn.setText(Messages
				.getString("CommandsPage.commandListSection.buttons.list.label")); //$NON-NLS-1$

		listBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				addCommandListElement();
			}
		});
		
		commandBtn = toolkit.createButton(comandListSectionComposite, "", SWT.FLAT);
		listBtnFrmData.right = new FormAttachment(commandBtn, 0, SWT.RIGHT);
		listBtnFrmData.top = new FormAttachment(commandBtn, -28, SWT.TOP);
		listBtnFrmData.bottom = new FormAttachment(commandBtn, -5, SWT.TOP);
		final FormData commandBtnFrmData = new FormData();
		commandBtn.setLayoutData(commandBtnFrmData);
		toolkit.adapt(commandBtn, true, true);
		commandBtn.setText(Messages
				.getString("CommandsPage.commandListSection.buttons.command.label")); //$NON-NLS-1$

		commandBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				addCommandElement();
			}
		});
		
		namedGroupBtn = toolkit.createButton(comandListSectionComposite, "", SWT.FLAT);
		commandBtnFrmData.right = new FormAttachment(namedGroupBtn, 0, SWT.RIGHT);
		commandBtnFrmData.left = new FormAttachment(namedGroupBtn, 0, SWT.LEFT);
		commandBtnFrmData.top = new FormAttachment(namedGroupBtn, -28, SWT.TOP);
		commandBtnFrmData.bottom = new FormAttachment(namedGroupBtn, -5, SWT.TOP);
		final FormData namedGroupBtnFrmData = new FormData(SWT.FILL,SWT.FILL);
		namedGroupBtn.setLayoutData(namedGroupBtnFrmData);
		toolkit.adapt(namedGroupBtn, true, true);
		namedGroupBtn.setText(Messages
				.getString("CommandsPage.commandListSection.buttons.namedGroup.label")); //$NON-NLS-1$
		namedGroupBtn.setAlignment(SWT.UP | SWT.LEFT_TO_RIGHT);
		
		namedGroupBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				addNamedGroupElement();
			}
		});
		
		anonymousGroupBtn = toolkit.createButton(comandListSectionComposite, "", SWT.FLAT);
		namedGroupBtnFrmData.right = new FormAttachment(anonymousGroupBtn, 0, SWT.RIGHT);
		namedGroupBtnFrmData.left = new FormAttachment(anonymousGroupBtn, 0, SWT.LEFT);
		namedGroupBtnFrmData.top = new FormAttachment(anonymousGroupBtn, -34, SWT.TOP);
		namedGroupBtnFrmData.bottom = new FormAttachment(anonymousGroupBtn, -5, SWT.TOP);
		final FormData anonymousGroupBtnFrmData = new FormData(SWT.FILL,SWT.FILL);
		anonymousGroupBtnFrmData.left = new FormAttachment(commandsTree, 5, SWT.RIGHT);
		anonymousGroupBtnFrmData.right = new FormAttachment(0, 395);
		anonymousGroupBtn.setLayoutData(anonymousGroupBtnFrmData);
		toolkit.adapt(anonymousGroupBtn, true, true);
		anonymousGroupBtn.setText(Messages
				.getString("CommandsPage.commandListSection.buttons.anonymousGroup.label")); //$NON-NLS-1$
		anonymousGroupBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				addAnonymousGroupElement();
			}
		});
		
		duplicateBtn = toolkit.createButton(comandListSectionComposite, "", SWT.FLAT);//SWT.TOGGLE | SWT.BORDER);		
		anonymousGroupBtnFrmData.top = new FormAttachment(duplicateBtn, -35, SWT.TOP);
		anonymousGroupBtnFrmData.bottom = new FormAttachment(duplicateBtn, -5, SWT.TOP);
		final FormData duplicateBtnFrmData = new FormData();
		duplicateBtnFrmData.right = new FormAttachment(anonymousGroupBtn, 150, SWT.LEFT);
		duplicateBtnFrmData.left = new FormAttachment(anonymousGroupBtn, 0, SWT.LEFT);
		duplicateBtn.setLayoutData(duplicateBtnFrmData);
		toolkit.adapt(duplicateBtn, true, true);
		duplicateBtn.setText(Messages
				.getString("CommandsPage.commandListSection.buttons.duplicate.label")); //$NON-NLS-1$
		duplicateBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {				
				copyBranch();
			}
		});
		editImageBtn = toolkit.createButton(comandListSectionComposite, "", SWT.FLAT);//new Button(comandListSectionComposite, SWT.FLAT);
		duplicateBtnFrmData.top = new FormAttachment(editImageBtn, -28, SWT.TOP);
		duplicateBtnFrmData.bottom = new FormAttachment(editImageBtn, -5, SWT.TOP);
		final FormData editImageBtnFrmData = new FormData();
		editImageBtnFrmData.right = new FormAttachment(duplicateBtn, 0, SWT.RIGHT);
		editImageBtnFrmData.top = new FormAttachment(0, 162);
		editImageBtnFrmData.bottom = new FormAttachment(0, 185);
		editImageBtnFrmData.left = new FormAttachment(duplicateBtn, 0, SWT.LEFT);
		editImageBtn.setLayoutData(editImageBtnFrmData);
		toolkit.adapt(editImageBtn, true, true);
		editImageBtn.setText(Messages
				.getString("CommandsPage.commandListSection.buttons.edit.label")); //$NON-NLS-1$
		editImageBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				selected = getSelectionTree();
				if (selected != null){
					modelCommands.editTitleIcon(selected);
				}
			}
		});
		
		removeBtn = toolkit.createButton(comandListSectionComposite, "", SWT.FLAT);//new Button(comandListSectionComposite, SWT.FLAT);
		final FormData removeBtnFrmData = new FormData();
		removeBtnFrmData.bottom = new FormAttachment(0, 210);
		removeBtnFrmData.right = new FormAttachment(editImageBtn, 0, SWT.RIGHT);
		removeBtnFrmData.top = new FormAttachment(0, 188);
		removeBtnFrmData.left = new FormAttachment(editImageBtn, 0, SWT.LEFT);
		removeBtn.setLayoutData(removeBtnFrmData);
		toolkit.adapt(removeBtn, true, true);
		removeBtn.setText(Messages
				.getString("CommandsPage.commandListSection.buttons.remove.label")); //$NON-NLS-1$
		removeBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
					removeElement();
			}
		});
		initializeCommandListSection();		
	}
	
	private void copyBranch() {
		if (selected instanceof CommandList){
			return;
		}
		CommandElementTreeContentProvider provider = 
			(CommandElementTreeContentProvider)	commandsTreeViewer.getContentProvider();
		Object parent = provider.getParent(selected);
		modelCommands.isCopy = true;
		dropAdapter.performCopy(selected, parent);
		modelCommands.isCopy = false;		
	}

	/**
	 * Create editing section. The controls for edit the properties of the elements.
	 * @param toolkit
	 * @param body
	 */
	private void CreateEditingSection(FormToolkit toolkit,Composite body){		
		editingSection = toolkit.createSection(body, Section.TITLE_BAR | Section.DESCRIPTION);
		final FormData editingSectionFrmData = new FormData();
		editingSectionFrmData.right = new FormAttachment(100, -5);
		editingSectionFrmData.top = new FormAttachment(0, 0);
		editingSectionFrmData.bottom = new FormAttachment(0,300);
		editingSectionFrmData.left = new FormAttachment(0, 425);
		editingSection.setLayoutData(editingSectionFrmData);
		editingSection.setDescription(Messages
				.getString("CommandsPage.commandEditingSection.description")); //$NON-NLS-1$
	
	    final Composite editingSectionComposite = toolkit.createComposite(editingSection);
		editingSection.setClient(editingSectionComposite);
		final GridLayout editSectionGridLayout = new GridLayout();
		editSectionGridLayout.numColumns = 4;
		editingSectionComposite.setLayout(editSectionGridLayout);
		toolkit.adapt(editingSectionComposite);
		editingSection.setClient(editingSectionComposite);
	
		nameLbl = new Label(editingSectionComposite, SWT.NONE);
		toolkit.adapt(nameLbl, true, true);
		nameLbl.setText(Messages
				.getString("CommandsPage.commandEditingSection.controls.name.label")); //$NON-NLS-1$
	
		nameTxt = toolkit.createText(editingSectionComposite, "", SWT.BORDER);
		toolkit.adapt(nameTxt, true, true);
		setLayoutEditingSection(nameTxt);
		nameTxt.addListener(SWT.FocusOut, new Listener() {
		      public void handleEvent(Event e) {
		    	  if (!NamePropertySupport.isLegalName(nameTxt.getText())
		    		 || (!ViewEditorUtils.validateUniqueName(editor.getDataModel(),nameTxt.getText()))){ //The name must be valid.
					     return;		    		  
					}
		    	  refreshPropertiesCommand(COMMAND_PROPERTY_NAME, nameTxt.getText());
		      }
	
		    });
		new Label(editingSectionComposite, SWT.NONE);
		new Label(editingSectionComposite, SWT.NONE);
	
		final Label textLabel = new Label(editingSectionComposite, SWT.NONE);
		toolkit.adapt(textLabel, true, true);
		textLabel.setText(Messages
				.getString("CommandsPage.commandEditingSection.controls.text.label")); //$NON-NLS-1$
	
		txtTxt = toolkit.createText(editingSectionComposite, "", SWT.BORDER);
		toolkit.adapt(txtTxt, true, true);
		setLayoutEditingSection(txtTxt);
		txtTxt.addListener(SWT.FocusOut, new Listener() {
		      public void handleEvent(Event e) {
		    	  refreshPropertiesCommand(COMMAND_PROPERTY_TEXT, txtTxt.getText());
		      }
	
		    });
		final Label shortTextLabel = new Label(editingSectionComposite, SWT.NONE);
		toolkit.adapt(shortTextLabel, true, true);
		shortTextLabel.setText(Messages
				.getString("CommandsPage.commandEditingSection.controls.shortText.label")); //$NON-NLS-1$
	
		shortTxtTxt = toolkit.createText(editingSectionComposite, "", SWT.BORDER);
		toolkit.adapt(shortTxtTxt, true, true);
		setLayoutEditingSection(shortTxtTxt);
		shortTxtTxt.addListener(SWT.FocusOut, new Listener() {
		      public void handleEvent(Event e) {
		    	  refreshPropertiesCommand(COMMAND_PROPERTY_SHORTTEXT, shortTxtTxt.getText());
		      }
	
		    });
		final Label idLabel = new Label(editingSectionComposite, SWT.NONE);
		toolkit.adapt(idLabel, true, true);
		idLabel.setText(Messages
				.getString("CommandsPage.commandEditingSection.controls.id.label")); //$NON-NLS-1$
	
		idCombo = new Combo(editingSectionComposite, SWT.NONE | SWT.READ_ONLY);
		toolkit.adapt(idCombo, true, true);
		final GridData idComboGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		idComboGridData.widthHint=80;
		idCombo.setLayoutData(idComboGridData);
		idCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				refreshPropertiesCommand(COMMAND_PROPERTY_COMMAND_ID,
						idCombo.getItem(idCombo.getSelectionIndex()));
			}
	
			public void widgetDefaultSelected(SelectionEvent arg0) {				
			}
		});
		priorityLabel = new Label(editingSectionComposite, SWT.NONE);
		toolkit.adapt(priorityLabel, true, true);
		priorityLabel.setText(Messages
				.getString("CommandsPage.commandEditingSection.controls.priority.label")); //$NON-NLS-1$
	
		priorityTxt = toolkit.createText(editingSectionComposite, "", SWT.BORDER);
		toolkit.adapt(priorityTxt, true, true);
		setLayoutEditingSection(priorityTxt);
		priorityTxt.setText(""); //$NON-NLS-1$
		priorityTxt.addListener(SWT.FocusOut, new Listener() {
		      public void handleEvent(Event e) {
		    	  if (priorityTxt.getText().matches("[-+]?\\b\\d+\\b")){
		    		  refreshPropertiesCommand(COMMAND_PROPERTY_PRIORITY, 
		    			  priorityTxt.getText());
		    	  }
		      }
	
		    });		
		final Label typeLabel = new Label(editingSectionComposite, SWT.NONE);
		toolkit.adapt(typeLabel, true, true);
		typeLabel.setText(Messages
				.getString("CommandsPage.commandEditingSection.controls.type.label")); //$NON-NLS-1$
	
		typeCombo = new Combo(editingSectionComposite, SWT.NONE | SWT.READ_ONLY);
		toolkit.adapt(typeCombo, true, true);
		final GridData typeComboGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		typeComboGridData.widthHint=80;
		typeCombo.setLayoutData(typeComboGridData);
		typeCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				if (typeCombo.getSelectionIndex() > 0) {
					refreshPropertiesCommand(COMMAND_PROPERTY_TYPE, 
							typeHashTable.get(typeCombo.getItem(typeCombo.getSelectionIndex())));
				}
			}	
			public void widgetDefaultSelected(SelectionEvent arg0) {				
			}
		});
		final Label anonymousGroupLabel = new Label(editingSectionComposite, SWT.NONE);
		toolkit.adapt(anonymousGroupLabel, true, true);
		anonymousGroupLabel.setText(Messages
				.getString("CommandsPage.commandEditingSection.controls.anonymousGrp.label")); //$NON-NLS-1$
	
		anonymousGrpCombo = new Combo(editingSectionComposite, SWT.NONE | SWT.READ_ONLY);
		toolkit.adapt(anonymousGrpCombo, true, true);
		final GridData anonymousGrpComboGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		anonymousGrpComboGridData.widthHint=80;
		anonymousGrpCombo.setLayoutData(anonymousGrpComboGridData);
		anonymousGrpCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				if (anonymousGrpCombo.getSelectionIndex() > -1) {
					refreshPropertiesCommand(COMMAND_PROPERTY_ANONYMOUS_GROUP,
							anonymousGrpCombo.getItem(anonymousGrpCombo
									.getSelectionIndex()));
				}
			}
	
			public void widgetDefaultSelected(SelectionEvent arg0) {				
			}
		});
		final Label namedGroupLinkLabel = new Label(editingSectionComposite, SWT.NONE);
		toolkit.adapt(namedGroupLinkLabel, true, true);
		namedGroupLinkLabel.setText(Messages
				.getString("CommandsPage.commandEditingSection.controls.namedGrpLnk.label")); //$NON-NLS-1$
	
		namedGrpLinkTxt = toolkit.createText(editingSectionComposite, "", SWT.NONE);
		toolkit.adapt(namedGrpLinkTxt, true, true);
		setLayoutEditingSection(namedGrpLinkTxt);
		final Label namedGroupLabel = new Label(editingSectionComposite, SWT.NONE);
		toolkit.adapt(namedGroupLabel, true, true);
		namedGroupLabel.setText(Messages
				.getString("CommandsPage.commandEditingSection.controls.namedGrp.label")); //$NON-NLS-1$
	
		namedGrpCombo = new Combo(editingSectionComposite, SWT.NONE | SWT.READ_ONLY);
		toolkit.adapt(namedGrpCombo, true, true);
		final GridData namedGrpComboGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		namedGrpComboGridData.widthHint=80;
		namedGrpCombo.setLayoutData(namedGrpComboGridData);
		namedGrpCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				if (namedGrpCombo.getSelectionIndex() > -1){
					refreshPropertiesCommand(COMMAND_PROPERTY_NAMED_GROUP,
						namedGrpCombo.getItem(namedGrpCombo.getSelectionIndex()));
				}
			}
	
			public void widgetDefaultSelected(SelectionEvent arg0) {				
			}
		});
		final Label stateFlagsLabel = new Label(editingSectionComposite, SWT.NONE);
		toolkit.adapt(stateFlagsLabel, true, true);
		stateFlagsLabel.setText(Messages
				.getString("CommandsPage.commandEditingSection.controls.state.label")); //$NON-NLS-1$
	
		stateEditBtn = toolkit.createButton(editingSectionComposite, "", SWT.FLAT);
		toolkit.adapt(stateEditBtn, true, true);
		stateEditBtn.setText(Messages
				.getString("CommandsPage.commandEditingSection.buttons.editState.label")); //$NON-NLS-1$
		
		stateEditBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				propertyEditorDialog(COMMAND_PROPERTY_STATE_FLAGS);//call to the Dialog
				
			}
		});
		
		final Label cpfFlagsLabel = new Label(editingSectionComposite, SWT.NONE);
		toolkit.adapt(cpfFlagsLabel, true, true);
		cpfFlagsLabel.setText(Messages
				.getString("CommandsPage.commandEditingSection.controls.cpf.label")); //$NON-NLS-1$
	
		cpfEditBtn = toolkit.createButton(editingSectionComposite, "", SWT.FLAT);
		toolkit.adapt(cpfEditBtn, true, true);
		cpfEditBtn.setText(Messages
				.getString("CommandsPage.commandEditingSection.buttons.editCpf.label")); //$NON-NLS-1$
		
		cpfEditBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				propertyEditorDialog(COMMAND_PROPERTY_CPF_FLAGS);//call to the Dialog
				
			}
		});
		
		hookControls();
	}
	
	private void hookControls() {
		
		SingleSettingTextHandlerCommands handler = new SingleSettingTextHandlerCommands(nameTxt, 
				new FormEditorEditingContext(editor.getFormEditor(), nameTxt),
				new RegExInputValidatorCommands("^[(a-z)|(A-Z)][\\w]*$", false, Messages.getString("CommandsPage.name.validateEmpty")
						));
		handler.setLabel(nameLbl);
		controlManager.add(handler);
		
		SingleSettingTextHandlerCommands handlerNumber = new SingleSettingTextHandlerCommands(priorityTxt, 
				new FormEditorEditingContext(editor.getFormEditor(), nameTxt),
				new NumberValidatorCommands(Integer.MIN_VALUE, Integer.MAX_VALUE, false, 
						Messages.getString("CommandsPage.priority.validate")));
		handlerNumber.setLabel(priorityLabel);
		controlManager.add(handlerNumber);
	}
	
	/**
	 * Create the commands ID section. The table with the commands ID's.
	 * @param toolkit
	 * @param body
	 */
	private void CreateCommandIDSection(FormToolkit toolkit,Composite body){
	
		textEditorsList =  new Vector<Text>();
		commandIdSection = toolkit.createSection(body, Section.TITLE_BAR | Section.DESCRIPTION);
		final FormData commandIdSectionFrmData = new FormData();
		commandIdSectionFrmData.top = new FormAttachment(0, 310);
		commandIdSectionFrmData.right = new FormAttachment(100, 0);
		commandIdSectionFrmData.bottom = new FormAttachment(100, 0);
		commandIdSectionFrmData.left = new FormAttachment(0, 2);
		commandIdSection.setLayoutData(commandIdSectionFrmData);
		commandIdSection.setDescription(Messages
				.getString("CommandsPage.commandIdSection.description")); //$NON-NLS-1$
		commandIdSection.setText(Messages
				.getString("CommandsPage.commandIdSection.Title")); //$NON-NLS-1$
		
		
		final Composite commandIdSectionComposite = toolkit.createComposite(commandIdSection);
		commandIdSectionComposite.setLayout(new FormLayout());
		commandIdSection.setClient(commandIdSectionComposite);
		toolkit.adapt(commandIdSectionComposite);
	
		commandIDTable = toolkit.createTable(commandIdSectionComposite, SWT.BORDER | SWT.FULL_SELECTION);
		final FormData commandIDTableFrmData = new FormData();
		commandIDTableFrmData.right = new FormAttachment(0, 495);
		commandIDTableFrmData.bottom = new FormAttachment(0, 221);
		commandIDTableFrmData.top = new FormAttachment(0, 28);
		commandIDTableFrmData.left = new FormAttachment(0, 5);
		commandIDTable.setLayoutData(commandIDTableFrmData);
		toolkit.adapt(commandIDTable, true, true);
		commandIDTable.setLinesVisible(true);
		commandIDTable.setHeaderVisible(true);
		commandIDTable.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			public void widgetSelected(SelectionEvent arg0) {
				removeCommandIdBtn.setEnabled(true);
				goToCodeBtn.setEnabled(true);
			}
			
		});
		
		commandIDTable.addListener(SWT.Traverse, new Listener(){

			public void handleEvent(Event arg0) {
				if (commandIDTable.getSelectionCount() == 0){
					removeCommandIdBtn.setEnabled(false);
					goToCodeBtn.setEnabled(false);
				}				
			}
			
		});
				
		commandIdColumn = new TableColumn(commandIDTable, SWT.NONE);
		commandIdColumn.setWidth(110);
		commandIdColumn.setText(Messages
				.getString("CommandsPage.commandIdSection.table.column1.label")); //$NON-NLS-1$
		
		commandIdEColumn = new TableColumn(commandIDTable, SWT.NONE);
		commandIdEColumn.setWidth(110);
		commandIdEColumn.setText(Messages
				.getString("CommandsPage.commandIdSection.table.column2.label")); //$NON-NLS-1$
		
		enumerateColumn = new TableColumn(commandIDTable, SWT.NONE);
		enumerateColumn.setWidth(110);
		enumerateColumn.setText(Messages
				.getString("CommandsPage.commandIdSection.table.column3.label")); //$NON-NLS-1$
	
		functionColumn = new TableColumn(commandIDTable, SWT.NONE);
		functionColumn.setWidth(165);
		functionColumn.setText(Messages
				.getString("CommandsPage.commandIdSection.table.column4.label")); //$NON-NLS-1$
	
		
		newBtn = toolkit.createButton(commandIdSectionComposite, "", SWT.FLAT);//new Button(commandIdSectionComposite, SWT.FLAT);
		final FormData newBtnFormData = new FormData();
		newBtnFormData.right = new FormAttachment(0, 580);
		newBtnFormData.top = new FormAttachment(commandIDTable, 0, SWT.TOP );
		newBtnFormData.left = new FormAttachment(commandIDTable, 5, SWT.RIGHT );
		newBtn.setLayoutData(newBtnFormData);
		toolkit.adapt(newBtn, true, true);
		newBtn.setText(Messages
				.getString("CommandsPage.commandIdSection.buttons.new.label")); //$NON-NLS-1$
		
		newBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				modelCommands.addCommandID(null);
			}
		});
		
		goToCodeBtn = toolkit.createButton(commandIdSectionComposite, "", SWT.FLAT);//new Button(commandIdSectionComposite, SWT.FLAT);
		final FormData goToCodeBtnFromData = new FormData();
		goToCodeBtnFromData.right = new FormAttachment(newBtn, 0, SWT.RIGHT);
		goToCodeBtnFromData.top = new FormAttachment(newBtn, 5, SWT.BOTTOM );
		goToCodeBtnFromData.left = new FormAttachment(newBtn, 0, SWT.LEFT);
		goToCodeBtn.setLayoutData(goToCodeBtnFromData);
		toolkit.adapt(goToCodeBtn, true, true);
		goToCodeBtn.setEnabled(false);
		goToCodeBtn.setText(Messages
				.getString("CommandsPage.commandIdSection.buttons.code.label")); //$NON-NLS-1$
		
		goToCodeBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				goToCodeAction();
			}
		});		
		
		removeCommandIdBtn = toolkit.createButton(commandIdSectionComposite, "", SWT.FLAT);//new Button(commandIdSectionComposite, SWT.FLAT);
		final FormData removeCommandIdFormData = new FormData();
		removeCommandIdFormData.right = new FormAttachment(goToCodeBtn, 0, SWT.RIGHT);
		removeCommandIdFormData.top = new FormAttachment(goToCodeBtn, 5, SWT.BOTTOM );
		removeCommandIdFormData.left = new FormAttachment(goToCodeBtn, 0, SWT.LEFT );
		removeCommandIdBtn.setLayoutData(removeCommandIdFormData);
		toolkit.adapt(removeCommandIdBtn, true, true);
		removeCommandIdBtn.setEnabled(false);
		removeCommandIdBtn.setText(Messages
				.getString("CommandsPage.commandIdSection.buttons.remove.label")); //$NON-NLS-1$
		
		removeCommandIdBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {				
				deleteCommandIdGUI();
			}
		});
		initializeCommandIdSection();
		initCommandIdEditorListener();		
		initializeEditingSection();
		if (modelCommands.getCommandListElements().size() > 0){
			commandsTreeViewer.setSelection(new StructuredSelection(modelCommands.getCommandListElements().get(0)),true);
		}
		else{			
			editingSection.setVisible(false);			
		}
		
	}

	/**
	 * Initializes the listener for the columns in the Command ids table.
	 */	
	private void initCommandIdEditorListener() {
		commandIDTable.addListener(SWT.MouseDown, new Listener() {
			
			public void handleEvent(Event event) {
				final TableEditor editorCell = new TableEditor (commandIDTable);
				editorCell.horizontalAlignment = SWT.LEFT;
				editorCell.grabHorizontal = true;
				Rectangle clientArea = commandIDTable.getClientArea();
				Point pt = new Point(event.x, event.y);
				int index = commandIDTable.getTopIndex();
				while (index < commandIDTable.getItemCount()) {
					boolean visible = false;
					final TableItem item = commandIDTable.getItem(index);
					for (int i = 0; i < commandIDTable.getColumnCount(); i++) {
						Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {
							final int column = i;
							if (column == COLUMN_COMMAND_ID_NAME) {
								editorColumnName(editorCell, item);
								i = commandIDTable.getColumnCount();
								index = commandIDTable.getItemCount();
							}
							//second editor
							else if (column == COLUMN_COMMAND_ID_COMMANDID) { 
								editorColumnCommandId(editorCell, item);
								i = commandIDTable.getColumnCount();
								index = commandIDTable.getItemCount();
							}
							else  if (column == COLUMN_COMMAND_ID_FUNCTION) {
								editorColumnEventHandler(editorCell, item);
								i = commandIDTable.getColumnCount();
								index = commandIDTable.getItemCount();
							}							
						}
						if (!visible && rect.intersects(clientArea)) {
							visible = true;
						}
					}
					if (!visible)
						return;
					index++;
				}
			}
		});
	}

	/**
	 * Launches the property Editor Dialog
	 * @param flagID - cpfFlags ID or stateFlags ID
	 */
	private void propertyEditorDialog(String flagID){
		if (selected != null){
			IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
					((CommandElementBase)selected).getName());
			propertyEditor = new PropertyEditor(editor.getEditorSite().getShell(),
						flagID, instance, modelCommands);
			int dialogResult = propertyEditor.open();
			if (dialogResult == PropertyEditor.OK) {
				//The refresh is done inside that class
			}
		}
	}
	/**
	 *  Create the drag/drop source on the tree.
	 *  This code will allow Drag and Drop operations
	 *  
	 */
	private void createDragDropSource(){		
	 	int ops = DND.DROP_COPY | DND.DROP_MOVE;
	    Transfer[] transfers = new Transfer[] { CommandElementTransfer.getInstance() };
	    dragListener =  new CommandElementDragListener(commandsTreeViewer);
	    dropAdapter = new CommandTreeDropAdapter(
  		  		commandsTreeViewer, modelCommands, editor);
	    commandsTreeViewer.addDragSupport(ops, transfers, dragListener);
	    transfers = new Transfer[] {CommandElementTransfer.getInstance()};
	    ops = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
	    commandsTreeViewer.addDropSupport(ops, transfers, dropAdapter);		
	
	}
	/**
	 * Initializes the tree with the command lists,the commands and the groups.
	 */
	private void initializeCommandListSection(){		
		modelCommands.setTreeViewer(commandsTreeViewer);
		modelCommands.setListener(commandListChildListener);//also sets the command list group
		modelCommands.setPropertyListeners(commandListPropertyListener,
											commandIDPropertyListener,
											commandPropertyListener,
											namedGroupPropertyListener,
											anonymousGroupPropertyListener);
		modelCommands.initData(duplicateBtn);		
		labelProvider = new CommandListLabelProvider();
		TreeCellModifier modifier = new TreeCellModifier(commandsTreeViewer, pageUtils, modelCommands,
				controlManager,
				editor);
		cellEditors = new CellEditor[2];
		cellEditors[0] = null;
	    Vector<String> currentIDs = pageUtils.getCurrentObjectsFromModel(UI_COMMAND_ID);
	    cellEditors[1] = new ComboBoxCellEditor(commandsTree,
	    		currentIDs.toArray(new String[currentIDs.size()]),
	    		SWT.NONE);	
	    commandsTreeViewer.setCellEditors(cellEditors);
	    commandsTreeViewer.setColumnProperties(new String[] { "element", "commandid" }); //$NON-NLS-1$ //$NON-NLS-2$
	    commandsTreeViewer.setLabelProvider(labelProvider);
		commandsTreeViewer.setContentProvider(
	  	         new CommandElementTreeContentProvider());
		commandsTreeViewer.setCellModifier(modifier);		
		commandsTreeViewer.setInput(modelCommands.getCommandListElements());
		commandsTreeViewer.expandAll();		
		createDragDropSource();		
	}
	/**
	 * Initializes the editing section
	 */
	private void initializeEditingSection(){
		//nameTxt.setText("");
		txtTxt.setText("");
		shortTxtTxt.setText("");
		priorityTxt.setText("0");
		initIDCombo();
		initTypeCombo();
		initNamedGrpLnkTxt();
		initAnonymousGrpCombo();
		initNamedGrpCombo();
	}
	/**
	 * Initializes the table of command ids.
	 */
	private void initializeCommandIdSection(){
		String commandEvent;
		IComponentInstance commandIdInstance;
		Vector<String> currentIDs = pageUtils.getCurrentObjectsFromModel(UI_COMMAND_ID);
		for (String idName : currentIDs) {
			commandIdInstance = ModelUtils.lookupReference(editor.getDataModel(),idName);
			IEventBinding eventBinding = commandIdInstance.findEventBinding(COMMAND_ID_EVENT);
			commandEvent = ""; //$NON-NLS-1$
			if (eventBinding != null){
				commandEvent = eventBinding.getHandlerName();
			}
			addCommandIdToGUI(idName,commandEvent);			
		}
		if (commandIDTable.getSelectionCount() == 0){
			removeCommandIdBtn.setEnabled(false);
			goToCodeBtn.setEnabled(false);
		}		
	}
	/**
	 * Displays the fields editable for the anonymous group instance
	 * @param element
	 */
	protected void displayPropertiesAnonymousGroup(AnonymousGroup element) {
		displayPropertiesGeneric(element.getRealName());		
	}
	private void displayPropertiesGeneric(String text) {
		nameTxt.setText(text);
		idCombo.setVisible(false);
		txtTxt.setVisible(false);
		shortTxtTxt.setVisible(false);
		priorityTxt.setVisible(false);
		typeCombo.setVisible(false);
		anonymousGrpCombo.setVisible(false);
		namedGrpCombo.setVisible(false);		
		stateEditBtn.setVisible(false);
		cpfEditBtn.setVisible(false);
	}

	/**
	 * Displays the fields editable for the named group or the command instance
	 * @param element
	 * @param flag
	 */
	protected void displayPropertiesCommandOrNamed(CommandElementBase element, boolean flag) {
		IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
											element.getName());
		IPropertySource properties = ModelUtils.getPropertySource(instance.getEObject());
		IComponentInstance parent = ModelUtils.getComponentInstance(
				instance.getParent());
				
		nameTxt.setText(element.getName());
		if (flag){
			idCombo.setVisible(true);
			String value = (String)properties.getPropertyValue(COMMAND_ID_PROPERTY_COMMAND_ID);
			if (value.equals("")){
				idCombo.deselectAll();		
			}
			else{
				idCombo.select(idCombo.indexOf(value));
			}
		}
		else{
			idCombo.setVisible(false);
		}
		txtTxt.setVisible(true);
		txtTxt.setText((String)properties.getPropertyValue(COMMAND_PROPERTY_TEXT));
		shortTxtTxt.setVisible(true);
		shortTxtTxt.setText((String)properties.getPropertyValue(COMMAND_PROPERTY_SHORTTEXT));
		priorityTxt.setVisible(true);
		priorityTxt.setText(((Integer)properties.getPropertyValue(COMMAND_PROPERTY_PRIORITY)).toString());
		typeCombo.setVisible(true);
		typeCombo.select(typeCombo.indexOf(
						typeValueDisplayHashTable.get((String)properties.getPropertyValue(COMMAND_PROPERTY_TYPE))));
		typeCombo.setEnabled(true);
		anonymousGrpCombo.setVisible(true);
		initNamedGrpCombo();
		initAnonymousGrpCombo();
		removeGrpCombo(parent,anonymousGrpCombo);
		anonymousGrpCombo.select(anonymousGrpCombo.indexOf((String)properties.
				getPropertyValue(COMMAND_PROPERTY_ANONYMOUS_GROUP)));
        initNamedGrpCombo(); // ensure this is initialized without the current selection
		namedGrpCombo.setVisible(true);
		removeGrpCombo(parent,namedGrpCombo);
		namedGrpCombo.select(namedGrpCombo.indexOf((String)properties.
								getPropertyValue(COMMAND_PROPERTY_NAMED_GROUP)));
		stateEditBtn.setVisible(true);
		cpfEditBtn.setVisible(true);
		
	}

	/**
	 * Displays the fields editable for the command list instance
	 * @param element
	 */
	protected void displayPropertiesCommandList(CommandList element) {
		displayPropertiesGeneric(element.getName());
	}
	
	/**
	 * Calls the add command list method from the local model.
	 */
	private void addCommandListElement() {
		modelCommands.addCommandList(commandListConfigurationGroup);        		
	}
	
	/**
	 * Calls the method of the local model to add a command
	 */

	private void addCommandElement() {
		Object domain = getSelectionTree();
		String newName = "";
		String parent = "";
		if (domain != null) {			
			if (domain instanceof CommandList){
				parent = ((CommandList) domain).getName();
			}
			else{
				parent = (((CommandElementBase)domain).getParent()).getName();
			}
			newName = modelCommands.addCommand(domain);			
			if (!newName.equals("")) {
				commandsTreeViewer.refresh();
				CommandList listParent = modelCommands
						.getCommandListByName(parent);
				CommandModel newCommand = modelCommands.getCommandByNameList(
						listParent, newName);
				commandsTreeViewer.setSelection(new StructuredSelection(
						newCommand), true);
			}
		}	
	}

	/**
	 * Calls the method of the local model to add a named group
	 */
	private void addNamedGroupElement(){
		Object domain = getSelectionTree();
		if (domain != null) {			
			modelCommands.addNamedGroup(domain); 			
			commandsTreeViewer.refresh();
		}		
	}
	
	/**
	 * Calls the method of the local model to add an anonymous group
	 */
	private void addAnonymousGroupElement(){
		Object domain = getSelectionTree();
		if (domain != null) {			
			modelCommands.addAnonymousGroup(domain);			
			commandsTreeViewer.refresh();
		}		
	}
	
	/**
	 * Adds a command id to the table
	 * @param nameId - Name of the command ID 
	 * @param functionName - Name of the event
	 */
	private void addCommandIdToGUI(String nameId,String functionName){
		TableItem itemNew = new TableItem(commandIDTable, SWT.NONE);
		itemNew.setText(COLUMN_COMMAND_ID_NAME,nameId); //name
		itemNew.setText(COLUMN_COMMAND_ID_FUNCTION,functionName);//function
		IComponentInstance commandIdInstance = ModelUtils.lookupReference(editor.getDataModel(),nameId);
		String commandId = ModelUtils.getPropertySource(commandIdInstance.getEObject()).
													getPropertyValue(COMMAND_ID_PROPERTY_COMMAND_ID).toString();
		String systemId = ModelUtils.getPropertySource(commandIdInstance.getEObject()).
													getPropertyValue(COMMAND_ID_PROPERTY_SYSTEM_ID).toString();
		if (!systemId.equals("")){
			commandId = systemId;
		}
		itemNew.setText(COLUMN_COMMAND_ID_COMMANDID,commandId);		
				
		if(commandId.equals("") || commandId == null ){
			commandId = ModelUtils.getPropertySource(commandIdInstance.getEObject()).
													getPropertyValue(COMMAND_ID_PROPERTY_SYSTEM_ID).toString();			
			itemNew.setText(COLUMN_COMMAND_ID_ENUMERATOR,commandId);//enumerator
			itemNew.setText(COLUMN_COMMAND_ID_COMMANDID,commandId);	
		}else{
			itemNew.setText(COLUMN_COMMAND_ID_ENUMERATOR,ViewEditorUtils.generateNameEnumerator(uiDesignName,commandId));//enumerator			
		}
		if (functionName == null){
			functionName = ""; //$NON-NLS-1$
		}

		EObject commandObject = ModelUtils.getEObject(commandIdInstance);
		ModelUtils.getComponentInstance(commandObject).addPropertyListener(commandIDPropertyListener);
		ModelUtils.getComponentInstance(commandObject).addEventBindingListener(commandIdEventListener);
		removeCommandIdBtn.setEnabled(true);
		goToCodeBtn.setEnabled(true);
	}

	/**
	 * Remove the selected element from the real model
	 */
	private void removeElement(){
		Object select = getSelectionTree();
		String component = "";
		String name = "";
		if (select != null) {
			if (select instanceof CommandList){
				component = Messages.getString("CommandsPage.commandListSection.element.list");
				name = ((CommandList) select).getName();
			}
			else if (select instanceof CommandModel){
				component = Messages.getString("CommandsPage.commandListSection.element.command");
				name = ((CommandModel) select).getName();
			}
			else if (select instanceof NamedGroup){
				component = Messages.getString("CommandsPage.commandListSection.element.named");
				name = ((NamedGroup) select).getName();
			}
			else if (select instanceof AnonymousGroup){
				component = Messages.getString("CommandsPage.commandListSection.element.anonymous");
				name = ((AnonymousGroup) select).getRealName();
			}
			MessageBox confirm = new MessageBox(Display.getCurrent().getActiveShell(),
					SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		    String text = Messages.getString("CommandsPage.commandListSection.actions.remove.confirm");
		    String textFormat = MessageFormat.format(text, name);
		    confirm.setMessage(textFormat);
		    String title = Messages.getString("CommandsPage.commandListSection.actions.remove.title");
		    String formatTitle = MessageFormat.format(title, component);
			confirm.setText(formatTitle);
			if (confirm.open() == SWT.YES) {
				modelCommands.deleteElements(select);			
				commandsTreeViewer.refresh();
			}			
		}		
	}
	/**
	 * Delete the selected item from the table
	 * This is in the case that the user select an item from the table.
	 */
	private void deleteCommandIdGUI(){
		TableItem[] items = commandIDTable.getSelection();
		TableItem item;
		String name = ""; //$NON-NLS-1$
		if (items.length != 0){
			item = items[0];
			name = item.getText(0);
			MessageBox confirm = new MessageBox(Display.getCurrent().getActiveShell(),
					SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		    String text = Messages.getString(
		    		"CommandsPage.commandIdSection.actions.remove.confirm"); //$NON-NLS-1$
		    String textFormat = MessageFormat.format(text, name);
		    confirm.setMessage(textFormat);
			confirm.setText(Messages.getString(
					"CommandsPage.commandIdSection.actions.remove.title")); //$NON-NLS-1$
			if (confirm.open() == SWT.YES) {
				modelCommands.deleteCommandId(ModelUtils.lookupReference(
									editor.getDataModel(),name));
			}			
		}
	}
	
	/**
	 * Change the specified property using a command
	 * @param inputObject
	 * @param propertySource
	 * @param propertyname
	 * @param newValue
	 */
	private void createAndExecuteSetPropertyCommand(Object inputObject, 
							IPropertySource propertySource, String propertyName, Object newValue){
		FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), inputObject);
		CompoundCommand cc = new CompoundCommand();
		SetPropertyCommand propertyCommand = new SetPropertyCommand(propertySource,
			propertyName, newValue);
		cc.append(propertyCommand);
		String text = Messages.getString("CommandsPage.changeProperties");
	    String textFormat = MessageFormat.format(text, propertyName);
	    cc.setLabel(textFormat);
		EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
		editor.getCommandStack().execute(wrapper);
	}
	
	/**
	 * Refreshes the command's properties that are displayed in the editor
	 * @param commandPropertyName - property ID 
	 * @param text - new value
	 */
	public void refreshPropertiesCommand(String commandPropertyName, String text) {
		if (selected == null){
			return;
		}
		String name = ""; //$NON-NLS-1$
		if (selected instanceof CommandList){
			name = ((CommandList) selected).getName();
		}
		else if (selected instanceof AnonymousGroup){
			name = ((AnonymousGroup)selected).getRealName();
		}
		else {
			name = ((CommandElementBase)selected).getName();
		}
		IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
								name);
		IPropertySource properties = ModelUtils.getPropertySource(instance.getEObject());
		IPropertyDescriptor[] descriptors = properties.getPropertyDescriptors();
		boolean flag = false;
		for (IPropertyDescriptor descriptor : descriptors){
			if (descriptor.getId().equals(commandPropertyName)){
				flag = true;
				break;
			}
		}
		if (flag) {
			if (commandPropertyName.equals(COMMAND_PROPERTY_PRIORITY)) {
				Integer old = (Integer) properties
						.getPropertyValue(commandPropertyName);
				if (!old.toString().equals(text)) {
					String key = "Name:" + name + " " + "Property:"
							+ commandPropertyName;
					createAndExecuteSetPropertyCommand(key, properties,
							commandPropertyName, new Integer(text));
				}
			}//all the others are Strings, the property "type" uses an enum!!		
			else {
				String old = (String) properties
						.getPropertyValue(commandPropertyName);
				if (!old.equals(text)) {
					createAndExecuteSetPropertyCommand(editingSection,
							properties, commandPropertyName, text);
				}
			}
		}		
	}

	/**
	 * Refreshes the changes to the instance in the GUI to the model.
	 * Only the property "name" can be changed, the event handler is 
	 * managed in the EventBindigListener because when the user changes
	 * the name of the Event handler is detected as a remove event.
	 * when the change of  the name is made from the GUI, the instance is 
	 * remove from the propertyChangeListener and then added again, to avoid
	 * problems with the editor of the cell.
	 * @param nameInstance
	 * @param propertyId
	 * @param newValue
	 */
	private void refreshChangesCommandIDToModel(String nameInstance, String propertyId, String newValue){
		IComponentInstance commandIdInstance;
		commandIdInstance = ModelUtils.lookupReference(editor.getDataModel(),nameInstance);
		EObject commandIdObject = commandIdInstance.getEObject();
		//name property
		IPropertySource properties = ModelUtils.getPropertySource(commandIdObject);
		String key = "Name:"+newValue+" Property:"+COMMANDID_PROPERTY_NAME;
		createAndExecuteSetPropertyCommand(key, properties, 
				propertyId, newValue);		
	}
	
	/**
	 *	Refreshes the changes made to the model into the GUI. This can be only
	 *	the property "name" and the event handler. The last one is detected in the 
	 *  EventBindingListener. When the event handler is added or changed (removed)
	 *  this change is reflected on the GUI. 
	 * @param instance
	 * @param propertyId
	 */
	private void refreshChangesCommandIDToGUI(EObject instance, String propertyId){
		IPropertySource properties = ModelUtils.getPropertySource(instance);
		if (propertyId.equals(COMMAND_ID_PROPERTY_NAME)){
			resetCommandIdGUI();
			initIDCombo();			
			return;
		}
		String name = (String) properties.getPropertyValue(COMMAND_ID_PROPERTY_NAME);
		int index = ViewEditorUtils.getIndexTableByValue(commandIDTable,name);		
		if (propertyId.equals(COMMAND_ID_EVENT)){ //event handler
			IEventBinding eventBinding = ModelUtils.getComponentInstance(instance).
										 findEventBinding(COMMAND_ID_EVENT);
			String commandEvent = ""; //when the Event handler has been removed //$NON-NLS-1$
			if (eventBinding != null){				
				commandEvent = eventBinding.getHandlerName();
			}
			commandIDTable.getItem(index).setText(COLUMN_COMMAND_ID_FUNCTION,commandEvent);			
		} else if (propertyId.equals(COMMAND_ID_PROPERTY_SYSTEM_ID)){
			String commandId = (String)properties.getPropertyValue(COMMAND_ID_PROPERTY_SYSTEM_ID);
			if (commandId.equals("")){
				commandId = (String)properties.getPropertyValue(COMMAND_ID_PROPERTY_COMMAND_ID);
			}
			commandIDTable.getItem(index).setText(COLUMN_COMMAND_ID_COMMANDID,
					commandId);
		} else if (propertyId.equals(COMMAND_ID_PROPERTY_COMMAND_ID)){
			String commandId = (String)properties.getPropertyValue(COMMAND_ID_PROPERTY_COMMAND_ID);
			if (commandId.equals("")){
				commandId = (String)properties.getPropertyValue(COMMAND_ID_PROPERTY_SYSTEM_ID);
			}
			if ((index < commandIDTable.getItemCount()) ) {
				commandIDTable.getItem(index).setText(
						COLUMN_COMMAND_ID_COMMANDID, commandId);
			}
		}
	}
	/**
	 * Reset the values of the commandIDTable, dispose all the editors of the first 
	 * column.
	 */
	private void resetCommandIdGUI(){
			for(Control cellEditor : tableEditorList){
				cellEditor.dispose();
			}			
			commandIDTable.removeAll();			
			for (Text editText : textEditorsList){
				editText.dispose();
			}			
			initializeCommandIdSection();
	}
	
	/**
	 * Get the name and the event handler's event name of the selected item.
	 */
	private void goToCodeAction(){
		TableItem[] items = commandIDTable.getSelection();
		TableItem itemSelect;
		if (items.length > 0){
			itemSelect = items[0];
			String event = itemSelect.getText(COLUMN_COMMAND_ID_FUNCTION);
			String name = itemSelect.getText(COLUMN_COMMAND_ID_NAME);
			IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(), name);
			goToCodeCommandId(instance, event);
		}
	}
	private String generateDefaultNameEventHandler(IComponentInstance instance){
		String event = "";
		IComponentEventDescriptorProvider eventProvider = ModelUtils.
		getComponentEventDescriptorProvider(
				ModelUtils.getEObject(instance));
		IEventDescriptor descriptor = eventProvider.findEventDescriptor(COMMAND_ID_EVENT);
		HandlerMethodInformation handlerInfo = descriptor.
				generateHandlerMethodInfo(instance, null);
		event = handlerInfo.getDisplayText();
		return event;
	}
	
	/**
	 * This method generates the default eventHandler or goes to the existing one.
	 * Add the event binding to the instance and then the listener changes the GUI. 
	 * @param name
	 * @param event
	 */
	private void goToCodeCommandId(IComponentInstance instance, String  event){
		IComponentEventDescriptorProvider eventProvider = ModelUtils.
											getComponentEventDescriptorProvider(
													ModelUtils.getEObject(instance));
		IEventDescriptor descriptor = eventProvider.findEventDescriptor(COMMAND_ID_EVENT);
		boolean exists = false;
		boolean doSave = true;
		if (event.equals("")){
			event = generateDefaultNameEventHandler(instance);
		} //else adds the user's
		if (instance.findEventBinding(COMMAND_ID_EVENT) != null){//It already has an event handler
			//Remove old one
			if (!instance.findEventBinding(COMMAND_ID_EVENT).getHandlerName().equals
						(event)){
				removeEventBinding(instance, COMMAND_ID_EVENT);				
			}
			else{
				exists = true;
			}
		} // It doesn't have an event handler			
		Command command = editor.getDataModel().
		createAddEventBindingCommand(instance.getEObject(),
				descriptor, event); 
		Check.checkState(command.canExecute());
		command.execute();
		if (!exists) { //save model!!				
			doSave = EventCommands.userConfirmationToSaveModelDialog(
					editor.getEditorSite().getShell(), editor.getDataModel());
			if ( !ViewEditorUtils.saveDataModel(editor)) {
				removeEventBinding(instance, COMMAND_ID_EVENT);
				return;
			}
		}
		if ((instance.findEventBinding(COMMAND_ID_EVENT) != null) && doSave){
			//in this case the code does exists, because has just been created.
			descriptor.gotoHandlerCode(instance.findEventBinding(COMMAND_ID_EVENT), true);			
		}	
	}

	/**
	 * This code was taken from the EventCommands class. It has the validations in case the code
	 * doesn't exist.
	 * @param binding
	 * @param isNewBinding
	 */
	private void navigateToHandlerCode(IEventBinding binding, boolean isNewBinding) {
		IEventDescriptor eventDescriptor = binding.getEventDescriptor();
		IStatus status = eventDescriptor.gotoHandlerCode(binding, isNewBinding);
		if (status != null) {
			if (!isNewBinding) {
				// Hmm, an error.  It could be the data model was not saved.
				// It may just be a problem in the component's sourcegen, hence
				// the fallthrough to the descriptive error later.
				MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						Messages.getString("CommandsPage.Error"), null, //$NON-NLS-1$
						Messages.getString("CommandsPage.NoEventHandlerFound"), //$NON-NLS-1$
						MessageDialog.QUESTION,
						new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL }, //$NON-NLS-1$ //$NON-NLS-2$
						0);
				int result = dialog.open();
				if (result == MessageDialog.OK) {
					if (ViewEditorUtils.saveDataModel(editor)) {
						status = eventDescriptor.gotoHandlerCode(binding, isNewBinding);	
					}
				}
			}
			if (status != null) {
				Logging.log(UIDesignerPlugin.getDefault(), status);
				Logging.showErrorDialog(Messages.getString("CommandsPage.CouldNotNavigate"), null, status); //$NON-NLS-1$
			}
		}
		
	}
	/**
	 * Initialize the command id combo with the elements from the table
	 */
	private void initIDCombo(){
		TableItem[] items = commandIDTable.getItems();		
		idCombo.removeAll();
		for(TableItem item : items){
			idCombo.add(item.getText(0));
		}	
	}
	
	
	/**
	 * Initializes the type combo with the values of the enumerator
	 */
	private void initTypeCombo(){
		String[]  types = readEnumCommands(COMMAND_TYPES);
		for (int i = 0; i < types.length; i++){
			typeCombo.add(types[i]);			
		}
	}
	
	/**
	 * Initializes the Group link field.
	 */
	private void initNamedGrpLnkTxt(){
		namedGrpLinkTxt.setText("Link"); //$NON-NLS-1$
		namedGrpLinkTxt.setEnabled(false);
	}
	
	/**
	 * Initializes the anonymous group combo
	 */
	private void initAnonymousGrpCombo(){		
		List<String> groups = pageUtils.getCurrentObjectsFromModel(UI_ANONYMOUS_GROUP);
		anonymousGrpCombo.removeAll();
		anonymousGrpCombo.add(""); //$NON-NLS-1$
		for (String name : groups){
			anonymousGrpCombo.add(name);
		}		
	}
	
	
	private void addNamedGroupChildNames(NamedGroup namedGroup, List<String> names) {
		// add self name
		names.add(namedGroup.getName());
		
		// add children
		CommandElementTreeContentProvider provider = 
			(CommandElementTreeContentProvider)	commandsTreeViewer.getContentProvider();
		Object[] children = provider.getChildrenNamedGroup(namedGroup);
		for (Object object : children) {
			if (object instanceof NamedGroup) {
				addNamedGroupChildNames((NamedGroup) object, names);
			}
		}
	}
	
	/**
	 * Initializes the named group combo
	 */
	private void initNamedGrpCombo(){
		List<String> excludedNames = new ArrayList<String>();
		if (selected instanceof NamedGroup) {
			addNamedGroupChildNames((NamedGroup) selected, excludedNames);
		}
		List<String> groups = pageUtils.getCurrentObjectsFromModel(UI_NAMED_GROUP);
		namedGrpCombo.removeAll();
		namedGrpCombo.add(""); //$NON-NLS-1$
		for (String name : groups){
			if (!excludedNames.contains(name))
				namedGrpCombo.add(name);
		}
	}
	
	/**
	 * Initializes the cell editor of the TreeViewer
	 */
	private void initComboCellEditor() {
		Vector<String> currentIDs = pageUtils.getCurrentObjectsFromModel(UI_COMMAND_ID);
		((ComboBoxCellEditor)cellEditors[1]).setItems(
				currentIDs.toArray(new String[currentIDs.size()]));
	}
	
	/**
	 * Initializes the Command list group children listener
	 */
	private void initCommandListGroupChildListener(){
		commandListGroupChildListener = new IComponentInstanceChildListener() {
			public void childAdded(EObject parent, EObject child) {
				if (commandIDTable == null){
					return;
				}
				IComponentInstance instance = ModelUtils.getComponentInstance(child);
				String type = instance.getComponentId();
				if (type.equals(UI_COMMAND_ID)){
					ModelUtils.getComponentInstance(child).addPropertyListener(
							commandIDPropertyListener);
					IEventBinding eventBinding = ModelUtils.getComponentInstance(child).
												findEventBinding(COMMAND_ID_EVENT);
					String commandEvent = ""; //$NON-NLS-1$
					if (eventBinding != null){
						commandEvent = eventBinding.getHandlerName();
					}
					String name = pageUtils.getPropertyValueByInstance(child, COMMAND_ID_PROPERTY_NAME);
					modelCommands.addComandIdModel(name,commandEvent,child);
					addCommandIdToGUI(name, commandEvent);
					initComboCellEditor();
					initIDCombo();
					commandIDTable.select(ViewEditorUtils.getIndexTableByValue(
							commandIDTable, name ));					
					return;
				}
				else if(type.equals(UI_COMMAND_LIST)){
					modelCommands.addCommandListModel(child);
					ModelUtils.getComponentInstance(child).addChildListener(commandListChildListener);
					ModelUtils.getComponentInstance(child).addPropertyListener(
									commandListPropertyListener);
					CommandList list = modelCommands.getCommandListByName(ModelUtils.getComponentInstance(
																child).getName());
					commandsTreeViewer.setSelection(new StructuredSelection(list), true);
				}				
			}

			public void childRemoved(EObject parent, EObject child) {
				if (commandIDTable == null){
					return;
				}
				IComponentInstance instance = ModelUtils.getComponentInstance(child);
				String type = instance.getComponentId();
				if ( type.equals(UI_COMMAND_ID) ){
					modelCommands.deleteCommandIdModel(instance);
					resetCommandIdGUI();
					initComboCellEditor();
					initIDCombo();
					instance.removePropertyListener(commandIDPropertyListener);
				}
				else if ( type.equals(UI_COMMAND) ){
					modelCommands.deleteCommandModel(parent, instance);					
				}
				else if(type.equals(UI_COMMAND_LIST)){					
					modelCommands.deleteCommandListModel(parent, instance);
					instance.removePropertyListener(commandListPropertyListener);
				} 
				
			}			
			public void childrenReordered(EObject parent) {
				if (commandIDTable == null){
					return;
				}
				resetCommandIdGUI();
				initIDCombo();
				initComboCellEditor();
			}
		};
	}
	
	/**
	 * Initializes the Command list children listener
	 */
	private void initCommandListChildListener(){
		commandListChildListener = new IComponentInstanceChildListener() {
			public void childAdded(EObject parent, EObject child) {
				if (commandIDTable == null){
					return;
				}
				IComponentInstance instance = ModelUtils.getComponentInstance(child);
				String type = instance.getComponentId();
				if (type.equals(UI_COMMAND)){
					modelCommands.addCommandModel(child);
					ModelUtils.getComponentInstance(child).addPropertyListener(
											commandPropertyListener);
					commandsTreeViewer.refresh(true);					
					String name = instance.getName();
					String nameParent = ModelUtils.getComponentInstance((instance.getParent())).getName();
					CommandList list = modelCommands.getCommandListByName(nameParent);
					CommandModel command = modelCommands.getCommandByNameList(list, name);
					commandsTreeViewer.setSelection(new StructuredSelection(command), true);
					return;
				}	
				else if (type.equals(UI_NAMED_GROUP)){
					refreshCombos(UI_NAMED_GROUP, COMMAND_PROPERTY_NAME);
					modelCommands.addNamedGroupModel(parent, instance);
					ModelUtils.getComponentInstance(child).addPropertyListener(
								namedGroupPropertyListener);
					commandsTreeViewer.refresh();
					String name = instance.getName();
					String nameParent = ModelUtils.getComponentInstance((instance.getParent())).getName();
					CommandList list = modelCommands.getCommandListByName(nameParent);
					NamedGroup group = (NamedGroup) modelCommands.getGroupByName(list, name, 0);
					commandsTreeViewer.setSelection(new StructuredSelection(group), true);
					return;
				}
				else if (type.equals(UI_ANONYMOUS_GROUP)){
					refreshCombos(UI_ANONYMOUS_GROUP, COMMAND_PROPERTY_NAME);
					modelCommands.addAnonymousGroupModel(parent, instance);
					ModelUtils.getComponentInstance(child).addPropertyListener(
							anonymousGroupPropertyListener);
					commandsTreeViewer.refresh();
					String name = instance.getName();
					String nameParent = ModelUtils.getComponentInstance((instance.getParent())).getName();
					CommandList list = modelCommands.getCommandListByName(nameParent);
					AnonymousGroup group = (AnonymousGroup) modelCommands.getGroupByName(list, name, 1);
					commandsTreeViewer.setSelection(new StructuredSelection(group), true);
					return;
				}			
			}

			public void childRemoved(EObject parent, EObject child) {
				if (commandIDTable == null){
					return;
				}
				IComponentInstance instance = ModelUtils.getComponentInstance(child);
				String type = instance.getComponentId();
				if ( type.equals(UI_COMMAND) ){
					modelCommands.deleteCommandModel(parent, instance);
					instance.removePropertyListener(commandPropertyListener);
				}
				else if ((type.equals(UI_NAMED_GROUP)) || (type.equals(UI_ANONYMOUS_GROUP))){
					refreshCombos(type, COMMAND_PROPERTY_NAME);
					modelCommands.deleteGroupModel(parent, instance);
					if (type.equals(UI_NAMED_GROUP)){
						instance.removePropertyListener(namedGroupPropertyListener);
					}
					else{
						instance.removePropertyListener(anonymousGroupPropertyListener);
					}
					return;
				}
			}
			
			public void childrenReordered(EObject parent) {
				if (commandIDTable == null){
					return;
				}				
			}
		};
	}
	
	/**
	 * Initializes the change properties listener for all the elements
	 */
	private void initPropertiesListeners(){
		commandListPropertyListener = new IComponentInstancePropertyListener() {
			public void propertyChanged(EObject componentInstance, Object propertyId) {
				if (commandIDTable == null){
					return;
				}
				if ((propertyId.equals(COMMAND_PROPERTY_NAME))){
					modelCommands.refreshPropertyCommandList(componentInstance);
				}
				CommandList list = modelCommands.getCommandListByName(ModelUtils.
									getComponentInstance(componentInstance).getName());
				if (list != null){
					commandsTreeViewer.setSelection(new StructuredSelection(list), true);
				}
			}
		};
			
		commandPropertyListener = new IComponentInstancePropertyListener() {
			public void propertyChanged(EObject componentInstance, Object propertyId) {
				if (commandIDTable == null){
					return;
				}
				//In modelCommands only refreshes the properties involved with
				//the tree model (NamedGroup, AnonymousGroup, CommandId, Name)
				if ((propertyId.equals(COMMAND_PROPERTY_NAME)) ||
						(propertyId.equals(COMMAND_PROPERTY_NAMED_GROUP)) ||
						(propertyId.equals(COMMAND_PROPERTY_ANONYMOUS_GROUP)) ||
						(propertyId.equals(COMMAND_PROPERTY_COMMAND_ID))){
					modelCommands.refreshPropertiesCommand(componentInstance, propertyId);
					commandsTreeViewer.refresh();					
				}
				//Refreshes from properties view to editing section
				IComponentInstance commandInstance = ModelUtils.getComponentInstance(componentInstance);
				String nameInstance = commandInstance.getName();
				String nameParent = ModelUtils.getComponentInstance(
									commandInstance.getParent()).getName();
				CommandList list = modelCommands.getCommandListByName(nameParent);
				CommandModel command = modelCommands.getCommandByNameList(list, nameInstance);
				if (command != null){
					commandsTreeViewer.setSelection(new StructuredSelection(command), true);
				}				
			}
		};
			
		namedGroupPropertyListener = new IComponentInstancePropertyListener() {
			public void propertyChanged(EObject componentInstance, Object propertyId) {
				if (commandIDTable == null){
					return;
				}
				modelCommands.refreshPropertiesNamedGroup(componentInstance, propertyId);
				commandsTreeViewer.refresh();
				refreshCombos(UI_NAMED_GROUP,(String)propertyId);
				IComponentInstance commandInstance = ModelUtils
						.getComponentInstance(componentInstance);
				String nameInstance = commandInstance.getName();
				String nameParent = ModelUtils.getComponentInstance(
						commandInstance.getParent()).getName();
				CommandList list = modelCommands.getCommandListByName(nameParent);
				NamedGroup groupChanged = (NamedGroup) modelCommands.getGroupByName(list,
						nameInstance, 0);
				if (groupChanged != null){
					commandsTreeViewer.setSelection(new StructuredSelection(groupChanged), true);
				}				
			}
		};
			
		anonymousGroupPropertyListener = new IComponentInstancePropertyListener() {
			public void propertyChanged(EObject componentInstance, Object propertyId) {
				if (commandIDTable == null){
					return;
				}
				if (((String)propertyId).equals(COMMAND_PROPERTY_NAME)) {
					modelCommands.refreshPropertyAnonymousGroup(componentInstance, propertyId);
				}
				commandsTreeViewer.refresh();
				refreshCombos(UI_ANONYMOUS_GROUP, (String)propertyId);
				IComponentInstance commandInstance = ModelUtils
				.getComponentInstance(componentInstance);
				String nameInstance = commandInstance.getName();
				String nameParent = ModelUtils.getComponentInstance(
						commandInstance.getParent()).getName();
				CommandList list = modelCommands.getCommandListByName(nameParent);
				AnonymousGroup groupChanged = (AnonymousGroup) modelCommands.getGroupByName(list,
						nameInstance, 1);
				if (groupChanged != null){
					commandsTreeViewer.setSelection(new StructuredSelection(groupChanged), true);
				}				
			}
		};

		commandIDPropertyListener = new IComponentInstancePropertyListener() {
			public void propertyChanged(EObject componentInstance, Object propertyId) {
				if (commandIDTable == null){
					return;
				}		
				modelCommands.refreshPropertyCommandId(componentInstance, propertyId);
				refreshChangesCommandIDToGUI(componentInstance, (String) propertyId);
				initComboCellEditor();
				refreshCombos(UI_COMMAND_ID, (String)propertyId);											
			}
		};

		commandIdEventListener = new IEventBindingListener(){
			public void bindingAdded(EObject instance,
					IEventBinding eventBinding) {
				if (commandIDTable == null){
					return;
				}
				refreshChangesCommandIDToGUI(instance, COMMAND_ID_EVENT);			
			}

			public void bindingRemoved(EObject instance,
					IEventBinding eventBinding) {
				if (commandIDTable == null){
					return;
				}
				refreshChangesCommandIDToGUI(instance, COMMAND_ID_EVENT);		
			}

		};
	}
	/**
	 * Refreshes the corresponding combo with the new instances
	 * @param idComboSelect
	 * @param property
	 */
	private void refreshCombos(String idComboSelect, String property) {
		if (property.equals(COMMAND_PROPERTY_NAME) && (idComboSelect.equals(UI_NAMED_GROUP))){
			initNamedGrpCombo();			
			return;
		}
		else if (property.equals(COMMAND_PROPERTY_NAME) && (idComboSelect.equals(UI_ANONYMOUS_GROUP))){
			initAnonymousGrpCombo();
			return;
		}
		else if (property.equals(COMMAND_PROPERTY_NAME) && (idComboSelect.equals(UI_COMMAND_ID))){
			initIDCombo();			
		}
	}
	/**
	 * When an instance of a group is remove we need to refresh the corresponding
	 * combo.
	 * @param parent
	 * @param combo
	 */
	private void removeGrpCombo(IComponentInstance parent,Combo combo) {
		String[] items = combo.getItems();
		String thisParent = parent.getName();
		// i starts in 1 because the first item is ""
		for (int i = 1; i < items.length; i++){
			IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
							items[i]);
			if (instance != null){
				String nameParent = ModelUtils.getComponentInstance(
					instance.getParent()).getName();
				if (!thisParent.equals(nameParent)){
					combo.remove(items[i]);
				}
			}
			else{
				combo.remove(items[i]);
			}
		}		
	}
	/**
	 * Gets the object current selected
	 * @return Object from the domain
	 */
	private Object getSelectionTree(){
		Object domain = null;
		IStructuredSelection selection = (IStructuredSelection)commandsTreeViewer.getSelection();
		if (!selection.isEmpty()) {
			domain = selection.getFirstElement();
	   	}
	   	return domain;
	}
	
	
	/**
	 * Sets the grid data for the Text controls
	 * @param control
	 */
	private void setLayoutEditingSection(Text control) {
		final GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.widthHint = 80;
		control.setLayoutData(gridData);
	}
	
	/**
	 * Reads the values of the enumerator given and returns the display values.
	 * @param property
	 * @return
	 */	
	private String[] readEnumCommands(String property){
		IComponentSet componentSet = editor.getDataModel().getComponentSet();		
		Check.checkContract(componentSet != null);
		IComponent component = componentSet.lookupComponent(UI_COMMAND_ID);
		ITypeDescriptor descriptor = component.getComponentSet().lookupTypeDescriptor(property);
		EnumPropertyTypeDescriptor enumType = (EnumPropertyTypeDescriptor) descriptor;
		Collection enums  = enumType.getChoiceOfValues();		
		String[] ids = new String[enums.size()];
		int idx = 0;
		for (Iterator iter = enums.iterator(); iter.hasNext();) {
			Object element = (Object) iter.next();
			String display = enumType.toDisplayString(element);
			typeHashTable.put(display,element.toString());
			typeValueDisplayHashTable.put(element.toString(), display);
			ids[idx++] = display;
		}		
		return ids;
	}
	
	@Override
	/**
	 * Method that its call to get the focus when the undo/redo actions are executed
	 */
	public boolean selectReveal(Object object) {
		boolean result = false;
		if (object == commandsTreeViewer) {
			result = true;
			commandListSection.forceFocus();
		} else if (object instanceof String) {
			String key = (String)object;
			if (key.startsWith("CommandElement-"))
				return false;
			else{
				 result = true;
				 String auxName = key.substring(key.indexOf("Property:"), key.length()) ;
				 String[] nameProperty = auxName.split("Property:"); 
		
				 if (nameProperty[1].equals(COMMANDID_PROPERTY_NAME)){
					 String[] names = key.split(" ");
					 String[] nameToFind = names[0].split("Name:");
					 int index = ViewEditorUtils.getIndexTableByValue(commandIDTable, nameToFind[1]);
					 commandIDTable.select(index);					 
				 } else if (nameProperty[1].equals(COMMAND_PROPERTY_NAME)){
					 nameTxt.setFocus();
				 }
				 else if (nameProperty[1].equals(COMMAND_PROPERTY_TEXT)){
					 txtTxt.setFocus();
				 }
				 else if (nameProperty[1].equals(COMMAND_PROPERTY_SHORTTEXT)){
					 shortTxtTxt.setFocus();
				 }
				 else if (nameProperty[1].equals(COMMAND_PROPERTY_TYPE)){
					 typeCombo.setFocus();
				 }
				 else if (nameProperty[1].equals(COMMAND_PROPERTY_PRIORITY)){
					 priorityTxt.setFocus();
				 }
				 else if (nameProperty[1].equals(COMMAND_PROPERTY_COMMAND_ID)){
					 idCombo.setFocus();
				 }
				 else if (nameProperty[1].equals(COMMAND_PROPERTY_NAMED_GROUP)){
					 namedGrpCombo.setFocus();
				 }
				 else if (nameProperty[1].equals(COMMAND_PROPERTY_ANONYMOUS_GROUP)){
					 anonymousGrpCombo.setFocus();
				 }
				 else if (nameProperty[1].equals(COMMAND_PROPERTY_STATE_FLAGS)){
					 stateEditBtn.setFocus();
				 }
				 else if (nameProperty[1].equals(COMMAND_PROPERTY_CPF_FLAGS)){
					 cpfEditBtn.setFocus();
				 }
			}
		
	}
		return result;
	}

	
	/**
	 * Creates the editor for the column name in the Command IDs table, and creates the listener for the
	 * FocusOut event.
	 * @param editorCell - Active cell editor
	 * @param item	- Row selected	
	 */
	public void editorColumnName(final TableEditor editorCell,final TableItem item) {
		nameCommandID = new Text(commandIDTable, SWT.NONE);
		handlerCommandID = new SingleSettingTextHandlerCommands(nameCommandID, 
				new FormEditorEditingContext(editor.getFormEditor(), nameCommandID),
				new RegExInputValidatorCommands("^[(a-z)|(A-Z)][\\w]*$", false, Messages.getString("CommandsPage.name.validateEmpty")
						));
		handlerCommandID.setLabel(null);
		controlManager.add(handlerCommandID);
		
		Listener textListener = new Listener() {
			public void handleEvent(final Event e) {
				switch (e.type) {
				case SWT.FocusOut:
					setNameCommandId(editorCell, item, nameCommandID);
					controlManager.handleValidationSucceeded(nameCommandID);
					break;
				case SWT.Traverse:
					switch (e.detail) {
					case SWT.TRAVERSE_RETURN:
						setNameCommandId(editorCell, item, nameCommandID);
						break;						
						// FALL THROUGH
					case SWT.TRAVERSE_ESCAPE:
						nameCommandID.dispose();
						e.doit = false;
					}
					break;
				}
			}
		};
		nameCommandID.addListener(SWT.FocusOut, textListener);
		nameCommandID.addListener(SWT.Traverse, textListener);						
		if (editorCell.getEditor() != null){
			editorCell.setEditor(null);
		}
		editorCell.setEditor(nameCommandID, item, COLUMN_COMMAND_ID_NAME);
		tableEditorList.add(nameCommandID);
		nameCommandID.setText(item.getText(COLUMN_COMMAND_ID_NAME));
		nameCommandID.selectAll();
		nameCommandID.setFocus();
	}

	public void editorColumnEventHandler(final TableEditor editorCell,
			final TableItem item) {
		nameEvent = new Text(commandIDTable, SWT.NONE);
		handlerEvent = new SingleSettingTextHandlerCommands(nameEvent, 
				new FormEditorEditingContext(editor.getFormEditor(), nameEvent),
				new RegExInputValidatorCommands("^[(a-z)|(A-Z)][\\w]*$", true, Messages.getString("CommandsPage.name.validateEmpty")
						));
		handlerEvent.setLabel(null);
		controlManager.add(handlerEvent);
		
		
		Listener textListener2 = new Listener() {
			public void handleEvent(final Event e) {
				switch (e.type) {
				case SWT.MouseDoubleClick: 
					setEventHandler(editorCell, item, nameEvent, true);
					break;
				case SWT.FocusOut:					
					setEventHandler(editorCell, item, nameEvent, false);
					controlManager.handleValidationSucceeded(nameEvent);
					break;
				case SWT.Traverse:
					switch (e.detail) {
					case SWT.TRAVERSE_RETURN:
						setEventHandler(editorCell, item, nameEvent, true);
						break;
						//FALL THROUGH
					case SWT.TRAVERSE_ESCAPE:
						nameEvent.dispose();
						e.doit = false;
					}
					break;
				}
			}
		};
		nameEvent.addListener(SWT.FocusOut, textListener2);
		nameEvent.addListener(SWT.Traverse, textListener2);
		nameEvent.addListener(SWT.MouseDoubleClick, textListener2);		
		if (editorCell.getEditor() != null){
			editorCell.setEditor(null);
		}
		editorCell.setEditor(nameEvent, item, COLUMN_COMMAND_ID_FUNCTION);
		tableEditorList.add(nameEvent);
		if (item.getText(COLUMN_COMMAND_ID_FUNCTION).equals("")){
			//generate the default handler name
			IComponentInstance instance = ModelUtils.lookupReference(editor.getDataModel(),
												item.getText(COLUMN_COMMAND_ID_NAME));
			item.setText(COLUMN_COMMAND_ID_FUNCTION, generateDefaultNameEventHandler(instance));			
		}
		nameEvent.setText(item.getText(COLUMN_COMMAND_ID_FUNCTION));
		nameEvent.selectAll();
		nameEvent.setFocus();
	}

	public void editorColumnCommandId(final TableEditor editorCell,
			final TableItem item) {
		final CCombo combo = new CCombo(commandIDTable, SWT.NONE);
		Listener comboListener = new Listener() {
			public void handleEvent(final Event e) {
				switch (e.type) {
				case SWT.FocusOut:
					setSystemOrCommandId(editorCell, item, combo);
					break;
				case SWT.Traverse:
					switch (e.detail) {
					case SWT.TRAVERSE_RETURN:						
						// FALL THROUGH
						setSystemOrCommandId(editorCell, item, combo);
						break;
					case SWT.TRAVERSE_ESCAPE:
						combo.dispose();
						e.doit = false;
					}
					break;
				}
			}
		};
		combo.addListener(SWT.FocusOut, comboListener);
		combo.addListener(SWT.Traverse, comboListener);
		if (editorCell.getEditor() != null){
			editorCell.setEditor(null);
		}
				
		IComponentInstance commandIdInstance = ModelUtils.lookupReference(
				editor.getDataModel(),item.getText(0));
		String commandId = ModelUtils.getPropertySource(commandIdInstance.getEObject()).
											getPropertyValue(COMMAND_ID_PROPERTY_COMMAND_ID).toString();
		
		combo.add(commandId);
		if(commandId.equals("") || commandId == null ){
			commandId = ModelUtils.getPropertySource(commandIdInstance.getEObject()).
				getPropertyValue(COMMAND_ID_PROPERTY_SYSTEM_ID).toString();					
		}
		String[] systemIds = readEnumCommands(COMMAND_SYSTEM_IDS);
		for (String sId : systemIds) {
			combo.add(sId);
		}
		combo.select(combo.indexOf(commandId));
		editorCell.minimumWidth = combo.computeSize(commandIDTable
				.getColumn(COLUMN_COMMAND_ID_COMMANDID).getWidth(), SWT.DEFAULT).x;
		commandIDTable.getColumn(COLUMN_COMMAND_ID_COMMANDID).setWidth(editorCell.minimumWidth);
		
		editorCell.setEditor(combo, item, COLUMN_COMMAND_ID_COMMANDID);
		tableEditorList.add(combo);
		combo.setText(item.getText(COLUMN_COMMAND_ID_COMMANDID));
		combo.setFocus();
	}

	private void setNewCommandIdProperty(String commandIdSelected, String old) {
		boolean flag = false;
		IComponentInstance commandIdInstance = ModelUtils.lookupReference(
				editor.getDataModel(),old);
		IPropertySource properties = ModelUtils.getPropertySource(commandIdInstance.getEObject());
		String[] systemIds = readEnumCommands(COMMAND_SYSTEM_IDS);
		for (String sId : systemIds) {
			if(sId.equals(commandIdSelected)){
				flag = true;
				break;
			}
		}
		if (flag){ //system id
			createAndExecuteSetPropertyCommand(editingSection, 
					properties, COMMAND_ID_PROPERTY_SYSTEM_ID, commandIdSelected);			
			createAndExecuteSetPropertyCommand(editingSection, 
					properties, COMMAND_ID_PROPERTY_COMMAND_ID, "");			
		}
		else{ //user id
			createAndExecuteSetPropertyCommand(editingSection, 
					properties, COMMAND_ID_PROPERTY_COMMAND_ID, commandIdSelected);	
			createAndExecuteSetPropertyCommand(editingSection, 
					properties, COMMAND_ID_PROPERTY_SYSTEM_ID, "");			
		}		
	}
	private void removeEventBinding(IComponentInstance instance, String eventID){
		Command commandRemove = editor.getDataModel().
		createRemoveEventBindingCommand(
				instance.findEventBinding(eventID));
		Check.checkState(commandRemove.canExecute());
		commandRemove.execute();
	}
	private void setEventHandler(final TableEditor editorCell,
			final TableItem item, final Text text2, boolean enter) {
		Text aux = (Text)editorCell.getEditor();									
		String oldName = item.getText(COLUMN_COMMAND_ID_FUNCTION);
		String newName = aux.getText();
		IComponentInstance commandIdInstance = ModelUtils.lookupReference(
				editor.getDataModel(),editorCell.getItem().getText());
		boolean exist = (commandIdInstance.findEventBinding(COMMAND_ID_EVENT) != null);
		if (enter){ //Go to code
			if (newName.equals("")){
				if (exist ){
					removeEventBinding(commandIdInstance,COMMAND_ID_EVENT);					
				}
				item.setText(COLUMN_COMMAND_ID_FUNCTION, "");
			}
			else{
				if (newName.matches("^[(a-z)|(A-Z)][\\w]*$")){
					goToCodeCommandId(commandIdInstance,newName);
					item.setText(COLUMN_COMMAND_ID_FUNCTION, newName);
				}
			}			
		} //Nothing, unless the user has edited the value
		else{
			if (newName.equals("")){
				if (exist){
					removeEventBinding(commandIdInstance,COMMAND_ID_EVENT);					
				}
				item.setText(COLUMN_COMMAND_ID_FUNCTION, "");
			}
			else{
				if (!newName.equals(oldName) && (!oldName.equals(""))){
					if (newName.matches("^[(a-z)|(A-Z)][\\w]*$")){
						goToCodeCommandId(commandIdInstance, newName);
						item.setText(COLUMN_COMMAND_ID_FUNCTION, newName);				
					}					
				}
				else{
					if (exist){
						item.setText(COLUMN_COMMAND_ID_FUNCTION, newName);
					}
					else{
						item.setText(COLUMN_COMMAND_ID_FUNCTION, "");
					}
				}
			}
			
		}			
		text2.dispose();
	}

	private void setNameCommandId(final TableEditor editorCell, final TableItem item,
			final Text text) {
		Text aux = (Text)editorCell.getEditor();									
		String oldName = editorCell.getItem().getText();
		String newName = aux.getText();
		item.setText(COLUMN_COMMAND_ID_NAME, text.getText());
		if ((!oldName.equals(newName)) && NamePropertySupport.isLegalName(aux.getText())
				&& ViewEditorUtils.validateUniqueName(editor.getDataModel(),aux.getText())){								
			editorCell.getItem().setText(COLUMN_COMMAND_ID_NAME, aux.getText());
			refreshChangesCommandIDToModel(oldName,COMMAND_ID_PROPERTY_NAME,newName);
		}
		else{
			item.setText(COLUMN_COMMAND_ID_NAME, oldName);
		}
		text.dispose();
	}

	private void setSystemOrCommandId(final TableEditor editorCell,
			final TableItem item, final CCombo combo) {
		CCombo comboEditor = (CCombo)editorCell.getEditor();
		String commandIdSelected = comboEditor.getText();
		String old = item.getText(COLUMN_COMMAND_ID_COMMANDID);
		if (!commandIdSelected.equals(old)){
			setNewCommandIdProperty(commandIdSelected, 
									item.getText(COLUMN_COMMAND_ID_NAME));
			item.setText(COLUMN_COMMAND_ID_COMMANDID, commandIdSelected);
		}
		else{
			item.setText(COLUMN_COMMAND_ID_COMMANDID, old);
		}					
		combo.dispose();
	}
}