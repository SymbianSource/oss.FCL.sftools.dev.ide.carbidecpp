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


package com.nokia.sdt.component.symbian.querycontainment;

import com.nokia.sdt.component.symbian.scripting.WrappedComponent;
import com.nokia.sdt.component.symbian.scripting.WrappedInstance;

import org.eclipse.core.runtime.IStatus;

public interface IScriptQueryContainment {
	
    /**
     * Returns true if the container can add a new instance of
     * the given component. This method should check:
     *  - the static constraints of the component
     *  - the static constraints of the container
     *  - the dynamic constraints of the container.
     * @param instance
     * @param component
     * @return IStatus.OK or error status explaining why not
     */
	public IStatus canContainComponent(WrappedInstance instance, WrappedComponent component);

    /**
     * Returns true if the container can add the child.
     * This method should check:
     *    - the static constraints of the child's component
     *    - the dynamic constraints of the child
     *    - the static constraints of the container
     *    - the dynamic constraints of the container.
     * @param instance
     * @param child
     * @return IStatus.OK or error status explaining why not
     */
	public IStatus canContainChild(WrappedInstance instance, WrappedInstance child);

    /**
     * Tell whether the given instance can remove a given child instance
     * @param instance
     * @param child
     * @return true if possible
     */
	public boolean canRemoveChild(WrappedInstance instance, WrappedInstance child);

    /**
     * Returns true if the container allows the given component to be in the palette
     * @param component component to check
     * @return true if possible
     */
	public boolean isValidComponentInPalette(WrappedInstance instance, WrappedComponent component);
}