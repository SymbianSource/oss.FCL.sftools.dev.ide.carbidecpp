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

package com.nokia.sdt.component.symbian.layout;

import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Point;

public class LayoutAdapterCode extends AdapterImpl implements ILayout, ICodeImplAdapter {
	ILayout impl = null;
	
	public LayoutAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof ILayout);
		
		this.impl = (ILayout) impl;
		setTarget(instance);
	}
	
	public void layout(ILookAndFeel laf) {
		try {
			impl.layout(laf);
		} catch (Throwable t) {
			ComponentSystemPlugin.log(t);
		}
	}
	
	public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
		return impl.getPreferredSize(wHint, hHint, laf);
	}

	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(ILayout.class);
	}

	public Object getImpl() {
		return impl;
	}
	
}
