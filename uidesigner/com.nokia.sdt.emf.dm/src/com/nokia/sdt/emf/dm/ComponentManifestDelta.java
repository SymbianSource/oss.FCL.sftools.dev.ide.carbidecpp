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
package com.nokia.sdt.emf.dm;

import org.osgi.framework.Version;

	/**
	 * Information about a changed or missing component
	 */
public class ComponentManifestDelta {
	
	// Can't use enum here because EMF < 2.3 will not handle it
	public static final int NEWER = 0;   // current component is newer than manifest
	public static final int OLDER = 1;   // current component is older than manifest
	public static final int MISSING = 2; // component is not present
	
	private String componentID;
	private int type;
	private Version oldVersion;
	private Version newVersion;

	/**
	 * Creates a NEWER/OLDER delta
	 */
	public ComponentManifestDelta(String componentID,
			int type,
			Version oldVersion, Version newVersion) {
		checkType(type);
		this.componentID = componentID;
		this.type = type;
		this.oldVersion = oldVersion;
		this.newVersion = newVersion;
	}

	public String getComponentID() {
		return componentID;
	}
	
	public int getType() {
		return type;
	}

	public Version getNewVersion() {
		return newVersion;
	}

	public Version getOldVersion() {
		return oldVersion;
	}
	
	void checkType(int type) {
		switch (type) {
		case NEWER:
		case OLDER:
		case MISSING:
			break;
		default:
			throw new IllegalArgumentException();
		}
	}
}
