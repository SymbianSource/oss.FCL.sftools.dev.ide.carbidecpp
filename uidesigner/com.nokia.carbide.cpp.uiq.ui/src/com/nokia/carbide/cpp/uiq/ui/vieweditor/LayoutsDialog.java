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
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.datamodel.images.IImagePropertyRendering;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NamePropertySupport;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.symbian.dm.UIQModelUtils;
import com.nokia.sdt.symbian.images.SymbianImagePropertyRendering;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.sdt.symbian.ui.images.DirectEditingUtilities;
import com.swtdesigner.ResourceManager;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.IPropertySource;
import com.nokia.sdt.symbian.dm.*;
import com.nokia.sdt.datamodel.adapter.*;

/**
 * This class creates the dialog for the Add layout action. It manages all the fields
 * of the user's input.
 *
 */
public class LayoutsDialog extends Dialog{
	public IDesignerDataModelEditor editor;
	private int type;
	private String layoutType;
	private String viewLayout;
	private EObject layoutConfigurationGroup;
	private Object imageObj;
	//GUI
	private Label lblError;
	private Text txtName;
	private Combo cmbCommandList;
	private Text txtTabCaption; 
	private Label labelTabImage;
	private Combo cmbScroll;
	private Button btnEdit;
	private Combo cmbCommandList2;
	private Combo cmbLayout;
	private Button button;
	private Group viewGroup;
	private String nameLayout;
	private String commandListLayout;
	private String tabCaption;
	private String commandListView;
	private String scrollContainer;
	private String layoutManager;
	private boolean error = false;
	private final String UI_COMMAND_LIST = UIQModelUtils.UIQ_COMMAND_LIST;
	private final String UIQ_CQIKVIEW= UIQModelUtils.UIQ_CQIKVIEW;
	private static final String VIEW_LAYOUT = UIQModelUtils.UIQ_VIEW_LAYOUT;
	private static final String UIQ_CQIKCONTAINER = UIQModelUtils.UIQ_CQIKCONTAINER;
	private UIConfigurationPageUtils uiConfigurationPageUtils;
	//Properties IDs
	public static final String TAB_IMAGE_PROPERTY = "tabImage";
	public static final String BMP_FILE_PROPERTY = "bmpfile";
	public static final String BMP_ID_PROPERTY = "bmpid";
	public static final String BMP_MASK_PROPERTY = "bmpmask";
	/**
	 * The class constructor.
	 * @param parentShell
	 * @param type - 0 for View and 1 for SimpleDialog
	 * @param editor
	 * @param uiConfigurationPageUtils
	 * @param layoutType
	 * @param viewLayout
	 * @param layoutConfigurationGroup
	 */
	protected LayoutsDialog(Shell parentShell,int type,IDesignerDataModelEditor editor,
			UIConfigurationPageUtils uiConfigurationPageUtils,
			String layoutType,
			String viewLayout,
			EObject layoutConfigurationGroup){
		super(parentShell);
		this.type = type; 
		this.editor = editor;
		this.uiConfigurationPageUtils = uiConfigurationPageUtils;
		this.layoutType = layoutType;
		this.viewLayout = viewLayout;
		this.layoutConfigurationGroup = layoutConfigurationGroup;
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	protected Control createDialogArea(Composite parent) {
		Composite formComposite = (Composite) super.createDialogArea(parent);
		formComposite.setLayout(new FormLayout());
		lblError = new Label(formComposite, SWT.NONE);
		lblError.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		final FormData frmDataLblError = new FormData();
		frmDataLblError.bottom = new FormAttachment(0, 19);
		frmDataLblError.top = new FormAttachment(0, 1);
		frmDataLblError.left = new FormAttachment(0, 1);
		frmDataLblError.right = new FormAttachment(0, 353);
		lblError.setLayoutData(frmDataLblError);
		lblError.setText(Messages.getString("LayoutsDialog.error"));
		lblError.setVisible(false);
		//First two controls ini...
		final Composite controlsComposite = new Composite(formComposite, SWT.NONE);
		final GridLayout controlsGrid = new GridLayout();
		controlsGrid.numColumns = 2;
		controlsComposite.setLayout(controlsGrid);
		final FormData controlsGroupFrmData = new FormData();
		controlsGroupFrmData.bottom = new FormAttachment(0, 93 - (type * 25));
		controlsGroupFrmData.right = new FormAttachment(0, 341);
		controlsGroupFrmData.top = new FormAttachment(0, 34);
		controlsGroupFrmData.left = new FormAttachment(0, 7);
		controlsComposite.setLayoutData(controlsGroupFrmData);
		final Label lblName = new Label(controlsComposite, SWT.NONE);
		lblName.setLayoutData(new GridData(80,SWT.DEFAULT));
		lblName.setText(Messages.getString("LayoutsDialog.lblName"));
		txtName = new Text(controlsComposite, SWT.BORDER);
		txtName.setText("");
		txtName.addModifyListener(new ModifyListener(){
		      public void modifyText(ModifyEvent event) {
		        // Get the widget whose text was modified
		        nameLayout = txtName.getText();
		        validateControls();
		      }
		    });
		
		txtName.setLayoutData(new GridData(180,SWT.DEFAULT));
		if (type == 0) {
			final Label lblCommandList = new Label(controlsComposite, SWT.NONE);
			lblCommandList.setLayoutData(new GridData(80,SWT.DEFAULT));
			lblCommandList.setText(Messages.getString("LayoutsDialog.lblCommand"));
			cmbCommandList = new Combo(controlsComposite, SWT.READ_ONLY);
			cmbCommandList.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent arg0) {
					commandListLayout = "";//cmbCommandList.getItem(0); 
				}

				public void widgetSelected(SelectionEvent arg0) {
					if (cmbCommandList.getSelectionIndex() == 0){ //<none>
						commandListLayout = "";
					}
					else{
						commandListLayout = cmbCommandList.getItem(cmbCommandList.getSelectionIndex());
					}
				}
			});
		
			final GridData cmbGridData = new GridData(SWT.LEFT, SWT.CENTER, true, false);
			cmbGridData.widthHint = 165;
			cmbGridData.heightHint = 180;
			cmbCommandList.setLayoutData(cmbGridData);
	
			//First two controls end...
			//View Page Group ini..
			viewGroup = new Group(formComposite, SWT.NONE);
			final GridLayout viewGroupGrid = new GridLayout();
			viewGroupGrid.numColumns = 3;
			viewGroup.setLayout(viewGroupGrid);
			viewGroup.setText(Messages.getString("LayoutsDialog.lblviewGroup"));
			final FormData viewGroupFrmData = new FormData();
			viewGroupFrmData.bottom = new FormAttachment(0, 240);//206
			viewGroupFrmData.top = new FormAttachment(0, 95);
			viewGroupFrmData.right = new FormAttachment(0, 341);
			viewGroupFrmData.left = new FormAttachment(0, 7);
			viewGroup.setLayoutData(viewGroupFrmData);
			final Label lblTabCaption = new Label(viewGroup, SWT.NONE);
			lblTabCaption
					.setText(Messages.getString("LayoutsDialog.lblTabCap"));
			lblTabCaption.setLayoutData(new GridData(80, SWT.DEFAULT));
			txtTabCaption = new Text(viewGroup, SWT.BORDER);
			txtTabCaption.setText("ViewPage");
			tabCaption=txtTabCaption.getText();
			txtTabCaption.addModifyListener(new ModifyListener(){
			      public void modifyText(ModifyEvent event) {
			        // Get the widget whose text was modified
			        tabCaption = txtTabCaption.getText();
			        validateControls();
			      }
			    });
			
			final GridData txtCaptionGridData = new GridData(SWT.LEFT,
					SWT.CENTER, true, false);
			txtCaptionGridData.widthHint = 180;
			txtTabCaption.setLayoutData(txtCaptionGridData);
			new Label(viewGroup, SWT.NONE);
			final Label lblTabImage = new Label(viewGroup, SWT.NONE);
			lblTabImage.setText(Messages.getString("LayoutsDialog.lblTabImg"));
			lblTabImage.setLayoutData(new GridData(80, SWT.DEFAULT));
			labelTabImage = new Label(viewGroup, SWT.BORDER);
			labelTabImage.setText("");
			
			labelTabImage.addPaintListener(new PaintListener(){
			     
				public void paintControl(PaintEvent arg0) {
					 validateControls();
				}
			    });
			
			final GridData txtImageGridData = new GridData(SWT.LEFT,
					SWT.CENTER, true, false);
			txtImageGridData.widthHint = 180;
			txtImageGridData.heightHint = 64;
			txtImageGridData.widthHint = 64;
			labelTabImage.setLayoutData(txtImageGridData);
			btnEdit = new Button(viewGroup, SWT.NONE);
			btnEdit.setText(Messages.getString("LayoutsDialog.btnEdit"));
			btnEdit.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					editTitleIcon();
				}
			});
			final Label lblCommandList2 = new Label(viewGroup, SWT.NONE);
			lblCommandList2.setText(Messages.getString("LayoutsDialog.lblCommand2"));
			lblCommandList2.setLayoutData(new GridData(80, SWT.DEFAULT));
			cmbCommandList2 = new Combo(viewGroup, SWT.READ_ONLY);
			cmbCommandList2.addSelectionListener(new SelectionListener() {

				public void widgetDefaultSelected(SelectionEvent arg0) {
					commandListView = "";//cmbCommandList2.getItem(0); 
				}

				public void widgetSelected(SelectionEvent arg0) {
					if (cmbCommandList2.getSelectionIndex() == 0){ //<none>
						commandListView = "";
					}
					else{
						commandListView = cmbCommandList2.getItem(cmbCommandList2.getSelectionIndex());
					}
				}
			});
			
			final GridData cmb2GridData = new GridData(SWT.LEFT, SWT.CENTER,
					true, false);
			cmb2GridData.widthHint = 180;
			cmb2GridData.heightHint = 180;
			cmbCommandList2.setLayoutData(cmb2GridData);
		}
		//Container Group ini..
		final Group containerGroup = new Group(formComposite, SWT.NONE);
		final GridLayout containerGroupGrid = new GridLayout();
		containerGroupGrid.numColumns = 2;
		containerGroup.setLayout(containerGroupGrid);
		containerGroup.setText(Messages.getString("LayoutsDialog.lblcontGroup"));
		final FormData frmDatacontainerGroup = new FormData();
		frmDatacontainerGroup.bottom = new FormAttachment(0, 331-(type*184));
		frmDatacontainerGroup.top = new FormAttachment(0, 249-(type*180));
		frmDatacontainerGroup.right = new FormAttachment(0, 341);
		frmDatacontainerGroup.left = new FormAttachment(0, 7);
		containerGroup.setLayoutData(frmDatacontainerGroup);

		final Label lblScroll = new Label(containerGroup, SWT.NONE);
		lblScroll.setLayoutData(new GridData());
		lblScroll.setText(Messages.getString("LayoutsDialog.lblScroll"));

		cmbScroll = new Combo(containerGroup, SWT.READ_ONLY);
		cmbScroll.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
				scrollContainer = cmbScroll.getItem(0); 
			}

			public void widgetSelected(SelectionEvent arg0) {
				scrollContainer = cmbScroll.getItem(cmbScroll.getSelectionIndex());
			}
		});
		
		final GridData cmbScrollGridData = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		cmbScrollGridData.widthHint = 165;
		cmbScroll.setLayoutData(cmbScrollGridData);

		final Label lblLayout = new Label(containerGroup, SWT.NONE);
		lblLayout.setLayoutData(new GridData());
		lblLayout.setText(Messages.getString("LayoutsDialog.lblLayout"));

		cmbLayout = new Combo(containerGroup, SWT.READ_ONLY);
		cmbLayout.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent arg0) {
				layoutManager = cmbLayout.getItem(0); 
			}

			public void widgetSelected(SelectionEvent arg0) {
				layoutManager = cmbLayout.getItem(cmbLayout.getSelectionIndex());
			}
		});
		
		final GridData cmbLayoutGridData = new GridData(SWT.LEFT, SWT.CENTER, true, false);
		cmbLayoutGridData.widthHint = 165;
		cmbLayout.setLayoutData(cmbLayoutGridData);
		//Container Group end..
		initControls();
		validateControls();
		return formComposite;
	}

	/**
	 * This method is called when the user press the Edit button. 
	 * It creates a dummy object to edit the image property,then it copies the values
	 * and delete the dummy instance when the user close the MBMImageEditor 
	 */
	private void editTitleIcon() { 
		IPropertySource viewPageProperties; 
		IComponentSet componentSet = editor.getDataModel().getComponentSet();
		Check.checkContract(componentSet != null);
		IComponent component = componentSet.lookupComponent(layoutType);
		Check.checkContract(component != null);
		EObject layoutInstance = editor.getDataModel().createNewComponentInstance(
				component);
		Command addLayoutCommand = editor.getDataModel()
		.createAddNewComponentInstanceCommand(
				layoutConfigurationGroup, layoutInstance, 0);
		CompoundCommand cc = new CompoundCommand();
		cc.append(addLayoutCommand);
		FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editor.getFormEditor(), null);
		EditingContextCommand wrapper2 = new EditingContextCommand(cc, false, editingContext);
		editor.getCommandStack().execute(wrapper2);
		EObject viewPage=ModelUtils.findFirstComponentInstance(layoutInstance, viewLayout, true);
		org.eclipse.gef.commands.Command gefCommand = DirectEditingUtilities.editImageProperty(
                    this.getShell(),viewPage,TAB_IMAGE_PROPERTY);
        if (gefCommand != null) {                 
              EditingContextCommand wrapper = new EditingContextCommand(gefCommand, false, null);
              editor.getCommandStack().execute(wrapper);
              viewPageProperties = ModelUtils.getPropertySource(viewPage);
              imageObj= (Object) viewPageProperties.getPropertyValue(TAB_IMAGE_PROPERTY);
              updateTitleIconImage(viewPage);
        }
        //REMOVE dummy object
        List<EObject> layoutsToRemove = new ArrayList<EObject>();
    	layoutsToRemove.add(layoutInstance);
        CompoundCommand remove = new CompoundCommand();
    	remove.setLabel(Messages.getString("LayoutsDialog.Delete.tipCommand")); //$NON-NLS-1$
		Command removeCommand = editor.getDataModel().createRemoveComponentInstancesCommand(layoutsToRemove);		
		remove.append(removeCommand);
		EditingContextCommand wrapperRemove = new EditingContextCommand(remove, false, null);
		editor.getCommandStack().execute(wrapperRemove);
		//REMOVE dummy object
	}
	
	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		System.currentTimeMillis();
		button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		if (error){
			button.setEnabled(false);
		}
		else{
			button.setEnabled(true);
		}
	}
	
	/**
	 * Return the initial size of the dialog
	 */
	protected Point getInitialSize() {
		return new Point(356, 402-(this.type*180));
	}
	/**
	 * Set the dialog's title.
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.getString("LayoutsDialog.dialogTitle")); //$NON-NLS-1$
	}
	
	/**
	 * When the OK button it's pressed, calls this method for validations.
	 */
	protected void okPressed() {
		validateControls();
		if ( !error ){
			this.close();
		}
		
	}
	
	/**
	 * Initializes the controls of the dialog, when the model is a view page.
	 */
	private void initControlsView(){ //View Page
		Vector<String> currentCommandList=uiConfigurationPageUtils.getCurrentObjectsFromModel(UI_COMMAND_LIST);
		cmbCommandList.add(Messages.getString("CommandListCombo.option"));
		cmbCommandList2.add(Messages.getString("CommandListCombo.option"));
		for(String commandList:currentCommandList){ //name starts empty, first cmbCommandList
			cmbCommandList.add(commandList);
			cmbCommandList2.add(commandList);
		}
		cmbCommandList.select(0);	
		cmbCommandList2.select(0);
		commandListLayout = "";//cmbCommandList.getItem(0); 
		commandListView= ""; //cmbCommandList2.getItem(0); 
	}
	
	/**
	 * This method updates the field Image on the dialog. It displays the image selected.
	 * @param instance
	 */
	private void updateTitleIconImage(EObject instance) {
		ImageData titleIcon = null;
		IImagePropertyRendering ipr = new SymbianImagePropertyRendering();
		Point size = labelTabImage.getSize();
		
		if (size.x == 0 || size.y == 0) {
			size = new Point(20, 20);
			labelTabImage.setSize(size);
		}
		ipr.setViewableSize(null);
		ipr.setScaling(true);
		ipr.setImageProperty(ModelUtils.getComponentInstance(instance),
				TAB_IMAGE_PROPERTY, null);
		titleIcon = ipr.getImageData();

		if (titleIcon != null) {
			if (labelTabImage.getImage() != null)
				labelTabImage.getImage().dispose();
			labelTabImage.setImage(new Image(Display.getDefault(), titleIcon));
		} else {
			ImageDescriptor id = ResourceManager.getPluginImageDescriptor(UIPlugin.getDefault(), "icons/iconHolder.png"); //$NON-NLS-1$
			labelTabImage.setImage(id.createImage());
		}
		viewGroup.layout();
	}
	
	/**
	 * This method initializes the common combos for the view and the simple dialog.
	 */
	private void initControlsContainer(){//CQikContainer 
		//scroll , layout manager
		for(int i = 0;i < uiConfigurationPageUtils.containerScrollLabels.length; i++){
			cmbScroll.add(uiConfigurationPageUtils.containerScrollLabels[i]);
		}
		cmbScroll.select(cmbScroll.indexOf(Messages.getString("ContainerSelectionPage.scrollable")));		
		//Layout manager
		for(int i = 0; i < uiConfigurationPageUtils.layoutManagersLabels.length; i++){
			cmbLayout.add(uiConfigurationPageUtils.layoutManagersLabels[i]);
		}
		cmbLayout.select(0);//This is the default layout
		scrollContainer = cmbScroll.getItem(cmbScroll.getSelectionIndex());
		layoutManager = cmbLayout.getItem(cmbLayout.getSelectionIndex());
	}
	/**
	 * This method call the initializers.
	 */
	private void initControls(){		
		if (type == 0){//View Page 
			initControlsView();
			initControlsContainer();
		}
		else{ //Simple dialog 
			initControlsContainer();
		}
	}
	
	/**
	 * This method validates the user's input. Initialize the state of the dialog to no error.
	 * The error label is set invisible and the flag Error is set to False, and the
	 * OK button is enabled.
	 * The validations are executed and in case of error it calls the setError method.
	 */
	public void validateControls(){
		error = false;
		lblError.setVisible(false);
		DesignerDataModel model = (DesignerDataModel) editor.getDataModel();
		IComponentInstance instance[] = model.getRootComponentInstances();
		String idDesign=instance[0].getComponentId();
		Vector<String> currentItems;
		if (idDesign.compareTo(UIQ_CQIKVIEW) == 0) {
			currentItems = uiConfigurationPageUtils.getCurrentObjectsFromModel(VIEW_LAYOUT);		
		}
		else{
			currentItems = uiConfigurationPageUtils.getCurrentObjectsFromModel(UIQ_CQIKCONTAINER);
		}

		if (button != null){
			button.setEnabled(true);
		}
		if (txtTabCaption != null){
			if(txtTabCaption.getText().equals("")){
				//The tab Image and the tab Caption properties cannot
				//be null at the same time.
				if(labelTabImage.getImage() == null){  
					setError("LayoutsDialog.EditImage.validateTab");
				} else {
					String bmpFile = (String) ((IPropertySource)imageObj).getPropertyValue("bmpfile");
					if (bmpFile == null || bmpFile.length() ==0) {
						setError("LayoutsDialog.EditImage.validateTab");
					}
				}
			}
		}

		if(txtName.getText().equals("")){ //The name cannot be empty
			if (button != null){
				//setError("LayoutsDialog.rename.validateEmpty");
				button.setEnabled(false);
			}
		}
		else{
			if (!NamePropertySupport.isLegalName(nameLayout)/*||
					currentItems.contains(txtName.getText())*/){ //The name must be valid.
				setError("LayoutsDialog.rename.validateEmpty");
			}
			else{//if its legal, let's check if its unique
				EObject foundObj = model.findByNameProperty(nameLayout);
				if (foundObj != null ) {
					String result = NamePropertySupport.duplicateNameMessage(nameLayout);
					setError(result);
				}
			}
		}
		
/*		
		if (!txtName.getText().equals("")&&!NamePropertySupport.isLegalName(nameLayout)){ //The name must be valid.
			setError("LayoutsDialog.rename.validateEmpty");
		}	
*/
	}
	
	/**
	 * It sets the state for Error of the dialog. The error label is visible.
	 * The OK button is disabled and the flag error is set to true.
	 * @param msg - The localizable string for the error message.  
	 */
	public void setError(String msg){
		String label = Messages.getString(msg);
		if (label.charAt(0) == '!'){
			label = msg;
		}
		lblError.setText(label);
		lblError.setVisible(true);
		error = true;
		if (button != null){
			button.setEnabled(false);
		}
	}
	/**
	 * Returns the name of the layout.
	 * @return name of the layout.
	 */
	public String getNameLayout(){
		return nameLayout;
	}
	/**
	 * Returns the command list selected.
	 * @return the command list selected.
	 */
	public String getCommandListLayout(){
		return commandListLayout;
	}
	
	/**
	 * Return the tab caption input by the user.
	 * @return tab caption.
	 */
	public String getTabCaption(){
		return tabCaption;
	}
	/**
	 * Returns the object image that contains the image properties.
	 * @return object with the image properties.
	 */
	public Object getTabImageObj(){
		return imageObj;
	}
	/**
	 * Returns the command list selected for the View.
	 * @return command list's name.
	 */
	public String getCommanListView(){
		return commandListView;
	}
	/**
	 * Return the constant for the Scroll container.
	 * @return scroll constant.
	 */
	public String getScrollContainer(){
		return scrollContainer;
	}
	/**
	 * Return the layout manager selected.
	 * @return the layout manager selected, the string displayed.
	 */
	public String getLayoutManager(){
		return layoutManager;
	}
	
}
