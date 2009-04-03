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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib;

import java.net.URL;

import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX.InterXProperties;

/**
 * A property class holds information about a developer library.
 */
public class DevLibProperties {
	private String interXRootDir = ""; // relative path of interchange file
	private URL devLibURL; // url of developer library
	protected InterXProperties interXProperties; // properties of interX file
	// user friendly name of developer library used in preference page
	private String userFriendlyName;
	private boolean valid;

	public String getUserFriendlyName() {
		return userFriendlyName;
	}

	public void setUserFriendlyName(String userFriendlyName) {
		this.userFriendlyName = userFriendlyName;
	}

	protected DevLibProperties() {

	}

	public DevLibProperties(URL devLibURL) {
		this.devLibURL = devLibURL;
	}

	protected void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isValid() {
		return valid && interXProperties != null && interXProperties.isValid();
	}

	public InterXProperties getInterXProperties() {
		return interXProperties;
	}

	public void setInterXProperties(InterXProperties interXProperties) {
		this.interXProperties = interXProperties;
		setUserFriendlyName(interXProperties.getUserFriendlyName());
	}

	public String getInterXRootDir() {
		return interXRootDir;
	}

	public void setInterXRootDir(String interXRootDir) {
		this.interXRootDir = interXRootDir;
	}

	public URL getDevLibURL() {
		return devLibURL;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((interXProperties == null) ? 0 : interXProperties.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevLibProperties other = (DevLibProperties) obj;
		if (interXProperties == null) {
			if (other.interXProperties != null)
				return false;
		} else if (!interXProperties.equals(other.interXProperties))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return interXProperties.toString();
	}
}
