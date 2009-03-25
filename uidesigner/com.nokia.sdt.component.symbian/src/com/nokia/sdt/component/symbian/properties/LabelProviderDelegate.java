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
package com.nokia.sdt.component.symbian.properties;

import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/*
 * We wrapper label providers and intercept the dispose
 * call so we can cache label providers on property descriptors.
 * It seems that the GEF property sheet doesn't dispose
 * label providers. Also, the ILabelProvider API doesn't
 * allow clients to dispose images. So to minimize leakage
 * we need caching, but we need to handle dispose properly
 * if/when it is called.
 */
class LabelProviderDelegate implements ILabelProvider {
	
	private ILabelProvider delegate;
	private AbstractPropertyDescriptor owner;

	public LabelProviderDelegate(ILabelProvider delegate,
			AbstractPropertyDescriptor owner) {
		Check.checkArg(delegate);
		Check.checkArg(owner);
		this.delegate = delegate;
		this.owner = owner;
	}
	
	public void dispose() {
		owner.labelProviderDisposed(delegate);
		delegate.dispose();
	}

	public void addListener(ILabelProviderListener listener) {
		delegate.addListener(listener);
	}

	public Image getImage(Object element) {
		return delegate.getImage(element);
	}

	public String getText(Object element) {
		return delegate.getText(element);
	}

	public boolean isLabelProperty(Object element, String property) {
		return delegate.isLabelProperty(element, property);
	}

	public void removeListener(ILabelProviderListener listener) {
		delegate.removeListener(listener);
	}
}
