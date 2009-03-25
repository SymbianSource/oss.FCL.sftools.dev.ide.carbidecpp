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

import com.nokia.cpp.internal.api.utils.core.MessageLocation;

import java.util.List;

/**
 * A location defined in &lt;defineLocation&gt; XML.
 * This is the raw information provided by the component,
 * and returned strings may contain references to variables.  
 * 
 *
 */
public interface ISourceGenLocation {
    /** Tell which component this was defined for; this is an identifier wherein
     * all the source gen location ids should be unique */
    public String getComponentId();
    
    /** Create a message location that points the user to the source responsible
     * for this location
     * @return message location, never null
     */
    public MessageLocation createMessageLocation();
    
    /** Get the base location, or null;
     * if not null, then domain, directory, and file should be
     * retrieved from the returned value; this interface will return null. */
    public String getBaseLocation();
    
    /** Get the domain named by the location */
    public String getDomain();
    /** Get the directory specifier (not variable replaced) */
    public String getDirectory();
    /** Get the file specifier (not variable-replaced) */
    public String getFile();
    
    /** Get the identifier, unique in the call stack of components */
    public String getId();
    
    /** Get the location path segment.
     * <p>
     * If getBaseLocation() returns non-null, then the full
     * location should be constructed by catenating its location 
     * and this one.
     * @return the location segment
     */
    public String getLocation();
    
    /** Tell whether the location is owned or not.
     * This tells if the location is generated from scratch every
     * time (true) or only generated once (false).
     */
    public boolean isOwned();
    
    /** Get the Javascript function name defined to create
     * the contributions that initialize this location */
    public String getFunction();
    
    /** Get the filter name: blank, 'default' are returned as null */
    public String getFilter();
    
    /** Get the events on which to switch
     * @return event names or null */
    public List getIfEvents();
    
    /** Tell whether the location defines the event handler (when getIfEvent is true)
     * This refers to the method which the user edits, not anything
     * required by the API. 
     */
    public boolean isEventHandler();

	/**
	 * Tell if the location is variable or based on variables 
	 */
	public boolean isVariable();
}
