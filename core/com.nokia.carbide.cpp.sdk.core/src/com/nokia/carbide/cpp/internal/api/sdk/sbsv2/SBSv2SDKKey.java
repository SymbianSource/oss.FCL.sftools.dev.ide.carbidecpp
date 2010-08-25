/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
* Test the BldInfViewPathHelper class.
*
*/

package com.nokia.carbide.cpp.internal.api.sdk.sbsv2;

import java.io.Serializable;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

public class SBSv2SDKKey implements Serializable {

	private static final long serialVersionUID = -5672527971643437442L;

	private String uniqueId;
	private String epocRoot;

	public SBSv2SDKKey() {
		uniqueId = "";
		epocRoot = "";
	}

	public SBSv2SDKKey(ISymbianSDK sdk) {
		uniqueId = sdk.getUniqueId();
		epocRoot = sdk.getEPOCROOT();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SBSv2SDKKey other = (SBSv2SDKKey) obj;
		if (epocRoot == null) {
			if (other.epocRoot != null)
				return false;
		} else if (!epocRoot.equals(other.epocRoot))
			return false;
		if (uniqueId == null) {
			if (other.uniqueId != null)
				return false;
		} else if (!uniqueId.equals(other.uniqueId))
			return false;
		return true;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public String getEpocRoot() {
		return epocRoot;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((epocRoot == null) ? 0 : epocRoot.hashCode());
		result = prime * result
				+ ((uniqueId == null) ? 0 : uniqueId.hashCode());
		return result;
	}

	public void setUniqueId(String id) {
		uniqueId = id;
	}

	public void setEpocRoot(String root) {
		epocRoot = root;
	}

	@Override
	public String toString() {
		return "SBSv2SDKKey [uniqueId=" + uniqueId + ", epocRoot=" + epocRoot
				+ "]";
	}

}
