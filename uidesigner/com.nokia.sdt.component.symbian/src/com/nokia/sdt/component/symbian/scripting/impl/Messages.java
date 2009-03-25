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
package com.nokia.sdt.component.symbian.scripting.impl;

import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.Message;
import com.nokia.cpp.internal.api.utils.core.MessageLocation;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.scripting.ScriptException;
import com.nokia.sdt.utils.*;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
    private static final String BUNDLE_NAME = "com.nokia.sdt.component.symbian.scripting.impl.strings"; //$NON-NLS-1$

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
    
    /** Report exception to log, problems view, and throw wrapped exception. */ 
    static void failure(Throwable ex, String key, String arg)
            throws ScriptException {
        String format = Messages.getString(key);
        String message = MessageFormat.format(format, new Object[] { arg });
        ComponentSystemPlugin.log(ex);

        // the key is fixed but the underlying message comes
        // directly from the exception
        IMessage msg = new Message(IMessage.ERROR,
                null,
                "SourceGenAdapterScript.ErrorDuringScript",
                message);        

        MessageReporting.emitMessage(msg);

        throw new ScriptException(message, ex);
    }

    /**
     * Log a message and throw wrapped exception.
     * @param ex
     * @param key
     * @param args
     * @throws ScriptException
     */
    static public void failure(Throwable ex, MessageLocation location, String key, Object args[]) throws ScriptException {
        String format = Messages.getString(key);
        String message = MessageFormat.format(format, args);
        ComponentSystemPlugin.log(ex);
        
        // the key is fixed but the underlying message comes
        // directly from the exception
        IMessage msg = new Message(IMessage.ERROR,
                location,
                "SourceGenAdapterScript.ErrorDuringScript",
                message);        

        MessageReporting.emitMessage(msg);
        
        throw new ScriptException(message, ex);
    }

}
