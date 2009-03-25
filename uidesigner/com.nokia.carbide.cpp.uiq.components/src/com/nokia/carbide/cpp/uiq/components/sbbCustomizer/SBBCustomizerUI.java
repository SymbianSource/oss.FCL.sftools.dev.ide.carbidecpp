/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.cpp.uiq.components.sbbCustomizer;

import com.nokia.sdt.component.customizer.IComponentCustomizerCommandFactory;
import com.nokia.sdt.datamodel.adapter.IComponentCustomizerUI;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class SBBCustomizerUI implements IComponentCustomizerUI {

	private EObject instance;
	private SBBCustomizerComposite composite;
	private SBBCustomizerCommandFactory commandFactory;

	public SBBCustomizerUI(EObject instance) {
		this.instance = instance;
		commandFactory = new SBBCustomizerCommandFactory();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IComponentCustomizerUI#getCustomizerComposite(org.eclipse.swt.widgets.Composite)
	 */
	public Composite getCustomizerComposite(Composite parent) {
		if (composite != null) {
			composite.dispose();
		}
		
		composite = new SBBCustomizerComposite(parent, SWT.NULL, instance);
		return composite;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IComponentCustomizerUI#getCommandFactory()
	 */
	public IComponentCustomizerCommandFactory getCommandFactory() {
		Check.checkContract(composite != null);
		commandFactory.setValue(composite.getValue());
		return commandFactory;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IModelAdapter#getEObject()
	 */
	public EObject getEObject() {
		return instance;
	}

}
