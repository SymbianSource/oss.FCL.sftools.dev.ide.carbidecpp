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
package com.nokia.sdt.testsupport;



import org.eclipse.core.runtime.Plugin;


/**
 * This class holds a reference to the invoking plugin so
 * plugin-relative tests can work easier.
 * 
 * 
 *
 */
public class TestHelpers {

    static public Plugin plugin;
    
    /**
     * Set up this class with the plugin relative to which we should
     * find files for #plugnRelativeFile(String).
     * 
     * @param hostingPlugin the plugin -- may be null
     */
    public static void setPlugin(Plugin hostingPlugin) {
        plugin = hostingPlugin;
    }
    
}
