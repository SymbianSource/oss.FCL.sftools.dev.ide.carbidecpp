/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.remoteconnections.interfaces;

/**
 * The interface for the connection type extension
 */
public interface IConnectionType {
	
	/**
	 * A unique identifier for this connection type
	 * @return String
	 */
	String getIdentifier();
	
	/**
	 * The display name
	 * @return String
	 */
	String getDisplayName();
	
	/**
	 * A description for settings UI for this connection type
	 * @return String
	 */
	String getDescription();
	
	/**
	 * Return the fully qualified help context.
	 * @return String
	 * @see org.eclipse.ui.help.IWorkbenchHelpSystem#displayHelp(String contextId)
	 */
	String getHelpContext();
	
	/**
	 * Return a class used to create IConnection instances from UI or settings
	 * @return IConnectionFactory
	 */
	IConnectionFactory getConnectionFactory();

}
