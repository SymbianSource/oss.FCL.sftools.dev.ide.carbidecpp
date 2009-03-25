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
/**
 * 
 */
package com.nokia.tcf.impl;

import java.util.ArrayList;

public class TCErrorListenerList<ITCErrorListener> extends ArrayList<ITCErrorListener> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4097007367675340218L;

	/**
	 * our add overrides ArrayList.add in order throw NullPointerException
	 */
	@Override
	public boolean add(ITCErrorListener o) throws NullPointerException {
		if (o == null) throw new NullPointerException();
		return super.add(o);
	}

}
