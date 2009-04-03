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
package com.nokia.carbide.cpp.debug.kernelaware;

import org.eclipse.core.runtime.IStatus;

import com.freescale.cdt.debug.cw.core.cdi.Session;

/**
 * Listener that is interested in OS data change. For instance, OSDataManager is
 * a notifier while the OS View is a lisenter.
 * 
 */
public interface IDataChangedListener {
	/**
	 * Do handling when OS Data becomes dirty in the model.
	 */
	public void dataDirty();

	/**
	 * Do handling when OS Data has been updated in the model.
	 */
	public void dataUpdated(final Session session, final IStatus status);
}
