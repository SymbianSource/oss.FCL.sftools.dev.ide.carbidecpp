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

import com.nokia.sdt.sourcegen.ISourceFormatter;
import com.nokia.sdt.sourcegen.core.ISourceFile;
import com.nokia.sdt.sourcegen.core.ISourceRange;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Basic source file node.  This merely holds an ISourceFile
 * and provides basic support for updating file text.
 * 
 * @see AstRssSourceFile
 * @see AstLxxSourceFile
 * @see AstLocSourceFile
 * 
 *
 */
public abstract class AstSourceFile extends AstNode implements IAstSourceFile {

    /** List of nodes in file (shallow) (IAstTopLevelNode and IAstPreprocessorNode) */
	protected IAstListNode fileNodes;
    
    private ISourceFile file;
    private ITranslationUnit unit;
    /** Source formatter */
    private ISourceFormatter formatter;
    private boolean readOnly;
    
    /**
     * Create a source file which can be rewritten
     */
    public AstSourceFile(ISourceFile file, Class baseType) {
        super();
        setSourceFile(file);
        setReadOnly(false);
        init(baseType);
    }
    
    protected void init(Class baseType) {
        fileNodes = new AstListNode(baseType, 
        		new Object[] { ISourceFormatter.SEGMENT_NEWLINE },
        		new Object[] { ISourceFormatter.SEGMENT_NEWLINE });
        fileNodes.setParent(this);
        fileNodes.setDirty(false);
        dirty = false;
    }
    
    /**
     * Create a source file which is read-only (i.e. system header).
     * This does not say anything about the attributes of
     * the file on disk, it says we do not try to rewrite
     * the file.
     */
    public AstSourceFile(ISourceFile file, boolean readOnly) {
        super();
        setSourceFile(file);
        setReadOnly(readOnly);
        init(IAstNode.class);
    }

    /**
     * Create an empty source file.
     */
    public AstSourceFile() {
        super();
        init(IAstNode.class);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#toString()
     */
    public String toString() {
        return "AstSourceFile {" + file.getFileName() + "}\n" + dump(); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    public IAstNode[] getChildren() {
	    return new IAstNode[] { fileNodes };
	}
    
    /*
     * (non-Javadoc)
     *  
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
     public IAstNode[] getReferencedNodes() {
         return getChildren();
     }

     /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
    	return new Object[] { fileNodes };
    }
    
	/* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile#setTranslationUnit(com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit)
     */
    public void setTranslationUnit(ITranslationUnit unit) {
    	if (this.unit != unit)
    		dirty = true;
    	this.unit = unit;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTranslationUnit()
     */
    public ITranslationUnit getTranslationUnit() {
        return unit;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile#getSourceFile()
     */
    public ISourceFile getSourceFile() {
        return file;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile#setSourceFile(com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFile)
     */
    public void setSourceFile(ISourceFile file) {
        Check.checkArg(file);
        if (this.file == null || this.file != file)
        	dirty = true;
        this.file = file;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile#isReadOnly()
     */
    public boolean isReadOnly() {
        return readOnly;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile#setReadOnly(boolean)
     */
    public void setReadOnly(boolean readOnly) {
    	if (this.readOnly != readOnly)
    		dirty = true;       // the node is dirty, not the file
        this.readOnly = readOnly;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile#rewrite(ISourceFormatter)
     */
    public boolean rewrite(ISourceFormatter formatter) {

        if (file == null)
            return false;
        
        if (readOnly || !hasDirtySourceTree())
            return false;
        
        if (file.getText() != null) {
        	formatter.getSourceFormatting().setEOL(scanForNewLine(file.getText()));
        }
        
        String text = getCurrentTextUpdating(formatter, file, 0, 1);

        dirty = false;
        
        char[] textArray = text.toCharArray();
        
        if (Arrays.equals(textArray, file.getText())) {
        	file.setDirty(false);
            return false;
        }
        
        file.setText(textArray);
        
        return true;
    }
    
    /**
     * Scan for the currently existing newline style
	 * @param text
	 * @return newline sequence or null if unknown
	 */
	private String scanForNewLine(char[] text) {
		for (int idx = 0; idx < text.length; idx++) {
			if (text[idx] == '\n')
				return "\n";
			if (text[idx] == '\r') 
				if (idx + 1 < text.length && text[idx+1] == '\n')
					return "\r\n";
				else
					return "\r";
		}
		return null;
	}

	/* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile#removeAllContents()
     */
    public void removeAllContents() {
        
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile#flatten()
     */
    public void flatten() {
        
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile#moveContents(com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile)
     */
    public void moveContents(IAstSourceFile file) {
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#getSourceFormatter()
     */
    public ISourceFormatter getSourceFormatter() {
        return formatter;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#setSourceFormatter()
     */
    public void setSourceFormatter(ISourceFormatter formatter) {
        this.formatter = formatter;
    }
 
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#getNodes()
     */
    public IAstNode[] getFileNodes() {
        return (IAstNode[]) fileNodes.getChildren();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#getFileNodes(java.lang.Class)
     */
    public IAstNode[] getFileNodes(Class klass) {
        if (klass == null)
            return (IAstNode[]) fileNodes.getChildren();

        List nodes = new ArrayList();
        for (ListIterator iter = fileNodes.listIterator(); iter.hasNext();) {
            IAstNode node = (IAstNode) iter.next();
            if (klass.isInstance(node))
                nodes.add(node);
        }
        
        Object arr = Array.newInstance(klass, nodes.size());
        return (IAstNode[]) nodes.toArray((IAstNode[]) arr);
   }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#addNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    public void addFileNode(IAstNode newNode) {
    	// Track the node before which to insert newNode.
    	// This is either the node whose source is after newNode,
    	// or the first node without source (assumed to be new,
    	// thus intended to follow any existing text) before
    	// any such node.
    	IAstNode before = null;
    	ISourceRange newRange = newNode.getExtendedRange();
    	if (newRange != null) {
    		for (ListIterator iter = fileNodes.listIterator(); iter.hasNext();) {
				IAstNode node = (IAstNode) iter.next();
				ISourceRange range = node.getExtendedRange();
				if (range != null) {
					int cmp = range.compareTo(newRange);
					Check.checkArg(cmp != 0); 
					if (cmp > 0) {
						// try to insert before new nodes
						if (before == null)
							before = node;
						break;
					}
				} else {
					// try to insert before the first new node
					if (before == null)
						before = node;
				}
			}
    		insertBeforeFileNode(newNode, before);
    	} else {
    		fileNodes.addItem(newNode);
    	}
        markDirtyTU();
    }

    protected void markDirtyTU() {
        ITranslationUnit tu = getTranslationUnit();
        if (tu != null)
            tu.markDirty();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#appendNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    public void appendFileNode(IAstNode node) {
    	fileNodes.addItem(node);
        markDirtyTU();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#insertNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode, com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    public void insertFileNode(IAstNode after, IAstNode node) {
    	fileNodes.insertItem(after, node);
        markDirtyTU();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#insertBeforeFileNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode, com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    public void insertBeforeFileNode(IAstNode node, IAstNode before) {
    	fileNodes.insertBeforeFileNode(node, before);
        markDirtyTU();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRssSourceFile#removeNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    public void removeFileNode(IAstNode node) {
    	fileNodes.removeItem(node);
        markDirtyTU();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile#findFileNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
     */
    public boolean findFileNode(IAstNode node) {
    	return fileNodes.containsItem(node);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile#setReadOnlyRecursive(boolean)
     */
    public void setReadOnlyRecursive(boolean readOnly) {
    	setReadOnly(readOnly);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile#getFileNodeList()
     */
    public IAstListNode getFileNodeList() {
    	return fileNodes;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile#findNodesAt(int)
     */
    public IAstNode[] findNodesAt(int offset) {
    	List<IAstNode> nodes = new ArrayList<IAstNode>();
    	findNodesAt(nodes, this, offset);
    	return (IAstNode[]) nodes.toArray(new IAstNode[nodes.size()]);
    }

	/**
	 * @param nodes
	 * @param node
	 */
	private void findNodesAt(List<IAstNode> nodes, IAstNode node, int offset) {
		boolean search = false;
		ISourceRange range = node.getSourceRange();
		if (range != null && range.getOffset() <= offset && offset < range.getEndOffset()) {
			nodes.add(node);
			search = true;
		} else if (range == null) {
			search = true;
		}
		if (search) {
			IAstNode[] kids = node.getChildren();
			for (int i = 0; i < kids.length; i++) {
				if (range == null
						|| kids[i].getSourceRange() == null 
						|| kids[i].getSourceRange().getFile() == range.getFile()) {
					findNodesAt(nodes, kids[i], offset);
				}
			}
		}
	}

    
}
