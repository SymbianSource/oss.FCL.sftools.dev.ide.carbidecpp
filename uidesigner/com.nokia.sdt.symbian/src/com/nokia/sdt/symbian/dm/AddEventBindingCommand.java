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
package com.nokia.sdt.symbian.dm;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.component.event.HandlerMethodInformation;
import com.nokia.sdt.component.event.IEventDescriptor;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.emf.dm.DmFactory;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.sdt.symbian.Messages;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;
import java.util.Collection;

public class AddEventBindingCommand extends AbstractCommand {

	private INode node;
	private IEventDescriptor eventDescriptor;
	private String userSpecifiedName;
	private HandlerMethodInformation handlerInfo;
	private com.nokia.sdt.emf.dm.IEventBinding binding;
	private boolean prepared;
	
	AddEventBindingCommand(EObject target, IEventDescriptor eventDescriptor, String userSpecifiedName) {
		Check.checkArg(target);
		Check.checkArg(eventDescriptor);
		this.node = (INode) target;
		this.eventDescriptor = eventDescriptor;
		this.userSpecifiedName = userSpecifiedName;
		setLabel(Messages.getString("AddEventBindingCommand.1")); //$NON-NLS-1$
		setDescription(Messages.getString("AddEventBindingCommand.0")); //$NON-NLS-1$
		
	
	}
		
	protected boolean prepare() {
		IComponentInstance ci = ModelUtils.getComponentInstance(node);
		if (ci != null) {
			handlerInfo = eventDescriptor.generateHandlerMethodInfo(ci, userSpecifiedName);
			if (handlerInfo != null) {
				binding = DmFactory.eINSTANCE.createIEventBinding();
				binding.setEventID(eventDescriptor.getId());
				binding.setEventHandlerDisplayText(handlerInfo.getDisplayText());
				binding.setEventHandlerInfo(null);
				prepared = true;
			}
		}
		return true;
	}
	
	public void execute() {
		// This is to allow use in a compound command, where the object may not 
		// be in the model when prepare() is called. Adapters are not available before
		// an object is in the model.
		if (!prepared) {
			prepare();
		}
		node.getEventBindings().add(binding);
	}
	
	public void undo() {
		if (binding != null) {
			node.getEventBindings().remove(binding);
		}
	}

	public void redo() {
		node.getEventBindings().add(binding);
	}

	// The affected object is the node receiving the binding
	public Collection getAffectedObjects() {
		Collection result = new ArrayList();
		result.add(node);
		return result;
	}

	// the result is the IEventBinding
	public Collection getResult() {
		Collection result = null;
		if (binding != null) {
			result = new ArrayList();
			EventBinding eb = new EventBinding(node, binding);
			result.add(eb);
		}
		return result;
	}
	

}
