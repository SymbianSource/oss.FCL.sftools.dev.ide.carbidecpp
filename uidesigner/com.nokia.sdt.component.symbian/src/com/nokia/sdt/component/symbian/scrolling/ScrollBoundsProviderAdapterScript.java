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
package com.nokia.sdt.component.symbian.scrolling;

import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.datamodel.adapter.IScrollBoundsProvider;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.scripting.ScriptException;

import org.eclipse.swt.graphics.Rectangle;

public class ScrollBoundsProviderAdapterScript extends ScriptAdapterImplBase implements IScrollBoundsProvider {
	public ScrollBoundsProviderAdapterScript() {
        super(IScrollBoundsProvider.class, IScriptScrollBoundsProvider.class);
	}

    protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException {
        // none
    }
    
	public Rectangle getScrollBounds(final ILookAndFeel laf) {
		Rectangle rectangle = null;
		
        try {
    	    rectangle = (Rectangle) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                    WrappedInstance instance = getWrappedInstance();
                    return ((IScriptScrollBoundsProvider) getImpl()).getScrollBounds(instance, laf);
                }

            });
        } catch (ScriptException e) {
            // already logged
        }
        
		return rectangle;
	}

    

}
