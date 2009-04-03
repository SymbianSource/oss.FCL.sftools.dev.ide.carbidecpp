/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGInstallFileStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.*;

import org.eclipse.core.runtime.IPath;

import java.util.*;

public class PKGInstallFile implements IPKGInstallFile {

	private final IASTPKGInstallFileStatement installFileStmt;
	private final IPKGStatementContainer container;
	private Map<EPKGLanguage, IPath> sourceFiles;
	private IPath destinationFile;
	private List<String> options;

	PKGInstallFile(IASTPKGInstallFileStatement installFileStmt, IPKGStatementContainer container) {
		this.installFileStmt = installFileStmt;
		this.container = container;
		sourceFiles = new HashMap<EPKGLanguage, IPath>();
		options = new ArrayList<String>();
	}

	public IPKGStatementContainer getContainer() {
		return container;
	}

	public Map<EPKGLanguage, IPath> getSourceFiles() {
		return sourceFiles;
	}

	public IPath getDestintationFile() {
		return destinationFile;
	}

	public void setDestinationFile(IPath destinationFile) {
		this.destinationFile = destinationFile;
	}

	public List<String> getOptions() {
		return options;
	}

	public IPKGInstallFile copy() {
		PKGInstallFile copy = new PKGInstallFile(installFileStmt, container);
		// copy destination files
		copy.setDestinationFile(destinationFile);
		// copy source files
		Map<EPKGLanguage, IPath> cloneSourceFiles = copy.getSourceFiles();
		for (EPKGLanguage language : sourceFiles.keySet()) {
			cloneSourceFiles.put(language, sourceFiles.get(language));
		}
		// copy options
		for (String option : options) {
			copy.options.add(option);
		}

		return copy;
	}

	public IASTPKGInstallFileStatement getInstallFileStatement() {
		return installFileStmt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((destinationFile == null) ? 0 : destinationFile.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
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
		final PKGInstallFile other = (PKGInstallFile) obj;
		if (destinationFile == null) {
			if (other.destinationFile != null)
				return false;
		} else if (!destinationFile.equals(other.destinationFile))
			return false;
		if (options == null) {
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
		if (sourceFiles == null) {
			if (other.sourceFiles != null)
				return false;
		} else if (!sourceFiles.equals(other.sourceFiles))
			return false;
		return true;
	}
}
