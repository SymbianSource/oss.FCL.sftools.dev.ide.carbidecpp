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

package com.nokia.carbide.cdt.builder.test;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.cpp.internal.api.utils.core.HostOS;

import junit.framework.TestCase;

/**
 * 
 */
public class BaseTest extends TestCase {
	protected IPath getStockFullPath() {
		return new Path(HostOS.IS_WIN32 ? "c:/" : "/opt/project");
	}
}
