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

import com.nokia.sdt.emf.dm.INode;
import com.nokia.sdt.symbian.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.EObject;

import java.util.ArrayList;
import java.util.Collection;

public class RemoveEventBindingCommand extends AbstractCommand {

	private INode node;
	private com.nokia.sdt.emf.dm.IEventBinding binding;
	
	RemoveEventBindingCommand(com.nokia.sdt.datamodel.adapter.IEventBinding binding) {
		Check.checkArg(binding);
		EObject owner = binding.getOwner();
		Check.checkArg(owner);
		Check.checkArg(owner instanceof INode);
		this.node = (INode) owner;
		this.binding = node.findBinding(binding.getEventDescriptor().getId());
		setLabel(Messages.getString("RemoveEventBindingCommand.0")); //$NON-NLS-1$
		setDescription(Messages.getString("RemoveEventBindingCommand.1")); //$NON-NLS-1$
	}
	
	protected boolean prepare() {
		return binding != null;
	}

	public void execute() {
		node.getEventBindings().remove(binding);
	}
	
	public void undo() {
		if (!node.getEventBindings().contains(binding)) {
			node.getEventBindings().add(binding);
		}
	}
	
	public void redo() {
		node.getEventBindings().remove(binding);
	}

	public Collection getAffectedObjects() {
		Collection result = new ArrayList();
		result.add(node);
		return result;
	}
}
