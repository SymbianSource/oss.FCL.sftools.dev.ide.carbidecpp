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

package com.nokia.sdt.component.symbian.visual;

import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.datamodel.adapter.IVisualAppearance;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.drawing.GC;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Point;

public class VisualAppearanceAdapterCode extends AdapterImpl implements IVisualAppearance, ICodeImplAdapter {
	IVisualAppearance impl = null;
	
	public VisualAppearanceAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof IVisualAppearance);
		
		this.impl = (IVisualAppearance) impl;
		setTarget(instance);
	}
	
	public void draw(GC gc, ILookAndFeel laf) {
		try {
			impl.draw(gc, laf);
		} catch (Throwable t) {
			ComponentSystemPlugin.log(t);
		}
	}

	public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
		try {
			return impl.getPreferredSize(wHint, hHint, laf);
		} catch (Throwable t) {
			ComponentSystemPlugin.log(t);
			return new Point(1,1);
		}
	}
	
	public EObject getEObject() {
		return (EObject) getTarget();
	}

	public boolean isAdapterForType(Object type) {
		return type.equals(IVisualAppearance.class);
	}

	public Object getImpl() {
		return impl;
	}

}
