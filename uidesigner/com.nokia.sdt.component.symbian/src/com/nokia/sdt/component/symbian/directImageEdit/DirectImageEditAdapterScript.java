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
package com.nokia.sdt.component.symbian.directImageEdit;

import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.datamodel.adapter.IDirectImageEdit;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.scripting.ScriptException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class DirectImageEditAdapterScript extends ScriptAdapterImplBase implements IDirectImageEdit {
	public DirectImageEditAdapterScript() {
        super(IDirectImageEdit.class, IScriptDirectImageEdit.class);
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
                    return ((IScriptDirectImageEdit) getImpl()).getImagePropertyPaths(instance);
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
                    return ((IScriptDirectImageEdit) getImpl()).getImageBounds(instance, propertyPath, laf);
                }

            });
        } catch (ScriptException e) {
            // already logged
        }
        return bounds;
	}

    public IStatus validateImage(final String propertyId, final ILookAndFeel laf, final String filePath, final Point size) {
        IStatus status = null;
        try {
            status = (IStatus) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                    WrappedInstance instance = getWrappedInstance();
                    return ((IScriptDirectImageEdit) getImpl()).validateImage(instance, propertyId, laf, filePath, size);
                }
            });
        } catch (ScriptException e) {
            // already logged
        }
        return status;
    }
   
    public boolean isScaling(final String propertyId, final ILookAndFeel laf) {
        Boolean ret = null;
        try {
            ret = (Boolean) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                    WrappedInstance instance = getWrappedInstance();
                    return ((IScriptDirectImageEdit) getImpl()).isScaling(instance, propertyId, laf);
                }
            });
        } catch (ScriptException e) {
            // already logged
        }
        return ret != null ? ret.booleanValue() : false;
    }
 

    public Point getRenderedImageSize(final String propertyId, final ILookAndFeel laf, final Point size) {
    	Point realSize = null;
        try {
        	realSize = (Point) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                    WrappedInstance instance = getWrappedInstance();
                    return ((IScriptDirectImageEdit) getImpl()).getRenderedImageSize(instance, propertyId, laf, size);
                }
            });
        } catch (ScriptException e) {
            // already logged
        }
        return realSize;
    }
}
