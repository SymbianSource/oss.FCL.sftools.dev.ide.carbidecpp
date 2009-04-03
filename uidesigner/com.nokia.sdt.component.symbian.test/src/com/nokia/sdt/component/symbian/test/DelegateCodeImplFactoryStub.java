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
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;

import org.eclipse.emf.ecore.EObject;

import java.util.Collections;
import java.util.List;

/**
 *
 */
public class DelegateCodeImplFactoryStub implements IImplFactory {

	public Object getImpl(EObject componentInstance) {
		final EObject eobject = componentInstance;
		
		return new IImplementationDelegate() {

			public List<Class> getDelegateInterfaces() {
				Class interfaceType = ILayout.class;
				return Collections.singletonList(interfaceType);
			}

			public List<EObject> getDelegates(Class interfaceType) {
				if (!interfaceType.isAssignableFrom(ILayout.class))
					return null;
				
				IComponentInstance instance = Utilities.getComponentInstance(getEObject());
				IDesignerDataModel designerDataModel = instance.getDesignerDataModel();
				EObject delegate = 
					designerDataModel.findByNameProperty("delegate");
				
				return Collections.singletonList(delegate);
			}

			public EObject getEObject() {
				return eobject;
			}
			
		};
	}

}
