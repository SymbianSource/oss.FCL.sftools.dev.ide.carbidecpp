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
package com.nokia.sdt.component.event;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IEventBinding;

import org.eclipse.core.runtime.IStatus;

/**
 * Describes a single event supported by a component
 *
 */
public interface IEventDescriptor {

	/**
	 * Returns the unique internal name for the event
	 */
	String getId();

	/**
	 * Returns localized display text for the event
	 */
	String getDisplayText();
	
	/**
	 * Returns an optional category for the event. Used to
	 * group related events in the event editor.
	 */
	String getCategory();

	/**
	 * Returns localized tooltip text
	 */
	String getDescription();

	/**
	 * Returns a documentation key used with the Help system
	 */
	String getHelpKey();

	/**
	 * Returns the logical group the event is contained in.
	 * Used for filtering.
	 * @return group name, or null  
	 */
	String getGroup();

	/**
	 * Generate handler method name needed for an event binding.
	 * The name may use the passed instance, or other instances accessible
	 * from it (e.g. the parent) in order to generate the name.
	 * @param instance the component exposing the event. This component, or others accessible
	 * from it, such as the parent, may be used as part of the name generation
	 * @param userSpecifiedName if non-null, a name to be used instead of the default. This
	 * may only replace part of the method name, i.e. to allow changing the name of the method, but
	 * not of the class where the method is defined.
	 * @see HandlerMethodInformation
	 */
	HandlerMethodInformation generateHandlerMethodInfo(IComponentInstance instance, String userSpecifiedName);
	
	/**
	 * Validate a user entered event handler name
	 * @return null if valid, or a localized message if not
	 */
	String isValidHandlerName(String proposedName);
	
	/**
	 * Navigate to the handler code for an event, typically
	 * by opening the editor and scrolling to the associated
	 * location.
	 * @param binding the symbol, as returned by generateHandlerMethodInfo,
	 * for the handler.
	 * @param newBinding true if this binding was just created and is being navigated to
	 * for the first time.
	 * @return null (or possibly informational IStatus) if successful. An error
	 * IStatus for failure.
	 */
	IStatus gotoHandlerCode(IEventBinding binding, boolean newBinding);

}
