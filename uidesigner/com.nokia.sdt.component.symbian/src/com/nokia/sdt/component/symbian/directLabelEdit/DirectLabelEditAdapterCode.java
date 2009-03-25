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

package com.nokia.sdt.component.symbian.directLabelEdit;

import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.datamodel.adapter.IDirectLabelEdit;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.drawing.IFont;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Rectangle;

public class DirectLabelEditAdapterCode extends AdapterImpl implements IDirectLabelEdit, ICodeImplAdapter {
	IDirectLabelEdit impl = null;
	
	public DirectLabelEditAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof IDirectLabelEdit);
		
		this.impl = (IDirectLabelEdit) impl;
		setTarget(instance);
	}
	
	public String[] getPropertyPaths() {
		return impl.getPropertyPaths();
	}

	public Rectangle getVisualBounds(String propertyPath, ILookAndFeel laf) {
		return impl.getVisualBounds(propertyPath, laf);
	}

	public IFont getLabelFont(String propertyPath, ILookAndFeel laf) {
		return impl.getLabelFont(propertyPath, laf);
	}

	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(IDirectLabelEdit.class);
	}

	public Object getImpl() {
		return impl;
	}

}
