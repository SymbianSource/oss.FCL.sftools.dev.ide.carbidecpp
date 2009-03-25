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
package com.nokia.sdt.utils.drawing;

import java.io.File;

/** 
 * Factory for creating font renderers from files. 
 *
 */
public class FontFactory {
    static public IFont createFromFile(String filename, float size) {
        return new AwtFont(filename, size);
    }

    public static IFont createFromFile(File file, float size) {
        return createFromFile(file.getAbsolutePath(), size);
    }
}
