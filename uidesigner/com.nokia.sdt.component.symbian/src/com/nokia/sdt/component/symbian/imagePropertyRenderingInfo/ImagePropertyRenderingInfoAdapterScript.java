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
package com.nokia.sdt.component.symbian.imagePropertyRenderingInfo;

import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.datamodel.adapter.IImagePropertyRenderingInfo;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.scripting.ScriptException;

import org.eclipse.swt.graphics.Point;

public class ImagePropertyRenderingInfoAdapterScript extends ScriptAdapterImplBase implements IImagePropertyRenderingInfo {
	public ImagePropertyRenderingInfoAdapterScript() {
        super(IImagePropertyRenderingInfo.class, IScriptImagePropertyRenderingInfo.class);
	}

    protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException {
        // none
    }
    
    public boolean isScaling(final String propertyId, final ILookAndFeel laf) {
        Boolean ret = null;
        try {
            ret = (Boolean) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                    WrappedInstance instance = getWrappedInstance();
                    return ((IScriptImagePropertyRenderingInfo) getImpl()).isScaling(instance, propertyId, laf);
                }
            });
        } catch (ScriptException e) {
            // already logged
        }
        return ret != null ? ret.booleanValue() : false;
    }

    public boolean isPreservingAspectRatio(final String propertyId, final ILookAndFeel laf) {
        Boolean ret = null;
        try {
            ret = (Boolean) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                    WrappedInstance instance = getWrappedInstance();
                    return ((IScriptImagePropertyRenderingInfo) getImpl()).isPreservingAspectRatio(instance, propertyId, laf);
                }
            });
        } catch (ScriptException e) {
            // already logged
        }
        return ret != null ? ret.booleanValue() : false;
    }

    public Point getViewableSize(final String propertyId, final ILookAndFeel laf) {
        Point ret = null;
        try {
            ret = (Point) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                    WrappedInstance instance = getWrappedInstance();
                    return ((IScriptImagePropertyRenderingInfo) getImpl()).getViewableSize(instance, propertyId, laf);
                }
            });
        } catch (ScriptException e) {
            // already logged
        }
        return ret;
    }

    public Point getAlignmentWeights(final String propertyId, final ILookAndFeel laf) {
        Point ret = null;
        try {
            ret = (Point) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                    WrappedInstance instance = getWrappedInstance();
                    return ((IScriptImagePropertyRenderingInfo) getImpl()).getAlignmentWeights(instance, propertyId, laf);
                }
            });
        } catch (ScriptException e) {
            // already logged
        }
        return ret;
    }
 
}
