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
package com.nokia.carbide.cpp.debug.crashdebugger;

public interface ISessionChangedListener {
    /**
     * Notifies that the session has started.
     */
    public void sessionStarted();

    /**
     * Notifies that output text has been received from the session.
     */
    public void sessionOutputReceived(String text);

    /**
     * Notifies that the session has ended.
     */
    public void sessionEnded();
}
