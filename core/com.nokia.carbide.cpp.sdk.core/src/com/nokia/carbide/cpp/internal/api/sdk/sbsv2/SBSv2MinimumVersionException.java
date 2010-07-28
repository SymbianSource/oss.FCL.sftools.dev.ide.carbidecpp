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
*/
package com.nokia.carbide.cpp.internal.api.sdk.sbsv2;

/**
 * Exception thrown in case something fails when trying
 * find out facts about current SDK/Platform environment tools.
 * @since 3.0
 */
public class SBSv2MinimumVersionException extends Exception {
		
	static final long serialVersionUID = -6103977959623981590L;

	/**
	 * Default constructor.
	 */
	public SBSv2MinimumVersionException(){
		super();
	}

	/**
	 * Constructor with attached message.
	 * @param message Informative message about situation causing the exception.
	 */
	public SBSv2MinimumVersionException(String message){
		super(message);
	}

}
