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
package com.nokia.sdt.component.symbian.sourcemapping;

import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
    private static final String BUNDLE_NAME = "com.nokia.sdt.component.symbian.sourcemapping.messages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private Messages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
    
    /**
     * Create a message
     * @param severity (Message.xxx)
     * @param msgKey
     */
    static public Message create(int severity, MessageLocation location, String msgKey) {
        return new Message(severity, location, msgKey, Messages.getString(msgKey));
    }

    /**
     * Emit a message
     * @param severity (Message.xxx)
     * @param msgKey
     * @param arg
     */
    static public Message create(int severity, MessageLocation location, String msgKey, Object arg) {
        return new Message(severity, location, msgKey, Messages.getString(msgKey), arg); 
    }
    
    /**
     * Emit a message
     * @param severity (Message.xxx)
     * @param msgKey
     * @param args
     */
    static public Message create(int severity, MessageLocation location, String msgKey, Object[] args) {
        return new Message(severity, location, msgKey, Messages.getString(msgKey), args); 
    }
}
