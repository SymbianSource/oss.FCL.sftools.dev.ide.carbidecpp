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
package com.nokia.carbide.cpp.internal.api.project.core;

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMMPReference;

import java.util.List;

/**
 * Given a list of IMMPReference's, return which IMMPReference's to add
 * the source file to.  The main implementation is meant to be a dialog
 * so the user can choose 0 or more mmp files.  It can be implemented by
 * a JUnit test as well though.
 *
 */
public interface IMMPSelectionResolver {
	
	/**
	 * 
	 * @param allMMPs list of all mmps in the bld.inf
	 * @param mmpsContainingSourcePath list of all mmps that already contain the source path
	 * @return the list of mmps to add the source to, can be empty
	 */
	public List<IMMPReference> addSourceToWhichMMPs(List<IMMPReference> allMMPs, List<IMMPReference> mmpsContainingSourcePath);

}
