/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.model.makefile.image;

import com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel;

/**
 * Model that specifically handles scalable icon image Makefiles
 * <p>
 * Views are created with an IImageMakefileViewConfiguration.
 * @see IImageMakefileViewConfiguration
 */
public interface IImageMakefileOwnedModel extends IImageMakefileModel, IOwnedModel<IImageMakefileView> {

}
