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
/**
 * 
 */
package com.nokia.sdt.component.symbian.actionFilter;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.uidesigner.ui.command.DataModelCommandWrapper;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.IAction;

/**
 * Base delegate function to conditionally add components to a design.
 * This publishes and enables an "Add ... item" action which only appears when it's legal
 * to do so.
 * 
 *
 */
public abstract class BaseAddComponentActionFilterDelegate extends
		BaseComponentActionFilterDelegate {

	@Override
	protected boolean isActionVisibleForSelectedObject(EObject selected) {
		return isLegalTarget(selected);
	}

	protected void updateActionForSelectedObject(IAction action, EObject selected) {
		EObject addTarget = getAddTarget(selected);
		Check.checkContract(addTarget != null);
		IComponentInstance instance = ModelUtils.getComponentInstance(addTarget);
		Check.checkState(instance != null);
		ILayoutContainer lc = (ILayoutContainer) EcoreUtil.getRegisteredAdapter(addTarget, ILayoutContainer.class);
		if (lc != null) {
			String componentId = getAddedComponentId(selected);
			if (componentId != null) {
				IComponent component = instance.getDesignerDataModel().getComponentSet().lookupComponent(componentId);
				if (component != null) {
					if (lc.canContainComponent(component, null)) {
						if (isAddingEnabledAuxiliary(selected)) {
							action.setEnabled(true);
							return;
						}
					}
				}
			}
		}
		action.setEnabled(false);
	}

	@Override
	protected void executeActionCommand(IAction action, EObject target, CommandStack stack) {
		EObject addTarget = getAddTarget(target);
		Check.checkContract(addTarget != null);

		IComponentInstance targetInstance = ModelUtils.getComponentInstance(addTarget);
		Check.checkState(targetInstance != null);
		IDesignerDataModel model = targetInstance.getDesignerDataModel();
		
		String componentId = getAddedComponentId(target);
		Check.checkState(componentId != null);
		IComponent component = model.getComponentSet().lookupComponent(componentId);
		Check.checkState(component != null);

		EObject instance = model.createNewComponentInstance(component);
		Check.checkState(instance != null);
		
		org.eclipse.emf.common.command.Command emfCommand = model.createAddNewComponentInstanceCommand(addTarget, instance, getInsertPosition(target));
		Check.checkState(emfCommand.canExecute());
		
		DataModelCommandWrapper wrapper = new DataModelCommandWrapper();
		wrapper.setDataModelCommand(emfCommand);
		wrapper.setLabel(TextUtils.inverseTitleCase(action.getText()));
		
		// execute the add command now 
		stack.execute(wrapper);
		
		// select the new item(s)
		EditorUtils.setSelectionToAffectedObjects(editor, emfCommand.getAffectedObjects());
		
		// now execute init code and execute that command
		Command initCommand = getInitializeInstanceCommand(addTarget, instance);
		if (initCommand != null) {
			stack.execute(initCommand);
		}
	}

	/**
	 * Get the component ID you want to add, based on the selected item
	 * @param target selected target
	 * @return component ID
	 */
	abstract protected String getAddedComponentId(EObject target);

	/**
	 * Tell if the given selected item should host the add command.  
	 * This should not do any checks based on the
	 * current state of the component (except perhaps checks on parents,
	 * design properies, etc) -- enablement is checked via querying 
	 * containment.
	 * @param target selected target
	 * @return true if given selected component should host the Add Item command
	 */
	abstract protected boolean isLegalTarget(EObject target);

	/**
	 * Get the actual target of an add based on the selected target
	 * @param target selected target
	 */
	abstract protected EObject getAddTarget(EObject target);

	/** 
	 * Optional auxiliary checks to <i>disable</i> adding an item provided all
	 * the other checks (including containment) succeed.  
	 * @param target selected target
	 * @return true to enable menu item, false to disable
	 */
	protected boolean isAddingEnabledAuxiliary(EObject target) {
		return true;
	}
	
	/**
	 * Optional information about the insertion position.
	 */
	protected int getInsertPosition(EObject target) {
		return IDesignerDataModel.AT_END;
	}
	
	/**
	 * Optional routine to create commands to initialize an instance after
	 * it has been added.
	 * @param target the actual parent (from #getAddTarget())
	 * @param instance the new instance
	 * @return commands to set properties, etc. (or null)
	 */
	protected Command getInitializeInstanceCommand(EObject target, EObject instance) {
		return null;
	}
	
}
