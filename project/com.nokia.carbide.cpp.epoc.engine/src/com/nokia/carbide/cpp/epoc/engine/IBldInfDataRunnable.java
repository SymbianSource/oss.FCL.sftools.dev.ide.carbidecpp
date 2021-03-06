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

package com.nokia.carbide.cpp.epoc.engine;

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfData;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfModel;

/**
 * Instantiate this interface and pass to EpocEnginePlugin#runWithMMPData()
 * to encapsulate some of the bookkeeping of model/view handling. 
 *
 */
public interface IBldInfDataRunnable extends IDataRunnable<IBldInfModel, IBldInfData> {

}
