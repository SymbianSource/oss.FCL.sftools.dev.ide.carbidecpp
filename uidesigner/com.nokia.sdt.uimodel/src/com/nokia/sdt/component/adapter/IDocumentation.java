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
 * Component adapter interface for documentation information from components.
 */
public interface IDocumentation extends IComponentAdapter {

	/**
	 * @return the helpTopic
	 */
	String getHelpTopic();
	
	/**
	 * @return the information
	 */
	String getInformation();
	
	/**
	 * @return the description for a wizard page
	 */
	String getWizardDescription();
}
