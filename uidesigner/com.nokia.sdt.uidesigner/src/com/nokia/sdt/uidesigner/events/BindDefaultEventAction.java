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
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.editor.*;
import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.sdt.uidesigner.ui.actions.ShowEventsAction;
import com.nokia.sdt.uidesigner.ui.command.DataModelCommandWrapper;
import com.nokia.sdt.uidesigner.ui.utils.*;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

import java.text.MessageFormat;
import java.util.List;

public class BindDefaultEventAction extends SelectionAction {
	public final static String ID = "com.nokia.sdt.uidesigner.bindDefaultEvent"; //$NON-NLS-1$
	private IEventDescriptor descriptor;
	private EObject instance;
	private Shell shell;
	private IDesignerEditor designerEditor;
	private IEditorPart editorPart;
	
	public BindDefaultEventAction(IEditorPart editorPart) {
		super(editorPart);
		this.editorPart = editorPart;
		this.designerEditor = (IDesignerEditor) editorPart.getAdapter(IDesignerEditor.class);
		shell = editorPart.getSite().getShell();
	}
	
	protected boolean calculateEnabled() {
		descriptor = null;
		List objects = getSelectedObjects();
		if ((objects != null) && (objects.size() == 1)) {
			Object object = objects.get(0);
			if (object instanceof EditPart) {
				Object model = ((EditPart) object).getModel();
				if (model instanceof EObject)
					setSelectedInstance((EObject) model, shell);
			}
		}
		
		return descriptor != null;
	}
	
	private void setSelectedInstance(EObject instance, Shell shell) {
		this.instance = instance;
		this.shell = shell;
		IComponentEditor componentEditor = Adapters.getComponentEditor(instance);
		if ((componentEditor != null) && componentEditor.isTemporaryObject())
			return;
		
		IComponentEventDescriptorProvider eventDescriptorProvider = 
			ModelUtils.getComponentEventDescriptorProvider(instance);
		int defaultEventIndex = eventDescriptorProvider.getDefaultEventIndex();
		if (defaultEventIndex >= 0) {
			IEventDescriptor[] eventDescriptors = eventDescriptorProvider.getEventDescriptors();
			descriptor = eventDescriptors[defaultEventIndex];
			IComponentInstance componentInstance = Adapters.getComponentInstance(instance);
			IEventBinding binding = componentInstance.findEventBinding(descriptor.getId());
			if (binding != null)
				setText(MessageFormat.format(Strings.getString("BindDefaultEventAction.gotoCodeLabel"),  //$NON-NLS-1$
						new Object[] { descriptor.getDisplayText() }));
			else
				setText(MessageFormat.format(Strings.getString("BindDefaultEventAction.label"),  //$NON-NLS-1$
						new Object[] { descriptor.getDisplayText() }));
		}
	}
	
	public void run() {
		if (descriptor != null) {
			IComponentInstance componentInstance = Adapters.getComponentInstance(instance);
			IEventBinding binding = componentInstance.findEventBinding(descriptor.getId());
			boolean isNewBinding = false;
			if (binding == null) {
				IDesignerDataModel model = componentInstance.getDesignerDataModel();
				isNewBinding = true;
				Command command = EventCommands.createEventBindingCommand(
							new IComponentInstance[] {componentInstance}, descriptor, null);
				if (command != null) {
					executeEMFCommand(command);
					binding = EventCommands.getEventBindingFromCommandResult(command);

					boolean doSave = EventCommands.userConfirmationToSaveModelDialog(
							shell, model);
					if (!doSave || !saveDataModel()) {
						binding = null;
					}
				}
			}
			if (binding != null) {
				EventCommands.navigateToHandlerCode(getEventPage(), binding, isNewBinding);
			}
		}
	}

	private EventPage getEventPage() {
		IEditorPart part = (IEditorPart) designerEditor.getAdapter(IEditorPart.class);
		ShowEventsAction showEventsAction = 
			new ShowEventsAction(part);
		showEventsAction.run();
		IWorkbenchPage page = part.getSite().getPage().getWorkbenchWindow().getActivePage();
		EventView viewPart = (EventView) page.findView(showEventsAction.getViewPartId());
		return (EventPage) viewPart.getCurrentPage();
	}

	private boolean saveDataModel() {
		return EditorServices.saveEditor(editorPart);
	}
	
	private boolean executeEMFCommand(Command command) {
		boolean success = true;
		try {
			org.eclipse.gef.commands.CommandStack commandStack = null;
			if (designerEditor != null) {
				commandStack = designerEditor.getEditDomain().getCommandStack();
			}
			
			if (commandStack != null) {
				DataModelCommandWrapper wrapper = new DataModelCommandWrapper();
				wrapper.setDataModelCommand(command);
				commandStack.execute(wrapper);
			}
			else {
				if (command.canExecute()) {
					command.execute();
				}
			}
		}
		catch (Exception x) {
			IStatus status = Logging.newSimpleStatus(0, x);
			UIDesignerPlugin.getDefault().log(x);
			Logging.showErrorDialog(Messages.getString("EventPage.2"), status.getMessage(), status); //$NON-NLS-1$
			success = false;
		}
		return success;
	}

	protected void init() {
		super.init();
		setId(ID);
		setText(MessageFormat.format(Strings.getString("BindDefaultEventAction.label"),  //$NON-NLS-1$
										new Object[] { "" }));  //$NON-NLS-1$
	}

}
