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

import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.symbian.SymbianLanguage;
import com.nokia.sdt.symbian.dm.*;
import com.nokia.sdt.symbian.dm.SymbianModelUtils.LocalizationFileFormat;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.sdt.symbian.ui.appeditor.context.EditingContextCommand;
import com.nokia.sdt.symbian.ui.appeditor.context.FormEditorEditingContext;
import com.nokia.sdt.symbian.ui.noexport.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.*;

import java.util.ArrayList;
import java.util.Iterator;

public class LanguagesPage extends FormPage {

	private ILocalizedStringBundle bundle;
	private ArrayList<SymbianLanguage> enabledLanguages = new ArrayList<SymbianLanguage>();
	
	private SectionPart localizationFormatSectionPart;
	private SectionPart includedLanguagesSectionPart;
	private Button removeButton;
	private Button addButton;
	private Button dotLocButton;
	private Button dotRlsButton;
	private AppEditorContext editorContext;
	private CheckboxTableViewer viewer;
	
	private IDesignerData.IModelPropertyListener modelPropertyListener;
	private ILocalizedStringBundle.IListener bundleListener;
	
	private static final String HELP_CONTEXT_ID = 
		UIPlugin.PLUGIN_ID + "." + "appEditorLanguagesPageContext"; //$NON-NLS-1$

	public LanguagesPage(AppEditorContext editorContext) {
		super(editorContext.getFormEditor(), AppEditorContext.LANGUAGES_PAGE_ID, Messages.getString("LanguagesPage.1"));  //$NON-NLS-1$
		this.editorContext = editorContext;

		DesignerDataModel model = (DesignerDataModel) editorContext.getRootDataModel();
		DesignerDataModel modelImpl = (DesignerDataModel) model;
		IDesignerData designerData = modelImpl.getDesignerData();
		if (designerData != null) {
			bundle = designerData.getStringBundle();
		}
		Check.checkArg(bundle != null);
		
		initListeners();
	}
	
	@Override
	public void dispose() {
		DesignerDataModel dm = (DesignerDataModel) editorContext.getRootDataModel();
		dm.getDesignerData().removeModelPropertyListener(modelPropertyListener);
		bundle.removeListener(bundleListener);
		super.dispose();
	}
	
	private void initListeners() {
		modelPropertyListener = new IDesignerData.IModelPropertyListener() {
			public void propertyChanged(String propertyId, String propertyValue) {
				if (SymbianModelUtils.SYMBIAN_LOCALIZATION_FILE_FORMAT.equals(propertyId)) {
					updateFormatButtons();
				}
			}
		};
		DesignerDataModel dm = (DesignerDataModel) editorContext.getRootDataModel();
		dm.getDesignerData().addModelPropertyListener(modelPropertyListener);
		
		bundleListener = new ILocalizedStringBundle.IListener() {
			public void stringTableAdded(Language language) {
				SymbianLanguage sl = SymbianLanguage.getFromLanguageID(language.getLanguageCode());
				viewer.add(sl);
				cacheLanguagesList();
				updateButtons();
			}

			public void stringTableRemoved(Language language) {
				SymbianLanguage sl = SymbianLanguage.getFromLanguageID(language.getLanguageCode());
				viewer.remove(sl);
				cacheLanguagesList();
				updateButtons();
			}

			public void defaultLanguageChanged(Language newDefaultLanguage) {
				SymbianLanguage sl = SymbianLanguage.getFromLanguageID(newDefaultLanguage.getLanguageCode());
				viewer.setAllChecked(false);
				viewer.setChecked(sl, true);
			}
		};
		bundle.addListener(bundleListener);
	}

	private void cacheLanguagesList() {
		enabledLanguages.clear();
		SymbianLanguage allLanguages[] = SymbianLanguage.getLanguages();
		for (Iterator iter = bundle.getLocalizedStringTables().iterator();
			 iter.hasNext();) {
			ILocalizedStringTable lst = (ILocalizedStringTable) iter.next();
			int index = SymbianLanguage.getIndexFromLanguageID(lst.getLanguage().getLanguageCode());
			enabledLanguages.add(allLanguages[index]);
		}
	}
		
	SymbianLanguage getDisplayLanguage() {
		Language currLanguage = editorContext.getDisplayLanguage();
		int index = SymbianLanguage.getIndexFromLanguageID(currLanguage.getLanguageCode());
		return SymbianLanguage.getLanguages()[index];		
	}
	
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		final ScrolledForm form = managedForm.getForm();
		form.setText(Messages.getString("LanguagesPage.2")); //$NON-NLS-1$
		Composite body = form.getBody();
		body.setLayout(new TableWrapLayout());
		toolkit.paintBordersFor(body);
		String href = null;
			
		SymbianModelUtils.SDKType sdkType = SymbianModelUtils.getModelSDK(editorContext.getRootDataModel());
		switch (sdkType) {
		case S60: 
			href = "/com.nokia.sdt.uidesigner.help/html/reference/app_editor/ref_languages.htm";//$NON-NLS-1$
			break;
		case UIQ:
			href = "/com.nokia.carbide.cpp.uiq.help/html/reference/app_editor/ref_languages.htm";//$NON-NLS-1$
			break;
		}

		FormUtilities.addHelpToolbarItem(form.getForm(), href, Messages.getString("LanguagesPage.4")); //$NON-NLS-1$

		Section includedLanguagesSection = toolkit.createSection(body, Section.DESCRIPTION | Section.TITLE_BAR);
		includedLanguagesSectionPart = new SectionPart(includedLanguagesSection);
		managedForm.addPart(includedLanguagesSectionPart);
	
		final TableWrapData tableWrapData_1 = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP);
		tableWrapData_1.heightHint = 294;
		tableWrapData_1.grabHorizontal = true;
		includedLanguagesSection.setLayoutData(tableWrapData_1);
		includedLanguagesSection.setDescription(Messages.getString("LanguagesPage.5")); //$NON-NLS-1$
		includedLanguagesSection.setText(Messages.getString("LanguagesPage.6")); //$NON-NLS-1$

		final Composite composite = toolkit.createComposite(includedLanguagesSection, SWT.NONE);
		toolkit.adapt(composite);
		composite.setLayout(new FormLayout());
		includedLanguagesSection.setClient(composite);
		toolkit.paintBordersFor(composite);

		final Table table = toolkit.createTable(composite, SWT.CHECK);
		final FormData formData = new FormData();
		formData.bottom = new FormAttachment(100, -9);
		formData.left = new FormAttachment(0, 7);
		formData.right = new FormAttachment(0, 230);
		formData.top = new FormAttachment(0, 6);
		table.setLayoutData(formData);
		table.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.DEL) {
					removeLanguage();
				}
			}
		});
		
		viewer = new CheckboxTableViewer(table);
		viewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				SymbianLanguage language = (SymbianLanguage) event.getElement();
				if (viewer.getChecked(language)) {
					performSetDisplayLanguage(language);
				}
				else {
					// enforce radio-button-like behavior, keep one
					// item checked at all times.
					viewer.setChecked(language, true);					
				}
			}
		});
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new LabelProvider());
		viewer.setSorter(new ViewerSorter() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				SymbianLanguage l1 = (SymbianLanguage) e1;
				SymbianLanguage l2 = (SymbianLanguage) e2;
				return l1.code - l2.code;
			}
		});
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updateButtons();
			}
		});

		final Composite composite_1 = toolkit.createComposite(composite, SWT.NONE);
		toolkit.adapt(composite_1);
		final RowLayout rowLayout_1 = new RowLayout(SWT.VERTICAL);
		rowLayout_1.justify = true;
		rowLayout_1.fill = true;
		composite_1.setLayout(rowLayout_1);
		final FormData formData_1 = new FormData();
		formData_1.bottom = new FormAttachment(0, 70);
		formData_1.top = new FormAttachment(0, 6);
		formData_1.right = new FormAttachment(0, 295);
		formData_1.left = new FormAttachment(table, 5, SWT.DEFAULT);
		composite_1.setLayoutData(formData_1);
		toolkit.paintBordersFor(composite_1);

		addButton = toolkit.createButton(composite_1, Messages.getString("LanguagesPage.8"), SWT.NONE); //$NON-NLS-1$
		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				addLanguages();
			}
		});

		removeButton = toolkit.createButton(composite_1, Messages.getString("LanguagesPage.removeButton.text"), SWT.NONE); //$NON-NLS-1$
		removeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				removeLanguage();
			}
		});

		Section localizationFormatSection = toolkit.createSection(body, Section.DESCRIPTION | Section.TITLE_BAR);
		localizationFormatSectionPart = new SectionPart(localizationFormatSection);
		localizationFormatSection.setDescription(Messages.getString("LanguagesPage.10")); //$NON-NLS-1$
		final TableWrapData tableWrapData_2 = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		tableWrapData_2.heightHint = 89;
		tableWrapData_2.grabHorizontal = true;
		localizationFormatSection.setLayoutData(tableWrapData_2);
		localizationFormatSection.setText(Messages.getString("LanguagesPage.11")); //$NON-NLS-1$

		final Composite composite_2 = toolkit.createComposite(localizationFormatSection, SWT.NONE);
		final RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		rowLayout.marginTop = 8;
		composite_2.setLayout(rowLayout);
		toolkit.paintBordersFor(composite_2);
		localizationFormatSection.setClient(composite_2);

		dotLocButton = toolkit.createButton(composite_2, Messages.getString("LanguagesPage.13"), SWT.RADIO); //$NON-NLS-1$
		dotLocButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (dotLocButton.getSelection()) {
					performSetLocalizationFormat(SymbianModelUtils.LocalizationFileFormat.LOC);
				}
			}
		});

		dotRlsButton = toolkit.createButton(composite_2, Messages.getString("LanguagesPage.12"), SWT.RADIO); //$NON-NLS-1$
		dotRlsButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (dotRlsButton.getSelection()) {
					performSetLocalizationFormat(SymbianModelUtils.LocalizationFileFormat.RLS);
				}
			}
		});
		
		refresh();	
	}
		
	private void addLanguages() {
		ArrayList<Language> enabledLanguages = new ArrayList();
		for (Iterator iter = bundle.getLocalizedStringTables().iterator(); iter.hasNext();) {
			ILocalizedStringTable lst = (ILocalizedStringTable) iter.next();
			enabledLanguages.add(lst.getLanguage());
		}
		LanguagesDialog dialog = new LanguagesDialog(editorContext.getEditorSite().getShell(), enabledLanguages);
		int dialogResult = dialog.open();
		if (dialogResult == LanguagesDialog.OK) {
			SymbianLanguage[] selectedLanguages = dialog.getSelectedLanguages();
			if (selectedLanguages != null) {
				
				AddLanguagesCommand emfCommand = new AddLanguagesCommand(
						editorContext.getRootDataModel(), selectedLanguages);
				FormEditorEditingContext editingContext = new FormEditorEditingContext(
						null, editorContext.getFormEditor(), viewer);
				EditingContextCommand wrapper = new EditingContextCommand(emfCommand, false, editingContext);
				editorContext.addAndExecuteCommand(wrapper);
			}
		}
	}
	
	private void removeLanguage() {
		IStructuredSelection ss = (IStructuredSelection) viewer.getSelection();
		SymbianLanguage lang = (SymbianLanguage) ss.getFirstElement();
		if (lang != null) {
			
			CompoundCommand cc = new CompoundCommand();
			cc.setLabel(Messages.getString("LanguagesPage.removeLanguageUndoLabel")); //$NON-NLS-1$
			if (lang == getDisplayLanguage()) {
				// we always need a display language set, so pick a different
				// one here
				SymbianLanguage newDisplayLanguage = enabledLanguages.get(0);
				if (newDisplayLanguage == lang) {
					newDisplayLanguage = enabledLanguages.get(1);
				}
				SetDefaultLanguageCommand dlCommand = new SetDefaultLanguageCommand(
									editorContext.getRootDataModel(), 
									new Language(newDisplayLanguage.code));
				cc.append(dlCommand);
			}
			
			SymbianLanguage requestedLanguages[] = {lang};
			RemoveLanguagesCommand removeCommand = new RemoveLanguagesCommand(
					editorContext.getRootDataModel(), requestedLanguages);
			cc.append(removeCommand);
			
			FormEditorEditingContext editingContext = new FormEditorEditingContext(
					null, editorContext.getFormEditor(), viewer);
			
			EditingContextCommand wrapper = new EditingContextCommand(cc, false, editingContext);
			editorContext.addAndExecuteCommand(wrapper);
		}
	}
	
	/**
	 * Set display language as an undoable action
	 */
	private void performSetDisplayLanguage(SymbianLanguage language) {
		// Display language is a project property, we don't set it until
		// the user saves. At that time we must also update other open
		// editors
		if (language != null) {
			FormEditorEditingContext editingContext = new FormEditorEditingContext(
					null, editorContext.getFormEditor(), language);
			
			SetDefaultLanguageCommand command = new SetDefaultLanguageCommand(
					editorContext.getRootDataModel(), new Language(language.code));
			
			EditingContextCommand wrapper = new EditingContextCommand(command, false, editingContext);
			editorContext.addAndExecuteCommand(wrapper);
		}
	}
	
	/**
	 * Update the display language
	 */
/*
	void setDisplayLanguage(SymbianLanguage language) {
		SymbianLanguage currDisplayLanguage = getDisplayLanguage();
		if (language != currDisplayLanguage) {
			editorContext.setDisplayLanguage(new Language(language.code));
			viewer.setAllChecked(false);
			viewer.setChecked(language, true);
			includedLanguagesSectionPart.markDirty();
		}
	}
*/		
	void performSetLocalizationFormat(SymbianModelUtils.LocalizationFileFormat format) {
		Command emfCommand = SymbianModelUtils.setLocalizationFormat(editorContext.getRootDataModel(), format);
		if (emfCommand != null) {
			FormEditorEditingContext editingContext = new FormEditorEditingContext(null, 
					editorContext.getFormEditor(), 
					localizationFormatSectionPart);
			EditingContextCommand wrapper = new EditingContextCommand(emfCommand, false, editingContext);
			editorContext.addAndExecuteCommand(wrapper);
		}
	}
	
	private void refresh() {
		cacheLanguagesList();
		updateButtons();
		viewer.setInput(enabledLanguages);
		viewer.setAllChecked(false);
		viewer.setChecked(getDisplayLanguage(), true);
		viewer.refresh();
		
		updateFormatButtons();
	}
	
	private void updateFormatButtons() {
		LocalizationFileFormat localizationFormat = SymbianModelUtils.getLocalizationFormat(editorContext.getRootDataModel());
		if (localizationFormat == SymbianModelUtils.LocalizationFileFormat.LOC) {
			dotLocButton.setSelection(true);
			dotRlsButton.setSelection(false);
		} else {
			dotLocButton.setSelection(false);
			dotRlsButton.setSelection(true);
		}		
	}
	
	private void updateButtons() {
		IStructuredSelection ss = (IStructuredSelection) viewer.getSelection();
		if (ss.getFirstElement() != null && enabledLanguages.size() > 1) {
			removeButton.setEnabled(true);
		} else {
			removeButton.setEnabled(false);
		}
	}
	
	@Override
	public boolean selectReveal(Object object) {
		boolean result = false;
		Object element = object;
		if (object instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) object;
			element = selection.getFirstElement();
		}
		if (element instanceof SymbianLanguage) {
			viewer.reveal(element);
			viewer.setSelection(new StructuredSelection(element));
			result = true;
		}
		return result;
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		WorkbenchUtils.setHelpContextId(getPartControl(), HELP_CONTEXT_ID);
	}
}
