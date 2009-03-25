/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.component.symbian.test;

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.adapter.IChildCommandExtender;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.SetPropertyCommand;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;

import java.util.Collection;
import java.util.List;

/**
 * 
 *
 */
public class ChildCommandExtenderCodeImplFactoryStub implements IImplFactory {
	
	class ChildExtenderStub implements IChildCommandExtender {
	
		private EObject instance;
	
		public ChildExtenderStub(EObject instance) {
			this.instance = instance;
		}
		
		public Command getExtendedAddNewComponentInstanceCommand(EObject owner, Collection<EObject> children, int insertionPosition, Command command) {
			return command.chain(new SetPropertyCommand(owner, "test", "added"));
		}

		public Command getExtendedMoveComponentInstanceCommand(EObject targetObject, EObject newOwner, int insertionPosition, Command command) {
			return command.chain(new SetPropertyCommand(newOwner, "test", "moved"));
		}

		public Command getExtendedRemoveComponentInstancesCommand(List<EObject> objectsToRemove, Command command) {
			EObject object = objectsToRemove.get(0);
			EObject owner = ModelUtils.getComponentInstance(object).getParent();
			return command.chain(new SetPropertyCommand(owner, "test", "removed"));
		}

		public EObject getEObject() {
			return instance;
		}

	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.ICodeImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject instance) {
		return new ChildExtenderStub(instance);
	}
}
