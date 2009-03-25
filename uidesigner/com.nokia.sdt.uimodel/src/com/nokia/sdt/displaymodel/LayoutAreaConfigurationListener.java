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


package com.nokia.sdt.displaymodel;

import com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration;

import java.util.Collection;

/**
 * 
 * <br><br>
 * A listener for layout area configuration changes
 */
public interface LayoutAreaConfigurationListener {
	
	/**
	 * Notification that a new configuration is about to be applied,
	 * but is not applied yet. This is an opportunity to invalidate
	 * cached state without the need to rely on the ordering of
	 * configurationChanged events.
	 */
	void configurationChanging(LayoutAreaConfiguration configuration);
	/**
	 * @param configuration a <code>LayoutAreaConfiguration</code>
	 */
	void configurationChanged(LayoutAreaConfiguration configuration);
	
	/**
	 * Notification that the set of supported LayoutAreaConfigurations
	 * has changed. Maybe accompanied by configurationChanging and
	 * configurationChanged events.
	 * @param newConfigurations collection of LayoutAreaConfiguration
	 */
	void configurationSetChanged(Collection newConfigurations);
}