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
package com.nokia.sdt.sourcegen.contributions;

/**
 * Visitor for a location tree.
 * <p>
 * Call pattern is:<br>
 * * #enter(&lt;root&gt;)<br>
 * * #visit(child1) ...<br>
 * * #visit(child2) ...<br>
 * * #exit(&lt;root&gt;)<br>
 * 
 *
 */
public interface ILocationVisitor {
	/**
	 * Called when entering a location.
	 * @param location
	 * @return true to descend, false to skip
	 */
	boolean enter(ILocation location);
	
	/** 
	 * Called for each child location.
	 * @param location
	 * @return true to visit other children, false to stop child scan
	 */
	boolean visit(ILocation location);
	
	/**
	 * Called when exiting a location.
	 * @param location
	 */
	void exit(ILocation location);
	
}
