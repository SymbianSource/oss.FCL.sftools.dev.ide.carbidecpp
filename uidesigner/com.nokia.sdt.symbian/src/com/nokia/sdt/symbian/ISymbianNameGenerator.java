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

package com.nokia.sdt.symbian;

import com.nokia.sdt.sourcegen.INameGenerator;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

/**
 * 
 *
 */
public interface ISymbianNameGenerator extends INameGenerator {
    /** Identifier for resource directory */
    public static final String RESOURCE_DIRECTORY_ID = "resource"; //$NON-NLS-1$
    /** Identifier for build directory */
    public static final String BUILD_DIRECTORY_ID = "build"; //$NON-NLS-1$

    public String getRssBaseName(IDesignerDataModelSpecifier dmSpec);
}
