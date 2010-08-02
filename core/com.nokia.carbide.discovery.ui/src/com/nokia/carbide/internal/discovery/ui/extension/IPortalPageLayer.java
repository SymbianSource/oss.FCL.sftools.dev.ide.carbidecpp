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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;

/**
 * Interface to a portal page layer extension
 */
public interface IPortalPageLayer extends ICommandBarFactory {

	/**
	 * Called to create the control for the portal page layer
	 * @param parent Composite
	 * @param part IEditorPart
	 * @return Control
	 */
	Control createControl(Composite parent, IEditorPart part);
	
	/**
	 * Called to initialize the portal page layer when shown for the first time
	 */
	void init();
	
	/**
	 * Called to dispose internal resources of the portal page layer
	 */
	void dispose();
}
