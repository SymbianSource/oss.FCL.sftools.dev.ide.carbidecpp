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
package com.nokia.sdt.symbian.ui.appeditor;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IComponentInstancePropertyListener;
import com.nokia.sdt.datamodel.images.IImagePropertyRendering;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.SetPropertyCommand;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.emf.dm.IDesignerData;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.symbian.dm.S60ModelUtils;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.dm.SymbianModelUtils.SDKType;
import com.nokia.sdt.symbian.images.SymbianImagePropertyRendering;
import com.nokia.sdt.symbian.sdk.SdkUtilities;
import com.nokia.sdt.symbian.sdk.SdkUtilities.SelectableSDKInfo;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.sdt.symbian.ui.appeditor.context.EditingContextCommand;
import com.nokia.sdt.symbian.ui.appeditor.context.FormEditorEditingContext;
import com.nokia.sdt.symbian.ui.images.DirectEditingUtilities;
import com.nokia.sdt.symbian.ui.noexport.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.nokia.sdt.utils.ui.ImageIcon;
import com.swtdesigner.ResourceManager;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.List;

public class OverviewPage extends FormPage {

	private List<ISymbianSDK> selectableSDKs;
	private SDKType sdkType;
	private AppEditorContext editorContext;
	
	private SectionPart componentsSectionPart;
	private Label summaryText;
	private Combo componentVersionsCombo;
	private Label titleBarDescription;
	private Text titleNameText;
	private boolean settingTitleNameText;
	private Label iconLabel;
	private Button defaultIconButton;
	private Button overrideIconButton;
		
	private EObject appUIEObject;
	private IComponentInstance appUIComponentInstance;
	private IComponentInstancePropertyListener componentInstancePropertyListener;
	private static String CAPTION_PROPERTY_NAME = "caption"; //$NON-NLS-1$
	private static String SHORT_CAPTION_PROPERTY_NAME = "shortCaption"; //$NON-NLS-1$
	private static String TITLE_ICON_PROPERTY_NAME = "image"; //$NON-NLS-1$
	private ImageIcon titleIcon;
	
	private IDesignerData.IModelPropertyListener modelPropertyListener;
	private Composite iconSubcomposite;
	
	private enum TitleIconState {
		DEFAULT,
		OVERRIDE
	}
		
	private static final String HELP_CONTEXT_ID = 
		UIPlugin.PLUGIN_ID + "." + "appEditorOverviewPageContext"; //$NON-NLS-1$ //$NON-NLS-2$
	
	public OverviewPage(AppEditorContext editorContext) {
		super(editorContext.getFormEditor(), AppEditorContext.OVERVIEW_PAGE_ID, Messages.getString("OverviewPage.pageTitle")); //$NON-NLS-1$ //$NON-NLS-2$
		this.editorContext = editorContext;
		
		IProject project = editorContext.getRootDataModel().getProjectContext().getProject();
		sdkType = SymbianModelUtils.getModelSDK(editorContext.getRootDataModel());
		SelectableSDKInfo info = SdkUtilities.getSelectableSDKsForProject(project, sdkType.vendorPattern);
		selectableSDKs = info.selectableSDKs;
		
		modelPropertyListener = new IDesignerData.IModelPropertyListener() {
			public void propertyChanged(String propertyId, String propertyValue) {
				if (SymbianModelUtils.SYMBIAN_VERSION_PROPERTY.equals(propertyId)) {
					if (componentVersionsCombo != null) {
						int index = getCurrentSDKIndex();
						if (index >= 0) {
							componentVersionsCombo.select(index);
						}
					}
				}
			}
		};
		DesignerDataModel dm = (DesignerDataModel) editorContext.getRootDataModel();
		dm.getDesignerData().addModelPropertyListener(modelPropertyListener);
		
		appUIEObject = ModelUtils.getComponentInstance(editorContext.getRootDataModel().getRootComponentInstances()[0].getChildren()[0]).getChildren()[0];
		appUIComponentInstance = ModelUtils.getComponentInstance(appUIEObject);
		componentInstancePropertyListener = new IComponentInstancePropertyListener() {
			public void propertyChanged(EObject componentInstance, Object propertyId) {
				appUIPropertyChanged(propertyId);
			}
		};
		appUIComponentInstance.addPropertyListener(componentInstancePropertyListener);
	}
	
	private void appUIPropertyChanged(Object propertyId) {
		if (TITLE_ICON_PROPERTY_NAME.equals(propertyId)) {
			if (imageValueIsSet()) {
				setTitleIconState(TitleIconState.OVERRIDE);
			} else {
				setTitleIconState(TitleIconState.DEFAULT);
			}
			updateTitleIconImage();
		} else if (CAPTION_PROPERTY_NAME.equals(propertyId)) {
			String textValue = getModelTitleNameTextValue();
			setTitleNameText(textValue);			
		}
	}
	
	private boolean imageValueIsSet() {
		IPropertySource ps = getApppUIPropertySource();
		boolean result = ps.isPropertySet(TITLE_ICON_PROPERTY_NAME);
		if (result) {
			// distinguish between compound property with all defaults, vs
			// actually set
			IPropertySource imagePS = (IPropertySource) ps.getPropertyValue(TITLE_ICON_PROPERTY_NAME);
			result = imagePS.isPropertySet(SymbianModelUtils.IMAGE_COMPOUND_FILE);
		}
		return result;
	}
	
	@Override
	public void dispose() {
		DesignerDataModel dm = (DesignerDataModel) editorContext.getRootDataModel();
		dm.getDesignerData().removeModelPropertyListener(modelPropertyListener);
		super.dispose();
	}

	@Override
	public Object getAdapter(Class adapter) {
		Object result = null;
		if (adapter.getClass().equals(AppEditorContext.class)) {
			result = editorContext;
		}
		else {
			result = super.getAdapter(adapter);
		}
		return result;
	}
	
	private String createS60SummaryText() {
		StringBuffer result = new StringBuffer();
		String lineSeparator = System.getProperty("line.separator"); //$NON-NLS-1$
		switch (editorContext.getS60RootModelType()) {
		case BASIC_APPUI: {
			result.append(Messages.getString("Overview.summary1.s60.basic")); //$NON-NLS-1$
			result.append(lineSeparator);
		}break;
		
		case VIEW_APPUI: {
			result.append(Messages.getString("Overview.summary1.s60.view")); //$NON-NLS-1$
			result.append(lineSeparator);
		} break;
		
		case LEGACY: {
			result.append(Messages.getString("Overview.summary1.s60.legacy")); //$NON-NLS-1$
			result.append(lineSeparator);
		} break;
		
		default:
			break;
		}
		return result.toString();
	}
	
	private String createUIQSummaryText() {
		StringBuffer result = new StringBuffer();
		String lineSeparator = System.getProperty("line.separator"); //$NON-NLS-1$
		switch (editorContext.getUIQRootModelType()) {
		case BASIC_APPUI: {
			result.append(Messages.getString("Overview.summary1.uiq.basic")); //$NON-NLS-1$
			result.append(lineSeparator);
		}break;
		
		case VIEW_APPUI: {
			result.append(Messages.getString("Overview.summary1.uiq.view")); //$NON-NLS-1$
			result.append(lineSeparator);
		} break;
		
		case LEGACY: {
			result.append(Messages.getString("Overview.summary1.uiq.legacy")); //$NON-NLS-1$
			result.append(lineSeparator);
		} break;
		
		default:
			break;
		}
		return result.toString();
	}
	
	private String createSummaryText() {
		String result = null;
		SymbianModelUtils.SDKType sdkType = SymbianModelUtils.getModelSDK(editorContext.getRootDataModel());
		switch (sdkType) {
		case S60: 
			result = createS60SummaryText();
			break;
		case UIQ:
			result = createUIQSummaryText();
			break;
		}
		return result;
	}

	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText(Messages.getString("OverviewPage.form.text")); //$NON-NLS-1$
		Composite body = form.getBody();
		body.setLayout(new FormLayout());
		toolkit.paintBordersFor(body);
		String href = null;
		
		SymbianModelUtils.SDKType sdkType = SymbianModelUtils.getModelSDK(editorContext.getRootDataModel());
		switch (sdkType) {
		case S60: 
			href = "/com.nokia.sdt.uidesigner.help/html/reference/app_editor/ref_overview.htm"; //$NON-NLS-1$
			break;
		case UIQ:
			href = "/com.nokia.carbide.cpp.uiq.help/html/reference/app_editor/ref_overview.htm"; //$NON-NLS-1$
			break;
		}

		
		FormUtilities.addHelpToolbarItem(form.getForm(), href, Messages.getString("OverviewPage.pageHelpTooltip")); //$NON-NLS-1$

		final Section summarySection = toolkit.createSection(body, Section.TITLE_BAR);
		final FormData formData_3 = new FormData();
		formData_3.bottom = new FormAttachment(0, 105);
		formData_3.top = new FormAttachment(0, 5);
		formData_3.right = new FormAttachment(0, 290);
		formData_3.left = new FormAttachment(0, 5);
		summarySection.setLayoutData(formData_3);
		summarySection.setText(Messages.getString("OverviewPage.SummarySectionTitle")); //$NON-NLS-1$

		summaryText = toolkit.createLabel(summarySection, null, SWT.WRAP);
		summarySection.setClient(summaryText);
		summaryText.setText(createSummaryText());

		final Section componentsSection = toolkit.createSection(body, Section.DESCRIPTION | Section.TITLE_BAR);
		componentsSectionPart = new SectionPart(componentsSection);
		final FormData formData_1 = new FormData();
		formData_1.top = new FormAttachment(0, 150);
		formData_1.bottom = new FormAttachment(0, 321);
		formData_1.right = new FormAttachment(0, 288);
		formData_1.left = new FormAttachment(0, 5);
		componentsSection.setLayoutData(formData_1);
		
		componentsSection.setDescription(Messages.getString("OverviewPage.componentsSection.description")); //$NON-NLS-1$
		componentsSection.setText(Messages.getString("OverviewPage.componentsSection.text")); //$NON-NLS-1$

		final Composite componentVersionsComposite = toolkit.createComposite(componentsSection, SWT.NONE);
		componentVersionsComposite.setLayout(new TableWrapLayout());
		toolkit.paintBordersFor(componentVersionsComposite);
		componentsSection.setClient(componentVersionsComposite);

		componentVersionsCombo = new Combo(componentVersionsComposite, SWT.READ_ONLY);
		componentVersionsCombo.setItems(getSDKStrings());
		componentVersionsCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setComponentVersion(componentVersionsCombo.getSelectionIndex());
			}
		});
		componentVersionsCombo.setText(Messages.getString("OverviewPage.componentVersionsCombo.text")); //$NON-NLS-1$
		int itemToSelect = getCurrentSDKIndex();
		if (itemToSelect >= 0) {
			componentVersionsCombo.select(itemToSelect);
		}
		final TableWrapData tableWrapData = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP);
		tableWrapData.maxWidth = 300;
		tableWrapData.grabHorizontal = true;
		componentVersionsCombo.setLayoutData(tableWrapData);

		final Section applicationContentSection = toolkit.createSection(body, Section.TITLE_BAR);
		final FormData formData_2 = new FormData();
//		SymbianModelUtils.SDKType sdkType = SymbianModelUtils.getModelSDK(editorContext.getRootDataModel());
		switch (sdkType) {
		case S60: 
			formData_2.bottom = new FormAttachment(0, 247);
			break;
		case UIQ:
			formData_2.bottom = new FormAttachment(0, 150);
		}
		formData_2.top = new FormAttachment(0, 5);
		formData_2.right = new FormAttachment(0, 525);
		formData_2.left = new FormAttachment(0, 289);
		applicationContentSection.setLayoutData(formData_2);
		applicationContentSection.setText(Messages.getString("OverviewPage.applicationContentSection.text")); //$NON-NLS-1$

		final Composite pagesComposite = toolkit.createComposite(applicationContentSection, SWT.NONE);
		final TableWrapLayout tableWrapLayout = new TableWrapLayout();
		tableWrapLayout.numColumns = 2;
		pagesComposite.setLayout(tableWrapLayout);
		toolkit.paintBordersFor(pagesComposite);
		applicationContentSection.setClient(pagesComposite);

		final ImageHyperlink viewsLink = toolkit.createImageHyperlink(pagesComposite, SWT.NONE);
		viewsLink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				editorContext.getFormEditor().setActivePage(AppEditorContext.VIEWS_PAGE_ID);
			}
		});

		viewsLink.setImage(ResourceManager.getPluginImage(UIPlugin.getDefault(), "icons/pencil.png")); //$NON-NLS-1$
		viewsLink.setText(Messages.getString("OverviewPage.viewsLink.text")); //$NON-NLS-1$

		toolkit.createLabel(pagesComposite, Messages.getString("OverviewPage.null.text"), SWT.WRAP); //$NON-NLS-1$

		final ImageHyperlink languagesLink = toolkit.createImageHyperlink(pagesComposite, SWT.NONE);
		languagesLink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				editorContext.getFormEditor().setActivePage(AppEditorContext.LANGUAGES_PAGE_ID);
			}
		});
		languagesLink.setImage(ResourceManager.getPluginImage(UIPlugin.getDefault(), "icons/pencil.png")); //$NON-NLS-1$
		languagesLink.setText(Messages.getString("OverviewPage.languagesLink.text")); //$NON-NLS-1$

		toolkit.createLabel(pagesComposite, Messages.getString("OverviewPage.null.text1"), SWT.WRAP); //$NON-NLS-1$

		switch (sdkType) {
		case S60: 
			if (editorContext.getS60RootModelType() != S60ModelUtils.S60RootModelType.LEGACY) {
				final ImageHyperlink appUiLink = toolkit.createImageHyperlink(pagesComposite, SWT.NONE);
				appUiLink.addHyperlinkListener(new HyperlinkAdapter() {
					public void linkActivated(HyperlinkEvent e) {
						activateAppUiPage();
					}
				});
				appUiLink.setImage(ResourceManager.getPluginImage(UIPlugin.getDefault(), "icons/pencil.png")); //$NON-NLS-1$
				appUiLink.setText(Messages.getString("OverviewPage.appUiLink.text")); //$NON-NLS-1$
				toolkit.createLabel(pagesComposite, Messages.getString("OverviewPage.null.text2"), SWT.WRAP); //$NON-NLS-1$
			}
			break;
		case UIQ:
			final Section titleBarSection = toolkit.createSection(body, Section.TITLE_BAR);
			final FormData formData_4 = new FormData();
			formData_4.bottom = new FormAttachment(0, 600);
			formData_4.top = new FormAttachment(0, 150);
			formData_4.right = new FormAttachment(0, 525);
			formData_4.left = new FormAttachment(0, 289);
			titleBarSection.setLayoutData(formData_4);
			titleBarSection.setText(Messages.getString("OverviewPage.titleBarSection.title")); //$NON-NLS-1$
			
			final Composite titleBarComposite = toolkit.createComposite(titleBarSection, SWT.NONE);
			final TableWrapLayout tableWrapLayout2 = new TableWrapLayout();
			tableWrapLayout2.numColumns = 1;
			titleBarComposite.setLayout(tableWrapLayout2);
			toolkit.paintBordersFor(titleBarComposite);
			titleBarSection.setClient(titleBarComposite);			
			
			titleBarDescription = toolkit.createLabel(titleBarComposite, null, SWT.WRAP);
			titleBarDescription.setText(Messages.getString("OverviewPage.titleBarSection.description")); //$NON-NLS-1$
						
			final Section nameSection = toolkit.createSection(titleBarComposite, Section.EXPANDED);
			final TableWrapData nameTableWrapData = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
			nameSection.setLayoutData(nameTableWrapData);
			nameSection.setText(Messages.getString("OverviewPage.titleBarSection.nameSubtitle")); //$NON-NLS-1$
			toolkit.createCompositeSeparator(nameSection);
			toolkit.paintBordersFor(nameSection);
			
			final Composite nameComposite = toolkit.createComposite(nameSection, SWT.WRAP);
			final TableWrapLayout nameCompositeTableWrapLayout = new TableWrapLayout();
			nameCompositeTableWrapLayout.numColumns = 1;
			nameComposite.setLayout(nameCompositeTableWrapLayout);
			toolkit.paintBordersFor(nameComposite);
			nameSection.setClient(nameComposite);
						
			titleNameText = toolkit.createText(nameComposite, ""); //$NON-NLS-1$
			final TableWrapData nameTextTableWrapData = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP);
			titleNameText.setLayoutData(nameTextTableWrapData);
			
			titleNameText.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent arg0) {
					if (!settingTitleNameText) {
						textChanged();
					}
				}
			});
			settingTitleNameText = true;
			titleNameText.setText(getModelTitleNameTextValue());
			settingTitleNameText = false;
			
			final Section iconSection = toolkit.createSection(titleBarComposite, Section.EXPANDED);
			final TableWrapData iconTableWrapData = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
			iconSection.setLayoutData(iconTableWrapData);
			iconSection.setText(Messages.getString("OverviewPage.titleBarSection.contextIconSubtitle")); //$NON-NLS-1$
			toolkit.createCompositeSeparator(iconSection);
			toolkit.paintBordersFor(iconSection);
			
			final Composite iconComposite = toolkit.createComposite(iconSection, SWT.WRAP);
			final TableWrapLayout iconCompositeTableWrapLayout = new TableWrapLayout();
			iconCompositeTableWrapLayout.numColumns = 1;
			iconComposite.setLayout(iconCompositeTableWrapLayout);
			toolkit.paintBordersFor(iconComposite);
			iconSection.setClient(iconComposite);			
			
			iconSubcomposite = toolkit.createComposite(iconComposite, SWT.WRAP);
			iconSubcomposite.setLayoutData(new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP));
			final TableWrapLayout iconSubcompositeTableWrapLayout = new TableWrapLayout();
			iconSubcompositeTableWrapLayout.numColumns = 3;
			iconSubcomposite.setLayout(iconSubcompositeTableWrapLayout);
			toolkit.paintBordersFor(iconSubcomposite);
			
			defaultIconButton = new Button(iconSubcomposite, SWT.RADIO);
			final TableWrapData defaultIconTableWrapData = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP);
			defaultIconTableWrapData.colspan = 3;
			defaultIconButton.setLayoutData(defaultIconTableWrapData);
			defaultIconButton.setText(Messages.getString("OverviewPage.titleBarSection.defaultIcon")); //$NON-NLS-1$
			defaultIconButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (defaultIconButton.getSelection()) {
						performSetTitleIconState(TitleIconState.DEFAULT);
					}
				}
			});
			
			overrideIconButton = new Button(iconSubcomposite, SWT.RADIO);
			overrideIconButton.setLayoutData(new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP));	
			overrideIconButton.setText(Messages.getString("OverviewPage.titleBarSection.overrideIcon")); //$NON-NLS-1$
			overrideIconButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					if (overrideIconButton.getSelection()) {
						performSetTitleIconState(TitleIconState.OVERRIDE);
					}
				}
			});
			iconLabel = new Label(iconSubcomposite, SWT.BORDER);
			TableWrapData tableWrapData2 = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP);
			tableWrapData2.maxHeight = 64;
			tableWrapData2.maxWidth = 64;
			iconLabel.setLayoutData(tableWrapData2);
			
			updateTitleIconImage();
			
			/*if (true) {
				
			}/* else {
				titleIcon = new ImageIcon(iconSubcomposite, SWT.SHADOW_IN, false);
				titleIcon.setLayoutData(new TableWrapData(TableWrapData.RIGHT, TableWrapData.TOP));
		        titleIcon.setData(".uid", "appearanceIcon"); //$NON-NLS-1$	
		        titleIcon.setImage(new Image(Display.getCurrent(), "c:\\incubusCover.jpg"));
			}*/
			
			Button editImageButton = toolkit.createButton(iconSubcomposite, Messages.getString("AvkonViewDetails.editImageButton.text"), SWT.NONE); //$NON-NLS-1$
			editImageButton.setLayoutData(new TableWrapData(TableWrapData.RIGHT, TableWrapData.TOP));
			editImageButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					editTitleIcon();
				}
			});
			
			//IPropertySource ps = getApppUIPropertySource();
		    //final ImagePropertyInfo imagePropertyInfo = ImageGlobals.getImagePropertyInfoFromProperty(appUIComponentInstance, (IPropertySource)ps.getPropertyValue(TITLE_ICON_PROPERTY_NAME));
		    //imagePropertyInfo.isEmptyImage();
		    if (imageValueIsSet()) {
				setTitleIconState(TitleIconState.OVERRIDE);
			} else {
				setTitleIconState(TitleIconState.DEFAULT);
			}
		}
	}

	private void updateTitleIconImage() {
		ImageData titleIcon = null;
		IImagePropertyRendering ipr = new SymbianImagePropertyRendering();
		Point size = iconLabel.getSize();
		if (size.x == 0 || size.y == 0) {
			size = new Point(20, 20);
			iconLabel.setSize(size);
		}
		ipr.setViewableSize(null);
		ipr.setScaling(true);
		ipr.setImageProperty(appUIComponentInstance,
				TITLE_ICON_PROPERTY_NAME, null);
		titleIcon = ipr.getImageData();

		if (titleIcon != null) {
			if (iconLabel.getImage() != null)
				iconLabel.getImage().dispose();
			iconLabel.setImage(new Image(Display.getDefault(), titleIcon));
		} else {
			ImageDescriptor id = ResourceManager.getPluginImageDescriptor(UIPlugin.getDefault(), "icons/iconHolder.png"); //$NON-NLS-1$
			iconLabel.setImage(id.createImage());
		}
		iconSubcomposite.layout();
	}
	
	private void performSetTitleIconState(TitleIconState state) {
		CompoundCommand cc = new CompoundCommand();
		cc.setLabel(Messages.getString("AvkonViewDetailsPage.setTabGroupUndoLabel")); //$NON-NLS-1$
		switch (state) {
		case DEFAULT:
			if (imageValueIsSet()) {
				cc.append(setTitleIconCommand(null));
			}
			break;
			
		case OVERRIDE: 
			if (imageValueIsSet()) {
				cc.append(setTitleIconCommand(null));
			}
			break;
		}
		
		setTitleIconState(state);
		if (!cc.isEmpty()) {
			FormEditorEditingContext editingContext = new FormEditorEditingContext(
					null, editorContext.getFormEditor(), appUIEObject);
			EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
			editorContext.addAndExecuteCommand(wrapper);
		}
		editorContext.getFormEditor().editorDirtyStateChanged();
	}
	
	private void setTitleIconState(TitleIconState state) {
		defaultIconButton.setSelection(state == TitleIconState.DEFAULT);
		overrideIconButton.setSelection(state == TitleIconState.OVERRIDE);
	}
	
	private void textChanged() {
		if (titleNameText.getText() != null) {
			FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editorContext.getFormEditor(), appUIEObject);
			CompoundCommand emfCompound = new CompoundCommand();
			emfCompound.append(setTitleNameTextCommand(titleNameText.getText()));
			EditingContextCommand wrapper2 = new EditingContextCommand(emfCompound, false, editingContext);
			editorContext.addAndExecuteCommand(wrapper2);
		}
	}
	
	private SetPropertyCommand setTitleIconCommand(String textValue) {
		return new SetPropertyCommand(getApppUIPropertySource(),
				TITLE_ICON_PROPERTY_NAME, textValue);
	}
	
	private SetPropertyCommand setTitleNameTextCommand(String textValue) {
		return new SetPropertyCommand(getApppUIPropertySource(),
				CAPTION_PROPERTY_NAME, textValue);
	}
	
	private void setTitleNameText(String text) {
		settingTitleNameText = true;
		if (text != null) {
			String currText = titleNameText.getText();
			if (!text.equals(currText)) {
				titleNameText.setText(text);
			}
		} else {
			titleNameText.setText(""); //$NON-NLS-1$
		}
		settingTitleNameText = false;
	}
	
	private String getModelTitleNameTextValue() {
		IPropertySource ps = getApppUIPropertySource();
		return (String)ps.getPropertyValue(CAPTION_PROPERTY_NAME);
	}
	
	private IPropertySource getApppUIPropertySource() {
		IPropertySource result = null;
		if (appUIEObject != null) {
			result = ModelUtils.getPropertySource(appUIEObject);
		}
		return result;
	}

	private String[] getSDKStrings() {
		String[] result = new String[selectableSDKs.size()];
		for (int i = 0; i < selectableSDKs.size(); i++) {
			result[i] = selectableSDKs.get(i).getUniqueId();
		}
		return result;
	}
	
	private void setComponentVersion(int sdkIndex) {
		if (sdkIndex >= 0 && sdkIndex < selectableSDKs.size()) {
			ISymbianSDK sdk = selectableSDKs.get(sdkIndex);
			String version = sdk.getSDKVersion().toString();
			Command emfCommand = SymbianModelUtils.setSDKVersion(editorContext.getRootDataModel(), version);
			FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editorContext.getFormEditor(), componentVersionsCombo);
			EditingContextCommand wrapper = new EditingContextCommand(emfCommand, false, editingContext);
			editorContext.addAndExecuteCommand(wrapper);
		}
	}
	
	/**
	 * Get the root data model's component version property and look up it's
	 * index in the selectable SDK list
	 */
	private int getCurrentSDKIndex() {
		int result = -1;
		String version = SymbianModelUtils.getSDKVersion(editorContext.getRootDataModel());
		Check.checkState(version != null);
		ISymbianSDK sdk = SdkUtilities.getBestSDKForVendorAndVersion(sdkType.vendorPattern, version);
		if (sdk != null) {
			for (int i = 0; i < selectableSDKs.size(); i++) {
				if (selectableSDKs.get(i) == sdk) {
					result = i;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public boolean selectReveal(Object object) {
		boolean result = false;
		if (object == componentVersionsCombo) {
			result = true;
			componentVersionsCombo.setFocus();
		}
		return result;
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		WorkbenchUtils.setHelpContextId(getPartControl(), HELP_CONTEXT_ID);
	}

	public void activateAppUiPage() {
		int appUiPageIndex = editorContext.getAppUiPageIndex();
		if (appUiPageIndex >= 0) {
			IDesignerDataModelEditor dmEditor = 
				(IDesignerDataModelEditor) getEditor().getAdapter(IDesignerDataModelEditor.class);
			Check.checkState(dmEditor != null);
			dmEditor.activatePage(appUiPageIndex);
		}
	}
	
	private void editTitleIcon() {
		// open image editing dialog
		org.eclipse.gef.commands.Command gefCommand = DirectEditingUtilities.editImageProperty(
				editorContext.getEditorSite().getShell(),
				appUIEObject,
				TITLE_ICON_PROPERTY_NAME);
		
		if (gefCommand != null) {
			// Put all the commands in a final wrapper that has the editing context.
			FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editorContext.getFormEditor(), appUIEObject);
			EditingContextCommand wrapper2 = new EditingContextCommand(gefCommand, false, editingContext);
			editorContext.addAndExecuteCommand(wrapper2);
		}
	}
}

