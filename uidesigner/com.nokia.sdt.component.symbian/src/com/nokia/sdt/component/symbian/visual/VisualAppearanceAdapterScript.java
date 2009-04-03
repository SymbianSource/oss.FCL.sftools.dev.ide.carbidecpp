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
package com.nokia.sdt.component.symbian.visual;

import com.nokia.sdt.component.symbian.scripting.INameRegistrar;
import com.nokia.sdt.component.symbian.scripting.IScriptCodeWrapper;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.datamodel.adapter.IVisualAppearance;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.scripting.ScriptException;
import com.nokia.sdt.utils.drawing.GC;

import org.eclipse.swt.graphics.Point;

public class VisualAppearanceAdapterScript extends ScriptAdapterImplBase implements IVisualAppearance {
	
	public VisualAppearanceAdapterScript() {
        super(IVisualAppearance.class, IScriptVisualAppearance.class);

	}
	
	protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException {
	    // none
	}
    
	public void draw(final GC gc, final ILookAndFeel laf) {
        try {
            invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                    /* draw! */
                    ((IScriptVisualAppearance) getImpl()).draw(getWrappedInstance(), laf, gc);
                    return null;
                } }
            );
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
							return ((IScriptVisualAppearance) getImpl()).getPreferredSize(getWrappedInstance(), laf, wHint, hHint);
						}
					});
		} catch (ScriptException e) {
			// already logged
		}
		return point;
	}
	
}
