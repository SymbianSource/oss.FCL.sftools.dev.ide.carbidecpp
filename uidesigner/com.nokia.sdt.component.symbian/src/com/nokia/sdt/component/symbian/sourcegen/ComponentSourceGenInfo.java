/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.sdt.scripting.IScriptContext;
import com.nokia.sdt.sourcegen.IComponentSourceGenInfo;

import java.util.Collections;
import java.util.Map;

public class ComponentSourceGenInfo implements IComponentSourceGenInfo {
    String prototype;
    private Map templateIdMap;
    private Map templateGroupMap;
    private Map locationMap;
    private IScriptContext scriptContext;
	private IComponentSourceGenInfo base;
	private SourceGenMacroSupport macroSupport;

    public ComponentSourceGenInfo(
    		IComponentSourceGenInfo base,
            String prototype, 
            Map templateIdMap, Map templateGroupMap,
            Map locationMap,
            SourceGenMacroSupport macroSupport) {
    	this.base = base;
        this.prototype = prototype;
        this.templateIdMap = templateIdMap;
        this.templateGroupMap = templateGroupMap;
        this.locationMap = locationMap;
        this.macroSupport = macroSupport;
    }

    public IComponentSourceGenInfo getBaseInfo() {
    	return base;
    }
    
    public Map getLocationIdToLocationMap() {
        return Collections.unmodifiableMap(locationMap);
    }
    
    public String getPrototype() {
        return prototype;
    }

    public Map getTemplateIdToSourceGenTemplateMap() {
        return Collections.unmodifiableMap(templateIdMap);
    }

    public Map getTemplateGroupIdToSourceGenTemplateGroupMap() {
        return Collections.unmodifiableMap(templateGroupMap);
    }
    
    public void setScriptContext(IScriptContext context) {
        this.scriptContext = context;
    }
    public IScriptContext getScriptContext() {
        return scriptContext;
    }
    public Map getNameToMacroMap() {
    	return macroSupport.getNameToMacroMap();
    }

}