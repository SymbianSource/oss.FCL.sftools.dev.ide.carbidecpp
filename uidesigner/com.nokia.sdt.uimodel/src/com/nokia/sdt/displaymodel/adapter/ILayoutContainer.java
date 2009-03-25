/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.displaymodel.adapter;

import org.eclipse.emf.ecore.EObject;



/**
 * 
 * <br><br>
 * The layout object adapter for a container object
 */
public interface ILayoutContainer extends IContainer {

	public enum ELayoutCategory {
		ABSOLUTE,
		ORDERED
	}

	/**
	 * Returns true if the child can be resized.
	 * This method should check:
	 * 		- static constraints of the child component
	 * 		- dynamic constraints of the child component
	 * 		- static constraints of the container
	 * 		- dynamic constraints of the container
	 * @param child a <code>ILayoutObject</code>
	 * @return <code>true</code> if child can be resized in this container
	 */
	boolean canResizeChild(ILayoutObject child);

	/**
	 * Returns true if the child can be moved.
	 * This method should check:
	 * 		- static constraints of the child component
	 * 		- dynamic constraints of the child component
	 * 		- static constraints of the container
	 * 		- dynamic constraints of the container
	 * @param child a <code>ILayoutObject</code>
	 * @return <code>true</code> if child can be moved in this container
	 */
	boolean canMoveChild(ILayoutObject child);

	/**
	 * This method tells the container to layout its children
	 */
	void layoutChildren();

	/**
	 * @return <code>true</code> if this container scrolls vertically
	 */
	boolean scrollsVertically();

	/**
	 * @return <code>true</code> if this container scrolls horizontally
	 */
	boolean scrollsHorizontally();
	
	/**
	 * @return array of visible child instances or <code>null</code> if no children
	 */
	EObject[] getVisibleChildren();
	
	/**
	 * @return {@link ELayoutCategory}
	 */
	ELayoutCategory getLayoutCategory();

	/**
	 * Is called when the container is created to support target feedback
	 * @return {@link ITargetFeedbackListener} or null for default feedback
	 */
	ITargetFeedbackListener getTargetFeedbackListener();

}
