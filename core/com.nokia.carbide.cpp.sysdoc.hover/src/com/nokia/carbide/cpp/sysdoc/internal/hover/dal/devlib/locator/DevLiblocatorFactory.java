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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.locator;

import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverManager;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.MessagesConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibHelper;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibProperties;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibHelper.DevLibSourceEnum;
import com.nokia.carbide.cpp.sysdoc.internal.hover.exceptions.HoverException;

/**
 * 
 * A Factory class creates an instance of {@link IDevLibLocator} given
 * {@link DevLibProperties}
 * 
 */
public class DevLiblocatorFactory {

	/**
	 * Creates an instance of {@link IDevLibLocator}
	 * 
	 * @return
	 */
	public static IDevLibLocator createDevLibLocator() {
		DevLibProperties devLibProp = HoverManager.getActiveDevLibProperties();
		DevLibSourceEnum loc = DevLibHelper.findDevLibSourceType(devLibProp);
		switch (loc) {
		case LOCAL:
			break;
		case HTTP:
			break;
		case JAR:
			return new JarDevLibLocator(devLibProp);
		}
		throw new HoverException(MessagesConstants.UNDEFINED_DEV_LIB_LOC_TYPE
				+ ":" + devLibProp.getDevLibURL().toString());
	}

}
