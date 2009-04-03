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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.cdt.core.ErrorParserManager;
import org.eclipse.cdt.core.IErrorParser;
import org.eclipse.cdt.core.IMarkerGenerator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

// CodeWarrior parseable message format
// Limitation: -wraplines must not be used on the compiler/linker, otherwise only the line carrying the token
// would be kept
//"executable filename"|"Compiler/Assembler/Importer/Usage"|"Note/Warning/Error/Alert/Status"
//optional line		("full path of source file"|"line number"|"token offset"|"token length"|"selection offset"|"selection length")
//optional line		="source line dump"
//one or more line	>"message text"
//
// Since we are going to have multiple lines of >"message text",
// we push all lines of messages into scratch buffer and start 
// the parsing from the scratch buffer after seeing the the next 
// message (marked with >) or the first line that is not a WINSCW 
// message.
//
// We would append \n to each lines in the scratch buffer so we
// can recognize a modified token with \n, there is always 
// possibility that some token e.g. '(' and ')' show up in the 
// message line

public abstract class CWParseableErrorParser implements IErrorParser {
	
	protected static final Pattern startingLinePattern = Pattern.compile("(.+)\\|(Compiler|Assembler|Importer|Usage)\\|(Note|Warning|Error|Alert|Status)");
	protected static final Pattern srcLocPattern = Pattern.compile("\\((.+)\\|(\\d+)\\|(\\d+)\\|(\\d+)\\|(\\d+)\\|(\\d+)\\)");
	protected static final Pattern srcSnapshotPattern = Pattern.compile("=([^=>]*)");
	protected static final Pattern messagePattern = Pattern .compile("(?:>[^=>]*)+");
	
	protected String executableName;
	
	private class BadMessageException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7354275152181068450L;
	}
	
	public CWParseableErrorParser() {
	}
	
	private void sanityCheckForMessageInProgress(ErrorParserManager eoParser) throws BadMessageException{
		// haven't started parsing, great
		if (eoParser.getScratchBuffer().length() == 0) {
			return;
		}
		// started parsing and very last line was a message
		String previousLine = eoParser.getPreviousLine();
		if (startingLinePattern.matcher(previousLine).matches() ||
				srcLocPattern.matcher(previousLine).matches() ||
				srcSnapshotPattern.matcher(previousLine).matches() ||
				messagePattern.matcher(previousLine).matches()) {
			return;
		}
		throw new BadMessageException();
	}
	
	private void writeInProgressMessageToScratchBuffer(String line, ErrorParserManager eoParser) throws BadMessageException {
		sanityCheckForMessageInProgress(eoParser);
		// always tag a new line as separator for processLine()
		eoParser.appendToScratchBuffer(line + "\n");
	}
	
	public boolean processLine(String line, ErrorParserManager eoParser) {		
		
		try {
			// Use scratch buffer of this error parser to track the state of this
			// parser e.g. processing 1. starting line, 2. optional () line, 3. optional =, 4. multiple > line
			
			// look for |"Note/Warning/Error/Alert/Status" to start a WINSCW message
			
			Matcher startingLineMatcher = startingLinePattern.matcher(line);
			
			if (startingLineMatcher.matches())
			{
				if (eoParser.getScratchBuffer().length() != 0)
				{
					// we just finished another error message and see the next one on
					// this line, parse and display that message in buffer
					
					parseMarkerFromMessageInScratchBuffer(eoParser);
					eoParser.clearScratchBuffer();
				}
				
				if (startingLineMatcher.matches()) {
					executableName = startingLineMatcher.group(1);
				}
				
				writeInProgressMessageToScratchBuffer(line, eoParser);
				return false;
			}
			else if(line.startsWith("#") && eoParser.getPreviousLine().startsWith("###")) {
				// catch usage errors which are reported like this:
				// ### mwldsym2.exe Usage Error:
				// #   Unknown option 'stackcommit0x00007d00'

				// strip the # and whitespace from the beginning
				String msg = line.substring(1, line.length()).trim();
				eoParser.generateMarker(null, 0, msg, IMarkerGenerator.SEVERITY_ERROR_BUILD, null);
				eoParser.clearScratchBuffer();
				return true;
			}
			else
			{
				// Haven't started looking at a possible WINSCW message
				// and this line is not the first line of WINSCW message
				// so it is not a message line
				if (eoParser.getScratchBuffer().length() == 0)
					return false;
				
				// See ("full path of source file"|"line number"|"token offset"|"token length"|"selection offset"|"selection length"),
				if (srcLocPattern.matcher(line).matches())
				{
					writeInProgressMessageToScratchBuffer(line, eoParser);
					return false;
				}
				
				// See ="source line dump",
				if (srcSnapshotPattern.matcher(line).matches())
				{
					writeInProgressMessageToScratchBuffer(line, eoParser);
					return false;
				}

				// See >"message text"
				if (messagePattern.matcher(line).matches())
				{
					writeInProgressMessageToScratchBuffer(line, eoParser);
					return false;
				}

				// can't see a token, but we already started a WINSCW message
				if (eoParser.getScratchBuffer().length() != 0)
				{	// We already saw parseable message, and the last line is the last of previous message, print it
					parseMarkerFromMessageInScratchBuffer(eoParser);
					eoParser.clearScratchBuffer();
				}
				else
				{
					// maybe we had a wrapped line from -wraplines passed to the tools 
					// or those garbage lines from the tools, throw them away
				}
			}
	
		} catch (StringIndexOutOfBoundsException e) {
		} catch (NumberFormatException e) {
		} catch (BadMessageException e) {
			// clear buffer if we see something wrong
			eoParser.clearScratchBuffer();
		}
				
		return false;
	}
	
	// takes a newline separated message from WINSCW in ErrorParserManager scratch buffer
	private boolean parseMarkerFromMessageInScratchBuffer(ErrorParserManager eoParser) {
		int severity = IMarkerGenerator.SEVERITY_INFO;
		IFile file = null;
		IPath externalFilePath = null;
		int num = 0;
		String desc = "";

		int fragmentIndex = 0;
		String savedMessage = eoParser.getScratchBuffer();
		String allFragments[] = savedMessage.split("\n");
		
		Matcher startingLineMatcher = startingLinePattern.matcher(allFragments[fragmentIndex]);
		fragmentIndex++;
		if (startingLineMatcher.matches()) {
			String severityString = startingLineMatcher.group(3);
			
			//look for |"Note/Warning/Error/Alert/Status"
			if (severityString.equals("Error"))
			{
				severity = IMarkerGenerator.SEVERITY_ERROR_BUILD;
			}
			else {
				if (severityString.equals("Warning"))
				{
					severity = IMarkerGenerator.SEVERITY_WARNING;
				}
				else
				{
					if (severityString.equals("Note") ||
							severityString.equals("Alert") ||
							severityString.equals("Status"))
					{
						severity = IMarkerGenerator.SEVERITY_INFO;
					}
					else
					{
						// doesn't seems to be a parseable message, clean up and give up
						return false;
					}
				}
			}
		}
		
		if (allFragments[fragmentIndex].startsWith("(")) {
			String sourceLocation = allFragments[fragmentIndex];
			fragmentIndex++;
			if (sourceLocation != null && sourceLocation.startsWith("(")) {
				// look for ("full path of source file"|"line number"|"token offset"|"token length"|"selection offset"|"selection length")
				Matcher sourceLocationMatcher = srcLocPattern.matcher(sourceLocation);
				if (sourceLocationMatcher.matches()) {
					String fileString = sourceLocationMatcher.group(1);
					file = eoParser.findFileName(fileString);
					if (null == file){
						file = eoParser.findFilePath(fileString);
						if (null == file) {
							// one last try before bailing out we may be in a wrong
							// directory.  This will happen, for example in the Makefile:
							// all: foo.c
							//    cd src3; gcc -c bar/foo.c
							// the user do a cd(1).
							IPath path = new Path(fileString);
							if (path.segmentCount() > 1) {
								String name = path.lastSegment();
								file = eoParser.findFileName(fileString);
								if (file != null) {
									if (eoParser.isConflictingName(fileString)) {
										desc = "[Conflicting names: " + name + " ] " + desc; //$NON-NLS-1$ //$NON-NLS-2$
										file = null;							
									}
								}
							}
						}
					}

					if (file == null) {
						externalFilePath = new Path(fileString);
					}
				
					String numString = sourceLocationMatcher.group(2);
					num = Integer.parseInt(numString);
				}
			}

		}
		
		// skip all the source snapshot lines, we don't need it
		while (fragmentIndex < allFragments.length && allFragments[fragmentIndex].startsWith("=")) {
			fragmentIndex++;
		}

		// look for >"message text" and join all of them with a space in between
		while (fragmentIndex < allFragments.length && allFragments[fragmentIndex].startsWith(">")) {
			if (desc.length() > 0) {
				desc += " ";
			}
			desc += allFragments[fragmentIndex].substring(1);
			fragmentIndex++;
		}

		eoParser.generateExternalMarker(file != null ? file : eoParser.getProject(), num, desc, severity, null, externalFilePath);
		return true;
	}
}
