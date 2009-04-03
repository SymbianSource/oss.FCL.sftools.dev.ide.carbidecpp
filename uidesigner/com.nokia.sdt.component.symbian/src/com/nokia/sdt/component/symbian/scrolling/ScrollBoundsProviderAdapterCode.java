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

package com.nokia.sdt.component.symbian.scrolling;

import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.datamodel.adapter.IScrollBoundsProvider;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Rectangle;

public class ScrollBoundsProviderAdapterCode extends AdapterImpl implements IScrollBoundsProvider, ICodeImplAdapter {
	IScrollBoundsProvider impl = null;
	
	public ScrollBoundsProviderAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof IScrollBoundsProvider);
		
		this.impl = (IScrollBoundsProvider) impl;
		setTarget(instance);
	}
	
	public Rectangle getScrollBounds(ILookAndFeel laf) {
		return impl.getScrollBounds(laf);
	}

	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(IScrollBoundsProvider.class);
	}

	public Object getImpl() {
		return impl;
	}
	
}
