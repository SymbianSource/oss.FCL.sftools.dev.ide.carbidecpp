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

import com.nokia.sdt.datamodel.adapter.IComponentInstancePropertyListener;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.SetPropertyCommand;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.ui.appeditor.context.EditingContextCommand;
import com.nokia.sdt.symbian.ui.appeditor.context.FormEditorEditingContext;
import com.nokia.sdt.symbian.ui.images.DirectEditingUtilities;
import com.nokia.sdt.symbian.ui.noexport.Messages;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.*;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.views.properties.IPropertySource;

public class AvkonViewDetailsPage implements IDetailsPage {

	private Button imageRadioButton;
	private Button textRadioButton;
	private Button inTabGroupButton;
	private Button editImageButton;
	private AppEditorContext editorContext;
	private Text tabText;
	private boolean settingTabText;
	private IManagedForm managedForm;
	private ViewModelEntry currEntry;
	private IComponentInstancePropertyListener propertyListener;
	
	private enum TabState {
		NONE,
		TEXT,
		IMAGE
	}
	private String cleanTabText = Messages.getString("AvkonViewDetailsPage.0"); //$NON-NLS-1$
	private TabState cleanTabState;

	public AvkonViewDetailsPage(AppEditorContext editorContext) {
		this.editorContext = editorContext;
		
		propertyListener = new IComponentInstancePropertyListener() {
			public void propertyChanged(EObject componentInstance, Object propertyId) {
				modelPropertyChanged(propertyId);
			}
		};
	}

	public void initialize(IManagedForm managedForm) {
		this.managedForm = managedForm;
	}

	public void createContents(Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		parent.setLayout(gridLayout);

		final Section settingsSection = toolkit.createSection(parent,
				Section.DESCRIPTION | Section.EXPANDED | Section.TITLE_BAR);
		settingsSection.setDescription(Messages.getString("ViewDetails.description")); //$NON-NLS-1$
		settingsSection.setLayoutData(new GridData(GridData.FILL_BOTH));
		settingsSection.setText(Messages.getString("ViewDetails.title")); //$NON-NLS-1$

		final Composite composite = toolkit.createComposite(settingsSection, SWT.NONE);
		composite.setLayout(new TableWrapLayout());
		composite.setLocation(-8, 15);
		toolkit.paintBordersFor(composite);
		settingsSection.setClient(composite);

		inTabGroupButton = toolkit.createButton(composite, Messages.getString("ViewDetails.isInAppUiButton"), SWT.CHECK); //$NON-NLS-1$
		inTabGroupButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				if (btn.getSelection()) {
					performSetTabState(TabState.TEXT);
				} else {
					performSetTabState(TabState.NONE);
				}
			}
		});

		final Section tabDetailsSection = toolkit.createSection(composite, Section.DESCRIPTION | Section.EXPANDED);
		tabDetailsSection.setDescription(Messages.getString("ViewDetails.tabDetailsDescription")); //$NON-NLS-1$
		final TableWrapData tableWrapData = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP);
		tableWrapData.maxWidth = 200;
		tableWrapData.heightHint = 149;
		tabDetailsSection.setLayoutData(tableWrapData);
		tabDetailsSection.setText(Messages.getString("ViewDetails.tabDetailsTitle")); //$NON-NLS-1$
		toolkit.createCompositeSeparator(tabDetailsSection);

		final Composite composite_1 = toolkit.createComposite(tabDetailsSection, SWT.NONE);
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		composite_1.setLayout(gridLayout_1);
		toolkit.paintBordersFor(composite_1);
		tabDetailsSection.setClient(composite_1);

		textRadioButton = toolkit.createButton(composite_1, Messages.getString("AvkonViewDetailsPage.textRadioButton"), SWT.RADIO); //$NON-NLS-1$
		textRadioButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (textRadioButton.getSelection()) {
					performSetTabState(TabState.TEXT);
				}
			}
		});
		textRadioButton.setLayoutData(new GridData());

		tabText = toolkit.createText(composite_1, null);
		tabText.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		tabText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if (!settingTabText) {
					textChanged();
				}
			}
		});

		imageRadioButton = toolkit.createButton(composite_1, Messages.getString("AvkonViewDetailsPage.imageButton"), SWT.RADIO); //$NON-NLS-1$
		imageRadioButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (imageRadioButton.getSelection()) {
					performSetTabState(TabState.IMAGE);
				}
			}
		});
		imageRadioButton.setLayoutData(new GridData());

		editImageButton = toolkit.createButton(composite_1, Messages.getString("AvkonViewDetails.editImageButton.text"), SWT.NONE); //$NON-NLS-1$
		editImageButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				editTabImage();
			}
		});
		final GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridData.widthHint = 68;
		editImageButton.setLayoutData(gridData);
		new Label(composite_1, SWT.NONE);
		
		settingsSection.pack();

		}

	public void dispose() {
		setCurrentEntry(null);
	}

	public void setFocus() {
	}
	
	private void textChanged() {
		if (tabText.getText().length() > 0) {
			performSetTabState(TabState.TEXT);
		} else {
			editorContext.getFormEditor().editorDirtyStateChanged();
		}
	}
	
	private void editTabImage() {
		// open image editing dialog
		org.eclipse.gef.commands.Command gefCommand = DirectEditingUtilities.editImageProperty(
				editorContext.getEditorSite().getShell(),
				currEntry.getViewReference().getEObject(),
				SymbianModelUtils.VIEWREF_TAB_IMAGE);
		
		if (gefCommand != null) {
			
			// Make an EMF compound command to hold the 'set in tab group' and 'set tab text' EMF commands
			CompoundCommand emfCompound = new CompoundCommand();
			if (!isInTabGroup()) {
				emfCompound.append(setInTabGroupCommand(true));
			}
			
			// since text overrides image we must clear the text. First commit it so undo will restore to that
			IPropertySource ps = getViewRefPropertySource();
			ps.setPropertyValue(SymbianModelUtils.VIEWREF_TAB_TEXT, tabText.getText());
			emfCompound.append(setTabTextCommand(null));
			
			// Make a GEF compound command to hold the 'image property' command and our EMF compound command
			org.eclipse.gef.commands.CompoundCommand gefCompoundCommand = new org.eclipse.gef.commands.CompoundCommand();
			EditingContextCommand wrapper1 = new EditingContextCommand(emfCompound, false, null);
			gefCompoundCommand.add(wrapper1);
			gefCompoundCommand.add(gefCommand);
			
			// Put all the commands in a final wrapper that has the editing context.
			FormEditorEditingContext editingContext = new FormEditorEditingContext(null, editorContext.getFormEditor(), currEntry);
			EditingContextCommand wrapper2 = new EditingContextCommand(gefCompoundCommand, false, editingContext);
			editorContext.addAndExecuteCommand(wrapper2);
		}		
	}

	private boolean isInTabGroup() {
		boolean result = false;
		if (currEntry != null && currEntry.getViewReference() != null && 
				currEntry.isAvkonView()) {
			IPropertySource ps = getViewRefPropertySource();
			result = Boolean.valueOf((Boolean)ps.getPropertyValue(SymbianModelUtils.VIEWREF_IN_TAB_GROUP));
		}
		return result;
	}
	
	private String getModelTabTextValue() {
		IPropertySource ps = getViewRefPropertySource();
		return (String)ps.getPropertyValue(SymbianModelUtils.VIEWREF_TAB_TEXT);
	}
		
	private boolean imageValueIsSet() {
		IPropertySource ps = getViewRefPropertySource();
		boolean result = ps.isPropertySet(SymbianModelUtils.VIEWREF_TAB_IMAGE);
		if (result) {
			// distinguish between compound property with all defaults, vs
			// actually set
			IPropertySource imagePS = (IPropertySource) ps.getPropertyValue(SymbianModelUtils.VIEWREF_TAB_IMAGE);
			result = imagePS.isPropertySet(SymbianModelUtils.IMAGE_COMPOUND_FILE);
		}
		return result;
	}
	
	private void modelPropertyChanged(Object propertyId) {
		if (SymbianModelUtils.VIEWREF_TAB_TEXT.equals(propertyId)) {
			String textValue = getModelTabTextValue();
			setTabText(textValue);
			if (!TextUtils.isEmpty(textValue)) {
				setTabState(TabState.TEXT);
			}
			
		} else if (SymbianModelUtils.VIEWREF_TAB_IMAGE.equals(propertyId)) {
			if (imageValueIsSet()) {
				setTabState(TabState.IMAGE);
			}
		} else if (SymbianModelUtils.VIEWREF_IN_TAB_GROUP.equals(propertyId)) {
			boolean inTabGroup = isInTabGroup();
			if (inTabGroup) {
				TabState tabState = getTabState();
				if (tabState == TabState.NONE) {
					tabState = TabState.TEXT;
				}
				setTabState(tabState);
			}
			else {
				setTabText(null);
				setTabState(TabState.NONE);
			}
		}
	}
	
	private SetPropertyCommand setInTabGroupCommand(boolean inTabGroup) {
		return new SetPropertyCommand(getViewRefPropertySource(), 
				SymbianModelUtils.VIEWREF_IN_TAB_GROUP, 
				Boolean.valueOf(inTabGroup).toString());
	}
	
	private SetPropertyCommand setTabTextCommand(String textValue) {
		return new SetPropertyCommand(getViewRefPropertySource(),
				SymbianModelUtils.VIEWREF_TAB_TEXT, textValue);
	}
	
	private SetPropertyCommand setTabImageCommand(Object imageValue) {
		return new SetPropertyCommand(getViewRefPropertySource(),
				SymbianModelUtils.VIEWREF_TAB_IMAGE, imageValue);
	}

	private void performSetTabState(TabState state) {
		CompoundCommand cc = new CompoundCommand();
		cc.setLabel(Messages.getString("AvkonViewDetailsPage.setTabGroupUndoLabel")); //$NON-NLS-1$
		IPropertySource ps = getViewRefPropertySource();
		switch (state) {
		case NONE:
			if (isInTabGroup()) {
				cc.append(setInTabGroupCommand(false));
				// commit current tab text so undo will restore it
				ps.setPropertyValue(SymbianModelUtils.VIEWREF_TAB_TEXT, tabText.getText());
				cc.append(setTabTextCommand(null));
				cc.append(setTabImageCommand(null));
			}
			break;
			
		case TEXT: {
			if (!isInTabGroup()) {
				cc.append(setInTabGroupCommand(true));
			}
			if (imageValueIsSet()) {
				cc.append(setTabImageCommand(null));
			}
			cc.append(setTabTextCommand(tabText.getText()));
		}
		break;
		
		case IMAGE: {
			if (!isInTabGroup()) {
				cc.append(setInTabGroupCommand(true));
			}
			// commit current tab text so undo will restore it
			ps.setPropertyValue(SymbianModelUtils.VIEWREF_TAB_TEXT, tabText.getText());
			if (!TextUtils.isEmpty(getModelTabTextValue())) {
				cc.append(setTabTextCommand(null));
			}
		}
		break;
		}
		
		setTabState(state);
		if (!cc.isEmpty()) {
			FormEditorEditingContext editingContext = new FormEditorEditingContext(
					null, editorContext.getFormEditor(), currEntry);
			EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
			editorContext.addAndExecuteCommand(wrapper);
		}
		editorContext.getFormEditor().editorDirtyStateChanged();
	}

		
	private void setTabText(String text) {
		settingTabText = true;
		if (text != null) {
			String currText = tabText.getText();
			if (!text.equals(currText)) {
				tabText.setText(text);
			}
		} else {
			tabText.setText(""); //$NON-NLS-1$
		}
		settingTabText = false;
	}

	/**
	 * Update from model object
	 */
	public void refresh() {
		TabState tabState = TabState.NONE;
		if (currEntry == null || currEntry.getViewReference() == null || !currEntry.isAvkonView()) {
			setTabText(null);
		}
		else {
			boolean inTabGroup = isInTabGroup();
			if (inTabGroup) {
				String modelTabText = getModelTabTextValue();
				boolean hasImage = imageValueIsSet();
				if (!TextUtils.isEmpty(modelTabText) || !hasImage) {
					tabState = TabState.TEXT;
					setTabText(modelTabText);
				}
				else {
					tabState = TabState.IMAGE;
					setTabText(null);
				}
			}
			else {
				setTabText(null);
			}
		}
		cleanTabText = tabText.getText();
		setTabState(tabState);
		cleanTabState = tabState;
	}
	
	private void setTabState(TabState state) {
		inTabGroupButton.setSelection(state != TabState.NONE);
		textRadioButton.setSelection(state == TabState.TEXT);
		imageRadioButton.setSelection(state == TabState.IMAGE);
	}
	
	private TabState getTabState() {
		TabState result = TabState.NONE;
		if (textRadioButton.getSelection()) {
			result = TabState.TEXT;
		} else if (imageRadioButton.getSelection()) {
			result = TabState.IMAGE;
		}
		return result;
	}

	public boolean setFormInput(Object input) {
		boolean result = false;
		if (tabText == input) {
			tabText.setFocus();
			tabText.selectAll();
			result = true;
		}
		else if (inTabGroupButton == input) {
			inTabGroupButton.setFocus();
			result = true;
		}
		return result;
	}
	
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ss = (IStructuredSelection) selection;
		Object obj = ss.getFirstElement();
		if (obj instanceof ViewModelEntry) {
			commit(false);
			setCurrentEntry((ViewModelEntry)obj);
			refresh();
		}
	}
	
	private void setCurrentEntry(ViewModelEntry entry) {
		if (currEntry != null && currEntry.getViewReference() != null) {
			currEntry.getViewReference().removePropertyListener(propertyListener);
		}
		currEntry = entry;
		if (currEntry != null && currEntry.getViewReference() != null) {
			currEntry.getViewReference().addPropertyListener(propertyListener);
		}
	}
	
	private IPropertySource getViewRefPropertySource() {
		IPropertySource result = null;
		if (currEntry != null && currEntry.getViewReference() != null) {
			result = ModelUtils.getPropertySource(currEntry.getViewReference().getEObject());
		}
		return result;
	}

	public void commit(boolean onSave) {
	}

	public boolean isDirty() {
		return false;
	}

	public boolean isStale() {
		return false;
	}
}
