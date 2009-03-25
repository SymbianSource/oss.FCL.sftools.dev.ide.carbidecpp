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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom;

/**
 * The location of a token (IToken).
 *
 */
public interface ITokenLocation {
	/** If this is a virtual location, return the less-virtual parent location */
	ITokenLocation getParentLocation();
	/** Get offset within parent */
	int getParentOffset();
	/** Get length within parent */
	int getParentLength();

}
