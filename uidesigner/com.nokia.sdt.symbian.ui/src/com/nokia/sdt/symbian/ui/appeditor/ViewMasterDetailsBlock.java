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

import com.nokia.cpp.internal.api.utils.core.ClassUtils;
import com.nokia.cpp.internal.api.utils.ui.RadioButtonTableViewer;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.SetPropertyCommand;
import com.nokia.sdt.symbian.dm.S60ModelUtils;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.dm.UIQModelUtils;
import com.nokia.sdt.symbian.dm.S60ModelUtils.S60RootModelType;
import com.nokia.sdt.symbian.dm.SymbianModelUtils.SDKType;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.sdt.symbian.ui.appeditor.context.EditingContextCommand;
import com.nokia.sdt.symbian.ui.appeditor.context.FormEditorEditingContext;
import com.nokia.sdt.symbian.ui.noexport.Messages;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;
import com.swtdesigner.ResourceManager;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.forms.*;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;
import org.osgi.framework.Bundle;

import java.text.MessageFormat;
import java.util.Collections;

public class ViewMasterDetailsBlock extends MasterDetailsBlock {

	private IManagedForm managedForm;
	private Section viewsSection;
	private SectionPart viewsSectionPart;
	private Section navigationPaneTabbingSection;
	private Button enableTabsButton;
	private Button moveDownButton;
	private Button moveUpButton;
	private Button removeButton;
	@SuppressWarnings("unused")
	private FormPage page;
	private AppEditorContext editorContext;
	private ViewModel model;
	private RadioButtonTableViewer viewer;
	private static final String lineSeparator = System.getProperty("line.separator"); //$NON-NLS-1$
		
	public ViewMasterDetailsBlock(FormPage page, AppEditorContext editorContext) {
		this.page = page;
		this.editorContext = editorContext;
				
		model = new ViewModel(editorContext.getRootDataModel());		
	}
	
	// we use this key class to force the details page
	// to bypass its internal cache and always call getPage
	static class PageKey {
		Object object;
	}

	@Override
	public void createContent(IManagedForm managedForm) {
		this.managedForm = managedForm;
		super.createContent(managedForm);
		detailsPart.setPageLimit(1);
		detailsPart.setPageProvider(new IDetailsPageProvider() {
			
			public Object getPageKey(Object object) {
				PageKey key = new PageKey();
				key.object = object;
				return key;
			}

			public IDetailsPage getPage(Object key) {
				IDetailsPage result = null;
				ViewModelEntry entry = null;
				
				if (SymbianModelUtils.getModelSDK(editorContext.getRootDataModel()) == SDKType.S60) {
				
					if (key instanceof PageKey && 
						((PageKey)key).object instanceof ViewModelEntry) {
						entry = (ViewModelEntry) ((PageKey)key).object;
					}
					if (entry != null) {
						if (model.isNaviTabbingEnabled() && entry.isAvkonView) {
							result = new AvkonViewDetailsPage(editorContext);
						} else {
							// Customize description for "no tabbing" page. 
							// Tabbing could be disabled because there's 
							// no ViewAppUi, or because the navitab is not 
							// configured, or because the current entry is not
							// an Avkon view
							boolean isLegacy = 	editorContext.getS60RootModelType() == S60RootModelType.LEGACY;
							NoTabbingDetailsPage noTabbingDetailsPage = new NoTabbingDetailsPage();
							if (isLegacy) {
								noTabbingDetailsPage.setDescription(Messages.getString("ViewMasterDetailsBlock.noTabbingBecauseLegacy")); //$NON-NLS-1$
							}
							else if (!model.isViewAppUi()) {
								noTabbingDetailsPage.setDescription(Messages.getString("ViewMasterDetailsBlock.notViewSwitchingApplication")); //$NON-NLS-1$
							} else if (!model.isNaviTabbingEnabled()) {
								StringBuffer buf = new StringBuffer();
								buf.append(Messages.getString("ViewMasterDetailsBlock.naviTabbingNotEnabled")); //$NON-NLS-1$
								if (!entry.isAvkonView) {
									buf.append(lineSeparator);
									buf.append(lineSeparator);
									buf.append(Messages.getString("ViewMasterDetailsBlock.notAnAvkonView")); //$NON-NLS-1$
								}
								noTabbingDetailsPage.setDescription(buf.toString());
							}
							else if (!entry.isAvkonView) {
								noTabbingDetailsPage.setDescription(Messages.getString("ViewMasterDetailsBlock.notAnAvkonView")); //$NON-NLS-1$
							}
							result = noTabbingDetailsPage;
						}
					}
				}
				return result;
			}
		});
		
		initModelListener();
	}
	
	private void initModelListener() {
		model.addModelListener(new ViewModel.IListener() {
			public void entryChanged(ViewModelEntry entry) {
				refreshEntry(entry);
				updateButtons();
			}

			public void initialViewChanged(ViewModelEntry oldInitialView, ViewModelEntry newInitialView) {
				refreshViewer();
			}

			public void entryAdded(ViewModelEntry entry) {
				refreshViewer();
			}

			public void entriesReordered() {
				refreshViewer();
			}

			public void entryRemoved(ViewModelEntry entry) {
				refreshViewer();
			}
			
			public void naviPaneTabbingChanged(boolean enabled) {
					enableTabsButton.setSelection(enabled);
					managedForm.fireSelectionChanged(viewsSectionPart, viewer.getSelection());
			}
		});

	}
	
	@Override
	protected void registerPages(DetailsPart detailsPart) {
	}

	protected void createMasterPart(final IManagedForm managedForm,
			Composite parent) {

		boolean isLegacy = editorContext.getS60RootModelType() == S60RootModelType.LEGACY;
		
		parent.setLayout(new FormLayout());
		String href = null; 
		SymbianModelUtils.SDKType sdkType = SymbianModelUtils.getModelSDK(editorContext.getRootDataModel());
		
		switch (sdkType) {
		case S60: 
			href = "/com.nokia.sdt.uidesigner.help/html/reference/app_editor/ref_ui_design.htm"; //$NON-NLS-1$
			break;
		case UIQ:
			href = "/com.nokia.carbide.cpp.uiq.help/html/reference/app_editor/ref_ui_design.htm"; //$NON-NLS-1$
			break;
		}	
			
		FormUtilities.addHelpToolbarItem(managedForm.getForm().getForm(),
				href, Messages.getString("ViewMasterDetailsBlock.helpTooltip")); //$NON-NLS-1$

		managedForm.getForm().setText(Messages.getString("ViewMasterDetailsBlock.pageTitle")); //$NON-NLS-1$
		FormToolkit toolkit = managedForm.getToolkit();

		final Composite rootComposite = toolkit.createComposite(parent, SWT.NONE);
		final FormData formData_1 = new FormData();
		formData_1.bottom = new FormAttachment(100, -5);
		formData_1.top = new FormAttachment(0, 0);
		rootComposite.setLayoutData(formData_1);
		rootComposite.setLayout(new FormLayout());
		toolkit.paintBordersFor(rootComposite);

		viewsSection = toolkit.createSection(rootComposite,
				Section.DESCRIPTION | Section.EXPANDED | Section.TITLE_BAR);
		final FormData formData_2 = new FormData();
		formData_2.right = new FormAttachment(100, 0);
		formData_2.bottom = new FormAttachment(0, 300);
		formData_2.left = new FormAttachment(0, 0);
		formData_2.top = new FormAttachment(0, 0);
		viewsSection.setLayoutData(formData_2);
		String descriptionKey = isLegacy? "ViewMasterDetailsBlock.legacyDescription" : "ViewMasterDetailsBlock.description"; //$NON-NLS-1$ //$NON-NLS-2$
		viewsSection.setDescription(Messages.getString(descriptionKey));
		viewsSection.setText(Messages.getString("ViewMasterDetailsBlock.viewsSectionTitle")); //$NON-NLS-1$

		final Composite composite = toolkit.createComposite(viewsSection,
				SWT.NONE);
		composite.setLayout(new FormLayout());
		toolkit.paintBordersFor(composite);
		viewsSection.setClient(composite);

		viewsSectionPart = new SectionPart(viewsSection);
	
		final Table table_1 = toolkit.createTable(composite, SWT.FULL_SELECTION);
		new TableColumn(table_1, SWT.LEFT);
		new TableColumn(table_1, SWT.LEFT);
		table_1.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Rectangle bounds = table_1.getBounds();
				TableColumn col = table_1.getColumn(0);
				boolean isLegacy = 	editorContext.getS60RootModelType() == S60RootModelType.LEGACY;
				int colWidth = isLegacy? 0 : 25;
				col.setWidth(colWidth);
				col = table_1.getColumn(1);
				col.setWidth(bounds.width-colWidth-3);
			}
		});
	
		table_1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.DEL) {
					removeView();
				}
			}
		});
		final FormData formData = new FormData();
		formData.right = new FormAttachment(0, 285);
		formData.bottom = new FormAttachment(100, -41);
		formData.top = new FormAttachment(0, 10);
		formData.left = new FormAttachment(0, 5);
		table_1.setLayoutData(formData);
		viewer = new RadioButtonTableViewer(table_1);
		viewer.setStyle(SWT.FLAT);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updateButtons();
				managedForm.fireSelectionChanged(viewsSectionPart, event.getSelection());
			}
		});
		
		viewer.addRadioStateListener(new RadioButtonTableViewer.IRadioStateListener() {
/*
			public void checkStateChanged(CheckStateChangedEvent event) {
				ViewModelEntry entry = (ViewModelEntry) event.getElement();
				if (viewer.getChecked(entry)) {
					if (!entryCanBeInitialView(entry)) {
						viewer.setChecked(entry, false);
					} else {
						if (model.getInitialView() != entry) {
							setInitialView(entry);
						}
					}
				}
				else {
					// enforce radio-button-like behavior, keep one
					// item checked at all times.
					viewer.setChecked(entry, true);
				}
			}
*/
			public void radioStateChanged(Object prevSelected, Object newSelected) {
				if (newSelected != null) {
					ViewModelEntry entry = (ViewModelEntry) newSelected;
					setInitialView(entry);
				}
			}
		});
		
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());

		final ImageHyperlink addNewUiImageHyperlink = toolkit.createImageHyperlink(composite, SWT.NONE);
		addNewUiImageHyperlink.setImage(ResourceManager.getPluginImage(UIPlugin.getDefault(), "icons/view.png")); //$NON-NLS-1$
		final FormData formData_4 = new FormData();
		formData_4.top = new FormAttachment(table_1, 8, SWT.DEFAULT);
		formData_4.bottom = new FormAttachment(table_1, 28, SWT.BOTTOM);
		formData_4.right = new FormAttachment(0, 155);
		formData_4.left = new FormAttachment(0, 5);
		addNewUiImageHyperlink.setLayoutData(formData_4);
		addNewUiImageHyperlink.setText(Messages.getString("ViewMasterDetailsBlock.AddNewDesignLink")); //$NON-NLS-1$
		addNewUiImageHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				newViewWizard();
			}
		});
		
		final Composite composite_1 = toolkit.createComposite(composite, SWT.NONE);
		final RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		rowLayout.justify = true;
		rowLayout.fill = true;
		composite_1.setLayout(rowLayout);
		final FormData formData_5 = new FormData();
		int bottomOffset = isLegacy? 80 : 135;
		formData_5.bottom = new FormAttachment(0, bottomOffset);
		formData_5.left = new FormAttachment(table_1, 4, SWT.DEFAULT);
		formData_5.right = new FormAttachment(100, -12);
		formData_5.top = new FormAttachment(table_1, 0, SWT.TOP);
		composite_1.setLayoutData(formData_5);
		toolkit.paintBordersFor(composite_1);

		removeButton = toolkit.createButton(composite_1, Messages.getString("ViewMasterDetailsBlock.RemoveButtonText"), SWT.NONE); //$NON-NLS-1$	
		removeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				removeView();
			}
		});
		
		if (!isLegacy) {
			
			moveUpButton = toolkit.createButton(composite_1, Messages.getString("ViewMasterDetailsBlock.moveUpButtonLabel"), SWT.NONE); //$NON-NLS-1$
			moveUpButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					moveUp();
				}
			});
			
			moveDownButton = toolkit.createButton(composite_1, Messages.getString("ViewMasterDetailsBlock.moveDownButtonLabel"), SWT.NONE); //$NON-NLS-1$
			moveDownButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					moveDown();
				}
			});
		}

		final Button importButton = toolkit.createButton(composite_1, Messages.getString("ViewMasterDetailsBlock.importButtonLabel"), SWT.NONE); //$NON-NLS-1$
		importButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				importViews();
			}
		});
		
		if (SymbianModelUtils.getModelSDK(editorContext.getRootDataModel()) == SDKType.S60 && editorContext.getS60RootModelType() != S60RootModelType.LEGACY) {
			
			navigationPaneTabbingSection = toolkit.createSection(rootComposite, Section.DESCRIPTION | Section.EXPANDED | Section.TITLE_BAR);
			final FormData formData_3 = new FormData();
			formData_3.right = new FormAttachment(100, -5);
			formData_3.left = new FormAttachment(0, 0);
			formData_3.top = new FormAttachment(viewsSection, 5, SWT.BOTTOM);
			formData_3.bottom = new FormAttachment(100, -5);
			navigationPaneTabbingSection.setLayoutData(formData_3);
			navigationPaneTabbingSection.setDescription(Messages.getString("ViewMasterDetailsBlock.NaviTabDescription")); //$NON-NLS-1$
			navigationPaneTabbingSection.setText(Messages.getString("ViewMasterDetailsBlock.naviTabSectionTitle")); //$NON-NLS-1$
			
			final Composite composite_2 = toolkit.createComposite(navigationPaneTabbingSection, SWT.NONE);
			navigationPaneTabbingSection.setClient(composite_2);
			toolkit.paintBordersFor(composite_2);
			
			enableTabsButton = toolkit.createButton(composite_2, Messages.getString("ViewMasterDetailsBlock.naviTabCheckboxTitle"), SWT.CHECK); //$NON-NLS-1$
			enableTabsButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					enableTabs(enableTabsButton.getSelection());
				}
			});
			enableTabsButton.setBounds(11, 12, 198, 23);
			if (model.isViewAppUi()) {
				enableTabsButton.setSelection(model.isNaviTabbingEnabled());
			} else {
				enableTabsButton.setEnabled(false);
			}
		}
		
		managedForm.addPart(viewsSectionPart);
		
		refreshViewer();
	}
	
	private void refreshViewer() {
		ViewModelEntry[] entries = model.getEntries();
		viewer.setInput(entries);	
/*
		for (ViewModelEntry entry : entries) {
			updateCheckForEntry(entry);
		}
*/
		updateButtons();
	}
		
	private void refreshEntry(ViewModelEntry entry) {
		// viewer.refresh(entry) & viewer.update(entry, null) do nothing if no widget
		viewer.refresh();
//		updateCheckForEntry(entry);
	}
	
	private boolean entryCanBeInitialView(ViewModelEntry entry) {
		boolean result = entry.specifier != null;
		if (result && SymbianModelUtils.getModelSDK(editorContext.getRootDataModel()) == SDKType.S60) {
			result = model.isViewAppUi();
			result = entry.isAvkonView;
		} else if (result && SymbianModelUtils.getModelSDK(editorContext.getRootDataModel()) == SDKType.UIQ) {
			IPropertySource entryProperties = Utilities.getPropertySource(entry.getViewReference().getEObject());
			result = (Boolean)entryProperties.getPropertyValue(UIQModelUtils.VIEWDIALOG_PROPERTY_ISAPPUICONTAINER);
		}
		
		return result;
	}
/*
	private void updateCheckForEntry(ViewModelEntry entry) {
		// Don't allow setting the initial view if this is a view app ui and entry is not
		// an Avkon view, or if the design file is missing.
		boolean grayed = !entryCanBeInitialView(entry);
		if (grayed != viewer.getGrayed(entry)) {
			viewer.setGrayed(entry, grayed);
		}
		boolean isInitial = model.getInitialView() == entry;
		if (isInitial != viewer.getChecked(entry)) {
			viewer.setChecked(entry, isInitial);	
		}
	}
*/	
	boolean selectReveal(Object object) {
		boolean result = false;
		Object element = object;
		if (object instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) object;
			element = selection.getFirstElement();
		}
		if (element instanceof ViewModelEntry) {
			viewer.reveal(element);
			IStructuredSelection currSelection = (IStructuredSelection) viewer.getSelection();
			if (currSelection.getFirstElement() != element) {
				viewer.setSelection(new StructuredSelection(element));
			}
			result = true;
		}
		else if (object == viewer) {
			result = true;
		}
		else if (object == enableTabsButton) {
			result = true;
		}
		else if (detailsPart != null) {
			result = detailsPart.setFormInput(object);
			if (!result) {
				IDetailsPage currentPage = detailsPart.getCurrentPage();
				if (currentPage != null) {
					result = currentPage.setFormInput(object);
				}
			}
		}
		return result;
	}
	
	private ViewModelEntry getSelectedEntry() {
		IStructuredSelection ss = (IStructuredSelection) viewer.getSelection();
		return (ViewModelEntry) (ss != null? ss.getFirstElement() : null);
	}
	
	private void updateButtons() {
		ViewModelEntry entry = getSelectedEntry();
		// disable all button commands for entries not in the model.
		// yes, we could rename/delete here, but let's reenforce the
		// idea that these other designs are external and not managed.
		if (entry != null && entry.viewReference != null) {
			removeButton.setEnabled(calculateRemoveButtonEnablement(entry));
			int index = model.indexOfEntry(entry);
			if (index >= 0 && index < model.getEntryCount() - 1) {
				setButtonEnabled(moveDownButton, true);
			} else {
				setButtonEnabled(moveDownButton, false);
			}
			setButtonEnabled(moveUpButton, index > 0);
		}
		else {
			removeButton.setEnabled(false);
			setButtonEnabled(moveUpButton, false);
			setButtonEnabled(moveDownButton, false);
		}
	}
	
	private boolean calculateRemoveButtonEnablement(ViewModelEntry selectedEntry) {
		IDesignerDataModel designerDataModel = editorContext.getRootDataModel();
		SDKType modelSDK = SymbianModelUtils.getModelSDK(designerDataModel);
		if (modelSDK == SDKType.S60) {
			return model.getEntryCount() > 1;
		} else if (modelSDK == SDKType.UIQ) {
			IPropertySource selectedEntryProperties = Utilities.getPropertySource(selectedEntry.getViewReference().getEObject());						
			if (!(Boolean)selectedEntryProperties.getPropertyValue("isAppUIContainer")) {
				return true;
			} else {
				int appUIContainersCount = 0;
				for (int i=0; i<model.getEntries().length; i++) {
					IPropertySource entryProperties = Utilities.getPropertySource(model.getEntries()[i].getViewReference().getEObject());
					if ((Boolean)entryProperties.getPropertyValue("isAppUIContainer"))
						appUIContainersCount++;
				}
				return appUIContainersCount > 1;
			}
		} else
			return false;
	}
	
	private void setButtonEnabled(Button btn, boolean enabled) {
		if (btn != null) {
			btn.setEnabled(enabled);
		}
	}
	
	private void removeView() {
		ViewModelEntry entry = getSelectedEntry();
		if (entry != null) {
			Command emfCommand = editorContext.getRootDataModel().createRemoveComponentInstancesCommand(
					Collections.singletonList(entry.viewReference.getEObject()));
			FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editorContext.getFormEditor(), viewer);
			EditingContextCommand wrapper = new EditingContextCommand(emfCommand, false, editingContext);
			editorContext.addAndExecuteCommand(wrapper);
		}
	}
	
	private void importViews() {
		String message = Messages.getString("ViewMasterDetailsBlock.importDesignsPrompt"); //$NON-NLS-1$
		IDesignerDataModelSpecifier importableModels[] = model.getImportableModels();
		ListSelectionDialog dialog = new ListSelectionDialog(
				editorContext.getEditorSite().getShell(),
				importableModels, new ArrayContentProvider(), 
				new LabelProvider(),
				message);
		dialog.setTitle(Messages.getString("ViewMasterDetailsBlock.importDesignsDialogTitle")); //$NON-NLS-1$
		int dlgResult = dialog.open();
		if (dlgResult == ListSelectionDialog.OK) {
			Object[] entriesToImport = dialog.getResult();
			if (entriesToImport != null) {
				
				CompoundCommand cc = new CompoundCommand();
				cc.setLabel(Messages.getString("ViewMasterDetailsBlock.importUndoLabel")); //$NON-NLS-1$
				for (int i = 0; i < entriesToImport.length; i++) {
					IDesignerDataModelSpecifier specifier = (IDesignerDataModelSpecifier) entriesToImport[i];
					Command emfCommand = SymbianModelUtils.addViewReference(
							editorContext.getRootDataModel(),
							specifier);
					if (emfCommand != null) {
						cc.append(emfCommand);
					}
				}
				// Wrap the command as a GEF command, and the editing context for undo
				StructuredSelection selection = new StructuredSelection(entriesToImport);
				FormEditorEditingContext editingContext = 
					new FormEditorEditingContext(null, editorContext.getFormEditor(), selection);
				EditingContextCommand wrappedCommand = new EditingContextCommand(
						cc, true, editingContext);
				editorContext.addAndExecuteCommand(wrappedCommand);
			}
		}
	}
	
	private void moveUp() {
		ViewModelEntry entry = getSelectedEntry();
		moveEntryDelta(entry, -1);
	}
	
	private void moveDown() {
		ViewModelEntry entry = getSelectedEntry();
		moveEntryDelta(entry, 1);
	}

	private void moveEntryDelta(ViewModelEntry entry, int delta) {
		if (entry != null ) {
			int currIndex = model.indexOfEntry(entry);
			int newIndex = currIndex + delta;
			EObject parent = model.getParentOfEntry(entry);
			if (parent != null && (newIndex >= 0 || newIndex < model.getEntryCount())) {
				int currParentIndex = model.indexOfEntryInParent(entry);
				ViewModelEntry moveTarget = model.getEntries()[newIndex];
				int targetParentIndex = model.indexOfEntryInParent(moveTarget);
				if (targetParentIndex > currParentIndex) {
					targetParentIndex++;
				}
				Command emfCommand = editorContext.getRootDataModel().createMoveComponentInstanceCommand(
						entry.viewReference.getEObject(), parent, targetParentIndex);
				FormEditorEditingContext context = new FormEditorEditingContext(null, editorContext.getFormEditor(), entry);
				EditingContextCommand wrapper = new EditingContextCommand(emfCommand, false, context);
				editorContext.addAndExecuteCommand(wrapper);		
			}
		}
	}
		
	private void setInitialView(ViewModelEntry entry) {
		IDesignerDataModel dm = editorContext.getRootDataModel();
		EObject appUiObj = SymbianModelUtils.findAppUi(dm);
		IPropertySource ps = ModelUtils.getPropertySource(appUiObj);
		
		SetPropertyCommand spc = new SetPropertyCommand(ps, SymbianModelUtils.APPUI_INITIALVIEW,
				entry.getViewReference().getName());
		FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editorContext.getFormEditor(), entry);
		EditingContextCommand wrapper = new EditingContextCommand(spc, false, editingContext);
		editorContext.addAndExecuteCommand(wrapper);
		refreshViewer();
	}
	
	private void enableTabs(boolean enable) {
		Command command = S60ModelUtils.enableNaviPaneTabbing(
				editorContext.getRootDataModel(), enable);
		if (command != null) {
			FormEditorEditingContext editingContext = new FormEditorEditingContext(null, 
					editorContext.getFormEditor(), enableTabsButton);
			EditingContextCommand wrapper = new EditingContextCommand(
					command, false, editingContext);
			editorContext.addAndExecuteCommand(wrapper);
		}
	}
	
	private void newViewWizard() {
		IDesignerDataModel model = editorContext.getRootDataModel();
		SDKType modelSDK = SymbianModelUtils.getModelSDK(model);
		String wizardClass = null;
		String bundleName = null;
		if (modelSDK == SDKType.S60) {
			bundleName = "com.nokia.sdt.series60.componentlibrary"; //$NON-NLS-1$
			wizardClass = "com.nokia.sdt.series60.viewwizard.ViewWizard"; //$NON-NLS-1$
		} else if (modelSDK == SDKType.UIQ) {
			bundleName = "com.nokia.carbide.cpp.uiq.ui"; //$NON-NLS-1$
			wizardClass = "com.nokia.carbide.cpp.uiq.ui.viewwizard.ViewWizard"; //$NON-NLS-1$
		}
		
		if (bundleName != null && wizardClass != null) {
			Bundle bundle = Platform.getBundle(bundleName);
			if (bundle != null) {
				String fmt = Messages.getString("ViewMasterDetailsBlock.cantLoadWizardFormatString"); //$NON-NLS-1$
				String params[] = {wizardClass};
				BasicNewResourceWizard wizard = (BasicNewResourceWizard) ClassUtils.loadAndCreateInstance(
						bundle, wizardClass, BasicNewResourceWizard.class,
						UIPlugin.getDefault(), fmt, params);
				if (wizard != null) {
					IStructuredSelection ss = null;
					IProject project = model.getModelSpecifier().getProjectContext().getProject();
					if (project != null) {
						ss = new StructuredSelection(project);
					}
					IWorkbenchWindow wbw = editorContext.getEditorSite().getWorkbenchWindow();
					wizard.init(wbw.getWorkbench(), ss);
					WizardDialog dialog = new WizardDialog(wbw.getShell(), wizard);
					dialog.open();
				}
			}
		}
	}

	protected void createToolBarActions(IManagedForm managedForm) {
	}
	/*
	static class ViewContentProvider implements IStructuredContentProvider {

		public Object[] getElements(Object inputElement) {
			Object[] result;
			if (inputElement instanceof ViewModel) {
				ViewModel viewModel = (ViewModel) inputElement;
				result = viewModel.getEntriesInModel();
			}
			else {
				result = new Object[0];
			}
			return result;
		}
	
		public void dispose() {
		}
		
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	*/
	
	class ViewContentProvider extends ArrayContentProvider implements RadioButtonTableViewer.IRadioButtonContentProvider {

		public Object getSelectedElement() {
			return model.getInitialView();
		}

		public boolean hasRadioButton(Object element) {
			boolean isLegacy = editorContext.getS60RootModelType() == S60RootModelType.LEGACY;
			return !isLegacy && entryCanBeInitialView((ViewModelEntry)element);
		}
	}
	
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
			
		public String getColumnText(Object obj, int index) {
			if (index == 0) return "";
			String result = obj.toString();
			if (obj instanceof ViewModelEntry) {
				ViewModelEntry entry = (ViewModelEntry) obj;
				String fmt = Messages.getString("ViewMasterDetailsBlock.ViewTextDecoration"); //$NON-NLS-1$
				String info = entry.mainComponentFriendlyName;
				if (info == null) {
					info = Messages.getString("ViewMasterDetailsBlock.missingUIDesignLabelDecoration"); //$NON-NLS-1$
				}
				Object params[] = {result, info};
				result = MessageFormat.format(fmt, params);
			}
			return result;
		}
		
		public Image getColumnImage(Object obj, int index) {
			return null;
		}		
	}

	public void dispose() {
		if (model != null) {
			model.dispose();
		}
	}

	public void initialDisplay() {
		// Select the first item so the details section is also displayed
		Object first = viewer.getElementAt(0);
		if (first != null) {
			viewer.setSelection(new StructuredSelection(first));
		}
	}

}
