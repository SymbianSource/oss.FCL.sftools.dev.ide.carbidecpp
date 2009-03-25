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

package com.nokia.sdt.component.symbian.imagePropertyRenderingInfo;

import com.nokia.sdt.component.symbian.implementations.ICodeImplAdapter;
import com.nokia.sdt.datamodel.adapter.IDirectLabelEdit;
import com.nokia.sdt.datamodel.adapter.IImagePropertyRenderingInfo;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Point;

public class ImagePropertyRenderingInfoAdapterCode extends AdapterImpl implements IImagePropertyRenderingInfo, ICodeImplAdapter {
    IImagePropertyRenderingInfo impl = null;
	
	public ImagePropertyRenderingInfoAdapterCode() {
	}
	
	public void init(Object impl, EObject instance) {
		Check.checkArg(instance);
		Check.checkContract(impl instanceof IImagePropertyRenderingInfo);
		
		this.impl = (IImagePropertyRenderingInfo) impl;
		setTarget(instance);
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

    public boolean isScaling(String propertyId, ILookAndFeel laf) {
        return impl.isScaling(propertyId, laf);
    }

    public boolean isPreservingAspectRatio(String propertyId, ILookAndFeel laf) {
        return impl.isPreservingAspectRatio(propertyId, laf);
    }

    public Point getViewableSize(String propertyId, ILookAndFeel laf) {
        return impl.getViewableSize(propertyId, laf);
    }

    public Point getAlignmentWeights(String propertyId, ILookAndFeel laf) {
        return impl.getAlignmentWeights(propertyId, laf);
    }

}
