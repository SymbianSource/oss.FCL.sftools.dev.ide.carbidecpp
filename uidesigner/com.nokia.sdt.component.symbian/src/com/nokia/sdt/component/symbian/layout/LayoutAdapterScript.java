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
package com.nokia.sdt.component.symbian.layout;

import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.scripting.ScriptException;

import org.eclipse.swt.graphics.Point;

public class LayoutAdapterScript extends ScriptAdapterImplBase implements ILayout {
	public LayoutAdapterScript() {
        super(ILayout.class, IScriptLayout.class);
	}

    protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException {
        // none
    }
    
	public void layout(final ILookAndFeel laf) {
        try {
    	    invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                    WrappedInstance instance = getWrappedInstance();
                    ((IScriptLayout) getImpl()).layout(instance, laf);
                    return null;
                }

            });
        } catch (ScriptException e) {
            // already logged
        }
	}

	public Point getPreferredSize(final int wHint, final int hHint, final ILookAndFeel laf) {
		Point point = null;
		try {
			point = (Point) invokeScriptCode(getEObject(),
					new IScriptCodeWrapper() {

						public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
						}

						public Object run() {
							/* get the size! */
							return ((IScriptLayout) getImpl()).getPreferredSize(getWrappedInstance(), laf, wHint, hHint);
						}
					});
		} catch (ScriptException e) {
			// already logged
		}
		return point;
	}

    

}
