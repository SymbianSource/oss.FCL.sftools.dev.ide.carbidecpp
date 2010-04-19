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

package com.nokia.cpp.internal.api.utils.core;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;


/**
 * Utilities used for portability between hosts.
 * <p>
 * NOTE: please keep this in sync with the org.eclipse.cdt.debug.edc version of this class!
 */
public class HostOS {
	/** Is the host Windows? */
	public static boolean IS_WIN32 = File.separatorChar == '\\';
	/** Is the host some Unix variant? */
	public static boolean IS_UNIX = File.separatorChar == '/';
	/** Executable file extension */
	public static final String EXE_EXT = IS_WIN32 ? ".exe" : "";
	
	/**
	 * Ensure that the executable name mentioned is canonical for the machine.
	 * This only affects Windows, currently, ensuring that an ".exe" is attached.
	 * @param executablePath
	 * @return updated path
	 */
	public static String canonicalizeExecutableName(String executable) {
		if (IS_WIN32) {
			IPath executablePath = new Path(executable);
			String ext = executablePath.getFileExtension();
			if (ext == null) {
				executable += EXE_EXT;
			}
		}
		return executable;
	}

	/**
	 * Scan the PATH variable and see if the given binary is visible on
	 * the PATH that will be used at runtime (with the default environment and overrides).
	 * @param program - program name to find on the path
	 * @param pathValue the value of the path in the system to search on
	 * @return IPath if program is on PATH, else <code>null</code>
	 */
	public static IPath findProgramOnPath(String program, String pathValue) {
		
		// be sure proper path/extension are present
		program = HostOS.canonicalizeExecutableName(program);
		
		IPath path = null;
		
		IPath[] pathEntries = PathUtils.getPathEntries(pathValue);
		for (IPath pathEntry : pathEntries) {
			IPath testPath = pathEntry.append(program);
			if (testPath.toFile().exists()) {
				path = testPath;
				break;
			}
		}
		
		return path;
	}
}
