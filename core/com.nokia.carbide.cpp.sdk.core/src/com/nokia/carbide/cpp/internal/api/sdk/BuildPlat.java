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
*/
package com.nokia.carbide.cpp.internal.api.sdk;

public class BuildPlat {
	
	public static final String EKA2_IDENTIFIER = "EKA2";
	
	private String platName;
	private String osIdentifier;
	private boolean enabled;
	
	public BuildPlat(String platName, String osIdentifier, boolean enabled){
		this.platName = platName;
		this.osIdentifier = osIdentifier;
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getOsIdentifier() {
		return osIdentifier;
	}
	public void setOsIdentifier(String osVersion) {
		this.osIdentifier = osVersion;
	}
	public String getPlatName() {
		return platName;
	}
	public void setPlatName(String platName) {
		this.platName = platName;
	}
}