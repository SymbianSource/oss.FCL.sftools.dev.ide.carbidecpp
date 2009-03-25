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


package com.nokia.sdt.component.symbian.test;

import com.nokia.sdt.component.customizer.IComponentCustomizerCommandFactory;
import com.nokia.sdt.datamodel.adapter.IComponentCustomizerUI;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

/**
 * 
 *
 */
public class TestCustomizerPage implements IComponentCustomizerUI {

	private EObject instance;

	public TestCustomizerPage(EObject instance) {
		this.instance = instance;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IModelAdapter#getEObject()
	 */
	public EObject getEObject() {
		return instance;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IComponentCustomizerUI#getCustomizerComposite(org.eclipse.swt.widgets.Composite)
	 */
	public Composite getCustomizerComposite(final Composite parent) {
		final Composite[] compositeHolder = { null };
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				Composite composite = new Composite(parent, SWT.DEFAULT);
				Text text = new Text(composite, SWT.DEFAULT);
				text.setText("Foo");
				compositeHolder[0] = composite;
			}
		});
		return compositeHolder[0];
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.datamodel.adapter.IComponentCustomizerUI#getCommandFactory()
	 */
	public IComponentCustomizerCommandFactory getCommandFactory() {
		return new TestCustomizerFactory();
	}

}
