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

import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByReference;

/**
 * Opaque Pointer to API Handle used in some native APIs
 *
 */
public class LPAPIHANDLE extends ByReference {

	public LPAPIHANDLE() {
		this(null);
	}
	public LPAPIHANDLE(APIHANDLE h) {
		super(Pointer.SIZE);
		setValue(h);
	}
	public void setValue(APIHANDLE h) {
		getPointer().setPointer(0, h != null ? h.getPointer() : null);
	}
	public APIHANDLE getValue() {
        Pointer p = getPointer().getPointer(0);
        if (p == null)
            return null;
        if (APIHANDLE.INVALID_HANDLE_VALUE.getPointer().equals(p)) 
            return APIHANDLE.INVALID_HANDLE_VALUE;
        APIHANDLE h = new APIHANDLE();
        h.setPointer(p);
        return h;
	}
	/**
	 * @param dataSize
	 */
	public LPAPIHANDLE(int dataSize) {
		super(dataSize);
		// TODO Auto-generated constructor stub
	}

}
