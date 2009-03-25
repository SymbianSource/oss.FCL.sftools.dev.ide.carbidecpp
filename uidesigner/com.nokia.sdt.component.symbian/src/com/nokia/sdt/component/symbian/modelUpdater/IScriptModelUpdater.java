/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.component.symbian.modelUpdater;

import com.nokia.sdt.component.symbian.scripting.WrappedInstance;
import com.nokia.sdt.datamodel.IDesignerDataModel;

/**
 * This script interface is used to let instances update the model
 *
 */
public interface IScriptModelUpdater {
    /**
     * Update the model
     * @param instance the WrappedInstance
     * @param dataModel the IDesignerDataModel
     */
    public void updateModel(WrappedInstance instance, IDesignerDataModel dataModel);
}
