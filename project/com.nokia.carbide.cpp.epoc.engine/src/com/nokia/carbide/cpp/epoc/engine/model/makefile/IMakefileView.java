/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.model.makefile;

import com.nokia.carbide.cpp.epoc.engine.model.IView;

/**
 * This is the interface to reading and modifying the Makefile contents.
 * <p>
 * This is radically simplified and stupid.  CDT's IMakefile doesn't provide write
 * access, so instead of implementing a parallel layer, we expose only
 * operations that query the model or make simple text-based operations.
 * Changes are made to the backing text and the model is reparsed.
 * Obviously, this is meant only for occasional operations. 
 *
 */
public interface IMakefileView extends IView, IMakefileViewBase {
	/**
	 * Tell if the view has changed since the last commit
	 * @return true if changed
	 */
	boolean hasChanges();
}
