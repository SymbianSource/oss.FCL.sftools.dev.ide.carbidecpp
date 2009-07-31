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
package com.nokia.sdt.component.symbian.scripting;

import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptContext;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptScope;
import com.nokia.sdt.component.symbian.visual.javascript.*;
import com.nokia.sdt.displaymodel.GlobalCache;
import com.nokia.sdt.scripting.*;
import com.nokia.sdt.utils.ImageUtils;
import com.nokia.sdt.utils.drawing.ImageDump;
import com.nokia.sdt.utils.drawing.TextRendering;

import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;

import java.io.StringReader;

public class ComponentScriptingManager {

    public static final class ScriptContextFactory implements IScriptContextFactory {
        public IScriptContext createScriptContext(IScriptScope parentScope, boolean provideTransient) throws ScriptException {
            IScriptContext context = new ScriptContext(parentScope, provideTransient);
            ScriptGlobals.setupGlobals(context.getScope());
            ComponentGlobals.setupGlobals(context.getScope());
            return context;
        }
    }

    static final String INSTANCE_ID = "ComponentScriptManager.GLOBAL_SCOPE_ID";
    private ScriptScope globalScope;
    
    private ComponentScriptingManager() {
        globalScope = new ScriptScope(null, ScriptingManager.getInstance().getGlobalScope());
        ScriptingManager.getInstance().setScriptContextFactory(new ScriptContextFactory());
        try {
            setupGlobals();
        } catch (ScriptException e) {
        }
    }
    
    /**
     * Get the global instance of the component script manager,
     * shared across all components, component sets, and data models.
     * @return singleton ComponentScriptManager
     */
    static public ComponentScriptingManager getInstance() {
        ComponentScriptingManager manager = (ComponentScriptingManager) GlobalCache.getCache().get(INSTANCE_ID);
        if (manager == null) {
            manager = new ComponentScriptingManager();
            GlobalCache.getCache().put(INSTANCE_ID, manager);
        }
        return manager;
    }

    /**
     * Get the global scope that all component scripts share.
     * Any objects registered directly should be:
     * <ul><li>globals general to all components,
     * <li>those that take a parameter specifying the component, or
     * <li>those that reference 'componentBaseDir'.
     * </ul><p>
     * For any other objects that depend on the "current component", 
     * use the transient global scope via IComponentScriptAdapter.
     */
    public IScriptScope getComponentGlobalScope() {
        return globalScope;
    }
    
    /**
     * Set up the globals that available to all components
     * @throws ScriptException
     */
    private void setupGlobals() throws ScriptException {
        /* Put visual-specific globals in scope */
        globalScope.defineObject("Colors", new Colors()); //$NON-NLS-1$
        globalScope.defineObject("Fonts", new Fonts()); //$NON-NLS-1$
        globalScope.defineObject("Images", new Images()); //$NON-NLS-1$

        /*
         * We don't expose a real font class because we need an enhanced
         * API that allows loading Truetype fonts at runtime (plus, we
         * don't expect to use "normal" fonts for rendering).  Instead,
         * this class exposes constants for use in controlling text
         * drawing through GC.drawFormattedString(). 
         */
        globalScope.defineObject("Font", JsFont.class); //$NON-NLS-1$

        /* 
         * Rhino isn't as cool as we want.  We need to make the
         * names of these classes visible to script, but the wrap()
         * and defineClass() methods expect classes to be Rhino-enabled
         * (i.e. implementing Scriptable, having a single zero-argument
         * constructor, etc).  It's a pain to do this for SWT classes
         * since they're all FINAL, meaning we need to reimplement
         * the entire interface and call through to the final class.  
         * Fortunately, the implicit import mechanism does what we 
         * want, by exposing non-modified existing Java classes.  
         * We make use of this here. 
         */
        
        IScriptContext swtIncludes = new ScriptContext(null, false);
        swtIncludes.compileAndExecute(new Path("<SWT imports>"), new StringReader( //$NON-NLS-1$
                "Rectangle = Packages." + Rectangle.class.getName() + "\n" + //$NON-NLS-1$ org.eclipse.swt.graphics.Rectangle
                "Point = Packages." + Point.class.getName() + "\n" + //$NON-NLS-1$  org.eclipse.swt.graphics.Point
                "Color = Packages." + Color.class.getName() + "\n" + //$NON-NLS-1$  org.eclipse.swt.graphics.Color
                "Transform = Packages." + Transform.class.getName() + "\n" + //$NON-NLS-1$ org.eclipse.swt.graphics.Transform
                "Path = Packages." + Path.class.getName() + "\n" + //$NON-NLS-1$ org.eclipse.swt.graphics.Path
                "Pattern = Packages." + Pattern.class.getName() + "\n" + //$NON-NLS-1$ org.eclipse.swt.graphics.Pattern
                "Region = Packages." + Region.class.getName() + "\n" + //$NON-NLS-1$ org.eclipse.swt.graphics.Region
                "Image = Packages." + Image.class.getName() + "\n" + //$NON-NLS-1$ org.eclipse.swt.graphics.Image
                "ImageData = Packages. " + ImageData.class.getName() + "\n" + //$NON-NLS-1$ org.eclipse.swt.graphics.ImageData
                "PaletteData = Packages." + PaletteData.class.getName() + "\n" + //$NON-NLS-1$ org.eclipse.swt.graphics.PaletteData
                "GC = Packages.com.nokia.sdt.utils.drawing.GC\n"+ //$NON-NLS-1$
                "SWT = Packages." + SWT.class.getName() + "\n" //$NON-NLS-1$ org.eclipse.swt.SWT
        ));

        globalScope.include(swtIncludes.getScope());

        // Also expose designer-specific classes
        IScriptContext otherIncludes = new ScriptContext(null, false);
        otherIncludes.compileAndExecute(new Path("<Designer imports>"), new StringReader( //$NON-NLS-1$
                "TextUtils = 		Packages." + TextUtils.class.getName() + "\n" + //$NON-NLS-1$
                "TextRendering = 	Packages." + TextRendering.class.getName() + "\n" + //$NON-NLS-1$
        		"ImageDump = 		Packages." + ImageDump.class.getName() + "\n" + //$NON-NLS-1$
        		"ImageUtils = 		Packages."  + ImageUtils.class.getName() + "\n" //$NON-NLS-1$
        ));

        globalScope.include(otherIncludes.getScope());

    }
}

