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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGEmbeddedSisStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.*;

import org.eclipse.core.runtime.IPath;

import java.util.*;

public class PKGEmbeddedSISFile implements IPKGEmbeddedSISFile {

	private final IASTPKGEmbeddedSisStatement embeddedSisFileStmt;
	private final IPKGStatementContainer container;
	private Map<EPKGLanguage, IPath> sourceFiles;
	private String uid;

	PKGEmbeddedSISFile(IASTPKGEmbeddedSisStatement embeddedSisFileStmt, IPKGStatementContainer container) {
		this.embeddedSisFileStmt = embeddedSisFileStmt;
		this.container = container;
		sourceFiles = new HashMap<EPKGLanguage, IPath>();
	}

	public IPKGStatementContainer getContainer() {
		return container;
	}

	public Map<EPKGLanguage, IPath> getSourceFiles() {
		return sourceFiles;
	}

	public IPKGEmbeddedSISFile copy() {
		PKGEmbeddedSISFile copy = new PKGEmbeddedSISFile(embeddedSisFileStmt, container);
		// copy source files
		Map<EPKGLanguage, IPath> cloneSourceFiles = copy.getSourceFiles();
		for (EPKGLanguage language : sourceFiles.keySet()) {
			cloneSourceFiles.put(language, sourceFiles.get(language));
		}
		copy.setUid(uid);

		return copy;
	}

	public IASTPKGEmbeddedSisStatement getEmbeddedSisFileStatement() {
		return embeddedSisFileStmt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((uid == null) ? 0 : uid.hashCode());
		result = prime * result
				+ ((sourceFiles == null) ? 0 : sourceFiles.hashCode());
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
		final PKGEmbeddedSISFile other = (PKGEmbeddedSISFile) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		if (sourceFiles == null) {
			if (other.sourceFiles != null)
				return false;
		} else if (!sourceFiles.equals(other.sourceFiles))
			return false;
		return true;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
