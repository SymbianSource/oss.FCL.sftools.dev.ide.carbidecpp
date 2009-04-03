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

package com.nokia.carbide.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.cpp.epoc.engine.model.IModel;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;

/**
 * An MMP model.  Views created on this model must pass an
 * {@link IMMPViewConfiguration} instance to {@link IModel#createView(IViewConfiguration)}.
 * @see IMMPViewConfiguration
 * @see IViewConfiguration
 */
public interface IMMPModel extends IModel<IMMPView> {

}
