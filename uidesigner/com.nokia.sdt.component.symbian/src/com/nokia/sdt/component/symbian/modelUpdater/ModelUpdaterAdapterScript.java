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
package com.nokia.sdt.component.symbian.modelUpdater;

import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IModelUpdater;
import com.nokia.sdt.scripting.ScriptException;

public class ModelUpdaterAdapterScript extends ScriptAdapterImplBase implements IModelUpdater {
	public ModelUpdaterAdapterScript() {
        super(IModelUpdater.class, IScriptModelUpdater.class);
	}

    protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException {
        // none
    }
    
	public void updateModel(final IDesignerDataModel dataModel) {
        try {
    	    invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                    WrappedInstance instance = getWrappedInstance();
                    ((IScriptModelUpdater) getImpl()).updateModel(instance, dataModel);
                    return null;
                }

            });
        } catch (ScriptException e) {
            // already logged
        }
	}

}
