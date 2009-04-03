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
package com.nokia.sdt.component.symbian.sourcegen;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IComponentScriptAdapter;
import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.emf.component.SourceGenType;
import com.nokia.sdt.scripting.*;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.contributions.SourceGenGlobals;
import com.nokia.sdt.utils.MessageReporting;
import com.nokia.cpp.internal.api.utils.core.*;

import java.util.*;

/**
 * This adapter reads the facets described by the &lt;srcgen&gt;
 * element of a component definition and generates the data
 * we use in IComponentSourceGen.
 * 
 * 
 *
 */
public class SourceGenAdapterScript extends ScriptAdapterImplBase implements IComponentSourceGen {

    private ComponentSourceGenInfo sourceGenInfo;

    private List messages;

    IScriptContext scriptContext;

	private String jsCode;

	private SourceGenType sourceGen;

	private IComponent component;
    
    public SourceGenAdapterScript(IComponent component, 
            SourceGenType sourceGen) throws ScriptException {
        super(IComponentSourceGen.class, IScriptSourceGen.class);

        Check.checkArg(component);
        Check.checkArg(sourceGen);
        
        this.component = component;
        this.sourceGen = sourceGen;
        
        compileSourceGen();
    }
    
    private void compileSourceGen() throws ScriptException {
        messages = new ArrayList();
        IComponentSourceGenInfo baseSourceGenInfo = null;
        
        IComponentScriptAdapter adapter = (IComponentScriptAdapter) component.getAdapter(IComponentScriptAdapter.class);
        
        SourceGenXMLParser parser = new SourceGenXMLParser(component, sourceGen);
        baseSourceGenInfo = parser.base;
        
        sourceGenInfo = parser.getSourceGenInfo();
        jsCode = parser.getJavascript();
        try {
            // compile the Javascript 
            //System.out.println(sourceGenInfo.getJavascript());

            // link up the context to the base sourcegen context
            scriptContext = adapter.getContextForText(
                    SourceGenXMLParser.getNearestComponentSourceGenLocation(sourceGen, component).toOSString(), 
                    jsCode,
                    baseSourceGenInfo != null && baseSourceGenInfo.getScriptContext() != null?
                            baseSourceGenInfo.getScriptContext().getScope()
                            : null
                    );
            sourceGenInfo.setScriptContext(scriptContext);
            
            // get the object we created
            IScriptObject protoImpl = scriptContext.getScope().createInstance(sourceGenInfo.getPrototype());
            
            Check.checkState(protoImpl != null);
            
            init(scriptContext, protoImpl, null, component);
        } catch (ScriptException e) {
            MessageReporting.emitMessage(new Message(IMessage.ERROR,
                    component.createMessageLocation(),
                    "SourceGenAdapterScript.ErrorDuringScript",
                    Messages.getString("SourceGenAdapterScript.ErrorDuringScript"),
                    e.getLocalizedMessage()));
            //ComponentSystemPlugin.log(e);
            // leave sourceGenImpl null
        }
    }

    protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException {
        registrar.register("Engine", new SourceGenGlobals());
    }
    
    private void registerSourceGenGlobals(INameRegistrar registrar, IContributionContext ccontext) throws ScriptException {
        for (Iterator iter = ccontext.getVariables().entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            registrar.register(entry.getKey().toString(), entry.getValue());
        }
    }
    
    public List generateLocation(final IContributionContext context, final String locationId) {
        try {
            Object ret = invokeScriptCode(context.getInstance().getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                    registerSourceGenGlobals(registrar, context);
                }

                public Object run() {
                    WrappedInstance instance = getWrappedInstance(context.getInstance().getEObject());
                    WrappedInstance refInstance = null;
                    if (context.getRefInstance() != null)
                    	refInstance = getWrappedInstance(context.getRefInstance().getEObject());
                    return ((IScriptSourceGen) getImpl()).generateLocation(instance, refInstance, "", locationId);
                }

            });
            
            return (List)ret;
        } catch (ScriptException e) {
            // already logged
            return Collections.EMPTY_LIST;
        }
        
    }
    
    public List generate(final IContributionContext context) {
        try {
            Object ret = invokeScriptCode(context.getInstance().getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                    registerSourceGenGlobals(registrar, context);
                }

                public Object run() {
                    WrappedInstance instance = getWrappedInstance(context.getInstance().getEObject());
                    WrappedInstance refInstance = null;
                    if (context.getRefInstance() != null)
                    	refInstance = getWrappedInstance(context.getRefInstance().getEObject());
                    if (getImpl() != null) {
                        return ((IScriptSourceGen) getImpl()).generate(instance, refInstance, context.getForm());
                    } else
                        return Collections.EMPTY_LIST;
                }

            });
            
            return (List) ret;
            
        } catch (ScriptException e) {
            // already logged
            return Collections.EMPTY_LIST;
        }
        
    }
    
    public List getCompilerMessages() {
        return messages;
    }

    public void resetCompilerMessages() {
        messages.clear();
    }
    
    /**
     * Testing accessor to the script generated by the component.
     * Note: regenerates script if it was already disposed by
     * #trimWorkingSet(). 
     * @return javascript source for sourcegen
     */
    public String getJavascript() throws ScriptException {
    	if (jsCode == null) {
    		compileSourceGen();
    	}
    	return jsCode;
    }
    
    public IComponentSourceGenInfo getSourceGenInfo() {
        return sourceGenInfo;
    }
    
    public void trimWorkingSet() {
    	jsCode = null;
    }
    
}
