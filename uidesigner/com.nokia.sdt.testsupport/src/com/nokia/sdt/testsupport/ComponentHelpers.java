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

package com.nokia.sdt.testsupport;

import com.nokia.sdt.component.*;
import com.nokia.sdt.component.symbian.*;

import org.eclipse.core.runtime.CoreException;
import org.osgi.framework.Version;

public abstract class ComponentHelpers {

	public static ComponentSet queryAllComponents(IComponentProvider provider) throws CoreException {
		ComponentSet result = null;
		ComponentSetResult cr = provider.queryComponents(null);
		if (cr != null)
			result = (ComponentSet) cr.getComponentSet();
		return result;
	}

	public static ComponentSet querySDKFilter(IComponentProvider provider,
				String sdkName, String sdkVersionString) throws CoreException {
		ComponentSet result = null;
		Version sdkVersion = new Version(sdkVersionString);
		ComponentSDKSelector filter = new ComponentSDKSelector(sdkName, sdkVersion);
		ComponentSetResult cr = provider.queryComponents(filter);
		if (cr != null)
			result = (ComponentSet) cr.getComponentSet();
		return result;
	}
	
}
