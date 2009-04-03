/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.scrolling;

import com.nokia.sdt.component.symbian.scripting.WrappedInstance;
import com.nokia.sdt.displaymodel.ILookAndFeel;

import org.eclipse.swt.graphics.Rectangle;

/**
 * This script interface is used to define the scroll bounds for a scrolling container.
 *  This is optional for scrolling containers that can have scroll area smaller than its bounds.
 *
 */
public interface IScriptScrollBoundsProvider {
    /**
     * @return the current scroll bounds in local coordinates
     */
    public Rectangle getScrollBounds(WrappedInstance instance, ILookAndFeel laf);
}
