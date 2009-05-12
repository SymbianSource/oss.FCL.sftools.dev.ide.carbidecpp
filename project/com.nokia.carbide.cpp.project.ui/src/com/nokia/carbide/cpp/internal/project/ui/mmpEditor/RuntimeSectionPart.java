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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.*;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.ChangeFlagSettingOperation;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.CompositeOperation;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.EMMPListSelector;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs.CapabilitiesDialog;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.ui.editor.FormEditorEditingContext;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import java.util.*;
import java.util.List;

public class RuntimeSectionPart extends SectionPart {

	private Label stackSizeLabel;
	private Label vendorIDLabel;
	private Label secureIDLabel;
	private Label maxHeapSizeLabel;
	private Label minHeapSizeLabel;
	private Label capabilitiesLabel;
	private final MMPEditorContext editorContext;
	private final ControlManager controlManager;
	private Text vendorIDText;
	private Text secureIDText;
	private ComboViewer processPriorityViewer;
	private Text stackSizeText;
	private Text maxHeapSizeText;
	private Text capabilitiesText;
	private Button chooseButton;
	private Text minHeapSizeText;
	private Button debuggableButton;
	private ComboViewer pagingModeViewer;
	private CapabilitiesDialog capabilitiesDialog;
	
	static final String DEFAULT_MIN_HEAP = "0x1000"; //$NON-NLS-1$
	static final String DEFAULT_MAX_HEAP = "0x100000"; //$NON-NLS-1$
	static final String[] PROCESS_PRIORITIES = new String[] {"", "low", "background", "foreground", "high", "windowserver", "fileserver", "realtimeserver", "supervisor"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$
	static final String[] PAGING_MODE_VALUES = new String[] {"not specified", "paged", "unpaged"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	static final int PAGING_NOT_SPECIFIED = 0;
	static final int PAGING_ENABLED = 1;
	static final int PAGING_DISABLED = 2;
	
	/**
	 * Create the SectionPart
	 * @param parent
	 * @param toolkit
	 * @param style
	 */
	public RuntimeSectionPart(Composite parent, FormToolkit toolkit, int style, 
			MMPEditorContext editorContext, ControlManager controlManager) {
		super(parent, toolkit, style);
		this.editorContext = editorContext;
		this.controlManager = controlManager;
		createClient(getSection(), toolkit);
	}

	/**
	 * Fill the section
	 */
	private void createClient(Section section, FormToolkit toolkit) {
		section.setText(Messages.RuntimeSectionPart_runtimeSectionTitle);
		Composite container = toolkit.createComposite(section);
		toolkit.paintBordersFor(container);
		final FormLayout formLayout = new FormLayout();
		formLayout.marginHeight = 2;
		formLayout.marginWidth = 2;
		container.setLayout(formLayout);
		//
		section.setClient(container);

		capabilitiesLabel = toolkit.createLabel(container, Messages.RuntimeSectionPart_capabilitiesLabel, SWT.NONE);
		final FormData formData = new FormData();
		formData.right = new FormAttachment(0, 70);
		formData.top = new FormAttachment(0, 5);
		formData.bottom = new FormAttachment(0, 25);
		capabilitiesLabel.setLayoutData(formData);

		capabilitiesText = toolkit.createText(container, null, SWT.V_SCROLL | SWT.WRAP);
		capabilitiesText.setToolTipText(Messages.RuntimeSectionPart_editCapabilitiesTooltip);
		final FormData formData_1 = new FormData();
		formData_1.right = new FormAttachment(0, 245);
		formData_1.left = new FormAttachment(capabilitiesLabel, 0, SWT.LEFT);
		capabilitiesText.setLayoutData(formData_1);

		chooseButton = toolkit.createButton(container, Messages.RuntimeSectionPart_chooseCapabilitiesBtn, SWT.NONE);
		formData_1.bottom = new FormAttachment(chooseButton, 37, SWT.BOTTOM);
		formData_1.top = new FormAttachment(chooseButton, 5, SWT.DEFAULT);
		chooseButton.setToolTipText(Messages.RuntimeSectionPart_chooseCapabilitiesTooltip);
		final FormData formData_2 = new FormData();
		formData_2.bottom = new FormAttachment(capabilitiesLabel, 23, SWT.TOP);
		formData_2.top = new FormAttachment(capabilitiesLabel, 0, SWT.TOP);
		formData_2.left = new FormAttachment(0, 130);
		formData_2.right = new FormAttachment(capabilitiesText, 0, SWT.RIGHT);
		chooseButton.setLayoutData(formData_2);
		chooseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				doChooseCapabilities();
			}
		});

		minHeapSizeLabel = toolkit.createLabel(container, Messages.RuntimeSectionPart_minHeapLabel, SWT.NONE);
		final FormData formData_3 = new FormData();
		formData_3.top = new FormAttachment(capabilitiesText, 10, SWT.DEFAULT);
		formData_3.right = new FormAttachment(0, 130);
		formData_3.left = new FormAttachment(0, 7);
		minHeapSizeLabel.setLayoutData(formData_3);

		minHeapSizeText = toolkit.createText(container, null, SWT.NONE);
		minHeapSizeText.setToolTipText(Messages.RuntimeSectionPart_minHeapTooltip);
		final FormData formData_4 = new FormData();
		formData_4.top = new FormAttachment(minHeapSizeLabel, -2, SWT.TOP);
		formData_4.right = new FormAttachment(capabilitiesText, 0, SWT.RIGHT);
		minHeapSizeText.setLayoutData(formData_4);

		maxHeapSizeLabel = toolkit.createLabel(container, Messages.RuntimeSectionPart_maxHeapLabel, SWT.NONE);
		formData_4.left = new FormAttachment(maxHeapSizeLabel, 5, SWT.RIGHT);
		final FormData formData_5 = new FormData();
		formData_5.top = new FormAttachment(minHeapSizeLabel, 12, SWT.DEFAULT);
		formData_5.right = new FormAttachment(minHeapSizeLabel, 0, SWT.RIGHT);
		formData_5.left = new FormAttachment(minHeapSizeLabel, 0, SWT.LEFT);
		maxHeapSizeLabel.setLayoutData(formData_5);

		maxHeapSizeText = toolkit.createText(container, null, SWT.NONE);
		maxHeapSizeText.setToolTipText(Messages.RuntimeSectionPart_maxHeapTooltip);
		final FormData formData_6 = new FormData();
		formData_6.left = new FormAttachment(minHeapSizeText, 0, SWT.LEFT);
		formData_6.top = new FormAttachment(maxHeapSizeLabel, 0, SWT.TOP);
		formData_6.right = new FormAttachment(minHeapSizeText, 0, SWT.RIGHT);
		maxHeapSizeText.setLayoutData(formData_6);

		stackSizeLabel = toolkit.createLabel(container, Messages.RuntimeSectionPart_stackSizeLabel, SWT.NONE);
		final FormData formData_7 = new FormData();
		formData_7.top = new FormAttachment(0, 125);
		formData_7.right = new FormAttachment(maxHeapSizeLabel, 0, SWT.RIGHT);
		formData_7.left = new FormAttachment(maxHeapSizeLabel, 0, SWT.LEFT);
		stackSizeLabel.setLayoutData(formData_7);

		stackSizeText = toolkit.createText(container, null, SWT.NONE);
		stackSizeText.setToolTipText(Messages.RuntimeSectionPart_stackSizeTooltip);
		final FormData formData_8 = new FormData();
		formData_8.left = new FormAttachment(maxHeapSizeText, 0, SWT.LEFT);
		formData_8.top = new FormAttachment(stackSizeLabel, 0, SWT.TOP);
		formData_8.right = new FormAttachment(maxHeapSizeText, 0, SWT.RIGHT);
		stackSizeText.setLayoutData(formData_8);

		Label processPriorityLabel;
		processPriorityLabel = toolkit.createLabel(container, Messages.RuntimeSectionPart_processPriorityLabel, SWT.NONE);
		final FormData formData_9 = new FormData();
		formData_9.right = new FormAttachment(stackSizeLabel, 83, SWT.LEFT);
		formData_9.left = new FormAttachment(stackSizeLabel, 0, SWT.LEFT);
		processPriorityLabel.setLayoutData(formData_9);

		processPriorityViewer = new ComboViewer(container, SWT.READ_ONLY|SWT.FLAT);
		processPriorityViewer.setContentProvider(new ArrayContentProvider());
		processPriorityViewer.setLabelProvider(new ProcessPriorityLabelProvider());
		processPriorityViewer.setInput(PROCESS_PRIORITIES);
		Combo processPriorityCombo;
		processPriorityCombo = processPriorityViewer.getCombo();
		formData_9.top = new FormAttachment(processPriorityCombo, -18, SWT.TOP);
		formData_9.bottom = new FormAttachment(processPriorityCombo, -5, SWT.TOP);
		processPriorityCombo.setToolTipText(Messages.RuntimeSectionPart_processPriorityTooltip);
		final FormData formData_10 = new FormData();
		formData_10.top = new FormAttachment(0, 164);
		formData_10.bottom = new FormAttachment(0, 185);
		formData_10.right = new FormAttachment(stackSizeText, 0, SWT.RIGHT);
		formData_10.left = new FormAttachment(processPriorityLabel, 0, SWT.LEFT);
		processPriorityCombo.setLayoutData(formData_10);
		toolkit.adapt(processPriorityCombo, true, true);

		secureIDLabel = toolkit.createLabel(container, Messages.RuntimeSectionPart_secureIDLabel, SWT.NONE);
		final FormData formData_11 = new FormData();
		formData_11.top = new FormAttachment(0, 200);
		formData_11.left = new FormAttachment(processPriorityCombo, 0, SWT.LEFT);
		secureIDLabel.setLayoutData(formData_11);
		toolkit.adapt(secureIDLabel, true, true);

		secureIDText = toolkit.createText(container, null, SWT.NONE);
		secureIDText.setToolTipText(Messages.RuntimeSectionPart_secureIDTooltip);
		final FormData formData_12 = new FormData();
		formData_12.top = new FormAttachment(secureIDLabel, 0, SWT.TOP);
		formData_12.right = new FormAttachment(processPriorityCombo, 0, SWT.RIGHT);
		formData_12.left = new FormAttachment(stackSizeText, 0, SWT.LEFT);
		secureIDText.setLayoutData(formData_12);

		vendorIDLabel = toolkit.createLabel(container, Messages.RuntimeSectionPart_vendorIDLabel, SWT.NONE);
		formData.left = new FormAttachment(vendorIDLabel, 0, SWT.LEFT);
		FormData formData_13;
		formData_13 = new FormData();
		formData_13.top = new FormAttachment(0, 230);
		formData_13.right = new FormAttachment(secureIDLabel, 54, SWT.LEFT);
		formData_13.left = new FormAttachment(secureIDLabel, 0, SWT.LEFT);
		vendorIDLabel.setLayoutData(formData_13);

		vendorIDText = toolkit.createText(container, null, SWT.NONE);
		vendorIDText.setToolTipText(Messages.RuntimeSectionPart_vendorIDTooltip);
		final FormData formData_14 = new FormData();
		formData_14.top = new FormAttachment(vendorIDLabel, 0, SWT.TOP);
		formData_14.left = new FormAttachment(secureIDText, 0, SWT.LEFT);
		formData_14.right = new FormAttachment(secureIDText, 0, SWT.RIGHT);
		vendorIDText.setLayoutData(formData_14);

		debuggableButton = toolkit.createButton(container, Messages.RuntimeSectionPart_debuggableButton, SWT.CHECK);
		debuggableButton.setToolTipText(Messages.RuntimeSectionPart_debuggableToolTip);
		final FormData formData_15 = new FormData();
		formData_15.top = new FormAttachment(0, 260);
		formData_15.left = new FormAttachment(vendorIDLabel, 0, SWT.LEFT);
		debuggableButton.setLayoutData(formData_15);
		
		Label demandPagingLabel;
		demandPagingLabel = toolkit.createLabel(container, Messages.RuntimeSectionPart_pagingModeLabel, SWT.NONE);
		final FormData formData_16 = new FormData();
		formData_16.left = new FormAttachment(stackSizeLabel, 0, SWT.LEFT);
		demandPagingLabel.setLayoutData(formData_16);

		pagingModeViewer = new ComboViewer(container, SWT.READ_ONLY|SWT.FLAT);
		pagingModeViewer.setContentProvider(new ArrayContentProvider());
		pagingModeViewer.setLabelProvider(new LabelProvider());
		pagingModeViewer.setInput(PAGING_MODE_VALUES);
		Combo pagingModeCombo;
		pagingModeCombo = pagingModeViewer.getCombo();
		formData_16.top = new FormAttachment(pagingModeCombo, -18, SWT.TOP);
		formData_16.bottom = new FormAttachment(pagingModeCombo, -5, SWT.TOP);
		pagingModeCombo.setToolTipText(Messages.RuntimeSectionPart_pagingModeToolTip);
		final FormData formData_17 = new FormData();
		formData_17.top = new FormAttachment(0, 300);
		formData_17.bottom = new FormAttachment(0, 320);
		formData_17.right = new FormAttachment(stackSizeText, 0, SWT.RIGHT);
		formData_17.left = new FormAttachment(demandPagingLabel, 0, SWT.LEFT);
		pagingModeCombo.setLayoutData(formData_17);
		toolkit.adapt(pagingModeCombo, true, true);

		hookControls();
		refresh();
	}
	
	private static class ProcessPriorityLabelProvider extends LabelProvider {

		@Override
		public String getText(Object element) {
			String result = element != null? element.toString() : ""; //$NON-NLS-1$
			if (result.equals("")) { //$NON-NLS-1$
				result = Messages.RuntimeSectionPart_unspecifiedProcessPriorityLabel;
			}
			return result;
		}
		
	}
	
	/**
	 * Special handling for pair of controls that handles two list items
	 */
	private class HeapSizeControlHandler extends ControlHandler	 {
		public HeapSizeControlHandler(Control control) {
			super(control, new NumberValidator(0, Integer.MAX_VALUE, true, Messages.RuntimeSectionPart_heapSizeValidationErr));
		}

		@Override
		public void addListItems(List items) {
			refresh();
		}

		@Override
		public void removeListItems(List items) {
			refresh();
		}

		@Override
		protected void doRefresh() {
			List<String> heapSettings = editorContext.mmpView.getListArgumentSettings().get(EMMPStatement.EPOCHEAPSIZE);
			String minHeap = null;
			String maxHeap = null;
			if (heapSettings != null && heapSettings.size() > 0) {
				minHeap = heapSettings.get(0);
				if (heapSettings.size() > 1) {
					maxHeap = heapSettings.get(1);
				}
			}
			ControlHandler minHandler = ControlHandler.getHandlerForControl(minHeapSizeText);
			minHandler.storeText(minHeap);
			Text minText = (Text) minHandler.getControl();
			if (minHeap != null) minText.setSelection(minHeap.length());
			ControlHandler maxHandler = ControlHandler.getHandlerForControl(maxHeapSizeText);
			maxHandler.storeText(maxHeap);
			Text maxText = (Text) maxHandler.getControl();
			if (maxHeap != null) maxText.setSelection(maxHeap.length());
		}
	}

	/**
	 * Special handling for paging model control.
	 */
	private class PagingModeControlHandler extends ControlHandler {

		public PagingModeControlHandler(Control control) {
			super(control, null);
		}

		@Override
		protected void controlSelected() {
			refresh();
		}

		@Override
		protected void doRefresh() {
			boolean isPaged = editorContext.mmpView.getFlags().contains(EMMPStatement.PAGED);
			boolean isUnpaged = editorContext.mmpView.getFlags().contains(EMMPStatement.UNPAGED);
			ControlHandler pagingModeHandler = ControlHandler.getHandlerForControl(pagingModeViewer.getControl());
			if (!isPaged && !isUnpaged) {
				pagingModeHandler.storeText(PAGING_MODE_VALUES[PAGING_NOT_SPECIFIED]);
			}
			else if (isPaged) {
				pagingModeHandler.storeText(PAGING_MODE_VALUES[PAGING_ENABLED]);
			}
			else if (isUnpaged) {
				pagingModeHandler.storeText(PAGING_MODE_VALUES[PAGING_DISABLED]);
			}
		}

		@Override
		protected void textModified() {
			super.textModified();
			pagingModeSelectionChanged();
		}
	}
	
	private void hookControls() {
		ControlHandler handler = new ListSettingTextHandler(capabilitiesText, 
				new FormEditorEditingContext(editorContext.editor, capabilitiesText),
				EMMPStatement.CAPABILITY, editorContext);
		handler.setLabel(capabilitiesLabel);
		handler.setListDelimiter(" "); //$NON-NLS-1$
		controlManager.add(handler);
						
		controlManager.add(new SingleSettingTextHandler(processPriorityViewer, 
				new FormEditorEditingContext(editorContext.editor, processPriorityViewer.getControl()),
				EMMPStatement.EPOCPROCESSPRIORITY, editorContext, false));
		
		handler = new SingleSettingTextHandler(secureIDText, 
				new FormEditorEditingContext(editorContext.editor, secureIDText),
				new NumberValidator(0, MMPEditorContext.maxUID, true, Messages.RuntimeSectionPart_secureIDValidationErr),
				EMMPStatement.SECUREID, editorContext);
		controlManager.add(handler);
		
		handler = new SingleSettingTextHandler(stackSizeText, 
				new FormEditorEditingContext(editorContext.editor, stackSizeText),
				new NumberValidator(0, Integer.MAX_VALUE, true, Messages.RuntimeSectionPart_stackSizeValidationErr), 
				EMMPStatement.EPOCSTACKSIZE, editorContext);
		controlManager.add(handler);
		
		handler = new SingleSettingTextHandler(vendorIDText, 
				new FormEditorEditingContext(editorContext.editor, vendorIDText),
				new NumberValidator(0, Integer.MAX_VALUE, true, Messages.RuntimeSectionPart_vendorIDValidationErr), 
				EMMPStatement.VENDORID, editorContext);
		controlManager.add(handler);
		
		// these two separate fields edit the heap size list setting
		handler = new HeapSizeControlHandler(minHeapSizeText) {
			protected void textModified() {
				super.textModified();
				minHeapSizeTextChanged();
			}
		};
		controlManager.add(handler);
		
		handler = new HeapSizeControlHandler(maxHeapSizeText) {
			protected void textModified() {
				super.textModified();
				maxHeapSizeTextChanged();
			}
		};
		controlManager.add(handler);
		
		controlManager.add(new FlagSettingHandler(debuggableButton,
				new FormEditorEditingContext(editorContext.editor, debuggableButton),
				EMMPStatement.DEBUGGABLE_UDEBONLY, editorContext));

		controlManager.add(new PagingModeControlHandler(pagingModeViewer.getControl()));
	}

	private void maxHeapSizeTextChanged() {
		List<String> heapSettings = editorContext.mmpView.getListArgumentSettings().get(EMMPStatement.EPOCHEAPSIZE);
		ControlHandler handler = ControlHandler.getHandlerForControl(maxHeapSizeText);
		String maxHeapText = ControlHandler.getControlText(maxHeapSizeText);
		IUndoableOperation op;
		boolean refreshMin = false;
		if (heapSettings == null || heapSettings.size() < 2) {
			List<String> items = new ArrayList<String>();
			if (heapSettings.size() == 0) {
				items.add(DEFAULT_MIN_HEAP);
				refreshMin = true;
			}
			items.add(maxHeapText);
			op = new AddListValueOperation(editorContext.mmpView, 
					new FormEditorEditingContext(editorContext.editor, maxHeapSizeText),
					handler, EMMPListSelector.HEAP_SIZE, items);
		} else {
			// ensure a reasonable min heap default, unless they're clearing the max heap
			if (maxHeapText.length() > 0) {
				Map<Integer, Object> replaceMap = new HashMap<Integer, Object>();
				replaceMap.put(1, maxHeapText);
				if (TextUtils.strlen(heapSettings.get(0))==0) {
					replaceMap.put(0, DEFAULT_MIN_HEAP);
					refreshMin = true;
				}
				op = new ReplaceListValueOperation(editorContext.mmpView, 
						new FormEditorEditingContext(editorContext.editor, maxHeapSizeText),
						handler, EMMPListSelector.HEAP_SIZE, replaceMap);

			} else {
				// when max heap is clear we have to clear the min heap too
				List<Integer> indices = new ArrayList<Integer>();
				indices.add(0);
				indices.add(1);
				op = new RemoveListValueOperation(editorContext.mmpView, 
						new FormEditorEditingContext(editorContext.editor, maxHeapSizeText),
						handler, EMMPListSelector.HEAP_SIZE, indices);
				refreshMin = true;
			}
		}
		editorContext.executeOperation(op);
		if (refreshMin) {
			ControlHandler.getHandlerForControl(minHeapSizeText).refresh();
		}
	}

	private void minHeapSizeTextChanged() {
		List<String> heapSettings = editorContext.mmpView.getListArgumentSettings().get(EMMPStatement.EPOCHEAPSIZE);
		ControlHandler handler = ControlHandler.getHandlerForControl(minHeapSizeText);
		String minHeapText = ControlHandler.getControlText(minHeapSizeText);
		IUndoableOperation op;
		boolean refreshMax = false;
		if (heapSettings == null || heapSettings.size() == 0) {
			List<String> items = new ArrayList<String>();
			items.add(minHeapText);
			items.add(DEFAULT_MAX_HEAP);
			refreshMax = true;
			op = new AddListValueOperation(editorContext.mmpView, 
					new FormEditorEditingContext(editorContext.editor, minHeapSizeText),
					handler, EMMPListSelector.HEAP_SIZE, items);
		} else {
			// If we're modifying the min heap value, also ensure that max heap is valid.
			// But if we're clearing min heap, we have to also clear max heap as one can't 
			// be specified without the other.
			if (minHeapText.length() > 0) {
				Map<Integer, Object> replaceMap = new HashMap<Integer, Object>();
				replaceMap.put(0, minHeapText);
				if (minHeapText.length() > 0 && heapSettings.size() > 1 && TextUtils.strlen(heapSettings.get(1))==0) {
					replaceMap.put(1, DEFAULT_MAX_HEAP);
					refreshMax = true;
				}
				op = new ReplaceListValueOperation(editorContext.mmpView, 
						new FormEditorEditingContext(editorContext.editor, minHeapSizeText),
						handler, EMMPListSelector.HEAP_SIZE, replaceMap);
			} else {
				// remove both entries
				// when max heap is clear we have to clear the min heap too
				List<Integer> indices = new ArrayList<Integer>();
				indices.add(0);
				indices.add(1);
				op = new RemoveListValueOperation(editorContext.mmpView, 
						new FormEditorEditingContext(editorContext.editor, maxHeapSizeText),
						handler, EMMPListSelector.HEAP_SIZE, indices);
				refreshMax = true;
			}
		}
		editorContext.executeOperation(op);
		if (refreshMax) {
			ControlHandler.getHandlerForControl(maxHeapSizeText).refresh();
		}
	}

	private void doChooseCapabilities() {
		List<String> capabilities = editorContext.mmpView.getListArgumentSettings().get(EMMPStatement.CAPABILITY);
		capabilitiesDialog = new CapabilitiesDialog(getSection().getShell(), capabilities.toArray());
		int dlgResult = capabilitiesDialog.open();
		if (dlgResult == Dialog.OK) {
			Object[] checkedCapabilities = capabilitiesDialog.getCheckedCapabilities();
			List<Object> newList = new ArrayList<Object>();
			for (Object obj : checkedCapabilities) {
				newList.add(obj.toString());
			}
			ReplaceAllListValueOperation op = new ReplaceAllListValueOperation(editorContext.mmpView,
					new FormEditorEditingContext(editorContext.editor, capabilitiesText),
					ControlHandler.getHandlerForControl(capabilitiesText),
					EMMPListSelector.CAPABILITIES, newList);
			editorContext.executeOperation(op);
			ListSettingTextHandler capabilitiesHandler = (ListSettingTextHandler) ControlHandler.getHandlerForControl(capabilitiesText);
			capabilitiesHandler.refresh();		
		}
	}

	/**
	 * Handle changes in the paging model section combo box.
	 */
	private void pagingModeSelectionChanged() {
		String selectionText = ControlHandler.getControlText(pagingModeViewer.getControl());
		FormEditorEditingContext context = new FormEditorEditingContext(editorContext.editor, pagingModeViewer.getControl());
		if (selectionText == null || selectionText.equals(PAGING_MODE_VALUES[PAGING_NOT_SPECIFIED])) {
			ChangeFlagSettingOperation op1 = new ChangeFlagSettingOperation(editorContext.mmpView, 
					context, pagingModeViewer.getControl(), EMMPStatement.PAGED, false);
			ChangeFlagSettingOperation op2 = new ChangeFlagSettingOperation(editorContext.mmpView, 
					context, pagingModeViewer.getControl(), EMMPStatement.UNPAGED, false);
			CompositeOperation op3 = new CompositeOperation("", context, editorContext.operationHistory, false);
			op3.add(op1);
			op3.add(op2);
			editorContext.executeOperation(op3);
		}
		else if (selectionText.equals(PAGING_MODE_VALUES[PAGING_ENABLED])) {
			ChangeFlagSettingOperation op1 = new ChangeFlagSettingOperation(editorContext.mmpView, 
					context, pagingModeViewer.getControl(), EMMPStatement.PAGED, true);
			ChangeFlagSettingOperation op2 = new ChangeFlagSettingOperation(editorContext.mmpView, 
					context, pagingModeViewer.getControl(), EMMPStatement.UNPAGED, false);
			CompositeOperation op3 = new CompositeOperation("", context, editorContext.operationHistory, false);
			op3.add(op1);
			op3.add(op2);
			editorContext.executeOperation(op3);
		}
		else if (selectionText.equals(PAGING_MODE_VALUES[PAGING_DISABLED])) {
			ChangeFlagSettingOperation op1 = new ChangeFlagSettingOperation(editorContext.mmpView, 
					context, pagingModeViewer.getControl(), EMMPStatement.PAGED, false);
			ChangeFlagSettingOperation op2 = new ChangeFlagSettingOperation(editorContext.mmpView, 
					context, pagingModeViewer.getControl(), EMMPStatement.UNPAGED, true);
			CompositeOperation op3 = new CompositeOperation("", context, editorContext.operationHistory, false);
			op3.add(op1);
			op3.add(op2);
			editorContext.executeOperation(op3);
		}
	}

	@Override
	public void refresh() {
		super.refresh();

		List<String> heapSettings = editorContext.mmpView.getListArgumentSettings().get(EMMPStatement.EPOCHEAPSIZE);
		if (heapSettings != null && heapSettings.size() > 0) {
			ControlHandler.getHandlerForControl(minHeapSizeText).storeText(heapSettings.get(0));
			if (heapSettings.size() > 1) {
				ControlHandler.getHandlerForControl(maxHeapSizeText).storeText(heapSettings.get(1));
			}
		}
	}

	public Button getChooseButton() {
		return chooseButton;
	}

	public Text getCapabilitiesText() {
		return capabilitiesText;
	}

	public Text getMinHeapSizeText() {
		return minHeapSizeText;
	}

	public Text getMaxHeapSizeText() {
		return maxHeapSizeText;
	}

	public Text getStackSizeText() {
		return stackSizeText;
	}

	public Combo getProcessPriorityCombo() {
		return processPriorityViewer.getCombo();
	}

	public Text getSecureIDText() {
		return secureIDText;
	}

	public Text getVendorIDText() {
		return vendorIDText;
	}

	public Button getDebuggableButton() {
		return debuggableButton;
	}
	
	public Combo getPagingModeCombo() {
		return pagingModeViewer.getCombo();
	}

	public CapabilitiesDialog getCapabilitiesDialog() {
		return capabilitiesDialog;
	}

}
