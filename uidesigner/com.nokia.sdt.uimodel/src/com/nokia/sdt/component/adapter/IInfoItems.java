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


package com.nokia.sdt.component.adapter;

/**
 * Component adapter interface for help items.
 */
public interface IInfoItems extends IComponentAdapter {
	
	/**
	 * @param numDisplayableItems how many items the ui can support to allow prioritization
	 * @return the number of help items that exist
	 */
	int getNumItems(int numDisplayableItems);
	
	/**
	 * This is a label for the type of help item, e.g. "Class name:"
	 * @param index a 0-based index must be less than what is returned in <code>getNumItems()</code>
	 * @return the label String
	 */
	String getItemTypeLabel(int index);
	
	/**
	 * This is a label for the help item, e.g. "CMyClass"
	 * @param index a 0-based index must be less than what is returned in <code>getNumItems()</code>
	 * @return the label String
	 */
	String getItemLabel(int index);
	
	/**
	 * @param index a 0-based index must be less than what is returned in <code>getNumItems()</code>
	 * @return the help topic
	 */
	String getItemHelpTopic(int index);
}
