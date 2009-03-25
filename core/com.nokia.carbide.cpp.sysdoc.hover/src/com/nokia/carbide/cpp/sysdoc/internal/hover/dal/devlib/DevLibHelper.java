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
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib;

import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.URLHelper;

/**
 * A common utility library for developer libraries
 */
public class DevLibHelper {
	// Source types for a developer library.
	public enum DevLibSourceEnum {
		JAR, HTTP, LOCAL
	};

	/**
	 * Extract out type of a developer library. It can be either plug-in, online
	 * end point, or local file system.
	 * 
	 * @param activeDevLibLoc
	 * @return
	 */
	public static DevLibSourceEnum findDevLibSourceType(
			DevLibProperties activeDevLibLoc) {
		// if it is a developer library plug-in
		if (activeDevLibLoc==null){
			return null;
		}
		
		if (URLHelper.isURLJAR(activeDevLibLoc.getDevLibURL())) {
			return DevLibSourceEnum.JAR;
		}
		return null;
	}
}
