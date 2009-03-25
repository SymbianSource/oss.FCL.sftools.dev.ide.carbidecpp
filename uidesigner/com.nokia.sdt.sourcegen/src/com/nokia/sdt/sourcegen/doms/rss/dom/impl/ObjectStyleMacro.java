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

package com.nokia.sdt.sourcegen.doms.rss.dom.impl;

import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IObjectStyleMacro;

/**
 * 
 *
 */
public class ObjectStyleMacro extends Macro implements IObjectStyleMacro {

    /**
     * @param name
     * @param expansion must not be null
     */
    public ObjectStyleMacro(String name, String expansion) {
        super(name, expansion);
    }

}
