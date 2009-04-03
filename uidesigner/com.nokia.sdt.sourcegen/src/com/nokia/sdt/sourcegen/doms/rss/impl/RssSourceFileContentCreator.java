/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen.doms.rss.impl;

import com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager.INewFileContentCreator;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstPreprocessorTextNode;
import com.nokia.sdt.symbian.SymbianLanguage;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 *
 */
public class RssSourceFileContentCreator implements INewFileContentCreator {


	private static final Pattern LXX_FILE_PATTERN = Pattern.compile(".*\\.l(\\d\\d)");
	private static final Pattern RLS_FILE_PATTERN = Pattern.compile(".*_(\\d\\d)\\.rls");

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.IRssProjectFileManager.INewFileContentCreator#createNewContent(com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile)
	 */
	public IAstNode[] createNewContent(IAstRssSourceFile rssFile) {
		int languageID = detectLxxOrRlsFile(rssFile);
		if (languageID >= 0)
			return createLocalizedStringFileContent(rssFile, languageID);
		
		return IAstNode.NO_CHILDREN;
	}

	/**
	 * @param rssFile
	 * @return
	 */
	private IAstNode[] createLocalizedStringFileContent(IAstRssSourceFile rssFile, int languageID) {
		String commentFormat = "// localized strings for language: {0}\n";
		String comment = MessageFormat.format(commentFormat, new Object[] { 
			SymbianLanguage.getFromLanguageID(languageID)
		});
		IAstNode node = new AstPreprocessorTextNode(comment);
		return new IAstNode[] { node };
	}

	/**
	 * @param rssFile
	 * @return
	 */
	private int detectLxxOrRlsFile(IAstRssSourceFile rssFile) {
		String fileName = rssFile.getSourceFile().getFileName();
		Matcher matcher = LXX_FILE_PATTERN.matcher(fileName);
		if (matcher.matches()) {
			int languageID = Integer.parseInt(matcher.group(1));
			return languageID;
		}
		
		matcher = RLS_FILE_PATTERN.matcher(fileName);
		if (matcher.matches()) {
			int languageID = Integer.parseInt(matcher.group(1));
			return languageID;
		}
		
		return -1;
	}

	
}
