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
package com.nokia.carbide.cpp.debug.kernelaware;

public class OSObjectProperty {
	
	private String id;
	private Object value;

	public OSObjectProperty(String id, Object value) {
		this.id = id;
		this.value = value;
	}
	
	public OSObjectProperty(OSObjectProperty src) {
		this.id = src.id;
		this.value = src.value;
	}

	public String getID() {
		return id;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
}
