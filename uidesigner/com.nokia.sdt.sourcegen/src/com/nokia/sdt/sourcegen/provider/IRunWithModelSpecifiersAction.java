/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen.provider;

import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

/**
 * 
 *
 */
public interface IRunWithModelSpecifiersAction {
	/**
	 * Invoke the action for a known data model specifier.
	 * @param spec
	 * @param isRoot
	 */
	public void run(IDesignerDataModelSpecifier spec, boolean isRoot);

	/**
	 * Run when no data models are known.
	 */
	public void runWhenNoModelSpecifiers();
}
