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
package com.nokia.sdt.sourcegen.contributions;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.sdt.utils.*;

import java.util.*;

/**
 * Each context frame corresponds to the source generation of one component
 * instance.  These are stored on a stack corresponding to the
 * chain of root -> container -> child calls to createChildContributions()
 */
public class ContributionContext implements IContributionContext {
    public IComponentSourceGen sourceGen;
    public IComponentSourceGenInfo info;
    public IContributionGatherer gatherer;
    //Map locationMap;
    List locationDefiningContributions = new ArrayList();
    public String form;
    public IComponentInstance instance;
    public IComponentInstance refInstance;
    public IComponent component;
    Map variables;
    
    private ContributionContext() {
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String info = instance.getName() + "(" + instance.getComponentId() + ")" ; //$NON-NLS-1$ //$NON-NLS-2$
        if (refInstance != null) {
        	info += " @ref: "  + refInstance.getName() + " (" + refInstance.getComponentId() + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
        return info;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.IContributionContext#getForm()
     */
    public String getForm() {
        return form;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.IContributionContext#getInstance()
     */
    public IComponentInstance getInstance() {
        return instance;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.IContributionContext#getRefInstance()
     */
    public IComponentInstance getRefInstance() {
    	return refInstance;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.IContributionContext#getVariables()
     */
    public Map getVariables() {
        return variables;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.IContributionContext#getComponentSourceGen()
     */
    public IComponentSourceGen getComponentSourceGen() {
        return sourceGen;
    }
    
    /**
     * Create a new stack frame for the given instance, or
     * nothing if this instance does not have source generation
     * @param engine the source engine
     * @param instance
     * @param refInstance TODO
     * @param form
     * @return new StackFrame or null
     */
    static ContributionContext createFrame(ContributionEngine engine, IContributionGatherer gatherer, IComponentInstance instance, IComponentInstance refInstance, String form) {
    	Check.checkArg(form);
        IComponent component = instance.getComponent();
        if (component != null) {
            IComponentSourceGen sourceGen = (IComponentSourceGen) component.getAdapter(IComponentSourceGen.class);
            if (sourceGen != null) {
                ContributionContext frame = new ContributionContext();
                frame.gatherer = gatherer;
                frame.instance = instance;
                frame.refInstance = refInstance;
                frame.component = component;
                frame.form = form;
                frame.sourceGen = sourceGen;
                frame.info = sourceGen.getSourceGenInfo();
                frame.variables = new HashMap();
                
                // add any names specific to the component system
                engine.varProvider.defineInstanceVariables(frame.variables, instance.getEObject());

                // we don't need the Javascript anymore
                sourceGen.trimWorkingSet();

                return frame;
            }
        } else {
        	MessageReporting.emit(IMessage.ERROR, 
        			instance.getDesignerDataModel().getModelSpecifier().createMessageLocation(),
        			"ContributionContext.MissingComponent", //$NON-NLS-1$
        			Messages.getString("ContributionContext.MissingComponent"),  //$NON-NLS-N$  //$NON-NLS-1$
        			new Object[] { instance.getName(), instance.getComponentId() } );
        }
        return null;
    }
}