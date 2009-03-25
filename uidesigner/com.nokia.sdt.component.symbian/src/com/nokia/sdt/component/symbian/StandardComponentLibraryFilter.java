/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian;

/**
 * 
 *
 */
public class StandardComponentLibraryFilter implements IComponentLibraryFilter {
	private String sdkVendor;

	public StandardComponentLibraryFilter(String sdkVendor) {
		this.sdkVendor = sdkVendor;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.symbian.IComponentLibraryFilter#accept(com.nokia.sdt.component.symbian.IComponentLibrary)
	 */
	public boolean accept(IComponentLibrary lib) {
		if (lib.getBundle() == null) {
			// not a plugin project, so a user component library, which we allow
			return true;
		}
		// and only look at component libraries matching our SDK name
		return lib.getSDKVendorPattern().matcher(sdkVendor).matches();
	}

}
