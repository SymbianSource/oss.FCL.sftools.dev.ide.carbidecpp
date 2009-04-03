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

package com.nokia.carbide.cpp.epoc.engine.model;

/**
 * This listener provides notifications of notable changes to the model.  
 *
 */
public interface IModelListener {
	/**
	 * Called when underlying source and DOM have changed due to view commits
	 */
	void modelUpdated(IOwnedModel model, IView view);
	
	/**
	 * Called when underlying source and DOM have changed with unknown deltas 
	 * (e.g. external text edits, CVS checkout, direct document manipulation, etc)
	 */
	void modelChanged(IOwnedModel model);

}
