/*
* Copyright (c) 2008-2009 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.internal.cpp.epoc.engine.model.pkg;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGPackageHeaderStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.*;

import java.util.*;

public class PKGHeader implements IPKGHeader {

	private final IASTPKGPackageHeaderStatement astHeader;
	private final IPKGStatementContainer container;
	private String uid;
	private List<String> version;
	private List<String> options;

	PKGHeader(IASTPKGPackageHeaderStatement astHeader, IPKGStatementContainer container) {
		this.astHeader = astHeader;
		this.container = container;
		version = new ArrayList<String>();
		options = new ArrayList<String>();
	}

	public IASTPKGPackageHeaderStatement getASTHeader() {
		return astHeader;
	}

	public IPKGStatementContainer getContainer() {
		return container;
	}

	public List<String> getOptions() {
		return options;
	}

	public PKGHeader copy() {
		PKGHeader copy = new PKGHeader(astHeader, container);

		copy.setUid(uid);
		copy.getVersion().clear();
		copy.getVersion().addAll(version);

		// copy options
		for (String option : options) {
			copy.options.add(option);
		}

		return copy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((uid == null) ? 0 : uid.hashCode());
		result = prime * result
				+ ((version == null) ? 0 : version.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
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
		final PKGHeader other = (PKGHeader) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		if (options == null) {
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
		return true;
	}

	public List<String> getVersion() {
		return version;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
