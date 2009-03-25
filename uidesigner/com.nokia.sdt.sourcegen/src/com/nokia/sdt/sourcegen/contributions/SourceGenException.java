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

package com.nokia.sdt.sourcegen.contributions;

import com.nokia.sdt.sourcegen.core.Messages;

public class SourceGenException extends Exception {
    private static final long serialVersionUID = 1L;
    private String key;
    private Object[] args;
    public SourceGenException(String key, String arg) {
        this.key = key;
        this.args = new String[] { arg };
    }
    public SourceGenException(String key, Object[] args) {
        this.key = key;
        this.args = args;
    }
    public String getKey() {
        return key;
    }
    public String getLocMsg() {
        return Messages.getString(key);
    }
    public Object[] getArgs() {
        return args;
    }

    /*
    public IStatus createStatus(String format, Object[] args) {
    	String msg = MessageFormat.format(
				Messages.getString(key),
				args); 
    	return Logging.newStatus(SourceGenPlugin.getDefault(), 
    			IStatus.ERROR,
    			MessageFormat.format(format msg);
	}
    
    public IMessage createMessage(MessageLocation location, String format, Object[] args) {
    	return new Message(IMessage.ERROR,
                location,
                key,
                Messages.getString(key),
                args);        	
    }
    */
}