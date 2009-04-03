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

package com.nokia.sdt.sourcegen.templates.core;

/**
 * Catch-all exception for problems arising from the
 * compilation process.
 * 
 * 
 *
 */
public class TemplateException extends Exception {

    private static final long serialVersionUID = 1L;

    public TemplateException(Throwable wrapped) {
        super(wrapped);
    }
}
