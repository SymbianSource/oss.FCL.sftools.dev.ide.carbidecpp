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
/**
 * 
 */
package com.nokia.sdt.component.symbian;

/** A filter for component libraries in IComponentSystem
 * 
 * 
 * 
 */
public interface IComponentLibraryFilter {
    /** Return true if the component library should be included */
    public boolean accept(IComponentLibrary lib);
}
