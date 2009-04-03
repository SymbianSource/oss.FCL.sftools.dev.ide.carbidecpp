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

package com.nokia.sdt.sourcegen;

import com.nokia.sdt.scripting.IScriptContext;

import java.util.Map;

/**
 * Information gathered from parsing the &lt;sourcegen&gt; XML
 * 
 *
 */
public interface IComponentSourceGenInfo {
	/** Get the base information */
	IComponentSourceGenInfo getBaseInfo();
	
    /** Get the location map (String -> ISourceGenLocation) */
    Map getLocationIdToLocationMap();
    
    /** Get the prototype name */
    String getPrototype();
    
    /** Get the map of template ids to ISourceGenTemplate 
     * <p>
     * The map has an ordered set of keys following the template
     * definition/inclusion order. 
     */
    Map getTemplateIdToSourceGenTemplateMap();
    
    /** Get the map of template group ids to ISourceGenTemplateGroup
     * <p>
     * The map has an ordered set of keys following the template group
     * definition/inclusion order. 
     */
    Map getTemplateGroupIdToSourceGenTemplateGroupMap();
    
    /**
     * Get the context
     */
    IScriptContext getScriptContext();
    
    /** 
     * Get the macros declared
     * @see ResolvedMacro
     */
    Map getNameToMacroMap();
}
