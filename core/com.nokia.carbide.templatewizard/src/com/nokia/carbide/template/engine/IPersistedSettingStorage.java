/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.template.engine;

/**
 * This interface is used in {@link ILoadedTemplate#createLoadedTemplateUI()} and
 * {@link ILoadedTemplateUI#persistValues(IPersistedSettingStorage)} to read and write
 * the persisted settings to a client-implemented store. 
 * @since 2.1
 *
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IPersistedSettingStorage {
	/**
	 * Read the value for the stored property 'key'
	 * @param key property key
	 * @return value or <code>null</code> if never persisted
	 */
	String read(String key);
	
	/** 
	 * Store the value for the property 'key'.  
	 * @param key property key
	 * @param value value or <code>null</code> to delete
	 */
	void write(String key, String value);
}
