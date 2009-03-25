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
package com.nokia.carbide.cdt.internal.builder;

import com.nokia.carbide.cdt.builder.project.IEnvironmentVariable;

public class EnvironmentVariable implements IEnvironmentVariable {
	
	private String name;
	private String value;
	private int use;
	
	public EnvironmentVariable(String name, String value, int use){
		this.name = name;
		this.value = value;
		this.use = use;
	}
	
	
	public String getName() {
		return name;
	}

	public int getUse() {
		return use;
	}

	public String getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUse(int use) {
		this.use = use;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		boolean equal = false;
		if (obj instanceof IEnvironmentVariable) {
			IEnvironmentVariable var = (IEnvironmentVariable)obj;
			equal = var.getName().equals(name) && var.getValue().equals(value) && var.getUse() == use;
		}
		return equal;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return name.hashCode();
	}

}
