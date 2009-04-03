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

import java.util.Vector;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TreeItem;

import com.nokia.sdt.datamodel.util.NamePropertySupport;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.symbian.dm.UIQModelUtils;
import com.nokia.cpp.internal.api.utils.ui.editor.FormEditorEditingContext;

/**
 * Class that allows the edition of the second column of the TreeViewer
 *
 */
public class TreeCellModifier implements ICellModifier {
	private TreeViewer viewer;
	private UIConfigurationPageUtils pageUtils;
	private final String UI_COMMAND_ID = UIQModelUtils.UIQ_COMMAND_ID;
	private final static String COLUMN_ELEMENT = "element"; //$NON-NLS-1$
	private CommandElementModel model;
	private CCombo comboTreeCommandId;
	private SingleSettingTextHandlerCommands handlerTree;
	private final ControlManagerCommands controlManager;
	private IDesignerDataModelEditor editor;
	/**
	 * Class constructor
	 * @param viewer
	 * @param pageUtils
	 * @param model
	 */
	public TreeCellModifier(TreeViewer viewer, UIConfigurationPageUtils pageUtils,
							CommandElementModel model,
							ControlManagerCommands controlManager,
							IDesignerDataModelEditor editor) {
		this.viewer = viewer;
		this.pageUtils = pageUtils;
		this.model = model;
		this.controlManager = controlManager;
		this.editor = editor;
	}
	
	/**
	 * Checks if the selected column can be modified
	 * Only the second column (command id) can be modified.
	 */
	public boolean canModify(Object element, String property) {
		if (!(property.equals(COLUMN_ELEMENT))){
			if (element instanceof CommandModel){
				//only the command instance can be modified
				CellEditor[] editors = this.viewer.getCellEditors();
				ComboBoxCellEditor combo = (ComboBoxCellEditor)editors[1];
				if (comboTreeCommandId != null){
					comboTreeCommandId = null;
				}
				comboTreeCommandId = (CCombo) combo.getControl();
				if (handlerTree == null){
					handlerTree = new SingleSettingTextHandlerCommands(comboTreeCommandId, 
							new FormEditorEditingContext(editor.getFormEditor(), comboTreeCommandId),
							new RegExInputValidatorCommands("^[(a-z)|(A-Z)][\\w]*$", true, 
									Messages.getString("CommandsPage.name.validateEmptyCombo")));
					handlerTree.setLabel(null);
					controlManager.add(handlerTree);
				}
				Listener listener = new Listener() {
					public void handleEvent(final Event e) {
						switch (e.type) {
						case SWT.FocusIn:						
							break;
						case SWT.Traverse:
							switch (e.detail) {
							case SWT.TRAVERSE_RETURN:
							controlManager.remove(handlerTree);							
							break;						
							case SWT.TRAVERSE_ESCAPE:
								controlManager.remove(handlerTree);	
							e.doit = false;
							}	
							break;
						}	
					}
				};
				comboTreeCommandId.addListener(SWT.FocusIn, listener);
				comboTreeCommandId.addListener(SWT.Traverse, listener);		
				return true;
			}			
		}
		return false;				
	}

	/**
	 * Returns the value selected on the combo.
	 */
	public Object getValue(Object element, String property) {
		Vector<String> currentIDs = pageUtils.getCurrentObjectsFromModel(UI_COMMAND_ID);
		String strCommandId = ((CommandModel) element).getCommandID();
		int indexSelect = currentIDs.indexOf(strCommandId);
		return indexSelect;
	}

	/**
	 * Refreshes the local model with the current selected item on the combo
	 */
	public void modify(Object element, String property, Object value) {
		Vector<String> currentIDs = pageUtils.getCurrentObjectsFromModel(UI_COMMAND_ID);
		int index = (Integer) (value);
		CellEditor[] editors = this.viewer.getCellEditors();
		ComboBoxCellEditor combo = (ComboBoxCellEditor)editors[1];
		CCombo comboEdit = (CCombo) combo.getControl();
		String text = comboEdit.getText();
		CommandModel command = (CommandModel)((TreeItem) element).getData();
		if (index == -1){ //it has nothing selected
			//we create the default CommandId
			String name = ""; //$NON-NLS-1$
			if (text.equals("")){ //$NON-NLS-1$
				name = null;			
			}
			else{
				if (!NamePropertySupport.isLegalName(text)){
					return;
				}
				name = text;
			}
			CommandId commandId = model.getCommandIdByName(name);
			if (commandId == null){
				name = model.addCommandID(name);
			}			
			command.setCommandID(name);
			model.setCommandIDModel(command, name);
			viewer.refresh();
			return;
		}			
		command.setCommandID(currentIDs.get(index));
		model.setCommandIDModel(command, currentIDs.get(index));		
		viewer.refresh();
	}
}
