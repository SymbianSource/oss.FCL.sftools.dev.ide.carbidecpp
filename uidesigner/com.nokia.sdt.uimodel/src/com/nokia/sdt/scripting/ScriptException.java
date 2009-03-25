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
package com.nokia.sdt.scripting;


/** 
 * Encapsulate exceptions thrown from scripting engine
 * while compiling code.
 * 
 * 
 *
 */
public class ScriptException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ScriptException() {
        super();
    }

    public ScriptException(Throwable cause) {
        super(cause);
    }
    
    public ScriptException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage() {
//        return super.getMessage();
        if (getCause() == this || getCause() == null)
            return super.getMessage();
        String causeMsg = getCause().getMessage();
        if (causeMsg != null && causeMsg.length() > 0)
            return causeMsg;
        else
            return super.getMessage();
        //if (super.getMessage())
        //return (super.getMessage() != null ? super.getMessage() + ":\n" : "") + getCause().getMessage();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Throwable#getLocalizedMessage()
     */
    public String getLocalizedMessage() {
        //return super.getLocalizedMessage();
        if ((getCause() == this) || (getCause() == null))
            return super.getLocalizedMessage();
        String causeMsg = getCause().getLocalizedMessage();
        if (causeMsg != null && causeMsg.length() > 0)
            return causeMsg;
        else
            return super.getLocalizedMessage();
        //return (super.getLocalizedMessage() != null ?
                //super.getLocalizedMessage() + ":\n" : "") + getCause().getMessage();
    }
    
}
