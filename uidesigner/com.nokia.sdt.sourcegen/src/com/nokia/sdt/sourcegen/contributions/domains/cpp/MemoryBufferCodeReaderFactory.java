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

package com.nokia.sdt.sourcegen.contributions.domains.cpp;

import com.nokia.sdt.sourcegen.core.SourceGenContext;

import org.eclipse.cdt.core.dom.CDOM;
import org.eclipse.cdt.core.dom.ICodeReaderFactory;
import org.eclipse.cdt.core.parser.*;
import org.eclipse.core.runtime.CoreException;

import java.io.File;

public final class MemoryBufferCodeReaderFactory implements ICodeReaderFactory {
    private SourceGenContext context;

    /**
     * 
     */
    public MemoryBufferCodeReaderFactory(SourceGenContext context) {
        this.context = context;
    }
    
    public int getUniqueIdentifier() {
        return CDOM.PARSE_WORKING_COPY_WHENEVER_POSSIBLE + 1000;
    }

    public CodeReader createCodeReaderForTranslationUnit(String path) {
        File file = new File(path);
        if (!file.exists())
            return null;
        
        ISourceManipulator sfi = context.getSourceManipulator(path);
        if (sfi == null)
            return null;
        try {
            if (!sfi.isLoaded())
                sfi.load();
        } catch (CoreException e) {
            return null;
        }
        if (sfi.getCurrentText() != null)
            return new CodeReader(path, sfi.getCurrentText()); 
        else
            return new CodeReader(path, "".toCharArray());  //$NON-NLS-1$

    }

    public CodeReader createCodeReaderForInclusion(IScanner scanner, String path) {
        return createCodeReaderForTranslationUnit(path);
    }

    public ICodeReaderCache getCodeReaderCache() {
        return null;
    }

	public CodeReader createCodeReaderForInclusion(String path) {
		return createCodeReaderForTranslationUnit(path);
	}
}