/**
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.remoteconnections.discovery.pccs.pccsnative;

import com.sun.jna.FromNativeContext;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;

/**
 *  Opaque API Handle used in most native APIs
 *
 */
public class APIHANDLE extends PointerType {
	// Constant value representing an invalid HANDLE.
    public static APIHANDLE INVALID_HANDLE_VALUE = new APIHANDLE(Pointer.createConstant((long)-1));
	private boolean immutable;

	public APIHANDLE() {
	}

	/**
	 * @param p
	 */
	public APIHANDLE(Pointer p) {
		setPointer(p); 
		immutable = true;	
	}

	/**
	 * Override to the appropriate object for INVALID_HANDLE_VALUE.
	 */
	@Override
	public Object fromNative(Object nativeValue, FromNativeContext context) {
        Object o = super.fromNative(nativeValue, context);
        if (INVALID_HANDLE_VALUE.equals(o))
            return INVALID_HANDLE_VALUE;
        return o;
	}

	@Override
	public void setPointer(Pointer p) {
        if (immutable)
            throw new UnsupportedOperationException("immutable reference"); //$NON-NLS-1$
		super.setPointer(p);
	}

}
