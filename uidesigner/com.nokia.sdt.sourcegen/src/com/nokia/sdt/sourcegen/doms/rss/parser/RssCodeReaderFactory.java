/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen.doms.rss.parser;

import org.eclipse.cdt.core.dom.ICodeReaderFactory;
import org.eclipse.cdt.core.parser.*;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.nokia.cpp.internal.api.utils.core.FileUtils;

import java.io.File;
import java.io.IOException;

public class RssCodeReaderFactory implements ICodeReaderFactory {

        public int getUniqueIdentifier() {
            return 123;
        }

        private CodeReader getCodeReader(String path) throws IOException {
        	IFile file = FileUtils.convertFileToIFile(new File(path));
        	String charSet = null;
        	if (file != null) {
        		try {
					charSet = file.getCharset();
				} catch (CoreException e) {
				}
        	}
        	if (charSet != null) {
        		return new CodeReader(path, charSet);
        	}
        	return new CodeReader(path);
        }
        public CodeReader createCodeReaderForTranslationUnit(String path) {
            try {
                return getCodeReader(path);
            } catch (IOException e) {
                return null;
            }
        }

        public CodeReader createCodeReaderForInclusion(IScanner scanner, String path) {
            try {
                return getCodeReader(path);
            } catch (IOException e) {
                return null;
            }
        }

        public ICodeReaderCache getCodeReaderCache() {
            return null;
        }

		public CodeReader createCodeReaderForInclusion(String path) {
        	try {
                return getCodeReader(path);
            } catch (IOException e) {
                return null;
            }
		}
}
