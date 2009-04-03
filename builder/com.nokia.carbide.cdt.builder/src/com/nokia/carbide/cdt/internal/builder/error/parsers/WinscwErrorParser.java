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
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class WinscwErrorParser implements IErrorParser {
	
	public WinscwErrorParser() {
	}
	
	public boolean processLine(String line, ErrorParserManager eoParser) {
		// CodeWarrior parseable message format
		// Limitation: -wraplines must not be used on the compiler/linker, otherwise only the line carrying the token
		// would be kept
		//"executable filename"|"Compiler/Assembler/Importer"|"Note/Warning/Error/Alert/Status"
		//optional line		("full path of source file"|"line number"|"token offset"|"token length"|"selection offset"|"selection length")
		//optional line		="source line dump"
		//one or more line	>"message text"
		//
		// Since we are going to have multiple lines of >"message text",
		// we are going to push all lines of parseable messages into 
		// scratch buffer and start the parsing from the scratch buffer
		// after seeing the the next message or the first line that 
		// is not parseable message.
		//
		// We would append \n to each lines in the scratch buffer so we
		// can recognize a modified token with \n, there is always 
		// possibilty that some token e.g. '(' and ')' show up in the 
		// message line
		
		try {
			// See two "|", this line may be the first line of parseable message
			// look for |"Note/Warning/Error/Alert/Status" to start a parserable message
			if (line.indexOf("|") != line.lastIndexOf("|") &&
				(line.indexOf("|Note") != -1 ||
				line.indexOf("|Warning") != -1 ||
				line.indexOf("|Error") != -1 ||
				line.indexOf("|Alert") != -1 ||
				line.indexOf("|Status") != -1))
			{
				// we may just finished another error message, 
				// parse and display that message in buffer
				if (eoParser.getScratchBuffer().length() != 0)
				{
					if (eoParser.getScratchBuffer().indexOf("\n>") != -1)
					{
						processLineFromBuffer(eoParser);
						eoParser.clearScratchBuffer();
					}
				}
				eoParser.appendToScratchBuffer(line + "\n");
				return false;
			}
			else if(line.startsWith("#") && eoParser.getPreviousLine().startsWith("###")) {
				// catch usage errors which are reported like this:
				// ### mwldsym2.exe Usage Error:
				// #   Unknown option 'stackcommit0x00007d00'

				// strip the # and whitespace from the beginning
				String msg = line.substring(1, line.length()).trim();
				eoParser.generateMarker(null, 0, msg, IMarkerGenerator.SEVERITY_ERROR_BUILD, null);
			}
			else
			{
				// ignore make errors as the useful info will already be logged as an error
				// note that we do consume the error, we just don't report it
				if (line.startsWith("make: *** [") && -1 != line.indexOf("Error ")) {
					return true;
				}

				// Haven't started looking at a possible parseable message
				// and this line is not the first line of parseable message
				// so it is not a message line
				if (eoParser.getScratchBuffer().length() == 0)
					return false;
				
				// See ("full path of source file"|"line number"|"token offset"|"token length"|"selection offset"|"selection length"),
				if (line.startsWith("(") && line.endsWith(")"))
				{
					eoParser.appendToScratchBuffer(line + "\n");
					return false;
				}
				
				// See ="source line dump",
				if (line.startsWith("="))
				{
					eoParser.appendToScratchBuffer(line + "\n");
					return false;
				}

				// See >"message text"
				if (line.startsWith(">"))
				{
					eoParser.appendToScratchBuffer(line + "\n");
					return false;
				}

				// can't see a token, but we already started a parseable message
				if (eoParser.getPreviousLine().startsWith(">"))
				{	// We already saw parseable message, and the last line is the last of previous message, print it
					processLineFromBuffer(eoParser);
					eoParser.clearScratchBuffer();
				}
				else
				{
					// maybe we had a wrapped line from -wraplines passed to the tools 
					// or those garbbage lines from the tools, throw them away
				}
			}
	
		} catch (StringIndexOutOfBoundsException e) {
		} catch (NumberFormatException e) {
		}
				
		return false;
	}

	private boolean processLineFromBuffer(ErrorParserManager eoParser) {

		try {

			String savedMessage = eoParser.getScratchBuffer();
			
			// Sanity check for parseable message: 1. tokens marking remaining 
			// three kinds of parseable message exist. 2. they are in correct order
			
			if (savedMessage.indexOf("\n>") == -1)
			{
				return false;
			}
			
			// if there is source file related info
			if (savedMessage.indexOf("\n(") != -1)
			{
				if (savedMessage.indexOf(")\n") == -1 ||
						savedMessage.indexOf("\n=") == -1 ||
						savedMessage.indexOf("\n>") < savedMessage.indexOf("\n=") ||
						savedMessage.indexOf("\n=") < savedMessage.indexOf(")\n") ||
						savedMessage.indexOf(")\n") < savedMessage.indexOf("\n("))
				{
					return false;
				}
			}

			int severityIndex;
			int severity;
				
			//look for |"Note/Warning/Error/Alert/Status"
			severityIndex = savedMessage.indexOf("|", savedMessage.indexOf("|") + 1) + 1;
				
			if (savedMessage.startsWith("Error", severityIndex))
			{
				severity = IMarkerGenerator.SEVERITY_ERROR_BUILD;
			}
			else {
				if (savedMessage.startsWith("Warning", severityIndex))
				{
					severity = IMarkerGenerator.SEVERITY_WARNING;
				}
				else
				{
					if (savedMessage.startsWith("Note", severityIndex) ||
							savedMessage.startsWith("Alert", severityIndex) ||
							savedMessage.startsWith("Status", severityIndex))
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
			
			// look for ("full path of source file"|"line number"|"token offset"|"token length"|"selection offset"|"selection length")
			IFile file = null;
			IPath externalFilePath = null;
			int num = 0;
			String desc = "";
			if (savedMessage.indexOf("\n(") != -1)
			{
				int fileStartIndex = savedMessage.indexOf("\n(") + 2;
				int fileEndIndex = savedMessage.indexOf("|", fileStartIndex);
				String fileString = savedMessage.substring(fileStartIndex, fileEndIndex);
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
			
				int numStartIndex = savedMessage.indexOf("|", fileEndIndex) + 1;
				int numEndIndex = savedMessage.indexOf("|", numStartIndex);
				String numString = savedMessage.substring(numStartIndex, numEndIndex);
				num = Integer.parseInt(numString);
			}
			
			// look for >"message text"
			int descStartIndex = savedMessage.indexOf("\n>") + 2;
			int descEndIndex = savedMessage.indexOf("\n", descStartIndex);

			// at least one line of desc
			desc += savedMessage.substring(descStartIndex,descEndIndex);

			// join more >"message text" into the same line, single line
			// description is an Eclipse limitation
			while ((descStartIndex = savedMessage.indexOf("\n>", descEndIndex)) != -1)
			{
				descStartIndex += 2;
				descEndIndex = savedMessage.indexOf("\n", descStartIndex);
				desc += " " + savedMessage.substring(descStartIndex,descEndIndex);
			}
			eoParser.generateExternalMarker(file != null ? file : eoParser.getProject(), num, desc, severity, null, externalFilePath);
			return true;
			
		} catch (StringIndexOutOfBoundsException e) {
		} catch (NumberFormatException e) {
		}

		return false;
		
	}	
}
