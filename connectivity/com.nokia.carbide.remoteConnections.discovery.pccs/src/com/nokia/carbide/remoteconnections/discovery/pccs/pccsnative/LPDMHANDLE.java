/*
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

import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByReference;

public class LPDMHANDLE extends ByReference {
	public LPDMHANDLE() {
		this(null);
	}
    public LPDMHANDLE(DMHANDLE h) {
        super(Pointer.SIZE);
        setValue(h);
    }
    public void setValue(DMHANDLE h) {
        getPointer().setPointer(0, h != null ? h.getPointer() : null);
    }
    public DMHANDLE getValue() {
        Pointer p = getPointer().getPointer(0);
        if (p == null)
            return null;
        if (DMHANDLE.INVALID_HANDLE_VALUE.getPointer().equals(p)) 
            return DMHANDLE.INVALID_HANDLE_VALUE;
        DMHANDLE h = new DMHANDLE();
        h.setPointer(p);
        return h;
    }
}
