/*
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
package com.nokia.carbide.internal.discovery.ui.extension;

import org.eclipse.ui.IEditorPart;

/**
 * Interface to a factory for command bars for a portal page/layer
 */
public interface ICommandBarFactory {

	/**
	 * Return action bars for the portal page layer
	 * @param part IEditorPart
	 * @param updater IActionUIUpdater
	 * @return IActionBar[]
	 */
	IActionBar[] createCommandBars(IEditorPart part, IActionUIUpdater updater);

}
