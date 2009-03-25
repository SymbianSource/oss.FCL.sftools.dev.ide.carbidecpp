/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IModelUpdater;
import com.nokia.sdt.datamodel.util.ModelUtils;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;

/**
 * 
 *
 */
public class ModelUpdaterCodeImplFactoryStub implements IImplFactory {
	
	class ModelUpdaterStub implements IModelUpdater {
	
		private EObject instance;
	
		public ModelUpdaterStub(EObject instance) {
			this.instance = instance;
		}
		
		public void updateModel(IDesignerDataModel dataModel) {
			IComponentInstance ci = ModelUtils.getComponentInstance(getEObject());
			EObject parent = ci.getParent();
			IComponentInstance parentCi = ModelUtils.getComponentInstance(parent);
			EObject newObject = dataModel.createNewComponentInstance(parentCi.getComponent());
			Command command = dataModel.createAddNewComponentInstanceCommand(instance, newObject, 0);
			if (command.canExecute())
				command.execute();
		}

		public EObject getEObject() {
			return instance;
		}

	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.ICodeImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject instance) {
		return new ModelUpdaterStub(instance);
	}
}
