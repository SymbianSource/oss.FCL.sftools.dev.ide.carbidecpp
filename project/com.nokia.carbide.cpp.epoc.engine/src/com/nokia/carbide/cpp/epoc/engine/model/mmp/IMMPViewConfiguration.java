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

package com.nokia.carbide.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;

/**
 * This encapsulates differences between different MMP versions. 
 *
 */
public interface IMMPViewConfiguration extends IViewConfiguration {
	/**
	 * Tell whether the given MMP keyword is supported.
	 */
	boolean isStatementSupported(EMMPStatement statement);
	
	/**
	 * Get the default directory used for .def file exports.
	 * @param isASSP true if this is an ASSP build
	 * @return 	the name of the directory used for platform-specific defs
	 * (e.g. "BWINS", "EABI", ...).  This directory, if present, is removed from 
	 * the path before setting DEFFILE.  May be null if unknown.
	 */
	String getDefaultDefFileBase(boolean isASSP);
	
	/**
	 * Tell if this is an emulator build.  Used for .def filepath calculation.
	 */
	boolean isEmulatorBuild();
}
