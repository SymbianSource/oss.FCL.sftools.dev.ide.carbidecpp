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

package com.nokia.sdt.component.symbian;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentFilter;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.osgi.framework.Version;

import java.util.HashMap;
import java.util.Map;

public class ComponentSDKSelector extends StandardComponentLibraryFilter implements IComponentFilter {

	private String sdkName;
	private Version sdkVersion;
	
	public ComponentSDKSelector(String name, Version version) {
		super(name);
		Check.checkArg(name);
		Check.checkArg(version);
		sdkName = name;
		sdkVersion = version;
	}

	public boolean accept(IComponent component) {
		boolean result = false;
		Component c = (Component) component;
		if (sdkName.equals(c.getSDKName())) {
			Version min = c.getMinSDKVersion();
			if (min == null || min.compareTo(sdkVersion) <= 0) {
				Version max = c.getMaxSDKVersion();
				if (max == null || max.compareTo(sdkVersion) >= 0) {
					result = true;
				}
			}
		}
		return result;
	}
	
	public Map getPersistenceProperties() {
		Map result = new HashMap();
		result.put(ComponentProvider.VENDOR_PROPERTY, sdkName);
		result.put(ComponentProvider.VERSION_PROPERTY, sdkVersion);
		return result;
	}
}
