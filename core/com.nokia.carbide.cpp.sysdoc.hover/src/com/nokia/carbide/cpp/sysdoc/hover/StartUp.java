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
package com.nokia.carbide.cpp.sysdoc.hover;

import org.eclipse.ui.IStartup;

import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverManager;

/**
 * 
 * Startup class responsible of loading Hover plugin when Carbide starts and
 * afterwards, indexing and if necessary uncompressing will start.
 * 
 */
public class StartUp implements IStartup {

	public void earlyStartup() {
		HoverManager.getInstance().initHover();
	}
}
