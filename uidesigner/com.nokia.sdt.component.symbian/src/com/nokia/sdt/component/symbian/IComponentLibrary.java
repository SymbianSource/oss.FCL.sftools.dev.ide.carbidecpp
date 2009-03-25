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
package com.nokia.sdt.component.symbian;

import com.nokia.sdt.component.*;

import org.osgi.framework.Bundle;

import java.util.Iterator;
import java.util.regex.Pattern;


/** This interface describes access to a library of components.
 * Each library can have a unique component schema, loading behavior,
 * etc.
 * 
 * 
 *
 */
public interface IComponentLibrary extends Iterable<IComponent> {

    /** 
     * Tell if the library is loaded.
     */
    public boolean isLoaded();

    /** 
     * Load the components for the library.  Has no effect
     * if library is already loaded. */ 
    public void loadComponents() throws ComponentSystemException;

    /** 
     * Refresh the components by searching for added, removed,
     * and changed components. 
     */
    public void refreshComponents() throws ComponentSystemException;
    
    /** 
     * Get an iterator over the components.  
     * 
     * The iterator refers to an unmodifiable collection, so
     * synchronization is not required. 
     * 
     * You must invoke loadComponents() first.  
     */
    public Iterator<IComponent> iterator();

    /** 
     * Get the library's unique name 
     */
    public String getId();

    /**
     * Return all the components in the component library
     * 
     * @param filter
     *            filter component library (can be null to accept all)
     * @return list of components
     */
    public IComponent[] getComponents(IComponentFilter filter);

    /** Get the interface to the named component */
    //public IComponent getComponent(String className);
	
	void setProvider(IComponentProvider provider);
	IComponentProvider getProvider();
	
    /** Set the library's bundle, for plug-in component libraries */
	void setBundle(Bundle bundle);
	
    /**
     *  Get the library's bundle, for plug-in component libraries
     * @return Bundle (may be null)
     */
	Bundle getBundle();
	
	/**
	 * Get the library's SDK vendor regex.
	 * @return pattern used to match supported SDK vendor strings. Never null.
	 */
	Pattern getSDKVendorPattern();
}
