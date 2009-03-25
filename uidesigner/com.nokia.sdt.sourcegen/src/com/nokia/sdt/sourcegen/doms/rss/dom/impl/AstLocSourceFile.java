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

package com.nokia.sdt.sourcegen.doms.rss.dom.impl;

import com.nokia.cpp.internal.api.utils.core.ChainedIterator;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.sourcegen.ISourceFormatter;
import com.nokia.sdt.sourcegen.core.ISourceFile;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIncludeDirective;

import java.util.*;

public class AstLocSourceFile extends AstRssSourceFile implements
        IAstLocSourceFile {

	/** List of IAstPreprocessorIncludeDirective nodes referencing IAstLxxSourceFile */
    private List<IAstPreprocessorIncludeDirective> lxxFiles;
    

    /**
     */
    public AstLocSourceFile(ISourceFile file) {
        super(file);
        this.lxxFiles = new ArrayList<IAstPreprocessorIncludeDirective>();
        categories.add(lxxFiles);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstRssSourceFile#refresh()
     */
    @Override
    public void refresh() {
    	if (lxxFiles != null)
    		lxxFiles.clear();
    	super.refresh();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstRssSourceFile#getCategory(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    @Override
    protected List getCategory(IAstNode node) {
    	if (node instanceof IAstPreprocessorIncludeDirective
    			&& ((IAstPreprocessorIncludeDirective) node).getFile() instanceof IAstLxxSourceFile)
    		return lxxFiles;
    	return super.getCategory(node);
    }
    
    protected Iterator allIncludes() {
    	return new ChainedIterator(super.allIncludes(),
    					lxxFiles.iterator());
    }
   
    public List getTextSegments(IAstListNode listNode) {
        List objs = super.getTextSegments(listNode);
        
        // go through and convert any lxx file references into includes
        for (ListIterator iter = objs.listIterator(); iter.hasNext();) {
            Object segment = iter.next();
            if (!(segment instanceof IAstPreprocessorIncludeDirective))
            	continue;
            IAstPreprocessorIncludeDirective incl = (IAstPreprocessorIncludeDirective) segment;
            	
            if (!(incl.getFile() instanceof IAstLxxSourceFile))
            	continue;
            
            IAstLxxSourceFile file = (IAstLxxSourceFile) incl.getFile();
            
            int langCode = file.getLanguageCode();
            iter.remove();
            iter.add("#ifdef " + getLanguageMacro(langCode)); //$NON-NLS-1$
            iter.add(ISourceFormatter.SEGMENT_NEWLINE);
            iter.add("#include \"" + file.getSourceFile().getFileName() + "\""); //$NON-NLS-1$ //$NON-NLS-2$
            iter.add(ISourceFormatter.SEGMENT_NEWLINE);
            iter.add("#endif"); //$NON-NLS-1$
            iter.add(ISourceFormatter.SEGMENT_NEWLINE);
        }
        return objs;
    }

    private String getLanguageMacro(int code) {
        char nums[] = { '0', '0' };
        Check.checkState(code < 100);
        nums[0] = Character.forDigit(code / 10, 10);
        nums[1] = Character.forDigit(code % 10, 10);
        return "LANGUAGE_" + new String(nums); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLocSourceFile#getLxxSourceFiles()
     */
    public IAstLxxSourceFile[] getLxxSourceFiles() {
    	IAstLxxSourceFile[] lxxSrcs = new IAstLxxSourceFile[lxxFiles.size()];
    	int idx = 0;
    	for (Iterator iter = lxxFiles.iterator(); iter.hasNext();) {
			IAstPreprocessorIncludeDirective incl = (IAstPreprocessorIncludeDirective) iter.next();
			lxxSrcs[idx++] = (IAstLxxSourceFile) incl.getFile();
		}
    	return lxxSrcs;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLocSourceFile#addLxxSourceFile(com.nokia.sdt.sourcegen.doms.rss.dom.IAstLxxSourceFile)
     */
    public IAstPreprocessorIncludeDirective addLxxSourceFile(IAstLxxSourceFile file) {
    	Check.checkArg(findLxxFile(file.getLanguageCode()) == null);
    	IAstPreprocessorIncludeDirective incl = new AstPreprocessorIncludeDirective(
    			file.getSourceFile().getFileName(), 
    			true, file);
    	addFileNode(incl);
    	return incl;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLocSourceFile#removeLxxSourceFile(com.nokia.sdt.sourcegen.doms.rss.dom.IAstLxxSourceFile)
     */
    public void removeLxxSourceFile(IAstLxxSourceFile file) {
    	IAstNode node = findInclude(file.getSourceFile());
    	if (node != null)
    		removeFileNode(node);
    	else
    		Check.checkArg(false);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLocSourceFile#findLxxFile(int)
     */
    public IAstLxxSourceFile findLxxFile(int langCode) {
    	for (Iterator iter = lxxFiles.iterator(); iter.hasNext();) {
    		IAstPreprocessorIncludeDirective incl = (IAstPreprocessorIncludeDirective) iter.next();
			IAstLxxSourceFile lxxFile = (IAstLxxSourceFile) incl.getFile();
			if (lxxFile.getLanguageCode() == langCode)
				return lxxFile;
		}
    	return null;
    }
}
