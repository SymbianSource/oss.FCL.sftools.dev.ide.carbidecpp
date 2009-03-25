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
package com.nokia.carbide.cdt.builder.project;

/**
 * Interface that clients implement to listen for changes to the project property
 * of a Carbide project.
 *
 */
public interface ICarbideProjectPropertyChangedListener {
	
	/**
	 * Receive an event that the project property has changed for a project
	 * @param currentConfig - The configuration that is the new default
	 */
	void projectPropertyChanged(ICarbideProjectInfo cpi);

}
