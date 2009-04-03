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
package com.nokia.sdt.component.symbian.visual.javascript;

import com.nokia.sdt.utils.drawing.IFontConstants;

import org.mozilla.javascript.ScriptableObject;

/**
 * Dummy class meant solely to expose constants to Javascript.
 * 
 * 
 *
 */
public class JsFont extends ScriptableObject implements IFontConstants {
    private static final long serialVersionUID = 1L;
 
    public JsFont() {
    }

    public String getClassName() {
        return "Font";
    }
}