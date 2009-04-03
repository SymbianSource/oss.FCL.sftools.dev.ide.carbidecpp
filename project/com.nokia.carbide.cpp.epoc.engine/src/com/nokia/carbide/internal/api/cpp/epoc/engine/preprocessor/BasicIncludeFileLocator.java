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
* This implementation searches the directory of the current source file
* and a list of provided paths using these rules:
* <p>
* <li>Every file is checked in the directory of the current #include, if known.
* <li>A user include "..." is searched on user paths then system paths.
* <li>A system include &lt;...&gt; is searched on system paths only.
*
*/
package com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor;

import java.io.File;
import java.io.IOException;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator;
public class BasicIncludeFileLocator implements IIncludeFileLocator {

	private static final File[] NO_DIRS = new File[0];
	
	private File[] userPaths;
	private File[] systemPaths;

	public BasicIncludeFileLocator(File[] userPaths, File[] systemPaths) {
		setPaths(userPaths, systemPaths);
	}

	public void setPaths(File[] userPaths, File[] systemPaths) {
		this.userPaths = userPaths != null ? userPaths : NO_DIRS;
		this.systemPaths = systemPaths != null ? systemPaths : NO_DIRS;
	}
	
	public File findIncludeFile(String file, boolean isUser, File currentDir) {
		// see if the file exists as an absolute file
		File theFile = new File(file);
		if (theFile.exists() && theFile.isFile() && theFile.isAbsolute())
			return canonical(theFile);

		// if a user include and the current directory is known try to get the file relative to this dir
		if (isUser && currentDir != null) {
			theFile = new File(currentDir, file);
			if (theFile.exists() && theFile.isFile())
				return canonical(theFile);
		}

		if (isUser) {
			// search user directories first
			for (File dir : userPaths) {
				theFile = new File(dir, file);
				if (theFile.exists() && theFile.isFile())
					return canonical(theFile);
			}
		}
		
		// always search system directories
		for (File dir : systemPaths) {
			theFile = new File(dir, file);
			if (theFile.exists() && theFile.isFile()) {
				return canonical(theFile);
			}
		}
		
		// last case - a system include that is located in the same directory as the file including it
		if (!isUser && currentDir != null) {
			theFile = new File(currentDir, file);
			if (theFile.exists() && theFile.isFile())
				return canonical(theFile);
		}

		return null;
	}

	private File canonical(File theFile) {
		try {
			return theFile.getCanonicalFile();
		} catch (IOException e) {
			return theFile;
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator#getSystemPaths()
	 */
	public File[] getSystemPaths() {
		return systemPaths;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator#getUserPaths()
	 */
	public File[] getUserPaths() {
		return userPaths;
	}
}
