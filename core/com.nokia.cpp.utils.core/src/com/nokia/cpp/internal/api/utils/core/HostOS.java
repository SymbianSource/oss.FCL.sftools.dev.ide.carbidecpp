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
 */
public class HostOS {
	/** Is the host Windows? */
	public static boolean IS_WIN32 = File.separatorChar == '\\';
	/** Is the host some Unix variant? */
	public static boolean IS_UNIX = File.separatorChar == '/';
	
	/** The name of the PATH variable in the environment.  Capitalized differently per OS. */
	public static String PATH_VARIABLE_NAME = IS_WIN32 ? "Path" : "PATH";
	
	/**
	 * Convert a variable constructed blindly for a Win32 environment into
	 * Unix-like syntax.  This is typically used for PATH or lists
	 * of paths where ';' is the entry separator and '\' is the 
	 * path component separator.
	 * <p>
	 * NOTE: we assume that the entries in the
	 * path list are already legal Unix paths, but just with the
	 * wrong slash.
	 * @param env
	 * @return converted string
	 */
	public static String convertPathListToUnix(String env) {
		if (env == null) return null;
		env = env.replaceAll(";", ":");  // entry separators
		env = env.replaceAll("\\\\", "/");  // path separators
		return env;
	}

	/**
	 * Convert a path constructed blindly for a Win32 environment into
	 * Unix-like syntax.  <p>
	 * NOTE: we assume that the path is already a legal Unix path, 
	 * but just with the wrong slash.
	 * @param file
	 * @return converted string
	 */
	public static String convertPathToUnix(String file) {
		if (file == null) return null;
		// handle Windows slashes and canonicalize
		file = file.replaceAll("\\\\", "/");
		return file;
	}
	

	/**
	 * Convert a path which may be in Windows or Unix format to Windows format.
	 * NOTE: we assume that the path is already a legal path, 
	 * but just with the wrong slash.
	 * @param file
	 * @return converted string
	 */
	public static String convertPathToWindows(String file) {
		if (file == null) return null;
		file = file.replaceAll("/", "\\\\");
		return file;
	}
	
	/**
	 * Convert a path which may be in Windows or Unix format to Windows format.
	 * NOTE: we assume that the path is already a legal path, 
	 * but just with the wrong slash.
	 * @param file
	 * @return converted string
	 */
	public static String convertPathToWindows(IPath path) {
		return convertPathToWindows(path.toPortableString());
	}

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
				executable += ".exe";
			}
		}
		return executable;
	}

	/**
	 * Get the PATH entries from the given path environment value or the
	 * system environment.
	 * @param pathValue the expected PATH/Path value, or <code>null</code> for the system value
	 * @return array of IPath, never <code>null</code>
	 */
	public static IPath[] getPathEntries(String pathValue) {
		String pathVar = pathValue != null ? pathValue : System.getenv(PATH_VARIABLE_NAME);
		
		if (pathVar == null)
			pathVar = "";
		
		String pathSeparator = System.getProperty("path.separator");
		String[] pathEntries = pathVar.split(pathSeparator);
		IPath[] paths = new IPath[pathEntries.length];
		for (int i = 0; i < pathEntries.length; i++) {
			paths[i] = new Path(pathEntries[i]);
		}
		return paths;
	}
	
	/**
	 * Scan the PATH variable and see if the given binary is visible on
	 * the PATH that will be used at runtime (with the default environment and overrides).
	 * @param pathValue the expected Path 
	 * @param program
	 * @return IPath if program is on PATH, else <code>null</code>
	 */
	public static IPath findProgramOnPath(String program, String pathValue) {
		
		// be sure proper path/extension are present
		program = canonicalizeExecutableName(program);
		
		IPath path = null;
		
		IPath[] pathEntries = getPathEntries(pathValue);
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
