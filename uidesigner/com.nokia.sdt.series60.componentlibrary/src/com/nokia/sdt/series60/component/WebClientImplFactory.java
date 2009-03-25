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
package com.nokia.sdt.series60.component;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.component.event.IEventDescriptor;
import com.nokia.sdt.component.event.IEventDescriptorProvider;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.SetPropertyCommand;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

public class WebClientImplFactory implements IImplFactory {
	
	static final String WAIT_DIALOG_PROPERTY = "waitDialog"; //$NON-NLS-1$
	static final String NEW_WAIT_DIALOG_KEY = "newWaitDialog"; //$NON-NLS-1$
	static final String WAIT_DIALOG_COMPONENT = "com.nokia.sdt.series60.WaitDialog"; //$NON-NLS-1$
	static final String CANCELED_CODE_PROPERTY = "canceledHandlerCode"; //$NON-NLS-1$
	static final String CANCELED_EVENT = "canceled";//$NON-NLS-1$
	
	static final String CREDENTIALS_QUERY_PROPERTY = "credentialsQuery"; //$NON-NLS-1$
	static final String NEW_CREDENTIALS_DIALOG_KEY = "newCredentialsQuery"; //$NON-NLS-1$
	static final String MULTI_LINE_DATA_QUERY = "com.nokia.sdt.series60.MultiLineDataQuery"; //$NON-NLS-1$
	static final String SECOND_TYPE_PROPERTY = "type2";//$NON-NLS-1$
	static final String SECOND_SECRET_EDITOR = "EMultiDataSecondSecEd";//$NON-NLS-1$
	static final String FIRST_PROMPT_PROPERTY = "text"; //$NON-NLS-1$
	static final String SECOND_PROMPT_PROPERTY = "text2"; //$NON-NLS-1$
	
	static final String EDWIN_TEXT_PROPERTY = "text"; //$NON-NLS-1$
	
	public Object getImpl(EObject componentInstance) {
		return new SetValueCommandExtender(componentInstance);
	}
	
	static class SetValueCommandExtender extends AbstractSetValueNewInstanceCreator {

		public SetValueCommandExtender(EObject instance) {
			super(instance);
		}

		@Override
		protected String getNewComponentID(String propertyID, String creationKey) {
			String result = null;
			if (propertyID.equals(WAIT_DIALOG_PROPERTY)) {
				if (NEW_WAIT_DIALOG_KEY.equals(creationKey)) {
					result = WAIT_DIALOG_COMPONENT;
				}
				
			} else if (propertyID.equals(CREDENTIALS_QUERY_PROPERTY) ) {
				if (NEW_CREDENTIALS_DIALOG_KEY.equals(creationKey)) {
					result = MULTI_LINE_DATA_QUERY;
				}
			}
			return result;
		}

		@Override
		protected Command makeAddComponentCommand(String propertyID, String creationKey, EObject instance) {
			
			// We're adding non-layout objects. We need to ask the display model for the root of non-layout objects.
			IComponentInstance ci = ModelUtils.getComponentInstance(getEObject());
			EObject rootContainer = ci.getRootContainer();
			boolean disposeDisplayModel = false;
			IDisplayModel displayModel = getModel().getExistingDisplayModelForRootContainer(rootContainer);
			if (displayModel == null) {
				try {
					displayModel = getModel().getDisplayModelForRootContainer(rootContainer);
				} catch (CoreException x) {
					Series60ComponentPlugin.log(x);
				}
				disposeDisplayModel = true;
			}
			
			Command result = null;
			if (displayModel != null) {
				EObject nonLayoutRoot = displayModel.getNonLayoutRoot();
				if (disposeDisplayModel) {
					displayModel.dispose();
				}
				result = getModel().createAddNewComponentInstanceCommand(nonLayoutRoot, instance, IDesignerDataModel.AT_END);
			}
			return result;
		}

		@Override
		protected void addInitializeComponentCommands(String propertyID, String creationKey, 
				EObject instance, CompoundCommand cc) {
			if (propertyID.equals(WAIT_DIALOG_PROPERTY)) {
				IComponentInstance ci = ModelUtils.getComponentInstance(getEObject());
				// Set the code to generate for the canceled event. We're injecting code to
				// cancel the HTTP transaction when the wait dialog is canceled.
				String webClientInstanceName = "i" + TextUtils.titleCase(ci.getName());
				String code = webClientInstanceName + "->CancelTransactionL();";
				SetPropertyCommand cmd = new SetPropertyCommand(instance, CANCELED_CODE_PROPERTY, code);
				cmd.ignoreUndoRedo();
				cc.append(cmd);
				
				// Make a binding for the wait dialog's canceled event
				IComponent waitDialogComponent = getModel().getComponentSet().lookupComponent(getNewComponentID(propertyID, creationKey));
				IEventDescriptorProvider edp = (IEventDescriptorProvider) waitDialogComponent.getAdapter(IEventDescriptorProvider.class);
				IEventDescriptor eventDescriptor = edp.findEventDescriptor(CANCELED_EVENT);
				Command bindingCmd = getModel().createAddEventBindingCommand(instance, eventDescriptor, null);
				cc.append(bindingCmd);
				
			} else if (propertyID.equals(CREDENTIALS_QUERY_PROPERTY)) {
				// Set line 2 to secret editor
				SetPropertyCommand cmd = new SetPropertyCommand(instance, SECOND_TYPE_PROPERTY, SECOND_SECRET_EDITOR);
				cmd.ignoreUndoRedo();
				cc.append(cmd);
				
				// Set the prompts
				cmd = new SetPropertyCommand(instance, FIRST_PROMPT_PROPERTY, Messages.getString("WebClientImplFactory.usernamePrompt")); //$NON-NLS-1$
				cmd.ignoreUndoRedo();
				cc.append(cmd);
				cmd = new SetPropertyCommand(instance, SECOND_PROMPT_PROPERTY, Messages.getString("WebClientImplFactory.passwordPrompt")); //$NON-NLS-1$
				cmd.ignoreUndoRedo();
				cc.append(cmd);

				// initialize the children
				InitializeDataQueryChildrenCommand initChildrenCmd = new InitializeDataQueryChildrenCommand(instance);
				cc.append(initChildrenCmd);
			}
		}
	}
	
	/**
	 * Command to initialize the children of the data query. It sets line two to be a password field and
	 * clears the default text of the user name field.
	 * This command doesn't need to handle undo because the entire instance is removed on undo
	 *
	 */
	static class InitializeDataQueryChildrenCommand extends AbstractCommand {
		
		EObject dataQueryInstance;
		
		InitializeDataQueryChildrenCommand(EObject dataQueryInstance) {
			this.dataQueryInstance = dataQueryInstance;
		}

		@Override
		protected boolean prepare() {
			return true;
		}

		public void execute() {
			IComponentInstance dataQuery = ModelUtils.getComponentInstance(dataQueryInstance);
			EObject[] children = dataQuery.getChildren();
			Check.checkContract(children != null && children.length == 2);
			
			// Delete the default text of the user name text editor
			IPropertySource ps = ModelUtils.getPropertySource(children[0]);
			ps.setPropertyValue(EDWIN_TEXT_PROPERTY, "");
		}
		
		public void undo() {
			// no need to do anything, the instance will be deleted
		}

		public void redo() {
			execute();			
		}
	}
}
