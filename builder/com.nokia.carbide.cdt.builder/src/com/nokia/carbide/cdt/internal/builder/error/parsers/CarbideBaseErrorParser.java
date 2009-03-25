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
package com.nokia.carbide.cdt.internal.builder.error.parsers;

import org.eclipse.cdt.core.ErrorParserManager;
import org.eclipse.cdt.core.IErrorParser;
import org.eclipse.cdt.core.IMarkerGenerator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.cpp.internal.api.utils.core.FileUtils;

public abstract class CarbideBaseErrorParser implements IErrorParser {

	protected int msgFirstColon;
	protected String msgDescription;
	protected int msgSeverity;
	protected int msgLineNumber;
	protected String msgFileName;
	protected IResource msgIFile;
	protected IPath externalFilePath;

	public void initialise() {
		msgSeverity = IMarkerGenerator.SEVERITY_ERROR_RESOURCE;
		msgLineNumber = -1; // initialise to -1
		msgIFile = null;
		externalFilePath = null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.core.IErrorParser#processLine(java.lang.String,
	 *      org.eclipse.cdt.core.ErrorParserManager)
	 */
	public boolean processLine(String aString,
			ErrorParserManager aErrorParserManager) {
		return false;
	}

	/**
	 * @param currLine
	 *            line to check for file level message
	 * @param matchLines
	 *            prefixes that indicate that this file has failed on a file
	 *            level
	 * @return true if one of the aLines matched with aLine
	 */
	protected boolean checkForLineBeginnings(String currLine, String[] matchLines) {

		boolean fileWideMessage = false;
		for (String testLine : matchLines) {
			if (currLine.startsWith(testLine)) {
				fileWideMessage = true;
				break;
			}
		}
		return fileWideMessage;
	}

	/**
	 * sets iFirstColon to first 'relevant' colon in the string. If the message
	 * starts with a file name with a windows path, i.e. aLine = "X:..." then
	 * sets iFirstColon to the next occurance of a colon. If there is no colon
	 * then sets to -1
	 * 
	 * @param line
	 *            line to test
	 * @return true if iFirstColon>=0, false if iFirstColon == -1
	 */
	protected boolean setFirstColon(String line) {
		
		msgFirstColon = line.indexOf(':');
		if (msgFirstColon == 1 || (msgFirstColon == 2 && line.startsWith("\""))) {
			try {
				String os = System.getProperty("os.name"); //$NON-NLS-1$
				if (os != null && os.startsWith("Win")) { //$NON-NLS-1$
					try {
						if (Character.isLetter(line.charAt(msgFirstColon-1))) {
							msgFirstColon = line.indexOf(':', msgFirstColon+1);
						}
					} catch (StringIndexOutOfBoundsException e) {
						// can't happen as iFirstColon==1 implies
						// aLine.length>=2
					}
				}
			} catch (SecurityException e) {
			}
		}
		if (msgFirstColon == -1) {
			return false;
		}
		return true;
	}

	/**
	 * @param line
	 *            message line to try and set line number and file name from
	 * @return true if file name and line number could be set, false otherwise
	 */
	protected boolean setFileNameAndLineNumber(String line) {
		// Get the first Substring, which must be in the form of
		// "fileName(lineNumber)"
		String firstSubstr = line.substring(0, msgFirstColon);
		if (firstSubstr != null) {
			int firstParenIdx = firstSubstr.indexOf('(');
			if (firstParenIdx < 0)
				return false;
			int lastParenIdx = firstSubstr.indexOf(')');
			if (lastParenIdx < 0)
				return false;
			
			String lineNumber = firstSubstr.substring(firstParenIdx + 1, lastParenIdx);
			try {
				msgLineNumber = Integer.parseInt(lineNumber);
			} catch (NumberFormatException e) {
				// Failed to get Line Number.
				if (firstParenIdx != lastParenIdx) {
					return false;
				}
			}

			// Deal with filename
			msgFileName = firstSubstr.substring(0, firstParenIdx);

			// The pattern is to general we have to guard:
			// Before making this pattern a marker we do one more check
			// The fileName that we extract __must__ look like a valid file
			// name.
			if (!Path.EMPTY.isValidPath(msgFileName)) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * @param fileExtensions
	 *            file extensions to exclude
	 * @return true if file name ends in one of the extensions, false otherwise
	 */
	protected boolean hasExcludedExtension(String[] fileExtensions) {
		if (msgFileName.length() > 0) {
			for (String extension : fileExtensions) {
				if (msgFileName.toLowerCase().endsWith(extension)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * sets iIFile variable if possible
	 * 
	 * @param errorParserManager
	 *            Errro parser manager to query
	 */
	protected void setFile(ErrorParserManager errorParserManager) {
		// if no filename, just return
		if (msgFileName.trim().length() == 0) {
			return;
		}
		
		msgIFile = errorParserManager.findFileName(msgFileName);
		if (msgIFile == null) {
			msgIFile = errorParserManager.findFilePath(msgFileName);
		}
		
		if (msgIFile == null){
			// still null. this might be a file on another drive spec
			IPath fullPath = new Path(msgFileName);
			IProject project = errorParserManager.getProject();
			if (project != null){
				fullPath = fullPath.setDevice(project.getLocation().getDevice());
				try {
					msgIFile = FileUtils.convertFileToIFile(fullPath.toFile());
				} catch (Throwable t) {
				}
				if (msgIFile != null && !msgIFile.exists()){
					msgIFile = null;
				}
			}
		}
		
		// Display the fileName.
		if (msgIFile == null) {
			msgDescription = msgDescription + "[" + msgFileName + "]"; //$NON-NLS-1$ //$NON-NLS-2$
			externalFilePath = new Path(msgFileName);
			if (!externalFilePath.isAbsolute()) {
				externalFilePath = errorParserManager.getWorkingDirectory().append(externalFilePath);
			}
			msgIFile = errorParserManager.getProject();
		}
	}

	/**
	 * sets iDescription to be the description part of the input line
	 * 
	 * @param line
	 *            input line
	 */
	public abstract void setDescription(String line);

}
