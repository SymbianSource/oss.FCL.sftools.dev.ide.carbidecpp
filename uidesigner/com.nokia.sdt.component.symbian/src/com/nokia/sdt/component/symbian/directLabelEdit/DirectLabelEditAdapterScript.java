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
package com.nokia.sdt.component.symbian.directLabelEdit;

import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.datamodel.adapter.IDirectLabelEdit;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.scripting.ScriptException;
import com.nokia.sdt.utils.drawing.IFont;

import org.eclipse.swt.graphics.Rectangle;

public class DirectLabelEditAdapterScript extends ScriptAdapterImplBase implements IDirectLabelEdit {
	public DirectLabelEditAdapterScript() {
        super(IDirectLabelEdit.class, IScriptDirectLabelEdit.class);
	}

    protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException {
        // none
    }
    
	public String[] getPropertyPaths() {
		String[] paths = null;
        try {
    	    paths = (String[]) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                    WrappedInstance instance = getWrappedInstance();
                    return ((IScriptDirectLabelEdit) getImpl()).getPropertyPaths(instance);
                }

            });
        } catch (ScriptException e) {
            // already logged
        }
        return paths;
	}

	public Rectangle getVisualBounds(final String propertyPath, final ILookAndFeel laf) {
		Rectangle bounds = null;
        try {
    	    bounds = (Rectangle) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                    WrappedInstance instance = getWrappedInstance();
                    return ((IScriptDirectLabelEdit) getImpl()).getLabelBounds(instance, propertyPath, laf);
                }

            });
        } catch (ScriptException e) {
            // already logged
        }
        return bounds;
	}

	public IFont getLabelFont(final String propertyPath, final ILookAndFeel laf) {
		IFont font = null;
        try {
        	font = (IFont) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                    WrappedInstance instance = getWrappedInstance();
                    return ((IScriptDirectLabelEdit) getImpl()).getLabelFont(instance, propertyPath, laf);
                }

            });
        } catch (ScriptException e) {
            // already logged
        }
        return font;
	}

}
