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
package com.nokia.sdt.uidesigner.events;

import com.nokia.sdt.component.event.IEventDescriptor;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.sdt.preferences.PreferenceConstants;
import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.sdt.uidesigner.ui.utils.Messages;
import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.ui.QueryWithTristatePrefDialog;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import java.text.MessageFormat;
import java.util.Collection;

/** 
 * Helper code related to event binding commands
 *
 */
public class EventCommands {

	/**
	 * Utility to add, remove or update event bindings using
	 * IDesignerDataModel-provided commands. All instances are
	 * assumed to be in the same data model.
	 * @param instances the instances to be modified.
	 * @param descriptor
	 * @param userSpecifiedHandler
	 * @return an EMF command
	 */
	public static Command createEventBindingCommand(IComponentInstance[] instances,
			IEventDescriptor descriptor,
			String userSpecifiedHandler) {
		Check.checkArg(instances != null && instances.length > 0);
		Check.checkArg(descriptor);
		IDesignerDataModel dm = instances[0].getDesignerDataModel();		
		
		CompoundCommand result = new CompoundCommand(CompoundCommand.LAST_COMMAND_ALL);;

		// If the handler name is non-empty we create a new binding
		// or update an existing one.
		// If the handler name is empty the behavior is different
		// depending on whether a binding exists. If there's no
		// binding we add a new one with the default name. If there
		// is a binding, we interpret this as clearing the binding.

		for (int i = 0; i < instances.length; i++) {
			IComponentInstance currInstance = instances[i];
			IEventBinding binding = currInstance.findEventBinding(descriptor.getId());
			if (TextUtils.strlen(userSpecifiedHandler) > 0) {
				if (binding == null) {
					result.append(dm.createAddEventBindingCommand(
							currInstance.getEObject(), descriptor, 
							userSpecifiedHandler));
				}
				else {
					if (!binding.getHandlerName().equals(userSpecifiedHandler)) {
						result.append(dm.createRemoveEventBindingCommand(binding));
						result.append(dm.createAddEventBindingCommand(currInstance.getEObject(),
								descriptor, userSpecifiedHandler));
						result.setLabel(Messages.getString("EventCommands.0")); //$NON-NLS-1$
					}
				}
			}
			else {
				if (binding != null) {
					result.append(dm.createRemoveEventBindingCommand(binding));
				}
				else {
					result.append(dm.createAddEventBindingCommand(
							currInstance.getEObject(), descriptor, null));
				}
			}
		}
		return result.unwrap();
	}
	
	/**
	 * Return true if it's OK to save the model prior to modifying event bindings
	 * and saving the model. This method does not actually save the model, as it 
	 * should be called prior to performing changes.
	 * <p>
	 * The dialog indicates whether the save happens, not whether the binding
	 * is performed.  The binding can remain in memory until the user later
	 * tries to go to the source, in which case a save confirmation will
	 * be reissued.
	 * @return true if it's OK to proceed
	 */
	public static boolean userConfirmationToSaveModelDialog(
			Shell parentShell, IDesignerDataModel model) {
		String displayName = model.getModelSpecifier().getDisplayName();
		
		String fmt = Messages.getString("EventCommands.1"); //$NON-NLS-1$
		Object params[] = {displayName};
		String prompt = MessageFormat.format(fmt, params);

		QueryWithTristatePrefDialog dialog = new QueryWithTristatePrefDialog(
					parentShell, Messages.getString("EventCommands.2"), prompt, //$NON-NLS-1$
					UIModelPlugin.getDefault(), 
					PreferenceConstants.P_PROMPT_BEFORE_EVENT_SAVE, false,
					QueryWithTristatePrefDialog.QUERY_YES_NO);
		
		boolean result = dialog.doQuery();
		return result;
	}
	
	public static void navigateToHandlerCode(EventPage page, IEventBinding binding, boolean isNewBinding) {
		IEventDescriptor eventDescriptor = binding.getEventDescriptor();
		IStatus status = eventDescriptor.gotoHandlerCode(binding, isNewBinding);
		if (status != null) {
			if (!isNewBinding) {
				// Hmm, an error.  It could be the data model was not saved.
				// It may just be a problem in the component's sourcegen, hence
				// the fallthrough to the descriptive error later.
				MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						Messages.getString("EventCommands.Error"), null, //$NON-NLS-1$
						Messages.getString("EventCommands.NoEventHandlerFound"), //$NON-NLS-1$
						MessageDialog.QUESTION,
						new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL }, //$NON-NLS-1$ //$NON-NLS-2$
						0);
				int result = dialog.open();
				if (result == MessageDialog.OK) {
					if (page.saveDataModel()) {
						status = eventDescriptor.gotoHandlerCode(binding, isNewBinding);	
					}
				}
			}
			if (status != null) {
				Logging.log(UIDesignerPlugin.getDefault(), status);
			}
		}
		
	}
	
	public static IEventBinding getEventBindingFromCommandResult(Command command) {
		IEventBinding result = null;
		Collection cmdResult = command.getResult();
		if (cmdResult != null && cmdResult.size() == 1) {
			Object object = cmdResult.iterator().next();
			if (object instanceof IEventBinding) {
				result = (IEventBinding) object;
			}
		}
		return result;
	}
}
