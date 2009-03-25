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

package com.nokia.sdt.datamodel.images;


/**
 * A provider for image information in the data model.
 * There is one of these per Eclipse project, i.e. the
 * one that owns a data model. 
 * 
 *
 */
public interface IProjectImageInfoProvider {
    /**
     * Get information about the project's image files.
     * @return image data for project
     */
    IProjectImageInfo getProjectImageInfo();
    
}
