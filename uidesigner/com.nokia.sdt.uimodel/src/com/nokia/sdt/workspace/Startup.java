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
package com.nokia.sdt.workspace;

import org.eclipse.ui.IStartup;

/**
 * Invoked on Eclipse startup, unless disabled by the user.
 * Ensures the WorkspaceContext is initialized and can begin
 * monitoring user moves/deletes of design files.
 *
 */
public class Startup implements IStartup {
	public void earlyStartup() {
		// force creation of the singleton. It will refresh automatically
		@SuppressWarnings("unused") 
		WorkspaceContext wc = WorkspaceContext.getContext();
	}
	
}
