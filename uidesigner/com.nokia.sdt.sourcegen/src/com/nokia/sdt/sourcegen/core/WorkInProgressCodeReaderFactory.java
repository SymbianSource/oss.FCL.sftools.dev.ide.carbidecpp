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

package com.nokia.sdt.sourcegen.core;

import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.sdt.sourcegen.SourceGenPlugin;

import org.eclipse.cdt.core.dom.ICodeReaderFactory;
import org.eclipse.cdt.core.parser.*;
import org.eclipse.core.resources.IFile;

import java.io.*;

/**
 * CodeReaderFactory that always works from a cache, if possible.
 * This is used for sourcegen while it is modifying file contents.
 * For our purposes, include files don't matter, so always treat
 * these as unknown.
 * 
 *
 */
public class WorkInProgressCodeReaderFactory implements ICodeReaderFactory {

	WorkInProgressCodeReaderCache codeReaderCache = new WorkInProgressCodeReaderCache();
	
	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.dom.ICodeReaderFactory#getUniqueIdentifier()
	 */
	public int getUniqueIdentifier() {
		return getClass().hashCode();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.dom.ICodeReaderFactory#createCodeReaderForTranslationUnit(java.lang.String)
	 */
	public CodeReader createCodeReaderForTranslationUnit(String path) {
		return readFile(path);
	}

	/**
	 * Read contents of file with the given absolute filesystem path.
	 * @param path
	 * @return
	 */
	private CodeReader readFile(String path) {
		CodeReader reader = codeReaderCache.get(path);
		if (reader != null)
			return reader;
		
		File file = new File(path);
		if (!file.exists())
			return null;
		
		try {
			IFile ifile = FileUtils.convertFileToIFile(new File(path));
			InputStream is;
			if (ifile != null) {
				if (!ifile.exists())
					return null;
				
				is = ifile.getContents();
			} else {
				is = new FileInputStream(file);
			}
			char[] contents = FileUtils.readInputStreamContents(is, null);
			is.close();
			reader = new CodeReader(path, contents);
			codeReaderCache.put(path, reader);
			return reader;
		} catch (Exception e) {
			SourceGenPlugin.getDefault().log(e);
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.dom.ICodeReaderFactory#createCodeReaderForInclusion(java.lang.String)
	 */
	public CodeReader createCodeReaderForInclusion(IScanner scanner, String path) {
		//return readFile(path);
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.dom.ICodeReaderFactory#getCodeReaderCache()
	 */
	public ICodeReaderCache getCodeReaderCache() {
		return codeReaderCache;
	}

	/**
	 * Replace contents of the cached file with the text.
	 * @param path
	 * @param text
	 */
	public void replaceFileContents(String path, char[] text) {
		codeReaderCache.put(path, new CodeReader(path, text));
	}
	
	public CodeReader createCodeReaderForInclusion(String path) {
		return null;
	}
	
}